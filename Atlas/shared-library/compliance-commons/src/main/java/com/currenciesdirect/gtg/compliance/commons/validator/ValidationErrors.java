package com.currenciesdirect.gtg.compliance.commons.validator;

import java.util.List;

/**
 * The Class ValidationErrors.
 */
public class ValidationErrors {
	
	/** The message. */
	private String message;
	
	/** The fields. */
	private List<String> fields;

	/**
	 * Instantiates a new validation errors.
	 *
	 * @param message the message
	 * @param fields the fields
	 */
	public ValidationErrors(String message, List<String> fields) {
		super();
		this.message = message;
		this.fields = fields;
	}
	
	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<String> getFields(){
		return fields;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		StringBuilder sb = new StringBuilder();
		for(String field : fields){
			if(sb.length() > 0){
				sb.append(", ");	
			}
			sb.append(field);
		}
		return sb.toString().concat(" ").concat(message);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getErrorMessage();
	}

}