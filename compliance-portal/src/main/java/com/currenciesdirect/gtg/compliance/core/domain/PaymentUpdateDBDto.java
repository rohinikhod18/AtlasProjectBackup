package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

public class PaymentUpdateDBDto extends BaseUpdateDBDto {

	private List<String> previousReason;
	
	private String tradeContactid;
	
	private String tradeContractnumber;
	
	private String tradePaymentid;
	
	
	public List<String> getPreviousReason() {
		return previousReason;
	}

	public void setPreviousReason(List<String> previousReason) {
		this.previousReason = previousReason;
	}

	public String getTradeContactid() {
		return tradeContactid;
	}

	public void setTradeContactid(String tradeContactid) {
		this.tradeContactid = tradeContactid;
	}

	public String getTradeContractnumber() {
		return tradeContractnumber;
	}

	public void setTradeContractnumber(String tradeContractnumber) {
		this.tradeContractnumber = tradeContractnumber;
	}

	public String getTradePaymentid() {
		return tradePaymentid;
	}

	public void setTradePaymentid(String tradePaymentid) {
		this.tradePaymentid = tradePaymentid;
	}
	
}
