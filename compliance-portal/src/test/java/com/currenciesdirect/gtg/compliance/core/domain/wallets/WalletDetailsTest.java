package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import static org.junit.Assert.*;

import org.junit.Test;

public class WalletDetailsTest {

	@Test
	public void testGetWalletAvailableBalance() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletAvailableBalance("4000");
		assertTrue(walletDetails.getWalletAvailableBalance().equalsIgnoreCase("4000"));
	}

	@Test
	public void testSetWalletAvailableBalance() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setWalletAvailableBalance("4000");
		assertTrue(walletDetails1.getWalletAvailableBalance().equalsIgnoreCase("4000"));
	}

	@Test
	public void testGetWalletTotalBalance() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletTotalBalance("20000");
		assertTrue(walletDetails.getWalletTotalBalance().equalsIgnoreCase("20000"));
	}

	@Test
	public void testSetWalletTotalBalance() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setWalletTotalBalance("20000");
		assertTrue(walletDetails1.getWalletTotalBalance().equalsIgnoreCase("20000"));
	}

	@Test
	public void testGetWalletNumber() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletNumber("50");
		assertTrue(walletDetails.getWalletNumber().equalsIgnoreCase("50"));
	}

	@Test
	public void testSetWalletNumber() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setWalletNumber("50");
		assertTrue(walletDetails1.getWalletNumber().equalsIgnoreCase("50"));
	}

	@Test
	public void testGetWalletCurrency() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletCurrency("GBP");
		assertTrue(walletDetails.getWalletCurrency().equalsIgnoreCase("GBP"));
	}

	@Test
	public void testSetWalletCurrency() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setWalletCurrency("GBP");
		assertTrue(walletDetails1.getWalletCurrency().equalsIgnoreCase("GBP"));
	}
	
	@Test
	public void testGetTotalBalance() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setTotalBalance(3000);
		assertTrue(walletDetails.getTotalBalance()==3000);
	}
	
	@Test
	public void testSetTotalBalance() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setTotalBalance(3000);
		assertTrue(walletDetails1.getTotalBalance()==3000);
	}


	@Test
	public void testGetAvailableBalance() {
		WalletDetails walletDetails = new WalletDetails();
		walletDetails.setWalletAvailableBalance("200");
		assertTrue(walletDetails.getWalletAvailableBalance().equalsIgnoreCase("200"));
	}
	
	@Test
	public void testSetAvailableBalance() {
		WalletDetails walletDetails1 = new WalletDetails();
		walletDetails1.setWalletAvailableBalance("200");
		assertTrue(walletDetails1.getWalletAvailableBalance().equalsIgnoreCase("200"));
	}
}