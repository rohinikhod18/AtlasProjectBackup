package com.currenciesdirect.gtg.compliance.core.domain.registration;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.FraugsterDetails;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.IntuitionDetails;
import com.currenciesdirect.gtg.compliance.core.domain.KycDetails;
import com.currenciesdirect.gtg.compliance.core.domain.SanctionDetails;

/**
 * The Class RegistrationCfxContactDetailsDto.
 * 
 */
public class RegistrationCfxContactDetailsDto  extends BaseDetailDto  implements IRegistrationDetails {

		/** The other contacts. */
		private List<ContactWrapper> otherContacts;

		/** The internal rule. */
		private List<InternalRule> internalRule;
		
		/** The kyc details. */
		private KycDetails kycDetails;
		
		/** The sanction details. */
		private SanctionDetails sanctionDetails;
		
		/** The fraugster details. */
		private FraugsterDetails fraugsterDetails;
		
		//AT-4114
		/** The intuition details. */ 
		private IntuitionDetails intuitionDetails; 
		
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
		 * @param otherContacts the new other contacts
		 */
		public void setOtherContacts(List<ContactWrapper> otherContacts) {
			this.otherContacts = otherContacts;
		}

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
		 * @param internalRule the new internal rule
		 */
		public void setInternalRule(List<InternalRule> internalRule) {
			this.internalRule = internalRule;
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
		 * @param kycDetails the new kyc details
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
		 * @param sanctionDetails the new sanction details
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
		 * @param fraugsterDetails the new fraugster details
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

