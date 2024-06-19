package com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCardTransactionResponse extends BaseResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
