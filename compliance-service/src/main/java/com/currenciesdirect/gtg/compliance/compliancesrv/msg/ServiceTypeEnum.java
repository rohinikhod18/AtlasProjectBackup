package com.currenciesdirect.gtg.compliance.compliancesrv.msg;

import com.currenciesdirect.gtg.compliance.commons.util.Constants;

/**
 * The Enum ServiceTypeEnum.
 */
public enum ServiceTypeEnum {

	/** The gateway service. */
	GATEWAY_SERVICE(true, false, false, "GATEWAY"),

	/** The kyc service. */
	KYC_SERVICE(false, false, true, "KYC"),

	/** The black list service. */
	BLACK_LIST_SERVICE(false, false, true, "BLACKLIST"),

	/** The ip service. */
	IP_SERVICE(false, false, true, "IP"),

	/** The hrc service. */
	HRC_SERVICE(false, false, true, "COUNTRYCHECK"),

	/** The global check service. */
	GLOBAL_CHECK_SERVICE(false, false, true, "GLOBALCHECK"),

	/** The card fraud score service. */
	CARD_FRAUD_SCORE_SERVICE(false, false, true, "CARDFRAUDSCORE"),
	//AT-3714
	/** The card fraud score service. */
	FRAUD_SIGHT_SCORE_SERVICE(false, false, true, "FRAUDSIGHTSCORE"),

	/** The fraugster service. */
	FRAUGSTER_SERVICE(false, false, true, "FRAUGSTER"),

	/** The fraugster onupdate service. */
	FRAUGSTER_ONUPDATE_SERVICE(false, false, true, "FRAUGSTER"),

	/** The sanction service. */
	SANCTION_SERVICE(false, false, true, "SANCTION"),

	/** The document upload service. */
	DOCUMENT_UPLOAD_SERVICE(false, false, true, "DOCUMENT"),

	/** The internal rule service. */
	INTERNAL_RULE_SERVICE(false, false, true, "INTERNAL"),

	/** The custom checks service. */
	CUSTOM_CHECKS_SERVICE(false, false, true, "VELOCITYCHECK"),

	/** The save to broadcast service. */
	SAVE_TO_BROADCAST_SERVICE(false, false, false, "SAVETOBROADCAST"),

	/** The fundsin delete service. */
	FUNDSIN_DELETE_SERVICE(false, false, false, "FUNDSINDELETE"),
	
	/** The fundsout bulk recheck service. */
	FUNDSOUT_BULK_RECHECK_SERVICE(false, false, false, "FUNDSOUTBULKRECHECK"),
	
	/** The fundsout bulk recheck service. */
	FUNDSIN_BULK_RECHECK_SERVICE(false, false, false, "FUNDSINBULKRECHECK"),
	
	/** The registration bulk recheck service. */
	REGISTRATION_BULK_RECHECK_SERVICE(false, false, false, "REGISTRATIONBULKRECHECK"),
	
	/** The onfido service. */
	ONFIDO_SERVICE(false,true,false,"ONFIDO"),
		
	/** BLACK_LIST_PAYREF_SERVICE */
	BLACKLIST_PAY_REF_SERVICE(false,false,true,"BLACKLIST_PAY_REF"),
	
	TRANSACTION_MONITORING_SERVICE(false, false, true, Constants.TRANSACTION_MONITORING),
	
	//Added for AT-4014
	TRANSACTION_MONITORING_UPDATE_SERVICE(false, false, true, Constants.TRANSACTION_MONITORING),
	
	TRANSACTION_MONITORING_MQ_RESEND(false, false, true, Constants.TRANSACTION_MONITORING);

	/** The gateway service. */
	private boolean gatewayService;

	/** The integration feature services. */
	private boolean integrationFeatureServices;

	/** The core system service. */
	private boolean coreSystemService;

	/** The short name. */
	private String shortName;

	/**
	 * Instantiates a new service type enum.
	 *
	 * @param acqAdapterService
	 *            the acq adapter service
	 * @param integrationFeatureServices
	 *            the integration feature services
	 * @param coreSystemService
	 *            the core system service
	 * @param shortName
	 *            the short name
	 */
	private ServiceTypeEnum(boolean acqAdapterService, boolean integrationFeatureServices, boolean coreSystemService,
			String shortName) {
		this.gatewayService = acqAdapterService;
		this.integrationFeatureServices = integrationFeatureServices;
		this.coreSystemService = coreSystemService;
		this.shortName = shortName;
	}

	/**
	 * Checks if is acq adapter service.
	 *
	 * @return true, if is acq adapter service
	 */
	public boolean isAcqAdapterService() {
		return gatewayService;
	}

	/**
	 * Checks if is integration feature services.
	 *
	 * @return true, if is integration feature services
	 */
	public boolean isIntegrationFeatureServices() {
		return integrationFeatureServices;
	}

	/**
	 * Checks if is core system service.
	 *
	 * @return true, if is core system service
	 */
	public boolean isCoreSystemService() {
		return coreSystemService;
	}

	/**
	 * Gets the short name.
	 *
	 * @return the short name
	 */
	public String getShortName() {
		return shortName;
	}

}