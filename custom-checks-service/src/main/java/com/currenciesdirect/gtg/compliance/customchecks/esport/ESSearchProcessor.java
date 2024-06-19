/*
 * 
 */
package com.currenciesdirect.gtg.compliance.customchecks.esport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;

import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.ClientPayee;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeClient;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeParents;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePayments;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;

/**
 * The Class ESSearchProcessor.
 */
public class ESSearchProcessor {

	/**
	 * Search funds out document.
	 *
	 * @param orgCode
	 *            the org code
	 * @param accId
	 *            the acc id
	 * @param baneAccNo
	 *            the bane acc no
	 * @param date
	 *            the date
	 * @param tradeAccNum
	 *            the trade acc num
	 * @return the search result
	 */
	public SearchResult searchFundsOutDocument(String orgCode, Integer accId, String baneAccNo, String date,
			String tradeAccNum) {

		TransportClient client = ESTransportClient.getInstance();
		// Query to find number of funds out done today
		BoolQueryBuilder boolQuery = new BoolQueryBuilder();
		BoolQueryBuilder innerboolQuery = QueryBuilders.boolQuery();
		boolQuery.must(QueryBuilders.matchQuery(Constants.FIELD_ACC_ID, accId));
		boolQuery.must(QueryBuilders.matchQuery(Constants.FIELD_ORG_CODE, orgCode));
		boolQuery.must(QueryBuilders.matchQuery(Constants.FIELD_CREATED_ON, date));
		boolQuery.must(QueryBuilders.matchQuery(Constants.FIELD_IS_DELETED, Boolean.FALSE));
		innerboolQuery.filter(
				QueryBuilders.termQuery(Constants.FIELD_BENEFICIARY_COUNTRY, Constants.VALUE_BENEFICIARY_COUNTRY));
		innerboolQuery
				.filter(QueryBuilders.termQuery(Constants.FIELD_BUYING_CURRENCY, Constants.VALUE_BUYING_CURRENCY));
		innerboolQuery.filter(QueryBuilders.termQuery(Constants.FIELD_BUYING_AMOUNT,
				Double.parseDouble(Constants.VALUE_BUYING_AMOUNT)));
		boolQuery.mustNot(innerboolQuery);
		
		FilterAggregationBuilder lowAgg = AggregationBuilders.filter(Constants.AGG_INTRA_DAY, boolQuery);
		SearchRequestBuilder searchIntraDay = client.prepareSearch(Constants.INDEX_FUNDSOUT).setSize(0).setFrom(0)
				.setQuery(null);
		searchIntraDay.addAggregation(lowAgg);

		// Query to find if same beneficiary account number is used by any other
		// accounts
		BoolQueryBuilder beneQuery = new BoolQueryBuilder();
		beneQuery.must(QueryBuilders.matchPhraseQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, baneAccNo));

		BoolQueryBuilder currentAccountExclusion = QueryBuilders.boolQuery();
		currentAccountExclusion.filter(
				QueryBuilders.termQuery(Constants.FIELD_ACC_ID, accId));
		beneQuery.mustNot(currentAccountExclusion);
		AggregationBuilder distinctTradeAccNumber = AggregationBuilders.terms(Constants.AGG_TRADE_ACC_NUMBER)
				.field(Constants.FIELD_TRADE_ACC_NUMBER).size(100);
		SearchRequestBuilder searchTradeAccNumberWithBene = client.prepareSearch(Constants.INDEX_FUNDSOUT).setSize(0)
				.setFrom(0).setQuery(beneQuery);
		searchTradeAccNumberWithBene.addAggregation(distinctTradeAccNumber);

