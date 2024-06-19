package com.currenciesdirect.gtg.compliance.customchecks.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.customchecks.core.ICacheDBService;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckFraudPredictRules;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckVelocityRules;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CacheDBServiceImpl.
 * 
 * @author abhijeetg
 */
@SuppressWarnings({"squid:S2095", "squid:S3077"})
public class CacheDBServiceImpl extends AbstractDao implements ICacheDBService {

	/** The db service impl. */
	private static volatile CacheDBServiceImpl dbServiceImpl = null;

	/**
	 * Instantiates a new cache DB service impl.
	 */
	private CacheDBServiceImpl() {

	}

	/**
	 * Gets the single instance of CacheDBServiceImpl.
	 *
	 * @return single instance of CacheDBServiceImpl
	 */
	public static CacheDBServiceImpl getInstance() {
		if (dbServiceImpl == null) {
			synchronized (CacheDBServiceImpl.class) {
				if (dbServiceImpl == null) {
					dbServiceImpl = new CacheDBServiceImpl();
				}
			}
		}
		return dbServiceImpl;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.ICacheDBService#getVelocityRulesCacheData()
	 */
	@Override
	public Map<String, CustomCheckVelocityRules> getVelocityRulesCacheData() throws CustomChecksException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String organizationCode = null;
		String custType = null;
		String eventType = null;
		String key = null;
		ConcurrentHashMap<String, CustomCheckVelocityRules> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(DBConstants.GET_VELOCITY_RULES.getValue());
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				CustomCheckVelocityRules customCheckVelocityRules = new CustomCheckVelocityRules();
				organizationCode = resultSet.getString("code");
				custType = resultSet.getString("CustType");
				eventType = resultSet.getString("EventType");
				customCheckVelocityRules.setCountThreshold(resultSet.getInt("CountThreshold"));
				customCheckVelocityRules.setAmountThreshold(resultSet.getDouble("amountThreshold"));
				key = organizationCode + "-" + custType + "-" + eventType;
				hm.put(key.toUpperCase(), customCheckVelocityRules);
			}
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_LOADING_CACHE_DATA, e);
		} finally {
				closeResultset(resultSet);
				closePrepareStatement(stmt);
				closeConnection(conn);
		}
		return hm;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.ICacheDBService#getAccountVelocityRules(java.lang.Integer)
	 */
	@Override
	public CustomCheckVelocityRules getAccountVelocityRules(Integer accId) throws CustomChecksException {		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;		
		CustomCheckVelocityRules customCheckVelocityRules = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(DBConstants.GET_ACCOUNT_VELOCITY_RULES.getValue());
			stmt.setInt(1, accId);
			resultSet = stmt.executeQuery();
			while(resultSet.next()){
				customCheckVelocityRules = new CustomCheckVelocityRules();
				customCheckVelocityRules.setAmountThreshold(resultSet.getDouble("amountThreshold"));
				customCheckVelocityRules.setCountThreshold(resultSet.getInt("CountThreshold"));
				return customCheckVelocityRules;
			}
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_LOADING_CACHE_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * Checks if is beneficiary account whitelisted.
	 *
	 * @param beneAccountToCheck the bene account to check
	 * @return true, if is beneficiary account whitelisted
	 * @throws CustomChecksException the custom checks exception
	 */
	public boolean isBeneficiaryAccountWhitelisted(String beneAccountToCheck) throws CustomChecksException{
		boolean result = false;
		PreparedStatement stmtBeneCheck = null;
		ResultSet rsBeneCheck = null;
		Connection conn = null;
		try {
			conn = getConnection();
			stmtBeneCheck = conn.prepareStatement(DBConstants.CHECK_BENEFICIARY_WHITELIST.getValue());
			stmtBeneCheck.setString(1, beneAccountToCheck);
			rsBeneCheck = stmtBeneCheck.executeQuery();
			if(rsBeneCheck.next()) {
				result = true;
			}

		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_CONNECTION, e);
		} finally {
			closeResultset(rsBeneCheck);
			closePrepareStatement(stmtBeneCheck);
			closeConnection(conn);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.ICacheDBService#getFraudPredictProviderInitConfigProperty()
	 */
	@Override
	public Map<String, CustomCheckFraudPredictRules> getFraudPredictProviderInitConfigProperty()
			throws CustomChecksException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		CustomCheckFraudPredictRules property = null;
		String providerName = null;
		ConcurrentHashMap<String, CustomCheckFraudPredictRules> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(DBConstants.GET_FRAUD_PREDICT_PROVIDER_INFO.getValue());

			resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, CustomCheckFraudPredictRules.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_LOADING_CACHE_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return hm;
	}

}
