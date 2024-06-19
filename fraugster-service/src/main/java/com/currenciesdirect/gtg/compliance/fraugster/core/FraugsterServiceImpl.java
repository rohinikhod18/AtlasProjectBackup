/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken;
import com.currenciesdirect.gtg.compliance.fraugster.core.sessionhandler.FraugsterSessionHandler;
import com.currenciesdirect.gtg.compliance.fraugster.core.validator.FraugsterValidator;
import com.currenciesdirect.gtg.compliance.fraugster.core.validator.IValidator;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.fraugsterport.FraudPredictPort;
import com.currenciesdirect.gtg.compliance.fraugster.fraugsterport.FraugsterPort;
import com.currenciesdirect.gtg.compliance.fraugster.util.Constants;

/**
 * The Class FraugsterServiceImpl.
 *
 * @author manish
 */
public class FraugsterServiceImpl implements IFraugsterService {

	private static final String NOT_REQUIRED = "NOT_REQUIRED";

    /** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FraugsterServiceImpl.class);

	/** The i fraugster service. */
	@SuppressWarnings("squid:S3077")
	private static volatile IFraugsterService iFraugsterService = null;

	/** The i fraugster provider service. */
	private IFraugsterProviderService iFraugsterProviderService = FraugsterPort.getInstance();
	
	/** The i fraugster provider service. */
	private IFraudPredictProviderService iFraudPredictProviderService = FraudPredictPort.getInstance();

	/** The concrete data builder. */
	private FraugsterConcreteDataBuilder concreteDataBuilder = FraugsterConcreteDataBuilder.getInstance();

	/** The ivalidator. */
	private IValidator ivalidator = FraugsterValidator.getInstance();

	/** The fraugster session handler. */
	private FraugsterSessionHandler fraugsterSessionHandler = FraugsterSessionHandler.getInstance();
	
	/** The Constant FRAUGSTER . */
	private static final String FRAUGSTER = "Fraugster";
	
	/** The Constant FRAUGSTER_SERVICE_PROVIDER . */
	private static final String FRAUGSTER_SERVICE_PROVIDER = "fraugster.service.provider";
	
	

	/**
	 * Instantiates a new fraugster service impl.
	 */
	private FraugsterServiceImpl() {
	}

	/**
	 * Gets the single instance of FraugsterServiceImpl.
	 *
	 * @return single instance of FraugsterServiceImpl
	 */
	public static IFraugsterService getInstance() {
		if (iFraugsterService == null) {
			synchronized (FraugsterServiceImpl.class) {
				if (iFraugsterService == null) {
					iFraugsterService = new FraugsterServiceImpl();
				}
			}
		}
		return iFraugsterService;

	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterService#doFraugsterCheckForNewSignUp(FraugsterSignupRequest)
	 */
	@Override
	public FraugsterSignupResponse doFraugsterCheckForNewSignUp(FraugsterSignupRequest request)
      throws FraugsterException {

    List<FraugsterSignupContactRequest> contacts = null;
    List<FraugsterSignupContactResponse> contactResponsesNewSignup = new ArrayList<>();
    FraugsterSignupResponse responseNewSignup = new FraugsterSignupResponse();
    FraugsterProviderProperty fraugsterProviderProperty = null;
    FraugsterSessionToken sessionToken = null;
    double score = 0.00;
    try {

      if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
        fraugsterProviderProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.TRANSACTION);
        score = fraugsterProviderProperty.getRegistrationThreesholdScore();// added Reg_threesholdScore -Saylee
        sessionToken = fraugsterSessionHandler.getSessionToken();
      } else {
        fraugsterProviderProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.FRAUD_PREDICT_SIGNUP);
        score = fraugsterProviderProperty.getRegistrationThreesholdScore();
      }

      contacts = request.getContactRequests();

      for (FraugsterSignupContactRequest contact : contacts) {
        FraugsterSignupContactResponse fraudDetectionResponse;
        fraudDetectionResponse =
            doSignUpCheckForContact(request, fraugsterProviderProperty, sessionToken, score, contact);
        contactResponsesNewSignup.add(fraudDetectionResponse);
      }

