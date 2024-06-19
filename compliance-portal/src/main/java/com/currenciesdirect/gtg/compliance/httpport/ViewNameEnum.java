package com.currenciesdirect.gtg.compliance.httpport;

/**
 * The Enum ViewNameEnum.
 */
public enum ViewNameEnum {

	/** The registration queue. */
	REGISTRATION_QUEUE("RegistrationQueue", "Shows all compliance failed records"),

	/** The registratin details. */
	REGISTRATIN_DETAILS("RegistrationItem", "Shows selected registration details"),
	
	/** The registratin details. */
	HOLISTIC_FUNDS_IN_VIEW("HolisticFundsInView", "Shows selected holistic funds in details"),
	
	WALLET_DETAILS("WalletDetails", "Shows selected wallet details"),
	
	HOLISTIC_FUNDS_OUT_VIEW("HolisticFundsOutView", "Shows selected holistic funds out details"),

	/** The payment in queue. */
	PAYMENT_IN_QUEUE("PaymentInQueue", "show all failed payment In records"),

	/** The payment in queue. */
	PAYMENT_OUT_QUEUE("PaymentOutQueue", "Show failed payment out records"),

	/** The payment in details. */
	PAYMENT_IN_DETAILS("PaymentInDetails", ""),
	
	PAYMENT_OUT_DETAILS("PaymentOutDetails", ""),
	
	DASHBOARD("DashBoard", ""),
	
	DASHBOARD_COMPLIANCE("DashBoardCompliance", ""),
	
	DASHBOARD_BANKING("DashBoardBanking", ""),
	
	REG_REPORT("RegReport","Shows all Registration records report"),
	
	PAYMENTOUT_REPORT("PaymentOutReport","Shows all Payment Out records report"),
	
	PAYMENTIN_REPORT("PaymentInReport","Shows all Payment In records report"),
	
	WORK_EFFICIENCY_REPORT("WorkEfficiencyReport","Shows efficiency of all the users"),
	
	CFX_REGISTRATION_DETAILS("RegistrationCfxDetails","Shows selected cfx registration details"),
	
	CFX_PAYMENT_OUT_DETAILS("PaymentOutCfxDetails","Shows selected cfx payment out details"),
	
	CFX_PAYMENT_IN_DETAILS("PaymentInCfxDetails","Shows selected cfx payment in details"),
	
	ADMINISTRATION("Administration",""),
	
	HOLISTIC_REPORT("HolisticReport","Shows all Holistic records report"),
	
	PFX_HOLISTIC_VIEW("PFXHolisticView", "Shows PFX holistic details"),
	
	CFX_HOLISTIC_VIEW("CFXHolisticView", "Shows CFX holistic details"),
	
	BENE_REPORT("BeneReport","Shows all Beneficiaries list"),
	
	BENE_DETAILS("BeneficiaryDetails","Shows details of selected beneficiary"),
	
	FRAUD_RING_GRAPH_VIEW("FraudRingGrpah", "Shows fraud ring graph for specific client"),
	
	DATA_ANONYMISATION_QUEUE("DataAnonymisationQueue", "Shows all compliance anonymisation records"),;

	/** The view name. */
	private String viewName;

	/** The view description. */
	private String viewDescription;

	/**
	 * Instantiates a new view name enum.
	 *
	 * @param viewName
	 *            the view name
	 * @param viewDescription
	 *            the view description
	 */
	ViewNameEnum(String viewName, String viewDescription) {
		this.viewName = viewName;
		this.viewDescription = viewDescription;
	}

	/**
	 * Gets the view name.
	 *
	 * @return the view name
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * Gets the view description.
	 *
	 * @return the view description
	 */
	public String getViewDescription() {
		return viewDescription;
	}

}
