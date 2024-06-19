
package com.currenciesdirect.gtg.compliance.blacklist.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.currenciesdirect.gtg.compliance.blacklist.core.IDBService;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistIdAndData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPIdAndData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * @author Rajesh
 *
 */
public class DBServiceImpl extends AbstractDao implements IDBService {

	private static volatile IDBService iDBService = null;

	private DBServiceImpl() {
	}

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

	@Override
	public BlacklistModifierResponse saveIntoBlacklist(BlacklistRequest blacklistRequest) throws BlacklistException {
		LOG.info("DBServiceImpl.saveIntoBlacklist: start");
		BlacklistModifierResponse response = new BlacklistModifierResponse();
		try (Connection connection = getConnection();) {
			beginTransaction(connection);
			if (saveIntoBlacklist(blacklistRequest, connection)) {
				response.setStatus(ResponseStatus.SUCCESS.getStatus());
				commitTransaction(connection);
				LOG.info("Data saved successfully");
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}

		} catch (BlacklistException e) {
			throw e;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA, e);
		}
		return response;
	}

	@Override
	public BlacklistSTPResponse stpSearchIntoBlacklist(BlacklistSTPRequest blacklistRequest) throws BlacklistException {
		LOG.info("DBServiceImpl.searchIntoBlacklist: start");
		BlacklistSTPResponse response = null;
		try (Connection connection = getConnection();) {
			response = stpSearchIntoBlacklist(blacklistRequest, connection);
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return response;
	}

	@Override
	public BlacklistUISearchResponse uiSearchIntoBlacklist(BlacklistUISearchRequest blacklistRequest)
			throws BlacklistException {
		BlacklistUISearchResponse searchResponse = new BlacklistUISearchResponse();
		List<BlacklistData> responseDataList = new CopyOnWriteArrayList<>();
		searchResponse.setData(responseDataList);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatementWildCard = connection
						.prepareStatement(DBQueryConstant.SEARCH_BALCKLIST_DATA_WILD_CARD.getQuery());) {
			ResultSet rs = null;
			preparedStatementWildCard.setNString(1, blacklistRequest.getType());
			String value = replaceWildCard(blacklistRequest.getValue());
			preparedStatementWildCard.setNString(2, "%" + value + "%");
			rs = preparedStatementWildCard.executeQuery();
			while (rs.next()) {
				BlacklistData blacklistData = new BlacklistData();
				blacklistData.setType(blacklistRequest.getType());
				blacklistData.setValue(rs.getNString("Value"));
				responseDataList.add(blacklistData);
			}
			rs.close();

		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return searchResponse;
	}

	@Override
	public BlacklistModifierResponse updateIntoBlacklist(BlacklistUpdateRequest blacklistRequest)
			throws BlacklistException {
		LOG.info("DBServiceImpl.updateIntoBlacklist: start");
		BlacklistModifierResponse response = new BlacklistModifierResponse();
		try (Connection connection = getConnection();) {
			beginTransaction(connection);

			if (updateIntoBlacklist(blacklistRequest, connection)) {
				LOG.info("Data updated successfully");
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

	@Override
	public BlacklistModifierResponse deleteFromBacklist(BlacklistRequest blacklistRequest) throws BlacklistException {
		LOG.info("DBServiceImpl.deleteFromBacklist: start");
		BlacklistModifierResponse response = new BlacklistModifierResponse();
		try (Connection connection = getConnection();) {
			beginTransaction(connection);
			if (deleteFromBlacklist(blacklistRequest, connection)) {
				LOG.info("Data deleted successfully");
				response.setStatus(ResponseStatus.SUCCESS.getStatus());
				commitTransaction(connection);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}

			response.setStatus(ResponseStatus.SUCCESS.getStatus());
			commitTransaction(connection);
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_DELETING_DATA, e);
		}
		return response;
	}

	@Override
	public Map<String, Integer> getAllBlacklistTypes() throws BlacklistException {
		LOG.info("DBServiceImpl.getAllBlacklistTypes: start");
		ConcurrentHashMap<String, Integer> blacklistType = new ConcurrentHashMap<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(DBQueryConstant.GET_BLACKLIST_TYPE.getQuery());) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				blacklistType.put(rs.getNString("Type"), rs.getInt("Relevance"));
			}
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_FETCHING_DATA_BLACKLIST_TYPE, exception);
		}
		return blacklistType;
	}

	private boolean saveIntoBlacklist(BlacklistRequest request, Connection connection) throws BlacklistException {
		Boolean status = true;
		BlacklistData[] insertData = request.getData();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(DBQueryConstant.INSERT_INTO_BLACKLIST_DATA.getQuery());) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			request.setCreatedOn(timestamp);
			request.setUpdatedOn(timestamp);

			for (int i = 0; i < insertData.length; i++) {
				preparedStatement.setNString(1, insertData[i].getType());
				preparedStatement.setNString(2, insertData[i].getValue());
				preparedStatement.setTimestamp(3, request.getCreatedOn());
				preparedStatement.setNString(4, request.getCreatedBy());
				preparedStatement.setTimestamp(5, request.getUpdatedOn());
				preparedStatement.setNString(6, request.getUpdatedBy());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int i : count) {
				if (i <= 0)
					status = false;
			}
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA, e);
		}
		return status;
	}

	private boolean updateIntoBlacklist(BlacklistUpdateRequest request, Connection connection)
			throws BlacklistException {
		Boolean status = true;
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
			for (int i : count) {
				if (i <= 0)
					status = false;
			}
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
		return status;
	}

	private boolean deleteFromBlacklist(BlacklistRequest request, Connection connection) throws BlacklistException {
		Boolean status = true;
		BlacklistData[] deleteData = request.getData();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(DBQueryConstant.DELETE_BLACKLIST_DATA.getQuery());) {
			for (int i = 0; i < deleteData.length; i++) {
				preparedStatement.setNString(1, deleteData[i].getType());
				preparedStatement.setNString(2, deleteData[i].getValue());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int i : count) {
				if (i <= 0)
					status = false;
			}
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_DELETING_DATA, e);
		}
		return status;
	}

	private BlacklistSTPResponse stpSearchIntoBlacklist(BlacklistSTPRequest request, Connection connection)
			throws BlacklistException {
		List<BlacklistIdAndData> searchIdData = request.getSearch();
		boolean status = false;
		BlacklistSTPResponse searchResponse = new BlacklistSTPResponse();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(DBQueryConstant.SEARCH_BALCKLIST_DATA.getQuery());
				PreparedStatement preparedStatementWildCard = connection
						.prepareStatement(DBQueryConstant.SEARCH_BALCKLIST_DATA_WILD_CARD.getQuery());) {
			List<BlacklistSTPIdAndData> idAndDataResponseList = new CopyOnWriteArrayList<>();
			for (int i = 0; i < searchIdData.size(); i++) {
				BlacklistIdAndData idAndData = searchIdData.get(i);
				List<BlacklistData> searchData = idAndData.getData();
				List<BlacklistSTPData> responseDataList = new CopyOnWriteArrayList<>();
				boolean isBlacklisted = fetchStpData(searchData, preparedStatementWildCard,
						preparedStatement,responseDataList);
				if(isBlacklisted && !status){
					status = true;
				}
				BlacklistSTPIdAndData stpIdAndData = new BlacklistSTPIdAndData();
				stpIdAndData.setId(idAndData.getId());
				stpIdAndData.setData(responseDataList);
				stpIdAndData.setStatus(isBlacklisted ? ResponseStatus.FAIL.getStatus() : ResponseStatus.PASS.getStatus());
				idAndDataResponseList.add(stpIdAndData);
			}
			searchResponse.setStatus(status ? ResponseStatus.FAIL.getStatus() : ResponseStatus.PASS.getStatus()); 
			searchResponse.setSearch(idAndDataResponseList);

		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return searchResponse;
	}

	private boolean fetchStpData(List<BlacklistData> searchData,
			PreparedStatement preparedStatementWildCard, PreparedStatement preparedStatement, List<BlacklistSTPData> responseDataList) throws SQLException {
		boolean isBlacklisted = false;
		for (int j = 0; j < searchData.size(); j++) {
			ResultSet rs;
			if (isEligibleForWildCard(searchData.get(j).getType()) && isWildCardPresent(searchData.get(j).getValue())) {
				preparedStatementWildCard.setNString(1, searchData.get(j).getType());
				String value = replaceWildCard(searchData.get(j).getValue());
				preparedStatementWildCard.setNString(2, value);
				rs = preparedStatementWildCard.executeQuery();
			} else {
				preparedStatement.setNString(1, searchData.get(j).getType());
				preparedStatement.setNString(2, searchData.get(j).getValue());
				rs = preparedStatement.executeQuery();
			}
			int count = 0;
			while (rs.next()) {
				count++;
			}
			BlacklistSTPData responseData = new BlacklistSTPData();
			responseData.setType(searchData.get(j).getType());
			responseData.setValue(searchData.get(j).getValue());
			if (count > 0) {
				responseData.setFound(true);
				responseData.setMatch(count);
				isBlacklisted = true;
			} else {
				responseData.setFound(false);
				responseData.setMatch(0);
			}
			responseDataList.add(responseData);
			rs.close();
		}
		return isBlacklisted;

	}

	private boolean isWildCardPresent(String value) {
		return value.contains("*");
	}

	private boolean isEligibleForWildCard(String type) {
		return WildCardEligible.contains(type);
	}

	private String replaceWildCard(String value) {
		return new String(value.replace("*", "%")).intern();
	}
}