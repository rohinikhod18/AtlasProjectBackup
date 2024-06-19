package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
/**
 * The Class CustomCheck.
 */
public class CustomCheck implements IDomain {
	
	private IpCheck ipCheck;

	private GlobalCheck globalCheck;
	
	private CountryCheck countryCheck;
	
	private PaymentOutCustomCheck paymentOutCustomCheck;

	private Integer customCheckTotalRecords;
	
	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;
	
	public IpCheck getIpCheck() {
		return ipCheck;
	}

	public GlobalCheck getGlobalCheck() {
		return globalCheck;
	}

	public CountryCheck getCountryCheck() {
		return countryCheck;
	}

	public void setIpCheck(IpCheck ipCheck) {
		this.ipCheck = ipCheck;
	}

	public void setGlobalCheck(GlobalCheck globalCheck) {
		this.globalCheck = globalCheck;
	}

	public void setCountryCheck(CountryCheck countryCheck) {
		this.countryCheck = countryCheck;
	}

	
	public Integer getCustomCheckTotalRecords() {
		return customCheckTotalRecords;
	}

	public void setCustomCheckTotalRecords(Integer customCheckTotalRecords) {
		this.customCheckTotalRecords = customCheckTotalRecords;
	}

	public PaymentOutCustomCheck getPaymentOutCustomCheck() {
		return paymentOutCustomCheck;
	}

	public void setPaymentOutCustomCheck(PaymentOutCustomCheck paymentOutCustomCheck) {
		this.paymentOutCustomCheck = paymentOutCustomCheck;
	}

	public Integer getPassCount() {
		return passCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}
	
}
