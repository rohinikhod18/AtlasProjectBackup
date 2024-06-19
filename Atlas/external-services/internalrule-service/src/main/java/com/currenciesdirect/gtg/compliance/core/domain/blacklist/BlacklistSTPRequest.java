package com.currenciesdirect.gtg.compliance.core.domain.blacklist;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistData;
import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;


/**
 * The Class BlacklistSTPRequest.
 * 
 * @author Rajesh
 */
public class BlacklistSTPRequest implements IRequest  {

	/** The search. */
	private List<BlacklistData> search;

	/** The correlation id. */
	private String correlationId;

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public List<BlacklistData> getSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param data
	 *            the new search
	 */
	public void setSearch(List<BlacklistData> data) {
		this.search = data;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId
	 *            the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
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
		BlacklistSTPRequest other = (BlacklistSTPRequest) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId)) {
			return false;
		  }
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search)) {
			return false;
		  }
		return true;
	}

	@Override
	public String toString() {
		return "BlacklistSTPRequest [search=" + search + ", correlationId=" + correlationId + "]";
	}
}
