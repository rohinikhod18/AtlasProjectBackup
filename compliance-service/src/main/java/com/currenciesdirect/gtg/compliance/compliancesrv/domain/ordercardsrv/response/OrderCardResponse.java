package com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * The Class OrderCardResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCardResponse extends BaseResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The card eligibility status. */
	@ApiModelProperty(value = "A card eligibility status")
	private String cardEligibilityStatus;

	/** The card update status. */
	@ApiModelProperty(value = "A card update status")
	private String cardUpdateStatus;

	private String status;

	/**
	 * Gets the card eligibility status.
	 *
	 * @return the card eligibility status
	 */
	public String getCardEligibilityStatus() {
		return cardEligibilityStatus;
	}

	/**
	 * Sets the card eligibility status.
	 *
	 * @param cardEligibilityStatus the new card eligibility status
	 */
	public void setCardEligibilityStatus(String cardEligibilityStatus) {
		this.cardEligibilityStatus = cardEligibilityStatus;
	}

	public String getCardUpdateStatus() {
		return cardUpdateStatus;
	}

	public void setCardUpdateStatus(String cardUpdateStatus) {
		this.cardUpdateStatus = cardUpdateStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
