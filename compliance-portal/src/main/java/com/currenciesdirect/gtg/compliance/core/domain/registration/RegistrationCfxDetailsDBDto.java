package com.currenciesdirect.gtg.compliance.core.domain.registration;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;

public class RegistrationCfxDetailsDBDto  {

	private List<RegistrationDetailsDBDto> regCfxDetailsDBDto;
	
	private List<UploadDocumentTypeDBDto> documentTypeList;
	
	private PaginationDetails paginationDetails;
	
	private Watchlist watachList;

	private ActivityLogs activityLogs;

	private StatusReason contactStatusReason;
	
	private String legalEntity;
	
	private String alertComplianceLog;
	
	private Boolean isOnQueue;

	private String dataAnonStatus;
	
	private String poiExists;
	
	public List<RegistrationDetailsDBDto> getRegCfxDetailsDBDto() {
		return regCfxDetailsDBDto;
	}

	public void setRegCfxDetailsDBDto(List<RegistrationDetailsDBDto> regCfxDetailsDBDto) {
		this.regCfxDetailsDBDto = regCfxDetailsDBDto;
	}

	public List<UploadDocumentTypeDBDto> getDocumentTypeList() {
		return documentTypeList;
	}

	public void setDocumentTypeList(List<UploadDocumentTypeDBDto> documentTypeList) {
		this.documentTypeList = documentTypeList;
	}

	public PaginationDetails getPaginationDetails() {
		return paginationDetails;
	}

	public void setPaginationDetails(PaginationDetails paginationDetails) {
		this.paginationDetails = paginationDetails;
	}

	public Watchlist getWatachList() {
		return watachList;
	}

	public void setWatachList(Watchlist watachList) {
		this.watachList = watachList;
	}

	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}

	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

	public StatusReason getContactStatusReason() {
		return contactStatusReason;
	}

	public void setContactStatusReason(StatusReason contactStatusReason) {
		this.contactStatusReason = contactStatusReason;
	}
	
	/**
	 * Gets the legal entity.
	 *
	 * @return the legal entity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * Sets the legal entity.
	 *
	 * @param legalEntity the new legal entity
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getAlertComplianceLog() {
		return alertComplianceLog;
	}

	public void setAlertComplianceLog(String alertComplianceLog) {
		this.alertComplianceLog = alertComplianceLog;
	}

	public Boolean getIsOnQueue() {
		return isOnQueue;
	}

	public void setIsOnQueue(Boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	/**
	 * @return the dataAnonStatus
	 */
	public String getDataAnonStatus() {
		return dataAnonStatus;
	}

	/**
	 * @param dataAnonStatus the dataAnonStatus to set
	 */
	public void setDataAnonStatus(String dataAnonStatus) {
		this.dataAnonStatus = dataAnonStatus;
	}

	/**
	 * @return the poiExists
	 */
	public String getPoiExists() {
		return poiExists;
	}

	/**
	 * @param poiExists the poiExists to set
	 */
	public void setPoiExists(String poiExists) {
		this.poiExists = poiExists;
	}
	
	
}
