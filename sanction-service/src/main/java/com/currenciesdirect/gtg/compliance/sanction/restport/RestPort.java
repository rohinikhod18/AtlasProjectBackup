/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: RestPort.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.restport;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.sanction.core.IService;
import com.currenciesdirect.gtg.compliance.sanction.core.ServiceImpl;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;

/**
 * The Class RestPort.
 */
@Path("/")
public class RestPort {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RestPort.class);
	
	/** The i service. */
	private IService iService = ServiceImpl.getInstance();


	
	/**
	 * Gets the scanction details.
	 *
	 * @param correlationId the correlation id
	 * @param sanctionRequest            the sanction request
	 * @return the scanction details
	 * @throws SanctionException             the sanction exception
	 */
	@POST
	@Path("/checkSanctions")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkFundoutSanctions(@HeaderParam("correlationId") String correlationId,
			SanctionRequest sanctionRequest) throws SanctionException {
		SanctionResponse sanctionResponse = new SanctionResponse();
		SanctionRequest copiedsanctionRequest = null;
		try {
			copiedsanctionRequest = (SanctionRequest) ObjectCloner.deepCopy(sanctionRequest);
			updateOfacAndWcOfRequest(sanctionRequest);
			sanctionResponse = iService.getSanctionDetails(copiedsanctionRequest);
			updateWCAndOfacValues(sanctionResponse);
		} catch (SanctionException sanctionException) {
			logDebug(sanctionException);
			sanctionResponse = createServiceFailureResponse(sanctionRequest);
			sanctionResponse.setErrorCode(sanctionException.getSanctionErrors().getErrorCode());
			sanctionResponse.setErrorDescription(sanctionException.getSanctionErrors().getErrorDescription());
			return Response.ok().entity(sanctionResponse).build();
		} catch (Exception e) {
			LOG.error("Error while geting sanction details : getScanctionDetails() ", e);
			sanctionResponse.setErrorCode("0999");
			sanctionResponse.setErrorDescription("Generic Exception");
			sanctionResponse = createServiceFailureResponse(sanctionRequest);
			return Response.ok().entity(sanctionResponse).build();
		}
		return Response.status(200).entity(sanctionResponse).build(); 
	}
	
	/**
	 * Update WC and ofac values.
	 *
	 * @param sanctionResponse the sanction response
	 */
	private void updateWCAndOfacValues(SanctionResponse sanctionResponse) {
		List<SanctionContactResponse> contactResponse = sanctionResponse.getContactResponses();
		List<SanctionBeneficiaryResponse> beneficiaryResponse = sanctionResponse.getBeneficiaryResponses();
		List<SanctionBankResponse> bankResponse = sanctionResponse.getBankResponses();	
		updateWCAndOfacValuesForContact(contactResponse);
		updateWCAndOfacValuesForBeneficiary(beneficiaryResponse);
		updateWCAndOfacValuesForBank(bankResponse);
		
	}

	/**
	 * Update WC and ofac values for bank.
	 *
	 * @param bankResponse the bank response
	 */
	private void updateWCAndOfacValuesForBank(List<SanctionBankResponse> bankResponse) {
		if(bankResponse == null) {
			return;
		}
		
		for(SanctionBankResponse beneficiary : bankResponse) {
			setWorldCheckValue(beneficiary);
			setOfacListValue(beneficiary);						
		}				
	}

	/**
	 * Update WC and ofac values for beneficiary.
	 *
	 * @param beneficiaryResponse the beneficiary response
	 */
	private void updateWCAndOfacValuesForBeneficiary(List<SanctionBeneficiaryResponse> beneficiaryResponse) {
		if(beneficiaryResponse == null) {
			return;
		}
		
		for(SanctionBeneficiaryResponse beneficiary : beneficiaryResponse) {
				setWorldCheckValue(beneficiary);
				setOfacListValue(beneficiary);						
		}
		
	}

	/**
	 * Update WC and ofac values for contact.
	 *
	 * @param contactResponse the contact response
	 */
	private void updateWCAndOfacValuesForContact(List<SanctionContactResponse> contactResponse) {
		if(contactResponse == null) {
			return;
		}
			
		for(SanctionContactResponse contact : contactResponse) {
				setWorldCheckValue(contact);
				setOfacListValue(contact);	
		}
			
	}

	/**
	 * Sets the ofac list value.
	 *
	 * @param base the new ofac list value
	 */
	private void setOfacListValue(SanctionBaseResponse base) {
		if(base == null){
			return;
		}
		if(base.getOfacList() == null || base.getOfacList().trim().isEmpty()) {
			base.setOfacList(Constants.NOT_AVAILABLE);
		}
		if(base.getOfacList().equalsIgnoreCase(Constants.NO_MATCH_FOUND)){
			base.setOfacList(Constants.SAFE);
		} else if(base.getOfacList().equalsIgnoreCase(Constants.MATCH_FOUND)){			
			base.setOfacList(Constants.CONFIRMED_HIT);
		}
	}

	/**
	 * Sets the world check value.
	 *
	 * @param base the new world check value
	 */
	private void setWorldCheckValue(SanctionBaseResponse base) {
		if(base == null){
			return;
		}
		if(base.getWorldCheck() == null || base.getWorldCheck().trim().isEmpty()) {
			base.setWorldCheck(Constants.NOT_AVAILABLE);
		}
		if(base.getWorldCheck().equalsIgnoreCase(Constants.NO_MATCH_FOUND)) {
			base.setWorldCheck(Constants.SAFE);
		} else if(base.getWorldCheck().equalsIgnoreCase(Constants.MATCH_FOUND)) {			
				base.setWorldCheck(Constants.CONFIRMED_HIT);
		}
	}
	
	/**
	 * Update ofac and wc of request.
	 *
	 * @param request the request
	 */
	private void updateOfacAndWcOfRequest(SanctionRequest request){
		updateOfacAndWcOfBank(request.getBankRequests());
		updateOfacAndWcOfContact(request.getContactrequests());
		updateOfacAndWcOfBeneficiary( request.getBeneficiaryRequests());
	}
	
	
	/**
	 * Update ofac and wc of bank.
	 *
	 * @param banckRequests the banck requests
	 */
	private void updateOfacAndWcOfBank(List<SanctionBankRequest> banckRequests){
		if(banckRequests == null){
			return ;
		}
		for(SanctionBankRequest bankRequest : banckRequests){
			bankRequest.setPreviousOfac(updateProvideStatus(bankRequest.getPreviousOfac()));
			bankRequest.setPreviousWorldCheck(updateProvideStatus(bankRequest.getPreviousWorldCheck()));
		}
	}
	
	/**
	 * Update ofac and wc of contact.
	 *
	 * @param contactRequests the contact requests
	 */
	private void updateOfacAndWcOfContact(List<SanctionContactRequest> contactRequests){
		if(contactRequests == null){
			return ;
		}
		for(SanctionContactRequest contactRequest : contactRequests){
			contactRequest.setPreviousOfac(updateProvideStatus(contactRequest.getPreviousOfac()));
			contactRequest.setPreviousWorldCheck(updateProvideStatus(contactRequest.getPreviousWorldCheck()));
		}
	}
	
	/**
	 * Update ofac and wc of beneficiary.
	 *
	 * @param beneficiaryRequests the beneficiary requests
	 */
	private void updateOfacAndWcOfBeneficiary(List<SanctionBeneficiaryRequest> beneficiaryRequests){
		if(beneficiaryRequests == null){
			return ;
		}
		for(SanctionBeneficiaryRequest beneficiaryRequest : beneficiaryRequests){
			beneficiaryRequest.setPreviousOfac(updateProvideStatus(beneficiaryRequest.getPreviousOfac()));
			beneficiaryRequest.setPreviousWorldCheck(updateProvideStatus(beneficiaryRequest.getPreviousWorldCheck()));
		}
	}

	/**
	 * Update provide status.
	 *
	 * @param providerStatus the provider status
	 * @return the string
	 */
	private String updateProvideStatus(String providerStatus) {
		if(providerStatus == null || providerStatus.trim().isEmpty() || providerStatus.equalsIgnoreCase(Constants.NOT_AVAILABLE)){
			return providerStatus;
		}
		if(providerStatus.equalsIgnoreCase(Constants.SAFE)){
			return Constants.NO_MATCH_FOUND;
		} else if(providerStatus.equalsIgnoreCase(Constants.CONFIRMED_HIT)){
			return Constants.MATCH_FOUND;
		} 
		return providerStatus;
	}

	/**
	 * Log debug.
	 *
	 * @param exception the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class RestPort  :", exception);
	}
	
	/**
	 * Creates the service failure response.
	 *
	 * @param sanctionRequest the sanction request
	 * @return the sanction response
	 */
	private SanctionResponse createServiceFailureResponse(SanctionRequest sanctionRequest){
		SanctionResponse response=new SanctionResponse();
		  List<SanctionContactResponse> contactResponsesList = new ArrayList<>();
		  List<SanctionBeneficiaryResponse> beneficiaryResponsesList = new ArrayList<>();
		  List<SanctionBankResponse> bankResponsesList = new ArrayList<>();
		  
		  if(null != sanctionRequest.getContactrequests() && !sanctionRequest.getContactrequests().isEmpty()){
			  for(SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()){
				  SanctionContactResponse contactResponse = new SanctionContactResponse();
				  contactResponse.setContactId(contactRequest.getContactId());
				  contactResponse.setStatus(Constants.SERVICE_FAILURE);
				  contactResponse.setSanctionId(contactRequest.getSanctionId());
				  contactResponsesList.add(contactResponse);
				  response.setContactResponses(contactResponsesList);
			  }
		  }
		  
		  if(null != sanctionRequest.getBankRequests() && !sanctionRequest.getBankRequests().isEmpty()){
			  for(SanctionBankRequest bankRequest : sanctionRequest.getBankRequests()){
				  SanctionBankResponse bankResponses = new SanctionBankResponse();
				  bankResponses.setBankID(bankRequest.getBankId());
				  bankResponses.setSanctionId(bankRequest.getSanctionId());
				  bankResponses.setStatus(Constants.SERVICE_FAILURE);
				  bankResponsesList.add(bankResponses);
				  response.setBankResponses(bankResponsesList);
			  }
		  }
		  
		  if(null != sanctionRequest.getBeneficiaryRequests() && !sanctionRequest.getBeneficiaryRequests().isEmpty()){
			  for(SanctionBeneficiaryRequest beneficiaryRequest : sanctionRequest.getBeneficiaryRequests()){
				  SanctionBeneficiaryResponse beneficiaryResponses = new SanctionBeneficiaryResponse();
				  beneficiaryResponses.setBeneficiaryId(beneficiaryRequest.getBeneficiaryId());
				  beneficiaryResponses.setSanctionId(beneficiaryRequest.getSanctionId());
				  beneficiaryResponses.setStatus(Constants.SERVICE_FAILURE);
				  beneficiaryResponsesList.add(beneficiaryResponses);
				  response.setBeneficiaryResponses(beneficiaryResponsesList);
			  }
		  }
		  response.setErrorCode("0999");
		  response.setErrorDescription("Generic Exception");
		  
		return response;
		
	}

}
