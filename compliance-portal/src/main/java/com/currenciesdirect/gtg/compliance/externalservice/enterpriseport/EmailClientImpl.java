package com.currenciesdirect.gtg.compliance.externalservice.enterpriseport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentEmailRequest;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInEmailRequest;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutEmailRequest;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateRequest;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.SoapClient;

import communicationapi.EmailMessage;
import communicationapi.MessageAttributes;
import communicationapi.RequestHeader;

public class EmailClientImpl implements IEmailClient{
	
	private static final String UPDATED_PAYMENT_STATUS = "#UpdatedPaymentStatus";
  private static Logger log = LoggerFactory.getLogger(EmailClientImpl.class);
	
	/**
	 * Instantiates a new email client impl.
	 */

	@Override
	public Boolean sendEmail(PaymentUpdateRequest paymentUpdateRequest, PaymentEmailRequest paymentEmailRequest) {
		SoapClient client = SoapClient.getInstance();
		PaymentInEmailRequest paymentInEmailrequest = new PaymentInEmailRequest();
		PaymentOutEmailRequest paymentOutEmailRequest = new PaymentOutEmailRequest();
		if(paymentEmailRequest instanceof PaymentInEmailRequest) {
			paymentInEmailrequest = (PaymentInEmailRequest) paymentEmailRequest;
		}
		else if(paymentEmailRequest instanceof PaymentOutEmailRequest){
			paymentOutEmailRequest = (PaymentOutEmailRequest) paymentEmailRequest;
		}
		
		EmailMessage message = null;
		
		if(null != paymentOutEmailRequest.getPaymentOutId() && null == paymentInEmailrequest.getPaymentInId()) {
			message = prepareMessageForPaymentOutRejection(paymentOutEmailRequest);
		} 
		else if(null != paymentInEmailrequest.getPaymentInId() && null == paymentOutEmailRequest.getPaymentOutId()){
			message = prepareMessageForPaymentInRejection(paymentInEmailrequest);
		}
		
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setOrganizationCode(paymentUpdateRequest.getOrgCode());
		requestHeader.setSource(Constants.SOURCE_ATLAS);
		Boolean result = Boolean.FALSE;
		try {
			result = client.execute(message, requestHeader,Constants.SERVICE_FLOWNAME_EMAIL);
		} catch (Exception e) {
			log.error("Error in SendEmail()",e);
		}
		return result;
	}
	
	private EmailMessage prepareMessageForPaymentOutRejection(PaymentOutEmailRequest paymentOutEmailRequest) {
		EmailMessage emailMessage = new EmailMessage();
		List<MessageAttributes> messageAttributesList = new ArrayList<>();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Map<String,String> attributes = new HashMap<>();
		if(Constants.REJECT.equalsIgnoreCase(paymentOutEmailRequest.getStatus())) {
			attributes.put(UPDATED_PAYMENT_STATUS, "Rejected");
		} else {
			attributes.put(UPDATED_PAYMENT_STATUS, "Seized");
		}
		attributes.put("#CustomerNumber", paymentOutEmailRequest.getClientNumber());
		attributes.put("#TradeContractId", paymentOutEmailRequest.getTradeContractId());
		attributes.put("#BeneficiaryAmount", paymentOutEmailRequest.getBeneficiaryAmount());
		attributes.put("#Currency", paymentOutEmailRequest.getCurrency());
		attributes.put("#PaymentStatus", paymentOutEmailRequest.getStatus());
		attributes.put("#TodayDate", DateTimeFormatter.dateTimeFormatter(timestamp));
		attributes.put("#UserName", paymentOutEmailRequest.getUserName());
		attributes.put("#StatusReason", paymentOutEmailRequest.getStatusReason());
		attributes.put("#Comment", paymentOutEmailRequest.getComment());
		attributes.put("#BeneficiaryName", paymentOutEmailRequest.getBeneficiaryName());
		
		Set<Map.Entry<String, String>> entrySet = attributes.entrySet();
		for (Entry<String, String> entry : entrySet){
			MessageAttributes messageAttribute = new MessageAttributes();
			messageAttribute.setID(entry.getKey());
			messageAttribute.setValue(entry.getValue());
			messageAttributesList.add(messageAttribute);
		}
		emailMessage.setAttributes(messageAttributesList);
		emailMessage.setLocale(Constants.LOCALE_EN);
		emailMessage.setEvent(Constants.PAYMENT_OUT_REJECT_EVENT);
		emailMessage.setCustomerEmail(System.getProperty(paymentOutEmailRequest.getOrgCode().replaceAll("\\s+", "").toLowerCase()+Constants.EAMIL_SENDTO) );
		emailMessage.setCustomerType(paymentOutEmailRequest.getCustType());	
		
		return emailMessage;
	}
	
	private EmailMessage prepareMessageForPaymentInRejection(PaymentInEmailRequest paymentInEmailrequest) {
		EmailMessage emailMessage = new EmailMessage();
		List<MessageAttributes> messageAttributesList = new ArrayList<>();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Map<String,String> attributes = new HashMap<>();
		if(Constants.REJECT.equalsIgnoreCase(paymentInEmailrequest.getStatus())) {
			attributes.put(UPDATED_PAYMENT_STATUS, "Rejected");
		} else {
			attributes.put(UPDATED_PAYMENT_STATUS, "Seized");
		}
		attributes.put("#CustomerNumber", paymentInEmailrequest.getClientNumber());
		attributes.put("#TradeContractId", paymentInEmailrequest.getTradeContractId());
		attributes.put("#DebtorAmount", paymentInEmailrequest.getDebtorAmount());
		attributes.put("#Currency", paymentInEmailrequest.getCurrency());
		attributes.put("#PaymentStatus", paymentInEmailrequest.getStatus());
		attributes.put("#TodayDate", DateTimeFormatter.dateTimeFormatter(timestamp));
		attributes.put("#UserName", paymentInEmailrequest.getUserName());
		attributes.put("#StatusReason", paymentInEmailrequest.getStatusReason());
		attributes.put("#Comment", paymentInEmailrequest.getComment());
		attributes.put("#PaymentMethod", paymentInEmailrequest.getPaymentMethod());
		
		Set<Map.Entry<String, String>> entrySet = attributes.entrySet();
		for (Entry<String, String> entry : entrySet){
			MessageAttributes messageAttribute = new MessageAttributes();
			messageAttribute.setID(entry.getKey());
			messageAttribute.setValue(entry.getValue());
			messageAttributesList.add(messageAttribute);
		}
		emailMessage.setAttributes(messageAttributesList);
		emailMessage.setLocale(Constants.LOCALE_EN);
		emailMessage.setEvent(Constants.PAYMENT_IN_REJECT_EVENT);
		emailMessage.setCustomerEmail(System.getProperty(paymentInEmailrequest.getOrgCode().replaceAll("\\s+", "").toLowerCase()+Constants.EAMIL_SENDTO) );
		emailMessage.setCustomerType(paymentInEmailrequest.getCustType());
		
		return emailMessage;
	}
	
}
