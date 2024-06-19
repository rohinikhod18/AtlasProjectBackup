package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.opensaml.ws.wstrust.RequestType;

/**
 * The Class WebServiceConfigDetail will hold the configuration details required
 * for calling external REST service like Host, URL, port, request headers etc.
 * 
 * @author Rohit P.
 *
 */
public class WebServiceConfigDetail {

	/** The full request url. */
	private String requestUrl;

	/** The request type. */
	private RequestType requestType;

	/** The request headers. */
	private MultivaluedMap<String, Object> requestHeaders;

	/** The media type. */
	private MediaType mediaType;

	/** The host. */
	private String host;

	/** The port. */
	private Integer port;

	/** The user name. */
	private String userName;

	/** The password. */
	private String password;

	/** The soap service name. */
	private String soapServiceName;

	/** The url template. */
	private String urlTemplate;

	/** The name space name. */
	private String nameSpaceName;

	/** The local part name. */
	private String localPartName;

	/**
	 * Gets the request url.
	 *
	 * @return the request url
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * Sets the request url.
	 *
	 * @param requestUrl
	 *            the new request url
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType
	 *            the new request type
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the request headers.
	 *
	 * @return the request headers
	 */
	public MultivaluedMap<String, Object> getRequestHeaders() {
		return requestHeaders;
	}

	/**
	 * Sets the request headers.
	 *
	 * @param requestHeaders
	 *            the request headers
	 */
	public void setRequestHeaders(MultivaluedMap<String, Object> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	/**
	 * Gets the media type.
	 *
	 * @return the media type
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * Sets the media type.
	 *
	 * @param mediaType
	 *            the new media type
	 */
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host
	 *            the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port
	 *            the new port
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * Gets the soap service name.
	 *
	 * @return the soapServiceName
	 */
	public String getSoapServiceName() {
		return soapServiceName;
	}

	/**
	 * Sets the soap service name.
	 *
	 * @param soapServiceName
	 *            the soapServiceName to set
	 */
	public void setSoapServiceName(String soapServiceName) {
		this.soapServiceName = soapServiceName;
	}

	/**
	 * Gets the url template.
	 *
	 * @return the urlTemplate
	 */
	public String getUrlTemplate() {
		return urlTemplate;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the new user name
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
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the url template.
	 *
	 * @param urlTemplate
	 *            the urlTemplate to set
	 */
	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}

	/**
	 * Gets the name space name.
	 *
	 * @return the nameSpaceName
	 */
	public String getNameSpaceName() {
		return nameSpaceName;
	}

	/**
	 * Sets the name space name.
	 *
	 * @param nameSpaceName
	 *            the nameSpaceName to set
	 */
	public void setNameSpaceName(String nameSpaceName) {
		this.nameSpaceName = nameSpaceName;
	}

	/**
	 * Gets the local part name.
	 *
	 * @return the localPartName
	 */
	public String getLocalPartName() {
		return localPartName;
	}

	/**
	 * Sets the local part name.
	 *
	 * @param localPartName
	 *            the localPartName to set
	 */
	public void setLocalPartName(String localPartName) {
		this.localPartName = localPartName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WebServiceConfigDetail [requestUrl=");
		builder.append(requestUrl);
		builder.append(", requestType=");
		builder.append(requestType);
		builder.append(", requestHeaders=");
		builder.append(requestHeaders);
		builder.append(", mediaType=");
		builder.append(mediaType);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", soapServiceName=");
		builder.append(soapServiceName);
		builder.append(", urlTemplate=");
		builder.append(urlTemplate);
		builder.append(", nameSpaceName=");
		builder.append(nameSpaceName);
		builder.append(", localPartName=");
		builder.append(localPartName);
		builder.append(']');
		return builder.toString();
	}
}
