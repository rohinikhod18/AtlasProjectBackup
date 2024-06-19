package com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;

public class IntuitionHistoricPaymentEmailAlert {
	
	/** The i comm hub service impl. */
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;
	
	/**
	 * Send email alert for historic payment error.
	 *
	 * @param subject the subject
	 * @param content the content
	 */
	public void sendEmailAlertForHistoricPaymentError(String subject, String content) {
		SendEmailRequestForTM sendTMEmailRequest = new SendEmailRequestForTM();
		EmailHeaderForTM header = new EmailHeaderForTM();
		EmailPayloadForTM payload = new EmailPayloadForTM();

		header.setSourceSystem("Atlas");
		header.setOrgCode("Currencies Direct");
		header.setLegalEntity("CDLGB");

		String email = System.getProperty("intuition.sendTo");
		List<String> list = Arrays.asList(email.split(","));

		payload.setEmailId(list);
		payload.setSubject(subject);
		payload.setEmailContent(content);
		payload.setFromEmilId(System.getProperty("intuition.sendFrom"));
		payload.setReplyToEmailId(System.getProperty("intuition.sendFrom"));

		sendTMEmailRequest.setTmHeader(header);
		sendTMEmailRequest.setTmPayload(payload);

		iCommHubServiceImpl.sendEmailForTMAlert(sendTMEmailRequest, true);
	}

}
