package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.commons.enums.TransactionTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.INewRegistrationDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact.DeleteContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class NewRegistrationDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
public class NewRegistrationDBServiceImpl extends AbstractRegistrationDBService implements INewRegistrationDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(NewRegistrationDBServiceImpl.class);

	/** The Constant ENTITY_TYPE. */
	private static final String ENTITY_TYPE = "EntityType";

	/** The Constant CONTACTID. */
	private static final String CONTACTID = "contactid";

	/** The Constant ACCOUNT. */
	private static final String ACCOUNT = "oldAccount";
	
	/** The Constant ACCOUNT_TMFLAG. */
	private static final String ACCOUNT_TMFLAG = "AccountTMFlag";
	
	/** The Constant CONTACT_COMPLIANCE_STATUS. */
	private static final String CONTACT_COMPLIANCE_STATUS = "ContactComplianceStatus";
	
	/** The Constant VERSION. */
	private static final String VERSION = "Version";
	
	/** The Constant CONTACT_ID. */
	private static final String CONTACT_ID = "ContactID";
	
	/**
	 * The Constant
	 * ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS.
	 */
	private static final String ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS = "Error while updating compliance status updateComplianceStatus() ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#saveNewRegistration(org.springframework.
	 * messaging.Message)
	 */
	public Message<MessageContext> saveNewRegistration(Message<MessageContext> message) throws ComplianceException {
		try {
			RegistrationServiceRequest request = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			OperationEnum registrationOperation = message.getPayload().getGatewayMessageExchange().getOperation();

			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			switch (registrationOperation) {
			case NEW_REGISTRATION:
				saveNewRegisration(request, token.getUserID());
				break;
			case ADD_CONTACT:
				addContact(request, token.getUserID());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			LOG.error("Error in AbstractRegistrationDBService saveOrUpdateRegistration()", e);
			message.getPayload().setFailed(true);
		}
		return message;

	}

	/**
	 * Adds the contact.
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return true, if successful
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private boolean addContact(RegistrationServiceRequest request, Integer user) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		try {
			beginTransaction(connection);
			Integer accountId = getAccountIdByCrmAccountId(request.getAccount().getAccSFID());
			request.getAccount().setId(accountId);
			saveIntoContact(connection, request.getAccount().getContacts(), accountId, request.getOrgCode(), user);
			saveIntoContactAttribute(connection, request.getAccount().getContacts(), user);
			commitTransaction(connection);
			return true;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Save into contact attribute.
	 *
	 * @param connection
	 *            the connection
	 * @param contacts
	 *            the contacts
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoContactAttribute(Connection connection, List<Contact> contacts, Integer user)
			throws ComplianceException {
		for (Contact contact : contacts) {
			String contactJson = JsonConverterUtil.convertToJsonWithoutNull(contact);
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_CONTACT_ATTRIBUTE.getQuery());
				statement.setInt(1, contact.getId());
				statement.setString(2, contactJson);
				statement.setInt(3, user);
				statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				statement.setInt(5, user);
				statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				statement.setInt(7, contact.getVersion());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(statement);
			}
		}
	}

	/**
	 * Save new regisration.
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return true, if successful
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	/*
	 * Get Account/contact (contacts can be multiple) from registration or input
	 * method parameter save into contact attribute (in json format) save into
	 * Account attribute(in json format) save into accounts table save into
	 * contact save all generated keys into MessageContext do all in one
	 * transaction
	 */
	private boolean saveNewRegisration(RegistrationServiceRequest request, Integer user) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);

		try {
			beginTransaction(connection);
			Integer accountId = saveIntoAccount(connection, request.getAccount(), request.getOrgCode(), user);
			saveIntoAccountAttribute(connection, request.getAccount(), accountId, user);
			saveIntoContact(connection, request.getAccount().getContacts(), accountId, request.getOrgCode(), user);
			saveIntoContactAttribute(connection, request.getAccount().getContacts(), user);
			commitTransaction(connection);

			return true;
		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Save into contact.
	 *
	 * @param connection
	 *            the connection
	 * @param contacts
	 *            the contacts
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
	private boolean saveIntoContact(Connection connection, List<Contact> contacts, int accountId, String orgCode,
			Integer user) throws ComplianceException {
		if (contacts != null && !contacts.isEmpty()) {
			PreparedStatement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_CONTACT.getQuery(),
						Statement.RETURN_GENERATED_KEYS);
				for (Contact contact : contacts) {

					rs = setValueToContact(accountId, orgCode, user, statement, contact);
				}
				return true;
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(statement);

			}
		}

		return false;
	}

	/**
	 * Sets the value to contact.
	 *
	 * @param accountId
	 *            the account id
	 * @param orgCode
	 *            the org code
	 * @param user
	 *            the user
	 * @param statement
	 *            the statement
	 * @param contact
	 *            the contact
	 * @return the result set
	 * @throws SQLException
	 *             the SQL exception
	 */
	private ResultSet setValueToContact(int accountId, String orgCode, Integer user, PreparedStatement statement,
			Contact contact) throws SQLException {
		ResultSet rs;
		statement.setString(1, orgCode);
		statement.setInt(2, accountId);
		statement.setString(3, contact.getFirstName() + " " + contact.getLastName());
		statement.setString(4, contact.getContactSFID());
		statement.setInt(5, contact.getTradeContactID());
		statement.setBoolean(6, contact.getPrimaryContact());
		statement.setString(7, ContactComplianceStatus.NOT_PERFORMED.name());
		statement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
		Calendar c = Calendar.getInstance();
		c.setTime(new Timestamp(System.currentTimeMillis()));
		c.add(Calendar.YEAR, 3);
		statement.setTimestamp(9, new Timestamp(c.getTimeInMillis()));
		statement.setInt(10, user);
		statement.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
		statement.setInt(12, user);
		statement.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
		statement.setInt(14, contact.getVersion());
		statement.setString(15, contact.getCountry());
		statement.setInt(16, ServiceStatus.getStatusAsInteger(contact.getPreviousKycStatus()));
		statement.setInt(17, ServiceStatus.getStatusAsInteger(contact.getPreviousFraugsterStatus()));
		statement.setInt(18, ServiceStatus.getStatusAsInteger(contact.getPreviousSanctionStatus()));
		statement.setInt(19, ServiceStatus.getStatusAsInteger(contact.getPreviousBlacklistStatus()));
		statement.setInt(20, ServiceStatus.getStatusAsInteger(contact.getPreviousCountryGlobalCheckStatus()));
		statement.setInt(21, ServiceStatus.getStatusAsInteger(contact.getPreviousPaymentinWatchlistStatus()));//Added for AT-2986
		statement.setInt(22, ServiceStatus.getStatusAsInteger(contact.getPreviousPaymentoutWatchlistStatus()));//Added for AT-2986
		statement.setBoolean(23, false);
		// set STP Flag
		statement.setBoolean(24, false);
		statement.executeUpdate();
		rs = statement.getGeneratedKeys();

		if (rs.next()) {
			contact.setId(rs.getInt(1));
		}
		return rs;
	}

	/**
	 * Save into account attribute.
	 *
	 * @param connection
	 *            the connection
	 * @param account
	 *            the account
	 * @param accountId
	 *            the account id
	 * @param user
	 *            the user
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer saveIntoAccountAttribute(Connection connection, Account account, Integer accountId, Integer user)
			throws ComplianceException {
		List<Contact> contacts = account.getContacts();
		account.setContacts(null);
		String accountJson = JsonConverterUtil.convertToJsonWithoutNull(account);
		account.setContacts(contacts);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_ACCOUNT_ATTRIBUTE.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, accountId);
			statement.setString(2, accountJson);
			statement.setInt(3, user);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.setInt(5, user);
			statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			statement.setInt(7, account.getVersion());
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);

		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#updateComplianceStatus(org.springframework.
	 * messaging.Message)
	 */
	@Override
	public Message<MessageContext> updateComplianceStatus(Message<MessageContext> signUpMessage) {
		Connection signUpConnection = null;
		try {
			RegistrationResponse signUpResponse = (RegistrationResponse) signUpMessage.getPayload()
					.getGatewayMessageExchange().getResponse();

			RegistrationServiceRequest signUpRequest = (RegistrationServiceRequest) signUpMessage.getPayload()
					.getGatewayMessageExchange().getRequest();
			signUpRequest.getAccount().setAccountStatus(signUpResponse.getAccount().getAcs().name());

			Integer accountId = signUpRequest.getAccount().getId();

			UserProfile token = (UserProfile) signUpMessage.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			signUpConnection = getConnection(Boolean.FALSE);
			Boolean isContactModified = (Boolean) signUpRequest.getAdditionalAttribute("isContactModified");
			isContactModified = isContactModified == null ? Boolean.FALSE : isContactModified;

			beginTransaction(signUpConnection);
			OperationEnum registrationOperation = signUpMessage.getPayload().getGatewayMessageExchange().getOperation();
			updateAccountComplianceStatus(signUpConnection, signUpResponse, token.getUserID(),
					signUpRequest.getAccount().getCustType(), registrationOperation);

			Account signUpAccount = null;
			if (registrationOperation == OperationEnum.ADD_CONTACT) {
				signUpAccount = getAccountFromAccountAttribute(signUpRequest.getAccount().getId());
			} else {
				signUpAccount = (Account) ObjectCloner.deepCopy(signUpRequest.getAccount());
			}
			signUpAccount.setAccountStatus(signUpResponse.getAccount().getAcs().name());
			signUpAccount.setId(accountId);
			if (Boolean.TRUE.equals(isContactModified)) {
				List<Contact> contactList = updateContactAttributesFields(signUpRequest, signUpResponse);
				updateContactAttributes(contactList);
				// Clone request object, remove contacts so that does not get
				// serialized to DB
				// also update Account status, so next update will have it
				// readily
				// available
				updateContactComplianceStatus(signUpConnection, signUpResponse, token.getUserID(), signUpRequest,
						signUpRequest.getAccount().getCustType());
				saveIntoContactComplianceHistory(signUpConnection, signUpResponse, token.getUserID());
				saveIntoProfileStatusReason(signUpConnection, signUpResponse, signUpAccount.getId(),
						signUpRequest.getOrgCode(), token.getUserID());
			}
			signUpAccount.getContacts().clear();
			updateIntoAccountAttributeStatus(signUpConnection, signUpAccount, token.getUserID());
			saveIntoAccountComplianceHistory(signUpConnection, signUpResponse, token.getUserID());

			commitTransaction(signUpConnection);
		} catch (Exception e) {
			signUpMessage.getPayload().setFailed(true);
			LOG.error(ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS, e);
		} finally {
			try {
				closeConnection(signUpConnection);
			} catch (ComplianceException e) {
				signUpMessage.getPayload().setFailed(true);
				LOG.error(ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS, e);
			}
		}
		return signUpMessage;
	}

	/**
	 * Update contact attributes fields.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the list
	 */
	private List<Contact> updateContactAttributesFields(RegistrationServiceRequest request,
			RegistrationResponse response) {
		List<Contact> contactList = new ArrayList<>();
		if (response.getAccount().getContacts() != null) {
			for (ComplianceContact contactResponse : response.getAccount().getContacts()) {
				for (Contact req : request.getAccount().getContacts()) {
					setContactStatusToRequest(contactList, contactResponse, req);
				}
			}
		}
		return contactList;
	}

	/**
	 * Update contact compliance status.
	 *
	 * @param conactConn
	 *            the conact conn
	 * @param response
	 *            the response
	 * @param user
	 *            the user
	 * @param custType
	 *            the cust type
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateContactComplianceStatus(Connection conactConn, RegistrationResponse response, Integer user, RegistrationServiceRequest signUpRequest,
			String custType) throws ComplianceException {
		PreparedStatement contactStatement = null;
		Boolean isOnQueue = Boolean.FALSE;
		String legalEntity = signUpRequest.getAccount().getCustLegalEntity();
		
		for (ComplianceContact contact : response.getAccount().getContacts()) {
			
			try {
				Boolean kycReason = getKYCReasonCodeStatus(contact);
				
				if (custType.equalsIgnoreCase(CustomerTypeEnum.PFX.name())
						&& contact.getCcs().name().equals(ContactComplianceStatus.INACTIVE.name())
						&& Boolean.FALSE.equals(kycReason)) {
					isOnQueue = Boolean.TRUE;
				}
				//Add for AT-3398(Sub task AT-3491)
				if(LegalEntityEnum.isEULegalEntity(legalEntity) && contact.getBlacklistStatus().equalsIgnoreCase("FAIL")){
					isOnQueue = Boolean.TRUE;
				}
				
				contactStatement = conactConn
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_COMPLIANCE_STATUS_STP.getQuery());
				contactStatement.setString(1, contact.getCcs().name());
				contactStatement.setInt(2, user);
				contactStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStatement.setInt(4, ServiceStatus.getStatusAsInteger(contact.getKycStatus()));
				contactStatement.setInt(5, ServiceStatus.getStatusAsInteger(contact.getFraugsterStatus()));
				contactStatement.setInt(6, ServiceStatus.getStatusAsInteger(contact.getSanctionStatus()));
				contactStatement.setInt(7, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStatement.setInt(8, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStatement.setBoolean(9, isOnQueue);
				// set STP Flag
				contactStatement.setBoolean(10, contact.getSTPFlag());
				// AT-1549
				// set POI Needed flag, which would be used for 
				// Onfido updates
				Boolean poiNeeded = (ComplianceReasonCode.KYC_POI==contact.getCrc());
				contactStatement.setBoolean(11, poiNeeded);
				//changes for AT-2986
				if (custType.equalsIgnoreCase(CustomerTypeEnum.PFX.name())){
						WatchListDetails watchlistdet = getPaymentWatchListReasons(contact);
						processWatchList(contact, watchlistdet);
				}
				contactStatement.setInt(12, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStatement.setInt(13, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStatement.setInt(14, contact.getId());
				contactStatement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(contactStatement);
			}
		}
	}

	private WatchListDetails getPaymentWatchListReasons(ComplianceContact contact)
			throws ComplianceException {
		Connection readOnlyConn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		WatchListDetails watchlist = new WatchListDetails();
		
		
		String query = DBQueryConstant.GET_WATCHLIST_REASON_LIST_FOR_PAYMENTOUT_AND_PAYMENTIN_WITH_CON.getQuery();
		try {
			readOnlyConn = getConnection(Boolean.TRUE);

			// the query check for new reasons added as well as old contacts on
			// watchlist
			// and inclusive of all watchlist reason, if anything stops payment
			// In and Out
			// also highet risk category
			preparedStatement = readOnlyConn.prepareStatement(query);
			preparedStatement.setInt(1, contact.getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				watchlist.setStopPaymentIn(resultSet.getBoolean("StopPaymentIn"));
				watchlist.setStopPaymentOut(resultSet.getBoolean("StopPaymentOut"));
				watchlist.setCategory(resultSet.getInt("Category"));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(readOnlyConn);
		}
		return watchlist;
	}
	
	private void processWatchList(ComplianceContact contact, WatchListDetails watchDetails) {
		String paymentInwatchlistStatus = ServiceStatus.PASS.name();
		String paymentOutwatchlistStatus = ServiceStatus.PASS.name();

		if (watchDetails.isStopPaymentIn() && watchDetails.getCategory().equals(1))
			paymentInwatchlistStatus = ServiceStatus.FAIL.name();
		if (watchDetails.isStopPaymentIn() && watchDetails.getCategory().equals(2))
			paymentInwatchlistStatus = ServiceStatus.WATCH_LIST.name();

		if (watchDetails.isStopPaymentOut() && watchDetails.getCategory().equals(1))
			paymentOutwatchlistStatus = ServiceStatus.FAIL.name();
		if (watchDetails.isStopPaymentOut() && watchDetails.getCategory().equals(2))
			paymentOutwatchlistStatus = ServiceStatus.WATCH_LIST.name();

		contact.setPaymentinWatchlistStatus(paymentInwatchlistStatus);
		contact.setPaymentoutWatchlistStatus(paymentOutwatchlistStatus);
	}
	
	private Boolean getKYCReasonCodeStatus(ComplianceContact contact) {
		return null != contact.getCrc() && (contact.getCrc()==ComplianceReasonCode.KYC
				|| contact.getCrc()==ComplianceReasonCode.KYC_NA 
				|| contact.getCrc()==ComplianceReasonCode.KYC_POA
				|| contact.getCrc()==ComplianceReasonCode.KYC_POI);
	}

	private Boolean setstatus(Boolean accountStatusInActive, String customerType)
	{
		  Boolean status;
		if (Boolean.TRUE.equals(accountStatusInActive) && customerType.equalsIgnoreCase(CustomerTypeEnum.CFX.name())) {
			status=Boolean.TRUE;
			return status;
		} else {
			status= Boolean.FALSE;
			return status;
	    }	
	}
	
	/**
	 * Update account compliance status.
	 *
	 * @param connection
	 *            the connection
	 * @param signUpResponse
	 *            the response
	 * @param user
	 *            the user
	 * @param customerType
	 *            the customer type
	 * @param registrationOperation
	 *            the registration operation
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateAccountComplianceStatus(Connection connection, RegistrationResponse signUpResponse, Integer user,
			String customerType, OperationEnum registrationOperation) throws ComplianceException {
		PreparedStatement signUpStmt = null;
		Boolean accountStatusInActive = Boolean.FALSE;

		try {
			ComplianceAccount signUpAccount = signUpResponse.getAccount();
			accountStatusInActive = signUpAccount.getAcs().name().equals(ContactComplianceStatus.INACTIVE.name());
			if (registrationOperation==OperationEnum.ADD_CONTACT)
				signUpStmt = connection.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_COMPLIANCE_STATUS.getQuery());
			else
				signUpStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_COMPLIANCE_STATUS_STP.getQuery());

			signUpStmt.setString(1, signUpAccount.getAcs().name());
			signUpStmt.setString(2, signUpAccount.getAcs().name());
			signUpStmt.setInt(3, user);
			signUpStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			signUpStmt.setInt(5, ServiceStatus.getStatusAsInteger(signUpAccount.getFraugsterStatus()));
			signUpStmt.setInt(6, ServiceStatus.getStatusAsInteger(signUpAccount.getBlacklistStatus()));
			signUpStmt.setInt(7, ServiceStatus.getStatusAsInteger(signUpAccount.getPaymentinWatchlistStatus()));
			signUpStmt.setInt(8, ServiceStatus.getStatusAsInteger(signUpAccount.getPaymentoutWatchlistStatus()));

			if (null != signUpAccount.getRegisteredDate()) {
				signUpStmt.setTimestamp(9, signUpAccount.getRegisteredDate());
				signUpStmt.setTimestamp(10, signUpAccount.getExpiryDate());
			} else {
				signUpStmt.setNull(9, Types.TIMESTAMP);
				signUpStmt.setNull(10, Types.TIMESTAMP);
			}

			/**
			 * Added new column(osOnQueue) to show inactive CFX account on Reg
			 * queue - By Abhijit G.
			 */
			
			signUpStmt.setBoolean(11, setstatus(accountStatusInActive, customerType));
			
			signUpStmt.setInt(12, ServiceStatus.getStatusAsInteger(signUpAccount.getKycStatus()));
			signUpStmt.setInt(13, ServiceStatus.getStatusAsInteger(signUpAccount.getSanctionStatus()));

			// STP Flag is set if request is Signup
			if (registrationOperation==OperationEnum.NEW_REGISTRATION) {
				signUpStmt.setBoolean(14, signUpAccount.getSTPFlag());
				signUpStmt.setInt(15, signUpResponse.getAccount().getId());
					 
			} else {
				signUpStmt.setInt(14, signUpResponse.getAccount().getId());
			}

			signUpStmt.executeUpdate();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(signUpStmt);
		}
	}

	/**
	 * Save into account.
	 *
	 * @param connection
	 *            the connection
	 * @param account
	 *            the account
	 * @param orgCode
	 *            the org code
	 * @param user
	 *            the user
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer saveIntoAccount(Connection connection, Account account, String orgCode, Integer user)
			throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			// CFX and CFX (Etailer) are treated as CFX
			// while saving in account table, Cust Type should be saved as
			// 1:CFX, 2:PFX, 3:CFX(Etailer)
			Integer custType = getCustomerType(account);
			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_ACCOUNT.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, orgCode);
			statement.setString(2, account.getAccountName());
			statement.setString(3, account.getAccSFID());
			statement.setInt(4, account.getTradeAccountID());// TradeAccountId
			statement.setString(5, account.getTradeAccountNumber());// TradeAccountNumber
			statement.setInt(6, custType);
			statement.setString(7, ContactComplianceStatus.IN_QUEUE.name());
			statement.setInt(8, 1);
			statement.setNull(9, Types.TIMESTAMP);
			statement.setNull(10, Types.TIMESTAMP);
			statement.setInt(11, user);

			statement.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
			statement.setInt(13, user);
			statement.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
			statement.setInt(15, account.getVersion());
			statement.setInt(16, ServiceStatus.getStatusAsInteger(account.getPreviousKycStatus()));
			statement.setInt(17, ServiceStatus.getStatusAsInteger(account.getPreviousFraugsterStatus()));
			statement.setInt(18, ServiceStatus.getStatusAsInteger(account.getPreviousSanctionStatus()));
			statement.setInt(19, ServiceStatus.getStatusAsInteger(account.getPreviousBlacklistStatus()));
			statement.setInt(20, ServiceStatus.getStatusAsInteger(account.getPreviousPaymentinWatchlistStatus()));
			statement.setInt(21, ServiceStatus.getStatusAsInteger(account.getPreviousPaymentoutWatchlistStatus()));
			if (StringUtils.isNullOrEmpty(account.getCustLegalEntity())) {
				statement.setNull(22, Types.NVARCHAR);
			} else {
				statement.setString(22, account.getCustLegalEntity());
			}
			statement.setBoolean(23, false);
			statement.setInt(24, 0);
			statement.setString(25, account.getCustLegalEntity());
			statement.setInt(26, 5);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				account.setId(rs.getInt(1));
				return rs.getInt(1);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);

		}

		return null;
	}

	/**
	 * Gets the first fraugster summary.
	 *
	 * @param entityId
	 *            the entity id
	 * @param entityType
	 *            the entity type
	 * @return the first fraugster summary
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FraugsterSummary getFirstFraugsterSummary(Integer entityId, String entityType) throws ComplianceException {
		Connection fraugsterSummaryConn = null;
		PreparedStatement fraugsterSummaryStatment = null;
		ResultSet fraugsterSummaryRS = null;
		FraugsterSummary fraugsterSummary = null;
		try {
			fraugsterSummaryConn = getConnection(Boolean.TRUE);
			fraugsterSummaryStatment = fraugsterSummaryConn
					.prepareStatement(DBQueryConstant.GET_FRAUGSTER_FIRST_SUMMARY.getQuery());
			fraugsterSummaryStatment.setInt(1, EntityEnum.getEntityTypeAsInteger(entityType));
			fraugsterSummaryStatment.setString(2, "" + entityId);
			fraugsterSummaryRS = fraugsterSummaryStatment.executeQuery();
			if (fraugsterSummaryRS.next()) {
				fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
						fraugsterSummaryRS.getString("Summary"));
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(fraugsterSummaryRS);
			closePrepareStatement(fraugsterSummaryStatment);
			closeConnection(fraugsterSummaryConn);
		}
		return fraugsterSummary;
	}

	/**
	 * Gets the service failed registration details.
	 *
	 * @param accountId
	 *            the account id
	 * @param customerType
	 *            the customer type
	 * @return the service failed registration details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public RegistrationServiceRequest getServiceFailedRegistrationDetails(Integer accountId, String customerType, Integer contactId)
			throws ComplianceException { // Add contact id in parameter for AT-4289
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Account account = new Account();
		List<Contact> contact = new ArrayList<>();
		RegistrationServiceRequest registrationDetails = new RegistrationServiceRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			if (CustomerTypeEnum.PFX.name().equals(customerType)) {
				preparedStatement = connection
						.prepareStatement(DBQueryConstant.GET_DETAILS_FOR_SERVICE_FAILED_REG_PFX.getQuery());
				preparedStatement.setInt(1, accountId);
				preparedStatement.setInt(2, contactId); // AT-4289
			} else {
				preparedStatement = connection
						.prepareStatement(DBQueryConstant.GET_DETAILS_FOR_SERVICE_FAILED_REG_CFX.getQuery());
				preparedStatement.setInt(1, accountId);
				preparedStatement.setInt(2, accountId);
			}
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String accountJson = resultSet.getString("AccountAttrib");
				if (null != accountJson) {
					account = JsonConverterUtil.convertToObject(Account.class, resultSet.getString("AccountAttrib"));
				}
				Contact con = JsonConverterUtil.convertToObject(Contact.class, resultSet.getString("ContactAttrib"));
				con.setId(resultSet.getInt(CONTACTID));
				con.setContactStatus(resultSet.getString((CONTACT_COMPLIANCE_STATUS)));
				if (!CustomerTypeEnum.PFX.name().equals(customerType)) { // AT-5487
					con.setPreviousFraugsterStatus(ServiceStatus.getStatusAsString(resultSet.getInt("ContactFraugsterStatus")));
					con.setPreviousBlacklistStatus(ServiceStatus.getStatusAsString(resultSet.getInt("ContactBlacklistStatus")));
				}
				contact.add(con);
				account.setId(resultSet.getInt("accountId"));
				account.setTradeAccountNumber(resultSet.getString("TradeAccNo"));
				account.setAccountStatus(resultSet.getString("AccountComplianceStatus"));
				account.setContacts(contact);
				registrationDetails.setOrgCode(resultSet.getString("OrgCode"));
				registrationDetails.setOrgId(resultSet.getInt("OrgId"));
				registrationDetails.addAttribute(ACCOUNT, account);
				registrationDetails.addAttribute("contact", con);
				registrationDetails.setAccount(account);
				registrationDetails.addAttribute(Constants.FRAUGSTER_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("FraugsterStatus")));
				if (!CustomerTypeEnum.PFX.name().equals(customerType)) {
					registrationDetails.addAttribute("FRAUGSTER_STATUS_CONTACT",
							ServiceStatus.getStatusAsString(resultSet.getInt("ContactFraugsterStatus")));
					registrationDetails.addAttribute("BLACKLIST_STATUS_CONTACT",
							ServiceStatus.getStatusAsString(resultSet.getInt("ContactBlacklistStatus")));
				}
				registrationDetails.addAttribute(Constants.BLACKLIST_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("BlacklistStatus")));
				registrationDetails.addAttribute(Constants.SANCTION_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("SanctionStatus")));
				registrationDetails.addAttribute(Constants.EID_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("EIDStatus")));
				registrationDetails.addAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("PayInWatchListStatus")));
				registrationDetails.addAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("PayOutWatchListStatus")));
				registrationDetails.addAttribute("EventID", resultSet.getInt("EventID"));
				registrationDetails.addAttribute(ACCOUNT_TMFLAG, resultSet.getInt(ACCOUNT_TMFLAG)); //AT-4288
				registrationDetails.addAttribute("OldContactStatus", resultSet.getString(CONTACT_COMPLIANCE_STATUS)); //AT-5396
				registrationDetails.addAttribute("ContactIdForCFX", contactId); // AT-5487
			}

		} catch (Exception e) {
			LOG.error("Error while fetching service failed reg details :", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return registrationDetails;

	}

	/**
	 * Gets the service failed registration ESL details.
	 *
	 * @param eventId
	 *            the event id
	 * @param accId
	 *            the acc id
	 * @param serviceType
	 *            the service type
	 * @param registrationDetails
	 *            the registration details
	 * @param entityType
	 *            the entity type
	 * @return the service failed registration ESL details
	 * @throws SQLException 
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private EventServiceLog getEslForServiceFailureOfContact(Integer eventId, Integer accId,
			ServiceTypeEnum serviceType, RegistrationServiceRequest registrationDetails,Connection conn) throws SQLException
	{
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		EventServiceLog eventServiceLog = new EventServiceLog();
		for (Contact con : registrationDetails.getAccount().getContacts()) {

			String query = "";
			query = DBQueryConstant.GET_ESL_FOR_SERVICE_FAILURE_OF_CONTACT.getQuery();

			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, serviceType.getShortName());
			//preparedStatment.setInt(2, eventId); // remove eventId - AT-4289
			preparedStatment.setInt(2, accId);
			preparedStatment.setInt(3, con.getId());
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				eventServiceLog.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(ENTITY_TYPE)));
				eventServiceLog.setServiceType(rs.getInt("ServiceType"));
				eventServiceLog.setProviderResponse(rs.getString("ProviderResponse"));
				eventServiceLog.setStatus(ServiceStatus.getStatusAsString(rs.getInt("Status")));
				eventServiceLog.setSummary(rs.getString("summary"));
				eventServiceLog.setEntityId(rs.getInt("EntityID"));
				eventServiceLog.setEntityVersion(rs.getInt("EntityVersion"));
				eventServiceLog.setEventId(rs.getInt("eventID"));
			}

		}
		return eventServiceLog;
	}
	private EventServiceLog getEslServiceFailureContactAccount(Integer eventId, Integer accId,
			ServiceTypeEnum serviceType, RegistrationServiceRequest registrationDetails, Integer entityType,Connection conn) throws SQLException 
	{
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		EventServiceLog eventServiceLog = new EventServiceLog();
		Integer contactId = (Integer) registrationDetails.getAdditionalAttribute("contactId");
		String query = "";
		if (EntityEnum.CONTACT.getEntityTypeAsInteger().equals(entityType)) {
			query = DBQueryConstant.GET_ESL_FOR_SERVICE_FAILURE_OF_CONTACT.getQuery();
		} else {
			query = DBQueryConstant.GET_ESL_FOR_SERVICE_FAILURE_OF_ACCOUNT.getQuery();
		}

		preparedStatment = conn.prepareStatement(query);
		preparedStatment.setString(1, serviceType.getShortName());
		//preparedStatment.setInt(2, eventId); // remove eventId - AT-4289
		preparedStatment.setInt(2, accId);
		if (EntityEnum.CONTACT.getEntityTypeAsInteger().equals(entityType))
			preparedStatment.setInt(3, contactId);
		rs = preparedStatment.executeQuery();
		while (rs.next()) {
			eventServiceLog.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(ENTITY_TYPE)));
			eventServiceLog.setServiceType(rs.getInt("ServiceType"));
			eventServiceLog.setProviderResponse(rs.getString("ProviderResponse"));
			eventServiceLog.setStatus(ServiceStatus.getStatusAsString(rs.getInt("Status")));
			eventServiceLog.setSummary(rs.getString("summary"));
			eventServiceLog.setEntityId(rs.getInt("EntityID"));
			eventServiceLog.setEntityVersion(rs.getInt("EntityVersion"));
			eventServiceLog.setEventId(rs.getInt("eventID"));
		}
		return eventServiceLog;
	}
	
	public EventServiceLog getServiceFailedRegistrationESLDetails(Integer eventId, Integer accId,
			ServiceTypeEnum serviceType, RegistrationServiceRequest registrationDetails, Integer entityType)
			throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		EventServiceLog eventServiceLog = new EventServiceLog();
		Integer contactId = (Integer) registrationDetails.getAdditionalAttribute("contactId");
		try {
			conn = getConnection(Boolean.TRUE);
			if (EntityEnum.CONTACT.getEntityTypeAsInteger().equals(entityType) && null == contactId) {
				eventServiceLog=getEslForServiceFailureOfContact(eventId, accId, serviceType, registrationDetails, conn);
			} else {
				eventServiceLog=getEslServiceFailureContactAccount(eventId, accId, serviceType, registrationDetails, entityType, conn);
			}
		} catch (Exception e) {
			LOG.error("Error while fetching eventservicelog details :", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return eventServiceLog;

	}
	/**
	 * Gets the account details for service failed registration details.
	 *
	 * @param transId
	 *            the trans id
	 * @param string
	 *            the string
	 * @return the account details for service failed registration details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public RegistrationServiceRequest getAccountDetailsForServiceFailedRegistrationDetails(Integer transId,
			String string) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		RegistrationServiceRequest registrationDetails = new RegistrationServiceRequest();
		Account acc = new Account();
		try {
			conn = getConnection(Boolean.TRUE);
			String query = "";
			if (string.equals(TransactionTypeEnum.ACCOUNT.getTransactionTypeAsString()))
				query = DBQueryConstant.GET_ACCOUNT_DETAILS_FROM_ACCOUNTID.getQuery();
			else
				query = DBQueryConstant.GET_ACCOUNT_DETAILS_FROM_CONTACTID.getQuery();

			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setInt(1, transId);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				acc.setId(rs.getInt("AccountId"));
				acc.setCustType(CustomerTypeEnum.getCustumerTypeAsString(rs.getInt("type")));
				registrationDetails.setAccount(acc);
			}
		} catch (Exception e) {
			LOG.error("Error while fetching Account details : {1}", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return registrationDetails;

	}
	
	/**
	 * Gets the account contact details for status update.
	 *
	 * @param crmAccountId the crm account id
	 * @return the account contact details for status update
	 */
	public RegistrationServiceRequest getAccountDetailsForStatusUpdate(String crmAccountId) throws ComplianceException{
		RegistrationServiceRequest registrationServiceRequest = new RegistrationServiceRequest();
		Account acc = new Account();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection(Boolean.TRUE);
			String query = DBQueryConstant.GET_ACCOUNT_DETAILS_BY_ACC_SF_ID.getQuery();
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, crmAccountId);
			rs = preparedStatment.executeQuery();
			List<Contact> contacts = new ArrayList<>();
			int count=0;
			while (rs.next()) {
				if(count==0) {
					acc.setId(rs.getInt("AccountId"));
					acc.setAccountName(rs.getString("AccountName"));
					acc.setAccSFID(rs.getString("AccountSFId"));
					acc.setCustType(rs.getString("CustType"));
					acc.setTradeAccountID(rs.getInt("TradeAccountId"));
					acc.setAccountStatus(ContactComplianceStatus.getComplianceAsString(rs.getInt("AccountComplianceStatus")));
					acc.setVersion(rs.getInt(VERSION));
					registrationServiceRequest.addAttribute("OrganizationId", rs.getInt("OrgID"));
					registrationServiceRequest.addAttribute("AccountRegisteredDate", rs.getTimestamp("ARegisteredDate"));
					registrationServiceRequest.addAttribute("AccountRegistrationInDate", rs.getTimestamp("ARegistrationInDate"));
					registrationServiceRequest.addAttribute("AccountExpiryDate", rs.getTimestamp("AComplianceExpiry"));
				}
				Contact contact = new Contact();
				contact = JsonConverterUtil.convertToObject(Contact.class, rs.getString("ContactAttributes"));
				contact.setId(rs.getInt(CONTACT_ID));
				contact.setContactSFID(rs.getString("ContactSFID"));
				contact.setContactStatus(ContactComplianceStatus.getComplianceAsString(rs.getInt(CONTACT_COMPLIANCE_STATUS)));
				contact.setPreviousKycStatus(ServiceStatus.getStatusAsString(rs.getInt("EIDSTATUS")));
				contact.setPreviousSanctionStatus(ServiceStatus.getStatusAsString(rs.getInt("SanctionStatus")));
				contact.setPreviousFraugsterStatus(ServiceStatus.getStatusAsString(rs.getInt("FraugsterStatus")));
				contact.setPreviousBlacklistStatus(ServiceStatus.getStatusAsString(rs.getInt("BlacklistStatus")));
				contact.setPreviousCountryGlobalCheckStatus(ServiceStatus.getStatusAsString(rs.getInt("CustomCheckStatus")));
				contact.setPoiNeeded(rs.getBoolean("POI_NEEDED"));
				registrationServiceRequest.addAttribute("ContactRegisteredDate", rs.getTimestamp("CRegisteredDate"));
				registrationServiceRequest.addAttribute("ContactRegistrationInDate", rs.getTimestamp("CRegistrationInDate"));
				registrationServiceRequest.addAttribute("ContactExpiryDate", rs.getTimestamp("CComplianceExpiry"));
				contacts.add(contact);
				count++;
			}
			acc.setContacts(contacts);
			registrationServiceRequest.setAccount(acc);
		} catch (Exception e) {
			LOG.error("Error while fetching Account details in getAccountDetailsForStatusUpdate():", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}		
		return registrationServiceRequest;
	}

	public RegistrationServiceRequest getContatDetailsForDelete(DeleteContactRequest request) throws ComplianceException{
		RegistrationServiceRequest registrationServiceRequest = new RegistrationServiceRequest();
		Account acc = new Account();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		List<Contact> contacts = new ArrayList<>();
		Integer paymentInId = 0;
		Integer paymentOutId = 0;
		try {
			conn = getConnection(Boolean.TRUE);
			String query = DBQueryConstant.GET_CONTACT_DETAILS_FOR_DELETE.getQuery();
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, request.getOrgCode());
			preparedStatment.setString(2, request.getContactSfId());
			preparedStatment.setInt(3, request.getTradeContactId());
			preparedStatment.setString(4, request.getAccountSfId());
			preparedStatment.setInt(5, request.getTradeAccountId());
			rs = preparedStatment.executeQuery();
			if(rs.next()){
				Contact contact = new Contact();
				contact = JsonConverterUtil.convertToObject(Contact.class, rs.getString("ConAttrib"));
				contact.setId(rs.getInt(CONTACT_ID));
				contact.setContactSFID(rs.getString("CRMContactID"));
				contact.setTradeContactID(rs.getInt("TradeContactID"));
				acc.setAccSFID(rs.getString("CrmAccountID"));
				acc.setTradeAccountID(rs.getInt("TradeAccountID"));
				acc.setId(rs.getInt("AccountID"));
				acc.setVersion(rs.getInt(VERSION));
				acc.setCustType(CustomerTypeEnum.getCustumerTypeAsString(rs.getInt("custType")));
				paymentInId = rs.getInt("PaymentInId");
				paymentOutId = rs.getInt("PaymentOutId");
				contacts.add(contact);
				acc.setContacts(contacts);
				request.setOrgId(rs.getInt("OrgID"));
				request.addAttribute("contactID", acc.getContacts().get(0).getId());
			}
			registrationServiceRequest.setAccount(acc);
			if(paymentInId == 0 && paymentOutId == 0) {
				registrationServiceRequest.addAttribute("PaymentInitiated", Boolean.FALSE);
			} else {
				registrationServiceRequest.addAttribute("PaymentInitiated", Boolean.TRUE);
			}
			
			
		} catch (Exception e) {
			LOG.error("Error while fetching Account details in getContatDetailsForDelete():", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}	
		return registrationServiceRequest;
	}
	
	/**
	 * Save funds out delete request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> saveContactDeleteRequest(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {
			DeleteContactRequest deleteContactRequest = (DeleteContactRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			deleteContact(conn, deleteContactRequest, token.getUserID());
		} catch (Exception e) {
			LOG.error("", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}
		return message;
	}
	
	/**
	 * Delete contact.
	 *
	 * @param conn the conn
	 * @param cDeleteRequest the c delete request
	 * @param userID the user ID
	 * @throws ComplianceException the compliance exception
	 */
	private void deleteContact(Connection conn, DeleteContactRequest cDeleteRequest, Integer userID)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.DELETE_CONTACT.getQuery());
			preparedStatment.setInt(1, userID);
			preparedStatment.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(3, (Integer)cDeleteRequest.getAdditionalAttribute("contactID"));
			boolean deleted = preparedStatment.execute();
			cDeleteRequest.addAttribute("deletedSuccessfully", deleted);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	public RegistrationServiceRequest getAccountDetailsForIntuitionRegUpdateStatus(Integer accountId, Integer contactId, String custType) throws ComplianceException{
		RegistrationServiceRequest registrationServiceRequest = new RegistrationServiceRequest();
		Account acc = new Account();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			String query;
			if(custType.equalsIgnoreCase("PFX"))
				 query = DBQueryConstant.UPDATE_INTUITION_STATUS_PFX.getQuery();
			else
				 query = DBQueryConstant.UPDATE_INTUITION_STATUS_CFX.getQuery();
			
			preparedStatment = conn.prepareStatement(query);
			
			if(custType.equalsIgnoreCase("PFX"))
				preparedStatment.setInt(1, contactId);
			else
				preparedStatment.setInt(1, accountId);
			
			rs = preparedStatment.executeQuery();
			List<Contact> contacts = new ArrayList<>();
			int count=0;
			while (rs.next()) {
				if(count==0) {
					acc.setId(rs.getInt("ID"));
					acc.setTradeAccountID(rs.getInt("TradeAccountId"));
					acc.setTradeAccountNumber(rs.getString("TradeAccountNumber"));
					acc.setAccSFID(rs.getString("CRMAccountID"));
					acc.setRegistrationDateTime(rs.getString("ComplianceDoneOn"));   
					acc.setAccountStatus(rs.getString("AccountStatus"));
					acc.setVersion(rs.getInt(VERSION));
					registrationServiceRequest.addAttribute(ACCOUNT_TMFLAG, rs.getInt(ACCOUNT_TMFLAG));
					registrationServiceRequest.addAttribute("AccountRegisteredDate", rs.getString("regDateTime"));
					registrationServiceRequest.addAttribute("IntuitionRiskLevel", rs.getString("IntuitionRiskLevel"));
				}
				Contact contact = new Contact();
				contact.setId(rs.getInt(CONTACT_ID));
				contact.setContactSFID(rs.getString("CRMContactID")); 
				contact.setTradeContactID(rs.getInt("TradeContactID"));
				contact.setContactStatus(ContactComplianceStatus.getComplianceAsString(rs.getInt("ComplianceStatus")));
				contacts.add(contact);
				count++;
			}
			acc.setContacts(contacts);
			registrationServiceRequest.setAccount(acc);
		} catch (Exception e) {
			LOG.error("Error while fetching Account details getAccountDetailsForIntuitionRegUpdateStatus():", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}		
		return registrationServiceRequest;
	}
	
	/**
	 * Update account TM flag.
	 *
	 * @param signUpMessage the sign up message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> updateAccountTMFlag(Message<MessageContext> signUpMessage) throws ComplianceException {
		PreparedStatement signUpStmt = null;
		Connection connection = null;
		String accTMFlag = "";

		try {
			UserProfile token = (UserProfile) signUpMessage.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			RegistrationResponse signUpResponse = (RegistrationResponse) signUpMessage.getPayload()
					.getGatewayMessageExchange().getResponse();
			OperationEnum registrationOperation = signUpMessage.getPayload().getGatewayMessageExchange().getOperation();
			
			if (registrationOperation==OperationEnum.NEW_REGISTRATION && 
					signUpResponse.getAccount().getTransactionMonitoringStatus().equalsIgnoreCase("PASS")) {
					accTMFlag = "AccountTMFlag = 1,";
				}				

				connection = getConnection(Boolean.FALSE);
				signUpStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_ACC_TM_FLAG.getQuery().replace("#", accTMFlag));
				signUpStmt.setString(1, signUpResponse.getAccount().getIntuitionRiskLevel());
				signUpStmt.setInt(2, token.getUserID());	
				signUpStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				signUpStmt.setInt(4, signUpResponse.getAccount().getId());
				signUpStmt.executeUpdate();
			
				
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(signUpStmt);
			closeConnection(connection);
		}
		return signUpMessage;
	}
	
	public Integer getUserFromSSOUserID(String ssoUserID) throws ComplianceException {
		Connection conn = null;
		PreparedStatement userStatment = null;
		ResultSet resultSet = null;
		Integer userID = 1;
		try {
			conn = getConnection(Boolean.TRUE);
			userStatment = conn.prepareStatement(DBQueryConstant.GET_USER_ID_BY_SSOUSERID.getQuery());
			userStatment.setString(1, ssoUserID);
			resultSet = userStatment.executeQuery();
			if (resultSet.next()) {
				userID = resultSet.getInt(1);
			}
			return userID;
		} catch (Exception e) {
			LOG.error(Constants.ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(userStatment);
			closeConnection(conn);
		}
		return userID;
	}
	
 }