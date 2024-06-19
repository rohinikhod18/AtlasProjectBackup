package com.currenciesdirect.gtg.compliance.core.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaymentInSummaryTest {

	@Test
	public void testGetThirdPartyFlag() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setThirdPartyFlag(true);
		assertEquals(true,paymentInSummary.getThirdPartyFlag());
	}

	@Test
	public void testSetThirdPartyFlag() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setThirdPartyFlag(true);
		assertEquals(true,paymentInSummary1.getThirdPartyFlag());
	}

	@Test
	public void testGetCountryOfFund() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setCountryOfFund("AUS");
		assertEquals("AUS",paymentInSummary.getCountryOfFund());
	}

	@Test
	public void testSetCountryOfFund() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setCountryOfFund("AUS");
		assertEquals("AUS",paymentInSummary1.getCountryOfFund());
	}

	@Test
	public void testGetSellCurrency() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setSellCurrency("GBP");
		assertEquals("GBP",paymentInSummary.getSellCurrency());
	}

	@Test
	public void testGetDebtorAccountNumber() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setDebtorAccountNumber("NATAAU3303M6666666666");
		assertEquals("NATAAU3303M6666666666",paymentInSummary.getDebtorAccountNumber());
	}

	@Test
	public void testGetDebtorName() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setDebtorName("Abrian K");
		assertEquals(paymentInSummary.getDebtorName(),paymentInSummary.getDebtorName());
	}

	@Test
	public void testGetIsDeleted() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setIsDeleted(false);
		assertEquals(false,paymentInSummary.getIsDeleted());
	}

	@Test
	public void testGetDeletedDate() {
		PaymentInSummary paymentInSummary=new PaymentInSummary();
		paymentInSummary.setDeletedDate("17/09/2020 05:46:30");
		assertEquals(paymentInSummary.getDeletedDate(),paymentInSummary.getDeletedDate());
	}

	@Test
	public void testSetSellCurrency() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setSellCurrency("GBP");
		assertEquals("GBP",paymentInSummary1.getSellCurrency());
	}

	@Test
	public void testSetDebtorAccountNumber() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setDebtorAccountNumber("NATAAU3303M6666666666");
		assertEquals("NATAAU3303M6666666666",paymentInSummary1.getDebtorAccountNumber());
	}

	@Test
	public void testSetDebtorName() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setDebtorName("Abrian K");
		assertEquals(paymentInSummary1.getDebtorName(),paymentInSummary1.getDebtorName());
	}

	@Test
	public void testSetIsDeleted() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setIsDeleted(false);
		assertEquals(false,paymentInSummary1.getIsDeleted());
	}

	@Test
	public void testSetDeletedDate() {
		PaymentInSummary paymentInSummary1=new PaymentInSummary();
		paymentInSummary1.setDeletedDate("17/09/2020 05:46:30");
		assertEquals(paymentInSummary1.getDeletedDate(),paymentInSummary1.getDeletedDate());
	}

}