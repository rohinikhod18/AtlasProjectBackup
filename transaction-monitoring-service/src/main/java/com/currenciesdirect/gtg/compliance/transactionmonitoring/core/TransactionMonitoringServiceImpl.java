/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringErrors;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.transactionmonitoringport.IntuitionPort;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.util.Constants;

/**
 * The Class TransactionMonitoringServiceImpl.
 */
public class TransactionMonitoringServiceImpl implements ITransactionMonitoringService {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(TransactionMonitoringServiceImpl.class);

	/** The i transaction monitoring service. */
	@SuppressWarnings("squid:S3077")
	private static volatile ITransactionMonitoringService iTransactionMonitoringService = null;

	/** The i transaction monitoring provider service. */
	private ITransactionMonitoringProviderService iTransactionMonitoringProviderService = IntuitionPort.getInstance();

	/** The concrete data builder. */
	private TransactionMonitoringConcreteDataBuilder concreteDataBuilder = TransactionMonitoringConcreteDataBuilder
			.getInstance();
	
	private static final String ERROR = "Error:";

	/**
	 * Instantiates a new transaction monitoring service impl.
	 */
	private TransactionMonitoringServiceImpl() {
	}

	/**
	 * Gets the single instance of TransactionMonitoringServiceImpl.
	 *
	 * @return single instance of TransactionMonitoringServiceImpl
	 */
	public static ITransactionMonitoringService getInstance() {
		if (iTransactionMonitoringService == null) {
			synchronized (TransactionMonitoringServiceImpl.class) {
				if (iTransactionMonitoringService == null) {
					iTransactionMonitoringService = new TransactionMonitoringServiceImpl();
				}
			}
		}
		return iTransactionMonitoringService;

	}

