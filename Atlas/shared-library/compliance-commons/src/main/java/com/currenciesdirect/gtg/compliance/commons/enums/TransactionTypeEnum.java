package com.currenciesdirect.gtg.compliance.commons.enums;

/**
 * The Enum TransactionTypeEnum.
 */
public enum TransactionTypeEnum {

	
	/** The account. */
	ACCOUNT(1,"ACCOUNT"),
	
	/** The paymentin. */
	PAYMENTIN(2,"PAYMENTIN"),
	
	/** The paymentout. */
	PAYMENTOUT(3,"PAYMENTOUT"),
	
	/** The contact. */
	CONTACT(4,"CONTACT");
	

	/** The transaction type as integer. */
	private Integer transactionTypeAsInteger;
	
	/** The transaction type as string. */
	private String transactionTypeAsString;

	
	/**
	 * Instantiates a new transaction type enum.
	 *
	 * @param transactionTypeAsInteger the transaction type as integer
	 * @param transactionTypeAsString the transaction type as string
	 */
	TransactionTypeEnum(Integer transactionTypeAsInteger, String transactionTypeAsString) {
		this.transactionTypeAsInteger = transactionTypeAsInteger;
		this.transactionTypeAsString = transactionTypeAsString;
	}


	
	/**
	 * Gets the transaction type as integer.
	 *
	 * @return the transaction type as integer
	 */
	public Integer getTransactionTypeAsInteger() {
		return transactionTypeAsInteger;
	}

	
	/**
	 * Gets the transaction type as string.
	 *
	 * @return the transaction type as string
	 */
	public String getTransactionTypeAsString() {
		return transactionTypeAsString;
	}
	
	
	
	/**
	 * Gets the transaction type as string.
	 *
	 * @param type the type
	 * @return the transaction type as string
	 */
	public static String getTransactionTypeAsString(Integer type) {
		for (TransactionTypeEnum status : TransactionTypeEnum.values()) {
			if (status.getTransactionTypeAsInteger().equals(type)) {
				return status.getTransactionTypeAsString();
			}
		}
		return null;
	}

	
	/**
	 * Gets the transaction type as integer.
	 *
	 * @param type the type
	 * @return the transaction type as integer
	 */
	public static Integer getTransactionTypeAsInteger(String type) {
		for (TransactionTypeEnum status : TransactionTypeEnum.values()) {
			if (status.getTransactionTypeAsString().equals(type)) {
				return status.getTransactionTypeAsInteger();
			}
		}
		return null;
	}





}
