/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.IDBService;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringErrors;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Class TransactionMonitoringDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
public class TransactionMonitoringDBServiceImpl extends AbstractDao implements IDBService {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TransactionMonitoringDBServiceImpl.class);
	
	/** The idb service. */
	private static IDBService idbService = new TransactionMonitoringDBServiceImpl();

	/**
	 * Instantiates a new transaction monitoring DB service impl.
	 */
	private TransactionMonitoringDBServiceImpl() {

	}

	/**
	 * Gets the single instance of TransactionMonitoringDBServiceImpl.
	 *
	 * @return single instance of TransactionMonitoringDBServiceImpl
	 */
	public static IDBService getInstance() {
		return idbService;
	}

	/**
	 * Gets the transaction monitoring provider init config property.
	 *
	 * @return the transaction monitoring provider init config property
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public ConcurrentMap<String, TransactionMonitoringProviderProperty> getTransactionMonitoringProviderInitConfigProperty()
			throws TransactionMonitoringException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		TransactionMonitoringProviderProperty property = null;
		String providerName = null;
		ConcurrentHashMap<String, TransactionMonitoringProviderProperty> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(
					TransactionMonitoringQueryConstants.GET_TRANSACTION_MONITORING_PROVIDER_INIT_CONFIG_PROPERTY);

			resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, TransactionMonitoringProviderProperty.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA,
					e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return hm;
	}
	
	/**
	 * Save into transaction monitoring MQ.
	 *
	 * @param accountID the account ID
	 * @param id the id
	 * @param jsonAccountSignupRequest the json account signup request
	 * @param requestType the request type
	 * @param orgCode the org code
	 * @param createdBy the created by
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public void saveIntoTransactionMonitoringMQ(Integer accountID, Integer id, 
			String jsonAccountSignupRequest, String requestType, String orgCode, Integer createdBy, Integer contactId) throws TransactionMonitoringException {
		PreparedStatement broadcastEventStmt = null;
		Connection connection = null;
		
		try {
			
			connection = getConnection();
			broadcastEventStmt = connection.prepareStatement(TransactionMonitoringQueryConstants.SAVE_INTO_TRANSACTION_MONITORING_MQ);
			
			broadcastEventStmt.setString(1, orgCode);
			broadcastEventStmt.setString(2, requestType);
			if (accountID != null) {
				broadcastEventStmt.setInt(3, accountID);
			} else {
				broadcastEventStmt.setNull(3, Types.INTEGER);
			}

			if (contactId != null) {
				broadcastEventStmt.setInt(4, contactId);
			} else {
				broadcastEventStmt.setNull(4, Types.INTEGER);
			}
			if (id != null && (requestType.equalsIgnoreCase("payment_in") || requestType.equalsIgnoreCase("payment_in_update"))) {
				broadcastEventStmt.setInt(5, id);
			} else {
				broadcastEventStmt.setNull(5, Types.INTEGER);
			}
			if (id != null && (requestType.equalsIgnoreCase("payment_out") || requestType.equalsIgnoreCase("payment_out_update"))) {
				broadcastEventStmt.setInt(6, id);
			} else {
				broadcastEventStmt.setNull(6, Types.INTEGER);
			}
			broadcastEventStmt.setString(7, jsonAccountSignupRequest);
			broadcastEventStmt.setInt(8, 1);
			broadcastEventStmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			broadcastEventStmt.setInt(10, createdBy);
			broadcastEventStmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			broadcastEventStmt.setInt(12, 1);
			broadcastEventStmt.executeUpdate();
			
		} catch (Exception e) {
			
			LOG.error("Error in TransactionMonitoringDBServiceImpl class saveIntoTransactionMonitoringMQ method:", e);
		} finally {
			closePrepareStatement(broadcastEventStmt);
			closeConnection(connection);
		}
		
	}

	/**
	 * Save into post card transaction monitorig MQ.
	 *
	 * @param trxID the trx ID
	 * @param jsonCardRequest the json card request
	 * @throws TransactionMonitoringException 
	 */
	@Override
	public void saveIntoPostCardTransactionMonitorigMQ(String trxID, String jsonCardRequest, String cardRequestType) throws TransactionMonitoringException {
		PreparedStatement preparedStmt = null;
		Connection connection = null;
		
		try {
			connection = getConnection();
			preparedStmt = connection.prepareStatement(TransactionMonitoringQueryConstants.SAVE_INTO_POST_CARD_TRANSACTION_MONITORING_MQ);
			
			preparedStmt.setString(1, trxID);
			preparedStmt.setString(2, cardRequestType);
			preparedStmt.setString(3, jsonCardRequest);
			preparedStmt.setInt(4, 1);
			preparedStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStmt.setInt(6, 1);
			preparedStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStmt.setInt(8, 1);
			
			preparedStmt.executeUpdate();
						
		} catch(Exception e){
			LOG.error("Error in TransactionMonitoringDBServiceImpl class saveIntoPostCardTransactionMonitorigMQ method:", e);
		} finally {
			closePrepareStatement(preparedStmt);
			closeConnection(connection);
		}
		
	}

	/**
	 * Save into post card transaction monitoring failed request.
	 *
	 * @param trxID the trx ID
	 * @param jsonCardRequest the json card request
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public void saveIntoPostCardTransactionMonitoringFailedRequest(String trxID, String jsonCardRequest)
			throws TransactionMonitoringException {
		PreparedStatement preparedStmt = null;
		Connection connection = null;
		
		try {
			connection = getConnection();
			preparedStmt = connection.prepareStatement(TransactionMonitoringQueryConstants.SAVE_INTO_POST_CARD_TRANSACTION_MONITORING_FAILED_REQUEST);
			
			preparedStmt.setString(1, trxID);
			preparedStmt.setString(2, jsonCardRequest);
			preparedStmt.setInt(3, 1);
			preparedStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			
			preparedStmt.executeUpdate();
						
		} catch(Exception e){
			LOG.error("Error in TransactionMonitoringDBServiceImpl class saveIntoPostCardTransactionMonitoringFailedRequest method:", e);
		} finally {
			closePrepareStatement(preparedStmt);
			closeConnection(connection);
		}
		
	}

}
