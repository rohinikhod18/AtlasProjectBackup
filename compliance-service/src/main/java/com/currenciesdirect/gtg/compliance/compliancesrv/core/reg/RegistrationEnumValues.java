/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;

/**
 * @author manish
 *
 */
public class RegistrationEnumValues {
	private static final String ERROR = "Error";
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationEnumValues.class);
	private static RegistrationEnumValues enumValues = null;
	
	private List<String> addressTypes = new ArrayList<>();
	private List<String> residentialStatuses = new ArrayList<>();
	private List<String> organizations = new ArrayList<>();
	private List<String> purposeOfTransactions = new ArrayList<>();
	private List<String> titles = new ArrayList<>();
	private List<String> registrationModes = new ArrayList<>();
	private List<String> primaryPhones = new ArrayList<>();
	private List<String> updateMethods = new ArrayList<>();
	private List<String> genders = new ArrayList<>();
	private List<String> channels = new ArrayList<>();
	private List<String> customerTypes = new ArrayList<>();
	private List<String> transactionValues = new ArrayList<>();
	private List<String> source = new ArrayList<>();
	private List<String> usaStates = new ArrayList<>();
	
	@Autowired
	private NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;
	private RegistrationEnumValues(){
		addressTypes.add("Current Address");
		addressTypes.add("Primary");
		addressTypes.add("Billing");
		addressTypes.add("Previous Address 1");
		addressTypes.add("Registered");
		addressTypes.add("Temporary Address");
		addressTypes.add("Work Address");
		
		residentialStatuses.add("SA Resident");
		residentialStatuses.add("SA Resident Temporary Abroad");
		residentialStatuses.add("Non-Resident");
		residentialStatuses.add("Foreign National");
		residentialStatuses.add("Emigrant");
		residentialStatuses.add("Immigrant");
		
		organizations.add("Currencies Direct");
		organizations.add("TorFX");
		organizations.add("TorFXOz");
		organizations.add("CD SA");
		
		purposeOfTransactions.add("Buying Car/Boat/Plane");
		purposeOfTransactions.add("Buying Products/Services");
		purposeOfTransactions.add("Emigration");
		purposeOfTransactions.add("Sending to Family");
		purposeOfTransactions.add("Living Expenses");
		purposeOfTransactions.add("Making an Investment");
		purposeOfTransactions.add("Paying Mortgage/Bills");
		purposeOfTransactions.add("Property Maintenance/Build");
		purposeOfTransactions.add("Property Purchase");
		purposeOfTransactions.add("Other");
		purposeOfTransactions.add("MigrationDefault");
		purposeOfTransactions.add("Art / Antiques");
		purposeOfTransactions.add("Business invoices");
		purposeOfTransactions.add("Holiday");
		purposeOfTransactions.add("Inheritance");
		purposeOfTransactions.add("Land");
		purposeOfTransactions.add("Mortgage");
		purposeOfTransactions.add("Payment from customer (corporate)");
		purposeOfTransactions.add("Pension");
		purposeOfTransactions.add("Sale of investment");
		purposeOfTransactions.add("Single Farm Payment");
		purposeOfTransactions.add("Cash");
		purposeOfTransactions.add("Charitable donation");
		purposeOfTransactions.add("Fund reversal");
		purposeOfTransactions.add("Prop sale");
		purposeOfTransactions.add("Salary");
		purposeOfTransactions.add("Study fees");
		purposeOfTransactions.add("Travel expenses");
		purposeOfTransactions.add("Treasury");
		purposeOfTransactions.add("Yacht");
		purposeOfTransactions.add("Bill payments");
		purposeOfTransactions.add("Building / repairs");
		purposeOfTransactions.add("Freight");
		purposeOfTransactions.add("Living Funds");
		purposeOfTransactions.add("Others");

		titles.add("Alcalde");
		titles.add("Captain");
		titles.add("Doctor");
		titles.add("Dottore");
		titles.add("Dr");
		titles.add("Lady");
		titles.add("Lord");
		
		titles.add("Father");
		titles.add("Senor");
		titles.add("Senora");
		titles.add("Senorita");
		titles.add("Mother");
		titles.add("Dr (Male)");
		titles.add("Professor (Male)");
		
		titles.add("Madame");
		titles.add("Mademoiselle");
		titles.add("Major");
		titles.add("Miss");
		titles.add("Mr");
		titles.add("Mr & Mme");
		titles.add("Mr & Mrs");
		titles.add("Mr & Ms");
		titles.add("Mr et Mme");
		titles.add("Mrs");
		titles.add("Ms");
		titles.add("Padre");
		titles.add("Père");
		titles.add("Profesor");
		titles.add("Professeur");
		titles.add("Professor");
		titles.add("Professore");
		titles.add("Rev");
		titles.add("Révérend");
		titles.add("Reverendo");
		titles.add("Senhor");
		titles.add("Senhora");
		titles.add("Senhor e Senhorita");
		titles.add("Senhorita");
		titles.add("Señor");
		titles.add("Señora");
		titles.add("Señorita");
		titles.add("Señor y Señora");
		titles.add("Señor y Señorita");
		titles.add("Sig.na");
		titles.add("Sig.ra");
		titles.add("Sig & Sig na");
		titles.add("Sig & Sig ra");
		titles.add("Signor");
		titles.add("Signora");
		titles.add("Signore");
		titles.add("Signorina");
		titles.add("Sir");
		titles.add("Sr");
		titles.add("Sr e Sra");
		titles.add("Dr (Male)");
		titles.add("Dr (Female)");
		titles.add("Professor (Male)");
		titles.add("Professor (Female)");
		titles.add("Prof"); //Add for AT-5055
		
		registrationModes.add("Fax");
		registrationModes.add("Internet");
		registrationModes.add("Post");
		registrationModes.add("InPerson");
		
		primaryPhones.add("Mobile");
		primaryPhones.add("Home");
		primaryPhones.add("Work");
		
		updateMethods.add("MANUAL");
		updateMethods.add("ONLINE");
		
		genders.add("Male");
		genders.add("Female");
		
		channels.add("Website");
		channels.add("Internal");
		
		customerTypes.add("CFX");
		customerTypes.add("PFX");
		customerTypes.add("Affiliate");
		
		transactionValues.add("Under 2,000");
		transactionValues.add("2,000 - 5,000");
		transactionValues.add("2,000 - 10,000"); 
		transactionValues.add("5,000 - 10,000");
		transactionValues.add("10,000 - 25,000");
		transactionValues.add("25,000 - 50,000");
		transactionValues.add("50,000 - 100,000");
		transactionValues.add("100,000 - 250,000");
		transactionValues.add("over 250,000");
		//Add for AT-4789
		transactionValues.add("Under 100,000");
		transactionValues.add("100,000 - 1,000,000");
		transactionValues.add("1,000,000 - 10,000,000"); 
		transactionValues.add("over 10,000,000");
		
		source.add("Affiliate");
		source.add("Billboards");
		source.add("Book");
		source.add("Charity");
		source.add("Direct Mail - Post / Mail");
		source.add("Email campaign");
		source.add("Exhibitions & Events");
		source.add("Internet");
		source.add("List");
		source.add("Magazine");
		source.add("Networking");
		source.add("Newspaper");
		source.add("Other");
		source.add("Press");
		source.add("Radio");
		source.add("Referral");
		source.add("SelfGen");
		source.add("Outdoor advertising");
		source.add("Self Generated");
		source.add("Show");
		source.add("Sponsorship");
		source.add("TV");
		source.add("Web");
		
		usaStates.add("Georgia"); 
		usaStates.add("Florida");
		usaStates.add("Montana"); 
		usaStates.add("New Mexico"); 
		usaStates.add("South Carolina"); 
		usaStates.add("Wisconsin"); 
		usaStates.add("North Carolina");
		usaStates.add("Alabama"); 
		usaStates.add("California");
		usaStates.add("Connecticut");
		usaStates.add("Delaware"); 
		usaStates.add("Hawaii"); 
		usaStates.add("Idaho");
		usaStates.add("Indiana");
		usaStates.add("Kansas"); 
		usaStates.add("Kentucky");
		usaStates.add("Louisiana"); 
		usaStates.add("Maryland");
		usaStates.add("Massachusetts");
		usaStates.add("Michigan");
		usaStates.add("Minnesota");
		usaStates.add("Mississippi");
		usaStates.add("Missouri");
		usaStates.add("Nebraska"); 
		usaStates.add("Nevada");
		usaStates.add("New Hampshire"); 
		usaStates.add("New Jersey");
		usaStates.add("New York");
		usaStates.add("Puerto Rico");
		usaStates.add("North Dakota");
		usaStates.add("Oregon"); 
		usaStates.add("South Dakota");
		usaStates.add("Tennessee"); 
		usaStates.add("Utah");
		usaStates.add("Virginia");
		usaStates.add("Wyoming"); 
		usaStates.add("District of Columbia");
		usaStates.add("American Samoa");
		usaStates.add("Arkansas");
		usaStates.add("Alaska");
		usaStates.add("Arizona"); 
		usaStates.add("Colorado"); 
		usaStates.add("Illinois");
		usaStates.add("Iowa");
		usaStates.add("Maine");
		usaStates.add("Ohio");
		usaStates.add("Oklahoma");
		usaStates.add("Rhode Island");
		usaStates.add("Texas");
		usaStates.add("Vermont");
		usaStates.add("Washington");
		usaStates.add("West Virginia"); 
		usaStates.add("Guam");
		usaStates.add("Northern Mariana Islands");
		usaStates.add("U.S. Virgin Islands");
		usaStates.add("Pennsylvania");
		
	}
	
	public static RegistrationEnumValues getInstance() {
		if(enumValues==null){
			enumValues = new RegistrationEnumValues();
		}
		return enumValues;
	}
	
	/**
	 * Check titles.
	 *
	 * @param title the title
	 * @return true, if successful
	 */
	public String checkTitles(String title) {
		try {
			if (title == null || title.isEmpty()) {
				return null;
			}
				
			for (String Title : titles) {
				if (title.equalsIgnoreCase(Title)) {
					return title;
				}
			}
		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return null;
	}
	
	/**
	 * Check purrpose of transaction.
	 *
	 * @param purposeofTransaction the purposeof transaction
	 * @return true, if successful
	 */
	public boolean checkPurrposeOfTransaction(String purposeofTransaction){
		try{
			for (String PurposeOfTransactions : purposeOfTransactions) {
				if(purposeofTransaction.equalsIgnoreCase(PurposeOfTransactions)){
					return true;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return false;
	}
	
	/**
	 * Check usa states.
	 *
	 * @param state the state
	 * @return true, if successful
	 */
	public String checkUsaStates(String state){
		try{
			for (String states : usaStates) {
				if(state.equalsIgnoreCase(states)){
					return state;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	/**
	 * Check organizations.
	 *
	 * @param organization the organization
	 * @return true, if successful
	 */
	public boolean checkOrganizations(String organization){
		try{
			for (String Organization : organizations) {
				if(organization.equalsIgnoreCase(Organization)){
					return true;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return false;
	}
	
	/**
	 * Check residential statuses.
	 *
	 * @param residentialStatus the residential status
	 * @return true, if successful
	 */
	public boolean checkResidentialStatuses(String residentialStatus){
		try{
			for (String ResidentialStatus : residentialStatuses) {
				if(residentialStatus.equalsIgnoreCase(ResidentialStatus)){
					return true;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return false;
	}
	
	/**
	 * Check address types.
	 *
	 * @param addressType the address type
	 * @return true, if successful
	 */
	public String checkAddressTypes(String addressType) {
		try {
			if (addressType == null || addressType.isEmpty()) {
				return null;
			}
				for (String AddressType : addressTypes) {
					if (addressType.equalsIgnoreCase(AddressType)) {
						return addressType;
					}
				}
		
		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return null;
	}
	
	
	/**
	 * Check registration modes.
	 *
	 * @param registrationMode the registration mode
	 * @return the string
	 */
	public String checkRegistrationModes(String registrationMode){
		try{
			for (String RegistrationMode : registrationModes) {
				if(registrationMode.equalsIgnoreCase(RegistrationMode)){
					return registrationMode;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	/**
	 * Check primary phones.
	 *
	 * @param primaryPhone the primary phone
	 * @return the string
	 */
	public String checkPrimaryPhones(String primaryPhone){
		try{
			for (String PrimaryPhone : primaryPhones) {
				if(primaryPhone.equalsIgnoreCase(PrimaryPhone)){
					return primaryPhone;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	/**
	 * Check update methods.
	 *
	 * @param updateMethod the update method
	 * @return the string
	 */
	public String checkUpdateMethods(String updateMethod){
		try{
			for (String UpdateMethod : updateMethods) {
				if(updateMethod.equalsIgnoreCase(UpdateMethod)){
					return updateMethod;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	/**
	 * Check gender.
	 *
	 * @param gender the gender
	 * @return the string
	 */
	public String checkGender(String gender) {
		try {
			if (gender == null || gender.isEmpty()) {
				return null;
			}
				for (String Gender : genders) {
					if (gender.equalsIgnoreCase(Gender)) {
						return gender;
					}
				}
			
			return gender;
		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return null;
	}
	
	/**
	 * Check channels.
	 *
	 * @param channel the channel
	 * @return the string
	 */
	public String checkChannels(String channel){
		try{
			for (String Channel : channels) {
				if(channel.equalsIgnoreCase(Channel)){
					return channel;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	
	/**
	 * Check customer types.
	 *
	 * @param customerType the customer type
	 * @return the string
	 */
	public String checkCustomerTypes(String customerType){
		try{
			for (String CustomerType : customerTypes) {
				if(customerType.equalsIgnoreCase(CustomerType)){
					return customerType;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	/**
	 * Check country enum.
	 *
	 * @param country the country
	 * @return the string
	 */
	public String checkCountryEnum(String country){
		try {
			if(Boolean.TRUE.equals(newRegistrationDBServiceImpl.checkCountryEnum(country.trim()))){
				return country;
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return null;
	}
	
	/**
	 * Check transaction values.
	 *
	 * @param transactionValue the transaction value
	 * @return the string
	 */
	public String checkTransactionValues(String transactionValue) {
		try {
			for (String value : transactionValues) {
				if (transactionValue.equalsIgnoreCase(value)) {
					return transactionValue;
				}
			}
		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return null;
	}

	/**
	 * Check source.
	 *
	 * @param sources the sources
	 * @return the string
	 */
	public String checkSource(String sources) {
		try {
			for (String value : source) {
				if (sources.equalsIgnoreCase(value)) {
					return sources;
				}
			}
		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return null;
	}
}
