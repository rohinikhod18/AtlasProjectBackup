package com.currenciesdirect.gtg.compliance.compliancesrv.core.blacklist;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogData;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.PaymentReferenceResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class ResendActivityLogBuilder.
 */
public class PaymentReferenceResendActivityLogBuilder {

	public static final String UPDATED_STATUS = "{UPDATED_STATUS}";
	public static final String PRE_STATUS = "{PRE_STATUS}";
	public static final String PAYMENT_REFERENCE = "Payment Reference";
	public static final String RECHECK_SERVICE_NAME = "{RECHECK_SERVICE_NAME}";
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentReferenceResendActivityLogBuilder.class);

	/**
	 * Builds the activity logs.
	 *
	 * @param fResendMessage the f resend message
	 * @return the message
	 */
	@ServiceActivator
	public Message<MessageContext> buildActivityLogs(Message<MessageContext> fResendMessage) {

		try {
			UserProfile userProfile = (UserProfile) fResendMessage.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			String userName = userProfile.getPreferredUserName();
			MessageExchange fExchange = fResendMessage.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			ServiceInterfaceType serviceType = fResendMessage.getPayload().getGatewayMessageExchange()
					.getServiceInterface();
			ActivityLogs resendActivityLogs = null;
			String summary;
			PaymentReferenceResendResponse fResendResponse = new PaymentReferenceResendResponse();

			if (ServiceInterfaceType.FUNDSOUT == serviceType) {

				FundsOutPaymentReferenceResendRequest resendRequest = fResendMessage.getPayload().getGatewayMessageExchange()
						.getRequest(FundsOutPaymentReferenceResendRequest.class);
				FundsOutRequest fRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsOutRequest.class);
				EventServiceLog resendLog = fExchange.getEventServiceLog(ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE,
						EntityEnum.BENEFICIARY.name(), fRequest.getBeneficiary().getBeneficiaryId());
				summary = resendLog.getSummary();
				setPaymetReferenceSummaryData(summary, fResendResponse);
				List<PaymentOutActivityLogDto> activityLogDtos = getPaymentOutActivityLog(fRequest, userName,
						resendLog);
				resendActivityLogs = getPaymentOutActivityLogData(activityLogDtos);
				fResendResponse.setActivityLogs(resendActivityLogs);
				fResendResponse.setEventServiceLogId(resendLog.getEventServiceLogId());
				fResendResponse.addAttribute("paymentOutActivities", activityLogDtos);
			}
			fResendMessage.getPayload().getGatewayMessageExchange().setResponse(fResendResponse);
		} catch (Exception ex) {
			LOGGER.error("Error building log : ", ex);
			fResendMessage.getPayload().setFailed(true);
		}

		return fResendMessage;
	}
	
	/**
	 * Gets the payment out activity log.
	 *
	 * @param request the request
	 * @param userName the user name
	 * @param logs the logs
	 * @return the payment out activity log
	 */
	private List<PaymentOutActivityLogDto> getPaymentOutActivityLog(FundsOutRequest request, String userName,
			EventServiceLog logs) {

		List<PaymentOutActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		PaymentOutActivityLogDto fPOActivityLogDto = new PaymentOutActivityLogDto();
		fPOActivityLogDto.setPaymentOutId(request.getFundsOutId());
		fPOActivityLogDto.setOrgCode(request.getOrgCode());
		fPOActivityLogDto.setActivityBy(userName);
		fPOActivityLogDto.setCreatedBy(userName);
		fPOActivityLogDto.setUpdatedBy(userName);
		fPOActivityLogDto.setCreatedOn(timestamp);
		fPOActivityLogDto.setTimeStatmp(timestamp);
		fPOActivityLogDto.setUpdatedOn(timestamp);
		PaymentOutActivityLogDetailDto fPOActivityLogDetailDto = new PaymentOutActivityLogDetailDto();
		fPOActivityLogDto.setActivityLogDetailDto(fPOActivityLogDetailDto);
		fPOActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_PAYMENT_REFERENCE_REPEAT);
		fPOActivityLogDetailDto.setLog(getPaymentOutPaymentReferenceLog(request, logs));
		fPOActivityLogDetailDto.setCreatedBy(userName);
		fPOActivityLogDetailDto.setUpdatedBy(userName);
		fPOActivityLogDetailDto.setCreatedOn(timestamp);
		fPOActivityLogDetailDto.setUpdatedOn(timestamp);
		activityLogDtos.add(fPOActivityLogDto);
		return activityLogDtos;
	}
	
	/**
	 * Set blacklist summary data
	 * @param userName
	 * @param summary
	 * @param resendResponse
	 * @param eventServiceLog
	 */
	private void setPaymetReferenceSummaryData(String summary, PaymentReferenceResendResponse resendResponse) {
		if(summary != null) {
			BlacklistPayRefSummary paymentReferenceSummary = JsonConverterUtil.convertToObject(BlacklistPayRefSummary.class, summary);
			 resendResponse.setSummary(paymentReferenceSummary);
			}
		}	
	
	/**
	 * Gets the payment out activity log data.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @return the payment out activity log data
	 */
	private ActivityLogs getPaymentOutActivityLogData(List<PaymentOutActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = null;
		if (activityLogDtos != null && !activityLogDtos.isEmpty()) {
			activityLogs = getPaymentOutActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}
	
	/**
	 * Gets the payment out activity logs response.
	 *PAYMENT_OUT_PAYMENT_REFERENCE_REPEAT
	 * @param activityLogDtos the activity log dtos
	 * @return the payment out activity logs response
	 */
	private ActivityLogs getPaymentOutActivityLogsResponse(List<PaymentOutActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentOutActivityLogDto profileActivityLogDto : activityLogDtos) {
			ActivityLogData activity = new ActivityLogData();
			activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PAYMENT_OUT_PAYMENT_REFERENCE_REPEAT.getModule() + " "
					+ ActivityType.PAYMENT_OUT_PAYMENT_REFERENCE_REPEAT.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	/**
	 * Gets the payment out payment reference log.
	 *
	 * @param request the request
	 * @param log the log
	 * @return the payment out payment reference log
	 */
	private String getPaymentOutPaymentReferenceLog(FundsOutRequest request, EventServiceLog log) {
		String prevFStatus = request.getAdditionalAttribute(Constants.PAYMENT_REFERENCE_STATUS, String.class);
		if(prevFStatus == null) {
			prevFStatus = Constants.NOT_PERFORMED;
		}
		return ActivityTemplateEnum.PAYMENT_RECHECK.getTemplate().replace(RECHECK_SERVICE_NAME, PAYMENT_REFERENCE)
				.replace(PRE_STATUS, prevFStatus).replace(UPDATED_STATUS, log.getStatus());

	}	
}
	