/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.IResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author manish
 *
 */
public class ProfileResponse extends BaseResponse implements Serializable,IResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "The reason for being Inactive", dataType = "java.lang.String", required = true)
	private String reasonForInactive;
	
	public String getReasonForInactive() {
		return reasonForInactive;
	}
	public void setReasonForInactive(String reasonForInactive) {
		this.reasonForInactive = reasonForInactive;
	}

}
