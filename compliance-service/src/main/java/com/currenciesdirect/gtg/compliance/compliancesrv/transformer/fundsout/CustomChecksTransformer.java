package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.EuPoiCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class CustomChecksTransformer.
 */
public class CustomChecksTransformer extends AbstractTransformer{
	private static final Logger LOG = LoggerFactory.getLogger(CustomChecksTransformer.class);

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformRequest(org.springframework.messaging.Message)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			FundsOutBaseRequest fundsOutRequest=(FundsOutBaseRequest) messageExchange.getRequest();

			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute("eventId");
			
			Account account = (Account) fundsOutRequest.getAdditionalAttribute("account");
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute("contact");//Add for AT-3349
			
			CustomChecksRequest serviceRequest =  new CustomChecksRequest();
			serviceRequest.setOrgCode(fundsOutRequest.getOrgCode());
			serviceRequest.setPaymentTransId(fundsOutRequest.getFundsOutId());
			serviceRequest.setAccId(account.getId());
			// ??  is below correct?
			serviceRequest.setBuyAmountOnAccount(account.getMaxTransactionAmount());
			serviceRequest.setBuyCurrencyOnAccount(account.getBuyingCurrency());
			serviceRequest.setReasonsOfTransferOnAccount(account.getPurposeOfTran());
			serviceRequest.setSellAmountOnAccount(account.getMaxTransactionAmount());
			serviceRequest.setSellCurrencyOnAccount(account.getSellingCurrency());
			serviceRequest.setZeroFundsClearAfterLEDate((boolean) fundsOutRequest.getAdditionalAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE));//Add for AT-3349
			serviceRequest.setPoiExistsFlag(contact.getPoiExists()); //Add for AT-3349
			
			//ADD for AT-3161
			if(account.getCustType().equals("PFX")){
				//changes for AT-3243
				serviceRequest.setfPScoreUpdateOn((LinkedHashMap<String, String>) fundsOutRequest.getAdditionalAttribute("FraugsterPreviousScore&UpdatedOn"));
			}
			
			Integer beneId = null;
			ServiceProviderEnum provider =  ServiceProviderEnum.CUSTOM_CHECKS_SERVICE;
			if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
					&& operation == OperationEnum.FUNDS_OUT) {
				FundsOutRequest details = (FundsOutRequest)  messageExchange.getRequest();
				beneId =  details.getBeneficiary().getBeneficiaryId();
				details.setType("FUNDS_OUT_ADD");
				serviceRequest.setESDocument(details);
			}else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
					&& operation == OperationEnum.UPDATE_OPI) {
				FundsOutRequest oldReuqest = (FundsOutRequest)fundsOutRequest.getAdditionalAttribute("oldRequest");
				oldReuqest.setType("FUNDS_OUT_UPDATE");
				beneId =  oldReuqest.getBeneficiary().getBeneficiaryId();
				serviceRequest.setESDocument(oldReuqest);
				fundsOutRequest.setVersion(oldReuqest.getVersion()+1);
			}else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
					&& operation == OperationEnum.DELETE_OPI) {
				FundsOutRequest oldReuqest = (FundsOutRequest)fundsOutRequest.getAdditionalAttribute("oldRequest");
				oldReuqest.setType("FUNDS_OUT_DELETE");
				beneId =  oldReuqest.getBeneficiary().getBeneficiaryId();
				serviceRequest.setESDocument(oldReuqest);
				fundsOutRequest.setVersion(oldReuqest.getVersion()+1);
			}else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
					&& operation == OperationEnum.CUSTOMCHECK_RESEND) {
				FundsOutRequest details = (FundsOutRequest)  messageExchange.getRequest();
				details.setType("FUNDS_OUT_REPEAT");
				beneId =  details.getBeneficiary().getBeneficiaryId();
				serviceRequest.setESDocument(details);
				provider =  ServiceProviderEnum.REPEAT_CUSTOM_CHECKS_SERVICE;
			}
			
			CustomCheckResponse serviceResponse = new CustomCheckResponse();
			MessageExchange ccExchange = createMessageExchange(serviceRequest, serviceResponse, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			setCustomCheckResponseStatus(token, fundsOutRequest, eventId, ccExchange, beneId, provider,
					serviceResponse);

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in CustomChecks transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * @param token
	 * @param fundsOutRequest
	 * @param eventId
	 * @param eventServiceLogs
	 * @param beneId
	 * @param provider
	 * @param serviceResponse
	 */
	private void setCustomCheckResponseStatus(UserProfile token, FundsOutBaseRequest fundsOutRequest, Integer eventId,
			MessageExchange ccExchange, Integer beneId, ServiceProviderEnum provider,
			CustomCheckResponse serviceResponse) {
		if(fundsOutRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)){
			serviceResponse.setOverallStatus(ServiceStatus.NOT_PERFORMED.name());
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, 
					provider, beneId, fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(), 
					serviceResponse, serviceResponse));
		}else {
			//Add EuPoiCheckResponse for AT-3349
			VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
			WhiteListCheckResponse whiteListCheckResponse = new WhiteListCheckResponse();
			EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
			velocityCheckResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			whiteListCheckResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			euPoiCheckResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			serviceResponse.setVelocityCheck(velocityCheckResponse);
			serviceResponse.setWhiteListCheck(whiteListCheckResponse);
			serviceResponse.setEuPoiCheck(euPoiCheckResponse);
			serviceResponse.setFraudPredictStatus(ServiceStatus.NOT_REQUIRED.name());
			serviceResponse.setOverallStatus(ServiceStatus.NOT_REQUIRED.name());
			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, 
					provider, beneId, fundsOutRequest.getVersion(), EntityEnum.BENEFICIARY.name(), token.getUserID(), 
					serviceResponse, serviceResponse, ServiceStatus.NOT_REQUIRED));
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		FundsOutRequest fReuqest = null;
		MessageExchange exchange = null;
		EventServiceLog log = null;
		try {
			exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			CustomChecksRequest serviceRequest = exchange.getRequest(CustomChecksRequest.class);
			fReuqest = (FundsOutRequest) serviceRequest.getESDocument();

			CustomCheckResponse serviceResponse = (CustomCheckResponse) exchange.getResponse();
			log = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, EntityEnum.BENEFICIARY.name(),
					fReuqest.getBeneficiary().getBeneficiaryId());
			if(null != serviceResponse) {
				updateEventServiceLogEntry(log, serviceResponse, serviceResponse, serviceResponse.getOverallStatus());
				serviceResponse.setCheckedOn(log.getUpdatedOn().toString());
			} else {
				serviceResponse = createDefaultServiceDownResponse();
				log = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
							EntityEnum.BENEFICIARY.name(), fReuqest.getBeneficiary().getBeneficiaryId());
				updateEventServiceLogEntry(log, serviceResponse, serviceResponse, serviceResponse.getOverallStatus());
				exchange.setResponse(serviceResponse);
			}
			
		} catch (Exception e) {
			LOG.error("Error in CustomChecks transformer class : transformResponse -", e);
			try {
				CustomCheckResponse serviceResponse = createDefaultServiceDownResponse();
				if (fReuqest != null) {
					log = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
							EntityEnum.BENEFICIARY.name(), fReuqest.getBeneficiary().getBeneficiaryId());
				}
				updateEventServiceLogEntry(log, serviceResponse, serviceResponse, serviceResponse.getOverallStatus());
				if (exchange != null)
					exchange.setResponse(serviceResponse);
			} catch (Exception ex) {
				LOG.error("Error in CustomChecks transformer class : transformResponse -", ex);
				message.getPayload().setFailed(true);
			}
		}

		return message;
	}
	
	private CustomCheckResponse createDefaultServiceDownResponse(){
		CustomCheckResponse serviceResponse = new CustomCheckResponse();
		VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
		velocityCheckResponse.setBeneCheck(ServiceStatus.SERVICE_FAILURE.name());
		velocityCheckResponse.setNoOffundsoutTxn(ServiceStatus.SERVICE_FAILURE.name());
		velocityCheckResponse.setPermittedAmoutcheck(ServiceStatus.SERVICE_FAILURE.name());
		velocityCheckResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		WhiteListCheckResponse whiteListCheckResponse = new WhiteListCheckResponse();
		whiteListCheckResponse.setAmoutRange(ServiceStatus.SERVICE_FAILURE.name());
		whiteListCheckResponse.setCurrency(ServiceStatus.SERVICE_FAILURE.name());
		whiteListCheckResponse.setReasonOfTransfer(ServiceStatus.SERVICE_FAILURE.name());
		whiteListCheckResponse.setThirdParty(ServiceStatus.SERVICE_FAILURE.name());
		whiteListCheckResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();//Add for AT-3349
		euPoiCheckResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		serviceResponse.setVelocityCheck(velocityCheckResponse);
		serviceResponse.setWhiteListCheck(whiteListCheckResponse);
		serviceResponse.setEuPoiCheck(euPoiCheckResponse);
		serviceResponse.setFraudPredictStatus(ServiceStatus.SERVICE_FAILURE.name());
		serviceResponse.setOverallStatus(ServiceStatus.SERVICE_FAILURE.name());
		return serviceResponse;
		
	}

}
