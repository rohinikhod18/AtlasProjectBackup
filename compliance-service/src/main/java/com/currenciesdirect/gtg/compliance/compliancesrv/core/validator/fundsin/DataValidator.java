/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.fundsin;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author manish
 *
 */
public class DataValidator extends BaseMessageValidator{

	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	private static final Logger LOGGER = LoggerFactory.getLogger(DataValidator.class);
	
	@Autowired
	private FundsInDBServiceImpl fundsInDBService;

	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		FundsInCreateResponse response = null;
		MessageExchange messageExchange = null;
		try {
			response = new FundsInCreateResponse();
			messageExchange = message.getPayload().getGatewayMessageExchange();
			response.setOsrID(messageExchange.getRequest().getOsrId());
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.FUNDS_IN) {
				FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
				response.setTradePaymentID(fundsInRequest.getTrade().getPaymentFundsInId());
				fundsInRequest.setVersion(1);
				createBaseResponse(response, validateMandatoryFields(fundsInRequest), 
							FundsInReasonCode.MISSINGINFO.getReasonCode(), Constants.MSG_MISSING_INFO,"PAYMENTIN");
				updateResponseForPaymentDone(response, fundsInRequest);
				messageExchange.setResponse(response);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN 
					&& operation == OperationEnum.DELETE_OPI) {

				FundsInDeleteRequest fRequest = (FundsInDeleteRequest) messageExchange.getRequest();
				FieldValidator fv = validateFundsInDelete(fRequest);
				FundsInDeleteResponse deleteResponse = new FundsInDeleteResponse();
				createBaseResponse(deleteResponse, fv, 
						FundsInReasonCode.MISSINGINFO.getReasonCode(), Constants.MSG_MISSING_INFO,"");
				deleteResponse.setOsrID(fRequest.getOsrId());
				messageExchange.setResponse(deleteResponse);
			
				
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN
					&& operation == OperationEnum.WHITELIST_UPDATE) {
				CustomCheckResponse customCheckResponse=new CustomCheckResponse();
				UdateWhiteListRequest fRequest = (UdateWhiteListRequest) messageExchange.getRequest();
				FundsInCreateRequest oldReuqest = (FundsInCreateRequest) fRequest.getAdditionalAttribute("oldRequest");
				checkFundsInExistsAndCreateResponse(customCheckResponse, fRequest, oldReuqest);
				messageExchange.setResponse(customCheckResponse);

			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN
					&& operation == OperationEnum.SANCTION_RESEND) {
				SanctionResendResponse resendResponse = new SanctionResendResponse();
				FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
				response.setTradePaymentID(fundsInRequest.getTrade().getPaymentFundsInId());
				FieldValidator fv = new FieldValidator();
				checkIsSanctionEligible(fundsInRequest, fv);
				createBaseResponse(resendResponse, fv, FundsInReasonCode.THIRDPARTY_SANCTION_NOT_ELIGIBLE.getReasonCode(),
						FundsInReasonCode.THIRDPARTY_SANCTION_NOT_ELIGIBLE.getReasonDescription(),"");
				messageExchange.setResponse(resendResponse);
			}
			
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			setResponseOnError(response);
		}
		setCorrelationIdInResponse(correlationID, response);
		
		return message;

	}
	

	/**
	 * Sets the correlation id in response.
	 *
	 * @param correlationID the correlation ID
	 * @param response the response
	 */
	private void setCorrelationIdInResponse(UUID correlationID, FundsInCreateResponse response) {
		if(response != null)
			response.setCorrelationID(correlationID);
	}

	/**
	 * Sets the response on error.
	 *
	 * @param response the new response on error
	 */
	private void setResponseOnError(FundsInCreateResponse response) {
		if(response != null)
		{
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setResponseReason(FundsInReasonCode.MISSINGINFO);
		}
	}

	/**
	 * Check funds in exists and create response.
	 *
	 * @param customCheckResponse the custom check response
	 * @param fRequest the f request
	 * @param oldReuqest the old reuqest
	 */
	private void checkFundsInExistsAndCreateResponse(CustomCheckResponse customCheckResponse,
			UdateWhiteListRequest fRequest, FundsInCreateRequest oldReuqest) {
		if (null != oldReuqest.getFundsInId()) {
			fRequest.addAttribute("oldRequest", oldReuqest);
			createBaseResponse(customCheckResponse, null, null, null,"");
		}
	}

	/**
	 * Check is sanction eligible.
	 *
	 * @param fundsInRequest the funds in request
	 * @param fv the fv
	 */
	private void checkIsSanctionEligible(FundsInCreateRequest fundsInRequest, FieldValidator fv) {
		if (!fundsInRequest.isSanctionEligible()) {
			fv.addError("Not sanction eligible", "paymentFundsInId");
		}
	}

	private FieldValidator validateMandatoryFields(FundsInCreateRequest fundsInCreateRequest) {
		FieldValidator fv = new FieldValidator();
		FundsInCreateRequest oldReuqest = (FundsInCreateRequest) fundsInCreateRequest
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		if (null != oldReuqest && null != oldReuqest.getFundsInId()) {
			fv.addError(Constants.ERR_PAYMENT_EXISTS, "payment_fundsIN_Id");
		}
		Integer orgId = fundsInCreateRequest.getOrgId();
		if (null == orgId)
			fv.addError(Constants.ERR_INVALID_ORG, "org_code");
		Account acc = (Account) fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		
		if (null != oldReuqest && null != oldReuqest.getCustType() && null != acc && !acc.getCustType().equals(oldReuqest.getCustType())){
			fv.addError(Constants.ERR_CUST_TYPE_INVALID, "cust_type");
		}
		if (null == acc)
			fv.addError(Constants.ERR_INVALID_ACCOUNT, "trade_account_number");
		Contact contact = (Contact) fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
		if (null == contact)
			fv.addError(Constants.ERR_INVALID_COTACT, "trade_contact_id");
		return fv;
	}
	
	private FieldValidator validateFundsInDelete(FundsInDeleteRequest request) throws ComplianceException{
		FieldValidator fv = new FieldValidator();
		if(null == fundsInDBService.doesFundsInExists(request.getPaymentFundsInId())){
			fv.addError(Constants.ERR_PAYMENT_NOT_EXISTS, "payment_fundsin_id");
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
	private void updateResponseForPaymentDone(FundsInCreateResponse response,FundsInCreateRequest fundsInCreateRequest){

		FundsInCreateRequest oldReuqest = (FundsInCreateRequest) fundsInCreateRequest
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		String oldStatus = (String)oldReuqest.getAdditionalAttribute("status");
		if(null != oldReuqest.getFundsInId() &&
				"PAYMENTIN [payment_fundsIN_Id Payment ID Already Exists]".equals(response.getErrorDescription()) 
				&& "701".equals(response.getResponseCode())){
			
			response.setDecision(BaseResponse.DECISION.FAIL);
			if(null != oldStatus && 
					!FundsInComplianceStatus.CLEAR.getFundsInStatusAsString().equalsIgnoreCase(oldStatus)){
				response.setResponseCode("728");
				response.setResponseDescription("FAIL");	
			}else{
				response.setResponseCode("728");
				response.setResponseDescription("PASS");
			}
			response.setOrgCode(fundsInCreateRequest.getOrgCode());
			response.setTradeContractNumber(fundsInCreateRequest.getTrade().getContractNumber());
			response.setStatus(oldStatus);
			response.setErrorCode(null);
			response.setErrorDescription(null);
		}
		
	}


}
