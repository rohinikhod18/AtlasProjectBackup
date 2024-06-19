/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.core.validators.Ivalidator;
import com.currenciesdirect.gtg.compliance.kyc.core.validators.ValidatorFactory;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.gbgroupport.GBGroupPort;
import com.currenciesdirect.gtg.compliance.kyc.lexisnexisport.CarbonServicePort;
import com.currenciesdirect.gtg.compliance.kyc.lexisnexisport.LexisNexisPort;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;

/**
 * * The Class KYCServiceImpl.
 * 
 * This is KYC service class. This service takes request coming from
 * compliance-service and gets kyc check details from kyc providers(GBGroup and
 * LexisNexis) against that request.
 * 
 * @author manish
 *
 */
public class KYCServiceImpl implements IKYCService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(KYCServiceImpl.class);

	/** The Constant ERROR_IN_KYC_SERVICE_CHECK_KYC_METHOD. */
	private static final String ERROR_IN_KYC_SERVICE_CHECK_KYC_METHOD = "error in KYCService : checkKYC method ";

	/** The kyc service. */
	private static IKYCService kycService = null;

	/** The validator factory. */
	private ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

	/** The concrete data builder. */
	private KYCConcreteDataBuilder concreteDataBuilder = KYCConcreteDataBuilder.getInstance();

	/**
	 * Instantiates a new KYC service impl.
	 */
	private KYCServiceImpl() {

	}

	/**
	 * Gets the single instance of KYCServiceImpl.
	 *
	 * @return single instance of KYCServiceImpl
	 */
	public static IKYCService getInstance() {
		if (kycService == null) {
			kycService = new KYCServiceImpl();
		}
		return kycService;
	}

	/**
	 * The Class ServiceImpl. Kyc service performs kyc check on only 17
	 * countries out of which for 7 countries we call gbgroup provider and for
	 * remaining lexisnexis provider. This mapping is saved in database and
	 * maintained in kyc cache Implementation steps : 1) validate the request 2)
	 * if request is valid create response with invalid request error for
	 * corresponding contact else go to step 3 3) get provider name depending on
	 * country name present in request from cache and get provider properties
	 * from provider cache(This cache is initialized at the deployment time.
	 * Cache contains kyc provider url,passwords and other information which are
	 * saved in database) 4) create lexis-nexis and gbgroup tasks(kyc service
	 * accepts multiple contacts in requests.Depending on country of contact
	 * these task are created) Here task executor pattern is used for
	 * multi-threading) 5) submit tasks to executors service.(There are
	 * executors 1)LexisNexisPort 2)GBGroupPort both are responsible for
	 * transforming request to provider acceptable format,call provider and
	 * transform response) 6) after execution of step 5, merge responses of both
	 * executors to kyc response object 7) return response to
	 * compliance-service.
	 *
	 * @param request
	 *            the request
	 * @return the KYC provider response
	 * @throws KYCException
	 *             the KYC exception
	 */

	@Override
	public KYCProviderResponse checkKYC(KYCProviderRequest request) throws KYCException {
		KYCProviderResponse response = new KYCProviderResponse();
		List<KYCContactRequest> contats = null;
		List<Callable<KYCContactResponse>> lst = new ArrayList<>();
		List<KYCContactResponse> contactResponses = new ArrayList<>();
		try {
			contats = request.getContact();
			for (KYCContactRequest contact : contats) { 
				if (Constants.ORG_CD_SA.equalsIgnoreCase(request.getOrgCode())
						|| Constants.ORG_E4F.equalsIgnoreCase(request.getOrgCode())
						|| LegalEntityEnum.TORSG.getLECode().equals(request.getLegalEntity())) { // AT-4157 - TORSG
					KYCContactResponse contactResponse = createNotRequiredResponse(contact);
					contactResponses.add(contactResponse);
				} //AT-3327 Stop e-IDV for EU Legal entity clients
				else if(LegalEntityEnum.CDLEU.getLECode().equals(request.getLegalEntity())
						|| LegalEntityEnum.FCGEU.getLECode().equals(request.getLegalEntity())
						|| LegalEntityEnum.TOREU.getLECode().equals(request.getLegalEntity())){
					KYCContactResponse contactResponse = createNotRequiredResponse(contact);
					contactResponse.setBandText(Constants.EU_LE_POI_NEEDED);
					contactResponses.add(contactResponse);
				}
			/*	else {
					createRequiredResponse(request, lst, contactResponses, contact);
				}
			}

			if (!lst.isEmpty()) {
				ExecutorService kycExecutors = KYCExecutors.getExecutorService();

				List<Future<KYCContactResponse>> tasks = kycExecutors.invokeAll(lst);

				for (Future<KYCContactResponse> task : tasks) {
					contactResponses.add(task.get());
				}
			}*/
			}
			KYCProviderResponse kycProviderResponse= new KYCProviderResponse();
			kycProviderResponse.setAccountId("0716E04000zKzriLBX");
			kycProviderResponse.setOrgCode("Currencies Direct");
			kycProviderResponse.setSourceApplication("Dione");
			List<KYCContactResponse> contactResponseList= new ArrayList<>();
			KYCContactResponse kyccontactResponse= new KYCContactResponse();
			kyccontactResponse.setBandText("NOT POI NEEDED");
			kyccontactResponse.setErrorCode("KYC000");
			kyccontactResponse.setErrorDescription("GBGroup Error: Organisation is active");
			kyccontactResponse.setContactSFId("002-C-0000052774");
			kyccontactResponse.setId(52774);
			kyccontactResponse.setOverallScore("0");
			kyccontactResponse.setProviderName("GBGroup");
			kyccontactResponse.setProviderMethod("authenticateSP");
			kyccontactResponse.setStatus("PASS");
			kycProviderResponse.setContactResponse(contactResponseList);
			contactResponseList.add(kyccontactResponse);
			
			
			//kycProviderResponse.setContactResponse(contactResponses);
			kycProviderResponse.setAccountId(request.getAccountSFId());
			kycProviderResponse.setOrgCode(request.getOrgCode());
			kycProviderResponse.setSourceApplication(request.getSourceApplication());
			
			 String jsonAccountSignupResponse = JsonConverterUtil.convertToJsonWithNull(kycProviderResponse);
			 LOG.warn("\n ------- KYC Response Start -------- \n  {}", jsonAccountSignupResponse);
		        LOG.warn(" \n -----------KYC Account Response End ---------");
			
		} catch (Exception e) {
			String logData = " Error in KYCService : checkKYC AuroraAccountID :";
			logData = logData.concat(request.getAccountSFId())
					.concat(" CorrelationID : ")
					.concat(request.getCorrelationID().toString())
					.concat("; ").concat(e.toString());
			LOG.error(logData);
			throw new KYCException(KYCErrors.KYC_GENERIC_EXCEPTION, e);
		}
		return response;
	}

	/**
	 * Creates the required response.
	 *
	 * @param request
	 *            the request
	 * @param lst
	 *            the lst
	 * @param contactResponses
	 *            the contact responses
	 * @param contact
	 *            the contact
	 */
	private void createRequiredResponse(KYCProviderRequest request, List<Callable<KYCContactResponse>> lst,
			List<KYCContactResponse> contactResponses, KYCContactRequest contact) {
		Ivalidator ivalidator;
		String providerName;
		String country;
		KYCProviderProperty providerProperty;
		country = contact.getAddress().getCountry().toUpperCase();
		try {
			ivalidator = validatorFactory.getValidator(country);
			FieldValidator fv = ivalidator.validateKYCRequest(contact);
			if (fv != null && !fv.isFailed()) {
				String logData = " Error in KYCService : checkKYC AuroraAccountID :";
				logData = logData.concat(request.getAccountSFId()).concat(" CorrelationID :" )
						//.concat(request.getCorrelationID().toString())
						.concat(" Mandatory parameters are missing in request");
				LOG.error(logData);
				KYCContactResponse contactResponse = new KYCContactResponse();
				contactResponse.setId(contact.getId());
				contactResponse.setContactSFId(contact.getContactSFId());

				contactResponse.setStatus(Constants.NOT_PERFORMED);
				contactResponse.setErrorCode("KYC0008");
				contactResponse
						.setErrorDescription("Mandatory fields are missing in request :" + fv.getErrors().toString());
				contactResponses.add(contactResponse);
			} else {

				providerName = concreteDataBuilder.getProviderForCountry(country);

				providerProperty = concreteDataBuilder.getProviderInitConfigProperty(providerName);
				
				if (Constants.LEXISNEXIS_PROVIDER.equalsIgnoreCase(providerName)) {
					lst.add(new LexisNexisPort(contact, providerProperty));
				} else if (Constants.GBGROUP_PROVIDER.equalsIgnoreCase(providerName)) {
					lst.add(new GBGroupPort(contact, providerProperty));
				} else if(Constants.CARBONSERVICE_PROVIDER.equalsIgnoreCase(providerName)){
					lst.add(new CarbonServicePort(contact, providerProperty));
				} else {
					throw new KYCException(KYCErrors.INVALID_PROVIDER);
				}

			}
		} catch (KYCException e) {
			KYCContactResponse contactResponse = new KYCContactResponse();
			contactResponse.setId(contact.getId());
			contactResponse.setErrorCode(e.getkycErrors().getErrorCode());
			contactResponse.setErrorDescription(e.getkycErrors().getErrorDescription());
			if (e.getkycErrors()==KYCErrors.COUNTRY_NOT_SUPPORTED) {
				contactResponse.setStatus(Constants.NOT_REQUIRED);
				LOG.debug(ERROR_IN_KYC_SERVICE_CHECK_KYC_METHOD, e);
			} else {
				contactResponse.setStatus(Constants.NOT_PERFORMED);
				LOG.error(ERROR_IN_KYC_SERVICE_CHECK_KYC_METHOD, e);
			}
			contactResponse.setOverallScore(Constants.NOT_AVAILABLE);
			/**
			 * Set ContactSFId if status is NOT_REQUIRED or NOT_PERFORMED
			 * -Saylee
			 */
			contactResponse.setContactSFId(contact.getContactSFId());
			contactResponses.add(contactResponse);
		} catch (Exception e) {
			LOG.error(ERROR_IN_KYC_SERVICE_CHECK_KYC_METHOD, e);
			KYCContactResponse contactResponse = new KYCContactResponse();
			contactResponse.setId(contact.getId());
			contactResponse.setErrorCode("0999");
			contactResponse.setErrorDescription("Generic exception");
			contactResponse.setStatus(Constants.SERVICE_FAILURE);
			/**
			 * Set ContactSFId if status is SERVICE_FAILURE -Saylee
			 */
			contactResponse.setContactSFId(contact.getContactSFId());
			contactResponses.add(contactResponse);
		}
	}

	/**
	 * Creates the not required response.
	 *
	 * @param contact
	 *            the contact
	 * @return the KYC contact response
	 */
	private KYCContactResponse createNotRequiredResponse(KYCContactRequest contact) {
		KYCContactResponse contactResponse = new KYCContactResponse();
		contactResponse.setId(contact.getId());
		contactResponse.setContactSFId(contact.getContactSFId());
		contactResponse.setStatus(Constants.NOT_REQUIRED);
		contactResponse.setOverallScore(Constants.NOT_AVAILABLE);
		return contactResponse;
	}

	
}
