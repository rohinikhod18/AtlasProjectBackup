package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;

/**
 * The Interface ICustomerModifierService.
 * 
 * @author Rajesh
 */
public interface ICustomerModifierService {

	/**
	 * Save document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan response
	 * @throws CustomerDataScanException
	 *             the customer data scan exception
	 */
	public CustomerDataScanInsertResponse saveDocument(CustomerDataScanInsertRequest document)
			throws CustomerDataScanException;

	/**
	 * Update document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan response
	 * @throws CustomerDataScanException
	 *             the customer data scan exception
	 */
	public CustomerDataScanResponse updateDocument(CustomerDataScanUpdateRequest document)
			throws CustomerDataScanException;

	/**
	 * Delete document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan response
	 * @throws CustomerDataScanException
	 *             the customer data scan exception
	 */
	public CustomerDataScanResponse deleteDocument(CustomerDataScanDeleteRequest document)
			throws CustomerDataScanException;

}
