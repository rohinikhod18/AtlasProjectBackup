package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IRegistrationDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class AbstractRegistrationDBService.
 */
@SuppressWarnings("squid:S2095")
public abstract class AbstractRegistrationDBService extends AbstractDBServiceImpl implements IRegistrationDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRegistrationDBService.class);
	
	/** The Constant VERSION. */
	private static final String VERSION = "Version";
	
	/** The Constant OLD_ORGANIZATION_ID. */
	private static final String OLD_ORGANIZATION_ID ="OldOrganizationId";

	/** The Constant ACCOUNT_TM_FLAG. */
	private static final String ACCOUNT_TM_FLAG ="AccountTMFlag";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getContactFromContactAttribute(java.lang.Integer)
	 */
	@Override
	public Contact getContactFromContactAttribute(Integer contactId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ATTRIBUTES_FROM_CONTACTID.getQuery());
			preparedStatment.setInt(1, contactId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				return JsonConverterUtil.convertToObject(Contact.class, rs.getString(1));
			}

		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getAccountFromAccountAttribute(java.lang.Integer)
	 */
	@Override
	public Account getAccountFromAccountAttribute(Integer accountId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ATTRIBUTES_FROM_ACCOUNTID.getQuery());
			preparedStatment.setInt(1, accountId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				return JsonConverterUtil.convertToObject(Account.class, rs.getString(1));
			}

		} catch (SQLException e) {
			LOG.error("", e);
			} catch (Exception e) {
			LOG.error(Constants.ERROR, e);
			} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#isAccountexist(java.lang.String)
	 */
	@Override
	public Boolean isAccountexist(String accountID) throws ComplianceException {
		Connection conn = null;
		PreparedStatement accStatment = null;
		ResultSet accountRS = null;
		try {
			conn = getConnection(Boolean.TRUE);
			accStatment = conn.prepareStatement(DBQueryConstant.CHECK_ACCOUNT_ID_EXIST.getQuery());
			accStatment.setString(1, accountID);
			accountRS = accStatment.executeQuery();
			if (accountRS.next()) {
				accountRS.close();
				return Boolean.TRUE;
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(accountRS);
			closePrepareStatement(accStatment);
			closeConnection(conn);

		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#isContactexist(java.lang.String)
	 */
	@Override
	public Boolean isContactexist(String contactID) throws ComplianceException {
		Connection conn = null;
		PreparedStatement contactStatment = null;
		ResultSet contactRS = null;
		try {
			conn = getConnection(Boolean.TRUE);
			contactStatment = conn.prepareStatement(DBQueryConstant.CHECK_CONTACT_ID_EXIST.getQuery());
			contactStatment.setString(1, contactID);
			contactRS = contactStatment.executeQuery();
			if (contactRS.next()) {
				contactRS.close();
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(contactRS);
			closePrepareStatement(contactStatment);
			closeConnection(conn);
		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#checkCountryEnum(java.lang.String)
	 */
	@Override
	public Boolean checkCountryEnum(String country) throws ComplianceException {
		Connection countryConn = null;
		PreparedStatement countryStatment = null;
		ResultSet countryRS = null;
		try {
			countryConn = getConnection(Boolean.TRUE);
			countryStatment = countryConn.prepareStatement(DBQueryConstant.CHECK_COUNTRY_ENUM.getQuery());
			countryStatment.setString(1, country);
			countryRS = countryStatment.executeQuery();
			if (countryRS.next()) {
				countryRS.close();
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(countryRS);
			closePrepareStatement(countryStatment);
			closeConnection(countryConn);
		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getAccountVersion(java.lang.String)
	 */
	@Override
	public Integer getAccountVersion(String accountId) throws ComplianceException {
		Connection accVersionConn = null;
		PreparedStatement accVersionStatment = null;
		ResultSet accVersionRS = null;
		try {
			accVersionConn = getConnection(Boolean.TRUE);
			accVersionStatment = accVersionConn.prepareStatement(DBQueryConstant.GET_ACCOUNT_VERSION.getQuery());
			accVersionStatment.setString(1, accountId);
			accVersionRS = accVersionStatment.executeQuery();
			if (accVersionRS.next()) {
				return accVersionRS.getInt(VERSION);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(accVersionRS);
			closePrepareStatement(accVersionStatment);
			closeConnection(accVersionConn);
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getContactVersion(java.lang.String)
	 */
	@Override
	public Integer getContactVersion(String contactId) throws ComplianceException {
		Connection contactVersionConn = null;
		PreparedStatement contactVersionStatment = null;
		ResultSet contactVersionRS = null;
		try {
			contactVersionConn = getConnection(Boolean.TRUE);
			contactVersionStatment = contactVersionConn
					.prepareStatement(DBQueryConstant.GET_CONTACT_VERSION.getQuery());
			contactVersionStatment.setString(1, contactId);
			contactVersionRS = contactVersionStatment.executeQuery();
			if (contactVersionRS.next()) {
				return contactVersionRS.getInt(VERSION);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(contactVersionRS);
			closePrepareStatement(contactVersionStatment);
			closeConnection(contactVersionConn);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getOrganisationID(java.lang.String)
	 */
	@Override
	public Integer getOrganisationID(String orgCode) throws ComplianceException {
		Connection orgConn = null;
		PreparedStatement orgStatment = null;
		ResultSet orgRS = null;
		try {
			orgConn = getConnection(Boolean.TRUE);
			orgStatment = orgConn.prepareStatement(DBQueryConstant.GET_ORG_ID.getQuery());
			orgStatment.setString(1, orgCode);
			orgRS = orgStatment.executeQuery();
			if (orgRS.next()) {
				return orgRS.getInt("ID");
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(orgRS);
			closePrepareStatement(orgStatment);
			closeConnection(orgConn);
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getRegistrationRequestByContactId(java.lang.
	 * Integer, java.lang.Integer)
	 */
	@Override
	public RegistrationServiceRequest getRegistrationRequestByContactId(Integer entityId, Integer accountId)
			throws ComplianceException {
		RegistrationServiceRequest request = new RegistrationServiceRequest();
		Connection regRequestConn = null;
		PreparedStatement regRequestStatment = null;
		ResultSet regRequestRS = null;
		try {
			regRequestConn = getConnection(Boolean.TRUE);
			regRequestStatment = regRequestConn
					.prepareStatement(DBQueryConstant.GET_ACCOUNT_AND_CONTACT_ATTRIBUTE_BY_CONTACTID.getQuery());
			regRequestStatment.setInt(1, accountId);
			regRequestStatment.setInt(2, entityId);
			regRequestStatment.setInt(3, entityId);
			regRequestRS = regRequestStatment.executeQuery();
			if (regRequestRS.next()) {
				
				request.setOrgCode(regRequestRS.getString("Org"));
				Account account = JsonConverterUtil.convertToObject(Account.class, regRequestRS.getString("AccAttrib"));
				account.setId(accountId);
				Contact contact = JsonConverterUtil.convertToObject(Contact.class, regRequestRS.getString("ConAttrib"));
				contact.setId(entityId);
				List<Contact> contacts = new ArrayList<>();
				contacts.add(contact);
				account.setContacts(contacts);
				request.setOldOrgId(regRequestRS.getInt(OLD_ORGANIZATION_ID));
				request.setAccount(account);
			}
			
			request.setDeviceInfo(getDeviceInfoDetails(accountId));
			return request;

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(regRequestRS);
			closePrepareStatement(regRequestStatment);
			closeConnection(regRequestConn);
		}
		return null;
	}

	
	/**
	 * @param accountId
	 * @return dInfo
	 * @throws ComplianceException
	 */
	private DeviceInfo getDeviceInfoDetails(Integer accountId)throws ComplianceException  {
		
		DeviceInfo dInfo = new DeviceInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		con = getConnection(Boolean.TRUE);
		ps = con.prepareStatement(DBQueryConstant.GET_DEVICE_INFO.getQuery());
		ps.setInt(1, accountId);
		rs = ps.executeQuery();
		if (rs.next()) {
				dInfo.setBrowserLanguage(rs.getString("BrowserLanguage"));
				dInfo.setBrowserMajorVersion(rs.getString("BrowserVersion"));
				dInfo.setBrowserName(rs.getString("BrowserName"));
				
				if(null !=rs.getString("BrowserOnline")) {
				if(rs.getString("BrowserOnline").equals("1")) {
					dInfo.setBrowserOnline("true");	
				}
				else {
					dInfo.setBrowserOnline("false");	
			 	  }
			    }
				
				dInfo.setCdAppId(rs.getString("CDAppID"));
				dInfo.setCdAppVersion(rs.getString("CDAppVersion"));
				dInfo.setDeviceId(rs.getString("DeviceID"));
				dInfo.setDeviceManufacturer(rs.getString("DeviceManufacturer"));
				dInfo.setDeviceName(rs.getString("DeviceName"));
				dInfo.setOsName(rs.getString("OSType"));
				dInfo.setDeviceType(rs.getString("DeviceType"));
				dInfo.setDeviceVersion(rs.getString("DeviceVersion"));
				dInfo.setOsDateAndTime(rs.getString("OSTimestamp"));
				dInfo.setUserAgent(rs.getString("UserAgent"));
				dInfo.setScreenResolution(rs.getString("ScreenResolution"));
		 }
		return dInfo;
	} catch (Exception e) {
		LOG.error("", e);
	} finally {
		closeResultset(rs);
		closePrepareStatement(ps);
		closeConnection(con);
	 }
		return null;
  }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getRegistrationDateTime(java.lang.Integer)
	 */
	@Override
	public Timestamp getRegistrationDateTime(Integer accId) throws ComplianceException {
		Connection regTimeConn = null;
		PreparedStatement regTimeStatment = null;
		ResultSet regTimeRS = null;
		try {
			regTimeConn = getConnection(Boolean.TRUE);
			regTimeStatment = regTimeConn.prepareStatement(DBQueryConstant.GET_ACCOUNT_CREATION_TIME.getQuery());
			regTimeStatment.setInt(1, accId);
			regTimeRS = regTimeStatment.executeQuery();
			if (regTimeRS.next()) {
				return regTimeRS.getTimestamp(1);
			}

		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(regTimeRS);
			closePrepareStatement(regTimeStatment);
			closeConnection(regTimeConn);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#isTradeAccountIdExist(java.lang.Integer)
	 */
	@Override
	public Boolean isTradeAccountIdExist(Integer accountID) throws ComplianceException {
		Connection tradeAccIdConn = null;
		PreparedStatement tradeAccIdStatment = null;
		ResultSet tradeAccIdRS = null;
		try {
			tradeAccIdConn = getConnection(Boolean.TRUE);
			tradeAccIdStatment = tradeAccIdConn
					.prepareStatement(DBQueryConstant.CHECK_TRADE_ACCOUNT_ID_EXIST_FOR_REPEAT_CHECK.getQuery());
			tradeAccIdStatment.setInt(1, accountID);
			tradeAccIdRS = tradeAccIdStatment.executeQuery();
			if (tradeAccIdRS.next()) {
				tradeAccIdRS.close();
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(tradeAccIdRS);
			closePrepareStatement(tradeAccIdStatment);
			closeConnection(tradeAccIdConn);
		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getRegistrationRequestByAccountID(java.lang.
	 * Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public RegistrationServiceRequest getRegistrationRequestByAccountID(Integer entityId, Integer accountId,
			String orgCode) throws ComplianceException {

		Connection regAccIDconn = null;
		PreparedStatement regAccIDStatment = null;
		ResultSet regAccIDRS = null;
		try {
			regAccIDconn = getConnection(Boolean.TRUE);
			regAccIDStatment = regAccIDconn
					.prepareStatement(DBQueryConstant.GET_ACCOUNT_ATTRIBUTE_BY_ACCOUNT_ID.getQuery());
			regAccIDStatment.setInt(1, accountId);
			regAccIDRS = regAccIDStatment.executeQuery();
			if (regAccIDRS.next()) {
				RegistrationServiceRequest request = new RegistrationServiceRequest();
				request.setOrgCode(orgCode);
				Account account = JsonConverterUtil.convertToObject(Account.class, regAccIDRS.getString("Attributes"));
				account.setId(accountId);
				request.setAccount(account);
				request.setOldOrgId(regAccIDRS.getInt(OLD_ORGANIZATION_ID));
				return request;
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(regAccIDRS);
			closePrepareStatement(regAccIDStatment);
			closeConnection(regAccIDconn);
		}
		return null;

	}

	/**
	 * Update contact attributes.
	 *
	 * @param contacts
	 *            the contacts
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	protected void updateContactAttributes(List<Contact> contacts) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			if (contacts != null && !contacts.isEmpty()) {
				statement = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_ATTRIBUTE.getQuery());
				for (Contact contact : contacts) {
					String contactJson = JsonConverterUtil.convertToJsonWithoutNull(contact);
					statement.setString(1, contactJson);
					statement.setInt(2, contact.getId());
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
	}

	/**
	 * Save into contact compliance history.
	 *
	 * @param connection
	 *            the connection
	 * @param response
	 *            the response
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	protected void saveIntoContactComplianceHistory(Connection connection, RegistrationResponse response, Integer user)
			throws ComplianceException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_CONTACT_COMPLIANCE_HISTORY.getQuery());

			for (ComplianceContact contact : response.getAccount().getContacts()) {
				statement.setInt(1, contact.getId());
				statement.setString(2, contact.getCcs().name());
				statement.setInt(3, user);
				statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				statement.setInt(5, contact.getVersion());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

	/**
	 * Save into profile status reason.
	 *
	 * @param connection
	 *            the connection
	 * @param response
	 *            the response
	 * @param accountId
	 *            the account id
	 * @param orgCode
	 *            the org code
	 * @param user
	 *            the user
	 * @return true, if successful
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	protected boolean saveIntoProfileStatusReason(Connection connection, RegistrationResponse response, int accountId,
			String orgCode, Integer user) throws ComplianceException {
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_PROFILE_STATUS_REASON.getQuery());
			for (ComplianceContact contact : response.getAccount().getContacts()) {
				if (null != contact.getCrc() && null != contact.getCrc().getReasonShort()) {
					statement.setString(1, orgCode);
					statement.setInt(2, accountId);
					statement.setInt(3, contact.getId());
					statement.setString(4, "ALL");
					statement.setString(5, contact.getCrc().getReasonShort());
					statement.setInt(6, user);
					statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
					statement.setInt(8, user);
					statement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
					statement.addBatch();
				}
			}
			statement.executeBatch();
			return true;
		} catch (SQLException se) {
			if (se.getMessage().contains("UNIQUE KEY constraint 'CN_UniqueResourceLock'")) {
				return true;
			}
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, se);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}

	}

	/**
	 * Update into account attribute status.
	 *
	 * @param connection
	 *            the connection
	 * @param acc
	 *            the acc
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	protected void updateIntoAccountAttributeStatus(Connection connection, Account acc, Integer userID)
			throws ComplianceException {
		List<Contact> contacts = acc.getContacts();
		acc.setContacts(null);
		String objJson = JsonConverterUtil.convertToJsonWithoutNull(acc);
		acc.setContacts(contacts);
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_INTO_ACCOUNT_ATTRIBUTE_STATUS.getQuery());

			statement.setString(1, objJson);
			statement.setInt(2, userID);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, acc.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

	/**
	 * Update KYC status.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateKYCStatus(Message<MessageContext> message) throws ComplianceException {

		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
		RegistrationServiceRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(RegistrationServiceRequest.class);
		EntityDetails entityDetails = (EntityDetails) request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		EventServiceLog evenServiceLog = exchange.getEventServiceLog(exchange.getServiceTypeEnum(),
				entityDetails.getEntityType(), entityDetails.getEntityId());
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Integer kycStatus = ServiceStatus.NOT_PERFORMED.getServiceStatusAsInteger();
		if (!StringUtils.isNullOrTrimEmpty(evenServiceLog.getStatus())) {
			kycStatus = ServiceStatus.getStatusAsInteger(evenServiceLog.getStatus());
		}
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatment = connection.prepareStatement(DBQueryConstant.UPDATE_KYC_STATUS_FOR_CONTACT.getQuery());
			preparedStatment.setInt(1, kycStatus);
			preparedStatment.setInt(2, evenServiceLog.getEntityId());
			preparedStatment.executeUpdate();
		} catch (Exception e) {
			LOG.error("Error in AbstractRegistrationDBService updateKYCStatus()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}
		return message;
	}

	/**
	 * Save into account compliance history.
	 *
	 * @param connection
	 *            the connection
	 * @param response
	 *            the response
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	protected void saveIntoAccountComplianceHistory(Connection connection, RegistrationResponse response, Integer user)
			throws ComplianceException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_ACCOUNT_COMPLIANCE_HISTORY.getQuery());
			statement.setInt(1, response.getAccount().getId());
			statement.setString(2, response.getAccount().getAcs().name());
			statement.setInt(3, user);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.setInt(5, response.getAccount().getVersion());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getAccountIdByCrmAccountId(java.lang.String)
	 */
	@Override
	public Integer getAccountIdByCrmAccountId(String crmAccountId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ACCOUNTID_FROM_CRMACCOUNTID.getQuery());
			preparedStatment.setString(1, crmAccountId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				return rs.getInt("Id");
			}

		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return null;
	}

	/**
	 * This function return Customer type as a integer if Customer type is CFX
	 * then return 1 if Customer type is PFX then return 2 if Customer type is
	 * CFX(etailer) then return 3 Added in Atlas 1.6
	 *
	 * @author abhijeetg
	 * @param account the account
	 * @return the customer type
	 */
	protected Integer getCustomerType(Account account) {

		Integer custType = CustomerTypeEnum.getCustumerTypeAsInteger(account.getCustType());
		if (null != account.getCompany() && !StringUtils.isNullOrEmpty(account.getCompany().getEtailer())
				&& "true".equalsIgnoreCase(account.getCompany().getEtailer()) && custType == 1) {
			custType = 3;
		}
		return custType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getAccountDetails(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.util.List)
	 */
	@Override
	public RegistrationServiceRequest getAccountDetails(String orgCode, String buyCurrency, String sellCurrency,
			String countryName, String accountSfId, List<String> contactSFIds) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		RegistrationServiceRequest details = new RegistrationServiceRequest();
		try {
			String contactIdsStr = "''";
			if (!contactSFIds.isEmpty()) {
				contactIdsStr = contactSFIds.toString();
				contactIdsStr = contactIdsStr.substring(1, contactIdsStr.length() - 1).replace(", ", ",");
			}
			connection = getConnection(Boolean.TRUE);
			String query = DBQueryConstant.GET_ACCOUNT_CONTACT_DETAILS.getQuery().replace(Constants.CONTACTSFID,
					contactIdsStr);
			statement = connection.prepareStatement(query);
			statement.setString(1, accountSfId);
			statement.setString(2, orgCode);
			statement.setString(3, countryName);
			statement.setString(4, buyCurrency);
			statement.setString(5, sellCurrency);
			rs = statement.executeQuery();
			int count = 1;
			String json = null;
			Account account = null;
			List<Contact> oldContacts = new ArrayList<>();
			while (rs.next()) {
				json = rs.getString("ACAttrib");
				if (count == 1) {
					account = setValuesForAccount(rs, details, json, account);

				}

				json = rs.getString("CAttrib");
				if (null != json) {
					Contact c = JsonConverterUtil.convertToObject(Contact.class, json);
					c.setPrimaryContact(rs.getBoolean("PrimaryContact"));
					c.setId(rs.getInt("ContactId"));
					c.setVersion(rs.getInt("CAVersion"));
					c.setContactStatus(rs.getString("CoStatus"));
					c.setContactSFID(rs.getString("CRMContactID"));
					c.setPreviousBlacklistStatus(ServiceStatus.getStatusAsString(rs.getInt("CBlacklistStatus")));
					c.setPreviousKycStatus(ServiceStatus.getStatusAsString(rs.getInt("CEIDStatus")));
					c.setPreviousFraugsterStatus(ServiceStatus.getStatusAsString(rs.getInt("CFraugsterStatus")));
					c.setPreviousSanctionStatus(ServiceStatus.getStatusAsString(rs.getInt("CSanctionStatus")));
					c.setPreviousCountryGlobalCheckStatus(ServiceStatus.getStatusAsString(rs.getInt("CCustomCheckStatus")));
					c.setPreviousPaymentinWatchlistStatus(ServiceStatus.getStatusAsString(rs.getInt("CPayInWatchlistStatus")));//Add for AT-2986
					c.setPreviousPaymentoutWatchlistStatus(ServiceStatus.getStatusAsString(rs.getInt("CPayOutWatchlistStatus")));//Add for AT-2986
					c.setOnQueue(rs.getBoolean("ContactOnQueue"));
					oldContacts.add(c);
				}
				count++;
			}
			if (null != account)
				account.setContacts(oldContacts);
			details.addAttribute(Constants.FIELD_OLD_CONTACTS, oldContacts);
			details.setAccount(account);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return details;
	}

	/**
	 * Sets the values for account.
	 *
	 * @param rs the rs
	 * @param details the details
	 * @param json the json
	 * @param account the account
	 * @return the account
	 * @throws SQLException the SQL exception
	 */
	private Account setValuesForAccount(ResultSet rs, RegistrationServiceRequest details, String json, Account account)
			throws SQLException {
		if (null != json) {
			account = JsonConverterUtil.convertToObject(Account.class, json);
			account.setId(rs.getInt("AccountId"));
			account.setAccountStatus(rs.getString("ACStatus"));
			account.setVersion(rs.getInt("ACVersion"));
			account.setPreviousBlacklistStatus(ServiceStatus.getStatusAsString(rs.getInt("ABlacklistStatus")));
			account.setPreviousKycStatus(ServiceStatus.getStatusAsString(rs.getInt("AEIDStatus")));
			account.setPreviousFraugsterStatus(ServiceStatus.getStatusAsString(rs.getInt("AFraugsterStatus")));
			account.setPreviousSanctionStatus(ServiceStatus.getStatusAsString(rs.getInt("ASanctionStatus")));
			account.setPreviousPaymentinWatchlistStatus(
					ServiceStatus.getStatusAsString(rs.getInt("PayInWatchlistStatus")));
			account.setPreviousPaymentoutWatchlistStatus(
					ServiceStatus.getStatusAsString(rs.getInt("PayOutWatchlistStatus")));
			account.setOnQueue(rs.getBoolean("AccOnQueue"));
			account.setCustLegalEntity(rs.getString("ACLegalEntity"));//Add for AT-3327

		}
		details.setOldOrgId(rs.getInt(OLD_ORGANIZATION_ID));
		if(null == details.getOldOrgId() || 0 == details.getOldOrgId()) {
			details.setOrgId(rs.getInt("RequestOrgID"));
		} else {
			details.setOrgId(details.getOldOrgId());
		}
		details.addAttribute("noOfContacts", rs.getInt("contacts"));
		details.addAttribute("BuyCurrencyId", rs.getInt("BuyCurrencyId"));
		details.addAttribute("SellCurrencyId", rs.getInt("SellCurrencyId"));
		details.addAttribute("CountryID", rs.getInt("CountryID"));
		details.addAttribute("ACOrgID", rs.getInt("ACOrgID"));
		details.addAttribute("ACOrgName", rs.getString("ACOrgName"));
		details.addAttribute("RegistrationInTime", rs.getTimestamp("RegistrationInTime"));
		details.addAttribute("RegisteredTime", rs.getTimestamp("RegisteredTime"));
		details.addAttribute("ComplianceExpiry", rs.getTimestamp("ComplianceExpiry"));
		details.addAttribute(ACCOUNT_TM_FLAG, rs.getInt(ACCOUNT_TM_FLAG));//Add for AT-4169
		details.addAttribute("IntuitionRiskLevel", rs.getString("IntuitionRiskLevel"));//Added for AT-4190
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getFirstFraugsterSignupSummary(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public FraugsterSummary getFirstFraugsterSignupSummary(Integer entityId, String entityType)
			throws ComplianceException {
		Connection fSummaryConn = null;
		PreparedStatement fSummaryStatment = null;
		ResultSet rs = null;
		FraugsterSummary fraugsterSummary = null;

		try {
			fSummaryConn = getConnection(Boolean.TRUE);
			fSummaryStatment = fSummaryConn
					.prepareStatement(DBQueryConstant.GET_FRAUGSTER_FIRST_SIGNUP_SUMMARY.getQuery());
			fSummaryStatment.setInt(1, EntityEnum.getEntityTypeAsInteger(entityType));
			fSummaryStatment.setString(2, "" + entityId);
			rs = fSummaryStatment.executeQuery();
			if (rs.next()) {
				fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class, rs.getString("Summary"));
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(fSummaryStatment);
			closeConnection(fSummaryConn);
		}
		return fraugsterSummary;
	}
	
	/**
	 * Gets the last fraugster signup details.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @param fundsOutRequest the funds out request
	 * @return the last fraugster signup details
	 * @throws ComplianceException the compliance exception
	 */
	//ADD for AT-3161 
	//changes for AT-3243
	public void getFraugsterSignupDetails(Integer entityId, String entityType, FundsOutRequest fundsOutRequest)
			throws ComplianceException {
		Connection fSummaryConn = null;
		PreparedStatement fSummaryStatment = null;
		ResultSet rs = null;
		LinkedHashMap<String, String> linkmap = new LinkedHashMap<>();

		try {
			fSummaryConn = getConnection(Boolean.TRUE);
			fSummaryStatment = fSummaryConn
					.prepareStatement(DBQueryConstant.GET_FRAUGSTER_SIGNUP_DETAILS.getQuery());
			fSummaryStatment.setInt(1, EntityEnum.getEntityTypeAsInteger(entityType));
			fSummaryStatment.setString(2, "" + entityId);
			rs = fSummaryStatment.executeQuery();
			while (rs.next()) {
				linkmap.put(rs.getString("UpdatedOn"), rs.getString("fraugsterApprovedScore"));//changes for AT-3243
			}
			fundsOutRequest.addAttribute("FraugsterPreviousScore&UpdatedOn", linkmap);
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(fSummaryStatment);
			closeConnection(fSummaryConn);
		}
	}

	/**
	 * Sets the contact status to request.
	 *
	 * @param contactList the contact list
	 * @param contactResponse the contact response
	 * @param req the req
	 */
	protected void setContactStatusToRequest(List<Contact> contactList, ComplianceContact contactResponse,
			Contact req) {
		if (contactResponse.getId().equals(req.getId())) {
			req.setContactStatus(contactResponse.getCcs().toString());
			contactList.add(req);
		}
	}
	
	/**
	 * Update fraugster status.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> updateFraugsterStatus(Message<MessageContext> message) throws ComplianceException {

		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		FraugsterResendRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(FraugsterResendRequest.class);
		RegistrationServiceRequest oldRequest = request.getAdditionalAttribute(Constants.OLD_REQUEST,
				RegistrationServiceRequest.class);
		EntityDetails entityDetails = (EntityDetails) oldRequest.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		EventServiceLog evenServiceLog = exchange.getEventServiceLog(exchange.getServiceTypeEnum(),
				entityDetails.getEntityType(), entityDetails.getEntityId());
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Integer fraugsterStatus = ServiceStatus.NOT_PERFORMED.getServiceStatusAsInteger();
		if (!StringUtils.isNullOrTrimEmpty(evenServiceLog.getStatus())) {
			fraugsterStatus = ServiceStatus.getStatusAsInteger(evenServiceLog.getStatus());
		}
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatment = connection.prepareStatement(DBQueryConstant.UPDATE_FRAUGSTER_STATUS_FOR_CONTACT.getQuery());
			preparedStatment.setInt(1, fraugsterStatus);
			preparedStatment.setInt(2, evenServiceLog.getEntityId());
			preparedStatment.executeUpdate();
		} catch (Exception e) {
			LOG.error("Error in AbstractRegistrationDBService updateFraugsterStatus()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.IRegistrationDBService#getOldOrganisationID(java.lang.Integer)
	 */
	@Override
	public Integer getOldOrganisationID(Integer accountId) throws ComplianceException {
		Connection orgConn = null;
		PreparedStatement orgStatment = null;
		ResultSet orgRS = null;
		try {
			orgConn = getConnection(Boolean.TRUE);
			orgStatment = orgConn.prepareStatement(DBQueryConstant.GET_OLD_ORG_ID.getQuery());
			orgStatment.setInt(1, accountId);
			orgRS = orgStatment.executeQuery();
			if (orgRS.next()) {
				return orgRS.getInt(OLD_ORGANIZATION_ID);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(orgRS);
			closePrepareStatement(orgStatment);
			closeConnection(orgConn);
		}
		return null;
	}

	
	/**
	 * Gets the account id by trade account number.
	 *
	 * @param tradeAccountNumber the trade account number
	 * @param tradeContactId the trade contact id
	 * @return the account id by trade account number
	 * @throws ComplianceException the compliance exception
	 */
	//Added For AT-4812
    public Map<String, String> getAccountIdByTradeAccountNumber(String tradeAccountNumber, Integer tradeContactId) throws ComplianceException {
        Connection conn = null;
        PreparedStatement preparedStatment = null;
        ResultSet rs = null;
        Map<String, String> accountInfo = new HashMap<>();
        try {
            conn = getConnection(Boolean.TRUE);
            preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ACCOUNTID_FROM_TRADEACCOUNTNUMBER.getQuery());
            preparedStatment.setString(1, tradeAccountNumber);
            preparedStatment.setInt(2, tradeContactId);
            rs = preparedStatment.executeQuery();
            if (rs.next()) {
            	accountInfo.put("id", rs.getString("Id"));
            	accountInfo.put("orgCode",rs.getString("Code"));
            	accountInfo.put("contactId",rs.getString("contactId"));
            	accountInfo.put("tradeAccountID",rs.getString("TradeAccountID"));
            	accountInfo.put("accountTMFlag",rs.getString(ACCOUNT_TM_FLAG)); // AT-5127
            }
        } catch (SQLException e) {
            throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
        } finally {
            closeResultset(rs);
            closePrepareStatement(preparedStatment);
            closeConnection(conn);
        }
        return accountInfo;
    }
    
    /**
     * Update into account TM flag.
     *
     * @param accountId the account id
     * @param accountTMFlag the account TM flag
     * @param userId the user id
     * @throws ComplianceException the compliance exception
     */
    //AT-5127
	public void updateIntoAccountTMFlag(Integer accountId, Integer accountTMFlag, Integer userId)
			throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection
					.prepareStatement(DBQueryConstant.UPDATE_INTO_ACCOUNT_TMFLAG.getQuery());

			statement.setInt(1, accountTMFlag);
			statement.setInt(2, userId);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, accountId);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
	}
}
