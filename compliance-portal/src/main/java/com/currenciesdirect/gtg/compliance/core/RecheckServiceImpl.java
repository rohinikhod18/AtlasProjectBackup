package com.currenciesdirect.gtg.compliance.core;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RegistrationRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.recheckmonitor.RecheckMonitorThread;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;

/**
 * The Class RecheckServiceImpl.
 */
@Component("recheckServiceImpl")
public class RecheckServiceImpl implements IRecheckService {

	public static final String BASE_COMPLIANCE_SERVICE_URL = "baseComplianceServiceUrl";

	/** The i recheck DB service. */
	@Autowired
	@Qualifier("reCheckDBServiceImpl")
	private IRecheckDBService iRecheckDBService;
	
	/** The payment out DB service impl. */
	@Autowired
    @Qualifier("paymentOutDBServiceImpl")
	private IPaymentOutDBService paymentOutDBServiceImpl;
	
	/** The payment in DB service impl. */
	@Autowired
    @Qualifier("payInDBServiceImpl")
	private IPaymentInDBService paymentInDBServiceImpl;
	
	@Value("${datalake.tx.kafka.enable}")
	public String datalakeTxKafkaEnable;

	

	/** The log. */
	private Logger log = LoggerFactory.getLogger(RecheckServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#
	 * repeatCheckFundsOutFailures(com.currenciesdirect.gtg.compliance.iam.core.
	 * domain.UserProfile,
	 * com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse repeatCheckFundsOutFailures(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse baseRepeatCheckResponse = new BaseRepeatCheckResponse();
		List<PaymentOutRecheckFailureDetails> paymentOutFailedList;
		List<ReprocessFailedDetails> reprocessFailedList;
		try {
			paymentOutFailedList = iRecheckDBService.getFundsOutFailures(request);
			int noOfRecordsToProcess = iRecheckDBService.saveFailedDetailsToDB(paymentOutFailedList, baseRepeatCheckResponse);
			reprocessFailedList = iRecheckDBService.getReprocessList(request, baseRepeatCheckResponse);
			RecheckMonitorThread recheckMonitorThread = new RecheckMonitorThread(user,  
					 reprocessFailedList, iRecheckDBService, baseRepeatCheckResponse);
			recheckMonitorThread.setName(Constants.RECHECK_MONITOR+"-"+reprocessFailedList.get(0).getBatchId());
			recheckMonitorThread.start();
			baseRepeatCheckResponse.setTotalCount(noOfRecordsToProcess);
			return baseRepeatCheckResponse;
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#repeatCheckRegistrationFailures(com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse repeatCheckRegistrationFailures(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
		List<RegistrationRecheckFailureDetails> registrationFailedList;
		List<ReprocessFailedDetails> reprocessFailedList;
		try {
			registrationFailedList = iRecheckDBService.getRegistrationFailures(request);
			int noOfRecordsToProcess = iRecheckDBService.saveFailedRegDetailsToDB(registrationFailedList, repeatCheckResponse);
			reprocessFailedList = iRecheckDBService.getReprocessList(request, repeatCheckResponse);
			RecheckMonitorThread recheckMonitorThread = new RecheckMonitorThread(user,  
					 reprocessFailedList, iRecheckDBService, repeatCheckResponse);
			recheckMonitorThread.setName(Constants.RECHECK_MONITOR+"-"+reprocessFailedList.get(0).getBatchId());
			recheckMonitorThread.start();
			repeatCheckResponse.setTotalCount(noOfRecordsToProcess);
			return repeatCheckResponse;

		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}

	/**
	 * Log error.
	 *
	 * @param t
	 *            the t
	 */
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#
	 * getRegistrationServiceFailureCount(com.currenciesdirect.gtg.compliance.
	 * iam.core.domain.UserProfile,
	 * com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse repeatCheckFundsInFailures(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse baseRepeatCheckResponse = new BaseRepeatCheckResponse();
		List<PaymentInRecheckFailureDetails> paymentInFailedList;
		List<ReprocessFailedDetails> reprocessFailedList;
		try {
			paymentInFailedList = iRecheckDBService.getFundsInFailures(request);
			int noOfRecordsToProcess = iRecheckDBService.saveFailedFundsInDetailsToDB(paymentInFailedList, baseRepeatCheckResponse);
			reprocessFailedList = iRecheckDBService.getReprocessList(request, baseRepeatCheckResponse);
            RecheckMonitorThread recheckMonitorThread =
                  new RecheckMonitorThread(user, reprocessFailedList, iRecheckDBService, baseRepeatCheckResponse);
            recheckMonitorThread.setName(Constants.RECHECK_MONITOR+"-"+reprocessFailedList.get(0).getBatchId());
			recheckMonitorThread.start();
			baseRepeatCheckResponse.setTotalCount(noOfRecordsToProcess);
			return baseRepeatCheckResponse;
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#getRegistrationServiceFailureCount(com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse getRegistrationServiceFailureCount(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse repeatCheckResponse = null;
		try {
			repeatCheckResponse = iRecheckDBService.getRegistrationServiceFailureCount(request);
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return repeatCheckResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#
	 * getFundsInServiceFailureCount(com.currenciesdirect.gtg.compliance.iam.
	 * core.domain.UserProfile,
	 * com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse getFundsInServiceFailureCount(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse repeatCheckResponse = null;
		try {
			repeatCheckResponse = iRecheckDBService.getFundsInServiceFailureCount(request);
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return repeatCheckResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#
	 * getFundsOutServiceFailureCount(com.currenciesdirect.gtg.compliance.iam.
	 * core.domain.UserProfile,
	 * com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse getFundsOutServiceFailureCount(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse repeatCheckResponse = null;
		try {
			repeatCheckResponse = iRecheckDBService.getFundsOutServiceFailureCount(request);
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return repeatCheckResponse;
	}

	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#getRepeatCheckProgressBar(com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public Integer getRepeatCheckProgressBar(BaseRepeatCheckRequest request) throws CompliancePortalException {
		int response = 0;
		try {
			response = iRecheckDBService.getRepeatCheckProgressBar(request);
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#forceClearfundsOuts(com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse forceClearFundsOuts(UserProfile user, BaseRepeatCheckRequest request) throws CompliancePortalException{
	  BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
	  List<PaymentOutRecheckFailureDetails> paymentOutFailedList;
      try {
          paymentOutFailedList = iRecheckDBService.getFundsOutFailures(request);
          PaymentOutActivityLogDto activityLogs;
          for(PaymentOutRecheckFailureDetails entry:paymentOutFailedList){
            activityLogs = builFundsOutActivity(user, entry);
            paymentOutDBServiceImpl.forceClearPaymentOut(entry, activityLogs, user);
            callKafkaForPaymentUpdate(Constants.OPERATION_UPDATE_OPI,Constants.SERVICE_FUNDSOUT,UUID.randomUUID().toString(),String.valueOf(entry.getTradePaymentId()));
          }
          repeatCheckResponse.setStatus("PASS");
      }catch (CompliancePortalException e) {
        logError(e);
        throw e;
    } catch (Exception e) {
        logError(e);
        throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
    }
	  return repeatCheckResponse;
	}
	
	public void callKafkaForPaymentUpdate(String operationType, String serviceType, String transactionId, String tradePaymentId) {
		
		if(!("true".equalsIgnoreCase(datalakeTxKafkaEnable))) {
			log.info("------- Feature DatalakeTxKafka is Disabled --------\n");
			return;
		}
		
		try {
		String baseUrl = System.getProperty("baseComplianceServiceUrl")+"/compliance-service/rest-services/producedatalaketxmessage";
		baseUrl+="?operation="+operationType+"&serviceType="+serviceType+"&transactionId="+transactionId+"&tradePaymentId="+tradePaymentId;
		
		
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		clientPool.sendAsyncRequest(
				baseUrl, "POST",
				null, headers, MediaType.APPLICATION_JSON_TYPE);

		}catch(CompliancePortalException e) {
			log.error(e.getMessage());
		}
	}
		
	
	
	/**
	 * Buil funds out activity.
	 *
	 * @param user the user
	 * @param entry the entry
	 * @return the payment out activity log dto
	 */
	private PaymentOutActivityLogDto builFundsOutActivity(UserProfile user, PaymentOutRecheckFailureDetails entry){
	  PaymentOutActivityLogDto logData = new PaymentOutActivityLogDto();
	  
	  logData.setAccountId(entry.getAccountId());
	  logData.setActivityBy(user.getName());
	  logData.setComment("Force cleared payment");
	  logData.setCreatedBy(user.getName());
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	  logData.setCreatedOn(timestamp);
	  logData.setOrgCode(entry.getOrgCode());
	  logData.setTimeStatmp(timestamp);
	  logData.setUpdatedBy(user.getName());
	  logData.setUpdatedOn(timestamp);
	  logData.setPaymentOutId(entry.getPaymentOutId());
	  
	  PaymentOutActivityLogDetailDto activityLogDetailDto = new PaymentOutActivityLogDetailDto();
      activityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_RECHECK);
      activityLogDetailDto.setLog("Force cleared Payment out");
      activityLogDetailDto.setCreatedBy(user.getName());
      activityLogDetailDto.setUpdatedBy(user.getName());
      activityLogDetailDto.setCreatedOn(timestamp);
      activityLogDetailDto.setUpdatedOn(timestamp);
      logData.setActivityLogDetailDto(activityLogDetailDto);	  
	  return logData;
	}
	

	
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#forceClearFundsIn(com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile, com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public BaseRepeatCheckResponse forceClearFundsIn(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException {
		BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
		List<PaymentInRecheckFailureDetails> paymentInFailedList;
		try {
			paymentInFailedList = iRecheckDBService.getFundsInFailures(request);
			PaymentInActivityLogDto activityLogs;
			for (PaymentInRecheckFailureDetails entry : paymentInFailedList) {
				activityLogs = builFundsInActivity(user, entry);
				paymentInDBServiceImpl.forceClearPaymentIn(entry, activityLogs, user);
	            callKafkaForPaymentUpdate(Constants.OPERATION_UPDATE_OPI,Constants.SERVICE_FUNDSIN,UUID.randomUUID().toString(),String.valueOf(entry.getTradePaymentId()));
			}
			repeatCheckResponse.setStatus("PASS");
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return repeatCheckResponse;
	}

	/**
	 * Buil funds in activity.
	 *
	 * @param user the user
	 * @param entry the entry
	 * @return the payment in activity log dto
	 */
	private PaymentInActivityLogDto builFundsInActivity(UserProfile user, PaymentInRecheckFailureDetails entry) {
		PaymentInActivityLogDto logData = new PaymentInActivityLogDto();

		logData.setAccountId(entry.getAccountId());
		logData.setActivityBy(user.getName());
		logData.setComment("Force cleared payment");
		logData.setCreatedBy(user.getName());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		logData.setCreatedOn(timestamp);
		logData.setOrgCode(entry.getOrgCode());
		logData.setTimeStatmp(timestamp);
		logData.setUpdatedBy(user.getName());
		logData.setUpdatedOn(timestamp);
		logData.setPaymentInId(entry.getPaymentInId());

		PaymentInActivityLogDetailDto activityLogDetailDto = new PaymentInActivityLogDetailDto();
		activityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_RECHECK);
		activityLogDetailDto.setLog("Force cleared Payment In");
		activityLogDetailDto.setCreatedBy(user.getName());
		activityLogDetailDto.setUpdatedBy(user.getName());
		activityLogDetailDto.setCreatedOn(timestamp);
		activityLogDetailDto.setUpdatedOn(timestamp);
		logData.setActivityLogDetailDto(activityLogDetailDto);
		return logData;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRecheckService#deleteReprocessFailed(com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest)
	 */
	@Override
	public Integer deleteReprocessFailed(BaseRepeatCheckRequest request) throws CompliancePortalException {
		Integer result = 0;
		try {
			result = iRecheckDBService.deleteReprocessFailed(request);
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return result;
	}
	
	/**
	 * Update TMMQ retry count.
	 *
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	public boolean updateTMMQRetryCount() throws CompliancePortalException {
		boolean result = false;
		try {
			result = iRecheckDBService.updateTMMQRetryCount();
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return result;
	}

	/**
	 * Show count reprocess failed.
	 *
	 * @param batchId the batch id
	 * @return the string
	 * @throws CompliancePortalException the compliance portal exception
	 */
	//AT-4289
	@Override
	public String showCountReprocessFailed(Integer batchId) throws CompliancePortalException {
		String result;
		try {
			result = iRecheckDBService.showCountReprocessFailed(batchId);
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return result;
	}

	//AT-4289
	@Override
	public Integer clearReprocessFailed() throws CompliancePortalException {
		Integer result = 0;
		try {
			result = iRecheckDBService.clearReprocessFailed();
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
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
		boolean result = false;
		try {
			result = iRecheckDBService.updatePostCardTMMQRetryCount();
		} catch (CompliancePortalException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return result;
	}
}
