/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: DBServiceImpl.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.sanction.core.IDBService;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class DBServiceImpl.
 * 
 * @author abhijitg
 */
@SuppressWarnings({"squid:S2095", "squid:S3077"})
public class DBServiceImpl extends AbstractDao implements IDBService {

	/** The db service impl. */
	private static volatile  DBServiceImpl dbServiceImpl = null;

	/**
	 * Instantiates a new DB service impl.
	 */
	private DBServiceImpl() {

	}

	/**
	 * Gets the single instance of DBServiceImpl.
	 *
	 * @return single instance of DBServiceImpl
	 */
	public static DBServiceImpl getInstance() {
		if (dbServiceImpl == null) {
			synchronized (DBServiceImpl.class) {
				if (dbServiceImpl == null) {
					dbServiceImpl = new DBServiceImpl();
				}
			}
		}
		return dbServiceImpl;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.sanction.core.IDBService#getSanctionProviderInitConfigProperty()
	 */
	@Override
	public Map<String, ProviderProperty> getSanctionProviderInitConfigProperty()
			throws SanctionException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		ProviderProperty property = null;
		String providerName = null;
		ConcurrentHashMap<String, ProviderProperty> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(SanctionQueryConstant.GET_SANCTION_PROVIDER_INIT_CONFIG_PROPERTY);

			resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, ProviderProperty.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA, e);
		} finally {
				closeResultset(resultSet);
				closePrepareStatement(stmt);
				closeConnection(conn);
		}
		return hm;
	}

}