		// Query to get Whitelist data
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_WHITELIST_ACC_ID, accId));
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_WHITELIST_ORG_CODE, orgCode));
		SearchRequestBuilder accountSearch = client.prepareSearch(Constants.INDEX_WHITELIST).setQuery(accQuery);

		MultiSearchResponse sr = client.prepareMultiSearch().add(searchIntraDay).add(searchTradeAccNumberWithBene)
				.add(accountSearch).get();
		SearchResponse srOnly = sr.getResponses()[0].getResponse();
		SearchResponse srMatchedTradeAccNum = sr.getResponses()[1].getResponse();
		SearchResponse srAccWhiteList = sr.getResponses()[2].getResponse();

		SearchResult result = new SearchResult();
		if (null != srOnly && srOnly.getHits().getTotalHits() > 0) {
			Filter intraDay = srOnly.getAggregations().get(Constants.AGG_INTRA_DAY);
			result.setIntraDayCount(intraDay.getDocCount());
		}
		if (null != srMatchedTradeAccNum && srMatchedTradeAccNum.getHits().getTotalHits() > 0) {
			updateResultWithBeneMatch(tradeAccNum, srMatchedTradeAccNum, result);
		}
		if (null != srAccWhiteList && srAccWhiteList.getHits().getTotalHits() > 0) {
			String source = srAccWhiteList.getHits().getHits()[0].getSourceAsString();
			result.setAccWhiteList(JsonConverterUtil.convertToObject(AccountWhiteList.class, source));
		}
		return result;
	}

	/**
	 * Update result with bene match.
	 *
	 * @param tradeAccNum
	 *            the trade acc num
	 * @param srMatchedTradeAccNum
	 *            the sr matched trade acc num
	 * @param result
	 *            the result
	 */
	private void updateResultWithBeneMatch(String tradeAccNum, SearchResponse srMatchedTradeAccNum,
			SearchResult result) {
		StringTerms resultAgg = srMatchedTradeAccNum.getAggregations().get(Constants.AGG_TRADE_ACC_NUMBER);
		StringBuilder acccountsFound = new StringBuilder();
		Long count = 0L;
		for (Terms.Bucket entry : resultAgg.getBuckets()) {
			if (!tradeAccNum.equalsIgnoreCase(entry.getKeyAsString())) {
				count++;
				acccountsFound.append(entry.getKeyAsString()).append(", ");
			}
		}
		result.setOtherAccountsWithBene(count);
		String matchedAccNumber = "No Match Found";
		if (acccountsFound.toString().length() > 1) {
			matchedAccNumber = acccountsFound.toString().substring(0, acccountsFound.toString().length() - 2);
		}
		result.setMatchedAccNumber(matchedAccNumber);
	}

	/**
	 * Search white list document.
	 *
	 * @param orgCode
	 *            the org code
	 * @param accId
	 *            the acc id
	 * @return the search result
	 */
	public SearchResult searchWhiteListDocument(String orgCode, Integer accId) {
		SearchResult result = new SearchResult();

		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_WHITELIST_ACC_ID, accId));
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_WHITELIST_ORG_CODE, orgCode));
		TransportClient client = ESTransportClient.getInstance();
		SearchRequestBuilder accountSearch = client.prepareSearch(Constants.INDEX_WHITELIST).setTypes("account")
				.setQuery(accQuery);
		SearchResponse srAcc = client.search(accountSearch.request()).actionGet();
		if (null != srAcc && srAcc.getHits().getTotalHits() > 0) {
			String source = srAcc.getHits().getHits()[0].getSourceAsString();
			result.setAccWhiteList(JsonConverterUtil.convertToObject(AccountWhiteList.class, source));
		}
		return result;
	}

	/**
	 * Search beneficiary details.
	 *
	 * @param beneAccNumber
	 *            the bene acc number
	 * @return the custom check payee response
	 */
	public PayeeESResponse searchBeneficiaryDetails(String beneAccNumber) {
		PayeeESResponse customCheckPayeeResponse = new PayeeESResponse();
		List<PayeeClient> payeeClients = new ArrayList<>();
		List<String> tradeAccNumbersShouldNotMatchList = new ArrayList<>();
		List<String> tradeAccNumbersShouldMatchList = new ArrayList<>();
		getPayeeClientDetails(beneAccNumber, payeeClients, tradeAccNumbersShouldNotMatchList,tradeAccNumbersShouldMatchList);
		customCheckPayeeResponse.setChildren(payeeClients);

		return customCheckPayeeResponse;
	}

	/**
	 * Gets the payee client details.
	 *
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param payeeClients
	 *            the clients
	 * @param tradeAccNumbersShouldNotMatchList
	 * @param tradeAccNumbersShouldMatchList
	 * @return the payee client details
	 */
	private List<PayeeClient> getPayeeClientDetails(String beneAccNumber, List<PayeeClient> payeeClients,
			List<String> tradeAccNumbersShouldNotMatchList, List<String> tradeAccNumbersShouldMatchList) {

		TransportClient client = ESTransportClient.getInstance();
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));

		AggregationBuilder distinctTradeAccNumber = AggregationBuilders.terms(Constants.AGG_TRADE_ACC_NUMBER)
				.field(Constants.FIELD_TRADE_ACC_NUMBER).size(100);
		SearchRequestBuilder searchTradeAccNumberWithBene = client.prepareSearch(Constants.INDEX_FUNDSOUT)
				.setFetchSource(new String[] { Constants.FIELD_TRADE_ACCOUNT_NUMBER }, null).setQuery(accQuery);
		searchTradeAccNumberWithBene.addAggregation(distinctTradeAccNumber);
		SearchResponse srAcc = client.search(searchTradeAccNumberWithBene.request()).actionGet();
		Terms terms = srAcc.getAggregations().get("tradeAccNumber");
		Collection<Terms.Bucket> buckets = terms.getBuckets();

		for (Bucket bucket : buckets) {
			tradeAccNumbersShouldMatchList.add(bucket.getKeyAsString());
		}

		for (Bucket bucket : buckets) {
			PayeeClient clientObj = new PayeeClient();
			setPayeeClientDetails(beneAccNumber, clientObj, bucket, tradeAccNumbersShouldNotMatchList,tradeAccNumbersShouldMatchList);
			payeeClients.add(clientObj);
		}

		return payeeClients;

	}

	/**
	 * Sets the payee client details.
	 *
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param clientObj
	 *            the client obj
	 * @param bucket
	 *            the bucket
	 * @param tradeAccNumbersShouldNotMatchList
	 * @param tradeAccNumbersShouldMatchList
	 * @return the custom check payee client
	 */
	private PayeeClient setPayeeClientDetails(String beneAccNumber, PayeeClient clientObj, Bucket bucket,
			List<String> tradeAccNumbersShouldNotMatchList, List<String> tradeAccNumbersShouldMatchList) {
		List<PayeePayments> payments = new ArrayList<>();
		List<ClientPayee> children = new ArrayList<>();
		clientObj.setId(bucket.getKeyAsString());
		clientObj.setType("client");
		getClientPayees(clientObj.getId(), children, beneAccNumber, tradeAccNumbersShouldNotMatchList,tradeAccNumbersShouldMatchList);
		if (!children.isEmpty())
			clientObj.setChildren(children);
		getClientPayments(beneAccNumber, clientObj.getId(), payments);
		Integer numberOfBenes = getCountOfBeneficiaries(clientObj.getId(), beneAccNumber);
		clientObj.setPayments(payments);
		clientObj.setTotalBeneficiaries(numberOfBenes);

		return clientObj;
	}

	/**
	 * Gets the client payees.
	 *
	 * @param tradeAccNumber
	 *            the trade acc number
	 * @param children
	 *            the children
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param tradeAccNumbersShouldNotMatchList
	 * @param tradeAccNumbersShouldMatchList
	 * @return the client payees
	 */
	private List<ClientPayee> getClientPayees(String tradeAccNumber, List<ClientPayee> children, String beneAccNumber,
			List<String> tradeAccNumbersShouldNotMatchList, List<String> tradeAccNumbersShouldMatchList) {

		TransportClient client = ESTransportClient.getInstance();
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_TRADE_ACCOUNT_NUMBER, tradeAccNumber));
		accQuery.mustNot(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));

		AggregationBuilder distinctBeneAccNumber = AggregationBuilders.terms(Constants.AGG_BENE_ACC_NUMBER)
				.field(Constants.BENEFICIARY_ACCOUNT_NUMBER_KEYWORD).size(100);
		SearchRequestBuilder searchTradeAccNumberWithBene = client.prepareSearch(Constants.INDEX_FUNDSOUT)
				.setQuery(accQuery);
		searchTradeAccNumberWithBene.addAggregation(distinctBeneAccNumber);
		SearchResponse srAcc = client.search(searchTradeAccNumberWithBene.request()).actionGet();
		Terms terms = srAcc.getAggregations().get(Constants.AGG_BENE_ACC_NUMBER);
		Collection<Terms.Bucket> buckets = terms.getBuckets();

		for (Bucket bucket : buckets) {
			ClientPayee clientObj = new ClientPayee();
			if (!tradeAccNumbersShouldNotMatchList.contains(tradeAccNumber))
				tradeAccNumbersShouldNotMatchList.add(tradeAccNumber);
			setClientPayees(clientObj, bucket.getKeyAsString(), tradeAccNumber,tradeAccNumbersShouldNotMatchList, tradeAccNumbersShouldMatchList);
			if (!clientObj.getOtherParents().isEmpty())
				children.add(clientObj);
		}

		return children;

	}

	/**
	 * Sets the client payees.
	 *
	 * @param clientObj
	 *            the client obj
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param tradeAccNumber
	 *            the trade acc number
	 * @param tradeAccNumbersShouldNotMatchList
	 * @param tradeAccNumbersShouldMatchList
	 * @return the client payee
	 */
	private ClientPayee setClientPayees(ClientPayee clientObj, String beneAccNumber, String tradeAccNumber,
			List<String> tradeAccNumbersShouldNotMatchList, List<String> tradeAccNumbersShouldMatchList) {

		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));
		TransportClient client = ESTransportClient.getInstance();
		SearchRequestBuilder paymentSearch = client
				.prepareSearch(Constants.INDEX_FUNDSOUT).setFetchSource(new String[] {
						Constants.FIELD_BENE_ACCOUNT_NUMBER, "beneficiary.first_name", "beneficiary.last_name", }, null)
				.setQuery(accQuery);
		SearchResponse srAcc = client.search(paymentSearch.request()).actionGet();
		if (null != srAcc && srAcc.getHits().getHits().length > 0) {
			searchHitsAndSetValues(clientObj, srAcc);
		}
		List<PayeeParents> otherParents = new ArrayList<>();
		getPayeesOtherClients(beneAccNumber, otherParents, tradeAccNumbersShouldNotMatchList,tradeAccNumbersShouldMatchList);
		clientObj.setOtherParents(otherParents);
		Integer noPayments = 0;
		noPayments = getNoOfPayments(tradeAccNumber, beneAccNumber, noPayments);
		clientObj.setNoPayments(noPayments);

		return clientObj;
	}

	private void searchHitsAndSetValues(ClientPayee clientObj, SearchResponse srAcc) {
		String beneName = "";
		String firstName = "";
		String lastName = "";
		for (Map.Entry<String, Object> entry : srAcc.getHits().getHits()[0].getSource().entrySet()) {
			HashMap<String, Object> hm = (HashMap<String, Object>) entry.getValue();
			for (Map.Entry<String, Object> entry1 : hm.entrySet()) {
				if (entry1.getKey().equals("account_number"))
					clientObj.setId(entry1.getValue().toString());
				if (entry1.getKey().equals("first_name") && null != entry1.getValue())
					firstName = beneName.concat(entry1.getValue().toString());
				if (entry1.getKey().equals("last_name") && null != entry1.getValue())
					lastName = beneName.concat(entry1.getValue().toString());
			}
			beneName = beneName.concat(firstName).concat(" ").concat(lastName);
			clientObj.setName(beneName);
			clientObj.setType("beneficiary");
		}
	}

	/**
	 * Gets the payees other clients.
	 *
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param otherParents
	 *            the other parents
	 * @param tradeAccNumbersShouldNotMatchList
	 * @param tradeAccNumbersShouldMatchList
	 * @return the payees other clients
	 */
	private List<PayeeParents> getPayeesOtherClients(String beneAccNumber, List<PayeeParents> otherParents,
			List<String> tradeAccNumbersShouldNotMatchList, List<String> tradeAccNumbersShouldMatchList) {
		TransportClient client = ESTransportClient.getInstance();
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));
		for (String tradeAccNumber : tradeAccNumbersShouldNotMatchList)
			accQuery.mustNot(QueryBuilders.matchQuery(Constants.FIELD_TRADE_ACCOUNT_NUMBER, tradeAccNumber));

		AggregationBuilder distinctBeneAccNumber = AggregationBuilders.terms(Constants.AGG_TRADE_ACC_NUMBER)
				.field(Constants.FIELD_TRADE_ACC_NUMBER).size(100);
		SearchRequestBuilder searchTradeAccNumberWithBene = client.prepareSearch(Constants.INDEX_FUNDSOUT)
				.setQuery(accQuery);
		searchTradeAccNumberWithBene.addAggregation(distinctBeneAccNumber);
		SearchResponse srAcc = client.search(searchTradeAccNumberWithBene.request()).actionGet();
		Terms terms = srAcc.getAggregations().get(Constants.AGG_TRADE_ACC_NUMBER);
		Collection<Terms.Bucket> buckets = terms.getBuckets();

		for (Bucket bucket : buckets) {
			for (String tradeAccNumber : tradeAccNumbersShouldMatchList) {
				if (tradeAccNumber.equals(bucket.getKeyAsString())) {
					PayeeParents otherParentsObj = new PayeeParents();
					otherParentsObj.setId(bucket.getKeyAsString());
					if (!tradeAccNumbersShouldNotMatchList.contains(bucket.getKeyAsString()))
						tradeAccNumbersShouldNotMatchList.add(bucket.getKeyAsString());
					Integer noPayments = 0;
					noPayments = getNoOfPayments(bucket.getKeyAsString(), beneAccNumber, noPayments);
					otherParentsObj.setNoPayments(noPayments);
					otherParents.add(otherParentsObj);
				}
			}
		}
		return otherParents;
	}

	/**
	 * Gets the no of payments.
	 *
	 * @param keyAsString
	 *            the key as string
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param noPayments
	 *            the no payments
	 * @return the no of payments
	 */
	private Integer getNoOfPayments(String keyAsString, String beneAccNumber, Integer noPayments) {
		TransportClient client = ESTransportClient.getInstance();
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_TRADE_ACCOUNT_NUMBER, keyAsString));

		AggregationBuilder distinctBeneAccNumber = AggregationBuilders.terms(Constants.AGG_BENE_ACC_NUMBER)
				.field(Constants.BENEFICIARY_ACCOUNT_NUMBER_KEYWORD).size(100);
		SearchRequestBuilder searchTradeAccNumberWithBene = client.prepareSearch(Constants.INDEX_FUNDSOUT)
				.setQuery(accQuery);
		searchTradeAccNumberWithBene.addAggregation(distinctBeneAccNumber);
		SearchResponse srAcc = client.search(searchTradeAccNumberWithBene.request()).actionGet();
		Terms terms = srAcc.getAggregations().get(Constants.AGG_BENE_ACC_NUMBER);
		Collection<Terms.Bucket> buckets = terms.getBuckets();

		for (Bucket bucket : buckets) {
			noPayments = (int) bucket.getDocCount();
		}
		return noPayments;
	}

	/**
	 * Gets the count of beneficiaries.
	 *
	 * @param tradeAccountNumber
	 *            the trade account number
	 * @param beneAccNumber
	 *            the bene acc number
	 * @return the count of beneficiaries
	 */
	private Integer getCountOfBeneficiaries(String tradeAccountNumber, String beneAccNumber) {

		Integer numberOfBenes;
		TransportClient client = ESTransportClient.getInstance();
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_TRADE_ACCOUNT_NUMBER, tradeAccountNumber));
		accQuery.mustNot(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));

		AggregationBuilder countOfBenes = AggregationBuilders.cardinality(Constants.AGG_COUNT_OF_BENE)
				.field(Constants.BENEFICIARY_ACCOUNT_NUMBER_KEYWORD);
		SearchRequestBuilder searchNumberOfBenesWithTradeAccNumber = client.prepareSearch(Constants.INDEX_FUNDSOUT)
				.setQuery(accQuery);
		searchNumberOfBenesWithTradeAccNumber.addAggregation(countOfBenes);
		SearchResponse srAcc = client.search(searchNumberOfBenesWithTradeAccNumber.request()).actionGet();
		Cardinality cardinality = srAcc.getAggregations().get("countOfBene");
		numberOfBenes = (int) cardinality.getValue();

		return numberOfBenes;
	}

	/**
	 * Gets the client payments.
	 *
	 * @param beneAccNumber
	 *            the bene acc number
	 * @param tradeAccNumber
	 *            the trade acc number
	 * @param payments
	 *            the payments
	 * @return the client payments
	 */
	private List<PayeePayments> getClientPayments(String beneAccNumber, String tradeAccNumber,
			List<PayeePayments> payments) {
		BoolQueryBuilder accQuery = new BoolQueryBuilder();
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_BENE_ACCOUNT_NUMBER, beneAccNumber));
		accQuery.must(QueryBuilders.matchQuery(Constants.FIELD_TRADE_ACCOUNT_NUMBER, tradeAccNumber));
		TransportClient client = ESTransportClient.getInstance();
		SearchRequestBuilder paymentSearch = client.prepareSearch(Constants.INDEX_FUNDSOUT)
				.setFetchSource(new String[] { Constants.FIELD_TRADE_ACCOUNT_NUMBER, Constants.FIELD_BUYING_CURRENCY,
						Constants.FIELD_BUYING_AMOUNT, Constants.VALUE_PAYMENT_REFERENCE,
						Constants.VALUE_TRADE_TRANSACTION_DATE }, null)
				.setQuery(accQuery);
		SearchResponse srAcc = client.search(paymentSearch.request()).actionGet();
		if (null != srAcc && srAcc.getHits().getHits().length > 0) {

			for (int i = 0; i < srAcc.getHits().getHits().length; i++) {
				PayeePayments paymentsObj = new PayeePayments();
				for (Map.Entry<String, Object> entry : srAcc.getHits().getHits()[i].getSource().entrySet()) {
					paymentsObj = setClientPaymentDetails(paymentsObj, entry);
				}
				payments.add(paymentsObj);
			}

		}

		return payments;
	}

	/**
	 * Sets the client payment details.
	 *
	 * @param paymentsObj
	 *            the payments obj
	 * @param entry
	 *            the entry
	 * @return the payee payments
	 */
	private PayeePayments setClientPaymentDetails(PayeePayments paymentsObj, Map.Entry<String, Object> entry) {
		HashMap<String, Object> hm = (HashMap<String, Object>) entry.getValue();
		for (Map.Entry<String, Object> entry1 : hm.entrySet()) {
			if (entry1.getKey().equals("buy_currency"))
				paymentsObj.setCurrency(entry1.getValue().toString());
			if (entry1.getKey().equals("amount"))
				paymentsObj.setAmount(Double.parseDouble(entry1.getValue().toString()));
			if (entry1.getKey().equals("payment_reference")
					&& (entry1.getValue() == null || entry1.getValue().equals("")))
				paymentsObj.setReference(Constants.DASH_DETAILS_PAGE);
			else if (entry1.getKey().equals("payment_reference"))
				paymentsObj.setReference(entry1.getValue().toString());
			if (entry1.getKey().equals("trans_ts"))
				paymentsObj.setDate(entry1.getValue().toString());
		}
		return paymentsObj;
	}

}