package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDao;

@SuppressWarnings({ "squid:S2095", "squid:S1319" })
public class SanctionCsvDBServiceImpl extends AbstractDao {

	private int pfxMonthDuration = Integer.parseInt(System.getProperty("sanction.csv.months.pfx"));

	private int cfxMonthDuration = Integer.parseInt(System.getProperty("sanction.csv.months.cfx"));

	private static final Logger LOG = LoggerFactory.getLogger(SanctionCsvDBServiceImpl.class);

	public static final String CLNTS = "CLNTS";

	public static final Integer CREATED_BY = 1;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat dobFormat = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Gets the sanction csv data.
	 *
	 * @param message the message
	 * @return the sanction csv data
	 * @throws ComplianceException the compliance exception
	 */
	public Message<SanctionCsvMessageContext> getSanctionCsvData(Message<SanctionCsvMessageContext> message)
			throws ComplianceException {

		HashMap<String, List<SanctionCsvRequest>> sanctionCsvRequestMap = new HashMap<>();
		List<Integer> sanctionCsvContactList = new ArrayList<>();
		try {
			getSanctionCsvPfxData(sanctionCsvRequestMap, sanctionCsvContactList);
			message.getPayload().setSanctionCsvFileData(sanctionCsvRequestMap);
		} catch (Exception e) {
			LOG.error("Error while returning sanction csv data :", e);
		}
		return message;
	}

