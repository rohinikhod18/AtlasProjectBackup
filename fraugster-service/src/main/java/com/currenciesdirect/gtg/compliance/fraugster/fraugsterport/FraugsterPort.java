package com.currenciesdirect.gtg.compliance.fraugster.fraugsterport;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderSignUpRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterOnUpdateProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsInProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsOutProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSearchDataResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSignupProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.util.Constants;
import com.currenciesdirect.gtg.compliance.fraugster.util.ObjectUtils;

/**
 * The Class FraugsterPort.
 * 
 * @author abhijitg
 */
public class FraugsterPort implements IFraugsterProviderService {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FraugsterPort.class);

	/** The i fraugster provider service. */
	private static IFraugsterProviderService iFraugsterProviderService = null;

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	/** The fraugster transformer. */
	private FraugsterTransformer fraugsterTransformer = FraugsterTransformer.getInstance();

	/**
	 * Instantiates a new fraugster port.
	 */
	private FraugsterPort() {
	}

	/**
	 * Gets the single instance of FraugsterPort.
	 *
	 * @return single instance of FraugsterPort
	 */
	public static IFraugsterProviderService getInstance() {
		if (iFraugsterProviderService == null) {
			iFraugsterProviderService = new FraugsterPort();
		}
		return iFraugsterProviderService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.
	 * IFraugsterProviderService#doFraugsterCheck(com.currenciesdirect.gtg.
	 * compliance.fraugster.core.domain.IndentityFraudDetectionRequest)
	 */
	@Override
	public FraugsterSignupContactResponse doFraugsterSignupCheck(FraugsterSignupContactRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken) throws FraugsterException {
		Response response = null;
		ResteasyClient client = null;
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		FraugsterSignupProviderRequest fraugsterSignupProviderRequest = null;
		ProviderSignUpRequest providerSignUpRequest = null;
		FraugsterSearchDataResponse dataResponse = new FraugsterSearchDataResponse();
   try {
		
			 
				LOG.debug("FraugsterPort STARTED : doFraugsterCheck() ");
				client = resteasyClientHandler.getRetEasyClient();
	
				fraugsterSignupProviderRequest = fraugsterTransformer.transformNewSignupRequest(request);
				ObjectUtils.setNullFieldsToDefault(providerSignUpRequest);
				
				fraugsterSignupProviderRequest
						.setCustDOB(DateTimeFormatter.getDateInRFC3339(fraugsterSignupProviderRequest.getCustDOB()));
				fraugsterSignupProviderRequest.setCustSignupTs(
						DateTimeFormatter.getDateTimeInRFC3339(fraugsterSignupProviderRequest.getCustSignupTs()));
				fraugsterSignupProviderRequest.setDlExpiryDate(
						DateTimeFormatter.getDateInRFC3339(fraugsterSignupProviderRequest.getDlExpiryDate()));
				fraugsterSignupProviderRequest
						.setOsTs(DateTimeFormatter.getDateTimeInRFC3339(fraugsterSignupProviderRequest.getOsTs()));
				ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
				Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
						Constants.SESSION_TOKEN + " "  + sessionToken.getSessionToken());
				response = clientrequest.post(Entity.entity(fraugsterSignupProviderRequest, MediaType.APPLICATION_JSON));
				dataResponse = handleFraugsterServerTansactionResponse(response);
				fraudDetectionResponse = fraugsterTransformer.transformNewSignupResponse(dataResponse);
		 		
		} catch (FraugsterException e) {
			LOG.error("Error in  FraugsterPort : doFraugsterSignupCheck() ", e);
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_SIGNUP, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;
	}

	
	

	private FraugsterSearchDataResponse handleFraugsterServerTansactionResponse(Response response)
			throws FraugsterException {
		FraugsterSearchDataResponse dataResponse = new FraugsterSearchDataResponse();
		if(null != response) {
			if (response.getStatus() == 200) {
				dataResponse = response.readEntity(FraugsterSearchDataResponse.class);
			}
			else if (response.getStatus() == 401) {
				throw new FraugsterException(FraugsterErrors.UNAUTHORIZED_EXCEPTION);
			} 
			else if (response.getStatus() == 429) {
				throw new FraugsterException(FraugsterErrors.SERVER_THROTTLER_EXCEPTION);
			}
		   else if (response.getStatus() == 500) {
				throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_GENERIC_EXCEPTION);
			} else if (response.getStatus() == 400) {
				throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_INVALID_REQUEST_EXCEPTION);
			}
		} else {
			throw new FraugsterException(FraugsterErrors.UNAUTHORIZED_EXCEPTION);
		}
		return dataResponse;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService#doLogin(com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty)
	 */
	@Override
	public FraugsterSessionToken doLogin(FraugsterProviderProperty fraugsterProviderProperty) throws FraugsterException {

		FraugsterSessionToken sessionToken = new FraugsterSessionToken();
		Response response = null;
		ResteasyClient client = null;
		String credentials = null;
		String base64encoded = null;
		try {
			client = resteasyClientHandler.getRetEasyClient();
			credentials = fraugsterProviderProperty.getUserName() + ":" + fraugsterProviderProperty.getPassWord();
			base64encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION, "Basic " + base64encoded);
			clientrequest.accept("application/json;charset=UTF-8");
			response = clientrequest.post(null);
			sessionToken = handleFraugsterServerLoginResponse(response);
		} catch (Exception e) {
			LOG.error("Error in  FraugsterPort : doLogin() ", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return sessionToken;
	}

	/**
	 * Handle fraugster server login response.
	 *
	 * @param response the response
	 * @return the fraugster session token
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterSessionToken handleFraugsterServerLoginResponse(Response response) throws FraugsterException {
		FraugsterSessionToken sessionToken;
		if (response.getStatus() == 200) {
			sessionToken = response.readEntity(FraugsterSessionToken.class);
		} else if (response.getStatus() == 401) {
			throw new FraugsterException(FraugsterErrors.UNAUTHORIZED_EXCEPTION);
		} else if (response.getStatus() == 429) {
			throw new FraugsterException(FraugsterErrors.SERVER_THROTTLER_EXCEPTION);
		} else {
			throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_GENERIC_EXCEPTION);
		}
		return sessionToken;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService#doLogout(com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken)
	 */
	@Override
	public Boolean doLogout(FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken)
			throws FraugsterException {
		Response response = null;
		ResteasyClient client = null;
		Boolean status = Boolean.FALSE;
		try {
			client = resteasyClientHandler.getRetEasyClient();

			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					Constants.SESSION_TOKEN + " "  + sessionToken.getSessionToken());

			response = clientrequest.delete();
			status = handleFraugsterServerLogutResponse(response);
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_LOGOUT, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return status;
	}

	/**
	 * Handle fraugster server logut response.
	 *
	 * @param response the response
	 * @return the boolean
	 * @throws FraugsterException the fraugster exception
	 */
	private Boolean handleFraugsterServerLogutResponse(Response response) throws FraugsterException {
		boolean status;
		if (response.getStatus() == 200) {
			status = true;
		} else if (response.getStatus() == 401) {
			throw new FraugsterException(FraugsterErrors.UNAUTHORIZED_EXCEPTION);
		} else if (response.getStatus() == 429) {
			throw new FraugsterException(FraugsterErrors.SERVER_THROTTLER_EXCEPTION);
		} else {
			throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_GENERIC_EXCEPTION);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService#doFraugsterOnUpdateCheck(com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterOnUpdateContactRequest, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken)
	 */
	@Override
	public FraugsterOnUpdateContactResponse doFraugsterOnUpdateCheck(FraugsterOnUpdateContactRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken) throws FraugsterException {

		Response response = null;
		ResteasyClient client = null;
		FraugsterOnUpdateContactResponse fraudDetectionResponse = new FraugsterOnUpdateContactResponse();
		FraugsterOnUpdateProviderRequest fraugsterOnUpdateProviderRequest = null;
		FraugsterSearchDataResponse dataResponse = new FraugsterSearchDataResponse();
		try {
			LOG.debug("FraugsterPort STRATED : doFraugsterOnUpdateCheck() ");
			client = resteasyClientHandler.getRetEasyClient();
			fraugsterOnUpdateProviderRequest = fraugsterTransformer.transformOnUpdateRequest(request);
			ObjectUtils.setNullFieldsToDefault(fraugsterOnUpdateProviderRequest);
			fraugsterOnUpdateProviderRequest
					.setCustDOB(DateTimeFormatter.getDateInRFC3339(fraugsterOnUpdateProviderRequest.getCustDOB()));
			fraugsterOnUpdateProviderRequest.setCustSignupTs(
					DateTimeFormatter.getDateTimeInRFC3339(fraugsterOnUpdateProviderRequest.getCustSignupTs()));
			fraugsterOnUpdateProviderRequest.setDlExpiryDate(
					DateTimeFormatter.getDateInRFC3339(fraugsterOnUpdateProviderRequest.getDlExpiryDate()));
			fraugsterOnUpdateProviderRequest
					.setOsTs(DateTimeFormatter.getDateTimeInRFC3339(fraugsterOnUpdateProviderRequest.getOsTs()));
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					Constants.SESSION_TOKEN + " " + sessionToken.getSessionToken());
			response = clientrequest.post(Entity.entity(fraugsterOnUpdateProviderRequest, MediaType.APPLICATION_JSON));
			dataResponse = handleFraugsterServerTansactionResponse(response);
			fraudDetectionResponse = fraugsterTransformer.transformOnUpdateResponse(dataResponse);
		} catch (FraugsterException e) {
			throw e;
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_ONUPDATE, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;

	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService#doFraugsterPaymentsOutCheck(com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsOutContactRequest, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken)
	 */
	@Override
	public FraugsterPaymentsOutContactResponse doFraugsterPaymentsOutCheck(FraugsterPaymentsOutContactRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken) throws FraugsterException {
		Response response = null;
		ResteasyClient client = null;
		FraugsterPaymentsOutContactResponse fraudDetectionResponse = new FraugsterPaymentsOutContactResponse();
		FraugsterPaymentsOutProviderRequest fraugsterPaymentsOutProviderRequest = null;
		FraugsterSearchDataResponse dataResponse = new FraugsterSearchDataResponse();
		try {
			LOG.debug("FraugsterPort STRATED : doFraugsterPaymentsOutCheck() ");
			client = resteasyClientHandler.getRetEasyClient();
			fraugsterPaymentsOutProviderRequest = fraugsterTransformer.transformPaymentsOutRequest(request);
			ObjectUtils.setNullFieldsToDefault(fraugsterPaymentsOutProviderRequest);
			fraugsterPaymentsOutProviderRequest.setCustSignupTs(
					DateTimeFormatter.getDateTimeInRFC3339(fraugsterPaymentsOutProviderRequest.getCustSignupTs()));
			fraugsterPaymentsOutProviderRequest.setOpiCreatedDate(
					DateTimeFormatter.getDateInRFC3339(fraugsterPaymentsOutProviderRequest.getOpiCreatedDate()));
			fraugsterPaymentsOutProviderRequest
					.setOsTs(DateTimeFormatter.getDateTimeInRFC3339(fraugsterPaymentsOutProviderRequest.getOsTs()));
			fraugsterPaymentsOutProviderRequest.setTransTs(
					DateTimeFormatter.getDateTimeInRFC3339(fraugsterPaymentsOutProviderRequest.getTransTs()));
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					Constants.SESSION_TOKEN + " "  + sessionToken.getSessionToken());
			response = clientrequest
					.post(Entity.entity(fraugsterPaymentsOutProviderRequest, MediaType.APPLICATION_JSON));
			dataResponse = handleFraugsterServerTansactionResponse(response);
			fraudDetectionResponse = fraugsterTransformer.transformPaymentsOutResponse(dataResponse);
		} catch (FraugsterException e) {
			throw e;
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_PAYMENTOUT, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;

	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService#doFraugsterPaymentsInCheck(com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsInContactRequest, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty, com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken)
	 */
	@Override
	public FraugsterPaymentsInContactResponse doFraugsterPaymentsInCheck(FraugsterPaymentsInContactRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken) throws FraugsterException {
		Response response = null;
		ResteasyClient client = null;
		FraugsterPaymentsInContactResponse fraudDetectionResponse = new FraugsterPaymentsInContactResponse();
		FraugsterPaymentsInProviderRequest fraugsterPaymentsInProviderRequest = null;
		FraugsterSearchDataResponse dataResponse = new FraugsterSearchDataResponse();
		try {
			LOG.debug("FraugsterPort STRATED : doFraugsterPaymentsInCheck() ");
			client = resteasyClientHandler.getRetEasyClient();
			fraugsterPaymentsInProviderRequest = fraugsterTransformer.transformPaymentsInRequest(request);
			ObjectUtils.setNullFieldsToDefault(fraugsterPaymentsInProviderRequest);
			fraugsterPaymentsInProviderRequest.setCustSignupTs(
					DateTimeFormatter.getDateTimeInRFC3339(fraugsterPaymentsInProviderRequest.getCustSignupTs()));
			fraugsterPaymentsInProviderRequest.setDebitCardAddedDate(DateTimeFormatter
					.getDateTimeInRFC3339(fraugsterPaymentsInProviderRequest.getDebitCardAddedDate()));
			fraugsterPaymentsInProviderRequest.setChequeClearanceDate(DateTimeFormatter
					.getDateTimeInRFC3339(fraugsterPaymentsInProviderRequest.getChequeClearanceDate()));
			fraugsterPaymentsInProviderRequest
					.setOsTs(DateTimeFormatter.getDateTimeInRFC3339(fraugsterPaymentsInProviderRequest.getOsTs()));
			fraugsterPaymentsInProviderRequest.setTransTs(
					DateTimeFormatter.getDateTimeInRFC3339(fraugsterPaymentsInProviderRequest.getTransTs()));
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					Constants.SESSION_TOKEN  + " " + sessionToken.getSessionToken());
			response = clientrequest
					.post(Entity.entity(fraugsterPaymentsInProviderRequest, MediaType.APPLICATION_JSON));
			dataResponse = handleFraugsterServerTansactionResponse(response);
			fraudDetectionResponse = fraugsterTransformer.transformPaymentsInResponse(dataResponse);
		} catch (FraugsterException e) {
			throw e;
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_PAYMENTIN, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;

	}

}
