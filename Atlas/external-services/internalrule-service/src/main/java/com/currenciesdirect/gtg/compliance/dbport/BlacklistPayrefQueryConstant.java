package com.currenciesdirect.gtg.compliance.dbport;

public class BlacklistPayrefQueryConstant 
{
	public static final String GET_BLACKLISTPAYREF_PROVIDER_INIT_CONFIG_PROPERTY = "select\r\n" + 
			" a.Attribute,\r\n" + 
			" b.Code\r\n" + 
			" FROM\r\n" + 
			" compliance_ServiceProviderAttribute a\r\n" + 
			" LEFT JOIN compliance_ServiceProvider b on\r\n" + 
			" a.ID = b.ID\r\n" + 
			" where\r\n" + 
			" b.internal = 0\r\n" + 
			" and b.servicetype =(\r\n" + 
			" SELECT\r\n" + 
			" ID\r\n" + 
			" FROM\r\n" + 
			" Compliance_ServiceTypeEnum\r\n" + 
			" WHERE\r\n" + 
			" Code = 'BLACKLIST_PAY_REF')";
	private BlacklistPayrefQueryConstant()
	{
		
	}
}
