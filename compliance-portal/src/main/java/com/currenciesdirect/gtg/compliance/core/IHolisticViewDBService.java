package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IHolisticViewDBService.
 */
public interface IHolisticViewDBService {

	/**
	 * Gets the holistic account details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @return the holistic account details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticAccountDetails(HolisticViewRequest holisticViewRequest) throws CompliancePortalException;

	/**
	 * Gets the holistic payment details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @param holisticViewResponse the holistic view response
	 * @return the holistic payment details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticPaymentDetails(HolisticViewRequest holisticViewRequest,HolisticViewResponse holisticViewResponse) throws CompliancePortalException;

	/**
	 * Gets the holistic payment in details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @param holisticViewResponse the holistic view response
	 * @return the holistic payment in details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticPaymentInDetails(HolisticViewRequest holisticViewRequest,HolisticViewResponse holisticViewResponse) throws CompliancePortalException;
	
	/**
	 * Gets the holistic payment out details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @param holisticViewResponse the holistic view response
	 * @return the holistic payment out details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public HolisticViewResponse getHolisticPaymentOutDetails(HolisticViewRequest holisticViewRequest,HolisticViewResponse holisticViewResponse) throws CompliancePortalException;
	
	/**
	 * Adds the documents.
	 *
	 * @param holisticViewResponse the holistic view response
	 * @return the holistic view response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public void addDocuments(HolisticViewResponse holisticViewResponse) throws CompliancePortalException;
	
	
	/**
	 * Gets the further payment details list.
	 *
	 * @param holisticViewResponse the holistic view response
	 * @return the further payment details list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public void getFurtherPaymentDetailsList(HolisticViewResponse holisticViewResponse) throws CompliancePortalException;

	
	
}