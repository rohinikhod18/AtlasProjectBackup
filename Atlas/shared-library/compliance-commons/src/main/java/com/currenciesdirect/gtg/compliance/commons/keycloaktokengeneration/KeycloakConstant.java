package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

/**
 * The Class KeycloakConstant.
 *
 * @author Rohit P.
 */
public class KeycloakConstant {

	/** The Constant KEYCLOAK_TOKEN_URL. */
	public static final String KEYCLOAK_TOKEN_URL = "keycloak.token.url";
	
	/** The Constant KEYCLOAK_TOKEN_GRANT_TYPE. */
	public static final String KEYCLOAK_TOKEN_GRANT_TYPE = "keycloak.token.granttype";
	
	/** The Constant KEYCLOAK_TOKEN_USER_NAME. */
	public static final String KEYCLOAK_TOKEN_USER_NAME = "keycloak.token.username";

	/** The Constant KEYCLOAK_TOKEN_PASSWORD. */
	public static final String KEYCLOAK_TOKEN_PASWORD = "keycloak.token.password";

	/** The Constant KEYCLOAK_TOKEN_CLIENT_ID. */
	public static final String KEYCLOAK_TOKEN_CLIENT_ID = "keycloak.token.clientid";

	/** The Constant KEYCLOAK_TOKEN_CLIENT_SECRET. */
	public static final String KEYCLOAK_TOKEN_CLIENT_SECRET = "keycloak.token.clientsecret";

	/** The Constant KEYCLOAK_INACTIVITY_REFRESH_TIME. */
	public static final Integer KEYCLOAK_INACTIVITY_REFRESH_TIME = 30;

	/** The Constant KEYCLOAK_GRANT_TYPE. */
	public static final String PARAM_KEYCLOAK_GRANT_TYPE = "grant_type";

	/** The Constant KEYCLOAK_CLIENT_ID. */
	public static final String PARAM_KEYCLOAK_CLIENT_ID = "client_id";

	/** The Constant KEYCLOAK_CLIENT_SECRET. */
	public static final String PARAM_KEYCLOAK_CLIENT_SECRET = "client_secret";

	/** The Constant KEYCLOAK_USERNAME. */
	public static final String PARAM_KEYCLOAK_USERNAME = "username";

	/** The Constant KEYCLOAK_PASSWORD. */
	public static final String PARAM_KEYCLOAK_PAWD = "password";

	/** The Constant KEY_ACCESS_TOKEN. */
	public static final Object KEY_ACCESS_TOKEN = "access_token";

	/** The Constant TOKEN_TYPE_BEARER. */
	public static final String TOKEN_TYPE_BEARER = "Bearer";
	
	/** The Constant SCHEDULERTIME. */
	public static final String SCHEDULERTIME = "titan.keycloak.token.schedulertime";
	
	/** The Constant REQUEST_HEADER_ACCEPT. */
	public static final String REQUEST_HEADER_ACCEPT = "Accept";
	
	/** The Constant REQUEST_HEADER_ACCEPT_ENCODING. */
	public static final String REQUEST_HEADER_ACCEPT_ENCODING = "Accept-Encoding";

	/** The Constant REQUEST_HEADER_ACCEPT_LANGUAGE. */
	public static final String REQUEST_HEADER_ACCEPT_LANGUAGE = "Accept-Language";

	/** The Constant REQUEST_HEADER_CONTENT_TYPE. */
	public static final String REQUEST_HEADER_CONTENT_TYPE = "Content-Type";

	/** The Constant REQUEST_HEADER_VALUE_ACCEPT. */
	public static final String REQUEST_HEADER_VALUE_ACCEPT = "application/x-www-form-urlencoded";

	/** The Constant REQUEST_HEADER_VALUE_ACCEPT_ENCODING. */
	public static final Object REQUEST_HEADER_VALUE_ACCEPT_ENCODING = "gzip, deflate";

	/** The Constant REQUEST_HEADER_VALUE_ACCEPT_LANGUAGE. */
	public static final Object REQUEST_HEADER_VALUE_ACCEPT_LANGUAGE = "en-US,en;q=0.8";

	/** The Constant KEY_HEADER_CONTENT_TYPE. */
	public static final String KEY_HEADER_CONTENT_TYPE = "Content-type";

	/** The Constant VALUE_CONTENT_TYPE_JSON. */
	public static final String VALUE_CONTENT_TYPE_JSON = "text/plain";

	/**
	 * Instantiates a new keycloak constant.
	 */
	private KeycloakConstant() {

	}

}
