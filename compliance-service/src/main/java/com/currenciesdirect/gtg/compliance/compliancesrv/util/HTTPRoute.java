package com.currenciesdirect.gtg.compliance.compliancesrv.util;

public class HTTPRoute {

	private String hostname;
	private Integer maxConnections;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Integer getMaxConnections() {
		return maxConnections;
	}
	public void setMaxConnections(Integer maxConnections) {
		this.maxConnections = maxConnections;
	}
	public HTTPRoute(String hostname, Integer maxConnections) {
		super();
		this.hostname = hostname;
		this.maxConnections = maxConnections;
	}
		
}
