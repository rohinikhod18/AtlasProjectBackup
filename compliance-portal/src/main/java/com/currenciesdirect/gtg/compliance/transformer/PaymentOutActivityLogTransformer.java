package com.currenciesdirect.gtg.compliance.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;

/**
 * The Class PaymentOutActivityLogTransformer.
 */
@Component("paymentOutActivityLogTransformer")
public class PaymentOutActivityLogTransformer implements ITransform<ActivityLogs, PaymentOutUpdateDBDto> {

	@Override
	public ActivityLogs transform(PaymentOutUpdateDBDto updateRequestHandlerDto) {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDatas = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDatas);
		if (updateRequestHandlerDto == null || updateRequestHandlerDto.getActivityLogs() == null) {
			return activityLogs;
		}
		for (PaymentOutActivityLogDto activityLogDtos : updateRequestHandlerDto.getActivityLogs()) {
			ActivityLogDataWrapper activityLogData = getActivityLogData(activityLogDtos);
			if (activityLogData != null) {
				activityLogDatas.add(activityLogData);
			}
		}
		activityLogs.setIsWatchlistUpdated(updateRequestHandlerDto.getIsWatchlistUpdated()); //AT-4576
		return activityLogs;
	}

	private ActivityLogDataWrapper getActivityLogData(PaymentOutActivityLogDto activityLogDto) {
		ActivityLogDataWrapper activityLogData = null;
		PaymentOutActivityLogDetailDto activityLogDetailDto = activityLogDto.getActivityLogDetailDto();

		if (activityLogDetailDto != null) {
			activityLogData = new ActivityLogDataWrapper();
			String log = "";
			if (activityLogDto.getActivityLogDetailDto() != null
					&& activityLogDto.getActivityLogDetailDto().getLog() != null) {
				log = activityLogDto.getActivityLogDetailDto().getLog();
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
