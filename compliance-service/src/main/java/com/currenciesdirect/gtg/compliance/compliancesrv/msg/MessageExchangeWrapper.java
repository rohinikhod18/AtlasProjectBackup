package com.currenciesdirect.gtg.compliance.compliancesrv.msg;

import java.io.Serializable;

public class MessageExchangeWrapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MessageExchange messageExchange;

	public MessageExchangeWrapper() {
		// default.
	}

	public MessageExchangeWrapper(MessageExchange messageExchange) {
		this.messageExchange = messageExchange;
	}

	public MessageExchange getMessageExchange() {
		return messageExchange;
	}

	public void setMessageExchange(MessageExchange messageExchange) {
		this.messageExchange = messageExchange;
	}

	@Override
	public String toString() {
		return "MessageExchangeWrapper [messageExchange=" + messageExchange + "]";
	}
}