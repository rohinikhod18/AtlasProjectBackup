package com.currenciesdirect.gtg.compliance.compliancesrv.transformer;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.EuPoiCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.FirstCreditCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;

public abstract class AbstractCustomCheckTransformer extends AbstractTransformer{

	/**
	 * Creates the default response based on whether other checks are to be performed or not
	 *
	 * @param doPerformOtherChecks the do perform other checks
	 * @return the custom check response
	 */
	protected CustomCheckResponse createDefaultResponse(ServiceStatus status) {
		CustomCheckResponse serviceResponse = new CustomCheckResponse();

		serviceResponse.setOverallStatus(status.name());
		VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
		WhiteListCheckResponse whiteListCheckResponse = new WhiteListCheckResponse();
		FirstCreditCheckResponse firstCreditCheckResponse = new FirstCreditCheckResponse(); //AT-3346
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();//AT-3349
		
		velocityCheckResponse.setStatus(status.name());
		velocityCheckResponse.setBeneCheck(status.name());
		velocityCheckResponse.setNoOffundsoutTxn(status.name());
		velocityCheckResponse.setPermittedAmoutcheck(status.name());

		whiteListCheckResponse.setStatus(status.name());
		whiteListCheckResponse.setAmoutRange(status.name());
		whiteListCheckResponse.setCurrency(status.name());
		whiteListCheckResponse.setReasonOfTransfer(status.name());
		whiteListCheckResponse.setThirdParty(status.name());

		firstCreditCheckResponse.setStatus(status.name()); //AT-3346
		euPoiCheckResponse.setStatus(status.name());//AT-3349
		
		serviceResponse.setVelocityCheck(velocityCheckResponse);
		serviceResponse.setWhiteListCheck(whiteListCheckResponse);
		serviceResponse.setFirstCreditCheck(firstCreditCheckResponse); //AT-3346
		serviceResponse.setEuPoiCheck(euPoiCheckResponse);//AT-3349
		serviceResponse.setOverallStatus(status.name());
		return serviceResponse;
	}
}
