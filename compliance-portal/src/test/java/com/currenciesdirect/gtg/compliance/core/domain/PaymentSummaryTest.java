package com.currenciesdirect.gtg.compliance.core.domain;

import static org.junit.Assert.*;


import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class PaymentSummaryTest {

	@Test
	public void testGetFirstTradeDate() {
		PaymentSummary paymentSummary= new PaymentSummary();
		paymentSummary.setFirstTradeDate("17/09/2020 05:46:30");
		assertEquals("17/09/2020 05:46:30",paymentSummary.getFirstTradeDate());
	}

	@Test
	public void testSetFirstTradeDate() {
		PaymentSummary paymentSummary1= new PaymentSummary();
		paymentSummary1.setFirstTradeDate("17/09/2020 05:46:30");
		assertEquals("17/09/2020 05:46:30",paymentSummary1.getFirstTradeDate());
	}

	@Test
	public void testGetLastTradeDate() {
		PaymentSummary paymentSummary= new PaymentSummary();
		paymentSummary.setLastTradeDate("17/09/2020 05:46:30");
		assertEquals("17/09/2020 05:46:30",paymentSummary.getLastTradeDate());

	}

	@Test
	public void testSetLastTradeDate() {
		PaymentSummary paymentSummary1= new PaymentSummary();
		paymentSummary1.setLastTradeDate("17/09/2020 05:46:30");
		assertEquals("17/09/2020 05:46:30",paymentSummary1.getLastTradeDate());
	}

	@Test
	public void testGetNoOfTrades() {
		PaymentSummary paymentSummary= new PaymentSummary();
		paymentSummary.setNoOfTrades(2);
		assertEquals(2,paymentSummary.getNoOfTrades(),0);
	}

	@Test
	public void testSetNoOfTrades() {
		PaymentSummary paymentSummary1= new PaymentSummary();
		paymentSummary1.setNoOfTrades(2);
		assertEquals(2,paymentSummary1.getNoOfTrades(),0);
	}

	@Test
	public void testGetTotalSaleAmount() {
		PaymentSummary paymentSummary= new PaymentSummary();
		paymentSummary.setTotalSaleAmount(0.0);
		assertEquals(0.0,paymentSummary.getTotalSaleAmount(),0);
	}

	@Test
	public void testSetTotalSaleAmount() {
		PaymentSummary paymentSummary1= new PaymentSummary();
		paymentSummary1.setTotalSaleAmount(0.0);
		assertEquals(0.0,paymentSummary1.getTotalSaleAmount(),0);
	}

}