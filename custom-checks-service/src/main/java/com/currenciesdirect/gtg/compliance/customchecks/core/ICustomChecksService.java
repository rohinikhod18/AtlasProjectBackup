package com.currenciesdirect.gtg.compliance.customchecks.core;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.SearchWhiteListRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.response.SearchWhiteListResponse;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Interface ICustomerSearchService.
 * 
 * @author Rajesh
 */
public interface ICustomChecksService {

	/**
	 * @param request
	 * @return
	 * @throws CustomChecksException
	 */
	public CustomCheckResponse performCheck(CustomChecksRequest request) throws CustomChecksException;

	/**
	 * @param request
	 * @return
	 * @throws CustomChecksException
	 */
	public CustomCheckResponse repeatCheck(CustomChecksRequest request) throws CustomChecksException;

	/**
	 * @param request
	 * @return
	 * @throws CustomChecksException
	 */
	public CustomCheckResponse updateWhiteListData(AccountWhiteList request) throws CustomChecksException;

	/**
	 * @param request
	 * @return
	 */
	public SearchWhiteListResponse searchAccountWhilteList(SearchWhiteListRequest request);

	/**
	 * Gets the beneficiary details.
	 * 
	 * @param beneBankAccNumber
	 *
	 * @return the beneficiary details
	 * @throws CustomChecksException
	 *             the custom checks exception
	 */
	public PayeeESResponse getBeneficiaryDetails(String beneBankAccNumber) throws CustomChecksException;

}
