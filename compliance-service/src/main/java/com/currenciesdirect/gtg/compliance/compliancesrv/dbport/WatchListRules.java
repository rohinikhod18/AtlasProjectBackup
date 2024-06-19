/*
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchangeWrapper;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author bnt
 *
 */
public class WatchListRules extends AbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WatchListRules.class);

	/**
	 * Business -- whenever Global check and Country check status is
	 * "WATCH_LIST" then contact should be added in Contact watch list table for
	 * its respective watch list reason. When Fraugster status is "WATCH_LIST"
	 * or "SERVICE_FAILURE" then contact should be added in Contact watch list
	 * table for its respective watch list reason. If status is updated from
	 * "WATCH_LIST" to any other then status then remove entries from contact
	 * watch list table.
	 * 
	 * Implementation-- 1) Create an list of watch list and delete watch list.
	 * a) if service type is "INTERNAL_RULE_SERVICE" then check statuses for
	 * "USGLOBALCHECK" and "COUNTRYCHECK" if "USGLOBALCHECK" status is
	 * "WATCH_LIST" then add in watch list else add in delete watch list. (for
	 * all registration flow) b) if service type is "FRAUGSTER_SERVICE" and
	 * having status "WATCH_LIST" or "SERVICE_FAILURE" then add in watch list
	 * else add in delete watch list. 2) After generating delete watch list ,
	 * delete records from contact watch list. 3) Set watch list status to
	 * update Account table (i.e watchlist column), set status in
	 * "WATCHLIST_STATUS" additional attribute. 4) save watch list entries in
	 * contactWatchlist table.
	 * 
	 * @param msg
	 * @return
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> msg) {
		UserProfile token = (UserProfile) msg.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		List<WatchListEnry> watchList = new ArrayList<>();
		List<WatchListEnry> deleteWatchList = new ArrayList<>();
		MessageExchange messageExchange = msg.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest request = messageExchange.getRequest(RegistrationServiceRequest.class);
		OperationEnum operation = msg.getPayload().getGatewayMessageExchange().getOperation();
		try {
			for (MessageExchangeWrapper exchange : msg.getPayload().getMessageCollection()) {
				switch (exchange.getMessageExchange().getServiceTypeEnum()) {

				case INTERNAL_RULE_SERVICE:
					InternalServiceResponse iResponse = (InternalServiceResponse) exchange.getMessageExchange()
							.getResponse();
					updateInternaRulesWatchList(watchList, iResponse, deleteWatchList);
					break;

				case FRAUGSTER_SERVICE:
					handleFraugsterWatchList(msg, watchList, deleteWatchList, operation, exchange);
					break;
				default:
					break;
				}

			}
			checkForKYCWatchlist(watchList, messageExchange, request, operation);

		} catch (Exception ex) {
			LOG.error("error creating watchlist: ", ex);
			msg.getPayload().setFailed(true);
		}
		try {
			/**
			 * added delete method to remove entries from contactWatchlist table
			 * ,fixed issue AT-440: abhijit G
			 */
			deleteFromContactWatchList(deleteWatchList);
			setAccountWatchListStatus(msg, watchList);
			saveWatchList(watchList, token.getUserID());

		} catch (Exception e) {
			msg.getPayload().setFailed(true);
			LOG.error("Error saving: ", e);
		}
		return msg;
	}

	/**
	 * Check for KYC watchlist.
	 *
	 * @param watchList
	 *            the watch list
	 * @param messageExchange
	 *            the message exchange
	 * @param request
	 *            the request
	 * @param operation
	 *            the operation
	 * @param isAddToWatchlist
	 *            the is add to watchlist
	 * @param isAccountModified
	 *            the is account modified
	 */
	private void checkForKYCWatchlist(List<WatchListEnry> watchList, MessageExchange messageExchange,
			RegistrationServiceRequest request, OperationEnum operation) {
		Boolean isCFXForUpdate = OperationEnum.UPDATE_ACCOUNT==operation
				&& CustomerTypeEnum.CFX.name().equals(request.getAccount().getCustType());
		Boolean isKYCSanctionPerformed = (Boolean) messageExchange.getRequest()
				.getAdditionalAttribute("isKYCSanctionPerformed");

		// if CFX customer KYC sanction eligible then add watchlist
		if (Boolean.TRUE.equals(isCFXForUpdate) && null != isKYCSanctionPerformed && Boolean.TRUE.equals(isKYCSanctionPerformed))
			addKYCWatchList(watchList, messageExchange);

	}

	private void handleFraugsterWatchList(Message<MessageContext> msg, List<WatchListEnry> watchList,
			List<WatchListEnry> deleteWatchList, OperationEnum operation, MessageExchangeWrapper exchange) {
		if ((OperationEnum.NEW_REGISTRATION)==operation
				|| (OperationEnum.ADD_CONTACT)==(msg.getPayload().getGatewayMessageExchange().getOperation())) {
			FraugsterSignupResponse fResponse = (FraugsterSignupResponse) exchange.getMessageExchange().getResponse();
			handleFraugsterSignUp(watchList, fResponse);
		} else if ((OperationEnum.UPDATE_ACCOUNT)==(operation)) {
			handleFraugsterOnUpdate(watchList, exchange, deleteWatchList);
		}
	}

	private void setAccountWatchListStatus(Message<MessageContext> msg, List<WatchListEnry> watchList)
			throws ComplianceException {

		RegistrationServiceRequest request = (RegistrationServiceRequest) msg.getPayload().getGatewayMessageExchange()
				.getRequest();
		Account account = request.getAccount();
		Integer accountId = account.getId();
		WatchListDetails watchDetails = getPaymentWatchListReasons(accountId, watchList);
		/**
		 * changes done to update watchlist status for PaymentsIn and
		 * PaymentsOut
		 */
		processWatchList(request, watchDetails);

	}

	private void processWatchList(RegistrationServiceRequest request, WatchListDetails watchDetails) {
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

		request.addAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS, paymentInwatchlistStatus);

		request.addAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS, paymentOutwatchlistStatus);
	}

	/**
	 * Made changes for AT-345 If status is SERVICE_FAILURE then watchlistReson
	 * should be added Changes made by -Saylee
	 */
	private void handleFraugsterSignUp(List<WatchListEnry> watchList, FraugsterSignupResponse fResponse) {
		if (fResponse != null) {
			List<FraugsterSignupContactResponse> identityResponse = fResponse.getContactResponses();
			for (FraugsterSignupContactResponse idResponse : identityResponse) {
				if (ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(idResponse.getStatus())
						|| ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(idResponse.getStatus())) {
					addToWactchList(watchList, idResponse.getId(), WatchList.FRAUGSTER.getDescription());
				}
			}
		}
	}

	/**
	 * Added condition for status is SERVICE_FAILURE -Saylee
	 * 
	 * @param deleteWatchList
	 */
	private void handleFraugsterOnUpdate(List<WatchListEnry> watchList, MessageExchangeWrapper exchange,
			List<WatchListEnry> deleteWatchList) {
		FraugsterOnUpdateResponse fResponse = (FraugsterOnUpdateResponse) exchange.getMessageExchange().getResponse();
		if (fResponse != null) {
			List<FraugsterOnUpdateContactResponse> identityResponse = fResponse.getContactResponses();
			for (FraugsterOnUpdateContactResponse idResponse : identityResponse) {
				if (ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(idResponse.getStatus())
						|| ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(idResponse.getStatus())) {
					addToWactchList(watchList, idResponse.getId(), WatchList.FRAUGSTER.getDescription());
				} else {
					deleteWactchList(deleteWatchList, idResponse.getId(), WatchList.FRAUGSTER.getDescription());
				}
			}
		}
	}

	private void updateInternaRulesWatchList(List<WatchListEnry> watchList, InternalServiceResponse iResponse,
			List<WatchListEnry> deleteWatchList) {
		for (ContactResponse cResponse : iResponse.getContacts()) {
			if (cResponse.getGlobalCheck() != null
					&& ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(cResponse.getGlobalCheck().getStatus())) {

				addToWactchList(watchList, cResponse.getId(), WatchList.USGLOBALCHECK.getDescription());
			} else {
				deleteWactchList(deleteWatchList, cResponse.getId(), WatchList.USGLOBALCHECK.getDescription());
			}
			if (cResponse.getCountryCheck() != null && null != cResponse.getId()
					&& ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(cResponse.getCountryCheck().getStatus())) {
				addToWactchList(watchList, cResponse.getId(), WatchList.COUNTRYCHECK.getDescription());
			} else {
				deleteWactchList(deleteWatchList, cResponse.getId(), WatchList.COUNTRYCHECK.getDescription());
			}
		}
	}

	private void addToWactchList(List<WatchListEnry> watchList, Integer contactId, String type) {
		WatchListEnry entry = new WatchListEnry();
		entry.setContactID(contactId);
		entry.setWatchListName(type);
		watchList.add(entry);
	}

	private void deleteWactchList(List<WatchListEnry> deleteWatchList, Integer contactId, String type) {
		WatchListEnry entry = new WatchListEnry();
		entry.setContactID(contactId);
		entry.setWatchListName(type);
		deleteWatchList.add(entry);
	}

	private void saveWatchList(List<WatchListEnry> watchList, Integer user) throws ComplianceException {
		if (watchList != null && !watchList.isEmpty()) {
			Connection connection = null;

			try {
				connection = getConnection(Boolean.FALSE);
				beginTransaction(connection);
				insertIntoWatchList(connection, watchList, user);
				commitTransaction(connection);
			} finally {
				closeConnection(connection);
			}
		}
	}

	/**
	 * Delete from contact watch list. if contact watch list changes from watch
	 * list to any other status then remove previous entries from contact watch
	 * list table.
	 * 
	 * @param deleteWatchList
	 *            the delete watch list
	 * @throws ComplianceException
	 *             the compliance exception
	 * @author abhijeetg
	 */
	private void deleteFromContactWatchList(List<WatchListEnry> deleteWatchList) throws ComplianceException {
		if (deleteWatchList != null && !deleteWatchList.isEmpty()) {
			Connection connection = null;
			try {
				connection = getConnection(Boolean.FALSE);
				beginTransaction(connection);
				deleteFromWatchList(connection, deleteWatchList);
				commitTransaction(connection);
			} finally {
				closeConnection(connection);
			}
		}
	}

	private void insertIntoWatchList(Connection connection, List<WatchListEnry> watchList, Integer userid)
			throws ComplianceException {
		for (WatchListEnry details : watchList) {
			try (PreparedStatement statement = connection
					.prepareStatement(DBQueryConstant.INSERT_INTO_CONTACT_WATCHLIST.getQuery())) {
				statement.setString(1, details.getWatchListName());
				statement.setInt(2, details.getContactID());
				statement.setInt(3, userid);
				statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				statement.executeUpdate();
			} catch (SQLException se) {
				if (!se.getMessage().startsWith("Cannot insert duplicate key"))
					LOG.warn("Can not insert multiple records for same watchlist reason : ", se);
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
		}
	}

	/**
	 * Delete from watch list.
	 *
	 * @param connection
	 *            the connection
	 * @param deleteWatchList
	 *            the delete watch list
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void deleteFromWatchList(Connection connection, List<WatchListEnry> deleteWatchList)
			throws ComplianceException {
		for (WatchListEnry details : deleteWatchList) {
			try (PreparedStatement statement = connection
					.prepareStatement(DBQueryConstant.DELETE_FROM_CONTACT_WATCHLIST.getQuery())) {
				statement.setString(1, details.getWatchListName());
				statement.setInt(2, details.getContactID());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
		}
	}

	private static class WatchListEnry {

		/** The contact ID. */
		private int contactID;
		private String watchListName;

		/**
		 * Gets the contact ID.
		 *
		 * @return the contact ID
		 */
		public int getContactID() {
			return contactID;
		}

		/**
		 * Sets the contact ID.
		 *
		 * @param contactID
		 *            the new contact ID
		 */
		public void setContactID(int contactID) {
			this.contactID = contactID;
		}

		/**
		 * Gets the watch list name.
		 *
		 * @return the watch list name
		 */
		public String getWatchListName() {
			return watchListName;
		}

		/**
		 * Sets the watch list name.
		 *
		 * @param watchListName
		 *            the new watch list name
		 */
		public void setWatchListName(String watchListName) {
			this.watchListName = watchListName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return watchListName;
		}
	}

	/**
	 * Gets the payment watch list reasons.
	 *
	 * @return the payment watch list reasons
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	private WatchListDetails getPaymentWatchListReasons(Integer accountId, List<WatchListEnry> watchLists)
			throws ComplianceException {
		Connection readOnlyConn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		WatchListDetails watchlist = new WatchListDetails();
		StringBuilder watchListNames = new StringBuilder();
		for (WatchListEnry e : watchLists) {
			watchListNames.append('\'').append(e.watchListName).append("',");
		}

		String filter = "''";
		if (watchListNames.length() > 1) {
			watchListNames.deleteCharAt(watchListNames.length() - 1);
			filter = watchListNames.toString();
		}
		
		try {
			readOnlyConn = getConnection(Boolean.TRUE);

			// the query check for new reasons added as well as old contacts on
			// watchlist
			// and inclusive of all watchlist reason, if anything stops payment
			// In and Out
			// also highet risk category
			preparedStatement = readOnlyConn.prepareStatement(DBQueryConstant.GET_WATCHLIST_REASON_LIST_FOR_PAYMENTOUT_AND_PAYMENTIN.getQuery().replace("#",
					filter));
			preparedStatement.setInt(1, accountId);
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

	private void addKYCWatchList(List<WatchListEnry> watchList, MessageExchange messageExchange) {
		RegistrationServiceRequest request = messageExchange.getRequest(RegistrationServiceRequest.class);
		if (request != null) {
			@SuppressWarnings("unchecked")
			List<Contact> identityResponse = (List<Contact>) request
					.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
			for (Contact idResponse : identityResponse) {

				addToWactchList(watchList, idResponse.getId(), WatchList.ACCOUNTUPDATEDCHECK.getDescription());

			}
		}

	}

	@SuppressWarnings("squid:S2095")
	public List<String> getWatchlist(Integer accId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		List<String> whitelists = new ArrayList<>();
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_WATCHLIST_REASONS.getQuery());
			preparedStatment.setInt(1, accId);
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				if (null != rs.getString("reason"))
					whitelists.add(rs.getString("reason"));
			}

		} catch (ComplianceException | SQLException e) {
			LOG.error(" ", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return whitelists;
	}

}
