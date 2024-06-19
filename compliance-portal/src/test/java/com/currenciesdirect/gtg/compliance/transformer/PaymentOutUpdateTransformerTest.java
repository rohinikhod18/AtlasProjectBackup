package com.currenciesdirect.gtg.compliance.transformer;





import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;


public class PaymentOutUpdateTransformerTest {

	@InjectMocks
	PaymentOutUpdateTransformer paymentOutUpdateTransformer;
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

	}
	public PaymentOutUpdateRequest getPaymentOutUpdateRequest()
	{
		StatusReasonUpdateRequest statusReasonUpdateRequest= new StatusReasonUpdateRequest();
		statusReasonUpdateRequest.setName("Alert from 3rd party");
		statusReasonUpdateRequest.setPreValue(false);
		statusReasonUpdateRequest.setUpdatedValue(false);
		StatusReasonUpdateRequest[] statusReasons= {statusReasonUpdateRequest};
		WatchlistUpdateRequest watchlistUpdateRequest= new WatchlistUpdateRequest();
		watchlistUpdateRequest.setName("Account Info Updated");
		watchlistUpdateRequest.setPreValue(false);
		watchlistUpdateRequest.setUpdatedValue(false);
		WatchlistUpdateRequest[] watchlist= {watchlistUpdateRequest};
			PaymentOutUpdateRequest paymentOutUpdateRequest = new PaymentOutUpdateRequest();
			paymentOutUpdateRequest.setAccountId(543775);
			paymentOutUpdateRequest.setAccountSfId("0010O00001tGABfQAO");
			paymentOutUpdateRequest.setBeneCheckStatus("PASS");
			paymentOutUpdateRequest.setBeneCheckStatus("8,877.39");
			paymentOutUpdateRequest.setBeneficiaryName("KONTRI LTD");
			paymentOutUpdateRequest.setBuyCurrency("EUR");
			paymentOutUpdateRequest.setClientNumber("0202000003119142");
			paymentOutUpdateRequest.setComment("abc");
			paymentOutUpdateRequest.setContactId(672303);
			paymentOutUpdateRequest.setContactSfId("0030O000025C6mrQAC");
			paymentOutUpdateRequest.setCountry("Germany (DEU)");
			paymentOutUpdateRequest.setCountryRiskLevel("(Low Risk Country)");
			paymentOutUpdateRequest.setCustType("CFX");
			paymentOutUpdateRequest.setEmail("przemyslaw.sienkiewicz@junk.co.uk");
			paymentOutUpdateRequest.setFragusterEventServiceLogId(23263770);
			paymentOutUpdateRequest.setIsOnQueue(true);
			paymentOutUpdateRequest.setLegalEntity("CDLGB");
			paymentOutUpdateRequest.setOrgCode("Currencies Direct");
			paymentOutUpdateRequest.setOverallWatchlistStatus(false);
			paymentOutUpdateRequest.setPaymentOutId(2849850);
			paymentOutUpdateRequest.setPrePaymentOutStatus("HOLD");
			paymentOutUpdateRequest.setTradeBeneficiayId("4046422");
			paymentOutUpdateRequest.setTradeContactId("406362");
			paymentOutUpdateRequest.setTradeContractNumber("0202000003119142-003600376");
			paymentOutUpdateRequest.setTradePaymentId("3600376");
			paymentOutUpdateRequest.setUpdatedPaymentOutStatus("HOLD");
			paymentOutUpdateRequest.setUserName("cd.comp.system");
			paymentOutUpdateRequest.setUserResourceId(778933);
			paymentOutUpdateRequest.setWatchlist(watchlist);
			paymentOutUpdateRequest.setStatusReasons(statusReasons);
		return paymentOutUpdateRequest;
	}
	public 	PaymentOutUpdateDBDto getPaymentOutUpdateDBDto()
	{
		PaymentOutUpdateRequest request=getPaymentOutUpdateRequest();
		PaymentOutUpdateDBDto requestHandler=new PaymentOutUpdateDBDto();
		requestHandler.setAccountId(request.getAccountId());
		requestHandler.setContactId(request.getContactId());
		requestHandler.setAccountSfId(request.getAccountSfId());
		requestHandler.setContactSfId(request.getContactSfId());
		requestHandler.setPaymentOutId(request.getPaymentOutId());
		requestHandler.setOrgCode(request.getOrgCode());
		requestHandler.setComment(request.getComment());
		requestHandler.setCreatedBy("cd.comp.system");
		requestHandler.setOverallPaymentOutWatchlistStatus(Boolean.TRUE.equals(request.getOverallWatchlistStatus()) ? ServiceStatusEnum.PASS : ServiceStatusEnum.FAIL);
		requestHandler.setAddWatchlist(new ArrayList<>());
		requestHandler.setDeletedWatchlist(new ArrayList<>());
		requestHandler.setPreviousReason(new ArrayList<>());
		requestHandler.setAddReasons(new ArrayList<>());
		requestHandler.setDeletedReasons(new ArrayList<>());
		requestHandler.setActivityLogs(new ArrayList<>());
		requestHandler.setTradeBeneficiayId(request.getTradeBeneficiayId());
		requestHandler.setTradeContactid(request.getTradeContactId());
		requestHandler.setTradeContractnumber(request.getTradeContractNumber());
		requestHandler.setTradePaymentid(request.getTradePaymentId());
		requestHandler.setWatchlist(request.getWatchlist());
		requestHandler.setCustType(request.getCustType());
		requestHandler.setIsPaymentOnQueue(request.getIsOnQueue());
		requestHandler.setIsRequestFromQueue(request.getIsOnQueue());
		requestHandler.setFragusterEventServiceLogId(request.getFragusterEventServiceLogId());
		requestHandler.setUserResourceId(request.getUserResourceId());
		return requestHandler;	
	}

	@Test
	public void testTransform() {
		PaymentOutUpdateDBDto expectedResult= getPaymentOutUpdateDBDto();
		PaymentOutUpdateDBDto actualResult=paymentOutUpdateTransformer.transform(getPaymentOutUpdateRequest());
		assertEquals(expectedResult.getAccountId(),actualResult.getAccountId());;
	}

}