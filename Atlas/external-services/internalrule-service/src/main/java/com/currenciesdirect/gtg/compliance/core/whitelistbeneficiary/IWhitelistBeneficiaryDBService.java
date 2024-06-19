package com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

public interface IWhitelistBeneficiaryDBService {
	
	/**
	 * Gets the whitelist beneficiary data.
	 *
	 * @param request the request
	 * @return the whitelist beneficiary data
	 * @throws InternalRuleException the Internal Rule exception
	 */
	public List<WhitelistBeneficiaryResponse> getWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request) throws InternalRuleException;
	
	/**
	 * Save into whitelist beneficiary.
	 *
	 * @param whitelistBeneficiaryRequest the whitelist beneficiary request
	 * @return the whitelist beneficiary response
	 * @throws InternalRuleException the Internal Rule exception
	 */
	public WhitelistBeneficiaryResponse saveIntoWhitelistBeneficiary(WhitelistBeneficiaryRequest whitelistBeneficiaryRequest) throws InternalRuleException;
	
	/**
	 * Delete from white list beneficiary data.
	 *
	 * @param whitelistBeneficiaryRequest the whitelist beneficiary request
	 * @return the whitelist beneficiary response
	 * @throws InternalRuleException the Internal Rule exception
	 */
	public WhitelistBeneficiaryResponse deleteFromWhiteListBeneficiaryData(WhitelistBeneficiaryRequest whitelistBeneficiaryRequest) throws InternalRuleException;
	
	/**
	 * Search white list beneficiary data.
	 *
	 * @param whitelistBeneficiaryRequest the whitelist beneficiary request
	 * @return the list
	 * @throws InternalRuleException the Internal Rule exception
	 */
	public List<WhitelistBeneficiaryResponse> searchWhiteListBeneficiaryData(WhitelistBeneficiaryRequest whitelistBeneficiaryRequest) throws InternalRuleException;


}
