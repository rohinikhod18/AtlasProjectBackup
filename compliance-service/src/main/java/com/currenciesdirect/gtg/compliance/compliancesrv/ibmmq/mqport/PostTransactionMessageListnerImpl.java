package com.currenciesdirect.gtg.compliance.compliancesrv.ibmmq.mqport;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.currenciesdirect.gtg.compliance.compliancesrv.ibmmq.service.IPostTransactionMessageListnerService;

public class PostTransactionMessageListnerImpl implements MessageListener {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PostTransactionMessageListnerImpl.class);

	/** The post transaction message listner service impl. */
	@Autowired
	private IPostTransactionMessageListnerService postTransactionMessageListnerServiceImpl;

	/**
	 * On message.
	 *
	 * @param message the message
	 */
	//AT-4948
	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				String textMsg = ((TextMessage) message).getText();
				postTransactionMessageListnerServiceImpl.prepareSendPostTransactionRequest(textMsg);
			}
		} catch (JMSException exception) {
			LOG.error("JMSException in onMessage() of PostTransactionMessageListnerImpl: ", exception);
		} catch (Exception exception) {
			LOG.error("Exception in onMessage() of PostTransactionMessageListnerImpl: ", exception);
		}

	}

}
