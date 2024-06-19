package com.currenciesdirect.gtg.compliance.core.domain;



public class PaymentDetailsDto extends BaseDetailDto{
	
	/** The fraugster. */
	private Fraugster fraugster;
	
	private FurtherPaymentDetails furtherPaymentDetails;
	
	/** The custom checks. */
	private CustomCheck customCheck;
	
	private Watchlist contactWatchlist;
	
	private String name;
	
	private IntuitionPaymentResponse intuition;
	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Fraugster getFraugster() {
		return fraugster;
	}

	public void setFraugster(Fraugster fraugster) {
		this.fraugster = fraugster;
	}

	public CustomCheck getCustomCheck() {
		return customCheck;
	}

	public void setCustomCheck(CustomCheck customCheck) {
		this.customCheck = customCheck;
	}

	public Watchlist getContactWatchlist() {
		return contactWatchlist;
	}

	public void setContactWatchlist(Watchlist contactWatchlist) {
		this.contactWatchlist = contactWatchlist;
	}

	public FurtherPaymentDetails getFurtherPaymentDetails() {
		return furtherPaymentDetails;
	}

	public void setFurtherPaymentDetails(FurtherPaymentDetails furtherPaymentDetails) {
		this.furtherPaymentDetails = furtherPaymentDetails;
	}

	public IntuitionPaymentResponse getIntuition() {
		return intuition;
	}

	public void setIntuition(IntuitionPaymentResponse intuition) {
		this.intuition = intuition;
	}
	

}
