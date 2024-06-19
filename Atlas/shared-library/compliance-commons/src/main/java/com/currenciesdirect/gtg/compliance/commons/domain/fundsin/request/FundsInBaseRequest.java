/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsInBaseRequest.
 *
 * @author manish
 */
public abstract class FundsInBaseRequest extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the request originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty("org_code")
	protected String orgCode;

	/** The source application. */
	@ApiModelProperty(value = "The source application where the request originated", required = true, example ="Salesforce, Dione, CD Aurora", position = 10)
	@JsonProperty("source_application")
	protected String sourceApplication;

	/** The cust type. */
	@ApiModelProperty(value = "The customer type (PFX/CFX)", required = true, example = "PFX")
	@JsonProperty("cust_type")
	protected String custType;

	/** The org id. */
	@JsonIgnore
	protected Integer orgId;

	/** The funds in id. */
	@JsonIgnore
	protected Integer fundsInId;

	/** The version. */
	@JsonIgnore
	private Integer version;
	
	/** The debtor bank name. */
	@ApiModelProperty(value = "Debtor Bank Name", required = true, example = "", position = 20)
	@JsonProperty("debtor_bank_name")
	protected String debtorBankName;

	/**
	 * Gets the debtor bank name.
	 *
	 * @return the debtor bank name
	 */
	@JsonProperty("debtor_bank_name")
	public String getDebtorBankName() {
		return debtorBankName;
	}

	/**
	 * Sets the debtor bank name.
	 *
	 * @param bankName the new debtor bank name
	 */
	@JsonProperty("debtor_bank_name")
	public void setDebtorBankName(String debtorBankName) {
		this.debtorBankName = debtorBankName;
	}

	/**
	 * Gets the org code.
	 *
	 * @return The orgCode
	 */
	@JsonProperty("org_code")
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            The org_code
	 */
	@JsonProperty("org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the source application.
	 *
	 * @return The sourceApplication
	 */
	@JsonProperty("source_application")
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            The source_application
	 */
	@JsonProperty("source_application")
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the custType
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType
	 *            the custType to set
	 */
	@JsonProperty("cust_type")
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the org id.
	 *
	 * @return the org id
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * Sets the org id.
	 *
	 * @param orgId
	 *            the new org id
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * Gets the funds in id.
	 *
	 * @return the funds in id
	 */
	public Integer getFundsInId() {
		return fundsInId;
	}

	/**
	 * Sets the funds in id.
	 *
	 * @param fundsInId
	 *            the new funds in id
	 */
	public void setFundsInId(Integer fundsInId) {
		this.fundsInId = fundsInId;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Gets the payment funds in id.
	 *
	 * @return the payment funds in id
	 */
	public abstract Integer getPaymentFundsInId();

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public abstract String getTradeAccountNumber();

}
