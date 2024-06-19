package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FxTicketDetailRequestList.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fx_ticket_payload"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketDetailRequestList {

    /** The fx ticket payload. */
    @JsonProperty("fx_ticket_payload")
    private FxTicketRequestPayload fxTicketPayload;

    /**
     * Gets the fx ticket payload.
     *
     * @return the fx ticket payload
     */
    @JsonProperty("fx_ticket_payload")
    public FxTicketRequestPayload getFxTicketPayload() {
        return fxTicketPayload;
    }

    /**
     * Sets the fx ticket payload.
     *
     * @param fxTicketPayload the new fx ticket payload
     */
    @JsonProperty("fx_ticket_payload")
    public void setFxTicketPayload(FxTicketRequestPayload fxTicketPayload) {
        this.fxTicketPayload = fxTicketPayload;
    }

}
