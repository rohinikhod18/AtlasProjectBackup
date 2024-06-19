package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

/**
 * The Class PaymentOutQueueData.
 */
public class PaymentOutQueueData {

	/** The payment out id.*/
	private String paymentOutId;
	
	/** The transaction id.*/
	private String transactionId;
	
	/** The client id.*/
	private String clientId;
	
	/** The date.*/
	private String date;
	
	/** The contact name.*/
	private String contactName;

	/** The type. */
	private String type;

	/** The buy currency. */
	private String buyCurrency;

	/** The amount. Buy Amount */
	private String amount;
	
	/** The beneficiary.*/
	private String beneficiary;
	
	/** The country. beneficiary_country*/
	private String country;
	
	private String isoCountry;
	
	/** The overall status.*/
	private String overallStatus;
	
	/** The watchlist. */
	private String watchlist;
	
	/** The fraugster. */
	private String fraugster;

	/** The sanction. */
	private String sanction;

	/** The blacklist. */
	private String blacklist;
	
	/** The custom check. */
	private String customCheck;

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
	
	/** The organisation. */
	private String organisation;
	
	/** The account Sf id. */
	private String acsfId;
	
	/** The ReasonOfTransfer by. */
	private String reasonOfTransfer;
	
	private String valueDate;
	
	private String maturityDate;
	
	/** The legal entity. */
	private String legalEntity;
	
	private String initialStatus;
	
	private String blacklistPayRef; //AT-3854 ,//AT-3855
	
	private String intuitionStatus; // AT-4607
	
