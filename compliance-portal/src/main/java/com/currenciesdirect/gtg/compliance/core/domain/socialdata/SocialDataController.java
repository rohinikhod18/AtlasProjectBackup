package com.currenciesdirect.gtg.compliance.core.domain.socialdata;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.currenciesdirect.gtg.compliance.core.report.ISocialDataService;
import com.currenciesdirect.gtg.compliance.httpport.BaseController;

@Controller
public class SocialDataController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(SocialDataController.class);
	
	@Autowired
	@Qualifier("socialDataServiceImpl")
	private ISocialDataService iSocialDataServiceResponse;

	@PostMapping(value="/getSocialData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public SocialDataResponse getSocialData(@RequestBody SocialDataRequest socialDataRequest) {
		SocialDataResponse socialDataResponse = null;
		try {
			socialDataResponse = iSocialDataServiceResponse.getSocialData(socialDataRequest);
		}
		catch(Exception e) {
			log.error("Exception in getSocialData in SocialDataController {1}",e);
		}
		return socialDataResponse;
	}
}
