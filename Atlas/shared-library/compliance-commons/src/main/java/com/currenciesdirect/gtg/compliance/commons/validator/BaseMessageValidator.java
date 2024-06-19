package com.currenciesdirect.gtg.compliance.commons.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.InternalProcessingCode;

/**
 * The Class BaseMessageValidator.
 */
public abstract class BaseMessageValidator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageValidator.class);

	/**
	 * Creates the base response.
	 *
	 * @param response
	 *            the response
	 * @param fv
	 *            the fv
	 * @param resonCode
	 *            the reson code
	 * @param reasonDesc
	 *            the reason desc
	 * @param eventType
	 *            the event type
	 */
	public void createBaseResponse(BaseResponse response, FieldValidator fv, String resonCode, String reasonDesc,
			String eventType) {

		if (fv != null && !fv.isFailed()) {
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setResponseCode(resonCode);
			response.setResponseDescription(reasonDesc);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(eventType.concat(" ").concat(fv.getErrors().toString()));
			LOGGER.debug("{} : {}", response.getErrorCode(), response.getErrorDescription());
		} else {
			response.setDecision(BaseResponse.DECISION.SUCCESS);
		}
	}
}
