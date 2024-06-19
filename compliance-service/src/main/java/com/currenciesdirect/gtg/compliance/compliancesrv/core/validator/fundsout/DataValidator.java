package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.fundsout;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author bnt
 *
 */
public class DataValidator extends BaseMessageValidator{
	public static final String PAYMENT_FUNDSOUT_ID = "payment_fundsout_id";

	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	private static final Logger LOGGER = LoggerFactory.getLogger(DataValidator.class);
	
	private static final String PAYMENTOUT_EVENTTYPE = "PAYMENTOUT";

	@Autowired
	private FundsOutDBServiceImpl fundsOutDBService;
	
	/**
	 * @param message
	 * @param correlationID
	 * @return
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		FundsOutResponse response = new FundsOutResponse();		
		MessageExchange messageExchange =  message.getPayload().getGatewayMessageExchange();
		response.setOsrID(message.getHeaders().get(MessageContextHeaders.OSR_ID).toString());
		try {
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			if(serviceInterfaceType == ServiceInterfaceType.FUNDSOUT) {
				if (operation == OperationEnum.FUNDS_OUT) {
					FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
					fundsOutRequest.setVersion(1);
					FieldValidator fv = new FieldValidator();
					FundsOutRequest oldReuqest = (FundsOutRequest)fundsOutRequest.getAdditionalAttribute(Constants.OLD_REQUEST);
					
					Account acc = (Account)fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
					checkWhetherAccountExists(fv, acc);
					Contact contact = (Contact)fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
					checkWhetherContactExistsForAccount(fv, contact);
					
					Account account = (Account) oldReuqest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
					checkForCustTypeAndExistingFundsOut(fv, oldReuqest, account);				
					
					createBaseResponse(response, fv, 
							FundsInReasonCode.MISSINGINFO.getReasonCode(), Constants.MSG_MISSING_INFO,PAYMENTOUT_EVENTTYPE);
					setResponseForExistingFundsOut(response, fundsOutRequest, fv);
					messageExchange.setResponse(response);
				}else if (operation == OperationEnum.SANCTION_RESEND) {
					SanctionResendResponse sanctionResendResponse = new SanctionResendResponse();
					FundsOutSanctionResendRequest fundsOutSanctionResendRequest =  messageExchange.getRequest(FundsOutSanctionResendRequest.class);
					FieldValidator fv = validateFundsOutSanctionResend(fundsOutSanctionResendRequest);
					createBaseResponse(sanctionResendResponse, fv, 
							FundsInReasonCode.MISSINGINFO.getReasonCode(), Constants.MSG_MISSING_INFO,"");
					messageExchange.setResponse(sanctionResendResponse);
				}else if (operation == OperationEnum.UPDATE_OPI) {
					FundsOutUpdateRequest fRequest = (FundsOutUpdateRequest) messageExchange.getRequest();
					FundsOutRequest oldReuqest = (FundsOutRequest)fRequest.getAdditionalAttribute(Constants.OLD_REQUEST);
					Account acc = (Account)oldReuqest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
					validateUpdateDeleteFundsOut(response, fRequest, oldReuqest,acc);
					messageExchange.setResponse(response);
				}else if (operation == OperationEnum.DELETE_OPI) {
					FundsOutDeleteRequest fRequest = (FundsOutDeleteRequest) messageExchange.getRequest();
					FundsOutRequest oldReuqest = (FundsOutRequest)fRequest.getAdditionalAttribute(Constants.OLD_REQUEST);
					Account acc = (Account)oldReuqest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
					validateUpdateDeleteFundsOut(response, fRequest, oldReuqest,acc);
					messageExchange.setResponse(response);
				}else if (operation == OperationEnum.WHITELIST_UPDATE) {
					CustomCheckResponse customCheckResponse = new CustomCheckResponse(); 
					UdateWhiteListRequest fRequest = (UdateWhiteListRequest) messageExchange.getRequest();
					FundsOutRequest oldReuqest = (FundsOutRequest)fRequest.getAdditionalAttribute(Constants.OLD_REQUEST);
					validateWhitelistUpdateFundsOut(customCheckResponse, fRequest, oldReuqest);
					messageExchange.setResponse(customCheckResponse);
				}
			}
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setResponseReason(FundsOutReasonCode.MISSINGINFO);
			messageExchange.setResponse(response);
		}
		response.setCorrelationID(correlationID);
		
		return message;
	}

	/**
	 * Check whether account exists.
	 *
	 * @param fv the fv
	 * @param acc the acc
	 */
	private void checkWhetherAccountExists(FieldValidator fv, Account acc) {
		if(null == acc)
			fv.addError(Constants.ERR_INVALID_ACCOUNT, "trade_account_number");
	}

	/**
	 * Check whether contact exists for account.
	 *
	 * @param fv the fv
	 * @param contact the contact
	 */
	private void checkWhetherContactExistsForAccount(FieldValidator fv, Contact contact) {
		if(null == contact)
			fv.addError(Constants.ERR_INVALID_COTACT, "trade_contact_id");
	}

