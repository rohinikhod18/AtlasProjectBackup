package com.currenciesdirect.gtg.compliance.commons.domain.activity;

/**
 * The Enum PaymentMethodEnum.
 */
public enum PaymentMethodEnum {

	/** The unknown. */
	UNKNOWN(0, "UNKNOWN", "UNKNOWN"),

	/** The foa. */
	FOA(1, "FOA", "FOA"),

	/** The switchdebit. */
	SWITCHDEBIT(2, "SWITCH/DEBITCARD", "SWITCH/DEBIT"),

	/** The directdebit. */
	DIRECTDEBIT(3, "DIRECTDEBIT", "DIRECTDEBIT"),

	/** The chequedraft. */
	CHEQUEDRAFT(4, "CHEQUE/DRAFT", "CHEQUE/DRAFT"),

	/** The bacschapstt. */
	BACSCHAPSTT(5, "BACS/CHAPS/TT", "BACS/CHAPS/TT"),
	
	/** The Wallet.*/
	WALLET(6,"WALLET","WALLET"),
	
	/** The returnoffunds. */
	RETURNOFFUNDS(7,"RETURN_OF_FUNDS","RETURN_OF_FUNDS"),
	
	/** The wire. */
	WIRE(8,"WIRE","WIRE"),
	
	/** The ach. */
	ACH(9,"ACH","ACH"),
	
	/** The neft. */
	NEFT(10,"NEFT","NEFT"),
	
	/** The rtgs. */
	RTGS(11,"RTGS","RTGS"),
	
	/** The fps. */
	FPS(12,"FPS","FPS"),
	
	/** The cheque. */
	CHEQUE(13,"CHEQUE","CHEQUE"),
	
	/** The bacs. */
	BACS(14,"BACS","BACS"),
	
	/** The chaps. */
	CHAPS(15,"CHAPS","CHAPS"),
	
	/** The eft. */
	EFT(16,"EFT","EFT"),
	
	/** The sepa. */
	SEPA(17,"SEPA","SEPA");

	/** The request payment method. */
	private String requestPaymentMethod;

	/** The database payment method. */
	private String databasePaymentMethod;

	/** The paymen method as integer. */
	private Integer paymenMethodAsInteger;

	/**
	 * Instantiates a new payment method enum.
	 *
	 * @param paymenMethodAsInteger
	 *            the paymen method as integer
	 * @param requestPaymentMethod
	 *            the request payment method
	 * @param databasePaymentMethod
	 *            the database payment method
	 */
	PaymentMethodEnum(Integer paymenMethodAsInteger, String requestPaymentMethod, String databasePaymentMethod) {
		this.requestPaymentMethod = requestPaymentMethod;
		this.databasePaymentMethod = databasePaymentMethod;
		this.paymenMethodAsInteger = paymenMethodAsInteger;
	}

	/**
	 * Gets the request payment method.
	 *
	 * @return the request payment method
	 */
	public String getRequestPaymentMethod() {
		return requestPaymentMethod;
	}

	/**
	 * Gets the database payment method.
	 *
	 * @return the database payment method
	 */
	public String getDatabasePaymentMethod() {
		return databasePaymentMethod;
	}

	/**
	 * Gets the paymen method as integer.
	 *
	 * @return the paymen method as integer
	 */
	public Integer getPaymenMethodAsInteger() {
		return paymenMethodAsInteger;
	}

	/**
	 * Gets the database methodfrom request.
	 *
	 * @param status
	 *            the status
	 * @return the database methodfrom request
	 */
	public static String getDatabaseMethodfromRequest(String status) {
		for (PaymentMethodEnum paymentMethodEnum : PaymentMethodEnum.values()) {
			if (paymentMethodEnum.getRequestPaymentMethod().equalsIgnoreCase(status)) {
				return paymentMethodEnum.getDatabasePaymentMethod();
			}
		}
		return null;
	}

	/**
	 * Gets the payment method as integer.
	 *
	 * @param method
	 *            the method
	 * @return the payment method as integer
	 */
	public static Integer getPaymentMethodAsInteger(String method) {
		for (PaymentMethodEnum paymentMethodEnum : PaymentMethodEnum.values()) {
			if (paymentMethodEnum.getDatabasePaymentMethod().equalsIgnoreCase(method)) {
				return paymentMethodEnum.getPaymenMethodAsInteger();
			}
		}
		return null;
	}

}
