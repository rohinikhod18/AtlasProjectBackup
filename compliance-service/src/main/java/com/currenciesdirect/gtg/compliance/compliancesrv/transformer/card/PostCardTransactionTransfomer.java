package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.card;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.enterprise.EnterpriseServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.enterprise.EnterpriseIPAddressDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.PostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;

public class PostCardTransactionTransfomer extends AbstractTransformer {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PostCardTransactionTransfomer.class);
	
	private static final String DATE_TIME_STRING = "yyyy-MM-dd HH:mm:ss";
	
	/** The Constant TIME_STRING. */
	private static final String TIME_STRING = "00:00:00";
	
	/** The enterprise service impl. */
	@Autowired	
	protected EnterpriseServiceImpl enterpriseServiceImpl;
	
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
		PostCardTransactionRequest request =  (PostCardTransactionRequest) messageExchange.getRequest();
		TransactionMonitoringPostCardTransactionRequest tmRequest = new TransactionMonitoringPostCardTransactionRequest();
		TransactionMonitoringPostCardTransactionResponse tmResponse = new TransactionMonitoringPostCardTransactionResponse();
		
		MessageExchange ccExchange = new MessageExchange();
		
		try {
			transformTransactionMonitoringPostCardRequest(request, tmRequest);	
			if(request.getIpAddress() != null && !request.getIpAddress().isEmpty() && !request.getIpAddress().equals("")) {
				getIPAddressDetails(request, tmRequest);
			}
			
			ccExchange.setRequest(tmRequest);
			ccExchange.setResponse(tmResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
			
		} catch(Exception e) {
			LOG.error("Error in PostCardTransactionTransfomer transformRequest()", e);
		}
		return message;
	}

	/**
	 * Gets the IP address details.
	 *
	 * @param request the request
	 * @param tmRequest the tm request
	 * @return the IP address details
	 * @throws ComplianceException the compliance exception
	 */
	private void getIPAddressDetails(PostCardTransactionRequest request,
			TransactionMonitoringPostCardTransactionRequest tmRequest) throws ComplianceException {
		EnterpriseIPAddressDetails enterpriseIpAdd;
		enterpriseIpAdd = enterpriseServiceImpl.getIPAddressDetails(request.getIpAddress());
		if(enterpriseIpAdd != null && enterpriseIpAdd.getIpAddress() != null && enterpriseIpAdd.getLongitude() != null) {
			setIPDetails(enterpriseIpAdd, tmRequest);
		}
	}

	/**
	 * Transform transaction monitoring post card request.
	 *
	 * @param request the request
	 * @param tmRequest the tm request
	 * @throws ParseException 
	 */
	private void transformTransactionMonitoringPostCardRequest(PostCardTransactionRequest request,
			TransactionMonitoringPostCardTransactionRequest tmRequest) throws ParseException {
		tmRequest.setTrxID(request.getPaymentLifeId());
		tmRequest.setTitanAccountNumber(request.getAccountNumber());
		tmRequest.setCardToken(request.getCardToken());
		tmRequest.setTxnAmount(request.getTxnAmount());
		tmRequest.setgPSDatestamp(
				request.getGpsDatestamp() != null ? dateTimeFormatter(request.getGpsDatestamp().toString()) : null);
		tmRequest.setRequestType(request.getCardRequestType());
		tmRequest.setCardInputMode(request.getCardInputMode());
		tmRequest.setCardInputModeDesc(request.getCardInputModeDesc());
		tmRequest.setMerchID(request.getMerchantID());
		tmRequest.setMerchantName(request.getMerchantName());
		tmRequest.setMerchantNameOther(request.getMerchantNameOther());
		tmRequest.setmCCCode(request.getMccCode());
		tmRequest.setmCCCodeDescription(request.getMccCodeDescription());
		tmRequest.setMerchantCountryCode(request.getMerchantCountryCode());
		tmRequest.setMerchantPostcode(request.getMerchantPostCode());
		tmRequest.setMerchantAddress(request.getMerchantAddress());
		tmRequest.setpOSTerminal(request.getPosTerminal());
		tmRequest.setBin(request.getBin());
		tmRequest.setProductID(request.getProductId());
		tmRequest.setAcqDate(
				request.getAcqDatestamp() != null ? dateTimeFormatter(request.getAcqDatestamp().toString()) : null);
		tmRequest.setCardPresentIndicator(request.getCardPresentInd());
		tmRequest.setCardPresentDescription(request.getCardPresentDesc());
		tmRequest.setTxnCountry(request.getTxnCountry());
		tmRequest.setMultiPartInd(request.getPreAuthMultiPartInd());
		tmRequest.setMultiPartFinal(request.getPreAuthMultiPartFinal());
		tmRequest.setMultiPartMatchingReference(request.getPreAuthMatchingReference());
		tmRequest.setBillCcy(request.getBillCcy());
		
		//AT-5249
		Double billAmount = request.getBillAmount();
		if(billAmount != null) {
			billAmount = billAmount * -1;
			tmRequest.setBillAmount(billAmount);
		}
		
		tmRequest.setEffectiveAmount(request.getEffectiveAmount());
		tmRequest.setAdditionalAmountType(request.getAdditionalAmountType());
		tmRequest.setAdditionalAmountCcy(request.getAdditionalAmountCcy());
		tmRequest.setAdditionalAmount(request.getAdditionalAmount());
		tmRequest.setConsolidatedWalletBalances(request.getConsolidatedWalletBalances());
		tmRequest.setMerchantRegion(request.getMerchantRegion());
		tmRequest.setMerchantCity(request.getMerchantCity());
		tmRequest.setMerchantContact(request.getMerchantContact());
		tmRequest.setMerchantSecondContact(request.getMerchantSecondContact());
		tmRequest.setMerchantWebsite(request.getMerchantWebsite());
		tmRequest.setAcquirerID(request.getAcquirerId());
		tmRequest.setgPSDecisionCode(request.getGpsDecisionCode());
		tmRequest.setgPSDecisionCodeDescription(request.getGpsDecisionCodeDescription());
		tmRequest.setTitanDecisionCode(
				request.getTitanDecision().equalsIgnoreCase("000") ? "00" : request.getTitanDecision()); // AT-5357
		tmRequest.setTitanDecisionDescription(request.getTitanDecisionDescription());
		tmRequest.setCardStatusCode(request.getCardStatusCode());
		tmRequest.setgPSSTIPFlag(request.getGpsStipFlag());
		tmRequest.setaVSResult(request.getAvsResult());
		
		if (request.getGpsPosData() != null && !request.getGpsPosData().isEmpty()
				&& request.getGpsPosData().length() > 18) {
			tmRequest.setsCAAuthenticationExemptionCode(String.valueOf(request.getGpsPosData().charAt(18)));
		}
		
		tmRequest.setcVVResults(request.getCvvResults());
		tmRequest.setDeviceID(request.getDeviceId());
		tmRequest.setUpdateStatus(request.getUpdateStatus());
		tmRequest.setUpdateDate(request.getDateUpdate() != null ? dateFormatter(request.getDateUpdate().toString()) : null);
		tmRequest.setChargebackReason(request.getChargebackReason());
		
		tmRequest.setpOSDatestamp(posdateTimeFormatter(request.getPosDate(),request.getPosTime()));
		
		tmRequest.setgPSPOSCapability(request.getGpsPosCapability());
		tmRequest.setgPSPOSData(request.getGpsPosData());
		tmRequest.setIpAddress(request.getIpAddress());
		tmRequest.setTxnCCY(request.getTxnCurrency());
		tmRequest.setsCAAuthenticationExemptionDescription(null);
		tmRequest.setUpdateAmount(request.getRefundorDispAmount());
		tmRequest.setAuthenticationMethod(request.getAuthenticationMethod());
		tmRequest.setAuthenticationMethodDesc(request.getAuthenticationMethodDesc());
		//AT-5371
		tmRequest.setPtWallet(request.getPtWallet());
		tmRequest.setPtDeviceType(request.getPtDeviceType());
		tmRequest.setPtDeviceIP(request.getPtDeviceIP());
		tmRequest.setPtDeviceID(request.getPtToken()); //Updated for AT-5474
	}
	
	/**
	 * Sets the IP details.
	 *
	 * @param request the request
	 * @param tmRequest the tm request
	 */
	private void setIPDetails(EnterpriseIPAddressDetails request,
			TransactionMonitoringPostCardTransactionRequest tmRequest) {
		tmRequest.setContinent(request.getContinent());
		tmRequest.setLongitude(request.getLongitude().toString());
		tmRequest.setLatitiude(request.getLatitiude().toString());
		tmRequest.setRegion(request.getRegion());
		tmRequest.setCity(request.getCity());
		tmRequest.setTimezone(request.getTimezone());
		tmRequest.setOrganization(request.getOrganization());
		tmRequest.setCarrier(request.getCarrier());
		tmRequest.setConnectionType(request.getConnectionType());
		tmRequest.setLineSpeed(request.getLineSpeed());
		tmRequest.setIpRoutingType(request.getIpRoutingType());
		tmRequest.setCountryName(request.getCountryName());
		tmRequest.setCountryCode(request.getCountryCode());
		tmRequest.setStateName(request.getStateName());
		tmRequest.setStateCode(request.getStateCode());
		tmRequest.setPostalCode(request.getPostalCode());
		tmRequest.setAreaCode(request.getAreaCode());
		tmRequest.setAnonymizerStatus(request.getAnonymizerStatus());
		tmRequest.setIpAddress(request.getIpAddress());
	}

	/**
	 * Date formatter.
	 *
	 * @param date the date
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	private String dateTimeFormatter(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_TIME_STRING);
		Date dateToFormat = df.parse(date);
		DateFormat outputformat = new SimpleDateFormat(DATE_TIME_STRING);
		return outputformat.format(dateToFormat);
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

		try {
			if (tmCardResponse.getHttpStatus() == 400) {
				sendAlertEmailForInvalidRequest(tmCardRequest.getTrxID(), tmCardResponse.getErrorDescription());
			}
		} catch (Exception e) {
			LOG.error("Error in PostCardTransactionTransformer transformResponse() method : ", e);
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
	
	/**
	 * Date formatter.
	 *
	 * @param date the date
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	private String dateFormatter(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_TIME_STRING);
		Date dateToFormat = df.parse(date);
		DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd");
		return outputformat.format(dateToFormat);
	}
	
	/**
	 * Posdate time formatter.
	 *
	 * @param posDate the pos date
	 * @param posTime the pos time
	 * @return the string
	 */
	private String posdateTimeFormatter(String posDate, String posTime) {
		String posDateTime = null;
		
		if(posDate != null && !posDate.isEmpty() && !posDate.equalsIgnoreCase("")) {
			posTime=(posTime != null && !posTime.isEmpty() && !posTime.equalsIgnoreCase("")) ? posTime : TIME_STRING;
			if(posDate.length() == 8){
    		    StringBuilder sbDate = new StringBuilder(posDate);
    			sbDate.insert(4, '-');
    			sbDate.insert(7, '-');
    			posDate = sbDate.toString();
    		}
			if(!posTime.equalsIgnoreCase(TIME_STRING) && posTime.length() == 6) {
				StringBuilder sbTime = new StringBuilder(posTime);
				sbTime.insert(2, ':');
				sbTime.insert(5,':');
				posTime = sbTime.toString();
				posDateTime = posDate + " " + posTime;
			}
			else if(!posTime.equalsIgnoreCase(TIME_STRING) && posTime.length() == 12){
			    StringBuilder sbTime = new StringBuilder(posTime);
				sbTime.insert(0, "20");
				sbTime.insert(4, '-');
				sbTime.insert(7, '-');
				sbTime.insert(10, ' ');
				sbTime.insert(13, ':');
				sbTime.insert(16, ':');
				posDateTime = sbTime.toString();
			}
			else{
			    posTime = TIME_STRING;
			    posDateTime = posDate + " " + posTime;
			}
		}
				
		return posDateTime;
	}
}
