package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.reg;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.reg.RegistrationEnumValues;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * @author bnt
 *
 */
public class DataValidator extends BaseMessageValidator {

	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";
	private static final String ACCOUNT = "oldAccount";
	private static final String BUY_CURRENCY_ID = "BuyCurrencyId";
	private static final String SELL_CURRENCY_ID = "SellCurrencyId";
	private static final String COUNTRY_ID = "CountryID";
	private static final String CONTACTS = "oldContacts";

	private static final Logger LOGGER = LoggerFactory.getLogger(DataValidator.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		RegistrationResponse response = new RegistrationResponse();
		RegistrationServiceRequest registrationServiceRequest = null;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),
					message.getPayload());
			registrationServiceRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			registrationServiceRequest.setCorrelationID(correlationID);
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			FieldValidator validator = new FieldValidator();
			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.NEW_REGISTRATION) {
				validateNewRegistration(registrationServiceRequest, validator);
				createBaseResponse(response, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, Constants.SIGN_UP);
			} else if (serviceInterfaceType == ServiceInterfaceType.PROFILE
					&& operation == OperationEnum.UPDATE_ACCOUNT) {
				validateUpdateAccount(registrationServiceRequest, validator);
				createBaseResponse(response, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, Constants.UPDATE);
			} else if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.ADD_CONTACT) {
				validateAddContact(registrationServiceRequest, validator);
				createBaseResponse(response, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, Constants.ADD_CONTACT);
			}
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		response.setCorrelationID(correlationID);
		response.setOsrID(messageExchange.getRequest().getOsrId());
		messageExchange.setResponse(response);
		return message;
	}

	private boolean validateContactsForUpdate(RegistrationServiceRequest registrationServiceRequest, FieldValidator fv) {
		List<Contact> contacts = registrationServiceRequest.getAccount().getContacts();
		Boolean isValidate = Boolean.FALSE;
		for (Contact contact : contacts) {
			if (null != contact && null != contact.getId()) {
				validateUsaState(contact,fv);
				isValidate = Boolean.TRUE;
			} else {
				fv.addError(Constants.ERR_INVALID_COTACT, Constants.FIELD_CONTACT_TRADE_CONTACT_ID);
				isValidate = Boolean.FALSE;
				break;
			}
		}

		return isValidate;
	}

	@SuppressWarnings("unchecked")
	private Boolean validateNewContacts(RegistrationServiceRequest request, FieldValidator fv) {
		Account account = request.getAccount();
		List<Contact> oldContacts = (List<Contact>) request.getAdditionalAttribute(CONTACTS);
		List<Integer> oldTradeContactidList = (List<Integer>) request.getAdditionalAttribute(Constants.TRADE_CONTACT_ID_LIST);
		Boolean isValidate = Boolean.TRUE;
		
		Boolean isPrimaryContactPresent = isPrimaryContactPresent(oldContacts);
		for (Contact contact : account.getContacts()) {
			contact.setVersion(1);
			if (Constants.CFX.equalsIgnoreCase(account.getCustType())) {
				contact.setKYCEligible(false);
				contact.setSanctionEligible(false);
			}
			/** validate tradeContactid : Abhijit G*/
			if (null != getContactBySFId(contact.getContactSFID(), oldContacts)) {
				fv.addError(Constants.ERR_INVALID_COTACT_ALREADY, Constants.FIELD_CONTACT_CONTACT_SF_ID);
				break;
			} 
			else if (Boolean.TRUE.equals(validateContactTradeFields(contact.getTradeContactID(),oldTradeContactidList))) {
				fv.addError(Constants.ERR_INVALID_COTACT_ALREADY, Constants.FIELD_CONTACT_TRADE_CONTACT_ID);
			}
			
			if (contact.getPrimaryContact() != null && contact.getPrimaryContact() 
					&& Boolean.TRUE.equals(isPrimaryContactPresent)) {
				fv.addError(Constants.ERR_PRIMARY_CONTACT_ALREADY, Constants.FIELD_ACC_ACC_SF_ID);
			}

		}
		if (null == request.getAdditionalAttribute(COUNTRY_ID))
			fv.addError(Constants.ERR_COUNTRY_INVALID, "country_of_residence");

		return isValidate;
	}

	private Boolean isPrimaryContactPresent(List<Contact> contacts) {

		for (Contact contact : contacts) {
			if (Boolean.TRUE.equals(contact.getPrimaryContact())) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;

	}

	private Contact getContactBySFId(String sfId, List<Contact> contacts) {
		for (Contact c : contacts) {
			if (c.getContactSFID().equalsIgnoreCase(sfId))
				return c;
		}
		return null;
	}

	private void validateAccountForRegistration(RegistrationServiceRequest request, FieldValidator fv) {
	   
		/** validate tradeAccountid  and tradeAccountNumber: Abhijit G*/
		validateAccountTradeFields(request, fv);
		
		if (null == request.getAdditionalAttribute(BUY_CURRENCY_ID))
			fv.addError(Constants.ERR_BUY_CURRENCY, Constants.FIELD_ACC_BUYING_CURRENCY);
		if (null == request.getAdditionalAttribute(SELL_CURRENCY_ID))
			fv.addError(Constants.ERR_SELL_CURRENCY, Constants.FIELD_ACC_SELLING_CURRENCY);
		
	}

	private void validateNewRegistration(RegistrationServiceRequest registrationServiceRequest,
			FieldValidator validator) {
		if (null == registrationServiceRequest.getOrgId()) {
			validator.addError(Constants.ERR_INVALID_ORG, Constants.ORG_CODE);
		} else {
			validateAccountForRegistration(registrationServiceRequest, validator);
			registrationServiceRequest.getAccount().setVersion(1);
			validateNewContacts(registrationServiceRequest, validator);
		}
		if (isMultipleContactsArePrimary(registrationServiceRequest.getAccount().getContacts())) {
			validator.addError(Constants.ERR_MULTIPLE_PRIMARY_CONTACT, Constants.FIELD_ACC_ACC_SF_ID);
		}
	}

	private boolean isMultipleContactsArePrimary(List<Contact> contacts) {
		Integer count = 0;
		for (Contact contact : contacts) {
			if (contact.getPrimaryContact() != null && contact.getPrimaryContact()) {
				count++;
			}
		}
		return count > 1;
	}

	private void validateAddContact(RegistrationServiceRequest registrationServiceRequest, FieldValidator validator) {
		Account oldAccount = (Account) registrationServiceRequest.getAdditionalAttribute(ACCOUNT);
		if (null == oldAccount || null == oldAccount.getId() || null == oldAccount.getVersion()) {
			validator.addError(Constants.ERR_INVALID_ACCOUNT, Constants.FIELD_ACC_TRADE_ACCOUNT_ID);
			return;
		} else {
			validateNewContacts(registrationServiceRequest, validator);
		}

		if (!oldAccount.getCustType().equalsIgnoreCase(registrationServiceRequest.getAccount().getCustType())) {
			validator.addError(Constants.ERR_CUST_TYPE_MODIFIED, Constants.FIELD_ACC_CUST_TYPE);
		}

		if (!oldAccount.getAccSFID().equals(registrationServiceRequest.getAccount().getAccSFID())) {
			validator.addError(Constants.ERR_INVALID_ACCOUNT_NOT_PRESENT, Constants.FIELD_ACC_ACC_SF_ID);
		}

	}

	private void validateUpdateAccount(RegistrationServiceRequest registrationServiceRequest, FieldValidator validator) {
		Account oldAccount = (Account) registrationServiceRequest.getAdditionalAttribute(ACCOUNT);
		
		if (null == oldAccount || ( null == oldAccount.getId() || null == oldAccount.getVersion())) {
			validator.addError(Constants.ERR_INVALID_ACCOUNT_NOT_PRESENT, Constants.FIELD_ACC_TRADE_ACCOUNT_ID);
		} else {
			registrationServiceRequest.getAccount().setVersion(oldAccount.getVersion() + 1);
			validateContactsForUpdate(registrationServiceRequest, validator);
		}
		String orgName = (String) registrationServiceRequest.getAdditionalAttribute(Constants.ACC_ORG_NAME);
		if(orgName != null && !StringUtils.isNullOrTrimEmpty(registrationServiceRequest.getOrgCode()) && 
				!registrationServiceRequest.getOrgCode().equalsIgnoreCase(orgName)){
			validator.addError(Constants.ERR_MODIFIY_ORG, Constants.ORG_CODE);
		}
		Boolean isAccountModified = (Boolean) registrationServiceRequest.getAdditionalAttribute(Constants.FIELD_ACC_ISACCOUNTMODIFIED);
		Boolean isContactModified = (Boolean) registrationServiceRequest.getAdditionalAttribute(Constants.FIELD_ACC_ISCONTACTMODIFIED);
		Boolean isCompanyModified = (Boolean) registrationServiceRequest.getAdditionalAttribute(Constants.FIELD_ACC_ISCOMPANYMODIFIED);
		Boolean isConversionPredictionModified = (Boolean) registrationServiceRequest
				.getAdditionalAttribute(Constants.FIELD_ACC_ISCONVPREDICTIONMODIFIED);
		Boolean isCorporateComplianceModified = (Boolean) registrationServiceRequest
				.getAdditionalAttribute(Constants.FIELD_ACC_ISCORPORATECOMPLIANCEMODIFIED);
		checkIsContactsPresent(registrationServiceRequest, validator, isContactModified);
		checkIsAccountPresent(registrationServiceRequest, validator, oldAccount);
		checkIsAccountOrContactModified(validator, isAccountModified, isContactModified, isCompanyModified,
				isConversionPredictionModified, isCorporateComplianceModified);
	}

	/**
	 * @param registrationServiceRequest
	 * @param validator
	 * @param isContactModified
	 */
	private void checkIsContactsPresent(RegistrationServiceRequest registrationServiceRequest, FieldValidator validator,
			Boolean isContactModified) {
		if (null != isContactModified &&(registrationServiceRequest.getAccount().getContacts() != null
				&& !registrationServiceRequest.getAccount().getContacts().isEmpty()
				&& !isContactModified)) {
			validator.addError(Constants.ERR_CONTACT_NOT_UPDATED, Constants.FIELD_CONTACT_CONTACT_SF_ID);
		}
	}

	/**
	 * @param registrationServiceRequest
	 * @param validator
	 * @param oldAccount
	 */
	private void checkIsAccountPresent(RegistrationServiceRequest registrationServiceRequest, FieldValidator validator,
			Account oldAccount) {
		if (null != oldAccount && !oldAccount.getAccSFID().equals(registrationServiceRequest.getAccount().getAccSFID())) {
			validator.addError(Constants.ERR_INVALID_ACCOUNT_NOT_PRESENT, Constants.FIELD_ACC_ACC_SF_ID);
		}
	}

	/**
	 * @param validator
	 * @param isAccountModified
	 * @param isContactModified
	 */
	private void checkIsAccountOrContactModified(FieldValidator validator, Boolean isAccountModified,
			Boolean isContactModified, Boolean isCompanyModified, Boolean isConversionPredictionModified,
			Boolean isCorporateComplianceModified) {
		if (null != isContactModified && Boolean.FALSE.equals(isAccountModified) 
				&& Boolean.FALSE.equals(isContactModified) && Boolean.FALSE.equals(isCompanyModified)
				&& Boolean.FALSE.equals(isConversionPredictionModified) 
				&& Boolean.FALSE.equals(isCorporateComplianceModified)) {
			validator.addError(Constants.ERR_ACCOUNT_NOT_UPDATED, Constants.FIELD_ACC_TRADE_ACCOUNT_ID);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void validateAccountTradeFields(RegistrationServiceRequest request, FieldValidator fv){
		
		List<Integer> oldTreadeAccountList=(List<Integer>) request.getAdditionalAttribute(Constants.TRADE_ACCOUNT_ID_LIST);
		List<String> oldTradeAccountNumberList= (List<String>) request.getAdditionalAttribute(Constants.TRADE_ACCOUNT_NUMBER_LIST);
		
			
			if(!oldTreadeAccountList.isEmpty() && oldTreadeAccountList.contains(request.getAccount().getTradeAccountID())){
				fv.addError(Constants.ERR_INVALID_ACCOUNT_ALREADY, Constants.FIELD_ACC_TRADE_ACCOUNT_ID);
			}
			
			if(!oldTradeAccountNumberList.isEmpty() && oldTradeAccountNumberList.contains(request.getAccount().getTradeAccountNumber())){
				fv.addError(Constants.ERR_INVALID_ACCOUNT_ALREADY, Constants.FIELD_ACC_TRADE_ACCOUNT_NUMBER);
		}
	}
	
	private Boolean validateContactTradeFields(Integer tradeContactId, List<Integer> oldTradeConactIdList) {

	return (!oldTradeConactIdList.isEmpty() && oldTradeConactIdList.contains(tradeContactId));
	}

	/**
	 * Validate usa state.
	 *
	 * @param validator
	 *            the validator
	 */
	private void validateUsaState(Contact contact,FieldValidator validator) {
		RegistrationEnumValues enumValues = RegistrationEnumValues.getInstance();
		if (!StringUtils.isNullOrTrimEmpty(contact.getCountry()) && ("USA").equalsIgnoreCase(contact.getCountry())
				&& (StringUtils.isNullOrTrimEmpty(contact.getState())
						|| enumValues.checkUsaStates(contact.getState()) == null)) {
			validator.addError("Incorrect USA State", "state_province_county");
		}
	}
}