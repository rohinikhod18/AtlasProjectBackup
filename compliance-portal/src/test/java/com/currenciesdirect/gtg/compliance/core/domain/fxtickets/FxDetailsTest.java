package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import static org.junit.Assert.assertEquals;
import java.sql.Timestamp;
import org.junit.Test;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletDetails;

@SuppressWarnings("squid:S1192")
public class FxDetailsTest {

	@Test
	public void testGetId() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setId(1780281);
		assertEquals(1780281, fxDetails.getId(), 0);
	}

	@Test
	public void testSetId() {
		FxDetails fxDetails1 = new FxDetails();
		fxDetails1.setId(1780281);
		assertEquals(1780281, fxDetails1.getId(), 0);
	}

	@Test
	public void testGetTradeDetails() {
		TradeDetails tradeDetails = new TradeDetails();
		tradeDetails.setSellingCurrency("GBP");
		tradeDetails.setBuyingCurrency("USD");
		FxDetails fxDetails = new FxDetails();
		fxDetails.setTradeDetails(tradeDetails);
		assertEquals(tradeDetails.getSellingCurrency(), fxDetails.getTradeDetails().getSellingCurrency());
	}

	@Test
	public void testSetTradeDetails() {
		TradeDetails tradeDetails1 = new TradeDetails();
		tradeDetails1.setSellingCurrency("GBP");
		tradeDetails1.setBuyingCurrency("USD");
		FxDetails fxDetails = new FxDetails();
		fxDetails.setTradeDetails(tradeDetails1);
		assertEquals(tradeDetails1.getSellingCurrency(), fxDetails.getTradeDetails().getSellingCurrency());
	}

	@Test
	public void testGetWallet() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletNumber("C-001002088-GBP-TRAN");
		walletDetails.setWalletCurrency("GBP");
		FxDetails fxDetails = new FxDetails();
		fxDetails.setWallet(walletDetails);
		assertEquals(walletDetails.getWalletNumber(), fxDetails.getWallet().getWalletNumber());
	}

	@Test
	public void testSetWallet() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setWalletNumber("C-001002088-GBP-TRAN");
		walletDetails1.setWalletCurrency("GBP");
		FxDetails fxDetails1 = new FxDetails();
		fxDetails1.setWallet(walletDetails1);
		assertEquals(walletDetails1.getWalletNumber(), fxDetails1.getWallet().getWalletNumber());
	}

	@Test
	public void testGetCreatedBy() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setCreatedBy(1);
		assertEquals(1, fxDetails.getCreatedBy(), 0);
	}

	@Test
	public void testSetCreatedBy() {
		FxDetails fxDetails1 = new FxDetails();
		fxDetails1.setCreatedBy(1);
		assertEquals(1, fxDetails1.getCreatedBy(), 0);
	}

	@Test
	public void testGetCreatedOn() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setCreatedOn(Timestamp.valueOf("2020-11-14 13:05:06"));
		assertEquals(fxDetails.getCreatedOn(), fxDetails.getCreatedOn());
	}

	@Test
	public void testSetCreatedOn() {
		FxDetails fxDetails1 = new FxDetails();
		fxDetails1.setCreatedOn(Timestamp.valueOf("2020-11-14 13:05:06"));
		assertEquals( fxDetails1.getCreatedOn(), fxDetails1.getCreatedOn());
	}

	@Test
	public void testGetUpdatedBy() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setUpdatedBy(1);
		assertEquals(1, fxDetails.getUpdatedBy(), 0);
	}

	@Test
	public void testSetUpdatedBy() {
		FxDetails fxDetails1 = new FxDetails();
		fxDetails1.setUpdatedBy(1);
		assertEquals(1, fxDetails1.getUpdatedBy(), 0);
	}

	@Test
	public void testGetUpdatedOn() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setUpdatedOn(Timestamp.valueOf("2020-11-5 13:05:06"));
		assertEquals( fxDetails.getUpdatedOn(), fxDetails.getUpdatedOn());
	}

	@Test
	public void testSetUpdatedOn() {
		FxDetails fxDetails1 = new FxDetails();
		fxDetails1.setUpdatedOn(Timestamp.valueOf("2020-11-5 13:05:06"));
		assertEquals(fxDetails1.getUpdatedOn(), fxDetails1.getUpdatedOn());

	}

	@Test
	public void testGetSourceOfFunds() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setSourceOfFunds("-");
		assertEquals("-", fxDetails.getSourceOfFunds());

	}

	@Test
	public void testSetSourceOfFunds() {
		FxDetails fxDetails = new FxDetails();
		fxDetails.setSourceOfFunds("-");
		assertEquals("-", fxDetails.getSourceOfFunds());
	}

}