package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseQueueDto;

/**
 * The Class DataAnonDto.
 */
public class DataAnonymisationDto extends BaseQueueDto{
	
	/** The data anonymisation. */
	private List<DataAnonymisationDataRequest> dataAnonymisation;

	/**
	 * Gets the data anon.
	 *
	 * @return the data anon
	 */
	public List<DataAnonymisationDataRequest> getDataAnonymisation() {
		return dataAnonymisation;
	}

	/**
	 * Sets the data anon.
	 *
	 * @param dataAnonymisation the new data anon
	 */
	public void setDataAnonymisation(List<DataAnonymisationDataRequest> dataAnonymisation) {
		this.dataAnonymisation = dataAnonymisation;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataAnonymisation == null) ? 0 : dataAnonymisation.hashCode());
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
		DataAnonymisationDto other = (DataAnonymisationDto) obj;
		if (dataAnonymisation == null) {
			if (other.dataAnonymisation != null)
				return false;
		} else if (!dataAnonymisation.equals(other.dataAnonymisation)) {
			return false;
		  }
		return true;
	}

	@Override
	public String toString() {
		return "DataAnonymisationDto [dataAnonymisation=" + dataAnonymisation+"]";
	}

}
