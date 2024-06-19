package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;
import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FXTicketListResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FXTicketListResponse implements Serializable {


	  /** The SharedConstant serialVersionUID. */
	  private static final long serialVersionUID = 1L;

	  /** The id. */
	  @JsonProperty("id")
	  public Long id;

	  /** The trade details. */
	  @JsonProperty("trade_details")
	  private TradeDetails tradeDetails;

	  /** The wallet. */
	  @JsonProperty("wallet")
	  private WalletDetails wallet;

	  /** The created by. */
	  @JsonProperty("created_by")
	  private Integer createdBy;

	  /** The updated by. */
	  @JsonProperty("updated_by")
	  private Integer updatedBy;

	  /** The updated on. */
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	  @JsonProperty("updated_on")
	  private Timestamp updatedOn;

	  /** The status. */
  	@JsonProperty("status")
	  private String status;
	  
	  /** The customer instruction details. */
  	@JsonProperty("customer_instruction_details")
	  private CustomerInstructionDetails customerInstructionDetails;

        //ADD as per Jira AT-2486
  	  /** The source of funds. */
  	@JsonProperty("income_source_of_fund")
  	  private String sourceOfFunds;

	
	/**
	
	/**
	 * Gets the customer instruction details.
	 *
	 * @return the customer instruction details
	 */
	public CustomerInstructionDetails getCustomerInstructionDetails() {
		return customerInstructionDetails;
	}

	/**
	 * Sets the customer instruction details.
	 *
	 * @param customerInstructionDetails the new customer instruction details
	 */
	public void setCustomerInstructionDetails(CustomerInstructionDetails customerInstructionDetails) {
		this.customerInstructionDetails = customerInstructionDetails;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	  public Long getId() {
	      return id;
	  }

	  /**
  	 * Sets the id.
  	 *
  	 * @param id the id to set
  	 */
	  public void setId(Long id) {
	      this.id = id;
	  }

	  /**
  	 * Gets the created by.
  	 *
  	 * @return the createdBy
  	 */
	  public Integer getCreatedBy() {
	      return createdBy;
	  }

	  /**
  	 * Sets the created by.
  	 *
  	 * @param createdBy the createdBy to set
  	 */
	  public void setCreatedBy(Integer createdBy) {
	      this.createdBy = createdBy;
	  }

	  /**
  	 * Gets the updated by.
  	 *
  	 * @return the updatedBy
  	 */
	  public Integer getUpdatedBy() {
	      return updatedBy;
	  }

	  /**
  	 * Sets the updated by.
  	 *
  	 * @param updatedBy the updatedBy to set
  	 */
	  public void setUpdatedBy(Integer updatedBy) {
	      this.updatedBy = updatedBy;
	  }

	  /**
  	 * Gets the updated on.
  	 *
  	 * @return the updatedOn
  	 */
	  public Timestamp getUpdatedOn() {
	      return updatedOn;
	  }

	  /**
  	 * Sets the updated on.
  	 *
  	 * @param updatedOn the updatedOn to set
  	 */
	  public void setUpdatedOn(Timestamp updatedOn) {
	      this.updatedOn = updatedOn;
	  }

	  /**
  	 * Gets the trade details.
  	 *
  	 * @return the tradeDetails
  	 */
	  public TradeDetails getTradeDetails() {
	      return tradeDetails;
	  }

	  /**
  	 * Sets the trade details.
  	 *
  	 * @param tradeDetails the tradeDetails to set
  	 */
	  public void setTradeDetails(TradeDetails tradeDetails) {
	      this.tradeDetails = tradeDetails;
	  }

	  /**
  	 * Gets the wallet.
  	 *
  	 * @return the wallet
  	 */
	  public WalletDetails getWallet() {
	      return wallet;
	  }

	  /**
  	 * Sets the wallet.
  	 *
  	 * @param wallet the wallet to set
  	 */
	  public void setWallet(WalletDetails wallet) {
	      this.wallet = wallet;
	  }

	  /**
  	 * Gets the status.
  	 *
  	 * @return the status
  	 */
	  public String getStatus() {
	      return status;
	  }

	  /**
  	 * Sets the status.
  	 *
  	 * @param status the status to set
  	 */
	  public void setStatus(String status) {
	      this.status = status;
	  }


	   /**
	  * Gets the source of funds.
	  *
	  * @return the source of funds
	  */
	  	public String getSourceOfFunds() {
			return sourceOfFunds;
		}

		/**
	   * Sets the source of funds.
	   *
	   * @param sourceOfFunds the new source of funds
	   */
		public void setSourceOfFunds(String sourceOfFunds) {
			this.sourceOfFunds = sourceOfFunds;
		}

	  /* (non-Javadoc)
	   * @see java.lang.Object#toString()
	   */
	  @Override
	  public String toString() {
	      StringBuilder builder = new StringBuilder();
	      builder.append("FxTicket [createdBy=");
	      builder.append(createdBy);
	      builder.append(", updatedBy=");
	      builder.append(updatedBy);
	      builder.append(", updatedOn=");
	      builder.append(updatedOn);
	      builder.append(", status=");
	      builder.append(status);
	      builder.append(", sourceOfFunds=");
	      builder.append(sourceOfFunds);
	      builder.append(", tradeDetails=");
	      builder.append(tradeDetails);
	      builder.append(", wallet=");
	      builder.append(wallet);
	      builder.append(']');
	      return builder.toString();
	  }

}
