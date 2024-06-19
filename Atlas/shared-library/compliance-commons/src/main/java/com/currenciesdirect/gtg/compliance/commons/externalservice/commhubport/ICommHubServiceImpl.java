package com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.response.SendEmailResponse;

public interface ICommHubServiceImpl {
	
	/**
	 * Send email.
	 *
	 * @param sendEmailReq the send email req
	 * @return the send email response
	 */
	public SendEmailResponse sendEmail(SendEmailRequest sendEmailReq,boolean createNewToken);
	
	public SendEmailResponse sendEmailForTMAlert(SendEmailRequestForTM sendTMEmailRequest,boolean createNewToken);

}
