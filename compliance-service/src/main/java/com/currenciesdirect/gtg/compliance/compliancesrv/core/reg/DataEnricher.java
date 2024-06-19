/*
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.ConversionPrediction;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.commons.enums.TransactionTypeEnum;
import com.currenciesdirect.gtg.compliance.commons.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Company;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.CopyObjectUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.ParseHouseStreetNumber;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;

/**
 * The Class DataEnricher.
 */
public class DataEnricher extends AbstractDataEnricher {

	/** The Constant FIRST_SANCTION_SUMMARY. */
	private static final String FIRST_SANCTION_SUMMARY = "firstSanctionSummary";

	/** The Constant CONTACT_LOG_FOR_CONTACT. */
	private static final String CONTACT_LOG_FOR_CONTACT = "contactLogForContact";

	/** The Constant IS_EID_ELIGIBLE. */
	private static final String IS_EID_ELIGIBLE = "isEidEligible";

	/** The Constant IS_GLOBAL_CHECK_ELIGIBLE. */
	private static final String IS_GLOBAL_CHECK_ELIGIBLE = "isGlobalCheckEligible";

	/** The Constant IS_IP_CHECK_ELIGIBLE. */
	private static final String IS_IP_CHECK_ELIGIBLE = "isIpCheckEligible";

	/** The Constant IS_COUNTRY_CHECK_ELIGIBLE. */
	private static final String IS_COUNTRY_CHECK_ELIGIBLE = "isCountryCheckEligible";

	/** The Constant IS_BLACKLIST_ELIGIBLE. */
	private static final String IS_BLACKLIST_ELIGIBLE = "isBlacklistEligible";

	/** The Constant IS_SANCTION_ELIGIBLE2. */
	private static final String IS_SANCTION_ELIGIBLE_FOR_RECHECK = "isSanctionEligible";

	/** The Constant IS_FRAUGSTER_ELIGIBLE2. */
	private static final String IS_FRAUGSTER_ELIGIBLE_FOR_RECHECK = "isFraugsterEligible";

	/** The Constant IP_CHECK_STATUS. */
	private static final String IP_CHECK_STATUS = "IpCheckStatus";

	/** The Constant GLOBAL_CHECK_STATUS. */
	private static final String GLOBAL_CHECK_STATUS = "GlobalCheckStatus";

	/** The Constant COUNTRY_CHECK_STATUS. */
	private static final String COUNTRY_CHECK_STATUS = "CountryCheckStatus";

	/** The Constant IS_SANCTION_PERFORMED. */
	public static final String IS_SANCTION_PERFORMED = "isSanctionPerformed";

	/** The Constant IS_SANCTION_ELIGIBLE. */
	public static final String IS_SANCTION_ELIGIBLE = "isSanctionEligible,";

	/** The Constant IS_FRAUGSTER_ELIGIBLE. */
	public static final String IS_FRAUGSTER_ELIGIBLE = "isFraugsterEligible,";

	/** The Constant IS_KYC_ELIGIBLE. */
	public static final String IS_KYC_ELIGIBLE = "isKYCEligible,";

	/** The Constant SERIAL_VERSION_UID. */
	private static final String SERIAL_VERSION_UID = "serialVersionUID,";

	/** The Constant GENDER. */
	private static final String GENDER = "gender,";

	/** The Constant DOB. */
	private static final String DOB = "dob,";

	/** The Constant REGION. */
	private static final String REGION = "region,";

	/** The Constant SUB_CITY. */
	private static final String SUB_CITY = "subCity,";

	/** The Constant UNIT_NUMBER. */
	private static final String UNIT_NUMBER = "unitNumber,";

	/** The Constant SUB_BUILDINGOR_FLAT. */
	private static final String SUB_BUILDINGOR_FLAT = "subBuildingorFlat,";

	/** The Constant COUNTRY. */
	private static final String COUNTRY = "country,";

	/** The Constant POST_CODE. */
	private static final String POST_CODE = "postCode,";

	/** The Constant STATE_PROVINCE_COUNTY. */
	private static final String STATE_PROVINCE_COUNTY = "state,";

	/** The Constant SUB_STREET. */
	private static final String SUB_STREET = "subStreet,";

	/** The Constant STREET_TYPE. */
	private static final String STREET_TYPE = "streetType,";

	/** The Constant STREET_NUMBER. */
	private static final String STREET_NUMBER = "streetNumber,";

	/** The Constant BUILDING_NAME. */
	private static final String BUILDING_NAME = "buildingName,";

	/** The Constant CITY. */
	private static final String CITY = "city,";

	/** The Constant ADDRESS1. */
	private static final String ADDRESS1 = "address1,";

	/** The Constant ADDRESS_TYPE. */
	private static final String ADDRESS_TYPE = "addressType,";

	/** The Constant SECOND_SURNAME. */
	private static final String SECOND_SURNAME = "secondSurname,";

	/** The Constant LAST_NAME. */
	private static final String LAST_NAME = "lastName,";

	/** The Constant MIDDLE_NAME. */
	private static final String MIDDLE_NAME = "middleName,";

	/** The Constant FIRST_NAME. */
	private static final String FIRST_NAME = "firstName,";

	/** The Constant REQUEST_IS_NOT_VALID. */
	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	/** The Constant ACCOUNT. */
	private static final String ACCOUNT = "oldAccount";

	/** The Constant BUY_CURRENCY_ID. */
	private static final String BUY_CURRENCY_ID = "BuyCurrencyId";

	/** The Constant SELL_CURRENCY_ID. */
	private static final String SELL_CURRENCY_ID = "SellCurrencyId";

	/** The Constant COUNTRY_ID. */
	private static final String COUNTRY_ID = "CountryID";

	/** The Constant UPDATE_METHOD. */
	private static final String UPDATE_METHOD = "updateMethod";

	/** The Constant VERSION. */
	private static final String VERSION = "version";

	/** The Constant AREA_NUMBER. */
	private static final String AREA_NUMBER = "areaNumber,";

	/** The Constant AZA. */
	private static final String AZA = "aza,";

	/** The Constant CIVIC_NUMBER. */
	private static final String CIVIC_NUMBER = "civicNumber,";

	/** The Constant DISTRICT. */
	private static final String DISTRICT = "district,";

	/** The Constant FLOOR_NUMBER. */
	private static final String FLOOR_NUMBER = "floorNumber,";

	/** The Constant REGION_SUBURB. */
	private static final String REGION_SUBURB = "regionSuburb,";

	/** The Constant PREFECTURE. */
	private static final String PREFECTURE = "prefecture,";

	/** The Constant STREET. */
	private static final String STREET = "street,";

	/** The Constant YEARS_IN_ADDRESS. */
	private static final String YEARS_IN_ADDRESS = "yearsInAddress,";

	/** The Constant STREET. */
	private static final String BUILDING_NUMBER = "buildingNumber,";

	/** The Constant IS_CONTACT_MODIFIED. */
	private static final String IS_CONTACT_MODIFIED = "isContactModified";
	
	/** The Constant SSN. */
	private static final String SSN = "ssn,";
	
	/** The Constant CONTACT_ID_FOR_CFX. */
	private static final String CONTACT_ID_FOR_CFX = "ContactIdForCFX";

