package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerInsertRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.IRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanErrors;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;


/**
 * The Class ValidatorImpl.
 * 
 * @author Rajesh
 */
public class ValidatorImpl implements IValidator<IRequest> {

	static final Logger LOG = LoggerFactory.getLogger(ValidatorImpl.class);

	private static volatile IValidator<IRequest> iValidator = null;

	private ValidatorImpl() {
	}

	public static IValidator<IRequest> getInstance() {
		if (iValidator == null) {
			synchronized (ValidatorHelper.class) {
				if (iValidator == null) {
					iValidator = new ValidatorImpl();
				}
			}
		}
		return iValidator;
	}

	@Override
	public void validate(IRequest request) throws CustomerDataScanException {
		if (request instanceof CustomerDataScanInsertRequest) {
			CustomerDataScanInsertRequest requestObject = (CustomerDataScanInsertRequest) request;
			validateInsert(requestObject);
		} else if (request instanceof CustomerDataScanUpdateRequest) {
			CustomerDataScanUpdateRequest requestObject = (CustomerDataScanUpdateRequest) request;
			validateUpdate(requestObject);
		} else if (request instanceof CustomerDataScanSearchRequest) {
			CustomerDataScanSearchRequest requestObject = (CustomerDataScanSearchRequest) request;
			validateSearch(requestObject);
		} else if (request instanceof CustomerDataScanDeleteRequest) {
			CustomerDataScanDeleteRequest requestObject = (CustomerDataScanDeleteRequest) request;
			validateDelete(requestObject);
		} else {
			throw new IllegalArgumentException("Illigal Request");
		}
	}

	/**
	 * Validate insert request.
	 * 
	 * @param request
	 *            the request
	 * @throws CustomerDataScanException
	 */
	public void validateInsert(CustomerDataScanInsertRequest request)
			throws CustomerDataScanException {
		LOG.debug("ValidatorImpl.validateInsert: Start");
		for(CustomerInsertRequestData data : request.getSave()){
			if (data == null) {
				throw new CustomerDataScanException(
						CustomerDataScanErrors.ERROR_WHILE_VALIDATION);
			}
			if (data.getSfAccountID() == null) {
				throw new CustomerDataScanException(
						CustomerDataScanErrors.ERROR_WHILE_VALIDATION);
			}
		}
		

	}

	/**
	 * Validate update request.
	 * 
	 * @param request
	 *            the request
	 */
	public void validateUpdate(CustomerDataScanUpdateRequest request)
			throws CustomerDataScanException {
		LOG.debug("ValidatorImpl.validateUpdate: Start");
		if (request == null) {
			throw new CustomerDataScanException(
					CustomerDataScanErrors.ERROR_WHILE_VALIDATING_DATA);
		}
		if (request.getSfAccountID() == null) {
			throw new CustomerDataScanException(
					CustomerDataScanErrors.ERROR_WHILE_VALIDATING_DATA);
		}
	}

	/**
	 * Validate delete request.
	 * 
	 * @param request
	 *            the request
	 * @throws CustomerDataScanException
	 */
	public void validateDelete(CustomerDataScanDeleteRequest request)
			throws CustomerDataScanException {
		LOG.debug("ValidatorImpl.validateUpdate: Start");
		if (request == null) {
			throw new CustomerDataScanException(
					CustomerDataScanErrors.ERROR_WHILE_VALIDATING_DATA);
		}
		if (request.getSfAccountID() == null) {
			throw new CustomerDataScanException(
					CustomerDataScanErrors.ERROR_WHILE_VALIDATING_DATA);
		}
	}

	/**
	 * Validate search request.
	 * 
	 * @param request
	 *            the request
	 * @throws CustomerDataScanException
	 */
	public void validateSearch(CustomerDataScanSearchRequest request)
			throws CustomerDataScanException {
		LOG.debug("ValidatorImpl.validateSearch: Start");
		if (request == null) {
			throw new CustomerDataScanException(
					CustomerDataScanErrors.ERROR_WHILE_VALIDATING_DATA);
		}
	}

}
