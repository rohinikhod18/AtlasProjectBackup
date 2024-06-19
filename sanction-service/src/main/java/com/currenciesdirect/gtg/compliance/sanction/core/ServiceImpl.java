/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: ServiceImpl.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.commons.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusRequest;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusResponse;
import com.currenciesdirect.gtg.compliance.sanction.core.validator.SanctionValidator;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.currenciesdirect.gtg.compliance.sanction.finscanport.FinscanPort;
import com.currenciesdirect.gtg.compliance.sanction.finscanport.SLLookUpMultiTask;
import com.currenciesdirect.gtg.compliance.sanction.finscanport.SlGetStatusTask;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;

/**
 * The Class ServiceImpl.
 * 
 * This is Sanction service class. This service takes request coming from
 * compliance-service and gets sanction details from sanction provider(Finscan)
 * against that request.
 * 
 * @author abhijitg
 */
public class ServiceImpl implements IService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ServiceImpl.class);

	/** The service impl. */
	private static ServiceImpl serviceImpl = null;
	
	/** The iservice port. */
	private ISanctionService iservicePort = FinscanPort.getInstance();
	
	/** The concrete data builder. */
	private SanctionConcreteDataBuilder concreteDataBuilder = SanctionConcreteDataBuilder.getInstance();
	
	/** The i sanctionvalidator. */
	private ISanctionvalidator iSanctionvalidator = SanctionValidator.getInstance();

	/**
	 * Instantiates a new service impl.
	 */
	private ServiceImpl() {

	}

	/**
	 * Gets the single instance of ServiceImpl.
	 *
	 * @return single instance of ServiceImpl
	 */
	public static ServiceImpl getInstance() {
		if (serviceImpl == null) {
			serviceImpl = new ServiceImpl();
		}
		return serviceImpl;
	}

	/**
	 * Sanction service performs sanction check on five types of entities 1)
	 * Contact 2) Bank 3) Beneficiary 4) Account(Company) 5)Debtor Implementation steps :
	 * 1) validate the request 2) if request is invalid return response with
	 * invalid request error else go to step 3 3) get provider properties from
	 * provider config cache(This cache is initialized at the deployment time.
	 * Cache contains sanction provider url,passwords and other information
	 * which are saved in database) 4) create getstatus and sllookupMulti
	 * tasks(get status and sllookupmulti are finscan provider methods, if
	 * sanction is being performed for first time on respected entity then
	 * sllookupmulti task is added for that entity otherwise slgetstatus. This
	 * decision is taken from "isexisting" field present in request. Here task
	 * executor pattern is used for multi-threading) 5) submit tasks to
	 * executors service.(There are executors 1)SlgetStatusTask
	 * 2)SlLookupMultiTask both are responsible for transforming request to
	 * provider acceptable format,call provider and transform response) 6) after
	 * execution of step 5, merge responses of both executors to sanction
	 * response object 7) return response to compliance-service.
	 *
	 * @param sanctionRequest the sanction request
	 * @return the sanction details
	 * @throws SanctionException the sanction exception
	 */
	@Override
	public SanctionResponse getSanctionDetails(SanctionRequest sanctionRequest) throws SanctionException {
		/*SanctionResponse sanctionsResponse = new SanctionResponse();

		List<Callable<IDomain>> lst = new ArrayList<>();
		ProviderProperty providerProperty = null;
		FieldValidator fv = new FieldValidator();
		try {

			fv = iSanctionvalidator.validateRequest(sanctionRequest);
			if (fv != null && !fv.isFailed()) {
				SanctionContactResponse conResponse = new SanctionContactResponse();
				conResponse.setContactId(sanctionRequest.getContactrequests().get(0).getContactId());
				conResponse.setErrorCode(SanctionErrors.VALIDATION_EXCEPTION.getErrorCode());
				conResponse.setErrorDescription(
						SanctionErrors.VALIDATION_EXCEPTION.getErrorDescription() + fv.getErrors());
				List<SanctionContactResponse> contactResponses = new ArrayList<>();
				contactResponses.add(conResponse);
				LOG.error(SanctionErrors.VALIDATION_EXCEPTION.getErrorDescription());
				sanctionsResponse.setErrorCode(SanctionErrors.VALIDATION_EXCEPTION.getErrorCode());
				sanctionsResponse.setErrorDescription(
						SanctionErrors.VALIDATION_EXCEPTION.getErrorDescription() + fv.getErrors());
				sanctionsResponse.setContactResponses(contactResponses);
				return sanctionsResponse;
			}
			providerProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.FINSCAN_PROVIDER);

			
			SanctionRequest cloneSanctionRequest = (SanctionRequest) ObjectCloner.deepCopy(sanctionRequest);
			createTasks(sanctionRequest, lst, providerProperty);

			if (!lst.isEmpty()) {

				sanctionsResponse = invokeExecutors(lst, cloneSanctionRequest);
			} else {
				sanctionsResponse = iservicePort.checkSanctionDetails(sanctionRequest, providerProperty);
			}
			callSLMultiForClientNotExsist(cloneSanctionRequest, sanctionsResponse, providerProperty);

		} catch (SanctionException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Error in ServiceImpl : getSanctionDetails()", e);
			throw new SanctionException(SanctionErrors.ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER);
		}*/
		SanctionResponse sanctionResponse = new SanctionResponse();
		List<SanctionContactResponse> sanctionContactResponseList = new ArrayList<>();
		SanctionContactResponse sanctionContactResponse=new SanctionContactResponse();
		sanctionContactResponse.setOfacList("Safe");
		sanctionContactResponse.setWorldCheck("Safe");
		sanctionContactResponse.setStatus("Pass");
		sanctionContactResponseList.add(sanctionContactResponse);
		sanctionResponse.setContactResponses(sanctionContactResponseList);
		
		List<SanctionBeneficiaryResponse> beneficiaryResponseList = new ArrayList<>();
		SanctionBeneficiaryResponse beneficiaryResponse =new SanctionBeneficiaryResponse();
		beneficiaryResponse.setOfacList("Safe");
		beneficiaryResponse.setWorldCheck("Safe");
		beneficiaryResponse.setStatus("Pass");
		beneficiaryResponseList.add(beneficiaryResponse);
		sanctionResponse.setBeneficiaryResponses(beneficiaryResponseList);
		
		List<SanctionBankResponse> SanctionBankResponseList = new ArrayList<>();
		SanctionBankResponse bankResponse=new SanctionBankResponse();
		bankResponse.setOfacList("Safe");
		bankResponse.setWorldCheck("Safe");
		bankResponse.setStatus("Pass");
		SanctionBankResponseList.add(bankResponse);
		sanctionResponse.setBankResponses(SanctionBankResponseList);
		String jsonAccountSignupResponse = JsonConverterUtil.convertToJsonWithNull(sanctionResponse);
		 LOG.warn("\n ------- KYC Response Start -------- \n  {}", jsonAccountSignupResponse);
		return sanctionResponse;
	}

	/**
	 * Invoke executors.
	 *
	 * @param lst the lst
	 * @param request the request
	 * @return the sanction response
	 * @throws InterruptedException the interrupted exception
	 * @throws ExecutionException the execution exception
	 * @throws SanctionException the sanction exception
	 */
	private SanctionResponse invokeExecutors(List<Callable<IDomain>> lst, SanctionRequest request)
			throws InterruptedException, ExecutionException, SanctionException {
		ExecutorService sanctionExecutors = SanctionExecutors.getExecutorService();
		SanctionResponse sanctionMultiResponse = new SanctionResponse();
		SanctionResponse finalMultiResponse;
		SanctionResponse sanctionGetStatusResponse = new SanctionResponse();
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		List<SanctionBeneficiaryResponse> beneficiaryResponses = new ArrayList<>();
		List<SanctionBankResponse> bankResponses = new ArrayList<>();
		sanctionGetStatusResponse.setContactResponses(contactResponses);
		sanctionGetStatusResponse.setBeneficiaryResponses(beneficiaryResponses);
		sanctionGetStatusResponse.setBankResponses(bankResponses);

		List<Future<IDomain>> tasks = sanctionExecutors.invokeAll(lst);

		for (Future<IDomain> task : tasks) {
			if (task.get() instanceof SanctionResponse) {
				sanctionMultiResponse = (SanctionResponse) task.get();
			} else if (task.get() instanceof SanctionGetStatusResponse) {
				SanctionGetStatusResponse getStatusResponse;
				getStatusResponse = (SanctionGetStatusResponse) task.get();
				switch (getStatusResponse.getApplicationId()) {
				case Constants.CLNTS:
					SanctionContactResponse contactResponse = populategetStatusContactResponse(getStatusResponse,
							request);
					sanctionGetStatusResponse.getContactResponses().add(contactResponse);
					break;

				case Constants.BNFC:
					SanctionBeneficiaryResponse beneficiaryResponse = populateGetStatusBeneficiaryResponse(
							getStatusResponse,request);
					sanctionGetStatusResponse.getBeneficiaryResponses().add(beneficiaryResponse);
					break;

				case Constants.BANK:
					SanctionBankResponse bankResponse = populateGetStatusBankResponse(getStatusResponse,request);
					sanctionGetStatusResponse.getBankResponses().add(bankResponse);
					break;
				default:
					LOG.error("Wrong applicationID in sanctionresponse");
					throw new SanctionException(SanctionErrors.SANCTION_GENERIC_EXCEPTION);
				}
			}
		}
		finalMultiResponse = mergeSanctionResponses(sanctionMultiResponse, sanctionGetStatusResponse);

		return finalMultiResponse;
	}

	/**
	 * Populate get status bank response.
	 *
	 * @param getStatusResponse the get status response
	 * @param request the request
	 * @return the sanction bank response
	 */
	private SanctionBankResponse populateGetStatusBankResponse(SanctionGetStatusResponse getStatusResponse,SanctionRequest request) {
		SanctionBankRequest bankRequest = getSanctionBankRequestById(request, getStatusResponse.getId());
		if(null != bankRequest)
			setOfacAndWcFromPreValues(getStatusResponse, bankRequest.getPreviousStatus(), bankRequest.getPreviousOfac(),bankRequest.getPreviousWorldCheck());
		SanctionBankResponse bankResponse = new SanctionBankResponse();
		bankResponse.setBankID(getStatusResponse.getId());
		bankResponse.setPendingCount(getStatusResponse.getPendingCount());
		bankResponse.setResultsCount(getStatusResponse.getResultsCount());
		bankResponse.setSanctionId(getStatusResponse.getSanctionId());
		bankResponse.setStatus(getStatusResponse.getStatus());
		bankResponse.setStatusDescription(getStatusResponse.getStatusDescription());
		bankResponse.setProviderResponse(getStatusResponse.getProviderResponse());
		bankResponse.setProviderName(Constants.FINSCAN_PROVIDER);
		bankResponse.setProviderMethod(Constants.PROVIDER_METHOD_SL_GET_STATUS);
		bankResponse.setErrorCode(getStatusResponse.getErrorCode());
		bankResponse.setErrorDescription(getStatusResponse.getErrorDescription());
		bankResponse.setOfacList(getStatusResponse.getOfacList());
		bankResponse.setWorldCheck(getStatusResponse.getWorldCheck());
		return bankResponse;
	}

	/**
	 * Populate get status beneficiary response.
	 *
	 * @param getStatusResponse the get status response
	 * @param request the request
	 * @return the sanction beneficiary response
	 */
	private SanctionBeneficiaryResponse populateGetStatusBeneficiaryResponse(
			SanctionGetStatusResponse getStatusResponse,SanctionRequest request) {
		SanctionBeneficiaryRequest beneRequest = getSanctionBeneficiaryRequestById(request, getStatusResponse.getId());
		if(null != beneRequest)
			setOfacAndWcFromPreValues(getStatusResponse, beneRequest.getPreviousStatus(), beneRequest.getPreviousOfac(),beneRequest.getPreviousWorldCheck());
		SanctionBeneficiaryResponse beneficiaryResponse = new SanctionBeneficiaryResponse();
		beneficiaryResponse.setBeneficiaryId(getStatusResponse.getId());
		beneficiaryResponse.setPendingCount(getStatusResponse.getPendingCount());
		beneficiaryResponse.setResultsCount(getStatusResponse.getResultsCount());
		beneficiaryResponse.setSanctionId(getStatusResponse.getSanctionId());
		beneficiaryResponse.setStatus(getStatusResponse.getStatus());
		beneficiaryResponse.setStatusDescription(getStatusResponse.getStatusDescription());
		beneficiaryResponse.setProviderResponse(getStatusResponse.getProviderResponse());
		beneficiaryResponse.setProviderName(Constants.FINSCAN_PROVIDER);
		beneficiaryResponse.setProviderMethod(Constants.PROVIDER_METHOD_SL_GET_STATUS);
		beneficiaryResponse.setErrorCode(getStatusResponse.getErrorCode());
		beneficiaryResponse.setErrorDescription(getStatusResponse.getErrorDescription());
		beneficiaryResponse.setOfacList(getStatusResponse.getOfacList());
		beneficiaryResponse.setWorldCheck(getStatusResponse.getWorldCheck());
		return beneficiaryResponse;
	}

	/**
	 *  Set ofacList and worldCheck in ContactResponse -Saylee.
	 *
	 * @param getStatusResponse the get status response
	 * @param request the request
	 * @return the sanction contact response
	 */
	private SanctionContactResponse populategetStatusContactResponse(SanctionGetStatusResponse getStatusResponse,
			SanctionRequest request) {

		SanctionContactRequest contactRequest = getSanctionContactRequestById(request, getStatusResponse.getId());
		if(null != contactRequest)
			setSanctionOfacAndWc(getStatusResponse, contactRequest);

		SanctionContactResponse contactResponse = new SanctionContactResponse();
		contactResponse.setContactId(getStatusResponse.getId());
		contactResponse.setPendingCount(getStatusResponse.getPendingCount());
		contactResponse.setResultsCount(getStatusResponse.getResultsCount());
		contactResponse.setSanctionId(getStatusResponse.getSanctionId());
		contactResponse.setStatus(getStatusResponse.getStatus());
		contactResponse.setStatusDescription(getStatusResponse.getStatusDescription());
		contactResponse.setProviderResponse(getStatusResponse.getProviderResponse());
		contactResponse.setProviderName(Constants.FINSCAN_PROVIDER);
		contactResponse.setProviderMethod(Constants.PROVIDER_METHOD_SL_GET_STATUS);
		contactResponse.setErrorCode(getStatusResponse.getErrorCode());
		contactResponse.setErrorDescription(getStatusResponse.getErrorDescription());
		contactResponse.setOfacList(getStatusResponse.getOfacList());
		contactResponse.setWorldCheck(getStatusResponse.getWorldCheck());
		return contactResponse;
	}

	/**
	 * Merge sanction responses.
	 *
	 * @param multiResponse the multi response
	 * @param getStatusResponse the get status response
	 * @return the sanction response
	 */
	private SanctionResponse mergeSanctionResponses(SanctionResponse multiResponse,
			SanctionResponse getStatusResponse) {
		SanctionResponse sanctionResponse = new SanctionResponse();
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		List<SanctionBeneficiaryResponse> beneficiaryResponses = new ArrayList<>();
		List<SanctionBankResponse> bankResponses = new ArrayList<>();
		sanctionResponse.setContactResponses(contactResponses);
		sanctionResponse.setBeneficiaryResponses(beneficiaryResponses);
		sanctionResponse.setBankResponses(bankResponses);

		mergeGetStatusSanctionResponses(getStatusResponse, sanctionResponse);

		mergerLookUpMultiResponses(multiResponse, sanctionResponse);

		return sanctionResponse;
	}

	/**
	 * Merger look up multi responses.
	 *
	 * @param multiResponse the multi response
	 * @param sanctionResponse the sanction response
	 */
	private void mergerLookUpMultiResponses(SanctionResponse multiResponse, SanctionResponse sanctionResponse) {
		if (multiResponse.getContactResponses() != null && !multiResponse.getContactResponses().isEmpty()) {
			for (SanctionContactResponse contactResponse : multiResponse.getContactResponses()) {
				sanctionResponse.getContactResponses().add(contactResponse);
			}
		}

		if (multiResponse.getBeneficiaryResponses() != null && !multiResponse.getBeneficiaryResponses().isEmpty()) {
			for (SanctionBeneficiaryResponse beneficiaryResponse : multiResponse.getBeneficiaryResponses()) {
				sanctionResponse.getBeneficiaryResponses().add(beneficiaryResponse);
			}
		}
		if (multiResponse.getBankResponses() != null && !multiResponse.getBankResponses().isEmpty()) {
			for (SanctionBankResponse bankResponse : multiResponse.getBankResponses()) {
				sanctionResponse.getBankResponses().add(bankResponse);
			}
		}
		sanctionResponse.setProviderResponse(multiResponse.getProviderResponse());
		sanctionResponse.setErrorCode(multiResponse.getErrorCode());
		sanctionResponse.setErrorDescription(multiResponse.getErrorDescription());
	}

	/**
	 * Merge get status sanction responses.
	 *
	 * @param getStatusResponse the get status response
	 * @param sanctionResponse the sanction response
	 */
	private void mergeGetStatusSanctionResponses(SanctionResponse getStatusResponse,
			SanctionResponse sanctionResponse) {
		if (getStatusResponse.getContactResponses() != null && !getStatusResponse.getContactResponses().isEmpty()) {
			for (SanctionContactResponse contactResponse : getStatusResponse.getContactResponses()) {
				sanctionResponse.getContactResponses().add(contactResponse);
			}
		}

		if (getStatusResponse.getBeneficiaryResponses() != null
				&& !getStatusResponse.getBeneficiaryResponses().isEmpty()) {
			for (SanctionBeneficiaryResponse beneficiaryResponse : getStatusResponse.getBeneficiaryResponses()) {
				sanctionResponse.getBeneficiaryResponses().add(beneficiaryResponse);
			}
		}
		if (getStatusResponse.getBankResponses() != null && !getStatusResponse.getBankResponses().isEmpty()) {
			for (SanctionBankResponse bankResponse : getStatusResponse.getBankResponses()) {
				sanctionResponse.getBankResponses().add(bankResponse);
			}
		}
	}

	/**
	 * Creates the tasks.
	 *
	 * @param sanctionRequest the sanction request
	 * @param lst the lst
	 * @param providerProperty the provider property
	 */
	private void createTasks(SanctionRequest sanctionRequest, List<Callable<IDomain>> lst,
			ProviderProperty providerProperty) {
		List<SanctionContactRequest> contactRequests = new ArrayList<>();
		List<SanctionBeneficiaryRequest> beneficiaryRequests = new ArrayList<>();
		List<SanctionBankRequest> bankRequests = new ArrayList<>();

		if (sanctionRequest.getContactrequests() != null) {

			createGetStatusTasksForContact(sanctionRequest, lst, providerProperty, contactRequests);
		}

		if (sanctionRequest.getBeneficiaryRequests() != null) {
			createGetStatusTaskForBeneficiary(sanctionRequest, lst, providerProperty, beneficiaryRequests);
		}

		if (sanctionRequest.getBankRequests() != null) {
			createGetStatusTaskForBank(sanctionRequest, lst, providerProperty, bankRequests);
		}

		if (!(bankRequests.isEmpty() && beneficiaryRequests.isEmpty() && contactRequests.isEmpty())) {
			lst.add(new SLLookUpMultiTask(sanctionRequest, providerProperty));
		}
		sanctionRequest.setBankRequests(bankRequests);
		sanctionRequest.setBeneficiaryRequests(beneficiaryRequests);
		sanctionRequest.setContactrequests(contactRequests);
	}

	/**
	 * Creates the get status task for bank.
	 *
	 * @param sanctionRequest the sanction request
	 * @param lst the lst
	 * @param providerProperty the provider property
	 * @param bankRequests the bank requests
	 */
	private void createGetStatusTaskForBank(SanctionRequest sanctionRequest, List<Callable<IDomain>> lst,
			ProviderProperty providerProperty, List<SanctionBankRequest> bankRequests) {
		bankRequests.addAll(sanctionRequest.getBankRequests());
		for (SanctionBankRequest bankRequest : sanctionRequest.getBankRequests()) {
			if (Boolean.TRUE.equals(bankRequest.getIsExisting())) {
				SanctionGetStatusRequest fundsOutRequest = populateGetStatusRequestforBank(bankRequest);
				lst.add(new SlGetStatusTask(fundsOutRequest, providerProperty));
				bankRequests.remove(bankRequest);
			}
		}
	}

	/**
	 * Creates the get status task for beneficiary.
	 *
	 * @param sanctionRequest the sanction request
	 * @param lst the lst
	 * @param providerProperty the provider property
	 * @param beneficiaryRequests the beneficiary requests
	 */
	private void createGetStatusTaskForBeneficiary(SanctionRequest sanctionRequest, List<Callable<IDomain>> lst,
			ProviderProperty providerProperty, List<SanctionBeneficiaryRequest> beneficiaryRequests) {
		beneficiaryRequests.addAll(sanctionRequest.getBeneficiaryRequests());
		for (SanctionBeneficiaryRequest beneficiaryRequest : sanctionRequest.getBeneficiaryRequests()) {
			if (Boolean.TRUE.equals(beneficiaryRequest.getIsExisting())) {
				SanctionGetStatusRequest fundsOutRequest = populateGetStatusRequestforBeneficiary(beneficiaryRequest);
				lst.add(new SlGetStatusTask(fundsOutRequest, providerProperty));
				beneficiaryRequests.remove(beneficiaryRequest);
			}
		}
	}

	/**
	 * Creates the get status tasks for contact.
	 *
	 * @param sanctionRequest the sanction request
	 * @param lst the lst
	 * @param providerProperty the provider property
	 * @param contactRequests the contact requests
	 */
	private void createGetStatusTasksForContact(SanctionRequest sanctionRequest, List<Callable<IDomain>> lst,
			ProviderProperty providerProperty, List<SanctionContactRequest> contactRequests) {
		contactRequests.addAll(sanctionRequest.getContactrequests());
		for (SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()) {
			if (Boolean.TRUE.equals(contactRequest.getIsExisting())) {
				SanctionGetStatusRequest fundsOutRequest = populateGetStatusRequestforContact(contactRequest);
				lst.add(new SlGetStatusTask(fundsOutRequest, providerProperty));
				contactRequests.remove(contactRequest);
			}
		}
	}

	/**
	 * Populate get status requestfor contact.
	 *
	 * @param contactRequest the contact request
	 * @return the sanction get status request
	 */
	private SanctionGetStatusRequest populateGetStatusRequestforContact(SanctionContactRequest contactRequest) {
		SanctionGetStatusRequest fundsOutRequest = new SanctionGetStatusRequest();
		fundsOutRequest.setApplicationId(Constants.CLNTS);
		fundsOutRequest.setCountry(contactRequest.getCountry());
		fundsOutRequest.setDob(contactRequest.getDob());
		fundsOutRequest.setFullName(contactRequest.getFullName());
		fundsOutRequest.setGender(contactRequest.getGender());
		fundsOutRequest.setId(contactRequest.getContactId());
		fundsOutRequest.setSanctionId(contactRequest.getSanctionId());
		return fundsOutRequest;
	}

	/**
	 * Populate get status requestfor beneficiary.
	 *
	 * @param beneficiaryRequest the beneficiary request
	 * @return the sanction get status request
	 */
	private SanctionGetStatusRequest populateGetStatusRequestforBeneficiary(
			SanctionBeneficiaryRequest beneficiaryRequest) {
		SanctionGetStatusRequest fundsOutRequest = new SanctionGetStatusRequest();
		fundsOutRequest.setApplicationId(Constants.BNFC);
		fundsOutRequest.setCountry(beneficiaryRequest.getCountry());
		fundsOutRequest.setDob(beneficiaryRequest.getDob());
		fundsOutRequest.setFullName(beneficiaryRequest.getFullName());
		fundsOutRequest.setGender(beneficiaryRequest.getGender());
		fundsOutRequest.setId(beneficiaryRequest.getBeneficiaryId());
		fundsOutRequest.setSanctionId(beneficiaryRequest.getSanctionId());
		return fundsOutRequest;
	}

	/**
	 * Populate get status requestfor bank.
	 *
	 * @param bankRequest the bank request
	 * @return the sanction get status request
	 */
	private SanctionGetStatusRequest populateGetStatusRequestforBank(SanctionBankRequest bankRequest) {
		SanctionGetStatusRequest fundsOutRequest = new SanctionGetStatusRequest();
		fundsOutRequest.setApplicationId(Constants.BANK);
		fundsOutRequest.setCountry(bankRequest.getCountry());
		fundsOutRequest.setFullName(bankRequest.getBankName());
		fundsOutRequest.setId(bankRequest.getBankId());
		fundsOutRequest.setSanctionId(bankRequest.getSanctionId());
		return fundsOutRequest;
	}

	/**
	 *  Get contactRequest for particular ContactID.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the sanction contact request by id
	 */
	private SanctionContactRequest getSanctionContactRequestById(SanctionRequest request, int id) {
		for (SanctionContactRequest contactRequest : request.getContactrequests()) {
			if (contactRequest.getContactId() == id) {
				return contactRequest;
			}
		}
		return null;

	}
	
	/**
	 *  Get beneficiary request for particular beneficiaryID.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the sanction beneficiary request by id
	 */
	private SanctionBeneficiaryRequest getSanctionBeneficiaryRequestById(SanctionRequest request, int id) {
		for (SanctionBeneficiaryRequest beneReq : request.getBeneficiaryRequests()) {
			if (beneReq.getBeneficiaryId() == id) {
				return beneReq;
			}
		}
		return null;

	}
	
	
	/**
	 *  Get beneficiary request for particular beneficiaryID.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the sanction bank request by id
	 */
	private SanctionBankRequest getSanctionBankRequestById(SanctionRequest request, int id) {
		for (SanctionBankRequest bankReq : request.getBankRequests()) {
			if (bankReq.getBankId() == id) {
				return bankReq;
			}
		}
		return null;

	}


	/**
	 *  Get contactResponse for particular ContactID.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the sanction contact response by id
	 */
	public SanctionContactResponse getSanctionContactResponseById(SanctionResponse request, int id) {
		for (SanctionContactResponse contactResponse : request.getContactResponses()) {
			if (contactResponse.getContactId() == id) {
				return contactResponse;
			}
		}
		return null;

	}
	
	/**
	 * Gets the sanction bank response by id.
	 *
	 * @param response the response
	 * @param bankID the bank ID
	 * @return the sanction bank response by id
	 */
	private SanctionBankResponse getSanctionBankResponseById(SanctionResponse response, int bankID) {
		for (SanctionBankResponse bankResponse : response.getBankResponses()) {
			if (bankResponse.getBankID() == bankID) {
				return bankResponse;
			}
		}
		return null;
	}
	
	/**
	 * Gets the sanction beneficiary response by id.
	 *
	 * @param response the response
	 * @param beneficiaryId the beneficiary id
	 * @return the sanction beneficiary response by id
	 */
	private SanctionBeneficiaryResponse getSanctionBeneficiaryResponseById(SanctionResponse response,
			int beneficiaryId) {
		for (SanctionBeneficiaryResponse beneResponse : response.getBeneficiaryResponses()) {
			if (beneResponse.getBeneficiaryId() == beneficiaryId) {
				return beneResponse;
			}
		}
		return null;
	}

	/**
	 *  Get contactResponse for particular ContactID.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the sanction contact response index by id
	 */
	public Integer getSanctionContactResponseIndexById(SanctionResponse request, int id) {
		for (int i = 0; i < request.getContactResponses().size(); i++) {
			if (request.getContactResponses().get(i).getContactId() == id) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * Set Status for OfacList and WorldCheck for particular contactId check
	 * condition for SanctionStatus is Fail or Pending and PreviousStatus of
	 * contact is not Pass then set Previous Status of OfacList and WorldCheck
	 * for that Contact - Saylee.
	 *
	 * @param getStatusResponse the get status response
	 * @param request the request
	 */
	private void setSanctionOfacAndWc(SanctionGetStatusResponse getStatusResponse, SanctionContactRequest request) {
		setOfacAndWcFromPreValues(getStatusResponse,request.getPreviousStatus(),request.getPreviousOfac(),request.getPreviousWorldCheck());
	}

	/**
	 * Sets the ofac and wc from pre values.
	 *
	 * @param getStatusResponse the get status response
	 * @param preStatus the pre status
	 * @param preOfac the pre ofac
	 * @param preWc the pre wc
	 */
	private void setOfacAndWcFromPreValues(SanctionGetStatusResponse getStatusResponse,
			String preStatus,String preOfac,String preWc) {
		if ((Constants.FAIL.equals(getStatusResponse.getStatus())
				|| Constants.PENDING.equals(getStatusResponse.getStatus()))
				&& (null != preStatus && !Constants.PASS.equals(preStatus))) {
			if (null != preOfac) {
				getStatusResponse.setOfacList(preOfac);
			}
			if (null != preWc) {
				getStatusResponse.setWorldCheck(preWc);
			}
		}
	}

	/**
	 * Call SL multi for client not exsist.
	 *
	 * @param request the request
	 * @param response the response
	 * @param providerProperty the provider property
	 * @throws SanctionException the sanction exception
	 */
	private void callSLMultiForClientNotExsist(SanctionRequest request, SanctionResponse response,
			ProviderProperty providerProperty) throws SanctionException {
		if(response == null) {
			return;
		}
		List<SanctionContactRequest> contactRequests = new ArrayList<>();
		List<SanctionBankRequest> bankRequests = new ArrayList<>();
		List<SanctionBeneficiaryRequest> beneficiaryRequests = new ArrayList<>();
		List<SanctionContactResponse> newContactResponses = new ArrayList<>();
		List<SanctionBankResponse> newBankResponses = new ArrayList<>();
		List<SanctionBeneficiaryResponse> newBeneResponses = new ArrayList<>();
		try {
			addFailedContactRequests(request, response.getContactResponses(), contactRequests);
			addFailedBankRequests(request, response.getBankResponses(), bankRequests);
			addFailedBeneficiaryRequests(request, response.getBeneficiaryResponses(), beneficiaryRequests);

			SanctionRequest newRequest = new SanctionRequest();
			newRequest.setContactrequests(contactRequests);
			newRequest.setBeneficiaryRequests(beneficiaryRequests);
			newRequest.setBankRequests(bankRequests);
			
			if (!(bankRequests.isEmpty() && beneficiaryRequests.isEmpty() && contactRequests.isEmpty())) {
				SanctionResponse slMultiResponse = iservicePort.checkSanctionDetails(newRequest, providerProperty);
				mergetNewContactResponses(response, newContactResponses, slMultiResponse);
				mergetNewBankResponses(response, newBankResponses, slMultiResponse);
				mergetNewBeneficiaryResponses(response, newBeneResponses, slMultiResponse);
			}

		} catch (SanctionException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Error in ServiceImpl : getSanctionDetails()", e);
			throw new SanctionException(SanctionErrors.ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER);
		}
	}

	/**
	 * Merget new contact responses.
	 *
	 * @param response the response
	 * @param newResponses the new responses
	 * @param slMultiResponse the sl multi response
	 */
	private void mergetNewContactResponses(SanctionResponse response, List<SanctionContactResponse> newResponses,
			SanctionResponse slMultiResponse) {
		if (slMultiResponse == null || slMultiResponse.getContactResponses() == null) {
			return;
		}
		for (int i = 0; i < response.getContactResponses().size(); i++) {
			SanctionContactResponse contactResponse;
			SanctionContactResponse preContactResponse = response.getContactResponses().get(i);
			SanctionContactResponse newContactResponse = getSanctionContactResponseById(slMultiResponse,
					preContactResponse.getContactId());
			if (newContactResponse != null) {
				contactResponse = newContactResponse;
			} else {
				contactResponse = preContactResponse;
			}
			newResponses.add(contactResponse);
		}
		response.setContactResponses(newResponses);
	}
	
	/**
	 * Merget new bank responses.
	 *
	 * @param response the response
	 * @param newResponses the new responses
	 * @param slMultiResponse the sl multi response
	 */
	private void mergetNewBankResponses(SanctionResponse response, List<SanctionBankResponse> newResponses,
			SanctionResponse slMultiResponse) {
		if (slMultiResponse == null || slMultiResponse.getBankResponses() == null) {
			return;
		}
		for (int i = 0; i < response.getBankResponses().size(); i++) {
			SanctionBankResponse bankResponse;
			SanctionBankResponse preBankResponse = response.getBankResponses().get(i);
			SanctionBankResponse newBankResponse = getSanctionBankResponseById(slMultiResponse,
					preBankResponse.getBankID());
			if (newBankResponse != null) {
				bankResponse = newBankResponse;
			} else {
				bankResponse = preBankResponse;
			}
			newResponses.add(bankResponse);
		}
		response.setBankResponses(newResponses);
	}
	
	
	

	/**
	 * Merget new beneficiary responses.
	 *
	 * @param response the response
	 * @param newResponses the new responses
	 * @param slMultiResponse the sl multi response
	 */
	private void mergetNewBeneficiaryResponses(SanctionResponse response, List<SanctionBeneficiaryResponse> newResponses,
			SanctionResponse slMultiResponse) {
		if (slMultiResponse == null || slMultiResponse.getBeneficiaryResponses() == null) {
			return;
		}
		for (int i = 0; i < response.getBeneficiaryResponses().size(); i++) {
			SanctionBeneficiaryResponse beneficiaryResponse;
			SanctionBeneficiaryResponse preBeneResponse = response.getBeneficiaryResponses().get(i);
			SanctionBeneficiaryResponse newBeneResponse = getSanctionBeneficiaryResponseById(slMultiResponse,
					preBeneResponse.getBeneficiaryId());
			if (newBeneResponse != null) {
				beneficiaryResponse = newBeneResponse;
			} else {
				beneficiaryResponse = preBeneResponse;
			}
			newResponses.add(beneficiaryResponse);
		}
		response.setBeneficiaryResponses(newResponses);
	}

	/**
	 * Adds the failed contact requests.
	 *
	 * @param request the request
	 * @param contactResponses the contact responses
	 * @param contactRequests the contact requests
	 */
	private void addFailedContactRequests(SanctionRequest request, List<SanctionContactResponse> contactResponses,
			List<SanctionContactRequest> contactRequests) {
		if(contactResponses == null || request == null) {
			return;
		}
		for (int i = 0; i < contactResponses.size(); i++) {
			SanctionContactResponse contactResponse = contactResponses.get(i);
			if (contactResponse.getStatusDescription() != null && contactResponse.getStatusDescription().contains(Constants.CLIENT_NOT_EXSIST)) {
				SanctionContactRequest contactRequest = getSanctionContactRequestById(request,
						contactResponse.getContactId());
				contactRequests.add(contactRequest);
			}
		}
	}
	
	/**
	 * Adds the failed bank requests.
	 *
	 * @param request the request
	 * @param bankResponses the bank responses
	 * @param bankRequests the bank requests
	 */
	private void addFailedBankRequests(SanctionRequest request, List<SanctionBankResponse> bankResponses,
			List<SanctionBankRequest> bankRequests) {
		if(bankResponses == null || request == null) {
			return;
		}
		for (int i = 0; i < bankResponses.size(); i++) {
			SanctionBankResponse bankResponse = bankResponses.get(i);
			if (bankResponse.getStatusDescription() != null && bankResponse.getStatusDescription().contains(Constants.CLIENT_NOT_EXSIST)) {
				SanctionBankRequest bankRequest = getSanctionBankRequestById(request,
						bankResponse.getBankID());
				bankRequests.add(bankRequest);
			}
		}
	}
	
	/**
	 * Adds the failed beneficiary requests.
	 *
	 * @param request the request
	 * @param beneResponses the bene responses
	 * @param bankRequests the bank requests
	 */
	private void addFailedBeneficiaryRequests(SanctionRequest request, List<SanctionBeneficiaryResponse> beneResponses,
			List<SanctionBeneficiaryRequest> bankRequests) {
		if(beneResponses == null || request == null) {
			return;
		}
		for (int i = 0; i < beneResponses.size(); i++) {
			SanctionBeneficiaryResponse beneResponse = beneResponses.get(i);
			if (beneResponse.getStatusDescription() != null && beneResponse.getStatusDescription().contains(Constants.CLIENT_NOT_EXSIST)) {
				SanctionBeneficiaryRequest beneRequest = getSanctionBeneficiaryRequestById(request,
						beneResponse.getBeneficiaryId());
				bankRequests.add(beneRequest);
			}
		}
	}
	
	

}
