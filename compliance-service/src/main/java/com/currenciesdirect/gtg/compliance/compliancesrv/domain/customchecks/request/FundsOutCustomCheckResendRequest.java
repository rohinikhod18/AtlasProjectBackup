package com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.request;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CustomCheckResendRequest.
 */
public class FundsOutCustomCheckResendRequest extends FundsOutBaseRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Beneficiary funds out id", required = true)
	private Integer paymentOutId;
	
	@ApiModelProperty(value = "Payment In Id", required = true)
	private Integer paymentInId;
	
	@ApiModelProperty(value = "Trade Account Number", required = true)
	private String tradeAccountNumber;
	
	@ApiModelProperty(value = "Trade Contact Id", required = true)
	private Integer tradeContactId;
	
	@ApiModelProperty(value = "Trade Payment Id", required = true)
	private Integer tradePaymentId;
	
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	public Integer getTradeContactId() {
		return tradeContactId;
	}

	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	public Integer getPaymentInId() {
		return paymentInId;
	}

	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	public FieldValidator validateResendRequest(ServiceInterfaceType type){
		FieldValidator fv = new FieldValidator();
		switch(type){
		case FUNDSIN:
		fv.isEmpty(new String[] { paymentInId.toString(), tradeAccountNumber, },
				new String[] { "paymentInId", "tradeAccountNumber"});
		break;
		case FUNDSOUT:
			fv.isEmpty(new String[] { paymentOutId.toString()},
					new String[] { "paymentOutId"});
			break;
		default:
			fv.addError(Constants.MSG_MISSING_INFO, "something went wrrong");
			break;
		}
		return fv;
	}
	
	public  Integer getPaymentFundsoutId()
	{
		return this.paymentOutId;
	}
}
