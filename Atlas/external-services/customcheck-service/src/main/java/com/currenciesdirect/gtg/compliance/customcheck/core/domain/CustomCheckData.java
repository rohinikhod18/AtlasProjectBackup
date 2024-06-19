package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

/**
 * The Class CustomCheckData.
 */
public class CustomCheckData implements IDomain {

	private String dataField;

	private Integer score;

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataField == null) ? 0 : dataField.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
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
		CustomCheckData other = (CustomCheckData) obj;
		if (dataField == null) {
			if (other.dataField != null)
				return false;
		} else if (!dataField.equals(other.dataField))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomCheckData [dataField=" + dataField + ", score=" + score + "]";
	}

}
