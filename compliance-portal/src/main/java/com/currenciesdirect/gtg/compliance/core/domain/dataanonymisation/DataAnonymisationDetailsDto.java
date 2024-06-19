package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.Onfido;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;

/**
 * The Class DataAnonymisationDetailsDto.
 */
public class DataAnonymisationDetailsDto extends BaseDetailDto  implements IRegistrationDetails {

	/** The other contacts. */
	private List<ContactWrapper> otherContacts;

	/** The internal rule. */
	private InternalRule internalRule;

	/** The kyc. */
	private Kyc kyc;

	/** The sanction. */
	private Sanction sanction;

	/** The fraugster. */
	private Fraugster fraugster;
	
	/** The onfido. */
	private Onfido onfido;
	
	/**
	 * Gets the internal rule.
	 *
	 * @return the internal rule
	 */
	public InternalRule getInternalRule() {
		return internalRule;
	}

	/**
	 * Sets the internal rule.
	 *
	 * @param internalRule
	 *            the new internal rule
	 */
	public void setInternalRule(InternalRule internalRule) {
		this.internalRule = internalRule;
	}

	/**
	 * Gets the kyc.
	 *
	 * @return the kyc
	 */
	public Kyc getKyc() {
		return kyc;
	}

	/**
	 * Sets the kyc.
	 *
	 * @param kyc
	 *            the new kyc
	 */
	public void setKyc(Kyc kyc) {
		this.kyc = kyc;
	}

	/**
	 * Gets the other contacts.
	 *
	 * @return the other contacts
	 */
	public List<ContactWrapper> getOtherContacts() {
		return otherContacts;
	}

	/**
	 * Sets the other contacts.
	 *
	 * @param otherContacts
	 *            the new other contacts
	 */
	public void setOtherContacts(List<ContactWrapper> otherContacts) {
		this.otherContacts = otherContacts;
	}

	public Sanction getSanction() {
		return sanction;
	}

	public void setSanction(Sanction sanction) {
		this.sanction = sanction;
	}

	public Fraugster getFraugster() {
		return fraugster;
	}

	public void setFraugster(Fraugster fraugster) {
		this.fraugster = fraugster;
	}

	/**
	 * @return the onfido
	 */
	public Onfido getOnfido() {
		return onfido;
	}

	/**
	 * @param onfido the onfido to set
	 */
	public void setOnfido(Onfido onfido) {
		this.onfido = onfido;
	}
	
}
