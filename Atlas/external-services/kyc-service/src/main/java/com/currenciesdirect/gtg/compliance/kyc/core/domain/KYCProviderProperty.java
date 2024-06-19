/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;

/**
 * The Class ProviderProperty.
 *
 * @author manish
 */
public class KYCProviderProperty extends ProviderProperty {

	/** The qname. */
	private String qname;
	
	/** The profile version ID list. */
	private List<ProfileVersionID> profileVersionIDList;

	/**
	 * @return the qname
	 */
	public String getQname() {
		return qname;
	}

	/**
	 * @param qname the qname to set
	 */
	public void setQname(String qname) {
		this.qname = qname;
	}

	/**
	 * Gets the profile version ID list.
	 *
	 * @return the profile version ID list
	 */
	public List<ProfileVersionID> getProfileVersionIDList() {
		return profileVersionIDList;
	}

	/**
	 * Sets the profile version ID list.
	 *
	 * @param profileVersionIDList
	 *            the new profile version ID list
	 */
	public void setProfileVersionIDList(List<ProfileVersionID> profileVersionIDList) {
		this.profileVersionIDList = profileVersionIDList;
	}

}