	/**
	 * Do transaction monitoring check for new sign up.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringSignupResponse doTransactionMonitoringCheckForNewSignUp(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException {

		TransactionMonitoringSignupResponse responseNewSignup = new TransactionMonitoringSignupResponse();
		TransactionMonitoringAccountSignupResponse accSignupResponse = new TransactionMonitoringAccountSignupResponse();

		try {

			if (request.getAccountTMFlag() == 1 || request.getAccountTMFlag() == 2 || request.getAccountTMFlag() == 4
					|| request.getRequestType().equalsIgnoreCase("signup")) {
				accSignupResponse = performTransactionMonitoringAccountCheck(request);
			} else {
				accSignupResponse.setAccountId(request.getTransactionMonitoringAccountRequest().getId());
				accSignupResponse.setStatus(Constants.NOT_REQUIRED);
			}

			responseNewSignup.setTransactionMonitoringAccountSignupResponse(accSignupResponse);

		} catch (TransactionMonitoringException e) {
			LOG.error(ERROR, e);
		} catch (Exception e) {
			LOG.error("Error in doFraugsterCheckForNewRegistration() : ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.FAILED);
		}
		return responseNewSignup;
	}

	/**
	 * Perform transaction monitoring account check.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private TransactionMonitoringAccountSignupResponse performTransactionMonitoringAccountCheck(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException {

		TransactionMonitoringAccountSignupResponse tmResponse = new TransactionMonitoringAccountSignupResponse();
		TransactionMonitoringProviderProperty accountProviderProperty;

		try {
			accountProviderProperty = concreteDataBuilder
					.getProviderInitConfigProperty(Constants.TRANSACTION_MONITORING_SIGN_UP_ACCOUNT);
			tmResponse = iTransactionMonitoringProviderService.perfromIntuitionAccountCheck(request,
					accountProviderProperty);
			tmResponse.setAccountId(request.getTransactionMonitoringAccountRequest().getId());
			if(tmResponse.getStatus() == null) {
				tmResponse.setStatus(Constants.SERVICE_FAILURE);
			}

		} catch (TransactionMonitoringException e) {
			LOG.error("Error in performTransactionMonitoringAccountCheck() : ", e);
		}

		return tmResponse;

	}

	
	/**
	 * Do transaction monitoring check for update.
	 *
	 * @param request the request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringSignupResponse doTransactionMonitoringCheckForUpdate(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException {

		TransactionMonitoringSignupResponse responseNewSignup = new TransactionMonitoringSignupResponse();
		TransactionMonitoringAccountSignupResponse accSignupResponse = new TransactionMonitoringAccountSignupResponse();

		try {		
			if(request.getAccountTMFlag() == 1 || request.getAccountTMFlag() == 2 || request.getAccountTMFlag() == 4) {
					accSignupResponse = performTransactionMonitoringUpdateAndAddContactCheck(request);
			} else {
				accSignupResponse.setAccountId(request.getTransactionMonitoringAccountRequest().getId());
				accSignupResponse.setStatus(Constants.NOT_REQUIRED);
			}

			responseNewSignup.setTransactionMonitoringAccountSignupResponse(accSignupResponse);

		} catch (TransactionMonitoringException e) {
			LOG.error(ERROR, e);
		} catch (Exception e) {
			LOG.error("Error in doTransactionMonitoringCheckForUpdate() : ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.FAILED);
		}
		return responseNewSignup;
	}
	
	/**
	 * Do transaction monitoring check for funds in.
	 *
	 * @param request the request
	 * @return the transaction monitoring payment in response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringPaymentInResponse doTransactionMonitoringCheckForFundsIn(
			TransactionMonitoringPaymentsInRequest request) throws TransactionMonitoringException {

		TransactionMonitoringPaymentInResponse responseFundsIn = new TransactionMonitoringPaymentInResponse();

		try {
			
			responseFundsIn = performTransactionMonitoringFundsInCheck(request);

		} catch (TransactionMonitoringException e) {
			LOG.error(ERROR, e);
		} catch (Exception e) {
			LOG.error("Error in doTransactionMonitoringCheckForFundsIn() : ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.FAILED);
		}
		return responseFundsIn;
	}
	
	/**
	 * Perform transaction monitoring funds in check.
	 *
	 * @param request the request
	 * @return the transaction monitoring payment in response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private TransactionMonitoringPaymentInResponse performTransactionMonitoringFundsInCheck(
			TransactionMonitoringPaymentsInRequest request)
			throws TransactionMonitoringException {

		TransactionMonitoringPaymentInResponse tmResponse = new TransactionMonitoringPaymentInResponse();
		TransactionMonitoringProviderProperty fundsInProviderProperty;

		try {
			if(request.getAccountTMFlag() == 1 || request.getAccountTMFlag() == 2 || request.getAccountTMFlag() == 4) {
				fundsInProviderProperty = concreteDataBuilder
						.getProviderInitConfigProperty(Constants.TRANSACTION_MONITORING_FUNDSIN);
				tmResponse = iTransactionMonitoringProviderService.perfromIntuitionForFundsInCheck(request,
						fundsInProviderProperty);
				if (tmResponse.getStatus() == null) {
					tmResponse.setStatus(Constants.SERVICE_FAILURE);
				}
			} else {
				tmResponse.setStatus(Constants.NOT_REQUIRED);
			}
			tmResponse.setId(request.getFundsInId());

		} catch (TransactionMonitoringException e) {
			LOG.error("Error in performTransactionMonitoringAccountCheck() : ", e);
		}

		return tmResponse;

	}
	
	
	/**
	 * Do transaction monitoring check for funds out.
	 *
	 * @param request the request
	 * @return the transaction monitoring payment out response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPaymentOutResponse doTransactionMonitoringCheckForFundsOut(
			TransactionMonitoringPaymentsOutRequest request) throws TransactionMonitoringException {

		TransactionMonitoringPaymentOutResponse tmResponse = new TransactionMonitoringPaymentOutResponse();
		TransactionMonitoringProviderProperty fundsOutProviderProperty;

		try {
			if(request.getAccountTMFlag() == 1 || request.getAccountTMFlag() == 2 || request.getAccountTMFlag() == 4) {
				fundsOutProviderProperty = concreteDataBuilder
						.getProviderInitConfigProperty(Constants.TRANSACTION_MONITORING_FUNDSOUT);
				tmResponse = iTransactionMonitoringProviderService.perfromIntuitionForFundsOutCheck(request,
						fundsOutProviderProperty);
				if (tmResponse.getStatus() == null) {
					tmResponse.setStatus(Constants.SERVICE_FAILURE);
				}
			} else {
				tmResponse.setStatus(Constants.NOT_REQUIRED);
			}
			tmResponse.setId(request.getFundsOutId());

		} catch (TransactionMonitoringException e) {
			LOG.error(ERROR, e);
			tmResponse.setStatus(Constants.SERVICE_FAILURE);
		} catch (Exception e) {
			LOG.error("Error in doTransactionMonitoringCheckForFundsOut() : ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.FAILED);
		}
		return tmResponse;
	}
	
	/**
	 * Perform transaction monitoring update account check.
	 *
	 * @param request the request
	 * @return the transaction monitoring account signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private TransactionMonitoringAccountSignupResponse performTransactionMonitoringUpdateAndAddContactCheck(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException {

		TransactionMonitoringAccountSignupResponse tmResponse = new TransactionMonitoringAccountSignupResponse();
		TransactionMonitoringProviderProperty accountProviderProperty;

		try {
			accountProviderProperty = concreteDataBuilder
					.getProviderInitConfigProperty(Constants.TRANSACTION_MONITORING_SIGN_UP_ACCOUNT);
			tmResponse = iTransactionMonitoringProviderService.perfromIntuitionUpdateAndAddContactCheck(request,
					accountProviderProperty);
			tmResponse.setAccountId(request.getTransactionMonitoringAccountRequest().getId());
			if (tmResponse.getStatus() == null) {
				tmResponse.setStatus(Constants.SERVICE_FAILURE);
			}

		} catch (TransactionMonitoringException e) {
			LOG.error("Error in performTransactionMonitoringUpdateCheck() : ", e);
		}

		return tmResponse;

	}

	//AT-4896
	/**
	 * Do transaction monitoring check for post card transaction.
	 *
	 * @param request the request
	 * @return the transaction monitoring post card transaction response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringPostCardTransactionResponse doTransactionMonitoringCheckForPostCardTransaction(
			TransactionMonitoringPostCardTransactionRequest request) throws TransactionMonitoringException {
		TransactionMonitoringPostCardTransactionResponse tmResponse = new TransactionMonitoringPostCardTransactionResponse();
		TransactionMonitoringProviderProperty accountProviderProperty;
		
		try {
			accountProviderProperty = concreteDataBuilder
					.getProviderInitConfigProperty(Constants.TRANSACTION_MONITORING_POST_CARD_TRANSACTION);
			tmResponse = iTransactionMonitoringProviderService.performIntuitionPostCardTransactionCheck(request, accountProviderProperty);

		} catch(Exception e) {
			LOG.error("Error in doTransactionMonitoringCheckForPostCardTransaction() : ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.FAILED);
		}
		
		return tmResponse;
	}
}
