package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;

/**
 * @author bnt
 *
 */
public class FieldValidator {
	private static final Logger LOG = LoggerFactory.getLogger(FieldValidator.class);
	private static final String INVALID_INPUT = "check strings and fields can't be null. both must be of same size";
	private static final String BLANK = "";
	private static final String CAN_NOT_BE_BLANK = "can not be blank";
	private List<ValidationErrors> errors=Collections.emptyList();

	/**
	 * Instantiates a new field validator.
	 */
	public FieldValidator() {
		this(new ArrayList<>());
	}

	private FieldValidator(List<ValidationErrors> errors) {
		this.errors = errors;
	}

	/**
	 * @return
	 */
	public boolean isFailed() {
		return errors.isEmpty();
	}

	/**
	 * @return
	 */
	public List<ValidationErrors> getErrors() {
		return errors;
	}

	/**
	 * @param object
	 * @return
	 */
	public boolean isNULL(Object object) {
		return null == object;
	}

	/**
	 * @param str
	 * @return
	 */
	public boolean isEmpty(String str) {
		if(null == str){
			return true;
		}
		return str.trim().length() == 0;
	}

	/**
	 * @param str
	 * @param fields
	 */
	public void isEmpty(String[] str, String[] fields) {
		if (isNULL(str) || isNULL(fields) || fields.length != str.length) {
			throw new CompletionException(new Exception(INVALID_INPUT));
		}
		List<String> errorFields = new ArrayList<>();
		for (int i = 0; i < str.length; i++) {
			if (isEmpty(str[i])) {
				errorFields.add(fields[i]);
			}
		}
		if (!errorFields.isEmpty()) {
			errors.add(new ValidationErrors(CAN_NOT_BE_BLANK, errorFields));
		}
	}

	/**
	 * @param str
	 * @param fields
	 * @param format
	 */
	public void isDateInFormat(String[] str, String[] fields, String format) {
		if (isNULL(str) || isNULL(fields) || fields.length != str.length) {
			throw new CompletionException(new Exception(INVALID_INPUT));
		}
		List<String> errorFields = new ArrayList<>();
		for (int i = 0; i < str.length; i++) {
			if (!isThisDateValid(str[i], format)) {
				errorFields.add(fields[i]);
			}
		}
		if (!errorFields.isEmpty()) {
			errors.add(new ValidationErrors("must be in format of ".concat(format), errorFields));
		}
	}

	private boolean isThisDateValid(String dateToValidate, String dateFromat) {
		if (isEmpty(dateToValidate)) {
			throw new CompletionException(new Exception(INVALID_INPUT));
		}
		try {
			if(dateFromat.equals(Constants.RFC_TIMESTAMP_FORMAT)){
				DateTimeFormatter.getDateTimeInRFC3339(dateToValidate);
			}
			else{
				DateTimeFormatter.getDateInRFC3339(dateToValidate);
			}
			
		} catch (Exception e) {
			LOG.debug("Error validating date", e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * Validate date.
	 *
	 * @param dateToValidate the date to validate
	 * @param dateFromat the date fromat
	 * @param field the field
	 */
	public void validateDate(String dateToValidate, String dateFromat,String field) {
		if (isEmpty(dateToValidate)) {
			throw new CompletionException(new Exception(INVALID_INPUT));
		}
		List<String> errorFields = new ArrayList<>();
			if (!isThisDateValid(dateToValidate, dateFromat)) {
				errorFields.add(field);
			}
		if (!errorFields.isEmpty()) {
			errors.add(new ValidationErrors("must be in format of ".concat(dateFromat), errorFields));
		}
	}
	
	/**
	 * @param message
	 * @param errorField
	 */
	public void addError(String message, String errorField){
		List<String> errorFields = new ArrayList<>(1);
		errorFields.add(errorField);
		if(null==errors)
			errors = new ArrayList<>();
		errors.add(new ValidationErrors(message, errorFields));
	}
	
	
	
	/**
	 * @param str
	 * @param fields
	 */
	public void isValidObject(Object[] str, String[] fields) {
		if (isNULL(str) || isNULL(fields) || fields.length != str.length) {
			throw new CompletionException(new Exception(INVALID_INPUT));
		}
		
		List<String> errorFields = new ArrayList<>();
		for (int i = 0; i < str.length; i++) {
			if (isEmptyObject(str[i])) {
				errorFields.add(fields[i]);
			}
		}
		if (!errorFields.isEmpty()) {
			errors.add(new ValidationErrors(CAN_NOT_BE_BLANK, errorFields));
		}
	}
	
	private boolean isEmptyObject(Object in){
		return null == in  || in.toString().trim().isEmpty();
	}
	
	/**
	 * Merge errors.
	 *
	 * @param fieldValidator the field validator
	 */
	public void mergeErrors(FieldValidator fieldValidator){
		this.errors.addAll(fieldValidator.getErrors());
	}
	
	
	/**
	 * Checks if is field valid.
	 *
	 * @param field the field
	 * @return the boolean
	 */
	public Boolean isFieldValid(String field){
		Boolean result = Boolean.TRUE;
	    for (ValidationErrors validationErrors : errors) {
	    	result =  !validationErrors.getFields().contains(field);
		} 
	    return result;
	}

	public void isValidObjectOnUpdate(Object[] requestObject, String[] requestFields) {
		
		List<String> errorFields = new ArrayList<>();
		for (int i = 0; i < requestObject.length; i++) {
			if (isBlankObject(requestObject[i])) {
				errorFields.add(requestFields[i]);
			}
		}
		if (!errorFields.isEmpty()) {
			errors.add(new ValidationErrors(CAN_NOT_BE_BLANK, errorFields));
		}
		
	}
	
	private boolean isBlankObject(Object in){
		return BLANK == in;
	}
}
