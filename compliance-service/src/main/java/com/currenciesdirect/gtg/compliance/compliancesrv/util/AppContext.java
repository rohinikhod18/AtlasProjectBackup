/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import org.springframework.context.ApplicationContext;

/**
 * The Class AppContext.
 *
 * @author manish
 */
public class AppContext {
	
	/** The ctx. */
	private static ApplicationContext ctx;
	
	/**
	 * Instantiates a new app context.
	 */
	private AppContext (){
		
	}

	
	/**
	 * Sets the application context.
	 *
	 * @param context the new application context
	 */
	public static void setApplicationContext(ApplicationContext context){
		ctx = context;
	}
	
	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	public static ApplicationContext getApplicationContext(){
		return ctx;
	}
}
