package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.commons.enums.ReprocessStatusEnum;
import com.currenciesdirect.gtg.compliance.commons.enums.TransactionTypeEnum;
import com.currenciesdirect.gtg.compliance.core.IRecheckDBService;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RegistrationRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class RecheckDBServiceImpl.
 */
@Component("reCheckDBServiceImpl")
public class RecheckDBServiceImpl extends AbstractDao implements IRecheckDBService {

	/** The Constant STATUS. */
	private static final String STATUS = "Status";

  /** The Constant ORG_CODE. */
  public static final String ORG_CODE = "orgCode";

	/** The Constant ORG_ID. */
	public static final String ORG_ID = "orgID";

	/** The Constant CONTACT_ID. */
	public static final String CONTACT_ID = "ContactID";

	/** The Constant ACCOUNT_ID. */
	public static final String ACCOUNT_ID = "AccountID";

	/** The Constant EID_FAIL. */
	private static final String EID_FAIL = "Eid_Fail";

	/** The Constant CUSTOM_CHECK_FAIL. */
	private static final String CUSTOM_CHECK_FAIL = "CustomCheck_Fail";

	/** The Constant SANCTION_FAIL. */
	private static final String SANCTION_FAIL = "Sanction_Fail";

	/** The Constant BLACKLIST_FAIL. */
	private static final String BLACKLIST_FAIL = "Blacklist_Fail";

	/** The Constant FRAUGSTER_FAIL. */
	private static final String FRAUGSTER_FAIL = "Fraugster_Fail";
	
	private static final String MAX_BATCH_ID = "MaxBatchId";
	
	private static final String SOME_DATA_IS_MISSING = "Some data is missing";
	
