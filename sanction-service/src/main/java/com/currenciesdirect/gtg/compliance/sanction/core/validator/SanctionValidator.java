/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionValidator.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.sanction.core.ISanctionvalidator;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;

/**
 * @author manish
 *
 */
public class SanctionValidator implements ISanctionvalidator {

	private static ISanctionvalidator validateSanction = null;
	
	private static final Logger LOG = LoggerFactory.getLogger(SanctionValidator.class);
	
	 /**
 	 * Instantiates a new sanction validator.
 	 */
 	private SanctionValidator() {
		
	}
	 
	 public static ISanctionvalidator getInstance() {
		 if(validateSanction == null){
			 validateSanction = new SanctionValidator();
		 }
		 return validateSanction;
	 }
	  
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.sanction.core.ISanctionvalidator#validateRequest(com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionRequest)
	 */
	@Override
	public FieldValidator validateRequest(SanctionRequest sanctionRequest) {
		
		FieldValidator fieldValidator = new FieldValidator();
		try {
			if (sanctionRequest.getContactrequests() != null) {
				for (SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()) {
					fieldValidator.mergeErrors(validateContactRequest(contactRequest));
				}
			}

			if (sanctionRequest.getBeneficiaryRequests() != null) {
				for (SanctionBeneficiaryRequest beneficiaryRequest : sanctionRequest.getBeneficiaryRequests()) {
					fieldValidator.mergeErrors(validateBeneficiaryRequest(beneficiaryRequest));
				}
			}

			if (sanctionRequest.getBankRequests() != null) {
				for (SanctionBankRequest bankRequest : sanctionRequest.getBankRequests()) {
					fieldValidator.mergeErrors(validateBankRequest(bankRequest));
				}
			}
		} catch (Exception e) {
			LOG.debug("error in validator ", e);
			fieldValidator.addError(Constants.VALIDATION_ERROR, "");
		}
		return fieldValidator;

	}
	
	private FieldValidator validateContactRequest(SanctionContactRequest contactRequest) {
		FieldValidator fieldValidator = new FieldValidator();
		fieldValidator.isValidObject(
				new Object[] { contactRequest.getFullName(), contactRequest.getSanctionId() },
				new String[] { "fullname", "contact sanctionID" });

		return fieldValidator;
	}
	
	private FieldValidator validateBeneficiaryRequest(SanctionBeneficiaryRequest beneficiaryRequest) {
		FieldValidator fieldValidator = new FieldValidator();
		fieldValidator.isValidObject(
				new Object[] { beneficiaryRequest.getFullName(), beneficiaryRequest.getSanctionId(),
						beneficiaryRequest.getCountry() },
				new String[] { "fullname", "beneficairy sanctionID", "benficiary country" });

		return fieldValidator;
	}
	
	private FieldValidator validateBankRequest(SanctionBankRequest bankRequest){
		FieldValidator fieldValidator = new FieldValidator();
		fieldValidator.isValidObject(
				new Object[] { bankRequest.getCountry(), bankRequest.getSanctionId(),
						bankRequest.getBankName() },
				new String[] { "bank country", "bank sanctionID", "bank name" });

		return fieldValidator;
	}
}
