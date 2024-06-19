package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum SourceEnum.
 */
public enum SourceEnum {
	
	AFFILIATE("Affiliate"),
	BILLBOARDS("Billboards"),
	BOOK("Book"), 
	CHARITY("Charity"),
	MAIL("Direct Mail - Post / Mail"),
	EMAIL_CAMPAIGN("Email campaign"),
	EXHIBITIONS_AND_EVENTS("Exhibitions & Events"),
	INTERNET("Internet"),
	LIST("List"),
	MAGAZINE("Magazine"),
	NETWORKING("Networking"),
	NEWSPAPER("Newspaper"),
	OTHER("Other"),
	PRESS("Press"),
	RADIO("Radio"),
	REFERRAL("Referral"),
	SELGEN("SelfGen"),
	OUTDOOR_ADVERTISING("Outdoor advertising"),
	SELF_GENERATED("Self Generated"),
	SHOW("Show"),
	SPONSORSHIP("Sponsorship"),
	TV("TV"),
	WEB("Web");

	private String name;

	private SourceEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static List<String> getSourceValues(){
		List<String> ls = new ArrayList<>();
		SourceEnum[] values = SourceEnum.values();
		for(SourceEnum value : values){
			ls.add(value.name);
		}
		return ls;
	}
}
































 