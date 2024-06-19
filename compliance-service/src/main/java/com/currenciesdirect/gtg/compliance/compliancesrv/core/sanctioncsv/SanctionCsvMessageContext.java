package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;

public class SanctionCsvMessageContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The request. */
	private ServiceMessage request;

	/** The response. */
	private ServiceMessageResponse response;
	
	/** The sanction csv file data. */
	private Map<String,List<SanctionCsvRequest>> sanctionCsvFileData;
	
	/** The sanction csv request. */
	private SanctionCsvRequest sanctionCsvRequest;
	
	/**
	 * Sets the request.
	 *
	 * @param request the new request
	 */
	public void setRequest(ServiceMessage request) {
		this.request = request;
	}
	
	/**
	 * @return the response
	 */
	public ServiceMessage getRequest() {
		return request;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(ServiceMessage request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public ServiceMessageResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(ServiceMessageResponse response) {
		this.response = response;
	}

	/**
	 * Sets the sanction csv request.
	 *
	 * @param sanctionCsvRequest the new sanction csv request
	 */
	public void setSanctionCsvRequest(SanctionCsvRequest sanctionCsvRequest) {
		this.sanctionCsvRequest = sanctionCsvRequest;
	}

	/**
	 * @return the sanctionCsvRequest
	 */
	public SanctionCsvRequest getSanctionCsvRequest() {
		return sanctionCsvRequest;
	}

	/**
	 * @return the sanctionCsvFileData
	 */
	public Map<String,List<SanctionCsvRequest>> getSanctionCsvFileData() {
		return sanctionCsvFileData;
	}

	/**
	 * @param sanctionCsvFileData the sanctionCsvFileData to set
	 */
	public void setSanctionCsvFileData(Map<String,List<SanctionCsvRequest>> sanctionCsvFileData) {
		this.sanctionCsvFileData = sanctionCsvFileData;
	}
	
	

}
