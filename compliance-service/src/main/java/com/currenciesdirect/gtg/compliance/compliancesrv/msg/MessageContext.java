package com.currenciesdirect.gtg.compliance.compliancesrv.msg;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact.DeleteContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;


/**
 * The Class MessageContext.
 */
public class MessageContext implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The transaction id. */
	private String transactionId;
	
	/** The message collection. */
	private final LinkedList<MessageExchangeWrapper> messageCollection;
	
	/** The internal processing code. */
	private InternalProcessingCode internalProcessingCode;
	
	/** The audit info. */
	private final AuditInfo auditInfo;
	
	/** The org code. */
	private String orgCode;
	
	/** The service tobe invoked. */
	private ServiceTypeEnum serviceTobeInvoked;
	
	/** The failed. */
	private boolean failed = false;
	
	/** The no message left. */
	private Boolean noMessageLeft = Boolean.FALSE;

	/** The retry count. */
	private Integer retryCount = 0;
	
	

	public Boolean getNoMessageLeft() {
		return noMessageLeft;
	}

	public void setNoMessageLeft(Boolean noMessageLeft) {
		this.noMessageLeft = noMessageLeft;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * Instantiates a new message context.
	 */
	public MessageContext() {
		auditInfo = new AuditInfo();
		messageCollection = new LinkedList<>();
		internalProcessingCode = InternalProcessingCode.NOT_CLEARED;
		transactionId = UUID.randomUUID().toString();
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Gets the message collection.
	 *
	 * @return the message collection
	 */
	public List<MessageExchangeWrapper> getMessageCollection() {
		return messageCollection;
	}

	/**
	 * Append message exchange.
	 *
	 * @param messagExchange the messag exchange
	 */
	public void appendMessageExchange(MessageExchange messagExchange) {
		messageCollection.add(new MessageExchangeWrapper(messagExchange));
	}

	/**
	 * Gets the message exchange size.
	 *
	 * @return the message exchange size
	 */
	public int getMessageExchangeSize() {
		return messageCollection.size();
	}
	
	/**
	 * Gets the service tobe invoked.
	 *
	 * @return the service tobe invoked
	 */
	public ServiceTypeEnum getServiceTobeInvoked() {
		return serviceTobeInvoked;
	}

	/**
	 * Sets the service tobe invoked.
	 *
	 * @param serviceTobeInvoked the new service tobe invoked
	 */
	public void setServiceTobeInvoked(ServiceTypeEnum serviceTobeInvoked) {
		this.serviceTobeInvoked = serviceTobeInvoked;
	}

	/**
	 * Clear all.
	 */
	public void clearAll() {
		for (MessageExchangeWrapper exchange : messageCollection) {
			exchange.getMessageExchange().clear();
		}

	}

	/**
	 * Gets the message exchange.
	 *
	 * @param serviceTypeEnum the service type enum
	 * @return the message exchange
	 */
	public MessageExchange getMessageExchange(ServiceTypeEnum serviceTypeEnum) {
		for (MessageExchangeWrapper exchange : messageCollection) {
			if (exchange.getMessageExchange().getServiceTypeEnum()==serviceTypeEnum) {
				return exchange.getMessageExchange();
			}
		}
		return null;
	}
	
	/**
	 * Gets the gateway message exchange.
	 *
	 * @return the gateway message exchange
	 */
	public MessageExchange getGatewayMessageExchange() {
		ServiceTypeEnum serviceTypeEnum = ServiceTypeEnum.GATEWAY_SERVICE;
		for (MessageExchangeWrapper exchange : messageCollection) {
			if (exchange.getMessageExchange().getServiceTypeEnum() == serviceTypeEnum){
				return exchange.getMessageExchange();
			}
		}
		return null;
	}
	
	/**
	 * Removes the message exchange.
	 *
	 * @param serviceTypeEnum the service type enum
	 */
	public void removeMessageExchange(ServiceTypeEnum serviceTypeEnum) {
		Iterator<MessageExchangeWrapper> it = messageCollection.iterator();
		while (it.hasNext()) {
			MessageExchangeWrapper exchange = it.next();
			if (exchange.getMessageExchange().getServiceTypeEnum()==serviceTypeEnum) {
				it.remove();
				break;
			}
		}
	}

	/**
	 * Gets the internal processing code.
	 *
	 * @return the internal processing code
	 */
	public InternalProcessingCode getInternalProcessingCode() {
		return internalProcessingCode;
	}

	/**
	 * Sets the internal processing code.
	 *
	 * @param internalProcessingCode the new internal processing code
	 */
	public void setInternalProcessingCode(InternalProcessingCode internalProcessingCode) {
		this.internalProcessingCode = internalProcessingCode;
	}

	/**
	 * Gets the audit info.
	 *
	 * @return the audit info
	 */
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageContext other = (MessageContext) obj;
		if (messageCollection == null) {
			if (other.messageCollection != null)
				return false;
		} else if (!messageCollection.equals(other.messageCollection)) {
			return false;
		}
		if (orgCode == null) {
			if (other.orgCode != null) {
				return false;
			}
		} else if (!orgCode.equals(other.orgCode)) {
			return false;
		}
		if (transactionId == null) {
			if (other.transactionId != null) {
				return false;
			}
		} else if (!transactionId.equals(other.transactionId)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageCollection == null) ? 0 : messageCollection.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}
	
	/**
	 * Gets the interna rules request size.
	 *
	 * @return the interna rules request size
	 */
	public Integer getInternaRulesRequestSize(){
		InternalServiceRequest iRequest = (InternalServiceRequest)this.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getRequest();
		return iRequest.getSearchdata().size();
	}
	
	/**
	 * Gets the KYC request size.
	 *
	 * @return the KYC request size
	 */
	public Integer getKYCRequestSize(){
		KYCProviderRequest kycRequest = (KYCProviderRequest)this.getMessageExchange(ServiceTypeEnum.KYC_SERVICE).getRequest();
		return kycRequest.getContact().size();
	}
	
	/**
	 * Gets the fraugster on update request size.
	 *
	 * @return the fraugster on update request size
	 */
	public Integer getFraugsterOnUpdateRequestSize(){
		FraugsterOnUpdateRequest fraugsterOnUpdateRequest = (FraugsterOnUpdateRequest)this.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getRequest();
		return fraugsterOnUpdateRequest.getContactRequests()==null?0:fraugsterOnUpdateRequest.getContactRequests().size();
	}
	
	/**
	 * Gets the sanction request size.
	 *
	 * @return the sanction request size
	 */
	public Integer getSanctionRequestSize(){
		SanctionRequest kycRequest = (SanctionRequest)this.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getRequest();
		return kycRequest.getContactrequests()==null?0:kycRequest.getContactrequests().size();
	}
	
	/**
	 * Gets the KYC status.
	 *
	 * @return the KYC status
	 */
	public String getKYCStatus(){
		KYCProviderResponse kycResponse = (KYCProviderResponse)this.getMessageExchange(ServiceTypeEnum.KYC_SERVICE).getResponse();
		return kycResponse.getStatus();
	}
	
	/**
	 * Gets the internal rule service status.
	 *
	 * @return the internal rule service status
	 */
	public String getInternalRuleServiceStatus(){
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse)this.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		return internalServiceResponse.getStatus();
	}
	
	/**
	 * Gets the account status.
	 *
	 * @return the account status
	 */
	public String getAccountStatus(){
		RegistrationResponse response  = (RegistrationResponse)this.getGatewayMessageExchange().getResponse();
		return response.getAccount().getAcs().toString();
	}
	
	/**
	 * Gets the validation result.
	 *
	 * @return the validation result
	 */
	public String getValidationResult(){
		BaseResponse regResponse = (BaseResponse)this.getGatewayMessageExchange().getResponse();
		return regResponse.getDecision().toString();
	}
	
	/**
	 * Do perform other checks.
	 *
	 * @return true, if successful
	 */
	public boolean doPerformOtherChecks(){
		ServiceMessage iRequest =this.getGatewayMessageExchange().getRequest();
		return (Boolean) iRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS);
	}
	
	/**
	 * Do send email after address update.
	 *
	 * @return true, if successful
	 */
	public boolean doSendEmailAfterAddressUpdate(){
		ServiceMessage iRequest =this.getGatewayMessageExchange().getRequest();
		return (Boolean) iRequest.getAdditionalAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE);
	}
	
	/**
	 * Gets the request resource type.
	 *
	 * @return the request resource type
	 */
	public String getRequestResourceType(){
		SanctionUpdateRequest sanctionUpdateRequest = (SanctionUpdateRequest)this.getGatewayMessageExchange().getRequest();
		return sanctionUpdateRequest.getResourceType();
	}
	
	/**
	 * Checks if is failed.
	 *
	 * @return true, if is failed
	 */
	public boolean isFailed(){
		return failed;
	}
	
	/**
	 * Sets the failed.
	 *
	 * @param failed the new failed
	 */
	public void setFailed(boolean failed){
		this.failed = failed;
	}

	/**
	 * Checks if is broadcast required.
	 *
	 * @return the isBroadcastRequired
	 */
	public boolean isBroadcastRequired() {
		ServiceMessage iRequest =this.getGatewayMessageExchange().getRequest();
		return (Boolean) iRequest.getAdditionalAttribute(Constants.FIELD_BROADCAST_REQUIRED);
	}
	
	/**
	 * Checks if is sanction eligible.
	 *
	 * @return true, if is sanction eligible
	 */
	public boolean isSanctionEligible() {
		ServiceMessage request = this.getGatewayMessageExchange().getRequest();
		return (boolean) request.getAdditionalAttribute(Constants.RECHECK_SANCTION_ELIGIBLE);
	}

	/**
	 * Checks if is fraugster eligible.
	 *
	 * @return true, if is fraugster eligible
	 */
	public boolean isFraugsterEligible() {
		ServiceMessage request = this.getGatewayMessageExchange().getRequest();
		return (boolean) request.getAdditionalAttribute(Constants.RECHECK_FRAUGSTER_ELIGIBLE);
	}
	
	/**
	 * Checks if is custom check eligible.
	 *
	 * @return true, if is custom check eligible
	 */
	public boolean isCustomCheckEligible() {
		ServiceMessage request = this.getGatewayMessageExchange().getRequest();
		return (boolean) request.getAdditionalAttribute(Constants.RECHECK_CUSTOM_CHECK_ELIGIBLE);
	}
	
	/**
	 * Checks if is internal rule service eligible.
	 *
	 * @return true, if is internal rule service eligible
	 */
	public boolean isInternalRuleServiceEligible() {
		ServiceMessage request = this.getGatewayMessageExchange().getRequest();
		return (boolean) request.getAdditionalAttribute(Constants.RECHECK_BLACKLIST_ELIGIBLE) 
				|| (boolean) request.getAdditionalAttribute(Constants.RECHECK_COUNTRY_CHECK_ELIGIBLE);
	}

	/**
	 * Checks if is internal rule service eligible for reg.
	 *
	 * @return true, if is internal rule service eligible for reg
	 */
	public boolean isInternalRuleServiceEligibleForReg() {
		ServiceMessage request = this.getGatewayMessageExchange().getRequest();
		return (boolean) request.getAdditionalAttribute("isBlacklistEligible")
				|| (boolean) request.getAdditionalAttribute("isCountryCheckEligible")
				|| (boolean) request.getAdditionalAttribute("isIpCheckEligible")
				|| (boolean) request.getAdditionalAttribute("isGlobalCheckEligible");
	}
	
	/**
	 * Checks if is eid eligible.
	 *
	 * @return true, if is eid eligible
	 */
	public boolean isEidEligible() {
		ServiceMessage request = this.getGatewayMessageExchange().getRequest();
		return (boolean) request.getAdditionalAttribute("isEidEligible");
	}
	
	public String getCustTypeForContactDeletion(){
		DeleteContactRequest request = (DeleteContactRequest)this.getGatewayMessageExchange().getRequest();
		RegistrationServiceRequest regRequest = (RegistrationServiceRequest)request.getAdditionalAttribute("regRequest");
		return regRequest.getAccount().getCustType();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageContext [transactionId=" + transactionId + ", serviceTobeInvoked=" + serviceTobeInvoked + "]";
	}

	public boolean isIntuitionEligible() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		RegistrationServiceRequest regRequest = messageExchange.getRequest(RegistrationServiceRequest.class);

		int accountTMFlag = (int) regRequest.getAdditionalAttribute("AccountTMFlag");

		if (messageExchange.getOperation() == OperationEnum.NEW_REGISTRATION) {
			return true;
		} else {
			return (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4);
		}
	}
	
	//AT-4715
	public boolean isIntuitionStatusHold() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		FundsInCreateResponse response = messageExchange.getResponse(FundsInCreateResponse.class);
		boolean status = false;
		
		if (!response.getStatus().equalsIgnoreCase(FundsInComplianceStatus.HOLD.name())) 
			status = true;
		
		return status;
	}
	
	//AT-4716
	public boolean isIntuitionFundsOutStatusHold() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		FundsOutResponse response = messageExchange.getResponse(FundsOutResponse.class);
		boolean status = false;
		
		if (!response.getStatus().equalsIgnoreCase(FundsOutComplianceStatus.HOLD.name())) 
			status = true;
		
		return status;
	}
	
	/**
	 * Checks if is intuition MQ status hold.
	 *
	 * @return true, if is intuition MQ status hold
	 */
	public boolean isIntuitionMQStatusHold() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
				.getRequest();
		MessageExchange exchange = this.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);

		boolean status = false;

		if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in")
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in_update")) {
			TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
					.getResponse();
			TransactionMonitoringPaymentInResponse response = mqResponse.getTransactionMonitoringPaymentInResponse();
			if (!response.getPaymentStatus().equalsIgnoreCase(FundsInComplianceStatus.HOLD.name()))
				status = true;
		} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out")
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out_update")) {
			TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
					.getResponse();
			TransactionMonitoringPaymentOutResponse response = mqResponse.getTransactionMonitoringPaymentOutResponse();
			if (!response.getPaymentStatus().equalsIgnoreCase(FundsOutComplianceStatus.HOLD.name()))
				status = true;
		}

		return status;
	}

	
	/**
	 * Checks if is intuition status update eligible.
	 *
	 * @return true, if is intuition status update eligible
	 */
	//AT-4749
	public boolean isIntuitionStatusUpdateEligible() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		IntuitionPaymentStatusUpdateRequest request = (IntuitionPaymentStatusUpdateRequest) messageExchange
				.getRequest();
		IntuitionPaymentStatusUpdateResponse response = (IntuitionPaymentStatusUpdateResponse) messageExchange
				.getResponse();
		boolean decision = false;
		String paymentStatus = "";

		if (request.getTrxType().equalsIgnoreCase("FUNDSIN")) {
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request
					.getAdditionalAttribute("fundsInRequest");
			paymentStatus = (String) fundsInRequest.getAdditionalAttribute("status");
		} else {
			FundsOutRequest fundsOutRequest = (FundsOutRequest) request.getAdditionalAttribute("fundsOutRequest");
			paymentStatus = (String) fundsOutRequest.getAdditionalAttribute("status");
		}
		
		if (response.getPaymentStatus() != null && !response.getPaymentStatus().equalsIgnoreCase(paymentStatus))
			decision = true;

		return decision;
	}
	
	/**
	 * Payment TM transform.
	 *
	 * @return the string
	 */
	//AT-4749
	public String paymentTMTransform() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		IntuitionPaymentStatusUpdateRequest request = (IntuitionPaymentStatusUpdateRequest) messageExchange
				.getRequest();
		
		if (request.getTrxType().equalsIgnoreCase("FUNDSIN")) {
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request
					.getAdditionalAttribute("fundsInRequest");
			messageExchange.setRequest(fundsInRequest);
		} else {
			FundsOutRequest fundsOutRequest = (FundsOutRequest) request.getAdditionalAttribute("fundsOutRequest");
			messageExchange.setRequest(fundsOutRequest);
		}
		
		return request.getTrxType();
	}
	
	/**
	 * Checks if is intuition payment historic data saved.
	 *
	 * @return true, if is intuition payment historic data saved
	 */
	//AT-5264
	public boolean isIntuitionPaymentHistoricDataSaved() {
		MessageExchange messageExchange = this.getGatewayMessageExchange();
		IntuitionHistoricPaymentsRequest intuitionPaymentRequest = (IntuitionHistoricPaymentsRequest) messageExchange
				.getRequest();

		boolean paymentInLocalSaveFlag = (boolean) intuitionPaymentRequest
				.getAdditionalAttribute("IntuitionHistoricPaymentInLocalSave");
		boolean paymentOutLocalSaveFlag = (boolean) intuitionPaymentRequest
				.getAdditionalAttribute("IntuitionHistoricPaymentOutLocalSave");

		return paymentInLocalSaveFlag || paymentOutLocalSaveFlag;
	}

}