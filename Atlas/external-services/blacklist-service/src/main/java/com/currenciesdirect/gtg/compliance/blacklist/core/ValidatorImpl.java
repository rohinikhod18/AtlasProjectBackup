package com.currenciesdirect.gtg.compliance.blacklist.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistIdAndData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * The Class ValidatorImpl.
 * 
 * @author Rajesh
 */
public class ValidatorImpl implements IValidator<IRequest> {

	static final Logger LOG = LoggerFactory.getLogger(ValidatorImpl.class);

	private static volatile IValidator<IRequest> iValidator = null;

	private BalcklistDataBuilder dataBuilder = BalcklistDataBuilder.getInstance();

	public static IValidator<IRequest> getInstance() {
		if (iValidator == null) {
			synchronized (ValidatorImpl.class) {
				if (iValidator == null) {
					iValidator = new ValidatorImpl();
				}
			}
		}
		return iValidator;
	}

	@Override
	public boolean validate(IRequest object) throws BlacklistException {
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
		return true;
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
		LOG.info("ValidatorImpl.validateBlakclistRequest: start");
		try {
			if (request.getData().length == 0) {
				LOG.info("Data must not be empty");
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

	private void validateBlakclistSTPRequest(BlacklistSTPRequest request) throws BlacklistException {
		LOG.info("ValidatorImpl.validateBlakclistSTPRequest: start");
		try {
			if (request.getSearch().isEmpty()) {
				LOG.info("Data must not be empty");
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
			List<BlacklistIdAndData> dataList = request.getSearch();
			isBlcklistSTPRequestInvalid(dataList);
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
		LOG.info("ValidatorImpl.validateBlakclistUpdateRequest: start");
		try {
			if (request.getData() == null || request.getData().length == 0) {
				LOG.info("Data must not be null or empty");
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
		LOG.info("ValidatorImpl.validateBlakclistSearchRequest: start");
		try {
			if (request.getType().isEmpty() || request.getValue().isEmpty()
					|| !isBlacklistTypePresent(request.getType())) {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST, exception);
		}
	}

	private void isBlcklistRequestInvalid(BlacklistData[] dataArray) throws BlacklistException {
		for (BlacklistData data : dataArray) {
			if (data.getType().isEmpty() || data.getValue().isEmpty() || !isBlacklistTypePresent(data.getType())) {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			} else {
				validateFromRegx(data.getType(), data.getValue());
			}
		}

	}

	private void isBlcklistSTPRequestInvalid(List<BlacklistIdAndData> dataArray) throws BlacklistException {
		for (BlacklistIdAndData idAnddata : dataArray) {
			for (BlacklistData data : idAnddata.getData())
				if (data.getType().isEmpty() || data.getValue().isEmpty() || !isBlacklistTypePresent(data.getType())) {
					throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
				}
		}

	}

	private void isUpdateRequestInvalid(BlacklistUpdateData[] updateData) throws BlacklistException {
		for (BlacklistUpdateData data : updateData) {
			if (data.getType().isEmpty() || data.getOriginalValue().isEmpty() || data.getNewValue().isEmpty()
					|| !isBlacklistTypePresent(data.getType())) {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			} else {
				validateFromRegx(data.getType(), data.getOriginalValue());
				validateFromRegx(data.getType(), data.getNewValue());
			}
		}
	}

	void validateFromRegx(String type, String value) throws BlacklistException {
		RegxConstants regx = null;
		switch (type) {
		case "NAME":
			regx = RegxConstants.NAME;
			break;
		case "IPADDRESS":
			regx = RegxConstants.IP_ADDRESS;
			break;
		case "EMAIL":
			regx = RegxConstants.EMAIL;
			break;
		default:
			break;
		}
		if (regx != null && !RegxConstants.validate(regx, value)) {
			throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
		}
	}

	/**
	 * Checks if is blacklist type present.
	 *
	 * @param type
	 *            the type
	 * @return true, if is blacklist type present
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	private boolean isBlacklistTypePresent(String type) throws BlacklistException {
		if (!dataBuilder.isTypePresent(type))
			throw new IllegalArgumentException(type + " is not a valid Blacklist Type");
		return true;
	}

}
