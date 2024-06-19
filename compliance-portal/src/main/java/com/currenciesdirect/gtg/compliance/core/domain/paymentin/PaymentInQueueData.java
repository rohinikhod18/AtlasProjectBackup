package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

/**
 * The Class PaymentInQueueData.
 */
public class PaymentInQueueData {

	/** The transaction id. */
	private String transactionId;
	
	/** The date. */
	private String date;
	
	/** The client id. */
	private String clientId;
	
	/** The contact name. */
	private String contactName;
	
	/** The type. */
	private String type;
	
	/** The sell currency. */
	private String sellCurrency;
	
	/** The amount. */
	private String amount;
	
	/** The method. */
	private String method;
	
	/** The country. */
	private String country;
	
	/** The overall status. */
	private String overallStatus;
	
	/** The organization. */
	private String organization;
	
	/** The fraugster. */
	private String fraugster;

	/** The sanction. */
	private String sanction;

	/** The blacklist. */
	private String blacklist;
	
	/** The watchlist. */
	private String watchlist;
	
	/** The custom check. */
	private String customCheck;
	
	/** The contact id. */
	private Integer contactId;

	/** The account id. */
	private Integer accountId;
	
	/** The payment in id. */
	private String paymentInId;
	
	/** The user resource lock id. */
	private Integer userResourceLockId;
	
	/** The locked. */
	private Boolean locked;
	
	/** The locked by. */
	private String lockedBy;
	
	/** The total records. */
	private Integer totalRecords;
	
	/** The country full name. */
	private String countryFullName;
	
	/** The legal entity. */
	private String legalEntity;
	
	/** The Risk Status.  */
	private String riskStatus;
	
	
	private String initialStatus;
	
