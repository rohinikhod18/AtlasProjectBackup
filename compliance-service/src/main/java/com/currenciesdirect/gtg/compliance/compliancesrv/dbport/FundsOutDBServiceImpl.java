package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.ReprocessFailedSanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class FundsOutDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
public class FundsOutDBServiceImpl extends PaymentsAbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FundsOutDBServiceImpl.class);

	public static final String ENTITY_TYPE = "EntityType";
	private static final String ACCOUNTID = "accountid";
	private static final String EXCEPTION_IN_SAVE_FUNDS_OUT_REQUEST = "Exception in saveFundsOutRequest()";
	private static final String WATCHLISTREASONNAME = "watchlistreasonname";
	private static final String REASONS_OF_WATCHLIST = "reasonsOfWatchlist";
	private static final String PO_ATTRIB ="POAttrib";
	private static final String CONTACT_ID ="ContactID";
	private static final String PO_COMPLIANCE_STATUS ="POComplianceStatus";
	private static final String ACCOUNT = "account";
	private static final String CONTACTID ="contactid";
	private static final String CONTACT="contact";
	private static final String WATCHLIST_ID="watchlistid";
	private static final String BLACKLIST_STATUS = "BlacklistStatus";
	
	/** The Constant FUNDS_OUT_ID. */
	private static final String FUNDS_OUT_ID = "fundsoutId";
	
	/** The Constant TRADE_CONTACT_ID. */
	private static final String TRADE_CONTACT_ID = "TradeContactID";
	
	/** The Constant ACCOUNT_TM_FLAG. */
	private static final String ACCOUNT_TM_FLAG = "AccountTMFlag";
	
	/** The Constant CLIENT_RISK_LEVEL. */
	private static final String CLIENT_RISK_LEVEL = "ClientRiskLevel";
	
	/** The Constant FRAUGSTER_STATUS. */
	private static final String FRAUGSTER_STATUS = "FraugsterStatus";
	
	/** The Constant CUSTOM_CHECK_STATUS. */
	private static final String CUSTOM_CHECK_STATUS = "CustomCheckStatus";
	
	/** The Constant PAYMENT_REFERENCE_STATUS. */
	private static final String PAYMENT_REFERENCE_STATUS = "PaymentReferenceStatus";
	
	/** The Constant COMPLIANCE_STATUS. */
	private static final String COMPLIANCE_STATUS = "ComplianceStatus";
	
	/** The Constant ACCOUNT_VERSION. */
	private static final String ACCOUNT_VERSION = "AccountVersion";
	
	/**
	 * Does funds out exists.
	 *
	 * @param paymentFundsoutId
	 *            the payment fundsout id
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Integer doesFundsOutExists(Integer paymentFundsoutId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement poStatment = null;
		ResultSet poRS = null;
		try {
			conn = getConnection(Boolean.TRUE);
			poStatment = conn.prepareStatement(DBQueryConstant.DOES_FUNDS_OUT_EXIST.getQuery());
			poStatment.setInt(1, paymentFundsoutId);

			poRS = poStatment.executeQuery();
			if (poRS.next()) {
				return poRS.getInt(1);
			}

			closeResultset(poRS);
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(poRS);
			closePrepareStatement(poStatment);
			closeConnection(conn);
		}
		return null;
	}

	/**
	 * Following method calls two function which returns whether for given
	 * trade account number & organization account & contact is present and
	 * whether for given organization funds out id is present.
	 *
	 * @param fundsOutBaseRequest
	 *            the funds out base request
	 * @return the funds out details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsOutRequest getFundsOutDetails(FundsOutBaseRequest fundsOutBaseRequest) throws ComplianceException {
		Connection conn = null;
		FundsOutRequest details = new FundsOutRequest();
		try {
			conn = getConnection(Boolean.TRUE);

			if(fundsOutBaseRequest instanceof FundsOutSanctionResendRequest){
				FundsOutSanctionResendRequest resendRequest=(FundsOutSanctionResendRequest)fundsOutBaseRequest;
				details = getExistingFundsOutId(conn, fundsOutBaseRequest.getOrgCode(),
						resendRequest.getTradePaymentId());
			}else{
				details = getExistingFundsOutId(conn, fundsOutBaseRequest.getOrgCode(),
						fundsOutBaseRequest.getPaymentFundsoutId());
			}
			
			getFundsOutContactAccountDetails(details, conn, fundsOutBaseRequest.getTradeAccountNumber(),
					fundsOutBaseRequest.getOrgCode());

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeConnection(conn);
		}
		return details;
	}
	
	
	
	/**
	 * Following method calls two functions which returns whether for given
	 * trade account number & organization account & contact is present and
	 * whether for given organization funds out id is present.
	 *
	 * @param fundsOutBaseRequest
	 *            the funds out base request
	 * @return the funds out details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsOutRequest getFundsOutDetailsForBlacklistRepeatCheck(FundsOutBaseRequest fundsOutBaseRequest) throws ComplianceException {
		Connection conn = null;
		FundsOutRequest details = new FundsOutRequest();
		try {
			   conn = getConnection(Boolean.TRUE);
               details = getExistingFundsOutIdForBlacklistRepeatCheck(conn, fundsOutBaseRequest.getOrgCode(),
						fundsOutBaseRequest.getPaymentFundsoutId());
			   getFundsOutContactAccountDetails(details, conn, fundsOutBaseRequest.getTradeAccountNumber(),
					fundsOutBaseRequest.getOrgCode());

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeConnection(conn);
		}
		return details;
	}
	
	private FundsOutRequest getExistingFundsOutIdForBlacklistRepeatCheck(Connection conn, String orgCode, Integer fundsOutId)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		FundsOutRequest details = new FundsOutRequest();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_ID_FOR_BLACKLIST.getQuery());
			preparedStatment.setInt(1, fundsOutId);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				String json = rs.getString(PO_ATTRIB);
				if (null != json) {
					details = JsonConverterUtil.convertToObject(FundsOutRequest.class, rs.getString(PO_ATTRIB));
				}
				details.setFundsOutId(rs.getInt(FUNDS_OUT_ID));
				details.setAccId(rs.getInt(ACCOUNTID));
				details.setContactId(rs.getInt(CONTACT_ID));
				details.addAttribute(Constants.STATUS, rs.getString(PO_COMPLIANCE_STATUS));
				details.addAttribute(Constants.BLACKLIST_STATUS, ServiceStatus.getStatusAsString(rs.getInt(BLACKLIST_STATUS)));
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}
		return details;
	}

	/**
	 * Gets the funds out contact account details.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @param tradeAccountNumber
	 *            the trade account number
	 * @param orgCode
	 *            the org code
	 * @return the funds out contact account details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	void getFundsOutContactAccountDetails(FundsOutRequest request, Connection conn, String tradeAccountNumber,
			String orgCode) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String tradeAccNumber = StringUtils.isNullOrEmpty(tradeAccountNumber)?request.getTrade().getTradeAccountNumber():tradeAccountNumber;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_ACCOUNT_CONTACT_DETAILS.getQuery());
			preparedStatment.setString(1, orgCode);
			preparedStatment.setString(2, tradeAccNumber);
			preparedStatment.setString(3, orgCode);
			rs = preparedStatment.executeQuery();
			Integer count = 1;
			Integer noOfContacts = 0;
			List<String> watchListDetails = new ArrayList<>();
			MultiMap multiMapWatchlistCon = new MultiHashMap();//Add for AT-2986
			
			while (rs.next()) {

				request.setOrgId(rs.getInt("requestOrgID"));
				Account acc = JsonConverterUtil.convertToObject(Account.class, rs.getString("acattrib"));
				acc.setCustomerType(rs.getInt("custType"));
				acc.setId(rs.getInt(ACCOUNTID));
				acc.setAccountStatus(rs.getString("accountStatus"));
				request.setAccId(acc.getId());
				request.addAttribute(ACCOUNT, acc);
				request.addAttribute(Constants.LE_UPDATE_DATE, rs.getString("leupdatedate")); //Add for AT-3349
				Contact con = JsonConverterUtil.convertToObject(Contact.class, rs.getString("contactattrib"));
				con.setId(rs.getInt(CONTACTID));
				con.setContactStatus(rs.getString("ContactStatus"));
				con.setPoiExists(rs.getInt("poiexists"));	//Add for AT-3349	
				request.addAttribute(CONTACT + count, con);
				if (null != rs.getString(WATCHLISTREASONNAME) && !rs.getString(WATCHLISTREASONNAME).isEmpty()) {
					request.addAttribute(WATCHLIST_ID + count, rs.getInt(WATCHLIST_ID));
					watchListDetails.add(rs.getString(WATCHLISTREASONNAME));
				}
				multiMapWatchlistCon.put(rs.getInt(TRADE_CONTACT_ID), rs.getString(WATCHLISTREASONNAME));
				request.addAttribute(ACCOUNT_TM_FLAG, rs.getInt(ACCOUNT_TM_FLAG));
				request.addAttribute("DormantFlag", rs.getInt("DormantFlag"));
				
				count++;
				noOfContacts++;
			}
			request.addAttribute("noOfContacts", noOfContacts);
			request.addAttribute(REASONS_OF_WATCHLIST, watchListDetails);
			request.addAttribute("watchlist_with_trade&cont", multiMapWatchlistCon);
		} catch (Exception e) {
			LOG.error(" ", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}

	}

	/**
	 * Save funds out request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> saveFundsOutRequest(Message<MessageContext> message) {
		Connection conn = null;
		try {
			FundsOutRequest fundsOutRequest = (FundsOutRequest) message.getPayload().getGatewayMessageExchange()
					.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			Account accountID = (Account) fundsOutRequest.getAdditionalAttribute(ACCOUNT);
			Contact contactID = (Contact) fundsOutRequest.getAdditionalAttribute(CONTACT);
			conn = getConnection(Boolean.FALSE);
			beginTransaction(conn);
			saveIntoPaymentsOut(conn, fundsOutRequest, accountID.getId(), contactID.getId(), token.getUserID());
			saveIntoPaymentsOutAttribute(conn, fundsOutRequest, token.getUserID());
			commitTransaction(conn);
		} catch (Exception e) {
			try {
				transactionRolledBack(conn);
			} catch (ComplianceException e1) {
				LOG.error(EXCEPTION_IN_SAVE_FUNDS_OUT_REQUEST, e1);
			}
			LOG.error(EXCEPTION_IN_SAVE_FUNDS_OUT_REQUEST, e);
			message.getPayload().setFailed(true);
		} finally {
			try {
				closeConnection(conn);
			} catch (ComplianceException e) {
				LOG.error(EXCEPTION_IN_SAVE_FUNDS_OUT_REQUEST, e);
			}
		}
		return message;
	}

	/**
	 * Save into payments out.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutRequest
	 *            the funds out request
	 * @param accountID
	 *            the account ID
	 * @param contactID
	 *            the contact ID
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoPaymentsOut(Connection conn, FundsOutRequest fundsOutRequest, Integer accountID,
			Integer contactID, Integer user) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			// [OrganizationID], [AccountID], [ContactID], [ContractNumber],
			// [TradePaymentID], [ComplianceStatus],
			// [ComplianceDoneOn], [Deleted], [CreatedBy], [CreatedOn],
			// [UpdatedBy], [UpdatedOn]

			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_INTO_PAYMENTS_OUT.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setString(1, fundsOutRequest.getOrgCode());
			preparedStatment.setInt(2, accountID);
			preparedStatment.setInt(3, contactID);
			preparedStatment.setString(4, fundsOutRequest.getTrade().getContractNumber());
			preparedStatment.setInt(5, fundsOutRequest.getBeneficiary().getPaymentFundsoutId());
			preparedStatment.setString(6, FundsOutComplianceStatus.HOLD.name());
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setBoolean(8, false);
			preparedStatment.setInt(9, user);
			preparedStatment.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(11, user);
			preparedStatment.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(13, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(14, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(15, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(16, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setTimestamp(17, DateTimeFormatter
					.convertStringToTimestamp(fundsOutRequest.getBeneficiary().getTransactionDateTime()));
			preparedStatment.setBoolean(18, false);

			// Set STPFlag
			preparedStatment.setBoolean(19, false);
			preparedStatment.setString(20, fundsOutRequest.getTrade().getCustLegalEntity());
			preparedStatment.setInt(21, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.execute();
			rs = preparedStatment.getGeneratedKeys();
			if (rs.next()) {
				fundsOutRequest.setFundsOutId(rs.getInt(1));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Save into payments out attribute.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutRequest
	 *            the funds out request
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoPaymentsOutAttribute(Connection conn, FundsOutRequest fundsOutRequest, Integer userID)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_INTO_PAYMENTS_OUT_ATTRIBUTE.getQuery());
			preparedStatment.setInt(1, fundsOutRequest.getFundsOutId());
			preparedStatment.setInt(2, 1);
			preparedStatment.setString(3, JsonConverterUtil.convertToJsonWithoutNull(fundsOutRequest));
			preparedStatment.setInt(4, userID);
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(6, userID);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setDouble(8, fundsOutRequest.getBeneficiary().getAmount());
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Save into payments out attribute history.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutId
	 *            the funds out id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoPaymentsOutAttributeHistory(Connection conn, Integer fundsOutId, Integer userID)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			// [PaymentOutID], [Version], [Attributes], [CreatedBy],
			// [CreatedOn], [UpdatedBy], [UpdatedOn]

			preparedStatment = conn
					.prepareStatement(DBQueryConstant.INSERT_INTO_PAYMENTS_OUT_ATTRIBUTE_HISTORY.getQuery());
			preparedStatment.setInt(1, fundsOutId);
			preparedStatment.setInt(2, fundsOutId);
			preparedStatment.setInt(3, fundsOutId);

			preparedStatment.setInt(4, userID);
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(6, userID);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Save funds out update request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> saveFundsOutUpdateRequest(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {
			FundsOutUpdateRequest fundsOutRequest = (FundsOutUpdateRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			FundsOutRequest oldReuqest = (FundsOutRequest) fundsOutRequest.getAdditionalAttribute("oldRequest");
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			saveIntoPaymentsOutAttributeHistory(conn, oldReuqest.getFundsOutId(), token.getUserID());
			updateIntoPaymentsOut(conn, oldReuqest.getFundsOutId(), token.getUserID());
			updateIntoPaymentsOutAttribute(conn, oldReuqest, token.getUserID());
		} catch (Exception e) {
			LOG.error("", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}
		return message;
	}

	/**
	 * Update into payments out.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutId
	 *            the funds out id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateIntoPaymentsOut(Connection conn, Integer fundsOutId, Integer userID) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT.getQuery());
			preparedStatment.setInt(1, userID);
			preparedStatment.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(3, fundsOutId);
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Update into payments out attribute.
	 *
	 * @param conn
	 *            the conn
	 * @param request
	 *            the request
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateIntoPaymentsOutAttribute(Connection conn, FundsOutRequest request, Integer userID)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			// [Attributes]=?, [UpdatedBy]=?, [UpdatedOn]=? WHERE id = ?
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_ATTRIBUTE.getQuery());
			preparedStatment.setString(1, JsonConverterUtil.convertToJsonWithoutNull(request));
			preparedStatment.setInt(2, userID);
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setDouble(4, request.getBeneficiary().getAmount());
			preparedStatment.setInt(5, request.getFundsOutId());
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
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
	public Message<MessageContext> saveFundsOutDeleteRequest(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {
			FundsOutDeleteRequest fundsOutRequest = (FundsOutDeleteRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			deletePaymentsOut(conn, fundsOutRequest, token.getUserID());
		} catch (Exception e) {
			LOG.error("", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}
		return message;
	}

	/**
	 * Delete payments out.
	 *
	 * @param conn
	 *            the conn
	 * @param fDeleteRequest
	 *            the f delete request
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void deletePaymentsOut(Connection conn, FundsOutDeleteRequest fDeleteRequest, Integer userID)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.DELETE_FUNDS_OUT.getQuery());
			preparedStatment.setString(1, FundsOutComplianceStatus.REVERSED.name());
			preparedStatment.setInt(2, userID);
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, fDeleteRequest.getPaymentFundsoutId());
			preparedStatment.execute();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Update payment out status.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updatePaymentOutStatus(Message<MessageContext> message) throws ComplianceException {
		Connection conn = null;

		try {
			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			FundsOutBaseRequest fRequest = (FundsOutBaseRequest) exchange.getRequest();
			OperationEnum operation = exchange.getOperation();
			FundsOutResponse fundsOutResponse = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
					.getResponse();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			getContactWatchlist(conn, fRequest.getFundsOutId(), fundsOutResponse);//Add for AT-3470
			updateStatusIntoPaymetOut(conn, fundsOutResponse, fRequest.getFundsOutId(), token.getUserID(), operation);
			updateIntoPaymentOutStatusReason(conn, fundsOutResponse, fRequest.getFundsOutId(), token.getUserID());

		} catch (Exception e) {
			LOG.error("Exception in updatePaymentOutStatus()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}
		return message;
	}
	
	/**
	 * Gets the contact watchlist.
	 *
	 * @param conn the conn
	 * @param id the id
	 * @param fundsInCreateResponse the funds in create response
	 * @return the contact watchlist
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void getContactWatchlist(Connection conn, Integer id, FundsOutResponse fundsOutResponse) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("000");
		
		try {
			
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_CONTACT_WATCHLIST_FUNDS_OUT.getQuery());
			preparedStatment.setInt(1, id);
			rs = preparedStatment.executeQuery();
			while(rs.next()) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				String watchListReason=rs.getString("Reason");
				if(!watchListReason.contains("US Client List B client")) {
					if(rs.getInt("StopPaymentOut") == 1) {
						if(fundsOutResponse.getShortResponseCode() != null && fundsOutResponse.getShortResponseCode().contains("NSTP")) {
							sb.append("WR").append(df.format(rs.getInt("ID")));
						}else {
							fundsOutResponse.setShortResponseCode("NSTP");
							sb.append("WR").append(df.format(rs.getInt("ID")));
						}
					}else {
						sb.append("WA").append(df.format(rs.getInt("ID")));
						}
				}else if(WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(watchListReason)) {
					setShortCodeForUsClientListBWL(fundsOutResponse, rs, sb, df);
				}
			}
			if (sb.length() != 0 && fundsOutResponse.getShortResponseCode() != null) {
				String watch = fundsOutResponse.getShortResponseCode().concat(";").concat(sb.toString());
				fundsOutResponse.setShortResponseCode(watch);
			}
			
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeResultset(rs);
		}
	}

	private void setShortCodeForUsClientListBWL(FundsOutResponse fundsOutResponse, ResultSet rs, StringBuilder sb,
			DecimalFormat df) throws SQLException {
		if (Boolean.TRUE.equals(fundsOutResponse.getUsClientListBClientWLFlag())) {
			if (fundsOutResponse.getShortResponseCode() != null && fundsOutResponse.getShortResponseCode().contains("NSTP")) {
				sb.append("WR").append(df.format(rs.getInt("ID")));
			} else {
				fundsOutResponse.setShortResponseCode("NSTP");
				sb.append("WR").append(df.format(rs.getInt("ID")));
			}
		} else {
			sb.append("WA").append(df.format(rs.getInt("ID")));
		}
	}

	/**
	 * Update status into paymet out.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutResponse
	 *            the funds out response
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @param operation
	 *            the operation
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateStatusIntoPaymetOut(Connection conn, FundsOutResponse fundsOutResponse, Integer id,
			Integer userID, OperationEnum operation) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			/*
			 * if operation is FUNDS_OUT use query UPDATE_FUNDS_OUT_STATUS else
			 * for UPDATE_OPI and DELETE_OPI use query
			 * UPDATE_FUNDS_OUT_STATUS_FOR_UPDATE_DELETE_API . check added to
			 * update service statuses in Paymentout table : Abhijit G
			 */
			if (operation == OperationEnum.FUNDS_OUT) {
				Boolean paymentOutStatusHold = fundsOutResponse.getStatus().equals(FundsOutComplianceStatus.HOLD.name());
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_STATUS.getQuery());
				preparedStatment.setString(1, fundsOutResponse.getStatus());
				preparedStatment.setInt(2,
						ServiceStatus.getStatusAsInteger(fundsOutResponse.getBlacklistCheckStatus()));
				preparedStatment.setInt(3,
						ServiceStatus.getStatusAsInteger(fundsOutResponse.getFraugsterCheckStatus()));
				preparedStatment.setInt(4, ServiceStatus.getStatusAsInteger(fundsOutResponse.getSanctionCheckStatus()));
				preparedStatment.setInt(5, ServiceStatus.getStatusAsInteger(fundsOutResponse.getCustomCheckStatus()));
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, userID);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setBoolean(9, paymentOutStatusHold);
				preparedStatment.setBoolean(10, fundsOutResponse.getSTPFlag());				// set STPFlag
				preparedStatment.setString(11, fundsOutResponse.getShortResponseCode());//Add for AT-3470
				//Add for AT-3649
				preparedStatment.setInt(12, ServiceStatus.getStatusAsInteger(fundsOutResponse.getPaymentReferenceCheckStatus()));
				preparedStatment.setString(13,
						fundsOutResponse.getAdditionalAttribute(CLIENT_RISK_LEVEL) != null
								? (String) fundsOutResponse.getAdditionalAttribute(CLIENT_RISK_LEVEL)
								: null); //AT-4451
				preparedStatment.setInt(14, ServiceStatus.getStatusAsInteger(fundsOutResponse.getIntuitionCheckStatus()));
				preparedStatment.setInt(15, id);
				preparedStatment.execute();
			} else if(operation == OperationEnum.RECHECK_FAILURES) {
				Boolean paymentOutStatusHold = fundsOutResponse.getStatus().equals(FundsOutComplianceStatus.HOLD.name());
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_STATUS_WITHOUT_INITITAL_STATUS.getQuery());
				preparedStatment.setString(1, fundsOutResponse.getStatus());
				preparedStatment.setInt(2,
						ServiceStatus.getStatusAsInteger(fundsOutResponse.getBlacklistCheckStatus()));
				preparedStatment.setInt(3,
						ServiceStatus.getStatusAsInteger(fundsOutResponse.getFraugsterCheckStatus()));
				preparedStatment.setInt(4, ServiceStatus.getStatusAsInteger(fundsOutResponse.getSanctionCheckStatus()));
				preparedStatment.setInt(5, ServiceStatus.getStatusAsInteger(fundsOutResponse.getCustomCheckStatus()));
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, userID);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setBoolean(9, paymentOutStatusHold);
				preparedStatment.setBoolean(10, fundsOutResponse.getSTPFlag());				// set STPFlag
				//Add for AT-3649
				preparedStatment.setInt(11, ServiceStatus.getStatusAsInteger(fundsOutResponse.getPaymentReferenceCheckStatus()));
				preparedStatment.setInt(12, ServiceStatus.getStatusAsInteger(fundsOutResponse.getIntuitionCheckStatus()));
				preparedStatment.setInt(13, id);
				preparedStatment.execute();
			} else {
				preparedStatment = conn
						.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_STATUS_FOR_UPDATE_DELETE_API.getQuery());
				preparedStatment.setString(1, fundsOutResponse.getStatus());
				preparedStatment.setInt(2, id);
				preparedStatment.setInt(3, id);
				preparedStatment.setInt(4, id);
				preparedStatment.setInt(5, id);
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, userID);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(9, id);
				preparedStatment.execute();
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Update into payment out status reason.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutResponse
	 *            the funds out response
	 * @param id
	 *            the id
	 * @param user
	 *            the user
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateIntoPaymentOutStatusReason(Connection conn, FundsOutResponse fundsOutResponse, Integer id,
			Integer user) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			if (null != fundsOutResponse.getResponseReason()
					&& null != fundsOutResponse.getResponseReason().getFundsOutReasonShort()) {
				preparedStatment = conn
						.prepareStatement(DBQueryConstant.INSERT_INTO_FUNDS_OUT_STATUS_REASON.getQuery());
				preparedStatment.setInt(1, id);
				preparedStatment.setString(2, "ALL");
				preparedStatment.setString(3, fundsOutResponse.getResponseReason().getFundsOutReasonShort());
				preparedStatment.setInt(4, user);
				preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(6, user);
				preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				preparedStatment.execute();
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Gets the payment out request by id.
	 *
	 * @param fundsOutId
	 *            the funds out id
	 * @return the payment out request by id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsOutRequest getFundsOutRequestById(Integer fundsOutId) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		Connection connection = null;
		ResultSet rs = null;
		FundsOutRequest fundsOutRequest = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatment = connection
					.prepareStatement(DBQueryConstant.GET_FUNDSOUT_REQUEST_BY_PAYMENTOUTID.getQuery());
			preparedStatment.setInt(1, fundsOutId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				fundsOutRequest = JsonConverterUtil.convertToObject(FundsOutRequest.class, rs.getString("Attributes"));
				fundsOutRequest.setAccId(rs.getInt("AccountID"));
				fundsOutRequest.setContactId(rs.getInt(CONTACT_ID));
				return fundsOutRequest;
			}
			rs.close();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}

		return null;
	}

	/**
	 * Business -- Update sanction and customcheck status into paymentOut table
	 * after performing repeat check.
	 * 
	 * Implementation--- 1) If operation is SANCTION_RESEND then call
	 * updateSanctionServiceStatus() 1.a) Get SANCTION_OVERALLSTATUS as
	 * additional attribute to set sanction status on the basis of sanction
	 * contact, bank and beneficiary status. 2) If operation is
	 * CUSTOMCHECK_RESEND then call updateCustomCheckServiceStatus()
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> updateServiceStatusIntoPaymetOut(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			FundsOutBaseRequest fRequest = (FundsOutBaseRequest) messageExchange.getRequest();
			String status;
			if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && operation == OperationEnum.SANCTION_RESEND) {
				SanctionResponse fResponse = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE)
						.getResponse(SanctionResponse.class);
				status = (String) fResponse.getAdditionalAttribute(Constants.SANCTION_OVERALLSTATUS);
				updateSanctionServiceStatus(status, fRequest.getFundsOutId(), token.getUserID());

			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT
					&& operation == OperationEnum.CUSTOMCHECK_RESEND) {
				CustomCheckResendResponse resendResponse = message.getPayload().getGatewayMessageExchange()
						.getResponse(CustomCheckResendResponse.class);
				status = resendResponse.getResponse().getOverallStatus();
				updateCustomCheckServiceStatus(status, fRequest.getFundsOutId(), token.getUserID());
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT
					&& operation == OperationEnum.FRAUGSTER_RESEND) {
				FundsOutFruagsterResendRequest resendRequest = message.getPayload().getGatewayMessageExchange()
						.getRequest(FundsOutFruagsterResendRequest.class);
				FundsOutRequest oldRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsOutRequest.class);
				MessageExchange fExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
				FraugsterPaymentsOutResponse fpResponse = fExchange.getResponse(FraugsterPaymentsOutResponse.class);
				status = fpResponse.getStatus();
				
				updateFraugsterServiceStatus(status, oldRequest.getFundsOutId(), token.getUserID());
			}
			else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT
					&& operation == OperationEnum.BLACKLIST_RESEND) {
				FundsOutBlacklistResendRequest resendRequest = message.getPayload().getGatewayMessageExchange()
						.getRequest(FundsOutBlacklistResendRequest.class);
				FundsOutRequest oldRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsOutRequest.class);
				MessageExchange fExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
				InternalServiceResponse fpResponse = (InternalServiceResponse) fExchange.getResponse();
				
				status = fpResponse.getContacts().get(0).getContactStatus();
				updateBlacklistServiceStatus(status, oldRequest.getFundsOutId(), token.getUserID());
			}
			//Added for AT-3658
			else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT
					&& operation == OperationEnum.PAYMENT_REFERENCE_RESEND) {
				FundsOutPaymentReferenceResendRequest resendRequest = message.getPayload().getGatewayMessageExchange()
						.getRequest(FundsOutPaymentReferenceResendRequest.class);
				FundsOutRequest oldRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsOutRequest.class);
				MessageExchange fExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
				InternalServiceResponse fpResponse = (InternalServiceResponse) fExchange.getResponse();
				
				status = fpResponse.getContacts().get(0).getContactStatus();
				updatePaymentReferenceServiceStatus(status, oldRequest.getFundsOutId(), token.getUserID());
			}
						
		} catch (Exception e) {
			LOG.error("Error while updating Service status for repeat check :: updateServiceStatusIntoPaymetOut() :: ",
					e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Update sanction service status.
	 *
	 * @param status
	 *            the status
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void updateSanctionServiceStatus(String status, Integer id, Integer userID) throws ComplianceException {
		Connection connection = null;
		PreparedStatement sanctionStatment = null;
		try {
			connection = getConnection(Boolean.FALSE);
			sanctionStatment = connection.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_SANCTION_STATUS.getQuery());
			sanctionStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			sanctionStatment.setInt(2, userID);
			sanctionStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			sanctionStatment.setInt(4, id);
			sanctionStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(sanctionStatment);
			closeConnection(connection);
		}
	}

	/**
	 * Update custom check service status.
	 *
	 * @param status
	 *            the status
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void updateCustomCheckServiceStatus(String status, Integer id, Integer userID) throws ComplianceException {
		Connection connection = null;
		PreparedStatement ccStatment = null;
		try {
			connection = getConnection(Boolean.FALSE);
			ccStatment = connection.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_CUSTOMCHECK_STATUS.getQuery());
			ccStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			ccStatment.setInt(2, userID);
			ccStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ccStatment.setInt(4, id);
			ccStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(ccStatment);
			closeConnection(connection);
		}
	}
	
	
	
	/**
	 * Update Fraugster service status.
	 *
	 * @param status
	 *            the status
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void updateFraugsterServiceStatus(String status, Integer id, Integer userID) throws ComplianceException {
		Connection connection = null;
		PreparedStatement fStatment = null;
		try {
			connection = getConnection(Boolean.FALSE);
			fStatment = connection.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_FRAUGSTER_STATUS.getQuery());
			fStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			fStatment.setInt(2, userID);
			fStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			fStatment.setInt(4, id);
			fStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(fStatment);
			closeConnection(connection);
		}
	}

	
	/**
	 * Update Blacklist service status.
	 *
	 * @param status
	 *            the status
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void updateBlacklistServiceStatus(String status, Integer id, Integer userID) throws ComplianceException {
		Connection connection = null;
		PreparedStatement fStatment = null;
		try {
			connection = getConnection(Boolean.FALSE);
			fStatment = connection.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_BLACKLIST_STATUS.getQuery());
			fStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			fStatment.setInt(2, userID);
			fStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			fStatment.setInt(4, id);
			fStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(fStatment);
			closeConnection(connection);
		}
	}
	
	/**
	 * Gets the existing funds out id.
	 *
	 * @param conn
	 *            the conn
	 * @param orgCode
	 *            the org code
	 * @param fundsOutId
	 *            the funds out id
	 * @return the existing funds out id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FundsOutRequest getExistingFundsOutId(Connection conn, String orgCode, Integer fundsOutId)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		FundsOutRequest details = new FundsOutRequest();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_ID.getQuery());
			preparedStatment.setInt(1, fundsOutId);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				String json = rs.getString(PO_ATTRIB);
				if (null != json) {
					details = JsonConverterUtil.convertToObject(FundsOutRequest.class, rs.getString(PO_ATTRIB));
				}
				details.setFundsOutId(rs.getInt(FUNDS_OUT_ID));
				details.setAccId(rs.getInt(ACCOUNTID));
				details.setContactId(rs.getInt(CONTACT_ID));
				details.setDeviceInfo(createDeviceInfoObject(rs));
				details.addAttribute(Constants.STATUS, rs.getString(PO_COMPLIANCE_STATUS));
				details.addAttribute(Constants.FRAUGSTER_STATUS, ServiceStatus.getStatusAsString(rs.getInt(FRAUGSTER_STATUS)));
				details.getTrade().setTradeAccountNumber(rs.getString("TradeAccountNumber"));
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}
		return details;
	}

	private DeviceInfo createDeviceInfoObject(ResultSet rs) throws SQLException{
		DeviceInfo dInfo = new DeviceInfo();
		dInfo.setBrowserLanguage(rs.getString("BrowserLanguage"));
		dInfo.setBrowserMajorVersion(rs.getString("BrowserVersion"));
		dInfo.setBrowserName(rs.getString("BrowserName"));
		
		if(null != rs.getString("BrowserOnline")) {
		   if(rs.getString("BrowserOnline").equals("1")) {
			dInfo.setBrowserOnline("true");
		   }
		else
			dInfo.setBrowserOnline("false");
		}
		
		dInfo.setScreenResolution(rs.getString("ScreenResolution"));
		dInfo.setCdAppId(rs.getString("CDAppID"));
		dInfo.setCdAppVersion(rs.getString("CDAppVersion"));
		dInfo.setDeviceId(rs.getString("DeviceID"));
		dInfo.setDeviceManufacturer(rs.getString("DeviceManufacturer"));
		dInfo.setDeviceName(rs.getString("DeviceName"));
		dInfo.setDeviceType(rs.getString("DeviceType"));
		dInfo.setDeviceVersion(rs.getString("DeviceVersion"));
		dInfo.setOsDateAndTime(rs.getString("OSTimestamp"));
		dInfo.setUserAgent(rs.getString("UserAgent"));
		
		return dInfo;
	}
	
	/**
	 * Business -- Update sanction status into paymentOut table after updating
	 * values from UI.
	 * 
	 * Implementation--- 1) If operation is SANCTION_UPDATE then call
	 * updateSanctionServiceStatus() 2) Get SANCTION_OVERALLSTATUS as additional
	 * attribute to set sanction status on the basis of sanction contact, bank
	 * and beneficiary status.
	 * 
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateFundsOutSanctionStatus(Message<MessageContext> message)
			throws ComplianceException {

		try {
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			if (operation == OperationEnum.SANCTION_UPDATE) {
				UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
				MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
				SanctionUpdateRequest sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);
				updateSanctionServiceStatus(
						(String) messageExchange.getResponse().getAdditionalAttribute(Constants.SANCTION_OVERALLSTATUS),
						sanctionUpdateRequest.getResourceId(), token.getUserID());
			}
		} catch (Exception e) {
			LOG.error("Error in FundsOutDBServiceImpl updateFundsOutSanctionStatus()", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	/**
	 * Gets the sanction failed records.
	 *
	 * @param request
	 *            the request
	 * @return the sanction failed records
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Set<FundsOutSanctionResendRequest> getSanctionFailedRecords(ReprocessFailedSanctionRequest request)
			throws ComplianceException {

		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Set<FundsOutSanctionResendRequest> listToProcess = new HashSet<>();

		try {

			String query = DBQueryConstant.GET_SANCTION_FAILED_RECORDS.getQuery();
			if (request.getBatchSize() == null || request.getBatchSize() < 1) {
				query = query.replace("{BATCH_SIZE}", "");
			} else {
				query = query.replace("{BATCH_SIZE}", " TOP " + request.getBatchSize() + " ");
			}

			if (request.getPaymentOutId() == null || request.getPaymentOutId() < 1) {
				query = query.replace("{PAYMENT_OUT_ID}", "");
			} else {
				query = query.replace("{PAYMENT_OUT_ID}", "  AND payout.id=" + request.getPaymentOutId() + " ");
			}

			if (request.getStartDate() == null || request.getEndDate() == null) {
				query = query.replace("{CREATEDON}", "");
			} else {
				query = query.replace("{CREATEDON}", "AND  payOut.createdOn BETWEEN '" + request.getStartDate()
						+ "' AND '" + request.getEndDate() + "'  ");
			}

			connection = getConnection(Boolean.TRUE);
			// EventID, accountid, c.EntityID,c.EntityType, paymentOutId,
			// sse.status,
			preparedStatment = connection.prepareStatement(query);
			rs = preparedStatment.executeQuery();
			
			Integer status = null;
			while (rs.next()) {
				status = rs.getInt(Constants.STATUS);
				if(null == status || status == 8 || status == 0 || status == 5) {
					buildResendRequest(rs, listToProcess);
				}
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}
		return listToProcess;
	}

	private void buildResendRequest(ResultSet rs, Set<FundsOutSanctionResendRequest> listToProcess)
			throws SQLException {
		Integer entityType = rs.getInt(ENTITY_TYPE);
		
		FundsOutSanctionResendRequest requestToProcess = new FundsOutSanctionResendRequest();
		requestToProcess.setEventId(rs.getInt("EventID"));
		requestToProcess.setAccountId(rs.getInt(ACCOUNTID));
		
		requestToProcess.setEntityType(EntityEnum.getEntityTypeAsString(entityType));
		requestToProcess.setPaymentOutId(rs.getInt("paymentOutId"));
		requestToProcess.setContactID(rs.getInt(CONTACTID));
		requestToProcess.setTradeContractNumber(rs.getString("ContractNumber"));
		requestToProcess.setOrgCode(rs.getString("Code"));
		requestToProcess.setTradePaymentId(rs.getInt("TradePaymentID"));
		
		if(EntityEnum.BANK.getEntityTypeAsInteger().equals(entityType)) {
			requestToProcess.setEntityId(rs.getInt("vTradeBankID"));
			requestToProcess.setBankStatus(ServiceStatus.getStatusAsString(8));
		}
		if(EntityEnum.BENEFICIARY.getEntityTypeAsInteger().equals(entityType)) {
			requestToProcess.setEntityId(rs.getInt("vTradeBeneficiaryID"));
			requestToProcess.setBeneficiaryStatus(ServiceStatus.getStatusAsString(8));
		}
		if(EntityEnum.CONTACT.getEntityTypeAsInteger().equals(entityType)) {
			requestToProcess.setEntityId(rs.getInt(CONTACTID));
			requestToProcess.setContactStatus(ServiceStatus.getStatusAsString(8));
		}
		
		listToProcess.add(requestToProcess);
	}

	/**
	 * Gets the other entity status.
	 *
	 * @param request
	 *            the request
	 * @return the other entity status
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void getOtherEntityStatus(FundsOutSanctionResendRequest request) throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatment = connection.prepareStatement(DBQueryConstant.GET_OTHER_ENTITY_STATUS.getQuery());
			preparedStatment.setInt(1, request.getPaymentOutId());
			preparedStatment.setInt(2, EntityEnum.getEntityTypeAsInteger(request.getEntityType()));
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				if (null == request.getBeneficiaryStatus()
						&& EntityEnum.BENEFICIARY.getEntityTypeAsInteger() == rs.getInt(ENTITY_TYPE)) {
					request.setBeneficiaryStatus(ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS)));
				}
				if (null == request.getContactStatus()
						&& EntityEnum.CONTACT.getEntityTypeAsInteger() == rs.getInt(ENTITY_TYPE)) {
					request.setContactStatus(ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS)));
				}
				if (null == request.getBankStatus()
						&& EntityEnum.BANK.getEntityTypeAsInteger() == rs.getInt(ENTITY_TYPE)) {
					request.setBankStatus(ServiceStatus.getStatusAsString(rs.getInt(Constants.STATUS)));
				}
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}

	}

	/**
	 * Update payments out compliance status.
	 *
	 * @param fundsOutId
	 *            the funds out id
	 * @param userID
	 *            the user ID
	 * @param status
	 *            the status
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void updatePaymentsOutComplianceStatus(Integer fundsOutId, Integer userID, FundsOutComplianceStatus status)
			throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatment = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_STATUS.getQuery());
			preparedStatment.setInt(1, userID);
			preparedStatment.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(3, status.getFundsOutStatusAsInteger());
			preparedStatment.setInt(4, fundsOutId);
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}
	}

	/**
	 * Gets the sanction status.
	 *
	 * @param fundsOutId
	 *            the funds out id
	 * @return the sanction status
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public String getSanctionStatus(Integer fundsOutId) throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatment = connection.prepareStatement(DBQueryConstant.GET_SANCTION_STATUS.getQuery());
			preparedStatment.setInt(1, fundsOutId);
			rs = preparedStatment.executeQuery();
			if (rs.next())
				return ServiceStatus.getStatusAsString(rs.getInt("sanctionstatus"));
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(connection);
		}
		return null;
	}
	
	void getFailedFundsOutContactAccountDetails(FundsOutRequest request, Connection conn, String tradeAccountNumber,
			String orgCode) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			String contactJoin = " AND c.id = ? ORDER BY ";
			String query = DBQueryConstant.GET_FUNDS_OUT_ACCOUNT_CONTACT_DETAILS.getQuery().replace("ORDER BY", contactJoin);
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, orgCode);
			preparedStatment.setString(2, tradeAccountNumber);
			preparedStatment.setString(3, orgCode);
			preparedStatment.setString(4, request.getContactId().toString());
			rs = preparedStatment.executeQuery();
			Integer count = 1;
			Integer noOfContacts = 0;
			Integer accountTMFlag = 0;
			List<String> watchListDetails = new ArrayList<>();
			MultiMap multiMapWatchlistCon = new MultiHashMap();//AT-3502
			while (rs.next()) {

				request.setOrgId(rs.getInt("requestOrgID"));
				Account acc = JsonConverterUtil.convertToObject(Account.class, rs.getString("acattrib"));
				acc.setId(rs.getInt(ACCOUNTID));
				acc.setCustomerType(rs.getInt("custType"));
				acc.setAccountStatus(rs.getString("accountStatus"));
				request.setAccId(acc.getId());
				request.addAttribute(ACCOUNT, acc);
				request.addAttribute(Constants.LE_UPDATE_DATE, rs.getString("leupdatedate")); //Add for AT-3349
				Contact con = JsonConverterUtil.convertToObject(Contact.class, rs.getString("contactattrib"));
				con.setId(rs.getInt(CONTACTID));
				con.setContactStatus(rs.getString("ContactStatus"));
				con.setPoiExists(rs.getInt("poiexists"));	//Add for AT-3349
				request.addAttribute(CONTACT, con);
				if (null != rs.getString(WATCHLISTREASONNAME) && !rs.getString(WATCHLISTREASONNAME).isEmpty()) {
					request.addAttribute(WATCHLIST_ID+ count, rs.getInt(WATCHLIST_ID));
					watchListDetails.add(rs.getString(WATCHLISTREASONNAME));
					request.addAttribute("watchlistreasonname1",rs.getString(WATCHLISTREASONNAME));
				}
				multiMapWatchlistCon.put(rs.getInt(TRADE_CONTACT_ID), rs.getString(WATCHLISTREASONNAME));
				noOfContacts++;
				accountTMFlag = rs.getInt("accountTMFlag");
			}
			request.addAttribute("noOfContacts", noOfContacts);
			request.addAttribute(REASONS_OF_WATCHLIST, watchListDetails);
			request.addAttribute("watchlist_with_trade&cont", multiMapWatchlistCon);
			request.addAttribute(ACCOUNT_TM_FLAG, accountTMFlag);
		} catch (Exception e) {
			LOG.error(" ", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}

	}
	
	
	public List<EventServiceLog> getFailedFundsOutESLDetails(FundsOutRequest request) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		List<EventServiceLog> eventServiceLogList = new ArrayList<>();
		try {
			conn = getConnection(Boolean.TRUE);
			String query = DBQueryConstant.GET_ESL_FOR_SERVICE_FAILED_FUNDSOUT.getQuery();
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, request.getFundsOutId().toString());
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				EventServiceLog eventServiceLog = new EventServiceLog();
				eventServiceLog.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt(ENTITY_TYPE)));
				eventServiceLog.setServiceType(rs.getInt("ServiceType"));
				eventServiceLog.setProviderResponse(rs.getString("ProviderResponse"));
				eventServiceLog.setStatus(ServiceStatus.getStatusAsString(rs.getInt("Status")));
				eventServiceLog.setSummary(rs.getString("summary"));
				eventServiceLog.setEntityId(rs.getInt("EntityID"));
				eventServiceLog.setEntityVersion(rs.getInt("EntityVersion"));
				eventServiceLog.setEventId(rs.getInt("eventID"));
				eventServiceLogList.add(eventServiceLog);
			}
		} catch (Exception e) {
			LOG.error("Error while fetching eventservicelog details :", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return eventServiceLogList;

	}
	
	/**
	 * Gets the failed funds out details.
	 *
	 * @param paymentOutId the payment out id
	 * @param orgCode the org code
	 * @param message 
	 * @return the failed funds out details
	 * @throws ComplianceException the compliance exception
	 */
	public FundsOutRequest getFailedFundsOutDetails(Integer paymentOutId) throws ComplianceException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FundsOutRequest fundsOutDetails = new FundsOutRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_DETAILS_FOR_FAILED_FUNDS_OUT.getQuery());
			preparedStatement.setInt(1, paymentOutId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String json = resultSet.getString(PO_ATTRIB);
				if (null != json) {
					fundsOutDetails = JsonConverterUtil.convertToObject(FundsOutRequest.class, resultSet.getString(PO_ATTRIB));
				}
				fundsOutDetails.setFundsOutId(paymentOutId);
				fundsOutDetails.setAccId(resultSet.getInt(ACCOUNTID));
				fundsOutDetails.getTrade().setTradeAccountNumber(resultSet.getString("tradeaccountnumber"));
				fundsOutDetails.setContactId(resultSet.getInt(CONTACT_ID));
				fundsOutDetails.setDeviceInfo(createDeviceInfoObject(resultSet));
				fundsOutDetails.addAttribute(Constants.STATUS, resultSet.getString(PO_COMPLIANCE_STATUS));
				fundsOutDetails.addAttribute(Constants.FRAUGSTER_STATUS, ServiceStatus.getStatusAsString(resultSet.getInt(FRAUGSTER_STATUS)));
				fundsOutDetails.addAttribute(Constants.BLACKLIST_STATUS, ServiceStatus.getStatusAsString(resultSet.getInt(BLACKLIST_STATUS)));
				fundsOutDetails.addAttribute(Constants.SANCTION_STATUS, ServiceStatus.getStatusAsString(resultSet.getInt("SanctionStatus")));
				fundsOutDetails.addAttribute(Constants.CUSTOM_CHECK_STATUS, ServiceStatus.getStatusAsString(resultSet.getInt(CUSTOM_CHECK_STATUS)));
				fundsOutDetails.addAttribute(Constants.PAYMENT_REFERENCE_STATUS, ServiceStatus.getStatusAsString(resultSet.getInt(PAYMENT_REFERENCE_STATUS))); //AT-4445
		}
			getFailedFundsOutContactAccountDetails(fundsOutDetails, connection, fundsOutDetails.getTradeAccountNumber(),
					fundsOutDetails.getOrgCode());
		} catch (Exception e) {
			LOG.error("Error while fetching service failed funds out details :", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return fundsOutDetails;
	}
	
	
	/**
	 * AT-3349
	 * Check any funds clear for contact.
	 *
	 * @param tradeContactId the trade contact id
	 * @param tradeAccountuNumber the trade accountu number
	 * @param fundsOutRequest the funds out request
	 * @throws ComplianceException the compliance exception
	 */
	public void checkAnyFundsClearForContact(FundsOutRequest fundsOutRequest, String date) 
			throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection(Boolean.TRUE);
			int fundsInCount = 0;
			int fundsOutCount = 0;
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_NUMBER_OF_FI_CLEAR_AFTER_LEDATE.getQuery());
			preparedStatment.setString(1,fundsOutRequest.getTrade().getTradeAccountNumber());
			preparedStatment.setInt(2,fundsOutRequest.getTrade().getTradeContactId());
			preparedStatment.setString(3, date);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				 fundsInCount = rs.getInt("NumOfFundsInClear");
			}
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_NUMBER_OF_FO_CLEAR_AFTER_LEDATE.getQuery());
			preparedStatment.setString(1,fundsOutRequest.getTrade().getTradeAccountNumber());
			preparedStatment.setInt(2,fundsOutRequest.getTrade().getTradeContactId());
			preparedStatment.setString(3, date);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				 fundsOutCount = rs.getInt("NumOfFundsOutClear");
			}
			
			Integer count = fundsInCount + fundsOutCount;
			
			if(count.equals(0)) {
				fundsOutRequest.addAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE,Boolean.TRUE);
			}
			else {
				fundsOutRequest.addAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE,Boolean.FALSE);
			}
		}catch(Exception e) {
			LOG.error("Error while fetching checkAnyFundsClearForContact() details :", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
	}
	
	//Added for AT-3658
		/**
		 * Following method calls two functions which returns whether for given
		 * trade account number & organization account & contact is present and
		 * whether for given organization funds out id is present.
		 *
		 * @param fundsOutBaseRequest
		 *            the funds out base request
		 * @return the funds out details
		 * @throws ComplianceException
		 *             the compliance exception
		 */
		public FundsOutRequest getFundsOutDetailsForPaymentReferenceRepeatCheck(FundsOutBaseRequest fundsOutBaseRequest) throws ComplianceException {
			Connection conn = null;
			FundsOutRequest details = new FundsOutRequest();
			try {
				   conn = getConnection(Boolean.TRUE);
	               details = getExistingFundsOutIdForPaymentReferenceRepeatCheck(conn, fundsOutBaseRequest.getOrgCode(),
							fundsOutBaseRequest.getPaymentFundsoutId());
				   getFundsOutContactAccountDetails(details, conn, fundsOutBaseRequest.getTradeAccountNumber(),
						fundsOutBaseRequest.getOrgCode());

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeConnection(conn);
			}
			return details;
		}
		
		//Added for AT-3658
		private FundsOutRequest getExistingFundsOutIdForPaymentReferenceRepeatCheck(Connection conn, String orgCode, Integer fundsOutId)
				throws ComplianceException {

			PreparedStatement preparedStatment = null;
			ResultSet rs = null;
			FundsOutRequest details = new FundsOutRequest();
			try {
				preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_ID_FOR_PAYMENT_REFERENCE.getQuery());
				preparedStatment.setInt(1, fundsOutId);
				preparedStatment.setString(2, orgCode);
				rs = preparedStatment.executeQuery();
				while (rs.next()) {
					String json = rs.getString(PO_ATTRIB);
					if (null != json) {
						details = JsonConverterUtil.convertToObject(FundsOutRequest.class, rs.getString(PO_ATTRIB));
					}
					details.setFundsOutId(rs.getInt(FUNDS_OUT_ID));
					details.setAccId(rs.getInt(ACCOUNTID));
					details.setContactId(rs.getInt(CONTACT_ID));
					details.addAttribute(Constants.STATUS, rs.getString(PO_COMPLIANCE_STATUS));
					details.addAttribute(Constants.PAYMENT_REFERENCE_STATUS, ServiceStatus.getStatusAsString(rs.getInt(PAYMENT_REFERENCE_STATUS)));
				}
			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatment);
			}
			return details;
		}
		
		//Added for AT-3658
		/**
		 * Update Payment Reference service status.
		 *
		 * @param status
		 *            the status
		 * @param id
		 *            the id
		 * @param userID
		 *            the user ID
		 * @throws ComplianceException
		 *             the compliance exception
		 */
		public void updatePaymentReferenceServiceStatus(String status, Integer id, Integer userID) throws ComplianceException {
			Connection connection = null;
			PreparedStatement fStatment = null;
			try {
				connection = getConnection(Boolean.FALSE);
				fStatment = connection.prepareStatement(DBQueryConstant.UPDATE_FUNDS_OUT_PAYMENT_REFERENCE_STATUS.getQuery());
				fStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
				fStatment.setInt(2, userID);
				fStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				fStatment.setInt(4, id);
				fStatment.execute();

			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(fStatment);
				closeConnection(connection);
			}
		}

		/**
		 * Gets the funds out details for intuition.
		 *
		 * @param tradePayId the trade pay id
		 * @return the funds out details for intuition
		 * @throws ComplianceException the compliance exception
		 */
		//AT-4451
		public FundsOutRequest getFundsOutDetailsForIntuition(Integer tradePayId) throws ComplianceException {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			FundsOutRequest fundsOutDetails = new FundsOutRequest();;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_DETAILS_FOR_INTUITION.getQuery());
				preparedStatement.setInt(1, tradePayId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					String json = resultSet.getString(PO_ATTRIB);
					if (null != json) {
						fundsOutDetails = JsonConverterUtil.convertToObject(FundsOutRequest.class,
								resultSet.getString(PO_ATTRIB));
					}
					fundsOutDetails.setFundsOutId(resultSet.getInt("payOutId"));
					fundsOutDetails.setAccId(resultSet.getInt(ACCOUNTID));
					fundsOutDetails.getTrade().setTradeAccountNumber(resultSet.getString("Tradeaccountnumber"));
					fundsOutDetails.getTrade().setTradeContactId(resultSet.getInt(TRADE_CONTACT_ID));
					fundsOutDetails.setContactId(resultSet.getInt(CONTACT_ID));
					fundsOutDetails.addAttribute(Constants.STATUS, FundsOutComplianceStatus
							.getFundsInComplianceStatusAsString(resultSet.getInt(COMPLIANCE_STATUS)));
					fundsOutDetails.addAttribute(Constants.FRAUGSTER_STATUS,
							ServiceStatus.getStatusAsString(resultSet.getInt(FRAUGSTER_STATUS)));
					fundsOutDetails.addAttribute(Constants.BLACKLIST_STATUS,
							ServiceStatus.getStatusAsString(resultSet.getInt(BLACKLIST_STATUS)));
					fundsOutDetails.addAttribute(Constants.CUSTOM_CHECK_STATUS,
							ServiceStatus.getStatusAsString(resultSet.getInt(CUSTOM_CHECK_STATUS)));
					fundsOutDetails.addAttribute(Constants.PAYMENT_REFERENCE_STATUS,
							ServiceStatus.getStatusAsString(resultSet.getInt(PAYMENT_REFERENCE_STATUS)));
					fundsOutDetails.addAttribute(ACCOUNT_VERSION, resultSet.getInt(ACCOUNT_VERSION));
					fundsOutDetails.addAttribute(ACCOUNT_TM_FLAG, resultSet.getInt(ACCOUNT_TM_FLAG));
					fundsOutDetails.addAttribute("STPFlag", resultSet.getString("InitialStatus"));
					fundsOutDetails.addAttribute("orgcode", resultSet.getString("OrganizationCode"));
					fundsOutDetails.addAttribute(Constants.ETAILER, resultSet.getString(Constants.ETAILER)); //AT-5310
					fundsOutDetails.addAttribute("AffiliateName", resultSet.getString("AffiliateName")); // Add For AT-5454
				}
			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return fundsOutDetails;
		}

		/**
		 * Gets the country check status for intuition.
		 *
		 * @param fundsOut the funds out
		 * @param transactionMonitoringPaymentsOutRequest the transaction monitoring payments out request
		 * @return the country check status for intuition
		 * @throws ComplianceException the compliance exception
		 */
		//AT-4451
		public String getCountryCheckStatusForIntuition(FundsOutRequest fundsOut) throws ComplianceException {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String countryCheckStatus = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_COUNTRY_CHECK_STATUS_FOR_INTUITION.getQuery());
				preparedStatement.setInt(1, fundsOut.getBeneficiary().getBeneficiaryId());
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					countryCheckStatus = resultSet.getString("CountryCheckStatus");
				}

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return countryCheckStatus;
		}

		/**
		 * Gets the sanction status for intuition.
		 *
		 * @param fundsOutRequest the funds out request
		 * @param transactionMonitoringPaymentsOutRequest the transaction monitoring payments out request
		 * @return the sanction status for intuition
		 * @throws ComplianceException the compliance exception
		 */
		//AT-4451
		public void getSanctionStatusForIntuition(FundsOutRequest fundsOutRequest,
				TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest)
				throws ComplianceException {

			String contactSanctionStatus = getContactSanctionStatus(fundsOutRequest.getContactId(),
					fundsOutRequest.getFundsOutId());
			String beneficierySanctionStatus = getBeneficierySanctionStatus(
					fundsOutRequest.getBeneficiary().getBeneficiaryId(), fundsOutRequest.getFundsOutId());
			String bankSanctionStatus = getBankSanctionStatus(fundsOutRequest.getBeneficiary().getBeneficiaryBankid(),
					fundsOutRequest.getFundsOutId());

			transactionMonitoringPaymentsOutRequest.setSanctionContactStatus(contactSanctionStatus);
			transactionMonitoringPaymentsOutRequest.setSanctionBeneficiaryStatus(beneficierySanctionStatus);
			transactionMonitoringPaymentsOutRequest.setSanctionBankStatus(bankSanctionStatus);
		}

		/**
		 * Gets the contact sanction status.
		 *
		 * @param contactId the contact id
		 * @param fundsOutId the funds out id
		 * @return the contact sanction status
		 * @throws ComplianceException the compliance exception
		 */
		public String getContactSanctionStatus(Integer contactId, Integer fundsOutId) throws ComplianceException {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String status = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_CONTACT_SANCTION_STATUS.getQuery());
				preparedStatement.setInt(1, contactId);
				preparedStatement.setInt(2, fundsOutId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					status = resultSet.getString("ContactSanctionStatus");
				}

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return status;
		}

		/**
		 * Gets the beneficiery sanction status.
		 *
		 * @param beneficiaryId the beneficiary id
		 * @param fundsOutId the funds out id
		 * @return the beneficiery sanction status
		 * @throws ComplianceException the compliance exception
		 */
		public String getBeneficierySanctionStatus(Integer beneficiaryId, Integer fundsOutId) throws ComplianceException {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String status = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_BENEFICIARY_SANCTION_STATUS.getQuery());
				preparedStatement.setInt(1, beneficiaryId);
				preparedStatement.setInt(2, fundsOutId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					status = resultSet.getString("BeneficierySanctionStatus");
				}

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return status;
		}

		/**
		 * Gets the bank sanction status.
		 *
		 * @param beneficiaryBankid the beneficiary bankid
		 * @param fundsOutId the funds out id
		 * @return the bank sanction status
		 * @throws ComplianceException the compliance exception
		 */
		public String getBankSanctionStatus(Integer beneficiaryBankid, Integer fundsOutId) throws ComplianceException {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String status = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_OUT_BANK_SANCTION_STATUS.getQuery());
				preparedStatement.setInt(1, beneficiaryBankid);
				preparedStatement.setInt(2, fundsOutId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					status = resultSet.getString("BankSanctionStatus");
				}

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return status;
		}
		
		/**
		 * Gets the status update reason for intuition.
		 *
		 * @param fundsOutRequest the funds out request
		 * @param transactionMonitoringPaymentsOutRequest the transaction monitoring payments out request
		 * @return the status update reason for intuition
		 * @throws ComplianceException the compliance exception
		 */
		//AT-4451
		public String getStatusUpdateReasonForIntuition(FundsOutRequest fundsOutRequest) throws ComplianceException {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String statusUpdateReason= null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_STATUS_UPDATE_REASON_FOR_PAYMENT_OUT_INTUITION.getQuery());
				preparedStatement.setInt(1, fundsOutRequest.getFundsOutId());
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					statusUpdateReason = resultSet.getString("Reason");
				}

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return statusUpdateReason;
		}

		/**
		 * Gets the payment reference match keyword for intuition.
		 *
		 * @param beneficiaryId the beneficiary id
		 * @param transactionMonitoringPaymentsOutRequest the transaction monitoring payments out request
		 * @return the payment reference match keyword for intuition
		 * @throws ComplianceException the compliance exception
		 */
		//AT-4475
		public void getPaymentReferenceMatchKeywordForIntuition(Integer beneficiaryId,
				TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest) throws ComplianceException {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(DBQueryConstant.GET_PAYMENT_REFERENCE_MATCHED_KEYWORD_FOR_INTUITION.getQuery());
				preparedStatement.setInt(1, beneficiaryId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					transactionMonitoringPaymentsOutRequest.setPaymentRefMatchedKeyword(resultSet.getString("SanctionText"));
				}

			} catch (Exception e) {
				LOG.error("", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
		}
		
		/**
		 * Update account intuition client risk level.
		 *
		 * @param message the message
		 * @return the message
		 */
		public Message<MessageContext> updateAccountIntuitionClientRiskLevel(Message<MessageContext> message) {
	        Connection connection = null;
	        String paymentStatus = null;
	        try {

	            FundsOutResponse response = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
	                    .getResponse();
	            
	            TransactionMonitoringPaymentsOutRequest tmRequest = (TransactionMonitoringPaymentsOutRequest) message
						.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getRequest();
	            TransactionMonitoringPaymentOutResponse tmResponse = (TransactionMonitoringPaymentOutResponse) message
						.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getResponse();
				
				TransactionMonitoringPaymentsOutRequest request = (TransactionMonitoringPaymentsOutRequest) message
						.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getRequest();

				OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
                if(operation == OperationEnum.FUNDS_OUT || operation == OperationEnum.RECHECK_FAILURES) {
                    paymentStatus = response.getStatus();
                } else {
                    paymentStatus = tmResponse.getPaymentStatus();
                }
				
	            String clientRiskLevel = (String) response.getAdditionalAttribute(CLIENT_RISK_LEVEL);
	            Integer accountTMFlag = (Integer) request.getAdditionalAttribute(ACCOUNT_TM_FLAG);
	            if(clientRiskLevel != null && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
		            UserProfile userToken = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		            connection = getConnection(Boolean.FALSE);
		            beginTransaction(connection);
		            updateAccountIntuitionRiskLevel(request.getAccountId(), clientRiskLevel, userToken, connection);
		            updatePaymentOutIntuitionStatus(request.getFundsOutId(), tmResponse.getStatus(), userToken, connection);
					if (tmRequest.getUpdateStatus() != null
							&& tmRequest.getUpdateStatus().equalsIgnoreCase(FundsOutComplianceStatus.HOLD.name())
							&& paymentStatus != null) {
						updatePaymentOutStatus(paymentStatus, request.getFundsOutId(), connection);
					}
		            commitTransaction(connection);
	            }    
	        } catch (Exception e) {
	            message.getPayload().setFailed(true);
	            LOG.error("Error while updating IntuitionRiskLevel to Account", e);
	        } finally {
	            try {
	                closeConnection(connection);
	            } catch (ComplianceException e) {
	                message.getPayload().setFailed(true);
	                LOG.error("Error while updating IntuitionRiskLevel to Account", e);
	            }
	        }
	        return message;
	    }

	    /**
    	 * Update account intuition risk level.
    	 *
    	 * @param accountId the account id
    	 * @param clientRiskLevel the client risk level
    	 * @param userToken the user token
    	 * @param connection the connection
    	 * @throws ComplianceException the compliance exception
    	 */
    	public void updateAccountIntuitionRiskLevel(Integer accountId, String clientRiskLevel,
	            UserProfile userToken, Connection connection) throws ComplianceException {
	        PreparedStatement statement = null;
	        try {
	            statement = connection.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_INTUITION_RISK_LEVEL.getQuery());

	            statement.setString(1, clientRiskLevel);
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
	    
	    /**
    	 * Update payment out intuition status.
    	 *
    	 * @param fundsOutId the funds out id
    	 * @param status the status
    	 * @param userToken the user token
    	 * @param connection the connection
    	 * @throws ComplianceException the compliance exception
    	 */
    	public void updatePaymentOutIntuitionStatus(Integer fundsOutId, String status,
				UserProfile userToken, Connection connection) throws ComplianceException {
	        PreparedStatement statement = null;
	        try {
	            statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_INTUITION_CHECK_STATUS.getQuery());

	            statement.setInt(1, ServiceStatus.getStatusAsInteger(status));
	            statement.setInt(2, userToken.getUserID());
	            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
	            statement.setInt(4, fundsOutId);

	            statement.executeUpdate();
	        } catch (Exception e) {
	            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
	        } finally {
	            closePrepareStatement(statement);
	        }
	    }
	    
	    /**
		 * Add for AT-4752
		 * 
		 * Update short response code.
		 *
		 * @param message the message
		 * @return the message
		 * @throws ComplianceException the compliance exception
		 */
		public Message<MessageContext> updateShortResponseCode(Message<MessageContext> message) throws ComplianceException {
			Connection conn = null;

			try {
				MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
				FundsOutBaseRequest fRequest = (FundsOutBaseRequest) exchange.getRequest();
				FundsOutResponse fundsOutResponse = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
						.getResponse();
				conn = getConnection(Boolean.FALSE);
				getContactWatchlist(conn, fRequest.getFundsOutId(), fundsOutResponse);
				fRequest.addAttribute("AtlasSTPFlag", fundsOutResponse.getShortResponseCode());
				
			} catch (Exception e) {
				LOG.error("Exception in updatePaymentOutStatus()", e);
				message.getPayload().setFailed(true);
			} finally {
				closeConnection(conn);
			}
			return message;
		}
	    
	    public String getPaymentComplianceStatus(Integer payId) throws ComplianceException {
	        PreparedStatement statement = null;
	        Connection connection = null;
	        ResultSet resultSet = null;
	        String status = null; 
	        String tableName = "PaymentOut";
	        try {
	        	connection = getConnection(Boolean.TRUE);
	        	statement = connection.prepareStatement(DBQueryConstant.GET_PAYMENT_STATUS.getQuery().replace("#",
	        			tableName));
	        	statement.setInt(1, payId);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					status = FundsOutComplianceStatus.getFundsInComplianceStatusAsString(resultSet.getInt(COMPLIANCE_STATUS));
				
				}
	        } catch (Exception e) {
	            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
	        } finally {
	            closePrepareStatement(statement);
	        }
	        
	        return status;
	    }
	
		/**
		 * Update payment out status.
		 *
		 * @param paymentStatus the payment status
		 * @param paymentOutID the payment out ID
		 * @param connection the connection
		 * @throws ComplianceException the compliance exception
		 */
		public void updatePaymentOutStatus(String paymentStatus, Integer paymentOutID, Connection connection)
				throws ComplianceException {
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_STATUS.getQuery());

				statement.setInt(1, 1);
				statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				statement.setInt(3, FundsOutComplianceStatus.getFundsInComplianceStatusAsInteger(paymentStatus));
				statement.setInt(4, paymentOutID);

				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {

				closePrepareStatement(statement);

			}
		}
		
		/**
		 * Gets the payment out status by trade payment id.
		 *
		 * @param tradePaymentId the trade payment id
		 * @return the payment out status by trade payment id
		 * @throws ComplianceException the compliance exception
		 */
		//AT-4906
		public Integer getPaymentOutStatusByTradePaymentId(Integer tradePaymentId) throws ComplianceException {
			PreparedStatement preparedStatment = null;
			ResultSet resultSet = null;
			Connection conn = null;
			Integer paymentStatus = 0;
			try {
				conn = getConnection(Boolean.TRUE);
				preparedStatment = conn.prepareStatement(
						DBQueryConstant.GET_PAYMENT_STATUS_BY_PAYMENT_TRADE_ID.getQuery().replace("#", "PaymentOut"));
				preparedStatment.setInt(1, tradePaymentId);
				resultSet = preparedStatment.executeQuery();
				while (resultSet.next()) {
					paymentStatus = resultSet.getInt(COMPLIANCE_STATUS);
				}

			} catch (Exception e) {
				LOG.error("Error in getPaymentOutStatusByTradePaymentId() : ", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatment);
				closeConnection(conn);
			}

			return paymentStatus;

		}

		// AT-5304
		public List<FundsOutRequest> getIntuitionHistoricPaymentOut() throws ComplianceException {
			PreparedStatement preparedStatment = null;
			ResultSet resultSet = null;
			Connection connection = null;
			List<FundsOutRequest> fundsOutRequests = new ArrayList<>();
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatment = connection.prepareStatement(
						DBQueryConstant.GET_DETAILS_FOR_INTUITION_HISTORIC_PAYMENT_OUT_RECORDS.getQuery());
				resultSet = preparedStatment.executeQuery();
				while (resultSet.next()) {
					FundsOutRequest fundsOutRequest = setFundsOutDetailsForIntuition(resultSet);
					fundsOutRequest.getBeneficiary().setTransactionDateTime(resultSet.getString("TransactionTimeDate")); //Added for AT-5486
					fundsOutRequests.add(fundsOutRequest);
				}
			} catch (Exception e) {
				LOG.error("Error in getIntuitionHistoricPaymentOut() : ", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatment);
				closeConnection(connection);
			}
			return fundsOutRequests;
		}

		// AT-5304
		private FundsOutRequest setFundsOutDetailsForIntuition(ResultSet resultSet) throws SQLException {
			FundsOutRequest fundsOutDetails = new FundsOutRequest();
			String json = resultSet.getString(PO_ATTRIB);
			if (null != json) {
				fundsOutDetails = JsonConverterUtil.convertToObject(FundsOutRequest.class,
						resultSet.getString(PO_ATTRIB));
			}
			fundsOutDetails.setFundsOutId(resultSet.getInt("payOutId"));
			fundsOutDetails.setAccId(resultSet.getInt(ACCOUNTID));
			fundsOutDetails.getTrade().setTradeAccountNumber(resultSet.getString("Tradeaccountnumber"));
			fundsOutDetails.getTrade().setTradeContactId(resultSet.getInt(TRADE_CONTACT_ID));
			fundsOutDetails.setContactId(resultSet.getInt(CONTACT_ID));
			fundsOutDetails.addAttribute(Constants.STATUS, FundsOutComplianceStatus
					.getFundsInComplianceStatusAsString(resultSet.getInt(PO_COMPLIANCE_STATUS)));
			fundsOutDetails.addAttribute(Constants.FRAUGSTER_STATUS,
					ServiceStatus.getStatusAsString(resultSet.getInt(FRAUGSTER_STATUS)));
			fundsOutDetails.addAttribute(Constants.BLACKLIST_STATUS,
					ServiceStatus.getStatusAsString(resultSet.getInt(BLACKLIST_STATUS)));
			fundsOutDetails.addAttribute(Constants.SANCTION_STATUS,
					ServiceStatus.getStatusAsString(resultSet.getInt("SanctionStatus")));
			fundsOutDetails.addAttribute(Constants.CUSTOM_CHECK_STATUS,
					ServiceStatus.getStatusAsString(resultSet.getInt(CUSTOM_CHECK_STATUS)));
			fundsOutDetails.addAttribute(Constants.PAYMENT_REFERENCE_STATUS,
					ServiceStatus.getStatusAsString(resultSet.getInt(PAYMENT_REFERENCE_STATUS)));
			fundsOutDetails.addAttribute(ACCOUNT_VERSION, resultSet.getInt(ACCOUNT_VERSION));
			fundsOutDetails.addAttribute(ACCOUNT_TM_FLAG, resultSet.getInt(ACCOUNT_TM_FLAG));
			fundsOutDetails.addAttribute("STPFlag", resultSet.getString("InitialStatus"));
			fundsOutDetails.addAttribute("orgcode", resultSet.getString("OrganizationCode"));
			fundsOutDetails.addAttribute(Constants.ETAILER, resultSet.getString(Constants.ETAILER)); // AT-5310

			return fundsOutDetails;
		}
}
