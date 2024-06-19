package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.*;

@Component
public class SanctionCsvRetryHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(SanctionCsvRetryHandler.class);
	
	/** The Constant SANCTIONCSV_UPLOAD_IP. */
	private static final String SANCTIONCSV_UPLOAD_IP = System.getProperty("sanction.csv.server.ip");
	
	/** The Constant SANCTIONCSV_UPLOAD_USERNAME. */
	private static final String SANCTIONCSV_UPLOAD_USERNAME = System.getProperty("sanction.csv.server.username");
	
	/** The Constant SANCTIONCSV_UPLOAD_PASSWORD. */
	private static final String SANCTIONCSV_UPLOAD_PASSWORD = System.getProperty("sanction.csv.server.password");
	
	/** The Constant SANCTIONCSV_UPLOAD_DIRECTORY. */
	private static final String SANCTIONCSV_UPLOAD_DIRECTORY = System.getProperty("sanction.csv.server.directory");
	
	/** The Constant SANCTIONCSV_LOCAL_FILE. */
	private static final String SANCTIONCSV_LOCAL_DIRECTORY = System.getProperty("sanction.csv.local.directory");
	
	private int retryCount = 3;
	
	private int retryCounter = 1;
	
	/**
	 * Retry uploading csv.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void retryUploadingCsv() throws IOException {
		
		LOG.warn("-------------------------- Retrying uploading file {} time -------------",retryCounter);
		
		String fileName = "Full_inactiveclnts_" + new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + ".csv";
		String localLocation = SANCTIONCSV_LOCAL_DIRECTORY.trim();
		String localFilePath = localLocation+fileName;
		
		LOG.warn(fileName);
		
		ChannelSftp channelSftp = new ChannelSftp();
		try {
			channelSftp = getSftpSession();
			channelSftp.connect();
			String remoteDirPath = SANCTIONCSV_UPLOAD_DIRECTORY + fileName;
			LOG.warn("Remote Directory : {} ",remoteDirPath);
			channelSftp.put(localFilePath,remoteDirPath);
			
			LOG.warn("-------------------------- Retrying uploading file {} time Completed -------------",retryCounter);
		}catch(Exception e) {
			LOG.error("Error in SanctionCsvRetryHandler in retryUploadingCsv method ",e);
			if(retryCounter < retryCount) {
				retryCounter++;
				retryUploadingCsv();
			}
		}
		finally {
			channelSftp.exit();
			LOG.warn("-------------------------- Channel close -------------");
		}
	}
	
	/**
	 * Gets the sftp session.
	 *
	 * @return the sftp session
	 * @throws JSchException the j sch exception
	 */
	private ChannelSftp getSftpSession() throws JSchException {
		JSch jsch = new JSch();
		String remoteHost = SANCTIONCSV_UPLOAD_IP;
	    Session jschSession = jsch.getSession(SANCTIONCSV_UPLOAD_USERNAME, remoteHost, 22);
	    jschSession.setPassword(SANCTIONCSV_UPLOAD_PASSWORD);
	    java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");
	    jschSession.setConfig(config);
	    jschSession.connect();
	    return (ChannelSftp) jschSession.openChannel("sftp");
	}

}
