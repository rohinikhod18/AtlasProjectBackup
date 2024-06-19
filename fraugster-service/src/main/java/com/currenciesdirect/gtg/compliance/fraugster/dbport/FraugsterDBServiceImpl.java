/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.fraugster.core.IDBService;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;

/**
 * The Class FraugsterDBServiceImpl.
 *
 * @author manish
 */
@SuppressWarnings("squid:S2095")
public class FraugsterDBServiceImpl extends AbstractDao implements IDBService {

	/** The idb service. */
	private static IDBService idbService = new FraugsterDBServiceImpl();

	/**
	 * Instantiates a new fraugster DB service impl.
	 */
	private FraugsterDBServiceImpl() {

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
	public ConcurrentMap<String, FraugsterProviderProperty> getFraugsterProviderInitConfigProperty()
			throws FraugsterException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		FraugsterProviderProperty property = null;
		String providerName = null;
		ConcurrentHashMap<String, FraugsterProviderProperty> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(FraugsterQueryConstants.GET_FRAUGSTER_PROVIDER_INIT_CONFIG_PROPERTY);

			resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, FraugsterProviderProperty.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return hm;
	}

}
