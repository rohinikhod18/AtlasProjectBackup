package com.currenciesdirect.gtg.compliance.core.blacklist.payrefport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ProviderBlackPayrefResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderBlackPayrefResponse 
{
	
	/** The payment reference. */
	@JsonProperty("payment_reference")
	  private String paymentReference;
	
	/** The request id. */
	@JsonProperty("request_id")
	  private String requestId;
	
	/** The sanction text. */
	@JsonProperty("sanction_text")
	  private String sanctionText;
	
	/** The token set ratio. */
	@JsonProperty("token_set_ratio_percent")
	  private int  tokenSetRatio;

	/**
	 * Gets the payment reference.
	 *
	 * @return the payment reference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * Sets the payment reference.
	 *
	 * @param paymentReference the new payment reference
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * Gets the request id.
	 *
	 * @return the request id
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Sets the request id.
	 *
	 * @param requestId the new request id
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Gets the sanction text.
	 *
	 * @return the sanction text
	 */
	public String getSanctionText() {
		return sanctionText;
	}

	/**
	 * Sets the sanction text.
	 *
	 * @param sanctionText the new sanction text
	 */
	public void setSanctionText(String sanctionText) {
		this.sanctionText = sanctionText;
	}

	/**
	 * Gets the token set ratio.
	 *
	 * @return the token set ratio
	 */
	public int getTokenSetRatio() {
		return tokenSetRatio;
	}

	/**
	 * Sets the token set ratio.
	 *
	 * @param tokenSetRatio the new token set ratio
	 */
	public void setTokenSetRatio(int tokenSetRatio) {
		this.tokenSetRatio = tokenSetRatio;
	}
	
	

}
