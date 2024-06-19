package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.handler.annotation.Payload;

/**
 * The Interface FraugsterCsvUploadGateway.
 */
public interface FraugsterCsvUploadGateway {

	 /**
 	 * Send fraugster.
 	 */
 	@Payload("new String()")
	public void sendFraugster();
}
