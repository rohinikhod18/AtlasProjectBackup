package com.currenciesdirect.gtg.compliance.core.globalcheck.staterulecheck;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleRequest;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleResult;
import com.currenciesdirect.gtg.compliance.exception.globalcheck.GlobalCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.globalcheck.GlobalCheckException;
import com.currenciesdirect.gtg.compliance.util.Constants;

/**
 * The Class StateRuleService.
 */
public class StateRuleService implements IRuleService {

	/** The rule service. */
	private static IRuleService ruleService = null;

	/** The rule data. */
	private String ruleData;

	/** The kie base. */
	private KieBase kieBase;

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(StateRuleService.class);

	/**
	 * Instantiates a new state rule service.
	 */
	private StateRuleService() {
		init();
	}

	/**
	 * Gets the single instance of StateRuleService.
	 *
	 * @return single instance of StateRuleService
	 */
	public static IRuleService getInstance() {
		if (ruleService == null) {
			ruleService = new StateRuleService();
		}
		return ruleService;
	}

	/**
	 * Gets the rule data.
	 *
	 * @return the rule data
	 */
	public String getRuleData() {
		return ruleData;
	}

	/**
	 * Inits the.
	 */
	public void init() {
		ruleData = loadRuleText();
		if (ruleData == null || ruleData.trim().length() <= 0) {
			throw new IllegalArgumentException("unable to read state rule file");
		}
		try {
			kieBase = parseRule(ruleData, "state-rules.drl");
		} catch (GlobalCheckException e) {
			logger.error("Exception : {1}", e);
		}
	}

	/**
	 * Load rule text.
	 *
	 * @return the string
	 * @throws RuntimeException
	 *             the runtime exception
	 */
	public String loadRuleText() {
		InputStream stream = null;
		try {
			stream = this.getClass().getClassLoader().getResourceAsStream("state-rules.drl");
			try (Scanner scanner = new Scanner(stream)) {
				return scanner.useDelimiter("\\Z").next();
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException("unable to read state rule file", ex);
		}
	}

	/**
	 * Parses the rule.
	 *
	 * @param rule
	 *            the rule
	 * @param ruleName
	 *            the rule name
	 * @return the kie base
	 * @throws GlobalCheckException
	 *             the global check exception
	 */
	public KieBase parseRule(String rule, String ruleName) throws GlobalCheckException {
		KieServices kieServices = KieServices.Factory.get();
		KieResources kieResources = kieServices.getResources();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		InputStream is = new ByteArrayInputStream(rule.getBytes(StandardCharsets.UTF_8));
		String path = getUniquePath(ruleName);
		kieFileSystem.write(path, kieResources.newInputStreamResource(is, "UTF-8"));
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();
		Results results = kieBuilder.getResults();
		if (results.hasMessages(Message.Level.ERROR)) {
			throw new IllegalStateException("There are errors in the rules:\n".concat(results.toString()));
		}
		KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
		try {
			is.close();
		} catch (IOException e) {
			throw new GlobalCheckException(GlobalCheckErrors.APPLY_RULE_FAILED, e);
		}
		return kieContainer.getKieBase();
	}

	/**
	 * Gets the unique path.
	 *
	 * @param routeRuleName
	 *            the route rule name
	 * @return the unique path
	 */
	private static synchronized String getUniquePath(String routeRuleName) {
		return "src/main/resources/rules/" + System.nanoTime() + "/" + routeRuleName;
	}

	/**
	 * Evaluate rule.
	 *
	 * @param orgnaizationCode
	 *            the orgnaization code
	 * @param isoAlpha3CountryCode
	 *            the iso alpha 3 country code
	 * @param state
	 *            the state
	 * @return the state rule result
	 */
	public StateRuleResult evaluateRule(String orgnaizationCode, String isoAlpha3CountryCode, String state,
			String orgLegalEntity) {
		StateParam sp = new StateParam(isoAlpha3CountryCode, state, orgnaizationCode, orgLegalEntity);
		StatelessKieSession ksession = kieBase.newStatelessKieSession();
		ksession.execute(sp);
		if (sp.getResult() == null)
			sp.setResult(Constants.NOT_PERFORMED);
		return StateRuleResult.valueOf(sp.getResult());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.globalcheck.staterulecheck.
	 * IRuleService#applyRule(com.currenciesdirect.gtg.compliance.core.domain.
	 * globalcheck.StateRuleRequest)
	 */
	@Override
	public GlobalCheckContactResponse applyRule(StateRuleRequest request) throws GlobalCheckException {
		GlobalCheckContactResponse checkResponse = new GlobalCheckContactResponse();
		try {
			// change done to accept name of the state in any format and passing
			// it to database to resolve AT-277 by Vishal J
			StateRuleResult result = evaluateRule(request.getOrgCode(), request.getIsoAlpha3CountryCode(),
					request.getState().toUpperCase(), request.getOrgLegalEntity());
			checkResponse.setStatus(result.name());
			checkResponse.setCountry(request.getIsoAlpha3CountryCode());
			checkResponse.setState(request.getState());
		} catch (Exception e) {
			throw new GlobalCheckException(GlobalCheckErrors.APPLY_RULE_FAILED, e);
		}
		return checkResponse;
	}

}