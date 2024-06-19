/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.core.IValidator;
import com.currenciesdirect.gtg.compliance.core.domain.IpChecksEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpCheck;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;
import com.currenciesdirect.gtg.compliance.ipport.IpPort;
import com.currenciesdirect.gtg.compliance.util.Constants;
import com.currenciesdirect.gtg.compliance.util.ExternalErrorCodeEnum;
import com.currenciesdirect.gtg.compliance.validator.ip.IpValidator;

/**
 * The Class IpServiceImpl.
 *
 * @author manish
 */
public class IpServiceImpl implements IpService {

	/** The i P validation service. */
	private static IpService iPValidationService = new IpServiceImpl();

	/** The i validator. */
	private IValidator<IpServiceRequest> iValidator = IpValidator.getInstance();

	/** The builder. */
	private IpConcreteDataBuilder builder = IpConcreteDataBuilder.getInstance();

	/** The ip provider service. */
	private IpProviderService ipProviderService = IpPort.getInstance();

	/**
	 * Instantiates a new ip service impl.
	 */
	private IpServiceImpl() {

	}

	/**
	 * Gets the single instance of IpServiceImpl.
	 *
	 * @return single instance of IpServiceImpl
	 */
	public static IpService getInstance() {
		return iPValidationService;
	}



	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ip.IpService#checkIpPostCodeDistance(com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest)
	 */
	@Override
	public IpContactResponse checkIpPostCodeDistance(IpServiceRequest request) throws IpException {
		IpProviderProperty ipProviderProperty = new IpProviderProperty();
		IpContactResponse serviceResponse = new IpContactResponse();
		try {
			String country;
			if(request.getCountry()!=null)
			{
				country= request.getCountry().trim();
			}
			else
			{
				country="";
			}
			if (Constants.AUSTRALIA.equalsIgnoreCase(country)
					|| Constants.UNITEDKINGDOM.equalsIgnoreCase(country)) {
				Boolean isValidate = iValidator.validate(request);
				if(Boolean.TRUE.equals(isValidate)){
					ipProviderProperty = builder.getProviderInitConfigProperty(Constants.CHECK_IP_POSTCODE);
					serviceResponse = checkIpPostCodeDistanceWithGeoDiff(request, ipProviderProperty);
				}
				else if(null == request.getIpAddress() || request.getIpAddress().isEmpty())
					serviceResponse.setStatus(Constants.NOT_REQUIRED);			
				else
					throw new IpException(IpErrors.INVALID_REQUEST);
			}
			else {
				ipProviderProperty = builder.getProviderInitConfigProperty(Constants.GET_IP_DETAILS);
				serviceResponse = ipProviderService.getIpDetails(request, ipProviderProperty);
				serviceResponse.setStatus("PASS");
			}
		} catch (IpException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new IpException(IpErrors.IP_GENERIC_EXCEPTION, exception);
		}
		return serviceResponse;

	}
	
	/**
	 * Check ip post code distance with geo diff.
	 *
	 * @param request the request
	 * @param ipProviderProperty the ip provider property
	 * @return the ip contact response
	 * @throws IpException the ip exception
	 */
	private IpContactResponse checkIpPostCodeDistanceWithGeoDiff(IpServiceRequest request, IpProviderProperty ipProviderProperty)
			throws IpException {
		List<IpCheck> checks = new ArrayList<>();
		IpContactResponse serviceResponse;
		serviceResponse = ipProviderService.checkIpPostCodeDistance(request, ipProviderProperty);
		if (serviceResponse.getErrorCode() == null) {
			if (serviceResponse.getGeoDifference() > ipProviderProperty.getThreeSholdScore()) {
				serviceResponse.setStatus(Constants.FAIL);
				IpCheck distanceCheck = new IpCheck();
				distanceCheck.setName(IpChecksEnum.IP_DISTANCE_CHECK.getName());
				distanceCheck.setDescription(IpChecksEnum.IP_DISTANCE_CHECK.getFailDescription());
				checks.add(distanceCheck);
			} else {
				serviceResponse.setStatus(Constants.PASS);
				IpCheck distanceCheck = new IpCheck();
				distanceCheck.setName(IpChecksEnum.IP_DISTANCE_CHECK.getName());
				distanceCheck.setDescription(IpChecksEnum.IP_DISTANCE_CHECK.getPassDescription());
				checks.add(distanceCheck);

			}
			serviceResponse.setChecks(checks);
		}
		//for invalid postal code we are setting status as 'NOT_PERFORMED' (initially it was setting as NOT_AVAILABLE)
		else if(ExternalErrorCodeEnum.IP_SERVICE_COUNTRY_NOT_SUPPORTED.getErrorCode().equals(serviceResponse.getErrorCode()) 
				|| ExternalErrorCodeEnum.IP_SERVICE_INVALID_IP.getErrorCode().equals(serviceResponse.getErrorCode())
				|| ExternalErrorCodeEnum.IP_SERVICE_INVALID_LONGITUDE_AND_LATITUDE.getErrorCode().equals(serviceResponse.getErrorCode()) 
				|| ExternalErrorCodeEnum.IP_SERVICE_INVALID_POST_CODE.getErrorCode().equals(serviceResponse.getErrorCode())){
			serviceResponse.setStatus(Constants.FAIL);
		}/**else if (ExternalErrorCodeEnum.IP_SERVICE_INVALID_POST_CODE.getErrorCode().equals(serviceResponse.getErrorCode()))
				serviceResponse.setStatus(Constants.NOT_AVAILABLE);*/
		else{
			serviceResponse.setStatus(Constants.SERVICE_FAILURE);
		}
		return serviceResponse;
	}

}
