/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.dbport;

/**
 * The Class KYCQueryConstants.
 *
 * @author manish
 */
public class KYCQueryConstants {
	
	/** The Constant LOAD_KYC_COUNTRY_PROVIDER_MAPPING. */
	public static final String LOAD_KYC_COUNTRY_PROVIDER_MAPPING = "select * from KYC_CountryProviderMapping where ProviderName=?";
	
	/** The Constant LOG_KYC_REQUEST. */
	public static final String LOG_KYC_REQUEST = "INSERT INTO KYC_Service_Details(Correlation_Id,Organization_Code,Source,Request,Request_Type,Created_On,Created_By,Updated_On,Updated_By) VALUES (?,?,?,?,?,?,?,?,?)";
	
	/** The Constant LOG_KYC_RESPONSE. */
	public static final String LOG_KYC_RESPONSE = "UPDATE KYC_Service_Details SET Response=?,Updated_On=?,Updated_By=?,Provider_Name=? WHERE Correlation_Id=? AND Organization_Code=?";
	
	/** The Constant GET_KYC_PROVIDER_CONFIG_PROPERTY. */
	public static final String GET_KYC_PROVIDER_CONFIG_PROPERTY = "select a.Attribute FROM KYC_ServiceProviderAttribute a LEFT JOIN KYC_ServiceProvider b on a.ID = b.ID where b.Code=?";
	
	/** The Constant GET_KYC_PROVIDER_INIT_CONFIG_PROPERTY. */
	public static final String GET_KYC_PROVIDER_INIT_CONFIG_PROPERTY = "select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='KYC')";
	
	/** The Constant GET_SHORT_COUNTRY_CODE. */
	public static final String GET_SHORT_COUNTRY_CODE = "SELECT shortcode FROM country WHERE displayname = ?";
	/**
	 * Instantiates a new KYC query constants.
	 */
	private KYCQueryConstants(){
		
	}
}
