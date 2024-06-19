package com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class IntuitionHistoricPaymentCSVSftpUpload {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(IntuitionHistoricPaymentCSVSftpUpload.class);

	/** The Constant INTUITION_HISTORIC_PAYMENT_SFTP_SERVER. */
	private static final String INTUITION_HISTORIC_PAYMENT_SFTP_SERVER = System
			.getProperty("intuition.historic.payment.sftp.server");

	/** The Constant INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_USER. */
	private static final String INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_USER = System
			.getProperty("intuition.historic.payment.sftp.user");
	
	/** The Constant INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_PORT. */
	private static final Integer INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_PORT = Integer.parseInt(System
			.getProperty("intuition.historic.payment.sftp.port"));

	/** The Constant INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_PASSWORD. */
	private static final String INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_PASSWORD = System
			.getProperty("intuition.historic.payment.sftp.password");

	/** The Constant INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_DIRECTORY. */
	private static final String INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_DIRECTORY = System
			.getProperty("intuition.historic.payment.sftp.directory");
	
	private int retryCount = 3;

	private int retryCounter = 1;
	
	/** The email alert. */
	@Autowired
	private IntuitionHistoricPaymentEmailAlert emailAlert;

	/**
	 * Upload csv fileto intuition sftp server.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> uploadCsvFiletoIntuitionSftpServer(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		IntuitionHistoricPaymentsRequest intuitionPaymentRequest = (IntuitionHistoricPaymentsRequest) messageExchange
				.getRequest();

		String paymentInLocalFileLocation = (String) intuitionPaymentRequest
				.getAdditionalAttribute("IntuitionHistoricPaymentInLocalFile");
		String paymentInLocalFileName = (String) intuitionPaymentRequest
				.getAdditionalAttribute("IntuitionHistoricPaymentInLocalFileName");
		
		String paymentOutLocalFileLocation = (String) intuitionPaymentRequest
				.getAdditionalAttribute("IntuitionHistoricPaymentOutLocalFile");
		String paymentOutLocalFileName = (String) intuitionPaymentRequest
				.getAdditionalAttribute("IntuitionHistoricPaymentOutLocalFileName");

		ChannelSftp channelSftp = new ChannelSftp();
		try {
			channelSftp = getSftpSession();
			channelSftp.connect();
			if ((boolean) intuitionPaymentRequest.getAdditionalAttribute("IntuitionHistoricPaymentInLocalSave")) {
				String paymentInRemoteDirPath = INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_DIRECTORY
						+ paymentInLocalFileName;
				LOG.warn("Remote Directory : {} ", paymentInRemoteDirPath);
				channelSftp.put(paymentInLocalFileLocation, paymentInRemoteDirPath);
			}

			if ((boolean) intuitionPaymentRequest.getAdditionalAttribute("IntuitionHistoricPaymentOutLocalSave")) {
				String paymentOutRemoteDirPath = INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_DIRECTORY
						+ paymentOutLocalFileName;
				LOG.warn("Remote Directory : {} ", paymentOutRemoteDirPath);
				channelSftp.put(paymentOutLocalFileLocation, paymentOutRemoteDirPath);
			}
			
			LOG.info(
					"-------------------------- Intuition Historic Payment File Successfully uploaded to SFTP Server -------------");
		} catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentCSVSftpUpload in uploadCsvFiletoIntuitionSftpServer method ",
					e);
			
			if(retryCounter < retryCount) {
				retryCounter++; 
				uploadCsvFiletoIntuitionSftpServer(message);
				retryCounter++;
			}
			
			if (retryCounter > retryCount) {
				emailAlert.sendEmailAlertForHistoricPaymentError(
						"Intuition Historic Payment upload error - batch file sftp upload",
						"Intuition (Transaction monitoring) Historical Payment upload failed due to batch file sftp upload error on "
								+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())
								+ ". Please alert compliance officer to upload manually on Intuition server.");
			}

			message.getPayload().setFailed(true);
		} finally {
			channelSftp.exit();
		}

		return message;
	}

	/**
	 * Gets the sftp session.
	 *
	 * @return the sftp session
	 * @throws JSchException the j sch exception
	 */
	private ChannelSftp getSftpSession() throws JSchException {
		JSch jsch = new JSch();
		String remoteHost = INTUITION_HISTORIC_PAYMENT_SFTP_SERVER;
		Session jschSession = jsch.getSession(INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_USER, remoteHost, INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_PORT);
		jschSession.setPassword(INTUITION_HISTORIC_PAYMENT_SFTP_SERVER_PASSWORD);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		jschSession.setConfig(config);
		jschSession.connect();
		return (ChannelSftp) jschSession.openChannel("sftp");
	}
}
