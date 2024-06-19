package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching;
  
import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfig {
	
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CachingConfig.class);

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("sanctionResponseCache")));
		LOG.info("----------------Simple Cache Manager started------------------");
		return simpleCacheManager;
	}
}