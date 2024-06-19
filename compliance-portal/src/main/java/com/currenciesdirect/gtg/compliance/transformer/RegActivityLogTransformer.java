package com.currenciesdirect.gtg.compliance.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;

/**
 * The Class RegActivityLogTransformer.
 */
@Component("regActivityLogTransformer")
public class RegActivityLogTransformer implements ITransform<ActivityLogs, RegUpdateDBDto> {

	@Override
	public ActivityLogs transform(RegUpdateDBDto regUpdateRequestHandlerDto) {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDatas = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDatas);
		if (regUpdateRequestHandlerDto == null || regUpdateRequestHandlerDto.getActivityLog() == null) {
			return activityLogs;
		}
		for (ProfileActivityLogDto activityLogDtos : regUpdateRequestHandlerDto.getActivityLog()) {
			ActivityLogDataWrapper activityLogData = getActivityLogData(activityLogDtos);
			if (activityLogData != null) {
				activityLogDatas.add(activityLogData);
			}
		}
		activityLogs.setComplianceDoneOn(regUpdateRequestHandlerDto.getComplianceDoneOn());
		activityLogs.setRegistrationInDate(regUpdateRequestHandlerDto.getRegistrationInDate());
		activityLogs.setComplianceExpiry(regUpdateRequestHandlerDto.getComplianceExpiry());
		return activityLogs;
	}

	private ActivityLogDataWrapper getActivityLogData(ProfileActivityLogDto activityLogDto) {
		ActivityLogDataWrapper activityLogData = null;
		ActivityLogDetailDto activityLogDetailDto = activityLogDto.getActivityLogDetailDto();

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
