package com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.IDomain;

import io.swagger.annotations.ApiModelProperty;

public class PostCardTransactionMQRequest extends ServiceMessage implements IDomain, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@ApiModelProperty(value = "id", required = true)
	private Integer id;
	
	
	/** The transaction ID. */
	@ApiModelProperty(value = "transaction id", required = true)
	private String transactionID;
	
	/** The card request type. */
	@ApiModelProperty(value = "request type", required = true)
	private String cardRequestType;
	
	/** The request json. */
	@ApiModelProperty(value = "request json", required = true)
	private Object requestJson;
	
	/** The is present. */
	@ApiModelProperty(value = "isPresent", required = true)
	private Integer isPresent;
	
	/** The created by. */
	@ApiModelProperty(value = "created by", required = true)
	private Integer createdBy;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * @return the cardRequestType
	 */
	public String getCardRequestType() {
		return cardRequestType;
	}

	/**
	 * @param cardRequestType the cardRequestType to set
	 */
	public void setCardRequestType(String cardRequestType) {
		this.cardRequestType = cardRequestType;
	}

	/**
	 * @return the requestJson
	 */
	public Object getRequestJson() {
		return requestJson;
	}

	/**
	 * @param requestJson the requestJson to set
	 */
	public void setRequestJson(Object requestJson) {
		this.requestJson = requestJson;
	}

	/**
	 * @return the isPresent
	 */
	public Integer getIsPresent() {
		return isPresent;
	}

	/**
	 * @param isPresent the isPresent to set
	 */
	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
