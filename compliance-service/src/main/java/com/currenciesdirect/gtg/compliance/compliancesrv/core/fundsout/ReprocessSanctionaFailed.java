package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.ReprocessFailedSanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.ReprocessFailedSanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.ValidationErrors;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.BroadCastQueueDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class ReprocessSanctionaFailed.
 */
public class ReprocessSanctionaFailed {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReprocessSanctionaFailed.class);
	
	/** The funds out DB service. */
	@Autowired
	@Qualifier("fundsOutDBService")
	private FundsOutDBServiceImpl fundsOutDBService;
	
	/** The broad cast DB service. */
	@Autowired
	@Qualifier("reg.BroadCastQueueDBServiceImpl")
	private BroadCastQueueDBServiceImpl broadCastDBService;
	
	/** The http client. */
	private HttpClientPool httpClient = new HttpClientPool();
	
	 /** The Constant STATUSCODE_HEADER. */
 	private static final String STATUSCODE_HEADER = "http_statusCode";

	
	
	/**
	 * Reprocess sanction failed.
	 *
	 * @param msg the msg
	 * @return the message
	 */
	public Message<ReprocessFailedSanctionResponse> reprocessSanctionFailed(Message<ReprocessFailedSanctionRequest> msg){
		
		ReprocessFailedSanctionRequest request = msg.getPayload();
		ReprocessFailedSanctionResponse response = new ReprocessFailedSanctionResponse();
		validateRequest(request, response);
		if(!StringUtils.isNullOrTrimEmpty(response.getErrorCode())){
			return MessageBuilder.withPayload(response)
		            .copyHeadersIfAbsent(msg.getHeaders())
		            .setHeader(STATUSCODE_HEADER, HttpStatus.OK).build();
		}
		Long repeatSanctionCount = 0L;
		Long clearPaymentCount = 0L;
		Set<FundsOutSanctionResendRequest> listToProcess = getListToprocess(request);
		if(null != listToProcess && !listToProcess.isEmpty()){
			for(FundsOutSanctionResendRequest req : listToProcess){
				getOtherEntityStatus(req);
				if(!isSanctionPass(req)){
					repeatSanctionChck(req);
					repeatSanctionCount++;
				}
			}

			for(FundsOutSanctionResendRequest req : listToProcess){
				if(ServiceStatus.PASS.getServiceStatusAsString().equals( getSanctionStatus(req))){
					clearPaymentOut(req);
					clearPaymentCount++;
				}
			}
			
		}
		
		response.setClearPaymentCount(clearPaymentCount);
		response.setRepeatSanctionCount(repeatSanctionCount);
		return MessageBuilder.withPayload(response)
	            .copyHeadersIfAbsent(msg.getHeaders())
	            .setHeader(STATUSCODE_HEADER, HttpStatus.OK)
	.build();
	}
	
	/**
	 * Validate request.
	 *
	 * @param request the request
	 * @param response the response
	 * @return true, if successful
	 */
	private boolean validateRequest(ReprocessFailedSanctionRequest request,ReprocessFailedSanctionResponse response) {
		boolean isValid = true;
		FieldValidator fv = new FieldValidator();
		if(StringUtils.isNullOrTrimEmpty(request.getStartDate()) || StringUtils.isNullOrTrimEmpty(request.getEndDate()) ){
			isValid = false;
		}
		if(!StringUtils.isNullOrTrimEmpty(request.getStartDate()) && !StringUtils.isNullOrTrimEmpty(request.getEndDate())){
			fv.getErrors().add(new ValidationErrors("Dates must be in 'yyyy-MM-dd HH:mm:ss' format",new ArrayList<String>()));
			
			if(!validateDate(request.getStartDate()) || !validateDate(request.getEndDate())){
				isValid = false;
			} 
		} 
		
		if(!isValid){
			response.setStatus("INV_REQ");
			response.setErrorCode(FundsOutReasonCode.MISSINGINFO.getFundsOutReasonCode());
			if(fv.getErrors() == null || fv.getErrors().isEmpty()){
				response.setErrorDescription(FundsOutReasonCode.MISSINGINFO.getFundsOutReasonDescription());
			} else {
				response.setErrorDescription(fv.getErrors().toString());
			}
			
		}
		return isValid;
	}

	/**
	 * Checks if is sanction pass.
	 *
	 * @param req the req
	 * @return true, if is sanction pass
	 */
	private boolean isSanctionPass(FundsOutSanctionResendRequest req){
		if(EntityEnum.BENEFICIARY.getEntityTypeAsString().equals(req.getEntityType()) ){
			return ServiceStatus.PASS.getServiceStatusAsString().equals(req.getBeneficiaryStatus());
		}
		if(EntityEnum.BANK.getEntityTypeAsString().equals(req.getEntityType()) ){
			return ServiceStatus.PASS.getServiceStatusAsString().equals(req.getBankStatus());
		}
		if(EntityEnum.CONTACT.getEntityTypeAsString().equals(req.getEntityType()) ){
			return ServiceStatus.PASS.getServiceStatusAsString().equals(req.getContactStatus());
		}
		return true;
	}
	
	
	/**
	 * Gets the list toprocess.
	 *
	 * @param request the request
	 * @return the list toprocess
	 */
	private Set <FundsOutSanctionResendRequest> getListToprocess(ReprocessFailedSanctionRequest request){
		Set<FundsOutSanctionResendRequest> listToProcess = null;
		try {
			listToProcess = fundsOutDBService.getSanctionFailedRecords(request);
		} catch (ComplianceException e) {
			LOGGER.error(Constants.ERROR,e);
		}
		
		return listToProcess;
	}
	
	
	/**
	 * Repeat sanction chck.
	 *
	 * @param req the req
	 */
	private void repeatSanctionChck(FundsOutSanctionResendRequest req){
		try{
			httpClient.sendRequest(System.getProperty("baseComplianceServiceUrl")+ "/compliance-service/services-internal/repeatcheck/resendSanctionFailed", "POST", req, SanctionResendResponse.class, null);
		}catch(Exception ex){
			LOGGER.error(Constants.ERROR,ex);
		}
	}
	
	/**
	 * Gets the other entity status.
	 *
	 * @param req the req
	 * @return the other entity status
	 */
	private void getOtherEntityStatus(FundsOutSanctionResendRequest req){
		
		try {
			fundsOutDBService.getOtherEntityStatus(req);
		} catch (ComplianceException e) {
			LOGGER.error(Constants.ERROR,e);
		}
		
	}
	
	/**
	 * Gets the sanction status.
	 *
	 * @param req the req
	 * @return the sanction status
	 */
	private String getSanctionStatus(FundsOutSanctionResendRequest req){
		String status = "FAIL";
		
		try {
			status = fundsOutDBService.getSanctionStatus(req.getPaymentOutId());
		} catch (ComplianceException e) {
			LOGGER.error(Constants.ERROR,e);
		}
		
		return status ;
	}
	
	/**
	 * Clear payment out.
	 *
	 * @param req the req
	 */
	private void clearPaymentOut(FundsOutSanctionResendRequest req){
		try{
			fundsOutDBService.updatePaymentsOutComplianceStatus(req.getPaymentOutId(), 1, FundsOutComplianceStatus.CLEAR);
			broadCastDBService.saveIntoBroadcastQueue(getBroadCastDBObject(req.getAccountId(),req.getPaymentOutId(),
					getPaymentOutResponse(FundsOutComplianceStatus.CLEAR, req.getTradeContractNumber(), req.getTradePaymentId().toString(), 
							req.getOrgCode()), req.getOrgCode(), req.getContactID()));
		}catch(Exception ex){
			LOGGER.error(Constants.ERROR,ex);
		}
	}
	
	
	/**
	 * Gets the payment out response.
	 *
	 * @param paymentOutComplianceStatus the payment out compliance status
	 * @param tradeContractnumber the trade contractnumber
	 * @param tradePaymentid the trade paymentid
	 * @param orgCode the org code
	 * @return the payment out response
	 */
	private String getPaymentOutResponse(FundsOutComplianceStatus paymentOutComplianceStatus, String tradeContractnumber,
			String tradePaymentid, String orgCode) {

		FundsOutResponse paymentOutResponse = new FundsOutResponse();
		paymentOutResponse.setOrgCode(orgCode);
		
		paymentOutResponse.setTradeContractNumber(tradeContractnumber);
		paymentOutResponse.setTradePaymentID(Integer.parseInt(tradePaymentid));
		paymentOutResponse.setStatus(paymentOutComplianceStatus.name());
		paymentOutResponse.setResponseCode(FundsOutReasonCode.PASS.getFundsOutReasonCode());
		paymentOutResponse.setResponseDescription(FundsOutReasonCode.PASS.getFundsOutReasonDescription());
		return JsonConverterUtil.convertToJsonWithNull(paymentOutResponse);
	}
	
	/**
	 * Gets the broad cast DB object.
	 *
	 * @param accountId the account id
	 * @param paymentOutId the payment out id
	 * @param statusJson the status json
	 * @param orgCode the org code
	 * @param contactId the contact id
	 * @return the broad cast DB object
	 */
	private BroadcastEventToDB getBroadCastDBObject(Integer accountId, Integer paymentOutId, 
			String statusJson, String orgCode,Integer contactId) {
		BroadcastEventToDB db = new BroadcastEventToDB();
		db.setAccountId(accountId);
		db.setContactId(contactId);
		db.setPaymentOutId(paymentOutId);
		db.setCreatedBy(1);
		db.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliverOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveryStatus(BroadCastStatusEnum.NEW.getBroadCastStatusAsString());
		db.setEntityType(MQEntityTypeEnum.PAYMENTOUT);
		db.setOrgCode(orgCode);
		db.setStatusJson(statusJson);
		return db;
	}
	
   /**
    * Validate date.
    *
    * @param dateString the date string
    * @return true, if successful
    */
   private	boolean validateDate(String dateString){
	   boolean result=true;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
		} catch (ParseException e) {
			result= false;
		}
		String convertedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		if(convertedDate == null || !convertedDate.equals(dateString)){
			result= false;
		}
		return result;
	}
}
