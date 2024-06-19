package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum QueueType.
 */
public enum QueueType {
	
	CONTACT("Contact"),
	PAYMENT_IN("Payment In"),
	PAYMENT_OUT("Payment Out"),
	ACCOUNT("Account");
	
	private String name;

	private QueueType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static List<String> getQueueTypeValues(){
		List<String> ls = new ArrayList<>();
		QueueType[] values = QueueType.values();
		for(QueueType value : values){
			ls.add(value.name);
		}
		return ls;
	}
}
































 