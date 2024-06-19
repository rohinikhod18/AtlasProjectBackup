package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailTableData {
	
	/** The tr data. */
	@JsonProperty("trData")
	private String[] trData;

    public String[] getTrData ()
    {
        return trData;
    }

    public void setTrData (String[] trData)
    {
        this.trData = trData;
    }

    @Override
    public String toString()
    {
        return "[trData = "+trData+"]";
    }
}