	/**
	 * Gets the sanction csv pfx data.
	 *
	 * @param sanctionCsvRequestMap  the sanction csv request map
	 * @param sanctionCsvRequestList the sanction csv request list
	 * @param sanctionCsvContactList the sanction csv contact list
	 * @return the sanction csv pfx data
	 * @throws ComplianceException the compliance exception
	 */
	// Sanction CSV PFX Data
	public HashMap<String, List<SanctionCsvRequest>> getSanctionCsvPfxData(
			HashMap<String, List<SanctionCsvRequest>> sanctionCsvRequestMap,
			 List<Integer> sanctionCsvContactList)
			throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SanctionCsvRequest> sanctionCsvPfxRequestList = new ArrayList<>();
		try {

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -pfxMonthDuration);
			Date date = calendar.getTime();
			String dateFromForPfx = format.format(date);

			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(SanctionCsvQueryConstant.GET_PFX_DETAILS.getQuery());
			preparedStatement.setString(1, dateFromForPfx);
			preparedStatement.setString(2, dateFromForPfx);
			preparedStatement.setString(3, dateFromForPfx);
			preparedStatement.setString(4, dateFromForPfx);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				SanctionCsvRequest sanctionCsvRequest = new SanctionCsvRequest();
				sanctionCsvRequest.setSourceCode(CLNTS);
				sanctionCsvRequest.setClientId(rs.getString(SanctionCsvDBColumns.CLIENT_ID.getName()));
				sanctionCsvRequest.setCommentID(rs.getString(SanctionCsvDBColumns.COMMENT_ID.getName()));
				sanctionCsvRequest.setStatusIndicator("I");
				sanctionCsvRequest.setFullName(rs.getString(SanctionCsvDBColumns.FULLNAME.getName()));
				sanctionCsvRequest.setCountry(rs.getString(SanctionCsvDBColumns.COUNTRY.getName()));
				String dob = (rs.getString(SanctionCsvDBColumns.DOB.getName()));
				setDateOfBirth(sanctionCsvRequest, dob);
				sanctionCsvRequest.setRecordeType("I");
				sanctionCsvPfxRequestList.add(sanctionCsvRequest);
				sanctionCsvContactList.add(rs.getInt(SanctionCsvDBColumns.CONTACT_ID.getName()));
			}
				sanctionCsvRequestMap.put("PFX", sanctionCsvPfxRequestList);
				LOG.info("Extracted sanction CSV PFX data:");

				getSanctionCsvCfxAccountData(sanctionCsvRequestMap, sanctionCsvContactList);
		} catch (Exception e) {
			LOG.error("Error while fetching sanction csv PFX data :", e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
		return sanctionCsvRequestMap;
	}

	/**
	 * Gets the sanction csv cfx account data.
	 *
	 * @param sanctionCsvRequestMap the sanction csv request map
	 * @param sanctionCsvContactList the sanction csv contact list
	 * @return the sanction csv cfx account data
	 * @throws ComplianceException the compliance exception
	 */
	// Sanction CSV CFX Data
	public HashMap<String, List<SanctionCsvRequest>> getSanctionCsvCfxAccountData(
			HashMap<String, List<SanctionCsvRequest>> sanctionCsvRequestMap,
			List<Integer> sanctionCsvContactList)
			throws ComplianceException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SanctionCsvRequest> sanctionCsvCfxRequestList = new ArrayList<>();
		try {
			Calendar cale = Calendar.getInstance();
			cale.add(Calendar.MONTH, -cfxMonthDuration);
			Date newDate = cale.getTime();
			String dateFromForCfx = format.format(newDate);

			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(SanctionCsvQueryConstant.GET_CFX_ACCOUNT_DETAILS.getQuery());
			preparedStatement.setString(1, dateFromForCfx);
			preparedStatement.setString(2, dateFromForCfx);
			preparedStatement.setString(3, dateFromForCfx);
			preparedStatement.setString(4, dateFromForCfx);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				SanctionCsvRequest sanctionCsvRequest = new SanctionCsvRequest();
				sanctionCsvRequest.setSourceCode(CLNTS);
				sanctionCsvRequest.setClientId(rs.getString(SanctionCsvDBColumns.CLIENT_ID.getName()));
				sanctionCsvRequest.setCommentID(rs.getString(SanctionCsvDBColumns.COMMENT_ID.getName()));
				sanctionCsvRequest.setStatusIndicator("I");
				sanctionCsvRequest.setFullName(rs.getString(SanctionCsvDBColumns.FULLNAME.getName()));
				sanctionCsvRequest.setCountry(rs.getString(SanctionCsvDBColumns.COUNTRY.getName()));
				String dob = (rs.getString(SanctionCsvDBColumns.DOB.getName()));
				setDateOfBirth(sanctionCsvRequest, dob);
				sanctionCsvRequest.setRecordeType("I");
				sanctionCsvCfxRequestList.add(sanctionCsvRequest);
				sanctionCsvContactList.add(rs.getInt(SanctionCsvDBColumns.CONTACT_ID.getName()));
			}
				getSanctionCsvCfxContactData(sanctionCsvRequestMap, sanctionCsvCfxRequestList, sanctionCsvContactList);
		} catch (Exception e) {
			LOG.error("Error while fetching sanction csv CFX data :", e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
		return sanctionCsvRequestMap;
	}

	/**
	 * Gets the sanction csv cfx contact data.
	 *
	 * @param sanctionCsvRequestMap the sanction csv request map
	 * @param sanctionCsvCfxRequestList the sanction csv cfx request list
	 * @param sanctionCsvContactList the sanction csv contact list
	 * @return the sanction csv cfx contact data
	 * @throws ComplianceException the compliance exception
	 */
	public HashMap<String, List<SanctionCsvRequest>> getSanctionCsvCfxContactData(
			HashMap<String, List<SanctionCsvRequest>> sanctionCsvRequestMap,
			List<SanctionCsvRequest> sanctionCsvCfxRequestList, List<Integer> sanctionCsvContactList)
			throws ComplianceException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			Calendar cale = Calendar.getInstance();
			cale.add(Calendar.MONTH, -cfxMonthDuration);
			Date newDate = cale.getTime();
			String dateFromForCfx = format.format(newDate);

			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(SanctionCsvQueryConstant.GET_CFX_CONTACT_DETAILS.getQuery());
			preparedStatement.setString(1, dateFromForCfx);
			preparedStatement.setString(2, dateFromForCfx);
			preparedStatement.setString(3, dateFromForCfx);
			preparedStatement.setString(4, dateFromForCfx);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				SanctionCsvRequest sanctionCsvRequest = new SanctionCsvRequest();
				sanctionCsvRequest.setSourceCode(CLNTS);
				sanctionCsvRequest.setClientId(rs.getString(SanctionCsvDBColumns.CLIENT_ID.getName()));
				sanctionCsvRequest.setCommentID(rs.getString(SanctionCsvDBColumns.COMMENT_ID.getName()));
				sanctionCsvRequest.setStatusIndicator("I");
				sanctionCsvRequest.setFullName(rs.getString(SanctionCsvDBColumns.FULLNAME.getName()));
				sanctionCsvRequest.setCountry(rs.getString(SanctionCsvDBColumns.COUNTRY.getName()));
				String dob = (rs.getString(SanctionCsvDBColumns.DOB.getName()));
				setDateOfBirth(sanctionCsvRequest, dob);
				sanctionCsvRequest.setRecordeType("I");
				sanctionCsvCfxRequestList.add(sanctionCsvRequest);
				sanctionCsvContactList.add(rs.getInt(SanctionCsvDBColumns.CONTACT_ID.getName()));
			}
			sanctionCsvRequestMap.put("CFX", sanctionCsvCfxRequestList);
			LOG.info("Extracted sanction CSV CFX data:");
			if (sanctionCsvContactList != null) {
				addWatchList(sanctionCsvContactList);
			}
			getSanctionCsvDebtorData(sanctionCsvRequestMap);
		} catch (Exception e) {
			LOG.error("Error while fetching sanction csv CFX data :", e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
		return sanctionCsvRequestMap;
	}

	/**
	 * Gets the sanction csv debtor data.
	 *
	 * @param sanctionCsvRequestMap  the sanction csv request map
	 * @param sanctionCsvRequestList the sanction csv request list
	 * @return the sanction csv debtor data
	 * @throws ComplianceException the compliance exception
	 */
	public HashMap<String, List<SanctionCsvRequest>> getSanctionCsvDebtorData(
			HashMap<String, List<SanctionCsvRequest>> sanctionCsvRequestMap) throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SanctionCsvRequest> sanctionCsvDebtorRequestList = new ArrayList<>();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(SanctionCsvQueryConstant.GET_DEBTOR_DETAILS.getQuery());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				SanctionCsvRequest sanctionCsvRequest = new SanctionCsvRequest();
				sanctionCsvRequest.setSourceCode(CLNTS);
				sanctionCsvRequest.setClientId(rs.getString(SanctionCsvDBColumns.DEBTOR_CLIENT_ID.getName()));
				sanctionCsvRequest.setCommentID(rs.getString(SanctionCsvDBColumns.COMMENT_ID.getName()));
				sanctionCsvRequest.setStatusIndicator("I");
				sanctionCsvRequest.setFullName(rs.getString(SanctionCsvDBColumns.FULLNAME.getName()));
				sanctionCsvRequest.setCountry(rs.getString(SanctionCsvDBColumns.COUNTRY.getName()));
				String dob = (rs.getString(SanctionCsvDBColumns.DOB.getName()));
				setDateOfBirth(sanctionCsvRequest, dob);
				sanctionCsvRequest.setRecordeType("I");
				sanctionCsvDebtorRequestList.add(sanctionCsvRequest);
			}
			sanctionCsvRequestMap.put("DEBTOR", sanctionCsvDebtorRequestList);
			LOG.info("Extracted sanction CSV Debtor's data:");

		} catch (Exception e) {
			LOG.error("Error while fetching sanction csv Debtor's data :", e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
		return sanctionCsvRequestMap;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param sanctionCsvRequest the sanction csv request
	 * @param dob the dob
	 * @throws ParseException the parse exception
	 */
	private void setDateOfBirth(SanctionCsvRequest sanctionCsvRequest, String dob) throws ParseException {
		if(dob!=null && !dob.isEmpty()) {
			if(dob.contains("-")) {
			Date parseDate = format.parse(dob);
			String birthDate = dobFormat.format(parseDate);
			sanctionCsvRequest.setDob(birthDate);
			}
		} else {
			sanctionCsvRequest.setDob(dob);
		}
	}
	
	/**
	 * Adds the watch list.
	 *
	 * @param sanctionCsvContactList the sanction csv contact list
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws ComplianceException the compliance exception
	 */
	public void addWatchList(List<Integer> sanctionCsvContactList) throws IOException, ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Timestamp createdOn = new Timestamp(System.currentTimeMillis());
		List<Integer> finalContactList = new ArrayList<>();
		List<Integer> contactList = new ArrayList<>();
		try {
			int reason = getWatchlistReason();
			int watchListReason = reason;
			connection = getConnection(Boolean.TRUE);

			contactList=checkContactWatchlisEntry(sanctionCsvContactList);
			finalContactList=contactList.stream().distinct().collect(Collectors.toList());// to exclude douplicate entry
			
			// To add Watchlist
			preparedStatement = connection
					.prepareStatement(SanctionCsvQueryConstant.INSERT_INACTIVE_FINSCAN_WATCHLIST.getQuery());
			for (Integer ContactID : finalContactList) {
				Integer parameterIndex = 1;
				preparedStatement.setInt(parameterIndex, watchListReason);
				parameterIndex++;
				preparedStatement.setInt(parameterIndex, ContactID);
				parameterIndex++;
				preparedStatement.setInt(parameterIndex, CREATED_BY);
				parameterIndex++;
				preparedStatement.setTimestamp(parameterIndex, createdOn);
				preparedStatement.addBatch();
			}
			LOG.info("Watchlist added to all sanction CSV contacts");
		} catch (Exception e) {
			LOG.error("Error in addWatchList() for Sanction Csv Contacts : ", e);
		} finally {
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * Gets the watchlist reason.
	 *
	 * @return the watchlist reason
	 * @throws ComplianceException the compliance exception
	 */
	// To get Watchlist Id
	public Integer getWatchlistReason() throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer reason = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(SanctionCsvQueryConstant.GET_WATCHLIST_ID.getQuery());
			preparedStatement.setString(1, "Finscan Inactive");
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				reason = rs.getInt("Reason");
			}
		} catch (Exception e) {
			LOG.error("Error while fetching Watchlist reason in getWatchlistReason() :", e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
		return reason;
	}

	/**
	 * Check contact watchlis entry.
	 *
	 * @param sanctionCsvContactList the sanction csv contact list
	 * @return the list
	 * @throws ComplianceException the compliance exception
	 */
	public List<Integer> checkContactWatchlisEntry(List<Integer> sanctionCsvContactList) throws ComplianceException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer reason = getWatchlistReason();
		int watchListReason = reason;
		List<Integer> existingContactList = new ArrayList<>();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(SanctionCsvQueryConstant.CHECK_CONTACT_EXISTS_IN_CONTACTWATCHLIST.getQuery());
			preparedStatement.setInt(1, watchListReason);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				existingContactList.add(rs.getInt(SanctionCsvDBColumns.CONTACT_ID.getName()));
			}
			sanctionCsvContactList.removeAll(existingContactList);
		} catch (Exception e) {
			LOG.error("Error while checking contact Entry In Contact Watchlist :", e);
		} finally {
			closeResultset(rs);
			closeConnection(connection);
			closePrepareStatement(preparedStatement);
		}
		return sanctionCsvContactList;
	}
}
