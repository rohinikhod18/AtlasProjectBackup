package com.currenciesdirect.gtg.compliance.compliancesrv.ibmmq.service;

public interface IPostTransactionMessageListnerService {

	/**
	 * Prepare send post transaction request.
	 *
	 * @param textMsg the text msg
	 * @param message the message
	 */
	public void prepareSendPostTransactionRequest(String textMsg);
}
