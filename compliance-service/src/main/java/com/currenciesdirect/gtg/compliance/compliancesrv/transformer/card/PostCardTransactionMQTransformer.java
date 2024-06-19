package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.card;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.IntuitionPostCardAccountMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.IntuitionPostCardChargebackMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.IntuitionPostCardIPDeviceMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.IntuitionPostCardMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.IntuitionPostCardTransactionMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.PostCardTransactionMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionAccountMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionCFXLegalEntityMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionCardMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionIPDeviceMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class PostCardTransactionMQTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PostCardTransactionMQTransformer.class);
	
	/** The i comm hub service impl. */
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;

	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		PostCardTransactionMQRequest postCardMQRequest = (PostCardTransactionMQRequest) messageExchange.getRequest();
		TransactionMonitoringPostCardTransactionRequest tmRequest = new TransactionMonitoringPostCardTransactionRequest();
		TransactionMonitoringPostCardTransactionResponse tmResponse = new TransactionMonitoringPostCardTransactionResponse();

		MessageExchange ccExchange = new MessageExchange();

		try {
			String json = JsonConverterUtil.convertToJsonWithoutNull(postCardMQRequest.getRequestJson());
			IntuitionPostCardMQRequest mqRequest = JsonConverterUtil
					.convertToObject(IntuitionPostCardMQRequest.class, json);

			transformTransactionMonitoringPostCardMQRequest(mqRequest, tmRequest);
			tmRequest.setRequestType(postCardMQRequest.getCardRequestType());
			tmRequest.setIsPresent(postCardMQRequest.getIsPresent());

			ccExchange.setRequest(tmRequest);
			ccExchange.setResponse(tmResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOG.error("Error in PostCardTransactionMQTransformer transformRequest()", e);
		}
		return message;
	}

	/**
	 * Transform transaction monitoring post card MQ request.
	 *
	 * @param postCardMQRequest the post card MQ request
	 * @param tmRequest         the tm request
	 */
	private void transformTransactionMonitoringPostCardMQRequest(IntuitionPostCardMQRequest request,
			TransactionMonitoringPostCardTransactionRequest tmRequest) {
		
		tmRequest.setTrxID(request.getTrxID());
		tmRequest.setgPSDatestamp(request.getGPSDatestamp());
		tmRequest.setTitanAccountNumber(request.getTitanAccountNumber());
		tmRequest.setCardToken(request.getCardToken());
		tmRequest.setBin(request.getBin());
		tmRequest.setProductID(request.getProductID());
		tmRequest.setAcqDate(request.getAcqDate());
		tmRequest.setRequestType(request.getRequestType());
		tmRequest.setTxnCountry(request.getTxnCountry());
		tmRequest.setMultiPartInd(request.getMultiPartInd());
		tmRequest.setMultiPartFinal(request.getMultiPartFinal());
		tmRequest.setMultiPartMatchingReference(request.getMultiPartMatchingReference());
		tmRequest.setBillCcy(request.getBillCcy());
		tmRequest.setBillAmount(request.getBillAmount());
		tmRequest.setAdditionalAmountCcy(request.getAdditionalAmountCcy());
		tmRequest.setAdditionalAmount(request.getAdditionalAmount());
		tmRequest.setMerchID(request.getMerchID());
		tmRequest.setmCCCode(request.getMCCCode());
		tmRequest.setAcquirerID(request.getAcquirerID());
		tmRequest.setTitanDecisionCode(request.getTitanDecisionCode());
		tmRequest.setTitanDecisionDescription(request.getTitanDecisionDescription());
		tmRequest.setCardStatusCode(request.getCardStatusCode());
		tmRequest.setgPSSTIPFlag(request.getGPSSTIPFlag());
		tmRequest.setaVSResult(request.getAVSResult());
		tmRequest.setsCAAuthenticationExemptionCode(request.getSCAAuthenticationExemptionCode());
		tmRequest.setsCAAuthenticationExemptionDescription(request.getSCAAuthenticationExemptionDescription());
		tmRequest.setAuthenticationMethod(request.getAuthenticationMethod());
		tmRequest.setAuthenticationMethodDesc(request.getAuthenticationMethodDesc());	
		tmRequest.setcVVResults(request.getCVVResults());
		tmRequest.setpOSTerminal(request.getPOSTerminal());
		tmRequest.setpOSDatestamp(request.getGPSDatestamp());
		tmRequest.setgPSPOSCapability(request.getGPSPOSCapability());
		tmRequest.setgPSPOSData(request.getGPSPOSData());
		
		IntuitionPostCardTransactionMQRequest transaction = null;
		IntuitionPostCardAccountMQRequest account = null;
		IntuitionPostCardChargebackMQRequest chargeback = null;
		IntuitionPostCardIPDeviceMQRequest ipdevice = null;
		
		if(request.getTransaction() != null) {
			transaction = request.getTransaction().get(0);
			tmRequest.setCardInputMode(transaction.getCardInputMode());
			tmRequest.setCardInputModeDesc(transaction.getCardInputModeDesc());
			tmRequest.setCardPresentIndicator(transaction.getCardPresentIndicator());
			tmRequest.setCardPresentDescription(transaction.getCardPresentDescription());
			tmRequest.setTxnCCY(transaction.getTxnCCY());
			tmRequest.setTxnAmount(transaction.getTxnAmount());
			tmRequest.setEffectiveAmount(transaction.getEffectiveAmount());
			tmRequest.setAdditionalAmountType(transaction.getAdditionalAmountType());
			tmRequest.setMerchantName(transaction.getMerchantName());
			tmRequest.setMerchantNameOther(transaction.getMerchantNameOther());
			tmRequest.setmCCCodeDescription(transaction.getMCCCodeDescription());
			tmRequest.setMerchantCountryCode(transaction.getMerchantCountryCode());
			tmRequest.setMerchantPostcode(transaction.getMerchantPostcode());
			tmRequest.setMerchantRegion(transaction.getMerchantRegion());
			tmRequest.setMerchantAddress(transaction.getMerchantAddress());
			tmRequest.setMerchantCity(transaction.getMerchantCity());
			tmRequest.setMerchantContact(transaction.getMerchantContact());
			tmRequest.setMerchantSecondContact(transaction.getMerchantSecondContact());
			tmRequest.setMerchantWebsite(transaction.getMerchantWebsite());
			tmRequest.setgPSDecisionCode(transaction.getGPSDecisionCode());
			tmRequest.setgPSDecisionCodeDescription(transaction.getGPSDecisionCodeDescription());
			//AT-5371
			tmRequest.setPtWallet(transaction.getPtWallet());
			tmRequest.setPtDeviceType(transaction.getPtDeviceType());
			tmRequest.setPtDeviceIP(transaction.getPtDeviceIP());
			tmRequest.setPtDeviceID(transaction.getPtDeviceID());
		}
		
		if(request.getAccount() != null) {
			account = request.getAccount().get(0);
			tmRequest.setConsolidatedWalletBalances(account.getConsolidatedWalletBalances());
		}
		
		if(request.getChargeback() != null) {
			chargeback = request.getChargeback().get(0);
			tmRequest.setUpdateStatus(chargeback.getUpdateStatus());
			tmRequest.setUpdateDate(chargeback.getUpdateDate());
			tmRequest.setUpdateAmount(chargeback.getUpdateAmount());
			tmRequest.setChargebackReason(chargeback.getChargebackReason());
		}
		
		if(request.getIpDevice() != null) {
			ipdevice = request.getIpDevice().get(0);
			tmRequest.setIpAddress(ipdevice.getIpAddress());
			tmRequest.setContinent(ipdevice.getContinent());
			tmRequest.setLongitude(ipdevice.getLongitude());
			tmRequest.setLatitiude(ipdevice.getLatitiude());
			tmRequest.setRegion(ipdevice.getRegion());
			tmRequest.setCity(ipdevice.getCity());
			tmRequest.setTimezone(ipdevice.getTimezone());
			tmRequest.setOrganization(ipdevice.getOrganization());
			tmRequest.setCarrier(ipdevice.getCarrier());
			tmRequest.setConnectionType(ipdevice.getConnectionType());
			tmRequest.setLineSpeed(ipdevice.getLineSpeed());
			tmRequest.setIpRoutingType(ipdevice.getIpRoutingType());
			tmRequest.setCountryName(ipdevice.getCountryName());
			tmRequest.setCountryCode(ipdevice.getCountryCode());
			tmRequest.setStateName(ipdevice.getStateName());
			tmRequest.setStateCode(ipdevice.getStateCode());
			tmRequest.setPostalCode(ipdevice.getPostalCode());
			tmRequest.setAreaCode(ipdevice.getAreaCode());
			tmRequest.setAnonymizerStatus(ipdevice.getAnonymizerStatus());
			tmRequest.setDeviceID(ipdevice.getDeviceID());
		}
	}

	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		TransactionMonitoringPostCardTransactionRequest tmCardRequest = (TransactionMonitoringPostCardTransactionRequest) messageExchange
				.getRequest();
		TransactionMonitoringPostCardTransactionResponse tmCardResponse = (TransactionMonitoringPostCardTransactionResponse) messageExchange
				.getResponse();
		
		Boolean isFailedRequest = Boolean.FALSE;

		try {
			if (tmCardResponse.getHttpStatus() == 400) {
				sendAlertEmailForInvalidRequest(tmCardRequest.getTrxID(),tmCardResponse.getErrorDescription());
				isFailedRequest = Boolean.TRUE;
			}
			
			tmCardResponse.addAttribute("isFailedRequest",isFailedRequest);
			messageExchange.setResponse(tmCardResponse);
			
		} catch (Exception e) {
			LOG.error("Error in PostCardTransactionMQTransformer transformResponse() method : ", e);
		}

		return message;
	}

	/**
	 * Send alert email for invalid request.
	 *
	 * @param instructionNumber the instruction number
	 */
	private void sendAlertEmailForInvalidRequest(String instructionNumber, String errorDesc) {

		SendEmailRequestForTM sendTMEmailRequest = new SendEmailRequestForTM();
		EmailHeaderForTM header = new EmailHeaderForTM();
		EmailPayloadForTM payload = new EmailPayloadForTM();

		header.setSourceSystem("Atlas");
		header.setOrgCode("Currencies Direct");
		header.setLegalEntity("CDLGB");

		String email = System.getProperty("intuition.sendTo");
		List<String> list = Arrays.asList(email.split(","));

		payload.setEmailId(list);
		payload.setSubject("Intuition Post Card Transaction request failed");
		payload.setEmailContent("Post Card Transaction request for Instruction Number " + instructionNumber
				+ " to Intuition failed, due to "+errorDesc+", please alert dev team.");
		payload.setFromEmilId(System.getProperty("intuition.sendFrom"));
		payload.setReplyToEmailId(System.getProperty("intuition.sendFrom"));

		sendTMEmailRequest.setTmHeader(header);
		sendTMEmailRequest.setTmPayload(payload);

		iCommHubServiceImpl.sendEmailForTMAlert(sendTMEmailRequest, true);

	}
}