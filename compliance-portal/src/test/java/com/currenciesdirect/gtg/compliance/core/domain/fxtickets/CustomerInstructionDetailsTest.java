package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class CustomerInstructionDetailsTest {

	@Test
	public void testGetCustomerInstructionId() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setCustomerInstructionId((long) 1234555);
		assertEquals((long)1234555,customerInstructionDetails.getCustomerInstructionId(),0);
	}

	@Test
	public void testSetCustomerInstructionId() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setCustomerInstructionId((long) 1234555);
		assertEquals((long)1234555,customerInstructionDetails1.getCustomerInstructionId(),0);

	}

	@Test
	public void testGetOrganizationName() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setOrganizationName("Currencies Direct");
		assertEquals("Currencies Direct",customerInstructionDetails.getOrganizationName());
	}

	@Test
	public void testSetOrganizationName() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setOrganizationName("Currencies Direct");
		assertEquals("Currencies Direct",customerInstructionDetails1.getOrganizationName());
	}

	@Test
	public void testGetAccountNumber() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setAccountNumber("0202000002600209");
		assertEquals("0202000002600209",customerInstructionDetails.getAccountNumber());
	}

	@Test
	public void testSetAccountNumber() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setAccountNumber("0202000002600209");
		assertEquals("0202000002600209",customerInstructionDetails1.getAccountNumber());
	}

	@Test
	public void testGetReference() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setReference("-");
		assertEquals("-",customerInstructionDetails.getReference());
	
	}

	@Test
	public void testSetReference() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setReference("-");
		assertEquals("-",customerInstructionDetails1.getReference());
	}

	@Test
	public void testGetDealType() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setDealType("SPOT");
		assertEquals("SPOT",customerInstructionDetails.getDealType());
	}

	@Test
	public void testSetDealType() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setDealType("SPOT");
		assertEquals("SPOT",customerInstructionDetails1.getDealType());
		
	}

	@Test
	public void testGetOrganizationCode() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setOrganizationCode("TorFx");
		assertEquals("TorFx",customerInstructionDetails.getOrganizationCode());
	}

	@Test
	public void testSetOrganizationCode() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setOrganizationCode("TorFx");
		assertEquals("TorFx",customerInstructionDetails1.getOrganizationCode());
	}

	@Test
	public void testGetCustomerInstructionNumber() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setCustomerInstructionNumber("0202000002826275-003831494");
		assertEquals("0202000002826275-003831494",customerInstructionDetails1.getCustomerInstructionNumber());
	}

	@Test
	public void testSetCustomerInstructionNumber() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setCustomerInstructionNumber("0202000002826275-003831494");
		assertEquals("0202000002826275-003831494",customerInstructionDetails.getCustomerInstructionNumber());
	}

	@Test
	public void testGetAccountName() {
		CustomerInstructionDetails customerInstructionDetails= new CustomerInstructionDetails();
		customerInstructionDetails.setAccountName("Rahul Dev");
		assertEquals(customerInstructionDetails.getAccountName(),customerInstructionDetails.getAccountName());
	}

	@Test
	public void testSetAccountName() {
		CustomerInstructionDetails customerInstructionDetails1= new CustomerInstructionDetails();
		customerInstructionDetails1.setAccountName("Rahul Dev");
		assertEquals(customerInstructionDetails1.getAccountName(),customerInstructionDetails1.getAccountName());
	}

	
}