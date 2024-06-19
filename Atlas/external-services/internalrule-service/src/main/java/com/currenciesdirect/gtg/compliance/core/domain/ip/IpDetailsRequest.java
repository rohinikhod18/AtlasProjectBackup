/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.ip;

import java.io.Serializable;

/**
 * @author manish
 *
 */
public class IpDetailsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ipAddress;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
