package com.currenciesdirect.gtg.compliance.transformer;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

public class PaymentInUpdateTransformerTest {
@InjectMocks
PaymentInUpdateTransformer paymentInUpdateTransformer;

@Before
public void setUp() {

	MockitoAnnotations.initMocks(this);

}
private static final String Name = "cd.comp.system";
public PaymentInUpdateDBDto getPaymentInUpdateDBDto()
{
	WatchlistUpdateRequest watchlistData= new WatchlistUpdateRequest();
	watchlistData.setName("Account Info Updated");
	watchlistData.setPreValue(false);
	watchlistData.setUpdatedValue(false);
	WatchlistUpdateRequest[] watchlistDataSet= {watchlistData};
	PaymentInUpdateDBDto paymentInUpdateDBDto = new PaymentInUpdateDBDto();
	paymentInUpdateDBDto.setAccountId(49767);
	paymentInUpdateDBDto.setContactId(50630);
	paymentInUpdateDBDto.setAccountSfId("0016E000020plr0TAB");
	paymentInUpdateDBDto.setContactSfId("0037G23747c7pFlDTJ");
	paymentInUpdateDBDto.setPaymentInId(281299);
	paymentInUpdateDBDto.setOrgCode("Currencies Direct");
	paymentInUpdateDBDto.setComment("abc");
	paymentInUpdateDBDto.setCreatedBy(Name);
	paymentInUpdateDBDto.setOverallPaymentInWatchlistStatus( ServiceStatusEnum.PASS );
	paymentInUpdateDBDto.setAddWatchlist(new ArrayList<>());
	paymentInUpdateDBDto.setDeletedWatchlist(new ArrayList<>());
	paymentInUpdateDBDto.setPreviousReason(new ArrayList<>());
	paymentInUpdateDBDto.setAddReasons(new ArrayList<>());
	paymentInUpdateDBDto.setDeletedReasons(new ArrayList<>());
	paymentInUpdateDBDto.setActivityLogs(new ArrayList<>());
	paymentInUpdateDBDto.setTradeContactid("66365382");
	paymentInUpdateDBDto.setTradeContractnumber("0201000010009491-003796441");
	paymentInUpdateDBDto.setTradePaymentid("281301");
	paymentInUpdateDBDto.setWatchlist(watchlistDataSet);
	paymentInUpdateDBDto.setCustType("PFX");
	paymentInUpdateDBDto.setIsPaymentOnQueue(true);
	paymentInUpdateDBDto.setIsRequestFromQueue(true);
	paymentInUpdateDBDto.setFragusterEventServiceLogId(977257);
	paymentInUpdateDBDto.setUserResourceId(666554);
	return paymentInUpdateDBDto;
}
public PaymentInUpdateRequest getPaymentInUpdateRequest()
{
	StatusReasonUpdateRequest reasons= new StatusReasonUpdateRequest();
	reasons.setName("Alert from 3rd party");
	reasons.setPreValue(false);
	reasons.setUpdatedValue(false);
	
	StatusReasonUpdateRequest[] statusReasons= {reasons};
	
	WatchlistUpdateRequest watchlistData= new WatchlistUpdateRequest();
	watchlistData.setName("Account Info Updated");
	watchlistData.setPreValue(false);
	watchlistData.setUpdatedValue(false);
	WatchlistUpdateRequest[] watchlistDataSet= {watchlistData};
	
	PaymentInUpdateRequest paymentInUpdateRequest= new PaymentInUpdateRequest();
	paymentInUpdateRequest.setAccountId(49767);
	paymentInUpdateRequest.setAccountSfId("0016E000020plr0TAB");
	paymentInUpdateRequest.setClientNumber("0016E000020plr0TAB");
	paymentInUpdateRequest.setComment("abc");
	paymentInUpdateRequest.setContactId(50630);
	paymentInUpdateRequest.setContactName("John2 main-deb Cena");
	paymentInUpdateRequest.setContactSfId("0037G23747c7pFlDTJ");
	paymentInUpdateRequest.setCustType("PFX");
	paymentInUpdateRequest.setDebtorAmount("700");
	paymentInUpdateRequest.setEmail("john2cena@gmail.ca");
	paymentInUpdateRequest.setFragusterEventServiceLogId(977257);
	paymentInUpdateRequest.setIsOnQueue(true);
	paymentInUpdateRequest.setLegalEntity("CDLCA");
	paymentInUpdateRequest.setPaymentMethod("BACS/CHAPS/TT");
	paymentInUpdateRequest.setOrgCode("Currencies Direct");
	paymentInUpdateRequest.setOverallWatchlistStatus(false);
	paymentInUpdateRequest.setPaymentinId(281299);
	paymentInUpdateRequest.setPrePaymentInStatus("HOLD");
	paymentInUpdateRequest.setSellCurrency("GBP");
	paymentInUpdateRequest.setTradeContactId("66365382");
	paymentInUpdateRequest.setTradeContractNumber("0201000010009491-003796441");
	paymentInUpdateRequest.setTradePaymentId("281301");
	paymentInUpdateRequest.setUpdatedPaymentInStatus("HOLD");
	paymentInUpdateRequest.setUserName(Name);
	paymentInUpdateRequest.setUserResourceId(666554);
	paymentInUpdateRequest.setWatchlist(watchlistDataSet);
	paymentInUpdateRequest.setStatusReasons(statusReasons);
	return paymentInUpdateRequest;
}
	@Test
	public void testTransform() {
		PaymentInUpdateDBDto expectedResult=getPaymentInUpdateDBDto();
		PaymentInUpdateDBDto actualResult=paymentInUpdateTransformer.transform(getPaymentInUpdateRequest());
		assertEquals(expectedResult.getAccountId(),actualResult.getAccountId());
	}

}