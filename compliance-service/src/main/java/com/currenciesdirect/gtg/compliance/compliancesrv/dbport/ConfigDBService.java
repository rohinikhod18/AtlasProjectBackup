package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class ConfigDBService extends AbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigDBService.class);

	/**
	 * Gets the providerinit config properties.
	 *
	 * @return the providerinit config properties
	 * @throws ComplianceException the compliance exception
	 */
	public ConcurrentMap<String,ProviderProperty> getProviderinitConfigProperties() throws ComplianceException {

		ResultSet resultSet = null;
		ConcurrentHashMap<String, ProviderProperty> mp = new ConcurrentHashMap<>();
		Connection conn = null;
		try{
			conn =  getConnection(Boolean.TRUE);
			getProviderInfo(mp, conn);
		} catch (Exception e) {
			LOG.error("Cannot get connection object",e);
		}finally{
			closeResultset(resultSet);
			closeConnection(conn);
		}
		return mp;
	}

	/**
	 * Gets the provider info.
	 *
	 * @param mp the mp
	 * @param conn the conn
	 * @return the provider info
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	private ResultSet getProviderInfo(ConcurrentHashMap<String, ProviderProperty> mp,
			Connection conn) throws ComplianceException {
		String json;
		String providerName;
		ResultSet resultSet = null;
		try (PreparedStatement stmt = conn.prepareStatement(DBQueryConstant.GET_PROVIDER_INFO.getQuery())) {
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				
				mp.put(providerName, JsonConverterUtil.convertToObject(ProviderProperty.class, json));
			}
			closeResultset(resultSet);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
		return resultSet;
	}
	
	/**
	 * Gets the full name from ISO country codes.
	 *
	 * @return the full name from ISO country codes
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	public ConcurrentMap<String, String> getFullNameFromISOCountryCodes() throws ComplianceException {
		ResultSet resultSet = null;
		ConcurrentHashMap<String, String> mp = new ConcurrentHashMap<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(Boolean.TRUE);
			stmt = conn.prepareStatement(DBQueryConstant.GET_ISO_COUNTRY_CODE_AND_NAMES.getQuery());
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				mp.put(resultSet.getString("code"), resultSet.getString("displayname"));
			}
		} catch (Exception e) {
			LOG.error("Error while getting countrycodes fro db", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;
	}
	
	/**
	 * added under AT-2248-unrelated to any jira
	 * Gets the user id and name.
	 *
	 * @return the user id and name
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	public ConcurrentMap<String, Integer> getUserIdAndName() throws ComplianceException {
		ResultSet resultSet = null;
		ConcurrentHashMap<String, Integer> mp = new ConcurrentHashMap<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(Boolean.TRUE);
			stmt = conn.prepareStatement(DBQueryConstant.GET_USER_TABLE_IDS_NAMES.getQuery());
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				mp.put(resultSet.getString("UserName"), resultSet.getInt("ID"));
			}
		} catch (Exception e) {
			LOG.error("Error while getting user ID and SSOuserid from db", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;
	}
	
	/**AT-3346
	 * Gets the edd number.
	 *
	 * @return the edd number
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	public ConcurrentMap<String, Integer> getEddNumber() throws ComplianceException {
		ResultSet resultSet = null;
		ConcurrentHashMap<String, Integer> mp = new ConcurrentHashMap<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(Boolean.TRUE);
			stmt = conn.prepareStatement(DBQueryConstant.GET_EDD_COUNTRIES.getQuery());
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				mp.put(resultSet.getString("Code"), resultSet.getInt("EUFirstPayInEDD"));
			}
		}catch(Exception e) {
			LOG.error("Error while getting country code and edd number from db", e);
		}finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;
	}
	
	/**
	 * Gets the country state code.
	 *
	 * @return the country state code
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	public ConcurrentMap<String, String> getCountryStateCode() throws ComplianceException {
		ResultSet resultSet = null;
		ConcurrentHashMap<String, String> mp = new ConcurrentHashMap<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(Boolean.TRUE);
			stmt = conn.prepareStatement(DBQueryConstant.GET_COUNTRY_STATE_DATA.getQuery());//AT-3719
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				mp.put(resultSet.getString("StateName"), resultSet.getString("Code"));
			}
		} catch (Exception e) {
			LOG.error("Error while getting state Name and code from CountryState table", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;
	}
	
	/**
	 * Gets the country short code.
	 *
	 * @return the country short code
	 * @throws ComplianceException the compliance exception
	 */
	//AT-4666
	@SuppressWarnings("squid:S2095")
	public ConcurrentMap<String, String> getCountryShortCode() throws ComplianceException {
		ResultSet resultSet = null;
		ConcurrentHashMap<String, String> mp = new ConcurrentHashMap<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(Boolean.TRUE);
			stmt = conn.prepareStatement(DBQueryConstant.GET_COUNTRY_SHORT_CODE.getQuery());
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				mp.put(resultSet.getString("code"), resultSet.getString("shortcode"));
			}
		} catch (Exception e) {
			LOG.error("Error while getting countrycodes fro db", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;
	}
}