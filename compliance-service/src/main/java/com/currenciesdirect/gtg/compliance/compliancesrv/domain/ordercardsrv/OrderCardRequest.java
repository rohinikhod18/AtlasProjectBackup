package com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Class OrderCardRequest.
 */
public class OrderCardRequest extends ServiceMessage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trade account number. */
	@ApiModelProperty(value = "The titan account number", required = true)
	@JsonProperty(value="titanAccountNumber")
	private String tradeAccountNumber;
	
	/** The trade contact ID. */
	@ApiModelProperty(value = "The trade contact id", required = true)
	@JsonProperty(value="tradeContactID")
	private String tradeContactID;
	
	/** The password change date. */
	@ApiModelProperty(value = "The password change date", required = true)
	//@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="passwordChangeDate")
	private String passwordChangeDate;
	
	/** The application install date. */
	@ApiModelProperty(value = "The application install date", required = true)
	//@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="applicationInstallDate")
	private String applicationInstallDate;
	
	/** The application device id. */
	@ApiModelProperty(value = "The application device id", required = true)
	@JsonProperty(value="applicationDeviceId")
	private String applicationDeviceId;
	
	/** The request IP address. */
	@ApiModelProperty(value = "The request ip address", required = true)
	@JsonProperty(value="requestIPAddress")
	private String requestIPAddress;

	/** The org code. */
	@ApiModelProperty(value = "The orgCode", required = true)
	@JsonProperty(value = "orgCode")
	private String orgCode;

	/** Is secondary address delivery. */
	@ApiModelProperty(value = "Secondary address delivery", required = true)
	@JsonProperty(value = "cardDeliveryToSecondaryAddress")
	private Boolean cardDeliveryToSecondaryAddress;

	/** The account id. */
	@ApiModelProperty(value = "The account number", required = true)
	@JsonProperty(value = "accountId")
	private Integer accountId;

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the trade contact ID.
	 *
	 * @return the trade contact ID
	 */
	public String getTradeContactID() {
		return tradeContactID;
	}

	/**
	 * Sets the trade contact ID.
	 *
	 * @param tradeContactID the new trade contact ID
	 */
	public void setTradeContactID(String tradeContactID) {
		this.tradeContactID = tradeContactID;
	}

	/**
	 * Gets the password change date.
	 *
	 * @return the password change date
	 */
	public String getPasswordChangeDate() {
		return passwordChangeDate;
	}

	/**
	 * Sets the password change date.
	 *
	 * @param passwordChangeDate the new password change date
	 */
	public void setPasswordChangeDate(String passwordChangeDate) {
		this.passwordChangeDate = passwordChangeDate;
	}

	/**
	 * Gets the application install date.
	 *
	 * @return the application install date
	 */
	public String getApplicationInstallDate() {
		return applicationInstallDate;
	}

	/**
	 * Sets the application install date.
	 *
	 * @param applicationInstallDate the new application install date
	 */
	public void setApplicationInstallDate(String applicationInstallDate) {
		this.applicationInstallDate = applicationInstallDate;
	}

	/**
	 * Gets the application device id.
	 *
	 * @return the application device id
	 */
	public String getApplicationDeviceId() {
		return applicationDeviceId;
	}

	/**
	 * Sets the application device id.
	 *
	 * @param applicationDeviceId the new application device id
	 */
	public void setApplicationDeviceId(String applicationDeviceId) {
		this.applicationDeviceId = applicationDeviceId;
	}

	/**
	 * Gets the request IP address.
	 *
	 * @return the request IP address
	 */
	public String getRequestIPAddress() {
		return requestIPAddress;
	}

	/**
	 * Sets the request IP address.
	 *
	 * @param requestIPAddress the new request IP address
	 */
	public void setRequestIPAddress(String requestIPAddress) {
		this.requestIPAddress = requestIPAddress;
	}

	public Boolean getCardDeliveryToSecondaryAddress() {
		return cardDeliveryToSecondaryAddress;
	}

	public void setCardDeliveryToSecondaryAddress(Boolean cardDeliveryToSecondaryAddress) {
		this.cardDeliveryToSecondaryAddress = cardDeliveryToSecondaryAddress;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OrderCardRequest that = (OrderCardRequest) o;
		return tradeAccountNumber.equals(that.tradeAccountNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tradeAccountNumber);
	}

	@Override
	public String toString() {
		return "OrderCardRequest{" +
				"tradeAccountNumber='" + tradeAccountNumber + '\'' +
				", tradeContactID='" + tradeContactID + '\'' +
				", passwordChangeDate='" + passwordChangeDate + '\'' +
				", applicationInstallDate='" + applicationInstallDate + '\'' +
				", applicationDeviceId='" + applicationDeviceId + '\'' +
				", requestIPAddress='" + requestIPAddress + '\'' +
				", orgCode='" + orgCode + '\'' +
				", cardDeliveryToSecondaryAddress=" + cardDeliveryToSecondaryAddress +
				", accountId=" + accountId +
				'}';
	}
}
