/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport.ip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.ip.PostCodeLocation;
import com.currenciesdirect.gtg.compliance.core.ip.IDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;

/**
 * @author manish
 * 
 */
@SuppressWarnings("squid:S2095")
public class IpDBServiceImpl extends AbstractDao implements IDBService {

	private static IDBService idbService = new IpDBServiceImpl();

	/** The Constant LOG. */
	private IpDBServiceImpl() {

	}

	public static IDBService getInstance() {
		return idbService;
	}

	@Override
	public Map<String, IpProviderProperty> getIPProviderInitConfigProperty() throws InternalRuleException  {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		IpProviderProperty property = null;
		String providerName = null;
		Map<String, IpProviderProperty> hm = new ConcurrentHashMap<>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(IpQueryConstants.GET_IP_PROVIDER_INIT_CONFIG_PROPERTY);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, IpProviderProperty.class);
				hm.put(providerName, property);
			}
		} catch (Exception e) {
			throw new InternalRuleException(IpErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return hm;

	}

	@Override
	public PostCodeLocation getLocationFromPostCode(String postCode) throws InternalRuleException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		PostCodeLocation codeLocation = new PostCodeLocation();
		int count = 0;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(IpQueryConstants.GET_LOCATION_FROM_POSTCODE);
			stmt.setString(1, postCode);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				codeLocation.setLatitude(resultSet.getDouble("Latitude"));
				codeLocation.setLongitude(resultSet.getDouble("Longitude"));
				count++;
			}
			if (count < 1) {
				throw new InternalRuleException(IpErrors.INVALID_POST_CODE);
			}
			codeLocation.setPostcode(postCode);
		} catch (IpException exception) {
			throw exception;
		} catch (Exception e) {
			throw new InternalRuleException(IpErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		return codeLocation;
	}

}
