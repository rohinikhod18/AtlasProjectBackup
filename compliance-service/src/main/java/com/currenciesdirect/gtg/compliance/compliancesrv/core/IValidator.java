package com.currenciesdirect.gtg.compliance.compliancesrv.core;


import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

public interface IValidator {

	public Boolean validateRequest(Contact contact);
}
