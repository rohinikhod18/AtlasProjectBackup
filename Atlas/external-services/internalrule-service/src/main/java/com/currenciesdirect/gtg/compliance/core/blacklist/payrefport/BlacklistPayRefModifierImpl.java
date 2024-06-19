package com.currenciesdirect.gtg.compliance.core.blacklist.payrefport;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.core.blacklist.payref.IBlacklistPayref;
import com.currenciesdirect.gtg.compliance.exception.BlacklistPayrefErrors;
import com.currenciesdirect.gtg.compliance.exception.BlacklistPayrefException;
import com.currenciesdirect.gtg.compliance.util.Constants;




public class BlacklistPayRefModifierImpl implements IBlacklistPayref
{
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(BlacklistPayRefModifierImpl.class);

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler=ResteasyClientHandler.getInstance();
	
	/** The iblacklistpayref. */
	private static IBlacklistPayref iblacklistpayref = null;
	
	/**
	 * Gets the single instance of BlacklistPayRefModifierImpl.
	 *
	 * @return single instance of BlacklistPayRefModifierImpl
	 */
	public static IBlacklistPayref getInstance() {
		if (iblacklistpayref == null) {
			iblacklistpayref = new BlacklistPayRefModifierImpl();
		}
		return iblacklistpayref;
	}
	
	/**
	 * Do pay ref payments out check.
	 *
	 * @param request the request
	 * @param blacklistPayrefProviderProperty the blacklist payref provider property
	 * @return the blacklist payref contact response
	 * @throws BlacklistPayrefException the blacklist payref exception
	 */
	@SuppressWarnings("null")
	public BlacklistPayrefContactResponse doPayRefPaymentsOutCheck(InternalServiceRequestData request,
			BlacklistPayrefProviderProperty blacklistPayrefProviderProperty)throws BlacklistPayrefException {
		BlacklistPayrefContactResponse blacklistPayrefContactResponse= new BlacklistPayrefContactResponse();
		ResteasyClient client = null;
		ProviderBlacklistPayrefRequest providerblacklistPayRefRequest=new ProviderBlacklistPayrefRequest();
		Response response = null;
		ProviderBlackPayrefResponse providerBlackPayrefResponse= new ProviderBlackPayrefResponse();
		int fixedThreshold = blacklistPayrefProviderProperty.getFixedThreshold();
		try
		{
			LOG.debug("Payment Reference Port STARTED : doPayRefPaymentsOutCheck() ");
			client = resteasyClientHandler.getRetEasyClient();
			providerblacklistPayRefRequest.setPaymentReference(request.getPayementRefernce());
			providerblacklistPayRefRequest = ObjectUtils.replaceEmptyWithNull(providerblacklistPayRefRequest, ProviderBlacklistPayrefRequest.class);
			ResteasyWebTarget target1 = client.target(blacklistPayrefProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request();
			LOG.warn("\n ---Payment reference request---", providerblacklistPayRefRequest);
			response = clientrequest.post(Entity.entity(providerblacklistPayRefRequest, MediaType.APPLICATION_JSON));
			LOG.warn("\n ---Payment reference response---", response);
			providerBlackPayrefResponse=handleBlacklistPayrefServerTansactionResponse(response);
			transformBlacklistPayrefResponse(blacklistPayrefContactResponse, providerBlackPayrefResponse, fixedThreshold);
		}catch(BlacklistPayrefException e)
		{
			LOG.error("Exception in BlacklistPayRefModifierImpl doPayRefPaymentsOutCheck(): ", e);
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (response != null) {
				response.close();
			}
		}
		return blacklistPayrefContactResponse;
	}
	
	/**
	 * Transform blacklist payref response.
	 *
	 * @param blacklistPayrefContactResponse the blacklist payref contact response
	 * @param providerBlackPayrefResponse the provider black payref response
	 * @param fixedThreshold the fixed threshold
	 * @throws BlacklistPayrefException the blacklist payref exception
	 */
	private void transformBlacklistPayrefResponse(
			BlacklistPayrefContactResponse blacklistPayrefContactResponse,
			ProviderBlackPayrefResponse providerBlackPayrefResponse, int fixedThreshold)throws BlacklistPayrefException
	{
		blacklistPayrefContactResponse.setPaymentReference(providerBlackPayrefResponse.getPaymentReference());
		blacklistPayrefContactResponse.setRequestId(providerBlackPayrefResponse.getRequestId());
		blacklistPayrefContactResponse.setSanctionText(providerBlackPayrefResponse.getSanctionText());
		blacklistPayrefContactResponse.setTokenSetRatio(providerBlackPayrefResponse.getTokenSetRatio());
		if(providerBlackPayrefResponse.getTokenSetRatio() >= fixedThreshold){
			blacklistPayrefContactResponse.setStatus(Constants.FAIL);
		}else{
			blacklistPayrefContactResponse.setStatus(Constants.PASS);
		}
	}

	/**
	 * Handle blacklist payref server tansaction response.
	 *
	 * @param response the response
	 * @return the provider black payref response
	 * @throws BlacklistPayrefException the blacklist payref exception
	 */
	private ProviderBlackPayrefResponse handleBlacklistPayrefServerTansactionResponse(Response response) throws BlacklistPayrefException
	{
		ProviderBlackPayrefResponse providerBlackPayrefResponse=null;
		
		if (null != response) {
			if (response.getStatus() == 200) {
				providerBlackPayrefResponse = response.readEntity(ProviderBlackPayrefResponse.class);
				LOG.warn("\n ---Payment reference response---", providerBlackPayrefResponse);
			} else if (response.getStatus() == 500) {
				throw new BlacklistPayrefException(BlacklistPayrefErrors.BLACKLISTPAYREF_SERVER_GENERIC_EXCEPTION);
			} else if (response.getStatus() == 400) {
				providerBlackPayrefResponse = response.readEntity(ProviderBlackPayrefResponse.class);
			}
			else {
				throw new BlacklistPayrefException(BlacklistPayrefErrors.FAILED);
			}
		}
		return providerBlackPayrefResponse;
	}

	
	
}
