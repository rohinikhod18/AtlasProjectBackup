package com.currenciesdirect.gtg.compliance.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeader;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayload;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.CustomUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.CustomUpdateResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.Document;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RecentPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.docuploadport.UploadDocumentPort;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;


@Component("paymentOutServiceImpl")
public class PaymentOutServiceImpl implements IPaymentOutService{

	private Logger log = LoggerFactory.getLogger(PaymentOutServiceImpl.class);
	
	@Autowired
	@Qualifier("commHubServiceImpl")
	private ICommHubServiceImpl iCommHubServiceImpl;
	
	@Autowired
	@Qualifier("paymentOutDBServiceImpl")
	private IPaymentOutDBService iPaymentOutDBService;
	
	@Autowired
	@Qualifier("paymentOutUpdateTransformer")
	private ITransform<PaymentOutUpdateDBDto, PaymentOutUpdateRequest> paymentOutUpdateTransformer;
	
	@Override
	public PaymentOutQueueDto getPaymentOutQueueWithCriteria(PaymentOutSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iPaymentOutDBService.getPaymentOutQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}

	}

	@Override
	public PaymentOutDetailsDto getPaymentOutDetails(Integer paymentOutId, String custType, PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException {
		
		try {
			PaymentOutDetailsDto paymentOutDetailDto = iPaymentOutDBService.getPaymentOutDetails(paymentOutId, custType, searchCriteria);
			addDocuments(paymentOutDetailDto);
			if (searchCriteria != null && searchCriteria.getPage() != null) {
				paymentOutDetailDto.setCurrentRecord(searchCriteria.getPage().getCurrentRecord());
				paymentOutDetailDto.setTotalRecords(searchCriteria.getPage().getTotalRecords());
			}
			return paymentOutDetailDto;
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}
	
  @Override
	public  RecentPaymentOutDetails getMostRecentPaymentOutId(String tradeAccountNumber,String beneAccountNumber) throws CompliancePortalException {
		try {
			   return iPaymentOutDBService.getMostRecentPaymentOutId(tradeAccountNumber, beneAccountNumber);
			} 
		catch (CompliancePortalException e) {
			   logError(e.getOrgException());
			   throw e;
		   }
	}

	@Override
	public PaymentOutDetailsDto getPaymentOutDetailsWithCriteria(PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException {
		try {
			PaymentOutDetailsDto paymentOutDetailDto = iPaymentOutDBService.getPaymentOutDetailsWithCriteria(searchCriteria);
			addDocuments(paymentOutDetailDto);
			if (searchCriteria != null && searchCriteria.getPage() != null) {
				paymentOutDetailDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
				paymentOutDetailDto.setCurrentRecord(searchCriteria.getPage().getCurrentRecord());
				paymentOutDetailDto.setTotalRecords(searchCriteria.getPage().getTotalRecords());
			}
			return paymentOutDetailDto;
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		try {
			if (request == null) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			} else 	{
				return iPaymentOutDBService.getActivityLogs(request);
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

	@Override
	public ActivityLogs updatePaymentOut(PaymentOutUpdateRequest request) throws CompliancePortalException {
		
		try {
			PaymentOutUpdateDBDto paymentOutUpdateDBDto = paymentOutUpdateTransformer.transform(request);
			String paymentOutRequest = JsonConverterUtil.convertToJsonWithoutNull(paymentOutUpdateDBDto);
			log.info("\n Payment Out Status Change on UI request : \n{}",paymentOutRequest);
			if(Constants.CLEAR.equalsIgnoreCase(request.getUpdatedPaymentOutStatus())){
				CustomUpdateResponse customUpdateResponse ;
				customUpdateResponse=customUpdateService(request.getOrgCode(),request.getPaymentOutId(),
						JsonConverterUtil.convertToJsonWithNull(request.getUser()), 
						request.getCountry(),request.getCountryRiskLevel(), request.getBeneCheckStatus());
				if(Constants.PASS.equalsIgnoreCase(customUpdateResponse.getOverallStatus())){					
					return iPaymentOutDBService.updatePaymentOut(paymentOutUpdateDBDto);
				}
			}
			else {
				ActivityLogs activityLogs =  iPaymentOutDBService.updatePaymentOut(paymentOutUpdateDBDto);
				//Add code comment for jira AT-4519
				//sendEmailOnRejectOrSeizeByCommhub(request);
				return activityLogs;
			}
			
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
		return null;
	}


	/**
	 * Send email on reject or seize by commhub.
	 *
	 * @param paymentOutUpdateDBDto the payment out update DB dto
	 */
	
	//Code add for AT-1999 Commhub API
	private void sendEmailOnRejectOrSeizeByCommhub(PaymentOutUpdateRequest paymentOutUpdateDBDto) {
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if (Constants.REJECT.equalsIgnoreCase(paymentOutUpdateDBDto.getUpdatedPaymentOutStatus())
					|| Constants.SEIZE.equalsIgnoreCase(paymentOutUpdateDBDto.getUpdatedPaymentOutStatus())) {
				SendEmailRequest emailrequest = new SendEmailRequest();
				EmailHeader header =  new EmailHeader();
				header.setOrgCode(paymentOutUpdateDBDto.getOrgCode());
				header.setLegalEntity(paymentOutUpdateDBDto.getLegalEntity());
				header.setCustomerType(paymentOutUpdateDBDto.getCustType());
				header.setCustNumber(paymentOutUpdateDBDto.getClientNumber());
				String[] notification = {"Email"};
	 			header.setNotificationType(notification);	
				header.setEvent(Constants.PAYMENT_OUT_REJECT_EVENT);
				header.setLocale(Constants.LOCALE_EN);
				header.setSourceSystem("Atlas");
				header.setRetryTimeout("60000");
				header.setOsrId(UUID.randomUUID().toString());
				
				EmailPayload payload = new EmailPayload();
				payload.setEmailId(System.getProperty(paymentOutUpdateDBDto.getOrgCode().replaceAll("\\s+", "").toLowerCase()+Constants.EAMIL_SENDTO));
				payload.setSubject("Payment Out Rejected/Seized");
					Map<String,String> attributes = new HashMap<>();
					attributes.put("#updated_payment_status", getPlaceholdersValue(paymentOutUpdateDBDto.getUpdatedPaymentOutStatus()));
					attributes.put("#customer_number", getPlaceholdersValue(paymentOutUpdateDBDto.getClientNumber()));
					attributes.put("#trade_contractId", getPlaceholdersValue(paymentOutUpdateDBDto.getTradeContractNumber()));
					attributes.put("#beneficiary_amount", getPlaceholdersValue(paymentOutUpdateDBDto.getBeneficiaryAmount()));
					attributes.put("#currency", getPlaceholdersValue(paymentOutUpdateDBDto.getBuyCurrency()));
					attributes.put("#payment_status", getPlaceholdersValue(paymentOutUpdateDBDto.getPrePaymentOutStatus()));
					attributes.put("#today_date", DateTimeFormatter.dateTimeFormatter(timestamp));
					attributes.put("#username", getPlaceholdersValue(paymentOutUpdateDBDto.getUserName()));
					attributes.put("#status_reason", getPlaceholdersValue(paymentOutUpdateDBDto.getStatusReason()));
					attributes.put("#comment", getPlaceholdersValue(paymentOutUpdateDBDto.getComment()));
					attributes.put("#beneficiary_name", getPlaceholdersValue(paymentOutUpdateDBDto.getBeneficiaryName()));
				payload.setPlaceHolder(attributes);
					
				emailrequest.setHeader(header);
				emailrequest.setPayload(payload);

				iCommHubServiceImpl.sendEmail(emailrequest,true);
			}
		}catch(Exception e) {
			logError(e);
		}
	}

	private CustomUpdateResponse customUpdateService(String orgCode, Integer paymentOutId, String userProfile,
			String country, String countryRiskLevel, String beneCheckStatus) throws CompliancePortalException {
		Integer paymentInId = null;
		CustomUpdateRequest customUpdateRequest = getCustomUpdateRequest(orgCode, paymentOutId, paymentInId, country,
				countryRiskLevel, beneCheckStatus);
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		ArrayList<Object> list = new ArrayList<>();
		list.add(userProfile);
		headers.put("user", list);
		String baseUrl = System.getProperty("baseComplianceServiceUrl");
		return clientPool.sendRequest(baseUrl + "/compliance-service/services-internal/repeatcheck/updateWhiteListData",
				"POST", customUpdateRequest, CustomUpdateResponse.class, headers, MediaType.APPLICATION_JSON_TYPE);

	}

	private CustomUpdateRequest getCustomUpdateRequest(String orgCode, Integer paymentOutId, Integer paymentInId,
			String country, String countryRiskLevel, String beneCheckStatus) {
		CustomUpdateRequest customUpdateRequest = new CustomUpdateRequest();
		customUpdateRequest.setOrgCode(orgCode);
		customUpdateRequest.setPaymentOutId(paymentOutId);
		customUpdateRequest.setPaymentInId(paymentInId);
		customUpdateRequest.setCountry(country);
		customUpdateRequest.setCountryRiskLevel(countryRiskLevel);
		customUpdateRequest.setBeneCheckStatus(beneCheckStatus);
		return customUpdateRequest;
	}

	private void addDocuments(PaymentOutDetailsDto paymentOutDetailDto) throws CompliancePortalException {
		if (paymentOutDetailDto.getAccount() != null && paymentOutDetailDto.getCurrentContact() != null) {
			List<Document> documents = UploadDocumentPort.getAttachedDocument(
					paymentOutDetailDto.getCurrentContact().getCrmAccountId(),
					paymentOutDetailDto.getCurrentContact().getCrmContactId(),
					paymentOutDetailDto.getAccount().getOrgCode(),Constants.SOURCE_SALESFORCE);
					paymentOutDetailDto.setDocuments(UploadDocumentPort.updateDocumentDateFormat(documents));
		}
		
	}
	
	@Override
	public ViewMoreResponse viewMoreDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = null;
		try {
			
			if ("KYC".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getMoreKycDetails(viewMoreRequest);
			    
			}else if ("SANCTION".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getMoreSanctionDetails(viewMoreRequest);
			    
			}else if ("VELOCITYCHECK".equalsIgnoreCase(viewMoreRequest.getServiceType())){ 
				
				viewMoreResponse = iPaymentOutDBService.getMoreCustomCheckDetails(viewMoreRequest);

			}else if ("FRAUGSTER".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getMoreFraugsterDetails(viewMoreRequest);
				
			}else if ("COUNTRYCHECK".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getMoreCountryCheckDetails(viewMoreRequest);
			}else if ("BLACKLIST_PAY_REF".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getMorePaymentReferenceCheckDetails(viewMoreRequest);
				
			} else if ("TRANSACTION_MONITORING".equalsIgnoreCase(viewMoreRequest.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getMoreIntuitionCheckDetails(viewMoreRequest); //AT-4306
				
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
	public ViewMoreResponse getFurtherPaymentOutDetails(PaymentOutViewMoreRequest request) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = null;
		try {
			if ("PAYMENT_IN".equalsIgnoreCase(request.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getViewMoreFurtherPaymentInDetails(request);
			    
			}else if ("PAYMENT_OUT".equalsIgnoreCase(request.getServiceType())){
				
				viewMoreResponse = iPaymentOutDBService.getFurtherPaymentOutDetails(request);
			    
			}
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
		return viewMoreResponse;
	}
	
	/**
	 * Gets the placeholders value.
	 *
	 * @param placeholders the placeholders
	 * @return the placeholders value
	 */
	private String getPlaceholdersValue(String placeholders) {
		if(null != placeholders && !placeholders.isEmpty())
			return placeholders;
		return " ";
	}

	@Override
	public boolean setPoiExistsFlagForPaymentOut(UserProfile user, Contact contact) throws CompliancePortalException {
		boolean poiExists = false;
		try {
			poiExists = iPaymentOutDBService.setPoiExistsFlagForPaymentOut(user,contact);
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST, e);
		}
		return poiExists;
	}
}