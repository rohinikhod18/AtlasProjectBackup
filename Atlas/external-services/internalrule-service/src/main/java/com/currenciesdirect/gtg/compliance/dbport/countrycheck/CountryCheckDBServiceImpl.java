package com.currenciesdirect.gtg.compliance.dbport.countrycheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.countrycheck.ICountryCheckDBService;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckException;
import com.currenciesdirect.gtg.compliance.util.Constants;

/**
 * The Class CountryCheckDBServiceImpl.
 */
@SuppressWarnings("squid:S3077")
public class CountryCheckDBServiceImpl extends AbstractDao implements ICountryCheckDBService {

	/** The i DB service. */
	private static volatile ICountryCheckDBService iDBService = null;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CountryCheckDBServiceImpl.class);

	/**
	 * Instantiates a new country check DB service impl.
	 */
	private CountryCheckDBServiceImpl() {
	}

	/**
	 * Gets the single instance of CountryCheckDBServiceImpl.
	 *
	 * @return single instance of CountryCheckDBServiceImpl
	 */
	public static ICountryCheckDBService getInstance() {
		if (iDBService == null) {
			synchronized (CountryCheckDBServiceImpl.class) {
				if (iDBService == null) {
					iDBService = new CountryCheckDBServiceImpl();
				}
			}
		}
		return iDBService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.countrycheck.
	 * ICountryCheckDBService#getCountryCheckResponse(java.lang.String)
	 */
	@Override
	public CountryCheckContactResponse getCountryCheckResponse(String country) throws CountryCheckException {

		CountryCheckContactResponse response = null;
		ResultSet rs = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(DBQueryConstant.GET_COUNTRY_STATUS.getQuery())) {
			preparedStatement.setString(1, country);
			String riskLevel = null;
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				riskLevel = rs.getString("RiskLevel");
			}
			response = new CountryCheckContactResponse();
			response.setCountry(country);
			if (riskLevel == null) {
				response.setStatus(CountryCheckErrors.ERROR_COUNTRY_NOT_FOUND.getErrorDescription());
				return response;
			}
			response.setRiskLevel(riskLevel);
			if (RiskLevel.HIGHT_RISK.getRisk().equals(riskLevel)) {
				response.setStatus(Constants.WATCHLIST);
			} else if (RiskLevel.SANCTION_RISK.getRisk().equals(riskLevel)) {
				response.setStatus(Constants.FAIL);
			} else {
				response.setStatus(Constants.PASS);
			}

		} catch (Exception e) {
			throw new CountryCheckException(CountryCheckErrors.ERROR_WHILE_SEARCHING_DATA, e);
		} finally {
			try {
				closeResultset(rs);
			} catch (Exception e) {

				LOG.error("Error in FraugsterDBServiceIMPL:getFraugsterProviderInitConfigProperty() ", e);
			}
		}
		return response;
	}

	/**
	 * The Enum RiskLevel.
	 */
	public enum RiskLevel {

		/** The hight risk. */
		HIGHT_RISK("H"),
		/** The low risk. */
		LOW_RISK("L"),
		/** The sanction risk. */
		SANCTION_RISK("S");

		/** The risk. */
		private String risk;

		/**
		 * Instantiates a new risk level.
		 *
		 * @param risk
		 *            the risk
		 */
		RiskLevel(String risk) {
			this.risk = risk;
		}

		/**
		 * Gets the risk.
		 *
		 * @return the risk
		 */
		public String getRisk() {
			return this.risk;
		}
	}

}
