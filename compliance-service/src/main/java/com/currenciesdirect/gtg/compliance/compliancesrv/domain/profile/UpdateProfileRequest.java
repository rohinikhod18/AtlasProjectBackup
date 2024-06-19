package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UpdateProfileRequest extends ProfileReuqest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "account", dataType = "Account", required = true)
	@JsonProperty(value="account")
	private UpdateAccount account;
	public UpdateAccount getAccount() {
		return account;
	}
	public void setAccount(UpdateAccount account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "SignUpRequest [account=" + account + ", getOrgCode()=" + getOrgCode() + ", getSourceApplication()="
				+ getSourceApplication() + ", getDeviceInfo()=" + getDeviceInfo() + ", getEvent()="+ getEvent()+"]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateProfileRequest other = (UpdateProfileRequest) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account)) {
			return false;
		  }
		return true;
	}

	
}
