/**
 * 
 */
package com.currenciesdirect.gtg.compliance.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.currenciesdirect.gtg.compliance.core.Ivalidator;
import com.currenciesdirect.gtg.compliance.core.domain.IpRequestData;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.IpException;
import com.currenciesdirect.gtg.compliance.util.Constants;

/**
 * @author manish
 *
 */
public class IpValidator  implements Ivalidator{
	
	private Pattern pattern;
	private Matcher matcher;

	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	
	private static Ivalidator ivalidator = new IpValidator();
	

	public static Ivalidator getInstance(){
		return ivalidator;
	}
	
	private IpValidator() {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}

	private boolean validateIPFormat(final String ip) throws IpException {
		matcher = pattern.matcher(ip);
	    if(matcher.matches()) {
	    	return true;
	    } else {
			throw new IpException(IpErrors.INVALID_IP);
		}
	}
	
	private boolean validateCountry(String country) throws IpException {
		
		if(Constants.AUSTRALIA.equalsIgnoreCase(country) ||  Constants.UNITEDKINGDOM.equalsIgnoreCase(country)) {
			return true;
		} else {
			throw new IpException(IpErrors.COUNTRY_NOT_SUPPORTED);
		}
		
	}

	@Override
	public Boolean validateIpRequest(IpServiceRequest ipRequestList) throws IpException {
		try {
			for (IpRequestData ipRequest : ipRequestList.getSearchData()) {
				if (validateIPFormat(ipRequest.getIpAddress()) && validateCountry(ipRequest.getCountry())) {
					return true;
				}
			}
		} catch (IpException ipException) {
			throw ipException;
		} catch (Exception exception) {
			throw new IpException(IpErrors.IP_GENERIC_EXCEPTION, exception);
		}
		return false;
	}

}
