
package com.currenciesdirect.gtg.compliance.dbport.blacklist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import com.currenciesdirect.gtg.compliance.core.blacklist.IDBService;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistDataResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateData;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.ResponseStatus;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;
import com.currenciesdirect.gtg.compliance.util.Constants;

/**
 * The Class DBServiceImpl.
 *
 * @author Rajesh
 */
@SuppressWarnings("squid:S3077")
public class DBServiceImpl extends AbstractDao implements IDBService {

	private static final String COULD_NOT_CLOSE_CONNECTION = "Could not close connection";
	/** The i DB service. */
	private static volatile IDBService iDBService = null;
	
	private static final String ERROR = "Error :";
	
	/** The Constant LOG. */
	
	/**
	 * Instantiates a new DB service impl.
	 */
	private DBServiceImpl() {
	}

	/**
	 * Gets the single instance of DBServiceImpl.
	 *
	 * @return single instance of DBServiceImpl
	 */
	public static IDBService getInstance() {
		if (iDBService == null) {
			synchronized (DBServiceImpl.class) {
				if (iDBService == null) {
					iDBService = new DBServiceImpl();
				}
			}
		}
		return iDBService;
	}
	

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#saveIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistRequest)
	 */
	public InternalServiceResponse saveIntoBlacklist(InternalServiceRequest blacklistRequest) throws BlacklistException {
		LOG.debug("DBServiceImpl.saveIntoBlacklist: start");
		InternalServiceResponse response = new InternalServiceResponse();
		Connection connection = null;
		try {
			connection = getConnection();
			beginTransaction(connection);
			if (saveIntoBlacklist(blacklistRequest, connection)) {
				response.setStatus(ResponseStatus.SUCCESS.getStatus());
				commitTransaction(connection);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}

		} catch (BlacklistException e) {
			throw e;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally{
			try {
				closeConnection(connection);
			} catch(Exception e) {
				LOG.error(COULD_NOT_CLOSE_CONNECTION, e);
			}
			
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#stpSearchIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest)
	 */
	@Override
	public BlacklistContactResponse stpSearchIntoBlacklist(BlacklistSTPRequest blacklistRequest) throws BlacklistException {
		LOG.debug("DBServiceImpl.searchIntoBlacklist: start");
		BlacklistContactResponse response = null;
		Connection connection = null;
		try {
			connection = getConnection();
			response = stpSearchIntoBlacklist(blacklistRequest, connection);
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		} finally {
			try {
				closeConnection(connection);
			} catch (InternalRuleException e) {
				LOG.error(COULD_NOT_CLOSE_CONNECTION, e);
			}
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#uiSearchIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUISearchRequest)
	 */
	@SuppressWarnings("squid:S2095")
	public InternalServiceResponse uiSearchIntoBlacklist(InternalServiceRequest request)
			throws BlacklistException {
		BlacklistRequest blacklistRequest = request.getBlacklistRequest();
		BlacklistData[] insertData = blacklistRequest.getData();
		InternalServiceResponse searchResponse = new InternalServiceResponse();
		List<BlacklistDataResponse> blacklistDataList = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatementWildCard = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			preparedStatementWildCard = connection.prepareStatement(DBQueryConstant.SEARCH_BALCKLIST_DATA_WILD_CARD.getQuery());
			preparedStatementWildCard.setNString(1, request.getBlacklistDataType());
			String value = replaceWildCard(insertData[0].getValue());
			preparedStatementWildCard.setNString(2, "%" + value + "%");
			rs = preparedStatementWildCard.executeQuery();
			while (rs.next()) {
				BlacklistDataResponse blacklistData = new BlacklistDataResponse();
				blacklistData.setBlacklistType(request.getBlacklistDataType());
				blacklistData.setCreatedOnDate(removeMillisFromTimeStamp(rs.getTimestamp("created_on").toString()));
				blacklistData.setValue(rs.getString("value"));
				blacklistData.setNotes(rs.getString("Notes"));
				blacklistDataList.add(blacklistData);
			}
			searchResponse.setBlacklistDataResponse(blacklistDataList);
			searchResponse.setStatus("Success");
			rs.close();

		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		finally
		{	
			try {				
				closeResultset(rs);
				if(preparedStatementWildCard != null ) {
					closePrepareStatement(preparedStatementWildCard);
				}
				closeConnection(connection);
			} catch (InternalRuleException e) {				
				LOG.error("Error in stpSearchIntoBlacklist {1}", e);
				
			}
			
		}
		return searchResponse;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#updateIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest)
	 */
	@Override
	public BlacklistModifierResponse updateIntoBlacklist(BlacklistUpdateRequest blacklistRequest)
			throws BlacklistException {
		LOG.debug("DBServiceImpl.updateIntoBlacklist: start");
		BlacklistModifierResponse response = new BlacklistModifierResponse();
		try (Connection connection = getConnection();) {
			beginTransaction(connection);

			if (updateIntoBlacklist(blacklistRequest, connection)) {
				LOG.debug("Data updated successfully");
				response.setStatus(ResponseStatus.SUCCESS.getStatus());
				commitTransaction(connection);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}

		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#deleteFromBacklist(com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistRequest)
	 */
	public InternalServiceResponse deleteFromBacklist(InternalServiceRequest blacklistRequest) throws BlacklistException {
		LOG.debug("DBServiceImpl.deleteFromBacklist: start");
		InternalServiceResponse response = new InternalServiceResponse();
		Connection connection = null;
		try {
			connection = getConnection();
			beginTransaction(connection);
			if (deleteFromBlacklist(blacklistRequest, connection)) {
				LOG.debug("Data deleted successfully");
				response.setStatus(ResponseStatus.SUCCESS.getStatus());
				commitTransaction(connection);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}

		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_DELETING_DATA, e);
		} finally {
			try{
				closeConnection(connection);
			} catch(Exception e){
				LOG.error(COULD_NOT_CLOSE_CONNECTION, e);
			}
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#getAllBlacklistTypes()
	 */
	@Override
	public Map<String, Integer> getAllBlacklistTypes() throws BlacklistException {
		LOG.debug("DBServiceImpl.getAllBlacklistTypes: start");
		ConcurrentHashMap<String, Integer> blacklistType = new ConcurrentHashMap<>();
		ResultSet rs = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(DBQueryConstant.GET_BLACKLIST_TYPE.getQuery());) {
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				blacklistType.put(rs.getNString("Type"), rs.getInt("Relevance"));
			}
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_FETCHING_DATA_BLACKLIST_TYPE, exception);
		}
		finally
		{
			try {
				closeResultset(rs);
			} catch (InternalRuleException e) {
				LOG.error("Errror in getAllBlacklistTypes",e);
			}
		}
		return blacklistType;
	}

	/**
	 * Save into blacklist.
	 *
	 * @param request the request
	 * @param connection the connection
	 * @return true, if successful
	 * @throws BlacklistException the blacklist exception
	 */
	private boolean saveIntoBlacklist(InternalServiceRequest request, Connection connection) throws BlacklistException {
		Boolean status = Boolean.TRUE;
		BlacklistRequest blacklistRequest = request.getBlacklistRequest();
		BlacklistData[] insertData = request.getBlacklistRequest().getData();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_BLACKLIST_DATA.getQuery());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			blacklistRequest.setCreatedOn(timestamp);
			blacklistRequest.setUpdatedOn(timestamp);

			for (int i = 0; i < insertData.length; i++) {
				preparedStatement.setNString(1, insertData[i].getType());
				preparedStatement.setNString(2, insertData[i].getValue());
				preparedStatement.setTimestamp(3, blacklistRequest.getCreatedOn());
				preparedStatement.setNString(4, blacklistRequest.getCreatedBy());
				preparedStatement.setTimestamp(5, blacklistRequest.getUpdatedOn());
				preparedStatement.setNString(6, blacklistRequest.getUpdatedBy());
				preparedStatement.setInt(7, 0);
				preparedStatement.setNString(8, blacklistRequest.getNotes());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			status = getUpdateStatus(count);
		} catch (Exception e) {			
			try {
				LOG.debug("Duplicate entry found while inserting data...Trying to update delete status");
				status = updateDeleteFlagForBlacklistData(request, connection, insertData);
				LOG.debug("Error while ", e);
				throw new BlacklistException(BlacklistErrors.DUPLICATE_DATA_ADDITION);
			} catch (BlacklistException ex) {
				throw new BlacklistException(BlacklistErrors.DUPLICATE_DATA_ADDITION, ex);
			} catch (Exception ex) {
				throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA, ex);
			}		
		} finally {
			try{
				closePrepareStatement(preparedStatement);
			} catch(Exception e) {
				LOG.error("Could not close preparedStaement", e);
			}
		}
		return status;
	}

	/**
	 * @param request
	 * @param connection
	 * @param status
	 * @param insertData
	 * @return
	 * @throws SQLException
	 * @throws InternalRuleException
	 */
	@SuppressWarnings("squid:S2095")
	private Boolean updateDeleteFlagForBlacklistData(InternalServiceRequest request, Connection connection,
			BlacklistData[] insertData) throws SQLException, InternalRuleException {
		PreparedStatement selectPreparedStatement = null;
		PreparedStatement updatePreparedStatement = null;
		ResultSet rs = null;
		Boolean result=Boolean.FALSE;
		try {
			selectPreparedStatement = connection.prepareStatement(DBQueryConstant.IS_BLACKLIST_DATA_PRESENT.getQuery());
			selectPreparedStatement.setString(1, request.getBlacklistDataType());
			selectPreparedStatement.setString(2, insertData[0].getValue());
			rs = selectPreparedStatement.executeQuery();
			while (rs.next()) {
				if (rs.getString("Value").equalsIgnoreCase(insertData[0].getValue())
						&& rs.getString("BlacklistType").equalsIgnoreCase(request.getBlacklistDataType()) && rs.getInt("Deleted") == 1) {
						updatePreparedStatement = connection.prepareStatement(DBQueryConstant.UPDATE_DELETE_STATUS.getQuery());
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						updatePreparedStatement.setTimestamp(1, timestamp);
						updatePreparedStatement.setInt(2, rs.getInt("Id"));
						updatePreparedStatement.addBatch();
				}
			}
			int[] count = updatePreparedStatement!=null?updatePreparedStatement.executeBatch():new int[0];
			if(count.length == 0) {
				result= Boolean.FALSE;
				return result;
			}
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(updatePreparedStatement);
			closePrepareStatement(selectPreparedStatement);
			closeResultset(rs);
		}
		result=Boolean.TRUE;
		return result;
	}

	/**
	 * Update into blacklist.
	 *
	 * @param request the request
	 * @param connection the connection
	 * @return true, if successful
	 * @throws BlacklistException the blacklist exception
	 */
	private boolean updateIntoBlacklist(BlacklistUpdateRequest request, Connection connection)
			throws BlacklistException {
		Boolean status = Boolean.TRUE;
		BlacklistUpdateData[] deleteData = request.getData();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(DBQueryConstant.UPDATE_BALCKLIST_DATA.getQuery());) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			request.setUpdatedOn(timestamp);

			for (int i = 0; i < deleteData.length; i++) {
				preparedStatement.setNString(1, deleteData[i].getNewValue());
				preparedStatement.setTimestamp(2, request.getUpdatedOn());
				preparedStatement.setNString(3, request.getUpdatedBy());
				preparedStatement.setNString(4, deleteData[i].getType());
				preparedStatement.setNString(5, deleteData[i].getOriginalValue());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			status = getUpdateStatus(count);
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
		return status;
	}

	private Boolean getUpdateStatus(int[] count) {
		Boolean status = Boolean.TRUE;
		for (int i : count) {
			if (i <= 0)
				status = Boolean.FALSE;
		}
		return status;
	}

	/**
	 * Delete from blacklist.
	 *
	 * @param request the request
	 * @param connection the connection
	 * @return true, if successful
	 * @throws BlacklistException the blacklist exception
	 */
	private boolean deleteFromBlacklist(InternalServiceRequest request, Connection connection) throws BlacklistException {
		Boolean status = Boolean.TRUE;
		BlacklistRequest blacklistRequest = request.getBlacklistRequest();
		BlacklistData[] deleteData = blacklistRequest.getData();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(DBQueryConstant.DELETE_BLACKLIST_DATA.getQuery());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			for (int i = 0; i < deleteData.length; i++) {
				preparedStatement.setTimestamp(1, timestamp);
				preparedStatement.setNString(2, request.getBlacklistDataType());
				preparedStatement.setNString(3, deleteData[i].getValue());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			status = getUpdateStatus(count);
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_DELETING_DATA, e);
		} finally{
			try{
				closePrepareStatement(preparedStatement);
			}catch(Exception e) {
				LOG.error("Could not close preparedStaement", e);
			}
		}
		return status;
	}

	/**
	 * Stp search into blacklist.
	 *
	 * @param request the request
	 * @param connection the connection
	 * @return the blacklist contact response
	 * @throws BlacklistException the blacklist exception
	 */
	private BlacklistContactResponse stpSearchIntoBlacklist(BlacklistSTPRequest request, Connection connection)
			throws BlacklistException {
		List<BlacklistData> searchData = request.getSearch();
		BlacklistContactResponse searchResponse = new BlacklistContactResponse();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(DBQueryConstant.SEARCH_BALCKLIST_DATA.getQuery());
				PreparedStatement preparedStatementWildCard = connection
						.prepareStatement(DBQueryConstant.SEARCH_BALCKLIST_DATA_WILD_CARD.getQuery());
				PreparedStatement phoneNoPreparedStatement = connection.prepareStatement
						(DBQueryConstant.SEARCH_BALCKLIST_DATA_FOR_PHONE_NUMBER.getQuery());
				PreparedStatement domainPreparedStatement = connection.prepareStatement
						(DBQueryConstant.SEARCH_BALCKLIST_DATA_FOR_DOMAIN.getQuery())) {
				List<BlacklistSTPData> responseDataList = new CopyOnWriteArrayList<>();
				boolean isBlacklisted = fetchStpData(searchData,domainPreparedStatement, phoneNoPreparedStatement, preparedStatementWildCard,
						preparedStatement,responseDataList);
				searchResponse.setData(responseDataList);
				searchResponse.setStatus(isBlacklisted ? ResponseStatus.FAIL.getStatus() : ResponseStatus.PASS.getStatus());

		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return searchResponse;
	}

	/**
	 * Fetch stp data.
	 *
	 * @param searchData the search data
	 * @param preparedStatementWildCard the prepared statement wild card
	 * @param preparedStatement the prepared statement
	 * @param responseDataList the response data list
	 * @return true, if it's blacklisted
	 * @throws SQLException the SQL exception
	 * @throws NamingException 
	 * @throws BlacklistException 
	 */
	private boolean fetchStpData(List<BlacklistData> searchData,PreparedStatement domainPreparedStatement, PreparedStatement phoneNoPreparedStatement,
			PreparedStatement preparedStatementWildCard, PreparedStatement preparedStatement, List<BlacklistSTPData> responseDataList) throws SQLException {
		boolean isBlacklisted = false;
		boolean tmp = false;
		List<String> matchList = null;
		for (int j = 0; j < searchData.size(); j++) {
			ResultSet rs = null;
			matchList = new ArrayList<>(3);
			try {				
				/**
				 * Condition added to check domain of website or email whether it is containing in 
				 * blacklisted data or not.
				 * */
				if(Constants.DOMAIN.equalsIgnoreCase(searchData.get(j).getType()))
				{
					rs = domainPreparedStatement.executeQuery();					
					matchList = setIsDomainBlacklisted(searchData, j, rs);
				}
				/**Condition added to compare only 9 digits of phone number with database 
				 * phone numbers - Vishal J*/
				else if(Constants.PHONE_NUMBER.equalsIgnoreCase(searchData.get(j).getType()))	{
					phoneNoPreparedStatement.setNString(1, searchData.get(j).getType());
					phoneNoPreparedStatement.setNString(2, searchData.get(j).getValue());
					rs = phoneNoPreparedStatement.executeQuery();
					matchList = setCountForBlacklistData(rs);
				}else if (isEligibleForWildCard(searchData.get(j).getType()) && isWildCardPresent(searchData.get(j).getValue())) {
					preparedStatementWildCard.setNString(1, searchData.get(j).getType());
					String value = replaceWildCard(searchData.get(j).getValue());
					preparedStatementWildCard.setNString(2, value);
					rs = preparedStatementWildCard.executeQuery();
					matchList = setCountForBlacklistData(rs);
				} else {
					preparedStatement.setNString(1, searchData.get(j).getType());
					preparedStatement.setNString(2, searchData.get(j).getValue());
					rs = preparedStatement.executeQuery();
					matchList = setCountForBlacklistData(rs);
				}
				tmp = updateBlacklistResponse(searchData.get(j), responseDataList, matchList);
				//if multiple data points are found in blacklist, 
				//do not override the return value
				if(!isBlacklisted){
					isBlacklisted = tmp;
				}
			} catch(Exception e) {
				LOG.error("Exception in fetchStpData() in DBServiceImple class", e);
				throw e;
			} finally {
				try {
					closeResultset(rs);
				} catch (InternalRuleException e) {
					LOG.error(ERROR, e);
				}
			}
			
		}		
		return isBlacklisted;
	}

	private boolean updateBlacklistResponse(BlacklistData searchData, List<BlacklistSTPData> responseDataList,
			List<String> matchList) {
		boolean isBlacklisted = false;
		
		int count = matchList.size();
		
		BlacklistSTPData responseData = new BlacklistSTPData();
		responseData.setType(searchData.getType());
		responseData.setRequestType(searchData.getRequestType());
		responseData.setValue(searchData.getValue());
		if (count > 0) {
			responseData.setFound(Boolean.TRUE);
			responseData.setMatch(count);
			responseData.setMatchedData(matchList.toString().substring(1, matchList.toString().length()-1));
			isBlacklisted = true;
		} else {
			responseData.setFound(Boolean.FALSE);
			responseData.setMatch(0);
			responseData.setMatchedData("No Match Found");
		}
		responseDataList.add(responseData);
		return isBlacklisted;
	}

	private List<String> setCountForBlacklistData(ResultSet rs) throws SQLException {
		List<String> matchList = new ArrayList<>();
		while (rs.next()) {
			matchList.add(rs.getString(1));
		}
		return matchList;
	}

	private List<String> setIsDomainBlacklisted(List<BlacklistData> searchData, int j, ResultSet rs)
			throws SQLException {
		List<String> matchList = new ArrayList<>();
		while(rs.next()) {						
			Boolean isDomainBlacklisted = isDomainBlackListed(searchData.get(j).getValue(), rs.getString(1));
			if(Boolean.TRUE.equals(isDomainBlacklisted)){
				matchList.add(rs.getString(1));
			}
				
		}
		return matchList;
	}

	/**
	 * Checks if is wild card present.
	 *
	 * @param value the value
	 * @return true, if is wild card present
	 */
	private boolean isWildCardPresent(String value) {
		return value.contains("*");
	}

	/**
	 * Checks if is eligible for wild card.
	 *
	 * @param type the type
	 * @return true, if is eligible for wild card
	 */
	private boolean isEligibleForWildCard(String type) {
		return WildCardEligible.contains(type);
	}

	/**
	 * Replace wild card.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String replaceWildCard(String value) {
		return value.replace('*', '%').intern();
	}
	
	/**
	 * We write this method to match different formats of domains with our domain (from website or email)
	 * Since domain can come in any format we need to consider each scenario.
	 * Example formats domains :
	 *   isDomainBlackListed("post.com","post.com") - returns TRUE
	 *   isDomainBlackListed("doc.pdf.post.com","post.com") - returns TRUE
	 *   isDomainBlackListed("www.post.com","post.com") - returns TRUE
	 *   -----------------------------------------------------------------
	 *   isDomainBlackListed("post.co","post.com") - returns FALSE
	 *   isDomainBlackListed("superpost.com","post.com") - returns FALSE
	 *   isDomainBlackListed("post.co.in","post.com") - returns FALSE
	 *   isDomainBlackListed("post123.com","post.com") - returns FALSE
	 * 
	 * 	For this method 1st parameter will be domain coming from request and another parameter is domain
	 *  available in database. (We will check each domain entry in database with request domain.)
	 * */
	private boolean isDomainBlackListed(String source, String subItem){
		
		if( subItem.charAt( 0 ) == '@' )
		{
			subItem = subItem.substring(1);
			 
		}
       String  pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        
        return m.find();
   }
	
	@SuppressWarnings("squid:S2095")
	public InternalServiceResponse getBlacklistDataByType(InternalServiceRequest request)
			throws BlacklistException {
		LOG.debug("DBServiceImpl.getBlacklistDataByType: start");
		List<BlacklistDataResponse> blacklistDataList = new ArrayList<>();
		InternalServiceResponse response = new InternalServiceResponse();
		
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_BLACKLIST_DATA_BY_TYPE.getQuery());
			preparedStatement.setString(1, request.getBlacklistDataType());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				BlacklistDataResponse blacklistData = new BlacklistDataResponse();
				blacklistData.setBlacklistType(request.getBlacklistDataType());
				blacklistData.setCreatedOnDate(removeMillisFromTimeStamp(rs.getTimestamp("created_on").toString()));
				blacklistData.setValue(rs.getString("value"));
				blacklistData.setNotes(rs.getString("notes"));
				blacklistDataList.add(blacklistData);
			}
			response.setBlacklistDataResponse(blacklistDataList);
			response.setStatus("Success");
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_FETCHING_DATA_BLACKLIST_TYPE, exception);
		}
		finally
		{
			try {
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
				closeResultset(rs);				
			} catch (Exception e) {
				LOG.error("Errror in getBlacklistDataByType",e);
			}
		}
		return response;
	}
	
	/**
	 * Removes the millis from time stamp.
	 *
	 * @param date the date
	 * @return the string
	 */
	public String removeMillisFromTimeStamp(String date) {
		if(null != date && date.contains(".")) {
			return date.substring(0, date.indexOf('.'));
		}
		return date;
	}

	
}

