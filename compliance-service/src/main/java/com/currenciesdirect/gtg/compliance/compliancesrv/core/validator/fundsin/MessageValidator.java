/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.fundsin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentMethodEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.FundsInCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.reg.RegistrationEnumValues;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author manish
 *
 */
public class MessageValidator extends BaseMessageValidator {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageValidator.class);	
	
	@Value("#{${supported.paymentin.method}}")  
	private Map<String, String> mapPaymentList;

	/**
	 * Process.
	 *
	 * @param message
	 *            the message
	 * @param correlationID
	 *            the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		FundsInCreateResponse response = new FundsInCreateResponse();
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		response.setOsrID(messageExchange.getRequest().getOsrId());
		try {
			ServiceInterfaceType serviceInterfaceType = message.getPayload().getGatewayMessageExchange()
					.getServiceInterface();
			OperationEnum operationEnum = message.getPayload().getGatewayMessageExchange().getOperation();
			if (ServiceInterfaceType.FUNDSIN.name().equalsIgnoreCase(serviceInterfaceType.name())
					&& OperationEnum.FUNDS_IN.name().equalsIgnoreCase(operationEnum.name())) {

				FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
				response.setTradePaymentID(fundsInRequest.getTrade().getPaymentFundsInId());
				FieldValidator fv = validateCreateReuqest(fundsInRequest);
				createBaseResponse(response, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "PAYMENTIN");
				messageExchange.setResponse(response);

			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN
					&& operationEnum == OperationEnum.DELETE_OPI) {
				FundsInDeleteRequest fRequest = (FundsInDeleteRequest) messageExchange.getRequest();
				FieldValidator fv = validateDeleteReuqest(fRequest);
				FundsInDeleteResponse deleteResponse = new FundsInDeleteResponse();
				deleteResponse.setOrgCode(fRequest.getOrgCode());
				deleteResponse.setPaymentFundsinId(fRequest.getPaymentFundsInId());
				createBaseResponse(deleteResponse, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "PAYMENTIN");
				deleteResponse.setOsrID(fRequest.getOsrId());
				messageExchange.setResponse(deleteResponse);

			} else if (ServiceInterfaceType.FUNDSIN.name().equalsIgnoreCase(serviceInterfaceType.name())
					&& OperationEnum.CUSTOMCHECK_RESEND.name().equalsIgnoreCase(operationEnum.name())) {
				CustomCheckResendResponse checkResendResponse = new CustomCheckResendResponse();
				FundsInCustomCheckResendRequest fundsInRequest = messageExchange
						.getRequest(FundsInCustomCheckResendRequest.class);
				FieldValidator fv = validateResendRequest(fundsInRequest, ServiceInterfaceType.FUNDSIN);
				createBaseResponse(checkResendResponse, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "");
				messageExchange.setResponse(checkResendResponse);

			} else if (ServiceInterfaceType.FUNDSIN.name().equalsIgnoreCase(serviceInterfaceType.name())
					&& OperationEnum.SANCTION_RESEND.name().equalsIgnoreCase(operationEnum.name())) {
				SanctionResendResponse sanctionResendResponse = new SanctionResendResponse();
				FundsInSanctionResendRequest sanctionResendRequest = messageExchange
						.getRequest(FundsInSanctionResendRequest.class);
				FieldValidator fv = validateReuqest(sanctionResendRequest);
				createBaseResponse(sanctionResendResponse, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "");
				messageExchange.setResponse(sanctionResendResponse);
			}

		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setResponseReason(FundsInReasonCode.MISSINGINFO);
			messageExchange.setResponse(response);
		}
		response.setCorrelationID(correlationID);

		return message;

	}

	/**
	 * Business:-The Funds IN request is validated and mandaotry fields are
	 * present or not is checked. Implementation:- 1)request is received and we
	 * get country code from it by which we get complete name of country. 2)then
	 * customer type is found. 3) mandatory fields are validated whether null or
	 * not by passing fields in isValidObject method 4) paymentTime date is
	 * validated whether null or not and format of date if present is checked by
	 * passing fields in isDateInFormat method 5) Also format of other dates in
	 * request is checked in getOptionalDateFields method 6) Payment Method is
	 * checked in getPaymentMethod method 7) if payment method is SWITCH/DEBIT
	 * then need to check mandatory fields for debit card payment
	 */
	private FieldValidator validateCreateReuqest(FundsInCreateRequest fundsInRequest) {
		ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

		fundsInRequest.getTrade()
				.setCustType(RegistrationEnumValues.getInstance().checkCustomerTypes(fundsInRequest.getCustType()));
		FieldValidator fv = new FieldValidator();

		fv.isValidObject(new Object[] { fundsInRequest.getOrgCode(), fundsInRequest.getSourceApplication(),
				fundsInRequest.getTrade().getTradeAccountNumber(), fundsInRequest.getTrade().getTradeContactId(),
				fundsInRequest.getTrade().getCustType(), fundsInRequest.getTrade().getPurposeOfTrade(),
				fundsInRequest.getTrade().getPaymentFundsInId(), fundsInRequest.getTrade().getThirdPartyPayment(),
				fundsInRequest.getTrade().getSellingAmountBaseValue(),
				fundsInRequest.getTrade().getTransactionCurrency(), fundsInRequest.getTrade().getSellingAmount(),
				fundsInRequest.getTrade().getPaymentMethod(), fundsInRequest.getTrade().getCustLegalEntity() },
				new String[] { ORG_CODE, "source_application", "trade_account_number", "trade_contact_id", CUST_TYPE,
						"purpose_of_trade", "payment_fundsIN_Id", "third_party_payment", "selling_amount_base_value",
						"transaction_currency", "selling_amount", "payment_Method", "customer_legal_entity" });

		try {
			if (!StringUtils.isNullOrTrimEmpty(fundsInRequest.getTrade().getCountryOfFund())
					&& null == countryCache.getCountryFullName(fundsInRequest.getTrade().getCountryOfFund())) {
				fv.addError("Incorrect country code", fundsInRequest.getTrade().getCountryOfFund());
			}
		} catch (ComplianceException e) {
			LOGGER.error("Error in Validator", e);
		}

		fv.isDateInFormat(new String[] { fundsInRequest.getTrade().getPaymentTime() },
				new String[] { Constants.PAYMENTIN_TIME }, Constants.RFC_TIMESTAMP_FORMAT);

		getOptionalDateFields(fv, fundsInRequest);

		if (!fv.isNULL(fundsInRequest.getTrade().getPaymentMethod())
				&& !fv.isEmpty(fundsInRequest.getTrade().getPaymentMethod())) {
			getPaymentMethod(fv, fundsInRequest.getTrade());
		}

		if (Constants.PAYMENT_METHOD_DEBIT.equalsIgnoreCase(fundsInRequest.getTrade().getPaymentMethod())) {
			getMandatoryFieldsforDebitCardPayment(fv, fundsInRequest.getTrade());
		}
		return fv;
	}

	/**
	 * Business:-payment method for Funds IN is checked whether in correct
	 * format or not. Implementation:- 1)The received payment method is first
	 * passed to removeSpacesAndmakeLowercase method to remove any spaces in
	 * fields. 2)Returned value is passed to PaymentMethodEnum where equivalent
	 * value is replaced and set in trade class. 3)If value is not found then
	 * error is added in field validator and returned.
	 */
	private FieldValidator getPaymentMethod(FieldValidator fv, FundsInTrade trade) {
		String paymentMethod = removeSpacesAndmakeLowercase(trade.getPaymentMethod());
		trade.setPaymentMethod(getPaymentMethodFromProperty(paymentMethod));
		if (fv.isNULL(trade.getPaymentMethod())) {
			fv.addError(Constants.INCORRECT_PAYMENT_METHOD_FORMAT, paymentMethod);
		}
		return fv;
	}
	
	/**
	 * Gets the payment method from property.
	 *
	 * @param status the status
	 * @return the payment method from property
	 */
	//Add for AT-5245
	public String getPaymentMethodFromProperty(String status) {
		for (Map.Entry<String, String> m:mapPaymentList.entrySet()) {
			if (m.getKey().equalsIgnoreCase(status)) {
				return m.getValue();
			}
		}
		return null;
	}

	/**
	 * Business:-Mandatory fields required for payment method "SWITCH/DEBIT".
	 * Implementation:- 1) Mandatory fields required for payment method
	 * "SWITCH/DEBIT is checked and if null error is returned in field
	 * validator. 2)Debit card added date is checked whether null or not and in
	 * correct format or not.if not then error is returned in field validator.
	 */
	private FieldValidator getMandatoryFieldsforDebitCardPayment(FieldValidator fv, FundsInTrade trade) {
		fv.isValidObject(
				new Object[] { trade.getCcFirstName(), trade.getBillAddressLine(), trade.getAvsResult(),
						trade.getIsThreeds() },
				new String[] { "cc_first_name", "bill_address_line", "avs_result", "is_threeds" });
		fv.isDateInFormat(new String[] { trade.getDebitCardAddedDate() }, new String[] { "debit_card_added_date" },
				Constants.RFC_TIMESTAMP_FORMAT);
		return fv;
	}

	/**
	 * Business:-Optional date fields if present are checked whether in correct
	 * format or not.If not error is returned. Implementation:- 1) Trade
	 * clearance date if present is checked whether in correct format or not.If
	 * not error is returned in field validator. 2) DeviceInfo if present then
	 * deviceInfo date is checked whether in correct format or not.If not error
	 * is returned in field validator.
	 */
	private FieldValidator getOptionalDateFields(FieldValidator fv, FundsInCreateRequest fundsInRequest) {
		FundsInTrade trade = fundsInRequest.getTrade();
		DeviceInfo deviceInfo = fundsInRequest.getDeviceInfo();
		if (!(fv.isNULL(trade.getChequeClearanceDate())) && (!fv.isEmpty(trade.getChequeClearanceDate()))) {
			fv.isDateInFormat(new String[] { trade.getChequeClearanceDate() }, new String[] { "cheque_clearance_date" },
					Constants.RFC_TIMESTAMP_FORMAT);
		}
		if (null != deviceInfo && !StringUtils.isNullOrEmpty(deviceInfo.getOsDateAndTime())) {
			deviceInfo.setOsDateAndTime(DateTimeFormatter.getDateTimeInRFC3339(deviceInfo.getOsDateAndTime()));

		}
		return fv;

	}

	/**
	 * Removes the spaces and make lower case.
	 *
	 * @param requestValue
	 *            the request value
	 * @return the string
	 */
	private String removeSpacesAndmakeLowercase(String requestValue) {
		return requestValue.replace(" ", "").toLowerCase();
	}

	private FieldValidator validateDeleteReuqest(FundsInDeleteRequest fRequest) {

		FieldValidator fv = new FieldValidator();

		fv.isValidObject(
				new Object[] { fRequest.getOrgCode(), fRequest.getSourceApplication(), fRequest.getPaymentFundsInId() },
				new String[] { ORG_CODE, "source_application", "payment_fundsin_id" });
		return fv;
	}

	private FieldValidator validateReuqest(FundsInSanctionResendRequest fRequest) {
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { fRequest.getTradePaymentId(), fRequest.getOrgCode(), fRequest.getPaymentInId() },
				new String[] { "tradePaymentId", "orgCode", "paymentInId" });

		return fv;
	}

	private FieldValidator validateResendRequest(FundsInCustomCheckResendRequest fundsInRequest,
			ServiceInterfaceType type) {
		FieldValidator fv = new FieldValidator();
		switch (type) {
		case FUNDSIN:
			fv.isEmpty(
					new String[] { fundsInRequest.getPaymentInId().toString(),
							fundsInRequest.getTradeAccountNumber(), },
					new String[] { "paymentInId", "tradeAccountNumber" });
			break;
		case FUNDSOUT:
			fv.isEmpty(new String[] { fundsInRequest.getPaymentInId().toString() }, new String[] { "paymentOutId" });
			break;
		default:
			fv.addError(Constants.MSG_MISSING_INFO, "something went wrrong");
			break;
		}
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
		FundsInFraugsterResendRequest request = msgExchange.getRequest(FundsInFraugsterResendRequest.class);
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { request.getPaymentFundsInId(), request.getTradeAccountNumber(), request.getOrgCode(),
						request.getCustType() },
				new String[] { "fundsInId", "tradeaccountNumber", ORG_CODE, CUST_TYPE });

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
		FundsInBlacklistResendRequest request = msgExchange.getRequest(FundsInBlacklistResendRequest.class);
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { request.getPaymentFundsInId(), request.getTradeAccountNumber(), request.getOrgCode(),
						request.getCustType() },
				new String[] { "fundsInId", "tradeaccountNumber", ORG_CODE, CUST_TYPE });

		BlacklistResendResponse response = new BlacklistResendResponse();
		createBaseResponse(response, fv, FundsInReasonCode.MISSINGINFO.getReasonCode(), Constants.MSG_MISSING_INFO, "");
		msgExchange.setResponse(response);
		return message;
	}
}
