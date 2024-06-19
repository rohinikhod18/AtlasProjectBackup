package com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.core.blacklist.BlacklistModifierImpl;
import com.currenciesdirect.gtg.compliance.dbport.whitelistbeneficiary.WhitelistBeneficiaryDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class WhitelistBeneficiaryModifierImpl.
 */
public class WhitelistBeneficiaryModifierImpl implements IWhitelistBeneficiaryModifier {

	/** The i whitelist beneficiary. */
	@SuppressWarnings("squid:S3077")
	private static volatile IWhitelistBeneficiaryModifier iWhitelistBeneficiary = null;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WhitelistBeneficiaryModifierImpl.class);

	/** The i DB service. */
	private IWhitelistBeneficiaryDBService iDBService = WhitelistBeneficiaryDBServiceImpl.getInstance();

	/**
	 * Instantiates a new whitelist beneficiary modifier impl.
	 */
	private WhitelistBeneficiaryModifierImpl() {
	}

	/**
	 * Gets the single instance of WhitelistBeneficiaryModifierImpl.
	 *
	 * @return single instance of WhitelistBeneficiaryModifierImpl
	 */
	public static IWhitelistBeneficiaryModifier getInstance() {
		if (iWhitelistBeneficiary == null) {
			synchronized (BlacklistModifierImpl.class) {
				if (iWhitelistBeneficiary == null) {
					iWhitelistBeneficiary = new WhitelistBeneficiaryModifierImpl();
				}
			}
		}
		return iWhitelistBeneficiary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.
	 * IWhitelistBeneficiaryModifier#saveIntoWhitelistBeneficiaryData(com.
	 * currenciesdirect.gtg.compliance.commons.domain.internalruleservice.
	 * WhitelistBeneficiaryRequest)
	 */
	@Override
	public WhitelistBeneficiaryResponse saveIntoWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {
		LOG.debug("WhitelistBeneficiaryModifierImpl.saveIntoWhitelistBeneficiaryData : start");
		WhitelistBeneficiaryResponse response = new WhitelistBeneficiaryResponse();
		try {
			return iDBService.saveIntoWhitelistBeneficiary(request);

		} catch (InternalRuleException e) {
			response.setStatus("Fail");
			if (e.getErrors().getErrorCode()
					.equalsIgnoreCase(InternalRuleErrors.DATABASE_GENERIC_ERROR.getErrorCode())) {
				response.setErrorCode(InternalRuleErrors.DATABASE_GENERIC_ERROR.getErrorCode());
				response.setErrorDescription(InternalRuleErrors.DATABASE_GENERIC_ERROR.getErrorDescription());
			} else if (e.getErrors().getErrorCode()
					.equalsIgnoreCase(InternalRuleErrors.DUPLICATE_DATA_ADDITION.getErrorCode())) {
				response.setErrorCode(InternalRuleErrors.DUPLICATE_DATA_ADDITION.getErrorCode());
				response.setErrorDescription(InternalRuleErrors.DUPLICATE_DATA_ADDITION.getErrorDescription());
			} else if (e.getErrors().getErrorCode()
					.equalsIgnoreCase(InternalRuleErrors.INVALID_REQUEST.getErrorCode())) {
				response.setErrorCode(InternalRuleErrors.INVALID_REQUEST.getErrorCode());
				response.setErrorDescription(InternalRuleErrors.INVALID_REQUEST.getErrorDescription());
			} else {
				LOG.error("Exception in WhitelistBeneficiaryModifierImpl saveIntoWhitelistBeneficiaryData:", e);
			}
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR);
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.
	 * IWhitelistBeneficiaryModifier#getWhitelistBeneficiaryData(com.
	 * currenciesdirect.gtg.compliance.commons.domain.internalruleservice.
	 * WhitelistBeneficiaryRequest)
	 */
	@Override
	public List<WhitelistBeneficiaryResponse> getWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {

		LOG.debug("WhitelistBeneficiaryModifierImpl.getWhitelistBeneficiaryData : start");
		try {
			return iDBService.getWhitelistBeneficiaryData(request);
		} catch (InternalRuleException internalRuleException) {
			throw internalRuleException;
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.FAILED);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.
	 * IWhitelistBeneficiaryModifier#deleteFromWhiteListBeneficiaryData(com.
	 * currenciesdirect.gtg.compliance.commons.domain.internalruleservice.
	 * WhitelistBeneficiaryRequest)
	 */
	@Override
	public WhitelistBeneficiaryResponse deleteFromWhiteListBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {
		LOG.debug("WhitelistBeneficiaryModifierImpl.deleteFromWhiteListBeneficiaryData : start");
		try {
			return iDBService.deleteFromWhiteListBeneficiaryData(request);
		} catch (InternalRuleException internalRuleException) {
			throw internalRuleException;
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.FAILED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.
	 * IWhitelistBeneficiaryModifier#searchWhiteListBeneficiaryData(com.
	 * currenciesdirect.gtg.compliance.commons.domain.internalruleservice.
	 * WhitelistBeneficiaryRequest)
	 */
	@Override
	public List<WhitelistBeneficiaryResponse> searchWhiteListBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {
		LOG.debug("WhitelistBeneficiaryModifierImpl.searchWhiteListBeneficiaryData : start");
		try {
			return iDBService.searchWhiteListBeneficiaryData(request);
		} catch (InternalRuleException internalRuleException) {
			throw internalRuleException;
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.FAILED);
		}
	}

}
