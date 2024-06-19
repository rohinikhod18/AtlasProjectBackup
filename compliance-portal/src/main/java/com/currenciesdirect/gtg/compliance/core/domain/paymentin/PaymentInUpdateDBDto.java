package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateDBDto;

public class PaymentInUpdateDBDto extends PaymentUpdateDBDto {

	/** The paymentInstatus status. */
	private String paymentInStatus;	
	
	private Integer paymentInId;	
	
	/** The activity logs. */
	private List<PaymentInActivityLogDto> activityLogs;

	public String getPaymentInStatus() {
		return paymentInStatus;
	}

	public void setPaymentInStatus(String paymentInStatus) {
		this.paymentInStatus = paymentInStatus;
	}

	public Integer getPaymentInId() {
		return paymentInId;
	}

	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	public List<PaymentInActivityLogDto> getActivityLogs() {
		return activityLogs;
	}

	public void setActivityLogs(List<PaymentInActivityLogDto> activityLogs) {
		this.activityLogs = activityLogs;
	}
}
