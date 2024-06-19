package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

/**
 * The Class BaseMessageValidator.
 */
public abstract class BaseMessageValidator {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageValidator.class);
	
	/** The Constant ORG_CODE. */
	protected static final String ORG_CODE = "org_code";
	
	/** The Constant CUST_TYPE. */
	protected static final String CUST_TYPE="cust_type";

	/** The Constant REQUEST_IS_NOT_VALID. */
	protected static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	/**
	 * Creates the base response.
	 *
	 * @param response the response
	 * @param fv the fv
	 * @param resonCode the reson code
	 * @param reasonDesc the reason desc
	 * @param eventType the event type
	 */
	public void createBaseResponse(BaseResponse response, FieldValidator fv, String resonCode, String reasonDesc,
			String eventType) {

		if (fv != null && !fv.isFailed()) {
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setResponseCode(resonCode);
			response.setResponseDescription(reasonDesc);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(eventType.concat(" ").concat(fv.getErrors().toString()));
			LOGGER.debug("{} : {}",response.getErrorCode(),response.getErrorDescription());
		} else {
			response.setDecision(BaseResponse.DECISION.SUCCESS);
		}
	}
}
