package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IDeviceInfo;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class DeviceInfoImpl.
 */
public class DeviceInfoImpl extends AbstractDao implements IDeviceInfo {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DeviceInfoImpl.class);

	@Override
	public Message<MessageContext> saveDeviceInfo(Message<MessageContext> message) {
		DeviceInfo deviceInfo = null;
		Integer eventId = null;
		try {
			OperationEnum operationEnum = message.getPayload().getGatewayMessageExchange().getOperation();
			switch (operationEnum) {
			case NEW_REGISTRATION:
			case ADD_CONTACT:
				RegistrationServiceRequest request = (RegistrationServiceRequest) message.getPayload()
						.getGatewayMessageExchange().getRequest();
				deviceInfo = request.getDeviceInfo();
				break;
			case FUNDS_OUT:
				FundsOutRequest fundsOutRequest = (FundsOutRequest) message.getPayload().getGatewayMessageExchange()
						.getRequest();
				deviceInfo = fundsOutRequest.getDeviceInfo();
				break;
			case FUNDS_IN:
				FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) message.getPayload()
						.getGatewayMessageExchange().getRequest();
				deviceInfo = fundsInCreateRequest.getDeviceInfo();
				break;
			default:
				break;
			}

			eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute("eventId");

			if (deviceInfo != null) {
				saveIntoDeviceInfo(message, deviceInfo, eventId);
			}
		} catch (Exception e) {
			LOG.error("Error in saveDeviceInfo", e);
			try {
				saveIntoDeviceInfo(message, new DeviceInfo(), eventId);
			} catch (ComplianceException e1) {
				LOG.error("Error in saveDeviceInfo", e1);
			}
		}
		return message;
	}
	private void saveIntoDeviceInfo(Message<MessageContext> message, DeviceInfo deviceInfo, Integer eventId)
			throws ComplianceException {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Connection connection = getConnection(Boolean.FALSE);
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_DEVICEINFO.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			beginTransaction(connection);
			statement.setInt(1, eventId);
			updateParameters(deviceInfo, token, statement);
			statement.executeUpdate();
			commitTransaction(connection);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
	}
	
	private void updateParameters(DeviceInfo deviceInfo, UserProfile token, PreparedStatement statement)
			throws SQLException {
		setUserAgent(deviceInfo, statement);
			
		setDeviceType(deviceInfo, statement);

		setDeviceName(deviceInfo, statement);

		setDeviceVersion(deviceInfo, statement);

		setDeviceId(deviceInfo, statement);

		setDeviceMfg(deviceInfo, statement);

		setDeviceOS(deviceInfo, statement);

		setDeviceBrowserDetails(deviceInfo, statement);

		setDeviceOSTime(deviceInfo, statement);
		// Added new attribute in database
		setCDAppDetails(deviceInfo, statement);
		statement.setInt(16, token.getUserID());
		statement.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
		
	  //Following field is added for fraud predict API
		if (StringUtils.isNullOrEmpty(deviceInfo.getScreenResolution()))
			statement.setNull(18, Types.VARCHAR);
		else
			statement.setString(18, deviceInfo.getScreenResolution());	
	}
	
	private void setDeviceOSTime(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getOsDateAndTime())) {
			statement.setNull(13, Types.TIMESTAMP);
		} else {
			statement.setTimestamp(13, getTimeStampForSQl(deviceInfo));
		}
	}
	
	private void setCDAppDetails(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getCdAppId()))
			statement.setNull(14, Types.NVARCHAR);
		else
			statement.setString(14, deviceInfo.getCdAppId());

		if (StringUtils.isNullOrEmpty(deviceInfo.getCdAppVersion()))
			statement.setNull(15, Types.NVARCHAR);
		else
			statement.setString(15, deviceInfo.getCdAppVersion());
	}
	
	private void setDeviceBrowserDetails(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getBrowserName()))
			statement.setNull(9, Types.NVARCHAR);
		else
			statement.setString(9, deviceInfo.getBrowserName());

		if (StringUtils.isNullOrEmpty(deviceInfo.getBrowserMajorVersion()))
			statement.setNull(10, Types.NVARCHAR);
		else
			statement.setString(10, deviceInfo.getBrowserMajorVersion());

		if (StringUtils.isNullOrEmpty(deviceInfo.getBrowserLanguage()))
			statement.setNull(11, Types.NVARCHAR);
		else
			statement.setString(11, deviceInfo.getBrowserLanguage());

		if (StringUtils.isNullOrEmpty(deviceInfo.getBrowserOnline()))
			statement.setNull(12, Types.BOOLEAN);
		else
			statement.setBoolean(12, Boolean.parseBoolean(deviceInfo.getBrowserOnline()));		
		
	}
	
	private void setDeviceOS(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getOsName()))
			statement.setNull(8, Types.NVARCHAR);
		else
			statement.setString(8, deviceInfo.getOsName());
	}
	
	private void setDeviceMfg(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getDeviceManufacturer()))
			statement.setNull(7, Types.NVARCHAR);
		else
			statement.setString(7, deviceInfo.getDeviceManufacturer());
	}
	
	private void setDeviceId(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getDeviceId()))
			statement.setNull(6, Types.NVARCHAR);
		else
			statement.setString(6, deviceInfo.getDeviceId());
	}
	
	private void setDeviceVersion(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getDeviceVersion()))
			statement.setNull(5, Types.NVARCHAR);
		else
			statement.setString(5, deviceInfo.getDeviceVersion());
	}
	
	private void setDeviceName(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getDeviceName()))
			statement.setNull(4, Types.NVARCHAR);
		else
			statement.setString(4, deviceInfo.getDeviceName());
	}
	
	private void setDeviceType(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getDeviceType()))
			statement.setNull(3, Types.NVARCHAR);
		else
			statement.setString(3, deviceInfo.getDeviceType());
	}
	
	private void setUserAgent(DeviceInfo deviceInfo, PreparedStatement statement) throws SQLException {
		if (StringUtils.isNullOrEmpty(deviceInfo.getUserAgent()) || deviceInfo.getUserAgent().length() > 8000){
			statement.setNull(2, Types.NVARCHAR);
		} else {
			statement.setString(2, deviceInfo.getUserAgent());
		}
	}
	
	private Timestamp getTimeStampForSQl(DeviceInfo deviceInfo) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		Date date = null;
		Timestamp returnValue = new Timestamp(System.currentTimeMillis());
		try {
			date = df.parse(deviceInfo.getOsDateAndTime());
			returnValue = new Timestamp(date.getTime());
		} catch (Exception ex) {
			LOG.debug("Error parsing Device OsDateAndTime", ex);
		}
		return returnValue;
	}

}
