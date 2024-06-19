package com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Interface IWhitelistBeneficiaryModifier.
 */
public interface IWhitelistBeneficiaryModifier {
	
	
	/**
	 * Save into whitelist beneficiary data.
	 *
	 * @param request the request
	 * @return the whitelist beneficiary response
	 * @throws InternalRuleException the internal rule exception
	 */
	public WhitelistBeneficiaryResponse saveIntoWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request) throws InternalRuleException;

	/**
	 * Gets the whitelist beneficiary data.
	 *
	 * @param request the request
	 * @return the whitelist beneficiary data
	 * @throws InternalRuleException the internal rule exception
	 */
	public List<WhitelistBeneficiaryResponse> getWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request) throws InternalRuleException;
	
	/**
	 * Delete from white list beneficiary data.
	 *
	 * @param request the request
	 * @return the whitelist beneficiary response
	 * @throws InternalRuleException the internal rule exception
	 */
	public WhitelistBeneficiaryResponse deleteFromWhiteListBeneficiaryData(WhitelistBeneficiaryRequest request) throws InternalRuleException;
	
	/**
	 * Search white list beneficiary data.
	 *
	 * @param request the request
	 * @return the list
	 * @throws InternalRuleException the internal rule exception
	 */
	public List<WhitelistBeneficiaryResponse> searchWhiteListBeneficiaryData(WhitelistBeneficiaryRequest request) throws InternalRuleException;

	

}
