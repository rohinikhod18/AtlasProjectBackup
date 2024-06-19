package com.currenciesdirect.gtg.compliance.core;

import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.dbport.IpDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.IpException;





/**
 * @author manish
 * 
 */
public class IpConcreteDataBuilder extends IpAbstractCacheDataBuilder {

	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(IpConcreteDataBuilder.class);

	private static IpConcreteDataBuilder ipConcreteDataBuilder = null;
	
	private IDBService dbServiceImpl = IpDBServiceImpl.getInstance();

	
	private IpConcreteDataBuilder() {
		System.out.println("Contructor created");
	}

	public static IpConcreteDataBuilder getInstance() {
		if (ipConcreteDataBuilder == null) {
			ipConcreteDataBuilder = new IpConcreteDataBuilder();
		}
		return ipConcreteDataBuilder;
	}
	
	/*static{
		KYCConcreteDataBuilder.getInstance().loadCache();
	}*/

	@Override
	public void loadCache() {
		try {
			/*ConcurrentHashMap<String, List<String>> hm = new ConcurrentHashMap<String, List<String>>();

			hm = ikycDao.fetchKYCInitData();
			kYCCacheDataStructure.setCountryList(hm); */
			ConcurrentHashMap<String, IpProviderProperty> hm = new ConcurrentHashMap<String, IpProviderProperty>();
			hm = dbServiceImpl.getIPProviderInitConfigProperty();
			ipCacheDataStructure.setIpProviderinitConfigMap(hm);
		} catch (IpException ipException) {
			LOG.error("Error in class B2BConcreteDataBuilder loadCache() :",
					ipException);
		} catch (Exception e) {
			LOG.error("Error in class B2BConcreteDataBuilder loadCache() :", e);
		}
		
	}

	@Override
	public IpProviderProperty getProviderInitConfigProperty(String providerName) throws IpException {
		IpProviderProperty property = new IpProviderProperty();
		try {
			property =  ipCacheDataStructure.GetProviderinitConfigMap(providerName);
		} catch (Exception e) {
			LOG.error("Error in class B2BConcreteDataBuilder getProviderInitConfigProperty() :", e);
			throw new IpException(IpErrors.ERROR_WHILE_FETCHING_CACHE_DATA,e);
		}
		return property;
	}

	

	

}

	

