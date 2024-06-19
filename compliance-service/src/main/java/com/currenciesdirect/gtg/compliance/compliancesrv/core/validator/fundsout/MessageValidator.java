package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.fundsout;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Beneficiary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Trade;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.PaymentReferenceResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.request.FundsOutCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author bnt
 *
 */
public class MessageValidator extends BaseMessageValidator {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageValidator.class);
	
	private static final String SOURCE_APPLICATION = "source_application";

	private static final String PAYMENT_FUNDSOUT_ID = "payment_fundsout_id";

	private static final String MATURITY_DATE = "maturity_date";

	private static final String VALUE_DATE = "value_date";

	private static final String DEAL_DATE = "deal_date";

	private static final String PAYMENTOUT_EVENTTYPE = "PAYMENTOUT";
	
	//added for AT-3658
	private static final String PAYMENT_REFERENCE = "PAYMENTREFERENCE";
	
	/** The Constant FUNDS_OUT_ID. */
	private static final String FUNDS_OUT_ID = "fundsOutId";
	
	/** The Constant TRADE_ACCOUNT_NUMBER. */
	private static final String TRADE_ACCOUNT_NUMBER = "tradeaccountNumber";

	/**
	 * @param message
	 * @param correlationID
	 * @return
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		FundsOutResponse response = new FundsOutResponse();

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		response.setOsrID(message.getHeaders().get(MessageContextHeaders.OSR_ID).toString());
		try {

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT) {
				if (operation == OperationEnum.FUNDS_OUT) {
					FundsOutRequest fundsOutRequest;
					fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
					FieldValidator fv = validateCreateReuqest(fundsOutRequest);
					createBaseResponse(response, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
							Constants.MSG_MISSING_INFO, PAYMENTOUT_EVENTTYPE);
					messageExchange.setResponse(response);
				} else if (operation == OperationEnum.UPDATE_OPI) {

					FundsOutUpdateRequest fRequest = (FundsOutUpdateRequest) messageExchange.getRequest();
					FieldValidator fv = validateUpdateReuqest(fRequest);
					createBaseResponse(response, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
							Constants.MSG_MISSING_INFO, PAYMENTOUT_EVENTTYPE);
					messageExchange.setResponse(response);
				} else if (operation == OperationEnum.DELETE_OPI) {
					FundsOutDeleteRequest fRequest = (FundsOutDeleteRequest) messageExchange.getRequest();
					FieldValidator fv = validateDeleteReuqest(fRequest);
					createBaseResponse(response, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
							Constants.MSG_MISSING_INFO, PAYMENTOUT_EVENTTYPE);
					messageExchange.setResponse(response);

				} else if (operation == OperationEnum.SANCTION_RESEND) {
					SanctionResendResponse sanctionResendResponse = new SanctionResendResponse();
					FundsOutSanctionResendRequest fundsOutRequest = messageExchange
							.getRequest(FundsOutSanctionResendRequest.class);
					FieldValidator fv = validateResendReuqest(fundsOutRequest);
					createBaseResponse(sanctionResendResponse, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
							Constants.MSG_MISSING_INFO, "");
					messageExchange.setResponse(sanctionResendResponse);
				} else if (operation == OperationEnum.CUSTOMCHECK_RESEND) {
					CustomCheckResendResponse checkResendResponse = new CustomCheckResendResponse();
					FundsOutCustomCheckResendRequest fundsOutRequest = messageExchange
							.getRequest(FundsOutCustomCheckResendRequest.class);
					FieldValidator fv = fundsOutRequest.validateResendRequest(ServiceInterfaceType.FUNDSOUT);
					createBaseResponse(checkResendResponse, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
							Constants.MSG_MISSING_INFO, "");
					messageExchange.setResponse(checkResendResponse);
				}
			}
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setResponseReason(FundsOutReasonCode.MISSINGINFO);
			messageExchange.setResponse(response);
		}
		response.setCorrelationID(correlationID);

		return message;
	}

	/**
	 * Validate create reuqest.
	 *
	 * @return the field validator
	 */
	private FieldValidator validateCreateReuqest(FundsOutRequest fundsOutRequest) {
		ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
		FieldValidator fv = new FieldValidator();
		try {
			Trade trade = fundsOutRequest.getTrade();
			Beneficiary beneficiary = fundsOutRequest.getBeneficiary();

			fv.isValidObject(
					new Object[] { fundsOutRequest.getOrgCode(), fundsOutRequest.getSourceApplication(),
							/* fundsOutRequest.getCustType(), */ trade.getTradeAccountNumber(),
							trade.getTradeContactId(), trade.getCustType(), trade.getPurposeOfTrade(),
							beneficiary.getPaymentFundsoutId(), trade.getDealDate(), trade.getValueDate(),
							trade.getSellingAmount(), trade.getBuyingAmount(), trade.getSellCurrency(),
							trade.getBuyCurrency(), beneficiary.getCountry(), beneficiary.getFirstName(),
							beneficiary.getAccountNumber(), beneficiary.getBeneficaryBankName(),
							beneficiary.getBeneficiaryBankid(), beneficiary.getBeneficiaryId(),
							trade.getBuyingAmountBaseValue(), trade.getMaturityDate(), trade.getCustLegalEntity() },
					new String[] { ORG_CODE, SOURCE_APPLICATION, CUST_TYPE, "trade_account_number", "trade_contact_id",
							"purpose_of_trade", PAYMENT_FUNDSOUT_ID, DEAL_DATE, VALUE_DATE, "selling_amount",
							"buying_amount", "sell_currency", "buy_currency", "country", "first_name", "account_number",
							"beneficary_bank_name", "beneficiary_bankid", "beneficiary_id", "buying_amount_base_value",
							MATURITY_DATE, "customer_legal_entity" });

			validateDates(fv, trade);
			DeviceInfo deviceInfo = fundsOutRequest.getDeviceInfo();
			if (null != deviceInfo && !StringUtils.isNullOrEmpty(deviceInfo.getOsDateAndTime())) {
				deviceInfo.setOsDateAndTime(DateTimeFormatter.getDateTimeInRFC3339(deviceInfo.getOsDateAndTime()));
			}

			if (beneficiary.getPaymentFundsoutId() <= 0)
				fv.addError(Constants.ERR_PAYMENT_ID_INVALID, PAYMENT_FUNDSOUT_ID);
			if (null == countryCache.getCountryFullName(beneficiary.getCountry())) {
				fv.addError(Constants.ERR_COUNTRY_INVALID, "country");
			}

		} catch (Exception e) {
			LOGGER.debug("Cannot validate request", e);
			fv.addError(Constants.MSG_MISSING_INFO, "something went wrong");
		}
		return fv;
	}

	private FieldValidator validateDates(FieldValidator fv, Trade trade) {
		if (null != trade.getMaturityDate() && !trade.getMaturityDate().isEmpty()) {
			fv.isDateInFormat(new String[] { trade.getMaturityDate() }, new String[] { MATURITY_DATE },
					Constants.RFC_DATE_FORMAT);
			if (Boolean.TRUE.equals(fv.isFieldValid(MATURITY_DATE))) {
				trade.setMaturityDate(formatDate(trade.getMaturityDate()));
			}
		}

		if (null != trade.getDealDate() && !trade.getDealDate().isEmpty()) {
			fv.validateDate(trade.getDealDate(), Constants.RFC_DATE_FORMAT, DEAL_DATE);
			if (Boolean.TRUE.equals(fv.isFieldValid(DEAL_DATE))) {
				trade.setDealDate(formatDate(trade.getDealDate()));
			}
		}

		if (null != trade.getValueDate() && !trade.getValueDate().isEmpty()) {
			fv.validateDate(trade.getValueDate(), Constants.RFC_DATE_FORMAT, VALUE_DATE);
			if (Boolean.TRUE.equals(fv.isFieldValid(VALUE_DATE))) {
				trade.setValueDate(formatDate(trade.getValueDate()));
			}
		}
		return fv;
	}

	/**
	 * Format date.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	/*
	 * This formating for elastic search to parse date fields
	 */
	private String formatDate(String date) {
		String[] splitDate = date.split("-");
		StringBuilder formattedDate = new StringBuilder();
		formattedDate.append(splitDate[0]);
		for (int i = 1; i < splitDate.length; i++) {
			if (splitDate[i].length() < 2) {
				formattedDate.append("-0").append(splitDate[i]);
			} else {
				formattedDate.append("-").append(splitDate[i]);
			}
		}

		return formattedDate.toString();
	}

	private FieldValidator validateUpdateReuqest(FundsOutUpdateRequest fRequest) {

		FieldValidator fv = new FieldValidator();

		fv.isValidObject(
				new Object[] { fRequest.getOrgCode(), fRequest.getSourceApplication(), fRequest.getAmount(),
						fRequest.getPaymentFundsoutId() },
				new String[] { ORG_CODE, SOURCE_APPLICATION, "amount", PAYMENT_FUNDSOUT_ID });

		if (!StringUtils.isNullOrTrimEmpty(fRequest.getValueDate())) {
			fv.isDateInFormat(new String[] { fRequest.getValueDate() }, new String[] { VALUE_DATE },
					Constants.RFC_DATE_FORMAT);
		}
		return fv;
	}

	private FieldValidator validateDeleteReuqest(FundsOutDeleteRequest fRequest) {

		FieldValidator fv = new FieldValidator();

		fv.isValidObject(
				new Object[] { fRequest.getOrgCode(), fRequest.getSourceApplication(),
						fRequest.getPaymentFundsoutId() },
				new String[] { ORG_CODE, SOURCE_APPLICATION, PAYMENT_FUNDSOUT_ID });
		return fv;
	}

	/**
	 * Validate resend reuqest.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateResendReuqest(FundsOutSanctionResendRequest fundsOutRequest) {

		FieldValidator fv = new FieldValidator();

		fv.isValidObject(new Object[] { fundsOutRequest.getTradePaymentId().toString() },
				new String[] { "tradePaymentId" });
		fv.isValidObject(new Object[] { fundsOutRequest.getContactStatus() }, new String[] { "contactStatus" });
		fv.isValidObject(new Object[] { fundsOutRequest.getBankStatus() }, new String[] { "bankStatus" });
		fv.isValidObject(new Object[] { fundsOutRequest.getBeneficiaryStatus() }, new String[] { "beneficiaryStatus" });
		return fv;
	}

	/**
	 * Validate resend reuqest.
	 *
	 * @param message
	 *            the message
	 * @param correlationID
	 *            the correlation ID
	 * @return the message
	 */
	public Message<MessageContext> validateFraugsterResendReuqest(Message<MessageContext> message) {
		MessageExchange msgExchange = message.getPayload().getGatewayMessageExchange();
		FundsOutFruagsterResendRequest request = msgExchange.getRequest(FundsOutFruagsterResendRequest.class);
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { request.getPaymentFundsoutId(), request.getTradeAccountNumber(), request.getOrgCode(),
						request.getCustType() },
				new String[] { FUNDS_OUT_ID, TRADE_ACCOUNT_NUMBER, ORG_CODE, CUST_TYPE });

		FraugsterResendResponse response = new FraugsterResendResponse();
		createBaseResponse(response, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(), Constants.MSG_MISSING_INFO, "");
		msgExchange.setResponse(response);
		return message;
	}

	/**
	 * Validate resend reuqest.
	 *
	 * @param message
	 *            the message
	 * @param correlationID
	 *            the correlation ID
	 * @return the message
	 */
	public Message<MessageContext> validateBlacklistResendReuqest(Message<MessageContext> message) {
		MessageExchange msgExchange = message.getPayload().getGatewayMessageExchange();
		FundsOutBlacklistResendRequest request = msgExchange.getRequest(FundsOutBlacklistResendRequest.class);
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { request.getPaymentFundsoutId(), request.getTradeAccountNumber(), request.getOrgCode(),
						request.getCustType() },
				new String[] { FUNDS_OUT_ID, TRADE_ACCOUNT_NUMBER, ORG_CODE, CUST_TYPE });

		BlacklistResendResponse response = new BlacklistResendResponse();
		createBaseResponse(response, fv, FundsOutReasonCode.MISSINGINFO.getFundsOutReasonCode(),
				Constants.MSG_MISSING_INFO, "");
		msgExchange.setResponse(response);
		return message;
	}
	
	/**
	 * Validate resend reuqest.
	 *
	 * @param message
	 *            the message
	 * @param correlationID
	 *            the correlation ID
	 * @return the message
	 */
	public Message<MessageContext> validatePaymentReferenceResendReuqest(Message<MessageContext> message) {
		MessageExchange msgExchange = message.getPayload().getGatewayMessageExchange();
		FundsOutPaymentReferenceResendRequest request = msgExchange.getRequest(FundsOutPaymentReferenceResendRequest.class);
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { request.getPaymentFundsoutId(), request.getTradeAccountNumber(), request.getOrgCode(),
						request.getCustType()},
				new String[] { FUNDS_OUT_ID, TRADE_ACCOUNT_NUMBER, ORG_CODE, CUST_TYPE });

		PaymentReferenceResendResponse response = new PaymentReferenceResendResponse();
		createBaseResponse(response, fv, FundsOutReasonCode.MISSINGINFO.getFundsOutReasonCode(),
				Constants.MSG_MISSING_INFO, "");
		msgExchange.setResponse(response);
		return message;
	}

}