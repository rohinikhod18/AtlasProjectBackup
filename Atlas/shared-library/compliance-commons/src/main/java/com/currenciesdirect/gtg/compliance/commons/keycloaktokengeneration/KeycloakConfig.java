package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.exception.ComplianceCommonsError;
import com.currenciesdirect.gtg.compliance.commons.exception.ComplianceCommonsException;

/**
 * The Class KeycloakConfig.
 *
 * @author Rohit P.
 */
@Component("keycloakConfig")
public class KeycloakConfig implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The token url. */
	private String tokenUrl;

	/** The grant type. */
	private String grantType;

	/** The client id. */
	private String clientId;

	/** The client secret. */
	private String clientSecret;

	/** The user id. */
	private String userName;

	/** The password. */
	private String password;

	/** The auth token. */
	private String authToken;

	/** The parameter. */
	private String parameter;

	/** The keycloak config. */
	private static KeycloakConfig keycloakConfig;

	/**
	 * Instantiates a new keycloak config.
	 */
	public KeycloakConfig() {
		super();
	}

	/**
	 * Gets the single instance of KeycloakConfig.
	 *
	 * @return single instance of KeycloakConfig
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public static KeycloakConfig getInstance(String systemName) throws ComplianceCommonsException {
		keycloakConfig = prepareAndReturnKeyCloakConfig(systemName);
		return keycloakConfig;
	}

	/**
	 * Gets the token url.
	 *
	 * @return the tokenUrl
	 */
	public String getTokenUrl() {
		return tokenUrl;
	}

	/**
	 * Sets the token url.
	 *
	 * @param tokenUrl
	 *            the tokenUrl to set
	 */
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	/**
	 * Gets the grant type.
	 *
	 * @return the grantType
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * Sets the grant type.
	 *
	 * @param grantType
	 *            the grantType to set
	 */
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	/**
	 * Gets the client id.
	 *
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets the client id.
	 *
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Gets the client secret.
	 *
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * Sets the client secret.
	 *
	 * @param clientSecret
	 *            the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the auth token.
	 *
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * Sets the auth token.
	 *
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter.
	 *
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * Prepare and return keycloak configuration object.
	 *
	 * @return the key cloak config
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public static KeycloakConfig prepareAndReturnKeyCloakConfig(String systemName) throws ComplianceCommonsException {

		try {
			keycloakConfig = new KeycloakConfig();
			keycloakConfig.setTokenUrl(System.getProperty(systemName+"."+KeycloakConstant.KEYCLOAK_TOKEN_URL));
			keycloakConfig.setGrantType(System.getProperty(systemName+"."+KeycloakConstant.KEYCLOAK_TOKEN_GRANT_TYPE));
			keycloakConfig.setUserName(System.getProperty(systemName+"."+KeycloakConstant.KEYCLOAK_TOKEN_USER_NAME));
			keycloakConfig.setPassword(System.getProperty(systemName+"."+KeycloakConstant.KEYCLOAK_TOKEN_PASWORD));
			keycloakConfig.setClientId(System.getProperty(systemName+"."+KeycloakConstant.KEYCLOAK_TOKEN_CLIENT_ID));
			keycloakConfig.setClientSecret(System.getProperty(systemName+"."+KeycloakConstant.KEYCLOAK_TOKEN_CLIENT_SECRET));

			// to set modified URL with query params
			keycloakConfig.setParameter(prepareParameterString(keycloakConfig));

		} catch (Exception exception) {
			throw new ComplianceCommonsException(ComplianceCommonsError.KEYCLOAK_TOKEN_PROPERTY_ERROR, exception);
		}

		return keycloakConfig;
	}

	/**
	 * Prepare parameter string.
	 *
	 * @param config
	 *            the config
	 * @return the string
	 */
	public static String prepareParameterString(KeycloakConfig config) {
		StringBuilder tokenUrlWithQueryParams = new StringBuilder();
		tokenUrlWithQueryParams.append(KeycloakConstant.PARAM_KEYCLOAK_GRANT_TYPE).append("=").append(config.getGrantType()).append("&");
		tokenUrlWithQueryParams.append(KeycloakConstant.PARAM_KEYCLOAK_CLIENT_ID).append("=").append(config.getClientId()).append("&");
		tokenUrlWithQueryParams.append(KeycloakConstant.PARAM_KEYCLOAK_USERNAME).append("=").append(config.getUserName()).append("&");
		tokenUrlWithQueryParams.append(KeycloakConstant.PARAM_KEYCLOAK_PAWD).append("=").append(config.getPassword()).append("&");
		tokenUrlWithQueryParams.append(KeycloakConstant.PARAM_KEYCLOAK_CLIENT_SECRET).append("=").append(config.getClientSecret());

		return tokenUrlWithQueryParams.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeyCloakConfig [tokenUrl=");
		builder.append(tokenUrl);
		builder.append(", grantType=");
		builder.append(grantType);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", clientSecret=");
		builder.append(clientSecret);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", authToken=");
		builder.append(authToken);
		builder.append(", parameter=");
		builder.append(parameter);
		builder.append(']');
		return builder.toString();
	}

}
