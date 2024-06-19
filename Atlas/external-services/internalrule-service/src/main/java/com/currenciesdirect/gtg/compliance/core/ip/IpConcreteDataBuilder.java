package com.currenciesdirect.gtg.compliance.core.ip;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.dbport.ip.IpDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;

/**
 * @author manish
 * 
 */
public class IpConcreteDataBuilder extends IpAbstractCacheDataBuilder {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(IpConcreteDataBuilder.class);

	private static IpConcreteDataBuilder ipConcreteDataBuilder = null;
	
	private IDBService dbServiceImpl = IpDBServiceImpl.getInstance();

	
	private IpConcreteDataBuilder() {
		LOG.debug("Contructor created");
	}

	public static IpConcreteDataBuilder getInstance() {
		if (ipConcreteDataBuilder == null) {
			ipConcreteDataBuilder = new IpConcreteDataBuilder();
		}
		return ipConcreteDataBuilder;
	}
	
	@Override
	public void loadCache() {
		try {
			Map<String, IpProviderProperty> hm;
			hm = dbServiceImpl.getIPProviderInitConfigProperty();
			ipCacheDataStructure.setIpProviderinitConfigMap(hm);
		} catch (IpException ipException) {
			LOG.error("Error in class B2BConcreteDataBuilder loadCache() :",
					ipException);
		} catch (Exception e) {
			LOG.error("Error in class IpConcreteDataBuilder loadCache() :", e);
		}
		
	}

	@Override
	public IpProviderProperty getProviderInitConfigProperty(String providerName) throws IpException {
		IpProviderProperty property = new IpProviderProperty();
		try {
			property =  ipCacheDataStructure.getProviderInitConfigMap(providerName);
		} catch (Exception e) {
			throw new IpException(IpErrors.ERROR_WHILE_FETCHING_CACHE_DATA,e);
		}
		return property;
	}
}

	

