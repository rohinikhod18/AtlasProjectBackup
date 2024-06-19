package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

import java.util.List;

public class BusinessUnit {
	
	/** The queue records per LegalEntity. */
	private List<QueueRecordsPerLegalEntity> queueRecordsPerLegalEntity;

	public List<QueueRecordsPerLegalEntity> getQueueRecordsPerLegalEntity() {
		return queueRecordsPerLegalEntity;
	}

	public void setQueueRecordsPerLegalEntity(List<QueueRecordsPerLegalEntity> queueRecordsPerLegalEntity) {
		this.queueRecordsPerLegalEntity = queueRecordsPerLegalEntity;
	}
}
