package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IUpdateRegistrationDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OnfidoStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.PropertyHandler;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class UpdateRegistrationDBServiceImpl.
 */
public class UpdateRegistrationDBServiceImpl extends AbstractRegistrationDBService
		implements IUpdateRegistrationDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(UpdateRegistrationDBServiceImpl.class);

	/** The Constant ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS. */
	private static final String ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS = "Error while updating compliance status updateComplianceStatus() ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#updateAccountDetails(org.springframework.messaging
	 * .Message)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Message<MessageContext> updateAccountDetails(Message<MessageContext> message) throws ComplianceException {
		RegistrationServiceRequest request = (RegistrationServiceRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();

		List<Contact> contactAttibutes = (List<Contact>) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		Account accountAttributes = (Account) request.getAdditionalAttribute("oldAccount");
		boolean isAccountModified = (boolean) request.getAdditionalAttribute("isAccountModified");
		String defaultStatus = ContactComplianceStatus.NOT_PERFORMED.name();
		Boolean doPerformCheck = (Boolean) request.getAdditionalAttribute(Constants.PERFORM_CHECKS);
		if (doPerformCheck != null && !doPerformCheck) {
			defaultStatus = accountAttributes.getAccountStatus();
		}
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Connection connection = getConnection(Boolean.FALSE);
		try {
			beginTransaction(connection);
			Integer accountId = getAccountIdByCrmAccountId(request.getAccount().getAccSFID());
			request.getAccount().setId(accountId);
			if (!request.getAccount().getContacts().isEmpty()) {
				updateIntoContact(request.getAccount().getContacts(), connection, token.getUserID());
				//For CDSA Migration AT-4474
				if(request.getEvent() != null && request.getOrgCode().equalsIgnoreCase("CD SA") && request.getEvent().equalsIgnoreCase("migrate_customer")) {
                    updateIntoContactLegacyNumber(request.getAccount().getContacts(), connection, token.getUserID());
				}
				updateIntoContactAttribute(connection, request.getAccount().getContacts(), token.getUserID());
				saveIntoContactAttributeHistory(connection, contactAttibutes, token.getUserID());
			}
			if (isAccountModified) {
				updateIntoAccount(connection, request, token.getUserID(), defaultStatus);
				//For CDSA Migration AT-4474
				if(request.getEvent() != null && request.getOrgCode().equalsIgnoreCase("CD SA") && request.getEvent().equalsIgnoreCase("migrate_customer")) {
                    updateIntoAccountLegacyNumber(connection, request, token.getUserID());
				}
				updateIntoAccountAttribute(connection, request.getAccount(), token.getUserID());
				saveIntoAccountAttributeHistory(connection, accountAttributes, token.getUserID());
			}	
			commitTransaction(connection);
			LOG.warn("\n -------Old Account details in update registration -------- \n  {}", JsonConverterUtil.convertToJsonWithoutNull(accountAttributes));
			LOG.warn("\n -------New Contact details in update registration -------- \n  {}", JsonConverterUtil.convertToJsonWithoutNull(request.getAccount().getContacts()));
		} catch (Exception e) {
			LOG.error("Error in updateAccountDetails {1}",e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(connection);
		}
		return message;
	}

	/**
	 * Update into contact.
	 *
	 * @param contacts
	 *            the contacts
	 * @param connection
	 *            the connection
	 * @param user
	 *            the user
	 * @return true, if successful
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private boolean updateIntoContact(List<Contact> contacts, Connection connection, Integer user)
			throws ComplianceException {

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_BY_ID.getQuery());
			for (Contact contact : contacts) {
				statement.setString(1, contact.getFullName());
				statement.setString(2, contact.getContactSFID());
				statement.setInt(3, contact.getTradeContactID());
				if (null != contact.getPrimaryContact())
					statement.setBoolean(4, contact.getPrimaryContact());
				else
					statement.setBoolean(4, true);
				statement.setInt(5, user);
				statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				statement.setInt(7, contact.getVersion());
				statement.setString(8, contact.getCountry());
				statement.setInt(9, contact.getId());
				statement.addBatch();
			}
			statement.executeBatch();
			return true;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}
	
	/**
	 * Update into contact legacy id.
	 *
	 * @param contacts the contacts
	 * @param connection the connection
	 * @param user the user
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	private boolean updateIntoContactLegacyNumber(List<Contact> contacts, Connection connection, Integer user)
            throws ComplianceException {

       PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_BY_ID_FOR_LEGACY_NUMBER.getQuery());
            for (Contact contact : contacts) {
                statement.setInt(1, user);
                statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                statement.setInt(3, contact.getLegacyTradeContactID());
                statement.setInt(4, contact.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        } catch (Exception e) {
            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
        } finally {
            closePrepareStatement(statement);
        }
    }

	/**
	 * Update into account.
	 *
	 * @param connection
	 *            the connection
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @param defaultStatus
	 *            the default status
	 * @return true, if successful
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private boolean updateIntoAccount(Connection connection, RegistrationServiceRequest request, Integer user,
			String defaultStatus) throws ComplianceException {

		PreparedStatement statement = null;
		Boolean accountStatusActive = Boolean.FALSE;
		try {
			Integer custType = getCustomerType(request.getAccount());
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_INTO_ACCOUNT.getQuery());
			statement.setString(1, request.getOrgCode());
			statement.setString(2, request.getAccount().getAccountName());
			statement.setString(3, request.getAccount().getAccSFID());
			statement.setInt(4, request.getAccount().getTradeAccountID());
			statement.setString(5, request.getAccount().getTradeAccountNumber());
			statement.setInt(6, custType);
			statement.setString(7, request.getAccount().getAccountStatus());
			statement.setString(8, defaultStatus);
			statement.setInt(9, user);
			statement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			statement.setInt(11, request.getAccount().getVersion());
			// Need to merge this code with migration branch
			if (StringUtils.isNullOrEmpty(request.getAccount().getCustLegalEntity()))
				statement.setNull(12, Types.NVARCHAR);
			else
				statement.setString(12, request.getAccount().getCustLegalEntity());
			accountStatusActive = request.getAccount().getAccountStatus().equals(ContactComplianceStatus.ACTIVE.name());
			if (Boolean.TRUE.equals(accountStatusActive)) {
				statement.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
				Calendar c = Calendar.getInstance();
				c.setTime(new Timestamp(System.currentTimeMillis()));
				c.add(Calendar.YEAR, 3);
				statement.setTimestamp(14, new Timestamp(c.getTimeInMillis()));
			} else {
				statement.setNull(13, Types.TIMESTAMP);
				statement.setNull(14, Types.TIMESTAMP);
			}
			statement.setString(15, request.getAccount().getCustLegalEntity());
			statement.setInt(16, request.getAccount().getId());
			
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}
	
	/**
	 * Update into account legacy number.
	 *
	 * @param connection the connection
	 * @param request the request
	 * @param user the user
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	private boolean updateIntoAccountLegacyNumber(Connection connection, RegistrationServiceRequest request, Integer user
            ) throws ComplianceException {

       PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DBQueryConstant.UPDATE_INTO_ACCOUNT_FOR_LEGACY_NUMBER.getQuery());
            statement.setInt(1, user);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setString(3, request.getAccount().getLegacyTradeAccountNumber());
            statement.setInt(4, request.getAccount().getLegacyTradeAccountID());
            statement.setInt(5, request.getAccount().getId());
            
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
        } finally {
            closePrepareStatement(statement);
        }
    }

	/**
	 * Update into account attribute.
	 *
	 * @param connection
	 *            the connection
	 * @param acc
	 *            the acc
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateIntoAccountAttribute(Connection connection, Account acc, Integer user)
			throws ComplianceException {
		List<Contact> contacts = acc.getContacts();
		acc.setContacts(null);
		String objJson = JsonConverterUtil.convertToJsonWithoutNull(acc);
		acc.setContacts(contacts);
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_INTO_ACCOUNT_ATTRIBUTE.getQuery());
			statement.setString(1, objJson);
			statement.setInt(2, user);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, acc.getVersion());
			statement.setInt(5, acc.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

	/**
	 * Save into account attribute history.
	 *
	 * @param connection
	 *            the connection
	 * @param acc
	 *            the acc
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoAccountAttributeHistory(Connection connection, Account acc, Integer user)
			throws ComplianceException {
		List<Contact> contacts = acc.getContacts();
		acc.setContacts(null);
		String objJson = JsonConverterUtil.convertToJsonWithoutNull(acc);
		acc.setContacts(contacts);
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_ACCOUNT_ATTRIBUTE_HISTORY.getQuery());
			statement.setInt(1, acc.getId());
			statement.setString(2, objJson);
			statement.setInt(3, user);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.setInt(5, acc.getVersion());
			int count = statement.executeUpdate();
			if (count <= 0) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

	/**
	 * Update into contact attribute.
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
	private void updateIntoContactAttribute(Connection connection, List<Contact> contacts, Integer user)
			throws ComplianceException {
		for (Contact contact : contacts) {
			String contactJson = JsonConverterUtil.convertToJsonWithoutNull(contact);
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.UPDATE_INTO_CONTACT_ATTRIBUTE.getQuery());
				statement.setString(1, contactJson);
				statement.setInt(2, user);
				statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				statement.setInt(4, contact.getVersion());
				statement.setInt(5, contact.getId());

				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(statement);
			}
		}
	}

	/**
	 * Save into contact attribute history.
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
	private void saveIntoContactAttributeHistory(Connection connection, List<Contact> contacts, Integer user)
			throws ComplianceException {
		for (Contact contact : contacts) {
			String contactJson = JsonConverterUtil.convertToJsonWithoutNull(contact);
			PreparedStatement statement = null;
			try {
				statement = connection
						.prepareStatement(DBQueryConstant.INSERT_INTO_CONTACT_ATTRIBUTE_HISTORY.getQuery());
				statement.setInt(1, contact.getId());
				statement.setString(2, contactJson);
				statement.setInt(3, user);
				statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				statement.setInt(5, contact.getVersion());
				int count = statement.executeUpdate();
				if (count <= 0) {
					throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR);
				}
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(statement);
			}
		}
	}

	/**
	 * Update compliance status for reg update.
	 *
	 * @param updMessage
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> updateComplianceStatusForRegUpdate(Message<MessageContext> updMessage) {
		Connection updConnection = null;
		try {
			
			//AT-5396
			Boolean activeFlag = Boolean.parseBoolean(System.getProperty("persistActiveAccountStatusPostBRC"));
			//AT-5395
			Boolean inactiveFlag = Boolean.parseBoolean(System.getProperty("persistInactiveAccountStatusPostBRC"));
			
			RegistrationResponse updateResponse = (RegistrationResponse) updMessage.getPayload()
					.getGatewayMessageExchange().getResponse();

			RegistrationServiceRequest updateRequest = (RegistrationServiceRequest) updMessage.getPayload()
					.getGatewayMessageExchange().getRequest();
			updateRequest.getAccount().setAccountStatus(updateResponse.getAccount().getAcs().name());

			Integer accountId = updateRequest.getAccount().getId();
			Integer accountIdOfBulkRepeatCheck = (Integer) updateRequest.getAdditionalAttribute("accountId");

			UserProfile userToken = (UserProfile) updMessage.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			updConnection = getConnection(Boolean.FALSE);
			Boolean isContactModified = (Boolean) updateRequest.getAdditionalAttribute("isContactModified");
			isContactModified = isContactModified == null ? Boolean.FALSE : isContactModified;
			String oldAccountStatus = (String) updateRequest.getAdditionalAttribute("OldAccountStatus"); // AT-5396
			
			beginTransaction(updConnection);
			OperationEnum registrationOperation = updMessage.getPayload().getGatewayMessageExchange().getOperation();
			if (OperationEnum.RECHECK_FAILURES==registrationOperation && null != accountIdOfBulkRepeatCheck
					&& 0 != accountIdOfBulkRepeatCheck) {
				ComplianceAccount account = updateResponse.getAccount();
				
				//AT-5395 & AT-5396
				setAccountStatusPostBRCForAccount(activeFlag, inactiveFlag, oldAccountStatus, updateResponse);
				
				updateAccountComplianceStatusForRegUpdate(updConnection, updateResponse, userToken.getUserID(),
					updateRequest.getAccount().getCustType());
			}
			
			if(OperationEnum.RECHECK_FAILURES!=registrationOperation)
				updateAccountComplianceStatusForRegUpdate(updConnection, updateResponse, userToken.getUserID(),
						updateRequest.getAccount().getCustType());

			Account updateAccount = null;
			if (registrationOperation == OperationEnum.ADD_CONTACT) {
				updateAccount = getAccountFromAccountAttribute(updateRequest.getAccount().getId());
			} else {
				updateAccount = (Account) ObjectCloner.deepCopy(updateRequest.getAccount());
			}
			updateAccount.setAccountStatus(updateResponse.getAccount().getAcs().name());
			
			//AT-5395 & AT-5396
			setAccountStatusPostBRC(activeFlag, inactiveFlag, oldAccountStatus, updateResponse, registrationOperation, updateAccount);
			
			updateAccount.setId(accountId);
			if (Boolean.TRUE.equals(isContactModified)) {
				List<Contact> contactList = updateContactAttributesFields(updateRequest, updateResponse);
				updateContactAttributes(contactList);
				// Clone request object, remove contacts so that does not get
				// serialized to DB
				// also update Account status, so next update will have it
				// readily
				// available
				updateContactComplianceStatusForRegUpdate(updConnection, updateResponse, userToken.getUserID(),
						updateRequest, registrationOperation, activeFlag, inactiveFlag); //AT-5395 & AT-5396
				saveIntoContactComplianceHistory(updConnection, updateResponse, userToken.getUserID());
				saveIntoProfileStatusReason(updConnection, updateResponse, updateAccount.getId(),
						updateRequest.getOrgCode(), userToken.getUserID());
			}
			
			//Add For AT-2986 payIN/OUT watchlist status update in contact
			updateContactPaymentsWatchListStatusForUpdateAccOperation(updConnection, updateResponse, updateRequest,
					userToken, isContactModified, registrationOperation);
			
			/**
			 * Code added to change Contact status as per Account Status for CFX - Vishal J
			 * */
			if(!CustomerTypeEnum.PFX.getCustTypeAsString().equalsIgnoreCase(updateRequest.getAccount().getCustType())){
				for(ComplianceContact contact: updateResponse.getAccount().getContacts()) {
					updateContactComplianceStatusForCFX(updConnection, contact);
				}
			}
			updateAccount.getContacts().clear();
			updateIntoAccountAttributeStatus(updConnection, updateAccount, userToken.getUserID());
			saveIntoAccountComplianceHistory(updConnection, updateResponse, userToken.getUserID());

			commitTransaction(updConnection);
		} catch (Exception e) {
			updMessage.getPayload().setFailed(true);
			LOG.error(ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS, e);
		} finally {
			try {
				closeConnection(updConnection);
			} catch (ComplianceException e) {
				updMessage.getPayload().setFailed(true);
				LOG.error(ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS, e);
			}
		}
		return updMessage;
	}

	private void updateContactPaymentsWatchListStatusForUpdateAccOperation(Connection updConnection,
			RegistrationResponse updateResponse, RegistrationServiceRequest updateRequest, UserProfile userToken,
			Boolean isContactModified, OperationEnum registrationOperation) throws ComplianceException {
		if(OperationEnum.UPDATE_ACCOUNT==registrationOperation && Boolean.FALSE.equals(isContactModified) && 
				CustomerTypeEnum.PFX.getCustTypeAsString().equalsIgnoreCase(updateRequest.getAccount().getCustType())){
			updateContactPaymentsWatchListStatusForRegUpdate(updConnection, updateResponse, userToken.getUserID());
		}
	}

	private void setAccountStatusPostBRCForAccount(Boolean activeFlag, Boolean inactiveFlag, String oldAccountStatus, RegistrationResponse updateResponse) {
		if(Boolean.TRUE.equals(activeFlag) && oldAccountStatus.equalsIgnoreCase(ContactComplianceStatus.ACTIVE.getComplianceStatusAsString())) {
			ComplianceAccount account = updateResponse.getAccount();
			account.setAcs(ContactComplianceStatus.ACTIVE);
		}
		if(Boolean.TRUE.equals(inactiveFlag) && oldAccountStatus.equalsIgnoreCase(ContactComplianceStatus.INACTIVE.getComplianceStatusAsString())) {
			ComplianceAccount account = updateResponse.getAccount();
			account.setAcs(ContactComplianceStatus.INACTIVE);
		}
	}
	
	private void setAccountStatusPostBRC(Boolean activeFlag, Boolean inactiveFlag, String oldAccountStatus, RegistrationResponse updateResponse,
			OperationEnum registrationOperation, Account updateAccount) {
		if(OperationEnum.RECHECK_FAILURES==registrationOperation) {
			if(Boolean.TRUE.equals(activeFlag) && oldAccountStatus.equalsIgnoreCase(ContactComplianceStatus.ACTIVE.getComplianceStatusAsString())) {
				updateAccount.setAccountStatus(ContactComplianceStatus.getComplianceAsString(1));
				ComplianceAccount account = updateResponse.getAccount();
				account.setAcs(ContactComplianceStatus.ACTIVE);
			}
			if(Boolean.TRUE.equals(inactiveFlag) && oldAccountStatus.equalsIgnoreCase(ContactComplianceStatus.INACTIVE.getComplianceStatusAsString())) {
				updateAccount.setAccountStatus(ContactComplianceStatus.getComplianceAsString(4));
				ComplianceAccount account = updateResponse.getAccount();
				account.setAcs(ContactComplianceStatus.INACTIVE);
			}
		}
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
	 * Update account compliance status for reg update.
	 *
	 * @param connection
	 *            the connection
	 * @param response
	 *            the response
	 * @param user
	 *            the user
	 * @param customerType
	 *            the customer type
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateAccountComplianceStatusForRegUpdate(Connection connection, RegistrationResponse response,
			Integer user, String customerType) throws ComplianceException {
		PreparedStatement statement = null;
		Boolean isOnQueue = Boolean.FALSE;
		try {
			ComplianceAccount account = response.getAccount();
			if (account.getAcs().name().equals(ContactComplianceStatus.INACTIVE.name())
					&& customerType.equalsIgnoreCase(CustomerTypeEnum.CFX.name())) {
				isOnQueue = Boolean.TRUE;
			}
			statement = getPreparedStatementAsPerStatus(connection, response, user, account, isOnQueue);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);

		}
	}

	/**
	 * Gets the prepared statement as per status.
	 *
	 * @param connection            the connection
	 * @param response            the response
	 * @param user            the user
	 * @param account            the account
	 * @param isOnQueue the is on queue
	 * @return the prepared statement as per status
	 * @throws SQLException             the SQL exception
	 */
	private PreparedStatement getPreparedStatementAsPerStatus(Connection connection, RegistrationResponse response,
			Integer user, ComplianceAccount account, Boolean isOnQueue) throws SQLException {
		PreparedStatement statement;
		if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(account.getKycStatus())
				&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(account.getSanctionStatus())) {
			statement = connection.prepareStatement(
					DBQueryConstant.UPDATE_ACCOUNT_WITHOUT_SANCTION_AND_KYC_COMPLIANCE_STATUS.getQuery());
			setCommonValuesForUpdate(user, account, isOnQueue, statement);
			statement.setInt(12, response.getAccount().getId());
		} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(account.getKycStatus())) {
			statement = connection
					.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_WITHOUT_KYC_COMPLIANCE_STATUS.getQuery());
			setCommonValuesForUpdate(user, account, isOnQueue, statement);
			statement.setInt(12, ServiceStatus.getStatusAsInteger(account.getSanctionStatus()));
			statement.setInt(13, response.getAccount().getId());
		} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(account.getSanctionStatus())) {
			statement = connection
					.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_WITHOUT_SANCTION_COMPLIANCE_STATUS.getQuery());
			setCommonValuesForUpdate(user, account, isOnQueue, statement);
			statement.setInt(12, ServiceStatus.getStatusAsInteger(account.getKycStatus()));
			statement.setInt(13, response.getAccount().getId());
		} else {
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_COMPLIANCE_STATUS.getQuery());
			setCommonValuesForUpdate(user, account, isOnQueue, statement);
			statement.setInt(12, ServiceStatus.getStatusAsInteger(account.getKycStatus()));
			statement.setInt(13, ServiceStatus.getStatusAsInteger(account.getSanctionStatus()));
			statement.setInt(14, response.getAccount().getId());
		}

		return statement;
	}

	/**
	 * Sets the common values for update.
	 *
	 * @param user the user
	 * @param account the account
	 * @param isOnQueue the is on queue
	 * @param statement the statement
	 * @throws SQLException the SQL exception
	 */
	private void setCommonValuesForUpdate(Integer user, ComplianceAccount account, Boolean isOnQueue,
			PreparedStatement statement) throws SQLException {
		statement.setString(1, account.getAcs().name());
		statement.setString(2, account.getAcs().name());
		statement.setInt(3, user);
		statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		statement.setInt(5, ServiceStatus.getStatusAsInteger(account.getFraugsterStatus()));
		statement.setInt(6, ServiceStatus.getStatusAsInteger(account.getBlacklistStatus()));
		statement.setInt(7, ServiceStatus.getStatusAsInteger(account.getPaymentinWatchlistStatus()));
		statement.setInt(8, ServiceStatus.getStatusAsInteger(account.getPaymentoutWatchlistStatus()));
		if (null != account.getRegisteredDate()) {
			statement.setTimestamp(9, account.getRegisteredDate());
			statement.setTimestamp(10, account.getExpiryDate());
		} else {
			statement.setNull(9, Types.TIMESTAMP);
			statement.setNull(10, Types.TIMESTAMP);
		}
		statement.setBoolean(11, isOnQueue);
	}

	/**
	 * Update contact compliance status for reg update.
	 *
	 * @param connection            the connection
	 * @param response            the response
	 * @param user            the user
	 * @param request the request
	 * @param registrationOperation the registration operation
	 * @throws ComplianceException             the compliance exception
	 */
	private void updateContactComplianceStatusForRegUpdate(Connection connection, RegistrationResponse response,
			Integer user, RegistrationServiceRequest request, OperationEnum registrationOperation, Boolean activeFlag, Boolean inactiveFlag) throws ComplianceException {
		Boolean isOnQueue = Boolean.FALSE;
		for (ComplianceContact contact : response.getAccount().getContacts()) {
			try {
				String oldContactStatus = (String) request.getAdditionalAttribute("OldContactStatus");//AT-5396
				if(CustomerTypeEnum.PFX.name().equalsIgnoreCase(request.getAccount().getCustType())) {
					Contact c = request.getAccount().getContactByID(contact.getId());
					isOnQueue = c.isOnQueue();
					isOnQueue = setIsOnQueueStatusForPFXContacts(request, contact, c, isOnQueue);
				}
				if(registrationOperation == OperationEnum.RECHECK_FAILURES){
					Integer contactId = (Integer) request.getAdditionalAttribute("contactId");
					if (null != contactId && contactId.equals(contact.getId())) {
						setContactStatusPostBRC(activeFlag, inactiveFlag, oldContactStatus, contact); // AT-5395 & AT-5396
						updateStatusAsPerContactServiceStatus(connection, user, contact, isOnQueue);
					}
				} else {
					updateStatusAsPerContactServiceStatus(connection, user, contact, isOnQueue);
				}
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
		}
	}
	
	// AT-5395 & AT-5396
	private void setContactStatusPostBRC(Boolean activeFlag, Boolean inactiveFlag, String oldContactStatus, ComplianceContact contact) {
		if(Boolean.TRUE.equals(activeFlag) && oldContactStatus.equalsIgnoreCase("ACTIVE")) {
			contact.setCcs(ContactComplianceStatus.ACTIVE);
		}
		if(Boolean.TRUE.equals(inactiveFlag) && oldContactStatus.equalsIgnoreCase("INACTIVE")) {
			contact.setCcs(ContactComplianceStatus.INACTIVE);
		}
	}
	
	private Boolean setIsOnQueueStatusForPFXContacts(RegistrationServiceRequest request, ComplianceContact contact,
			Contact c, Boolean isOnQueue) {
		if (CustomerTypeEnum.PFX.name().equalsIgnoreCase(request.getAccount().getCustType())
				&& ContactComplianceStatus.INACTIVE==contact.getCcs()
				&& (ComplianceReasonCode.KYC==contact.getCrc()
						|| ComplianceReasonCode.KYC_NA==contact.getCrc())) {
			isOnQueue = Boolean.FALSE;
		} else if (CustomerTypeEnum.PFX.name().equalsIgnoreCase(request.getAccount().getCustType())
				&& ContactComplianceStatus.INACTIVE==contact.getCcs()
				&& (!c.isKYCEligible() && checkContactReasonCodes(contact))) {
			isOnQueue = Boolean.TRUE;
		}
		return isOnQueue;
	}

	/**
	 * Update contact compliance status for CFX.
	 *
	 * @param connection the connection
	 * @param contact the contact
	 * @param request the request
	 * @throws ComplianceException 
	 */
	private void updateContactComplianceStatusForCFX(Connection connection, ComplianceContact contact) throws ComplianceException {
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_STATUS_FOR_CFX.getQuery());
			preparedStatement.setString(1, contact.getCcs().name());
			preparedStatement.setInt(2, contact.getId());
			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	// AT-878 changes
	/**
	 * Check contact reason codes.
	 *
	 * @param contact the contact
	 * @return true, if successful
	 */
	// When KYC_NA, and onlu phone updated, should not be on queue
	private boolean checkContactReasonCodes(ComplianceContact contact) {
		boolean result = false;
		if (null != contact.getCrc() && !isContactOnWatchList(contact))
			result = true;
		return result;
	}

	/**
	 * Checks if is contact on watch list.
	 *
	 * @param contact the contact
	 * @return true, if is contact on watch list
	 */
	private boolean isContactOnWatchList(ComplianceContact contact) {
		boolean result = false;
		if (ComplianceReasonCode.FRAUGSTER_WATCHLIST==contact.getCrc()
				|| ComplianceReasonCode.USGLOBAL_WATCHLIST==contact.getCrc()
				|| ComplianceReasonCode.IPDISTANCECHECK==contact.getCrc()
				|| ComplianceReasonCode.HIGHRISK_WATCHLIST==contact.getCrc())
			result = true;
		return result;

	}

	/**
	 * Update status as per contact service status.
	 *
	 * @param connection            the connection
	 * @param user            the user
	 * @param contact            the contact
	 * @param isOnQueue the is on queue
	 * @throws SQLException             the SQL exception
	 * @throws ComplianceException             the compliance exception
	 */
	private void updateStatusAsPerContactServiceStatus(Connection connection, Integer user, ComplianceContact contact,
			Boolean isOnQueue) throws SQLException, ComplianceException {
		PreparedStatement contactStmt = null;
		try {
			//Add payment IN/Out watchlist staus fro AT-2986
			WatchListDetails watchlistdet = getPaymentWatchListReasons(contact);
			processWatchList(contact, watchlistdet);
			
			if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getKycStatus())
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getSanctionStatus())
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getFraugsterStatus())) {
				contactStmt = connection.prepareStatement(
						DBQueryConstant.UPDATE_CONTACT_WITHOUT_SANCTION_AND_KYC_AND_FRAUDPREDICT_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(6, isOnQueue);
				contactStmt.setInt(7, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(8, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(9, contact.getId());
			} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getKycStatus())
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getFraugsterStatus())) {
				contactStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_WITHOUT_KYC_AND_FRAUDPREDICT_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getSanctionStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(7, isOnQueue);
				contactStmt.setInt(8, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(9, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(10, contact.getId());
			} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getSanctionStatus())
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getFraugsterStatus())) {
				contactStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_WITHOUT_SANCTION_AND_FRAUDPREDICT_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getKycStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(7, isOnQueue);
				contactStmt.setInt(8, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(9, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(10, contact.getId());
			} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getKycStatus())
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getSanctionStatus())) {
				contactStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_WITHOUT_KYC_AND_SANCTION_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getFraugsterStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(7, isOnQueue);
				contactStmt.setInt(8, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(9, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(10, contact.getId());
			} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getKycStatus())) {
				contactStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_WITHOUT_KYC_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getFraugsterStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getSanctionStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(7, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(8, isOnQueue);
				contactStmt.setInt(9, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(10, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(11, contact.getId());
			} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getSanctionStatus())) {
				contactStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_WITHOUT_SANCTION_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getKycStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getFraugsterStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(7, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(8, isOnQueue);
				contactStmt.setInt(9, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(10, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(11, contact.getId());
			}  else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contact.getFraugsterStatus())) {
				contactStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_CONTACT_WITHOUT_FRAUDPREDICT_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getKycStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getSanctionStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(7, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(8, isOnQueue);
				contactStmt.setInt(9, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(10, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(11, contact.getId());
			} else {
				contactStmt = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_COMPLIANCE_STATUS.getQuery());
				contactStmt.setString(1, contact.getCcs().name());
				contactStmt.setInt(2, user);
				contactStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getKycStatus()));
				contactStmt.setInt(5, ServiceStatus.getStatusAsInteger(contact.getFraugsterStatus()));
				contactStmt.setInt(6, ServiceStatus.getStatusAsInteger(contact.getSanctionStatus()));
				contactStmt.setInt(7, ServiceStatus.getStatusAsInteger(contact.getBlacklistStatus()));
				contactStmt.setInt(8, ServiceStatus.getStatusAsInteger(contact.getCustomCheckStatus()));
				contactStmt.setBoolean(9, isOnQueue);
				contactStmt.setInt(10, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(11, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(12, contact.getId());
			}
			contactStmt.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(contactStmt);
		}

	}
	
	@SuppressWarnings("squid:S2095")
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#updateContactAttributes(org.springframework.
	 * messaging.Message)
	 */
	@Override
	public Message<MessageContext> updateContactAttributes(Message<MessageContext> message) {
		try {
			RegistrationServiceRequest request = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			List<Contact> contacts = request.getAccount().getContacts();
			updateContactAttributes(contacts);
		} catch (Exception e) {
			LOG.error("Exception in updateContactAttributes()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * @param message
	 * @return
	 * @throws ComplianceException
	 */
	public Message<MessageContext> updateAccountStatusAfterPOI(Message<MessageContext> message)
			throws ComplianceException {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		UserProfile userToken = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
		RegistrationResponse profileUpdateResponse = (RegistrationResponse) statusUpdateRequest
				.getAdditionalAttribute(Constants.FIELD_BROADCAST_RESPONSE);
		ComplianceAccount account = profileUpdateResponse.getAccount();
		Account oldAccount = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		String serviceStatus = (String) statusUpdateRequest.getAdditionalAttribute(Constants.ONFIDO_RESULT_REASON);
		Boolean statusUpdateReason = (Boolean) statusUpdateRequest.getAdditionalAttribute("statusUpdateReason");
		statusUpdateReason = statusUpdateReason == null ? Boolean.FALSE : statusUpdateReason;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_STATUS.getQuery());
			statement.setString(1, account.getAcs().getComplianceStatusAsString());
			statement.setInt(2, account.getAcs().getComplianceStatusAsInteger());
			statement.setTimestamp(3, profileUpdateResponse.getAccount().getRegisteredDate());
			statement.setTimestamp(4, profileUpdateResponse.getAccount().getExpiryDate());
			statement.setInt(5, 1);
			statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			if(null != statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS)
					&& null != statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS)) {
				statement.setInt(7, ServiceStatus.getStatusAsInteger((String) statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS)));
				statement.setInt(8, ServiceStatus.getStatusAsInteger((String) statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS)));
			}
			else {
				statement.setInt(7,5);
				statement.setInt(8,5);
			}
			statement.setInt(9, OnfidoStatusEnum.getStatusAsInteger(serviceStatus));
			statement.setString(10, statusUpdateRequest.getCrmAccountId());
			statement.executeUpdate();
			updateContactStatusAfterPOI(account, statusUpdateRequest, connection);
			if(Boolean.TRUE.equals(statusUpdateReason)) {
				saveIntoProfileStatusReason(connection, profileUpdateResponse, oldAccount.getId(),
						statusUpdateRequest.getOrgCode(), userToken.getUserID());
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return message;
	}

	/**
	 * @param account
	 * @param statusUpdateRequest
	 * @param connection
	 * @throws ComplianceException
	 */
	public void updateContactStatusAfterPOI(ComplianceAccount account, StatusUpdateRequest statusUpdateRequest,
			Connection connection) throws ComplianceException {
		
		Timestamp complianceRegistered = (Timestamp) statusUpdateRequest.getAdditionalAttribute("ContactRegisteredDate");
		Timestamp complianceExpiryDate = (Timestamp) statusUpdateRequest.getAdditionalAttribute("ContactExpiryDate");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_STATUS.getQuery());
			statement.setInt(1, account.getContacts().get(0).getCcs().getComplianceStatusAsInteger());
			if(null != complianceRegistered){
				statement.setTimestamp(2, complianceRegistered);
			} else{
				statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}
			
			if(null != complianceExpiryDate){
				statement.setTimestamp(3, complianceExpiryDate);
			}else{
				Timestamp time = new Timestamp(System.currentTimeMillis());
				Calendar c = Calendar.getInstance();
				c.setTime(time);
				c.add(Calendar.YEAR, PropertyHandler.getComplianceExpiryYears());
				statement.setTimestamp(3, new Timestamp(c.getTimeInMillis()));
			}
			statement.setInt(4, 1);
			statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			if(null != statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS)
					&& null != statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS)) {
				statement.setInt(6, ServiceStatus.getStatusAsInteger((String) statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS)));
				statement.setInt(7, ServiceStatus.getStatusAsInteger((String) statusUpdateRequest.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS)));
			}
			else {
				statement.setInt(6,5);
				statement.setInt(7,5);
			}
			statement.setString(8, statusUpdateRequest.getCrmContactId());
			
			statement.executeUpdate();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}
	
	/**
	 * Update contact payments watch list status for reg update.
	 *
	 * @param connection the connection
	 * @param response the response
	 * @param user the user
	 * @throws ComplianceException the compliance exception
	 */
	private void updateContactPaymentsWatchListStatusForRegUpdate(Connection connection, RegistrationResponse response,
			Integer user) throws ComplianceException {
		PreparedStatement contactStmt = null;
		
		for (ComplianceContact contact : response.getAccount().getContacts()) {	
			WatchListDetails watchlistdet = getPaymentWatchListReasons(contact);
			processWatchList(contact, watchlistdet);
			
			try{
				contactStmt = connection.prepareStatement(DBQueryConstant.UPDATE_CONTACT_PAYMENTS_WATCHLIST_STATUS.getQuery());
				contactStmt.setInt(1, user);
				contactStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				contactStmt.setInt(3, ServiceStatus.getStatusAsInteger(contact.getPaymentinWatchlistStatus()));
				contactStmt.setInt(4, ServiceStatus.getStatusAsInteger(contact.getPaymentoutWatchlistStatus()));
				contactStmt.setInt(5, contact.getId());
				contactStmt.executeUpdate();
			}
			catch(Exception e){
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
			finally {
				closePrepareStatement(contactStmt);
			}
		}
	}
	
	public Message<MessageContext> updateIntuitionRiskLevelForRegUpdate(Message<MessageContext> message) {
		Connection connection = null;
		try {

			RegistrationResponse response = (RegistrationResponse) message.getPayload().getGatewayMessageExchange()
					.getResponse();

			RegistrationServiceRequest request = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			Account account = request.getAccount();

			UserProfile userToken = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			connection = getConnection(Boolean.FALSE);
			beginTransaction(connection);
			updateAccountIntuitionRiskLevel(account.getId(), response, userToken, connection);
			commitTransaction(connection);
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error(ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS, e);
		} finally {
			try {
				closeConnection(connection);
			} catch (ComplianceException e) {
				message.getPayload().setFailed(true);
				LOG.error(ERROR_WHILE_UPDATING_COMPLIANCE_STATUS_UPDATE_COMPLIANCE_STATUS, e);
			}
		}
		return message;
	}

	private void updateAccountIntuitionRiskLevel(Integer accountId, RegistrationResponse updateResponse,
			UserProfile userToken, Connection connection) throws ComplianceException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_INTUITION_RISK_LEVEL.getQuery());

			statement.setString(1, updateResponse.getAccount().getIntuitionRiskLevel());
			statement.setInt(2, userToken.getUserID());
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, accountId);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

}
