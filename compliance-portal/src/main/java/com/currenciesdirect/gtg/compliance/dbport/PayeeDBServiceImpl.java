package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeClient;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsResponse;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.core.IPayeeDBService;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeQueueDto;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class PayeeDBServiceImpl.
 */
@Component("iPayeeDBService")
public class PayeeDBServiceImpl extends AbstractDao implements IPayeeDBService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IPayeeDBService#
	 * getPayeeWithCriteria(com.currenciesdirect.gtg.compliance.core.domain.
	 * report.BeneficiaryReportSearchCriteria)
	 */
	@Override
	public PayeeQueueDto getPayeeWithCriteria(BeneficiaryReportSearchCriteria searchCriteria)
			throws CompliancePortalException {

		PayeeQueueDto payeeQueueDto = new PayeeQueueDto();
		Connection connection = null;
		List<String> organization = getOrganizationList(connection);
		List<String> country = getCountryList(connection);
		List<String> currency = getCurrencyList(connection);
		payeeQueueDto.setCountry(country);
		payeeQueueDto.setOrganization(organization);
		payeeQueueDto.setCurrency(currency);

		return payeeQueueDto;

	}

	/**
	 * Gets the country list.
	 *
	 * @param connection
	 *            the connection
	 * @return the country list
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private List<String> getCountryList(Connection connection) throws CompliancePortalException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> country = new ArrayList<>();
		try {
			connection = getConnection(Boolean.TRUE);
			ps = connection.prepareStatement(RegistrationQueryConstant.GET_COUNTRY_LIST.getQuery());
			rs = ps.executeQuery();
			while (rs.next()) {
				country.add(rs.getString("Country"));
			}

		} catch (Exception e) {

			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(ps);
			closeConnection(connection);
		}
		return country;
	}

	/**
	 * Gets the currency list.
	 *
	 * @param connection
	 *            the connection
	 * @return the currency list
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private List<String> getCurrencyList(Connection connection) throws CompliancePortalException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> currency = new ArrayList<>();
		try {
			connection = getConnection(Boolean.TRUE);
			ps = connection.prepareStatement(RegistrationQueryConstant.GET_CURRENCY.getQuery());
			rs = ps.executeQuery();
			while (rs.next()) {
				currency.add(rs.getString(RegQueDBColumns.CURRENCY.getName()));
			}

		} catch (Exception e) {

			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(ps);
			closeConnection(connection);
		}
		return currency;
	}

	/**
	 * Gets the organization list.
	 *
	 * @param connection
	 *            the connection
	 * @return the organization list
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private List<String> getOrganizationList(Connection connection) throws CompliancePortalException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> organization = new ArrayList<>();
		try {
			connection = getConnection(Boolean.TRUE);
			ps = connection.prepareStatement(RegistrationQueryConstant.GET_ORGANIZATION.getQuery());
			rs = ps.executeQuery();
			while (rs.next()) {
				organization.add(rs.getString(RegQueDBColumns.CODE.getName()));
			}

		} catch (Exception e) {

			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(ps);
			closeConnection(connection);
		}
		return organization;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IPayeeDBService#
	 * getPayeeClientsDetailsFromDB(com.currenciesdirect.gtg.compliance.commons.
	 * domain.titan.response.PayeeESResponse)
	 */
	@SuppressWarnings("squid:S2095")
	@Override
	public PayeeESResponse getPayeeClientsDetailsFromDB(PayeeESResponse customCheckPayeeResponse)
			throws CompliancePortalException {
		Connection connection = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			for (PayeeClient payeeClient : customCheckPayeeResponse.getChildren()) {
				ps = connection.prepareStatement(RegistrationQueryConstant.GET_PAYEE_CLIENT_DETAILS.getQuery());
				ps.setString(1, payeeClient.getId());
				rs = ps.executeQuery();
				while (rs.next()) {
					payeeClient.setName(rs.getString("name"));
				}
				if (null == payeeClient.getName())
					payeeClient.setName(Constants.DASH_DETAILS_PAGE);
			}

		} catch (Exception e) {

			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(ps);
			closeConnection(connection);
		}

		return customCheckPayeeResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IPayeeDBService#
	 * getTransactionList(com.currenciesdirect.gtg.compliance.commons.domain.
	 * titan.response.PayeePaymentsRequest)
	 */
	@Override
	public List<PayeePaymentsResponse> getTransactionList(PayeePaymentsRequest payeePaymentsRequest)
			throws CompliancePortalException {
		List<PayeePaymentsResponse> response = new ArrayList<>();
		Connection connection = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			ps = connection.prepareStatement(RegistrationQueryConstant.GET_PAYMENT_DETAILS.getQuery());
			ps.setString(1, payeePaymentsRequest.getTradeAccountNumber());
			ps.setString(2, payeePaymentsRequest.getTradeBeneId());
			ps.setString(3, payeePaymentsRequest.getOrgCode());
			rs = ps.executeQuery();
			while (rs.next()) {
				PayeePaymentsResponse payeeresponse = new PayeePaymentsResponse();
				payeeresponse.setAmount(rs.getDouble("vBeneficiaryAmount"));
				payeeresponse.setCurrency(rs.getString("vBuyingCurrency"));
				payeeresponse.setDate(rs.getString("TransactionDate"));
				payeeresponse
						.setStatus(PaymentComplianceStatus.getStatusFromDatabaseStatus(rs.getInt("ComplianceStatus")));
				payeeresponse.setUpdatedOn(rs.getString("UpdatedOn"));
				payeeresponse.setReference(Constants.DASH_DETAILS_PAGE);
				if (null != rs.getString("reference"))
					payeeresponse.setReference(rs.getString("reference"));
				response.add(payeeresponse);
			}
		} catch (Exception e) {

			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(ps);
			closeConnection(connection);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IPayeeDBService#
	 * getIsBeneficiaryWhitelisted(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Boolean getIsBeneficiaryWhitelisted(String atlasBeneAccNumber, Boolean isBeneficiaryWhitelisted)
			throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			ps = connection.prepareStatement(RegistrationQueryConstant.CHECK_IF_BENE_IS_WHITELISTED.getQuery());
			ps.setString(1, atlasBeneAccNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				isBeneficiaryWhitelisted = Boolean.TRUE;
			}
		} catch (Exception e) {

			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(ps);
			closeConnection(connection);
		}
		return isBeneficiaryWhitelisted;
	}

}
