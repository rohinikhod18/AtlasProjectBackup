package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerInstructionForward {
	
	/** The customer instruction. */
	  @JsonIgnore
	  private Long customerInstructionId;
	  
	  /** The is open forward. */
	  @JsonProperty(value = "is_open_forward")
	  private Boolean isOpenForward;
	  
	  /** The open date. */
	  @JsonProperty(value = "open_date")
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	  private Timestamp openDate;
	  
	  /** The maturity date. */
	  @JsonProperty(value = "maturity_date")
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	  private Timestamp maturityDate;

	  
	  /**
	   * Method used for getting customerInstructionId
	   * @return the customerInstructionId
	   */
	  public Long getCustomerInstructionId() {
	    return customerInstructionId;
	  }

	  
	  /**
	   * Method used for setting customerInstructionId
	   * @param customerInstructionId the customerInstructionId to set
	   */
	  public void setCustomerInstructionId(Long customerInstructionId) {
	    this.customerInstructionId = customerInstructionId;
	  }

	  
	  /**
	   * Method used for getting isOpenForward
	   * @return the isOpenForward
	   */
	  public Boolean getIsOpenForward() {
	    return isOpenForward;
	  }

	  
	  /**
	   * Method used for setting isOpenForward
	   * @param isOpenForward the isOpenForward to set
	   */
	  public void setIsOpenForward(Boolean isOpenForward) {
	    this.isOpenForward = isOpenForward;
	  }

	  
	  /**
	   * Method used for getting openDate
	   * @return the openDate
	   */
	  public Timestamp getOpenDate() {
	    return openDate;
	  }

	  
	  /**
	   * Method used for setting openDate
	   * @param openDate the openDate to set
	   */
	  public void setOpenDate(Timestamp openDate) {
	    this.openDate = openDate;
	  }

	  
	  /**
	   * Method used for getting maturityDate
	   * @return the maturityDate
	   */
	  public Timestamp getMaturityDate() {
	    return maturityDate;
	  }

	  
	  /**
	   * Method used for setting maturityDate
	   * @param maturityDate the maturityDate to set
	   */
	  public void setMaturityDate(Timestamp maturityDate) {
	    this.maturityDate = maturityDate;
	  }

}
