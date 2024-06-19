/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.msg;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderResponse;

/**
 * The Class MQMessageContext.
 *
 * @author manish
 */
public class MQMessageContext implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The request. */
	private ServiceMessage request;

	/** The response. */
	private ServiceMessageResponse response;

	/** The mq provider request. */
	private MQProviderRequest mqProviderRequest;

	/** The mq provider response. */
	private MQProviderResponse mqProviderResponse;

	/** The datalake response. */
	private Response datalakeResponse;

	/** The no message left. */
	private Boolean noMessageLeft = Boolean.FALSE;

	/** The retry count. */
	private Integer retryCount = 0;

	/**
	 * Gets the retry count.
	 *
	 * @return the retry count
	 */
	public Integer getRetryCount() {
		return retryCount;
	}

	/**
	 * Sets the retry count.
	 *
	 * @param retryCount
	 *            the new retry count
	 */
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * Gets the mq provider response.
	 *
	 * @return the mq provider response
	 */
	public MQProviderResponse getMqProviderResponse() {
		return mqProviderResponse;
	}

	/**
	 * Sets the mq provider response.
	 *
	 * @param mqProviderResponse
	 *            the new mq provider response
	 */
	public void setMqProviderResponse(MQProviderResponse mqProviderResponse) {
		this.mqProviderResponse = mqProviderResponse;
	}

	/**
	 * Gets the mq provider request.
	 *
	 * @return the mq provider request
	 */
	public MQProviderRequest getMqProviderRequest() {
		return mqProviderRequest;
	}

	/**
	 * Sets the mq provider request.
	 *
	 * @param mqProviderRequest
	 *            the new mq provider request
	 */
	public void setMqProviderRequest(MQProviderRequest mqProviderRequest) {
		this.mqProviderRequest = mqProviderRequest;
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public ServiceMessage getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */
	public void setRequest(ServiceMessage request) {
		this.request = request;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public ServiceMessageResponse getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */
	public void setResponse(ServiceMessageResponse response) {
		this.response = response;
	}

	/**
	 * Gets the no message left.
	 *
	 * @return the no message left
	 */
	public Boolean getNoMessageLeft() {
		return noMessageLeft;
	}

	/**
	 * Sets the no message left.
	 *
	 * @param noMessageLeft
	 *            the new no message left
	 */
	public void setNoMessageLeft(Boolean noMessageLeft) {
		this.noMessageLeft = noMessageLeft;
	}

	/**
	 * Checks if is datalake eligible.
	 *
	 * @return true, if is datalake eligible
	 */
	public boolean isDatalakeEligible() {
		String entityType = this.getMqProviderRequest().getEntityType();
		return (entityType.equals(MQEntityTypeEnum.SIGNUP.toString())
				|| entityType.equals(MQEntityTypeEnum.ADDCONTACT.toString())
				|| entityType.equals(MQEntityTypeEnum.UPDATE.toString()));
	}

	/**
	 * Gets the datalake response.
	 *
	 * @return the datalake response
	 */
	public Response getDatalakeResponse() {
		return datalakeResponse;
	}

	/**
	 * Sets the datalake response.
	 *
	 * @param datalakeResponse
	 *            the new datalake response
	 */
	public void setDatalakeResponse(Response datalakeResponse) {
		this.datalakeResponse = datalakeResponse;
	}

}