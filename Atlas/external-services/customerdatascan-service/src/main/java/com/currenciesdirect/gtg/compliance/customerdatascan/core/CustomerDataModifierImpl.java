package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.IRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanErrors;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;


/**
 * The Class CustomerDataModifierImpl.
 * 
 * @author Rajesh
 */
public class CustomerDataModifierImpl implements ICustomerModifierService {

	static final Logger LOG = LoggerFactory
			.getLogger(CustomerDataModifierImpl.class);
	private IDBService iDBService = DBServiceImpl.getInstance();
	private static volatile  ICustomerModifierService customerData;
	private IValidator<IRequest> iValidator = ValidatorImpl.getInstance();

	private CustomerDataModifierImpl() {
	}

	public static ICustomerModifierService getInstance() {
		if (customerData == null) {
			synchronized (CustomerDataModifierImpl.class) {
				if (customerData == null) {
					customerData = new CustomerDataModifierImpl();
				}
			}
		}
		return customerData;
	}

	@Override
	public CustomerDataScanInsertResponse saveDocument(CustomerDataScanInsertRequest document)
			throws CustomerDataScanException {
		LOG.debug("CustomerDataModifierImpl.saveDocument() start");
		CustomerDataScanInsertResponse response = null;
		try {
			iValidator.validate(document);
			response = iDBService.saveDocument(document);
		} catch (CustomerDataScanException customerDataScanException) {
			logError(document.getCorrelationId(),customerDataScanException.getOrgException());
			throw customerDataScanException;
		} catch (Exception exception) {
			logError(document.getCorrelationId(),exception);
			iValidator.validate(document);
			throw new CustomerDataScanException(CustomerDataScanErrors.FAILED);
		}
		return response;
	}

	@Override
	public CustomerDataScanResponse updateDocument(CustomerDataScanUpdateRequest document)
			throws CustomerDataScanException {
		LOG.debug("CustomerDataModifierImpl.updateDocument() start");
		CustomerDataScanResponse response = null;
		try {
			iValidator.validate(document);
			response = iDBService.updateDocument(document);
		} catch (CustomerDataScanException customerDataScanException) {
			logError(document.getCorrelationId(),customerDataScanException.getOrgException());
			throw customerDataScanException;
		} catch (Exception exception) {
			logError(document.getCorrelationId(),exception);
			throw new CustomerDataScanException(CustomerDataScanErrors.FAILED);
		}
		return response;
	}

	@Override
	public CustomerDataScanResponse deleteDocument(CustomerDataScanDeleteRequest document)
			throws CustomerDataScanException {
		LOG.debug("CustomerDataModifierImpl.deleteDocument() start");
		CustomerDataScanResponse response = null;
		try {
			iValidator.validate(document);
			response = iDBService.deleteDocument(document);
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
