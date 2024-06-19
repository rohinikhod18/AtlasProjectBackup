package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RegistrationRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class RecheckDBServiceImplTest {

	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Mock
	AbstractDao abstractDao;

	@InjectMocks
	RecheckDBServiceImpl recheckDBServiceImpl;

	public static final String ACCOUNT_ID = "AccountID";
	public static final String CONTACT_ID = "ContactID";
	public static final String ORG_ID = "orgID";
	public static final String ORG_CODE = "orgCode";
	private static final String STATUS = "Status";
	private static final String SANCTION_FAIL = "Sanction_Fail";
	private static final String BLACKLIST_FAIL = "Blacklist_Fail";
	private static final String FRAUGSTER_FAIL = "Fraugster_Fail";
	private static final String EID_FAIL = "Eid_Fail";
	private static final String MaxBatchId = "MaxBatchId";
	private static final String BatchId = "BatchId";
	private static final String TransType = "TransType";
	private static final String TransId = "TransId";

	@Before
	public void setUp() {
		try {
			int[] count = { 1 };
			MockitoAnnotations.initMocks(this);
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.executeBatch()).thenReturn(count);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public BaseRepeatCheckRequest setBaseRepeatCheckRequest() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2020-08-04 00:00:00");
		baseRepeatCheckRequest.setDateTo("2020-08-05 00:00:00");
		baseRepeatCheckRequest.setModuleName("Registration");
		return baseRepeatCheckRequest;
	}

	public List<RegistrationRecheckFailureDetails> setRegistrationRecheckFailureDetailsPFX() {
		List<RegistrationRecheckFailureDetails> failedRegistrationList = new ArrayList<>();
		RegistrationRecheckFailureDetails recheckFailureDetail = new RegistrationRecheckFailureDetails();
		recheckFailureDetail.setAccountId(0);
		recheckFailureDetail.setContactId(50665);
		recheckFailureDetail.setCustomerType("PFX");
		failedRegistrationList.add(recheckFailureDetail);
		return failedRegistrationList;
	}

	public List<RegistrationRecheckFailureDetails> setRegistrationRecheckFailureDetailCFX() {
		List<RegistrationRecheckFailureDetails> failedRegistrationList = new ArrayList<>();
		RegistrationRecheckFailureDetails recheckFailureDetail = new RegistrationRecheckFailureDetails();
		recheckFailureDetail.setAccountId(49756);
		recheckFailureDetail.setContactId(0);
		recheckFailureDetail.setCustomerType("CFX");
		failedRegistrationList.add(recheckFailureDetail);
		return failedRegistrationList;
	}

	public void setOutputDataForPFX() {
		try {
			when(rs.getInt(ACCOUNT_ID)).thenReturn(0);
			when(rs.getInt(CONTACT_ID)).thenReturn(50665);
			when(rs.getInt(MaxBatchId)).thenReturn(4);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void setOutputDataForCFX() {
		try {
			when(rs.getInt(ACCOUNT_ID)).thenReturn(49756);
			when(rs.getInt(CONTACT_ID)).thenReturn(0);
			when(rs.getInt(MaxBatchId)).thenReturn(4);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void setMockForReprocessListRegistration() {
		try {
			when(rs.getInt("ID")).thenReturn(19308);
			when(rs.getInt(BatchId)).thenReturn(7);
			when(rs.getInt(TransType)).thenReturn(4);
			when(rs.getInt(TransId)).thenReturn(50642);
			when(rs.getInt(STATUS)).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public List<ReprocessFailedDetails> setReprocessListRegistrationOutput() {
		List<ReprocessFailedDetails> reprocessPaymentList = new ArrayList<>();
		ReprocessFailedDetails reprocessFailedPaymentDetail = new ReprocessFailedDetails();
		reprocessFailedPaymentDetail.setId(19308);
		reprocessFailedPaymentDetail.setBatchId(7);
		reprocessFailedPaymentDetail.setTransType("CONTACT");
		reprocessFailedPaymentDetail.setTransId(50642);
		reprocessFailedPaymentDetail.setStatus("FAILED");
		reprocessPaymentList.add(reprocessFailedPaymentDetail);
		return reprocessPaymentList;
	}

	public void setMockForReprocessListRegistrationAccount() {
		try {
			when(rs.getInt("ID")).thenReturn(19308);
			when(rs.getInt(BatchId)).thenReturn(7);
			when(rs.getInt(TransType)).thenReturn(1);
			when(rs.getInt(TransId)).thenReturn(50642);
			when(rs.getInt(STATUS)).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public List<ReprocessFailedDetails> setReprocessListRegistrationAccountOutput() {
		List<ReprocessFailedDetails> reprocessPaymentList = new ArrayList<>();
		ReprocessFailedDetails reprocessFailedPaymentDetail = new ReprocessFailedDetails();
		reprocessFailedPaymentDetail.setId(19308);
		reprocessFailedPaymentDetail.setBatchId(7);
		reprocessFailedPaymentDetail.setTransType("ACCOUNT");
		reprocessFailedPaymentDetail.setTransId(50642);
		reprocessFailedPaymentDetail.setStatus("FAILED");
		reprocessPaymentList.add(reprocessFailedPaymentDetail);
		return reprocessPaymentList;
	}

	public BaseRepeatCheckRequest getFundsInFailuresRequestData() {
		BaseRepeatCheckRequest request = new BaseRepeatCheckRequest();
		request.setDateFrom("2020-07-30 00:00:00");
		request.setDateTo("2020-07-31 00:00:00");
		request.setModuleName("Payment In");
		return request;
	}

	public List<PaymentInRecheckFailureDetails> setDataPaymentIn() {
		List<PaymentInRecheckFailureDetails> failedPaymentInList = new ArrayList<PaymentInRecheckFailureDetails>();
		PaymentInRecheckFailureDetails recheckFailureDetail = new PaymentInRecheckFailureDetails();
		recheckFailureDetail.setAccountId(45518);
		recheckFailureDetail.setContactId(50617);
		recheckFailureDetail.setComplianceStatus("HOLD");
		recheckFailureDetail.setOrgId(2);
		recheckFailureDetail.setOrgCode("Currencies Direct");
		recheckFailureDetail.setPaymentInId(291342);
		recheckFailureDetail.setTradeContractNumber("0202088009936016-003796509");
		recheckFailureDetail.setTradePaymentId(1457057430);
		failedPaymentInList.add(recheckFailureDetail);
		return failedPaymentInList;
	}

	public void setpaymentInOutput() {
		try {
			when(rs.getInt(ACCOUNT_ID)).thenReturn(45518);
			when(rs.getInt(CONTACT_ID)).thenReturn(50617);
			when(rs.getInt("compliancestatus")).thenReturn(4);
			when(rs.getInt(ORG_ID)).thenReturn(2);
			when(rs.getString(ORG_CODE)).thenReturn("Currencies Direct");
			when(rs.getInt("TradePaymentID")).thenReturn(1457057430);
			when(rs.getString("TradeContractNumber")).thenReturn("0202088009936016-003796509");
			when(rs.getInt("paymentID")).thenReturn(291342);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void setMockOutputForgetRegistrationServiceFailureCount() {
		try {
			when(rs.getInt(FRAUGSTER_FAIL)).thenReturn(1);
			when(rs.getInt(SANCTION_FAIL)).thenReturn(1);
			when(rs.getInt(BLACKLIST_FAIL)).thenReturn(1);
			when(rs.getInt(EID_FAIL)).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public List<ReprocessFailedDetails> setReprocessListPaymentInOutput() {
		List<ReprocessFailedDetails> reprocessPaymentList = new ArrayList<>();
		ReprocessFailedDetails reprocessFailedPaymentDetail = new ReprocessFailedDetails();
		reprocessFailedPaymentDetail.setId(19304);
		reprocessFailedPaymentDetail.setBatchId(5);
		reprocessFailedPaymentDetail.setTransType("PAYMENTIN");
		reprocessFailedPaymentDetail.setTransId(291342);
		reprocessFailedPaymentDetail.setStatus("FAILED");
		reprocessPaymentList.add(reprocessFailedPaymentDetail);
		return reprocessPaymentList;
	}

	public void setMockForReprocessListPaymentOut() {
		try {
			when(rs.getInt("ID")).thenReturn(19307);
			when(rs.getInt(BatchId)).thenReturn(6);
			when(rs.getInt(TransType)).thenReturn(3);
			when(rs.getInt(TransId)).thenReturn(182850);
			when(rs.getInt(STATUS)).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public List<ReprocessFailedDetails> setReprocessListOutput() {
		List<ReprocessFailedDetails> reprocessPaymentList = new ArrayList<>();
		ReprocessFailedDetails reprocessFailedPaymentDetail = new ReprocessFailedDetails();
		reprocessFailedPaymentDetail.setId(19307);
		reprocessFailedPaymentDetail.setBatchId(6);
		reprocessFailedPaymentDetail.setTransType("PAYMENTOUT");
		reprocessFailedPaymentDetail.setTransId(182850);
		reprocessFailedPaymentDetail.setStatus("FAILED");
		reprocessPaymentList.add(reprocessFailedPaymentDetail);
		return reprocessPaymentList;
	}

	@Test
	public void testGetRegistrationFailuresTestPFX() {
		try {
			setOutputDataForPFX();
			when(rs.getInt("type")).thenReturn(2);
			BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
			List<RegistrationRecheckFailureDetails> failedRegistrationList = setRegistrationRecheckFailureDetailsPFX();
			List<RegistrationRecheckFailureDetails> failedRegistrationList1 = recheckDBServiceImpl
					.getRegistrationFailures(baseRepeatCheckRequest);
			assertEquals(failedRegistrationList.get(0).getAccountId(), failedRegistrationList1.get(0).getAccountId());
			assertEquals(failedRegistrationList.get(0).getContactId(), failedRegistrationList1.get(0).getContactId());
			assertEquals(failedRegistrationList.get(0).getCustomerType(),
					failedRegistrationList1.get(0).getCustomerType());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public BaseRepeatCheckRequest getFundsOutFailuresRequestData() {
		BaseRepeatCheckRequest request = new BaseRepeatCheckRequest();
		request.setDateFrom("2020-07-23 00:00:00");
		request.setDateTo("2020-07-24 00:00:00");
		request.setModuleName("Payment Out");
		return request;
	}

	public List<PaymentOutRecheckFailureDetails> setDataPaymentOut() {
		List<PaymentOutRecheckFailureDetails> failedPaymentInList = new ArrayList<>();
		PaymentOutRecheckFailureDetails recheckFailureDetail = new PaymentOutRecheckFailureDetails();
		recheckFailureDetail.setAccountId(45518);
		recheckFailureDetail.setContactId(50617);
		recheckFailureDetail.setComplianceStatus("HOLD");
		recheckFailureDetail.setOrgId(2);
		recheckFailureDetail.setOrgCode("Currencies Direct");
		recheckFailureDetail.setPaymentOutId(182850);
		recheckFailureDetail.setTradeContractNumber("0202088009936016-003505372");
		recheckFailureDetail.setTradePaymentId(2001502000);
		failedPaymentInList.add(recheckFailureDetail);
		return failedPaymentInList;
	}

	public void setpaymentOutOutput() {
		try {
			when(rs.getInt(ACCOUNT_ID)).thenReturn(45518);
			when(rs.getInt(CONTACT_ID)).thenReturn(50617);
			when(rs.getInt("compliancestatus")).thenReturn(4);
			when(rs.getInt(ORG_ID)).thenReturn(2);
			when(rs.getString(ORG_CODE)).thenReturn("Currencies Direct");
			when(rs.getInt("TradePaymentID")).thenReturn(1457057430);
			when(rs.getString("TradeContractNumber")).thenReturn("0202088009936016-003796509");
			when(rs.getInt("paymentID")).thenReturn(291342);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void setMockForReprocessListPaymentIn() {
		try {
			when(rs.getInt("ID")).thenReturn(19304);
			when(rs.getInt(BatchId)).thenReturn(5);
			when(rs.getInt(TransType)).thenReturn(2);
			when(rs.getInt(TransId)).thenReturn(291342);
			when(rs.getInt(STATUS)).thenReturn(1);
		} catch (SQLException e) {	
			System.out.println(e);
		}
	}
	//-------Test cases--------//
	@Test
	public void testGetRegistrationFailuresTestCFX() {
		try {
			setOutputDataForCFX();
			when(rs.getInt("type")).thenReturn(1);
			BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
			List<RegistrationRecheckFailureDetails> failedRegistrationList = setRegistrationRecheckFailureDetailCFX();
			List<RegistrationRecheckFailureDetails> failedRegistrationList1 = recheckDBServiceImpl
					.getRegistrationFailures(baseRepeatCheckRequest);
			assertEquals(failedRegistrationList.get(0).getAccountId(), failedRegistrationList1.get(0).getAccountId());
			assertEquals(failedRegistrationList.get(0).getContactId(), failedRegistrationList1.get(0).getContactId());
			assertEquals(failedRegistrationList.get(0).getCustomerType(),
					failedRegistrationList1.get(0).getCustomerType());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testSaveFailedFundsInDetailsToDBForPFX() {
		try {
			int count = 1;
			BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
			List<RegistrationRecheckFailureDetails> failedRegistrationList = setRegistrationRecheckFailureDetailsPFX();
			int noOfRecordsToProcess;
			noOfRecordsToProcess = recheckDBServiceImpl.saveFailedRegDetailsToDB(failedRegistrationList,
					repeatCheckResponse);
			assertEquals(count, noOfRecordsToProcess);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testSaveFailedFundsInDetailsToDBForCFX() {
		try {
			int count = 1;
			BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
			List<RegistrationRecheckFailureDetails> failedRegistrationList = setRegistrationRecheckFailureDetailCFX();
			int noOfRecordsToProcess;
			noOfRecordsToProcess = recheckDBServiceImpl.saveFailedRegDetailsToDB(failedRegistrationList,
					repeatCheckResponse);
			assertEquals(count, noOfRecordsToProcess);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetReprocessListTestforContact() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
		BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
		try {
			setMockForReprocessListRegistration();
			repeatCheckResponse.setBatchId(2);
			List<ReprocessFailedDetails> reprocessPaymentList = setReprocessListRegistrationOutput();
			List<ReprocessFailedDetails> reprocessFailedList;
			reprocessFailedList = recheckDBServiceImpl.getReprocessList(baseRepeatCheckRequest, repeatCheckResponse);
			assertEquals(reprocessPaymentList.get(0).getId(), reprocessFailedList.get(0).getId());
			assertEquals(reprocessPaymentList.get(0).getBatchId(), reprocessFailedList.get(0).getBatchId());
			assertEquals(reprocessPaymentList.get(0).getStatus(), reprocessFailedList.get(0).getStatus());
			assertEquals(reprocessPaymentList.get(0).getTransId(), reprocessFailedList.get(0).getTransId());
			assertEquals(reprocessPaymentList.get(0).getTransType(), reprocessFailedList.get(0).getTransType());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetReprocessListTestforAccount() {
		try {
			BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
			BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
			setMockForReprocessListRegistrationAccount();
			repeatCheckResponse.setBatchId(2);
			List<ReprocessFailedDetails> reprocessPaymentList = setReprocessListRegistrationAccountOutput();
			List<ReprocessFailedDetails> reprocessFailedList;
			reprocessFailedList = recheckDBServiceImpl.getReprocessList(baseRepeatCheckRequest, repeatCheckResponse);
			assertEquals(reprocessPaymentList.get(0).getId(), reprocessFailedList.get(0).getId());
			assertEquals(reprocessPaymentList.get(0).getBatchId(), reprocessFailedList.get(0).getBatchId());
			assertEquals(reprocessPaymentList.get(0).getStatus(), reprocessFailedList.get(0).getStatus());
			assertEquals(reprocessPaymentList.get(0).getTransId(), reprocessFailedList.get(0).getTransId());
			assertEquals(reprocessPaymentList.get(0).getTransType(), reprocessFailedList.get(0).getTransType());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetFundsInFailures() {
		setpaymentInOutput();
		BaseRepeatCheckRequest request = getFundsInFailuresRequestData();
		List<PaymentInRecheckFailureDetails> failedPaymentInList1 = setDataPaymentIn();
		List<PaymentInRecheckFailureDetails> failedPaymentInList;
		try {
			failedPaymentInList = recheckDBServiceImpl.getFundsInFailures(request);
			assertEquals(failedPaymentInList1.get(0).getAccountId(), failedPaymentInList.get(0).getAccountId());
			assertEquals(failedPaymentInList1.get(0).getContactId(), failedPaymentInList.get(0).getContactId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testSaveFailedFundsInDetailsToDB() {
		try {
			int count = 1;
			List<PaymentInRecheckFailureDetails> failedPaymentInList1 = setDataPaymentIn();
			BaseRepeatCheckResponse baseRepeatCheckResponse = new BaseRepeatCheckResponse();
			int noOfRecordsToProcess;
			noOfRecordsToProcess = recheckDBServiceImpl.saveFailedFundsInDetailsToDB(failedPaymentInList1,
					baseRepeatCheckResponse);
			assertEquals(count, noOfRecordsToProcess);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetReprocessListForPaymentIn() {
		try {
			BaseRepeatCheckRequest request = getFundsInFailuresRequestData();
			BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
			setMockForReprocessListPaymentIn();
			repeatCheckResponse.setBatchId(5);
			List<ReprocessFailedDetails> reprocessPaymentList = setReprocessListPaymentInOutput();
			List<ReprocessFailedDetails> reprocessFailedList;
			reprocessFailedList = recheckDBServiceImpl.getReprocessList(request, repeatCheckResponse);
			assertEquals(reprocessPaymentList.get(0).getId(), reprocessFailedList.get(0).getId());
			assertEquals(reprocessPaymentList.get(0).getBatchId(), reprocessFailedList.get(0).getBatchId());
			assertEquals(reprocessPaymentList.get(0).getStatus(), reprocessFailedList.get(0).getStatus());
			assertEquals(reprocessPaymentList.get(0).getTransId(), reprocessFailedList.get(0).getTransId());
			assertEquals(reprocessPaymentList.get(0).getTransType(), reprocessFailedList.get(0).getTransType());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetFundsOutFailures() {
		try {
			setpaymentOutOutput();
			BaseRepeatCheckRequest request = getFundsOutFailuresRequestData();
			List<PaymentOutRecheckFailureDetails> failedPaymentOutList1 = setDataPaymentOut();
			List<PaymentOutRecheckFailureDetails> failedPaymentOutList;
			failedPaymentOutList = recheckDBServiceImpl.getFundsOutFailures(request);
			assertEquals(failedPaymentOutList.get(0).getAccountId(), failedPaymentOutList1.get(0).getAccountId());
			assertEquals(failedPaymentOutList.get(0).getContactId(), failedPaymentOutList1.get(0).getContactId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testSaveFailedFundsOutDetailsToDB() {
		try {
			int count = 1;
			List<PaymentOutRecheckFailureDetails> failedPaymentOutList1 = setDataPaymentOut();
			BaseRepeatCheckResponse baseRepeatCheckResponse = new BaseRepeatCheckResponse();
			int noOfRecordsToProcess = recheckDBServiceImpl.saveFailedDetailsToDB(failedPaymentOutList1,
					baseRepeatCheckResponse);
			assertEquals(count, noOfRecordsToProcess);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetReprocessListPaymentOut() throws CompliancePortalException, SQLException {
		BaseRepeatCheckRequest baseRepeatCheckRequest = getFundsOutFailuresRequestData();
		BaseRepeatCheckResponse repeatCheckResponse = new BaseRepeatCheckResponse();
		setMockForReprocessListPaymentOut();
		repeatCheckResponse.setBatchId(6);
		List<ReprocessFailedDetails> reprocessPaymentList = setReprocessListOutput();
		List<ReprocessFailedDetails> reprocessFailedList = recheckDBServiceImpl.getReprocessList(baseRepeatCheckRequest,
				repeatCheckResponse);
		assertEquals(reprocessPaymentList.get(0).getId(), reprocessFailedList.get(0).getId());
		assertEquals(reprocessPaymentList.get(0).getBatchId(), reprocessFailedList.get(0).getBatchId());
		assertEquals(reprocessPaymentList.get(0).getStatus(), reprocessFailedList.get(0).getStatus());
		assertEquals(reprocessPaymentList.get(0).getTransId(), reprocessFailedList.get(0).getTransId());
		assertEquals(reprocessPaymentList.get(0).getTransType(), reprocessFailedList.get(0).getTransType());
	}

	/*
	 * @Test public void testGetRegistrationServiceFailureCount() { Integer count =
	 * 1; BaseRepeatCheckRequest baseRepeatCheckRequest =
	 * setBaseRepeatCheckRequest();
	 * setMockOutputForgetRegistrationServiceFailureCount(); try {
	 * BaseRepeatCheckResponse baseRepeatCheckResponse = recheckDBServiceImpl
	 * .getRegistrationServiceFailureCount(baseRepeatCheckRequest);
	 * assertEquals(count, baseRepeatCheckResponse.getFraugsterFailCount()); } catch
	 * (CompliancePortalException e) { System.out.println(e); } }
	 */

	@Test
	public void testGetFundsInServiceFailureCount() {
		Integer count = 1;
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
		setMockOutputForgetRegistrationServiceFailureCount();
		try {
			BaseRepeatCheckResponse baseRepeatCheckResponse = recheckDBServiceImpl
					.getFundsInServiceFailureCount(baseRepeatCheckRequest);
			assertEquals(count, baseRepeatCheckResponse.getFraugsterFailCount());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetFundsOutServiceFailureCount() {
		Integer count = 1;
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
		setMockOutputForgetRegistrationServiceFailureCount();
		try {
			BaseRepeatCheckResponse baseRepeatCheckResponse = recheckDBServiceImpl
					.getFundsOutServiceFailureCount(baseRepeatCheckRequest);
			assertEquals(count, baseRepeatCheckResponse.getFraugsterFailCount());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testDeleteReprocessFailed() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
		baseRepeatCheckRequest.setBatchId(2);
		try {
			Integer expectedResult = 1;
			Integer result = recheckDBServiceImpl.deleteReprocessFailed(baseRepeatCheckRequest);
			assertEquals(expectedResult,result);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
}