package com.currenciesdirect.gtg.compliance.commons.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;


/**
 * The Class FieldValidator.
 *
 * @author bnt
 */
public class FieldValidator {
	
	/** The Constant RFC_TIMESTAMP_FORMAT. */
	private static final String RFC_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FieldValidator.class);
	
	/** The Constant INVALID_INPUT. */
	private static final String INVALID_INPUT = "check strings and fields can't be null. both must be of same size";
	
	/** The errors. */
	private List<ValidationErrors> errors;

	/**
	 * Instantiates a new field validator.
	 */
	public FieldValidator() {
		this(new ArrayList<>());
	}

	/**
	 * Instantiates a new field validator.
	 *
	 * @param errors the errors
	 */
	private FieldValidator(List<ValidationErrors> errors) {
		this.errors = errors;
	}

	/**
	 * Checks if is failed.
	 *
	 * @return true, if is failed
	 */
	public boolean isFailed() {
		return errors.isEmpty();
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<ValidationErrors> getErrors() {
		return errors;
	}

	/**
	 * Checks if is null.
	 *
	 * @param object the object
	 * @return true, if is null
	 */
	public boolean isNULL(Object object) {
		return null == object;
	}

	/**
	 * Checks if is empty.
	 *
	 * @param str the str
	 * @return true, if is empty
	 */
	public boolean isEmpty(String str) {
		if(null == str){
			return true;
		}
		return str.trim().length() == 0;
	}

	/**
	 * Checks if is empty.
	 *
	 * @param str the str
	 * @param fields the fields
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
			errors.add(new ValidationErrors("can not be blank", errorFields));
		}
	}

	/**
	 * Checks if is date in format.
	 *
	 * @param str the str
	 * @param fields the fields
	 * @param format the format
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

	/**
	 * Checks if is this date valid.
	 *
	 * @param dateToValidate the date to validate
	 * @param dateFromat the date fromat
	 * @return true, if is this date valid
	 */
	private boolean isThisDateValid(String dateToValidate, String dateFromat) {
		if (isEmpty(dateToValidate)) {
			throw new CompletionException(new Exception(INVALID_INPUT));
		}
		try {
			if(dateFromat.equals(RFC_TIMESTAMP_FORMAT)){
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
	 * Adds the error.
	 *
	 * @param message the message
	 * @param errorField the error field
	 */
	public void addError(String message, String errorField){
		List<String> errorFields = new ArrayList<>(1);
		errorFields.add(errorField);
		if(null==errors)
			errors = new ArrayList<>();
		errors.add(new ValidationErrors(message, errorFields));
	}
	
	
	
	/**
	 * Checks if is valid object.
	 *
	 * @param str the str
	 * @param fields the fields
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
			errors.add(new ValidationErrors("can not be blank", errorFields));
		}
	}
	
	/**
	 * Checks if is empty object.
	 *
	 * @param in the in
	 * @return true, if is empty object
	 */
	private boolean isEmptyObject(Object in){
		return null == in || in.toString().trim().isEmpty();
	}
	
	/**
	 * Merge errors.
	 *
	 * @param fieldValidator the field validator
	 */
	public void mergeErrors(FieldValidator fieldValidator){
		this.errors.addAll(fieldValidator.getErrors());
	}
}
