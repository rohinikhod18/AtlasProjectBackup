package com.currenciesdirect.gtg.compliance.core.loadcache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class FraudSightDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
public class FraudSightDBServiceImpl extends AbstractDao implements IDBService{
	/** The idb service. */
	private static IDBService idbService = new FraudSightDBServiceImpl();

	/**
	 * Instantiates a new fraugster DB service impl.
	 */
	private FraudSightDBServiceImpl() {

	}

	/**
	 * Gets the single instance of FraugsterDBServiceImpl.
	 *
	 * @return single instance of FraugsterDBServiceImpl
	 */
	public static IDBService getInstance() {
		return idbService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IDBService#
	 * getFraugsterProviderInitConfigProperty()
	 */
	@Override
	public ConcurrentMap<String, FraudSightProviderProperty> getFraudSightProviderInitConfigProperty()
			{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		FraudSightProviderProperty property = null;
		String providerName = null;
		ConcurrentHashMap<String, FraudSightProviderProperty> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=1 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='FRAUDSIGHTSCORE')");
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, FraudSightProviderProperty.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			LOG.error("error in FraudSightDBServiceImpl in getFraudSightProviderInitConfigProperty()", e);
		} finally{
			try{
				closeResultset(resultSet);
				closePrepareStatement(stmt);
				closeConnection(conn);
			}catch(Exception e) {
				LOG.error("Could not close preparedStaement", e);
			}
		}
		return hm;
	}
	}

