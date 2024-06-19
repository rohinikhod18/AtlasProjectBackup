package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.IRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanErrors;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;

/**
 * The Class CustomerDataSearchImpl.
 * 
 * @author Rajesh
 */
public class CustomerDataSearchImpl implements ICustomerSearchService {

	static final Logger LOG = LoggerFactory
			.getLogger(CustomerDataSearchImpl.class);
	private IDBService iDBService = DBServiceImpl.getInstance();
	private static volatile  ICustomerSearchService iCustomerSearchService;
	private IValidator<IRequest> iValidator = ValidatorImpl.getInstance();

	private CustomerDataSearchImpl() {
	}

	public static ICustomerSearchService getInstance() {
		if (iCustomerSearchService == null) {
			synchronized (CustomerDataSearchImpl.class) {
				if (iCustomerSearchService == null) {
					iCustomerSearchService = new CustomerDataSearchImpl();
				}
			}
		}
		return iCustomerSearchService;
	}

	@Override
	public CustomerDataScanSearchResponse searchDocument(CustomerDataScanSearchRequest document)
			throws CustomerDataScanException {
		LOG.debug("CustomerDataSearchImpl.searchDocument() start");
		CustomerDataScanSearchResponse response = null;
		try {
			iValidator.validate(document);
			response = iDBService.searchDocument(document);
		} catch (CustomerDataScanException customerDataScanException) {
			logError(document.getCorrelationId(),customerDataScanException.getOrgException());
			throw customerDataScanException;
		} catch (Exception exception) {
			logError(document.getCorrelationId(),exception);
			throw new CustomerDataScanException(CustomerDataScanErrors.FAILED);
		}
		return response;
	}

	private void logError(String correlationId,Throwable exception) {
		LOG.error("CorrelationId:" + correlationId  + "Exception: ", exception);
	}
}
