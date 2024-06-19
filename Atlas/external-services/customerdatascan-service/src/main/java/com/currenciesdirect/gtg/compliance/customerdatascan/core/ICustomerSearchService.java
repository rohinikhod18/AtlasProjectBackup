package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;

/**
 * The Interface ICustomerSearchService.
 * 
 * @author Rajesh
 */
@FunctionalInterface
public interface ICustomerSearchService {

	/**
	 * Search document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan search response
	 * @throws CustomerDataScanException
	 *             the customer data scan exception
	 */
	public CustomerDataScanSearchResponse searchDocument(CustomerDataScanSearchRequest document)
			throws CustomerDataScanException;

}
