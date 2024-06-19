package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public interface ISavedSearchDBService {

	public boolean savedSearch(UserProfile user, SavedSearchRequest savedSearchRequest) throws CompliancePortalException;
	
	public boolean deleteSavedSearch(UserProfile user, SavedSearchRequest savedSearchRequest) throws CompliancePortalException;
	
	public boolean updateSavedSearch(UserProfile user, SavedSearchRequest savedSearchRequest) throws CompliancePortalException;
	
	public int getSavedSearchCount(String pagetType, int id) throws CompliancePortalException;

}
