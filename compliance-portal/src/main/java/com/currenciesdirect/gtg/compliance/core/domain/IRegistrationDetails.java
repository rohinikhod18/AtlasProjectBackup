package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.docupload.DocumentDto;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IRegistrationDetails.
 */
public interface IRegistrationDetails {

	/**
	 * Sets the current record.
	 *
	 * @param currentRecord the new current record
	 */
	void setCurrentRecord(Integer currentRecord);

	/**
	 * Sets the total records.
	 *
	 * @param totalRecords the new total records
	 */
	void setTotalRecords(Integer totalRecords);

	/**
	 * Sets the search criteria.
	 *
	 * @param searchCriteria the new search criteria
	 */
	void setSearchCriteria(String searchCriteria);

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	void setErrorCode(String errorCode);

	/**
	 * Sets the error message.
	 *
	 * @param errorDescription the new error message
	 */
	void setErrorMessage(String errorDescription);

	/**
	 * Sets the user.
	 *
	 * @param userProfile the new user
	 */
	void setUser(UserProfile userProfile);

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	AccountWrapper getAccount();
	
	/**
	 * Gets the current contact.
	 *
	 * @return the current contact
	 */
	ContactWrapper getCurrentContact();

	/**
	 * Sets the documents.
	 *
	 * @param updateDocumentDateFormat the new documents
	 */
	void setDocuments(List<DocumentDto> updateDocumentDateFormat);
	
	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	void setSource(String source);
	
	/**
	 * Sets the checks if is pagenation required.
	 *
	 * @param isPagenationRequired the new checks if is pagenation required
	 */
	void setIsPagenationRequired(Boolean isPagenationRequired);
	
}
