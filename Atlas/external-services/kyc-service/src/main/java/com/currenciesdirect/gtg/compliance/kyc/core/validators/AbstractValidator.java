/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

/**
 * The Class AbstractValidator.
 *
 * @author manish
 */
public abstract class AbstractValidator {
	
	private static final String POST_CODE = "post_code";
	private static final String FIRST_NAME = "first_name";
	private static final String ERROR_IN_VALIDATOR = "error in validator ";
	private static final String LAST_NAME = "last_name";
	/** The Constant LOGER. */
	private static final org.slf4j.Logger LOGER = org.slf4j.LoggerFactory.getLogger(AbstractValidator.class);

	/**
	 * Checks if is null or empty.
	 *
	 * @param str the str
	 * @return true, if is null or empty
	 */
	public boolean isNullOrEmpty(String str){
		Boolean result = Boolean.TRUE;
		if(null!=str && !str.isEmpty()){
			result = Boolean.FALSE;
		}
		return result;
	}
	

	/**
	 * Validate KYC request last name.
	 *
	 * @param request the request
	 * @return the field validator
	 */
	public FieldValidator validateKYCRequestLastName(KYCContactRequest request){
		FieldValidator validator = new FieldValidator();
		try{
			validator.isValidObject(
					new Object[] { 
							request.getPersonalDetails().getSurName()},
					new String[] {LAST_NAME });
			
			
		} catch (Exception exception){
			LOGER.debug(ERROR_IN_VALIDATOR, exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}
		
		return validator;
	}
	
	
	/**
	 * Validate KYC request fname lname dob post code.
	 *
	 * @param request the request
	 * @return the field validator
	 */
	public FieldValidator validateKYCRequestFnameLnameDobPostCode(KYCContactRequest request){
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					new Object[] { request.getPersonalDetails().getForeName(),
							request.getPersonalDetails().getSurName(), request.getPersonalDetails().getDob(),
							request.getAddress().getPostCode() },
					new String[] { FIRST_NAME, LAST_NAME, "dob", POST_CODE });

		} catch (Exception exception) {
			LOGER.debug(ERROR_IN_VALIDATOR, exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}
	
	/**
	 * Validate KYC request last name post code.
	 *
	 * @param request the request
	 * @return the field validator
	 */
	public FieldValidator validateKYCRequestLastNamePostCode(KYCContactRequest request){
		FieldValidator validator = new FieldValidator();
		try{
			validator.isValidObject(
					new Object[] { 
							request.getPersonalDetails().getSurName(),request.getAddress().getPostCode()},
					new String[] {LAST_NAME,POST_CODE });
			
			
		} catch (Exception exception){
			LOGER.debug(ERROR_IN_VALIDATOR, exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}
		
		return validator;
	}
	
	/**
	 * Validate KYC request fname lname dob post code street city.
	 *
	 * @param request the request
	 * @return the field validator
	 */
	public FieldValidator validateKYCRequestFnameLnameDobPostCodeStreetCity(KYCContactRequest request){
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					new Object[] { request.getPersonalDetails().getForeName(),
							request.getPersonalDetails().getSurName(), request.getPersonalDetails().getDob(),
							 request.getAddress().getStreet(), request.getAddress().getCity(),
							request.getAddress().getPostCode() },
					new String[] { FIRST_NAME, LAST_NAME, "dob", "street", "city", POST_CODE });

		} catch (Exception exception) {
			LOGER.debug(ERROR_IN_VALIDATOR, exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}
	
	/**
	 * Validate KYC request fname lname dob post code street city building no.
	 *
	 * @param request the request
	 * @return the field validator
	 */
	public FieldValidator validateKYCRequestFnameLnameDobPostCodeStreetCityBuildingNo(KYCContactRequest request){
		FieldValidator validator = new FieldValidator();
		try {
			validator.isValidObject(
					new Object[] { request.getPersonalDetails().getForeName(),
							request.getPersonalDetails().getSurName(), request.getPersonalDetails().getDob(),
							request.getAddress().getBuildingNumber(),
							request.getAddress().getStreet(), request.getAddress().getCity(),
							request.getAddress().getPostCode() },
					new String[] { FIRST_NAME, LAST_NAME, "dob", "building_number", "street", "city", POST_CODE });

		} catch (Exception exception) {
			LOGER.debug(ERROR_IN_VALIDATOR, exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}


}
