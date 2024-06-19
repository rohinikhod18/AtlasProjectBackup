package com.currenciesdirect.gtg.compliance.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class HolisticViewServiceImpl.
 */
@Component("holisticViewServiceImpl")
public class HolisticViewServiceImpl implements IHolisticViewService {

	private Logger log = LoggerFactory.getLogger(HolisticViewServiceImpl.class);
	
	@Autowired
	@Qualifier("holisticDBServiceImpl")
	private IHolisticViewDBService iHolisticViewDBService;
	
	/** The i activity log DB service. */
	@Autowired
	@Qualifier("activityLogDBServiceImpl")
	private IActivityLogDBService iActivityLogDBService;
	
	@Override
	public HolisticViewResponse getHolisticViewDetails(HolisticViewRequest holisticViewRequest) throws CompliancePortalException {

		HolisticViewResponse holisticViewResponse = null;
		try {
			holisticViewResponse = iHolisticViewDBService.getHolisticAccountDetails(holisticViewRequest);
			holisticViewResponse = iHolisticViewDBService.getHolisticPaymentDetails(holisticViewRequest,holisticViewResponse);
			iHolisticViewDBService.addDocuments(holisticViewResponse);
			iHolisticViewDBService.getFurtherPaymentDetailsList(holisticViewResponse);
			// for landing page get only TOP 10 records
			ActivityLogRequest request = new ActivityLogRequest();
			request.setAccountId(holisticViewRequest.getAccountId());
			request.setMinRecord(0);
			request.setMaxRecord(10);
			holisticViewResponse.setActivityLogs(iActivityLogDBService.getAllActivityLogs(request));
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		}
		return holisticViewResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IHolisticViewService#getHolisticViewPaymentInDetails(com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest)
	 */
	@Override
	public HolisticViewResponse getHolisticViewPaymentInDetails(HolisticViewRequest holisticViewRequest) throws CompliancePortalException {

		HolisticViewResponse holisticViewResponse = null;
		try {
			holisticViewResponse = iHolisticViewDBService.getHolisticPaymentInDetails(holisticViewRequest,holisticViewResponse);
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		}
		return holisticViewResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IHolisticViewService#getHolisticViewPaymentOutDetails(com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest)
	 */
	@Override
	public HolisticViewResponse getHolisticViewPaymentOutDetails(HolisticViewRequest holisticViewRequest) throws CompliancePortalException {

		HolisticViewResponse holisticViewResponse = null;
		try {
			holisticViewResponse = iHolisticViewDBService.getHolisticPaymentOutDetails(holisticViewRequest,holisticViewResponse);
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		}
		return holisticViewResponse;
	}
	
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}
	
}