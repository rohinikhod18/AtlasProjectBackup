package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FxTicketPortalRequest.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketPortalRequest implements Serializable {
	
	  /** The Constant serialVersionUID. */
	  private static final long serialVersionUID = 1L;
	  
	  
	  /** The String. */
  	  @JsonProperty(value = "source_application")
  	  private String source;
	  
	  /** The org code. */
	  @JsonProperty(value = "org_code")
	  private String orgCode;

	  /** The account number. */
	  @JsonProperty(value = "account_number")
	  private String accountNumber;
	  
	  /** The osr id. */
	  @JsonProperty(value = "osr_id")
	  private String osrId;
	  
	/** The payment in search criteria. */
	  @JsonProperty(value = "search_criteria")
	  private FxTicketSearchCriteria fxTicketSearchCriteria;
	  
	  @JsonProperty(value = "fx_ticket_payload")
	  private FxTicketRequestPayload fxTicketRequestPayload;

	  
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
  	 * Gets the org code.
  	 *
  	 * @return the orgCode
  	 */
	  public String getOrgCode() {
	    return orgCode;
	  }

	  /**
  	 * Sets the org code.
  	 *
  	 * @param orgCode the orgCode to set
  	 */
	  public void setOrgCode(String orgCode) {
	    this.orgCode = orgCode;
	  }

	  /**
  	 * Gets the account number.
  	 *
  	 * @return the accountNumber
  	 */
	  public String getAccountNumber() {
	    return accountNumber;
	  }

	  /**
  	 * Sets the account number.
  	 *
  	 * @param accountNumber the accountNumber to set
  	 */
	  public void setAccountNumber(String accountNumber) {
	    this.accountNumber = accountNumber;
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
  	 * Sets the fx ticket search criteria.
  	 *
  	 * @param fxTicketSearchCriteria the fxTicketSearchCriteria to set
  	 */
	  public void setFxTicketSearchCriteria(FxTicketSearchCriteria fxTicketSearchCriteria) {
	    this.fxTicketSearchCriteria = fxTicketSearchCriteria;
	  }

	  /* (non-Javadoc)
	   * @see java.lang.Object#toString()
	   */
	  @Override
	  public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("FxTicketPortalRequest [orgCode=");
	    builder.append(orgCode);
	    builder.append(", accountNumber=");
	    builder.append(accountNumber);
	    builder.append(", fxTicketSearchCriteria=");
	    builder.append(fxTicketSearchCriteria);
	    builder.append(']');
	    return builder.toString();
	  }

	/**
	 * @return the fxTicketRequestPayload
	 */
	public FxTicketRequestPayload getFxTicketRequestPayload() {
		return fxTicketRequestPayload;
	}

	/**
	 * @param fxTicketRequestPayload the fxTicketRequestPayload to set
	 */
	public void setFxTicketRequestPayload(FxTicketRequestPayload fxTicketRequestPayload) {
		this.fxTicketRequestPayload = fxTicketRequestPayload;
	}

	
}
