package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum TransactionValuesEnum.
 */
public enum TransactionValuesEnum {
	
	TXN_UNDER_2000("Under 2,000"),
	TXN_2000_5000("2,000 - 5,000"),
	TXN_2000_10000("2,000 - 10,000"), 
	TXN_5000_10000("5,000 - 10,000"),
	TXN_10000_250000("10,000 - 25,000"),
	TXN_25000_50000("25,000 - 50,000"),
	TXN_50000_100000("50,000 - 100,000"),
	TXN_100000_250000("100,000 - 250,000"),
	TXN_OVER_250000("over 250,000"),
	//Add for AT-4789
	TXN_UNDER_100000("Under 100,000"),
	TXN_100000_1000000("100,000 - 1,000,000"),
	TXN_1000000_10000000("1,000,000 - 10,000,000"), 
	TXN_OVER_10000000("over 10,000,000");
	

	private String name;

	private TransactionValuesEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
	public static List<String> getTransactionValues(){
		List<String> ls = new ArrayList<>();
		TransactionValuesEnum[] values = TransactionValuesEnum.values();
		for(TransactionValuesEnum value : values){
			ls.add(value.name);
		}
		return ls;
	}
	
}
































 