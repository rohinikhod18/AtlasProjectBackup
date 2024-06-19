package com.currenciesdirect.gtg.compliance.httpport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.ITitanService;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.CustomerInstructionDetails;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketDetails;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketListResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxDetails;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketDetailList;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketDetailRequestList;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketDetailsRequest;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketFilter;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketPortalRequest;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketRequestPayload;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.MainCustomerInstruction;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.Page;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.TradeDetails;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.Wallet;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletDetails;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletRequest;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletResponse;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransaction;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransactionDetailsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransactionRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class TitanControllerTest {

	@InjectMocks
	TitanController titanController;

	@Mock
	ITitanService iTitanService;

	private static final String TORORGCODE = "TorFX";
	private static final String TITANORG = "titan.supported.organizations";
	private static final String ORGCODE = "Currencies Direct";
	private static final String TITANACCNO = "0202000002600209";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	public WalletRequest getWalletRequest() {
		WalletRequest walletRequest = new WalletRequest();
		walletRequest.setAccountNumber("0301000010020887");
		walletRequest.setOrgCode(TORORGCODE);
		return walletRequest;
	}

	public WalletResponse getwalletResponse() {
		WalletResponse walletResponse = new WalletResponse();
		List<WalletDetails> walletList = new ArrayList<>();
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletCurrency("GBP");
		walletDetails.setWalletNumber("C-001002088-GBP-TRAN");
		walletDetails.setWalletAvailableBalance("100.00");
		walletDetails.setWalletTotalBalance("100.00");
		walletList.add(walletDetails);
		walletResponse.setWalletList(walletList);
		return walletResponse;
	}

	public void setMockForGetCustomerAllWalletDetails() {
		try {
			when(iTitanService.getCustomerAllWalletDetails(anyObject())).thenReturn(getwalletResponse());
			System.setProperty(TITANORG, TORORGCODE);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockGetCustomerAllWalletDetails() {
		try {
			System.setProperty(TITANORG, TORORGCODE);
			CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
			when(iTitanService.getCustomerAllWalletDetails(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public WalletTransactionRequest getWalletTransactionRequest() {
		WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();
		walletTransactionRequest.setWalletNumber("C-000266970-GBP-TRAN");
		walletTransactionRequest.setOrgCode(ORGCODE);
		walletTransactionRequest.setAccountNumber("0202000002669709");
		return walletTransactionRequest;
	}

	public WalletTransactionDetailsResponse getWalletTransactionDetailsResponse() {
		double amount = 2339.62;
		BigDecimal walletAmount = BigDecimal.valueOf(amount);
		WalletTransactionDetailsResponse walletTransactionDetailsResponse = new WalletTransactionDetailsResponse();
		List<WalletTransaction> walletTransactionList = new ArrayList<>();
		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setReference("Credit Received via Bank Ref:4661415");
		walletTransaction.setTransactionDate(Timestamp.valueOf("2020-11-5 13:05:06"));
		walletTransaction.setEntryType("CR");
		walletTransaction.setAmount(walletAmount);
		walletTransactionList.add(walletTransaction);
		Wallet wallet = new Wallet();
		wallet.setAvailableBalance(42791.47);
		wallet.setTotalBalance(42791.47);
		wallet.setWalletCurrency("GBP");
		walletTransactionDetailsResponse.setWalletTransactionList(walletTransactionList);
		walletTransactionDetailsResponse.setWallet(wallet);
		return walletTransactionDetailsResponse;
	}

	public void setMockDataForGetCustomerWalletTransactionDetails() {
		try {
			when(iTitanService.getCustomerWalletTransactionDetails(anyObject()))
					.thenReturn(getWalletTransactionDetailsResponse());
			System.setProperty(TITANORG, ORGCODE);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public Page getpage() {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageOffset(0);
		page.setPageSize(5);
		return page;
	}

	public FxTicketPortalRequest getFxTicketPortalRequest() {
		List<String> organization = new ArrayList<>();
		organization.add(ORGCODE);
		FxTicketFilter fxTicketFilter = new FxTicketFilter();
		fxTicketFilter.setOrganization(organization);
		FxTicketSearchCriteria fxTicketSearchCriteria = new FxTicketSearchCriteria();
		fxTicketSearchCriteria.setFxTicketFilter(fxTicketFilter);
		fxTicketSearchCriteria.setIsFilterApply(false);
		fxTicketSearchCriteria.setPage(getpage());
		FxTicketRequestPayload fxTicketRequestPayload = new FxTicketRequestPayload();
		fxTicketRequestPayload.setTitanAccountNumber(TITANACCNO);
		FxTicketPortalRequest fxTicketPortalRequest = new FxTicketPortalRequest();
		fxTicketPortalRequest.setFxTicketRequestPayload(fxTicketRequestPayload);
		fxTicketPortalRequest.setFxTicketSearchCriteria(fxTicketSearchCriteria);
		return fxTicketPortalRequest;
	}

	public FXTicketResponse getFXTicketResponse() {
		double amount = 1991.59;
		BigDecimal sellingAmount = BigDecimal.valueOf(amount);
		double balance = 1.2324;
		BigDecimal treasuryRate = BigDecimal.valueOf(balance);
		TradeDetails tradeDetails = new TradeDetails();
		tradeDetails.setBookingDate(Timestamp.valueOf("2020-11-14 13:05:06"));
		tradeDetails.setSellingAmount(sellingAmount);
		tradeDetails.setSellingCurrency("GBP");
		tradeDetails.setBuyingCurrency("USD");
		tradeDetails.setTreasuryRate(treasuryRate);
		CustomerInstructionDetails customerInstructionDetails = new CustomerInstructionDetails();
		customerInstructionDetails.setReference("-");
		customerInstructionDetails.setDealType("SPOT");
		customerInstructionDetails.setAccountNumber(TITANACCNO);
		customerInstructionDetails.setOrganizationCode(ORGCODE);
		List<FXTicketListResponse> fxTicketListResponseList = new ArrayList<>();
		FXTicketListResponse fxTicketListResponse = new FXTicketListResponse();
		fxTicketListResponse.setTradeDetails(tradeDetails);
		fxTicketListResponse.setCustomerInstructionDetails(customerInstructionDetails);
		fxTicketListResponse.setId((long) 1780281);
		fxTicketListResponseList.add(fxTicketListResponse);
		FXTicketResponse fxTicketResponse = new FXTicketResponse();
		fxTicketResponse.setFxTicketListResponseList(fxTicketListResponseList);
		return fxTicketResponse;
	}

	public void setMockDataForGetCustomerFXTicketList() {
		try {
			when(iTitanService.getCustomerFXTicketList(anyObject())).thenReturn(getFXTicketResponse());
			System.setProperty(TITANORG, ORGCODE);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetCustomerFXTicketList() {
		try {
			CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
			when(iTitanService.getCustomerFXTicketList(anyObject())).thenThrow(e);
			System.setProperty(TITANORG, ORGCODE);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public FxTicketDetailsRequest getFxTicketDetailsRequest() {
		FxTicketRequestPayload fxTicketPayload = new FxTicketRequestPayload();
		fxTicketPayload.setTitanAccountNumber(TITANACCNO);
		fxTicketPayload.setOrganizationCode(ORGCODE);
		List<FxTicketDetailRequestList> fxTicketDetailRequestList = new ArrayList<>();
		FxTicketDetailRequestList fxTicketDetailRequest = new FxTicketDetailRequestList();
		fxTicketDetailRequest.setFxTicketPayload(fxTicketPayload);
		fxTicketDetailRequestList.add(fxTicketDetailRequest);
		FxTicketDetailsRequest fxTicketDetailsRequest = new FxTicketDetailsRequest();
		fxTicketDetailsRequest.setFxTicketDetailRequestList(fxTicketDetailRequestList);
		return fxTicketDetailsRequest;
	}

	public FXTicketDetails getFXTicketDetails() {
		double amount = 0.95690000;
		BigDecimal rate = BigDecimal.valueOf(amount);
		double balance = 0.91470000;
		BigDecimal agreedRate = BigDecimal.valueOf(balance);
		double sell = 788.00;
		BigDecimal sellingAmount = BigDecimal.valueOf(sell);
		double buy = 720.78;
		BigDecimal buyingAmount = BigDecimal.valueOf(buy);
		TradeDetails tradeDetail = new TradeDetails();
		tradeDetail.setBookingDate(Timestamp.valueOf("2020-09-10 13:27:18.0"));
		tradeDetail.setValueDate(Timestamp.valueOf("2020-09-11 13:27:18.0"));
		tradeDetail.setLiveRate(rate);
		tradeDetail.setAgreedRate(agreedRate);
		tradeDetail.setBuyingCurrency("CAD");
		tradeDetail.setSellingCurrency("AUD");
		tradeDetail.setBuyingAmount(buyingAmount);
		tradeDetail.setSellingAmount(sellingAmount);
		FxDetails fxDetails = new FxDetails();
		fxDetails.setTradeDetails(tradeDetail);
		MainCustomerInstruction mainCustomerInstruction = new MainCustomerInstruction();
		mainCustomerInstruction.setFxDetails(fxDetails);
		FXTicketDetails fxTicketDetailsResponse = new FXTicketDetails();
		List<FxTicketDetailList> fxTicketDetailList = new ArrayList<>();
		FxTicketDetailList fxTicketDetail = new FxTicketDetailList();
		fxTicketDetail.setMainCustomerInstruction(mainCustomerInstruction);
		fxTicketDetailList.add(fxTicketDetail);
		fxTicketDetailsResponse.setFxTicketDetailList(fxTicketDetailList);

		return fxTicketDetailsResponse;
	}

	public void setMockForGetCustomerFXTickeDetails() {
		try {
			when(iTitanService.getCustomerFXTicketDetails(anyObject())).thenReturn(getFXTicketDetails());
			System.setProperty(TITANORG, ORGCODE);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForGetCustomerFXTickeDetails() {
		try {
			CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
			when(iTitanService.getCustomerFXTicketDetails(anyObject())).thenThrow(e);
			System.setProperty(TITANORG, ORGCODE);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetCustomerAllWalletDetails() {
		setMockForGetCustomerAllWalletDetails();
		WalletResponse expectedResult = getwalletResponse();
		WalletResponse actualResult = titanController.getCustomerAllWalletDetails(getWalletRequest());
		assertEquals(expectedResult.getWalletList().get(0).getWalletNumber(),
				actualResult.getWalletList().get(0).getWalletNumber());
	}

	@Test
	public void testForGetCustomerAllWalletDetails() {
		setMockGetCustomerAllWalletDetails();
		WalletResponse actualResult = titanController.getCustomerAllWalletDetails(getWalletRequest());
		assertEquals(CompliancePortalErrors.DATABASE_GENERIC_ERROR.getErrorDescription(),
				actualResult.getErrorDescription());
	}

	@Test
	public void testCheckIsOrganizationAllowed() {
		System.setProperty(TITANORG, ORGCODE);
		Boolean result = titanController.checkIsOrganizationAllowed(ORGCODE);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testForCheckIsOrganizationAllowed() {
		Boolean result = titanController.checkIsOrganizationAllowed(TORORGCODE);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testGetCustomerWalletTransactionDetails() {
		setMockDataForGetCustomerWalletTransactionDetails();
		ModelAndView modelAndView = titanController.getCustomerWalletTransactionDetails(getWalletTransactionRequest());
		assertEquals("WalletDetails", modelAndView.getViewName());
	}

	@Test
	public void testRoundTwoDecimalsForWalletTransaction() {
		double amount = 2339.62;
		BigDecimal walletAmount = BigDecimal.valueOf(amount);
		List<WalletTransaction> walletTransactionList = new ArrayList<>();
		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setAmount(walletAmount);
		walletTransactionList.add(walletTransaction);
		titanController.roundTwoDecimalsForWalletTransaction(walletTransactionList);
		assertEquals("2,339.62", walletTransactionList.get(0).getWalletAmount());
	}

	@Test
	public void testRoundTwoDecimalsForWallet() {
		Wallet wallet = new Wallet();
		wallet.setAvailableBalance(42791.47);
		wallet.setTotalBalance(42791.47);
		titanController.roundTwoDecimalsForWallet(wallet);
		assertEquals("42,791.47", wallet.getWalletAvailableBalance());
	}

	@Test
	public void testGetCustomerFXTicketList() {
		setMockDataForGetCustomerFXTicketList();
		FXTicketResponse expectedResult = getFXTicketResponse();
		FXTicketResponse actualResult = titanController.getCustomerFXTicketList(getFxTicketPortalRequest());
		assertEquals(expectedResult.getFxTicketListResponseList().get(0).getId(),
				actualResult.getFxTicketListResponseList().get(0).getId());
	}

	@Test
	public void testForGetCustomerFXTicketList() {
		setMockForGetCustomerFXTicketList();
		FXTicketResponse actualResult = titanController.getCustomerFXTicketList(getFxTicketPortalRequest());
		assertEquals(CompliancePortalErrors.DATABASE_GENERIC_ERROR.getErrorDescription(),
				actualResult.getErrorDescription());
	}

	@Test
	public void testGetCustomerFXTickeDetails() {
		setMockForGetCustomerFXTickeDetails();
		ModelAndView modelAndView = titanController.getCustomerFXTickeDetails(getFxTicketDetailsRequest());
		assertEquals("fxticketdetails", modelAndView.getViewName());
	}

	@Test
	public void testForGetCustomerFXTickeDetails() {
		setMockDataForGetCustomerFXTickeDetails();
		ModelAndView modelAndView = titanController.getCustomerFXTickeDetails(getFxTicketDetailsRequest());
		FXTicketDetails fxTicketDetailsResponse = (FXTicketDetails) modelAndView.getModel()
				.get("fxTicketDetailWrapper");
		assertEquals(CompliancePortalErrors.DATABASE_GENERIC_ERROR.getErrorDescription(),
				fxTicketDetailsResponse.getResponseDescription());
	}
}