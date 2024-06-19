package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonContact;

@SuppressWarnings("squid:S1192")
public class WalletRequestTest {

	@Test
	public void testGetSourceApplication() {
		WalletRequest walletRequest= new WalletRequest();
		walletRequest.setSourceApplication("ATLAS");
	    assertTrue(walletRequest.getSourceApplication().equalsIgnoreCase("ATLAS"));
	}

	@Test
	public void testSetSourceApplication() {
		WalletRequest walletRequest1= new WalletRequest();
		walletRequest1.setSourceApplication("ATLAS");
	    assertTrue(walletRequest1.getSourceApplication().equalsIgnoreCase("ATLAS"));
	}

	@Test
	public void testGetOsrId() {
		WalletRequest walletRequest= new WalletRequest();
		walletRequest.setOsrId("26b4c045-5b47-42e4-bb2e-7173e30641ea");
		assertTrue(walletRequest.getOsrId().equalsIgnoreCase("26b4c045-5b47-42e4-bb2e-7173e30641ea"));
	}
	
	@Test
	public void testSetOsrId() {
		WalletRequest walletRequest1= new WalletRequest();
		walletRequest1.setOsrId("26b4c045-5b47-42e4-bb2e-7173e30641ea");
		assertTrue(walletRequest1.getOsrId().equalsIgnoreCase("26b4c045-5b47-42e4-bb2e-7173e30641ea"));
	}

	@Test
	public void testGetOrgCode() {
		WalletRequest walletRequest= new WalletRequest();
		walletRequest.setOrgCode("TorFXOz");
		assertTrue(walletRequest.getOrgCode().equalsIgnoreCase("TorFXOz"));
	}

	@Test
	public void testSetOrgCode() {
		WalletRequest walletRequest1= new WalletRequest();
		walletRequest1.setOrgCode("TorFXOz");
		assertTrue(walletRequest1.getOrgCode().equalsIgnoreCase("TorFXOz"));
	}

	@Test
	public void testGetAccountNumber() {
		WalletRequest walletRequest= new WalletRequest();
		walletRequest.setAccountNumber("0401000010027890");
		assertTrue(walletRequest.getAccountNumber().equalsIgnoreCase("0401000010027890"));
	}

	@Test
	public void testSetAccountNumber() {
		WalletRequest walletRequest1= new WalletRequest();
		walletRequest1.setAccountNumber("0401000010027890");
		assertTrue(walletRequest1.getAccountNumber().equalsIgnoreCase("0401000010027890"));
	}

}