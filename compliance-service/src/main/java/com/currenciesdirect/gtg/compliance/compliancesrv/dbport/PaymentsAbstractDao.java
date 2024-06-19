/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * @author manish
 *
 */
@SuppressWarnings("squid:S2095")
public class PaymentsAbstractDao extends AbstractRegistrationDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PaymentsAbstractDao.class);

	/**
	 * @param tradeAccountNumber
	 * @return
	 * @throws ComplianceException
	 */
	public Account getAccountByTradeAccountNumber(String tradeAccountNumber,String orgCode) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		Account accountID = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ACCOUNT_BY_TRADE_ACCOUNT_NUMBER.getQuery());
			preparedStatment.setString(1, tradeAccountNumber);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				String json = rs.getString("attributes");
				accountID = JsonConverterUtil.convertToObject(Account.class, json);
				accountID.setId(rs.getInt("ID"));
				accountID.setVersion(rs.getInt("version"));
				accountID.setLeUpdateDate(rs.getString("LEUpdateDate"));//Add for AT-3349
				accountID.setAccountStatus(rs.getString("AccountStatus"));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return accountID;
	}

	/**
	 * @param tradeContactID
	 * @return
	 * @throws ComplianceException
	 */
	public Contact getContactByTradeContactID(Integer tradeContactID,String orgCode) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		Contact contactID = null;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.CHECK_TRADE_CONTACT_ID_EXIST.getQuery());
			preparedStatment.setInt(1, tradeContactID);
			preparedStatment.setString(2, orgCode);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				String json = rs.getString("attributes");
				contactID = JsonConverterUtil.convertToObject(Contact.class, json);
				contactID.setId(rs.getInt("ID"));
				contactID.setVersion(rs.getInt("version"));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return contactID;
	}

	/**
	 * @param contactID
	 * @return
	 * @throws ComplianceException 
	 */
	public boolean isContactOnWatchList(Integer contactID, OperationEnum paymentType) throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		boolean isContactOnWatchList = false;
		ResultSet rs = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.IS_CONTACT_ON_WATCHLIST.getQuery());
			preparedStatment.setInt(1, contactID);

			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				if (OperationEnum.FUNDS_IN==paymentType) {
					isContactOnWatchList = rs.getBoolean("StopPaymentIn");
				}
				if (OperationEnum.FUNDS_OUT==paymentType) {
					isContactOnWatchList = rs.getBoolean("StopPaymentOut");
				}
			}

		} catch (Exception e) {
			LOG.error("Exception in isContactOnWatchList method:", e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return isContactOnWatchList;
	}
}