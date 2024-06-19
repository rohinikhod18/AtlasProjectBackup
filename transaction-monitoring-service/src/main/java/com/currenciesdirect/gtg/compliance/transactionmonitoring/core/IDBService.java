/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Interface IDBService.
 */
public interface IDBService {

	/**
	 * Gets the transaction monitoring provider init config property.
	 *
	 * @return the transaction monitoring provider init config property
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public ConcurrentMap<String, TransactionMonitoringProviderProperty> getTransactionMonitoringProviderInitConfigProperty()
			throws TransactionMonitoringException;
	
	/**
	 * Save into transaction monitoring MQ.
	 *
	 * @param accountID the account ID
	 * @param contactID the contact ID
	 * @param jsonAccountSignupRequest the json account signup request
	 * @param requestType the request type
	 * @param orgCode the org code
	 * @param createdBy the created by
	 * @param integer 
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public void saveIntoTransactionMonitoringMQ(Integer accountID, Integer contactID, String jsonAccountSignupRequest,String requestType, String orgCode, Integer createdBy, Integer integer) throws TransactionMonitoringException;

	/**
	 * Save into post card transaction monitorig MQ.
	 *
	 * @param trxID the trx ID
	 * @param jsonCardRequest the json card request
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public void saveIntoPostCardTransactionMonitorigMQ(String trxID, String jsonCardRequest, String cardRequestType) throws TransactionMonitoringException;

	/**
	 * Save into post card transaction monitoring failed request.
	 *
	 * @param trxID the trx ID
	 * @param jsonCardRequest the json card request
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public void saveIntoPostCardTransactionMonitoringFailedRequest(String trxID, String jsonCardRequest) throws TransactionMonitoringException;


}
