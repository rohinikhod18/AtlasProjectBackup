package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Interface ITransactionMonitoringProviderService.
 */
public interface ITransactionMonitoringProviderService {

	/**
	 * Perfrom intuition account check.
	 *
	 * @param request                 the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringAccountSignupResponse perfromIntuitionAccountCheck(TransactionMonitoringSignupRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException;

	
	/**
	 * Perfrom intuition for funds in.
	 *
	 * @param request the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring funds in response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPaymentInResponse perfromIntuitionForFundsInCheck(TransactionMonitoringPaymentsInRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException;
	
	/**
	 * Perfrom intuition for funds out check.
	 *
	 * @param request the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring payment out response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPaymentOutResponse perfromIntuitionForFundsOutCheck(TransactionMonitoringPaymentsOutRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException;
	
	/**
	 * Perfrom intuition update account check.
	 *
	 * @param request the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring account signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringAccountSignupResponse perfromIntuitionUpdateAndAddContactCheck(TransactionMonitoringSignupRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException;

	/**
	 * Perform intuition post card transaction check.
	 *
	 * @param request the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring post card transaction response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPostCardTransactionResponse performIntuitionPostCardTransactionCheck(
			TransactionMonitoringPostCardTransactionRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException;
}
