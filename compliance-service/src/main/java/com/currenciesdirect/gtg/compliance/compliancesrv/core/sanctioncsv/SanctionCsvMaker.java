package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;

import org.springframework.integration.support.MessageBuilder;

/**
 * The Class SanctionCsvMaker.
 */
public class SanctionCsvMaker {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SanctionCsvMaker.class);
	
	/**
	 * Creates the csv data.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<String> createCsvData(Message<SanctionCsvMessageContext> message)  throws ComplianceException{
		Map<String,Object> headers = message.getHeaders();
		Map<String,List<SanctionCsvRequest>> dataList = message.getPayload().getSanctionCsvFileData();
		String csvFileData = "csv";
		try {
			csvFileData = creatCsvFile(dataList);
			storeCsvFile(csvFileData);
		}catch(Exception e) {
			LOG.error("Error in SanctionCsvMaker createCsvData() : ",e);
		}
		
		return MessageBuilder.withPayload(csvFileData).copyHeaders(headers).build();
	}
	
	/**
	 * Creat csv file.
	 *
	 * @param dataList the data list
	 * @return the string
	 * @throws ComplianceException the compliance exception
	 */
	private String creatCsvFile(Map<String,List<SanctionCsvRequest>> dataMapList) {
		String csvFileData = null;
		StringBuilder fileCreation = new StringBuilder();
		try {
			fileCreation.append("\""+SanctionCsvConstants.SOURCE_CODE+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.CLIENT_ID+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.COMMENT_ID+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.STATUS_INDICATOR+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.FULL_NAME+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.COUNTRY+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.DOB+"\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\""+SanctionCsvConstants.RECORD_TYPE+"\"");
			fileCreation.append(SanctionCsvConstants.NEW_LINE);
			
			for(String key : dataMapList.keySet()) {
				appendListData(key,dataMapList,fileCreation);
			}

			csvFileData = fileCreation.toString();
		}catch(Exception e) {
			LOG.error("Error in SanctionCsvMaker creatCsvFile() : ",e);
		}
		return csvFileData;
	}
	
	/**
	 * Append list data.
	 *
	 * @param clientType the client type
	 * @param dataList the data list
	 * @param fileCreation the file creation
	 * @return the string builder
	 */
	private StringBuilder appendListData(String clientType, Map<String,List<SanctionCsvRequest>> dataMapList, StringBuilder fileCreation) {
		List<SanctionCsvRequest> list = dataMapList.get(clientType);
		
		for(SanctionCsvRequest data : list) {
			fileCreation.append("\"").append(data.getSourceCode()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\"").append(data.getClientId()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\"").append(data.getCommentID()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\"").append(data.getStatusIndicator()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			if(null != data.getFullName())
				fileCreation.append("\"").append(data.getFullName()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\"").append(data.getCountry()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			if(null != data.getDob())
				fileCreation.append("\"").append(data.getDob()).append("\"");
			fileCreation.append(SanctionCsvConstants.COMMA);
			fileCreation.append("\"").append(data.getRecordeType()).append("\"");
			fileCreation.append(SanctionCsvConstants.NEW_LINE);
		}
		return fileCreation;
	}
	
	/**
	 * Store csv file.
	 *
	 * @param csvFileData the csv file data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("squid:S2095")
	private void storeCsvFile(String csvFileData) throws IOException {
		
		String localDirectory = System.getProperty("sanction.csv.local.directory");
		String fileName = "Full_inactiveclnts_" + new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + ".csv";
		String trimLocation = localDirectory.trim();
		String localFileLocation = trimLocation+fileName;
		LOG.info(localFileLocation);
		
		File file = new File(localFileLocation);
		LOG.info("--------------------------------Local File created-------------------------");
		FileOutputStream fileOutStream = new FileOutputStream(file);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutStream);
		try {
			
			LOG.info("--------------------------------Saving file locally Started-------------------------");
			byte[] dataHolder = csvFileData.getBytes(StandardCharsets.UTF_8);
			bufferedOutputStream.write(dataHolder);
			LOG.info("--------------------------------Saving file locally Ended-------------------------");
		}catch(IOException e) {
			LOG.error("Error in SanctionCsvMaker storeCsvFile() : ",e);
		}
		finally {
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			fileOutStream.close();
		}
	}
	
}