/*
 * Copyright 2012-2017 Currencies Direct Ltd, United Kingdom
 *
 * titan-wrapper-service: PayeePaymentMethod.java Last modified: 26-Jul-2017
 */
package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PayeePaymentMethod.
 *
 * @author Suhas S
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperPayeePaymentMethod implements IDomain,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The payee. */
	@JsonProperty("payee_id")
	private Integer payee;

	/** The payment method. */
	@JsonProperty("payment_method")
	private Integer paymentMethod;

	/** The in bound out bound. */
	@JsonProperty("in_bound_out_bound")
	private String inBoundOutBound;

	/** The payment method code. */
	@JsonProperty("payment_method_code")
	private String paymentMethodCode;

	/** The payee bank list. */
	@JsonProperty("payee_bank")
	private WrapperPayeeBank payeeBank;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		if (null != id) {
			this.id = id;
		}
	}

	/**
	 * Gets the payee.
	 *
	 * @return the payee
	 */
	public Integer getPayee() {
		return payee;
	}

	/**
	 * Sets the payee.
	 *
	 * @param payee
	 *            the payee to set
	 */
	public void setPayee(Integer payee) {
		if (null != payee) {
			this.payee = payee;
		}
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the paymentMethod
	 */
	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(Integer paymentMethod) {
		if (null != paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
	}

	/**
	 * Gets the in bound out bound.
	 *
	 * @return the inBoundOutBound
	 */
	public String getInBoundOutBound() {
		return inBoundOutBound;
	}

	/**
	 * Sets the in bound out bound.
	 *
	 * @param inBoundOutBound
	 *            the inBoundOutBound to set
	 */
	public void setInBoundOutBound(String inBoundOutBound) {
		if (null != inBoundOutBound && !inBoundOutBound.isEmpty()) {
			this.inBoundOutBound = inBoundOutBound;
		}
	}

	/**
	 * Gets the payment method code.
	 *
	 * @return the paymentMethodCode
	 */
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}

	/**
	 * Sets the payment method code.
	 *
	 * @param paymentMethodCode
	 *            the paymentMethodCode to set
	 */
	public void setPaymentMethodCode(String paymentMethodCode) {
		if (null != paymentMethodCode && !paymentMethodCode.isEmpty()) {
			this.paymentMethodCode = paymentMethodCode;
		}
	}

	/**
	 * Gets the payee bank.
	 *
	 * @return the payeeBank
	 */
	public WrapperPayeeBank getPayeeBank() {
		return payeeBank;
	}

	/**
	 * Sets the payee bank.
	 *
	 * @param payeeBank
	 *            the payeeBank to set
	 */
	public void setPayeeBank(WrapperPayeeBank payeeBank) {
		this.payeeBank = payeeBank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayeePaymentMethod [id=");
		builder.append(id);
		builder.append(", payee=");
		builder.append(payee);
		builder.append(", paymentMethod=");
		builder.append(paymentMethod);
		builder.append(", inBoundOutBound=");
		builder.append(inBoundOutBound);
		builder.append(", paymentMethodCode=");
		builder.append(paymentMethodCode);
		builder.append(", payeeBank=");
		builder.append(payeeBank);
		builder.append(']');
		return builder.toString();
	}

}
