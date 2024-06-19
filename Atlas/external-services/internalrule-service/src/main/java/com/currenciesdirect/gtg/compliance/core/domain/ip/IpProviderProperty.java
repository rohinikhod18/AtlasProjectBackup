/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.ip;

/**
 * The Class IpProviderProperty.
 *
 * @author manish
 */
public class IpProviderProperty {

	/** The end point url. */
	private String endPointUrl;
	
	/** The three shold score. */
	private Double threeSholdScore;
	
	/** The socket timeout millis. */
	private Integer socketTimeoutMillis;
	
	/** The connection timeout millis. */
	private Integer connectionTimeoutMillis;
	


	/**
	 * Gets the socket timeout millis.
	 *
	 * @return the socket timeout millis
	 */
	public Integer getSocketTimeoutMillis() {
		return socketTimeoutMillis;
	}

	/**
	 * Sets the socket timeout millis.
	 *
	 * @param socketTimeoutMillis the new socket timeout millis
	 */
	public void setSocketTimeoutMillis(Integer socketTimeoutMillis) {
		this.socketTimeoutMillis = socketTimeoutMillis;
	}

	/**
	 * Gets the connection timeout millis.
	 *
	 * @return the connection timeout millis
	 */
	public Integer getConnectionTimeoutMillis() {
		return connectionTimeoutMillis;
	}

	/**
	 * Sets the connection timeout millis.
	 *
	 * @param connectionTimeoutMillis the new connection timeout millis
	 */
	public void setConnectionTimeoutMillis(Integer connectionTimeoutMillis) {
		this.connectionTimeoutMillis = connectionTimeoutMillis;
	}

	/**
	 * Gets the three shold score.
	 *
	 * @return the three shold score
	 */
	public Double getThreeSholdScore() {
		return threeSholdScore;
	}

	/**
	 * Sets the three shold score.
	 *
	 * @param threeSholdScore the new three shold score
	 */
	public void setThreeSholdScore(Double threeSholdScore) {
		this.threeSholdScore = threeSholdScore;
	}


	/**
	 * Gets the end point url.
	 *
	 * @return the end point url
	 */
	public String getEndPointUrl() {
		return endPointUrl;
	}

	/**
	 * Sets the end point url.
	 *
	 * @param endPointUrl the new end point url
	 */
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
	
	
}
