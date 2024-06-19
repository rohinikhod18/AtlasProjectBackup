package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IFraugsterUploadDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

@Component("fraugsterUploadDBServiceImpl")
public class FraugsterUploadDBServiceImpl extends AbstractDao implements IFraugsterUploadDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterUploadDBServiceImpl.class);

	@Override
	public void updateFraugsterDataTable(List<Integer> fraugsetrId) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		Integer count = 0;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_FRAUGSTER_DATA.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			if (null != fraugsetrId && !fraugsetrId.isEmpty()) {

				setValuesToFraugsterData(fraugsetrId, statement, count);
			}

		} catch (ComplianceException | SQLException e) {
			LOG.error("Error in FraugsterUploadSuccessHandler updateFraugsterDataTable()", e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}

	}

	/**
	 * @param fraugsetrId
	 * @param statement
	 * @param count
	 * @throws SQLException
	 */
	private void setValuesToFraugsterData(List<Integer> fraugsetrId, PreparedStatement statement, Integer count)
			throws SQLException {
		for (int i = 0; i <= fraugsetrId.size() - 1; i++) {
			statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			statement.setInt(2, fraugsetrId.get(i));
			statement.addBatch();
			count++;
			if (count == 1000) {
				statement.executeBatch();
				count = 0;
			}
		}
		if (count > 0) {
			statement.executeBatch();
		}
	}

	@SuppressWarnings("squid:S2095")
	public String getFraugster(List<Integer> list) throws ComplianceException {	
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		StringBuilder builder = new StringBuilder();
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_FRAUGSTERSCHEDULAR_DATA_FROM_DB.getQuery());
			
			rs = statement.executeQuery();
			Integer resultSetCount = 0;
			builder.append(Constants.CSV_HEADER_FRAGUSTER_ID)
			.append(Constants.COMMA)
			.append(Constants.CSV_HEADER_CURRENCIES_DIRECT_UNIQUE_ID)
			.append(Constants.COMMA)
			.append(Constants.CSV_HEADER_FRAUGSTERAPPROVEDFLAG)
			.append(Constants.COMMA)
			.append(Constants.CSV_HEADER_SYNC_STATUS)
			.append(Constants.COMMA)
			.append(Constants.CSV_HEADER_ASYNC_STATUS)
			.append(Constants.COMMA)
			.append(Constants.CSV_HEADER_ASYNC_STATUS_DATE)
			.append(Constants.NEW_LINE);
			while (rs.next()) {
				list.add(rs.getInt(Constants.FRAUGSTER_SCHEDULAR_TABLE_ID));
				builder.append(rs.getString(Constants.FRAUGSTER_SCHEDULAR_TABLE_FRAUGSTERTRANSACTION_ID))
				.append(Constants.COMMA)
				.append(rs.getString(Constants.FRAUGSTER_SCHEDULAR_TABLE_ATLAS_ID))
				.append(Constants.COMMA);
				if(rs.getBoolean(Constants.FRAUGSTER_SCHEDULAR_TABLE_STATUS)){
					builder.append(Constants.BOOLEAN_STATUS_TRUE_FLAG);
				} else {
					builder.append(Constants.BOOLEAN_STATUS_FALSE_FLAG);
				}
				builder.append(Constants.COMMA);
				builder.append(rs.getString(Constants.FRAUGSTER_SCHEDULAR_TABLE_SYNC_STATUS));
				builder.append(Constants.COMMA);
				if(null == rs.getString(Constants.FRAUGSTER_SCHEDULAR_TABLE_ASYNC_STATUS)){
					builder.append("");
				}else{
					builder.append(rs.getString(Constants.FRAUGSTER_SCHEDULAR_TABLE_ASYNC_STATUS));
				}
				builder.append(Constants.COMMA);
				
				if(null == rs.getTimestamp(Constants.FRAUGSTER_SCHEDULAR_TABLE_ASYNC_STATUS_DATE)){
					builder.append("");
				}else{
					builder.append(rs.getTimestamp(Constants.FRAUGSTER_SCHEDULAR_TABLE_ASYNC_STATUS_DATE));
				}
				builder.append(Constants.NEW_LINE);
				resultSetCount++;
			}
			if(resultSetCount==0){ 
				return Constants.EMPTY_STRING;
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return builder.toString();
	}

}


