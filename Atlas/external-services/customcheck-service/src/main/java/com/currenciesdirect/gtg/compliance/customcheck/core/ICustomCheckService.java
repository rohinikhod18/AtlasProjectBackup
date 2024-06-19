package com.currenciesdirect.gtg.compliance.customcheck.core;

import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchResponse;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateResponse;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckException;

/**
 * The Interface ICustomCheckService.
 */
public interface ICustomCheckService {

	/**
	 * Save custom check details.
	 *
	 * @param request the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean saveCustomCheckDetails(CustomCheckInsertRequest request) throws CustomCheckException ;

	/**
	 * Update custom check details.
	 *
	 * @param request the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public CustomCheckUpdateResponse updateCustomCheckDetails(CustomCheckUpdateRequest request)throws CustomCheckException ;

	/**
	 * Delete custom check details.
	 *
	 * @param request the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean deleteCustomCheckDetails(CustomCheckDeleteRequest request)throws CustomCheckException ;

	/**
	 * Search custom check details.
	 *
	 * @param request the request
	 * @return the custom check search response
	 * @throws CustomCheckException the custom check exception
	 */
	public CustomCheckSearchResponse searchCustomCheckDetails(CustomCheckSearchRequest request)throws CustomCheckException ;

}
