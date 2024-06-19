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
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;

public class PaymentOutActivityLogTransformerTest {
	
	@InjectMocks
	PaymentOutActivityLogTransformer paymentOutActivityLogTransformer;
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

	}
	
	public ActivityLogs getActivityLogs()
	{
		ActivityLogs activityLogs=new ActivityLogs();
		ActivityLogDataWrapper activityLogDataWrapper= new ActivityLogDataWrapper();
		activityLogDataWrapper.setActivityType("PAYMENT_OUT ADD_COMMENT");
		activityLogDataWrapper.setCreatedBy("cd.comp.system");
		activityLogDataWrapper.setCreatedOn("01/11/2020 13:05:06");
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
		activityLogData.add(activityLogDataWrapper);
		activityLogs.setActivityLogData(activityLogData);
		return activityLogs;	
	}
	
	public PaymentOutUpdateDBDto getPaymentOutUpdateDBDto()
	{
		PaymentOutUpdateDBDto paymentOutUpdateDBDto= new PaymentOutUpdateDBDto();
		PaymentOutActivityLogDetailDto activityLogDetailDto= new PaymentOutActivityLogDetailDto();
		activityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_ADD_COMMENT);
		activityLogDetailDto.setCreatedBy("cd.comp.system");
		activityLogDetailDto.setCreatedOn(Timestamp.valueOf("2020-11-01 13:05:06"));
		PaymentOutActivityLogDto PaymentOutActivityLogDto= new PaymentOutActivityLogDto();
		PaymentOutActivityLogDto.setAccountId(543775);
		PaymentOutActivityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		List<PaymentOutActivityLogDto> activityLog= new ArrayList<>();
		activityLog.add(PaymentOutActivityLogDto);
		paymentOutUpdateDBDto.setActivityLogs(activityLog);
		return paymentOutUpdateDBDto;
	}
	@Test
	public void testTransform() {
		
		ActivityLogs expectedResult=getActivityLogs();
		ActivityLogs actualResult=paymentOutActivityLogTransformer.transform(getPaymentOutUpdateDBDto());
		assertEquals(expectedResult.getActivityLogData().get(0).getActivityType(),actualResult.getActivityLogData().get(0).getActivityType());
	}

}
