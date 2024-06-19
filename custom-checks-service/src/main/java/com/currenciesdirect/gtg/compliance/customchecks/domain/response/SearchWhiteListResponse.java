package com.currenciesdirect.gtg.compliance.customchecks.domain.response;

import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author bnt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchWhiteListResponse implements IResponse {
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("errorDescription")
	private String errorDescription;
	@JsonProperty("whiteListData")
	private AccountWhiteList whiteListData;
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * @return the whiteListData
	 */
	public AccountWhiteList getWhiteListData() {
		return whiteListData;
	}
	/**
	 * @param whiteListData the whiteListData to set
	 */
	public void setWhiteListData(AccountWhiteList whiteListData) {
		this.whiteListData = whiteListData;
	}
	
	
	
}
