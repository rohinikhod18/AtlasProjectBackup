package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.core.domain.BaseUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EuPoiCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentInDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Intuition;
import com.currenciesdirect.gtg.compliance.core.domain.IntuitionPaymentResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationData;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentReferenceCheck;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.VelocityCheck;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;
import com.currenciesdirect.gtg.compliance.core.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.CustomCheckSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.EntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FragusterWatchListEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FraugsterReasonsEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class AbstractDBServicePaymentOut.
 */
public abstract class AbstractDBServicePaymentOut extends AbstractDBServiceLevel2 {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDBServicePaymentOut.class);

	private static final String PRE_WATCHLIST = "{PRE_WATCHLIST}";
	private static final String CURRENT_WATCHLIST = "{CURRENT_WATCHLIST}";
	
	protected Watchlist getAccountWatchlist(Integer accountId, Connection connection) throws CompliancePortalException {

		Watchlist watchlist = new Watchlist();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlist.setWatchlistData(watchlistDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(RegistrationQueryConstant.GET_ACCOUNT_WATCHLIST.getQuery());
			statement.setInt(1, accountId);
			rs = statement.executeQuery();
			while (rs.next()) {
				WatchListData watchlistData = new WatchListData();
				watchlistData.setName(rs.getString(Constants.WATCHLISTNAME));
				watchlistData.setContactId(rs.getInt(Constants.CONTACTID));
				watchlistData.setValue(Boolean.TRUE);
				watchlistDataList.add(watchlistData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return watchlist;
	}

	protected Watchlist getAccountWatchlistWithSelected(Integer accountId, Integer contactId, String custType,  Connection connection, UserProfile profile)
			throws CompliancePortalException {

		Watchlist watchlist = new Watchlist();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlist.setWatchlistData(watchlistDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean category1 = profile.getPermissions().getCanManageWatchListCategory1();
		boolean category2 = profile.getPermissions().getCanManageWatchListCategory2();
		try {
			if(Constants.CUST_TYPE_PFX.equalsIgnoreCase(custType)){
				statement = getPrepareStatementForContactWatchlist(contactId, connection, category1, category2);
			}else{
				statement = getPrepareStatementForWatchlist(accountId, connection, category1, category2);
			}
			rs = statement.executeQuery();
			while (rs.next()) {
				WatchListData watchlistData = new WatchListData();
				watchlistData.setName(rs.getString(Constants.WATCHLISTNAME));
				watchlistData.setContactId(rs.getInt(Constants.CONTACTID));
				Integer contactIdDb = rs.getInt(Constants.CONTACTID);
				if (contactIdDb != -1) {
					watchlistData.setValue(Boolean.TRUE);
				} else {
					watchlistData.setValue(Boolean.FALSE);
				}
				watchlistDataList.add(watchlistData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return watchlist;
	}

	 /**
	 * @param contactId
	 * @param connection
	 * @param category1
	 * @param category2
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("squid:S2095")
	private PreparedStatement getPrepareStatementForContactWatchlist(Integer contactId, Connection connection,
			boolean category1, boolean category2) throws SQLException {
		PreparedStatement statement;
		// if only one of category is enabled, then add second param to
		// query
		// else no filter needed
		String accountWatchlistQuery = "";
		int result = 0;
		if ((category1 == category2)) {
			accountWatchlistQuery = RegistrationQueryConstant.GET_CONTACT_WATCHLIST_WITH_SELECTED.getQuery();
		} else {
			accountWatchlistQuery = RegistrationQueryConstant.GET_CONTACT_WATCHLIST_CATEGORY_WITH_SELECTED.getQuery();
			if (category1 && !category2) {
				result = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus();

			} else if (!category1 && category2) {
				result = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus();
			}
		}

		statement = connection.prepareStatement(accountWatchlistQuery);

		statement.setInt(1, contactId);
		if ((!category1 && category2) || (category1 && !category2))
			statement.setInt(2, result);
		return statement;
	}
	
	/**
	 * @param accountId
	 * @param connection
	 * @param category1
	 * @param category2
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("squid:S2095")
	private PreparedStatement getPrepareStatementForWatchlist(Integer accountId, Connection connection,
			boolean category1, boolean category2) throws SQLException {
		PreparedStatement statement;
		// if only one of category is enabled, then add second param to
		// query
		// else no filter needed
		String accountWatchlistQuery = "";
		int result = 0;
		if ((category1 == category2)) {
			accountWatchlistQuery = RegistrationQueryConstant.GET_ACCOUNT_WATCHLIST_WITH_SELECTED.getQuery();
		} else {
			accountWatchlistQuery = RegistrationQueryConstant.GET_ACCOUNT_WATCHLIST_CATEGORY_WITH_SELECTED.getQuery();
			if (category1 && !category2) {
				result = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus();

			} else if (!category1 && category2) {
				result = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus();
			}
		}

		statement = connection.prepareStatement(accountWatchlistQuery);

		statement.setInt(1, accountId);
		if ((!category1 && category2) || (category1 && !category2))
			statement.setInt(2, result);
		return statement;
	}

	protected Boolean saveIntoBroadcastQueue(BroadcastEventToDB broadcastEventToDB, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.SAVE_INTO_BROADCAST_QUEUE.getQuery());
			preparedStatement.setString(1, broadcastEventToDB.getOrgCode());
			preparedStatement.setInt(2,
					BroadCastEntityTypeEnum.getBraodCastEntityTypeAsInteger(broadcastEventToDB.getEntityType()));
			preparedStatement.setInt(3, broadcastEventToDB.getAccountId());
			if (broadcastEventToDB.getContactId() != null) {
				preparedStatement.setInt(4, broadcastEventToDB.getContactId());
			} else {
				preparedStatement.setNull(4, Types.INTEGER);
			}
			if (broadcastEventToDB.getPaymentInId() != null) {
				preparedStatement.setInt(5, broadcastEventToDB.getPaymentInId());
			} else {
				preparedStatement.setNull(5, Types.INTEGER);
			}
			if (broadcastEventToDB.getPaymentOutId() != null) {
				preparedStatement.setInt(6, broadcastEventToDB.getPaymentOutId());
			} else {
				preparedStatement.setNull(6, Types.INTEGER);
			}
			preparedStatement.setString(7, broadcastEventToDB.getStatusJson());
			preparedStatement.setInt(8,
					BroadCastStatusEnum.getBraodCastStatusAsInteger(broadcastEventToDB.getDeliveryStatus()));
			preparedStatement.setTimestamp(9, broadcastEventToDB.getDeliverOn());
			preparedStatement.setString(10, broadcastEventToDB.getCreatedBy());
			preparedStatement.setTimestamp(11, broadcastEventToDB.getCreatedOn());
			int count = preparedStatement.executeUpdate();
			boolean result = true;
			if (count == 0) {
				result = false;
			}
			return result;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_SAVING_DATA_INTO_BROADCAST_QUEUE, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	protected List<IDomain> getMoreKycDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Kyc kyc = new Kyc();
				KYCSummary kycSummary = JsonConverterUtil.convertToObject(KYCSummary.class,
						resultSet.getString(Constants.SUMMARY));
				kyc.setId(resultSet.getInt("id")); // ESL id
				kyc.setCheckedOn(DateTimeFormatter.dateTimeFormatter(kycSummary.getCheckedOn()));
				kyc.setDob(kycSummary.getDob());
				kyc.setEidCheck(kycSummary.getEidCheck());
				kyc.setVerifiactionResult(kycSummary.getVerifiactionResult());
				kyc.setReferenceId(kycSummary.getReferenceId());
				boolean status = Constants.PASS.equals(kycSummary.getStatus());
				/** Added changes to set status as Not_Required on UI -Saylee */
				if (Constants.NOT_REQUIRED.equalsIgnoreCase(kycSummary.getStatus())) {
					kyc.setIsRequired(Boolean.FALSE);
					kyc.setStatusValue(Constants.NOT_REQUIRED_UI);
				} else {
					kyc.setIsRequired(Boolean.TRUE);
					kyc.setStatusValue(kycSummary.getStatus());
					kyc.setStatus(status);
				}
				kyc.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				domainList.add((IDomain) kyc);
			}
			return domainList;

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	protected List<IDomain> getMoreSanctionDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Sanction sanc = new Sanction();
				SanctionSummary sancSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
						resultSet.getString(Constants.SUMMARY));
				sanc.setUpdatedBy(resultSet.getString("updatedBy"));
				sanc.setEventServiceLogId(resultSet.getInt("id")); // Set esl id
				sanc.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				sanc.setSanctionId(sancSummary.getSanctionId());
				sanc.setOfacList(sancSummary.getOfacList());
				sanc.setWorldCheck(sancSummary.getWorldCheck());
				boolean status = "PASS".equals(sancSummary.getStatus());
				/** Added changes to set status as Not_Required on UI -Saylee */
				if (Constants.NOT_REQUIRED.equalsIgnoreCase(sancSummary.getStatus())) {
					sanc.setIsRequired(Boolean.FALSE);
					sanc.setStatusValue(Constants.NOT_REQUIRED_UI);
				} else {
					sanc.setIsRequired(Boolean.TRUE);
					sanc.setStatusValue(sancSummary.getStatus());
					sanc.setStatus(status);
				}
				sanc.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				domainList.add((IDomain) sanc);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}

	}

	protected List<IDomain> getMoreFraugsterDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Fraugster fraug = new Fraugster();
				FraugsterSummary fraugSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
						resultSet.getString(Constants.SUMMARY));
				boolean status = fraugSummary.getStatus() != null && Constants.PASS.equals(fraugSummary.getStatus());
				fraug.setCreatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				fraug.setUpdatedBy(resultSet.getString("updatedBy"));
				fraug.setFraugsterId(fraugSummary.getFrgTransId());
				fraug.setScore(fraugSummary.getScore());
				fraug.setStatus(status);
				fraug.setId(resultSet.getInt("id")); // fix AT-373 - Umesh
				domainList.add((IDomain) fraug);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	@Override
	protected List<String> getOrganization(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> organization = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_ORGANIZATION.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				organization.add(resultSet.getString(RegQueDBColumns.CODE.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return organization;
	}

	@Override
	protected List<String> getCurrency(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> currency = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_CURRENCY.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				currency.add(resultSet.getString(RegQueDBColumns.CURRENCY.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return currency;
	}

	@Override
	protected List<String> getAllCountries(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> countries = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_COUNTRY_LIST.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				countries.add(resultSet.getString("Country"));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return countries;
	}

	protected List<IDomain> getViewMoreFurtherPaymentInDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				FurtherPaymentInDetails paymentInDetails = new FurtherPaymentInDetails();
				/**
				 * We are setting account name and number according to payment
				 * method 1) If payment method is 'SWITCH/DEBIT' then we will
				 * show Card holder name and card number on UI else account name
				 * and debtor account number. 2)For methods other than
				 * SWITCH/DEBIT there is possibility that debtor_account_no is
				 * empty then we will show '-' for that.
				 */
				paymentInDetails.setPaymentId(resultSet.getInt("pyiid"));
				if ("SWITCH/DEBIT".equalsIgnoreCase(resultSet.getString(Constants.PAYMENT_METHOD_IN))) {
					paymentInDetails.setAccountName(resultSet.getString("Ccfirstname"));
				} else {
					setDebtorName(paymentInDetails, resultSet.getString("DebtorName"));
				}
				String debtorAccountNumber = resultSet.getString("DebtorAccountNumber");
				if (null != debtorAccountNumber && !debtorAccountNumber.isEmpty())
					paymentInDetails.setAccount(debtorAccountNumber);
				else
					paymentInDetails.setAccount("-");

				paymentInDetails.setDateOfPayment(
						DateTimeFormatter.removeTimeFromDate(resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)));
				paymentInDetails.setAmount(StringUtils
						.getNumberFormat(DecimalFormatter.convertToFourDigit(resultSet.getString(Constants.AMOUNT))));
				paymentInDetails.setSellCurrency(resultSet.getString("SellCurrency"));
				paymentInDetails.setMethod(resultSet.getString("PaymentMethodin"));
				paymentInDetails.setTradeContractNumber(resultSet.getString(Constants.TRADE_CONTRACT_NUMBER));
				//AT-1731 - SnehaZagade
				paymentInDetails.setBankName(resultSet.getString("BankName"));
				setRiskGuardianScore(paymentInDetails, resultSet.getString("RiskScore"), resultSet.getString("TScore"));
				domainList.add(paymentInDetails);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	/**
	 * Purpose: If tScore field of risk score is empty or null then we need to
	 * show "-" on UI else we will show that score.
	 * 
	 * Implementation: 1)We are checking 'tScore' field of fundsInCreateRequest.
	 * If it is null then we are setting risk score as "-" otherwise actual
	 * score to it.
	 * 
	 * - code changed by Vishal J
	 */
	protected List<IDomain> getFurtherPaymentOutDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				FurtherPaymentOutDetails paymentOutDetails = new FurtherPaymentOutDetails();
				paymentOutDetails.setPaymentId(resultSet.getInt("poid"));
				if (null != resultSet.getString(Constants.BENEFICIARY_LAST_NAME)
						&& !resultSet.getString(Constants.BENEFICIARY_LAST_NAME).isEmpty()) {
					String beneficiaryName = new StringBuilder()
							.append(resultSet.getString(Constants.BENEFICIARY_FIRST_NAME)).append(" ")
							.append(resultSet.getString(Constants.BENEFICIARY_LAST_NAME)).toString();
					paymentOutDetails.setAccountName(beneficiaryName);
				} else {
					paymentOutDetails.setAccountName(resultSet.getString(Constants.BENEFICIARY_FIRST_NAME));
				}
				paymentOutDetails.setAccount(resultSet.getString("AccountNumber"));
				if (null != resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)) {
					paymentOutDetails.setDateOfPayment(
							DateTimeFormatter.removeTimeFromDate(resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)));
				} else {
					paymentOutDetails.setDateOfPayment(Constants.DASH_DETAILS_PAGE);
				}
				paymentOutDetails.setAmount(StringUtils
						.getNumberFormat(DecimalFormatter.convertToFourDigit(resultSet.getString(Constants.AMOUNT))));
				paymentOutDetails.setBuyCurrency(resultSet.getString("BuyCurrency"));
				paymentOutDetails.setValuedate(DateTimeFormatter.dateFormat(resultSet.getString("MaturityDate")));
				paymentOutDetails.setTradeContractNumber(resultSet.getString(Constants.TRADE_CONTRACT_NUMBER));
				paymentOutDetails.setPaymentReference(resultSet.getString(Constants.PAYMENT_REFERENCE));
				//AT-1731 - SnehaZagade
				paymentOutDetails.setBankName(resultSet.getString("BankName"));
				domainList.add(paymentOutDetails);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}

	}

	protected List<IDomain> getMoreCustomCheckDetailsForPayment(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				PaymentOutCustomCheck customCheck = new PaymentOutCustomCheck();
				VelocityCheck velocityCheck = new VelocityCheck();
				WhitelistCheck whitelistCheck = new WhitelistCheck();
				EuPoiCheck euPoiCheck = new EuPoiCheck();
				customCheck.setVelocityCheck(velocityCheck);
				customCheck.setWhiteListCheck(whitelistCheck);
				CustomCheckSummary customCheckSummary = JsonConverterUtil.convertToObject(CustomCheckSummary.class,
						resultSet.getString(Constants.SUMMARY));
				getWhiteListandVelocityCheckSummary(velocityCheck, whitelistCheck,euPoiCheck, customCheckSummary);
				customCheck.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				customCheck.setEnitityId(resultSet.getString("entityid"));
				customCheck.setId(resultSet.getInt("id"));
				customCheck.setStatus(resultSet.getString("status"));
				customCheck.setEuPoiCheck(euPoiCheck);
				//Add for AT-3161
				if (customCheckSummary != null) {
					customCheck.setFraudPredictStatus(customCheckSummary.getFraudPredictStatus());
				}
				if (resultSet.getString(Constants.UPDATED_ON) != null) {
					customCheck.setCheckedOn(
							DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				}
				domainList.add((IDomain) customCheck);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	/**
	 * @param velocityCheck
	 * @param whitelistCheck
	 * @param customCheckSummary
	 */
	private void getWhiteListandVelocityCheckSummary(VelocityCheck velocityCheck, WhitelistCheck whitelistCheck,
			EuPoiCheck euPoiCheck,CustomCheckSummary customCheckSummary) {
		if (customCheckSummary != null) {
			if (customCheckSummary.getWhiteListCheck() != null) {
				whitelistCheck.setCurrency(customCheckSummary.getWhiteListCheck().getCurrency());
				if (Constants.NOT_REQUIRED
						.equalsIgnoreCase(customCheckSummary.getWhiteListCheck().getReasonOfTransfer())) {
					whitelistCheck.setReasonOfTransfer(Constants.NOT_REQUIRED_UI);
				} else {
					whitelistCheck.setReasonOfTransfer(customCheckSummary.getWhiteListCheck().getReasonOfTransfer());
				}
				whitelistCheck.setThirdParty(customCheckSummary.getWhiteListCheck().getThirdParty());
			}
			setAmountRangeStatus(whitelistCheck, customCheckSummary);
			if (customCheckSummary.getVelocityCheck() != null) {
				setVelocityAmountCheckStatus(velocityCheck, customCheckSummary);
				velocityCheck.setBeneCheck(customCheckSummary.getVelocityCheck().getBeneCheck());
				String noOfFundsOutTxnStatus = customCheckSummary.getVelocityCheck().getNoOffundsoutTxn();
				setNoOfFundsOutTxnStatus(velocityCheck, customCheckSummary, noOfFundsOutTxnStatus);
				velocityCheck.setMatchedAccNumber(customCheckSummary.getVelocityCheck().getMatchedAccNumber());
			}
			
           setEUPoiCheckStatus(euPoiCheck, customCheckSummary);
           
		}
	}

	private void setEUPoiCheckStatus(EuPoiCheck euPoiCheck, CustomCheckSummary customCheckSummary) {
		if(customCheckSummary.getEuPoiCheck()!=null)
           {
        	   if(Constants.NOT_REQUIRED.equalsIgnoreCase(customCheckSummary.getEuPoiCheck().getStatus()))
        	   {
        		   euPoiCheck.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(Constants.NOT_REQUIRED));//AT- 3349
				} else {
					euPoiCheck.setStatus(customCheckSummary.getEuPoiCheck().getStatus());
				}
           }
           else
           {
        	   euPoiCheck.setStatus("- - -");
           }
	}

	/**
	 * Sets the amount range status.
	 *
	 * @param whitelistCheck
	 *            the whitelist check
	 * @param customCheckSummary
	 *            the custom check summary
	 */
	private void setAmountRangeStatus(WhitelistCheck whitelistCheck, CustomCheckSummary customCheckSummary) {
		String amountrangeStatus = customCheckSummary.getWhiteListCheck().getAmoutRange();
		if (Constants.FAIL.equalsIgnoreCase(amountrangeStatus)
				&& null != customCheckSummary.getWhiteListCheck().getMaxAmount()) {
			whitelistCheck.setAmoutRange(customCheckSummary.getWhiteListCheck().getAmoutRange());
			whitelistCheck.setMaxAmount(
					StringUtils.getNumberFormat(customCheckSummary.getWhiteListCheck().getMaxAmount().toString()));
		} else {
			whitelistCheck.setAmoutRange(customCheckSummary.getWhiteListCheck().getAmoutRange());
		}

	}

	/**
	 * Sets the velocity amount check status.
	 *
	 * @param velocityCheck
	 *            the velocity check
	 * @param customCheckSummary
	 *            the custom check summary
	 */
	private void setVelocityAmountCheckStatus(VelocityCheck velocityCheck, CustomCheckSummary customCheckSummary) {
		String permittedAmountCheckStatus = customCheckSummary.getVelocityCheck().getPermittedAmoutcheck();
		if (Constants.FAIL.equalsIgnoreCase(permittedAmountCheckStatus)
				&& null != customCheckSummary.getVelocityCheck().getMaxAmount()) {
			velocityCheck.setPermittedAmoutcheck(customCheckSummary.getVelocityCheck().getPermittedAmoutcheck());
			velocityCheck.setMaxAmount(
					StringUtils.getNumberFormat(customCheckSummary.getVelocityCheck().getMaxAmount().toString()));
		} else {
			velocityCheck.setPermittedAmoutcheck(customCheckSummary.getVelocityCheck().getPermittedAmoutcheck());
		}
	}

	/**
	 * Method added to show reason of FAIL for No. Of transaction check at the
	 * time of Payment Out -Vishal J
	 */
	private void setNoOfFundsOutTxnStatus(VelocityCheck velocityCheck, CustomCheckSummary customCheckSummary,
			String noOfFundsOutTxnStatus) {
		if (Constants.FAIL.equals(noOfFundsOutTxnStatus)) {
			velocityCheck.setNoOffundsoutTxn(customCheckSummary.getVelocityCheck().getNoOffundsoutTxn() + " "
					+ customCheckSummary.getVelocityCheck().getNoOfFundsOutTxnDetails());
		} else {
			velocityCheck.setNoOffundsoutTxn(customCheckSummary.getVelocityCheck().getNoOffundsoutTxn());
		}
	}

	protected List<IDomain> getMoreCountryCheckDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				CountryCheck countryCheck = new CountryCheck();
				countryCheck.setEntityType(resultSet.getString("entityid"));
				countryCheck.setId(resultSet.getInt("id"));
				countryCheck.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(resultSet.getInt("status")));
				countryCheck
						.setCheckedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				domainList.add((IDomain) countryCheck);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	protected List<UploadDocumentTypeDBDto> getUploadDocumentList(Connection connection)
			throws CompliancePortalException {
		List<UploadDocumentTypeDBDto> documentType = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_DOCUMENT_LIST.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UploadDocumentTypeDBDto uploadDocument = new UploadDocumentTypeDBDto();
				uploadDocument.setId(resultSet.getInt("Id"));
				uploadDocument.setDocumentName(resultSet.getString("Name"));
				documentType.add(uploadDocument);
			}
			return documentType;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * @param accountId
	 * @param custType
	 * @param id
	 * @return paginationData
	 */
	protected PaginationData getPaginationData(String accountId, String custType, String id) {
		PaginationData paginationData = new PaginationData();
		paginationData.setAccountid(accountId);
		paginationData.setCustType(custType);
		paginationData.setId(id);
		return paginationData;
	}

	/**
	 * @param connection
	 * @param registrationSearchCriteria
	 * @param query
	 * @return PaginationDetails
	 * @throws CompliancePortalException
	 */
	protected PaginationDetails getPaginationDetailQuery(Connection connection,
			RegistrationSearchCriteria registrationSearchCriteria, String query) throws CompliancePortalException {

		PreparedStatement statement = null;
		ResultSet rs = null;
		PaginationDetails paginationDetails = new PaginationDetails();

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, 1);
			statement.setInt(2, registrationSearchCriteria.getPage().getCurrentRecord() - 1);
			statement.setInt(3, registrationSearchCriteria.getPage().getCurrentRecord() + 1);
			statement.setInt(4, registrationSearchCriteria.getPage().getTotalRecords());

			rs = statement.executeQuery();

			while (rs.next()) {
				if (registrationSearchCriteria.getPage().getCurrentRecord() - 1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setPrevRecord(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString("type"), rs.getString(Constants.DB_CONTACT_ID)));

				}
				if (registrationSearchCriteria.getPage().getCurrentRecord() + 1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setNextRecord(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString("type"), rs.getString(Constants.DB_CONTACT_ID)));

				}
				if (registrationSearchCriteria.getPage().getTotalRecords() == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setTotalRecords(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString("type"), rs.getString(Constants.DB_CONTACT_ID)));
				}
				if (1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setFirstRecord(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString("type"), rs.getString(Constants.DB_CONTACT_ID)));
				}
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return paginationDetails;

	}

	/**
	 * @param baseUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	protected void addWatchlistLog(BaseUpdateDBDto baseUpdateDBDto) throws CompliancePortalException {

		List<String> delWatchlist = baseUpdateDBDto.getDeletedWatchlist();
		List<String> addedWatchlist = baseUpdateDBDto.getAddWatchlist();

		try {
			String log = getLogFromWatchlist(baseUpdateDBDto, delWatchlist, addedWatchlist);
			if (log != null) {
				ProfileActivityLogDto watchlistActivity = getProfileActivityLog(baseUpdateDBDto, log,
						ActivityType.PROFILE_WATCHLIST);
				if (watchlistActivity.getAccountId() != null) {
					baseUpdateDBDto.getActivityLog().add(watchlistActivity);
				}
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	private String getLogFromWatchlist(BaseUpdateDBDto baseUpdateDBDto, List<String> delWatchlist,
			List<String> addedWatchlist) {
		String preWatchlist = getPreviousWatchlist(baseUpdateDBDto.getWatchlist());
		if (delWatchlist.isEmpty() && addedWatchlist.isEmpty()) {
			return null;
		} else if (!addedWatchlist.isEmpty() && !delWatchlist.isEmpty()) {
			return getActivityLogsForAddAndDeleteWatchlist(preWatchlist, addedWatchlist, delWatchlist);
		} else if (!addedWatchlist.isEmpty()) {
			return getActivityLogsForAddedWatchlist(preWatchlist, addedWatchlist);
		} else {
			return getActivityLogsForDeletedWatchlist(preWatchlist, delWatchlist);
		}
	}

	private String getActivityLogsForAddAndDeleteWatchlist(String preWatchlist, List<String> addedWatchlistList,
			List<String> delWatchlistList) {
		String result = null;
		if (null == preWatchlist)
			return result;
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
		ArrayList<String> current = new ArrayList<>();
		current.addAll(arrayList);
		for (String s : addedWatchlistList) {
			current.add(s);
		}
		for (String s : arrayList) {
			if (delWatchlistList.contains(s)) {
				current.remove(s);
			}
		}
		String currents = current.toString();
		String prevWatch = preWatchlist.replace(",", ", ");
		currents = currents.substring(1, currents.length() - 1);
		result = ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, prevWatch)
				.replace(CURRENT_WATCHLIST, currents);
		return result;
	}

	private String getActivityLogsForDeletedWatchlist(String preWatchlist, List<String> deletedWatchlistList) {
		if (null == preWatchlist)
			return null;
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
		ArrayList<String> current = new ArrayList<>();

		for (String s : arrayList) {
			if (!deletedWatchlistList.contains(s)) {
				current.add(s);
			}
		}

		String prevWatch = preWatchlist.replace(",", ", ");
		if (current.isEmpty()) {
			return ActivityTemplateEnum.ALL_WATHCLIST_DELETED.getTemplate().replace(PRE_WATCHLIST, prevWatch);

		}

		String currents = current.toString();
		currents = currents.substring(1, currents.length() - 1).replace(", ", ", ");
		return ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, prevWatch).replace(CURRENT_WATCHLIST,
				currents);
	}

	private ProfileActivityLogDto getProfileActivityLog(BaseUpdateDBDto baseUpdateDBDto, String log,
			ActivityType activityType) {

		ProfileActivityLogDto activityLogDto = new ProfileActivityLogDto();
		activityLogDto.setAccountId(baseUpdateDBDto.getAccountId());
		activityLogDto.setContactId(baseUpdateDBDto.getContactId());
		activityLogDto.setOrgCode(baseUpdateDBDto.getOrgCode());
		activityLogDto.setComment(baseUpdateDBDto.getComment());
		activityLogDto.setActivityBy(baseUpdateDBDto.getCreatedBy());
		activityLogDto.setCreatedBy(baseUpdateDBDto.getCreatedBy());
		activityLogDto.setUpdatedBy(baseUpdateDBDto.getCreatedBy());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		activityLogDto.setCreatedOn(timestamp);
		activityLogDto.setTimeStatmp(timestamp);
		activityLogDto.setUpdatedOn(timestamp);
		ActivityLogDetailDto activityLogDetailDto = new ActivityLogDetailDto();
		activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		activityLogDetailDto.setActivityType(activityType);
		activityLogDetailDto.setLog(log);
		activityLogDetailDto.setCreatedBy(baseUpdateDBDto.getCreatedBy());
		activityLogDetailDto.setUpdatedBy(baseUpdateDBDto.getCreatedBy());
		activityLogDetailDto.setCreatedOn(timestamp);
		activityLogDetailDto.setUpdatedOn(timestamp);
		return activityLogDto;
	}

	private String getPreviousWatchlist(WatchlistUpdateRequest[] request) {
		StringBuilder preWatchlist = new StringBuilder();
		boolean isEle = false;
		for (WatchlistUpdateRequest watchlist : request) {

			if (watchlist.getName() == null || watchlist.getName().isEmpty()) {
				continue;
			}

			if (Boolean.TRUE.equals(watchlist.getPreValue())) {
				isEle = true;
				preWatchlist.append(watchlist.getName()).append(",");
			}
		}
		if (isEle) {
			preWatchlist.setLength(preWatchlist.length() - 1);
		}
		if (preWatchlist.length() != 0) {
			return preWatchlist.toString();
		}
		return null;

	}

	private String getActivityLogsForAddedWatchlist(String preWatchlist, List<String> addedWatchlistList) {
		ArrayList<String> current = new ArrayList<>();
		String addWatchlistFormat;
		if (preWatchlist != null && !preWatchlist.isEmpty()) {
			ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
			current.addAll(arrayList);
			for (String s : addedWatchlistList) {
				current.add(s);
			}
		} else {
			current.addAll(addedWatchlistList);
		}

		String currents = current.toString();
		currents = currents.substring(1, currents.length() - 1).replace(", ", ", ");
		if (preWatchlist != null) {
			String prevWatch = preWatchlist.replace(",", ", ");
			addWatchlistFormat = ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, prevWatch);
		} else {
			addWatchlistFormat = ActivityTemplateEnum.ADD_WATCHLIST.getTemplate();
		}
		return addWatchlistFormat.replace(CURRENT_WATCHLIST, currents);
	}

	/**
	 * @param requestHandlerDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	protected void deleteWatchlist(BaseUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		Integer accountId = requestHandlerDto.getAccountId();
		Integer contactId = requestHandlerDto.getContactId();
		List<String> delWatchlist = requestHandlerDto.getDeletedWatchlist();

		if (delWatchlist != null && !delWatchlist.isEmpty()) {
			String[] delwatchlistArray = delWatchlist.toArray(new String[delWatchlist.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_WATCHLIST_REASON.getQuery());
			String query = builder.addCriteria("Reason", delwatchlistArray, "AND", ClauseType.IN).build();
			//Changes for AT-2986
			if(requestHandlerDto.getCustType().equals("PFX")){
				query = "DELETE ContactWatchList WHERE Contact =" + contactId + " AND Reason IN(" + query + ")";
			}else{
			query = "DELETE ContactWatchList WHERE Contact IN (SELECT Id FROM Contact WHERE AccountId =" + accountId
					+ ") AND Reason IN(" + query + ")";
			}
			deletePaymentReasonOrWatchlist(query, connection);
		}
	}

	
	/**
	 * @param baseUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	protected void addProfileActivityLogs(BaseUpdateDBDto baseUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		List<ProfileActivityLogDto> activityLogs = baseUpdateDBDto.getActivityLog();
		if (activityLogs != null && !activityLogs.isEmpty()) {
			insertProfileActivityLogs(activityLogs, connection);
			insertProfileActivityLogDetail(activityLogs, connection);
		}
	}

	/**
	 * Insert Profile Activity Log Detail
	 * 
	 * @param activityLogDtos
	 * @param connection
	 * @throws CompliancePortalException
	 */
	@SuppressWarnings("resource")
	public void insertProfileActivityLogDetail(List<ProfileActivityLogDto> activityLogDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement(RegistrationQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL.getQuery());
			for (ProfileActivityLogDto profileActivityLog : activityLogDto) {
				ActivityLogDetailDto actvtyLogDetailDto = profileActivityLog.getActivityLogDetailDto();
				statement.setInt(1, actvtyLogDetailDto.getActivityId());
				statement.setString(2, actvtyLogDetailDto.getActivityType().getModule());
				statement.setString(3, actvtyLogDetailDto.getActivityType().getType());
				statement.setNString(4, actvtyLogDetailDto.getLog());
				statement.setNString(5, actvtyLogDetailDto.getCreatedBy());
				statement.setTimestamp(6, actvtyLogDetailDto.getCreatedOn());
				statement.setNString(7, actvtyLogDetailDto.getUpdatedBy());
				statement.setTimestamp(8, actvtyLogDetailDto.getUpdatedOn());
				statement.addBatch();
			}
			int[] count = statement.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
			}

		} catch (CompliancePortalException e) {
			throw e;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(statement);
		}

	}

	/**
	 * @param query
	 * @param connection
	 * @throws CompliancePortalException
	 */
	protected void deletePaymentReasonOrWatchlist(String query, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int delRowCnt = preparedStatement.executeUpdate();
			if (delRowCnt == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_DELETING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	protected String getResponseStatus(PaymentUpdateDBDto paymentUpdateDBDto) {

		if (paymentUpdateDBDto.getAddReasons() != null && !(paymentUpdateDBDto.getAddReasons().isEmpty())) {
			return paymentUpdateDBDto.getAddReasons().get(0);

		} else if (paymentUpdateDBDto.getPreviousReason() != null
				&& !(paymentUpdateDBDto.getPreviousReason().isEmpty())) {
			return paymentUpdateDBDto.getPreviousReason().get(0);

		} else {
			return Constants.STATUS_UPDATED_MANUALLY;
		}
	}

	protected PaymentComplianceStatus getPaymentComplianceStatus(String paymentInStatus) {
		try {
			return PaymentComplianceStatus.valueOf(paymentInStatus.toUpperCase());
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Exception :{1}", e);
			return null;
		}
	}

	protected void updateWorkFlowTime(Integer resourceId, Connection connection) throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_WORKFLOW_TIME.getQuery());
			preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(2, resourceId);
			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}

	}

	protected void updateWatchlistStatus(BaseUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			if(requestHandlerDto.getCustType().equals("PFX")){
				preparedStatement = connection
						.prepareStatement(RegistrationQueryConstant.UPDATE_WATCHLISTSTATUS_CONTACT.getQuery());
				preparedStatement.setInt(5, requestHandlerDto.getContactId());
			}else{
				preparedStatement = connection
						.prepareStatement(RegistrationQueryConstant.UPDATE_WATCHLISTSTATUS_ACCOUNT.getQuery());
				preparedStatement.setInt(5, requestHandlerDto.getAccountId());
			}
			preparedStatement.setString(1, requestHandlerDto.getCreatedBy());
			preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			if (requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus().equals(2)) {
				preparedStatement.setInt(3, ServiceStatusEnum.FAIL.getDatabaseStatus());
			} else if (requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus().equals(7)) {
				preparedStatement.setInt(3, ServiceStatusEnum.WATCH_LIST.getDatabaseStatus());
			}  else {
				preparedStatement.setInt(3, ServiceStatusEnum.PASS.getDatabaseStatus());
			}

			if (requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus().equals(2)) {
				preparedStatement.setInt(4, ServiceStatusEnum.FAIL.getDatabaseStatus());
			} else if (requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus().equals(7)) {
				preparedStatement.setInt(4, ServiceStatusEnum.WATCH_LIST.getDatabaseStatus());
			}  else {
				preparedStatement.setInt(4, ServiceStatusEnum.PASS.getDatabaseStatus());
			}

			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}

	}

	protected void getWatchListStatus(BaseUpdateDBDto requestHandlerDto, Connection readOnlyConn)
			throws CompliancePortalException {

		List<WatchListDetails> watchListPaymentReasons = new ArrayList<>();
		List<String> toatalContactWatchListReason = new ArrayList<>();
		List<String> finalWatchList = new ArrayList<>();
		try {
			requestHandlerDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.PASS);
			requestHandlerDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.PASS);
			getPaymentWatchListReasons(readOnlyConn, watchListPaymentReasons);
			getTotalContactWatchListReasons(readOnlyConn, toatalContactWatchListReason,
					requestHandlerDto);
			List<String> delWatchlist = requestHandlerDto.getDeletedWatchlist();
			List<String> addWatchlist = requestHandlerDto.getAddWatchlist();

			if (!toatalContactWatchListReason.isEmpty()) {
				finalWatchList.addAll(toatalContactWatchListReason);
			}

			removeDeletedReasonFromWatchList(toatalContactWatchListReason, delWatchlist, finalWatchList);
			addReasonToWatchList(finalWatchList, addWatchlist);
			getFinalWatchListStatus(requestHandlerDto, watchListPaymentReasons, finalWatchList);
			
			requestHandlerDto.setIsWatchlistUpdated(Boolean.FALSE);
			if(!delWatchlist.isEmpty() || !addWatchlist.isEmpty()) {
				requestHandlerDto.setIsWatchlistUpdated(Boolean.TRUE);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}

	}

	@Override
	protected String addSort(String columnName, boolean isAsc, String query) {
		String orderByCon = "{ORDER_TYPE}";
		if (columnName != null) {
			query = query.replace("{SORT_FIELD_NAME}", columnName);
			if (isAsc) {
				query = query.replace(orderByCon, "ASC");
			} else {
				query = query.replace(orderByCon, "DESC");
			}
		}
		return query;
	}

	protected String getAlertComplianceLog(Integer accountId) throws CompliancePortalException {
		String alertComplianceLog = Constants.DASH_DETAILS_PAGE;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = getConnection(Boolean.TRUE);
			prepareStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_ALERT_COMPLIANCE_LOG.getQuery());
			prepareStatement.setInt(1, accountId);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				alertComplianceLog = resultSet.getString("Comments");
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}

		return alertComplianceLog;
	}

	protected void setFraugsterData(BaseUpdateDBDto baseUpdateDBDto, Connection conn, Connection readOnlyConn,
			Integer eventType, String status) throws CompliancePortalException {
		try {
			String asyncStatus;

			if (Constants.CLEAR.equalsIgnoreCase(status)) {
				List<FraugsterSummary> summaryList = getFraugsterEventServiceLogForPayment(readOnlyConn,
						baseUpdateDBDto.getContactId(), eventType);
				updateFraugsterSchedularDataOnReasonAdded(baseUpdateDBDto, null, conn, summaryList);
			} else {

				Boolean isWatchListAdded = isWatchListAdded(baseUpdateDBDto);
				Boolean isWatchListRemoved = isWatchListRemoved(baseUpdateDBDto);

				if (Boolean.TRUE.equals(isWatchListAdded)) {

					asyncStatus = FragusterWatchListEnum.FRAUGSTER_HIGH_RISK_OF_FRAUD.getWatchlistCode();
					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByAccountId(readOnlyConn,
							baseUpdateDBDto.getAccountId());
					updateFraugsterSchedularDataOnWLAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);

				} else if (Boolean.TRUE.equals(isWatchListRemoved)) {

					asyncStatus = FragusterWatchListEnum.APPROVED.getWatchlistCode();
					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByAccountId(readOnlyConn,
							baseUpdateDBDto.getAccountId());
					updateFraugsterSchedularDataOnWLAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);
				}

				if (Boolean.FALSE.equals(isWatchListAdded) && Boolean.FALSE.equals(isWatchListRemoved)) {
					setAsyncStatusForPaymentReason(baseUpdateDBDto, conn, readOnlyConn, eventType);
				}
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}

	private String setAsyncStatusForPaymentReason(BaseUpdateDBDto baseUpdateDBDto, Connection conn,
			Connection readOnlyConn, Integer eventType) throws CompliancePortalException {

		Boolean isReasonAdded = Boolean.FALSE;
		Boolean isReasonRemoved = Boolean.FALSE;
		String asyncStatus = FragusterWatchListEnum.APPROVED.getWatchlistCode();

		try {
			isReasonAdded = isReasonAddded(baseUpdateDBDto);
			isReasonRemoved = isReasonRemoved(baseUpdateDBDto);

			if (Boolean.TRUE.equals(isReasonAdded)) {
				for (String reason : baseUpdateDBDto.getAddReasons()) {
					asyncStatus = FraugsterReasonsEnum.getFraugsterReasonCode(reason);
				}
				List<FraugsterSummary> summaryList = getFraugsterEventServiceLogForPayment(readOnlyConn,
						baseUpdateDBDto.getContactId(), eventType);
				updateFraugsterSchedularDataOnReasonAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);

			} else if (Boolean.TRUE.equals(isReasonRemoved)) {
				asyncStatus = FraugsterReasonsEnum.APPROVED.getReasonCode();
				List<FraugsterSummary> summaryList = getFraugsterEventServiceLogForPayment(readOnlyConn,
						baseUpdateDBDto.getContactId(), eventType);
				updateFraugsterSchedularDataOnReasonAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}

		return asyncStatus;
	}
	
	//Added for AT-3658
	protected List<IDomain> getMorePaymentReferenceCheckDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				PaymentReferenceCheck paymentReferenceCheck = new PaymentReferenceCheck();
				BlacklistPayRefSummary blacklistPayRefSummary = JsonConverterUtil.convertToObject(BlacklistPayRefSummary.class,
						resultSet.getString(Constants.SUMMARY));
				paymentReferenceCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				paymentReferenceCheck.setPaymentReference(blacklistPayRefSummary.getPaymentReference());
				paymentReferenceCheck.setClosenessScore(blacklistPayRefSummary.getTokenSetRatio());
				paymentReferenceCheck.setOverallStatus(blacklistPayRefSummary.getStatus());
				paymentReferenceCheck.setMatchedKeyword(blacklistPayRefSummary.getSanctionText());
				paymentReferenceCheck.setId(resultSet.getInt("id"));
				domainList.add((IDomain) paymentReferenceCheck);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}
	
	//Added for AT-4306
	protected List<IDomain> getMoreIntuitionCheckDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				IntuitionPaymentResponse intuition = new IntuitionPaymentResponse();
				TransactionMonitoringPaymentsSummary intuitionSummary = JsonConverterUtil
						.convertToObject(TransactionMonitoringPaymentsSummary.class, resultSet.getString(Constants.SUMMARY));
				intuition
						.setCreatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				intuition.setCorrelationId(intuitionSummary.getCorrelationId());
				intuition.setClientRiskLevel(intuitionSummary.getClientRiskLevel());
				intuition.setClientRuleScore(intuitionSummary.getClientRiskScore());
				intuition.setRuleRiskLevel(intuitionSummary.getRuleRiskLevel());
				intuition.setRuleScore(intuitionSummary.getRuleScore());
				intuition.setStatus(intuitionSummary.getStatus());
				//Add for AT-5028
				if (intuitionSummary.getAction() == null && (intuitionSummary.getStatus().equalsIgnoreCase(Constants.CLEAN)
						|| intuitionSummary.getStatus().equalsIgnoreCase(Constants.CLEAR))) {
					intuition.setDecision(intuitionSummary.getStatus());
				} else if (intuitionSummary.getAction() == null && intuitionSummary.getStatus().equalsIgnoreCase(Constants.HOLD)) {
					intuition.setDecision(intuitionSummary.getStatus());
				} else if (intuitionSummary.getAction() != null
						&& (intuitionSummary.getAction().equalsIgnoreCase(Constants.CLEAR)
								|| intuitionSummary.getAction().equalsIgnoreCase(Constants.CLEAN))
						&& intuitionSummary.getStatus().equalsIgnoreCase(Constants.HOLD)) {
					intuition.setDecision(intuitionSummary.getAction());
				} else if (intuitionSummary.getAction() != null && (intuitionSummary.getAction().equalsIgnoreCase(Constants.REJECT)
						|| intuitionSummary.getAction().equalsIgnoreCase(Constants.SEIZE))
						&& intuitionSummary.getStatus().equalsIgnoreCase(Constants.HOLD)) {
					intuition.setDecision(intuitionSummary.getAction());
				}
				
				intuition.setUpdatedBy(intuitionSummary.getUserId());
				intuition.setId(resultSet.getInt("id"));
				domainList.add((IDomain) intuition);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}
}
