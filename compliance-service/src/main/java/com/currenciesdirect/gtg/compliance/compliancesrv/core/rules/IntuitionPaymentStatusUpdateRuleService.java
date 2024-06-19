package com.currenciesdirect.gtg.compliance.compliancesrv.core.rules;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class IntuitionPaymentStatusUpdateRuleService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(IntuitionPaymentStatusUpdateRuleService.class);

	/** The funds out DB service impl. */
	@Autowired
	protected FundsOutDBServiceImpl fundsOutDBServiceImpl;

	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) throws ComplianceException {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		IntuitionPaymentStatusUpdateRequest request = (IntuitionPaymentStatusUpdateRequest) messageExchange
				.getRequest();
		IntuitionPaymentStatusUpdateResponse response = (IntuitionPaymentStatusUpdateResponse) messageExchange
				.getResponse();

		if (request.getTrxType().equalsIgnoreCase("FUNDSIN")) {
			processFundsInRuleService(request, response);
		} else {
			processFundsOutRuleService(request, response);
		}

		return message;
	}

	/**
	 * Process funds in rule service.
	 *
	 * @param request  the request
	 * @param response the response
	 */
	private void processFundsInRuleService(IntuitionPaymentStatusUpdateRequest request,
			IntuitionPaymentStatusUpdateResponse response) {
		FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request.getAdditionalAttribute("fundsInRequest");
		List<FundsInReasonCode> responseReasons = new ArrayList<>(10);

		if (request.getAction().equalsIgnoreCase(FundsInComplianceStatus.CLEAR.name())
				|| request.getAction().equalsIgnoreCase(FundsInComplianceStatus.CLEAN.name())) {

			String paymentStatus = (String) fundsInRequest.getAdditionalAttribute("status");
			response.setPaymentStatus(paymentStatus);

			String sanctionStatus = (String) fundsInRequest.getAdditionalAttribute("SANCTION_STATUS");

			if (!ServiceStatus.PASS.name().equals(sanctionStatus)
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(sanctionStatus)
					&& !ServiceStatus.NOT_REQUIRED.name().equals(sanctionStatus)) {
				responseReasons.add(FundsInReasonCode.SANCTIONED);
			} else if (ServiceStatus.SERVICE_FAILURE.name().equals(sanctionStatus)) {
				responseReasons.add(FundsInReasonCode.SANCTION_SERVICE_FAIL);
			}

			if (responseReasons.isEmpty()) {
				response.setPaymentStatus(FundsInComplianceStatus.CLEAR.name());
			}
		} else if (request.getAction().equalsIgnoreCase(FundsInComplianceStatus.SEIZE.name())) {
			response.setPaymentStatus(FundsInComplianceStatus.SEIZE.name());
		} else if (request.getAction().equalsIgnoreCase(FundsInComplianceStatus.REJECT.name())) {
			response.setPaymentStatus(FundsInComplianceStatus.REJECT.name());
		}

	}

	/**
	 * Process funds out rule service.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ComplianceException the compliance exception
	 */
	private void processFundsOutRuleService(IntuitionPaymentStatusUpdateRequest request,
			IntuitionPaymentStatusUpdateResponse response) throws ComplianceException {
		FundsOutRequest fundsOutRequest = (FundsOutRequest) request.getAdditionalAttribute("fundsOutRequest");
		List<FundsOutReasonCode> responseReasons = new ArrayList<>(10);
		try {
			if (request.getAction().equalsIgnoreCase(FundsOutComplianceStatus.CLEAR.name())
					|| request.getAction().equalsIgnoreCase(FundsOutComplianceStatus.CLEAN.name())) {
				String paymentStatus = (String) fundsOutRequest.getAdditionalAttribute("status");
				response.setPaymentStatus(paymentStatus);

				setSanctionStatusResponse(fundsOutRequest, responseReasons);

				if (responseReasons.isEmpty()) {
					response.setPaymentStatus(FundsOutComplianceStatus.CLEAR.name());
				}
			} else if (request.getAction().equalsIgnoreCase(FundsOutComplianceStatus.SEIZE.name())) {
				response.setPaymentStatus(FundsOutComplianceStatus.SEIZE.name());
			} else if (request.getAction().equalsIgnoreCase(FundsOutComplianceStatus.REJECT.name())) {
				response.setPaymentStatus(FundsOutComplianceStatus.REJECT.name());
			}

		} catch (Exception e) {
			LOG.error("Error in processFundsOutRuleService() {1}", e);
		}

	}

	/**
	 * Sets the sanction status response.
	 *
	 * @param fundsOutRequest the funds out request
	 * @param responseReasons the response reasons
	 * @throws ComplianceException the compliance exception
	 */
	private void setSanctionStatusResponse(FundsOutRequest fundsOutRequest, List<FundsOutReasonCode> responseReasons)
			throws ComplianceException {

		String contactSanction = fundsOutDBServiceImpl.getContactSanctionStatus(fundsOutRequest.getContactId(),
				fundsOutRequest.getFundsOutId());
		String beneficierySanction = fundsOutDBServiceImpl.getBeneficierySanctionStatus(
				fundsOutRequest.getBeneficiary().getBeneficiaryId(), fundsOutRequest.getFundsOutId());
		String bankSanction = fundsOutDBServiceImpl.getBankSanctionStatus(
				fundsOutRequest.getBeneficiary().getBeneficiaryBankid(), fundsOutRequest.getFundsOutId());

		if (!ServiceStatus.PASS.name().equals(contactSanction)
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(contactSanction)
				&& !ServiceStatus.NOT_REQUIRED.name().equals(contactSanction)) {
			responseReasons.add(FundsOutReasonCode.CONTACT_SANCTIONED);
		} else if (ServiceStatus.NOT_REQUIRED.name().equals(contactSanction)) {
			responseReasons.add(FundsOutReasonCode.INACTIVE_CUSTOMER);
		}

		if (!ServiceStatus.NOT_REQUIRED.name().equals(bankSanction)) {
			if (!ServiceStatus.PASS.name().equals(bankSanction)
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(bankSanction)) {
				responseReasons.add(FundsOutReasonCode.BANK_SANCTIONED);
			}

			if (!ServiceStatus.PASS.name().equals(beneficierySanction)
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(beneficierySanction)) {
				responseReasons.add(FundsOutReasonCode.BENEFICIARY_SANCTIONED);
			}
		}
		
		if(ServiceStatus.SERVICE_FAILURE.name().equals(contactSanction)
				|| ServiceStatus.SERVICE_FAILURE.name().equals(beneficierySanction)
				|| ServiceStatus.SERVICE_FAILURE.name().equals(bankSanction)) {
			responseReasons.add(FundsOutReasonCode.SANCTION_SERVICE_FAIL);
		}
	}

}
