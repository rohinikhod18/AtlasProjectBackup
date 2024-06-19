package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FXTicketResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FXTicketResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response code. */
	@JsonProperty(value = "response_code")
	private String responseCode;

	/** The response description. */
	@JsonProperty(value = "response_description")
	private String responseDescription;

	/** The fx ticket list response list. */
	@JsonProperty(value = "fx_ticket_list")
	private List<FXTicketListResponse> fxTicketListResponseList = new ArrayList<>();

	/** The fx ticket. */
	@JsonProperty("fx_ticket")
	private FXTicket fxTicket;

	/** The fx ticket search criteria. */
	@JsonProperty("search_criteria")
	private FxTicketSearchCriteria fxTicketSearchCriteria;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/**
	 * Gets the error code.
	 *
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the response code.
	 *
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * Gets the response description.
	 *
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * Gets the fx ticket list response list.
	 *
	 * @return the fxTicketListResponseList
	 */
	public List<FXTicketListResponse> getFxTicketListResponseList() {
		return fxTicketListResponseList;
	}

	/**
	 * Sets the response code.
	 *
	 * @param responseCode
	 *            the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * Sets the response description.
	 *
	 * @param responseDescription
	 *            the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * Sets the fx ticket list response list.
	 *
	 * @param fxTicketListResponseList
	 *            the fxTicketListResponseList to set
	 */
	public void setFxTicketListResponseList(List<FXTicketListResponse> fxTicketListResponseList) {
		this.fxTicketListResponseList = fxTicketListResponseList;
	}

	/**
	 * Gets the fx ticket.
	 *
	 * @return the fxTicket
	 */
	public FXTicket getFxTicket() {
		return fxTicket;
	}

	/**
	 * Gets the fx ticket search criteria.
	 *
	 * @return the fxTicketSearchCriteria
	 */
	public FxTicketSearchCriteria getFxTicketSearchCriteria() {
		return fxTicketSearchCriteria;
	}

	/**
	 * Sets the fx ticket.
	 *
	 * @param fxTicket
	 *            the fxTicket to set
	 */
	public void setFxTicket(FXTicket fxTicket) {
		this.fxTicket = fxTicket;
	}

	/**
	 * Sets the fx ticket search criteria.
	 *
	 * @param fxTicketSearchCriteria
	 *            the fxTicketSearchCriteria to set
	 */
	public void setFxTicketSearchCriteria(FxTicketSearchCriteria fxTicketSearchCriteria) {
		this.fxTicketSearchCriteria = fxTicketSearchCriteria;
	}

}
