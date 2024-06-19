package com.currenciesdirect.gtg.compliance.blacklist.core.domain;

import java.util.List;

/**
 * The Class BlacklistSTPIdAndData.
 * 
 * @author Rajesh
 */
public class BlacklistSTPIdAndData {
	private String id;

	private List<BlacklistSTPData> data;
	
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BlacklistSTPData> getData() {
		return data;
	}

	public void setData(List<BlacklistSTPData> data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlacklistSTPIdAndData other = (BlacklistSTPIdAndData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlacklistSTPIdAndData [id=" + id + ", data=" + data + "]";
	}
}
