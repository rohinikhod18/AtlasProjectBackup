package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.ObjectUtils;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
/**
 * The Class Dashboard.
 * @author abhijeetg
 */
public class Dashboard implements IDomain{
	
	/** The reg dashboard. */
	private RegistrationDashboard regDashboard;
	
	/** The payment in dashboard. */
	private PaymentDashboard paymentInDashboard;
	
	/** The payment out dashboard. */
	private PaymentDashboard paymentOutDashboard;
	
	/** The refresh on. */
	private String refreshOn;
	
	/** The treshold fulfiment time. */
	private String tresholdFulfimentTime;
	
	/** The user. */
	private UserProfile user;
	
	/** The error message. */
	private String errorMessage;

	/** The error code. */
	private String errorCode;

	/**
	 * Gets the error message.
	 *
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error code.
	 *
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserProfile getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * Gets the reg dashboard.
	 *
	 * @return the reg dashboard
	 */
	public RegistrationDashboard getRegDashboard() {
		return regDashboard;
	}

	/**
	 * Sets the reg dashboard.
	 *
	 * @param regDashboard the new reg dashboard
	 */
	public void setRegDashboard(RegistrationDashboard regDashboard) {
		this.regDashboard = regDashboard;
	}

	/**
	 * Gets the payment in dashboard.
	 *
	 * @return the payment in dashboard
	 */
	public PaymentDashboard getPaymentInDashboard() {
		return paymentInDashboard;
	}

	/**
	 * Sets the payment in dashboard.
	 *
	 * @param paymentInDashboard the new payment in dashboard
	 */
	public void setPaymentInDashboard(PaymentDashboard paymentInDashboard) {
		this.paymentInDashboard = paymentInDashboard;
	}

	/**
	 * Gets the payment out dashboard.
	 *
	 * @return the payment out dashboard
	 */
	public PaymentDashboard getPaymentOutDashboard() {
		return paymentOutDashboard;
	}

	/**
	 * Sets the payment out dashboard.
	 *
	 * @param paymentOutDashboard the new payment out dashboard
	 */
	public void setPaymentOutDashboard(PaymentDashboard paymentOutDashboard) {
		this.paymentOutDashboard = paymentOutDashboard;
	}

	/**
	 * Gets the reg pfx by geography as json.
	 *
	 * @return the reg pfx by geography as json
	 */
	public  String getRegPfxByGeographyAsJson(){
		
		if(!ObjectUtils.isObjectNull(regDashboard) && !ObjectUtils.isObjectNull(regDashboard.getRegPfxByGeography())){
			return JsonConverterUtil.convertToJsonWithoutNull(regDashboard.getRegPfxByGeography().getQueueRecordsPerCountry());
		}
		return "";
	}
	
	/**
	 * Gets the reg cfx by geography as json.
	 *
	 * @return the reg cfx by geography as json
	 */
	public  String getRegCfxByGeographyAsJson(){
		
		if(!ObjectUtils.isObjectNull(regDashboard) && !ObjectUtils.isObjectNull(regDashboard.getRegCfxByGeography())){
		return JsonConverterUtil.convertToJsonWithoutNull(regDashboard.getRegCfxByGeography().getQueueRecordsPerCountry());
		}
		return "";
	}
	
	/**
	 * Gets the reg pfx by business unit json string.
	 *
	 * @return the reg pfx by business unit json string
	 */
	public  String getRegPfxByBusinessUnitJsonString(){
		
		if(!ObjectUtils.isObjectNull(regDashboard) && !ObjectUtils.isObjectNull(regDashboard.getRegPfxByBusinessUnit())){
			return JsonConverterUtil.convertToJsonWithoutNull(regDashboard.getRegPfxByBusinessUnit().getQueueRecordsPerLegalEntity());
		}
		return "";
	}

	/**
	 * Gets the reg cfx by business unit json string.
	 *
	 * @return the reg cfx by business unit json string
	 */
	public  String getRegCfxByBusinessUnitJsonString(){
		
		if(!ObjectUtils.isObjectNull(regDashboard) && !ObjectUtils.isObjectNull(regDashboard.getRegCfxByBusinessUnit())){
			return JsonConverterUtil.convertToJsonWithoutNull(regDashboard.getRegCfxByBusinessUnit().getQueueRecordsPerLegalEntity());
		}
		return "";
	}
	
