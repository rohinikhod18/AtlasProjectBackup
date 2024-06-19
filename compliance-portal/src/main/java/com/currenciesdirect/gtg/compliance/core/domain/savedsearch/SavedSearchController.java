package com.currenciesdirect.gtg.compliance.core.domain.savedsearch;

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

import com.currenciesdirect.gtg.compliance.core.report.ISavedSearchService;
import com.currenciesdirect.gtg.compliance.httpport.BaseController;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

@Controller
public class SavedSearchController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(SavedSearchController.class);
	
	@Autowired
	@Qualifier("savedSearchServiceImpls")
	private ISavedSearchService iSavedSearchServiceResponse;
	
	/**
	 * @param user
	 * @param savedSearchRequest
	 * To save user filter in DB
	 * @return
	 */
	
	@PostMapping(value = "/savedSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean savedSearch(@RequestAttribute("user") UserProfile user,
			@RequestBody SavedSearchRequest savedSearchRequest) {
		boolean saved = false;
		try {
			saved =  iSavedSearchServiceResponse.savedSearch(user, savedSearchRequest);
		}
		catch(Exception e) {
			log.debug("Exception in savedSearch in SavedSearchController {1}", e);
		}
		return saved;
	}
	
	@PostMapping(value = "/deleteSavedSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean deleteSavedSearch(@RequestAttribute("user") UserProfile user,
			@RequestBody SavedSearchRequest savedSearchRequest) {
		boolean delete = false;
		try {
			delete =  iSavedSearchServiceResponse.deleteSavedSearch(user, savedSearchRequest);
		}
		catch(Exception e) {
			log.debug("Exception in deleteSavedSearch in SavedSearchController {1}", e);
		}
		return delete;
	}
	
	@PostMapping(value = "/updateSavedSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean updateSavedSearch(@RequestAttribute("user") UserProfile user,
			@RequestBody SavedSearchRequest savedSearchRequest) {
		boolean update = false;
		try {
			update =  iSavedSearchServiceResponse.updateSavedSearch(user, savedSearchRequest);
		}
		catch(Exception e) {
			log.debug("Exception in updateSavedSearch in SavedSearchController {1}", e);
		}
		return update;
	}
	
}