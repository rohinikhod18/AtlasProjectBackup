package com.currenciesdirect.gtg.compliance.customchecks.esport;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.currenciesdirect.gtg.compliance.customchecks.domain.fundsout.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;

/**
 * The Class SearchQueryBuilder.
 */
@SuppressWarnings("squid:S3077")
public class SearchQueryBuilder {
	
	/** The builder. */
	private static volatile  SearchQueryBuilder builder = null;

	/**
	 * Instantiates a new search query builder.
	 */
	private SearchQueryBuilder() {
	}

	/**
	 * Gets the single instance of SearchQueryBuilder.
	 *
	 * @return single instance of SearchQueryBuilder
	 */
	public static SearchQueryBuilder getInstance() {
		if (builder == null) {
			synchronized (SearchQueryBuilder.class) {
				if (builder == null) {
					builder = new SearchQueryBuilder();
				}
			}
		}
		return builder;
	}

	/**
	 * Gets the boolen query builder.
	 *
	 * @return the boolen query builder
	 */
	public BoolQueryBuilder getBoolenQueryBuilder() {
		return QueryBuilders.boolQuery();
	}

	/**
	 * Adds the should with term.
	 *
	 * @param <T> the generic type
	 * @param boolqueryBuilder the boolquery builder
	 * @param fieldName the field name
	 * @param fieldValue the field value
	 */
	public <T extends Object> void addShouldWithTerm(
			BoolQueryBuilder boolqueryBuilder, String fieldName, T fieldValue) {
		if (fieldName != null && fieldValue != null)
			boolqueryBuilder.should(QueryBuilders.termQuery(fieldName,
					fieldValue));
	}

	/**
	 * Adds the must with term.
	 *
	 * @param boolqueryBuilder the boolquery builder
	 * @param fieldName the field name
	 * @param fieldValue the field value
	 */
	public void addMustWithTerm(BoolQueryBuilder boolqueryBuilder,
			String fieldName, String fieldValue) {
		if (fieldName != null && fieldValue != null)
			boolqueryBuilder.must(QueryBuilders
					.termQuery(fieldName, fieldValue));
	}

	/**
	 * Builds the.
	 *
	 * @param boolqueryBuilder the boolquery builder
	 * @return the string
	 */
	public String build(BoolQueryBuilder boolqueryBuilder) {
		return "{\"query\":" + boolqueryBuilder + "}";
	}
	
	
	/**
	 * Gets the elastic search request.
	 * 
	 * @param document
	 *            the document
	 * @return the elastic search request
	 */
	public static String getElasticSearchRequest(CustomChecksRequest document) {
		builder = SearchQueryBuilder.getInstance();
		BoolQueryBuilder boolQueryBuilder = builder.getBoolenQueryBuilder();

		builder.addMustWithTerm(boolQueryBuilder, "orgId", document.getOrgCode());
		FundsOutRequest fRequest = (FundsOutRequest) document.getESDocument();
		builder.addMustWithTerm(boolQueryBuilder, "trade.trade_contact_id",
				fRequest.getTrade().getTradeContactId().toString());

		builder.addMustWithTerm(boolQueryBuilder, "isDeleted", "false");
		return builder.build(boolQueryBuilder);
	}
	

}
