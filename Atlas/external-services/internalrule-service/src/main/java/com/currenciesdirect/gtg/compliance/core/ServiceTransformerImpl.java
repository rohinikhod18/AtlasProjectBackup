package com.currenciesdirect.gtg.compliance.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.util.Constants;

/**
 * The Class ServiceTransformerImpl.
 */
public class ServiceTransformerImpl implements IServiceTransformer  {

	/** The service tranformer. */
	@SuppressWarnings("squid:S3077")
	private static volatile ServiceTransformerImpl serviceTranformer = null;
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(ServiceTransformerImpl.class);

	/**
	 * Instantiates a new service transformer impl.
	 */
	private ServiceTransformerImpl() {

	}

	/**
	 * Gets the single instance of ServiceTransformerImpl.
	 *
	 * @return single instance of ServiceTransformerImpl
	 */
	public static ServiceTransformerImpl getInstance() {
		if (serviceTranformer == null) {
			synchronized (ServiceTransformerImpl.class) {
				if (serviceTranformer == null) {
					serviceTranformer = new ServiceTransformerImpl();
				}
			}
		}
		return serviceTranformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IServiceTransformer#
	 * transformToBlacklistSTPRequest(com.currenciesdirect.gtg.compliance.core.
	 * domain.InternalServiceRequestData, java.lang.String, java.lang.String)
	 */
	public BlacklistSTPRequest transformToBlacklistSTPRequest(InternalServiceRequestData requestData
			) {
		BlacklistSTPRequest blacklistSTPRequest = new BlacklistSTPRequest();

		List<BlacklistData> blacklistDatas = new ArrayList<>();
				
		transformAccountNumber(requestData, blacklistDatas);
		
		transformName(requestData, blacklistDatas);
		
		transformEmail(requestData, blacklistDatas);
		
		transformDomain(requestData, blacklistDatas);
		
		transformPhoneNo(requestData, blacklistDatas);
		
		transformIpAddress(requestData, blacklistDatas);
		
		blacklistSTPRequest.setSearch(blacklistDatas);
		return blacklistSTPRequest;
	}

	/**
	 * @param requestData
	 * @param blacklistDatas
	 */
	private void transformIpAddress(InternalServiceRequestData requestData, List<BlacklistData> blacklistDatas) {
		if (requestData.getIpAddress() != null && !requestData.getIpAddress().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.IPADDRESS);
			data.setRequestType(Constants.IPADDRESS);
			if (Boolean.FALSE.equals(requestData.getIsWildCardSearch())) {
				//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
				data.setValue(removeSpacesAndmakeLowercase(requestData.getIpAddress()));
			} 
			blacklistDatas.add(data);
		}
	}

	/**
	 * @param requestData
	 * @param blacklistDatas
	 */
	private void transformPhoneNo(InternalServiceRequestData requestData, List<BlacklistData> blacklistDatas) {
		if (requestData.getPhoneNoList() != null) {
			for(String phoneNo:requestData.getPhoneNoList()){
				BlacklistData data = new BlacklistData();
				data.setType(Constants.PHONE_NUMBER);
				data.setRequestType(Constants.PHONE_NUMBER);
				/**Code to resolve Jira Issue AT-276
				1)calling method to remove spaces from value of request field and to make it lowercase - Vishal J
				2)Here we will also remove '+' and '-' from mobile numbers provided in request so that we can search them with 
				  with blacklist data for mobile numbers and give response accordingly (found or not)*/
				if(phoneNo != null && !phoneNo.isEmpty()){
					phoneNo = phoneNo.replace(Constants.PLUS, Constants.EMPTY_STRING).replace(Constants.DASH, Constants.EMPTY_STRING).trim();
					data.setValue(removeSpacesAndmakeLowercase(phoneNo));
				}else{
					data.setValue("");
				}
				blacklistDatas.add(data);
			}
		}
	}

