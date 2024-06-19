package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IHolisticViewService.
 */
public interface IHolisticViewService {

	/**
	 * Gets the holistic view details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @return the holistic view details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticViewDetails(HolisticViewRequest holisticViewRequest) throws CompliancePortalException;

	/**
	 * Gets the holistic view payment in details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @return the holistic view payment in details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticViewPaymentInDetails(HolisticViewRequest holisticViewRequest)
			throws CompliancePortalException;

	/**
	 * Gets the holistic view payment out details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @return the holistic view payment out details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticViewPaymentOutDetails(HolisticViewRequest holisticViewRequest)
			throws CompliancePortalException;
	
}