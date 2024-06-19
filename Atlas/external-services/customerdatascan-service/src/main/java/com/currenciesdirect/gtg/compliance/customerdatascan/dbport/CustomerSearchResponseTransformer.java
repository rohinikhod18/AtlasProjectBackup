package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerSearchRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerSearchResponseData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.MatchAndCount;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanErrors;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;


/**
 * The Class CustomerSearchResponseTransformer.
 */
public class CustomerSearchResponseTransformer
		implements
		ITransformer<CustomerSearchResponseData, CustomerSearchRequestData> {

	/** The elastic response. */
	private String elasticResponse;
	
	private boolean isFound;

	/**
	 * Instantiates a new customer search response transformer.
	 *
	 * @param elasticResponse the elastic response
	 */
	public CustomerSearchResponseTransformer(String elasticResponse) {
		this.elasticResponse = elasticResponse;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customerdatascan.dbport.ITransformer#transform(java.lang.Object)
	 */
	@Override
	public CustomerSearchResponseData transform(
			CustomerSearchRequestData request)
			throws CustomerDataScanException {
		CustomerSearchResponseData searchResponse = null;
		try {
			JSONObject json = new JSONObject(getElasticResponse());
			json = (JSONObject) json.get(DBConstants.HITS.getValue());
			JSONArray array = json.getJSONArray(DBConstants.HITS.getValue());
			searchResponse = new CustomerSearchResponseData();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				ObjectMapper mapper = new ObjectMapper();
				DBModel model = mapper.readValue(object.get(DBConstants.SOURCE.getValue())
						.toString(), DBModel.class);
				matchSearchRequestWithResponse(request, searchResponse, model);
			}
			searchResponse.setStatus(isFound ? ResponseStatus.SUCCESS.getStatus() : ResponseStatus.FAILED.getStatus());
		} catch (Exception e) {
			throw new CustomerDataScanException(
					CustomerDataScanErrors.ERROR_WHILE_PARSING_RESPONSE, e);
		}
		return searchResponse;
	}

	/**
	 * Gets the elastic response.
	 *
	 * @return the elastic response
	 */
	public String getElasticResponse() {
		return elasticResponse;
	}

	/**
	 * Sets the elastic response.
	 *
	 * @param elasticResponse the new elastic response
	 */
	public void setElasticResponse(String elasticResponse) {
		this.elasticResponse = elasticResponse;
	}

	/**
	 * Match search request with response.
	 *
	 * @param request the request
	 * @param response the response
	 * @param model the model
	 */
	private void matchSearchRequestWithResponse(
			CustomerSearchRequestData request,
			CustomerSearchResponseData response, DBModel model) {
		response.setId(request.getId());
		response.setAddress(isMatch(request.getAddress(), model.getAddress(),
				response.getAddress()));
		response.setAuroraAccountID(isMatch(request.getAuroraAccountID(),
				model.getAuroraAccountID(), response.getAuroraAccountID()));
		response.setCountryOfResidence(isMatch(request.getCountryOfResidence(),
				model.getCountryOfResidence(), response.getCountryOfResidence()));
		response.setDobDay(isMatch(request.getDobDay(), model.getDobDay(),
				response.getDobDay()));
		response.setDobMonth(isMatch(request.getDobMonth(),
				model.getDobMonth(), response.getDobMonth()));
		response.setDobYear(isMatch(request.getDobYear(), model.getDobYear(),
				response.getDobYear()));
		response.setEmail(isMatch(request.getEmail(), model.getEmail(),
				response.getEmail()));
		response.setFamilyNameAtBirth(isMatch(request.getFamilyNameAtBirth(),
				model.getFamilyNameAtBirth(), response.getFamilyNameAtBirth()));
		response.setFamilyNameAtCitizenship(isMatch(
				request.getFamilyNameAtCitizenship(),
				model.getFamilyNameAtCitizenship(),
				response.getFamilyNameAtCitizenship()));
		response.setFax(isMatch(request.getFax(), model.getFax(),
				response.getFax()));
		response.setGender(isMatch(request.getGender(), model.getGender(),
				response.getGender()));
		response.setIpAddress(isMatch(request.getIpAddress(),
				model.getIpAddress(), response.getIpAddress()));
		response.setNationality(isMatch(request.getNationality(),
				model.getNationality(), response.getNationality()));
		response.setPhone(isMatch(request.getPhone(), model.getPhone(),
				response.getPhone()));
		response.setPlaceOfBirth(isMatch(request.getPlaceOfBirth(),
				model.getPlaceOfBirth(), response.getPlaceOfBirth()));
		response.setSfAccountID(isMatch(request.getSfAccountID(),
				model.getSfAccountID(), response.getSfAccountID()));
		response.setDigitalFootPrint(isMatch(request.getDigitalFootPrint(),
				model.getDigitalFootPrint(), response.getDigitalFootPrint()));
		response.setName(isMatch(request.getName(), model.getName(),
				response.getName()));
	}

	/**
	 * Checks if is match.
	 *
	 * @param <T> the generic type
	 * @param <P> the generic type
	 * @param value1 the value 1
	 * @param value2 the value 2
	 * @param matchAndCount the match and count
	 * @return the match and count
	 */
	private <T, P> MatchAndCount isMatch(T value1, P value2,
			MatchAndCount matchAndCount) {
		MatchAndCount temp = matchAndCount;
		if (temp == null && value1 != null) {
			temp = new MatchAndCount();
		}
		if (value1 != null && value2 != null && value1.equals(value2)) {
			temp.setFound(true);
			temp.setCount(temp.getCount() + 1);
			isFound =true;
		}
		return temp;
	}

}
