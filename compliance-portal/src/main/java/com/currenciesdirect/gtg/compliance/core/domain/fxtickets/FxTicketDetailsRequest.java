package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FxTicketDetailsRequest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source_application",
    "fx_ticket_detail_request_list"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketDetailsRequest {

    /** The source application. */
    @JsonProperty("source_application")
    private String sourceApplication;
    
    /** The osr id. */
	@JsonProperty(value = "osr_id")
	private String osrId;
	
	/** The fx ticket detail request list. */
    @JsonProperty("fx_ticket_detail_request_list")
    private List<FxTicketDetailRequestList> fxTicketDetailRequestList = null;

    
    /**
     * @return osrId
     */
    public String getOsrId() {
		return osrId;
	}

	/**
	 * @param osrId
	 */
	public void setOsrId(String osrId) {
		this.osrId = osrId;
	}
    
    /**
     * Gets the source application.
     *
     * @return the source application
     */
    @JsonProperty("source_application")
    public String getSourceApplication() {
        return sourceApplication;
    }

    /**
     * Sets the source application.
     *
     * @param sourceApplication the new source application
     */
    @JsonProperty("source_application")
    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    /**
     * Gets the fx ticket detail request list.
     *
     * @return the fx ticket detail request list
     */
    @JsonProperty("fx_ticket_detail_request_list")
    public List<FxTicketDetailRequestList> getFxTicketDetailRequestList() {
        return fxTicketDetailRequestList;
    }

    /**
     * Sets the fx ticket detail request list.
     *
     * @param fxTicketDetailRequestList the new fx ticket detail request list
     */
    @JsonProperty("fx_ticket_detail_request_list")
    public void setFxTicketDetailRequestList(List<FxTicketDetailRequestList> fxTicketDetailRequestList) {
        this.fxTicketDetailRequestList = fxTicketDetailRequestList;
    }

}
