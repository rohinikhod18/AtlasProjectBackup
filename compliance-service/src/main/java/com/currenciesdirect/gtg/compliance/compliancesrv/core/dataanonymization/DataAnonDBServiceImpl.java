package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

@Component("dataAnonDBServiceImpl")
public class DataAnonDBServiceImpl extends AbstractDao implements IDataAnonDBService{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DataAnonDBServiceImpl.class);
	
	/**
	 * Gets the account contact details.
	 *
	 * @param request the request
	 * @return the account contact details
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	public DataAnonRequestFromES getAccountContactDetails(DataAnonRequestFromES request) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			String query = DataAnonQueryConstant.GET_ACCOUT_CONTACTS_ID.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1, request.getAccount().getCrmId());
			rs = statement.executeQuery();
			int count = 1;
			Account oldAccount = new Account();
			String oldAccountAttribute = "";
			String oldContactAttribute = "";
			List<Contact> oldContacts = new ArrayList<>();
			while(rs.next()) {
				if(count == 1) {
					oldAccount.setId(rs.getInt("AccountID"));
					oldAccount.setTradeAccountID(rs.getInt("TradeAccountID"));
					oldAccount.setTradeAccountNumber(rs.getString("TradeAccountNumber"));
					oldAccount.setCustType(rs.getString("type"));
					oldAccount.setVersion(rs.getInt("AccountVersion"));
					oldAccountAttribute = rs.getString("AccountAttributes");
					oldContactAttribute = rs.getString("ContactAttributes");
				}
				Contact contact = new Contact();
				contact.setId(rs.getInt("ContactID"));
				contact.setTradeContactID(rs.getInt("TradeContactID"));
				contact.setVersion(rs.getInt("ContactVersion"));
				oldContacts.add(contact);
				count++;
			}
			oldAccount.setContacts(oldContacts);
			request.addAttribute(Constants.FIELD_OLD_ACCOUNT, oldAccount);
			request.addAttribute(Constants.FIELD_OLD_ACCOUNT_ATTRIBUTE, oldAccountAttribute);
			request.addAttribute(Constants.FIELD_OLD_CONTACTS_ATTRIBUTE, oldContactAttribute);
		} catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return request;
	}
	
	/**
	 * Check data anon request is in process queue.
	 *
	 * @param request the request
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	public boolean checkDataAnonRequestIsInProcessQueue(DataAnonRequestFromES request) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean isExisting = false;
		try {
			connection = getConnection(Boolean.TRUE);
			String query = DataAnonQueryConstant.GET_EXISTING_PROCESS_FROM_PROCESSQUEUE.getQuery();
			statement = connection.prepareStatement(query);
			statement.setInt(1, request.getNewReferenceId().intValue());
			rs = statement.executeQuery();
			if(rs.next()) {
				request.addAttribute(DataAnonConstants.EXISTING_ID_STATUS, rs.getString("status"));
				rs.close();
				isExisting = true;
			}
		}catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return isExisting;
	}
	
	/**
	 * Save data anon request.
	 *
	 * @param request the request
	 * @throws ComplianceException 
	 */
	@SuppressWarnings("squid:S2095")
	public void saveDataAnonRequest(DataAnonRequestFromES request,UserProfile userProfile) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Account oldAccount = (Account) request.getAdditionalAttribute(Constants.FIELD_OLD_ACCOUNT);
		String anonJson = JsonConverterUtil.convertToJsonWithNull(request);
		try {
			connection = getConnection(Boolean.TRUE);
			String query = DataAnonQueryConstant.INSERT_INTO_DATA_ANON_PROCESSQUEUE.getQuery();
			statement = connection.prepareStatement(query);
			statement.setFloat(1,request.getNewReferenceId());
			statement.setString(2,request.getAccount().getCustomerNumber());
			statement.setInt(3, oldAccount.getId());
			statement.setInt(4, 0);
			for(Contact oldContact : oldAccount.getContacts()) {
				if(oldContact.getTradeContactID().equals(request.getAccount().getContact().get(0).getTradeId().intValue())) {
					statement.setInt(4,oldContact.getId());
					break;
				}
			}
			statement.setString(5,anonJson);
			statement.setString(6,DataAnonConstants.RECEIVED);
			statement.setInt(7,1);
			statement.setInt(8,0);
			statement.setInt(9,userProfile.getUserID());
			statement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			statement.setInt(11,userProfile.getUserID());
			statement.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
			statement.execute();
			
		} catch(Exception e) {
		throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
	}

	/**
	 * Process data anonymization.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	public boolean processDataAnonymization(DataAnonRequestFromES request,Account oldAccount) {
		Connection connection = null;
		boolean isAnonymizationComplete = false;
		boolean anonymizationStatus = false;
		Integer accountId = oldAccount.getId();
		try {
			connection = getConnection(Boolean.TRUE);
			beginTransaction(connection);
			isAnonymizationComplete = updateAccount(connection,request,oldAccount);
			isAnonymizationComplete = updateAccountAttribute(connection,request,oldAccount);
			isAnonymizationComplete = updateContact(connection,request,oldAccount);
			isAnonymizationComplete = updateContactAttribute(connection,request,accountId);
			if(oldAccount.getVersion() > 1) {
				isAnonymizationComplete = updateAccountAttributeHistory(connection,request,oldAccount);
				isAnonymizationComplete = updateContactAttributeHistory(connection,request,accountId);
			}				
			isAnonymizationComplete = updateActivityLog(connection,accountId);
			isAnonymizationComplete = updateEventServiceLog(connection,request,accountId);
			if(isAnonymizationComplete) {
				updateDataAnonProcessStatus(connection,request);
				commitTransaction(connection);
				anonymizationStatus = true;
			}
			else {
				transactionRolledBack(connection);
				deleteDataAnonProcess(connection,request);
			}
		} catch (Exception e) {
			try {
				transactionRolledBack(connection);
			} catch (ComplianceException e1) {
				LOG.error("Error in DataAnonDBServiceImpl for transaction rollback : {1}", e1);
			}
			LOG.error("Error in DataAnonDBServiceImpl for processDataAnonymization : {1}", e);
		} finally {
			try {
				closeConnection(connection);
			} catch (ComplianceException e) {
				LOG.error("Error in DataAnonDBServiceImpl for closing connection : {1}", e);
			}
		}
		return anonymizationStatus;
	}
	
	/**
	 * Update account.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param rs the rs
	 * @param request the request
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateAccount(Connection connection,DataAnonRequestFromES request,Account oldAccount) throws ComplianceException {
		int rs;
		boolean status = false;
		PreparedStatement statement = null;
		try {
			String query = DataAnonQueryConstant.UPDATE_ACCOUNT.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1,request.getAccount().getAnonymizationDetails().getName());
			statement.setString(2, request.getAccount().getAnonymizationDetails().getCustomerNumber());
			statement.setInt(3, oldAccount.getId());
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateAccount : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update account attribute.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param request the request
	 * @param oldAccount the old account
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateAccountAttribute(Connection connection,DataAnonRequestFromES request,Account oldAccount) throws ComplianceException {
		int rs;
		String query;
		boolean status = false;
		PreparedStatement statement = null;
		try {
			if(Constants.PFX.equals(oldAccount.getCustType())) {
				query = DataAnonQueryConstant.UPDATE_ACCOUNT_ATTRIBUTE_PFX.getQuery();
				statement = connection.prepareStatement(query);
				statement.setString(1,request.getAccount().getAnonymizationDetails().getName());
				statement.setString(2, request.getAccount().getAnonymizationDetails().getCustomerNumber());
				statement.setInt(3, oldAccount.getId());
			}
			else {
				query = DataAnonQueryConstant.UPDATE_ACCOUNT_ATTRIBUTE_CFX.getQuery();
				statement = connection.prepareStatement(query);
				statement.setFloat(1, request.getAccount().getAnonymizationDetails().getNumber());
				statement.setString(2,request.getAccount().getAnonymizationDetails().getAddress().getAddressLine());
				statement.setString(3,request.getAccount().getAnonymizationDetails().getName());
				statement.setString(4, request.getAccount().getAnonymizationDetails().getCustomerNumber());
				statement.setInt(5, oldAccount.getId());
			}
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateAccountAttribute : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update account attribute history.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param request the request
	 * @param oldAccount the old account
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateAccountAttributeHistory(Connection connection,DataAnonRequestFromES request,Account oldAccount) throws ComplianceException {
		int rs;
		String query;
		boolean status = false;
		PreparedStatement statement = null;
		try {
			if(Constants.PFX.equals(oldAccount.getCustType())) {
				query = DataAnonQueryConstant.UPDATE_ACCOUNT_ATTRIBUTE_HISTORY_PFX.getQuery();
				statement = connection.prepareStatement(query);
				statement.setString(1,request.getAccount().getAnonymizationDetails().getName());
				statement.setString(2, request.getAccount().getAnonymizationDetails().getCustomerNumber());
				statement.setInt(3, oldAccount.getId());
			}
			else {
				query = DataAnonQueryConstant.UPDATE_ACCOUNT_ATTRIBUTE_HISTORY_CFX.getQuery();
				statement = connection.prepareStatement(query);
				statement.setFloat(1, request.getAccount().getAnonymizationDetails().getNumber());
				statement.setString(2,request.getAccount().getAnonymizationDetails().getAddress().getAddressLine());
				statement.setString(3,request.getAccount().getAnonymizationDetails().getName());
				statement.setString(4, request.getAccount().getAnonymizationDetails().getCustomerNumber());
				statement.setInt(5, oldAccount.getId());
			}
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		} catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateAccountAttributeHistory : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update contact.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param rs the rs
	 * @param request the request
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateContact(Connection connection,DataAnonRequestFromES request,Account oldAccount) throws ComplianceException {
		int rs;
		String query;
		boolean status = false;
		PreparedStatement statement = null;
		try {
			query = DataAnonQueryConstant.UPDATE_CONTACT.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1,request.getAccount().getContact().get(0).getAnonymizationDetails().getName());
			statement.setInt(2, oldAccount.getId());
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateContact : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update contact attribute.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param request the request
	 * @param oldAccount the old account
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateContactAttribute(Connection connection,DataAnonRequestFromES request,Integer accountId) throws ComplianceException {
		int rs;
		String query;
		boolean status = false;
		PreparedStatement statement = null;
		String contactAttributes = (String) request.getAdditionalAttribute(DataAnonConstants.CONTACT_ATTRIBUTE_ANONDATA);
		try {
			query = DataAnonQueryConstant.UPDATE_CONTACT_ATTRIBUTE.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1, contactAttributes);
			statement.setInt(2, accountId);
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateContactAttribute : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update contact attribute history.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param request the request
	 * @param oldAccount the old account
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateContactAttributeHistory(Connection connection,DataAnonRequestFromES request,Integer accountId) throws ComplianceException {
		int rs;
		String query;
		PreparedStatement statement = null;
		String contactAttributes = (String) request.getAdditionalAttribute(DataAnonConstants.CONTACT_ATTRIBUTE_ANONDATA);
		boolean status = false;
		try {
			query = DataAnonQueryConstant.UPDATE_CONTACT_ATTRIBUTE_HISTORY.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1, contactAttributes);
			statement.setInt(2, accountId);
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateContactAttribute : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update activity log.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param rs the rs
	 * @param request the request
	 * @return true, if successful
	 * @throws ComplianceException 
	 */
	private boolean updateActivityLog(Connection connection,Integer accountId) throws ComplianceException {
		int rs;
		String query;
		boolean status = false;
		PreparedStatement statement = null;
		try {
			query = DataAnonQueryConstant.UPDATE_PROFILE_ACTIVITY_LOG_DETAIL.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1,"----");
			statement.setInt(2, accountId);
			rs = statement.executeUpdate();
			if(rs > 0)
				status = true;
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateActivityLog : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
		return status;
	}
	
	/**
	 * Update event service log.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param rs the rs
	 * @param request the request
	 * @return true, if successful
	 */
	@SuppressWarnings("squid:S2095")
	private boolean updateEventServiceLog(Connection connection,DataAnonRequestFromES request,Integer accountId) {
		int regRs;
		int pInRs;
		int pOutRs;
		boolean status = true;
		PreparedStatement regStatement = null;
		PreparedStatement payInStatement = null;
		PreparedStatement payOutStatement = null;
		
		String regProviderResponse = (String) request.getAdditionalAttribute("blacklistProviderResponse");
		String payInProviderResponse = (String) request.getAdditionalAttribute("blacklistPayInProviderResponse");
		String payOutProviderResponse = (String) request.getAdditionalAttribute("blacklistPayOutProviderResponse");
		try {
			String regQuery = DataAnonQueryConstant.UPDATE_EVENT_SERVICE_LOG_FOR_REG_BLACKLIST.getQuery();
			regStatement = connection.prepareStatement(regQuery);
			regStatement.setString(1, regProviderResponse);
			regStatement.setInt(2,accountId);
			regRs = regStatement.executeUpdate();
			
			String payInQuery = DataAnonQueryConstant.UPDATE_EVENT_SERVICE_LOG_FOR_PAYIN_BLACKLIST.getQuery();
			payInStatement = connection.prepareStatement(payInQuery);
			payInStatement.setString(1, payInProviderResponse);
			payInStatement.setInt(2,accountId);
			pInRs = payInStatement.executeUpdate();
			
			String payOutQuery = DataAnonQueryConstant.UPDATE_EVENT_SERVICE_LOG_FOR_PAYOUT_BLACKLIST.getQuery();
			payOutStatement = connection.prepareStatement(payOutQuery);
			payOutStatement.setString(1, payOutProviderResponse);
			payOutStatement.setInt(2,accountId);
			pOutRs = payOutStatement.executeUpdate();
			
			if(regRs < 0 || pInRs < 0 || pOutRs < 0)
				status = false;
			
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateEventServiceLog : {1}", e);
			status = false;
		}
		return status;
	}
	
	/**
	 * Update data anon process status.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param request the request
	 * @throws ComplianceException 
	 */
	private void updateDataAnonProcessStatus(Connection connection,DataAnonRequestFromES request) throws ComplianceException {
		String query;
		PreparedStatement statement = null;
		try {
			query = DataAnonQueryConstant.UPDATE_DATAANON_STATUS.getQuery();
			statement = connection.prepareStatement(query);
			statement.setString(1,DataAnonConstants.RECEIVED_AND_PROCESSED);
			statement.setInt(2,request.getNewReferenceId().intValue());
			statement.executeUpdate();
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for updateDataAnonProcessStatus : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
	}
	
	/**
	 * Delete data anon process.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param request the request
	 * @throws ComplianceException 
	 */
	private void deleteDataAnonProcess(Connection connection,DataAnonRequestFromES request) throws ComplianceException {
		String query;
		PreparedStatement statement = null;
		try {
			query = DataAnonQueryConstant.DELETE_DATA_ANON_PROCESS.getQuery();
			statement = connection.prepareStatement(query);
			statement.setInt(1,request.getNewReferenceId().intValue());
			statement.execute();
		}catch(Exception e) {
			LOG.error("Error in DataAnonDBServiceImpl for deleteDataAnonProcess : {1}", e);
		}finally {
			closePrepareStatement(statement);
		}
	}
}