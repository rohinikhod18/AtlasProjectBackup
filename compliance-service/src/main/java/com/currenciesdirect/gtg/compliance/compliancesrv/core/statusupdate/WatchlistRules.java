package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.DBQueryConstant;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class WatchlistRules extends AbstractDao{
	
	private static final Logger LOG = LoggerFactory.getLogger(WatchlistRules.class);
	
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> processStatusUpdateWatchList(Message<MessageContext> message){
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
		boolean saveWatchList = false;
		WatchListEntry addWatchListEntry = new WatchListEntry();
		RegistrationResponse profileUpdateResponse = (RegistrationResponse) statusUpdateRequest
				.getAdditionalAttribute(Constants.FIELD_BROADCAST_RESPONSE);
		ComplianceAccount account = profileUpdateResponse.getAccount();
		Account oldAccount = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		try {
			for (ComplianceContact contact : account.getContacts()) {
				if(null != contact
						&& ComplianceReasonCode.ONFIDO_SUSPECT.name().equalsIgnoreCase(contact.getCrc().name())) {
					addWatchListEntry.setContactID(contact.getId());
					addWatchListEntry.setWatchListName(WatchList.ONFIDO_SUSPECT.getDescription());
					saveWatchList = true;
				}
			}
		}
		catch(Exception e) {
			LOG.error("error creating watchlist: ", e);
			message.getPayload().setFailed(true);
		}
		try {
			if(saveWatchList) {
				saveWatchList(addWatchListEntry,token.getUserID());
				setAccountWatchListStatus(statusUpdateRequest, addWatchListEntry, oldAccount);
			}
		} catch (Exception e) {
			message.getPayload().setFailed(true);
				LOG.error("Error saving: ", e);
		}
		return message;
	}
	
	private static class WatchListEntry {

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
	 * Adds the watch list.
	 *
	 * @param watchListEntry the watch list entry
	 * @param user the user
	 * @throws ComplianceException the compliance exception
	 */
	private void saveWatchList(WatchListEntry watchListEntry,Integer user) throws ComplianceException {
		if (null != watchListEntry) {
			Connection connection = null;
			try {
				connection = getConnection(Boolean.FALSE);
				beginTransaction(connection);
				insertIntoWatchList(connection, watchListEntry, user);
				commitTransaction(connection);
			} finally {
				closeConnection(connection);
			}
		}
	}
	
	/**
	 * Insert into watch list.
	 *
	 * @param connection the connection
	 * @param watchList the watch list
	 * @param userid the userid
	 * @throws ComplianceException the compliance exception
	 */
	private void insertIntoWatchList(Connection connection, WatchListEntry watchList, Integer userid)
			throws ComplianceException {
			try (PreparedStatement statement = connection
					.prepareStatement(DBQueryConstant.INSERT_INTO_CONTACT_WATCHLIST.getQuery())) {
				statement.setString(1, watchList.getWatchListName());
				statement.setInt(2, watchList.getContactID());
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
	
	/**
	 * Sets the account watch list status.
	 *
	 * @param profileUpdateResponse the profile update response
	 * @param watchList the watch list
	 * @param account the account
	 * @throws ComplianceException the compliance exception
	 */
	private void setAccountWatchListStatus(StatusUpdateRequest statusUpdateRequest, WatchListEntry watchList,Account oldAccount)
			throws ComplianceException {
		WatchListDetails watchDetails = getPaymentWatchListReasons(oldAccount.getId(), watchList);
		/**
		 * changes done to update watchlist status for PaymentsIn and
		 * PaymentsOut
		 */
		processWatchList(statusUpdateRequest, watchDetails);
	}
	
	/**
	 * Process watch list.
	 *
	 * @param statusUpdateRequest the status update request
	 * @param watchDetails the watch details
	 */
	private void processWatchList(StatusUpdateRequest statusUpdateRequest, WatchListDetails watchDetails) {
		String paymentInwatchlistStatus = ServiceStatus.PASS.name();
		String paymentOutwatchlistStatus = ServiceStatus.PASS.name();

		if (watchDetails.isStopPaymentIn() && watchDetails.getCategory().equals(1))
			paymentInwatchlistStatus = ServiceStatus.FAIL.name();

		if (watchDetails.isStopPaymentOut() && watchDetails.getCategory().equals(1))
			paymentOutwatchlistStatus = ServiceStatus.FAIL.name();

		statusUpdateRequest.addAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS, paymentInwatchlistStatus);

		statusUpdateRequest.addAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS, paymentOutwatchlistStatus);
	}
	
	/**
	 * Gets the payment watch list reasons.
	 *
	 * @param accountId the account id
	 * @param watchLists the watch lists
	 * @return the payment watch list reasons
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("squid:S2095")
	private WatchListDetails getPaymentWatchListReasons(Integer accountId, WatchListEntry watchLists)
			throws ComplianceException {
		Connection readOnlyConn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		WatchListDetails watchlist = new WatchListDetails();
		StringBuilder watchListNames = new StringBuilder();
		watchListNames.append('\'').append(watchLists.getWatchListName()).append("',");
		
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
}
