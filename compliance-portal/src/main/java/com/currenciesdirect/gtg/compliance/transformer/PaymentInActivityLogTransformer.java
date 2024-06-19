package com.currenciesdirect.gtg.compliance.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;

@Component("paymentInActivityLogTransformer")
public class PaymentInActivityLogTransformer implements ITransform<ActivityLogs, PaymentInUpdateDBDto> {

	@Override
	public ActivityLogs transform(PaymentInUpdateDBDto paymentInUpdateDBDto) {
		
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDatas = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDatas);
		if (paymentInUpdateDBDto == null || paymentInUpdateDBDto.getActivityLogs() == null) {
			return activityLogs;
		}
		for (PaymentInActivityLogDto activityLogDtos : paymentInUpdateDBDto.getActivityLogs()) {
			ActivityLogDataWrapper activityLogData = getActivityLogData(activityLogDtos);
			if (activityLogData != null) {
				activityLogDatas.add(activityLogData);
			}
		}
		activityLogs.setIsWatchlistUpdated(paymentInUpdateDBDto.getIsWatchlistUpdated()); //AT-4576
		return activityLogs;
	}

	/**
	 * @param activityLogDtos
	 * @return ActivityLogData
	 */
	private ActivityLogDataWrapper getActivityLogData(PaymentInActivityLogDto activityLogDtos) {
		
		ActivityLogDataWrapper activityLogData = null;
		PaymentInActivityLogDetailDto activityLogDetailDto = activityLogDtos.getActivityLogDetailDto();

		if (activityLogDetailDto != null) {
			activityLogData = new ActivityLogDataWrapper();
			String log = "";
			if (activityLogDtos.getActivityLogDetailDto() != null && activityLogDtos.getActivityLogDetailDto().getLog() != null) {
				log = activityLogDtos.getActivityLogDetailDto().getLog();
			}
			activityLogData.setActivity(log);
			ActivityType activityType = activityLogDetailDto.getActivityType();
			activityLogData.setActivityType(activityType.getModule() + " " + activityType.getType());
			activityLogData.setCreatedBy(activityLogDetailDto.getCreatedBy());
			activityLogData.setCreatedOn(DateTimeFormatter.dateTimeFormatter(activityLogDetailDto.getCreatedOn()));
		}

		return activityLogData;
	}
	
}
