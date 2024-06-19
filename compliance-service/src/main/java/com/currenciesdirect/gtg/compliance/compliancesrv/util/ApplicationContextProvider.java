package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
	AppContext.setApplicationContext(applicationContext);
	}

}
