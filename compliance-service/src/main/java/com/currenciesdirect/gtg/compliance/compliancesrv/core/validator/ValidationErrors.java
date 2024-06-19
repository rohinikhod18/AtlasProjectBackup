package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator;

import java.util.List;

public class ValidationErrors {
	private String message;
	private List<String> fields;

	public ValidationErrors(String message, List<String> fields) {
		super();
		this.message = message;
		this.fields = fields;
	}
	
	public List<String> getFields(){
		return fields;
	}

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

	@Override
	public String toString() {
		return getErrorMessage();
	}

}