	/**
	 * Gets the payment out id.
	 *
	 * @return the paymentOutId
	 */	
	public String getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * Sets the payment out id.
	 *
	 * @param paymentOutId the paymentOutId to set
	 */
	public void setPaymentOutId(String paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	/**
	 * Gets the contact name.
	 *
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
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
	 * Gets the buy currency.
	 *
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
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
	 * Gets the beneficiary.
	 *
	 * @return the beneficiary
	 */
	public String getBeneficiary() {
		return beneficiary;
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
	 * @return the overallStatus
	 */
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * Gets the watchlist.
	 *
	 * @return the watch list
	 */
	public String getWatchlist() {
		return watchlist;
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
	 * Gets the contact id.
	 *
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
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
	 * @return the lockedBy
	 */
	public String getLockedBy() {
		return lockedBy;
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
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Sets the contact name.
	 *
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the buy currency.
	 *
	 * @param buyCurrency the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * Sets the beneficiary.
	 *
	 * @param beneficiary the beneficiary to set
	 */
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Sets the overall status.
	 *
	 * @param overallStatus the overallStatus to set
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * Sets the watchlist.
	 *
	 * @param watchlist the watchlist to set
	 */
	public void setWatchlist(String watchlist) {
		this.watchlist = watchlist;
	}

	/**
	 * Sets the fraugster.
	 *
	 * @param fraugster the fraugster to set
	 */
	public void setFraugster(String fraugster) {
		this.fraugster = fraugster;
	}

	/**
	 * Sets the sanction.
	 *
	 * @param sanction the sanction to set
	 */
	public void setSanction(String sanction) {
		this.sanction = sanction;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist the blacklist to set
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Sets the locked.
	 *
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * Sets the locked by.
	 *
	 * @param lockedBy the lockedBy to set
	 */
	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Gets the user resource lock id.
	 *
	 * @return the userResourceLockId
	 */
	public Integer getUserResourceLockId() {
		return userResourceLockId;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Sets the user resource lock id.
	 *
	 * @param userResourceLockId the userResourceLockId to set
	 */
	public void setUserResourceLockId(Integer userResourceLockId) {
		this.userResourceLockId = userResourceLockId;
	}

	/**
	 * Gets the client id.
	 *
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets the client id.
	 *
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	 * @param organisation the organisation to set
	 */
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	
	/**
	 * Gets the acsf id.
	 *
	 * @return the acsfid
	 */
	public String getAcsfId() {
		return acsfId;
	}
	
	/**
	 * Sets the acsf id.
	 *
	 * @param acsfId the new acsf id
	 */
	public void setAcsfId(String acsfId) {
		this.acsfId = acsfId;
	}

	/**
	 * Gets the reason of transfer.
	 *
	 * @return the reasonOfTransfer
	 */
	public String getReasonOfTransfer() {
		return reasonOfTransfer;
	}
	
	/**
	 * Sets the reason of transfer.
	 *
	 * @param reasonOfTransfer the reasonOfTransfer to set
	 */
	public void setReasonOfTransfer(String reasonOfTransfer) {
		this.reasonOfTransfer = reasonOfTransfer;
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

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getIsoCountry() {
		return isoCountry;
	}

	public void setIsoCountry(String isoCountry) {
		this.isoCountry = isoCountry;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	
	/**
	 * @return the legalEntity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}
	
	public String getBlacklistPayRef() {
		return blacklistPayRef;
	}

	public void setBlacklistPayRef(String blacklistPayRef) {
		this.blacklistPayRef = blacklistPayRef;
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
		result = prime * result + ((beneficiary == null) ? 0 : beneficiary.hashCode());
		result = prime * result + ((blacklist == null) ? 0 : blacklist.hashCode());
		result = prime * result + ((buyCurrency == null) ? 0 : buyCurrency.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fraugster == null) ? 0 : fraugster.hashCode());
		result = prime * result + ((locked == null) ? 0 : locked.hashCode());
		result = prime * result + ((lockedBy == null) ? 0 : lockedBy.hashCode());
		result = prime * result + ((overallStatus == null) ? 0 : overallStatus.hashCode());
		result = prime * result + ((sanction == null) ? 0 : sanction.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userResourceLockId == null) ? 0 : userResourceLockId.hashCode());
		result = prime * result + ((watchlist == null) ? 0 : watchlist.hashCode());
		result = prime * result + ((legalEntity == null) ? 0 : legalEntity.hashCode());
		result = prime * result + ((blacklistPayRef == null) ? 0 : blacklistPayRef.hashCode());
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
		PaymentOutQueueData other = (PaymentOutQueueData) obj;
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
		if (beneficiary == null) {
			if (other.beneficiary != null)
				return false;
		} else if (!beneficiary.equals(other.beneficiary)) {
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
		if (overallStatus == null) {
			if (other.overallStatus != null)
				return false;
		} else if (!overallStatus.equals(other.overallStatus)) {
			return false;
		  }
		if (sanction == null) {
			if (other.sanction != null)
				return false;
		} else if (!sanction.equals(other.sanction)) {
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
		if (blacklistPayRef == null) {
			if (other.blacklistPayRef != null)
				return false;
		} else if (!blacklistPayRef.equals(other.blacklistPayRef)) {
			return false;
		}
		if (intuitionStatus == null) {
			if (other.intuitionStatus != null)
				return false;
		} else if (!intuitionStatus.equals(other.intuitionStatus)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PaymentOutQueueData [paymentOutId=" + paymentOutId + ", transactionId=" + transactionId + ", clientId="
				+ clientId + ", date=" + date + ", contactName=" + contactName + ", type=" + type + ", buyCurrency="
				+ buyCurrency + ", amount=" + amount + ", beneficiary=" + beneficiary + ", country=" + country
				+ ", isoCountry=" + isoCountry + ", overallStatus=" + overallStatus + ", watchlist=" + watchlist
				+ ", fraugster=" + fraugster + ", sanction=" + sanction + ", blacklist=" + blacklist + ", customCheck="
				+ customCheck + ", contactId=" + contactId + ", accountId=" + accountId + ", userResourceLockId="
				+ userResourceLockId + ", locked=" + locked + ", lockedBy=" + lockedBy + ", organisation="
				+ organisation + ", acsfId=" + acsfId + ", reasonOfTransfer=" + reasonOfTransfer + ", valueDate="
				+ valueDate + ", maturityDate=" + maturityDate + ", legalEntity=" + legalEntity + ", initialStatus="
				+ initialStatus + ", blacklistPayRef=" + blacklistPayRef + ", intuitionStatus=" + intuitionStatus + "]";
	}

}
