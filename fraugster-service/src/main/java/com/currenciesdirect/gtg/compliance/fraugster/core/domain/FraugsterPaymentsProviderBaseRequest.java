/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;


/**
 * @author manish
 *
 */
@SuppressWarnings("squid:S100")
public class FraugsterPaymentsProviderBaseRequest extends FraugsterProviderBaseRequest{

	private static final long serialVersionUID = 1L;

	private String  custFirstName;
	
	private String  custLastName;
	
	private Float  transAmt;
	
	private String  transCurrency;
	
	private String  customerAccountNumber;

	private String  trfrReason;

	private Float 	custSignupScore ;
	

	public String getCust_first_name() {
		return custFirstName;
	}

	public void setCust_first_name(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCust_last_name() {
		return custLastName;
	}

	public void setCust_last_name(String custLastName) {
		this.custLastName = custLastName;
	}

	public Float getTrans_amt() {
		return transAmt;
	}

	public void setTrans_amt(Float transAmt) {
		this.transAmt = transAmt;
	}

	public String getTrans_currency() {
		return transCurrency;
	}

	public void setTrans_currency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	public String getCustomer_account_number() {
		return customerAccountNumber;
	}

	public void setCustomer_account_number(String customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}

	public String getTrfr_reason() {
		return trfrReason;
	}

	public void setTrfr_reason(String trfrReason) {
		this.trfrReason = trfrReason;
	}

	public Float getCust_signup_score() {
		return custSignupScore;
	}

	public void setCust_signup_score(Float custSignupScore) {
		this.custSignupScore = custSignupScore;
	}
}