	private static final String COUNT = "count";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#
	 * getFundsOutFailures(com.currenciesdirect.gtg.compliance.core.domain.
	 * BaseRepeatCheckRequest)
	 */
	@Override
	public List<PaymentOutRecheckFailureDetails> getFundsOutFailures(BaseRepeatCheckRequest request)
			throws CompliancePortalException {

		List<PaymentOutRecheckFailureDetails> failedPaymentOutList = new ArrayList<>();
		String listQuery = PaymentOutQueryConstant.GET_SERVICE_FAILURE_PAYMENT_OUT_DETAILS.getQuery();
		Connection connection = null;
		PreparedStatement listStatement = null;
		ResultSet listRs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			listStatement = connection.prepareStatement(listQuery);
			listStatement.setString(1, request.getDateFrom());
			listStatement.setString(2, request.getDateTo());
			listRs = listStatement.executeQuery();
			while (listRs.next()) {
				PaymentOutRecheckFailureDetails recheckFailureDetail = new PaymentOutRecheckFailureDetails();
				recheckFailureDetail.setAccountId(listRs.getInt(ACCOUNT_ID));
				recheckFailureDetail.setContactId(listRs.getInt(CONTACT_ID));
				recheckFailureDetail.setComplianceStatus(
						PaymentComplianceStatus.getStatusFromDatabaseStatus(listRs.getInt("compliancestatus")));
				recheckFailureDetail.setOrgId(listRs.getInt(ORG_ID));
				recheckFailureDetail.setOrgCode(listRs.getString(ORG_CODE));
				recheckFailureDetail.setTradePaymentId(listRs.getInt("TradePaymentID"));
				recheckFailureDetail.setTradeContractNumber(listRs.getString("ContractNumber"));
				recheckFailureDetail.setPaymentOutId(listRs.getInt("paymentID"));
				failedPaymentOutList.add(recheckFailureDetail);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeConnection(connection);
		}
		return failedPaymentOutList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#
	 * getRegistrationServiceFailureCount(com.currenciesdirect.gtg.compliance.
	 * core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public List<PaymentInRecheckFailureDetails> getFundsInFailures(BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		
		List<PaymentInRecheckFailureDetails> failedPaymentInList = new ArrayList<>();
		String listQuery = PaymentInQueryConstant.GET_SERVICE_FAILURE_PAYMENT_IN_DETAILS.getQuery();
		Connection connection = null;
		PreparedStatement listStatement = null;
		ResultSet listRs = null;
		try{
			connection = getConnection(Boolean.TRUE);
			listStatement = connection.prepareStatement(listQuery);
			listStatement.setString(1, request.getDateFrom());
			listStatement.setString(2, request.getDateTo());
			listRs = listStatement.executeQuery();
			while(listRs.next()) {
				PaymentInRecheckFailureDetails recheckFailureDetail = new PaymentInRecheckFailureDetails();
				recheckFailureDetail.setAccountId(listRs.getInt(ACCOUNT_ID));
				recheckFailureDetail.setContactId(listRs.getInt(CONTACT_ID));
				recheckFailureDetail.setComplianceStatus(PaymentComplianceStatus.getStatusFromDatabaseStatus(listRs.getInt("compliancestatus")));
				recheckFailureDetail.setOrgId(listRs.getInt(ORG_ID));
				recheckFailureDetail.setOrgCode(listRs.getString(ORG_CODE));
				recheckFailureDetail.setTradePaymentId(listRs.getInt("TradePaymentID"));
				recheckFailureDetail.setTradeContractNumber(listRs.getString("TradeContractNumber"));
				recheckFailureDetail.setPaymentInId(listRs.getInt("paymentID"));
				failedPaymentInList.add(recheckFailureDetail);
			}
		} catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeConnection(connection);
		}		
		return failedPaymentInList;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#getRegistrationServiceFailureCount(com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse getRegistrationServiceFailureCount(BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse baseRepeatCheckResponse = null;
		String listQuery;
		
		//AT-5465
		Boolean scanActiveCustomerForBRC = Boolean.parseBoolean(System.getProperty("scanActiveCustomerForBRC"));
		if(Boolean.TRUE.equals(scanActiveCustomerForBRC)) {
			 listQuery = RegistrationQueryConstant.GET_SERVICE_FAILURE_REG_COUNT_FOR_ACTIVE_CUSTOMER.getQuery();
		}else {
			 listQuery = RegistrationQueryConstant.GET_SERVICE_FAILURE_REG_COUNT.getQuery();
		}
		
		Connection connection = null;
		PreparedStatement listStatement = null;
		ResultSet listRs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			listStatement = connection.prepareStatement(listQuery);
			listStatement.setString(1, request.getDateFrom());
			listStatement.setString(2, request.getDateTo());
			listStatement.setString(3, request.getDateFrom());
			listStatement.setString(4, request.getDateTo());
			listRs = listStatement.executeQuery();
			while (listRs.next()) {
				baseRepeatCheckResponse = new BaseRepeatCheckResponse();
				baseRepeatCheckResponse.setFraugsterFailCount(listRs.getInt(FRAUGSTER_FAIL));
				baseRepeatCheckResponse.setSanctionFailCount(listRs.getInt(SANCTION_FAIL));
				baseRepeatCheckResponse.setBlacklistFailCount(listRs.getInt(BLACKLIST_FAIL));
				baseRepeatCheckResponse.setEidFailCount(listRs.getInt(EID_FAIL));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeConnection(connection);
		}

		return baseRepeatCheckResponse;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#
	 * getFundsInServiceFailureCount(com.currenciesdirect.gtg.compliance.core.
	 * domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse getFundsInServiceFailureCount(BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse baseRepeatCheckResponse = null;
		String listQuery = PaymentInQueryConstant.GET_SERVICE_FAILURE_PAYMENT_IN_COUNT.getQuery();
		Connection connection = null;
		PreparedStatement listStatement = null;
		ResultSet listRs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			listStatement = connection.prepareStatement(listQuery);
			listStatement.setString(1, request.getDateFrom());
			listStatement.setString(2, request.getDateTo());
			listRs = listStatement.executeQuery();
			while (listRs.next()) {
				baseRepeatCheckResponse = new BaseRepeatCheckResponse();
				baseRepeatCheckResponse.setFraugsterFailCount(listRs.getInt(FRAUGSTER_FAIL));
				baseRepeatCheckResponse.setSanctionFailCount(listRs.getInt(SANCTION_FAIL));
				baseRepeatCheckResponse.setBlacklistFailCount(listRs.getInt(BLACKLIST_FAIL));
				baseRepeatCheckResponse.setCustomCheckFailCount(listRs.getInt(CUSTOM_CHECK_FAIL));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeConnection(connection);
		}

		return baseRepeatCheckResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#
	 * getFundsOutServiceFailureCount(com.currenciesdirect.gtg.compliance.core.
	 * domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse getFundsOutServiceFailureCount(BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse baseRepeatCheckResponse = null;
		String listQuery = PaymentOutQueryConstant.GET_SERVICE_FAILURE_PAYMENT_OUT_COUNT.getQuery();
		Connection connection = null;
		PreparedStatement listStatement = null;
		ResultSet listRs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			listStatement = connection.prepareStatement(listQuery);
			listStatement.setString(1, request.getDateFrom());
			listStatement.setString(2, request.getDateTo());
			listRs = listStatement.executeQuery();
			while (listRs.next()) {
				baseRepeatCheckResponse = new BaseRepeatCheckResponse();
				baseRepeatCheckResponse.setFraugsterFailCount(listRs.getInt(FRAUGSTER_FAIL));
				baseRepeatCheckResponse.setSanctionFailCount(listRs.getInt(SANCTION_FAIL));
				baseRepeatCheckResponse.setBlacklistFailCount(listRs.getInt(BLACKLIST_FAIL));
				baseRepeatCheckResponse.setCustomCheckFailCount(listRs.getInt(CUSTOM_CHECK_FAIL));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeConnection(connection);
		}

		return baseRepeatCheckResponse;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#getRegistrationFailures(com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public List<RegistrationRecheckFailureDetails> getRegistrationFailures(BaseRepeatCheckRequest request)
			throws CompliancePortalException {

		List<RegistrationRecheckFailureDetails> failedRegistrationList = new ArrayList<>();
		String listQueryForPfx;
		//AT-5465
		Boolean scanActiveCustomerForBRC = Boolean.parseBoolean(System.getProperty("scanActiveCustomerForBRC"));
		
		if(Boolean.TRUE.equals(scanActiveCustomerForBRC)) {
			listQueryForPfx = RegistrationQueryConstant.GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_PFX_ACTIVE_CUSTOMER.getQuery();
		}else {
			listQueryForPfx = RegistrationQueryConstant.GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_PFX.getQuery();
		}
		Connection connectionForPfx = null;
		PreparedStatement listStatementForPfx = null;
		ResultSet listRsForPfx = null;

		//AT-5465
		String listQueryForCfx; 
		if(Boolean.TRUE.equals(scanActiveCustomerForBRC)) { 
			listQueryForCfx = RegistrationQueryConstant.GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_CFX_ACTIVE_CUSTOMER.getQuery();
		}else {
			listQueryForCfx = RegistrationQueryConstant.GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_CFX.getQuery();
		}
		Connection connectionForCfx = null;
		PreparedStatement listStatementForCfx = null;
		ResultSet listRsForCfx = null;
		try {
			//PFX
			connectionForPfx = getConnection(Boolean.TRUE);
			listStatementForPfx = connectionForPfx.prepareStatement(listQueryForPfx);
			listStatementForPfx.setString(1, request.getDateFrom());
			listStatementForPfx.setString(2, request.getDateTo());
			listRsForPfx = listStatementForPfx.executeQuery();
			while (listRsForPfx.next()) {
				RegistrationRecheckFailureDetails recheckFailureDetail = new RegistrationRecheckFailureDetails();
				recheckFailureDetail.setAccountId(listRsForPfx.getInt(ACCOUNT_ID));
				recheckFailureDetail.setContactId(listRsForPfx.getInt(CONTACT_ID));
				recheckFailureDetail.setCustomerType(CustomerTypeEnum.getCustumerTypeAsString(listRsForPfx.getInt("type")));
				failedRegistrationList.add(recheckFailureDetail);
			}
			//CFX
			connectionForCfx = getConnection(Boolean.TRUE);
			listStatementForCfx = connectionForCfx.prepareStatement(listQueryForCfx);
			listStatementForCfx.setString(1, request.getDateFrom());
			listStatementForCfx.setString(2, request.getDateTo());
			listStatementForCfx.setString(3, request.getDateFrom());
			listStatementForCfx.setString(4, request.getDateTo());
			listRsForCfx = listStatementForCfx.executeQuery();
			while (listRsForCfx.next()) {
				RegistrationRecheckFailureDetails recheckFailureDetail = new RegistrationRecheckFailureDetails();
				recheckFailureDetail.setAccountId(listRsForCfx.getInt(ACCOUNT_ID));
				recheckFailureDetail.setContactId(listRsForCfx.getInt(CONTACT_ID));
				recheckFailureDetail.setCustomerType(CustomerTypeEnum.getCustumerTypeAsString(listRsForCfx.getInt("type")));
				failedRegistrationList.add(recheckFailureDetail);
			}
		
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRsForPfx);
			closePrepareStatement(listStatementForPfx);
			closeConnection(connectionForPfx);
			closeResultset(listRsForCfx);
			closePrepareStatement(listStatementForCfx);
			closeConnection(connectionForCfx);
		}
		return failedRegistrationList;
	}

	/**
	 * Save failed details to DB.
	 *
	 * @param paymentOutFailedList the payment out failed list
	 * @param baseRepeatCheckResponse the base repeat check response
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#saveFailedDetailsToDB(com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails)
	 */
	@SuppressWarnings("resource")
	@Override
	public Integer saveFailedDetailsToDB(List<PaymentOutRecheckFailureDetails> paymentOutFailedList, BaseRepeatCheckResponse baseRepeatCheckResponse)
			throws CompliancePortalException {
		Integer maxBatchId = 0;
		Connection connection = null;
		Connection getBatchIdConn = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement getBatchIdPreparedStatement = null;
		ResultSet resultSet = null;
		try{
			getBatchIdConn = getConnection(Boolean.TRUE);
			getBatchIdPreparedStatement = getBatchIdConn.prepareStatement(
					PaymentOutQueryConstant.GET_MAX_BATCH_ID_FROM_REPROCESS_FAILED.getQuery());
			resultSet = getBatchIdPreparedStatement.executeQuery();
			
			if(resultSet.next()){
				maxBatchId = resultSet.getInt(MAX_BATCH_ID);
			}			
			
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.INSERT_INTO_REPROCESS_FAILED.getQuery());
			for(PaymentOutRecheckFailureDetails failedPaymentOutDetails : paymentOutFailedList){
				preparedStatement.setInt(1, TransactionTypeEnum.PAYMENTOUT.getTransactionTypeAsInteger());
				preparedStatement.setInt(2, maxBatchId+1);
				preparedStatement.setInt(3, failedPaymentOutDetails.getPaymentOutId());
				preparedStatement.setInt(4, ReprocessStatusEnum.FAILED.getReprocessStatusAsInteger());
				preparedStatement.setInt(5, 0);
				preparedStatement.setInt(6, 1);
				preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int fundsOutInsertCount : count) {
				if (fundsOutInsertCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA,
							new Exception(SOME_DATA_IS_MISSING));
				}
			}
			baseRepeatCheckResponse.setBatchId(maxBatchId+1);
			return count.length;
		} catch(CompliancePortalException cpe) {
			throw cpe;
		}  catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		finally {
			closeResultset(resultSet);
			closePrepareStatement(getBatchIdPreparedStatement);
			closePrepareStatement(preparedStatement);
			closeConnection(getBatchIdConn);
			closeConnection(connection);
		}		
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#getFirstFailedPaymentOutToReprocess()
	 */
	@Override
	public ReprocessFailedDetails getFirstFailedRecordToReprocess(BaseRepeatCheckResponse baseRepeatCheckResponse, BaseRepeatCheckRequest request) throws CompliancePortalException {
		ReprocessFailedDetails failedRecordsDetails = new ReprocessFailedDetails();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.GET_FIRST_FAILED_RECORD.getQuery());
			preparedStatement.setInt(1, baseRepeatCheckResponse.getBatchId());
			
			if(request.getModuleName().equalsIgnoreCase("Payment Out"))
				preparedStatement.setInt(2, 3);
			if(request.getModuleName().equalsIgnoreCase("Payment In"))
				preparedStatement.setInt(2, 2);
			if(request.getModuleName().equalsIgnoreCase("Registration"))
				preparedStatement.setInt(2, 1);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				failedRecordsDetails.setId(resultSet.getInt("ID"));
				failedRecordsDetails.setBatchId(resultSet.getInt("BatchId"));
				failedRecordsDetails.setTransId(resultSet.getInt("TransId"));
				failedRecordsDetails.setTransType(TransactionTypeEnum.getTransactionTypeAsString(resultSet.getInt("TransType")));
				failedRecordsDetails.setStatus(ReprocessStatusEnum.getReprocessStatusAsString(resultSet.getInt(STATUS)));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return failedRecordsDetails;
		
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#getReprocessStatusForFailed(com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails)
	 */
	@Override
	public boolean getReprocessStatusForFailed(ReprocessFailedDetails reprocessFailedDetails) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.GET_REPROCESS_STATUS_FOR_FAILED_RECORD.getQuery());
			preparedStatement.setInt(1, reprocessFailedDetails.getTransId());
			preparedStatement.setInt(2, TransactionTypeEnum.getTransactionTypeAsInteger(reprocessFailedDetails.getTransType()));
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next() && (3 == resultSet.getInt(STATUS))) {
					return true;
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#deleteReprocessFailed()
	 */
	@Override
	public Integer deleteReprocessFailed(BaseRepeatCheckRequest request) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Integer result = 0;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.DELETE_REPROCESS_FAILED.getQuery());
			preparedStatement.setInt(1,request.getBatchId());
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#getReprocessPaymentsList(com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse)
	 */
	@Override
	public List<ReprocessFailedDetails> getReprocessList(BaseRepeatCheckRequest request, BaseRepeatCheckResponse baseRepeatCheckResponse)
			throws CompliancePortalException {

		List<ReprocessFailedDetails> reprocessPaymentList = new ArrayList<>();
		String listQuery = "";
		if(request.getModuleName().equalsIgnoreCase("Registration"))
			listQuery = RegistrationQueryConstant.GET_REPROCESS_ELIGIBLE_REGISTRATIONS.getQuery();
		else
			listQuery = PaymentOutQueryConstant.GET_REPROCESS_ELIGIBLE_PAYMENTS.getQuery();
		Connection connection = null;
		PreparedStatement listStatement = null;
		ResultSet listRs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			listStatement = connection.prepareStatement(listQuery);
			listStatement.setInt(1, baseRepeatCheckResponse.getBatchId());
			if(request.getModuleName().equalsIgnoreCase("Payment Out"))
				listStatement.setInt(2, TransactionTypeEnum.PAYMENTOUT.getTransactionTypeAsInteger());
			if(request.getModuleName().equalsIgnoreCase("Payment In"))
				listStatement.setInt(2, TransactionTypeEnum.PAYMENTIN.getTransactionTypeAsInteger());
			
			listRs = listStatement.executeQuery();
			while (listRs.next()) {
				ReprocessFailedDetails reprocessFailedPaymentDetail = new ReprocessFailedDetails();
				reprocessFailedPaymentDetail.setId(listRs.getInt("ID"));
				reprocessFailedPaymentDetail.setBatchId(listRs.getInt("BatchId"));
				reprocessFailedPaymentDetail.setTransType(TransactionTypeEnum.getTransactionTypeAsString(listRs.getInt("TransType")));
				reprocessFailedPaymentDetail.setTransId(listRs.getInt("TransId"));
				reprocessFailedPaymentDetail.setStatus(ReprocessStatusEnum.getReprocessStatusAsString(listRs.getInt(STATUS)));				
				reprocessPaymentList.add(reprocessFailedPaymentDetail);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeConnection(connection);
		}
		return reprocessPaymentList;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#saveFailedFundsInDetailsToDB(java.util.List, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse)
	 */
	@SuppressWarnings("resource")
	@Override
	public Integer saveFailedFundsInDetailsToDB(List<PaymentInRecheckFailureDetails> paymentInFailedList, BaseRepeatCheckResponse baseRepeatCheckResponse)
			throws CompliancePortalException {
		Integer maxBatchId = 0;
		Connection connection = null;
		Connection getBatchIdConn = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement getBatchIdPreparedStatement = null;
		ResultSet resultSet = null;
		try{
			
			getBatchIdConn = getConnection(Boolean.TRUE);
			getBatchIdPreparedStatement = getBatchIdConn.prepareStatement(
					PaymentOutQueryConstant.GET_MAX_BATCH_ID_FROM_REPROCESS_FAILED.getQuery());
			resultSet = getBatchIdPreparedStatement.executeQuery();
			
			if(resultSet.next()){
				maxBatchId = resultSet.getInt(MAX_BATCH_ID);
			}			
			
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.INSERT_INTO_REPROCESS_FAILED.getQuery());
			for(PaymentInRecheckFailureDetails failedPaymentInDetails : paymentInFailedList){
				preparedStatement.setInt(1, TransactionTypeEnum.PAYMENTIN.getTransactionTypeAsInteger());
				preparedStatement.setInt(2, maxBatchId+1);
				preparedStatement.setInt(3, failedPaymentInDetails.getPaymentInId());
				preparedStatement.setInt(4, ReprocessStatusEnum.FAILED.getReprocessStatusAsInteger());
				preparedStatement.setInt(5, 0);
				preparedStatement.setInt(6, 1);
				preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int fundsOutInsertCount : count) {
				if (fundsOutInsertCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA,
							new Exception(SOME_DATA_IS_MISSING));
				}
			}
			baseRepeatCheckResponse.setBatchId(maxBatchId+1);
			return count.length;
		} catch(CompliancePortalException cpe) {
			throw cpe;
		}  catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closePrepareStatement(getBatchIdPreparedStatement);
			closeConnection(connection);
			closeConnection(getBatchIdConn);
		}		
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#getRepeatCheckProgressBar(com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public Integer getRepeatCheckProgressBar(BaseRepeatCheckRequest request) throws CompliancePortalException {
		Integer response = null;
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = PaymentOutQueryConstant.GET_REPROCCESS_STATUS.getQuery();
		
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,request.getBatchId());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
					response = resultSet.getInt(STATUS);
				}
			}
		catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			}
		finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}	
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckDBService#saveFailedRegDetailsToDB(java.util.List, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse)
	 */
	@SuppressWarnings("resource")
	@Override
	public int saveFailedRegDetailsToDB(List<RegistrationRecheckFailureDetails> registrationFailedList,
			BaseRepeatCheckResponse repeatCheckResponse) throws CompliancePortalException {
		Integer maxBatchId = 0;
		Connection connection = null;
		Connection getBatchIdConn = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement getBatchIdPreparedStatement = null;
		ResultSet resultSet = null;
		try{
			
			getBatchIdConn = getConnection(Boolean.TRUE);
			getBatchIdPreparedStatement = getBatchIdConn.prepareStatement(
					RegistrationQueryConstant.GET_MAX_BATCH_ID_FROM_REPROCESS_FAILED.getQuery());
			resultSet = getBatchIdPreparedStatement.executeQuery();
			
			if(resultSet.next()){
				maxBatchId = resultSet.getInt(MAX_BATCH_ID);
			}			
			
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.INSERT_INTO_REPROCESS_FAILED.getQuery());
			for(RegistrationRecheckFailureDetails failedRegDetails : registrationFailedList){
				if(0 != failedRegDetails.getAccountId()){
					preparedStatement.setInt(1, TransactionTypeEnum.ACCOUNT.getTransactionTypeAsInteger());
					preparedStatement.setInt(3, failedRegDetails.getAccountId());
				}
				else{
					preparedStatement.setInt(1, TransactionTypeEnum.CONTACT.getTransactionTypeAsInteger());
					preparedStatement.setInt(3, failedRegDetails.getContactId());
				}
				preparedStatement.setInt(2, maxBatchId+1);
				preparedStatement.setInt(4, ReprocessStatusEnum.FAILED.getReprocessStatusAsInteger());
				preparedStatement.setInt(5, 0);
				preparedStatement.setInt(6, 1);
				preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int regInsertCount : count) {
				if (regInsertCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA,
							new Exception(SOME_DATA_IS_MISSING));
				}
			}
			repeatCheckResponse.setBatchId(maxBatchId+1);
			return count.length;
		} catch(CompliancePortalException cpe) {
			throw cpe;
		}  catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closePrepareStatement(getBatchIdPreparedStatement);
			closeConnection(connection);
			closeConnection(getBatchIdConn);
		}		
	}
	
	/**
	 * Update TMMQ retry count.
	 *
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	public boolean updateTMMQRetryCount() throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_TMMQ_RETRY_COUNT.getQuery());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			if(connection != null) {
				transactionRolledBack(connection);
			}
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return true;
	}
	
	
	/**
	 * Show count reprocess failed.
	 *
	 * @param batchId the batch id
	 * @return the string
	 * @throws CompliancePortalException the compliance portal exception
	 */ //AT-4355
	@Override
	public String showCountReprocessFailed(Integer batchId) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		JSONArray countArray = new JSONArray();
		ResultSet listForCount = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.COUNT_REPROCESS_FAILED.getQuery());
			preparedStatement.setInt(1, batchId);
			listForCount = preparedStatement.executeQuery();
			while (listForCount.next()) {
				countArray.put(new JSONObject().put(STATUS, "Pass : ").put(COUNT, listForCount.getString("Pass")))
						.put(new JSONObject().put(STATUS, "Fail : ").put(COUNT, listForCount.getString("Fail")))
						.put(new JSONObject().put(STATUS, "Exception : ").put(COUNT,listForCount.getString("Inprogress")));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listForCount);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return countArray.toString();
	}

	/**
	 * Clear reprocess failed.
	 *
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */ //AT-4355
	@Override
	public Integer clearReprocessFailed() throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Integer result = 0;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.CLEAR_REPROCESS_FAILED.getQuery());
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return result;
	}
	
	/**
	 * Update post card TMMQ retry count.
	 *
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	//Add For AT-5023
	@Override
	public boolean updatePostCardTMMQRetryCount() throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.UPDATE_POST_CARD_TMMQ_RETRY_COUNT.getQuery());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			if (connection != null) {
				transactionRolledBack(connection);
			}
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return true;
	}

}