/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.currenciesdirect.gtg.compliance.core.domain.CustomerLocation;
import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.IpRequestData;
import com.currenciesdirect.gtg.compliance.core.domain.IpResponseData;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PostCodeLocation;
import com.currenciesdirect.gtg.compliance.dbport.IpDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.IpException;
import com.currenciesdirect.gtg.compliance.quovaport.QouvaPort;
import com.currenciesdirect.gtg.compliance.util.Constants;
import com.currenciesdirect.gtg.compliance.validator.IpValidator;

/**
 * @author manish
 * 
 */
public class IpServiceImpl implements IpService{

	private static IpService iPValidationService = new IpServiceImpl();

	public static IpService getInstance() {
		return iPValidationService;
	}

	private IDBService idbService = IpDBServiceImpl.getInstance();
	
	private Ivalidator ivalidator = IpValidator.getInstance();
	
	private IpConcreteDataBuilder builder = IpConcreteDataBuilder.getInstance();
	
	private IpProviderService ipProviderService = QouvaPort.getInstance();



	/** The Constant LOG. */
	static final Logger LOG = org.slf4j.LoggerFactory
			.getLogger(IpServiceImpl.class);

	

	private IpServiceImpl() {
		
	}


	private Double calculateGeoDiffrence(CustomerLocation customerLocation,
			PostCodeLocation postCodeLocation) throws IpException {
		double difference = 1.0;
		LOG.info("calculateGeoDifference method START ");
		try {

			difference = distance(new Double(customerLocation.getLatitude()), new Double(customerLocation.getLongitude()), 
					postCodeLocation.getLatitude(), postCodeLocation.getLongitude(), "M");
			
		} catch (Exception e) {
			LOG.error("Exception in calculateGeoDifference ", e);
			throw new IpException(IpErrors.ERROR_WHILE_CALCULATING_GEO_DIFFERENCE,e);
		}
		LOG.info("calculateGeoDifference method END ");
		return Double.isNaN(difference) ? 1.0 : difference;

	}
	
	private  double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit == "K") {
		   dist = dist * 1.609344;
		  } else if (unit == "N") {
		   dist = dist * 0.8684;
		  }
		return dist;
	}
	 /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	 /*:: This function converts decimal degrees to radians       :*/
	 /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	 private  double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
	 }

	 /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	 /*:: This function converts radians to decimal degrees       :*/
	 /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	 private  double rad2deg(double rad) {
	  return (rad * 180 / Math.PI);
	 }


	@Override
	public IpServiceResponse checkIpPostCodeDistance(IpServiceRequest requestList)
			throws IpException {
		IpProviderProperty ipProviderProperty = new IpProviderProperty();
		CustomerLocation customerLocation = new CustomerLocation();
		PostCodeLocation postCodeLocation = new PostCodeLocation();
		Double geoDiff= null;
		IpServiceResponse serviceResponse = new IpServiceResponse();
		try {
			ivalidator.validateIpRequest(requestList);
			ipProviderProperty = builder.getProviderInitConfigProperty(Constants.QUOVA_PROVIDER);
			customerLocation = ipProviderService.getCustomerLocationFromIp(requestList.getSearchData().get(0), ipProviderProperty);
			List<IpResponseData> searchResult = new ArrayList<>();
			for (IpRequestData request : requestList.getSearchData()) {
				postCodeLocation = idbService.getLocationFromPostCode(request.getPostCode());
				geoDiff = calculateGeoDiffrence(customerLocation, postCodeLocation);
				IpResponseData ipResponse = new IpResponseData();
				ipResponse.setId(request.getId());
				ipResponse.setOrgCode(request.getOrgCode());
				ipResponse.setSourceApplication(request.getSourceApplication());
				ipResponse.setAnonymizerStatus(customerLocation.getIp_anonymizer_status());
				ipResponse.setGeoDifference(String.valueOf(geoDiff));
				ipResponse.setUnit("Miles");
				ipResponse.setAuroraAccountId(request.getAuroraAccountId());
				ipResponse.setAuroraContactId(request.getAuroraContactId());
				//ipResponse.setResponseStatus("Success");
				ipResponse.setPostcode(request.getPostCode());
				searchResult.add(ipResponse);
				if(geoDiff>200){
					ipResponse.setOverAllStatus("FAIL");
				}else {
					ipResponse.setOverAllStatus("PASS");
				}
			}
			serviceResponse.setSearchResult(searchResult);
			
		}catch(IpException exception) {
		
			LOG.error("Error in checkIpPostCodeDistance :"+"CorrelationID:"+requestList.getCorrelationId(),exception.getException());
			throw exception;
		} catch(Exception exception) {
			LOG.error("Error in checkIpPostCodeDistance :"+"CorrelationID:"+requestList.getCorrelationId(),exception);
			throw new IpException(IpErrors.IP_GENERIC_EXCEPTION);
		}
		return serviceResponse;
		
	}
	
	public static void main(String[] args){
		CustomerLocation cLoc = new CustomerLocation();
		cLoc.setLatitude("-22.86936");
		cLoc.setLongitude("-42.35321");
		PostCodeLocation pLoc = new PostCodeLocation();
		pLoc.setLatitude(-22.7116014);
		pLoc.setLongitude(-43.3229657);
		IpServiceImpl impl = new IpServiceImpl();
		try {
			System.out.println(impl.calculateGeoDiffrence(cLoc, pLoc));
		} catch (IpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
