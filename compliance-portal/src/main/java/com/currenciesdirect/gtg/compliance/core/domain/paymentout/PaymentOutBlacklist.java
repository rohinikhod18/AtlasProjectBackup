package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;

public class PaymentOutBlacklist {
	
	private Blacklist contactBlacklist;
	
	private Blacklist benficiaryBlacklist;
	
	private Blacklist bankBlacklist;

	public Blacklist getContactBlacklist() {
		return contactBlacklist;
	}

	public Blacklist getBenficiaryBlacklist() {
		return benficiaryBlacklist;
	}

	public Blacklist getBankBlacklist() {
		return bankBlacklist;
	}

	public void setContactBlacklist(Blacklist contactBlacklist) {
		this.contactBlacklist = contactBlacklist;
	}

	public void setBenficiaryBlacklist(Blacklist benficiaryBlacklist) {
		this.benficiaryBlacklist = benficiaryBlacklist;
	}

	public void setBankBlacklist(Blacklist bankBlacklist) {
		this.bankBlacklist = bankBlacklist;
	}

}
