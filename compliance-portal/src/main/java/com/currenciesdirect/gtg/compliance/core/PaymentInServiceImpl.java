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

import com.currenciesdirect.gtg.compliance.commons.domain.ITokenizer;
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
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.Document;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateRequest;
import com.currenciesdirect.gtg.compliance.docuploadport.UploadDocumentPort;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

@Component("paymentInServiceImpl")
public class PaymentInServiceImpl implements IPaymentInService {
	
	@Autowired
	@Qualifier("commHubServiceImpl")
	private ICommHubServiceImpl iCommHubServiceImpl;

	@Autowired
	@Qualifier("payInDBServiceImpl")
	private IPaymentInDBService iPaymentInDBService;

	@Autowired
	@Qualifier("paymentInUpdateTransformer")
	private ITransform<PaymentInUpdateDBDto, PaymentInUpdateRequest> paymentInUpdateTransformer;

	private Logger log = LoggerFactory.getLogger(PaymentInServiceImpl.class);
	
	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "Content-Type";
	
	/** The Constant AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";
	
	/** The i tokenizer. */
	@Autowired
	@Qualifier("tokenizer")
	private ITokenizer iTokenizer;
	

	@Override
	public PaymentInQueueDto getPaymentInQueueWithCriteria(PaymentInSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iPaymentInDBService.getPaymentInQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}

	}

	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

	@Override
	public PaymentInDetailsDto getPaymentInDetails(Integer paymentInId, String custType,
			PaymentInSearchCriteria searchCriteria) throws CompliancePortalException {
		try {
			PaymentInDetailsDto paymentInDetailDto = iPaymentInDBService.getPaymentInDetails(paymentInId, custType,
					searchCriteria);
			addDocuments(paymentInDetailDto);
			if (searchCriteria != null && searchCriteria.getPage() != null) {
				paymentInDetailDto.setCurrentRecord(searchCriteria.getPage().getCurrentRecord());
				paymentInDetailDto.setTotalRecords(searchCriteria.getPage().getTotalRecords());
			}
			return paymentInDetailDto;
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}

	}

	private void addDocuments(PaymentInDetailsDto paymentInDetailsDto) throws CompliancePortalException {
		if (paymentInDetailsDto.getAccount() != null && paymentInDetailsDto.getCurrentContact() != null) {
			List<Document> documents = UploadDocumentPort.getAttachedDocument(
					paymentInDetailsDto.getCurrentContact().getCrmAccountId(),
					paymentInDetailsDto.getCurrentContact().getCrmContactId(),
					paymentInDetailsDto.getAccount().getOrgCode(), Constants.SOURCE_SALESFORCE);
			paymentInDetailsDto.setDocuments(UploadDocumentPort.updateDocumentDateFormat(documents));
		}

	}

	@Override
	public ActivityLogs updatePaymentIn(PaymentInUpdateRequest paymentInUpdateRequest)
			throws CompliancePortalException {
		try {
			PaymentInUpdateDBDto paymentInUpdateDBDto = paymentInUpdateTransformer.transform(paymentInUpdateRequest);
			if ("CLEAR".equalsIgnoreCase(paymentInUpdateRequest.getUpdatedPaymentInStatus())) {
				CustomUpdateResponse customUpdateResponse;
				customUpdateResponse = customUpdateService(paymentInUpdateRequest.getOrgCode(),
						paymentInUpdateRequest.getPaymentinId(),
						JsonConverterUtil.convertToJsonWithNull(paymentInUpdateRequest.getUser()),
						paymentInUpdateRequest.getCountryRiskLevel());
				if ("PASS".equalsIgnoreCase(customUpdateResponse.getOverallStatus())) {
					return iPaymentInDBService.updatePaymentIn(paymentInUpdateDBDto);
				}
			} else {
				ActivityLogs activityLogs = iPaymentInDBService.updatePaymentIn(paymentInUpdateDBDto);
				
				//Add Code comment for jira AT-4519
				//sendEmailOnRejectOrSeizeByCommhub(paymentInUpdateRequest);
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

	private CustomUpdateResponse customUpdateService(String orgCode, Integer paymentInId, String userProfile, String countryRiskLevel)
			throws CompliancePortalException {
		Integer paymentOutId = null;
		CustomUpdateRequest customUpdateRequest = getCustomUpdateRequest(orgCode, paymentInId, paymentOutId, countryRiskLevel);
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		ArrayList<Object> list = new ArrayList<>();
		list.add(userProfile);
		headers.put("user", list);
		String baseUrl = System.getProperty("baseComplianceServiceUrl");
		return clientPool.sendRequest(
				baseUrl + "/compliance-service/services-internal/repeatcheck/updateWhiteListDatafundsIn", "POST",
				customUpdateRequest, CustomUpdateResponse.class, headers, MediaType.APPLICATION_JSON_TYPE);
	}

	private CustomUpdateRequest getCustomUpdateRequest(String orgCode, Integer paymentInId, Integer paymentOutId, String countryRiskLevel) {

		CustomUpdateRequest customUpdateRequest = new CustomUpdateRequest();
		customUpdateRequest.setOrgCode(orgCode);
		customUpdateRequest.setPaymentOutId(paymentOutId);
		customUpdateRequest.setPaymentInId(paymentInId);
		customUpdateRequest.setCountryRiskLevel(countryRiskLevel);
		return customUpdateRequest;
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		try {
			if (request == null) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			} else {
				return iPaymentInDBService.getActivityLogs(request);
			}

		} catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}

	@Override
	public ViewMoreResponse viewMoreDetails(PaymentInViewMoreRequest viewMoreRequest) throws CompliancePortalException {

		ViewMoreResponse viewMoreResponse = null;
		try {

			if ("SANCTION".equalsIgnoreCase(viewMoreRequest.getServiceType())) {

				viewMoreResponse = iPaymentInDBService.getMoreSanctionDetails(viewMoreRequest);

			} else if ("VELOCITYCHECK".equalsIgnoreCase(viewMoreRequest.getServiceType())) {

				viewMoreResponse = iPaymentInDBService.getMoreCustomCheckDetails(viewMoreRequest);

			} else if ("FRAUGSTER".equalsIgnoreCase(viewMoreRequest.getServiceType())) {

				viewMoreResponse = iPaymentInDBService.getMoreFraugsterDetails(viewMoreRequest);

			} else if ("TRANSACTION_MONITORING".equalsIgnoreCase(viewMoreRequest.getServiceType())) {

				viewMoreResponse = iPaymentInDBService.getMoreIntuitionDetails(viewMoreRequest); //AT-4306

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

	
	/**
	 * Send email on reject or seize by commhub.
	 *
	 * @param paymentInUpdateRequest the payment in update request
	 */
	
	//Code add for AT-1999 Commhub API
	private void sendEmailOnRejectOrSeizeByCommhub(PaymentInUpdateRequest paymentInUpdateRequest) {
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if (Constants.REJECT.equalsIgnoreCase(paymentInUpdateRequest.getUpdatedPaymentInStatus())
					|| Constants.SEIZE.equalsIgnoreCase(paymentInUpdateRequest.getUpdatedPaymentInStatus())) {
				SendEmailRequest emailrequest = new SendEmailRequest();
				EmailHeader header =  new EmailHeader();
				header.setOrgCode(paymentInUpdateRequest.getOrgCode());
				header.setLegalEntity(paymentInUpdateRequest.getLegalEntity());
				header.setCustomerType(paymentInUpdateRequest.getCustType());
				header.setCustNumber(paymentInUpdateRequest.getClientNumber());
				String[] notification = {"Email"};
	 			header.setNotificationType(notification);
				header.setEvent(Constants.PAYMENT_IN_REJECT_EVENT);
				header.setLocale(Constants.LOCALE_EN);
				header.setSourceSystem("Atlas");
				header.setRetryTimeout("60000");
				header.setOsrId(UUID.randomUUID().toString());
				
				EmailPayload payload = new EmailPayload();
				payload.setEmailId(System.getProperty(paymentInUpdateRequest.getOrgCode().replaceAll("\\s+", "").toLowerCase()+Constants.EAMIL_SENDTO));
				payload.setSubject("Payment In Rejected/Seized");
					Map<String,String> attributes = new HashMap<>();
					attributes.put("#updated_payment_status", getPlaceholdersValue(paymentInUpdateRequest.getUpdatedPaymentInStatus()));
					attributes.put("#customer_number", getPlaceholdersValue(paymentInUpdateRequest.getClientNumber()));
					attributes.put("#trade_contractId", getPlaceholdersValue(paymentInUpdateRequest.getTradeContractNumber()));
					attributes.put("#debtor_amount", getPlaceholdersValue(paymentInUpdateRequest.getDebtorAmount()));
					attributes.put("#currency", getPlaceholdersValue(paymentInUpdateRequest.getSellCurrency()));
					attributes.put("#payment_status", getPlaceholdersValue(paymentInUpdateRequest.getPrePaymentInStatus()));
					attributes.put("#today_date", DateTimeFormatter.dateTimeFormatter(timestamp));
					attributes.put("#username", getPlaceholdersValue(paymentInUpdateRequest.getUserName()));
					attributes.put("#status_reason", getPlaceholdersValue(paymentInUpdateRequest.getStatusReason()));
					attributes.put("#comment", getPlaceholdersValue(paymentInUpdateRequest.getComment()));
					attributes.put("#payment_method", getPlaceholdersValue(paymentInUpdateRequest.getPaymentMethod()));
				payload.setPlaceHolder(attributes);
					
				emailrequest.setHeader(header);
				emailrequest.setPayload(payload);

				iCommHubServiceImpl.sendEmail(emailrequest,true);
			}
		} catch(Exception e) {
			logError(e);
		}
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
	public boolean setPoiExistsFlagForPaymentIn(UserProfile user, Contact contact) throws CompliancePortalException {
		boolean poiExists = false;
		try {
			poiExists = iPaymentInDBService.setPoiExistsFlagForPaymentIn(user,contact);
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST, e);
		}
		return poiExists;
	}
}
