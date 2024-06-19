/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterSearchDataResponse.
 *
 * @author manish
 */
public class FraugsterSearchDataResponse extends FraugsterBaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The error msg. */
	@JsonProperty("error_msg")
	private String errorMsg;
	
	/** The frg trans id. */
	@JsonProperty("frg_trans_id")
	private String frgTransId;
	
	/** The fraugster approved. */
	@JsonProperty("fraugster_approved")
	private String fraugsterApproved;
	
	/** The investigation points. */
	@JsonProperty("investigation_points")
	private List<String> investigationPoints;
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#getErrorMsg()
	 */
	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#getFrgTransId()
	 */
	@Override
	public String getFrgTransId() {
		return frgTransId;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#getInvestigationPoints()
	 */
	@Override
	public List<String> getInvestigationPoints() {
		return investigationPoints;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#setErrorMsg(java.lang.String)
	 */
	@Override
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#setFrgTransId(java.lang.String)
	 */
	@Override
	public void setFrgTransId(String frgTransId) {
		this.frgTransId = frgTransId;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#setInvestigationPoints(java.util.List)
	 */
	@Override
	public void setInvestigationPoints(List<String> investigationPoints) {
		this.investigationPoints = investigationPoints;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#getFraugsterApproved()
	 */
	@Override
	public String getFraugsterApproved() {
		return fraugsterApproved;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse#setFraugsterApproved(java.lang.String)
	 */
	@Override
	public void setFraugsterApproved(String fraugsterApproved) {
		this.fraugsterApproved = fraugsterApproved;
	}

}
