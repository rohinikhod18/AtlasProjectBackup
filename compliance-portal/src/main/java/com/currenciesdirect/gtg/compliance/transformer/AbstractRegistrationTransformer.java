package com.currenciesdirect.gtg.compliance.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationContact;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;

public abstract class AbstractRegistrationTransformer  {
	
	protected void setBlacklistPassAndFailCount(Blacklist blacklist) {
		Integer passCount = 0;
		Integer failCount = 0;
		
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getName())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getName())) {
			failCount++;
		}
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getEmail())) {
			passCount++;
		} else if(Constants.TRUE.equalsIgnoreCase(blacklist.getEmail())) {
			failCount++;
		}
		/**
		 * Condition changed by Vishal J to get PASS count or FAIL count of IP check
		 * */
		if(Constants.FALSE.equalsIgnoreCase(blacklist.getIp())) {
			passCount++;
		} else if(Constants.TRUE.equalsIgnoreCase(blacklist.getIp())) {
			failCount++;
		}
		
		if (Boolean.TRUE.equals(blacklist.getPhone())) {
			passCount++;
		} else {
			failCount++;
		}
		/**removed count of status because it is not necessary to consider 'overall status' count while showing
		indicator to resolve AT-338 by Vishal J*/
		
		/**
		 * Added by Saylee B to show count according to domain check
		 * */
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getDomain())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getDomain())) {
			failCount++;
		}
		

		blacklist.setPassCount(passCount);
		blacklist.setFailCount(failCount);
	}
	
	protected void setBlacklistAccountPassAndFailCount(Blacklist blacklist) {
		Integer passCount = 0;
		Integer failCount = 0;
		
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getName())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getName())) {
			failCount++;
		}
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getWebSite())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getWebSite())) {
			failCount++;
		}
		/**removed count of status because it is not necessary to consider 'overall status' count while showing
		indicator to resolve AT-338 by Vishal J*/
		
		blacklist.setPassCount(passCount);
		blacklist.setFailCount(failCount);
	}
	
	/**
	 * 1)Method added by Vishal J to show PASS count or FAIL count for Blacklist check (CFX Registration)
	 *   of entity type 'CONTACT'
	 * 2)For 'CONTACT' we need to check its IP, EMAIL, and PHONE 
	 *   so we are setting count from their response.
	 * */
	protected void setBlacklistContactPassAndFailCount(Blacklist blacklist) {
		Integer passCount = 0;
		Integer failCount = 0;
		
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getEmail())) {
			passCount++;
		} else if(Constants.TRUE.equalsIgnoreCase(blacklist.getEmail())) {
			failCount++;
		}
		/**Method added to get Name count to resolve AT-322 by Vishal J*/
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getName())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getName())) {
			failCount++;
		}	
		if (Boolean.TRUE.equals(blacklist.getPhone())) {
			passCount++;
		} else {
			failCount++;
		}
		/**
		 * Removed count of overall status when showing passcount or failcount on blacklist check 
		 * to resolve AT-338 by Vishal J
		 * */
				
		blacklist.setPassCount(passCount);
		blacklist.setFailCount(failCount);
	}
	//new method end
	
	// KYC is supported for 18 countries, so for rest, desable repeat check button
	// Same is applicable for E4F and CDSA orgs as well.
	// AT-3327 added one more condition for Legal Entity
	protected Boolean checkIsKycSupportedCountry(String country, List<String> kycSupportedCountryList, String orgName, String legalEntity) {
		boolean repeatButtonDisable = false;
		
		for (String kycSupportedCountry : kycSupportedCountryList) {
			if (country.equalsIgnoreCase(kycSupportedCountry)) {
				repeatButtonDisable = true;
			}
		}
		if(Constants.CD_SA.equals(orgName)|| Constants.E4F.equals(orgName) 
				|| LegalEntityEnum.CDLEU.getLECode().equals(legalEntity)
				|| LegalEntityEnum.FCGEU.getLECode().equals(legalEntity)
				|| LegalEntityEnum.TOREU.getLECode().equals(legalEntity)
				|| LegalEntityEnum.TORSG.getLECode().equals(legalEntity)) { // AT-4157 - For TORSG
			repeatButtonDisable = false;
		}
		return repeatButtonDisable;
	}
	
	/**
	 * this function gives complete address as per input fields
	 * @author abhijeetg
	 * @param regContact
	 * @return
	 */
	protected String getCompleteAddress(RegistrationContact regContact) {
		List<String> list=new ArrayList<>();
		if(!isNullOrEmpty(regContact.getAddress1())){
			list.add(regContact.getAddress1());
		}
		if(!isNullOrEmpty(regContact.getUnitNumber())){
			list.add(regContact.getUnitNumber());
		}
		if(!isNullOrEmpty(regContact.getStreetNumber())){
			list.add(regContact.getStreetNumber());
		}
		if(!isNullOrEmpty(regContact.getAreaNumber())){
			list.add(regContact.getAreaNumber());
		}
		if(!isNullOrEmpty(regContact.getCivicNumber())){
			list.add(regContact.getCivicNumber());
		}
		if(!isNullOrEmpty(regContact.getDistrict())){
			list.add(regContact.getDistrict());
		}
		if(!isNullOrEmpty(regContact.getState())){
			list.add(regContact.getState());
		}
		if(!isNullOrEmpty(regContact.getCity())){
			list.add(regContact.getCity());	
		}
		if(!isNullOrEmpty(regContact.getPostCode())){
			list.add(regContact.getPostCode());
		}
		if(!isNullOrEmpty(regContact.getCountry())){
			list.add(regContact.getCountry());
		}
		return String.join(", ", list);
	}

	protected static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.trim().isEmpty())
			return false;

		return result;
	}
	
	protected void setBlacklistedMatchedData(BlacklistSummary blacklistSummary ,Blacklist blacklist){
		if(null != blacklistSummary ){
			blacklist.setAccNumberMatchedData(blacklistSummary.getAccNumberMatchedData());
			blacklist.setDomainMatchedData(blacklistSummary.getDomainMatchedData());
			blacklist.setEmailMatchedData(blacklistSummary.getEmailMatchedData());
			blacklist.setIpMatchedData(blacklistSummary.getIpMatchedData());
			blacklist.setNameMatchedData(blacklistSummary.getNameMatchedData());
			blacklist.setPhoneMatchedData(blacklistSummary.getPhoneMatchedData());
			blacklist.setWebsiteMatchedData(blacklistSummary.getWebsiteMatchedData());
		}
		
	}
	
	protected Watchlist deleteDuplicateWatclist(Watchlist watchList) { //same function is present
		TreeMap<String, WatchListData> reasonMap = new TreeMap<>();
		ArrayList<WatchListData> watchListDataList = new ArrayList<>();

		for (WatchListData watch : watchList.getWatchlistData()) {
			reasonMap.put(watch.getName(), watch);
		}
		watchListDataList.addAll(reasonMap.values());
		watchList.setWatchlistData(watchListDataList);
		return watchList;
	}
}
