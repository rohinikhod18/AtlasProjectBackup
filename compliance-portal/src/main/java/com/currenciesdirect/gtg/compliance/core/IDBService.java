package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryRequest;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoRequest;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IDBService.
 */
public interface IDBService {
	
	/**
	 * Gets the activity logs.
	 *
	 * @param request the request
	 * @return the activity logs
	 * @throws CompliancePortalException 
	 */
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException;

	/**
	 * Gets the provider response.
	 *
	 * @param logRequest the log request
	 * @return the provider response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ProviderResponseLogResponse getProviderResponse(ProviderResponseLogRequest logRequest) throws CompliancePortalException;

	/**
	 *  Gets the device info.
	 * 
	 * @param deviceInfoRequest the device info request
	 * @return
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DeviceInfoResponse getDeviceInfo(DeviceInfoRequest deviceInfoRequest) throws CompliancePortalException;

	/**
	 *  Gets the account history.
	 * 
	 * @param accountHistoryRequest the account history request
	 * @return
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public AccountHistoryResponse getAccountHistory(AccountHistoryRequest accountHistoryRequest) throws CompliancePortalException;
	
	/**
	 * @param lockResourceDBDto
	 * @return
	 * @throws CompliancePortalException
	 */
	public LockResourceResponse insertLockResource(LockResourceDBDto lockResourceDBDto) throws CompliancePortalException;
	
	/**
	 * @param lockResourceDBDto
	 * @return
	 * @throws CompliancePortalException 
	 */
	public LockResourceResponse updateLockResource(LockResourceDBDto lockResourceDBDto) throws CompliancePortalException;

}
