package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.ITokenizer;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsResponse;
import com.currenciesdirect.gtg.compliance.core.IPayeeDBService;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.Page;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class PayeeServiceImpl.
 */
@Component("payeeServiceImpl")
public class PayeeServiceImpl implements IPayeeService {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(PayeeServiceImpl.class);

	/** The Constant BASE_TITAN_URL. */
	private static final String BASE_TITAN_URL = "baseTitanUrl";

	/** The Constant AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";

	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "Content-Type";

	/** The Constant RETRY_COUNT. */
	public static final int RETRY_COUNT = 3;
	
	/** The Constant TITAN. */
	public static final String TITAN = "titan";

	/** The i tokenizer. */
	@Autowired
	@Qualifier("tokenizer")
	private ITokenizer iTokenizer;

	/** The i payee DB service. */
	@Autowired
	@Qualifier("iPayeeDBService")
	private IPayeeDBService iPayeeDBService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.titan.payee.IPayeeService#
	 * getBeneficiaryDetails(com.currenciesdirect.gtg.compliance.iam.core.domain
	 * .UserProfile,
	 * com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeDetailsRequest,
	 * com.currenciesdirect.gtg.compliance.core.domain.report.
	 * BeneficiaryReportSearchCriteria)
	 */
	@Override
	public PayeeResponse getBeneficiaryDetails(UserProfile user, PayeeDetailsRequest payeeRequest,
			BeneficiaryReportSearchCriteria searchCriteria) throws CompliancePortalException {
		PayeeResponse payeeResponse = new PayeeResponse();
		Boolean isBeneficiaryWhitelisted = Boolean.FALSE;

		try {
			payeeResponse = fetchPayeeDetails(payeeRequest);
			if (null != payeeResponse.getPayee()) {
				isBeneficiaryWhitelisted = getIsBeneficiaryWhitelisted(payeeResponse, isBeneficiaryWhitelisted);
				payeeResponse.setIsBeneficiaryWhitelisted(isBeneficiaryWhitelisted);
			}
		} catch (Exception e) {
			logError(e);
		}
		return payeeResponse;

	}

	/**
	 * Fetch payee details.
	 *
	 * @param payeeRequest
	 *            the payee request
	 * @return the payee response
	 * @throws Exception 
	 */
	private PayeeResponse fetchPayeeDetails(PayeeDetailsRequest payeeRequest) throws Exception {
		PayeeResponse response = new PayeeResponse();
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		
		String jsonPayeeRequest = JsonConverterUtil.convertToJsonWithoutNull(payeeRequest);
		log.warn("\n -------fetchPayeeDetails Payee Detail Request Start -------- \n  {}", jsonPayeeRequest);
		log.warn(" \n -----------fetchPayeeDetails Payee Detail Request End ---------");
		boolean breakFlag = false;
		int count = 0;
		String token;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);

