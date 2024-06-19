package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv.IntuitionHistoricPaymentEmailAlert;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class IntuitionHistoricPaymentInTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(IntuitionHistoricPaymentInTransformer.class);

	/** The funds in DB service impl. */
	@Autowired
	protected FundsInDBServiceImpl fundsInDBServiceImpl;

	/** The Constant CFXETAILER. */
	private static final String CFXETAILER = "CFX-Etailer";

	/** The Constant CHARACTER. */
	private static final String CHARACTER = "[\r\n]|[\t+]";
	
	/** The email alert. */
	@Autowired
	private IntuitionHistoricPaymentEmailAlert emailAlert;
	
	/** The Constant EMAIL_BODY_DATE_FORMAT. */
	private static final String EMAIL_BODY_DATE_FORMAT = "yyyy-MM-dd";

	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		IntuitionHistoricPaymentsRequest intuitionPaymentRequest = (IntuitionHistoricPaymentsRequest) messageExchange.getRequest();
		List<IntuitionHistoricPaymentRequest> intuitionPaymentInRequest = new ArrayList<>();
		try {
			List<FundsInCreateRequest> fundsInRequest = fundsInDBServiceImpl.getIntuitionHistoricPaymentIn();
			setDataForHistoricPaymentIn(fundsInRequest, intuitionPaymentInRequest);
			intuitionPaymentRequest.setIntuitionHistoricPaymentInRequest(intuitionPaymentInRequest);
		} catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentInTransformer transformRequest() : ", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	private void setDataForHistoricPaymentIn(List<FundsInCreateRequest> fundsInRequests,
			List<IntuitionHistoricPaymentRequest> intuitionPaymentInRequests) throws ComplianceException {
		try {
			for (FundsInCreateRequest request : fundsInRequests) {
				IntuitionHistoricPaymentRequest intuitionHistoricPaymentRequest = new IntuitionHistoricPaymentRequest();
				intuitionHistoricPaymentRequest.setTradeIdentificationNumber(
						request.getTrade().getContractNumber() + "_" + request.getFundsInId());
				intuitionHistoricPaymentRequest.setTradeContractNumber(request.getTrade().getContractNumber());
				intuitionHistoricPaymentRequest.setTradePaymentID(request.getTrade().getPaymentFundsInId().toString());
				intuitionHistoricPaymentRequest.setTradeAccountNumber(request.getTradeAccountNumber());
				intuitionHistoricPaymentRequest.setTimestamp(request.getTrade().getPaymentTime());
				String etailer = (String) request.getAdditionalAttribute(Constants.ETAILER);
				if (etailer != null && etailer.equalsIgnoreCase("true")) {
					intuitionHistoricPaymentRequest.setCustType(CFXETAILER);
				} else {
					intuitionHistoricPaymentRequest.setCustType(request.getCustType());
				}
				intuitionHistoricPaymentRequest.setPurposeOfTrade(request.getTrade().getPurposeOfTrade());
				intuitionHistoricPaymentRequest.setSellingAmount(request.getTrade().getSellingAmount());
				intuitionHistoricPaymentRequest.setTransactionCurrency(request.getTrade().getTransactionCurrency());
				intuitionHistoricPaymentRequest.setCustomerLegalEntity(request.getTrade().getCustLegalEntity());
				intuitionHistoricPaymentRequest.setPaymentMethod(request.getTrade().getPaymentMethod());
				intuitionHistoricPaymentRequest.setDebtorName((request.getTrade().getDebtorName() == null
						|| request.getTrade().getDebtorName().trim().isEmpty()) ? "--"
								: request.getTrade().getDebtorName().replaceAll(CHARACTER, " ").replaceAll("\"+", ""));
				intuitionHistoricPaymentRequest
						.setDebtorAccountNumber((request.getTrade().getDebtorAccountNumber() == null
								|| request.getTrade().getDebtorAccountNumber().trim().isEmpty()) ? "--"
										: request.getTrade().getDebtorAccountNumber());
				intuitionHistoricPaymentRequest.setBillAddressLine((request.getTrade().getBillAddressLine() != null)
						? request.getTrade().getBillAddressLine().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
						: null);
				intuitionHistoricPaymentRequest.setCountryOfFund((request.getTrade().getCountryOfFund() == null
						|| request.getTrade().getCountryOfFund().trim().isEmpty()) ? "--"
								: request.getTrade().getCountryOfFund());
				intuitionHistoricPaymentRequest.setTradeContactId(request.getTrade().getTradeContactId().toString());
				intuitionHistoricPaymentRequest.setTrxType("FUNDSIN");
				intuitionHistoricPaymentRequest.setContractNumber(request.getTrade().getContractNumber());
				intuitionHistoricPaymentRequest
						.setSellingAmountBaseValue(request.getTrade().getSellingAmountBaseValue());
				intuitionHistoricPaymentRequest.setThirdPartyPayment(request.getTrade().getThirdPartyPayment());

				intuitionHistoricPaymentRequest.setCcFirstName((request.getTrade().getCcFirstName() != null)
						? request.getTrade().getCcFirstName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
						: null);
				intuitionHistoricPaymentRequest.setBillAdZip(request.getTrade().getBillAdZip());
				intuitionHistoricPaymentRequest.setIsThreeds(request.getTrade().getIsThreeds());
				intuitionHistoricPaymentRequest.setAvsResult(request.getTrade().getAvsResult());
				intuitionHistoricPaymentRequest.setDebitCardAddedDate(request.getTrade().getDebitCardAddedDate());
				intuitionHistoricPaymentRequest
						.setVersion(request.getVersion() != null ? request.getVersion().toString() : null);
				intuitionHistoricPaymentRequest.setCVCCVVResult(request.getTrade().getCvcResult());
				if (request.getRiskScore() != null) {
					setRiskScoreData(request, intuitionHistoricPaymentRequest);
				}
				if (request.getFraudSight() != null) {
					intuitionHistoricPaymentRequest.setFraudsightReason(request.getFraudSight().getFsReasonCodes());
					intuitionHistoricPaymentRequest.setFraudsightRiskLevel(request.getFraudSight().getFsMessage());
				}
				if (request.getDeviceInfo() != null) {
					setDeviceInfoData(request, intuitionHistoricPaymentRequest);
				}
				setAllChecksStatusData(request, intuitionHistoricPaymentRequest);
				intuitionHistoricPaymentRequest.setAtlasSTPCodes((String) request.getAdditionalAttribute("STPFlag"));
				intuitionHistoricPaymentRequest.setUpdateStatus((String) request.getAdditionalAttribute("status"));
				
				intuitionHistoricPaymentRequest.setAccountId(request.getAccId());
				
				intuitionPaymentInRequests.add(intuitionHistoricPaymentRequest);
			}
		} catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentInTransformer setDataForHistoricPaymentIn() : ", e);
			
			emailAlert.sendEmailAlertForHistoricPaymentError(
					"Intuition Historic Payment In upload error - batch file generation",
					"Intuition (Transaction monitoring) Historical Payment In upload failed due to batch file generation error on "
							+ new java.text.SimpleDateFormat(EMAIL_BODY_DATE_FORMAT).format(new java.util.Date())
							+ ". Please alert compliance officer.");
			
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		}

	}

	private void setDeviceInfoData(FundsInCreateRequest request,
			IntuitionHistoricPaymentRequest intuitionHistoricPaymentRequest) {
		intuitionHistoricPaymentRequest.setBrwsrType(request.getDeviceInfo().getBrowserName());
		intuitionHistoricPaymentRequest.setBrwsrVersion(request.getDeviceInfo().getBrowserMajorVersion());
		intuitionHistoricPaymentRequest.setBrwsrLang(request.getDeviceInfo().getBrowserLanguage());
		intuitionHistoricPaymentRequest.setBrowserOnline(request.getDeviceInfo().getBrowserOnline());
		intuitionHistoricPaymentRequest.setDeviceName(request.getDeviceInfo().getDeviceName());
		intuitionHistoricPaymentRequest.setDeviceVersion(request.getDeviceInfo().getDeviceVersion());
		intuitionHistoricPaymentRequest.setDeviceId(request.getDeviceInfo().getDeviceId());
		intuitionHistoricPaymentRequest.setDeviceManufacturer(request.getDeviceInfo().getDeviceManufacturer());
		intuitionHistoricPaymentRequest.setDeviceType(request.getDeviceInfo().getDeviceType());
		intuitionHistoricPaymentRequest.setOsType(request.getDeviceInfo().getOsName());
		intuitionHistoricPaymentRequest.setScreenResolution(request.getDeviceInfo().getScreenResolution());
	}

	private void setAllChecksStatusData(FundsInCreateRequest request,
			IntuitionHistoricPaymentRequest intuitionHistoricPaymentRequest) throws ComplianceException {
		intuitionHistoricPaymentRequest.setBlacklist((String) request.getAdditionalAttribute("BLACKLIST_STATUS"));
		intuitionHistoricPaymentRequest.setSanctionsContact((String) request.getAdditionalAttribute("SANCTION_STATUS"));
		intuitionHistoricPaymentRequest.setCustomCheck((String) request.getAdditionalAttribute("CUSTOM_CHECK_STATUS"));
		intuitionHistoricPaymentRequest.setFraudPredict((String) request.getAdditionalAttribute("FRAUGSTER_STATUS"));

		String countryCheckStatus = fundsInDBServiceImpl.getCountryCheckStatus(request);
		intuitionHistoricPaymentRequest.setCountryCheck(countryCheckStatus);

		String statusUpdateReason = fundsInDBServiceImpl.getPaymentInStatusUpdateReason(request);
		intuitionHistoricPaymentRequest.setStatusUpdateReason(statusUpdateReason);

		List<String> watchList = fundsInDBServiceImpl.getContactWatchlist(request.getContactId());
		intuitionHistoricPaymentRequest.setWatchlist(watchList.toString());
	}

	private void setRiskScoreData(FundsInCreateRequest request,
			IntuitionHistoricPaymentRequest intuitionHistoricPaymentRequest) {
		intuitionHistoricPaymentRequest.setMessage(request.getRiskScore().getMessage());
		intuitionHistoricPaymentRequest.setRGID(request.getRiskScore().getRGID());
		if (request.getRiskScore().getTScore() != null)
			intuitionHistoricPaymentRequest.settScore(request.getRiskScore().getTScore());
		if (request.getRiskScore().getTRisk() != null)
			intuitionHistoricPaymentRequest.settRisk(request.getRiskScore().getTRisk().toString());
	}

	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {

		return message;
	}

}
