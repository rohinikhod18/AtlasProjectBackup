package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.config;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author prashant.verma
 */

@Configuration
public class KafkaTxConfig {

	@Value("${datalake.tx.failedretry.threadpool.size:10}")
	public int kafkaTxFailedRetryExecutorThreadPoolSize;
	
	@Value("${datalake.tx.primaryexecutor.threadpool.size:10}")
	public int kafkaTxPrimaryExecutorThreadPoolSize;
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaTxConfig.class);

	@Bean("KafkaTxFailedRetryExecutorService")
    public ExecutorService kafkaTxFailedRetryExecutorService() {
		LOG.info("KafkaTxConfig : kafkaTxFailedRetryExecutorService initialized with threadpool size : "+kafkaTxFailedRetryExecutorThreadPoolSize);
        return Executors.newFixedThreadPool(kafkaTxFailedRetryExecutorThreadPoolSize); 
    }
	
	@Bean("KafkaTxExecutorService")
    public ExecutorService kafkaTxExecutorService() {
		LOG.info("KafkaTxConfig : kafkaTxExecutorService initialized with threadpool size : "+kafkaTxPrimaryExecutorThreadPoolSize);
        return Executors.newFixedThreadPool(kafkaTxPrimaryExecutorThreadPoolSize); 
    }
}
