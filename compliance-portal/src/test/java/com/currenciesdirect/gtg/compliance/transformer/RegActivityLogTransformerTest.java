package com.currenciesdirect.gtg.compliance.transformer;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.domain.*;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
public class RegActivityLogTransformerTest {
@InjectMocks
RegActivityLogTransformer regActivityLogTransformer;
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

	}
	public RegUpdateDBDto getRegUpdateDBDto()
	{
		ActivityLogDetailDto activityLogDetailDto=new ActivityLogDetailDto();
		activityLogDetailDto.setActivityType(ActivityType.PROFILE_ADDED_COMMENT);
		activityLogDetailDto.setCreatedBy("cd.comp.system");
		activityLogDetailDto.setCreatedOn(Timestamp.valueOf("2020-11-01 13:05:06"));
		ProfileActivityLogDto profileActivityLogDto = new ProfileActivityLogDto();
		profileActivityLogDto.setAccountId(543775);
		profileActivityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		List<ProfileActivityLogDto> activityLog= new ArrayList<>();
		activityLog.add(profileActivityLogDto);
		RegUpdateDBDto regUpdateDBDto= new RegUpdateDBDto();
		regUpdateDBDto.setActivityLog(activityLog);
		return regUpdateDBDto;
		
	}
	public ActivityLogs getActivityLogs()
	{
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
		ActivityLogDataWrapper data= new ActivityLogDataWrapper();
		data.setActivityType("PROFILE ADD_COMMENT");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/11/2020 13:05:06");
		activityLogData.add(data);
		ActivityLogs  activityLogs= new ActivityLogs();
		activityLogs.setActivityLogData(activityLogData);
		return activityLogs;
		
	}
	@Test
	public void testTransform() {
		ActivityLogs expectedResult=getActivityLogs();
		ActivityLogs actualResult=regActivityLogTransformer.transform(getRegUpdateDBDto());
assertEquals(expectedResult.getActivityLogData().get(0).getActivityType(),actualResult.getActivityLogData().get(0).getActivityType());
	}

}
