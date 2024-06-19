/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Interface ITransactionMonitoringService.
 */
public interface ITransactionMonitoringService {

	/**
	 * Do transaction monitoring check for new sign up.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringSignupResponse doTransactionMonitoringCheckForNewSignUp(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException;
	
	
	/**
	 * Do transaction monitoring check for update.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringSignupResponse doTransactionMonitoringCheckForUpdate(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException;
	
	/**
	 * Do transaction monitoring check for funds in.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPaymentInResponse doTransactionMonitoringCheckForFundsIn(
			TransactionMonitoringPaymentsInRequest request) throws TransactionMonitoringException;
	
	/**
	 * Do transaction monitoring check for funds out.
	 *
	 * @param request the request
	 * @return the transaction monitoring payment out response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPaymentOutResponse doTransactionMonitoringCheckForFundsOut(
			TransactionMonitoringPaymentsOutRequest request) throws TransactionMonitoringException;
	
	/**
	 * Do transaction monitoring check for post card transaction.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPostCardTransactionResponse doTransactionMonitoringCheckForPostCardTransaction(
			TransactionMonitoringPostCardTransactionRequest request) throws TransactionMonitoringException;
}

