package com.currenciesdirect.gtg.compliance.validator.blacklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.core.IValidator;
import com.currenciesdirect.gtg.compliance.core.blacklist.BalcklistDataBuilder;
import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateData;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;
import com.currenciesdirect.gtg.compliance.validator.RegxConstants;

/**
 * The Class ValidatorImpl.
 * 
 * @author Rajesh
 */
public class BlacklistValidatorImpl implements IValidator<IRequest> {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BlacklistValidatorImpl.class);

	/** The i validator. */
	@SuppressWarnings("squid:S3077")
	private static volatile IValidator<IRequest> iValidator = null;

	/** The data builder. */
	private BalcklistDataBuilder dataBuilder = BalcklistDataBuilder.getInstance();

	/**
	 * Gets the single instance of BlacklistValidatorImpl.
	 *
	 * @return single instance of BlacklistValidatorImpl
	 */
	public static IValidator<IRequest> getInstance() {
		if (iValidator == null) {
			synchronized (BlacklistValidatorImpl.class) {
				if (iValidator == null) {
					iValidator = new BlacklistValidatorImpl();
				}
			}
		}
		return iValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.core.IValidator#validate(java.lang.
	 * Object)
	 */
	@Override
	public Boolean validate(IRequest object) throws BlacklistException {
		if (object instanceof BlacklistRequest) {
			validateBlakclistRequest((BlacklistRequest) object);
		}
		if (object instanceof BlacklistUpdateRequest) {
			validateBlakclistUpdateRequest((BlacklistUpdateRequest) object);
		}
		if (object instanceof BlacklistUISearchRequest) {
			validateBlakclistSearchRequest((BlacklistUISearchRequest) object);
		}
		if (object instanceof BlacklistSTPRequest) {
			validateBlakclistSTPRequest((BlacklistSTPRequest) object);
		}
		if (object instanceof InternalServiceRequest) {
			validateInternalServiceRequest((InternalServiceRequest) object);
		}
		return Boolean.TRUE;
	}

	/**
	 * Validate blakclist request.
	 *
	 * @param request
	 *            the request
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void validateInternalServiceRequest(InternalServiceRequest request) throws BlacklistException {
		LOG.debug("ValidatorImpl.validateBlakclistRequest: start");
		BlacklistRequest blacklistRequest = request.getBlacklistRequest();
		try {
			BlacklistData[] dataArray = blacklistRequest.getData();
			isBlcklistRequestInvalid(dataArray);
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST, exception);
		}
	}
	
	/**
	 * Validate blakclist request.
	 *
	 * @param request
	 *            the request
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void validateBlakclistRequest(BlacklistRequest request) throws BlacklistException {
		LOG.debug("ValidatorImpl.validateBlakclistRequest: start");
		try {
			if (request.getData().length == 0) {
				LOG.warn("Data must not be empty");
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
			BlacklistData[] dataArray = request.getData();
			isBlcklistRequestInvalid(dataArray);
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST, exception);
		}
	}

	/**
	 * Validate blakclist STP request.
	 *
	 * @param request
	 *            the request
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void validateBlakclistSTPRequest(BlacklistSTPRequest request) throws BlacklistException {
		LOG.debug("ValidatorImpl.validateBlakclistSTPRequest: start");
		try {
			if (request.getSearch().isEmpty()) {
				LOG.warn("Data must not be empty");
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST, exception);
		}
	}

	/**
	 * Validate blakclist update request.
	 *
	 * @param request
	 *            the request
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void validateBlakclistUpdateRequest(BlacklistUpdateRequest request) throws BlacklistException {
		LOG.debug("ValidatorImpl.validateBlakclistUpdateRequest: start");
		try {
			if (request.getData() == null || request.getData().length == 0) {
				LOG.warn("Data must not be null or empty");
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
			BlacklistUpdateData[] dataArray = request.getData();
			isUpdateRequestInvalid(dataArray);
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST, exception);
		}
	}

	/**
	 * Validate blakclist search request.
	 *
	 * @param request
	 *            the request
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void validateBlakclistSearchRequest(BlacklistUISearchRequest request) throws BlacklistException {
		LOG.debug("ValidatorImpl.validateBlakclistSearchRequest: start");
		try {
			if (request.getValue().isEmpty() || !dataBuilder.isTypePresent(request.getType())) {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST, exception);
		}
	}

	/**
	 * Checks if is blcklist request invalid.
	 *
	 * @param dataArray
	 *            the data array
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void isBlcklistRequestInvalid(BlacklistData[] dataArray) throws BlacklistException {
		for (BlacklistData data : dataArray) {
			if (data.getValue().isEmpty() || !dataBuilder.isTypePresent(data.getType().toUpperCase())) {
				throw new BlacklistException(BlacklistErrors.INVALID_INPUT);
			} else {
				validateFromRegx(data.getType(), data.getValue());
			}
		}

	}


	/**
	 * Checks if is update request invalid.
	 *
	 * @param updateData
	 *            the update data
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private void isUpdateRequestInvalid(BlacklistUpdateData[] updateData) throws BlacklistException {
		for (BlacklistUpdateData data : updateData) {
			if (data.getOriginalValue().isEmpty() || data.getNewValue().isEmpty()
					|| !dataBuilder.isTypePresent(data.getType())) {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			} else {
				validateFromRegx(data.getType(), data.getOriginalValue());
				validateFromRegx(data.getType(), data.getNewValue());
			}
		}
	}

	/**
	 * Validate from regx.
	 *
	 * @param type
	 *            the type
	 * @param value
	 *            the value
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	void validateFromRegx(String type, String value) throws BlacklistException {
		RegxConstants regx = null;
		switch (type.toUpperCase()) {
		case "NAME":
			regx = RegxConstants.NAME;
			break;
		case "IPADDRESS":
			regx = RegxConstants.IP_ADDRESS;
			break;
		case "EMAIL":
			regx = RegxConstants.EMAIL;
			break;
		case "PHONE_NUMBER":
			regx = RegxConstants.PHONE_NUMBER;
			break;
		default:
			break;
		}
		if (regx != null && !RegxConstants.validate(regx, value)) {
			throw new BlacklistException(BlacklistErrors.INVALID_INPUT);
		}
	}
	
}
