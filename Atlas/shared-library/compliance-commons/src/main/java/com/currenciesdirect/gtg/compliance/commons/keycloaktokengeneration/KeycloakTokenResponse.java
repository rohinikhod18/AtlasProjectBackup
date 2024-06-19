package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class KeycloakTokenResponse.
 *
 * @author rohit P.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakTokenResponse extends ServiceResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The auth token. */
	@JsonProperty("access_token")
	private String authToken;

	/** The expires in. */
	@JsonProperty(value = "expires_in")
	private Timestamp expiresIn;

	/** The refresh expires in. */
	@JsonProperty(value = "refresh_expires_in")
	private Timestamp refreshExpiresIn;

	/** The refresh token. */
	@JsonProperty(value = "refresh_token")
	private String refreshToken;

	/** The token type. */
	@JsonProperty(value = "token_type")
	private String tokenType;

	/** The id token. */
	@JsonProperty(value = "id_token")
	private String idToken;

	/** The Not before policy. */
	@JsonProperty(value = "not-before-policy")
	private Integer notBeforePolicy;

	/** The session state. */
	@JsonProperty(value = "session_state")
	private String sessionState;

	/**
	 * Gets the auth token.
	 *
	 * @return the auth token
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * Sets the auth token.
	 *
	 * @param authToken
	 *            the new auth token
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * Gets the expires in.
	 *
	 * @return the expires in
	 */
	public Timestamp getExpiresIn() {
		return expiresIn;
	}

	/**
	 * Sets the expires in.
	 *
	 * @param expiresIn
	 *            the new expires in
	 */
	public void setExpiresIn(Timestamp expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * Gets the refresh expires in.
	 *
	 * @return the refresh expires in
	 */
	public Timestamp getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	/**
	 * Sets the refresh expires in.
	 *
	 * @param refreshExpiresIn
	 *            the new refresh expires in
	 */
	public void setRefreshExpiresIn(Timestamp refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}

	/**
	 * Gets the refresh token.
	 *
	 * @return the refresh token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Sets the refresh token.
	 *
	 * @param refreshToken
	 *            the new refresh token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * Sets the token type.
	 *
	 * @param tokenType
	 *            the new token type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Gets the id token.
	 *
	 * @return the id token
	 */
	public String getIdToken() {
		return idToken;
	}

	/**
	 * Sets the id token.
	 *
	 * @param idToken
	 *            the new id token
	 */
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	/**
	 * Gets the not before policy.
	 *
	 * @return the not before policy
	 */
	public Integer getNotBeforePolicy() {
		return notBeforePolicy;
	}

	/**
	 * Sets the not before policy.
	 *
	 * @param notBeforePolicy
	 *            the new not before policy
	 */
	public void setNotBeforePolicy(Integer notBeforePolicy) {
		this.notBeforePolicy = notBeforePolicy;
	}

	/**
	 * Gets the session state.
	 *
	 * @return the session state
	 */
	public String getSessionState() {
		return sessionState;
	}

	/**
	 * Sets the session state.
	 *
	 * @param sessionState
	 *            the new session state
	 */
	public void setSessionState(String sessionState) {
		this.sessionState = sessionState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeycloakTokenResponse [authToken=");
		builder.append(authToken);
		builder.append(", expiresIn=");
		builder.append(expiresIn);
		builder.append(", refreshExpiresIn=");
		builder.append(refreshExpiresIn);
		builder.append(", refreshToken=");
		builder.append(refreshToken);
		builder.append(", tokenType=");
		builder.append(tokenType);
		builder.append(", idToken=");
		builder.append(idToken);
		builder.append(", notBeforePolicy=");
		builder.append(notBeforePolicy);
		builder.append(", sessionState=");
		builder.append(sessionState);
		builder.append(']');
		return builder.toString();
	}

}
