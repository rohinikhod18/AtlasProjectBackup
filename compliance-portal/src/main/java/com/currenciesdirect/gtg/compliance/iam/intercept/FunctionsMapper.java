package com.currenciesdirect.gtg.compliance.iam.intercept;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;

/**
 * Implementation : 
 * 1. Fetch request mapping annotation specific methods and url patterns 
 * 2. from methods and url patterns get functionAllowed Annotation to map function with url
 */
public class FunctionsMapper {

	private Map<String,String> urlMethodMap = new HashMap<>() ;
	
	private Map<String,FunctionsAllowed> urlWithFunctionMap = new HashMap<>();
	

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	/**
	 * Inits functions url specific.
	 */
	@PostConstruct
	public void init() {
	    Map<RequestMappingInfo, HandlerMethod> handlerMethods =
	                              this.requestMappingHandlerMapping.getHandlerMethods();

		for (Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
			RequestMappingInfo mapping = item.getKey();
			HandlerMethod method = item.getValue();
			String urlPattern = (String) mapping.getPatternsCondition().getPatterns().toArray()[0];
			String classWithMethod = method.getBeanType().getName() + "#" + method.getMethod().getName();
			urlMethodMap.put(urlPattern, classWithMethod);
			FunctionsAllowed functionAllowed = method.getMethodAnnotation(FunctionsAllowed.class);
			if (functionAllowed != null) {
				urlWithFunctionMap.put(urlPattern, functionAllowed);
			}
			
		}
	}
	
	/**
	 * Gets the class mehod name.
	 *
	 * @param url the url
	 * @return the class mehod name
	 */
	public String getClassMehodName(String url){
		return urlMethodMap.get(url);
		
	}
	
	/**
	 * Gets the allowed functions.
	 *
	 * @param url the url
	 * @return the allowed functions
	 */
	public FunctionsAllowed getAllowedFunctions(String url){
		return urlWithFunctionMap.get(url);
		
	}
	
}
