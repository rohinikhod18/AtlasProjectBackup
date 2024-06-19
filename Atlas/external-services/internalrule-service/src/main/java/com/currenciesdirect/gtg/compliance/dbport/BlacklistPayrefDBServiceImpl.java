package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.currenciesdirect.gtg.compliance.core.blacklist.payref.IDBService;
import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("squid:S2095")
public class BlacklistPayrefDBServiceImpl extends AbstractDao implements IDBService {
	/** The idb service. */
	private static IDBService idbService = new BlacklistPayrefDBServiceImpl();

	/**
	 * Instantiates a new BlacklistPayref DB service impl.
	 */

	private BlacklistPayrefDBServiceImpl() {

	}

	public static IDBService getInstance() {
		return idbService;
	}

	@Override
	public ConcurrentMap<String,BlacklistPayrefProviderProperty> getBlacklistPayrefProviderInitConfigProperty()
			throws InternalRuleException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		BlacklistPayrefProviderProperty property = null;
		String providerName = null;
		ConcurrentHashMap<String, BlacklistPayrefProviderProperty> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(BlacklistPayrefQueryConstant.GET_BLACKLISTPAYREF_PROVIDER_INIT_CONFIG_PROPERTY);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, BlacklistPayrefProviderProperty.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA, e);
		} finally {

			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);

		}
		return hm;
	}
}
