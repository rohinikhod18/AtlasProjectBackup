package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;

public class PaymentInDetailsDto extends PaymentDetailsDto {

	private PaymentInInfo paymentInInfo ;	
	
	private Sanction thirdPartySanction;
	
	private Blacklist debitorBlacklist;
	
	private Boolean thirdPartyPayment;
	
	private String primaryContactName;
	
	private Integer noOfContactsForAccount;
	
	
	
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	public Blacklist getDebitorBlacklist() {
		return debitorBlacklist;
	}

	public void setDebitorBlacklist(Blacklist debitorBlacklist) {
		this.debitorBlacklist = debitorBlacklist;
	}

	public PaymentInInfo getPaymentInInfo() {
		return paymentInInfo;
	}

	public void setPaymentInInfo(PaymentInInfo paymentInInfo) {
		this.paymentInInfo = paymentInInfo;
	}
	
	public Sanction getThirdPartySanction() {
		return thirdPartySanction;
	}

	public void setThirdPartySanction(Sanction thirdPartySanction) {
		this.thirdPartySanction = thirdPartySanction;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public Integer getNoOfContactsForAccount() {
		return noOfContactsForAccount;
	}

	public void setNoOfContactsForAccount(Integer noOfContactsForAccount) {
		this.noOfContactsForAccount = noOfContactsForAccount;
	}

	
	
}
