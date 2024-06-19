/**
 * 
 */
package com.currenciesdirect.gtg.compliance.externalservice.enterpriseport;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentEmailRequest;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateRequest;

/**
 * The Interface EmailAPI.
 *
 * @author vishalj
 */
public interface IEmailClient {
	
	/**
	 * Send email on payment rejection.
	 *
	 * @param PaymentUpdateRequest the payment update request
	 */
	public Boolean sendEmail(PaymentUpdateRequest paymentUpdateRequest, PaymentEmailRequest paymentEmailRequest);
}
