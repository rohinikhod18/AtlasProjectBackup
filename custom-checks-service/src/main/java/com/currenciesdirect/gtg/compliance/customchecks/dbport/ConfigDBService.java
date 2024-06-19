package com.currenciesdirect.gtg.compliance.customchecks.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class ConfigDBService.
 */
public class ConfigDBService extends AbstractDao {
	
	/**
	 * Gets the providerinit config properties.
	 *
	 * @return the providerinit config properties
	 * @throws CustomChecksException the custom checks exception
	 */
	@SuppressWarnings("squid:S2095")
	public ConcurrentMap<String, ProviderProperty> getProviderinitConfigProperties() throws CustomChecksException {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ConcurrentMap<String, ProviderProperty> mp = new ConcurrentHashMap<>();
		String providerName = null;
		Connection conn = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(DBConstants.GET_PROVIDER_INFO.getValue());
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				mp.put(providerName, JsonConverterUtil.convertToObject(ProviderProperty.class, json));
			}

		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_CONNECTION, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return mp;
	}
	
	
}