				response = clientPool.sendRequest(baseUrl + "/titan-wrapper/services/payee/getBeneficiaryDetails",
						"POST", JsonConverterUtil.convertToJsonWithNull(payeeRequest), PayeeResponse.class, headers,
						MediaType.APPLICATION_JSON_TYPE);
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					log.error(e.toString());
					breakFlag = true;
				}
			}
		} while (count < RETRY_COUNT);
		
		String jsonPayeeResponse = JsonConverterUtil.convertToJsonWithoutNull(response);
		log.warn("\n -------fetchPayeeDetails Payee Detail Response Start -------- \n  {}", jsonPayeeResponse);
		log.warn(" \n -----------fetchPayeeDetails Payee Detail Response End ---------");
		
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.titan.payee.IPayeeService#
	 * getBeneficiaryList(com.currenciesdirect.gtg.compliance.core.domain.report
	 * .BeneficiaryReportSearchCriteria)
	 */
	@Override
	public PayeeResponse getBeneficiaryList(BeneficiaryReportSearchCriteria searchCriteria)
			throws CompliancePortalException {

		PayeeListRequest payeeListRequest = new PayeeListRequest();
		BeneficiaryReportSearchCriteria beneficiaryReportSearchCriteria = new BeneficiaryReportSearchCriteria();
		Sort sort = new Sort();
		Page page = new Page();

		if (null != searchCriteria && null != searchCriteria.getSort()
				&& null != searchCriteria.getSort().getIsAscend())
			sort.setIsAscend(searchCriteria.getSort().getIsAscend());
		else
			sort.setIsAscend(Boolean.FALSE);
		sort.setFieldName("date");

		if (null != searchCriteria && null != searchCriteria.getPage()
				&& null != searchCriteria.getPage().getCurrentPage())
			page.setCurrentPage(searchCriteria.getPage().getCurrentPage());
		else
			page.setCurrentPage(1);

		page.setPageSize(Integer.valueOf(System.getProperty(Constants.PAGE_SIZE)));
		beneficiaryReportSearchCriteria.setPage(page);
		beneficiaryReportSearchCriteria.setSort(sort);
		if (null != searchCriteria) {
			beneficiaryReportSearchCriteria.setIsFilterApply(searchCriteria.getIsFilterApply());
			beneficiaryReportSearchCriteria.setFilter(searchCriteria.getFilter());
		}
		payeeListRequest.setOsrId(UUID.randomUUID().toString());
		payeeListRequest.setSourceApplication("Atlas");
		payeeListRequest.setBaseSearchCriteria(beneficiaryReportSearchCriteria);

		PayeeResponse payeeResponse = new PayeeResponse();

		try {
			payeeResponse = fetchPayeeList(payeeListRequest);
			if (null != payeeResponse && null != payeeResponse.getSearchCriteria())
				payeeResponse.setSearchCriteriaForUI(
						JsonConverterUtil.convertToJsonWithNull(payeeResponse.getSearchCriteria()));

		} catch (Exception e) {
			logError(e);
			payeeResponse.setIsSystemNotAvailaible(Boolean.TRUE);
		}
		return payeeResponse;
	}

	/**
	 * Log error.
	 *
	 * @param t
	 *            the t
	 */
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

	/**
	 * Fetch payee details.
	 *
	 * @param payeeListRequest
	 *            the payee list request
	 * @return the payee response
	 * @throws Exception 
	 */
	private PayeeResponse fetchPayeeList(PayeeListRequest payeeListRequest) throws Exception {
		PayeeResponse response = new PayeeResponse();
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		int count = 0;
		String token;
		
		String jsonPayeeRequest = JsonConverterUtil.convertToJsonWithoutNull(payeeListRequest);
		log.warn("\n -------fetchPayeeList Payee List Request Start -------- \n  {}", jsonPayeeRequest);
		log.warn(" \n -----------fetchPayeeList Payee List Request End ---------");
		boolean breakFlag = false;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);

				response = clientPool.sendRequest(
						baseUrl + "/titan-wrapper/services/payee/getMostRecentUsedBeneficiaries", "POST",
						JsonConverterUtil.convertToJsonWithNull(payeeListRequest), PayeeResponse.class, headers,
						MediaType.APPLICATION_JSON_TYPE);
				JsonConverterUtil.convertToJsonWithNull(response);
				if (null != payeeListRequest && null != payeeListRequest.getBaseSearchCriteria() && null != response
						&& null != response.getSearchCriteria())
					response.getSearchCriteria()
							.setIsFilterApply(payeeListRequest.getBaseSearchCriteria().getIsFilterApply());
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					log.error(e.toString());
					breakFlag = true;
				}
			}
		} while (count < RETRY_COUNT);
		
		String jsonPayeeResponse = JsonConverterUtil.convertToJsonWithoutNull(response);
		log.warn("\n -------fetchPayeeList Payee List Response Start -------- \n  {}", jsonPayeeResponse);
		log.warn(" \n -----------fetchPayeeList Payee List Response End ---------");
		
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.titan.payee.IPayeeService#
	 * getBeneficiaryListWithCriteria(com.currenciesdirect.gtg.compliance.core.
	 * domain.report.BeneficiaryReportSearchCriteria)
	 */
	@Override
	public PayeeQueueDto getBeneficiaryListWithCriteria(BeneficiaryReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iPayeeDBService.getPayeeWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.titan.payee.IPayeeService#
	 * getRegistrationDetailsFromES(com.currenciesdirect.gtg.compliance.iam.core
	 * .domain.UserProfile,
	 * com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeResponse)
	 */
	@Override
	public PayeeESResponse getRegistrationDetailsFromES(UserProfile user, PayeeResponse payeeRequestObj)
			throws CompliancePortalException {

		PayeeESResponse customCheckPayeeResponse = new PayeeESResponse();
		List<WrapperPayeePaymentMethod> payeePaymentMethod = null;
		payeePaymentMethod = payeeRequestObj.getPayee().getPayeePaymentMethodList();
		String atlasBeneAccNumber = getAtlasBeneAccountNumber(payeePaymentMethod);

		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		ArrayList<Object> list = new ArrayList<>();
		list.add(user);
		headers.put("user", list);
		String baseUrl = System.getProperty("baseComplianceServiceUrl");
		try {
			customCheckPayeeResponse = clientPool.sendRequest(baseUrl + "/custom-checks-service/getBeneficiaryDetails",
					"POST", atlasBeneAccNumber, PayeeESResponse.class, headers, MediaType.APPLICATION_JSON_TYPE);
			customCheckPayeeResponse = iPayeeDBService.getPayeeClientsDetailsFromDB(customCheckPayeeResponse);
			customCheckPayeeResponse.setName(payeeRequestObj.getPayee().getFullName());
			customCheckPayeeResponse.setType("beneficiary");

		} catch (CompliancePortalException e) {
			log.error(e.toString());
		}
		return customCheckPayeeResponse;

	}

	private String getAtlasBeneAccountNumber(List<WrapperPayeePaymentMethod> payeePaymentMethod) {
		String atlasBeneAccNumber = "";
		String beneAccNumber = "";
		String swiftCode = "";
		String intraCountryCode = "";

		for (WrapperPayeePaymentMethod payeePayment : payeePaymentMethod) {
			if (null != payeePayment && null != payeePayment.getPayeeBank()) {
				if (null != payeePayment.getPayeeBank().getAccountNumber())
					beneAccNumber = payeePayment.getPayeeBank().getAccountNumber();
				else if (null != payeePayment.getPayeeBank().getIban())
					beneAccNumber = payeePayment.getPayeeBank().getIban();
				if (null != payeePayment.getPayeeBank().getBankBIC())
					swiftCode = payeePayment.getPayeeBank().getBankBIC();
				if (null != payeePayment.getPayeeBank().getBankIntraCountryCode())
					intraCountryCode = payeePayment.getPayeeBank().getBankIntraCountryCode();

				atlasBeneAccNumber = atlasBeneAccNumber.concat(swiftCode).concat(beneAccNumber)
						.concat(intraCountryCode);
			}
		}
		return atlasBeneAccNumber;
	}

	private Boolean getIsBeneficiaryWhitelisted(PayeeResponse payeeResponse, Boolean isBeneficiaryWhitelisted)
			throws CompliancePortalException {
		String atlasBeneAccNumber = getAtlasBeneAccountNumber(payeeResponse.getPayee().getPayeePaymentMethodList());
		isBeneficiaryWhitelisted = iPayeeDBService.getIsBeneficiaryWhitelisted(atlasBeneAccNumber,
				isBeneficiaryWhitelisted);
		return isBeneficiaryWhitelisted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.titan.payee.IPayeeService#
	 * getTransactionList(com.currenciesdirect.gtg.compliance.iam.core.domain.
	 * UserProfile,
	 * com.currenciesdirect.gtg.compliance.commons.domain.titan.response.
	 * PayeePaymentsRequest)
	 */
	@Override
	public List<PayeePaymentsResponse> getTransactionList(UserProfile user, PayeePaymentsRequest payeePaymentsRequest)
			throws CompliancePortalException {
		List<PayeePaymentsResponse> payeePayments;
		payeePayments = iPayeeDBService.getTransactionList(payeePaymentsRequest);
		return payeePayments;
	}

}
