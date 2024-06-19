package com.currenciesdirect.gtg.compliance.blacklist.restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.blacklist.core.BlacklistModifierImpl;
import com.currenciesdirect.gtg.compliance.blacklist.core.BlacklistSearchImpl;
import com.currenciesdirect.gtg.compliance.blacklist.core.IBlacklistModifier;
import com.currenciesdirect.gtg.compliance.blacklist.core.IBlacklistSearch;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;



/**
 * The Class BlacklistService.
 *
 * @author Rajesh
 */
@Path("/blacklist")
public class BlacklistService {

	/** The Constant LOG. */
	static final Logger LOG = LoggerFactory.getLogger(BlacklistService.class);

	/** The iblacklist. */
	private IBlacklistModifier iblacklist = BlacklistModifierImpl.getInstance();

	/** The iblacklist search. */
	private IBlacklistSearch iblacklistSearch = BlacklistSearchImpl.getInstance();

	/** The Constant REQUEST_RECEIVED_HEADER. */
	private static final String REQUEST_RECEIVED_HEADER = "requestReceived";

	/**
	 * Save into blacklist.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveIntoBlacklist(@HeaderParam("correlationID") String correlationId,
			@HeaderParam("username") String userName, BlacklistRequest request) {
		LOG.info("BlacklistService.saveIntoBlacklist: start");
		BlacklistModifierResponse blkResponse = null;
		try {
			if (request != null) {
				request.setCorrelationId(correlationId);
				request.setCreatedBy(userName);
				request.setUpdatedBy(userName);
				blkResponse = iblacklist.saveIntoBlacklist(request);
			} else
				return Response.status(Status.BAD_REQUEST).build();
		} catch (BlacklistException e) {
			blkResponse = new BlacklistModifierResponse();
			blkResponse.setErrorCode(e.getBlacklistErrors().getErrorCode());
			blkResponse.setErrorDescription(e.getBlacklistErrors().getErrorDescription());
			if (e.getBlacklistErrors().getErrorCode().equals(BlacklistErrors.INVALID_REQUEST.getErrorCode())) {
				return Response.status(Status.BAD_REQUEST).entity(blkResponse).build();
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(blkResponse).build();
		}
		return Response.status(Status.OK).entity(blkResponse).build();
	}

	/**
	 * Update blacklist.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBlacklist(@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName, BlacklistUpdateRequest request) {
		LOG.info("BlacklistService.updateBlacklist: start");
		BlacklistModifierResponse blkResponse = null;
		try {
			if (request != null) {
				request.setCorrelationId(correlationId);
				request.setUpdatedBy(userName);
				blkResponse = iblacklist.updateIntoBlacklist(request);
			} else
				return Response.status(Status.BAD_REQUEST).build();
		} catch (BlacklistException e) {
			blkResponse = new BlacklistModifierResponse();
			blkResponse.setErrorCode(e.getBlacklistErrors().getErrorCode());
			blkResponse.setErrorDescription(e.getBlacklistErrors().getErrorDescription());
			if (e.getBlacklistErrors().getErrorCode().equals(BlacklistErrors.INVALID_REQUEST.getErrorCode())) {
				return Response.status(Status.BAD_REQUEST).entity(blkResponse).build();
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(blkResponse).build();
		}
		return Response.status(Status.OK).entity(blkResponse).build();
	}

	/**
	 * Delete blacklist.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBlacklist(@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName, BlacklistRequest request) {
		LOG.info("BlacklistService.deleteBlacklist: start");
		BlacklistModifierResponse blkResponse = null;
		try {
			if (request != null) {
				request.setCorrelationId(correlationId);
				request.setCreatedBy(userName);
				request.setUpdatedBy(userName);
				blkResponse = iblacklist.deleteFromBlacklist(request);
			} else
				return Response.status(Status.BAD_REQUEST).build();
		} catch (BlacklistException e) {
			blkResponse = new BlacklistModifierResponse();
			blkResponse.setErrorCode(e.getBlacklistErrors().getErrorCode());
			blkResponse.setErrorDescription(e.getBlacklistErrors().getErrorDescription());
			if (e.getBlacklistErrors().getErrorCode().equals(BlacklistErrors.INVALID_REQUEST.getErrorCode())) {
				return Response.status(Status.BAD_REQUEST).entity(blkResponse).build();
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(blkResponse).build();
		}
		return Response.status(Status.OK).entity(blkResponse).build();
	}

	/**
	 * Stp search blacklist.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/stpsearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response stpSearchBlacklist(@HeaderParam("correlationId") String correlationId,
			BlacklistSTPRequest request) {
		LOG.info("BlacklistService.scoreBlacklist: start");
		Long requestTime = System.currentTimeMillis();
		BlacklistSTPResponse blkResponse = null;
		try {
			if (request != null) {
				request.setCorrelationId(correlationId);
				blkResponse = iblacklistSearch.stpSearchFromBlacklist(request);
			} else
				return Response.status(Status.BAD_REQUEST).header(REQUEST_RECEIVED_HEADER, requestTime).build();
		} catch (BlacklistException e) {
			blkResponse = new BlacklistSTPResponse();
			blkResponse.setErrorCode(e.getBlacklistErrors().getErrorCode());
			blkResponse.setErrorDescription(e.getBlacklistErrors().getErrorDescription());
			if (e.getBlacklistErrors().getErrorCode().equals(BlacklistErrors.INVALID_REQUEST.getErrorCode())) {
				return Response.status(Status.BAD_REQUEST).entity(blkResponse)
						.header(REQUEST_RECEIVED_HEADER, requestTime).build();
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(blkResponse)
					.header(REQUEST_RECEIVED_HEADER, requestTime).build();
		}
		return Response.status(Status.OK).entity(blkResponse).header(REQUEST_RECEIVED_HEADER, requestTime).build();
	}

	/**
	 * Ui search blacklist.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/uisearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uiSearchBlacklist(@HeaderParam("correlationId") String correlationId,
			BlacklistUISearchRequest request) {
		LOG.info("BlacklistService.searchBlacklist: start");
		Long requestTime = System.currentTimeMillis();
		BlacklistUISearchResponse blkResponse = null;
		try {
			if (request != null) {
				request.setCorrelationId(correlationId);
				blkResponse = iblacklistSearch.uiSearchFromBlacklist(request);
				LOG.info(blkResponse.toString());
			} else
				return Response.status(Status.BAD_REQUEST).header(REQUEST_RECEIVED_HEADER, requestTime).build();
		} catch (BlacklistException e) {
			blkResponse = new BlacklistUISearchResponse();
			blkResponse.setErrorCode(e.getBlacklistErrors().getErrorCode());
			blkResponse.setErrorDescription(e.getBlacklistErrors().getErrorDescription());
			if (e.getBlacklistErrors().getErrorCode().equals(BlacklistErrors.INVALID_REQUEST.getErrorCode())) {
				return Response.status(Status.BAD_REQUEST).entity(blkResponse)
						.header(REQUEST_RECEIVED_HEADER, requestTime).build();
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(blkResponse)
					.header(REQUEST_RECEIVED_HEADER, requestTime).build();
		}
		return Response.status(Status.OK).entity(blkResponse).header(REQUEST_RECEIVED_HEADER, requestTime).build();
	}

}
