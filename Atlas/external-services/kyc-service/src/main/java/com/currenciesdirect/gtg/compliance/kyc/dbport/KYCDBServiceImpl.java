/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.dbport;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.currenciesdirect.gtg.compliance.kyc.core.IKYCDBService;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class KYCDBServiceImpl.
 *
 * @author manish
 */
@SuppressWarnings("squid:S2095")
public class KYCDBServiceImpl extends AbstractDao implements IKYCDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(KYCDBServiceImpl.class);

	/** The k YC dao impl. */
	private static IKYCDBService kYCDaoImpl = null;

	private static final String COUNTRY = "Country";
	
	/**
	 * Instantiates a new KYCDB service impl.
	 */
	private KYCDBServiceImpl() {

	}

	/**
	 * Gets the single instance of KYCDBServiceImpl.
	 *
	 * @return single instance of KYCDBServiceImpl
	 */
	public static IKYCDBService getInstance() {
		if (kYCDaoImpl == null) {
			kYCDaoImpl = new KYCDBServiceImpl();
		}
		return kYCDaoImpl;
	}

	/**
	 * 
	 * 1) Load list of countries per provider *.
	 *
	 * @return the concurrent map
	 * @throws KYCException
	 *             the KYC exception
	 */
	@Override
	public ConcurrentMap<String, List<String>> fetchKYCInitData() throws KYCException {
		LOG.debug("Start KYCDaoImpl : fetchKYCInitData.");

		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;
		StringBuilder queryString = new StringBuilder();
		Connection conn = null;
		ConcurrentHashMap<String, List<String>> mp = new ConcurrentHashMap<>();
		List<String> lexisNExisLst = new CopyOnWriteArrayList<>();
		List<String> gbGroupLst = new CopyOnWriteArrayList<>();
		List<String> carbonLst = new CopyOnWriteArrayList<>();
		try {
			conn = getConnection();
			queryString.setLength(0);

			// build list of lexis nexis
			stmt = conn.prepareStatement(KYCQueryConstants.LOAD_KYC_COUNTRY_PROVIDER_MAPPING);
			stmt.setString(1, Constants.LEXISNEXIS_PROVIDER);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				lexisNExisLst.add(resultSet.getString(COUNTRY));
			}
			closePrepareStatement(stmt);
			closeResultset(resultSet);
			// build list of GBGROUP
			stmt1 = conn.prepareStatement(KYCQueryConstants.LOAD_KYC_COUNTRY_PROVIDER_MAPPING);
			stmt1.setString(1, Constants.GBGROUP_PROVIDER);
			resultSet1 = stmt1.executeQuery();
			while (resultSet1.next()) {

				gbGroupLst.add(resultSet1.getString(COUNTRY));
			}
			closePrepareStatement(stmt1);
			closeResultset(resultSet1);
			
			// build list of carbon
			stmt2 = conn.prepareStatement(KYCQueryConstants.LOAD_KYC_COUNTRY_PROVIDER_MAPPING);
			stmt2.setString(1, Constants.CARBONSERVICE_PROVIDER);
			resultSet2 = stmt2.executeQuery();
				while (resultSet2.next()) {

					carbonLst.add(resultSet2.getString(COUNTRY));
				}
				
			mp.put(Constants.LEXISNEXIS_PROVIDER, lexisNExisLst);
			mp.put(Constants.GBGROUP_PROVIDER, gbGroupLst);
			mp.put(Constants.CARBONSERVICE_PROVIDER, carbonLst);


		} catch (Exception e) {
			throw new KYCException(KYCErrors.ERROR_WHILE_LOADING_CACHE, e);
		} finally {
			closeResultset(resultSet2);
			closeResultset(resultSet);
			closePrepareStatement(stmt2);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		LOG.debug("END KYCDaoImpl : fetchKYCInitData. ");
		return mp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.IKYCDBService#
	 * getKYCProviderinitConfigProperties()
	 */
	@Override
	public ConcurrentMap<String, KYCProviderProperty> getKYCProviderinitConfigProperties() throws KYCException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		ConcurrentHashMap<String, KYCProviderProperty> mp = new ConcurrentHashMap<>();
		String providerName = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(KYCQueryConstants.GET_KYC_PROVIDER_INIT_CONFIG_PROPERTY);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				KYCProviderProperty property = new KYCProviderProperty();
				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, KYCProviderProperty.class);
				mp.put(providerName, property);
			}
		} catch (Exception e) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;

	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.IKYCDBService#getCountryShortCode(java.lang.String)
	 */
	@Override
	public String getCountryShortCode(String country) throws KYCException {
		String countryShortCode = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(KYCQueryConstants.GET_SHORT_COUNTRY_CODE);
			stmt.setString(1, country);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				countryShortCode = resultSet.getString("shortcode");
			}
		} catch (Exception e) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return countryShortCode;
	}

}
