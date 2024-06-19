package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

import java.util.List;

/**
 * The Class CustomerDataScanSearchRequest.
 * 
 * 	@author Rajesh
 */
public class CustomerDataScanSearchRequest implements IRequest {

	private List<CustomerSearchRequestData> search;
	
	/** The source application. */
	private String sourceApplication;
	
	/** The request type. */
	private String requestType;
	
	/** The correlation id. */
	private String correlationId;
	
	/** The user name. */
	private String userName;

	public List<CustomerSearchRequestData> getSearch() {
		return search;
	}

	public void setSearch(List<CustomerSearchRequestData> search) {
		this.search = search;
	}

	public String getSourceApplication() {
		return sourceApplication;
	}

	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
