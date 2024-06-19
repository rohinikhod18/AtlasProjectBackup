package com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.opencsv.CSVWriter;

public class IntuitionHistoricPaymentCSVTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(IntuitionHistoricPaymentCSVTransformer.class);
	
	/** The email alert. */
	@Autowired
	private IntuitionHistoricPaymentEmailAlert emailAlert;
	
	/** The Constant EMAIL_BODY_DATE_FORMAT. */
	private static final String EMAIL_BODY_DATE_FORMAT = "yyyy-MM-dd";
	
	/** The Constant TIME_STRING. */
	private static final String TIME_STRING = " 00:00:00";
	
	/** The Constant PAYMENT_IN_LOCAL_SAVE_FLAG. */
	private static final String PAYMENT_IN_LOCAL_SAVE_FLAG = "IntuitionHistoricPaymentInLocalSave";
	
	/** The Constant PAYMENT_OUT_LOCAL_SAVE_FLAG. */
	private static final String PAYMENT_OUT_LOCAL_SAVE_FLAG = "IntuitionHistoricPaymentOutLocalSave";

	public Message<MessageContext> saveCsvFileToLocalServer(Message<MessageContext> message) {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			IntuitionHistoricPaymentsRequest intuitionPaymentRequest = (IntuitionHistoricPaymentsRequest) messageExchange
					.getRequest();

			transformIntuitionHistoricPaymentInCSVData(intuitionPaymentRequest);
			transformIntuitionHistoricPaymentOutCSVData(intuitionPaymentRequest);
		} catch (Exception e) {
			LOG.error("Error in IntuitionHistoricPaymentCSVTransformer saveCsvFileToLocalServer(): ", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	private void transformIntuitionHistoricPaymentInCSVData(IntuitionHistoricPaymentsRequest intuitionPaymentRequest) {
		try {
			List<IntuitionHistoricPaymentRequest> intuitionPaymentInRequest = intuitionPaymentRequest
					.getIntuitionHistoricPaymentInRequest();

			String localDirectory = System.getProperty("intuition.historic.payment.csv.local.directory");
			String fileName = "Payment_In_Historic_Data_"
					+ new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + ".csv";
			String trimLocation = localDirectory.trim();
			String localFileLocation = trimLocation + fileName;
			
			if (intuitionPaymentInRequest != null && !intuitionPaymentInRequest.isEmpty()) {
				File file = new File(localFileLocation);
				writeDataLineByLine(file, intuitionPaymentInRequest);
				LOG.info(
						"--------------------------------Intuition Historic Payment In File saved with a name : {}-------------------------",
						fileName);
				intuitionPaymentRequest.addAttribute("IntuitionHistoricPaymentInLocalFile", localFileLocation);
				intuitionPaymentRequest.addAttribute("IntuitionHistoricPaymentInLocalFileName", fileName);
				intuitionPaymentRequest.addAttribute(PAYMENT_IN_LOCAL_SAVE_FLAG, Boolean.TRUE);
			} else {
				intuitionPaymentRequest.addAttribute(PAYMENT_IN_LOCAL_SAVE_FLAG, Boolean.FALSE);
			}

		} catch (Exception e) {
			emailAlert.sendEmailAlertForHistoricPaymentError(
                    "Intuition Historic Payment In upload error - batch file save",
                    "Intuition (Transaction monitoring) Historical Payment In upload failed due to batch file save error on "
                            + new java.text.SimpleDateFormat(EMAIL_BODY_DATE_FORMAT).format(new java.util.Date())
                            + ". Please alert compliance team for support.");
			intuitionPaymentRequest.addAttribute(PAYMENT_IN_LOCAL_SAVE_FLAG, Boolean.FALSE);
			LOG.error("Error in IntuitionHistoricPaymentCSVTransformer transformIntuitionHistoricPaymentInCSVData() : ",
					e);
		}

	}

	private void writeDataLineByLine(File file, List<IntuitionHistoricPaymentRequest> intuitionPaymentInRequests)
			throws IOException {
		FileWriter outputfile = new FileWriter(file);
		CSVWriter writer = new CSVWriter(outputfile);
		try {
			String[] header = { "TradeIdentificationNumber", "TradeContractNumber", "TradePaymentID",
					"trade_account_number", "timestamp", "deal_date", "cust_type", "purpose_of_trade", "buying_amount",
					"buy_currency", "selling_amount", "sell_currency", "transaction_currency", "customer_legal_entity",
					"payment_Method", "amount", "first_name", "last_name", "country", "email", "phone",
					"account_number", "currency_code", "beneficiary_id", "beneficiary_bankid", "beneficary_bank_name",
					"beneficary_bank_address", "payment_reference", "payment_ref_matched_keyword", "debtor_name",
					"debtor_account_number", "bill_address_line", "country_of_fund", "watchlist",
					"Transactional.0.trade_account_number", "Transactional.0.trade_contact_id",
					"Transactional.0.trx_type", "Transactional.0.purpose_of_trade", "Transactional.0.contract_number",
					"Transactional.0.deal_date", "Transactional.0.third_party_payment",
					"Transactional.0.buying_amount_base_value", "Transactional.0.selling_amount_base_value",
					"Transactional.0.channel", "Debit_Card.0.debtor_name", "Debit_Card.0.debtor_account_number",
					"Debit_Card.0.cc_first_name", "Debit_Card.0.bill_ad_zip", "Debit_Card.0.country_of_fund",
					"Debit_Card.0.is_threeds", "Debit_Card.0.avs_result", "Debit_Card.0.debit_card_added_date",
					"Debit_Card.0.message", "Debit_Card.0.RGID", "Debit_Card.0.tScore", "Debit_Card.0.tRisk",
					"Debit_Card.0.fraudsight_risk_level", "Debit_Card.0.fraudsight_reason",
					"Debit_Card.0.CVC_CVV_Result", "Device_and_IP.0.brwsr_type", "Device_and_IP.0.brwsr_version",
					"Device_and_IP.0.brwsr_lang", "Device_and_IP.0.browser_online", "Device_and_IP.0.version",
					"Device_and_IP.0.status_update_reason", "Device_and_IP.0.device_name",
					"Device_and_IP.0.device_version", "Device_and_IP.0.device_id",
					"Device_and_IP.0.device_manufacturer", "Device_and_IP.0.device_type", "Device_and_IP.0.os_type",
					"Device_and_IP.0.screen_resolution", "Atlas_Flags.0.update_status", "Atlas_Flags.0.blacklist",
					"Atlas_Flags.0.country_check", "Atlas_Flags.0.sanctions_contact",
					"Atlas_Flags.0.sanctions_beneficiary", "Atlas_Flags.0.sanctions_bank",
					"Atlas_Flags.0.sanctions_3rd_party", "Atlas_Flags.0.payment_reference_check",
					"Atlas_Flags.0.fraud_predict", "Atlas_Flags.0.custom_check", "Atlas_Flags.0.atlas_STP_codes" };

			writer.writeNext(header);

			List<String[]> datas = new ArrayList<>();
			for (IntuitionHistoricPaymentRequest intuitionPaymentInRequest : intuitionPaymentInRequests) {
				String[] data = { intuitionPaymentInRequest.getTradeIdentificationNumber(),
						intuitionPaymentInRequest.getTradeContractNumber(),
						intuitionPaymentInRequest.getTradePaymentID(),
						intuitionPaymentInRequest.getTradeAccountNumber(), intuitionPaymentInRequest.getTimestamp(),
						intuitionPaymentInRequest.getDealDate(), intuitionPaymentInRequest.getCustType(),
						intuitionPaymentInRequest.getPurposeOfTrade(),
						intuitionPaymentInRequest.getBuyingAmount() != null
								? intuitionPaymentInRequest.getBuyingAmount().toString()
								: "",
						intuitionPaymentInRequest.getBuyCurrency(),
						intuitionPaymentInRequest.getSellingAmount() != null
								? intuitionPaymentInRequest.getSellingAmount().toString()
								: "",
						intuitionPaymentInRequest.getSellCurrency(), intuitionPaymentInRequest.getTransactionCurrency(),
						intuitionPaymentInRequest.getCustomerLegalEntity(),
						intuitionPaymentInRequest.getPaymentMethod(),
						intuitionPaymentInRequest.getAmount() != null ? intuitionPaymentInRequest.getAmount().toString()
								: "",
						intuitionPaymentInRequest.getFirstName(), intuitionPaymentInRequest.getLastName(),
						intuitionPaymentInRequest.getCountry(), intuitionPaymentInRequest.getEmail(),
						intuitionPaymentInRequest.getPhone(), intuitionPaymentInRequest.getAccountNumber(),
						intuitionPaymentInRequest.getCurrencyCode(), intuitionPaymentInRequest.getBeneficiaryId(),
						intuitionPaymentInRequest.getBeneficiaryBankid(),
						intuitionPaymentInRequest.getBeneficaryBankName(),
						intuitionPaymentInRequest.getBeneficaryBankAddress(),
						intuitionPaymentInRequest.getPaymentReference(),
						intuitionPaymentInRequest.getPaymentRefMatchedKeyword(),
						intuitionPaymentInRequest.getDebtorName(), intuitionPaymentInRequest.getDebtorAccountNumber(),
						intuitionPaymentInRequest.getBillAddressLine(), intuitionPaymentInRequest.getCountryOfFund(),
						intuitionPaymentInRequest.getWatchlist(), intuitionPaymentInRequest.getTradeAccountNumber(),
						intuitionPaymentInRequest.getTradeContactId(), intuitionPaymentInRequest.getTrxType(),
						intuitionPaymentInRequest.getPurposeOfTrade(),
						intuitionPaymentInRequest.getTradeContractNumber(), intuitionPaymentInRequest.getDealDate(),
						String.valueOf(intuitionPaymentInRequest.isThirdPartyPayment()),
						intuitionPaymentInRequest.getBuyingAmountBaseValue() != null
								? intuitionPaymentInRequest.getBuyingAmountBaseValue().toString()
								: "",
						intuitionPaymentInRequest.getSellingAmountBaseValue() != null
								? intuitionPaymentInRequest.getSellingAmountBaseValue().toString()
								: "",
						intuitionPaymentInRequest.getChannel(), intuitionPaymentInRequest.getDebtorName(),
						intuitionPaymentInRequest.getDebtorAccountNumber(), intuitionPaymentInRequest.getCcFirstName(),
						intuitionPaymentInRequest.getBillAdZip(), intuitionPaymentInRequest.getCountryOfFund(),
						intuitionPaymentInRequest.getIsThreeds(), intuitionPaymentInRequest.getAvsResult(),
						intuitionPaymentInRequest.getDebitCardAddedDate(), intuitionPaymentInRequest.getMessage(),
						intuitionPaymentInRequest.getRGID(),
						intuitionPaymentInRequest.gettScore() != null ? intuitionPaymentInRequest.gettScore().toString()
								: "",
						intuitionPaymentInRequest.gettRisk(), intuitionPaymentInRequest.getFraudsightRiskLevel(),
						intuitionPaymentInRequest.getFraudsightReason(), intuitionPaymentInRequest.getCVCCVVResult(),
						intuitionPaymentInRequest.getBrwsrType(), intuitionPaymentInRequest.getBrwsrVersion(),
						intuitionPaymentInRequest.getBrwsrLang(), intuitionPaymentInRequest.getBrowserOnline(),
						intuitionPaymentInRequest.getVersion(), intuitionPaymentInRequest.getStatusUpdateReason(),
						intuitionPaymentInRequest.getDeviceName(), intuitionPaymentInRequest.getDeviceVersion(),
						intuitionPaymentInRequest.getDeviceId(), intuitionPaymentInRequest.getDeviceManufacturer(),
						intuitionPaymentInRequest.getDeviceType(), intuitionPaymentInRequest.getOsType(),
						intuitionPaymentInRequest.getScreenResolution(), intuitionPaymentInRequest.getUpdateStatus(),
						intuitionPaymentInRequest.getBlacklist(), intuitionPaymentInRequest.getCountryCheck(),
						intuitionPaymentInRequest.getSanctionsContact(),
						intuitionPaymentInRequest.getSanctionsBeneficiary(),
						intuitionPaymentInRequest.getSanctionsBank(), intuitionPaymentInRequest.getSanctions3rdParty(),
						intuitionPaymentInRequest.getPaymentReferenceCheck(),
						intuitionPaymentInRequest.getFraudPredict(), intuitionPaymentInRequest.getCustomCheck(),
						intuitionPaymentInRequest.getAtlasSTPCodes() };

				datas.add(data);
			}

			writer.writeAll(datas);

		} catch (Exception e) {
			emailAlert.sendEmailAlertForHistoricPaymentError(
					"Intuition Historic Payment In upload error - batch file generation",
					"Intuition (Transaction monitoring) Historical Payment In upload failed due to batch file generation error on "
							+ new java.text.SimpleDateFormat(EMAIL_BODY_DATE_FORMAT).format(new java.util.Date())
							+ ". Please alert compliance officer.");
			
			LOG.error("Error in IntuitionHistoricPaymentCSVTransformer writeDataLineByLine(): ", e);
		} finally {
			writer.close();
			outputfile.close();
		}
	}
	private void transformIntuitionHistoricPaymentOutCSVData(IntuitionHistoricPaymentsRequest intuitionPaymentRequest) {
		try {
			List<IntuitionHistoricPaymentRequest> intuitionPaymentOutRequest = intuitionPaymentRequest.getIntuitionHistoricPaymentOutRequest();

			String localDirectory = System.getProperty("intuition.historic.payment.csv.local.directory");
			String fileName = "Payment_Out_Historic_Data_"
					+ new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + ".csv";
			String trimLocation = localDirectory.trim();
			String localFileLocation = trimLocation + fileName;

			if (intuitionPaymentOutRequest != null && !intuitionPaymentOutRequest.isEmpty()) {
				File file = new File(localFileLocation);

				writeDataLineByLinePaymentOut(file, intuitionPaymentOutRequest);
				LOG.info(
						"--------------------------------Intuition Historic Payment Out File saved with a name : {}-------------------------",
						fileName);
				intuitionPaymentRequest.addAttribute("IntuitionHistoricPaymentOutLocalFile", localFileLocation);
				intuitionPaymentRequest.addAttribute("IntuitionHistoricPaymentOutLocalFileName", fileName);
				intuitionPaymentRequest.addAttribute(PAYMENT_OUT_LOCAL_SAVE_FLAG, Boolean.TRUE);
			} else {
				intuitionPaymentRequest.addAttribute(PAYMENT_OUT_LOCAL_SAVE_FLAG, Boolean.FALSE);
			}

		} catch (Exception e) {
			emailAlert.sendEmailAlertForHistoricPaymentError(
                    "Intuition Historic Payment Out upload error - batch file save",
                    "Intuition (Transaction monitoring) Historical Payment Out upload failed due to batch file save error on "
                            + new java.text.SimpleDateFormat(EMAIL_BODY_DATE_FORMAT).format(new java.util.Date())
                            + ". Please alert compliance team for support.");
			intuitionPaymentRequest.addAttribute(PAYMENT_OUT_LOCAL_SAVE_FLAG, Boolean.FALSE);
			LOG.error("Error in IntuitionHistoricPaymentCSVTransformer transformIntuitionHistoricPaymentOutCSVData() : ",
					e);
		}
	}
		private void writeDataLineByLinePaymentOut(File file,
				List<IntuitionHistoricPaymentRequest> intuitionPaymentInRequests) throws IOException {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			try {
				String[] header = { "TradeIdentificationNumber", "TradeContractNumber", "TradePaymentID",
						"trade_account_number", "timestamp", "deal_date", "cust_type", "purpose_of_trade", "buying_amount",
						"buy_currency", "selling_amount", "sell_currency", "transaction_currency", "customer_legal_entity",
						"payment_Method", "amount", "first_name", "last_name", "country", "email", "phone",
						"account_number", "currency_code", "beneficiary_id", "beneficiary_bankid", "beneficary_bank_name",
						"beneficary_bank_address", "payment_reference", "payment_ref_matched_keyword","maturity_date", "debtor_name",
						"debtor_account_number", "bill_address_line", "country_of_fund", "watchlist",
						"Transactional.0.trade_account_number", "Transactional.0.trade_contact_id",
						"Transactional.0.trx_type", "Transactional.0.purpose_of_trade", "Transactional.0.contract_number",
						"Transactional.0.deal_date", "Transactional.0.third_party_payment",
						"Transactional.0.buying_amount_base_value", "Transactional.0.selling_amount_base_value",
						"Transactional.0.value_date","Transactional.0.opi_created_date","Transactional.0.maturity_date",
						"Transactional.0.channel", "Debit_Card.0.debtor_name", "Debit_Card.0.debtor_account_number",
						"Debit_Card.0.cc_first_name", "Debit_Card.0.bill_ad_zip", "Debit_Card.0.country_of_fund",
						"Debit_Card.0.is_threeds", "Debit_Card.0.avs_result", "Debit_Card.0.debit_card_added_date",
						"Debit_Card.0.message", "Debit_Card.0.RGID", "Debit_Card.0.tScore", "Debit_Card.0.tRisk",
						"Debit_Card.0.fraudsight_risk_level", "Debit_Card.0.fraudsight_reason",
						"Debit_Card.0.CVC_CVV_Result", "Device_and_IP.0.brwsr_type", "Device_and_IP.0.brwsr_version",
						"Device_and_IP.0.brwsr_lang", "Device_and_IP.0.browser_online", "Device_and_IP.0.version",
						"Device_and_IP.0.status_update_reason", "Device_and_IP.0.device_name",
						"Device_and_IP.0.device_version", "Device_and_IP.0.device_id",
						"Device_and_IP.0.device_manufacturer", "Device_and_IP.0.device_type", "Device_and_IP.0.os_type",
						"Device_and_IP.0.screen_resolution", "Atlas_Flags.0.update_status", "Atlas_Flags.0.blacklist",
						"Atlas_Flags.0.country_check", "Atlas_Flags.0.sanctions_contact",
						"Atlas_Flags.0.sanctions_beneficiary", "Atlas_Flags.0.sanctions_bank",
						"Atlas_Flags.0.sanctions_3rd_party", "Atlas_Flags.0.payment_reference_check",
						"Atlas_Flags.0.fraud_predict", "Atlas_Flags.0.custom_check", "Atlas_Flags.0.atlas_STP_codes" };

				writer.writeNext(header);

				List<String[]> datas = new ArrayList<>();
				for (IntuitionHistoricPaymentRequest intuitionPaymentOutRequest : intuitionPaymentInRequests) {
					String[] data = { intuitionPaymentOutRequest.getTradeIdentificationNumber(),
									intuitionPaymentOutRequest.getTradeContractNumber(),
									intuitionPaymentOutRequest.getTradePaymentID(),
									intuitionPaymentOutRequest.getTradeAccountNumber(), intuitionPaymentOutRequest.getTimestamp(),
									intuitionPaymentOutRequest.getDealDate()+TIME_STRING, intuitionPaymentOutRequest.getCustType(),
									intuitionPaymentOutRequest.getPurposeOfTrade(),
									intuitionPaymentOutRequest.getBuyingAmount() != null
									? intuitionPaymentOutRequest.getBuyingAmount().toString()
									: "",
									intuitionPaymentOutRequest.getBuyCurrency(),
									intuitionPaymentOutRequest.getSellingAmount() != null
									? intuitionPaymentOutRequest.getSellingAmount().toString()
									: "",
									intuitionPaymentOutRequest.getSellCurrency(), intuitionPaymentOutRequest.getTransactionCurrency(),
									intuitionPaymentOutRequest.getCustomerLegalEntity(),
									intuitionPaymentOutRequest.getPaymentMethod(),
									intuitionPaymentOutRequest.getAmount() != null ? intuitionPaymentOutRequest.getAmount().toString()
									: "",
									intuitionPaymentOutRequest.getFirstName(), intuitionPaymentOutRequest.getLastName(),
									intuitionPaymentOutRequest.getCountry(), intuitionPaymentOutRequest.getEmail(),
									intuitionPaymentOutRequest.getPhone(), intuitionPaymentOutRequest.getAccountNumber(),
									intuitionPaymentOutRequest.getCurrencyCode(), intuitionPaymentOutRequest.getBeneficiaryId(),
									intuitionPaymentOutRequest.getBeneficiaryBankid(),
									intuitionPaymentOutRequest.getBeneficaryBankName(),
									intuitionPaymentOutRequest.getBeneficaryBankAddress(),
									intuitionPaymentOutRequest.getPaymentReference(),
									intuitionPaymentOutRequest.getPaymentRefMatchedKeyword(), intuitionPaymentOutRequest.getMaturityDate(),
									intuitionPaymentOutRequest.getDebtorName(), intuitionPaymentOutRequest.getDebtorAccountNumber(),
									intuitionPaymentOutRequest.getBillAddressLine(), intuitionPaymentOutRequest.getCountryOfFund(),
									intuitionPaymentOutRequest.getWatchlist(), intuitionPaymentOutRequest.getTradeAccountNumber(),
									intuitionPaymentOutRequest.getTradeContactId(), intuitionPaymentOutRequest.getTrxType(),
									intuitionPaymentOutRequest.getPurposeOfTrade(),
									intuitionPaymentOutRequest.getTradeContractNumber(), 
									intuitionPaymentOutRequest.getDealDate()+TIME_STRING,
							String.valueOf(intuitionPaymentOutRequest.isThirdPartyPayment()),
							intuitionPaymentOutRequest.getBuyingAmountBaseValue() != null
									? intuitionPaymentOutRequest.getBuyingAmountBaseValue().toString()
									: "",
									intuitionPaymentOutRequest.getSellingAmountBaseValue() != null
									? intuitionPaymentOutRequest.getSellingAmountBaseValue().toString()
									: "",
									intuitionPaymentOutRequest.getValueDate(),intuitionPaymentOutRequest.getOpiCreatedDate(),
									intuitionPaymentOutRequest.getMaturityDate()+TIME_STRING,
									intuitionPaymentOutRequest.getChannel(),
									intuitionPaymentOutRequest.getDebtorName(),
									intuitionPaymentOutRequest.getDebtorAccountNumber(), intuitionPaymentOutRequest.getCcFirstName(),
									intuitionPaymentOutRequest.getBillAdZip(), intuitionPaymentOutRequest.getCountryOfFund(),
									intuitionPaymentOutRequest.getIsThreeds(), intuitionPaymentOutRequest.getAvsResult(),
									intuitionPaymentOutRequest.getDebitCardAddedDate(), intuitionPaymentOutRequest.getMessage(),
									intuitionPaymentOutRequest.getRGID(),
									intuitionPaymentOutRequest.gettScore() != null ? intuitionPaymentOutRequest.gettScore().toString()
									: "",
									intuitionPaymentOutRequest.gettRisk(), intuitionPaymentOutRequest.getFraudsightRiskLevel(),
									intuitionPaymentOutRequest.getFraudsightReason(), intuitionPaymentOutRequest.getCVCCVVResult(),
							intuitionPaymentOutRequest.getBrwsrType(), intuitionPaymentOutRequest.getBrwsrVersion(),
							intuitionPaymentOutRequest.getBrwsrLang(), intuitionPaymentOutRequest.getBrowserOnline(),
							intuitionPaymentOutRequest.getVersion(), intuitionPaymentOutRequest.getStatusUpdateReason(),
							intuitionPaymentOutRequest.getDeviceName(), intuitionPaymentOutRequest.getDeviceVersion(),
							intuitionPaymentOutRequest.getDeviceId(), intuitionPaymentOutRequest.getDeviceManufacturer(),
							intuitionPaymentOutRequest.getDeviceType(), intuitionPaymentOutRequest.getOsType(),
							intuitionPaymentOutRequest.getScreenResolution(), intuitionPaymentOutRequest.getUpdateStatus(),
							intuitionPaymentOutRequest.getBlacklist(), intuitionPaymentOutRequest.getCountryCheck(),
							intuitionPaymentOutRequest.getSanctionsContact(),
							intuitionPaymentOutRequest.getSanctionsBeneficiary(),
							intuitionPaymentOutRequest.getSanctionsBank(), intuitionPaymentOutRequest.getSanctions3rdParty(),
							intuitionPaymentOutRequest.getPaymentReferenceCheck(),
							intuitionPaymentOutRequest.getFraudPredict(), intuitionPaymentOutRequest.getCustomCheck(),
							intuitionPaymentOutRequest.getAtlasSTPCodes() };

					datas.add(data);
				}

				writer.writeAll(datas);

			} catch (Exception e) {
				emailAlert.sendEmailAlertForHistoricPaymentError(
						"Intuition Historic Payment Out upload error - batch file generation",
						"Intuition (Transaction monitoring) Historical Payment Out upload failed due to batch file generation error on "
								+ new java.text.SimpleDateFormat(EMAIL_BODY_DATE_FORMAT).format(new java.util.Date())
								+ ". Please alert compliance officer.");
				LOG.error("Error  in IntuitionHistoricPaymentCSVTransformer writeDataLineByLine(): ", e);
			} finally {
				writer.close();
				outputfile.close();
			}

		}
			
}