	/** The event DB service impl. */
	@Autowired
	protected IEventDBService eventDBServiceImpl;

	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEnricher.class);

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
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationResponse response = messageExchange.getResponse(RegistrationResponse.class);
		LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),
				message.getPayload());

		RegistrationServiceRequest request = messageExchange.getRequest(RegistrationServiceRequest.class);
		
		RegistrationServiceRequest updateRequest = (RegistrationServiceRequest) ObjectCloner.deepCopy(request);
		request.addAttribute("UpdateRequest", updateRequest);

		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		OperationEnum operation = messageExchange.getOperation();
		Boolean isContactModified = Boolean.TRUE;
		try {
			getUserTableId(message);
			getAccountContactDetails(request);
			ParseHouseStreetNumber.getInstance().parseStreetValues(request);

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.NEW_REGISTRATION) {
				setIsContactModified(request, isContactModified);

			} else if (serviceInterfaceType == ServiceInterfaceType.PROFILE
					&& operation == OperationEnum.UPDATE_ACCOUNT) {
				enrichDataForUpdate(request, message);
			} else if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.ADD_CONTACT) {
				Account oldAccount = (Account) request.getAdditionalAttribute(ACCOUNT);
				if (null != oldAccount) {
					/*** Set previous service statuses to account **/
					request.getAccount().setPreviousBlacklistStatus(oldAccount.getPreviousBlacklistStatus());
					request.getAccount().setPreviousFraugsterStatus(oldAccount.getPreviousFraugsterStatus());
					request.getAccount().setPreviousKycStatus(oldAccount.getPreviousKycStatus());
					request.getAccount().setPreviousSanctionStatus(oldAccount.getPreviousSanctionStatus());
					request.getAccount()
							.setPreviousPaymentinWatchlistStatus(oldAccount.getPreviousPaymentinWatchlistStatus());
					request.getAccount()
							.setPreviousPaymentoutWatchlistStatus(oldAccount.getPreviousPaymentoutWatchlistStatus());
					request.getAccount().setVersion(oldAccount.getVersion() + 1);
					request.getAccount().setChannel(oldAccount.getChannel());
					// needed to save broadcast service
					request.getAccount().setTradeAccountID(oldAccount.getTradeAccountID());
					request.getAccount().setCustLegalEntity(oldAccount.getCustLegalEntity());//Add for AT-3327

					newRegistrationDBServiceImpl.getInternalRuleServiceContactStatus(oldAccount.getId(), request);
				}
				setIsContactModified(request, isContactModified);
			}
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		response.setOsrID(messageExchange.getRequest().getOsrId());
		messageExchange.setResponse(response);
		return message;
	}

	/**
	 * Gets the account contact details.
	 *
	 * @param request
	 *            the request
	 * @return the account contact details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void getAccountContactDetails(RegistrationServiceRequest request) throws ComplianceException {
		List<String> contactSFIds = new ArrayList<>(request.getAccount().getContacts().size());
		/**
		 * added by neelesh Pant .... TradeContactID is taken as list because
		 * for multiple contacts we need to check weather any of the
		 * tradeContactId is already present in database if yes Validation error
		 * is given..
		 */
		List<Integer> tradeContactIds = new ArrayList<>(request.getAccount().getContacts().size());
		String country = null;
		for (Contact c : request.getAccount().getContacts()) {
			String formatedContactsfid = c.getContactSFID();
			formatedContactsfid = formatedContactsfid.replace(formatedContactsfid, "'" + formatedContactsfid + "'");
			contactSFIds.add(formatedContactsfid);

			tradeContactIds.add(c.getTradeContactID());
			if (c.getPrimaryContact() != null && c.getPrimaryContact())
				country = c.getCountry();
		}

		RegistrationServiceRequest details = newRegistrationDBServiceImpl.getAccountDetails(request.getOrgCode(),
				request.getAccount().getBuyingCurrency(), request.getAccount().getSellingCurrency(), country,
				request.getAccount().getAccSFID(), contactSFIds);
		if (null != details.getAccount() && null != details.getAccount().getId()) {
			request.addAttribute(ACCOUNT, details.getAccount());
		}

		/**
		 * get Account and Contact trade fields list for validation : Abhijit G
		 */
		Boolean isAccountContactIsExist = newRegistrationDBServiceImpl.getExistingAccountContactId(request.getOrgCode(),
				request.getAccount().getAccSFID(), request.getAccount().getTradeAccountID(),
				request.getAccount().getTradeAccountNumber(), tradeContactIds, contactSFIds, details);

		request.setOrgId(details.getOrgId());
		request.addAttribute(Constants.FIELD_OLD_CONTACTS,
				details.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS));
		request.addAttribute(BUY_CURRENCY_ID, details.getAdditionalAttribute(BUY_CURRENCY_ID));
		request.addAttribute(SELL_CURRENCY_ID, details.getAdditionalAttribute(SELL_CURRENCY_ID));
		request.addAttribute(COUNTRY_ID, details.getAdditionalAttribute(COUNTRY_ID));
		request.addAttribute(Constants.ACC_ORG_NAME, details.getAdditionalAttribute(Constants.ACC_ORG_NAME));

		/**
		 * Added additional attrubutes for trade Account and contact filed
		 * duplication validation : Abhijit G
		 */
		request.addAttribute(Constants.ACCOUNT_SF_ID_LIST,
				details.getAdditionalAttribute(Constants.ACCOUNT_SF_ID_LIST));
		request.addAttribute(Constants.TRADE_ACCOUNT_NUMBER_LIST,
				details.getAdditionalAttribute(Constants.TRADE_ACCOUNT_NUMBER_LIST));
		request.addAttribute(Constants.CONTACT_SF_ID_LIST,
				details.getAdditionalAttribute(Constants.CONTACT_SF_ID_LIST));
		request.addAttribute(Constants.TRADE_ACCOUNT_ID_LIST,
				details.getAdditionalAttribute(Constants.TRADE_ACCOUNT_ID_LIST));
		request.addAttribute(Constants.TRADE_CONTACT_ID_LIST,
				details.getAdditionalAttribute(Constants.TRADE_CONTACT_ID_LIST));
		request.addAttribute(Constants.IS_ACCOUNT_CONTACT_ID_EXIST, isAccountContactIsExist);
		request.addAttribute(Constants.FIELD_REGISTERED_TIME,
				details.getAdditionalAttribute(Constants.FIELD_REGISTERED_TIME));
		request.addAttribute(Constants.FIELD_REGISTRATION_IN_TIME,
				details.getAdditionalAttribute(Constants.FIELD_REGISTRATION_IN_TIME));
		request.addAttribute(Constants.FIELD_EXPIRY_TIME, details.getAdditionalAttribute(Constants.FIELD_EXPIRY_TIME));
		
		request.addAttribute("AccountTMFlag", details.getAdditionalAttribute("AccountTMFlag"));
		request.addAttribute("IntuitionRiskLevel", details.getAdditionalAttribute("IntuitionRiskLevel"));

	}

	/**
	 * Enrich data for update. Get existing Account, company, contact info check
	 * if any of these are modified For Contact/Account name ignore case
	 * changes, i.e. update the data, but do NOT perform any checks For address
	 * ignore special char changes/formatting changes, i.e. update the data, but
	 * do NOT perform any checks
	 *
	 * @param request
	 *            the request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("unchecked")
	private void enrichDataForUpdate(RegistrationServiceRequest request, Message<MessageContext> message) throws ComplianceException {
		String isAccountModifiedCaseChangeFields;
		Boolean isAccountContentChanged;
		Boolean isAccountCaseChange;
		Boolean isAccountModified;
		boolean isaddressupdated = false;
		Account oldAccount = (Account) request.getAdditionalAttribute(ACCOUNT);
		List<Contact> oldContacts = (List<Contact>) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		
		// Added for AT-5453
		LOGGER.warn("\n----------Account status for Trade Account Number : {}, AccountSF ID : {} is {}",
				oldAccount.getTradeAccountNumber(), oldAccount.getAccSFID(), oldAccount.getAccountStatus());
		for (Contact contact : oldContacts)
			LOGGER.warn("\nContact Status for Contact SF ID : {} is {}", contact.getContactSFID(),
					contact.getContactStatus());

		fieldsToNotUpdate(request, oldAccount, oldContacts, message);
		
		CopyObjectUtil.copyNullFields(request.getAccount(), oldAccount, Account.class);
		String accountFieldsModifiedWithoutSpcChar = "";
		
		isAccountModifiedCaseChangeFields = checkAccountModifiedCaseChangeFields(request, oldAccount);
		
		isAccountCaseChange = checkWhetherCaseOrContentChanged(isAccountModifiedCaseChangeFields);
		isAccountContentChanged = isAccountModified(oldAccount, request.getAccount());
		isAccountModified = isAccountCaseChange || isAccountContentChanged;
		String accountStrFieldsModifiedCaseInsensitive = CopyObjectUtil
				.compareFieldsCaseInsensitive(request.getAccount(), oldAccount);
		if (accountStrFieldsModifiedCaseInsensitive != null && accountStrFieldsModifiedCaseInsensitive.length() > 2) {
			accountFieldsModifiedWithoutSpcChar = checkAccountFieldsModifiedWithoutSpcChar(request, oldAccount,
					accountStrFieldsModifiedCaseInsensitive);
		} else {
			isAccountContentChanged = Boolean.FALSE;
		}
		if (null != oldAccount) {

			Boolean isAccountModifiedCaseInsensitive = isAccountModifiedCaseInsensitive(
					accountStrFieldsModifiedCaseInsensitive);

			request.addAttribute("isAccountModified", isAccountModified);
			Boolean isContactModified = Boolean.FALSE;
			Boolean isContactModifiedCaseInsensitive = Boolean.FALSE;
			Boolean isCompanyModifiedCaseInsensitive;
			Boolean isConversionPredictionModifiedCaseInsensitive;
			Boolean isCorporateComplianceModifiedCaseInsensitive;
			/*** Set previous service statuses to account **/
			Account account = setPreviousServiceStatusToAccount(request, oldAccount);
			isCompanyModifiedCaseInsensitive = checkFroCompanyInfoUpdate(request, oldAccount,
					accountFieldsModifiedWithoutSpcChar);
			request.addAttribute(Constants.FIELD_ACC_ISCOMPANYMODIFIED, isCompanyModifiedCaseInsensitive);

			isConversionPredictionModifiedCaseInsensitive = checkForConversionPredictionInfoUpdated(request,
					oldAccount);
			request.addAttribute(Constants.FIELD_ACC_ISCONVPREDICTIONMODIFIED,
					isConversionPredictionModifiedCaseInsensitive);

			isCorporateComplianceModifiedCaseInsensitive = checkForCorporateComplianceInfoUpdated(request, oldAccount);
			request.addAttribute(Constants.FIELD_ACC_ISCORPORATECOMPLIANCEMODIFIED,
					isCorporateComplianceModifiedCaseInsensitive);

			String contactModifiedFieldsForCaseChange;
			String contactModifiedFieldsForContentChange;
			Boolean isContactCaseChanged;
			Boolean isContactContentChanged;
			Boolean isContactFieldsChangedWithoutSpcChar;
			for (Contact contact : request.getAccount().getContacts()) {
				Contact oldContact = getContactBySFId(contact.getContactSFID(), oldContacts);
				if (null != oldContact) {
					CopyObjectUtil.copyNullFields(contact, oldContact, Contact.class);
					/*** Set previous service statuses to contact **/

					setPreviousServiceStatusToContact(contact, oldContact);

					contactModifiedFieldsForCaseChange = getListOfFieldsModifiedForCaseChange(contact, oldContact);

					isContactCaseChanged = checkWhetherCaseOrContentChanged(contactModifiedFieldsForCaseChange);

					contactModifiedFieldsForContentChange = getListOfFieldsModifiedForContentChange(contact,
							oldContact);

					isContactContentChanged = checkWhetherCaseOrContentChanged(contactModifiedFieldsForContentChange);

					isContactModified = isContactContentChanged || isContactCaseChanged;

					String contactFieldsModifiedWithoutSpcChar = isContactModifiedCaseInsensitiveWithoutSpcChar(contact,
							oldContact, contactModifiedFieldsForCaseChange + contactModifiedFieldsForContentChange);

					isContactFieldsChangedWithoutSpcChar = checkWhetherCaseOrContentChanged(
							contactFieldsModifiedWithoutSpcChar);

					isContactContentChanged = checkForSpcCharUpdate(isContactFieldsChangedWithoutSpcChar);

					isContactModifiedCaseInsensitive = proccessContactChanges(request,
							contactFieldsModifiedWithoutSpcChar, account, contact, isContactContentChanged);

					contact.setVersion(contact.getVersion() + 1);

					String previousInactiveReason = newRegistrationDBServiceImpl
							.getPreviousInactiveReason(contact.getId());
					request.addAttribute("previousInactiveReason", previousInactiveReason);

					/* code added to send email after address change(AT-1503) */
					isaddressupdated = checkToSendEmailOnAddressChange(isaddressupdated,
							contactFieldsModifiedWithoutSpcChar);
					setContactKYCEligibleForEULegalEntity(request, contact);//AT-3327
				}
			}

			if (Boolean.TRUE.equals(isAccountContentChanged)) {
				request.addAttribute(Constants.FIELD_ACC_ISACCOUNTMODIFIED, isAccountModifiedCaseInsensitive);
			}

			/**
			 * Added firstSanctionSummary to show values of SanctionID,
			 * WorldCheck, OfacList on UI -Saylee
			 */
			SanctionSummary sanctionSummary = newRegistrationDBServiceImpl
					.getFirstSanctionSummary(request.getAccount().getId(), EntityEnum.ACCOUNT.name());
			request.addAttribute(FIRST_SANCTION_SUMMARY, sanctionSummary);
			setIsContactModified(request, isContactModified);
			// Removed if request.getAccount().getContacts().size() > 0
			// condition, so that Rules Service always find below values.
			newRegistrationDBServiceImpl.getInternalRuleServiceContactStatus(request.getAccount().getId(), request);

			Boolean isUpdateRequestFieldsModified = isUpdateRequestFieldsModified(isAccountModifiedCaseInsensitive,
					isContactModifiedCaseInsensitive, isCompanyModifiedCaseInsensitive,
					isConversionPredictionModifiedCaseInsensitive, isCorporateComplianceModifiedCaseInsensitive);

			// this function will be invoked from loop, which iterates over
			// contact list.
			// if first contact is modified, and seconds is not, PERFORM_CHECKS
			// might be disabled.
			// once its enabled, make sure its not disabled again.
			setPerformChecksFlagInRequest(request, isUpdateRequestFieldsModified);

			accountInfoUpdatedFlagSet(request);

			request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, isaddressupdated);
			
			//AT-3241 Amend FraudPredict re-trigger process to include only email, mobile and landline changes
			//if only account update is done Fraudpredict check should not be done
			for (Contact contact : oldContacts) {
				contact.setFraugsterEligible(false);
			}
		}
	}

	private void setContactKYCEligibleForEULegalEntity(RegistrationServiceRequest request, Contact contact) {
		if(LegalEntityEnum.CDLEU.getLECode().equals(request.getAccount().getCustLegalEntity())
				|| LegalEntityEnum.FCGEU.getLECode().equals(request.getAccount().getCustLegalEntity())
				|| LegalEntityEnum.TOREU.getLECode().equals(request.getAccount().getCustLegalEntity())) {
			contact.setKYCEligible(false);
		}
	}

	/**
	 * @param request
	 * @param oldAccount
	 * @param accountStrFieldsModifiedCaseInsensitive
	 * @return
	 */
	private String checkAccountFieldsModifiedWithoutSpcChar(RegistrationServiceRequest request, Account oldAccount,
			String accountStrFieldsModifiedCaseInsensitive) {
		String accountFieldsModifiedWithoutSpcChar;
		accountFieldsModifiedWithoutSpcChar = accountStrFieldsModifiedCaseInsensitive.replace(" ", "");
		accountFieldsModifiedWithoutSpcChar = accountFieldsModifiedWithoutSpcChar.substring(1,
				accountFieldsModifiedWithoutSpcChar.length() - 2);
		accountFieldsModifiedWithoutSpcChar = CopyObjectUtil.removeSpecialCharcterAndCompare(
				accountFieldsModifiedWithoutSpcChar.split(","), oldAccount, request.getAccount());
		return accountFieldsModifiedWithoutSpcChar;
	}

	/**
	 * @param isaddressupdated
	 * @param contactFieldsModifiedWithoutSpcChar
	 * @return
	 */
	private boolean checkToSendEmailOnAddressChange(boolean isaddressupdated,
			String contactFieldsModifiedWithoutSpcChar) {
		if(null!=contactFieldsModifiedWithoutSpcChar){
			boolean isOtherFieldsChanged = getChangedFieldsExceptAddress(contactFieldsModifiedWithoutSpcChar);
			if(!isOtherFieldsChanged)
				isaddressupdated = getAddressFieldsToSendEmail1(contactFieldsModifiedWithoutSpcChar);
		}
		return isaddressupdated;
	}

	/**
	 * @param request
	 * @param oldAccount
	 * @return
	 */
	private String checkAccountModifiedCaseChangeFields(RegistrationServiceRequest request, Account oldAccount) {
		String isAccountModifiedCaseChangeFields;
		isAccountModifiedCaseChangeFields = CopyObjectUtil.compareFieldsForCaseChecking(oldAccount,
				request.getAccount());
		if (null != isAccountModifiedCaseChangeFields) {
			isAccountModifiedCaseChangeFields = isAccountModifiedCaseChangeFields.replace("contacts", "");
		}
		return isAccountModifiedCaseChangeFields;
	}

	/**
	 * @param request
	 * @param oldAccount
	 * @param oldContacts
	 */
	private void fieldsToNotUpdate(RegistrationServiceRequest request, Account oldAccount, List<Contact> oldContacts, Message<MessageContext> message) {
		//AT-1725
		if(null != oldAccount)	{
			setFieldsNotToUpdate(request,oldAccount,oldContacts, message);
		}
	}

	/**
	 * @param request
	 */
	private void accountInfoUpdatedFlagSet(RegistrationServiceRequest request) {
		// AT-1303
		if (request.getAccount().getCustType().equals(Constants.CFX)
				|| request.getAccount().getCustType().equals(Constants.CFXETAILER)) {
			setAccountInfoUpdatedWatchlistStatusFlag(request);
		}
	}

	/**
	 * Sets the previous service status to contact.
	 *
	 * @param contact the contact
	 * @param oldContact the old contact
	 */
	private void setPreviousServiceStatusToContact(Contact contact, Contact oldContact) {
		contact.setPreviousBlacklistStatus(oldContact.getPreviousBlacklistStatus());
		contact.setPreviousFraugsterStatus(oldContact.getPreviousFraugsterStatus());
		contact.setPreviousKycStatus(oldContact.getPreviousKycStatus());
		contact.setPreviousSanctionStatus(oldContact.getPreviousSanctionStatus());
		contact.setPreviousCountryGlobalCheckStatus(oldContact.getPreviousCountryGlobalCheckStatus());
		contact.setPreviousPaymentinWatchlistStatus(oldContact.getPreviousPaymentinWatchlistStatus());//Added for AT-2986
		contact.setPreviousPaymentoutWatchlistStatus(oldContact.getPreviousPaymentoutWatchlistStatus());//Added for AT-2986
	}

	/**
	 * Sets the previous service status to account.
	 *
	 * @param request the request
	 * @param oldAccount the old account
	 * @return the account
	 */
	private Account setPreviousServiceStatusToAccount(RegistrationServiceRequest request, Account oldAccount) {
		Account account = request.getAccount();
		account.setPreviousBlacklistStatus(oldAccount.getPreviousBlacklistStatus());
		account.setPreviousFraugsterStatus(oldAccount.getPreviousFraugsterStatus());
		account.setPreviousKycStatus(oldAccount.getPreviousKycStatus());
		account.setPreviousSanctionStatus(oldAccount.getPreviousSanctionStatus());
		account.setPreviousPaymentinWatchlistStatus(oldAccount.getPreviousPaymentinWatchlistStatus());
		account.setPreviousPaymentoutWatchlistStatus(oldAccount.getPreviousPaymentoutWatchlistStatus());
		return account;
	}

	/**
	 * AT-1303 This method is added to set account info updated watchlist for
	 * CFX user if KYC and Sanction related fields changed on account and
	 * contact.
	 *
	 * @param request
	 *            the new account info updated watchlist status flag
	 */
	private void setAccountInfoUpdatedWatchlistStatusFlag(RegistrationServiceRequest request) {
		Company company = request.getAccount().getCompany();
		Boolean isKYCEligible = (Boolean) request
				.getAdditionalAttribute("isCFXConatctligibleForAccountInfoUpdatedWatchlist");

		for (Contact contact : request.getAccount().getContacts()) {
			if (null != isKYCEligible && isKYCEligible || contact.isSanctionEligible())
				request.addAttribute("isKYCSanctionPerformed", Boolean.TRUE);
		}

		if (company.isKYCEligible() || company.isSanctionEligible()) {
			request.addAttribute("isKYCSanctionPerformed", Boolean.TRUE);
		}
	}

	/**
	 * Sets the perform checks flag in request.
	 *
	 * @param request
	 *            the request
	 * @param isUpdateRequestFieldsModified
	 *            the is update request fields modified
	 */
	private void setPerformChecksFlagInRequest(RegistrationServiceRequest request,
			Boolean isUpdateRequestFieldsModified) {
		Boolean performChecks = (Boolean) request.getAdditionalAttribute(Constants.PERFORM_CHECKS);
		if (null == performChecks) {
			performChecks = Boolean.FALSE;
		}
		if (Boolean.FALSE.equals(performChecks))
			request.addAttribute(Constants.PERFORM_CHECKS, isUpdateRequestFieldsModified);
	}

	/**
	 * Check for spc char update.
	 *
	 * @param isContactFieldsChangedWithoutSpcChar
	 *            the is contact fields changed without spc char
	 * @return the boolean
	 */
	private Boolean checkForSpcCharUpdate(Boolean isContactFieldsChangedWithoutSpcChar) {
		Boolean result = Boolean.TRUE;
		if (Boolean.FALSE.equals(isContactFieldsChangedWithoutSpcChar)) {
			result = Boolean.FALSE;
		}
		return result;
	}

	/**
	 * Check fro company info update.
	 *
	 * @param request
	 *            the request
	 * @param oldAccount
	 *            the old account
	 * @param accountFieldsModifiedWithoutSpcChar
	 *            the account fields modified without spc char
	 * @return the boolean
	 */
	private Boolean checkFroCompanyInfoUpdate(RegistrationServiceRequest request, Account oldAccount,
			String accountFieldsModifiedWithoutSpcChar) {
		Boolean result = Boolean.FALSE;
		if (Constants.CFX.equalsIgnoreCase(oldAccount.getCustType())) {
			result = isAccountCompanyInfoChanged(request, oldAccount, accountFieldsModifiedWithoutSpcChar);
		}
		return result;
	}

	/**
	 * Check for conversion prediction info updated.
	 *
	 * @param request
	 *            the request
	 * @param oldAccount
	 *            the old account
	 * @return the boolean
	 */
	private Boolean checkForConversionPredictionInfoUpdated(RegistrationServiceRequest request, Account oldAccount) {

		Boolean isConvPredicModifiedCaseInsensitive;
		ConversionPrediction oldConversionPrediction = oldAccount.getConversionPrediction();
		ConversionPrediction conversionPrediction = request.getAccount().getConversionPrediction();

		CopyObjectUtil.copyNullFields(conversionPrediction, oldConversionPrediction, ConversionPrediction.class);
		String convPredicStrFieldsModifiedCaseInsensitive = CopyObjectUtil
				.compareFieldsCaseInsensitive(conversionPrediction, oldConversionPrediction);

		isConvPredicModifiedCaseInsensitive = isConvPredicModifiedCaseInsensitive(
				convPredicStrFieldsModifiedCaseInsensitive);

		return isConvPredicModifiedCaseInsensitive;

	}

	/**
	 * Check for corporate compliance info updated.
	 *
	 * @param request
	 *            the request
	 * @param oldAccount
	 *            the old account
	 * @return the boolean
	 */
	private Boolean checkForCorporateComplianceInfoUpdated(RegistrationServiceRequest request, Account oldAccount) {

		Boolean result = Boolean.FALSE;
		if (Constants.CFX.equalsIgnoreCase(oldAccount.getCustType())) {
			result = isCorporateComplianceInfoChanged(request, oldAccount);
		}
		return result;
	}

	/**
	 * Check whether case or content changed.
	 *
	 * @param contactModifiedFieldsForCaseChange
	 *            the contact modified fields for case change
	 * @return the boolean
	 */
	private Boolean checkWhetherCaseOrContentChanged(String contactModifiedFieldsForCaseChange) {
		Boolean result = Boolean.FALSE;
		if (null != contactModifiedFieldsForCaseChange && contactModifiedFieldsForCaseChange.length() > 2) {
			result = Boolean.TRUE;
		}
		return result;
	}

	/**
	 * Proccess contact changes.
	 *
	 * @param request
	 *            the request
	 * @param contactFieldsModifiedWithoutSpcChar
	 *            the contact fields modified without spc char
	 * @param oldAccount
	 *            the old account
	 * @param contact
	 *            the contact
	 * @param isContactContentChanged
	 *            the is contact content changed
	 * @return true, if successful
	 */
	private boolean proccessContactChanges(RegistrationServiceRequest request,
			String contactFieldsModifiedWithoutSpcChar, Account oldAccount, Contact contact,
			Boolean isContactContentChanged) {
		// field Added for jira AT-875
		Boolean addToNewKycWatchList;
		addToNewKycWatchList = (Boolean) request
				.getAdditionalAttribute(Constants.FIELD_ADD_TO_NEW_KYC_WATCHLIST_ON_UPDATE);
		if (null == addToNewKycWatchList)
			addToNewKycWatchList = Boolean.FALSE;

		Boolean isContactModifiedCaseInsensitive = Boolean.FALSE;

		if (Constants.CFX.equalsIgnoreCase(oldAccount.getCustType())) {
			contact.setKYCEligible(false);
			contact.setSanctionEligible(false);

			if (Boolean.FALSE.equals(addToNewKycWatchList) 
					&& isCFXContactKYCEligible(contactFieldsModifiedWithoutSpcChar, request)) {
				addToNewKycWatchList = Boolean.TRUE;
				isContactModifiedCaseInsensitive = Boolean.TRUE;
			}
			//AT-3241 Amend FraudPredict re-trigger process to include only email, mobile and landline changes
			contact.setFraugsterEligible(isFraudPredictEligible(contactFieldsModifiedWithoutSpcChar));
		} else {
			if (Boolean.TRUE.equals(isContactContentChanged)) {
				isContactModifiedCaseInsensitive = Boolean.TRUE;
				contact.setKYCEligible(isKYCEligible(contactFieldsModifiedWithoutSpcChar));
				contact.setSanctionEligible(isSanctionEligible(contactFieldsModifiedWithoutSpcChar));
				//AT-3241 Amend FraudPredict re-trigger process to include only email, mobile and landline changes
				contact.setFraugsterEligible(isFraudPredictEligible(contactFieldsModifiedWithoutSpcChar));
			} else {
				contact.setKYCEligible(false);
				contact.setSanctionEligible(false);
				contact.setFraugsterEligible(false);
			}
		}
		// If for CFX, contact fields are modified and
		// Contact is sanction/KYC eligible, make CFX account Inactive - Umesh
		// Contact is added to "Pending KYC on account" watchlist after
		// update(AT-875) - rohit
		request.addAttribute(Constants.FIELD_ADD_TO_NEW_KYC_WATCHLIST_ON_UPDATE, addToNewKycWatchList);
		return isContactModifiedCaseInsensitive;
	}

	/**
	 * Checks if is contact modified case insensitive without spc char.
	 *
	 * @param contact
	 *            the contact
	 * @param oldContact
	 *            the old contact
	 * @param strFieldsModified
	 *            the str fields modified
	 * @return the string
	 */
	private String isContactModifiedCaseInsensitiveWithoutSpcChar(Contact contact, Contact oldContact,
			String strFieldsModified) {

		String contactFieldsModifiedWithoutSpcChar = "";
		if (null != strFieldsModified) {
			// strFieldsModified has string representation of array, e.g.
			// [f1,f2,f3]
			// remove brackets, spaces and then get array of field names by
			// splitting with comma.
			String tFieldsMod = strFieldsModified.replace("[", "").replace("]", "").replace(" ", "");

			contactFieldsModifiedWithoutSpcChar = CopyObjectUtil.removeSpecialCharcterAndCompare(tFieldsMod.split(","),
					oldContact, contact);
			if (null != contactFieldsModifiedWithoutSpcChar)
				contactFieldsModifiedWithoutSpcChar = contactFieldsModifiedWithoutSpcChar.replace(" ", "");
			else
				contactFieldsModifiedWithoutSpcChar = "";
		}
		return contactFieldsModifiedWithoutSpcChar;
	}

	/**
	 * Checks if is account company info changed.
	 *
	 * @param request
	 *            the request
	 * @param oldAccount
	 *            the old account
	 * @param accountFieldsModifiedWithoutSpcChar
	 *            the account fields modified without spc char
	 * @return the boolean
	 */
	private Boolean isAccountCompanyInfoChanged(RegistrationServiceRequest request, Account oldAccount,
			String accountFieldsModifiedWithoutSpcChar) {
		Boolean isCompanyModifiedCaseInsensitive;
		Company oldCompany = oldAccount.getCompany();
		Company company = request.getAccount().getCompany();

		CopyObjectUtil.copyNullFields(company, oldCompany, Company.class);
		String companyFieldsModifiedWithoutSpcChar = "";
		String companyStrFieldsModifiedCaseInsensitive = CopyObjectUtil.compareFieldsCaseInsensitive(company,
				oldCompany);
		if (companyStrFieldsModifiedCaseInsensitive != null && companyStrFieldsModifiedCaseInsensitive.length() > 2) {
			companyFieldsModifiedWithoutSpcChar = companyStrFieldsModifiedCaseInsensitive.replace(" ", "");
			companyFieldsModifiedWithoutSpcChar = companyFieldsModifiedWithoutSpcChar.substring(1,
					companyFieldsModifiedWithoutSpcChar.length() - 2);
			companyFieldsModifiedWithoutSpcChar = CopyObjectUtil.removeSpecialCharcterAndCompare(
					companyFieldsModifiedWithoutSpcChar.split(","), oldCompany, company);
		}

		isCompanyModifiedCaseInsensitive = isCompanyModifiedCaseInsensitive(companyStrFieldsModifiedCaseInsensitive);
		company.setSanctionEligible(
				isCompanySanctionEligible(companyFieldsModifiedWithoutSpcChar, accountFieldsModifiedWithoutSpcChar));
		return isCompanyModifiedCaseInsensitive;
	}

	/**
	 * Checks if is corporate compliance info changed.
	 *
	 * @param request
	 *            the request
	 * @param oldAccount
	 *            the old account
	 * @return the boolean
	 */
	private Boolean isCorporateComplianceInfoChanged(RegistrationServiceRequest request, Account oldAccount) {

		Boolean isCorporateComplianceModified;
		CorperateCompliance oldConversionPrediction = oldAccount.getCorperateCompliance();
		CorperateCompliance conversionPrediction = request.getAccount().getCorperateCompliance();

		CopyObjectUtil.copyNullFields(conversionPrediction, oldConversionPrediction, CorperateCompliance.class);
		String corperateComplianceStrFieldsModifiedCaseInsensitive = CopyObjectUtil
				.compareFieldsCaseInsensitive(conversionPrediction, oldConversionPrediction);

		isCorporateComplianceModified = isCorperateComplianceModifiedCaseInsensitive(
				corperateComplianceStrFieldsModifiedCaseInsensitive);

		return isCorporateComplianceModified;

	}

	/**
	 * Checks if is update request fields modified.
	 *
	 * @param isAccountModifiedCaseInsensitive
	 *            the is account modified case insensitive
	 * @param isContactModified
	 *            the is contact modified
	 * @param isCompanyModifiedCaseInsensitive
	 *            the is company modified case insensitive
	 * @param isConversionPredictionCaseInsensitive
	 *            the is conversion prediction case insensitive
	 * @param isCorporateComplianceCaseInsensitive
	 *            the is corporate compliance case insensitive
	 * @return the boolean
	 */
	private Boolean isUpdateRequestFieldsModified(Boolean isAccountModifiedCaseInsensitive, Boolean isContactModified,
			Boolean isCompanyModifiedCaseInsensitive, Boolean isConversionPredictionCaseInsensitive,
			Boolean isCorporateComplianceCaseInsensitive) {
		boolean result = false;
		Boolean checkConvPredAndCorpComp = isConversionPredictionCaseInsensitive
				|| isCorporateComplianceCaseInsensitive;
		if (Boolean.TRUE.equals(isAccountModifiedCaseInsensitive) 
				|| Boolean.TRUE.equals(isContactModified) 
				|| Boolean.TRUE.equals(isCompanyModifiedCaseInsensitive)
				|| Boolean.TRUE.equals(checkConvPredAndCorpComp))
			result = true;
		return result;
	}

	/**
	 * Checks if is account modified.
	 *
	 * @param oldAccount
	 *            the old account
	 * @param newAccount
	 *            the new account
	 * @return true, if is account modified
	 * 
	 *         used regex below to replace previous Status fields [contacts,
	 *         previousPaymentinWatchlistStatus,
	 *         previousPaymentoutWatchlistStatus]
	 */
	private boolean isAccountModified(Account oldAccount, Account newAccount) {
		// serialVersionUID [, contacts, version]
		String listFileds = CopyObjectUtil.compareFields(oldAccount, newAccount);
		if (null != listFileds) {
			listFileds = listFileds.replace("serialVersionUID", "").replace("contacts", "").replace(VERSION, "")
					.replaceAll("previous.[a-zA-z]+", "").replace(",", "").replace(" ", "");
			return (listFileds.length() > 2);
		}
		return false;
	}

	/**
	 * Checks if is account modified case insensitive.
	 *
	 * @param listFileds
	 *            the list fileds
	 * @return true, if is account modified case insensitive
	 */
	private boolean isAccountModifiedCaseInsensitive(String listFileds) {
		if (null != listFileds) {
			String result = listFileds.replace(",", "").replace(" ", "");
			return (result.length() > 2);
		}
		return false;
	}

	/**
	 * Checks if is company modified case insensitive.
	 *
	 * @param listFileds
	 *            the list fileds
	 * @return true, if is company modified case insensitive
	 */
	private boolean isCompanyModifiedCaseInsensitive(String listFileds) {
		boolean output = false;
		if (null != listFileds) {
			String result = listFileds.replace(",", "").replace(" ", "");
			output = (result.length() > 2);
		}
		return output;
	}

	/**
	 * Checks if is conv predic modified case insensitive.
	 *
	 * @param listModifiedFileds
	 *            the list fileds
	 * @return true, if is conv predic modified case insensitive
	 */
	private boolean isConvPredicModifiedCaseInsensitive(String listModifiedFileds) {
		boolean output = false;
		if (null != listModifiedFileds) {
			String result = listModifiedFileds.replace(",", "").replace(" ", "");
			output = (result.length() > 2);
		}
		return output;
	}

	/**
	 * Checks if is corperate compliance modified case insensitive.
	 *
	 * @param listorperateModifiedFileds
	 *            the list fileds
	 * @return true, if is corperate compliance modified case insensitive
	 */
	private boolean isCorperateComplianceModifiedCaseInsensitive(String listorperateModifiedFileds) {
		boolean output = false;
		if (null != listorperateModifiedFileds) {
			String result = listorperateModifiedFileds.replace(",", "").replace(" ", "");
			output = (result.length() > 2);
		}
		return output;
	}

	/**
	 * Gets the contact by SF id.
	 *
	 * @param sfId
	 *            the sf id
	 * @param contacts
	 *            the contacts
	 * @return the contact by SF id
	 */
	private Contact getContactBySFId(String sfId, List<Contact> contacts) {
		for (Contact c : contacts) {
			if (c.getContactSFID().equalsIgnoreCase(sfId))
				return c;
		}
		return null;
	}

	/**
	 * Sets the is contact modified.
	 *
	 * @param request
	 *            the request
	 * @param isContactModified
	 *            the is contact modified
	 */
	private void setIsContactModified(RegistrationServiceRequest request, Boolean isContactModified) {
		request.addAttribute(IS_CONTACT_MODIFIED, isContactModified);
	}

	/**
	 * Checks if is KYC eligible.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @return true, if is KYC eligible
	 */
	private static boolean isKYCEligible(String strFieldsModified) {
		boolean result;
		result = isAddressChanged(strFieldsModified);
		result = isNameChanged(strFieldsModified, result);
		if (result || strFieldsModified.contains(DOB) || strFieldsModified.contains(GENDER)
				|| strFieldsModified.contains(SSN))
			result = true;

		return result;
	}

	/**
	 * Checks if is address changed.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @return true, if is address changed
	 */
	private static boolean isAddressChanged(String strFieldsModified) {
		boolean result;
		boolean addressCheck = strFieldsModified.contains(ADDRESS_TYPE) || strFieldsModified.contains(ADDRESS1)
				|| strFieldsModified.contains(CITY);
		boolean stateBuildingstreetCheck = strFieldsModified.contains(STATE_PROVINCE_COUNTY)
				|| strFieldsModified.contains(BUILDING_NAME) || strFieldsModified.contains(STREET_NUMBER)
				|| strFieldsModified.contains(STREET_TYPE);
		boolean subAddress = strFieldsModified.contains(SUB_STREET) || strFieldsModified.contains(STATE_PROVINCE_COUNTY)
				|| strFieldsModified.contains(POST_CODE) || strFieldsModified.contains(COUNTRY);
		result = areAddressFieldsChanged(strFieldsModified, addressCheck, stateBuildingstreetCheck, subAddress);
		return result;
	}

	/**
	 * Are address fields changed.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @param addressCheck
	 *            the address check
	 * @param stateBuildingstreetCheck
	 *            the state buildingstreet check
	 * @param subAddress
	 *            the sub address
	 * @return true, if successful
	 */
	private static boolean areAddressFieldsChanged(String strFieldsModified, boolean addressCheck,
			boolean stateBuildingstreetCheck, boolean subAddress) {
		boolean subBuildingUnitDetails = strFieldsModified.contains(SUB_BUILDINGOR_FLAT)
				|| strFieldsModified.contains(UNIT_NUMBER) || strFieldsModified.contains(SUB_CITY)
				|| strFieldsModified.contains(REGION);
		boolean isaddrChanged = addressCheck || subAddress;
		return (isaddrChanged || stateBuildingstreetCheck || subBuildingUnitDetails);

	}

	/**
	 * Checks if is sanction eligible.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @return true, if is sanction eligible
	 */
	private static boolean isSanctionEligible(String strFieldsModified) {
		boolean result = false;
		if (strFieldsModified.contains(FIRST_NAME) || strFieldsModified.contains(MIDDLE_NAME)
				|| strFieldsModified.contains(LAST_NAME))
			result = true;
		if (result || strFieldsModified.contains(SECOND_SURNAME))
			result = true;
		if (result || strFieldsModified.contains(DOB) || strFieldsModified.contains(GENDER))
			result = true;
		result = isCountryModified(strFieldsModified, result);
		return result;

	}

	/**
	 * Checks if is country modified.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @param result
	 *            the result
	 * @return true, if is country modified
	 */
	private static boolean isCountryModified(String strFieldsModified, boolean result) {
		return (result || strFieldsModified.contains(COUNTRY));
	}

	/**
	 * Checks if is CFX contact KYC eligible.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @param request
	 *            the request
	 * @return true, if is CFX contact KYC eligible
	 */
	private static boolean isCFXContactKYCEligible(String strFieldsModified, RegistrationServiceRequest request) {
		boolean isKYCEligible;

		isKYCEligible = isAddressChanged(strFieldsModified);
		isKYCEligible = isNameChanged(strFieldsModified, isKYCEligible);
		if (isKYCEligible || strFieldsModified.contains(DOB) || strFieldsModified.contains(GENDER))
			isKYCEligible = true;
		isKYCEligible = isEmailChanged(strFieldsModified, isKYCEligible);
		if (isKYCEligible || strFieldsModified.contains("address2,") || strFieldsModified.contains("address3,"))
			isKYCEligible = true;
		if (isKYCEligible || strFieldsModified.contains("phoneMobile,") || strFieldsModified.contains("phoneHome,"))
			isKYCEligible = true;
		request.addAttribute("isCFXConatctligibleForAccountInfoUpdatedWatchlist", isKYCEligible);
		return isKYCEligible;
	}

	/**
	 * Checks if is email changed.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @param isKYCEligible
	 *            the is KYC eligible
	 * @return true, if is email changed
	 */
	private static boolean isEmailChanged(String strFieldsModified, boolean isKYCEligible) {
		Boolean emailChanged = Boolean.FALSE;
		if (isKYCEligible || strFieldsModified.contains("email,"))
			emailChanged = Boolean.TRUE;
		return emailChanged;
	}

	/**
	 * Checks if is name changed.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @param isKYCEligible
	 *            the is KYC eligible
	 * @return true, if is name changed
	 */
	private static boolean isNameChanged(String strFieldsModified, boolean isKYCEligible) {
		Boolean nameChanged = Boolean.FALSE;
		if (isKYCEligible || strFieldsModified.contains(FIRST_NAME) || strFieldsModified.contains(MIDDLE_NAME))
			nameChanged = Boolean.TRUE;
		if (isKYCEligible || strFieldsModified.contains(SECOND_SURNAME) || strFieldsModified.contains(LAST_NAME))
			nameChanged = Boolean.TRUE;
		return nameChanged;
	}

	/**
	 * Checks if is company sanction eligible.
	 *
	 * @param companyStrFieldsModified
	 *            the company str fields modified
	 * @param accountStrFieldsModified
	 *            the account str fields modified
	 * @return true, if is company sanction eligible
	 */
	private static boolean isCompanySanctionEligible(String companyStrFieldsModified, String accountStrFieldsModified) {
		boolean result = false;
		if (companyStrFieldsModified != null && companyStrFieldsModified.contains("countryOfEstablishment")) {
			result = true;
		}
		if (accountStrFieldsModified != null && accountStrFieldsModified.contains("accountName")) {
			result = true;
		}
		return result;
	}

	/**
	 * Gets the list of fields modified for case change.
	 *
	 * @param contact
	 *            the contact
	 * @param oldContact
	 *            the old contact
	 * @return the list of fields modified for case change
	 */
	private String getListOfFieldsModifiedForCaseChange(Contact contact, Contact oldContact) {
		String strFieldsModified = CopyObjectUtil.compareFieldsForCaseChecking(oldContact, contact);
		if (null != strFieldsModified) {
			strFieldsModified = strFieldsModified.replace(SERIAL_VERSION_UID, "").replace(IS_KYC_ELIGIBLE, "")
					.replace(IS_FRAUGSTER_ELIGIBLE, "").replace(IS_SANCTION_ELIGIBLE, "")
					.replace(IS_SANCTION_PERFORMED, "").replace(" ", "");
		} else {
			strFieldsModified = "";
		}
		return strFieldsModified;
	}

	/**
	 * Gets the list of fields modified for content change.
	 *
	 * @param contact
	 *            the contact
	 * @param oldContact
	 *            the old contact
	 * @return the list of fields modified for content change
	 */
	private String getListOfFieldsModifiedForContentChange(Contact contact, Contact oldContact) {
		String strFieldsModified = CopyObjectUtil.compareFieldsForContentChecking(oldContact, contact);
		if (null != strFieldsModified) {
			strFieldsModified = strFieldsModified.replace(SERIAL_VERSION_UID, "").replace(IS_KYC_ELIGIBLE, "")
					.replace(IS_FRAUGSTER_ELIGIBLE, "").replace(IS_SANCTION_ELIGIBLE, "")
					.replace(IS_SANCTION_PERFORMED, "").replace(UPDATE_METHOD, "").replace(VERSION, "")
					.replace(" ", "");
		} else {
			strFieldsModified = "";
		}
		return strFieldsModified;
	}

	/**
	 * Enrich data for failed registrations.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> enrichDataForFailedRegistrations(Message<MessageContext> message, @Header UUID correlationID)
			throws ComplianceException {
		ReprocessFailedDetails request = message.getPayload().getGatewayMessageExchange()
				.getRequest(ReprocessFailedDetails.class);
		RegistrationServiceRequest registrationDetails;
		RegistrationResponse response = new RegistrationResponse();
		registrationDetails = newRegistrationDBServiceImpl
				.getAccountDetailsForServiceFailedRegistrationDetails(request.getTransId(), request.getTransType());
		registrationDetails = newRegistrationDBServiceImpl.getServiceFailedRegistrationDetails(
				registrationDetails.getAccount().getId(), registrationDetails.getAccount().getCustType(), request.getTransId()); // AT-4288

		message.getPayload().setOrgCode(registrationDetails.getOrgCode());
		if (request.getTransType().equals(TransactionTypeEnum.ACCOUNT.getTransactionTypeAsString()))
			registrationDetails.addAttribute("accountId", request.getTransId());
		else
			registrationDetails.addAttribute("contactId", request.getTransId());

		Integer eventId = (Integer) registrationDetails.getAdditionalAttribute("EventID");
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Integer userID = newRegistrationDBServiceImpl.getUserIDFromSSOUserID(token.getPreferredUserName());
		token.setUserID(userID);
		SanctionResponse sanctionResponse = new SanctionResponse();
		List<EventServiceLog> sanctionEntityLogList = new ArrayList<>();
		if (setServiceChecksEligiblity(registrationDetails)) {
			registrationDetails.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		}
		ContactResponse contactResponse = new ContactResponse();
		buildFraugsterExchange(message, eventId, token, registrationDetails);
		buildSanctionExchange(message, registrationDetails, eventId, token, sanctionResponse, sanctionEntityLogList);
		buildKYCExchange(message, eventId, token, registrationDetails);
		buildInternalRuleServiceExchange(message, eventId, registrationDetails, contactResponse, token);
		setIsConatactModified(registrationDetails);

		registrationDetails.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		registrationDetails.addAttribute("FailedRegDetails", request);
		registrationDetails.addAttribute(Constants.OLD_REQUEST, registrationDetails);
		registrationDetails.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
		registrationDetails.addAttribute(Constants.FIELD_OLD_CONTACTS, registrationDetails.getAccount().getContacts());
		registrationDetails.addAttribute("OldAccountStatus", registrationDetails.getAccount().getAccountStatus()); //AT-5396
		
		newRegistrationDBServiceImpl.getInternalRuleServiceContactStatus(registrationDetails.getAccount().getId(),
				registrationDetails);
		accountInfoUpdatedFlagSet(registrationDetails);
		registrationDetails.setCorrelationID(correlationID); //AT-5514
		message.getPayload().getGatewayMessageExchange().setRequest(registrationDetails);
		message.getPayload().getGatewayMessageExchange().setResponse(response);
		return message;
	}

	/**
	 * Sets the checks if is conatact modified.
	 *
	 * @param registrationDetails
	 *            the new checks if is conatact modified
	 */
	private void setIsConatactModified(RegistrationServiceRequest registrationDetails) {
		if (CustomerTypeEnum.PFX.name().equals(registrationDetails.getAccount().getCustType())) {
			registrationDetails.addAttribute(IS_CONTACT_MODIFIED, Boolean.TRUE);
		}
		// AT-5515
		Integer contactIdForCFX = (Integer) registrationDetails.getAdditionalAttribute(CONTACT_ID_FOR_CFX);

		for(Contact con: registrationDetails.getAccount().getContacts()){
			if ((registrationDetails.getAdditionalAttribute(Constants.BLACKLIST_STATUS).equals(Constants.SERVICE_FAILURE)
					&& null != registrationDetails.getAccount().getId())
					|| (contactIdForCFX.equals(con.getId()) && con.getPreviousBlacklistStatus().equals(Constants.SERVICE_FAILURE))
					|| (contactIdForCFX.equals(con.getId()) && con.getPreviousFraugsterStatus().equals(Constants.SERVICE_FAILURE)))
			{
				registrationDetails.addAttribute(IS_CONTACT_MODIFIED, Boolean.TRUE);
			}
		}
	}

	/**
	 * Builds the sanction exchange.
	 *
	 * @param message
	 *            the message
	 * @param registrationDetails
	 *            the registration details
	 * @param eventId
	 *            the event id
	 * @param token
	 *            the token
	 * @param sanctionResponse
	 *            the sanction response
	 * @param sanctionEntityLogList
	 *            the sanction entity log list
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildSanctionExchange(Message<MessageContext> message, RegistrationServiceRequest registrationDetails,
			Integer eventId, UserProfile token, SanctionResponse sanctionResponse,
			List<EventServiceLog> sanctionEntityLogList) throws ComplianceException {
		if (CustomerTypeEnum.PFX.name().equals(registrationDetails.getAccount().getCustType()))
			buildSanctionExchangeForPFX(message, eventId, sanctionResponse, token, sanctionEntityLogList,
					registrationDetails);
		else
			buildSanctionExchangeForCFX(message, eventId, sanctionResponse, token, sanctionEntityLogList,
					registrationDetails);
	}

	/**
	 * Sets the service checks eligiblity.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @return true, if successful
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private boolean setServiceChecksEligiblity(RegistrationServiceRequest registrationDetails)
			throws ComplianceException {
		Boolean result = Boolean.FALSE;
		Integer accountId = (Integer) registrationDetails.getAdditionalAttribute("accountId");
		result = getIsFraugsterEligiblie(registrationDetails, result);
		result = getIsSanctionEligible(registrationDetails, result, accountId);
		result = getIsBlacklistEligible(registrationDetails, result, accountId);
		result = getIsCountryCheckEligible(registrationDetails, result);
		result = getIsIPCheckEligible(registrationDetails, result);
		result = getIsGlobalCheckEligible(registrationDetails, result);
		result = getIsEIDEligible(registrationDetails, result);
		return result;
	}

	/**
	 * Gets the checks if is global check eligible.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is global check eligible
	 */
	private Boolean getIsGlobalCheckEligible(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean globalCheckResult = result;
		if (null != registrationDetails.getAdditionalAttribute(GLOBAL_CHECK_STATUS)
				&& registrationDetails.getAdditionalAttribute(GLOBAL_CHECK_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_GLOBAL_CHECK_ELIGIBLE, Boolean.TRUE);
			globalCheckResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_GLOBAL_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		return globalCheckResult;
	}

	/**
	 * Gets the checks if is IP check eligible.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is IP check eligible
	 */
	private Boolean getIsIPCheckEligible(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean ipCheckResult = result;
		if (null != registrationDetails.getAdditionalAttribute(IP_CHECK_STATUS)
				&& registrationDetails.getAdditionalAttribute(IP_CHECK_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_IP_CHECK_ELIGIBLE, Boolean.TRUE);
			ipCheckResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_IP_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		return ipCheckResult;
	}

	/**
	 * Gets the checks if is country check eligible.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is country check eligible
	 */
	private Boolean getIsCountryCheckEligible(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean countryCheckResult = result;
		if (null != registrationDetails.getAdditionalAttribute(COUNTRY_CHECK_STATUS)
				&& registrationDetails.getAdditionalAttribute(COUNTRY_CHECK_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_COUNTRY_CHECK_ELIGIBLE, Boolean.TRUE);
			countryCheckResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_COUNTRY_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		return countryCheckResult;
	}

	/**
	 * Gets the checks if is EID eligible.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is EID eligible
	 */
	private Boolean getIsEIDEligible(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean eidResult = result;
		if (registrationDetails.getAccount().getCustType().equals(CustomerTypeEnum.PFX.name()))
			eidResult = getIsEIDEligibleForPfx(registrationDetails, eidResult);
		else
			registrationDetails.addAttribute(IS_EID_ELIGIBLE, Boolean.FALSE);
		return eidResult;
	}

	/**
	 * Gets the checks if is blacklist eligible.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @param accountId
	 *            the account id
	 * @return the checks if is blacklist eligible
	 */
	private Boolean getIsBlacklistEligible(RegistrationServiceRequest registrationDetails, Boolean result,
			Integer accountId) {
		Boolean blacklistResult = result;
		if (registrationDetails.getAccount().getCustType().equals(CustomerTypeEnum.PFX.name()))
			blacklistResult = getIsBlacklistEligibleForPfx(registrationDetails, blacklistResult);
		else
			blacklistResult = getIsBlacklistEligibleForCfx(registrationDetails, blacklistResult, accountId);
		return blacklistResult;
	}

	/**
	 * Gets the checks if is sanction eligible.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @param accountId
	 *            the account id
	 * @return the checks if is sanction eligible
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean getIsSanctionEligible(RegistrationServiceRequest registrationDetails, Boolean result,
			Integer accountId) throws ComplianceException {
		Boolean sanctionResult = result;
		if (registrationDetails.getAccount().getCustType().equals(CustomerTypeEnum.PFX.name()))
			sanctionResult = getIsSanctionEligibleForPfx(registrationDetails, sanctionResult);
		else
			sanctionResult = getIsSanctionEligibleForCfx(registrationDetails, sanctionResult, accountId);
		return sanctionResult;
	}

	/**
	 * Gets the checks if is fraugster eligiblie.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is fraugster eligiblie
	 */
	private Boolean getIsFraugsterEligiblie(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean fraugsterResult = result;
		if (registrationDetails.getAccount().getCustType().equals(CustomerTypeEnum.PFX.name()))
			fraugsterResult = getIsFraugsterEligiblieForPfx(registrationDetails, fraugsterResult);
		else
			fraugsterResult = getIsFraugsterEligiblieForCfx(registrationDetails, fraugsterResult);
		return fraugsterResult;
	}

	/**
	 * Gets the checks if is sanction eligible for cfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @param accountId
	 *            the account id
	 * @return the checks if is sanction eligible for cfx
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean getIsSanctionEligibleForCfx(RegistrationServiceRequest registrationDetails, Boolean result,
			Integer accountId) throws ComplianceException {
		Boolean sanctionResult = result;
		if (registrationDetails.getAdditionalAttribute(Constants.SANCTION_STATUS).equals(Constants.SERVICE_FAILURE)
				&& null != accountId) {
			registrationDetails.addAttribute(IS_SANCTION_ELIGIBLE_FOR_RECHECK, Boolean.TRUE);
			Contact contact = (Contact) registrationDetails.getAdditionalAttribute("contact");
			getFirstSanctionSummary(registrationDetails, contact);
			sanctionResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_SANCTION_ELIGIBLE_FOR_RECHECK, Boolean.FALSE);
		}
		return sanctionResult;
	}

	/**
	 * Gets the checks if is sanction eligible for pfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is sanction eligible for pfx
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean getIsSanctionEligibleForPfx(RegistrationServiceRequest registrationDetails, Boolean result)
			throws ComplianceException {
		Boolean sanctionResult = result;
		if (registrationDetails.getAdditionalAttribute(Constants.SANCTION_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_SANCTION_ELIGIBLE_FOR_RECHECK, Boolean.TRUE);
			Contact contact = (Contact) registrationDetails.getAdditionalAttribute("contact");
			getFirstSanctionSummary(registrationDetails, contact);
			sanctionResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_SANCTION_ELIGIBLE_FOR_RECHECK, Boolean.FALSE);
		}
		return sanctionResult;
	}

	/**
	 * Gets the first sanction summary.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param contact
	 *            the contact
	 * @return the first sanction summary
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void getFirstSanctionSummary(RegistrationServiceRequest registrationDetails, Contact contact)
			throws ComplianceException {
		try {
			SanctionSummary sanctionSummary = newRegistrationDBServiceImpl
					.getFirstSanctionSummary(registrationDetails.getAccount().getId(), EntityEnum.ACCOUNT.name());

			registrationDetails.addAttribute(FIRST_SANCTION_SUMMARY + registrationDetails.getAccount().getId(),
					sanctionSummary);

			EntityDetails entityDetails = eventDBServiceImpl
					.isSanctionPerformed(registrationDetails.getAccount().getId(), EntityEnum.ACCOUNT.name());

			contact.setSanctionPerformed(entityDetails.getIsExist());

		} catch (ComplianceException complianceException) {
			LOGGER.error("Exception in setServiceChecksEligiblity()", complianceException);
		}
	}

	/**
	 * Gets the checks if is blacklist eligible for cfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @param accountId
	 *            the account id
	 * @return the checks if is blacklist eligible for cfx
	 */
	private Boolean getIsBlacklistEligibleForCfx(RegistrationServiceRequest registrationDetails, Boolean result,
			Integer accountId) {
		Boolean blacklistResult = result;
		//AT-5487
		Integer contactIdForCFX = (Integer) registrationDetails.getAdditionalAttribute(CONTACT_ID_FOR_CFX);
		for(Contact con: registrationDetails.getAccount().getContacts()){
				if ((registrationDetails.getAdditionalAttribute(Constants.BLACKLIST_STATUS).equals(Constants.SERVICE_FAILURE)
						&& null != accountId)
						|| (contactIdForCFX.equals(con.getId()) && con.getPreviousBlacklistStatus()
								.equals(Constants.SERVICE_FAILURE))) 				
				{
					registrationDetails.addAttribute(IS_BLACKLIST_ELIGIBLE, Boolean.TRUE);
					blacklistResult = Boolean.TRUE;
					break;
				} else {
					registrationDetails.addAttribute(IS_BLACKLIST_ELIGIBLE, Boolean.FALSE);
				}
		}
		return blacklistResult;
	}

	/**
	 * Gets the checks if is blacklist eligible for pfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is blacklist eligible for pfx
	 */
	private Boolean getIsBlacklistEligibleForPfx(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean blacklistResult = result;
		if (registrationDetails.getAdditionalAttribute(Constants.BLACKLIST_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_BLACKLIST_ELIGIBLE, Boolean.TRUE);
			blacklistResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_BLACKLIST_ELIGIBLE, Boolean.FALSE);
		}
		return blacklistResult;
	}

	/**
	 * Gets the checks if is EID eligible for pfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is EID eligible for pfx
	 */
	private Boolean getIsEIDEligibleForPfx(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean eidResult = result;
		if (registrationDetails.getAdditionalAttribute(Constants.EID_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_EID_ELIGIBLE, Boolean.TRUE);
			eidResult = Boolean.TRUE;
		} else {
			registrationDetails.addAttribute(IS_EID_ELIGIBLE, Boolean.FALSE);
		}
		return eidResult;
	}

	/**
	 * Gets the checks if is fraugster eligiblie for pfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is fraugster eligiblie for pfx
	 */
	private Boolean getIsFraugsterEligiblieForPfx(RegistrationServiceRequest registrationDetails, Boolean result) {
		boolean fraugsterResult = result;
		if (registrationDetails.getAdditionalAttribute(Constants.FRAUGSTER_STATUS).equals(Constants.SERVICE_FAILURE)) {
			registrationDetails.addAttribute(IS_FRAUGSTER_ELIGIBLE_FOR_RECHECK, Boolean.TRUE);
			fraugsterResult = true;
		} else {
			registrationDetails.addAttribute(IS_FRAUGSTER_ELIGIBLE_FOR_RECHECK, Boolean.FALSE);
		}
		return fraugsterResult;
	}

	/**
	 * Gets the checks if is fraugster eligiblie for cfx.
	 *
	 * @param registrationDetails
	 *            the registration details
	 * @param result
	 *            the result
	 * @return the checks if is fraugster eligiblie for cfx
	 */
	private Boolean getIsFraugsterEligiblieForCfx(RegistrationServiceRequest registrationDetails, Boolean result) {
		Boolean fraugsterResult = result;
		//AT-5487
		Integer contactIdForCFX = (Integer) registrationDetails.getAdditionalAttribute(CONTACT_ID_FOR_CFX);
		for(Contact con: registrationDetails.getAccount().getContacts()){
			if (contactIdForCFX.equals(con.getId()) && con.getPreviousFraugsterStatus()
							.equals(Constants.SERVICE_FAILURE)) 				
			{
				registrationDetails.addAttribute(IS_FRAUGSTER_ELIGIBLE_FOR_RECHECK, Boolean.TRUE);
				fraugsterResult = Boolean.TRUE;
				break;
			} else {
				registrationDetails.addAttribute(IS_FRAUGSTER_ELIGIBLE_FOR_RECHECK, Boolean.FALSE);
			}
		}
		return fraugsterResult;
	}

	/**
	 * Builds the fraugster exchange.
	 *
	 * @param message
	 *            the message
	 * @param eventId
	 *            the event id
	 * @param token
	 *            the token
	 * @param registrationDetails
	 *            the registration details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildFraugsterExchange(Message<MessageContext> message, Integer eventId, UserProfile token,
			RegistrationServiceRequest registrationDetails) throws ComplianceException {
		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE_FOR_RECHECK)) {
			ServiceTypeEnum serviceType = ServiceTypeEnum.FRAUGSTER_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			EventServiceLog esl = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
					registrationDetails.getAccount().getId(), serviceType, registrationDetails, entityType);
			FraugsterOnUpdateResponse fResponse = new FraugsterOnUpdateResponse();
		try {
				FraugsterOnUpdateContactResponse fOnUpdateResponses = JsonConverterUtil
						.convertToObject(FraugsterOnUpdateContactResponse.class, esl.getProviderResponse());
				List<FraugsterOnUpdateContactResponse> fOnUpdateResponsesList = new ArrayList<>();
				fOnUpdateResponsesList.add(fOnUpdateResponses);
				fResponse.setContactResponses(fOnUpdateResponsesList);
				fResponse.setStatus(fOnUpdateResponses.getStatus());
				FraugsterSummary summary = JsonConverterUtil.convertToObject(FraugsterSummary.class, esl.getSummary());
				MessageExchange ccExchange = new MessageExchange();
				ccExchange.setResponse(fResponse);
				ccExchange.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);
				List<Contact> contact = registrationDetails.getAccount().getContacts();
				for (Contact con : contact) {
					ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(esl.getEventId(),
							ServiceTypeEnum.FRAUGSTER_SERVICE, ServiceProviderEnum.FRAUGSTER_ONUPDATE_SERVICE, con.getId(),
							con.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(), fOnUpdateResponses, summary,
							esl.getStatus()));
				}
				message.getPayload().appendMessageExchange(ccExchange);
			} catch (Exception e) { // Add try catch - For AT-4355
				LOGGER.error("Error in reading Fraugster on update contact response", e);
			}
		}
	}

	/**
	 * Builds the KYC exchange.
	 *
	 * @param message
	 *            the message
	 * @param eventId
	 *            the event id
	 * @param token
	 *            the token
	 * @param registrationDetails
	 *            the registration details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildKYCExchange(Message<MessageContext> message, Integer eventId, UserProfile token,
			RegistrationServiceRequest registrationDetails) throws ComplianceException {
		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_EID_ELIGIBLE)) {
			ServiceTypeEnum serviceType = ServiceTypeEnum.KYC_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			EventServiceLog esl = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
					registrationDetails.getAccount().getId(), serviceType, registrationDetails, entityType);
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();
			try {
				KYCContactResponse kycContactResponse = JsonConverterUtil.convertToObject(KYCContactResponse.class,
						esl.getProviderResponse());
				KYCSummary summary = JsonConverterUtil.convertToObject(KYCSummary.class, esl.getSummary());
				List<KYCContactResponse> kycContactResponseList = new ArrayList<>();
				kycContactResponseList.add(kycContactResponse);
				kycProviderResponse.setContactResponse(kycContactResponseList);
				MessageExchange ccExchange = new MessageExchange();
				ccExchange.setResponse(kycProviderResponse);
				ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
				List<Contact> contact = registrationDetails.getAccount().getContacts();
				for (Contact con : contact) {
					ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(esl.getEventId(),
							ServiceTypeEnum.KYC_SERVICE, ServiceProviderEnum.KYC_SERVICE, con.getId(), con.getVersion(),
							EntityEnum.CONTACT.name(), token.getUserID(), kycContactResponse, summary, esl.getStatus()));
				}

				message.getPayload().appendMessageExchange(ccExchange);
			}
			catch (Exception e) { // Add try catch - For AT-4355
				LOGGER.error("Error in reading KYC Provider Response", e);
			}
		}

	}

	/**
	 * Builds the internal rule service exchange.
	 *
	 * @param message
	 *            the message
	 * @param eventId
	 *            the event id
	 * @param registrationDetails
	 *            the registration details
	 * @param contactResponse
	 *            the contact response
	 * @param token
	 *            the token
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildInternalRuleServiceExchange(Message<MessageContext> message, Integer eventId,
			RegistrationServiceRequest registrationDetails, ContactResponse contactResponse, UserProfile token)
			throws ComplianceException {
		MessageExchange ccExchange = new MessageExchange();
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> responseList = new ArrayList<>();
		List<Contact> contact = registrationDetails.getAccount().getContacts();

		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)) {
			ServiceTypeEnum serviceTypeofBlacklist = ServiceTypeEnum.BLACK_LIST_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			EventServiceLog eslofBlacklist = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(
					eventId, registrationDetails.getAccount().getId(), serviceTypeofBlacklist, registrationDetails,
					entityType);
			try {
				BlacklistContactResponse blacklistContactResponse = JsonConverterUtil
						.convertToObject(BlacklistContactResponse.class, eslofBlacklist.getProviderResponse());
				contactResponse.setBlacklist(blacklistContactResponse);
			}
			catch (Exception e) { // Add try catch - For AT-4355
				LOGGER.error("Error in reading Blacklist Contact Response", e);
			}
		}
		if (CustomerTypeEnum.PFX.name().equals(registrationDetails.getAccount().getCustType())) {
			buildInternalRuleServiceExchangeForPFX(message, eventId, registrationDetails, contactResponse, ccExchange, responseList, contact);
		}

		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)
				&& !CustomerTypeEnum.PFX.name().equals(registrationDetails.getAccount().getCustType())) {
			buildInternalRuleServiceExchangeForCFX(eventId, registrationDetails, contactResponse, token, ccExchange,
					contact);
		}

		response.setContacts(responseList);
		ccExchange.setResponse(response);
		message.getPayload().appendMessageExchange(ccExchange);
	}

	/**
	 * Builds the internal rule service exchange for CFX.
	 *
	 * @param eventId
	 *            the event id
	 * @param registrationDetails
	 *            the registration details
	 * @param contactResponse
	 *            the contact response
	 * @param token
	 *            the token
	 * @param ccExchange
	 *            the cc exchange
	 * @param contact
	 *            the contact
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildInternalRuleServiceExchangeForCFX(Integer eventId, RegistrationServiceRequest registrationDetails,
			ContactResponse contactResponse, UserProfile token, MessageExchange ccExchange, List<Contact> contact)
			throws ComplianceException {
		ContactResponse contactRes = contactResponse;
		ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
				ServiceProviderEnum.BLACKLIST, registrationDetails.getAccount().getId(),
				registrationDetails.getAccount().getVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(),
				contactRes, createDefaultBlacklistSummary(contactRes.getBlacklist().getStatus()),
				ServiceStatus.valueOf(contactRes.getBlacklist().getStatus())));
		for (Contact con : contact) {
			contactRes = createDefaultCFXContactResponse(
					countryCache.getCountryFullName(null != con.getCountry() ? con.getCountry() : ""), contactRes);

			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
					ServiceTypeEnum.BLACK_LIST_SERVICE, ServiceProviderEnum.BLACKLIST, con.getId(), con.getVersion(),
					EntityEnum.CONTACT.name(), token.getUserID(), contactRes,
					createDefaultBlacklistSummary(contactRes.getBlacklist().getStatus()),
					ServiceStatus.valueOf(contactRes.getBlacklist().getStatus())));

			ccExchange.addEventServiceLog(
					createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
							ServiceProviderEnum.GLOBALCHECK, con.getId(), con.getVersion(), EntityEnum.CONTACT.name(),
							token.getUserID(), contactRes, contactRes.getGlobalCheck(),
							ServiceStatus.valueOf(contactRes.getGlobalCheck().getStatus())));

			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.HRC_SERVICE,
					ServiceProviderEnum.COUNTRYCHECK, con.getId(), con.getVersion(), EntityEnum.CONTACT.name(),
					token.getUserID(), contactRes, contactRes.getCountryCheck(),
					ServiceStatus.valueOf(contactRes.getCountryCheck().getStatus())));
		}
	}

	/**
	 * Builds the internal rule service exchange for PFX.
	 *
	 * @param message            the message
	 * @param eventId            the event id
	 * @param registrationDetails            the registration details
	 * @param contactResponse            the contact response
	 * @param ccExchange            the cc exchange
	 * @param responseList            the response list
	 * @param contact            the contact
	 * @throws ComplianceException             the compliance exception
	 */
	private void buildInternalRuleServiceExchangeForPFX(Message<MessageContext> message, Integer eventId,
			RegistrationServiceRequest registrationDetails, ContactResponse contactResponse, MessageExchange ccExchange, List<ContactResponse> responseList, List<Contact> contact)
			throws ComplianceException {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			ServiceTypeEnum serviceTypeofCountry = ServiceTypeEnum.HRC_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			EventServiceLog eslofCountry = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
					registrationDetails.getAccount().getId(), serviceTypeofCountry, registrationDetails, entityType);
			try {
				CountryCheckContactResponse countryCheckContactResponse = JsonConverterUtil
						.convertToObject(CountryCheckContactResponse.class, eslofCountry.getProviderResponse());
				contactResponse.setCountryCheck(countryCheckContactResponse);
			}catch (Exception e) { // Add try catch - For AT-4355
				LOGGER.error("Error in reading Country Check Contact Response", e);
			}
			message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(COUNTRY_CHECK_STATUS,
					eslofCountry.getStatus());
		}

		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_IP_CHECK_ELIGIBLE)) {
			ServiceTypeEnum serviceTypeofIp = ServiceTypeEnum.IP_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			EventServiceLog eslOfIp = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
					registrationDetails.getAccount().getId(), serviceTypeofIp, registrationDetails, entityType);
			try {
				IpContactResponse ipContactResponse = JsonConverterUtil.convertToObject(IpContactResponse.class,
						eslOfIp.getProviderResponse());
				contactResponse.setIpCheck(ipContactResponse);
			}catch (Exception e) {
				LOGGER.error("Error in reading Ip Contact Response", e);
			}
			message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(IP_CHECK_STATUS,
					eslOfIp.getStatus());
		}

		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_GLOBAL_CHECK_ELIGIBLE)) {
			ServiceTypeEnum serviceTypeofGlobalCheck = ServiceTypeEnum.GLOBAL_CHECK_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			EventServiceLog eslofGlobalCheck = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(
					eventId, registrationDetails.getAccount().getId(), serviceTypeofGlobalCheck, registrationDetails,
					entityType);
			try {
				GlobalCheckContactResponse globalCheckContactResponse = JsonConverterUtil
						.convertToObject(GlobalCheckContactResponse.class, eslofGlobalCheck.getProviderResponse());
				contactResponse.setGlobalCheck(globalCheckContactResponse);
			}catch (Exception e) { // Add try catch - For AT-4355
				LOGGER.error("Error in reading Global Check Contact Response", e);
			}
			message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(GLOBAL_CHECK_STATUS,
					eslofGlobalCheck.getStatus());
		}
		for (Contact con : contact) {
			contactResponse.setId(con.getId());
			responseList.add(contactResponse);
			if (null == contactResponse.getBlacklist()) {
				BlacklistContactResponse blacklistresponse = new BlacklistContactResponse();
				blacklistresponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
				contactResponse.setBlacklist(blacklistresponse);
			}
			if (null == contactResponse.getIpCheck()) {
				IpContactResponse ipResponse = new IpContactResponse();
				ipResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
				contactResponse.setIpCheck(ipResponse);
			}
			try {
				populateAllEventServiceLogs(token, ccExchange, eventId, contactResponse,
						createDefaultBlacklistSummary(contactResponse.getBlacklist().getStatus()),
						createDefaultIpSummary(contactResponse.getIpCheck().getStatus(), con.getIpAddress()), con);
			}catch (Exception e) { // Add try catch - For AT-4355
				LOGGER.error("Error in populate all EventServiceLogs", e);
			}
		}
	}

	/**
	 * Creates the event service log entry with status.
	 *
	 * @param eventId
	 *            the event id
	 * @param servceType
	 *            the servce type
	 * @param providerEnum
	 *            the provider enum
	 * @param entityID
	 *            the entity ID
	 * @param entityVersion
	 *            the entity version
	 * @param entityType
	 *            the entity type
	 * @param user
	 *            the user
	 * @param providerResponse
	 *            the provider response
	 * @param summary
	 *            the summary
	 * @param serviceStatus
	 *            the service status
	 * @return the event service log
	 */
	@SuppressWarnings("squid:S00107")
	protected EventServiceLog createEventServiceLogEntryWithStatus(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary, String serviceStatus) {

		EventServiceLog eventServiceLog = new EventServiceLog();
		eventServiceLog.setServiceName(servceType.getShortName());
		eventServiceLog.setServiceProviderName(providerEnum.getProvidername());
		eventServiceLog.setEntityId(entityID);
		eventServiceLog.setEventId(eventId);
		eventServiceLog.setEntityVersion(entityVersion);
		eventServiceLog.setEntityType(entityType);
		eventServiceLog.setStatus(serviceStatus);
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setCratedBy(user);
		eventServiceLog.setUpdatedBy(user);
		eventServiceLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		return eventServiceLog;
	}

	/**
	 * Builds the sanction exchange.
	 *
	 * @param message
	 *            the message
	 * @param eventId
	 *            the event id
	 * @param sanctionResponse
	 *            the sanction response
	 * @param token
	 *            the token
	 * @param sanctionEntityLogList
	 *            the sanction entity log list
	 * @param registrationDetails
	 *            the registration details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildSanctionExchangeForPFX(Message<MessageContext> message, Integer eventId,
			SanctionResponse sanctionResponse, UserProfile token, List<EventServiceLog> sanctionEntityLogList,
			RegistrationServiceRequest registrationDetails) throws ComplianceException {
		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_SANCTION_ELIGIBLE_FOR_RECHECK)) {
			ServiceTypeEnum serviceType = ServiceTypeEnum.SANCTION_SERVICE;
			Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
			Integer contactId = (Integer) registrationDetails.getAdditionalAttribute("contactId");
			EventServiceLog esl = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
					registrationDetails.getAccount().getId(), serviceType, registrationDetails, entityType);

			for (Contact con : registrationDetails.getAccount().getContacts()) {
				if (null != contactId && contactId.equals(con.getId())) {
					getSanctionContactResponse(esl, sanctionResponse, token, con);
					sanctionEntityLogList.add((EventServiceLog) sanctionResponse
							.getAdditionalAttribute(CONTACT_LOG_FOR_CONTACT + con.getId()));
				}
			}

			createPreviousSanctionExchange(message, sanctionResponse, sanctionEntityLogList);
		}
	}

	/**
	 * Builds the sanction exchange for CFX.
	 *
	 * @param message
	 *            the message
	 * @param eventId
	 *            the event id
	 * @param sanctionResponse
	 *            the sanction response
	 * @param token
	 *            the token
	 * @param sanctionEntityLogList
	 *            the sanction entity log list
	 * @param registrationDetails
	 *            the registration details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void buildSanctionExchangeForCFX(Message<MessageContext> message, Integer eventId,
			SanctionResponse sanctionResponse, UserProfile token, List<EventServiceLog> sanctionEntityLogList,
			RegistrationServiceRequest registrationDetails) throws ComplianceException {

		ServiceTypeEnum serviceType = ServiceTypeEnum.SANCTION_SERVICE;
		Integer entityType = EntityEnum.CONTACT.getEntityTypeAsInteger();
		EventServiceLog esl = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
				registrationDetails.getAccount().getId(), serviceType, registrationDetails, entityType);

		for (Contact con : registrationDetails.getAccount().getContacts()) {
			getSanctionContactResponse(esl, sanctionResponse, token, con);
			sanctionEntityLogList.add(
					(EventServiceLog) sanctionResponse.getAdditionalAttribute(CONTACT_LOG_FOR_CONTACT + con.getId()));
		}

		if (!(boolean) registrationDetails.getAdditionalAttribute(IS_SANCTION_ELIGIBLE_FOR_RECHECK)) {
			Integer entityTypeForAcc = EntityEnum.ACCOUNT.getEntityTypeAsInteger();
			EventServiceLog eslForAcc = newRegistrationDBServiceImpl.getServiceFailedRegistrationESLDetails(eventId,
					registrationDetails.getAccount().getId(), serviceType, registrationDetails, entityTypeForAcc);
			getSanctionAccountResponse(eslForAcc, sanctionResponse, token, registrationDetails);
			sanctionEntityLogList
					.add((EventServiceLog) sanctionResponse.getAdditionalAttribute("contactLogForAccount"));

			createPreviousSanctionExchange(message, sanctionResponse, sanctionEntityLogList);
		}
	}

	/**
	 * Creates the previous sanction exchange.
	 *
	 * @param message
	 *            the message
	 * @param sanctionResponse
	 *            the sanction response
	 * @param sanctionEntityLogList
	 *            the sanction entity log list
	 */
	private void createPreviousSanctionExchange(Message<MessageContext> message, SanctionResponse sanctionResponse,
			List<EventServiceLog> sanctionEntityLogList) {
		MessageExchange ccExchange = new MessageExchange();
		ccExchange.setResponse(sanctionResponse);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
		for (EventServiceLog sanctionLog : sanctionEntityLogList) {
			if (null != sanctionLog)
				ccExchange.addEventServiceLog(sanctionLog);
		}
		message.getPayload().appendMessageExchange(ccExchange);
	}

	/**
	 * Gets the sanction contact response.
	 *
	 * @param esl
	 *            the esl
	 * @param sanctionResponse
	 *            the sanction response
	 * @param token
	 *            the token
	 * @param con
	 *            the con
	 * @return the sanction contact response
	 */
	private EventServiceLog getSanctionContactResponse(EventServiceLog esl, SanctionResponse sanctionResponse,
			UserProfile token, Contact con) {
		if ("CONTACT".equalsIgnoreCase(esl.getEntityType())) {
			List<SanctionContactResponse> sanctionContactResponseList = new ArrayList<>();
			SanctionContactResponse sanctionContactResponse = JsonConverterUtil
					.convertToObject(SanctionContactResponse.class, esl.getProviderResponse());
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
					esl.getSummary());
			sanctionContactResponseList.add(sanctionContactResponse);
			sanctionResponse.setContactResponses(sanctionContactResponseList);
			EventServiceLog contactLogForContact = createEventServiceLogEntryWithStatus(esl.getEventId(),
					ServiceTypeEnum.SANCTION_SERVICE, ServiceProviderEnum.SANCTION_SERVICE, con.getId(),
					con.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(), sanctionContactResponse,
					sanctionSummary, esl.getStatus());
			sanctionResponse.addAttribute(CONTACT_LOG_FOR_CONTACT + con.getId(), contactLogForContact);
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.addEventServiceLog(contactLogForContact);
			return contactLogForContact;
		}
		return null;
	}

	/**
	 * Gets the sanction account response.
	 *
	 * @param esl
	 *            the esl
	 * @param sanctionResponse
	 *            the sanction response
	 * @param token
	 *            the token
	 * @param registrationDetails
	 *            the registration details
	 * @return the sanction account response
	 */
	private EventServiceLog getSanctionAccountResponse(EventServiceLog esl, SanctionResponse sanctionResponse,
			UserProfile token, RegistrationServiceRequest registrationDetails) {
		if ("ACCOUNT".equalsIgnoreCase(esl.getEntityType())) {
			List<SanctionContactResponse> sanctionContactResponseList = new ArrayList<>();
			SanctionContactResponse sanctionContactResponse = JsonConverterUtil
					.convertToObject(SanctionContactResponse.class, esl.getProviderResponse());
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
					esl.getSummary());
			sanctionContactResponseList.add(sanctionContactResponse);
			sanctionResponse.setContactResponses(sanctionContactResponseList);
			Account account = registrationDetails.getAccount();
			EventServiceLog contactLogForAccount = createEventServiceLogEntryWithStatus(esl.getEventId(),
					ServiceTypeEnum.SANCTION_SERVICE, ServiceProviderEnum.SANCTION_SERVICE, account.getId(),
					account.getVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(), sanctionContactResponse,
					sanctionSummary, esl.getStatus());
			sanctionResponse.addAttribute("contactLogForAccount", contactLogForAccount);
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.addEventServiceLog(contactLogForAccount);
			return contactLogForAccount;
		}
		return null;
	}

	/**
	 * Populate all event service logs.
	 *
	 * @param token
	 *            the token
	 * @param exchange
	 *            the exchange
	 * @param eventId
	 *            the event id
	 * @param response
	 *            the response
	 * @param blacklistSummary
	 *            the blacklist summary
	 * @param ip
	 *            the ip
	 * @param contact
	 *            the contact
	 */
	private void populateAllEventServiceLogs(UserProfile token, MessageExchange exchange, Integer eventId,
			ContactResponse response, BlacklistSummary blacklistSummary, IpSummary ip, Contact contact) {
		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
				ServiceProviderEnum.BLACKLIST, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, blacklistSummary,
				ServiceStatus.valueOf(response.getBlacklist().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.IP_SERVICE,
				ServiceProviderEnum.IP, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, ip, ServiceStatus.valueOf(response.getIpCheck().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
				ServiceProviderEnum.GLOBALCHECK, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, response.getGlobalCheck(),
				ServiceStatus.valueOf(response.getGlobalCheck().getStatus())));

		exchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.HRC_SERVICE,
				ServiceProviderEnum.COUNTRYCHECK, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), response, response.getCountryCheck(),
				ServiceStatus.valueOf(response.getCountryCheck().getStatus())));
	}

	/**
	 * Creates the event service log entry with status.
	 *
	 * @param eventId
	 *            the event id
	 * @param servceType
	 *            the servce type
	 * @param providerEnum
	 *            the provider enum
	 * @param entityID
	 *            the entity ID
	 * @param entityVersion
	 *            the entity version
	 * @param entityType
	 *            the entity type
	 * @param user
	 *            the user
	 * @param providerResponse
	 *            the provider response
	 * @param summary
	 *            the summary
	 * @param serviceStatus
	 *            the service status
	 * @return the event service log
	 */
	@SuppressWarnings("squid:S00107")
	private EventServiceLog createEventServiceLogEntryWithStatus(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary, ServiceStatus serviceStatus) {

		ServiceStatus newStatus;
		if (null == serviceStatus)
			newStatus = ServiceStatus.NOT_PERFORMED;
		else
			newStatus = serviceStatus;

		EventServiceLog eventServiceLog = new EventServiceLog();
		eventServiceLog.setServiceName(servceType.getShortName());
		eventServiceLog.setServiceProviderName(providerEnum.getProvidername());
		eventServiceLog.setEntityId(entityID);
		eventServiceLog.setEventId(eventId);
		eventServiceLog.setEntityVersion(entityVersion);
		eventServiceLog.setEntityType(entityType);
		eventServiceLog.setStatus(newStatus.name());
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setCratedBy(user);
		eventServiceLog.setUpdatedBy(user);
		eventServiceLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		return eventServiceLog;
	}

	/**
	 * Creates the default blacklist summary.
	 *
	 * @param status
	 *            the status
	 * @return the blacklist summary
	 */
	private BlacklistSummary createDefaultBlacklistSummary(String status) {
		BlacklistSummary blacklistSummary = new BlacklistSummary();
		blacklistSummary.setStatus(status);
		return blacklistSummary;
	}

	/**
	 * Creates the default ip summary.
	 *
	 * @param status
	 *            the status
	 * @param ipAddress
	 *            the ip address
	 * @return the ip summary
	 */
	private IpSummary createDefaultIpSummary(String status, String ipAddress) {
		IpSummary ip = new IpSummary();
		ip.setIpAddress(ipAddress);
		ip.setStatus(status);
		return ip;
	}

	/**
	 * Creates the default blacklist response.
	 *
	 * @param string
	 *            the string
	 * @return the blacklist contact response
	 */
	private BlacklistContactResponse createDefaultBlacklistResponse(String string) {
		BlacklistContactResponse blacklist = new BlacklistContactResponse();
		blacklist.setStatus(string);
		blacklist.setData(null);
		return blacklist;
	}

	/**
	 * Creates the default CFX contact response.
	 *
	 * @param countryName
	 *            the country name
	 * @param contactResponse
	 *            the contact response
	 * @return the contact response
	 */
	private ContactResponse createDefaultCFXContactResponse(String countryName, ContactResponse contactResponse) {
		ContactResponse response = new ContactResponse();
		response.setBlacklist(createDefaultBlacklistResponse(contactResponse.getBlacklist().getStatus()));
		response.setGlobalCheck(createDefaultGlobalCheckResponse(ServiceStatus.NOT_REQUIRED, countryName));
		response.setCountryCheck(createDefaultCountryCheckResponse(ServiceStatus.NOT_REQUIRED, countryName));
		return response;
	}

	/**
	 * Creates the default global check response.
	 *
	 * @param status
	 *            the status
	 * @param country
	 *            the country
	 * @return the global check contact response
	 */
	private GlobalCheckContactResponse createDefaultGlobalCheckResponse(ServiceStatus status, String country) {
		GlobalCheckContactResponse globalCheck = new GlobalCheckContactResponse();
		globalCheck.setStatus(status.name());
		globalCheck.setCountry(country);
		return globalCheck;
	}

	/**
	 * Creates the default country check response.
	 *
	 * @param status
	 *            the status
	 * @param country
	 *            the country
	 * @return the country check contact response
	 */
	private CountryCheckContactResponse createDefaultCountryCheckResponse(ServiceStatus status, String country) {
		CountryCheckContactResponse countryCheck = new CountryCheckContactResponse();
		countryCheck.setStatus(status.name());
		countryCheck.setCountry(country);
		return countryCheck;
	}

	/**
	 * Gets the address fields to send email 1.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @return the address fields to send email 1
	 */
	private static boolean getAddressFieldsToSendEmail1(String strFieldsModified) {
		boolean result;
		boolean address1 = strFieldsModified.contains(ADDRESS_TYPE) || strFieldsModified.contains(AREA_NUMBER)
				|| strFieldsModified.contains(AZA) || strFieldsModified.contains(BUILDING_NAME)
				|| strFieldsModified.contains(BUILDING_NUMBER);

		boolean address2 = strFieldsModified.contains(CIVIC_NUMBER) || strFieldsModified.contains(COUNTRY)
				|| strFieldsModified.contains(DISTRICT) || strFieldsModified.contains(FLOOR_NUMBER);

		result = getAddressFieldsToSendEmail2(strFieldsModified, address1, address2);
		return result;

	}

	/**
	 * Gets the address fields to send email 2.
	 *
	 * @param strFieldsModified
	 *            the str fields modified
	 * @param address1
	 *            the address 1
	 * @param address2
	 *            the address 2
	 * @return the address fields to send email 2
	 */
	private static boolean getAddressFieldsToSendEmail2(String strFieldsModified, boolean address1, boolean address2) {

		boolean address3 = strFieldsModified.contains(POST_CODE) || strFieldsModified.contains(PREFECTURE)
				|| strFieldsModified.contains(REGION_SUBURB) || strFieldsModified.contains(STATE_PROVINCE_COUNTY);

		boolean address4 = strFieldsModified.contains(STREET) || strFieldsModified.contains(STREET_NUMBER)
				|| strFieldsModified.contains(STREET_TYPE) || strFieldsModified.contains(SUB_BUILDINGOR_FLAT);

		boolean address5 = strFieldsModified.contains(SUB_CITY) || strFieldsModified.contains(CITY)
				|| strFieldsModified.contains(UNIT_NUMBER) || strFieldsModified.contains(YEARS_IN_ADDRESS);

		boolean isaddressChanged1 = address1 || address2;
		boolean isaddressChanged2 = address3 || address4;

		return (isaddressChanged1 || isaddressChanged2 || address5);
	}
	
	/**
	 * Gets the other changed fields except address.
	 *
	 * @param strFieldsModified the str fields modified
	 * @return the other changed fields except address
	 */
	private boolean getChangedFieldsExceptAddress(String strFieldsModified) {
		boolean result = false;
		result = isNameChanged(strFieldsModified, result);
		if (result || strFieldsModified.contains(DOB) || strFieldsModified.contains(GENDER))
			result = true;
		
		return result;
	}
	
	/**
	 * AT-1725 requirement
	 * Not to update TITAN ID of Account and Contact
	 */
	private void setFieldsNotToUpdate(RegistrationServiceRequest request, Account oldAccount, List<Contact> oldContacts, Message<MessageContext> message) {
		//AT-4474
		if(("CD SA").equalsIgnoreCase(request.getOrgCode()) && !request.getAccount().getTradeAccountNumber().equals(oldAccount.getTradeAccountNumber())) {
				setValuesForCDSAMigratedCustomer(request, oldAccount, oldContacts, message);
		}
		else {
			request.getAccount().setTradeAccountID(oldAccount.getTradeAccountID());
			request.getAccount().setTradeAccountNumber(oldAccount.getTradeAccountNumber());
			for(Contact contact : request.getAccount().getContacts()) {
				Contact oldContact = getContactBySFId(contact.getContactSFID(), oldContacts);
				if(null != oldContact)
					contact.setTradeContactID(oldContact.getTradeContactID());
			}
		}
	}

	/**
	 * @param request
	 * @param oldAccount
	 * @param oldContacts
	 * @param message
	 */
	private void setValuesForCDSAMigratedCustomer(RegistrationServiceRequest request, Account oldAccount,
			List<Contact> oldContacts, Message<MessageContext> message) {
		request.getAccount().setLegacyTradeAccountNumber(oldAccount.getTradeAccountNumber());
		request.getAccount().setLegacyTradeAccountID(oldAccount.getTradeAccountID());
		for(Contact contact : request.getAccount().getContacts()) {
			Contact oldContact = getContactBySFId(contact.getContactSFID(), oldContacts);
			if(null != oldContact)
				contact.setLegacyTradeContactID(oldContact.getTradeContactID());
		}
		if(("migrate_customer").equalsIgnoreCase(request.getEvent())) {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			Integer userID;
			try {
				userID = newRegistrationDBServiceImpl.getUserIDFromSSOUserID("cd.comp.migration");
				token.setUserID(userID);
			} catch (ComplianceException e) {
				LOGGER.error("Exception in setValuesForCDSAMigratedCustomer()", e);
			}
		}
	}
	
	//AT-3241 Amend FraudPredict re-trigger process to include only email, mobile and landline changes
	private static boolean isFraudPredictEligible(String strFieldsModified) {
		boolean result = false;
		if (strFieldsModified.contains("email,") || strFieldsModified.contains("phoneMobile,") || strFieldsModified.contains("phoneHome,"))
			result = true;
		return result;
	}
}
