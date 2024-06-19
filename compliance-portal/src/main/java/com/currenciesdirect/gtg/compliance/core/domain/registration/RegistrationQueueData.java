package com.currenciesdirect.gtg.compliance.core.domain.registration;

/**
 * The Class RegistrationQueueData.
 */
public class RegistrationQueueData  {

	/** The trade account id. */
	private String tradeAccountNum;

	/** The registered on. */
	private String registeredOn;

	/** The contact name. */
	private String contactName;

	/** The organisation. */
	private String organisation;

	/** The type. */
	private String type;

	/** The buy currency. */
	private String buyCurrency;

	/** The sell currency. */
	private String sellCurrency;

	/** The source. */
	private String source;

	/** The transaction value. */
	private String transactionValue;

	/** The eid check. */
	private String eidCheck;

	/** The fraugster. */
	private String fraugster;

	/** The sanction. */
	private String sanction;

	/** The blacklist. */
	private String blacklist;

	/** The contact id. */
	private Integer contactId;

	/** The account id. */
	private Integer accountId;
	
	/** The user resource lock id. */
	private Integer userResourceLockId;
	
	/** The locked. */
	private Boolean locked;
	
	/** The locked by. */
	private String lockedBy;	
	
	/** The custom check. */
	private String customCheck;
	
	/** **For DashBoard*****. */

	/** The user resource createdOn. */
	private String userResourceCreatedOn;
	
	/** The user resource work flow time. */
	private String userResourceWorkflowTime;
	
	/** The user resource entity type. */
	private String userResourceEntityType;
	
	/** The total records. */
	private Integer totalRecords;
	
	/** Newly added field for showing country of residence on queue. */
	private String countryOfResidence;
	
	/** Newly added field for showing either record is newly created or updated on queue. */
	private String newOrUpdated;
	
	/** Newly added field for showing date of registration on queue. */
	private String registeredDate;
	
	/** Newly added field for deciding whether record is newly created or updated on queue. */
	private Integer accountVersion;
	
	/** The compliance status. */
	private String complianceStatus;
	
	/** The data anon status. */
	private String dataAnonStatus;
	
	/** The legal entity. */
	private String legalEntity;
	
	/**
	 * Gets the custom check.
	 *
	 * @return the custom check
	 */
	public String getCustomCheck() {
		return customCheck;
	}

	/**
	 * Sets the custom check.
	 *
	 * @param customCheck the new custom check
	 */
	public void setCustomCheck(String customCheck) {
		this.customCheck = customCheck;
	}
	

	/**
	 * Gets the trade account id.
	 *
	 * @return the trade account id
	 */
	public String getTradeAccountNum() {
		return tradeAccountNum;
	}

	/**
	 * Sets the trade account id.
	 *
	 * @param tradeAccountNum the new trade account num
	 */
	public void setTradeAccountNum(String tradeAccountNum) {
		this.tradeAccountNum = tradeAccountNum;
	}

	/**
	 * Gets the registered on.
	 *
	 * @return the registered on
	 */
	public String getRegisteredOn() {
		return registeredOn;
	}

	/**
	 * Sets the registered on.
	 *
	 * @param registeredOn
	 *            the new registered on
	 */
	public void setRegisteredOn(String registeredOn) {
		this.registeredOn = registeredOn;
	}

	/**
	 * Gets the contact name.
	 *
	 * @return the contact name
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Sets the contact name.
	 *
	 * @param contactName
	 *            the new contact name
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Gets the organisation.
	 *
	 * @return the organisation
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * Sets the organisation.
	 *
	 * @param organisation
	 *            the new organisation
	 */
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the locked.
	 *
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	/**
	 * Sets the locked.
	 *
	 * @param locked the new locked
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * Gets the locked by.
	 *
	 * @return the locked by
	 */
	public String getLockedBy() {
		return lockedBy;
	}