	/**
	 * Check for cust type and existing funds out.
	 *
	 * @param fv the fv
	 * @param oldReuqest the old reuqest
	 * @param account the account
	 */
	private void checkForCustTypeAndExistingFundsOut(FieldValidator fv, FundsOutRequest oldReuqest, Account account) {
		if (null != oldReuqest.getCustType() && null != account && !account.getCustType().equals(oldReuqest.getCustType())){
			fv.addError(Constants.ERR_CUST_TYPE_INVALID, "cust_type");
		}
		if(null != oldReuqest.getFundsOutId()){
			fv.addError(Constants.ERR_PAYMENT_EXISTS, PAYMENT_FUNDSOUT_ID);
		}
	}

	/**
	 * Sets the response for existing funds out.
	 *
	 * @param response the response
	 * @param fundsOutRequest the funds out request
	 * @param fv the fv
	 */
	private void setResponseForExistingFundsOut(FundsOutResponse response, FundsOutRequest fundsOutRequest,
			FieldValidator fv) {
		if(fv != null && !fv.isFailed())
			updateResponseForPaymentDone(response, fundsOutRequest);
	}

	private void validateWhitelistUpdateFundsOut(CustomCheckResponse response, UdateWhiteListRequest fRequest,
			FundsOutRequest oldReuqest) {
		FieldValidator fv = new FieldValidator();
		
		if(null!=oldReuqest.getFundsOutId()){
			fRequest.addAttribute(Constants.OLD_REQUEST, oldReuqest);
			fRequest.setPaymentFundsOutId(oldReuqest.getFundsOutId());
			createBaseResponse(response, fv, 
					null, null,"");
		}else{
			fv.addError(Constants.ERR_PAYMENT_NOT_EXISTS, PAYMENT_FUNDSOUT_ID);
			createBaseResponse(response, fv, 
					null, null,"");
		}
	}
	
	private void validateUpdateDeleteFundsOut(FundsOutResponse response, FundsOutBaseRequest fRequest,
			FundsOutRequest oldReuqest, Account acc) {
		FieldValidator fv = new FieldValidator();
		checkWhetherAccountExists(fv, acc);
		if(null!=oldReuqest.getFundsOutId()){
			fRequest.addAttribute(Constants.OLD_REQUEST, oldReuqest);
			fRequest.setFundsOutId(oldReuqest.getFundsOutId());
			createBaseResponse(response, fv, 
					null, null,PAYMENTOUT_EVENTTYPE);
		}else{
			fv.addError(Constants.ERR_PAYMENT_NOT_EXISTS, PAYMENT_FUNDSOUT_ID);
			createBaseResponse(response, fv, 
					null, null,PAYMENTOUT_EVENTTYPE);
		}
	}

	
	private FieldValidator validateFundsOutSanctionResend(FundsOutSanctionResendRequest request) throws ComplianceException{
		FieldValidator fv = new FieldValidator();
		if(null == fundsOutDBService.doesFundsOutExists(request.getTradePaymentId())){
			fv.addError(Constants.ERR_PAYMENT_NOT_EXISTS, "tradePaymentId");
		}
		return fv;
	}
	
	/**
	 * sometimes Aurora fails to update Compliance response, in those cases Aurora sends the payment request again
	 * instead of sending INV_REquest, send compliance status of payment again
	 * so that Aurora will update status on their side
	 * 
	 *  if payment is on HOLD, set reason desc to FAIL else PASS
	 *
	 * @param response the response
	 * @param fundsInCreateRequest the funds in create request
	 */
	private void updateResponseForPaymentDone(FundsOutResponse response,FundsOutRequest fundsOutRequest){

		FundsOutBaseRequest oldReuqest = (FundsOutBaseRequest) fundsOutRequest
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		String oldStatus = (String)oldReuqest.getAdditionalAttribute("status");
		if("PAYMENTOUT [payment_fundsout_id Payment ID Already Exists]".equals(response.getErrorDescription()) 
				&& "701".equals(response.getResponseCode())){
			
			response.setDecision(BaseResponse.DECISION.FAIL);
			if(null != oldStatus && 
					!FundsInComplianceStatus.CLEAR.getFundsInStatusAsString().equalsIgnoreCase(oldStatus)){
				response.setResponseCode(FundsOutReasonCode.DUPLICATE_PAYMENT_FAIL.getFundsOutReasonCode());
				response.setResponseDescription(FundsOutReasonCode.DUPLICATE_PAYMENT_FAIL.getFundsOutReasonDescription());
			}else{
				response.setResponseCode(FundsOutReasonCode.DUPLICATE_PAYMENT_PASS.getFundsOutReasonCode());
				response.setResponseDescription(FundsOutReasonCode.DUPLICATE_PAYMENT_PASS.getFundsOutReasonDescription());
			}
			response.setOrgCode(fundsOutRequest.getOrgCode());
			response.setTradeContractNumber(fundsOutRequest.getTrade().getContractNumber());
			response.setStatus(oldStatus);
			response.setErrorCode(null);
			response.setErrorDescription(null);
		}
		
	}
}