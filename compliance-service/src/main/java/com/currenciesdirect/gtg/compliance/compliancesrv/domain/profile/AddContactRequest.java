package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AddContactRequest extends ProfileReuqest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty(value="id")
	private Integer id;
	
	@ApiModelProperty(value = "Account Salesforce ID", example = "0010O00001LUEp4FAH", required = true)
	@JsonProperty(value="acc_sf_id")
	private String accSFID;
	
	@ApiModelProperty(value = "Array of Contacts", required = true)
	@JsonProperty(value="contacts")
	private List<Contact> contacts = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccSFID() {
		return accSFID;
	}

	public void setAccSFID(String accSFID) {
		this.accSFID = accSFID;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "AddContactRequest [id=" + id + ", accSFID=" + accSFID + ", contacts=" + contacts + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accSFID == null) ? 0 : accSFID.hashCode());
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AddContactRequest other = (AddContactRequest) obj;
		if (accSFID == null) {
			if (other.accSFID != null)
				return false;
		} else if (!accSFID.equals(other.accSFID)) {
			return false;
		  }
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts)) {
			return false;
		  }
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		  }	
		return true;
	}

	
	
}
