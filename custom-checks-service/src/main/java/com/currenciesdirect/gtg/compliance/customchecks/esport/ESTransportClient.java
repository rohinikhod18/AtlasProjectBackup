package com.currenciesdirect.gtg.compliance.customchecks.esport;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.LogConfigurator;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("squid:S2095")
public class ESTransportClient {
	private static final Logger LOG = LoggerFactory.getLogger(ESTransportClient.class);
	private static TransportClient client ;
	
	private ESTransportClient() {
	}

	@SuppressWarnings("resource")
	static void init() {

		try {
			Settings settings = Settings.builder().put("http.enabled", true)
					.put("cluster.name", System.getProperty("elastic.server.cluster"))
					//.put("node.name", System.getProperty("elastic.server.node"))
					.put("path.conf", System.getProperty("elastic.log.conf.path"))
					.put("client.transport.ping_timeout", "100s")
					.put("client.transport.sniff", true) // changes to true as per Abhay's suggestion
					.put("client.transport.ignore_cluster_name", true).build();
			LogConfigurator.configureWithoutConfig(settings);
			
			Integer port = Integer.parseInt(System.getProperty("elastic.server.port"));
			
			//client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				//	InetAddress.getByName(System.getProperty("elastic.server.ip")),port));
			
			client = new PreBuiltTransportClient(settings);
			String serverIPs = System.getProperty("elastic.server.ip");  // property file will have all three IP, comma separated.
			String[] listServerIPs = serverIPs.split(",");
			for(String serverIP:listServerIPs) {
				client.addTransportAddress(new InetSocketTransportAddress(
							InetAddress.getByName(serverIP),port));
			}

		} catch (Exception e) {
			LOG.warn("error looking up elastic instance", e);
		}
	}


	public static TransportClient getInstance() {
		if (client == null) {
			init();
		}
		return client;
	}
	
	
	/**
	 * Close.
	 */
	public void close(){
		client.close();
	}
	
	
	
}