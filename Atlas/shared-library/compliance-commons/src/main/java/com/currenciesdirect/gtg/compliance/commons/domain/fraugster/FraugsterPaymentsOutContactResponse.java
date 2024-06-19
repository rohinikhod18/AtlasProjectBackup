/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsout.response.DecisionDrivers;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class FraugsterPaymentsOutContactResponse.
 *
 * @author manish
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FraugsterPaymentsOutContactResponse extends FraugsterBaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The decision drivers */
	private DecisionDrivers decisionDrivers;

	/**
	 * @return decisionDrivers
	 */
	public DecisionDrivers getDecisionDrivers() {
		return decisionDrivers;
	}

	/**
	 * @param decisionDrivers
	 */
	public void setDecisionDrivers(DecisionDrivers decisionDrivers) {
		this.decisionDrivers = decisionDrivers;
	}
	
}