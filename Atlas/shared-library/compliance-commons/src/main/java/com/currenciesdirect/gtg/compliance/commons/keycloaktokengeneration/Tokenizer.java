package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.ITokenizer;
import com.currenciesdirect.gtg.compliance.commons.exception.ComplianceCommonsError;
import com.currenciesdirect.gtg.compliance.commons.exception.ComplianceCommonsException;
import com.currenciesdirect.gtg.compliance.commons.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.commons.util.ObjectUtils;

/**
 * The Class Tokenizer.
 *
 * @author Rohit P.
 */
@Component("tokenizer")
public class Tokenizer implements ITokenizer{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(Tokenizer.class);

	/**
	 * The authorization token fetched from keycloak authorization server based
	 * on user.
	 */
	private Map<String,String> authTokens = new HashMap<>();

	/** The tokenizer reference to get singleton instance. */
	private static Tokenizer tokenizer;

	/** The Constant scheduledExecutorService. */
	public static final ScheduledExecutorService scheduledExecutorService = Executors
			.newSingleThreadScheduledExecutor();

	/**
	 * Gets the single instance of Tokenizer.
	 *
	 * @return single instance of Tokenizer
	 */
	public static Tokenizer getInstance() {
		if (tokenizer == null) {
			tokenizer = new Tokenizer();
		}
		return tokenizer;
	}

	/**
	 * Gets the auth token.
	 *
	 * @return the authToken
	 * @throws CompliancePortalException 
	 */
	@Override
	public synchronized String getAuthToken(String systemName,boolean createNewToken) throws ComplianceCommonsException {
		try {	
				if(createNewToken) {
					boolean toAdd = false;
					return getNewAuthToken(KeycloakConfig.getInstance(systemName),systemName,toAdd);
				}
				else {
					boolean toAdd = true;
					if(!authTokens.containsKey(systemName)) {
						getNewAuthToken(KeycloakConfig.getInstance(systemName),systemName,toAdd);
					}
				}
		} catch (Exception exception) {
			authTokens.remove(systemName); // reset token value on exception
			throw new ComplianceCommonsException(ComplianceCommonsError.KEYCLOAK_TOKEN_CREATION_ERROR,exception);
		}
		return authTokens.get(systemName);
	}

	/**
	 * Sets the auth token.
	 *
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken,String systemName) {
		synchronized(this){
			this.authTokens.remove(systemName);
		}
	}

	/**
	 * This method will refresh or obtain a new token from Keycloak server based
	 * on user credentials.
	 *
	 * @param keyCloakConfig
	 *            the key cloak config
	 * @return the new auth token
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private String getNewAuthToken(KeycloakConfig keyCloakConfig,String systemName,boolean toAdd) throws ComplianceCommonsException {
		// logic: code to refresh and get new token from keycloak server
		KeycloakTokenResponse tokenResponse = null;
		MultivaluedHashMap<String, Object> requestHeaders = null;
		WebServiceConfigDetail serviceConfig = null;
		String token = null;
		HttpClientPool clientPool = HttpClientPool.getInstance();
		try {
			serviceConfig = new WebServiceConfigDetail();
			serviceConfig.setMediaType(MediaType.APPLICATION_FORM_URLENCODED_TYPE);

			requestHeaders = new MultivaluedHashMap<>();
			requestHeaders.putSingle(KeycloakConstant.REQUEST_HEADER_ACCEPT, KeycloakConstant.REQUEST_HEADER_VALUE_ACCEPT);
			requestHeaders.putSingle(KeycloakConstant.REQUEST_HEADER_ACCEPT_ENCODING, KeycloakConstant.REQUEST_HEADER_VALUE_ACCEPT_ENCODING);
			requestHeaders.putSingle(KeycloakConstant.REQUEST_HEADER_ACCEPT_LANGUAGE, KeycloakConstant.REQUEST_HEADER_VALUE_ACCEPT_LANGUAGE);
			requestHeaders.putSingle(KeycloakConstant.REQUEST_HEADER_CONTENT_TYPE, KeycloakConstant.REQUEST_HEADER_VALUE_ACCEPT);
			serviceConfig.setRequestHeaders(requestHeaders);
			requestHeaders.putSingle(KeycloakConstant.KEY_HEADER_CONTENT_TYPE, KeycloakConstant.VALUE_CONTENT_TYPE_JSON);
			serviceConfig.setRequestUrl(keyCloakConfig.getTokenUrl());

			tokenResponse = clientPool.sendRequest(serviceConfig.getRequestUrl(), "POST", keyCloakConfig.getParameter(),
					KeycloakTokenResponse.class, serviceConfig.getRequestHeaders(), serviceConfig.getMediaType());

			if (!ObjectUtils.isObjectNull(tokenResponse)) {
				token = tokenResponse.getAuthToken();
				token = KeycloakConstant.TOKEN_TYPE_BEARER + " " + token;
				// reset the existing auth token with new refreshed token
				LOG.warn("{} Token generated",systemName);
				if(toAdd) {
					authTokens.put(systemName, token);
					return authTokens.get(systemName);
				}
			}
			else {
				LOG.warn("{} Token not generated",systemName);
			}
		} catch (Exception exception) {
			throw new ComplianceCommonsException(ComplianceCommonsError.KEYCLOAK_TOKEN_CREATION_ERROR, exception);
		}
		return token;
	}

	/**
	 * Runscheduler.
	 */
	public void runscheduler() {

		final Runnable startSchedular = () -> {

			LOG.debug("AuthToken Schedular started");
			String[] externalSystems = new String[] {"titan","commhub"};
			Integer i=0;
			boolean toAdd = true;
			try {
				for(i=0;i<externalSystems.length;i++) {
					getNewAuthToken(KeycloakConfig.getInstance(externalSystems[i]),externalSystems[i],toAdd);
				}
			} catch (Exception ce) {
				LOG.error("Error in AuthToken scheduling", ce);
			}
		};

		String schedulerTime = System.getProperty(KeycloakConstant.SCHEDULERTIME);
		if (null != schedulerTime) {
			scheduledExecutorService.scheduleAtFixedRate(startSchedular, Integer.parseInt(schedulerTime),
					Integer.parseInt(schedulerTime), TimeUnit.MINUTES);
		}

	}

}
