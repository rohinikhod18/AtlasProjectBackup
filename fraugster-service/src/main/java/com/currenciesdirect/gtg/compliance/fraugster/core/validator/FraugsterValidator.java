/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

/**
 * The Class FraugsterValidator.
 *
 * @author manish
 */
public class FraugsterValidator implements IValidator {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterValidator.class);

	/** The Constant ivalidator. */
	public static final IValidator ivalidator = new FraugsterValidator();

	/**
	 * Instantiates a new fraugster validator.
	 */
	private FraugsterValidator() {

	}

	/**
	 * Gets the single instance of FraugsterValidator.
	 *
	 * @return single instance of FraugsterValidator
	 */
	public static IValidator getInstance() {

		return ivalidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.fraugster.core.validator.IValidator#
	 * validateFraugsterSignupRequest(com.currenciesdirect.gtg.compliance.
	 * commons.domain.fraugster.FraugsterSignupContactRequest)
	 */
	@Override
	public Boolean validateFraugsterSignupRequest(FraugsterSignupContactRequest contact) {

		Boolean isvalidate = Boolean.FALSE;

		try {

			if (!contact.getEventType().isEmpty() && !contact.getCustID().isEmpty()
					&& !contact.getTransactionID().isEmpty()) {
				isvalidate = Boolean.TRUE;
			}
		} catch (Exception e) {
			logDebug(e);
			return Boolean.FALSE;
		}

		return isvalidate;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.fraugster.core.validator.IValidator#
	 * validateFraugsterOnUpdateRequest(com.currenciesdirect.gtg.compliance.
	 * commons.domain.fraugster.FraugsterOnUpdateContactRequest)
	 */
	@Override
	public Boolean validateFraugsterOnUpdateRequest(FraugsterOnUpdateContactRequest contact) {

		Boolean isvalidate = Boolean.FALSE;

		try {

			if (!contact.getEventType().isEmpty() && !contact.getCustID().isEmpty()
					&& !contact.getTransactionID().isEmpty()) {
				isvalidate = Boolean.TRUE;
			}
		} catch (Exception e) {
			logDebug(e);
			return Boolean.FALSE;
		}

		return isvalidate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.fraugster.core.validator.IValidator#
	 * validateFraugsterPaymentsOutRequest(com.currenciesdirect.gtg.compliance.
	 * commons.domain.fraugster.FraugsterPaymentsOutContactRequest)
	 */
	@Override
	public Boolean validateFraugsterPaymentsOutRequest(FraugsterPaymentsOutContactRequest contact) {

		Boolean isvalidate = Boolean.FALSE;

		try {

			if (!contact.getEventType().isEmpty() && !contact.getCustID().isEmpty()
					&& !contact.getTransactionID().isEmpty()) {
				isvalidate = Boolean.TRUE;
			}
		} catch (Exception e) {
			logDebug(e);
			return Boolean.FALSE;
		}

		return isvalidate;
	}

	/**
	 * Log debug.
	 *
	 * @param exception
	 *            the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class FraugsterServiceImpl :", exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.fraugster.core.validator.IValidator#
	 * validateFraugsterPaymentsInRequest(com.currenciesdirect.gtg.compliance.
	 * commons.domain.fraugster.FraugsterPaymentsInContactRequest)
	 */
	@Override
	public Boolean validateFraugsterPaymentsInRequest(FraugsterPaymentsInContactRequest contact)
			throws FraugsterException {
		Boolean isvalidate = Boolean.FALSE;

		try {

			if (!contact.getEventType().isEmpty() && !contact.getCustID().isEmpty()
					&& !contact.getTransactionID().isEmpty()) {
				isvalidate = Boolean.TRUE;
			}
		} catch (Exception e) {
			logDebug(e);
			return Boolean.FALSE;
		}

		return isvalidate;
	}

}
