package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv.IntuitionHistoricPaymentEmailAlert;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class IntuitionHistoricPaymentOutTransformer extends AbstractTransformer{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(IntuitionHistoricPaymentOutTransformer.class);
	
	@Autowired
	FundsOutDBServiceImpl fundsOutDBServiceImpl;
	
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
		List<IntuitionHistoricPaymentRequest> intuitionPaymentOutRequest = new ArrayList<>();
		try {
			List<FundsOutRequest> fundsOutRequest = fundsOutDBServiceImpl.getIntuitionHistoricPaymentOut();
			setDataForHistoricPaymentOut(fundsOutRequest, intuitionPaymentOutRequest);
			intuitionPaymentRequest.setIntuitionHistoricPaymentOutRequest(intuitionPaymentOutRequest);
		}
		catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentOutTransformer transformRequest() : ", e);
			message.getPayload().setFailed(true);
		}
		
		return message;
	}

	private void setDataForHistoricPaymentOut(List<FundsOutRequest> fundsOutRequests,
			List<IntuitionHistoricPaymentRequest> intuitionPaymentOutRequests) throws ComplianceException {
		try {
			for (FundsOutRequest request : fundsOutRequests) {
				IntuitionHistoricPaymentRequest intuitionHistoricPaymentRequest = new IntuitionHistoricPaymentRequest();
				intuitionHistoricPaymentRequest.setTradeIdentificationNumber(
						request.getTrade().getContractNumber() + "_" + request.getFundsOutId());
				intuitionHistoricPaymentRequest.setTradeContractNumber(request.getTrade().getContractNumber());
				intuitionHistoricPaymentRequest.setTradePaymentID(request.getBeneficiary().getPaymentFundsoutId().toString());
				intuitionHistoricPaymentRequest.setTradeAccountNumber(request.getTradeAccountNumber());
				intuitionHistoricPaymentRequest.setTimestamp(request.getBeneficiary().getTransactionDateTime());
				intuitionHistoricPaymentRequest.setMaturityDate(request.getTrade().getMaturityDate());
				intuitionHistoricPaymentRequest.setDealDate(request.getTrade().getDealDate());
				String etailer = (String) request.getAdditionalAttribute(Constants.ETAILER);
				if (etailer != null && etailer.equalsIgnoreCase("true")) {
					intuitionHistoricPaymentRequest.setCustType(CFXETAILER);
				} else {
					intuitionHistoricPaymentRequest
							.setCustType(request.getTrade().getCustType() == null ? request.getCustType()
									: request.getTrade().getCustType()); //AT-5486
				}
				intuitionHistoricPaymentRequest.setPurposeOfTrade(request.getTrade().getPurposeOfTrade());
				intuitionHistoricPaymentRequest.setBuyingAmount(request.getTrade().getBuyingAmount());
				intuitionHistoricPaymentRequest.setBuyCurrency(request.getTrade().getBuyCurrency());
				intuitionHistoricPaymentRequest.setSellCurrency(request.getTrade().getSellCurrency());
				intuitionHistoricPaymentRequest.setSellingAmount(request.getTrade().getSellingAmount());
				intuitionHistoricPaymentRequest.setCustomerLegalEntity(request.getTrade().getCustLegalEntity());
				intuitionHistoricPaymentRequest.setAmount(request.getBeneficiary().getAmount());
				intuitionHistoricPaymentRequest.setFirstName((request.getBeneficiary().getFirstName() != null)
						? request.getBeneficiary().getFirstName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
						: null);
				intuitionHistoricPaymentRequest.setLastName((request.getBeneficiary().getLastName() != null)
						? request.getBeneficiary().getLastName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
						: null);
				intuitionHistoricPaymentRequest.setCountry(request.getBeneficiary().getCountry());
				intuitionHistoricPaymentRequest.setEmail(request.getBeneficiary().getEmail());
				intuitionHistoricPaymentRequest.setPhone(request.getBeneficiary().getPhone());
				
				intuitionHistoricPaymentRequest.setAccountNumber(request.getBeneficiary().getAccountNumber());
				intuitionHistoricPaymentRequest.setBuyingAmountBaseValue(request.getTrade().getBuyingAmountBaseValue());
				intuitionHistoricPaymentRequest.setValueDate(request.getTrade().getValueDate());
				intuitionHistoricPaymentRequest.setOpiCreatedDate(request.getBeneficiary().getOpiCreatedDate());
				intuitionHistoricPaymentRequest.setCurrencyCode(request.getBeneficiary().getCurrencyCode());
				intuitionHistoricPaymentRequest.setBeneficiaryId(request.getBeneficiary().getBeneficiaryId().toString());
				intuitionHistoricPaymentRequest.setBeneficiaryBankid(request.getBeneficiary().getBeneficiaryBankid().toString());
				intuitionHistoricPaymentRequest.setBeneficaryBankName(request.getBeneficiary().getBeneficaryBankName());
				intuitionHistoricPaymentRequest.setBeneficaryBankAddress(request.getBeneficiary().getBeneficaryBankAddress());	
				intuitionHistoricPaymentRequest.setPaymentReference(request.getBeneficiary().getPaymentReference());
				intuitionHistoricPaymentRequest
						.setVersion(request.getVersion() != null ? request.getVersion().toString() : null);
				intuitionHistoricPaymentRequest.setTradeContactId(request.getTrade().getTradeContactId().toString());
				intuitionHistoricPaymentRequest.setTrxType("FUNDSOUT");
				intuitionHistoricPaymentRequest.setContractNumber(request.getTrade().getContractNumber());
				if (request.getDeviceInfo() != null) {
					setDeviceInfoData(request, intuitionHistoricPaymentRequest);
				}
				setAllChecksStatusData(request, intuitionHistoricPaymentRequest);
				intuitionHistoricPaymentRequest.setAtlasSTPCodes((String) request.getAdditionalAttribute("STPFlag"));
				intuitionHistoricPaymentRequest.setUpdateStatus((String) request.getAdditionalAttribute("status"));
				intuitionHistoricPaymentRequest.setAccountId(request.getAccId());
				intuitionPaymentOutRequests.add(intuitionHistoricPaymentRequest);
				
				
			}
		} catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentOutTransformer setDataForHistoricPaymentOut() : ", e);
			
			emailAlert.sendEmailAlertForHistoricPaymentError(
					"Intuition Historic Payment Out upload error - batch file generation",
					"Intuition (Transaction monitoring) Historical Payment Out upload failed due to batch file generation error on "
							+ new java.text.SimpleDateFormat(EMAIL_BODY_DATE_FORMAT).format(new java.util.Date())
							+ ". Please alert compliance officer.");
			
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		}

	}
	private void setDeviceInfoData(FundsOutRequest request,
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
	private void setAllChecksStatusData(FundsOutRequest request,
			IntuitionHistoricPaymentRequest intuitionHistoricPaymentRequest) throws ComplianceException {
		
		intuitionHistoricPaymentRequest.setBlacklist((String) request.getAdditionalAttribute("BLACKLIST_STATUS"));
		intuitionHistoricPaymentRequest.setCustomCheck((String) request.getAdditionalAttribute("CUSTOM_CHECK_STATUS"));
		intuitionHistoricPaymentRequest.setSanctionsContact((String) request.getAdditionalAttribute("SANCTION_STATUS"));
		intuitionHistoricPaymentRequest.setFraudPredict((String) request.getAdditionalAttribute("FRAUGSTER_STATUS"));
		intuitionHistoricPaymentRequest.setPaymentReferenceCheck((String) request.getAdditionalAttribute("PAYMENT_REFERENCE_STATUS"));

		String countryCheckStatus = fundsOutDBServiceImpl.getCountryCheckStatusForIntuition(request);
		intuitionHistoricPaymentRequest.setCountryCheck(countryCheckStatus);
		String statusUpdateReason = fundsOutDBServiceImpl.getStatusUpdateReasonForIntuition(request);
		intuitionHistoricPaymentRequest.setStatusUpdateReason(statusUpdateReason);
		String contactSanctionStatus = fundsOutDBServiceImpl.getContactSanctionStatus(request.getContactId(),request.getFundsOutId());
		String beneficierySanctionStatus =fundsOutDBServiceImpl.getBeneficierySanctionStatus(request.getBeneficiary().getBeneficiaryId(), request.getFundsOutId());
		String bankSanctionStatus =fundsOutDBServiceImpl.getBankSanctionStatus(request.getBeneficiary().getBeneficiaryBankid(),request.getFundsOutId());
		intuitionHistoricPaymentRequest.setSanctionsContact(contactSanctionStatus);
		intuitionHistoricPaymentRequest.setSanctionsBeneficiary(beneficierySanctionStatus);
		intuitionHistoricPaymentRequest.setSanctionsBank(bankSanctionStatus);
		List<String> watchList = fundsOutDBServiceImpl.getContactWatchlist(request.getContactId());
		intuitionHistoricPaymentRequest.setWatchlist(watchList.toString());
	}
	
	
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {

		return null;
	}

}
