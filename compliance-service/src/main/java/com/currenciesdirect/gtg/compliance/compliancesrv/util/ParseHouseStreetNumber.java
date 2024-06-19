package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;

/**
 * The Class ParseHouseStreetNumber.
 */
public class ParseHouseStreetNumber {

	/** The start with number. */
	private static Pattern startWithNumber = Pattern.compile(
			"\\A([a-zA-Z]\\d*|\\d+[a-zA-Z]{0,1}\\s{0,1}[-]{1}\\s{0,1}\\d*[a-zA-Z]{0,1}|\\d+[a-zA-Z]{0,1}\\s{0,1}[/]{1}\\s{0,1}\\d*[a-zA-Z]{0,1}|\\d+[a-zA-Z-]{0,1}\\d*[a-zA-Z]{0,1})\\s*+(.*)");

	/** The number. */
	private static Pattern number = Pattern.compile("[0-9]");
	
	/** The replace regex. */
	private static String replaceRegex = "(Appt|Apartment|Building|Build|Bldg|Bldg.|Apartment No.)";
	
	/** The street. */
	private String street;
	
	/** The house street building number. */
	private String houseStreetBuildingNumber;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParseHouseStreetNumber.class);
	
	/** The parse house street number. */
	private static ParseHouseStreetNumber parseHouseStreetNumber = null;
	
	/**
	 * Instantiates a new parses the house street number.
	 */
	private ParseHouseStreetNumber() {
		
	}
	
	/**
	 * Gets the single instance of ParseHouseStreetNumber.
	 *
	 * @return single instance of ParseHouseStreetNumber
	 */
	public static ParseHouseStreetNumber getInstance(){
		if(null==parseHouseStreetNumber){
			parseHouseStreetNumber = new ParseHouseStreetNumber();
		}
		return parseHouseStreetNumber;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		if(street != null){
			this.street = street.replace(":", "");
		}
	}

	/**
	 * Gets the house street building number.
	 *
	 * @return the house street building number
	 */
	public String getHouseStreetBuildingNumber() {
		return houseStreetBuildingNumber;
	}

	/**
	 * Sets the house street building number.
	 *
	 * @param houseStreetBuildingNumber the new house street building number
	 */
	public void setHouseStreetBuildingNumber(String houseStreetBuildingNumber) {
		this.houseStreetBuildingNumber = houseStreetBuildingNumber;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParseHouseStreetNumber [street=" + street + ", houseStreetBuildingNumber=" + houseStreetBuildingNumber
				+ "]";
	}

	/**
	 * Parses the house street number.
	 *
	 * @param originalAddressLine the original address line
	 * @return the parses the house street number
	 */
	public ParseHouseStreetNumber parseHouseStreetNumber(String originalAddressLine) {

		if (originalAddressLine == null || originalAddressLine.trim().length() == 0)
			return null;
		String address = originalAddressLine.replaceAll(replaceRegex, "");
		
		ParseHouseStreetNumber parsedHouseStreetNumber = new ParseHouseStreetNumber();
		parsedHouseStreetNumber.setStreet(address);

		String[] addressLine = address.trim().split("\\r?\\n");
		Matcher m = startWithNumber.matcher(addressLine[0]);

		if (m.find()) {
			parsedHouseStreetNumber.setHouseStreetBuildingNumber(m.group(1));
			checkForHouseStreetNumber(parsedHouseStreetNumber, m);
			if (addressLine.length > 1) {
				for (int i = 1; i < addressLine.length; i++) {
					parsedHouseStreetNumber
							.setStreet(parsedHouseStreetNumber.getStreet() + "\n" + addressLine[i].trim());
				}
			}
		}
		if (parsedHouseStreetNumber.getHouseStreetBuildingNumber() == null) {
			String otherformat = addressLine[0];
			otherformat = otherformat.replaceAll("[^0-9]+", " ");
			StringTokenizer token = new StringTokenizer(otherformat);

			while (token.hasMoreElements()) {

				String currentToken = token.nextElement().toString();
				Matcher numberMatcher = number.matcher(currentToken);
				if (numberMatcher.find()) {
					parsedHouseStreetNumber.setHouseStreetBuildingNumber(currentToken);
					parsedHouseStreetNumber.setStreet(addressLine[0].replace(currentToken, " "));
				}
			}

		}

		return parsedHouseStreetNumber;
	}

	/**
	 * Check for house street number.
	 *
	 * @param parsedHouseStreetNumber the parsed house street number
	 * @param m the m
	 */
	private void checkForHouseStreetNumber(ParseHouseStreetNumber parsedHouseStreetNumber, Matcher m) {
		if (m.group(2) != null) {
			String street1 = m.group(2).trim();
			if (m.group(1).trim().length() == 1 && Character.isAlphabetic(m.group(1).charAt(0))) {
				street1 = m.group(1) + street1;
				parsedHouseStreetNumber.setHouseStreetBuildingNumber(null);
			}
			if (street1.startsWith("/") || street1.startsWith(",") || street1.startsWith("-")) {
				parsedHouseStreetNumber.setStreet(street1.substring(1).trim());
			} else {
				parsedHouseStreetNumber.setStreet(street1.trim());
			}
		}
	}
	
	/**
	 * Parses the street values.
	 *
	 * @param serviceRequest the service request
	 */
	public void parseStreetValues(RegistrationServiceRequest serviceRequest) {
		try {
			Account account = serviceRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			if (null != contacts && !contacts.isEmpty()) {
				for (Contact contact : contacts) {
					addStreetValueToContact(contact);
				}

			}
		} catch (Exception e) {
			LOGGER.debug("Error while parsing address fields", e);
		}
	}

	/**
	 * @param contact
	 */
	private void addStreetValueToContact(Contact contact) {
		String contactStreet = contact.getStreet();
		if (null != contactStreet) {
			ParseHouseStreetNumber parseStreet = parseHouseStreetNumber(contactStreet);
			contact.setAddress1(contactStreet);
			if (!StringUtils.isNullOrTrimEmpty(parseStreet.getHouseStreetBuildingNumber())) { 
				contact.setBuildingNumber(parseStreet.getHouseStreetBuildingNumber());
			}
			if (null != parseStreet.getStreet() && !parseStreet.getStreet().isEmpty()) {
				contact.setStreet(parseStreet.getStreet());
			}
		}
	}
	
}