	/**
	 * @param requestData
	 * @param blacklistDatas
	 */
	private void transformDomain(InternalServiceRequestData requestData, List<BlacklistData> blacklistDatas) {
		if((requestData.getIsEmailDomainCheckRequired() == null || requestData.getIsEmailDomainCheckRequired())
				&& (requestData.getEmail() != null && !requestData.getEmail().isEmpty())) {
				//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
				String domain = removeSpacesAndmakeLowercase(requestData.getEmail());
				domain = domain.substring(domain.indexOf('@')+1);
				BlacklistData data = new BlacklistData();
				data.setType(Constants.DOMAIN);
				data.setRequestType(Constants.DOMAIN);
				data.setValue(domain);
				blacklistDatas.add(data);
		}
		
		if (requestData.getWebSite() != null && !requestData.getWebSite().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.DOMAIN);
			data.setRequestType(Constants.DOMAIN);
			/**calling method to remove spaces from value of request field and to make it lowercase - Vishal J*/
			String website = removeSpacesAndmakeLowercase(requestData.getWebSite());
			/**Sets value of domain from given website - Vishal J*/
			data.setValue(getDomainFromWebsite(website));
			blacklistDatas.add(data);
		}
	}

	/**
	 * @param requestData
	 * @param blacklistDatas
	 */
	private void transformEmail(InternalServiceRequestData requestData, List<BlacklistData> blacklistDatas) {
		if (requestData.getEmail() != null && !requestData.getEmail().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.EMAIL);
			data.setRequestType(Constants.EMAIL);
			//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
			data.setValue(removeSpacesAndmakeLowercase(requestData.getEmail()));
			blacklistDatas.add(data);
		}
	}

	/**
	 * @param requestData
	 * @param blacklistDatas
	 *  Code is added to resolve JIRA issue AT-1198
	 * Request type is set to uniquely identify the name, whether it is contact name, account name, company name or bank name
	 */
	private void transformName(InternalServiceRequestData requestData, List<BlacklistData> blacklistDatas) {
		if (requestData.getName() != null && !requestData.getName().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.NAME);
			data.setRequestType(Constants.CONTACT_NAME);
			//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
			data.setValue(removeSpacesAndmakeLowercase(requestData.getName()));
			blacklistDatas.add(data);
		}
		if (requestData.getCcName() != null && !requestData.getCcName().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.NAME);
			data.setRequestType(Constants.CCNAME);
			//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
			data.setValue(removeSpacesAndmakeLowercase(requestData.getCcName()));
			blacklistDatas.add(data);
		}
		
		if (requestData.getCompanyName() != null && !requestData.getCompanyName().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.NAME);
			data.setRequestType(Constants.COMPANY_NAME);
			//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
			data.setValue(removeSpacesAndmakeLowercase(requestData.getCompanyName()));
			blacklistDatas.add(data);
		}
		
		if (requestData.getBankName()!= null && !requestData.getBankName().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.NAME);
			data.setRequestType(Constants.BANK_NAME);
			//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
			data.setValue(removeSpacesAndmakeLowercase(requestData.getBankName()));
			blacklistDatas.add(data);
		}
		
		
		
	}

	/**
	 * @param requestData
	 * @param blacklistDatas
	 */
	private void transformAccountNumber(InternalServiceRequestData requestData, List<BlacklistData> blacklistDatas) {
		if (requestData.getAccountNumber() != null && !requestData.getAccountNumber().isEmpty()) {
			BlacklistData data = new BlacklistData();
			data.setType(Constants.ACCOUNT_NUMBER);
			data.setRequestType(Constants.ACCOUNT_NUMBER);
			//calling method to remove spaces from value of request field and to make it lowercase - Vishal J
			data.setValue(removeSpacesAndmakeLowercase(requestData.getAccountNumber()));
			blacklistDatas.add(data);
		}
	}

	/**
	 * Purpose: Below method fetches the hostname and make it ready to search in blacklist data by adding '@' to it.
	 * 
	 * Implementation:
	 * 1)We first check whether given website string contains protocol or not.
	 * 2)Then after adding protocol to it we call getDomainName() to get the host from website.
	 * 3)After getting host from it, we simply add '@' to it and make it ready to search in blacklist data with our database values
	 * 
	 * Method added by Vishal J
	 * */
	private String getDomainFromWebsite(String website) {
		String host = website;
		if(!website.startsWith("http") && !website.startsWith("https")){
			host = "http://" + website;
	    }
		try {
			 host = getDomainName(host);			
		} catch (Exception e) {
			logger.error("Exception in getting domain from website:", e);
		}		
		return host;
	}
	
	/**
	 * Purpose: Below method fetches the hostname from given website url.
	 * 
	 * Implementation:
	 * 1)We first check whether given website url is valid or not with URI().
	 * 2)If it is valid then we simply fetch the hostname from it.
	 * 
	 * Method added by Vishal J
	 *
	 * @param url the url
	 * @return the domain name
	 * @throws URISyntaxException the URI syntax exception
	 */
	public String getDomainName(String url) throws URISyntaxException {
	    URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IServiceTransformer#
	 * transformToRuleRequest(com.currenciesdirect.gtg.compliance.core.domain.
	 * InternalServiceRequestData, java.lang.String)
	 */
	@Override
	public StateRuleRequest transformToRuleRequest(InternalServiceRequestData requestData, String orgCode) {
		StateRuleRequest stateRuleRequest = new StateRuleRequest();
		stateRuleRequest.setIsoAlpha3CountryCode(requestData.getCountry());
		stateRuleRequest.setOrgCode(orgCode);
		stateRuleRequest.setState(requestData.getState());
		stateRuleRequest.setOrgLegalEntity(requestData.getOrgLegalEntity());

		return stateRuleRequest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IServiceTransformer#
	 * transformTpIpRequest(com.currenciesdirect.gtg.compliance.core.domain.
	 * InternalServiceRequestData, java.lang.String)
	 */
	@Override
	public IpServiceRequest transformTpIpRequest(InternalServiceRequestData requestData) {
		IpServiceRequest ipServiceRequest = new IpServiceRequest();
		ipServiceRequest.setCountry(requestData.getCountry());
		ipServiceRequest.setIpAddress(requestData.getIpAddress());
		ipServiceRequest.setPostalCode(requestData.getPostCode());
		return ipServiceRequest;
	}

	/**Purpose:
	 *  After checking request values (Name, phone no etc.) with blacklist data in our database the 
	 *  response that we got is in non readable format (firstnamelastname instead of Firstname Lastname) so we have to set those values again 
	 *  to set them into provider response. (User entered format)
	 * 
	 * Implementation:
	 * 1)We check the data type of request (name, email etc) and then we set its value from request itself.
	 * Changes done by Vishal J
	 * */
	@Override
	public BlacklistContactResponse transformBlacklistSTPResponse(InternalServiceRequestData requestData,
			BlacklistContactResponse blackListContactResponse) {
		Integer index = 0;
		for (BlacklistSTPData data : blackListContactResponse.getData()) {
			/**
			 * Condition added to check if data type is 'Phone Number' then we have to set original 
			 * number as provided by user into provider response. (By adding spaces and '+' or '-' again)
			 * Code to resolve Jira Issue AT-276
			 * -Vishal J
			 * */
			if((Constants.PHONE_NUMBER).equals(data.getType())){
					 data.setValue(requestData.getPhoneNoList().get(index));
					 index++;
			}		
			
			mapNameData(requestData, data);
			
			if ((Constants.ACCOUNT_NUMBER).equals(data.getType())) {
				data.setValue(requestData.getAccountNumber());
			}
			
			if ((Constants.IPADDRESS).equals(data.getType())) {
				data.setValue(requestData.getIpAddress());
			}
			
			if ((Constants.DOMAIN).equals(data.getType())) {
				transformResponseByEntityType(requestData, data);
			}
			
			if ((Constants.EMAIL).equals(data.getType())) {
				data.setValue(requestData.getEmail());
			}
		}
		return blackListContactResponse;
	}
	
    
	/**
	 * This method is changed to resolve JIRA issue AT-1198. request type is taken to uniquely identify name.
	 */
	private void mapNameData(InternalServiceRequestData requestData, BlacklistSTPData data) {
			
		if ((Constants.NAME).equals(data.getType()))
		{
			 if((Constants.CONTACT_NAME).equals(data.getRequestType())||
					 (Constants.CCNAME).equals(data.getRequestType())||
					 (Constants.BENEFICIARY_NAME).equals(data.getRequestType())){
				 
				 data.setValue(requestData.getName()); 
			 }
			 else if(Constants.BANK_NAME.equals(data.getRequestType()))
				 data.setValue(requestData.getBankName());
		}
			
       else
            	  data.setValue(requestData.getCompanyName());  
  }
	

	/**
	 * @param requestData
	 * @param data
	 */
	private void transformResponseByEntityType(InternalServiceRequestData requestData, BlacklistSTPData data) {
		if(Constants.CONTACT.equalsIgnoreCase(requestData.getEntityType()))
			data.setValue(data.getValue());
		else
			data.setValue(requestData.getWebSite());
	}
	
	/**
	 * Removes the spaces and make lowercase.
	 *
	 * @param requestValue the request value
	 * @return the string
	 * 
	 * Method added for common functionality (required for blacklist request data)to remove the spaces 
	 * from String and make it to lowercase.
	 * -Vishal J
	 */
	public String removeSpacesAndmakeLowercase(String requestValue){		 
		return requestValue.replace(Constants.SPACE, Constants.EMPTY_STRING).toLowerCase();
	}
	
}
