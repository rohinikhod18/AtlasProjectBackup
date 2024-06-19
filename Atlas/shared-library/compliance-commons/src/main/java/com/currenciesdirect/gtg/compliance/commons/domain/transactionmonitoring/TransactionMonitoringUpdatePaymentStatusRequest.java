package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class TransactionMonitoringUpdatePaymentStatusRequest extends ServiceMessage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The payment in id. */
	@ApiModelProperty(value = "The payment in id", required = true)
	@JsonProperty(value = "paymentInId")
	private Integer paymentInId;
	
	/** The payment out id. */
	@ApiModelProperty(value = "The payment out id", required = true)
	@JsonProperty(value = "paymentOutId")
	private Integer paymentOutId;
	
	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty(value = "tradeAccountNumber")
	private String tradeAccountNumber;
	
	/** The trade contact id. */
	@ApiModelProperty(value = "The trade contact id", required = true)
	@JsonProperty(value = "tradeContactId")
	private Integer tradeContactId;
	
	/** The trade payment id. */
	@ApiModelProperty(value = "The trade payment id", required = true)
	@JsonProperty(value = "tradePaymentId")
	private Integer tradePaymentId;
	
	/** The org code. */
	@ApiModelProperty(value = "The accountSfId", required = true)
	@JsonProperty(value = "org_code")
	private String orgCode;
	
	/** The account id. */
	@ApiModelProperty(value = "The account number", required = true)
	@JsonProperty(value = "accountId")
	private Integer accountId;
	
	/** The account version. */
	@ApiModelProperty(value = "The account version", required = true)
	@JsonProperty(value = "accountVersion")
	private Integer accountVersion;

	public Integer getPaymentInId() {
		return paymentInId;
	}

	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}
	
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	public Integer getTradeContactId() {
		return tradeContactId;
	}

	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
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

	public Integer getAccountVersion() {
		return accountVersion;
	}

	public void setAccountVersion(Integer accountVersion) {
		this.accountVersion = accountVersion;
	}

	
}
