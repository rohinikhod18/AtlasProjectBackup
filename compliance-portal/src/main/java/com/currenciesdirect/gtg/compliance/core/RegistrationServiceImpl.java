package com.currenciesdirect.gtg.compliance.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryRequest;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoRequest;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCfxDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.Document;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationUpdateRequest;
import com.currenciesdirect.gtg.compliance.docuploadport.UploadDocumentPort;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class RegistrationServiceImpl.
 */
@Component("registrationServiceImpl")
public class RegistrationServiceImpl implements IRegistrationService {

	private Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Autowired
	@Qualifier("regDBServiceImpl")
	private IRegistrationDBService iRegistrationQueueDBService;

	@Autowired
	@Qualifier("regUpdateTransformer")
	private ITransform<RegUpdateDBDto, RegistrationUpdateRequest> regUpdateTransformer;

	@Autowired
	@Qualifier("lockResourceTransformer")
	private ITransform<LockResourceDBDto, LockResourceRequest> lockResourceTransformer;
	
	@Autowired
	@Qualifier("registrationDetailsFactory")
	private RegistrationDetailsFactory registrationDetailsFactory;
	
	@Autowired
	@Qualifier("regCfxDBServiceImpl")
	private ICfxDBService iCfxDBService;

	@Override
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iRegistrationQueueDBService.getRegistrationQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	@Override
	public IRegistrationDetails getRegistrationDetails(Integer contactId, String custType, RegistrationSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {

			IRegistrationDetails iRegistrationDetailsDto;

			iRegistrationDetailsDto = registrationDetailsFactory.getRegistrationDetailsFactory(custType)
					.getRegistrationDetails(contactId, searchCriteria);

			if (iRegistrationDetailsDto == null) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			}
			addDocuments(iRegistrationDetailsDto);
			if (searchCriteria != null && searchCriteria.getPage() != null) {
				iRegistrationDetailsDto.setCurrentRecord(searchCriteria.getPage().getCurrentRecord());
				iRegistrationDetailsDto.setTotalRecords(searchCriteria.getPage().getTotalRecords());
			}
			return iRegistrationDetailsDto;
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	@Override
	public ActivityLogs updateContact(RegistrationUpdateRequest registrationUpdateRequest)
			throws CompliancePortalException {
		try {

			if (null == registrationUpdateRequest.getCustType() && registrationUpdateRequest.getCustType().isEmpty()) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
			
			RegUpdateDBDto updateRequestHandlerDto = regUpdateTransformer.transform(registrationUpdateRequest);

			return registrationDetailsFactory.getRegistrationDetailsFactory(registrationUpdateRequest.getCustType())
					.updateContact(updateRequestHandlerDto);
			
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
	}

	@Override
	public LockResourceResponse lockResource(LockResourceRequest lockResourceRequest) throws CompliancePortalException {
		LockResourceResponse lockResourceResponse = null;
		try {
			LockResourceDBDto updatelockResourceDBDto = lockResourceTransformer.transform(lockResourceRequest);
			if (lockResourceRequest.getUserResourceId() == null && lockResourceRequest.getLock()) {
				lockResourceResponse = iRegistrationQueueDBService.insertLockResource(updatelockResourceDBDto);
			} else {
				lockResourceResponse = iRegistrationQueueDBService.updateLockResource(updatelockResourceDBDto);
			}
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return lockResourceResponse;
	}
	
	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException{
		try {
			if (null == request || null == request.getCustType()) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			} else {
				return registrationDetailsFactory.getRegistrationDetailsFactory(request.getCustType()).getActivityLogs(request);
			}
			
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}

	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

	private void addDocuments(IRegistrationDetails registrationDetailsDto) {

		if (registrationDetailsDto instanceof RegistrationDetailsDto) {
			addDocumentsForPfx(registrationDetailsDto);
		} else {
			addDocumentsForCfx(registrationDetailsDto);
		}
	}
	
	private void addDocumentsForPfx(IRegistrationDetails registrationDetailsDto) {
		try {
			if (registrationDetailsDto.getAccount() != null && registrationDetailsDto.getCurrentContact() != null) {
				List<Document> documents = UploadDocumentPort.getAttachedDocument(
						registrationDetailsDto.getCurrentContact().getCrmAccountId(),
						registrationDetailsDto.getCurrentContact().getCrmContactId(),
						registrationDetailsDto.getAccount().getOrgCode(),Constants.SOURCE_SALESFORCE);
				if (documents != null) {
					registrationDetailsDto.setDocuments(UploadDocumentPort.updateDocumentDateFormat(documents));
				} else {
					registrationDetailsDto.setDocuments(new ArrayList<>());
				}
			}
		} catch (Exception e) {
			log.debug("Error : ", e);
			registrationDetailsDto.setDocuments(new ArrayList<>());
		}
	}
	
	private void addDocumentsForCfx(IRegistrationDetails registrationDetailsDto) {
		RegistrationCfxDetailsDto regCfxDetailsDto = (RegistrationCfxDetailsDto) registrationDetailsDto;
		try {
				if (regCfxDetailsDto.getCurrentContact() != null) {
				/**get list of document uploaded on account,
				 *  set source as salesforce for cfx to get list documents attached to respective CRMAccountid : Abhijit G*/
				List<Document> documents = UploadDocumentPort.getAttachedDocument(
						regCfxDetailsDto.getCurrentContact().getCrmAccountId(),
						regCfxDetailsDto.getCurrentContact().getCrmContactId(),
						regCfxDetailsDto.getAccount().getOrgCode(),Constants.SOURCE_SALESFORCE);
				if (documents != null) {
					registrationDetailsDto.setDocuments(UploadDocumentPort.updateDocumentDateFormat(documents));
				} else {
					registrationDetailsDto.setDocuments(new ArrayList<>());
				}
			}
				
		} catch (CompliancePortalException e) {
			log.debug("Error : ", e);
			registrationDetailsDto.setDocuments(new ArrayList<>());
		}
	}

	@Override
	public ViewMoreResponse viewMoreDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = null;
		try {
			
			if ("KYC".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iRegistrationQueueDBService.getMoreKycDetails(viewMoreRequest);
			    
			}else if ("SANCTION".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iRegistrationQueueDBService.getMoreSanctionDetails(viewMoreRequest);
			    
			}else if ("CUSTOMCHECK".equalsIgnoreCase(viewMoreRequest.getServiceType())){ 
				
				viewMoreResponse = iRegistrationQueueDBService.getMoreCustomCheckDetails(viewMoreRequest);

			}else if ("FRAUGSTER".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iRegistrationQueueDBService.getMoreFraugsterDetails(viewMoreRequest);
				
			}else if ("ONFIDO".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iRegistrationQueueDBService.getMoreOnfidoDetails(viewMoreRequest);
			    
			}else if ("TRANSACTION_MONITORING".equalsIgnoreCase(viewMoreRequest.getServiceType())){ //AT-4114
				
				viewMoreResponse = iRegistrationQueueDBService.getMoreIntuitionDetails(viewMoreRequest);
			    
			}
			
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return viewMoreResponse;
	}

	@Override
	public ProviderResponseLogResponse getProviderResponse(ProviderResponseLogRequest logRequest)
			throws CompliancePortalException {
		ProviderResponseLogResponse providerResponseLogResponse = null;
		try {
			providerResponseLogResponse = iRegistrationQueueDBService.getProviderResponse(logRequest);
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_PROVIDER_RESPONSE, e);
		}
		return providerResponseLogResponse;
	}

	@Override
	public LockResourceResponse lockResourceMultiContacts(LockResourceRequest lockResourceRequest)
			throws CompliancePortalException {

		LockResourceResponse lockResourceResponse = null;
		try {
			LockResourceDBDto updatelockResourceDBDto = lockResourceTransformer.transform(lockResourceRequest);
			if (lockResourceRequest.getUserResourceId() == null && lockResourceRequest.getLock()) {
				lockResourceResponse = iCfxDBService.insertLockResourceForMultiContact(updatelockResourceDBDto);
			} else {
				lockResourceResponse = iCfxDBService.updateLockResourceForMultiContact(updatelockResourceDBDto);
			}
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return lockResourceResponse;
	
	}
	
	@Override
	public DeviceInfoResponse getDeviceInfo(DeviceInfoRequest deviceInfoRequest)
			throws CompliancePortalException {
		DeviceInfoResponse deviceInfoResponse = null;
		try {
			deviceInfoResponse = iRegistrationQueueDBService.getDeviceInfo(deviceInfoRequest);
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		}
		return deviceInfoResponse;
	}
	
	@Override
	public AccountHistoryResponse getAccountHistory(AccountHistoryRequest accountHistoryRequest) throws CompliancePortalException{
		AccountHistoryResponse accountHistoryResponse = null;
		try {
			accountHistoryResponse = iRegistrationQueueDBService.getAccountHistory(accountHistoryRequest);
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		}
		return accountHistoryResponse;
	}
	
	public boolean setPoiExistsFlag(UserProfile user, Contact contact) throws CompliancePortalException{
		boolean poiExists = false;
		try {
			poiExists = iRegistrationQueueDBService.setPoiExistsFlag(user,contact);
		}catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST, e);
		}
		return poiExists;
	}

}