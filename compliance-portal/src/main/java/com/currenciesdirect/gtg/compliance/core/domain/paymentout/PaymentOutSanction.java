package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.Sanction;

/**
 * The Class PaymentOutSanction.
 */
public class PaymentOutSanction {

	private Sanction contactSanction;
	
	private Sanction beneficiarySanction;
	
	private Sanction bankSanction;

	public Sanction getContactSanction() {
		return contactSanction;
	}

	public Sanction getBeneficiarySanction() {
		return beneficiarySanction;
	}

	public Sanction getBankSanction() {
		return bankSanction;
	}

	public void setContactSanction(Sanction contactSanction) {
		this.contactSanction = contactSanction;
	}

	public void setBeneficiarySanction(Sanction beneficiarySanction) {
		this.beneficiarySanction = beneficiarySanction;
	}

	public void setBankSanction(Sanction bankSanction) {
		this.bankSanction = bankSanction;
	}
	
}
