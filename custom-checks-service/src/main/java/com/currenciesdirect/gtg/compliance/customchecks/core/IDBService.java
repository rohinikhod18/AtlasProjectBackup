package com.currenciesdirect.gtg.compliance.customchecks.core;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Interface IDBService.
 * 
 * @author Rajesh
 */
public interface IDBService {

	/**
	 * Save document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan response
	 * @throws CustomChecksException
	 *             the customer data scan exception
	 */
	public CustomCheckResponse saveDocument(CustomChecksRequest document)
			throws CustomChecksException;

	/**
	 * Update document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan response
	 * @throws CustomChecksException
	 *             the customer data scan exception
	 */
	public CustomCheckResponse updateDocument(CustomChecksRequest document)
			throws CustomChecksException;

	/**
	 * Delete document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan response
	 * @throws CustomChecksException
	 *             the customer data scan exception
	 */
	public CustomCheckResponse deleteDocument(CustomChecksRequest document)
			throws CustomChecksException;

	/**
	 * Search document.
	 * 
	 * @param document
	 *            the document
	 * @return the customer data scan search response
	 * @throws CustomChecksException
	 *             the customer data scan exception
	 */
	public Integer searchDocument(CustomChecksRequest document)
			throws CustomChecksException;
}
