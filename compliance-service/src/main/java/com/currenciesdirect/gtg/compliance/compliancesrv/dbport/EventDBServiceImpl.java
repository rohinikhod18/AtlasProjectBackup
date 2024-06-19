package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.commons.enums.EventServiceLogServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact.DeleteContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLogSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchangeWrapper;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.LongAndIntegerUtils;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class EventDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
public class EventDBServiceImpl extends AbstractDBServiceImpl implements IEventDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(EventDBServiceImpl.class);

	/** The Constant CONTACT_NAME2. */
	private static final String CONTACT_NAME2 = "ContactName";

	/** The Constant ENTITY_TYPE. */
	private static final String ENTITY_TYPE = "EntityType";

	/** The Constant CREATED_ON. */
	private static final String CREATED_ON = "CreatedOn";

	/** The Constant EVENT_ID. */
	private static final String EVENT_ID = "EventID";

	/** The Constant PROVIDER_RESPONSE. */
	private static final String PROVIDER_RESPONSE = "ProviderResponse";

	/** The Constant STATUS. */
	private static final String STATUS = "Status";

	/** The Constant SUMMARY. */
	private static final String SUMMARY = "Summary";

	/** The Constant UPDATED_BY. */
	private static final String UPDATED_BY = "UpdatedBy";

	/** The Constant UPDATED_ON. */
	private static final String UPDATED_ON = "UpdatedOn";

	/** The Constant ENTITYID. */
	private static final String ENTITYID = "entityid";

	/** The Constant CREATED_BY. */
	private static final String CREATED_BY = "CreatedBy";

	/** The Constant CONTACT_NAME. */
	private static final String CONTACT_NAME = CONTACT_NAME2;

	/** The Constant ACCOUNT_NAME. */
	private static final String ACCOUNT_NAME = "AccountName";

	/** The Constant ACCOUNT. */
	private static final String ACCOUNT = "account";
	
	/** The Constant IS_SANCTION_ELIGIBLE. */
	private static final String IS_SANCTION_ELIGIBLE ="isSanctionEligible";
	
	/** The Constant IS_FRAUGSTER_ELIGIBLE. */
	private static final String IS_FRAUGSTER_ELIGIBLE =	"isFraugsterEligible";
	
	/** The Constant IS_BLACKLIST_ELIGIBLE. */
	private static final String IS_BLACKLIST_ELIGIBLE =	"isBlacklistEligible";
	
	/** The Constant IS_COUNTRY_CHECK_ELIGIBLE. */
	private static final String IS_COUNTRY_CHECK_ELIGIBLE =	"isCountryCheckEligible";
	
	/** The Constant IS_Custom_CHECK_ELIGIBLE. */
	private static final String IS_CUSTOM_CHECK_ELIGIBLE =	"isCustomCheckEligible";
	
	/** The Constant IS_EID_ELIGIBLE. */
	private static final String IS_EID_ELIGIBLE = "isEidEligible";
	
	/** The Constant FUNDS_IN. */
	private static final String FUNDS_IN = "FUNDSIN";
	
	/** The service name. */
	private String serviceName;

	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the service name.
	 *
	 * @param serviceName
	 *            the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * createEvent(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> createEvent(Message<MessageContext> message) {

		Connection connection = null;
		try {
			String orgCode = message.getPayload().getOrgCode();
			ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();

			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			String dbEventName = eventType + "_" + operation;
			Integer createdBy = token.getUserID();
			connection = getConnection(Boolean.FALSE);
			Integer eventId = null;
			beginTransaction(connection);
			eventId = getEventIdForProfileAndFundsIn(eventType, operation, message, connection, orgCode, dbEventName,
					createdBy);
			if (eventId == null) {
				switch (eventType + "_" + operation) {
				case "FUNDSOUT_FUNDS_OUT":
					eventId = handleFundsOutCreate(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_UPDATE_OPI":
					eventId = handleFundsOutUpdate(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_DELETE_OPI":
					eventId = handleFundsOutDelete(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_SANCTION_RESEND":
					eventId = handleFundsOutSanctionResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_CUSTOMCHECK_RESEND":
					eventId = handleFundsOutCCResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_FRAUGSTER_RESEND":
					eventId = handleFundsOutFraugsterResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_BLACKLIST_RESEND":
					eventId = handleFundsOutBlacklistResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSIN_SANCTION_RESEND":
					eventId = handleFundsInSanctionResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSIN_FRAUGSTER_RESEND":
					eventId = handleFundsInFraugsterResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSIN_BLACKLIST_RESEND":
					eventId = handleFundsInBlacklistResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_RECHECK_FAILURES":
					eventId = handleFailedFundsOutRecheck(message, connection, orgCode, createdBy);
					break;
				case "FUNDSIN_RECHECK_FAILURES":
					eventId = handleFailedFundsInRecheck(message, connection, orgCode, createdBy);
					break;
				//Added for AT-3658
				case "FUNDSOUT_PAYMENT_REFERENCE_RESEND":
					eventId = handleFundsOutPaymentReferenceResend(message, connection, orgCode, createdBy);
					break;
				case "FUNDSOUT_REPEAT_CHECK_STATUS_UPDATE":
					eventId = handleFundsOutIntuitionRepeatCheck(message, connection, orgCode, createdBy);
					break;
				//AT-4749
				case "INTUITION_STATUS_UPDATE":
					eventId = handlePaymentIntuitionManualUpdate(message, connection, orgCode, createdBy);
					break;
				default:
					break;
				}
			}
			commitTransaction(connection);
			message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(Constants.FIELD_EVENTID,
					eventId);
		} catch (Exception e) {
			LOG.error("error in createEvent:", e);
			message.getPayload().setFailed(true);
		} finally {
			try {
				closeConnection(connection);
			} catch (ComplianceException e) {
				LOG.error("error in createEvent:", e);
				message.getPayload().setFailed(true);
			}
		}
		return message;
	}

	/**
	 * Gets the event id for profile and funds in.
	 *
	 * @param eventType
	 *            the event type
	 * @param operation
	 *            the operation
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param dbEventName
	 *            the db event name
	 * @param createdBy
	 *            the created by
	 * @return the event id for profile and funds in
	 */
	private Integer getEventIdForProfileAndFundsIn(ServiceInterfaceType eventType, OperationEnum operation,
			Message<MessageContext> message, Connection connection, String orgCode, String dbEventName,
			Integer createdBy) {
		Integer eventId = null;
		try {
			switch (eventType + "_" + operation) {
			case "PROFILE_NEW_REGISTRATION":
			case "PROFILE_ADD_CONTACT":
			case "PROFILE_UPDATECONTACT":
			case "PROFILE_UPDATE_ACCOUNT":
			case "PROFILE_KYC_RESEND":
			case "PROFILE_SANCTION_RESEND":
			case "PROFILE_BLACKLIST_RESEND":
				eventId = handleProfileEvents(message, connection, orgCode, dbEventName, createdBy);
				break;
			case "PROFILE_FRAUGSTER_RESEND":
				eventId = handleProfileFraugsterResend(message, connection, orgCode, dbEventName, createdBy);
				break;
			case "FUNDSIN_FUNDS_IN":
				eventId = handleFundsInCreate(message, connection, orgCode, createdBy);
				break;
			case "FUNDSIN_CUSTOMCHECK_RESEND":
				eventId = handleFundsInCCResend(message, connection, orgCode, createdBy);
				break;
			case "PROFILE_RECHECK_FAILURES":
				eventId = handleFailedProfileRecheck(message, connection, orgCode, createdBy);
				break;
			case "PROFILE_DELETE_CONTACT":
				eventId = handleDeleteContact(message, connection, createdBy);
				break;
			case "INTUITION_UPDATE_ACCOUNT":
			case "PROFILE_REPEAT_CHECK_STATUS_UPDATE":
			case "CARD_CARD_STATUS_UPDATE":
			case "INTUITION_NEW_REGISTRATION": //AT-5256
				eventId = handleIntuitionUpdateAccount(message, connection, orgCode, createdBy);
				break;
			case "FUNDSIN_REPEAT_CHECK_STATUS_UPDATE":
				eventId = handleIntuitionUpdateFundsIn(message, connection, orgCode, createdBy);
				break;
			case "FUNDSIN_DELETE_OPI": 
				eventId = handleFundsInDelete(message, connection, orgCode, createdBy);//Add for AT-4699
				break;
			case "CARD_CHECK_CARD_ELIGIBILITY": //Add for AT-5424
				eventId = handleCardCheckEligibility(message, connection, orgCode, createdBy);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			LOG.error("Error in createEvent for Profile:", e);
			message.getPayload().setFailed(true);
		}
		return eventId;
	}
	
	/**
	 * Handle funds in delete.
	 *
	 * @param message the message
	 * @param connection the connection
	 * @param orgCode the org code
	 * @param createdBy the created by
	 * @return the integer
	 * @throws ComplianceException the compliance exception
	 */
	//Add for AT-4699
	private Integer handleFundsInDelete(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInDeleteRequest fundsInDeleteRequest = (FundsInDeleteRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		account = (Account) fundsInDeleteRequest.getAdditionalAttribute(ACCOUNT);
		Integer payInId = (Integer) fundsInDeleteRequest.getAdditionalAttribute("payInId");
		eventId = saveIntoEvent(connection, orgCode, "FUNDSIN_DELETE", account, payInId, null, createdBy);
		return eventId;
	}
	
	//AT-4451
	private Integer handleIntuitionUpdateFundsIn(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		account = (Account) fundsInCreateRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, FUNDS_IN, account, fundsInCreateRequest.getFundsInId(), null,
				createdBy);
		return eventId;
	}

	/**
	 * Handle funds in create.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsInCreate(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		account = (Account) fundsInCreateRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, FUNDS_IN, account, fundsInCreateRequest.getFundsInId(), null,
				createdBy);
		return eventId;
	}

	/**
	 * Handle funds in sanction resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsInSanctionResend(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInCreateRequest finSanctionResend = (FundsInCreateRequest) message.getPayload().getGatewayMessageExchange()
				.getRequest();
		account = (Account) finSanctionResend.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSIN_SANCTION_RESEND", account,
				finSanctionResend.getFundsInId(), null, createdBy);
		return eventId;
	}

	/**
	 * Handle funds in CC resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsInCCResend(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInCreateRequest finCustomCheckResend = (FundsInCreateRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		account = (Account) finCustomCheckResend.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSIN_CUSTOMCHECK_RESEND", account,
				finCustomCheckResend.getFundsInId(), null, createdBy);
		return eventId;
	}

	/**
	 * Handle funds out CC resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutCCResend(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutRequest fCustomCheckResend = (FundsOutRequest) message.getPayload().getGatewayMessageExchange()
				.getRequest();
		account = (Account) fCustomCheckResend.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_CUSTOMCHECK_RESEND", account, null,
				fCustomCheckResend.getFundsOutId(), createdBy);
		return eventId;
	}

	/**
	 * Handle funds out CC resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutFraugsterResend(Message<MessageContext> message, Connection connection,
			String orgCode, Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutFruagsterResendRequest ffResend = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsOutFruagsterResendRequest.class);
		FundsOutRequest fRequest = (FundsOutRequest) ffResend.getAdditionalAttribute(Constants.OLD_REQUEST);
		account = (Account) fRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_FRAUGSTER_RESEND", account, null,
				fRequest.getFundsOutId(), createdBy);
		return eventId;
	}

	/**
	 * Handle funds out sanction resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutSanctionResend(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutRequest sanctionResendfundsOutRequest = (FundsOutRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		account = (Account) sanctionResendfundsOutRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_SANCTION_RESEND", account, null,
				sanctionResendfundsOutRequest.getFundsOutId(), createdBy);
		return eventId;
	}

	/**
	 * Handle funds out delete.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutDelete(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutDeleteRequest fDeleteRequest = (FundsOutDeleteRequest) message.getPayload().getGatewayMessageExchange()
				.getRequest();
		account = (Account) fDeleteRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_DELETE", account, null, fDeleteRequest.getFundsOutId(),
				createdBy);
		return eventId;
	}

	/**
	 * Handle funds out update.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutUpdate(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutUpdateRequest fUpdateRequest = (FundsOutUpdateRequest) message.getPayload().getGatewayMessageExchange()
				.getRequest();
		account = (Account) fUpdateRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_UPDATE", account, null, fUpdateRequest.getFundsOutId(),
				createdBy);
		return eventId;
	}

	/**
	 * Handle funds out create.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutCreate(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutRequest fundsOutRequest = (FundsOutRequest) message.getPayload().getGatewayMessageExchange()
				.getRequest();
		account = (Account) fundsOutRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_CREATE", account, null, fundsOutRequest.getFundsOutId(),
				createdBy);
		return eventId;
	}

	/**
	 * Handle profile events.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param dbEventName
	 *            the db event name
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleProfileEvents(Message<MessageContext> message, Connection connection, String orgCode,
			String dbEventName, Integer createdBy) throws ComplianceException {
		Integer eventId;
		RegistrationServiceRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(RegistrationServiceRequest.class);
		eventId = saveIntoEvent(connection, orgCode, dbEventName, request.getAccount(), null, null, createdBy);
		return eventId;
	}

	/**
	 * Save into event.
	 *
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param eventType
	 *            the event type
	 * @param account
	 *            the account
	 * @param paymentInID
	 *            the payment in ID
	 * @param paymentOutID
	 *            the payment out ID
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer saveIntoEvent(Connection connection, String orgCode, String eventType, Account account,
			Integer paymentInID, Integer paymentOutID, Integer createdBy) throws ComplianceException {
		ResultSet rs = null;
		try (PreparedStatement statement = connection.prepareStatement(
				DBQueryConstant.INSERT_INTO_EVENT_REGISTRATION.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, orgCode);
			statement.setString(2, eventType);
			if (null == account.getId())
				statement.setNull(3, java.sql.Types.INTEGER);
			else
				statement.setInt(3, account.getId());

			if (null == account.getVersion())
				statement.setNull(4, java.sql.Types.INTEGER);
			else
				statement.setInt(4, account.getVersion());

			if (null == paymentInID)
				statement.setNull(5, java.sql.Types.INTEGER);
			else
				statement.setInt(5, paymentInID);

			if (null == paymentOutID)
				statement.setNull(6, java.sql.Types.INTEGER);
			else
				statement.setInt(6, paymentOutID);
			statement.setInt(7, createdBy);
			statement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);

			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * updateEvent(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> updateEvent(Message<MessageContext> message) throws ComplianceException {
		ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
		if (ServiceInterfaceType.PROFILE.compareTo(eventType) == 0) {
			udpateIntoRegistrationEvent(message);
		}
		return message;
	}

	/**
	 * Udpate into registration event.
	 *
	 * @param message
	 *            the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void udpateIntoRegistrationEvent(Message<MessageContext> message) throws ComplianceException {
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute("eventId");
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_EVENT_REGISTRATION.getQuery());
			beginTransaction(connection);
			String status = "SUCCESS";
			String summary = "{\"KYC\":\"PASS\",\"BLACKLIST\":\"PASS\"}";
			statement.setString(1, status);
			statement.setString(2, summary);
			statement.setInt(3, eventId);
			statement.executeUpdate();
			commitTransaction(connection);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * createEventSerivceLog(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> createEventSerivceLog(Message<MessageContext> message) {

		try {
			List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();

			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				switch (exchange.getMessageExchange().getServiceTypeEnum()) {
				case KYC_SERVICE:
					eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.KYC_SERVICE));
					break;
				case INTERNAL_RULE_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.INTERNAL_RULE_SERVICE));
					break;
				case FRAUGSTER_SERVICE:
					eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.FRAUGSTER_SERVICE));
					break;
				case SANCTION_SERVICE:
					eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.SANCTION_SERVICE));
					break;
				case CUSTOM_CHECKS_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE));
					break;
				//Add for AT-3995
				case TRANSACTION_MONITORING_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE));
					break;
				case TRANSACTION_MONITORING_MQ_RESEND:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND));
					break;
				default:
					break;
				}
			}

			if (OperationEnum.SANCTION_RESEND==operation || OperationEnum.KYC_RESEND==operation
					|| OperationEnum.FRAUGSTER_RESEND==operation
					|| OperationEnum.BLACKLIST_RESEND==operation
					|| OperationEnum.PAYMENT_REFERENCE_RESEND == operation) {
				saveIntoEventServiceLogReturnsId(eventServiceLogs);
			} else {
				saveIntoEventServiceLog(eventServiceLogs);
			}
			saveIntoEventServiceLogSummary(eventServiceLogs);
		} catch (Exception e) {
			LOG.error("Error in EventDBServiceImpl createEventServiceLog()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * createUpdateEventSerivceLog(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> createUpdateEventSerivceLog(Message<MessageContext> message) {

		try {
			List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();

			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				switch (exchange.getMessageExchange().getServiceTypeEnum()) {
				case KYC_SERVICE:
					eventServiceLogs.addAll(
							getEventServiceWithoutNotRequiredLogsByService(message, ServiceTypeEnum.KYC_SERVICE));
					break;
				case INTERNAL_RULE_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.INTERNAL_RULE_SERVICE));
					break;
				case FRAUGSTER_SERVICE:
					eventServiceLogs.addAll(
							getEventServiceWithoutNotRequiredLogsByService(message, ServiceTypeEnum.FRAUGSTER_SERVICE));
					break;
				case SANCTION_SERVICE:
					eventServiceLogs.addAll(
							getEventServiceWithoutNotRequiredLogsByService(message, ServiceTypeEnum.SANCTION_SERVICE));
					break;
				case CUSTOM_CHECKS_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE));
					break;
				//Added for AT-4014
				case TRANSACTION_MONITORING_UPDATE_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_UPDATE_SERVICE));
					break;
				default:
					break;
				}
			}
			saveIntoEventServiceLog(eventServiceLogs);
			saveIntoEventServiceLogSummary(eventServiceLogs);
		} catch (Exception e) {
			LOG.error("Error in EventDBServiceImpl reateEventServiceLog()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * updateEventSerivceLog(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> updateEventSerivceLog(Message<MessageContext> message) throws ComplianceException {
		List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();

		for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
			eventServiceLogs.addAll(exchange.getMessageExchange().getEventServiceLogAsList());
		}
		updateEvent(eventServiceLogs);
		return message;
	}

	/**
	 * Update event.
	 *
	 * @param eventServiceLogs
	 *            the event service logs
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void updateEvent(List<EventServiceLog> eventServiceLogs) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection
					.prepareStatement(DBQueryConstant.UPDATE_EVENT_BY_EVENT_SERVICE_ID_AND_SERVICE_TYPE.getQuery());
			beginTransaction(connection);
			LOG.debug("::::::::::::::: updateEvent : {}", eventServiceLogs);
			for (EventServiceLog eventServiceLog : eventServiceLogs) {
				statement.setString(1, "" + eventServiceLog.getProviderResponse());
				statement.setInt(2, ServiceStatus.getStatusAsInteger(eventServiceLog.getStatus()));
				statement.setString(3, "" + eventServiceLog.getSummary());
				statement.setInt(4, eventServiceLog.getUpdatedBy());
				statement.setTimestamp(5, eventServiceLog.getUpdatedOn());
				statement.setInt(6, eventServiceLog.getEventServiceLogId());
				statement.setString(7, eventServiceLog.getServiceName());
				statement.addBatch();
			}
			int[] count = statement.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR,
							new Exception("Fail to update EventServiceLog"));
				}
			}
			statement.close();
			commitTransaction(connection);
		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
	}

	/**
	 * Save into event service log.
	 *
	 * @param eventServiceLogs
	 *            the event service logs
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoEventServiceLog(List<EventServiceLog> eventServiceLogs) throws ComplianceException {

		Connection connection = getConnection(Boolean.FALSE);
		try (PreparedStatement eslStmt = connection
				.prepareStatement(DBQueryConstant.INSERT_INTO_EVENT_SERVICE_LOG.getQuery())) {
			beginTransaction(connection);
			LOG.debug("::::::::::::::: saveIntoEventServiceLog : {}", eventServiceLogs);
			for (EventServiceLog oneEventLog : eventServiceLogs) {

				if (!StringUtils.isNullOrTrimEmpty(oneEventLog.getEntityType())
						&& !StringUtils.isNullOrTrimEmpty(oneEventLog.getServiceName())
						&& !StringUtils.isNullOrTrimEmpty(oneEventLog.getStatus())
						&& !StringUtils.isNullOrTrimEmpty(oneEventLog.getServiceProviderName())) {

					setValuesToEventLog(eslStmt, oneEventLog);
				}
			}
			eslStmt.executeBatch();
			commitTransaction(connection);
		} catch (Exception e) {

			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Sets the values to event log.
	 *
	 * @param eslStmt
	 *            the esl stmt
	 * @param oneEventLog
	 *            the one event log
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void setValuesToEventLog(PreparedStatement eslStmt, EventServiceLog oneEventLog) throws SQLException {
		eslStmt.setInt(1, oneEventLog.getEventId());
		eslStmt.setInt(2, EntityEnum.getEntityTypeAsInteger(oneEventLog.getEntityType()));
		if (oneEventLog.getEntityId() != null)
			eslStmt.setInt(3, oneEventLog.getEntityId());
		else
			eslStmt.setNull(3, Types.INTEGER);
		if (oneEventLog.getEntityVersion() != null)
			eslStmt.setInt(4, oneEventLog.getEntityVersion());
		else
			eslStmt.setNull(4, Types.INTEGER);
		eslStmt.setString(5, oneEventLog.getServiceProviderName());
		eslStmt.setString(6, oneEventLog.getServiceName());
		eslStmt.setString(7, "" + oneEventLog.getProviderResponse());
		eslStmt.setInt(8, ServiceStatus.getStatusAsInteger(oneEventLog.getStatus()));
		eslStmt.setString(9, "" + oneEventLog.getSummary());
		eslStmt.setInt(10, oneEventLog.getCratedBy());
		eslStmt.setTimestamp(11, oneEventLog.getCreatedOn());
		eslStmt.setInt(12, oneEventLog.getUpdatedBy());
		eslStmt.setTimestamp(13, oneEventLog.getUpdatedOn());
		eslStmt.addBatch();
	}

	/**
	 * Gets the event service logs by service.
	 *
	 * @param message
	 *            the message
	 * @param service
	 *            the service
	 * @return the event service logs by service
	 */
	private Collection<EventServiceLog> getEventServiceLogsByService(Message<MessageContext> message,
			ServiceTypeEnum service) {
		return message.getPayload().getMessageExchange(service).getEventServiceLogAsList();
	}

	/**
	 * Gets the event service without not required logs by service.
	 *
	 * @param message
	 *            the message
	 * @param service
	 *            the service
	 * @return the event service without not required logs by service
	 */
	private Collection<EventServiceLog> getEventServiceWithoutNotRequiredLogsByService(Message<MessageContext> message,
			ServiceTypeEnum service) {
		ArrayList<EventServiceLog> eventSeriveLogs = new ArrayList<>();
		Collection<EventServiceLog> eventServiceLogs = message.getPayload().getMessageExchange(service)
				.getEventServiceLogAsList();
		for (EventServiceLog eventLog : eventServiceLogs) {
			if (!ServiceStatus.NOT_REQUIRED.name().equals(eventLog.getStatus())) {
				eventSeriveLogs.add(eventLog);
			}
		}
		return eventSeriveLogs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getEventSerivceLog(java.util.List, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Map<String, EventServiceLog> getEventSerivceLog(List<Integer> eventIds, String serviceName,
			Integer contactId) throws ComplianceException {
		String eventIdsStr = eventIds.toString();
		eventIdsStr = eventIdsStr.substring(1, eventIdsStr.length() - 1).replace(", ", ",");
		String query = DBQueryConstant.GET_EVENT_LOGS_BY_EVENT_IDS_AND_SERVICE_TYPE.getQuery().replace("{EVENT_IDS}",
				eventIdsStr);
		Map<String, EventServiceLog> mapOfEventLogByEventId = new HashMap<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, contactId);
			preparedStatement.setString(2, serviceName);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				EventServiceLog eventServiceLog = new EventServiceLog();
				eventServiceLog.setEventId(rs.getInt(Constants.FIELD_EVENTID));
				eventServiceLog.setEntityId(rs.getInt(Constants.ENTITY_ID));
				eventServiceLog.setServiceName(serviceName);
				eventServiceLog.setProviderResponse(rs.getNString(Constants.PROVIDER_RESPONSE));
				eventServiceLog.setStatus(rs.getNString(Constants.STATUS));
				eventServiceLog.setSummary(rs.getNString(Constants.SUMMARY));
				eventServiceLog.setCratedBy(rs.getInt(Constants.CREATED_BY));
				eventServiceLog.setUpdatedBy(rs.getInt(Constants.UPDATED_BY));
				eventServiceLog.setUpdatedOn(rs.getTimestamp(Constants.UPDATED_ON));
				eventServiceLog.setCreatedOn(rs.getTimestamp(Constants.CREATED_ON));
				mapOfEventLogByEventId.put("" + eventServiceLog.getEventId(), eventServiceLog);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}

		return mapOfEventLogByEventId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * isEventExistByDBId(java.lang.Integer)
	 */
	@Override
	public Boolean isEventExistByDBId(Integer eventId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_EVENT_BY_ID.getQuery());
			preparedStatment.setInt(1, eventId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				return Boolean.TRUE;
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);

		}
		return Boolean.FALSE;
	}

	/**
	 * Purpose : When user is performing repeat check for SANCTION then there
	 * must be atleast one record available previously. We need the status of
	 * such records to decide which method (SLLookupMulti or SLGetStatus) should
	 * be called by provider for repeat check. 1) This method will give the
	 * previous details of Sanction check. 2) Depending upon those statuses we
	 * decide which method should get call.
	 * 
	 * This method is called at the time of - PFX Registration SANCTION repeat
	 * check - CFX Registration SANCTION repeat check (for account and contact)
	 * - Funds out SANCTION repeat check (PFX / CFX) - Funds in SANCTION repeat
	 * check (PFX / CFX)
	 *
	 * @param entityId
	 *            the entity id
	 * @param entityType
	 *            the entity type
	 * @return the previous sanction details
	 * @throws ComplianceException
	 *             the compliance exception
	 */

	@Override
	public EntityDetails getPreviousSanctionDetails(Integer entityId, String entityType) throws ComplianceException {

		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		String status = null;
		String summary = null;
		ResultSet rs = null;
		int count = 0;
		int failcount = 0;
		int passcount = 0;
		String providerResponse;
		List<String> providerResponseList = new ArrayList<>();

		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.GET_PREVIOUS_SANCTION_DETAILS_BY_ENTITY_ID.getQuery());
			preparedStatment.setString(1, "" + entityId);
			preparedStatment.setInt(2, EntityEnum.getEntityTypeAsInteger(entityType));
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				providerResponse = rs.getString(Constants.PROVIDER_RESPONSE);
				providerResponseList.add(providerResponse);
				if (count == 0) {
					status = ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS));
					summary = rs.getString(Constants.SUMMARY);
					entityDetails.setPrevStatus(status);
					entityDetails.setSummary(summary);
					count++;
				}
			}
			setProviderResponseInEntity(entityDetails, providerResponseList);

			entityDetails.setEntityId(entityId);
			entityDetails.setEntityType(entityType);

			for (String currentProviderResponse : providerResponseList) {
				if (currentProviderResponse.contains(ServiceStatus.NOT_PERFORMED.name())
						|| currentProviderResponse.contains(ServiceStatus.NOT_REQUIRED.name())
						|| currentProviderResponse.contains(ServiceStatus.SERVICE_FAILURE.name())) {
					failcount++;
				} else {
					passcount++;
				}
			}
			checkEntityAlreadyExists(entityDetails, failcount, passcount);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return entityDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getPreviousFraugsterDetails(java.lang.Integer, java.lang.String)
	 */
	@Override
	public EntityDetails getPreviousFraugsterDetails(Integer entityId, String entityType) throws ComplianceException {

		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		String status = null;
		String summary = null;
		ResultSet rs = null;
		int count = 0;
		int failcount = 0;
		int passcount = 0;
		String providerResponse;
		List<String> providerResponseList = new ArrayList<>();

		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.GET_PREVIOUS_FRAUGSTER_DETAILS_BY_ENTITY_ID.getQuery());
			preparedStatment.setString(1, "" + entityId);
			preparedStatment.setInt(2, EntityEnum.getEntityTypeAsInteger(entityType));
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				providerResponse = rs.getString(Constants.PROVIDER_RESPONSE);
				providerResponseList.add(providerResponse);
				if (count == 0) {
					status = ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS));
					summary = rs.getString(Constants.SUMMARY);
					entityDetails.setPrevStatus(status);
					entityDetails.setSummary(summary);
					count++;
				}
			}
			setProviderResponseInEntity(entityDetails, providerResponseList);

			entityDetails.setEntityId(entityId);
			entityDetails.setEntityType(entityType);

			for (String currentProviderResponse : providerResponseList) {
				if (currentProviderResponse.contains(ServiceStatus.NOT_PERFORMED.name())
						|| currentProviderResponse.contains(ServiceStatus.NOT_REQUIRED.name())
						|| currentProviderResponse.contains(ServiceStatus.SERVICE_FAILURE.name())) {
					failcount++;
				} else {
					passcount++;
				}
			}
			checkEntityAlreadyExists(entityDetails, failcount, passcount);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return entityDetails;
	}

	/**
	 * Sets the provider response in entity.
	 *
	 * @param entityDetails
	 *            the entity details
	 * @param providerResponseList
	 *            the provider response list
	 */
	private void setProviderResponseInEntity(EntityDetails entityDetails, List<String> providerResponseList) {
		String providerResponse;
		if (null != providerResponseList) {
			if (!providerResponseList.isEmpty()) {
				entityDetails.setProviderResponse(providerResponseList.get(0));
			} else {
				providerResponse = ServiceStatus.NOT_PERFORMED.name();
				providerResponseList.add(providerResponse);
				entityDetails.setPrevStatus(ServiceStatus.NOT_PERFORMED.name());
				entityDetails.setProviderResponse(providerResponseList.get(0));
			}
		}
	}

	/**
	 * Check entity already exists.
	 *
	 * @param entityDetails
	 *            the entity details
	 * @param failcount
	 *            the failcount
	 * @param passcount
	 *            the passcount
	 */
	private void checkEntityAlreadyExists(EntityDetails entityDetails, int failcount, int passcount) {
		if (passcount > 0)
			entityDetails.setIsExist(Boolean.TRUE);
		else if (failcount > 0)
			entityDetails.setIsExist(Boolean.FALSE);
	}

	/**
	 * This method is called to save details of previously performed KYC check
	 * for any contact. (Status, provider response, summary, contact name etc.
	 * fetched from recent KYC check )
	 *
	 * @param entityId
	 *            the entity id
	 * @param entityType
	 *            the entity type
	 * @return the entity details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@Override
	public EntityDetails isKycServicePerformed(Integer entityId, String entityType) throws ComplianceException {

		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		String status = null;
		String providerResponse = null;
		String summary = null;
		ResultSet rs = null;

		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_KYC_SERVICE_STATUS_BY_ENTITY_ID.getQuery());
			preparedStatment.setString(1, "" + entityId);
			preparedStatment.setInt(2, EntityEnum.getEntityTypeAsInteger(entityType));

			rs = preparedStatment.executeQuery();

			if (rs.next()) {
				status = ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS));
				providerResponse = rs.getString(Constants.PROVIDER_RESPONSE);
				summary = rs.getString(Constants.SUMMARY);
				entityDetails.setContactName(rs.getString(CONTACT_NAME2));
				entityDetails.setPrevStatus(status.toLowerCase());
				entityDetails.setProviderResponse(providerResponse);
				entityDetails.setSummary(summary);
			}
			entityDetails.setEntityId(entityId);
			entityDetails.setEntityType(entityType);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return entityDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * isEventServiceLogId(java.lang.Integer)
	 */
	@Override
	public Boolean isEventServiceLogId(Integer eventServiceLogId) throws ComplianceException {

		Connection conn = null;
		PreparedStatement preparedStatment = null;
		Integer id = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_EVENT_SERVICE_LOG_ID.getQuery());
			preparedStatment.setInt(1, eventServiceLogId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				id = rs.getInt("ID");
			}
			if (id != null && id.equals(eventServiceLogId)) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getEventSerivceLogById(java.lang.Integer)
	 */
	@Override
	public Map<String, EventServiceLog> getEventSerivceLogById(Integer eventServiceLogId) throws ComplianceException {
		Map<String, EventServiceLog> mapOfEventLogByEventId = new HashMap<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_EVENT_SERVICE_LOG_ID.getQuery());
			preparedStatement.setInt(1, eventServiceLogId);
			rs = preparedStatement.executeQuery();
			EventServiceLog eventServiceLog = new EventServiceLog();
			eventServiceLog.setEventServiceLogId(eventServiceLogId);
			eventServiceLog.setServiceName(serviceName);
			if (rs.next()) {
				eventServiceLog.setEventId(rs.getInt(EVENT_ID));
				eventServiceLog.setEntityId(rs.getInt(ENTITYID));

				eventServiceLog.setProviderResponse(rs.getNString(PROVIDER_RESPONSE));
				eventServiceLog.setStatus(rs.getNString(STATUS));
				eventServiceLog.setSummary(rs.getNString(SUMMARY));
				eventServiceLog.setCratedBy(rs.getInt(CREATED_BY));
				eventServiceLog.setUpdatedBy(rs.getInt(UPDATED_BY));
				eventServiceLog.setUpdatedOn(rs.getTimestamp(UPDATED_ON));
				eventServiceLog.setCreatedOn(rs.getTimestamp(CREATED_ON));
				mapOfEventLogByEventId.put("" + eventServiceLog.getEventServiceLogId(), eventServiceLog);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}

		return mapOfEventLogByEventId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getEventSerivceLogByIdAndService(java.lang.Integer, java.lang.String,
	 * org.springframework.messaging.Message)
	 */
	@Override
	public void getEventSerivceLogByIdAndService(Integer eventServiceLogId, String serviceName,
			Message<MessageContext> message) throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		EntityDetails entityDetails = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStmt = connection
					.prepareStatement(DBQueryConstant.PROFILE_GET_EVENT_SERVICE_LOG_ID_AND_SERVICE.getQuery());
			preparedStmt.setInt(1, eventServiceLogId);
			preparedStmt.setString(2, serviceName);
			rs = preparedStmt.executeQuery();
			EventServiceLog eventLog = new EventServiceLog();
			entityDetails = new EntityDetails();
			eventLog.setEventServiceLogId(eventServiceLogId);
			eventLog.setServiceName(serviceName);
			if (rs.next()) {
				eventLog.setEventId(rs.getInt(Constants.FIELD_EVENTID));
				eventLog.setEntityId(rs.getInt(Constants.ENTITY_ID));
				eventLog.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(Constants.ENTITY_TYPE)));

				eventLog.setProviderResponse(rs.getNString(Constants.PROVIDER_RESPONSE));
				eventLog.setStatus(ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS)));
				eventLog.setSummary(rs.getNString(Constants.SUMMARY));
				eventLog.setCratedBy(rs.getInt(Constants.CREATED_BY));
				eventLog.setUpdatedBy(rs.getInt(Constants.UPDATED_BY));
				eventLog.setUpdatedOn(rs.getTimestamp(Constants.UPDATED_ON));
				eventLog.setCreatedOn(rs.getTimestamp(Constants.CREATED_ON));
				entityDetails.setEntityId(rs.getInt(Constants.ENTITY_ID));
				entityDetails.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(Constants.ENTITY_TYPE)));
				if (null != EntityEnum.getEntityTypeAsString(rs.getInt(Constants.ENTITY_TYPE)) && EntityEnum.CONTACT
						.name().equalsIgnoreCase(EntityEnum.getEntityTypeAsString(rs.getInt(Constants.ENTITY_TYPE)))) {
					entityDetails.setContactName(rs.getNString(CONTACT_NAME));
				} else if (null != EntityEnum.getEntityTypeAsString(rs.getInt(Constants.ENTITY_TYPE))
						&& EntityEnum.ACCOUNT.name()
								.equalsIgnoreCase(EntityEnum.getEntityTypeAsString(rs.getInt(Constants.ENTITY_TYPE)))) {
					entityDetails.setAccountName(rs.getNString(ACCOUNT_NAME));
				}
				messageExchange.addEventServiceLog(eventLog);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStmt);
			closeConnection(connection);
		}

		message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(Constants.FIELD_ENTITY_DETAILS,
				entityDetails);

	}

	/**
	 * Save into event service log returns id.
	 *
	 * @param eventServiceLogs
	 *            the event service logs
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoEventServiceLogReturnsId(List<EventServiceLog> eventServiceLogs) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		ResultSet rs = null;
		try (PreparedStatement statement = connection.prepareStatement(
				DBQueryConstant.INSERT_INTO_EVENT_SERVICE_LOG.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
			beginTransaction(connection);
			LOG.debug("saveIntoEventServiceLogReturnsId : {}", eventServiceLogs);
			for (EventServiceLog eventServiceLog : eventServiceLogs) {
				statement.setInt(1, eventServiceLog.getEventId());
				statement.setInt(2, EntityEnum.getEntityTypeAsInteger(eventServiceLog.getEntityType()));
				if (eventServiceLog.getEntityId() != null)
					statement.setInt(3, eventServiceLog.getEntityId());
				else
					statement.setNull(3, Types.INTEGER);
				if (eventServiceLog.getEntityVersion() != null)
					statement.setInt(4, eventServiceLog.getEntityVersion());
				else
					statement.setNull(4, Types.INTEGER);
				statement.setString(5, eventServiceLog.getServiceProviderName());
				statement.setString(6, eventServiceLog.getServiceName());
				statement.setString(7, "" + eventServiceLog.getProviderResponse());
				statement.setInt(8, ServiceStatus.getStatusAsInteger(eventServiceLog.getStatus()));
				statement.setString(9, "" + eventServiceLog.getSummary());
				statement.setInt(10, eventServiceLog.getCratedBy());
				statement.setTimestamp(11, eventServiceLog.getCreatedOn());
				statement.setInt(12, eventServiceLog.getUpdatedBy());
				statement.setTimestamp(13, eventServiceLog.getUpdatedOn());
				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next()) {
					eventServiceLog.setEventServiceLogId(rs.getInt(1));
				}
			}
			commitTransaction(connection);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getFundsOutEventSerivceLogByIdAndService(java.lang.Integer,
	 * java.lang.String, org.springframework.messaging.Message)
	 */
	@Override
	public void getFundsOutEventSerivceLogByIdAndService(Integer eventServiceLogId, String serviceName,
			Message<MessageContext> message) throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		Connection connection = null;
		PreparedStatement poLogStmt = null;
		EntityDetails entityDetails = null;
		ResultSet poEventRs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			poLogStmt = connection
					.prepareStatement(DBQueryConstant.FUNDS_OUT_GET_EVENT_SERVICE_LOG_ID_AND_SERVICE.getQuery());
			poLogStmt.setInt(1, eventServiceLogId);
			poLogStmt.setString(2, serviceName);
			poEventRs = poLogStmt.executeQuery();
			EventServiceLog poEventLog = new EventServiceLog();
			entityDetails = new EntityDetails();
			poEventLog.setEventServiceLogId(eventServiceLogId);
			poEventLog.setServiceName(serviceName);
			if (poEventRs.next()) {
				poEventLog.setEventId(poEventRs.getInt(EVENT_ID));
				poEventLog.setEntityId(poEventRs.getInt(ENTITYID));
				poEventLog.setEntityType(EntityEnum.getEntityTypeAsString(poEventRs.getInt(ENTITY_TYPE)));
				poEventLog.setProviderResponse(poEventRs.getNString(PROVIDER_RESPONSE));
				poEventLog.setStatus(ServiceStatus.getStatusAsString(poEventRs.getInt(STATUS)));
				poEventLog.setSummary(poEventRs.getNString(SUMMARY));
				poEventLog.setCratedBy(poEventRs.getInt(CREATED_BY));
				poEventLog.setUpdatedBy(poEventRs.getInt(UPDATED_BY));
				poEventLog.setUpdatedOn(poEventRs.getTimestamp(UPDATED_ON));
				poEventLog.setCreatedOn(poEventRs.getTimestamp(CREATED_ON));

				entityDetails.setEntityId(poEventRs.getInt(ENTITYID));
				entityDetails.setEntityType(EntityEnum.getEntityTypeAsString(poEventRs.getInt(ENTITY_TYPE)));
				entityDetails.setContactName(poEventRs.getNString(CONTACT_NAME));
				entityDetails.setAccountName(poEventRs.getNString(ACCOUNT_NAME));
				entityDetails.setBankName(poEventRs.getNString("BankName"));
				entityDetails.setBeneficiaryName(poEventRs.getNString("BeneficiaryName"));
				messageExchange.addEventServiceLog(poEventLog);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(poEventRs);
			closePrepareStatement(poLogStmt);
			closeConnection(connection);
		}

		message.getPayload().getGatewayMessageExchange().getRequest().addAttribute("entityDetails", entityDetails);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getFundsInEventSerivceLogByIdAndService(java.lang.Integer,
	 * java.lang.String, org.springframework.messaging.Message)
	 */
	@Override
	public void getFundsInEventSerivceLogByIdAndService(Integer eventServiceLogId, String serviceName,
			Message<MessageContext> message) throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		EntityDetails entityDetails = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(DBQueryConstant.FUNDS_IN_GET_EVENT_SERVICE_LOG_ID_AND_SERVICE.getQuery());
			preparedStatement.setInt(1, eventServiceLogId);
			preparedStatement.setString(2, serviceName);
			rs = preparedStatement.executeQuery();
			EventServiceLog eventServiceLog = new EventServiceLog();
			entityDetails = new EntityDetails();
			eventServiceLog.setEventServiceLogId(eventServiceLogId);
			eventServiceLog.setServiceName(serviceName);
			if (rs.next()) {
				eventServiceLog.setEventId(rs.getInt(EVENT_ID));
				eventServiceLog.setEntityId(rs.getInt(ENTITYID));
				eventServiceLog.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(ENTITY_TYPE)));
				eventServiceLog.setProviderResponse(rs.getNString(PROVIDER_RESPONSE));
				eventServiceLog.setStatus(ServiceStatus.getStatusAsString(rs.getInt(STATUS)));
				eventServiceLog.setSummary(rs.getNString(SUMMARY));
				eventServiceLog.setCratedBy(rs.getInt(CREATED_BY));
				eventServiceLog.setUpdatedBy(rs.getInt(UPDATED_BY));
				eventServiceLog.setUpdatedOn(rs.getTimestamp(UPDATED_ON));
				eventServiceLog.setCreatedOn(rs.getTimestamp(CREATED_ON));
				entityDetails.setEntityId(rs.getInt(ENTITYID));
				entityDetails.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(ENTITY_TYPE)));
				entityDetails.setContactName(rs.getNString(CONTACT_NAME));
				entityDetails.setAccountName(rs.getNString(ACCOUNT_NAME));

				messageExchange.addEventServiceLog(eventServiceLog);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}

		message.getPayload().getGatewayMessageExchange().getRequest().addAttribute("entityDetails", entityDetails);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * isServicePerformedForFundsOutCustomCheck(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public EntityDetails isServicePerformedForFundsOutCustomCheck(Integer paymentOutId, String serviceName)
			throws ComplianceException {
		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		String status = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_CUSTOMCHECK_SERVICE_STATUS_BY_ENTITY_ID.getQuery());
			preparedStatment.setString(1, serviceName);
			preparedStatment.setInt(2, paymentOutId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				status = ServiceStatus.getStatusAsString(rs.getInt(STATUS));
				entityDetails.setPrevStatus(status.toLowerCase());
			}
			if (status != null && (status.equals(ServiceStatus.NOT_PERFORMED.name())
					|| status.equals(ServiceStatus.SERVICE_FAILURE.name()))) {
				entityDetails.setIsExist(Boolean.FALSE);
				return entityDetails;
			}

			entityDetails.setIsExist(Boolean.TRUE);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return entityDetails;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * isServicePerformedForFundsInCustomCheck(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public EntityDetails isServicePerformedForFundsInCustomCheck(Integer paymentInId, String serviceName)
			throws ComplianceException {
		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement ccStmt = null;
		String ccStatus = null;
		ResultSet ccRS = null;
		try {
			conn = getConnection(Boolean.TRUE);
			ccStmt = conn
					.prepareStatement(DBQueryConstant.GET_FUNDS_IN_CUSTOMCHECK_SERVICE_STATUS_BY_ENTITY_ID.getQuery());
			ccStmt.setString(1, serviceName);
			ccStmt.setInt(2, paymentInId);
			ccRS = ccStmt.executeQuery();
			if (ccRS.next()) {
				ccStatus = ServiceStatus.getStatusAsString(ccRS.getInt(STATUS));
				entityDetails.setPrevStatus(ccStatus.toLowerCase());
			}
			if (ccStatus != null && (ccStatus.equals(ServiceStatus.NOT_PERFORMED.name())
					|| ccStatus.equals(ServiceStatus.SERVICE_FAILURE.name()))) {
				entityDetails.setIsExist(Boolean.FALSE);
				return entityDetails;
			}

			entityDetails.setIsExist(Boolean.TRUE);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(ccRS);
			closePrepareStatement(ccStmt);
			closeConnection(conn);
		}
		return entityDetails;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * isSanctionPerformed(java.lang.Integer, java.lang.String)
	 */
	@Override
	public EntityDetails isSanctionPerformed(Integer entityId, String entityType) throws ComplianceException {

		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement sanctionStmt = null;
		String status = null;
		String sanctionResponse = null;
		String summary = null;
		ResultSet sanctionRs = null;
		Long entityServiceHash = null;
		try {
			conn = getConnection(Boolean.TRUE);
			sanctionStmt = conn.prepareStatement(DBQueryConstant.GET_RECENT_SPECIFIC_EVENT_SERVICE_DATA.getQuery());
			//AT-3118
			entityServiceHash = LongAndIntegerUtils.getEntityServiceHash(entityId,EntityEnum.getEntityTypeAsInteger(entityType),
					EventServiceLogServiceTypeEnum.SANCTION.getEventServiceTypeAsInteger());
			LOG.info("Entity Service Hash for {} : {}",entityType,entityServiceHash);
			sanctionStmt.setLong(1, entityServiceHash);
			sanctionRs = sanctionStmt.executeQuery();
			if (sanctionRs.next()) {
				status = ServiceStatus.getStatusAsString(sanctionRs.getInt(STATUS));
				sanctionResponse = sanctionRs.getString(PROVIDER_RESPONSE);
				summary = sanctionRs.getString(SUMMARY);
				entityDetails.setPrevStatus(status.toLowerCase());
				entityDetails.setProviderResponse(sanctionResponse);
				entityDetails.setSummary(summary);
			}
			/**
			 * Added condition if we manually pass record then we take updated
			 * providerResponse and set IsExist flag to false when serviceStatus
			 * is Not_Performed,Service_Failure or Not_Required Changes made by
			 * - Saylee
			 */
			if (sanctionResponse == null || sanctionResponse.contains(ServiceStatus.NOT_PERFORMED.name())
					|| sanctionResponse.contains(ServiceStatus.SERVICE_FAILURE.name())
					|| sanctionResponse.contains(ServiceStatus.NOT_REQUIRED.name())) {
				entityDetails.setIsExist(Boolean.FALSE);
			} else {
				entityDetails.setIsExist(Boolean.TRUE);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(sanctionRs);
			closePrepareStatement(sanctionStmt);
			closeConnection(conn);
		}
		return entityDetails;
	}

	/**
	 * Handle profile fraugster resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param dbEventName
	 *            the db event name
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleProfileFraugsterResend(Message<MessageContext> message, Connection connection, String orgCode,
			String dbEventName, Integer createdBy) throws ComplianceException {
		Integer eventId;
		FraugsterResendRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(FraugsterResendRequest.class);
		RegistrationServiceRequest oldRequest = request.getAdditionalAttribute(Constants.OLD_REQUEST,
				RegistrationServiceRequest.class);
		eventId = saveIntoEvent(connection, orgCode, dbEventName, oldRequest.getAccount(), null, null, createdBy);
		return eventId;
	}

	/**
	 * Handle funds in fraugster resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return event id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	protected Integer handleFundsInFraugsterResend(Message<MessageContext> message, Connection connection,
			String orgCode, Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInFraugsterResendRequest ffResend = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsInFraugsterResendRequest.class);
		FundsInCreateRequest fRequset = (FundsInCreateRequest) ffResend.getAdditionalAttribute(Constants.OLD_REQUEST);
		account = (Account) fRequset.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSIN_FRAUGSTER_RESEND", account, fRequset.getFundsInId(), null,
				createdBy);
		return eventId;
	}

	/**
	 * Handle funds in blacklist resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return event id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsInBlacklistResend(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInBlacklistResendRequest fbResend = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsInBlacklistResendRequest.class);
		FundsInCreateRequest fRequset = (FundsInCreateRequest) fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
		account = (Account) fRequset.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSIN_BLACKLIST_RESEND", account, fRequset.getFundsInId(), null,
				createdBy);
		return eventId;
	}

	/**
	 * Handle funds out blacklist resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return event id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutBlacklistResend(Message<MessageContext> message, Connection connection,
			String orgCode, Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutBlacklistResendRequest fbResend = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsOutBlacklistResendRequest.class);
		FundsOutRequest fRequest = (FundsOutRequest) fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
		account = (Account) fRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_BLACKLIST_RESEND", account, null,
				fRequest.getFundsOutId(), createdBy);
		return eventId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService#
	 * getPreviousBlacklistDetails(java.lang.Integer, java.lang.String)
	 */
	@Override
	public EntityDetails getPreviousBlacklistDetails(Integer entityId, String entityType) throws ComplianceException {

		EntityDetails entityDetails = new EntityDetails();
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		String status = null;
		String summary = null;
		ResultSet rs = null;
		int count = 0;
		int failcount = 0;
		int passcount = 0;
		String providerResponse;
		List<String> providerResponseList = new ArrayList<>();

		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.GET_PREVIOUS_BLACKLIST_DETAILS_BY_ENTITY_ID.getQuery());
			preparedStatment.setString(1, "" + entityId);
			preparedStatment.setInt(2, EntityEnum.getEntityTypeAsInteger(entityType));
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				providerResponse = rs.getString(Constants.PROVIDER_RESPONSE);
				providerResponseList.add(providerResponse);
				if (count == 0) {
					status = ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS));
					summary = rs.getString(Constants.SUMMARY);
					entityDetails.setPrevStatus(status);
					entityDetails.setSummary(summary);
					count++;
				}
			}
			setProviderResponseInEntity(entityDetails, providerResponseList);

			entityDetails.setEntityId(entityId);
			entityDetails.setEntityType(entityType);

			for (String currentProviderResponse : providerResponseList) {
				if (currentProviderResponse.contains(ServiceStatus.NOT_PERFORMED.name())
						|| currentProviderResponse.contains(ServiceStatus.NOT_REQUIRED.name())
						|| currentProviderResponse.contains(ServiceStatus.SERVICE_FAILURE.name())) {
					failcount++;
				} else {
					passcount++;
				}
			}
			checkEntityAlreadyExists(entityDetails, failcount, passcount);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return entityDetails;
	}

	/**
	 * Handle failed profile recheck.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFailedProfileRecheck(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		RegistrationServiceRequest registrationDetails = message.getPayload().getGatewayMessageExchange()
				.getRequest(RegistrationServiceRequest.class);
		eventId = saveIntoEvent(connection, orgCode, "PROFILE_RECHECK_FAILURES", registrationDetails.getAccount(), null,
				null, createdBy);
		return eventId;
	}

	/**
	 * Handle failed funds out recheck.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFailedFundsOutRecheck(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsOutRequest.class);
		account = (Account) fundsOutRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_RECHECK_FAILURES", account, null,
				fundsOutRequest.getFundsOutId(), createdBy);
		return eventId;
	}

	/**
	 * Handle failed funds in recheck.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFailedFundsInRecheck(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsInCreateRequest fundsInRequest = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsInCreateRequest.class);
		account = (Account) fundsInRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSIN_RECHECK_FAILURES", account, fundsInRequest.getFundsInId(),
				null, createdBy);
		return eventId;
	}

	/**
	 * Creates the event serivce log for service failed funds out.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> createEventSerivceLogForServiceFailedFundsOut(Message<MessageContext> message) {

		try {
			List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();
			FundsOutRequest fundsOutRequest = (FundsOutRequest) message.getPayload().getGatewayMessageExchange()
					.getRequest();

			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				switch (exchange.getMessageExchange().getServiceTypeEnum()) {
				case INTERNAL_RULE_SERVICE:
					addIntrenalRuleServiceLog(message, eventServiceLogs, fundsOutRequest);
					break;
				case FRAUGSTER_SERVICE:
					addFraugsterLog(message, eventServiceLogs, fundsOutRequest);
					break;
				case SANCTION_SERVICE:
					addSanctionLog(message, eventServiceLogs, fundsOutRequest);
					break;
				case CUSTOM_CHECKS_SERVICE:
					addCustomCheckLog(message, eventServiceLogs, fundsOutRequest);
					break;
				case TRANSACTION_MONITORING_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE));
					break;
				default:
					break;
				}
			}
			saveIntoEventServiceLog(eventServiceLogs);
		} catch (Exception e) {
			LOG.error("Error in EventDBServiceImpl createEventSerivceLogForServiceFailedFundsOut()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Adds the custom check log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsOutRequest
	 *            the funds out request
	 */
	private void addCustomCheckLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsOutRequest fundsOutRequest) {
		Boolean isCustomCheckEligible = (Boolean) fundsOutRequest.getAdditionalAttribute(IS_CUSTOM_CHECK_ELIGIBLE);
		if (Boolean.TRUE.equals(isCustomCheckEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE));
		}
	}

	/**
	 * Adds the sanction log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsOutRequest
	 *            the funds out request
	 */
	private void addSanctionLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsOutRequest fundsOutRequest) {
		Boolean isSanctionEligible = (Boolean) fundsOutRequest.getAdditionalAttribute(IS_SANCTION_ELIGIBLE);
		if (Boolean.TRUE.equals(isSanctionEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.SANCTION_SERVICE));
		}
	}

	/**
	 * Adds the fraugster log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsOutRequest
	 *            the funds out request
	 */
	private void addFraugsterLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsOutRequest fundsOutRequest) {
		Boolean isFraugsterEligible = (Boolean) fundsOutRequest.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE);
		if (Boolean.TRUE.equals(isFraugsterEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.FRAUGSTER_SERVICE));
		}
	}

	/**
	 * Adds the intrenal rule service log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsOutRequest
	 *            the funds out request
	 */
	private void addIntrenalRuleServiceLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsOutRequest fundsOutRequest) {
		if ((Boolean) fundsOutRequest.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)
				|| (Boolean) fundsOutRequest.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.INTERNAL_RULE_SERVICE));
		}
	}

	/**
	 * Creates the event serivce log for service failed registration.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> createEventSerivceLogForServiceFailedRegistration(Message<MessageContext> message) {

		try {
			List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();
			RegistrationServiceRequest registrationDetails = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();

			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				switch (exchange.getMessageExchange().getServiceTypeEnum()) {
				case INTERNAL_RULE_SERVICE:
					addIntrenalRuleServiceLog(message, eventServiceLogs, registrationDetails);
					break;
				case FRAUGSTER_SERVICE:
					addFraugsterLog(message, eventServiceLogs, registrationDetails);
					break;
				case SANCTION_SERVICE:
					addSanctionLog(message, eventServiceLogs, registrationDetails);
					break;
				case KYC_SERVICE:
					addKYCLog(message, eventServiceLogs, registrationDetails);
					break;
				case TRANSACTION_MONITORING_UPDATE_SERVICE:
					eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_UPDATE_SERVICE));
					break;
				default:
					break;
				}
			}
			saveIntoEventServiceLog(eventServiceLogs);
		} catch (Exception e) {
			LOG.error("Error in EventDBServiceImpl createEventSerivceLogForServiceFailedFundsOut()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Adds the custom check log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param registrationDetails
	 *            the registration details
	 */
	private void addKYCLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			RegistrationServiceRequest registrationDetails) {
		Boolean isEidEligible = (Boolean) registrationDetails.getAdditionalAttribute(IS_EID_ELIGIBLE);
		if (Boolean.TRUE.equals(isEidEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.KYC_SERVICE));
		}
	}

	/**
	 * Adds the sanction log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param registrationDetails
	 *            the registration details
	 */
	private void addSanctionLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			RegistrationServiceRequest registrationDetails) {
		Boolean isSanctionEligible = (Boolean) registrationDetails.getAdditionalAttribute(IS_SANCTION_ELIGIBLE);
		if (Boolean.TRUE.equals(isSanctionEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.SANCTION_SERVICE));
		}
	}

	/**
	 * Adds the fraugster log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param registrationDetails
	 *            the registration details
	 */
	private void addFraugsterLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			RegistrationServiceRequest registrationDetails) {
		Boolean isFraugsterEligible = (Boolean) registrationDetails.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE);
		if (Boolean.TRUE.equals(isFraugsterEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.FRAUGSTER_SERVICE));
		}
	}

	/**
	 * Adds the intrenal rule service log.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param registrationDetails
	 *            the registration details
	 */
	private void addIntrenalRuleServiceLog(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			RegistrationServiceRequest registrationDetails) {
		if ((Boolean) registrationDetails.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)
				|| (Boolean) registrationDetails.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.INTERNAL_RULE_SERVICE));
		}
	}

	/**
	 * Creates the event serivce log for service failed funds in.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> createEventSerivceLogForServiceFailedFundsIn(Message<MessageContext> message) {

		try {
			List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();

			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				switch (exchange.getMessageExchange().getServiceTypeEnum()) {
				case INTERNAL_RULE_SERVICE:
					addIntrenalRuleServiceLogForFundsIn(message, eventServiceLogs, fundsInRequest);
					break;
				case FRAUGSTER_SERVICE:
					addFraugsterLogForFundsIn(message, eventServiceLogs, fundsInRequest);
					break;
				case SANCTION_SERVICE:
					addSanctionLogForFundsIn(message, eventServiceLogs, fundsInRequest);
					break;
				case CUSTOM_CHECKS_SERVICE:
					addCustomCheckLogForFundsIn(message, eventServiceLogs, fundsInRequest);
					break;
				case TRANSACTION_MONITORING_SERVICE:
					eventServiceLogs
							.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE));
					break;
				default:
					break;
				}
			}
			saveIntoEventServiceLog(eventServiceLogs);
		} catch (Exception e) {
			LOG.error("Error in EventDBServiceImpl createEventSerivceLogForServiceFailedFundsIn()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Adds the custom check log for funds in.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsInRequest
	 *            the funds in request
	 */
	private void addCustomCheckLogForFundsIn(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsInCreateRequest fundsInRequest) {
		Boolean isCustomCheckEligible = (Boolean) fundsInRequest.getAdditionalAttribute(IS_CUSTOM_CHECK_ELIGIBLE);
		if (Boolean.TRUE.equals(isCustomCheckEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE));
		}
	}

	/**
	 * Adds the sanction log for funds in.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsInRequest
	 *            the funds in request
	 */
	private void addSanctionLogForFundsIn(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsInCreateRequest fundsInRequest) {
		Boolean isSanctionEligible = (Boolean) fundsInRequest.getAdditionalAttribute(IS_SANCTION_ELIGIBLE);
		if (Boolean.TRUE.equals(isSanctionEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.SANCTION_SERVICE));
		}
	}

	/**
	 * Adds the fraugster log for funds in.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsInRequest
	 *            the funds in request
	 */
	private void addFraugsterLogForFundsIn(Message<MessageContext> message, List<EventServiceLog> eventServiceLogs,
			FundsInCreateRequest fundsInRequest) {
		Boolean isFraugsterEligible = (Boolean) fundsInRequest.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE);
		if (Boolean.TRUE.equals(isFraugsterEligible)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.FRAUGSTER_SERVICE));
		}
	}

	/**
	 * Adds the intrenal rule service log for funds in.
	 *
	 * @param message
	 *            the message
	 * @param eventServiceLogs
	 *            the event service logs
	 * @param fundsInRequest
	 *            the funds in request
	 */
	private void addIntrenalRuleServiceLogForFundsIn(Message<MessageContext> message,
			List<EventServiceLog> eventServiceLogs, FundsInCreateRequest fundsInRequest) {
		if ((Boolean) fundsInRequest.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)
				|| (Boolean) fundsInRequest.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			eventServiceLogs.addAll(getEventServiceLogsByService(message, ServiceTypeEnum.INTERNAL_RULE_SERVICE));
		}
	}
	
	private Integer handleDeleteContact(Message<MessageContext> message, Connection connection,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		DeleteContactRequest deleteContactRequest = message.getPayload().getGatewayMessageExchange()
				.getRequest(DeleteContactRequest.class);
		RegistrationServiceRequest regServiceRequest = (RegistrationServiceRequest)deleteContactRequest.getAdditionalAttribute("regRequest");
		eventId = saveIntoEvent(connection, deleteContactRequest.getOrgCode(), "PROFILE_DELETE_CONTACT", 
				regServiceRequest.getAccount(), null, null, createdBy);
		return eventId;
	}
	
	/**
	 * AT-2248 - Comp.Event/EventServiceLog table
	 * 
	 * Save into event service log summary.
	 *
	 * @param eventServiceLogs the event service logs
	 * @throws ComplianceException the compliance exception
	 */
	private void saveIntoEventServiceLogSummary(List<EventServiceLog> eventServiceLogs) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		List<EventServiceLogSummary> eventServiceLogSummaryList = new ArrayList<>(); 
		try {
			if(null != eventServiceLogs && !eventServiceLogs.isEmpty()) {
				Integer eventId = eventServiceLogs.get(0).getEventId();
				eventServiceLogSummaryList = getEventServiceLogDataForCurrentOperation(eventId);
				insertOrUpdateEventServiceLogSummary(eventServiceLogSummaryList);
			}
		} catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * AT-2248
	 * Gets the event service log data for current operation.
	 *
	 * @param eventId the event id
	 * @return the event service log data for current operation
	 * @throws ComplianceException the compliance exception
	 * @throws SQLException the SQL exception
	 */
	private List<EventServiceLogSummary> getEventServiceLogDataForCurrentOperation(Integer eventId) throws ComplianceException, SQLException {
		Connection connection = getConnection(Boolean.FALSE);
		ResultSet resultSet = null;
		List<EventServiceLogSummary> eslSummaryList = new ArrayList<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(DBQueryConstant.GET_CURRENT_OPERATION_RECENT_DATA_FROM_EVENT_SERVICE_LOG.getQuery())) {
			preparedStatement.setInt(1,eventId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				EventServiceLogSummary eslSummary = new EventServiceLogSummary();
				eslSummary.setEventServiceLogId(resultSet.getInt("EventServiceLogID"));
				eslSummary.setEntityType(resultSet.getInt(ENTITY_TYPE));
				eslSummary.setServiceType(resultSet.getInt("ServiceType"));
				eslSummary.setStatus(resultSet.getInt(STATUS));
				eslSummary.setEntityVersion(resultSet.getInt("EntityVersion"));
				eslSummary.setEntityId(resultSet.getInt("EntityID"));
				eslSummaryList.add(eslSummary);
			}
		}catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}finally {
			closeResultset(resultSet);
			closeConnection(connection);
		}
		return eslSummaryList;
	}
	
	/**
	 * AT-2248
	 * Insert or update event service log summary.
	 *
	 * @param eventServiceLogSummaryList the event service log summary list
	 * @throws ComplianceException the compliance exception
	 * @throws SQLException 
	 */
	private void insertOrUpdateEventServiceLogSummary(List<EventServiceLogSummary> eventServiceLogSummaryList) throws ComplianceException, SQLException{
		Connection connection = getConnection(Boolean.FALSE);
		try(PreparedStatement preparedStatement = connection.prepareStatement(DBQueryConstant.INSERT_UPDATE_INTO_EVENT_SERVICE_LOG_SUMMARY.getQuery())) {
			beginTransaction(connection);
			for(EventServiceLogSummary eventServiceLogSummary : eventServiceLogSummaryList) {
				if(!ServiceStatus.NOT_PERFORMED.getServiceStatusAsInteger().equals(eventServiceLogSummary.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.getServiceStatusAsInteger().equals(eventServiceLogSummary.getStatus())
						&& !ServiceStatus.NOT_REQUIRED.getServiceStatusAsInteger().equals(eventServiceLogSummary.getStatus())) {
					preparedStatement.setInt(1,eventServiceLogSummary.getEntityId());
					preparedStatement.setInt(2,eventServiceLogSummary.getEntityType());
					preparedStatement.setInt(3,eventServiceLogSummary.getServiceType());
					preparedStatement.setInt(4,eventServiceLogSummary.getEventServiceLogId());
					preparedStatement.setInt(5,eventServiceLogSummary.getStatus());
					preparedStatement.addBatch();
				}
			}
			preparedStatement.executeBatch();
			commitTransaction(connection);
		}catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}finally {
			closeConnection(connection);
		}
	}
	
	//Added for AT-3658
	/**
	 * Handle funds out payment reference resend.
	 *
	 * @param message
	 *            the message
	 * @param connection
	 *            the connection
	 * @param orgCode
	 *            the org code
	 * @param createdBy
	 *            the created by
	 * @return event id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer handleFundsOutPaymentReferenceResend(Message<MessageContext> message, Connection connection,
			String orgCode, Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account;
		FundsOutPaymentReferenceResendRequest fbResend = message.getPayload().getGatewayMessageExchange()
				.getRequest(FundsOutPaymentReferenceResendRequest.class);
		FundsOutRequest fRequest = (FundsOutRequest) fbResend.getAdditionalAttribute(Constants.OLD_REQUEST);
		account = (Account) fRequest.getAdditionalAttribute(ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_PAYMENT_REFERENCE_RESEND", account, null,
				fRequest.getFundsOutId(), createdBy);
		return eventId;
	}
	
	/**
	 * Creates the event TMMQ.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> createEventTMMQ(Message<MessageContext> message) {

		Connection connection = null;
		try {
			
			TransactionMonitoringMQRequest request = (TransactionMonitoringMQRequest) message.getPayload().getGatewayMessageExchange().getRequest();			
			
			String orgCode = request.getOrgCode();
			Integer createdBy = request.getCreatedBy();
			connection = getConnection(Boolean.FALSE);
			Integer eventId = null;
			beginTransaction(connection);
			eventId = handleTransactionMonitoringResend(message, connection, orgCode, createdBy);
		
			commitTransaction(connection);
			message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(Constants.FIELD_EVENTID,
					eventId);
		} catch (Exception e) {
			LOG.error("error in createEventTMMQ:", e);
			message.getPayload().setFailed(true);
		} finally {
			try {
				closeConnection(connection);
			} catch (ComplianceException e) {
				LOG.error("error in createEventTMMQ:", e);
				message.getPayload().setFailed(true);
			}
		}
		return message;
	}
	
	/**
	 * Handle transaction monitoring resend.
	 *
	 * @param message the message
	 * @param connection the connection
	 * @param orgCode the org code
	 * @param createdBy the created by
	 * @return the integer
	 * @throws ComplianceException the compliance exception
	 */
	private Integer handleTransactionMonitoringResend(Message<MessageContext> message, Connection connection,
			String orgCode, Integer createdBy) throws ComplianceException {
		Integer eventId;
		Account account = new Account();
		TransactionMonitoringMQRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(TransactionMonitoringMQRequest.class);
		account.setId(request.getAccountID());
		account.setVersion(1);
		Integer payInId = request.getRequestType().equalsIgnoreCase("payment_in") ||
				request.getRequestType().equalsIgnoreCase("payment_in_update")?request.getPaymentInID():null;
		Integer payOutId = request.getRequestType().equalsIgnoreCase("payment_out") ||
				request.getRequestType().equalsIgnoreCase("payment_out_update")?request.getPaymentOutID():null;
		
		eventId = saveIntoEvent(connection, orgCode, "TRANSACTION_MONITORING_MQ_RESEND", account, payInId,
				payOutId, createdBy);
		return eventId;
	}
	
	private Integer handleIntuitionUpdateAccount(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		RegistrationServiceRequest updateStatusRequest = message.getPayload().getGatewayMessageExchange()
				.getRequest(RegistrationServiceRequest.class);
		eventId = saveIntoEvent(connection, orgCode, "PROFILE_UPDATE_ACCOUNT", 
				updateStatusRequest.getAccount(), null, null, createdBy);
		return eventId;
	}
	
	/**
	 * add for AT-5424
	 * 
	 * Handle card check eligibility.
	 *
	 * @param message the message
	 * @param connection the connectionn
	 * @param orgCode the org code
	 * @param createdBy the created by
	 * @return the integer
	 * @throws ComplianceException the compliance exception
	 */
	private Integer handleCardCheckEligibility(Message<MessageContext> message, Connection connection, String orgCode,
			Integer createdBy) throws ComplianceException {
		Integer eventId;
		RegistrationServiceRequest updateStatusRequest = message.getPayload().getGatewayMessageExchange()
				.getRequest(RegistrationServiceRequest.class);
		eventId = saveIntoEvent(connection, orgCode, "CARD_APPLY", 
				updateStatusRequest.getAccount(), null, null, createdBy);
		return eventId;
	}
	
	/**
	 * Creates the transaction monitoring event serivce log.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> createTransactionMonitoringEventSerivceLog(Message<MessageContext> message) {

		try {
			List<EventServiceLog> eventServiceLogs = new CopyOnWriteArrayList<>();

			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				if (exchange.getMessageExchange().getServiceTypeEnum() == ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE) {
					eventServiceLogs.addAll(
							getEventServiceLogsByService(message, ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE));
					break;
				}
			}
			saveIntoEventServiceLog(eventServiceLogs);
			saveIntoEventServiceLogSummary(eventServiceLogs);
		} catch (Exception e) {
			LOG.error("Error in EventDBServiceImpl createTransactionMonitoringEventSerivceLog()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	//AT-4451
		private Integer handleFundsOutIntuitionRepeatCheck(Message<MessageContext> message, Connection connection,
				String orgCode, Integer createdBy) throws ComplianceException {
			Integer eventId;
			Account account;
			FundsOutRequest fRequest = message.getPayload().getGatewayMessageExchange()
					.getRequest(FundsOutRequest.class);
			account = (Account) fRequest.getAdditionalAttribute(ACCOUNT);
			eventId = saveIntoEvent(connection, orgCode, "FUNDSOUT_UPDATE", account, null,
					fRequest.getFundsOutId(), createdBy);
			return eventId;
		}
		
		/**
		 * Handle payment intuition manual update.
		 *
		 * @param message the message
		 * @param connection the connection
		 * @param orgCode the org code
		 * @param createdBy the created by
		 * @return the integer
		 * @throws ComplianceException 
		 */
		//AT-4749
		private Integer handlePaymentIntuitionManualUpdate(Message<MessageContext> message, Connection connection,
				String orgCode, Integer createdBy) throws ComplianceException {
			IntuitionPaymentStatusUpdateRequest request = message.getPayload().getGatewayMessageExchange()
					.getRequest(IntuitionPaymentStatusUpdateRequest.class);
			Integer eventId;
			Account account = (Account) request.getAdditionalAttribute(ACCOUNT);

			if (request.getTrxType().equalsIgnoreCase(FUNDS_IN)) {
				FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request
						.getAdditionalAttribute("fundsInRequest");
				eventId = saveIntoEvent(connection, orgCode, "INTUITION_UPDATE", account, fundsInRequest.getFundsInId(),
						null, createdBy);
			} else {
				FundsOutRequest fundsOutRequest = (FundsOutRequest) request.getAdditionalAttribute("fundsOutRequest");
				eventId = saveIntoEvent(connection, orgCode, "INTUITION_UPDATE", account, null,
						fundsOutRequest.getFundsOutId(), createdBy);
			}

			return eventId;
		}
		
		/**
		 * Creates the intuition payment update event serivce log.
		 *
		 * @param message the message
		 * @return the message
		 */
		//AT-4749
		public Message<MessageContext> createIntuitionPaymentUpdateEventSerivceLog(Message<MessageContext> message) {

			try {
				List<EventServiceLog> eventServiceLogs;

				IntuitionPaymentStatusUpdateRequest request = message.getPayload().getGatewayMessageExchange()
						.getRequest(IntuitionPaymentStatusUpdateRequest.class);
				Integer fundsId = 0;

				if (request.getTrxType().equalsIgnoreCase(FUNDS_IN)) {
					FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request
							.getAdditionalAttribute("fundsInRequest");
					fundsId = fundsInRequest.getFundsInId();
				} else {
					FundsOutRequest fundsOutRequest = (FundsOutRequest) request
							.getAdditionalAttribute("fundsOutRequest");
					fundsId = fundsOutRequest.getFundsOutId();
				}

				eventServiceLogs = createEventServiceLogEntryForPaymentInIntuitionManualUpdate(message, request,
						fundsId);

				saveIntoEventServiceLog(eventServiceLogs);
				saveIntoEventServiceLogSummary(eventServiceLogs);
			} catch (Exception e) {
				LOG.error("Error in EventDBServiceImpl createTransactionMonitoringEventSerivceLog()", e);
				message.getPayload().setFailed(true);
			}
			return message;
		}

		/**
		 * Creates the event service log entry for payment in intuition manual update.
		 *
		 * @param message the message
		 * @param request the request
		 * @param fundsId 
		 * @return 
		 */
		//AT-4749
		private List<EventServiceLog> createEventServiceLogEntryForPaymentInIntuitionManualUpdate(
				Message<MessageContext> message, IntuitionPaymentStatusUpdateRequest request, Integer fundsId) {
			List<EventServiceLog> eventServiceLogs = new ArrayList<>();
			//UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			Account account = (Account) request.getAdditionalAttribute(ACCOUNT);

			TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
			TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();

			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute("eventId");

			EventServiceLog eventServiceLog = new EventServiceLog();
			summary.setStatus(request.getStatus());
			summary.setFundsInId(fundsId);
			summary.setRuleScore(request.getRuleScore());
			summary.setRuleRiskLevel(request.getRuleRiskLevel());
			summary.setClientRiskLevel(request.getClientRiskLevel());
			summary.setClientRiskScore(request.getClientRiskScore());
			summary.setCorrelationId(request.getCorrelationID().toString());
			summary.setUserId(request.getUserId());
			summary.setAction(request.getAction());//Add for AT-4880

			tmProviderResponse.setId(request.getId());
			tmProviderResponse.setRulesTriggered(request.getRulesTriggered());
			tmProviderResponse.setStatus(request.getStatus());
			tmProviderResponse.setRuleScore(request.getRuleScore());
			tmProviderResponse.setClientRiskScore(request.getClientRiskScore());
			tmProviderResponse.setRuleRiskLevel(request.getRuleRiskLevel());
			tmProviderResponse.setClientRiskLevel(request.getClientRiskLevel());
			tmProviderResponse.setCorrelationId(request.getCorrelationID().toString());
			tmProviderResponse.setAction(request.getAction());//Add for AT-4880

			String status = "FAIL";
			if (request.getAction().equalsIgnoreCase("Clean") || request.getAction().equalsIgnoreCase("Clear"))
				status = "PASS";

			eventServiceLog.setEventId(eventId);
			eventServiceLog.setEntityType(EntityEnum.ACCOUNT.name());
			eventServiceLog.setServiceName(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE.getShortName());
			if (request.getTrxType().equalsIgnoreCase(FUNDS_IN)) {
				eventServiceLog.setServiceProviderName(ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSIN.name());
			} else {
				eventServiceLog.setServiceProviderName(ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSOUT.name());
			}
			eventServiceLog.setEntityId(fundsId);
			eventServiceLog.setEntityVersion(account.getVersion());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
			eventServiceLog.setStatus(status);
			eventServiceLog.setCratedBy(1);
			eventServiceLog.setUpdatedBy(1);
			eventServiceLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

			eventServiceLogs.add(eventServiceLog);

			return eventServiceLogs;

		}
}
