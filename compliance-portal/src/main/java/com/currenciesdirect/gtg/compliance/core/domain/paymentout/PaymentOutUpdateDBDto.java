package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateDBDto;

/**
 * The Class PaymentOutUpdateDBDto.
 */
public class PaymentOutUpdateDBDto extends PaymentUpdateDBDto {
	
	/** The contact status. */
	private String paymentOutStatus;	
	
	private Integer paymentOutId;	
	
	/** The activity logs. */
	private List<PaymentOutActivityLogDto> activityLogs;	
	
	private String tradeBeneficiayId;

	public String getPaymentOutStatus() {
		return paymentOutStatus;
	}

	public void setPaymentOutStatus(String paymentOutStatus) {
		this.paymentOutStatus = paymentOutStatus;
	}

	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public List<PaymentOutActivityLogDto> getActivityLogs() {
		return activityLogs;
	}

	public void setActivityLogs(List<PaymentOutActivityLogDto> activityLogs) {
		this.activityLogs = activityLogs;
	}

	public String getTradeBeneficiayId() {
		return tradeBeneficiayId;
	}

	public void setTradeBeneficiayId(String tradeBeneficiayId) {
		this.tradeBeneficiayId = tradeBeneficiayId;
	}	
}
