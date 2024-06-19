package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.ConversionPrediction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class SignUpRequestWrapper.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequestWrapper {

	/** The signup request. */
	@ApiModelProperty(value = "The signup request object", required = true)
	@JsonProperty(value="request")
	private SignUpRequest signupRequest;
	
	/** The conversion prediction. */
	@ApiModelProperty(value = "The conversion prediction object", required = true)
	@JsonProperty(value="conversionPrediction")
	private ConversionPrediction conversionPrediction;

	/**
	 * @return the signupRequest
	 */
	public SignUpRequest getSignupRequest() {
		return signupRequest;
	}

	/**
	 * @param signupRequest the signupRequest to set
	 */
	public void setSignupRequest(SignUpRequest signupRequest) {
		this.signupRequest = signupRequest;
	}

	/**
	 * @return the conversionPrediction
	 */
	public ConversionPrediction getConversionPrediction() {
		return conversionPrediction;
	}

	/**
	 * @param conversionPrediction the conversionPrediction to set
	 */
	public void setConversionPrediction(ConversionPrediction conversionPrediction) {
		this.conversionPrediction = conversionPrediction;
	}
	
}
