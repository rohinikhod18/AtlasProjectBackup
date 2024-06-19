/*
 * Copyright 2012-2017 Currencies Direct Ltd, United Kingdom
 *
 * titan-wrapper-service: PayeeDelete.java
 * Last modified: 26-Jul-2017
*/
package com.currenciesdirect.gtg.compliance.core.titan.payee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class PayeeDelete.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapperPayeeDelete implements IDomain,Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/** The id. */
  @JsonProperty("payee_id")
  private Integer payeeId;
  
  /** The delete status. */
  @JsonProperty("delete_status")
  private Boolean deleteStatus;
  
  /** The delete status. */
  @JsonProperty("delete_exception")
  private String deleteException;


  /**
   * @return the payeeId
   */
  public Integer getPayeeId() {
    return payeeId;
  }

  /**
   * @param payeeId the payeeId to set
   */
  public void setPayeeId(Integer payeeId) {
    this.payeeId = payeeId;
  }

  /**
   * @return the deleteStatus
   */
  public Boolean getDeleteStatus() {
    return deleteStatus;
  }

  /**
   * @param deleteStatus the deleteStatus to set
   */
  public void setDeleteStatus(Boolean deleteStatus) {
    this.deleteStatus = deleteStatus;
  }

  /**
   * @return the deleteException
   */
  public String getDeleteException() {
    return deleteException;
  }

  /**
   * @param deleteException the deleteException to set
   */
  public void setDeleteException(String deleteException) {
    this.deleteException = deleteException;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PayeeDelete [payeeId=")
	    .append(payeeId)
	    .append(", deleteStatus=")
	    .append(deleteStatus)
	    .append(", deleteException=")
	    .append(deleteException)
	    .append(']');
    return builder.toString();
  }


}

