package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationCfxContactDetailsDto;

/**
 * The Class RegistrationCfxDetailsDto.
 * 
 * @author abhijeetg
 */
public class RegistrationCfxDetailsDto extends BaseDetailDto implements IRegistrationDetails {

	/** The contact details. */
	private List<RegistrationCfxContactDetailsDto> contactDetails;

	/** The internal rule. */
	private List<InternalRule> internalRule;

	/** The kyc details. */
	private KycDetails kycDetails;

	/** The sanction details. */
	private SanctionDetails sanctionDetails;

	/** The fraugster details. */
	private FraugsterDetails fraugsterDetails;
	
	/** The intuition details. */
	private IntuitionDetails intuitionDetails; //At-4114

	/**
	 * Gets the internal rule.
	 *
	 * @return the internal rule
	 */
	public List<InternalRule> getInternalRule() {
		return internalRule;
	}

	/**
	 * Sets the internal rule.
	 *
	 * @param internalRule
	 *            the new internal rule
	 */
	public void setInternalRule(List<InternalRule> internalRule) {
		this.internalRule = internalRule;
	}

	/**
	 * Gets the contact details.
	 *
	 * @return the contact details
	 */
	public List<RegistrationCfxContactDetailsDto> getContactDetails() {
		return contactDetails;
	}

	/**
	 * Sets the contact details.
	 *
	 * @param contactDetails
	 *            the new contact details
	 */
	public void setContactDetails(List<RegistrationCfxContactDetailsDto> contactDetails) {
		this.contactDetails = contactDetails;
	}

	/**
	 * Gets the kyc details.
	 *
	 * @return the kyc details
	 */
	public KycDetails getKycDetails() {
		return kycDetails;
	}

	/**
	 * Sets the kyc details.
	 *
	 * @param kycDetails
	 *            the new kyc details
	 */
	public void setKycDetails(KycDetails kycDetails) {
		this.kycDetails = kycDetails;
	}

	/**
	 * Gets the sanction details.
	 *
	 * @return the sanction details
	 */
	public SanctionDetails getSanctionDetails() {
		return sanctionDetails;
	}

	/**
	 * Sets the sanction details.
	 *
	 * @param sanctionDetails
	 *            the new sanction details
	 */
	public void setSanctionDetails(SanctionDetails sanctionDetails) {
		this.sanctionDetails = sanctionDetails;
	}

	/**
	 * Gets the fraugster details.
	 *
	 * @return the fraugster details
	 */
	public FraugsterDetails getFraugsterDetails() {
		return fraugsterDetails;
	}

	/**
	 * Sets the fraugster details.
	 *
	 * @param fraugsterDetails
	 *            the new fraugster details
	 */
	public void setFraugsterDetails(FraugsterDetails fraugsterDetails) {
		this.fraugsterDetails = fraugsterDetails;
	}

	/**
	 * @return the intuitionDetails
	 */
	public IntuitionDetails getIntuitionDetails() {
		return intuitionDetails;
	}

	/**
	 * @param intuitionDetails the intuitionDetails to set
	 */
	public void setIntuitionDetails(IntuitionDetails intuitionDetails) {
		this.intuitionDetails = intuitionDetails;
	}
	
	

}
