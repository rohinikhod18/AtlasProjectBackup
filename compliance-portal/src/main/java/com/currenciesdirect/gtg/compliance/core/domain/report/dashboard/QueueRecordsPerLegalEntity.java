package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

public class QueueRecordsPerLegalEntity {

	private String legalEntity;
	
	/** The records per legalEntity. */
	private Integer visits;

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public Integer getVisits() {
		return visits;
	}

	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	@Override
	public String toString() {
		return "QueueRecordsPerLegalEntity [legalEntity=" + legalEntity + ", visits=" + visits + "]";
	}

}
