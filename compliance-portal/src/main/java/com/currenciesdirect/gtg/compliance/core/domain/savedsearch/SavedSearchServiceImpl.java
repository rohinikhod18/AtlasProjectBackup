package com.currenciesdirect.gtg.compliance.core.domain.savedsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.report.ISavedSearchDBService;
import com.currenciesdirect.gtg.compliance.core.report.ISavedSearchService;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

@Component("savedSearchServiceImpls")
public class SavedSearchServiceImpl implements ISavedSearchService{

	private Logger log = LoggerFactory.getLogger(SavedSearchServiceImpl.class);
	
	@Autowired
	@Qualifier("savedSearchDBServiceImpl")
	private ISavedSearchDBService iSavedSearchDBServiceResponse;
	
	@Override
	public boolean savedSearch(UserProfile user,SavedSearchRequest savedSearchRequest) {
		boolean saved = false;
		try {
			int count = iSavedSearchDBServiceResponse.getSavedSearchCount(savedSearchRequest.getPageType(), user.getId());
			if(count < 5) {
				saved = iSavedSearchDBServiceResponse.savedSearch(user, savedSearchRequest);
			}
		}
		catch(Exception e) {
			log.debug("Exception in savedSearch in SavedSearchController {1}", e);
		}
		return saved;
	}
	
	@Override
	public boolean deleteSavedSearch(UserProfile user,SavedSearchRequest savedSearchRequest) {
		boolean saved = false;
		try {
			saved = iSavedSearchDBServiceResponse.deleteSavedSearch(user, savedSearchRequest);
		}
		catch(Exception e) {
			log.debug("Exception in deleteSavedSearch in SavedSearchController {1}", e);
		}
		return saved;
	}
	
	@Override
	public boolean updateSavedSearch(UserProfile user,SavedSearchRequest savedSearchRequest) {
		boolean update = false;
		try {
			update = iSavedSearchDBServiceResponse.updateSavedSearch(user, savedSearchRequest);
		}
		catch(Exception e) {
			log.debug("Exception in updateSavedSearch in SavedSearchController {1}", e);
		}
		return update;
	}
}

















