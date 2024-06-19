package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;


public class IpServiceRequest implements IDomain{

	private String correlationId;
	private List<IpRequestData> searchData;
	
	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public List<IpRequestData> getSearchData() {
		return searchData;
	}

	public void setSearch(List<IpRequestData> search) {
		this.searchData = search;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((searchData == null) ? 0 : searchData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IpServiceRequest other = (IpServiceRequest) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (searchData == null) {
			if (other.searchData != null)
				return false;
		} else if (!searchData.equals(other.searchData))
			return false;
		return true;
	}
	
	public static void main(String... args) throws Exception {

		IpServiceRequest rsr = new IpServiceRequest();
		rsr.setCorrelationId("correlationId");
		IpRequestData data = new IpRequestData();
			List<IpRequestData> lstContact = new ArrayList<>();
		lstContact.add(data);
		//lstContact.add(contact2);
		//lstContact.add(contact3);

		rsr.setSearch(lstContact);
		ObjectMapper jsonObj = new ObjectMapper( );
		
	    System.out.println( jsonObj.writeValueAsString(rsr) );
	}
}
