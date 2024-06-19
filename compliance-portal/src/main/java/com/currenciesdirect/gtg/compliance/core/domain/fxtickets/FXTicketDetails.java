
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FXTicketDetails.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "response_code",
    "response_description",
    "fx_ticket_detail_list"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class FXTicketDetails {

    /** The response code. */
    @JsonProperty("response_code")
    private String responseCode;
    
    /** The response description. */
    @JsonProperty("response_description")
    private String responseDescription;
    
    /** The fx ticket detail list. */
    @JsonProperty("fx_ticket_detail_list")
    private List<FxTicketDetailList> fxTicketDetailList = null;

    /**
     * Gets the response code.
     *
     * @return the response code
     */
    @JsonProperty("response_code")
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the response code.
     *
     * @param responseCode the new response code
     */
    @JsonProperty("response_code")
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Gets the response description.
     *
     * @return the response description
     */
    @JsonProperty("response_description")
    public String getResponseDescription() {
        return responseDescription;
    }

    /**
     * Sets the response description.
     *
     * @param responseDescription the new response description
     */
    @JsonProperty("response_description")
    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    /**
     * Gets the fx ticket detail list.
     *
     * @return the fx ticket detail list
     */
    @JsonProperty("fx_ticket_detail_list")
    public List<FxTicketDetailList> getFxTicketDetailList() {
        return fxTicketDetailList;
    }

    /**
     * Sets the fx ticket detail list.
     *
     * @param fxTicketDetailList the new fx ticket detail list
     */
    @JsonProperty("fx_ticket_detail_list")
    public void setFxTicketDetailList(List<FxTicketDetailList> fxTicketDetailList) {
        this.fxTicketDetailList = fxTicketDetailList;
    }

}
