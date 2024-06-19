package com.currenciesdirect.gtg.compliance.core;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.base.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationResponse;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymizationServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class DataAnonymisationServiceImpl.
 */
@Component("dataAnonymisationServiceImpl")
public class DataAnonymisationServiceImpl implements IAnonymisationService{
	
	private static final String CONTENT_TYPE = "Content-Type";

	private Logger log = LoggerFactory.getLogger(DataAnonymisationServiceImpl.class);
	
	/** The i anonymisation DB service. */
	@Autowired
	@Qualifier("dataAnonymisationDBServiceImpl")
	private IAnonymisationDBService iAnonymisationDBService;
	
	/** The registration details factory. */
	@Autowired
	@Qualifier("registrationDetailsFactory")
	private RegistrationDetailsFactory registrationDetailsFactory;
	
	/** The i cfx DB service. */
	@Autowired
	@Qualifier("regCfxDBServiceImpl")
	private ICfxDBService iCfxDBService;
	
	/**
	 * Gets the data anonymisation with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return the data anonymisation with criteria
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymisationDto getDataAnonymisationWithCriteria(DataAnonymisationSearchCriteria searchCriteria) 
			throws CompliancePortalException {
		try {
			return iAnonymisationDBService.getDataAnonymisationWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}
	
	/**
	 * Gets the data anonymize.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anonymize
	 */
	@Override
	public boolean getDataAnonymize(UserProfile user,DataAnonymisationDataRequest dataAnonymizeRequest) {
		boolean anonymize = false;
		try {
			anonymize = iAnonymisationDBService.getDataAnonymize(user, dataAnonymizeRequest);
		}
		catch(Exception e) {
			log.debug("Exception in getDataAnonymize in DataAnonymisationServiceImpl {1}", e);
		}
		return anonymize;
	}

	/**
	 * Update data anonymisation.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anonymisation responce
	 */
	@Override
	public DataAnonymisationResponse updateDataAnonymisation(UserProfile user,DataAnonymisationDataRequest dataAnonymizeRequest) {
		DataAnonymisationResponse dataAnonymisationResponce= new DataAnonymisationResponse();
		try {
			DataAnonymizationServiceRequest sendDataAnonData = iAnonymisationDBService.getAnonymisationDetails(dataAnonymizeRequest);
		    String jsonDataAnonRequest = JsonConverterUtil.convertToJsonWithoutNull(sendDataAnonData);
			log.warn("\n -------Data Anon Request Atlas to ES Start -------- \n  {}", jsonDataAnonRequest);
			log.warn(" \n -----------Data Anon Request Atlas to ES End ---------");
			HttpClientPool clientPool = HttpClientPool.getInstance();
			MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
			headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
			String baseUrl = System.getProperty("baseEnterpriseUrl");
			dataAnonymisationResponce= clientPool.sendRequest(
					baseUrl + "/DataAnonymization/rest/getCustomerDetails", "POST",jsonDataAnonRequest
					, DataAnonymisationResponse.class, headers, MediaType.APPLICATION_JSON_TYPE);
			if(InternalProcessingCode.APPROVED.getCode().equalsIgnoreCase(dataAnonymisationResponce.getCode())){
				iAnonymisationDBService.updateDataAnonymisation(user, dataAnonymizeRequest);
			}
		}
		catch(Exception e) {
			log.error("Exception in updateDataAnonymisation in DataAnonymisationServiceImpl {1}", e);
		}
		return dataAnonymisationResponce;
	}
	
	public boolean  cancelDataAnonymisation(UserProfile user,DataAnonymisationDataRequest dataAnonymizeRequest) {
		boolean anonymize = false;
		try {
			anonymize = iAnonymisationDBService.cancelDataAnonymisation(user, dataAnonymizeRequest);
		}
		catch(Exception e) {
			log.debug("Exception in cancelDataAnonymisation in DataAnonymisationServiceImpl {1}", e);
		}
		return anonymize;
	}
	
	public DataAnonymisationDto getDataAnonHistory(DataAnonymisationDataRequest request) throws CompliancePortalException{
		DataAnonymisationDto dataAnonymisationDto = null;
		try {
			dataAnonymisationDto = iAnonymisationDBService.getDataAnonHistory(request);
		}catch (CompliancePortalException e) {
			logError(e);
			throw e;

		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		}
		return dataAnonymisationDto;
	}
}