      responseNewSignup.setContactResponses(contactResponsesNewSignup);
      responseNewSignup.setOrgCode(request.getOrgCode());
      responseNewSignup.setSourceApplication(request.getSourceApplication());
      responseNewSignup.setStatus(Constants.PASS);


    } catch (FraugsterException e) {
      LOG.error("Error:", e);
      responseNewSignup.setStatus(Constants.SERVICE_FAILURE);
    } catch (Exception e) {
      LOG.error("Error in doFraugsterCheckForNewRegistration() : ", e);
      throw new FraugsterException(FraugsterErrors.FAILED);
    }
    return responseNewSignup;
  }

	/**
	 * Do sign up check for contact.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster signup contact response
	 */
	private FraugsterSignupContactResponse doSignUpCheckForContact(FraugsterSignupRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, double score,
			FraugsterSignupContactRequest contact) {
		
		FraugsterSessionToken reLoginSessionToken = null;
		
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		try {			
			fraudDetectionResponse = validateAndCallFraugsterSignUp(request, fraugsterProviderProperty, sessionToken,
					score, contact);
			
		} catch (FraugsterException e) {
			if(e.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.UNAUTHORIZED_EXCEPTION.getErrorCode())) {
				try {
					reLoginSessionToken = fraugsterSessionHandler.repeatLogin();
					fraudDetectionResponse = validateAndCallFraugsterSignUp(request, fraugsterProviderProperty,
							reLoginSessionToken, score, contact);					
				} catch(FraugsterException ex){
					handleFraugsterExceptionForSignup(contact, fraudDetectionResponse, ex);
				}				
			}else {
				handleFraugsterExceptionForSignup(contact, fraudDetectionResponse, e);
			}
		} catch (Exception e) {
			logError(e);
			fraudDetectionResponse.setId(contact.getId());
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
			fraudDetectionResponse.setId(contact.getId());
			fraudDetectionResponse.setErrorCode("0999");
			fraudDetectionResponse.setErrorDescription(FraugsterErrors.FAILED.getErrorDescription());
		}
		return fraudDetectionResponse;
	}

	/**
	 * Validate and call fraugster sign up.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster signup contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterSignupContactResponse validateAndCallFraugsterSignUp(FraugsterSignupRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, double score,
			FraugsterSignupContactRequest contact) throws FraugsterException {
		FraugsterSignupContactResponse fraudDetectionResponse;
		contact.setOrganizationCode(request.getOrgCode());
		fraudDetectionResponse = validateAndCheckFraugsterSignup(fraugsterProviderProperty, sessionToken,
				contact);		
		generateFraugsterResponseStatus(score, fraudDetectionResponse);
		fraudDetectionResponse.setId(contact.getId());
		return fraudDetectionResponse;
	}

	/**
	 * Handle fraugster exception for signup.
	 *
	 * @param contact the contact
	 * @param fraudDetectionResponse the fraud detection response
	 * @param ex the ex
	 */
	private void handleFraugsterExceptionForSignup(FraugsterSignupContactRequest contact,
			FraugsterSignupContactResponse fraudDetectionResponse, FraugsterException ex) {
		logDebug(ex);
		fraudDetectionResponse.setId(contact.getId());
		if(ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.INVALID_REQUEST.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode())){
			fraudDetectionResponse.setStatus(Constants.NOT_PERFORMED);
		}else{
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
		}
		fraudDetectionResponse.setId(contact.getId());
		fraudDetectionResponse.setErrorCode(ex.getFraugsterErrors().getErrorCode());
		fraudDetectionResponse.setErrorDescription(ex.getFraugsterErrors().getErrorDescription());
	}

	/**
	 * Validate and check fraugster signup.
	 *
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param contact the contact
	 * @return the fraugster signup contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterSignupContactResponse validateAndCheckFraugsterSignup(
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken,
			FraugsterSignupContactRequest contact)
			throws FraugsterException {
		FraugsterSignupContactResponse fraudDetectionResponse;
		Boolean isValidate;
		isValidate = ivalidator.validateFraugsterSignupRequest(contact);
		if (Boolean.FALSE.equals(isValidate)) {
			LOG.error("Request is not valid");
			throw new FraugsterException(FraugsterErrors.INVALID_REQUEST);
		} else if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
			fraudDetectionResponse = iFraugsterProviderService.doFraugsterSignupCheck(contact,
					fraugsterProviderProperty, sessionToken);
		}else {
			fraudDetectionResponse = iFraudPredictProviderService.doFraudPredictSignupCheck(contact,
					fraugsterProviderProperty);
		}
		return fraudDetectionResponse;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterService#doFraugsterCheckForOnUpdate(FraugsterOnUpdateRequest)
	 */
	@Override
	public FraugsterOnUpdateResponse doFraugsterCheckForOnUpdate(FraugsterOnUpdateRequest request)
			throws FraugsterException {

		List<FraugsterOnUpdateContactRequest> contacts = null;
		List<FraugsterOnUpdateContactResponse> contactResponsesUpdate = new ArrayList<>();
		FraugsterOnUpdateResponse responseUpdate = new FraugsterOnUpdateResponse();
		FraugsterProviderProperty fraugsterProviderProperty = null;
		FraugsterSessionToken sessionToken = null;
		double score = 0.00;
		try {
			
			if(FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
				fraugsterProviderProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.TRANSACTION);
				score = fraugsterProviderProperty.getRegistrationThreesholdScore();
				sessionToken = fraugsterSessionHandler.getSessionToken();
			}
		   else {
				fraugsterProviderProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.FRAUD_PREDICT_SIGNUP);
				score = fraugsterProviderProperty.getRegistrationThreesholdScore();
			}
			
			contacts = request.getContactRequests();

			for (FraugsterOnUpdateContactRequest contact : contacts) {
				FraugsterOnUpdateContactResponse fraudDetectionResponse;
				fraudDetectionResponse = doOnUpdateCheckForContact(request, fraugsterProviderProperty, sessionToken,
						score, contact);
				contactResponsesUpdate.add(fraudDetectionResponse);
			}

			responseUpdate.setContactResponses(contactResponsesUpdate);
			responseUpdate.setOrgCode(request.getOrgCode());
			responseUpdate.setSourceApplication(request.getSourceApplication());
			responseUpdate.setStatus(Constants.PASS);

		} catch (FraugsterException e) {
			LOG.error("Error:",e);
			responseUpdate.setStatus(Constants.SERVICE_FAILURE);
		} catch (Exception e) {
			LOG.error("Error in doFraugsterCheckForOnUpdate() : ", e);
			throw new FraugsterException(FraugsterErrors.FAILED);
		}
		return responseUpdate;
	}

	/**
	 * Do on update check for contact.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster on update contact response
	 */
	private FraugsterOnUpdateContactResponse doOnUpdateCheckForContact(FraugsterOnUpdateRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, final double score,
			FraugsterOnUpdateContactRequest contact) {
		FraugsterOnUpdateContactResponse fraudDetectionResponse = new FraugsterOnUpdateContactResponse();
		
		FraugsterSessionToken reLoginSessionToken = null;
		try {
			fraudDetectionResponse = validateAndCallFraugsterUpdate(request, fraugsterProviderProperty, sessionToken,
					score, contact);
			
		} catch (FraugsterException e) {
			if(e.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.UNAUTHORIZED_EXCEPTION.getErrorCode())) {
				try {
					reLoginSessionToken = fraugsterSessionHandler.repeatLogin();
					fraudDetectionResponse = validateAndCallFraugsterUpdate(request, fraugsterProviderProperty,
							reLoginSessionToken, score, contact);
					
				}catch(FraugsterException ex){
					handleFraugsterExceptionForUpdate(contact, fraudDetectionResponse, ex);
				}
			}
			else{
				handleFraugsterExceptionForUpdate(contact, fraudDetectionResponse, e);
			}
		} catch (Exception e) {
			logError(e);
			fraudDetectionResponse.setId(contact.getId());
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
			fraudDetectionResponse.setId(contact.getId());
			fraudDetectionResponse.setErrorCode("0999");
			fraudDetectionResponse.setErrorDescription(Constants.GENERIC_EXCEPTION);
		}
		return fraudDetectionResponse;
	}

	/**
	 * Validate and call fraugster update.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster on update contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterOnUpdateContactResponse validateAndCallFraugsterUpdate(FraugsterOnUpdateRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, final double score,
			FraugsterOnUpdateContactRequest contact) throws FraugsterException {
		FraugsterOnUpdateContactResponse fraudDetectionResponse;
		contact.setOrganizationCode(request.getOrgCode());
		

		fraudDetectionResponse = validateAndCheckFraugsterUpdate(fraugsterProviderProperty, sessionToken,
				contact);
		generateFraugsterResponseStatus(score, fraudDetectionResponse);
		fraudDetectionResponse.setId(contact.getId());
		return fraudDetectionResponse;
	}

	/**
	 * Handle fraugster exception for update.
	 *
	 * @param contact the contact
	 * @param fraudDetectionResponse the fraud detection response
	 * @param ex the ex
	 */
	private void handleFraugsterExceptionForUpdate(FraugsterOnUpdateContactRequest contact,
			FraugsterOnUpdateContactResponse fraudDetectionResponse, FraugsterException ex) {
		logDebug(ex);
		fraudDetectionResponse.setId(contact.getId());
		if(ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.INVALID_REQUEST.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode())){
			fraudDetectionResponse.setStatus(Constants.NOT_PERFORMED);
		}else{
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
		}
		fraudDetectionResponse.setErrorCode(ex.getFraugsterErrors().getErrorCode());
		fraudDetectionResponse.setErrorDescription(ex.getFraugsterErrors().getErrorDescription());
	}

	

	/**
	 * Validate and check fraugster update.
	 *
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param contact the contact
	 * @return the fraugster on update contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterOnUpdateContactResponse validateAndCheckFraugsterUpdate(
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken,
			FraugsterOnUpdateContactRequest contact)
			throws FraugsterException {
		Boolean isValidate;
		FraugsterOnUpdateContactResponse fraudDetectionResponse;
		isValidate = ivalidator.validateFraugsterOnUpdateRequest(contact);
		if (Boolean.FALSE.equals(isValidate)) {
			LOG.error("Request is not valid");
			throw new FraugsterException(FraugsterErrors.INVALID_REQUEST);
			
		} else if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))){
			 fraudDetectionResponse = iFraugsterProviderService.doFraugsterOnUpdateCheck(contact,
					fraugsterProviderProperty, sessionToken);
		}else {
			fraudDetectionResponse = iFraudPredictProviderService.doFraudPredictOnUpdateCheck(contact,
					fraugsterProviderProperty);
		}
		return fraudDetectionResponse;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterService#doFraugsterCheckForPaymentsOut(FraugsterPaymentsOutRequest)
	 */
	@Override
	public FraugsterPaymentsOutResponse doFraugsterCheckForPaymentsOut(FraugsterPaymentsOutRequest request)
			throws FraugsterException {


		List<FraugsterPaymentsOutContactRequest> contacts = null;
		List<FraugsterPaymentsOutContactResponse> contactResponsesPaymentsOut = new ArrayList<>();
		FraugsterPaymentsOutResponse responsePaymentsOut = new FraugsterPaymentsOutResponse();
		FraugsterProviderProperty fraugsterProviderProperty = null;
		FraugsterSessionToken sessionToken = null;
		double score = 0.00;
		try {
			
      if (System.getProperty("custType.toPerform.fraugster").contains(request.getCustType())) {

        if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
          fraugsterProviderProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.TRANSACTION);
          score = fraugsterProviderProperty.getPaymentOutThreesholdScore();
          sessionToken = fraugsterSessionHandler.getSessionToken();
        } else {
          fraugsterProviderProperty =
              concreteDataBuilder.getProviderInitConfigProperty(Constants.FRAUD_PREDICT_FUNDSOUT);
          score = fraugsterProviderProperty.getPaymentOutThreesholdScore();
        }

        contacts = request.getContactRequests();
        for (FraugsterPaymentsOutContactRequest contact : contacts) {
          FraugsterPaymentsOutContactResponse fraudDetectionResponse;
          fraudDetectionResponse =
              doPayementsOutCheckForContact(request, fraugsterProviderProperty, sessionToken, score, contact);
          contactResponsesPaymentsOut.add(fraudDetectionResponse);
        }

        responsePaymentsOut.setContactResponses(contactResponsesPaymentsOut);
        responsePaymentsOut.setOrgCode(request.getOrgCode());
        responsePaymentsOut.setSourceApplication(request.getSourceApplication());
        responsePaymentsOut.setStatus("Success");
      }
			
	 else {
			contacts = request.getContactRequests();
		 for (FraugsterPaymentsOutContactRequest contact : contacts) {
				FraugsterPaymentsOutContactResponse fraudDetectionResponse=new FraugsterPaymentsOutContactResponse();
				fraudDetectionResponse.setFraugsterApproved(Constants.NOT_REQUIRED); 
				fraudDetectionResponse.setFraugsterId(Constants.NOT_REQUIRED);
				fraudDetectionResponse.setFrgTransId(Constants.NOT_REQUIRED);
				fraudDetectionResponse.setProviderResponse(Constants.NOT_REQUIRED);
				fraudDetectionResponse.setScore(Constants.NOT_REQUIRED);
				fraudDetectionResponse.setStatus(NOT_REQUIRED);
				fraudDetectionResponse.setId(contact.getId());
				contactResponsesPaymentsOut.add(fraudDetectionResponse);
			}
		    responsePaymentsOut.setStatus(NOT_REQUIRED);
			responsePaymentsOut.setContactResponses(contactResponsesPaymentsOut);
		}
			
		} catch (FraugsterException e) {
			LOG.error("Error in doFraugsterCheckForPaymentsOut() : ", e);
			responsePaymentsOut.setStatus(Constants.SERVICE_FAILURE);
		} catch (Exception e) {
			LOG.error("Error in doFraugsterCheckFPaymentsOut() : ", e);
			throw new FraugsterException(FraugsterErrors.FAILED);
		}
		return responsePaymentsOut;
	
	}

	/**
	 * Do payements out check for contact.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster payments out contact response
	 */
	private FraugsterPaymentsOutContactResponse doPayementsOutCheckForContact(FraugsterPaymentsOutRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, final double score,
			FraugsterPaymentsOutContactRequest contact) {
		FraugsterPaymentsOutContactResponse fraudDetectionResponse = new FraugsterPaymentsOutContactResponse();
		
		FraugsterSessionToken reLoginSessionToken = null;
		try {
			fraudDetectionResponse = validateAndCallFraugsterPaymentOut(request, fraugsterProviderProperty,
					sessionToken, score, contact);
			
		} catch (FraugsterException e) {
			if(e.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.UNAUTHORIZED_EXCEPTION.getErrorCode())) {
				try {
					reLoginSessionToken = fraugsterSessionHandler.repeatLogin();
					fraudDetectionResponse = validateAndCallFraugsterPaymentOut(request, fraugsterProviderProperty,
							reLoginSessionToken, score, contact);
					
				} catch (FraugsterException ex) {
					handleFraugsterExceptionForPaymentOut(contact, fraudDetectionResponse, ex);
				}
			}else {
				handleFraugsterExceptionForPaymentOut(contact, fraudDetectionResponse, e);
			}
		} catch (Exception e) {
			logError(e);
			fraudDetectionResponse.setId(contact.getId());
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
			fraudDetectionResponse.setErrorCode("0999");
			fraudDetectionResponse.setErrorDescription(Constants.GENERIC_EXCEPTION);
		}
		return fraudDetectionResponse;
	}

	/**
	 * Validate and call fraugster payment out.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster payments out contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterPaymentsOutContactResponse validateAndCallFraugsterPaymentOut(FraugsterPaymentsOutRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, final double score,
			FraugsterPaymentsOutContactRequest contact) throws FraugsterException {
		FraugsterPaymentsOutContactResponse fraudDetectionResponse;
		contact.setOrganizationCode(request.getOrgCode());
		fraudDetectionResponse = validateAndCheckFraugsterPaymentsOut(request, fraugsterProviderProperty,
				sessionToken, contact);
        generateFraugsterResponseStatus(score, fraudDetectionResponse);
		fraudDetectionResponse.setId(contact.getId());
		return fraudDetectionResponse;
	}

	/**
	 * Handle fraugster exception for payment out.
	 *
	 * @param contact the contact
	 * @param fraudDetectionResponse the fraud detection response
	 * @param ex the ex
	 */
	private void handleFraugsterExceptionForPaymentOut(FraugsterPaymentsOutContactRequest contact,
			FraugsterPaymentsOutContactResponse fraudDetectionResponse, FraugsterException ex) {
		logDebug(ex);
		if(ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.INVALID_REQUEST.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode())){
			fraudDetectionResponse.setStatus(Constants.NOT_PERFORMED);
		}else{
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
		}
		fraudDetectionResponse.setId(contact.getId());
		fraudDetectionResponse.setErrorCode(ex.getFraugsterErrors().getErrorCode());
		fraudDetectionResponse.setErrorDescription(ex.getFraugsterErrors().getErrorDescription());
	}

	

	/**
	 * Validate and check fraugster payments out.
	 *
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param contact the contact
	 * @return the fraugster payments out contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterPaymentsOutContactResponse validateAndCheckFraugsterPaymentsOut(FraugsterPaymentsOutRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken,
			FraugsterPaymentsOutContactRequest contact)
          throws FraugsterException {
        FraugsterPaymentsOutContactResponse fraudDetectionResponse;
        Boolean isValidate;
        isValidate = ivalidator.validateFraugsterPaymentsOutRequest(contact);
        if (Boolean.FALSE.equals(isValidate)) {
          throw new FraugsterException(FraugsterErrors.INVALID_REQUEST);
    
        } else if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
          fraudDetectionResponse =
              iFraugsterProviderService.doFraugsterPaymentsOutCheck(contact, fraugsterProviderProperty, sessionToken);
        } else {
          fraudDetectionResponse =
              iFraudPredictProviderService.doFraudPredictPaymentsOutCheck(request, contact, fraugsterProviderProperty);
        }
        return fraudDetectionResponse;
      }
	
	/**
	 * Log error.
	 *
	 * @param exception the exception
	 */
	private void logError(Throwable exception) {
		LOG.error("Error in class FraugsterServiceImpl :", exception);
	}
	
	/**
	 * Log debug.
	 *
	 * @param exception the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class FraugsterServiceImpl :", exception);
	}
	
	/**
	 * Validate and check fraugster payments in.
	 *
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param contact the contact
	 * @return the fraugster payments in contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterPaymentsInContactResponse validateAndCheckFraugsterPaymentsIn(FraugsterPaymentsInRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken,
			FraugsterPaymentsInContactRequest contact)
			throws FraugsterException {
		 FraugsterPaymentsInContactResponse fraudDetectionResponse;
		 
		 if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))){
			 fraudDetectionResponse = iFraugsterProviderService.doFraugsterPaymentsInCheck(contact,
					fraugsterProviderProperty, sessionToken);
		 }else {
			 fraudDetectionResponse = iFraudPredictProviderService.doFraudPredictPaymentsInCheck(request, contact,
						fraugsterProviderProperty);
	     }
		return fraudDetectionResponse;
	}
	
	/**
	 * Do payements in check for contact.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster payments in contact response
	 */
	private FraugsterPaymentsInContactResponse doPayementsInCheckForContact(FraugsterPaymentsInRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, final double score,
			FraugsterPaymentsInContactRequest contact) {
		FraugsterPaymentsInContactResponse fraudDetectionResponse = new FraugsterPaymentsInContactResponse();
		
		FraugsterSessionToken reLoginSessionToken = null;
		try {
			fraudDetectionResponse = validateAndCallFraugsterPaymentIn(request, fraugsterProviderProperty, sessionToken,
					score, contact);
			
		} catch (FraugsterException e) {
			if(e.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.UNAUTHORIZED_EXCEPTION.getErrorCode())) {
				try {
					reLoginSessionToken = fraugsterSessionHandler.repeatLogin();
					fraudDetectionResponse = validateAndCallFraugsterPaymentIn(request, fraugsterProviderProperty,
							reLoginSessionToken, score, contact);
					
				} catch (FraugsterException ex) {
					handleFraugsterExceptionForPaymentIn(contact, fraudDetectionResponse, ex);
				}
				
			}else {
				handleFraugsterExceptionForPaymentIn(contact, fraudDetectionResponse, e);
			}
		} catch (Exception e) {
			logError(e);
			fraudDetectionResponse.setId(contact.getId());
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
			fraudDetectionResponse.setErrorCode("0999");
			fraudDetectionResponse.setErrorDescription(Constants.GENERIC_EXCEPTION);
		}
		return fraudDetectionResponse;
	}

	/**
	 * Validate and call fraugster payment in.
	 *
	 * @param request the request
	 * @param fraugsterProviderProperty the fraugster provider property
	 * @param sessionToken the session token
	 * @param score the score
	 * @param contact the contact
	 * @return the fraugster payments in contact response
	 * @throws FraugsterException the fraugster exception
	 */
	private FraugsterPaymentsInContactResponse validateAndCallFraugsterPaymentIn(FraugsterPaymentsInRequest request,
			FraugsterProviderProperty fraugsterProviderProperty, FraugsterSessionToken sessionToken, final double score,
			FraugsterPaymentsInContactRequest contact) throws FraugsterException {
		FraugsterPaymentsInContactResponse fraudDetectionResponse;
		contact.setOrganizationCode(request.getOrgCode());
		fraudDetectionResponse = validateAndCheckFraugsterPaymentsIn(request,fraugsterProviderProperty,
				sessionToken, contact);
		generateFraugsterResponseStatus(score, fraudDetectionResponse);
		
		fraudDetectionResponse.setId(contact.getId());
		return fraudDetectionResponse;
	}

	/**
	 * Handle fraugster exception for payment in.
	 *
	 * @param contact the contact
	 * @param fraudDetectionResponse the fraud detection response
	 * @param ex the ex
	 */
	private void handleFraugsterExceptionForPaymentIn(FraugsterPaymentsInContactRequest contact,
			FraugsterPaymentsInContactResponse fraudDetectionResponse, FraugsterException ex) {
		logDebug(ex);
		if(ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.INVALID_REQUEST.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION.getErrorCode())
				|| ex.getFraugsterErrors().getErrorCode().equals(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION.getErrorCode())){
			fraudDetectionResponse.setStatus(Constants.NOT_PERFORMED);
		}else{
			fraudDetectionResponse.setStatus(Constants.SERVICE_FAILURE);
		}
		fraudDetectionResponse.setId(contact.getId());
		fraudDetectionResponse.setErrorCode(ex.getFraugsterErrors().getErrorCode());
		fraudDetectionResponse.setErrorDescription(ex.getFraugsterErrors().getErrorDescription());
	}
	
	/**
	 * Generate fraugster response status.
	 *
	 * @param score the score
	 * @param fraudDetectionResponse the fraud detection response
	 */
	private void generateFraugsterResponseStatus(final double score,
			FraugsterBaseResponse fraudDetectionResponse) {
		
		if (Double.parseDouble(fraudDetectionResponse.getFraugsterApproved()) == 1.0) {

			fraudDetectionResponse.setStatus(Constants.PASS);

		} else if (Double.parseDouble(fraudDetectionResponse.getFraugsterApproved()) == 0.0) {

			fraudDetectionResponse.setStatus(Constants.WATCH_LIST);
		} else {
                     
			fraudDetectionResponse.setStatus(Constants.FAIL);
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterService#doFraugsterCheckForPaymentsIn(FraugsterPaymentsInRequest)
	 */
	@Override
	public FraugsterPaymentsInResponse doFraugsterCheckForPaymentsIn(FraugsterPaymentsInRequest request)
			throws FraugsterException {

		List<FraugsterPaymentsInContactRequest> contacts = null;
		List<FraugsterPaymentsInContactResponse> contactResponsesPaymentsIn = new ArrayList<>();
		FraugsterPaymentsInResponse responsePaymentsIn = new FraugsterPaymentsInResponse();
		FraugsterProviderProperty fraugsterProviderProperty = null;
		FraugsterSessionToken sessionToken = null;
		double score = 0.00;
		try {

			if (System.getProperty("custType.toPerform.fraugster").contains(request.getCustType())) {

				if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
					fraugsterProviderProperty = concreteDataBuilder
							.getProviderInitConfigProperty(Constants.TRANSACTION);
					sessionToken = fraugsterSessionHandler.getSessionToken();
					score = fraugsterProviderProperty.getPaymentInThreesholdScore();
				}else {

					fraugsterProviderProperty = concreteDataBuilder
							.getProviderInitConfigProperty(Constants.FRAUD_PREDICT_FUNDSIN);
					score = fraugsterProviderProperty.getPaymentInThreesholdScore();
					
				}

				contacts = request.getContactRequests();
				for (FraugsterPaymentsInContactRequest contact : contacts) {
					FraugsterPaymentsInContactResponse fraudDetectionResponse;
					fraudDetectionResponse = doPayementsInCheckForContact(request, fraugsterProviderProperty,
							sessionToken, score, contact);
					contactResponsesPaymentsIn.add(fraudDetectionResponse);
				}

				responsePaymentsIn.setContactResponses(contactResponsesPaymentsIn);
				responsePaymentsIn.setOrgCode(request.getOrgCode());
				responsePaymentsIn.setSourceApplication(request.getSourceApplication());
				responsePaymentsIn.setStatus("Success");
				
			} else {

				contacts = request.getContactRequests();
				for (FraugsterPaymentsInContactRequest contact : contacts) {
					FraugsterPaymentsInContactResponse fraudDetectionResponse = new FraugsterPaymentsInContactResponse();
					fraudDetectionResponse.setFraugsterApproved(Constants.NOT_REQUIRED);
					fraudDetectionResponse.setFraugsterId(Constants.NOT_REQUIRED);
					fraudDetectionResponse.setFrgTransId(Constants.NOT_REQUIRED);
					fraudDetectionResponse.setProviderResponse(Constants.NOT_REQUIRED);
					fraudDetectionResponse.setScore(Constants.NOT_REQUIRED);
					fraudDetectionResponse.setStatus(NOT_REQUIRED);
					fraudDetectionResponse.setId(contact.getId());
					contactResponsesPaymentsIn.add(fraudDetectionResponse);
				}

				responsePaymentsIn.setStatus(NOT_REQUIRED);
				responsePaymentsIn.setContactResponses(contactResponsesPaymentsIn);
			}

		} catch (FraugsterException e) {
			LOG.error("Error in doFraugsterCheckForPaymentsOut() : ", e);
			responsePaymentsIn.setStatus(Constants.SERVICE_FAILURE);
		} catch (Exception e) {
			LOG.error("Error in doFraugsterCheckFPaymentsOut() : ", e);
			throw new FraugsterException(FraugsterErrors.FAILED);
		}
		return responsePaymentsIn;

	}

}
