package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * The Class FragusterCsvData.
 */
public class FragusterCsvData {
	
	private String fraugsterId;
	
	private String cdUniqueId;
	
	private boolean fragusterApproved;
	
	private boolean asynchStatus;
	
	private String asynchStatusData;

	public String getFraugsterId() {
		return fraugsterId;
	}

	public void setFraugsterId(String fraugsterId) {
		this.fraugsterId = fraugsterId;
	}

	public String getCdUniqueId() {
		return cdUniqueId;
	}

	public void setCdUniqueId(String cdUniqueId) {
		this.cdUniqueId = cdUniqueId;
	}

	public boolean isFragusterApproved() {
		return fragusterApproved;
	}

	public void setFragusterApproved(boolean fragusterApproved) {
		this.fragusterApproved = fragusterApproved;
	}

	public boolean isAsynchStatus() {
		return asynchStatus;
	}

	public void setAsynchStatus(boolean asynchStatus) {
		this.asynchStatus = asynchStatus;
	}

	public String getAsynchStatusData() {
		return asynchStatusData;
	}

	public void setAsynchStatusData(String asynchStatusData) {
		this.asynchStatusData = asynchStatusData;
	}

}
