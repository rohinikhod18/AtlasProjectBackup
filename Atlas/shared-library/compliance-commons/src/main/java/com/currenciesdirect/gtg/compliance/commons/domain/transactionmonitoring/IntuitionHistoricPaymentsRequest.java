package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

public class IntuitionHistoricPaymentsRequest extends ServiceMessage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The intuition historic payment in request. */
	private List<IntuitionHistoricPaymentRequest> intuitionHistoricPaymentInRequest;
	
	//AT-5304
	private List<IntuitionHistoricPaymentRequest> intuitionHistoricPaymentOutRequest;

	/**
	 * @return the intuitionHistoricPaymentInRequest
	 */
	public List<IntuitionHistoricPaymentRequest> getIntuitionHistoricPaymentInRequest() {
		return intuitionHistoricPaymentInRequest;
	}

	/**
	 * @param intuitionHistoricPaymentInRequest the intuitionHistoricPaymentInRequest to set
	 */
	public void setIntuitionHistoricPaymentInRequest(List<IntuitionHistoricPaymentRequest> intuitionHistoricPaymentInRequest) {
		this.intuitionHistoricPaymentInRequest = intuitionHistoricPaymentInRequest;
	}

	/**
	 * @return the intuitionHistoricPaymentOutRequest
	 */
	public List<IntuitionHistoricPaymentRequest> getIntuitionHistoricPaymentOutRequest() {
		return intuitionHistoricPaymentOutRequest;
	}

	/**
	 * @param intuitionHistoricPaymentOutRequest the intuitionHistoricPaymentOutRequest to set
	 */
	public void setIntuitionHistoricPaymentOutRequest(List<IntuitionHistoricPaymentRequest> intuitionHistoricPaymentOutRequest) {
		this.intuitionHistoricPaymentOutRequest = intuitionHistoricPaymentOutRequest;
	}

	


}
