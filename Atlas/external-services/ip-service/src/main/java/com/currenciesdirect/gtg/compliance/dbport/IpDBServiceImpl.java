/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.currenciesdirect.gtg.compliance.core.IDBService;
import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.PostCodeLocation;
import com.currenciesdirect.gtg.compliance.exception.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.IpException;

/**
 * @author manish
 * 
 */
public class IpDBServiceImpl extends AbstractDao implements IDBService {

	private static IDBService idbService = new IpDBServiceImpl();

	/** The Constant LOG. */
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(IpDBServiceImpl.class);

	private IpDBServiceImpl() {

	}

	public static IDBService getInstance() {
		return idbService;
	}

	@Override
	public ConcurrentHashMap<String, IpProviderProperty> getIPProviderInitConfigProperty()
			throws IpException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		IpProviderProperty property = null;
		String providerName = null;
		ConcurrentHashMap<String, IpProviderProperty> hm = new ConcurrentHashMap<String, IpProviderProperty>();
		try {
			conn = getConnection();
			stmt = conn
					.prepareStatement(IpQueryConstants.GET_IP_PROVIDER_INIT_CONFIG_PROPERTY);

			resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				json = resultSet.getString("Attribute");
				providerName = resultSet.getString("Code");
				property = mapper.readValue(json, IpProviderProperty.class);
				hm.put(providerName, property);
			}

		} catch (Exception e) {
			
			throw new IpException(IpErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmt);
				closeConnection(conn);

			} catch (Exception e) {
			
				throw new IpException(IpErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return hm;

	}

	@Override
	public PostCodeLocation getLocationFromPostCode(String postCode)
			throws IpException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		PostCodeLocation codeLocation = new PostCodeLocation();
		int count = 0;
		try {
			conn = getConnection();
			stmt = conn
					.prepareStatement(IpQueryConstants.GET_LOCATION_FROM_POSTCODE);
			stmt.setString(1,postCode);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
			
				codeLocation.setLatitude(resultSet.getDouble("Latitude"));
				codeLocation.setLongitude(resultSet.getDouble("Longitude"));
				count++;
			}
			if(count<1){
				throw new IpException(IpErrors.INVALID_POST_CODE);
			}
			codeLocation.setPostcode(postCode);
		}catch(IpException exception) {
			throw exception;
		}
		catch (Exception e) {
			
				throw new IpException(IpErrors.DATABASE_GENERIC_ERROR, e);
			} finally {
				try {
					closeResultset(resultSet);
					closePrepareStatement(stmt);
					closeConnection(conn);

				} catch (Exception e) {
					
					throw new IpException(IpErrors.ERROR_WHILE_GETTING_POST_CODE_LOCATION, e);
				}
			}
			return codeLocation;
	}

}
