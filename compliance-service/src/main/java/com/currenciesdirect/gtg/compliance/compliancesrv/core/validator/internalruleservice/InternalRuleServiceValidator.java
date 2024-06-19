/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.internalruleservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

/**
 * The Class InternalRuleServiceValidator.
 *
 * @author manish
 */
public class InternalRuleServiceValidator  implements IValidator{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalRuleServiceValidator.class);
	
	/** The ip pattern. */
	private Pattern ipPattern;
	
	/** The email pattern. */
	private Pattern emailPattern;
	
	/** The name pattern. */
	private Pattern namePattern;
	
	/** The matcher. */
	private Matcher matcher;

	/** The Constant IPADDRESS_PATTERN. */
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/** The email. */
	private static final String	EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/** The name. */
	private static final String NAME_PATTERN = "^[a-zA-Z àèìòùÀÈÌÒÙáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãñõÃÑÕäëïöüÿÄËÏÖÜŸçÇßØøÅåÆæœÐ-]{0,100}$";
	
	
	/** The ivalidator. */
	private static IValidator ivalidator = new InternalRuleServiceValidator();
	
	/**
	 * Instantiates a new internal rule service validator.
	 */
	private InternalRuleServiceValidator() {
		ipPattern = Pattern.compile(IPADDRESS_PATTERN);
		emailPattern = Pattern.compile(EMAIL_PATTERN); 
		namePattern = Pattern.compile(NAME_PATTERN);
	}

	/**
	 * Gets the single instance of InternalRuleServiceValidator.
	 *
	 * @return single instance of InternalRuleServiceValidator
	 */
	public static IValidator getInstance(){
		return ivalidator;
	}

	/**
	 * Validate IP format.
	 *
	 * @param ip the ip
	 * @return true, if successful
	 */
	private boolean validateIPFormat(final String ip) {
		matcher = ipPattern.matcher(ip);
		return (matcher.matches());
		
	}
	
	/**
	 * Validate email format.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean validateEmailFormat(final String email) {
		matcher = emailPattern.matcher(email);
		return (matcher.matches());
	}
	
	/**
	 * Validate name format.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	private boolean validateNameFormat(final String name) {
		matcher = namePattern.matcher(name);
		return (matcher.matches());
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator#validateRequest(com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact)
	 */
	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isValidate = Boolean.FALSE;
		try {
			if (validateName(request.getFullName())
					&& validateEmail(request.getEmail())
					&& validateIP(request.getIpAddress()) 
					) {
					isValidate = Boolean.TRUE;
				}

		} catch (Exception exception) {
			LOGGER.debug("Something went wrong", exception);
			return Boolean.FALSE;
		}
		return isValidate;
	}
	
	/**
	 * Validate name.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	private boolean validateName(String name){
		return (!name.isEmpty() &&  validateNameFormat(name));
	}

	/**
	 * Validate email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean validateEmail(String email){
		return (!email.isEmpty() &&  validateEmailFormat(email));
	}
	
	/**
	 * Validate IP.
	 *
	 * @param ip the ip
	 * @return true, if successful
	 */
	private boolean validateIP(String ip){
		return (!ip.isEmpty() &&  validateIPFormat(ip));
	}
}
