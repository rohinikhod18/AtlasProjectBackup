package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.httpport.BaseController;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

@Controller
public class FraudRingController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FraudRingController.class);
	
	@Autowired
	@Qualifier("fraudRingGraphServiceImpl")
	private IFraudRingService iFraudRingGraphResponse;
	
	
	@PostMapping(value = "/getFraudRingDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public FraudRingResponse getFraudRingGraphData(@RequestAttribute("user") UserProfile user,
			@RequestBody FraudRingRequest fraudRingGraphRequest) throws CompliancePortalException {
		FraudRingResponse fraudRingResponse = null;
		try {
			fraudRingResponse = iFraudRingGraphResponse.getFraudRingGraphData(fraudRingGraphRequest);
		} catch (CompliancePortalException e) {
			log.debug("Exception in getFraudRingGraphData in FraudRingController {1}", e);
		}
		return fraudRingResponse;
	}
	
	@PostMapping(value = "/checkIsFraudRingPresent")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseBody
	public Boolean checkIsFraudRingPresent(@RequestAttribute("user") UserProfile user,
			@RequestBody FraudRingRequest fraudRingRequest) throws CompliancePortalException{
		boolean fraudStatus = false;
		try {
			fraudStatus = iFraudRingGraphResponse.checkIsFraudRingPresent(fraudRingRequest);
		}catch(CompliancePortalException e) {
			log.debug("Exception in checkIsFraudRingPresent in FraudRingController {1}", e);
		}
		return fraudStatus;
	}

	@PostMapping(value = "/getNodeDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseBody
	public FraudRingNode getNodeDetails(@RequestAttribute("user") UserProfile user,
			@RequestBody FraudRingRequest fraudRingRequest) {
		FraudRingNode fraudRingNode = null;
		try {
			fraudRingNode = iFraudRingGraphResponse.getNodeDetails(fraudRingRequest);
		}
		catch(Exception e) {
			log.debug("Exception in getNodeDetails in FraudRingController {1}", e);
		}
		return fraudRingNode;
	}
}