package com.currenciesdirect.gtg.compliance.dbport.enums;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;

/**
 * The Enum WorkEfficiencySlaEnum.
 */
public enum WorkEfficiencySlaEnum {
	
	/** The pfx contact. */
	PFX_CONTACT("PFX_Contact",Constants.SLA_PFX_CONTACT),
	
	/** The cfx account. */
	CFX_ACCOUNT("CFX_Account",Constants.SLA_CFX_ACCOUNT),
	
	/** The pfx payment in. */
	PFX_PAYMENT_IN("PFX_Payment In",Constants.SLA_PFX_PAYMENT_IN),
	
	/** The cfx payment in. */
	CFX_PAYMENT_IN("CFX_Payment In",Constants.SLA_CFX_PAYMENT_IN),
	
	/** The pfx payment out. */
	PFX_PAYMENT_OUT("PFX_Payment Out",Constants.SLA_PFX_PAYMENT_OUT),
	
	/** The cfx payment out. */
	CFX_PAYMENT_OUT("CFX_Payment Out",Constants.SLA_CFX_PAYMENT_OUT),
	
	/** The cfx etailer payment in. */
	CFX_ETAILER_PAYMENT_IN("CFX (etailer)_Payment In",Constants.SLA_CFX_ETAILER_PAYMENT_IN),
	
	/** The cfx etailer payment out. */
	CFX_ETAILER_PAYMENT_OUT("CFX (etailer)_Payment Out",Constants.SLA_CFX_ETAILER_PAYMENT_OUT),
	
	/** The cfx etailer account. */
	CFX_ETAILER_ACCOUNT("CFX (etailer)_Account",Constants.SLA_CFX_ETAILER_ACCOUNT);
	
	/** The sla value. */
	private String slaValue;
	
	/** The sla property. */
	private String slaProperty;
	
	/**
	 * Instantiates a new work efficiency sla enum.
	 *
	 * @param slaValue the sla value
	 * @param slaProperty the sla property
	 */
	WorkEfficiencySlaEnum (String slaValue, String slaProperty){
		this.slaValue = slaValue;
		this.slaProperty = slaProperty;
		
	}

	/**
	 * Gets the sla property from sla value.
	 *
	 * @param slaValue the sla value
	 * @return the sla property from sla value
	 */
	public static String getSlaPropertyFromSlaValue(String slaValue) {
		for (WorkEfficiencySlaEnum wrkEfficiency : WorkEfficiencySlaEnum.values()) {
			if (wrkEfficiency.getSlaValue().equals(slaValue)) {
				return wrkEfficiency.getSlaProperty();
			}
		}
		return Constants.SLA_PFX_CONTACT;
	}
	
	/**
	 * Gets the sla value.
	 *
	 * @return the sla value
	 */
	public String getSlaValue() {
		return slaValue;
	}
	
	/**
	 * Gets the sla property.
	 *
	 * @return the sla property
	 */
	public String getSlaProperty() {
		return slaProperty;
	}

	
}