	/**
	 * Gets the reg pfx fulfilment json string.
	 *
	 * @return the reg pfx fulfilment json string
	 */
	public  String getRegPfxFulfilmentJsonString(){
		
		if(!ObjectUtils.isObjectNull(regDashboard) && !ObjectUtils.isObjectNull(regDashboard.getRegPfxFulfilment())){
			return JsonConverterUtil.convertToJsonWithoutNull(regDashboard.getRegPfxFulfilment().getFulfilmentGraph());
		}
		return "";
	}
	
	/**
	 * Gets the reg cfx fulfilment json string.
	 *
	 * @return the reg cfx fulfilment json string
	 */
	public  String getRegCfxFulfilmentJsonString(){
		
		if(!ObjectUtils.isObjectNull(regDashboard) && !ObjectUtils.isObjectNull(regDashboard.getRegCfxFulfilment())){
			return JsonConverterUtil.convertToJsonWithoutNull(regDashboard.getRegCfxFulfilment().getFulfilmentGraph());
		}
		return "";
	}
	
	/**
	 * Gets the payment in business unit json string.
	 *
	 * @return the payment in business unit json string
	 */
	public  String getPaymentInBusinessUnitJsonString(){
		
		if(!ObjectUtils.isObjectNull(paymentInDashboard) && !ObjectUtils.isObjectNull(paymentInDashboard.getBusinessUnit())){
			return JsonConverterUtil.convertToJsonWithoutNull(paymentInDashboard.getBusinessUnit().getQueueRecordsPerLegalEntity());
		}
		return "";
	}
	
	/**
	 * Gets the payment out business unit json string.
	 *
	 * @return the payment out business unit json string
	 */
	public  String getPaymentOutBusinessUnitJsonString(){
		
		if(!ObjectUtils.isObjectNull(paymentOutDashboard) && !ObjectUtils.isObjectNull(paymentOutDashboard.getBusinessUnit())){
			return JsonConverterUtil.convertToJsonWithoutNull(paymentOutDashboard.getBusinessUnit().getQueueRecordsPerLegalEntity());
		}
		return "";
	}
	
	/**
	 * Gets the payment in fulfilment json string.
	 *
	 * @return the payment in fulfilment json string
	 */
	public  String getPaymentInFulfilmentJsonString(){
		
		if(!ObjectUtils.isObjectNull(paymentInDashboard) && !ObjectUtils.isObjectNull(paymentInDashboard.getFulfilment())){
			return JsonConverterUtil.convertToJsonWithoutNull(paymentInDashboard.getFulfilment().getFulfilmentGraph());
		}
		return "";
	}
	
	/**
	 * Gets the payment out fulfilment json string.
	 *
	 * @return the payment out fulfilment json string
	 */
	public  String getPaymentOutFulfilmentJsonString(){
		
		if(!ObjectUtils.isObjectNull(paymentOutDashboard) && !ObjectUtils.isObjectNull(paymentOutDashboard.getFulfilment())){
			return JsonConverterUtil.convertToJsonWithoutNull(paymentOutDashboard.getFulfilment().getFulfilmentGraph());
		}
		return "";
	}

	
	/**
	 * Gets the refresh on.
	 *
	 * @return the refresh on
	 */
	public String getRefreshOn() {
		return refreshOn;
	}

	/**
	 * Sets the refresh on.
	 *
	 * @param refreshOn the new refresh on
	 */
	public void setRefreshOn(String refreshOn) {
		this.refreshOn = refreshOn;
	}

	/**
	 * Gets the treshold fulfiment time.
	 *
	 * @return the treshold fulfiment time
	 */
	public String getTresholdFulfimentTime() {
		return tresholdFulfimentTime;
	}

	/**
	 * Sets the treshold fulfiment time.
	 *
	 * @param tresholdFulfimentTime the new treshold fulfiment time
	 */
	public void setTresholdFulfimentTime(String tresholdFulfimentTime) {
		this.tresholdFulfimentTime = tresholdFulfimentTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dashboard [regDashboard=" + regDashboard + ", paymentInDashboard=" + paymentInDashboard
				+ ", paymentOutDashboard=" + paymentOutDashboard + ", refreshOn=" + refreshOn + ", user=" + user
				+ ", errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
	
}
