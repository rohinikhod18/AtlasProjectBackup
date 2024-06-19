package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;


/**
 * The Class BaseQueueDto.
 */
public class BaseQueueDto {

	/** The page. */
	private Page page;
	
	/** The user. */
	private UserProfile user;

	/** The error message. */
	private String errorMessage;

	/** The error code. */
	private String errorCode;
	
	/** The source. */
	private List<String> source;
	
	/** The organization. */
	private List<String> organization;
	
	/** The currency. */
	private List<String> currency;
	
	/** The transaction value. */
	private List<String> transValue;
	
	/** The country. */
	private List<String> country;
	
	//For PaymentIn Report
	/** The watch list  */
	private Watchlist watchList;
	
	/** The owner. */
	private List<String> owner;
	
	/** The payment status. */
	private List<String> paymentStatus;
	
	/** The cust type. */
	private String custType;
	
	/** The is dashboard search criteria. */
	private Boolean isDashboardSearchCriteria;
	
	private Boolean isFromDetails;
	
	/** The total records. */
	private Integer totalRecords;
	
	/** The date from. */
	private String dateFrom;
	
	/** The date to. */
	private String dateTo;
	
	private String searchCriteria;
	
	/** The payment method. */
	private List<String> paymentMethod;
	
	private SavedSearch savedSearch;
	
	private String[] dateFilterType;
	
	/** The legal entity. */
	private List<String> legalEntity;
	
	private List<String> onfidoStatus;
	
	//AT-3714
	private String fraudSightFlag;
		
	public List<String> getOnfidoStatus() {
		return onfidoStatus;
	}

	public void setOnfidoStatus(List<String> onfidoStatus) {
		this.onfidoStatus = onfidoStatus;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserProfile getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public List<String> getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(List<String> source) {
		this.source = source;
	}

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public List<String> getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization(List<String> organization) {
		this.organization = organization;
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public List<String> getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency the new currency
	 */
	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}

	/**
	 * Gets the trans value.
	 *
	 * @return the trans value
	 */
	public List<String> getTransValue() {
		return transValue;
	}

	/**
	 * Sets the trans value.
	 *
	 * @param transValue the new trans value
	 */
	public void setTransValue(List<String> transValue) {
		this.transValue = transValue;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public List<String> getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(List<String> country) {
		this.country = country;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public List<String> getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(List<String> owner) {
		this.owner = owner;
	}

	/**
	 * Gets the payment status.
	 *
	 * @return the payment status
	 */
	public List<String> getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * Sets the payment status.
	 *
	 * @param paymentStatus the new payment status
	 */
	public void setPaymentStatus(List<String> paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	
	

	/**
	 * Gets the total records.
	 *
	 * @return the total records
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * Sets the total records.
	 *
	 * @param totalRecords the new total records
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the checks if is dashboard search criteria.
	 *
	 * @return the checks if is dashboard search criteria
	 */
	public Boolean getIsDashboardSearchCriteria() {
		return isDashboardSearchCriteria;
	}

	/**
	 * Sets the checks if is dashboard search criteria.
	 *
	 * @param isDashboardSearchCriteria the new checks if is dashboard search criteria
	 */
	public void setIsDashboardSearchCriteria(Boolean isDashboardSearchCriteria) {
		this.isDashboardSearchCriteria = isDashboardSearchCriteria;
	}

	/**
	 * Gets the date from.
	 *
	 * @return the date from
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * Sets the date from.
	 *
	 * @param dateFrom the new date from
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * Gets the date to.
	 *
	 * @return the date to
	 */
	public String getDateTo() {
		return dateTo;
	}

	/**
	 * Sets the date to.
	 *
	 * @param dateTo the new date to
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * Gets the watch list.
	 *
	 * @return the watch list
	 */
	public Watchlist getWatchList() {
		return watchList;
	}

	/**
	 * Sets the watch list.
	 *
	 * @param watchList the new watch list
	 */
	public void setWatchList(Watchlist watchList) {
		this.watchList = watchList;
	}

	/**
	 * @return the isFromDetails
	 */
	public Boolean getIsFromDetails() {
		return isFromDetails;
	}

	/**
	 * @param isFromDetails the isFromDetails to set
	 */
	public void setIsFromDetails(Boolean isFromDetails) {
		this.isFromDetails = isFromDetails;
	}
	
	/**
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	public List<String> getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentmethod  the payment method
	 */
	public void setPaymentMethod(List<String> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the savedSearch
	 */
	public SavedSearch getSavedSearch() {
		return savedSearch;
	}

	/**
	 * @param savedSearch the savedSearch to set
	 */
	public void setSavedSearch(SavedSearch savedSearch) {
		this.savedSearch = savedSearch;
	}

	public String[] getDateFilterType() {
		return dateFilterType;
	}

	public void setDateFilterType(String[] dateFilterType) {
		this.dateFilterType = dateFilterType;
	}

	public List<String> getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(List<String> legalEntity) {
		this.legalEntity = legalEntity;
	}

	/**
	 * @return the fraudSightFlag
	 */
	public String getFraudSightFlag() {
		return fraudSightFlag;
	}

	/**
	 * @param fraudSightFlag the fraudSightFlag to set
	 */
	public void setFraudSightFlag(String fraudSightFlag) {
		this.fraudSightFlag = fraudSightFlag;
	}
	
	

}
