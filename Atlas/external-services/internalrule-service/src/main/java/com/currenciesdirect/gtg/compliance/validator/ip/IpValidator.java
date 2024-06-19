/**
 * 
 */
package com.currenciesdirect.gtg.compliance.validator.ip;

import javax.validation.ValidationException;

import com.currenciesdirect.gtg.compliance.core.IValidator;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;
import com.currenciesdirect.gtg.compliance.validator.RegxConstants;

/**
 * The Class IpValidator.
 *
 * @author manish
 */
public class IpValidator implements IValidator<IpServiceRequest> {

	/** The ivalidator. */
	private static IValidator<IpServiceRequest> ivalidator = new IpValidator();

	/**
	 * Instantiates a new ip validator.
	 */
	private IpValidator() {
	}

	/**
	 * Gets the single instance of IpValidator.
	 *
	 * @return single instance of IpValidator
	 */
	public static IValidator<IpServiceRequest> getInstance() {
		synchronized (IpValidator.class) {
			if (ivalidator == null) {
				ivalidator = new IpValidator();
			}
		}
		return ivalidator;
	}

	/**
	 * Validate IP format.
	 *
	 * @param ip
	 *            the ip
	 * @return true, if successful
	 */
	private boolean validateIPFormat(final String ip) {

		return (RegxConstants.validate(RegxConstants.IP_ADDRESS, ip));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.core.IValidator#validate(java.lang.
	 * Object)
	 */
	@Override
	public Boolean validate(IpServiceRequest ipRequest) throws IpException{
	Boolean result= Boolean.FALSE;
		try {

			if (null != ipRequest.getIpAddress() && !ipRequest.getIpAddress().isEmpty()
					&& validateIPFormat(ipRequest.getIpAddress())) {
				result= Boolean.TRUE;
				return result;
			}

		} catch (Exception exception) {
			throw new ValidationException(new IpException(IpErrors.IP_GENERIC_EXCEPTION, exception));
		}
		result= Boolean.FALSE;
		return result;
	}

}