	private String intuitionStatus; // AT-4607
	
	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Gets the client id.
	 *
	 * @return the client id
	 */
	public String getClientId() {
		return clientId;
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
	 * Gets the sell currency.
	 *
	 * @return the sell currency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Gets the overall status.
	 *
	 * @return the overall status
	 */
	public String getOverallStatus() {
		return overallStatus;
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
	 * Gets the sanction.
	 *
	 * @return the sanction
	 */
	public String getSanction() {
		return sanction;
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
	 * Gets the watchlist.
	 *
	 * @return the watchlist
	 */
	public String getWatchlist() {
		return watchlist;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Sets the client id.
	 *
	 * @param clientId the new client id
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the sell currency.
	 *
	 * @param sellCurrency the new sell currency
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * Sets the method.
	 *
	 * @param method the new method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Sets the overall status.
	 *
	 * @param overallStatus the new overall status
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * Sets the fraugster.
	 *
	 * @param fraugster the new fraugster
	 */
	public void setFraugster(String fraugster) {
		this.fraugster = fraugster;
	}

	/**
	 * Sets the sanction.
	 *
	 * @param sanction the new sanction
	 */
	public void setSanction(String sanction) {
		this.sanction = sanction;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist the new blacklist
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Sets the watchlist.
	 *
	 * @param watchlist the new watchlist
	 */
	public void setWatchlist(String watchlist) {
		this.watchlist = watchlist;
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
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
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
	 * Gets the user resource lock id.
	 *
	 * @return the user resource lock id
	 */
	public Integer getUserResourceLockId() {
		return userResourceLockId;
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
	 * Gets the locked by.
	 *
	 * @return the locked by
	 */
	public String getLockedBy() {
		return lockedBy;
	}

	/**
	 * Sets the contact name.
	 *
	 * @param contactName the new contact name
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	 * Sets the locked.
	 *
	 * @param locked the new locked
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
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
	 * Gets the payment in id.
	 *
	 * @return the payment in id
	 */
	public String getPaymentInId() {
		return paymentInId;
	}

	/**
	 * Sets the payment in id.
	 *
	 * @param paymentInId the new payment in id
	 */
	public void setPaymentInId(String paymentInId) {
		this.paymentInId = paymentInId;
	}
	
	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
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
	
	
	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}

	/**
	 * Gets the country full name.
	 * 
	 * @return the country full name
	 */
	public String getCountryFullName() {
		return countryFullName;
	}

	/**
	 * Sets the country full name.
	 * 
	 * @param countryFullName the country full name
	 */
	public void setCountryFullName(String countryFullName) {
		this.countryFullName = countryFullName;
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

	
	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getIntuitionStatus() {
		return intuitionStatus;
	}

	public void setIntuitionStatus(String intuitionStatus) {
		this.intuitionStatus = intuitionStatus;
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
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((blacklist == null) ? 0 : blacklist.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fraugster == null) ? 0 : fraugster.hashCode());
		result = prime * result + ((locked == null) ? 0 : locked.hashCode());
		result = prime * result + ((lockedBy == null) ? 0 : lockedBy.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((overallStatus == null) ? 0 : overallStatus.hashCode());
		result = prime * result + ((paymentInId == null) ? 0 : paymentInId.hashCode());
		result = prime * result + ((sanction == null) ? 0 : sanction.hashCode());
		result = prime * result + ((sellCurrency == null) ? 0 : sellCurrency.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userResourceLockId == null) ? 0 : userResourceLockId.hashCode());
		result = prime * result + ((watchlist == null) ? 0 : watchlist.hashCode());
		result = prime * result + ((intuitionStatus == null) ? 0 : intuitionStatus.hashCode());
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
		PaymentInQueueData other = (PaymentInQueueData) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId)) {
			return false;
		  }
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount)) {
			return false;
		  }
		if (blacklist == null) {
			if (other.blacklist != null)
				return false;
		} else if (!blacklist.equals(other.blacklist)) {
			return false;
		  }
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId)) {
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
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country)) {
			return false;
		  }
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date)) {
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
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method)) {
			return false;
		  }
		if (overallStatus == null) {
			if (other.overallStatus != null)
				return false;
		} else if (!overallStatus.equals(other.overallStatus)) {
			return false;
		  }
		if (paymentInId == null) {
			if (other.paymentInId != null)
				return false;
		} else if (!paymentInId.equals(other.paymentInId)) {
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
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId)) {
			return false;
		  }
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type)) {
			return false;
		  }
		if (userResourceLockId == null) {
			if (other.userResourceLockId != null)
				return false;
		} else if (!userResourceLockId.equals(other.userResourceLockId)) {
			return false;
		  }
		if (watchlist == null) {
			if (other.watchlist != null)
				return false;
		} else if (!watchlist.equals(other.watchlist)) {
			return false;
		  }
		if (intuitionStatus == null) { //AT-4607
			if (other.intuitionStatus != null)
				return false;
		} else if (!intuitionStatus.equals(other.intuitionStatus)) {
			return false;
		  }
		return true;
	}

	@Override
	public String toString() {
		return "PaymentInQueueData [transactionId=" + transactionId + ", date=" + date + ", clientId=" + clientId
				+ ", contactName=" + contactName + ", type=" + type + ", sellCurrency=" + sellCurrency + ", amount="
				+ amount + ", method=" + method + ", country=" + country + ", overallStatus=" + overallStatus
				+ ", organization=" + organization + ", fraugster=" + fraugster + ", sanction=" + sanction
				+ ", blacklist=" + blacklist + ", watchlist=" + watchlist + ", customCheck=" + customCheck
				+ ", contactId=" + contactId + ", accountId=" + accountId + ", paymentInId=" + paymentInId
				+ ", userResourceLockId=" + userResourceLockId + ", locked=" + locked + ", lockedBy=" + lockedBy
				+ ", totalRecords=" + totalRecords + ", countryFullName=" + countryFullName + ", legalEntity="
				+ legalEntity + ", riskStatus=" + riskStatus + ", initialStatus=" + initialStatus + ", intuitionStatus="
				+ intuitionStatus + "]";
	}

}
