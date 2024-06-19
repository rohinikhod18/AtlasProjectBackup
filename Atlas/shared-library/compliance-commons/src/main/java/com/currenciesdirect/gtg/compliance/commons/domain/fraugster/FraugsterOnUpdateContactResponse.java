/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response.DecisionDrivers;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class FraugsterOnUpdateContactResponse.
 *
 * @author manish
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FraugsterOnUpdateContactResponse extends FraugsterBaseResponse  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The decision drivers */
	private DecisionDrivers decisionDrivers;

	/**
	 * @return decision Drivers
	 */
	public DecisionDrivers getDecisionDrivers() {
		return decisionDrivers;
	}

	/**
	 * @param decision Drivers
	 */
	public void setDecisionDrivers(DecisionDrivers decisionDrivers) {
		this.decisionDrivers = decisionDrivers;
	}

}
