package com.currenciesdirect.gtg.compliance.transformer;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;

public class PaymentInActivityLogTransformerTest {

@InjectMocks
PaymentInActivityLogTransformer paymentInActivityLogTransformer;
@Before
public void setUp() {

	MockitoAnnotations.initMocks(this);

}
public PaymentInUpdateDBDto getActivityLogs() 
{
	PaymentInActivityLogDetailDto paymentInActivityLogDetailDto=new PaymentInActivityLogDetailDto();
	paymentInActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_ADD_COMMENT);
	paymentInActivityLogDetailDto.setCreatedBy("cd.comp.system");
	paymentInActivityLogDetailDto.setCreatedOn(Timestamp.valueOf("2020-11-01 13:05:06"));
	paymentInActivityLogDetailDto.setActivityId(76234);
	
	 List<PaymentInActivityLogDto> activityLog=new ArrayList<>();
	 PaymentInActivityLogDto paymentInActivityLogDto= new PaymentInActivityLogDto();
	 paymentInActivityLogDto.setAccountId(543775);
	 paymentInActivityLogDto.setActivityLogDetailDto(paymentInActivityLogDetailDto);
	 activityLog.add(paymentInActivityLogDto);
	 PaymentInUpdateDBDto paymentInUpdateDBDto = new PaymentInUpdateDBDto();
		paymentInUpdateDBDto.setActivityLogs(activityLog);
		return paymentInUpdateDBDto;
}
public ActivityLogs setActivityLogs()
{
	ActivityLogs activityLogs= new ActivityLogs();
	ActivityLogDataWrapper activityLogData= new ActivityLogDataWrapper();
	activityLogData.setActivityType("PAYMENT_IN ADD_COMMENT");
	activityLogData.setCreatedBy("cd.comp.system");
	activityLogData.setCreatedOn("01/11/2020 13:05:06");
	List<ActivityLogDataWrapper> data=new ArrayList<>();
	data.add(activityLogData);
	activityLogs.setActivityLogData(data);
	return activityLogs;
	
	
}

	@Test
	public void testTransform() {
		ActivityLogs expectedData=setActivityLogs();
		ActivityLogs actualResult=	paymentInActivityLogTransformer.transform(getActivityLogs());
	assertEquals(expectedData.getActivityLogData().get(0).getActivityType(),actualResult.getActivityLogData().get(0).getActivityType());
	}

}