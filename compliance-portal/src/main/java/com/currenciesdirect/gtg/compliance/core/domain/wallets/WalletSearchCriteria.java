
package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.BaseSearchCriteria;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "page",
    "sort",
    "filter",
    "request_source"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletSearchCriteria extends BaseSearchCriteria{  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The wallet filter. */
	  @JsonProperty(value = "filter")
	  private WalletFilter walletFilter;
	  
	  /** The request source indicating request from which page. */
	  @JsonProperty(value="request_source")
	  private String requestSource;

	  /**
	   * Gets the wallet filter.
	   *
	   * @return the wallet filter
	   */
	  public WalletFilter getWalletFilter() {
	    return walletFilter;
	  }

	  /**
	   * Sets the wallet filter.
	   *
	   * @param walletFilter the new wallet filter
	   */
	  public void setWalletFilter(WalletFilter walletFilter) {
	    this.walletFilter = walletFilter;
	  }

	  /**
	   * Gets the request source.
	   *
	   * @return the request source
	   */
	  public String getRequestSource() {
	    return requestSource;
	  }

	  /**
	   * Sets the request source.
	   *
	   * @param requestSource the new request source
	   */
	  public void setRequestSource(String requestSource) {
	    this.requestSource = requestSource;
	  }
	}
