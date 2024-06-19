/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
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
 * The Class FundsInDBServiceImpl.
 *
 * @author manish
 */
@SuppressWarnings("squid:S2095")
public class FundsInDBServiceImpl extends PaymentsAbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FundsInDBServiceImpl.class);

	private static final String BLACKLIST_STATUS = "BlacklistStatus";
	private static final String PI_COMPLIANCE_STATUS = "PIComplianceStatus";
	private static final String STATUS = "status";
	private static final String CONTACT_ID2 = "ContactID";
	private static final String ACCOUNTID = "accountid";
	private static final String FUNDSIN_ID = "fundsinId";
	private static final String PI_ATTRIB = "PIAttrib";
	private static final String EXCEPTION_IN_SAVE_FUNDS_IN_REQUEST = "Exception in saveFundsInRequest()";
	private static final String ACCOUNT = "account";
	private static final String REASONS_OF_WATCHLIST = "reasonsOfWatchlist";
	private static final String WATCHLISTID = "watchlistid";
	private static final String WATCHLISTREASONNAME = "watchlistreasonname";
	private static final String REQUEST_ORG_ID ="requestOrgID";
	private static final String ACATTRIB ="acattrib";
	private static final String ACCOUNTSTATUS ="accountStatus";
	private static final String CONTACT_ID= "contactid";
	private static final String CONTACTATTRIB="contactattrib";
	private static final String CONTACT_STATUS ="ContactStatus";
	private static final String NUM_OF_FUNDS_IN_CLEAR ="NumOfFundsInClear";
	
	/** The Constant CLIENT_RISK_LEVEL. */
	private static final String CLIENT_RISK_LEVEL = "ClientRiskLevel";
	
	/** The Constant FRAUGSTER_STATUS. */
	private static final String FRAUGSTER_STATUS = "FraugsterStatus";
	
	/** The Constant ACCOUNT_TM_FLAG. */
	private static final String ACCOUNT_TM_FLAG = "AccountTMFlag";
	
	/** The Constant TRADE_CONTACT_ID. */
	private static final String TRADE_CONTACT_ID = "TradeContactID";
	
	/** The Constant PAYMENT_IN_ID. */
	private static final String PAYMENT_IN_ID = "payInId";
	
	/**
	 * Save funds in request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> saveFundsInRequest(Message<MessageContext> message) {
		Connection conn = null;
		try {
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			beginTransaction(conn);
			Account accountID = (Account) fundsInRequest.getAdditionalAttribute(ACCOUNT);
			Contact contactID = (Contact) fundsInRequest.getAdditionalAttribute("contact");
			saveIntoPaymentsIn(conn, fundsInRequest, accountID.getId(), contactID.getId(), token.getUserID());
			saveIntoPaymentsInAttribute(conn, fundsInRequest, token.getUserID());
			commitTransaction(conn);
		} catch (Exception e) {
			try {
				transactionRolledBack(conn);
			} catch (ComplianceException e1) {
				LOG.error(EXCEPTION_IN_SAVE_FUNDS_IN_REQUEST, e1);
			}
			LOG.error(EXCEPTION_IN_SAVE_FUNDS_IN_REQUEST, e);
			message.getPayload().setFailed(true);
		} finally {
			try {
				closeConnection(conn);
			} catch (ComplianceException e) {
				LOG.error(EXCEPTION_IN_SAVE_FUNDS_IN_REQUEST, e);
				message.getPayload().setFailed(true);
			}
		}
		return message;
	}

	/**
	 * Save into payments in.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInRequest
	 *            the funds in request
	 * @param accountID
	 *            the account ID
	 * @param contactID
	 *            the contact ID
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoPaymentsIn(Connection conn, FundsInCreateRequest fundsInRequest, Integer accountID,
			Integer contactID, Integer userID) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			/*
			 * SELECT [ID], [OrganizationID], [AccountID], [ContactID],
			 * [TradeContractNumber] , [TradePaymentID], [ComplianceStatus],
			 * [ComplianceDoneOn] , [Deleted], [CreatedBy], [CreatedOn],
			 * [UpdatedBy], [UpdatedOn] FROM [dbo].[PaymentIn] GO
			 */
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_INTO_PAYMENTS_IN.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setString(1, fundsInRequest.getOrgCode());
			preparedStatment.setInt(2, accountID);
			preparedStatment.setInt(3, contactID);
			preparedStatment.setString(4, fundsInRequest.getTrade().getContractNumber());
			preparedStatment.setInt(5, fundsInRequest.getTrade().getPaymentFundsInId());// ???
			preparedStatment.setString(6, FundsInComplianceStatus.HOLD.name());
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setBoolean(8, false);
			preparedStatment.setInt(9, userID);
			preparedStatment.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(11, userID);
			preparedStatment.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(13, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(14, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(15, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(16, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setTimestamp(17,
					DateTimeFormatter.convertStringToTimestamp(fundsInRequest.getTrade().getPaymentTime()));
			preparedStatment.setBoolean(18, false);
			// set STPFlag
			preparedStatment.setBoolean(19, false);
			preparedStatment.setString(20, fundsInRequest.getTrade().getCustLegalEntity());
			preparedStatment.setInt(21, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));
			preparedStatment.setInt(22, ServiceStatus.getStatusAsInteger(ServiceStatus.NOT_REQUIRED.name()));//Add for TM status
			preparedStatment.execute();
			rs = preparedStatment.getGeneratedKeys();
			if (rs.next()) {
				fundsInRequest.setFundsInId(rs.getInt(1));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Save into payments in attribute.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInRequest
	 *            the funds in request
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void saveIntoPaymentsInAttribute(Connection conn, FundsInCreateRequest fundsInRequest, Integer userID)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			/*
			 * [ID], [Version], [TransactionDate], [TransactionCurrency],
			 * [TransactionAmount] , [BaseCurrencyAmount], [PaymentMethod],
			 * [CountryOfPayment], [ThirdPartyPayment], [DebitorName],
			 * [Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]
			 */
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_INTO_PAYMENTS_IN_ATTRIBUTE.getQuery());
			preparedStatment.setInt(1, fundsInRequest.getFundsInId());
			preparedStatment.setInt(2, 1);
			preparedStatment.setString(3, JsonConverterUtil.convertToJsonWithoutNull(fundsInRequest));
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
	 * Update payment in status.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updatePaymentInStatus(Message<MessageContext> message) throws ComplianceException {
		Connection conn = null;
		try {
			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = exchange.getServiceInterface();
			OperationEnum operation = exchange.getOperation();
			boolean newFundsIn = (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.FUNDS_IN); 
			
			CustomCheckResponse ccResponse = (CustomCheckResponse) message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();
			
			FundsInBaseRequest fRequest = (FundsInBaseRequest) exchange.getRequest();
			FundsInCreateResponse fundsInCreateResponse = (FundsInCreateResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			if (newFundsIn) {
				TransactionMonitoringPaymentInResponse tmResponse = (TransactionMonitoringPaymentInResponse) message
						.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getResponse();
				TransactionMonitoringFundsProviderResponse tmProviderResponse = tmResponse
						.getTransactionMonitoringFundsProviderResponse();
				
				if (tmProviderResponse != null && tmProviderResponse.getClientRiskLevel() != null) { //Add for AT-4961
					fundsInCreateResponse.addAttribute(CLIENT_RISK_LEVEL, tmProviderResponse.getClientRiskLevel());
				}
			}
			getContactWatchlist(conn, fRequest.getFundsInId(), fundsInCreateResponse);//Add for AT-3470
			updateStatusIntoPaymetIn(conn, fundsInCreateResponse, fRequest.getFundsInId(), token.getUserID(),newFundsIn);
			updateIntoPaymentInStatusReason(conn, fundsInCreateResponse, fRequest.getFundsInId(), token.getUserID());
			updateEDDStatusInAccount(conn, fundsInCreateResponse,ccResponse);
		} catch (Exception e) {
			LOG.error("Exception in updatePaymentInstatus()", e);
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
	private void getContactWatchlist(Connection conn, Integer id, FundsInCreateResponse fundsInCreateResponse) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("000");
		
		try {
			
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_CONTACT_WATCHLIST_FUNDS_IN.getQuery());
			preparedStatment.setInt(1, id);
			rs = preparedStatment.executeQuery();
			while(rs.next()) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				String watchListReason=rs.getString("Reason");
				if(!WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(watchListReason) && !WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(watchListReason))
				{
					if(rs.getInt("StopPaymentIn") == 1 && fundsInCreateResponse.getStatus().equals(FundsInComplianceStatus.HOLD.name())) {
						if(fundsInCreateResponse.getShortResponseCode().contains("NSTP")) {
							sb.append("WR").append(df.format(rs.getInt("ID")));
						}else {
							fundsInCreateResponse.setShortResponseCode("NSTP");
							sb.append("WR").append(df.format(rs.getInt("ID")));
						}
					}else {
							sb.append("WA").append(df.format(rs.getInt("ID")));
						}
				}
				else if(WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(watchListReason)) {
					getShortCodeForDocRequiredWL(fundsInCreateResponse, rs, sb, df);
				}
				else if(WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(watchListReason)) {
					getShortCodeForVatRequiredWL(fundsInCreateResponse, rs, sb, df);
				}
			}	
			if(sb.length() != 0) {
				String watch = fundsInCreateResponse.getShortResponseCode().concat(";").concat(sb.toString());
				fundsInCreateResponse.setShortResponseCode(watch);
			}
			
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeResultset(rs);
		}
	}
	
	private void getShortCodeForDocRequiredWL(FundsInCreateResponse fundsInCreateResponse, ResultSet rs,
			StringBuilder sb, DecimalFormat df) throws SQLException {
		if (Boolean.TRUE.equals(fundsInCreateResponse.getDocRequiredWL())) {
			if (fundsInCreateResponse.getShortResponseCode().contains("NSTP")) {
				sb.append("WR").append(df.format(rs.getInt("ID")));
			} else {
				fundsInCreateResponse.setShortResponseCode("NSTP");
				sb.append("WR").append(df.format(rs.getInt("ID")));
			}
		} else {
			sb.append("WA").append(df.format(rs.getInt("ID")));
		}
	}
	
	private void getShortCodeForVatRequiredWL(FundsInCreateResponse fundsInCreateResponse, ResultSet rs,
			StringBuilder sb, DecimalFormat df) throws SQLException {
		if (Boolean.TRUE.equals(fundsInCreateResponse.getVatRequiredWL())) {
			if (fundsInCreateResponse.getShortResponseCode().contains("NSTP")) {
				sb.append("WR").append(df.format(rs.getInt("ID")));
			} else {
				fundsInCreateResponse.setShortResponseCode("NSTP");
				sb.append("WR").append(df.format(rs.getInt("ID")));
			}
		} else {
			sb.append("WA").append(df.format(rs.getInt("ID")));
		}
	}

	/**
	 * Update status into paymet in.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateStatusIntoPaymetIn(Connection conn, FundsInCreateResponse fundsInCreateResponse, Integer id,
			Integer userID, boolean newFundsIn) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		Boolean paymentInStatusHold = Boolean.FALSE;
		try {
			paymentInStatusHold = fundsInCreateResponse.getStatus().equals(FundsInComplianceStatus.HOLD.name());
			if(newFundsIn) {
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_IN_STATUS.getQuery());
				preparedStatment.setString(1, fundsInCreateResponse.getStatus());
				preparedStatment.setInt(2,ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getFraugsterCheckStatus()));
				preparedStatment.setInt(3,ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getSanctionCheckStatus()));
				preparedStatment.setInt(4,ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getBlacklistCheckStatus()));
				preparedStatment.setInt(5, ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getCustomCheckStatus()));
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, userID);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setBoolean(9, paymentInStatusHold);
				preparedStatment.setBoolean(10, fundsInCreateResponse.getSTPFlag());// set STP Flag
				preparedStatment.setString(11, fundsInCreateResponse.getShortResponseCode());//Add for AT-3470
				preparedStatment.setInt(12, ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getDebitCardFraudCheckStatus()));
				preparedStatment.setString(13,
						fundsInCreateResponse.getAdditionalAttribute(CLIENT_RISK_LEVEL) != null
								? (String) fundsInCreateResponse.getAdditionalAttribute(CLIENT_RISK_LEVEL)
								: null); //AT-4451
				preparedStatment.setInt(14, ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getIntuitionCheckStatus()));
				preparedStatment.setInt(15, id);
				preparedStatment.execute();
			}else {
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_IN_STATUS_WITHOUT_INITITAL_STATUS.getQuery());
				preparedStatment.setString(1, fundsInCreateResponse.getStatus());
				preparedStatment.setInt(2,ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getFraugsterCheckStatus()));
				preparedStatment.setInt(3,ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getSanctionCheckStatus()));
				preparedStatment.setInt(4,ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getBlacklistCheckStatus()));
				preparedStatment.setInt(5, ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getCustomCheckStatus()));
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, userID);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setBoolean(9, paymentInStatusHold);
				preparedStatment.setBoolean(10, fundsInCreateResponse.getSTPFlag());// set STP Flag
				preparedStatment.setInt(11, ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getDebitCardFraudCheckStatus()));
				preparedStatment.setInt(12, ServiceStatus.getStatusAsInteger(fundsInCreateResponse.getIntuitionCheckStatus()));
				preparedStatment.setInt(13, id);
				preparedStatment.execute();
			}
		
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Update into payment in status reason.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 * @param id
	 *            the id
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateIntoPaymentInStatusReason(Connection conn, FundsInCreateResponse fundsInCreateResponse,
			Integer id, Integer userID) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			// [PaymentOutID], [StatusUpdateReasonID], [CreatedBy], [CreatedOn],
			// [UpdatedBy], [UpdatedOn]
			if (null != fundsInCreateResponse.getResponseReason().getReasonShort()) {
				preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_INTO_FUNDS_IN_STATUS_REASON.getQuery());
				preparedStatment.setInt(1, id);
				preparedStatment.setString(2, "ALL");
				preparedStatment.setString(3, fundsInCreateResponse.getResponseReason().getReasonShort());
				preparedStatment.setInt(4, userID);
				preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(6, userID);
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
	 * Does funds in exists.
	 *
	 * @param paymentFundsInId
	 *            the payment funds in id
	 * @return the integer
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Integer doesFundsInExists(Integer paymentFundsInId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.DOES_FUNDS_IN_EXIST.getQuery());
			preparedStatment.setInt(1, paymentFundsInId);

			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

			closeResultset(rs);
		} catch (Exception e) {
			LOG.error("Error in doesFundsIN exists", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return null;
	}

	/**
	 * Following method calls two function which returns whether for given
	 * tradeaccount number & organizzation account & contact is present and
	 * whether for given organization fundsIn id is present.
	 *
	 * @param fundsInBaseRequest
	 *            the funds in base request
	 * @return the funds in details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsInCreateRequest getFundsInDetails(FundsInBaseRequest fundsInBaseRequest) throws ComplianceException {
		Connection conn = null;
		FundsInCreateRequest details = new FundsInCreateRequest();
		try {
			conn = getConnection(Boolean.TRUE);

			if (fundsInBaseRequest instanceof FundsInSanctionResendRequest) {
				FundsInSanctionResendRequest resendRequest = (FundsInSanctionResendRequest) fundsInBaseRequest;
				details = getExistingFundsInId(conn, fundsInBaseRequest.getOrgCode(),
						resendRequest.getTradePaymentId());
			} else {
				details = getExistingFundsInIdForFraugster(conn, fundsInBaseRequest.getOrgCode(),
						fundsInBaseRequest.getPaymentFundsInId());
			}

			getFundsInContactAccountDetails(details, conn, fundsInBaseRequest.getTradeAccountNumber(),
					fundsInBaseRequest.getOrgCode());
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeConnection(conn);
		}
		return details;
	}

	/**
	 * Following method calls two function which returns whether for given trade
	 * account number & organizzation account & contact is present and whether
	 * for given organization fundsIn id is present.
	 *
	 * @param fundsInBaseRequest
	 *            the funds in base request
	 * @return the funds in details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsInCreateRequest getFundsInDetailsForBlacklistRecheck(FundsInBaseRequest fundsInBaseRequest)
			throws ComplianceException {
		Connection conn = null;
		FundsInCreateRequest details = new FundsInCreateRequest();
		try {
			conn = getConnection(Boolean.TRUE);
			details = getExistingFundsInIdForBlacklistRecheck(conn, fundsInBaseRequest.getOrgCode(),
					fundsInBaseRequest.getPaymentFundsInId());
			getFundsInContactAccountDetailsForBlacklistRecheck(details, conn,
					fundsInBaseRequest.getTradeAccountNumber(), fundsInBaseRequest.getOrgCode());

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeConnection(conn);
		}
		return details;
	}

	private FundsInCreateRequest getExistingFundsInIdForBlacklistRecheck(Connection conn, String orgCode,
			Integer fundsInId) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		FundsInCreateRequest details = new FundsInCreateRequest();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_IN_ID_FOR_BLACKLIST.getQuery());
			preparedStatment.setInt(1, fundsInId);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {

				String json = rs.getString(PI_ATTRIB);
				if (null != json) {
					details = JsonConverterUtil.convertToObject(FundsInCreateRequest.class, rs.getString(PI_ATTRIB));
				}
				details.setFundsInId(rs.getInt(FUNDSIN_ID));
				details.setAccId(rs.getInt(ACCOUNTID));
				details.setContactId(rs.getInt(CONTACT_ID2));
				details.addAttribute(STATUS, rs.getString(PI_COMPLIANCE_STATUS));
				details.addAttribute(Constants.BLACKLIST_STATUS,
						ServiceStatus.getStatusAsString(rs.getInt(BLACKLIST_STATUS)));

			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}
		return details;
	}

	private void getFundsInContactAccountDetailsForBlacklistRecheck(FundsInCreateRequest request, Connection conn,
			String tradeAccountNumber, String orgCode) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_IN_ACCOUNT_CONTACT_DETAILS.getQuery());
			preparedStatment.setString(1, orgCode);
			preparedStatment.setString(2, tradeAccountNumber);
			preparedStatment.setString(3, orgCode);
			rs = preparedStatment.executeQuery();
			List<Contact> contacts = new ArrayList<>();
			Integer count = 1;
			while (rs.next()) {
				if (count == 1) {
					request.setOrgId(rs.getInt(REQUEST_ORG_ID));
					Account acc = JsonConverterUtil.convertToObject(Account.class, rs.getString(ACATTRIB));
					acc.setId(rs.getInt(ACCOUNTID));
					acc.setAccountStatus(rs.getString(ACCOUNTSTATUS));
					request.setAccId(acc.getId());
					request.addAttribute(ACCOUNT, acc);
					request.addAttribute(Constants.FIELD_NO_OF_CONTACT, rs.getInt("contacts"));
				}
				Contact con = JsonConverterUtil.convertToObject(Contact.class, rs.getString(CONTACTATTRIB));
				con.setId(rs.getInt(CONTACT_ID));
				con.setContactStatus(rs.getString(CONTACT_STATUS));
				request.setContactId(con.getId());
				contacts.add(con);
				count++;
			}
			request.addAttribute(Constants.FIELD_OLD_CONTACTS, contacts);
		} catch (Exception e) {
			LOG.error(" ", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}

	}

	/**
	 * Gets the existing funds in id.
	 *
	 * @param conn
	 *            the conn
	 * @param orgCode
	 *            the org code
	 * @param fundsInId
	 *            the funds in id
	 * @return the existing funds in id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FundsInCreateRequest getExistingFundsInIdForFraugster(Connection conn, String orgCode, Integer fundsInId)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		FundsInCreateRequest details = new FundsInCreateRequest();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_IN_ID_FOR_FRAUGSTER.getQuery());
			preparedStatment.setInt(1, fundsInId);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {

				String json = rs.getString(PI_ATTRIB);
				if (null != json) {
					details = JsonConverterUtil.convertToObject(FundsInCreateRequest.class, rs.getString(PI_ATTRIB));
				}
				details.setFundsInId(rs.getInt(FUNDSIN_ID));
				details.setAccId(rs.getInt(ACCOUNTID));
				details.setContactId(rs.getInt(CONTACT_ID2));
				details.setDeviceInfo(createDeviceInfoObject(rs));
				details.addAttribute(STATUS, rs.getString(PI_COMPLIANCE_STATUS));
				details.addAttribute(Constants.FRAUGSTER_STATUS,
						ServiceStatus.getStatusAsString(rs.getInt(FRAUGSTER_STATUS)));

			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
		}
		return details;
	}

	private FundsInCreateRequest getExistingFundsInId(Connection conn, String orgCode, Integer fundsInId)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		FundsInCreateRequest details = new FundsInCreateRequest();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_IN_ID.getQuery());
			preparedStatment.setInt(1, fundsInId);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {

				String json = rs.getString(PI_ATTRIB);
				if (null != json) {
					details = JsonConverterUtil.convertToObject(FundsInCreateRequest.class, rs.getString(PI_ATTRIB));
				}
				details.setFundsInId(rs.getInt(FUNDSIN_ID));
				details.addAttribute(STATUS, rs.getString(PI_COMPLIANCE_STATUS));
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
	 * Creates the device info object.
	 *
	 * @param rs
	 *            the rs
	 * @return the device info
	 * @throws SQLException
	 *             the SQL exception
	 */
	private DeviceInfo createDeviceInfoObject(ResultSet rs) throws SQLException {
		DeviceInfo dInfo = new DeviceInfo();
		dInfo.setBrowserLanguage(rs.getString("BrowserLanguage"));
		dInfo.setBrowserMajorVersion(rs.getString("BrowserVersion"));
		dInfo.setBrowserName(rs.getString("BrowserName"));

		if (null != rs.getString("BrowserOnline")) {
			if (rs.getString("BrowserOnline").equals("1"))
				dInfo.setBrowserOnline("true");
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
	 * Gets the funds in contact account details.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @param tradeAccountNumber
	 *            the trade account number
	 * @param orgCode
	 *            the org code
	 * @return the funds in contact account details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void getFundsInContactAccountDetails(FundsInCreateRequest request, Connection conn,
			String tradeAccountNumber, String orgCode) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_IN_ACCOUNT_CONTACT_DETAILS.getQuery());
			preparedStatment.setString(1, orgCode);
			preparedStatment.setString(2, tradeAccountNumber);
			preparedStatment.setString(3, orgCode);
			rs = preparedStatment.executeQuery();
			StringBuilder listOfWatchListIds = new StringBuilder();
			List<Contact> contacts = new ArrayList<>();
			List<String> watchListDetails = new ArrayList<>();
			MultiMap multiMapWatchlistCon = new MultiHashMap();//Add For AT-2986
			Integer count = 1;
			while (rs.next()) {
				if (count == 1) {
					setClientOrgId(request, rs);
					Account acc = JsonConverterUtil.convertToObject(Account.class, rs.getString(ACATTRIB));
					acc.setId(rs.getInt(ACCOUNTID));
					acc.setCustomerType(rs.getInt("custType"));
					acc.setAccountStatus(rs.getString(ACCOUNTSTATUS));
					request.setAccId(acc.getId());
					request.addAttribute(ACCOUNT, acc);
					request.addAttribute(Constants.FIELD_NO_OF_CONTACT, rs.getInt("contacts"));
					request.addAttribute(Constants.LE_UPDATE_DATE, rs.getTimestamp("leUpdateDate"));
					request.addAttribute(ACCOUNT_TM_FLAG, rs.getInt(ACCOUNT_TM_FLAG));
					request.addAttribute("DormantFlag", rs.getInt("DormantFlag"));
				}
				Contact con = JsonConverterUtil.convertToObject(Contact.class, rs.getString(CONTACTATTRIB));
				con.setId(rs.getInt(CONTACT_ID));
				con.setContactStatus(rs.getString(CONTACT_STATUS));
				con.setPoiExists(rs.getInt("poiExists"));
				request.setContactId(con.getId());
				contacts.add(con);
				watchListDetails.add(rs.getString(WATCHLISTREASONNAME));
				multiMapWatchlistCon.put(rs.getInt(TRADE_CONTACT_ID), rs.getString(WATCHLISTREASONNAME));
				count++;
			}
			request.addAttribute(Constants.FIELD_OLD_CONTACTS, contacts);
			request.addAttribute(WATCHLISTID, listOfWatchListIds.toString());
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
	 * Sets the client org id.
	 *
	 * @param request
	 *            the request
	 * @param rs
	 *            the rs
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void setClientOrgId(FundsInCreateRequest request, ResultSet rs) throws SQLException {
		if (0 == rs.getInt("OldOrganizationId")) {
			request.setOrgId(rs.getInt(REQUEST_ORG_ID));
		} else {
			request.setOrgId(rs.getInt("OldOrganizationId"));
		}
	}
	
	/**
	 * Gets the funds in request by id.
	 *
	 * @param fundsInId
	 *            the funds in id
	 * @return the funds in request by id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsInCreateRequest getFundsInRequestById(Integer fundsInId) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		Connection connection = null;
		ResultSet rs = null;
		FundsInCreateRequest fundsOutRequest = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatment = connection
					.prepareStatement(DBQueryConstant.GET_FUNDSIN_REQUEST_BY_PAYMENTINID.getQuery());
			preparedStatment.setInt(1, fundsInId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				fundsOutRequest = JsonConverterUtil.convertToObject(FundsInCreateRequest.class,
						rs.getString("Attributes"));
				fundsOutRequest.setAccId(rs.getInt(ACCOUNTID));
				fundsOutRequest.setContactId(rs.getInt(CONTACT_ID2));
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
	 * Business -- Update sanction, custom and fraugster check status into
	 * paymentIn table after performing repeat check.
	 * 
	 * Implementation--- 1) If operation is SANCTION_RESEND then call
	 * updateSanctionServiceStatus() 2) If operation is CUSTOMCHECK_RESEND then
	 * call updateCustomCheckServiceStatus()
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateServiceStatusIntoPaymentIn(Message<MessageContext> message)
			throws ComplianceException {

		try {
			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();

			ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			FundsInBaseRequest fRequest = (FundsInBaseRequest) exchange.getRequest();
			if (ServiceInterfaceType.FUNDSIN == eventType && OperationEnum.SANCTION_RESEND == operation) {
				SanctionResendResponse resendResponse = message.getPayload().getGatewayMessageExchange()
						.getResponse(SanctionResendResponse.class);
				String status = resendResponse.getSummary().getStatus();
				updateSanctionServiceStatus(status, fRequest.getFundsInId(), token.getUserID());
			} else if (ServiceInterfaceType.FUNDSIN == eventType && OperationEnum.CUSTOMCHECK_RESEND == operation) {
				CustomCheckResendResponse resendResponse = message.getPayload().getGatewayMessageExchange()
						.getResponse(CustomCheckResendResponse.class);
				String status = resendResponse.getResponse().getOverallStatus();
				updateCustomCheckServiceStatus(status, fRequest.getFundsInId(), token.getUserID());
			} else if (ServiceInterfaceType.FUNDSIN == eventType && OperationEnum.FRAUGSTER_RESEND == operation) {

				FundsInFraugsterResendRequest resendRequest = message.getPayload().getGatewayMessageExchange()
						.getRequest(FundsInFraugsterResendRequest.class);
				FundsInCreateRequest oldRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsInCreateRequest.class);
				MessageExchange fExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
				FraugsterPaymentsInResponse fpResponse = fExchange.getResponse(FraugsterPaymentsInResponse.class);

				String status = fpResponse.getContactResponses().get(0).getStatus();
				updateFraugsterServiceStatus(status, oldRequest.getFundsInId(), token.getUserID());
			}

			else if (ServiceInterfaceType.FUNDSIN == eventType && OperationEnum.BLACKLIST_RESEND == operation) {

				FundsInBlacklistResendRequest resendRequest = message.getPayload().getGatewayMessageExchange()
						.getRequest(FundsInBlacklistResendRequest.class);
				FundsInCreateRequest oldRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsInCreateRequest.class);
				MessageExchange fExchange = message.getPayload()
						.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
				InternalServiceResponse fpResponse = fExchange.getResponse(InternalServiceResponse.class);

				String status = fpResponse.getContacts().get(0).getContactStatus();
				updateBlacklistServiceStatus(status, oldRequest.getFundsInId(), token.getUserID());
			}
		} catch (Exception e) {
			LOG.error("Error while updating PaymentIn service status", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Update fraugster status in paymentIn table.
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
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_IN_BLACKLIST_STATUS.getQuery());

			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			preparedStatment.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(3, userID);
			preparedStatment.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(5, id);
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
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
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_IN_SANCTION_STATUS.getQuery());
			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			preparedStatment.setInt(2, userID);
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, id);
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
	}

	/**
	 * Update fraugster status in paymentIn table.
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
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_IN_FRAUGSTER_STATUS.getQuery());

			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			preparedStatment.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(3, userID);
			preparedStatment.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(5, id);
			preparedStatment.execute();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
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
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FUNDS_IN_CUSTOMCHECK_STATUS.getQuery());
			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(status));
			preparedStatment.setInt(2, userID);
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, id);
			preparedStatment.execute();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
	}

	/**
	 * update sanction status in paymentIn table if operation is SANCTION_UPDATE
	 * then update paymentIn table for sanction status.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */

	public Message<MessageContext> updateFundsInSanctionStatus(Message<MessageContext> message)
			throws ComplianceException {

		try {
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			if (operation == OperationEnum.SANCTION_UPDATE) {
				MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
				SanctionUpdateRequest sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);
				SanctionUpdateData data = sanctionUpdateRequest.getSanctions().get(0);
				EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId());
				updateSanctionServiceStatus(eventServiceLog.getStatus(), sanctionUpdateRequest.getResourceId(),
						token.getUserID());
			}
		} catch (Exception e) {
			LOG.error("Error in AbstractRegistrationDBService updateFundsInSanctionStatus()", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	/**
	 * Delete payment in.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> deletePaymentIn(Message<MessageContext> message) throws ComplianceException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preparedStatment = null;
		int updateCount = 0;
		OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		boolean isDeleted = false;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		FundsInDeleteRequest fDeleteRequest = messageExchange.getRequest(FundsInDeleteRequest.class);
		try {

			if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.DELETE_OPI) {

				conn = getConnection(Boolean.FALSE);
				preparedStatment = conn.prepareStatement(DBQueryConstant.DELETE_FUNDS_IN.getQuery());
				preparedStatment.setString(1, FundsInComplianceStatus.REVERSED.name());
				preparedStatment.setInt(2, token.getUserID());
				preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(4, fDeleteRequest.getPaymentFundsInId());
				preparedStatment.setString(5, fDeleteRequest.getOrgCode());
				updateCount = preparedStatment.executeUpdate();

			}

			if (updateCount > 0) {
				isDeleted = true;
				isNullLockReleasedOn(fDeleteRequest, conn);
			}

			MessageExchange ccExchange = new MessageExchange();
			FundsInDeleteResponse response = new FundsInDeleteResponse();
			fDeleteRequest.addAttribute("isFundsInDeleted", isDeleted);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.FUNDSIN_DELETE_SERVICE);
			ccExchange.setRequest(fDeleteRequest);
			ccExchange.setResponse(response);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return message;
	}

	/**
	 * Checks if is null lock released on.
	 *
	 * @param fDeleteRequest
	 *            the f delete request
	 * @param conn
	 *            the conn
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void isNullLockReleasedOn(FundsInDeleteRequest fDeleteRequest, Connection conn) throws ComplianceException {

		ResultSet rs = null;
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_IS_LOCK_RELEASED_ON.getQuery());
			preparedStatment.setInt(1, fDeleteRequest.getPaymentFundsInId());
			preparedStatment.setString(2, fDeleteRequest.getOrgCode());
			rs = preparedStatment.executeQuery();

			while (rs.next()) {

				if (null == rs.getTimestamp("LockReleasedOn")) {

					updateLockReleasedOn(fDeleteRequest, conn);
				}
			}
		} catch (Exception e) {
			LOG.error(" ", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);

		}
	}

	/**
	 * Update lock released on.
	 *
	 * @param fDeleteRequest
	 *            the f delete request
	 * @param conn
	 *            the conn
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void updateLockReleasedOn(FundsInDeleteRequest fDeleteRequest, Connection conn) throws ComplianceException {
		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_LOCK_RELEASED_ON.getQuery());
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, fDeleteRequest.getPaymentFundsInId());
			preparedStatment.setString(3, fDeleteRequest.getOrgCode());
			preparedStatment.executeUpdate();

		} catch (Exception e) {
			LOG.error(" ", e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Gets the failed funds in details.
	 *
	 * @param paymentInId
	 *            the payment in id
	 * @return the failed funds in details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public FundsInCreateRequest getFailedFundsInDetails(Integer paymentInId) throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FundsInCreateRequest fundsInDetails = new FundsInCreateRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_DETAILS_FOR_FAILED_FUNDS_IN.getQuery());
			preparedStatement.setInt(1, paymentInId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String json = resultSet.getString(PI_ATTRIB);
				if (null != json) {
					fundsInDetails = JsonConverterUtil.convertToObject(FundsInCreateRequest.class,
							resultSet.getString(PI_ATTRIB));
				}
				fundsInDetails.setFundsInId(paymentInId);
				fundsInDetails.setAccId(resultSet.getInt(ACCOUNTID));
				fundsInDetails.getTrade().setTradeAccountNumber(resultSet.getString("tradeaccountnumber"));
				fundsInDetails.setContactId(resultSet.getInt(CONTACT_ID2));
				fundsInDetails.setDeviceInfo(createDeviceInfoObject(resultSet));
				fundsInDetails.addAttribute(Constants.STATUS, resultSet.getString(PI_COMPLIANCE_STATUS));
				fundsInDetails.addAttribute(Constants.FRAUGSTER_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt(FRAUGSTER_STATUS)));
				fundsInDetails.addAttribute(Constants.BLACKLIST_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt(BLACKLIST_STATUS)));
				fundsInDetails.addAttribute(Constants.SANCTION_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("SanctionStatus")));
				fundsInDetails.addAttribute(Constants.CUSTOM_CHECK_STATUS,
						ServiceStatus.getStatusAsString(resultSet.getInt("CustomCheckStatus")));

			}
			getFailedFundsInContactAccountDetails(fundsInDetails, connection, fundsInDetails.getTradeAccountNumber(),
					fundsInDetails.getOrgCode());
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return fundsInDetails;
	}

	/**
	 * Gets the failed funds in contact account details.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @param tradeAccountNumber
	 *            the trade account number
	 * @param orgCode
	 *            the org code
	 * @return the failed funds in contact account details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void getFailedFundsInContactAccountDetails(FundsInCreateRequest request, Connection conn,
			String tradeAccountNumber, String orgCode) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;

		try {
			String contactJoin = " AND c.id = ?";
			preparedStatment = conn.prepareStatement(
					DBQueryConstant.GET_FUNDS_IN_ACCOUNT_CONTACT_DETAILS.getQuery().concat(contactJoin));
			preparedStatment.setString(1, orgCode);
			preparedStatment.setString(2, tradeAccountNumber);
			preparedStatment.setString(3, orgCode);
			preparedStatment.setString(4, request.getContactId().toString());
			rs = preparedStatment.executeQuery();
			Integer count = 1;
			Integer noOfContacts = 0;
			List<Contact> contacts = new ArrayList<>();
			List<String> watchListDetails = new ArrayList<>();
			MultiMap multiMapWatchlistCon = new MultiHashMap();//AT-3502
			while (rs.next()) {

				request.setOrgId(rs.getInt(REQUEST_ORG_ID));
				Account acc = JsonConverterUtil.convertToObject(Account.class, rs.getString(ACATTRIB));
				acc.setId(rs.getInt(ACCOUNTID));
				acc.setAccountStatus(rs.getString(ACCOUNTSTATUS));
				request.setAccId(acc.getId());
				request.addAttribute(ACCOUNT, acc);
				request.addAttribute(Constants.LE_UPDATE_DATE, rs.getTimestamp("leUpdateDate"));
				Contact con = JsonConverterUtil.convertToObject(Contact.class, rs.getString(CONTACTATTRIB));
				con.setId(rs.getInt(CONTACT_ID));
				con.setContactStatus(rs.getString(CONTACT_STATUS));
				con.setPoiExists(rs.getInt("poiExists"));
				request.addAttribute("contact", con);
				contacts.add(con);
				if (null != rs.getString(WATCHLISTREASONNAME) && !rs.getString(WATCHLISTREASONNAME).isEmpty()) {
					request.addAttribute(WATCHLISTID + count, rs.getInt(WATCHLISTID));
					watchListDetails.add(rs.getString(WATCHLISTREASONNAME));
					request.addAttribute("watchlistreasonname1", rs.getString(WATCHLISTREASONNAME));
				}
				multiMapWatchlistCon.put(rs.getInt(TRADE_CONTACT_ID), rs.getString(WATCHLISTREASONNAME));
				noOfContacts++;
			}
			request.addAttribute("noOfContacts", noOfContacts);
			request.addAttribute(Constants.FIELD_OLD_CONTACTS, contacts);
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
	 * Gets the failed funds in ESL details.
	 *
	 * @param request
	 *            the request
	 * @return the failed funds in ESL details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public List<EventServiceLog> getFailedFundsInESLDetails(FundsInCreateRequest request) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		List<EventServiceLog> eventServiceLogList = new ArrayList<>();
		try {
			conn = getConnection(Boolean.TRUE);
			String query = DBQueryConstant.GET_ESL_FOR_SERVICE_FAILED_FUNDSIN.getQuery();
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, request.getFundsInId().toString());
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				EventServiceLog eventServiceLog = new EventServiceLog();
				eventServiceLog.setEntityType(EntityEnum.getEntityTypeAsString(rs.getInt("EntityType")));
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
	 * AT-3346
	 * Check first funds in.
	 *
	 * @param conn the conn
	 * @param fundsInBaseRequest the funds in base request
	 * @param details the details
	 * @throws ComplianceException 
	 */
	@SuppressWarnings("null")
	public boolean checkFirstFundsIn(FundsInCreateRequest details) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Integer count = 0;
		boolean isFirstFundsIn = false;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ZERO_CLEAR_FUNDSIN_FOR_ACCOUNT.getQuery());
			preparedStatment.setString(1,details.getTradeAccountNumber());
			preparedStatment.setString(2,details.getTradeAccountNumber());
			rs = preparedStatment.executeQuery();
			while(rs.next()) {
				if(count.equals(rs.getInt(NUM_OF_FUNDS_IN_CLEAR)) &&
						(ServiceStatus.NOT_PERFORMED.getServiceStatusAsInteger().equals(rs.getInt("FirstCreditCheck"))
								|| ServiceStatus.FAIL.getServiceStatusAsInteger().equals(rs.getInt("FirstCreditCheck"))))
					return true;
			}
		}catch(Exception e) {
			LOG.error("Error while fetching checkFirstFundsIn() details :", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return isFirstFundsIn;
	}

	/**
	 * AT-3349
	 * Check any funds clear for contact.
	 *
	 * @param details the details
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	public boolean checkAnyFundsClearForContact(FundsInCreateRequest details,Timestamp leUpdateDate) throws ComplianceException{
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Integer count = 0;
		Integer fundsInCount = 0;
		Integer fundsOutCount = 0;
		boolean isAnyFundsClear = false;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_NUMBER_OF_FI_CLEAR_AFTER_LEDATE.getQuery());
			preparedStatment.setString(1,details.getTradeAccountNumber());
			preparedStatment.setInt(2,details.getTrade().getTradeContactId());
			preparedStatment.setTimestamp(3,leUpdateDate);
			rs = preparedStatment.executeQuery();
			while(rs.next()) {
				fundsInCount = rs.getInt(NUM_OF_FUNDS_IN_CLEAR);
			}
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_NUMBER_OF_FO_CLEAR_AFTER_LEDATE.getQuery());
			preparedStatment.setString(1,details.getTradeAccountNumber());
			preparedStatment.setInt(2,details.getTrade().getTradeContactId());
			preparedStatment.setTimestamp(3,leUpdateDate);
			rs = preparedStatment.executeQuery();
			while(rs.next()) {
				fundsOutCount = rs.getInt("NumOfFundsOutClear");
			}
			if(count == (fundsInCount+fundsOutCount)) {
				isAnyFundsClear = true;
			}
		}catch(Exception e) {
			LOG.error("Error while fetching checkAnyFundsClearForContact() details :", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return isAnyFundsClear;
	}
	
	/**
	 * Update EDD status in account.
	 *
	 * @param conn the conn
	 * @param fundsInCreateResponse the funds in create response
	 * @param ccResponse the cc response
	 * @throws ComplianceException the compliance exception
	 */
	private void updateEDDStatusInAccount(Connection conn, FundsInCreateResponse fundsInCreateResponse,
			CustomCheckResponse ccResponse) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_FIRST_EDD_CHECK_IN_ACCOUNT.getQuery());
			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(ccResponse.getFirstCreditCheck().getStatus()));
			preparedStatment.setString(2, fundsInCreateResponse.getTradeAccountNumber());
		    preparedStatment.executeUpdate();
		} catch(Exception e) {
			LOG.error("Error while updatig account table column Edd_check : ", e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	
	/**
	 * Add for AT-3738
	 * Check CDINC first funds in.
	 *
	 * @param details the details
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("null")
	public boolean checkCDINCFirstFundsIn(FundsInCreateRequest details) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Integer count = 0;
		boolean isCDINCFirstFundsIn = false;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ZERO_CLEAR_FUNDSIN_FOR_CDINC_ACCOUNT.getQuery());
			preparedStatment.setString(1,details.getTradeAccountNumber());
			rs = preparedStatment.executeQuery();
			while(rs.next()) {
				if(count.equals(rs.getInt(NUM_OF_FUNDS_IN_CLEAR)))
					return true;
			}
		}catch(Exception e) {
			LOG.error("Error while fetching checkCDINCFirstFundsIn() details :", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return isCDINCFirstFundsIn;
	}

	public FundsInCreateRequest getFundsInDetailsForIntuition(Integer tradePayId)throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FundsInCreateRequest fundsInDetails = new FundsInCreateRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_IN_FOR_DETAILS_INTUITION.getQuery());
			preparedStatement.setInt(1, tradePayId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fundsInDetails = setFundsInDetailsForIntuition(resultSet);
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return fundsInDetails;
	}

	/**
	 * Sets the funds in details for intuition.
	 *
	 * @param resultSet the result set
	 * @param fundsInDetails the funds in details
	 * @throws SQLException the SQL exception
	 */
	private FundsInCreateRequest setFundsInDetailsForIntuition(ResultSet resultSet)
			throws SQLException {
		FundsInCreateRequest fundsInDetails = new FundsInCreateRequest();
		String json = resultSet.getString(PI_ATTRIB);
		if (null != json) {
			fundsInDetails = JsonConverterUtil.convertToObject(FundsInCreateRequest.class,
					resultSet.getString(PI_ATTRIB));
		}
		fundsInDetails.setFundsInId(resultSet.getInt(PAYMENT_IN_ID));
		fundsInDetails.setAccId(resultSet.getInt(ACCOUNTID));
		fundsInDetails.getTrade().setTradeAccountNumber(resultSet.getString("Tradeaccountnumber"));
		fundsInDetails.getTrade().setTradeContactId(resultSet.getInt(TRADE_CONTACT_ID));
		fundsInDetails.setContactId(resultSet.getInt(CONTACT_ID2));
		fundsInDetails.addAttribute(Constants.STATUS, FundsInComplianceStatus
				.getFundsInComplianceStatusAsString(resultSet.getInt(PI_COMPLIANCE_STATUS)));
		fundsInDetails.addAttribute(Constants.FRAUGSTER_STATUS,
				ServiceStatus.getStatusAsString(resultSet.getInt(FRAUGSTER_STATUS)));
		fundsInDetails.addAttribute(Constants.BLACKLIST_STATUS,
				ServiceStatus.getStatusAsString(resultSet.getInt(BLACKLIST_STATUS)));
		fundsInDetails.addAttribute(Constants.SANCTION_STATUS,
				ServiceStatus.getStatusAsString(resultSet.getInt("SanctionStatus")));
		fundsInDetails.addAttribute(Constants.CUSTOM_CHECK_STATUS,
				ServiceStatus.getStatusAsString(resultSet.getInt("CustomCheckStatus")));
		fundsInDetails.addAttribute("AccountVersion", resultSet.getInt("AccountVersion"));
		fundsInDetails.addAttribute(ACCOUNT_TM_FLAG, resultSet.getInt(ACCOUNT_TM_FLAG));
		fundsInDetails.addAttribute("STPFlag", resultSet.getString("InitialStatus"));
		fundsInDetails.addAttribute("orgcode", resultSet.getString("OrganizationCode"));
		fundsInDetails.addAttribute(Constants.ETAILER, resultSet.getString(Constants.ETAILER)); //AT-5310
		
		return fundsInDetails;
	}

	public String getCountryCheckStatus(FundsInCreateRequest fundsInCreateRequest) throws ComplianceException {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String countryCheckStatus = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_FUNDS_IN_COUNTRY_CHECK_STATUS_FOR_INTUITION.getQuery());
			preparedStatement.setInt(1, fundsInCreateRequest.getFundsInId());
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

	public String getPaymentInStatusUpdateReason(FundsInCreateRequest fundsInCreateRequest) throws ComplianceException {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String statusUpdateReason= null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_STATUS_UPDATE_REASON_FOR_PAYMENT_IN_INTUITION.getQuery());
			preparedStatement.setInt(1, fundsInCreateRequest.getFundsInId());
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
	
	public Message<MessageContext> updateAccountIntuitionClientRiskLevel(Message<MessageContext> message) {
        Connection connection = null;
        String paymentStatus = null;
        try {

        	
        	
			TransactionMonitoringPaymentInResponse tmResponse = (TransactionMonitoringPaymentInResponse) message
					.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getResponse();
			
			TransactionMonitoringPaymentsInRequest request = (TransactionMonitoringPaymentsInRequest) message
					.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getRequest();

			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
            if(operation == OperationEnum.FUNDS_IN || operation == OperationEnum.RECHECK_FAILURES) {
            	FundsInCreateResponse response = (FundsInCreateResponse) message.getPayload().getGatewayMessageExchange()
                        .getResponse();
                paymentStatus = response.getStatus();
            } else {
                paymentStatus = tmResponse.getPaymentStatus();
            }
			
            String clientRiskLevel = (String) tmResponse.getAdditionalAttribute(CLIENT_RISK_LEVEL);
            Integer accountTMFlag = (Integer) request.getAdditionalAttribute(ACCOUNT_TM_FLAG);
            if(clientRiskLevel != null && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
            	 UserProfile userToken = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
                 connection = getConnection(Boolean.FALSE);
                 beginTransaction(connection);
                 updateAccountIntuitionRiskLevel(request.getAccountId(), clientRiskLevel, userToken, connection);
                 updatePaymentInIntuitionStatus(request.getFundsInId(), tmResponse.getStatus(), userToken, connection);
                 
					if (request.getUpdateStatus() != null
							&& request.getUpdateStatus().equalsIgnoreCase(FundsInComplianceStatus.HOLD.name())
							&& paymentStatus != null) {
	                	 updatePaymentInStatus(paymentStatus,request.getFundsInId(), connection);
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
	
	 public void updatePaymentInIntuitionStatus(Integer fundsInId, String status,
				UserProfile userToken, Connection connection) throws ComplianceException {
	        PreparedStatement statement = null;
	        try {
	            statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_IN_INTUITION_CHECK_STATUS.getQuery());

	            statement.setInt(1, ServiceStatus.getStatusAsInteger(status));
	            statement.setInt(2, userToken.getUserID());
	            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
	            statement.setInt(4, fundsInId);

	            statement.executeUpdate();
	        } catch (Exception e) {
	            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
	        } finally {
	            closePrepareStatement(statement);
	        }
	    }
	 
	 
	 public void getFundsInContactAccountDetailsForDeleteOpi(FundsInDeleteRequest fundsInDeleteRequest) throws ComplianceException {
			PreparedStatement preparedStatment = null;
			ResultSet rs = null;
			Connection conn = null;

			try {
				conn = getConnection(Boolean.TRUE);
				preparedStatment = conn.prepareStatement(DBQueryConstant.GET_FUNDS_IN_ACCOUNT_DETAILS_FOR_DELETE_OPI.getQuery());
				preparedStatment.setString(1, fundsInDeleteRequest.getTradeAccountNumber());
				preparedStatment.setInt(2, fundsInDeleteRequest.getPaymentFundsInId());
				preparedStatment.setString(3, fundsInDeleteRequest.getOrgCode());
				rs = preparedStatment.executeQuery();
				while (rs.next()) {
						Account acc = JsonConverterUtil.convertToObject(Account.class, rs.getString(ACATTRIB));
						acc.setId(rs.getInt(ACCOUNTID));
						acc.setCustomerType(rs.getInt("custType"));
						fundsInDeleteRequest.addAttribute(ACCOUNT, acc);
						fundsInDeleteRequest.addAttribute(PAYMENT_IN_ID, rs.getInt(PAYMENT_IN_ID));
						
				}
				
			} catch (Exception e) {
				LOG.error(" ", e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatment);
				 closeConnection(conn);
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
				
				FundsInBaseRequest fRequest = (FundsInBaseRequest) exchange.getRequest();
				FundsInCreateResponse fundsInCreateResponse = (FundsInCreateResponse) message.getPayload()
						.getGatewayMessageExchange().getResponse();
				conn = getConnection(Boolean.FALSE);
				fundsInCreateResponse.setStatus(FundsInComplianceStatus.HOLD.name());
				getContactWatchlist(conn, fRequest.getFundsInId(), fundsInCreateResponse);
				fRequest.addAttribute("AtlasSTPFlag", fundsInCreateResponse.getShortResponseCode());
				
			} catch (Exception e) {
				LOG.error("Exception in updatePaymentInstatus()", e);
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
	        String tableName = "PaymentIn";
	        try {
	        	connection = getConnection(Boolean.TRUE);
	        	statement = connection.prepareStatement(DBQueryConstant.GET_PAYMENT_STATUS.getQuery().replace("#",
	        			tableName));
	        	statement.setInt(1, payId);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					status = FundsInComplianceStatus.getFundsInComplianceStatusAsString(resultSet.getInt("ComplianceStatus"));
				
				}
	        } catch (Exception e) {
	            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
	        } finally {
	            closePrepareStatement(statement);
	        }
	        
	        return status;
	    }
	 
		public void updatePaymentInStatus(String paymentStatus, Integer paymentInID, Connection connection)
				throws ComplianceException {
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_IN_STATUS.getQuery());

				statement.setInt(1, 1);
				statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				statement.setInt(3, FundsInComplianceStatus.getFundsInComplianceStatusAsInteger(paymentStatus));
				statement.setInt(4, paymentInID);

				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {

				closePrepareStatement(statement);

			}
		}
		
		/**
		  * Gets the payment in status by trade payment id.
		  *
		  * @param tradePaymentId the trade payment id
		  * @return the payment in status by trade payment id
		  * @throws ComplianceException the compliance exception
		  */
		 //AT-4906
		public Integer getPaymentInStatusByTradePaymentId(Integer tradePaymentId) throws ComplianceException {
			PreparedStatement preparedStatment = null;
			ResultSet resultSet = null;
			Connection conn = null;
			Integer paymentStatus= 0;
			try {
				conn = getConnection(Boolean.TRUE);
				preparedStatment = conn.prepareStatement(
						DBQueryConstant.GET_PAYMENT_STATUS_BY_PAYMENT_TRADE_ID.getQuery().replace("#", "PaymentIn"));
				preparedStatment.setInt(1, tradePaymentId);
				resultSet = preparedStatment.executeQuery();
				while (resultSet.next()) {
					paymentStatus = resultSet.getInt("ComplianceStatus");
				}

			} catch (Exception e) {
				LOG.error("Error in getPaymentInStatusByTradePaymentId() : ", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatment);
				closeConnection(conn);
			}

			return paymentStatus;

		}

		
		/**
		 * Gets the intuition historic payment in.
		 *
		 * @return the intuition historic payment in
		 * @throws ComplianceException the compliance exception
		 */
		//AT-5264
		public List<FundsInCreateRequest> getIntuitionHistoricPaymentIn() throws ComplianceException {
			PreparedStatement preparedStatment = null;
			ResultSet resultSet = null;
			Connection connection = null;
			List<FundsInCreateRequest> fundsInRequests = new ArrayList<>();
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatment = connection.prepareStatement(
						DBQueryConstant.GET_DETAILS_FOR_INTUITION_HISTORIC_PAYMENT_IN_RECORDS.getQuery());
				resultSet = preparedStatment.executeQuery();
				while (resultSet.next()) {
					FundsInCreateRequest fundsInRequest = setFundsInDetailsForIntuition(resultSet);
					fundsInRequest.getTrade().setPaymentTime(resultSet.getString("PaymentTime")); //Added for AT-5486
					fundsInRequests.add(fundsInRequest);
				}
			} catch (Exception e) {
				LOG.error("Error in getIntuitionHistoricPaymentIn() : ", e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatment);
				closeConnection(connection);
			}
			return fundsInRequests;
		}
}