	/**
	 * Sets the locked by.
	 *
	 * @param lockedBy the new locked by
	 */
	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}

	/**
	 * Gets the buy currency.
	 *
	 * @return the buy currency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * Sets the buy currency.
	 *
	 * @param buyCurrency
	 *            the new buy currency
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * Gets the sell currency.
	 *
	 * @return the sell currency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * Sets the sell currency.
	 *
	 * @param sellCurrency
	 *            the new sell currency
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Gets the transaction value.
	 *
	 * @return the transaction value
	 */
	public String getTransactionValue() {
		return transactionValue;
	}

	/**
	 * Sets the transaction value.
	 *
	 * @param transactionValue
	 *            the new transaction value
	 */
	public void setTransactionValue(String transactionValue) {
		this.transactionValue = transactionValue;
	}

	/**
	 * Gets the eid check.
	 *
	 * @return the eid check
	 */
	public String getEidCheck() {
		return eidCheck;
	}

	/**
	 * Sets the eid check.
	 *
	 * @param eidCheck
	 *            the new eid check
	 */
	public void setEidCheck(String eidCheck) {
		this.eidCheck = eidCheck;
	}

	/**
	 * Gets the fraugster.
	 *
	 * @return the fraugster
	 */
	public String getFraugster() {
		return fraugster;
	}

	/**
	 * Sets the fraugster.
	 *
	 * @param fraugster
	 *            the new fraugster
	 */
	public void setFraugster(String fraugster) {
		this.fraugster = fraugster;
	}

	/**
	 * Gets the sanction.
	 *
	 * @return the sanction
	 */
	public String getSanction() {
		return sanction;
	}

	/**
	 * Sets the sanction.
	 *
	 * @param sanction
	 *            the new sanction
	 */
	public void setSanction(String sanction) {
		this.sanction = sanction;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public String getBlacklist() {
		return blacklist;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist
	 *            the new blacklist
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	

	/**
	 * Gets the user resource lock id.
	 *
	 * @return the user resource lock id
	 */
	public Integer getUserResourceLockId() {
		return userResourceLockId;
	}

	/**
	 * Sets the user resource lock id.
	 *
	 * @param userResourceLockId the new user resource lock id
	 */
	public void setUserResourceLockId(Integer userResourceLockId) {
		this.userResourceLockId = userResourceLockId;
	}

	/**
	 * Gets the user resource created on.
	 *
	 * @return the userResourceCreatedOn
	 */
	public String getUserResourceCreatedOn() {
		return userResourceCreatedOn;
	}

	/**
	 * Sets the user resource created on.
	 *
	 * @param userResourceCreatedOn the userResourceCreatedOn to set
	 */
	public void setUserResourceCreatedOn(String userResourceCreatedOn) {
		this.userResourceCreatedOn = userResourceCreatedOn;
	}

	/**
	 * Gets the user resource workflow time.
	 *
	 * @return the userResourceWorkflowTime
	 */
	public String getUserResourceWorkflowTime() {
		return userResourceWorkflowTime;
	}

	/**
	 * Sets the user resource workflow time.
	 *
	 * @param userResourceWorkflowTime the userResourceWorkflowTime to set
	 */
	public void setUserResourceWorkflowTime(String userResourceWorkflowTime) {
		this.userResourceWorkflowTime = userResourceWorkflowTime;
	}

	/**
	 * Gets the user resource entity type.
	 *
	 * @return the userResourceEntityType
	 */
	public String getUserResourceEntityType() {
		return userResourceEntityType;
	}

	/**
	 * Sets the user resource entity type.
	 *
	 * @param userResourceEntityType the userResourceEntityType to set
	 */
	public void setUserResourceEntityType(String userResourceEntityType) {
		this.userResourceEntityType = userResourceEntityType;
	}

	/**
	 * Gets the total records.
	 *
	 * @return the total records
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * Sets the total records.
	 *
	 * @param totalRecords the new total records
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * Gets the country of residence.
	 *
	 * @return the country of residence
	 */
	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	/**
	 * Sets the country of residence.
	 *
	 * @param countryOfResidence the new country of residence
	 */
	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * Gets the new or updated.
	 *
	 * @return the new or updated
	 */
	public String getNewOrUpdated() {
		return newOrUpdated;
	}

	/**
	 * Sets the new or updated.
	 *
	 * @param newOrUpdated the new new or updated
	 */
	public void setNewOrUpdated(String newOrUpdated) {
		this.newOrUpdated = newOrUpdated;
	}

	/**
	 * Gets the account version.
	 *
	 * @return the account version
	 */
	public Integer getAccountVersion() {
		return accountVersion;
	}

	/**
	 * Sets the account version.
	 *
	 * @param accountVersion the new account version
	 */
	public void setAccountVersion(Integer accountVersion) {
		this.accountVersion = accountVersion;
	}

	/**
	 * Gets the registered date.
	 *
	 * @return the registered date
	 */
	public String getRegisteredDate() {
		return registeredDate;
	}

	/**
	 * Sets the registered date.
	 *
	 * @param registeredDate the new registered date
	 */
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}

	/**
	 * Gets the compliance status.
	 *
	 * @return the complianceStatus
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * Sets the compliance status.
	 *
	 * @param complianceStatus the complianceStatus to set
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}
	
	

	/**
	 * @return the dataAnonStatus
	 */
	public String getDataAnonStatus() {
		return dataAnonStatus;
	}

	/**
	 * @param dataAnonStatus the dataAnonStatus to set
	 */
	public void setDataAnonStatus(String dataAnonStatus) {
		this.dataAnonStatus = dataAnonStatus;
	}
	
	/**
	 * Gets the legal entity.
	 *
	 * @return the legal entity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * Sets the legal entity.
	 *
	 * @param legalEntity the new legal entity
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@SuppressWarnings("squid:S3776")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((accountVersion == null) ? 0 : accountVersion.hashCode());
		result = prime * result + ((blacklist == null) ? 0 : blacklist.hashCode());
		result = prime * result + ((buyCurrency == null) ? 0 : buyCurrency.hashCode());
		result = prime * result + ((complianceStatus == null) ? 0 : complianceStatus.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result + ((countryOfResidence == null) ? 0 : countryOfResidence.hashCode());
		result = prime * result + ((customCheck == null) ? 0 : customCheck.hashCode());
		result = prime * result + ((eidCheck == null) ? 0 : eidCheck.hashCode());
		result = prime * result + ((fraugster == null) ? 0 : fraugster.hashCode());
		result = prime * result + ((locked == null) ? 0 : locked.hashCode());
		result = prime * result + ((lockedBy == null) ? 0 : lockedBy.hashCode());
		result = prime * result + ((newOrUpdated == null) ? 0 : newOrUpdated.hashCode());
		result = prime * result + ((organisation == null) ? 0 : organisation.hashCode());
		result = prime * result + ((registeredDate == null) ? 0 : registeredDate.hashCode());
		result = prime * result + ((registeredOn == null) ? 0 : registeredOn.hashCode());
		result = prime * result + ((sanction == null) ? 0 : sanction.hashCode());
		result = prime * result + ((sellCurrency == null) ? 0 : sellCurrency.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((totalRecords == null) ? 0 : totalRecords.hashCode());
		result = prime * result + ((tradeAccountNum == null) ? 0 : tradeAccountNum.hashCode());
		result = prime * result + ((transactionValue == null) ? 0 : transactionValue.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userResourceCreatedOn == null) ? 0 : userResourceCreatedOn.hashCode());
		result = prime * result + ((userResourceEntityType == null) ? 0 : userResourceEntityType.hashCode());
		result = prime * result + ((userResourceLockId == null) ? 0 : userResourceLockId.hashCode());
		result = prime * result + ((userResourceWorkflowTime == null) ? 0 : userResourceWorkflowTime.hashCode());
		result = prime * result + ((legalEntity == null) ? 0 : legalEntity.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationQueueData other = (RegistrationQueueData) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId)) {
			return false;
		  }
		if (accountVersion == null) {
			if (other.accountVersion != null)
				return false;
		} else if (!accountVersion.equals(other.accountVersion)) {
			return false;
		  }
		if (blacklist == null) {
			if (other.blacklist != null)
				return false;
		} else if (!blacklist.equals(other.blacklist)) {
			return false;
		  }
		if (buyCurrency == null) {
			if (other.buyCurrency != null)
				return false;
		} else if (!buyCurrency.equals(other.buyCurrency)) {
			return false;
		  }
		if (complianceStatus == null) {
			if (other.complianceStatus != null)
				return false;
		} else if (!complianceStatus.equals(other.complianceStatus)) {
			return false;
		  }
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId)) {
			return false;
		  }
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName)) {
			return false;
		  }
		if (countryOfResidence == null) {
			if (other.countryOfResidence != null)
				return false;
		} else if (!countryOfResidence.equals(other.countryOfResidence)) {
			return false;
		  }
		if (customCheck == null) {
			if (other.customCheck != null)
				return false;
		} else if (!customCheck.equals(other.customCheck)) {
			return false;
		  }
		if (eidCheck == null) {
			if (other.eidCheck != null)
				return false;
		} else if (!eidCheck.equals(other.eidCheck)) {
			return false;
		  }
		if (fraugster == null) {
			if (other.fraugster != null)
				return false;
		} else if (!fraugster.equals(other.fraugster)) {
			return false;
		  }
		if (locked == null) {
			if (other.locked != null)
				return false;
		} else if (!locked.equals(other.locked)) {
			return false;
		  }
		if (lockedBy == null) {
			if (other.lockedBy != null)
				return false;
		} else if (!lockedBy.equals(other.lockedBy)) {
			return false;
		  }
		if (newOrUpdated == null) {
			if (other.newOrUpdated != null)
				return false;
		} else if (!newOrUpdated.equals(other.newOrUpdated)) {
			return false;
		  }
		if (organisation == null) {
			if (other.organisation != null)
				return false;
		} else if (!organisation.equals(other.organisation)) {
			return false;
		  }
		if (registeredDate == null) {
			if (other.registeredDate != null)
				return false;
		} else if (!registeredDate.equals(other.registeredDate)) {
			return false;
		  }
		if (registeredOn == null) {
			if (other.registeredOn != null)
				return false;
		} else if (!registeredOn.equals(other.registeredOn)) {
			return false;
		  }
		if (sanction == null) {
			if (other.sanction != null)
				return false;
		} else if (!sanction.equals(other.sanction)) {
			return false;
		  }
		if (sellCurrency == null) {
			if (other.sellCurrency != null)
				return false;
		} else if (!sellCurrency.equals(other.sellCurrency)) {
			return false;
		  }
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source)) {
			return false;
		  }
		if (totalRecords == null) {
			if (other.totalRecords != null)
				return false;
		} else if (!totalRecords.equals(other.totalRecords)) {
			return false;
		  }
		if (tradeAccountNum == null) {
			if (other.tradeAccountNum != null)
				return false;
		} else if (!tradeAccountNum.equals(other.tradeAccountNum)) {
			return false;
		  }
		if (transactionValue == null) {
			if (other.transactionValue != null)
				return false;
		} else if (!transactionValue.equals(other.transactionValue)) {
			return false;
		  }
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type)) {
			return false;
		  }
		if (userResourceCreatedOn == null) {
			if (other.userResourceCreatedOn != null)
				return false;
		} else if (!userResourceCreatedOn.equals(other.userResourceCreatedOn)) {
			return false;
		  }
		if (userResourceEntityType == null) {
			if (other.userResourceEntityType != null)
				return false;
		} else if (!userResourceEntityType.equals(other.userResourceEntityType)) {
			return false;
		  }
		if (userResourceLockId == null) {
			if (other.userResourceLockId != null)
				return false;
		} else if (!userResourceLockId.equals(other.userResourceLockId)) {
			return false;
		  }
		if (userResourceWorkflowTime == null) {
			if (other.userResourceWorkflowTime != null)
				return false;
		} else if (!userResourceWorkflowTime.equals(other.userResourceWorkflowTime)) {
			return false;
		  }
		if (legalEntity == null) {
			if (other.legalEntity != null)
				return false;
		} else if (!legalEntity.equals(other.legalEntity)) {
			return false;
		  }
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegistrationQueueData [tradeAccountNum=" + tradeAccountNum + ", registeredOn=" + registeredOn
				+ ", contactName=" + contactName + ", organisation=" + organisation + ", type=" + type
				+ ", buyCurrency=" + buyCurrency + ", sellCurrency=" + sellCurrency + ", source=" + source
				+ ", transactionValue=" + transactionValue + ", eidCheck=" + eidCheck + ", fraugster=" + fraugster
				+ ", sanction=" + sanction + ", blacklist=" + blacklist + ", contactId=" + contactId + ", accountId="
				+ accountId + ", userResourceLockId=" + userResourceLockId + ", locked=" + locked + ", lockedBy="
				+ lockedBy + ", customCheck=" + customCheck + ", userResourceCreatedOn=" + userResourceCreatedOn
				+ ", userResourceWorkflowTime=" + userResourceWorkflowTime + ", userResourceEntityType="
				+ userResourceEntityType + ", totalRecords=" + totalRecords + ", countryOfResidence="
				+ countryOfResidence + ", newOrUpdated=" + newOrUpdated + ", registeredDate=" + registeredDate
				+ ", accountVersion=" + accountVersion + ", complianceStatus=" + complianceStatus + ", legalEntity=" + legalEntity + "]";
	}
	
}
