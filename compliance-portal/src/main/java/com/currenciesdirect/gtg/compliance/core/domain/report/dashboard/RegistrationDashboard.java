package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

/**
 * The Class RegistrationDashboard.
 * @author abhijeetg
 */
public class RegistrationDashboard  {

	/** The total reg records. */
	private Integer totalRegRecords;
	
	/** The total pfx records. */
	private Integer totalPfxRecords;
	
	/** The total cfx records. */
	private Integer totalCfxRecords;
	
	/** The percent pfx records. */
	private String percentPfxRecords;
	
	/** The percent cfx records. */
	private String percentCfxRecords;
	
	/** The reg pfx by geography. */
	private Geography regPfxByGeography;
	
	/** The reg cfx by geography. */
	private Geography regCfxByGeography;
	
	/** The reg pfx by geography json string. */
	private String regPfxByGeographyJsonString;
	
	/** The reg cfx by geography. */
	private String regCfxByGeographyJsonString;

	/** The reg by business unit. */
	private BusinessUnit regPfxByBusinessUnit;
	
	/** The reg cfx by business unit. */
	private BusinessUnit regCfxByBusinessUnit;
	
	/** The reg by business unit. */
	private String regPfxByBusinessUnitJsonString;
	
	/** The reg cfx by business unit. */
	private String regCfxByBusinessUnitJsonString;
	
	/** The reg fulfilment. */
	private Fulfilment regPfxFulfilment;
	
	/** The reg cfx fulfilment. */
	private Fulfilment regCfxFulfilment;
	
	/** The reg fulfilment. */
	private String regPfxFulfilmentJsonString;
	
	/** The reg cfx fulfilment. */
	private String regCfxFulfilmentJsonString;
	
	/** The reg timeline snapshot. */
	private TimelineSnapshot regPfxTimelineSnapshot;
	
	/** The reg cfx timeline snapshot. */
	private TimelineSnapshot regCfxTimelineSnapshot;

	/**
	 * Gets the total reg records.
	 *
	 * @return the total reg records
	 */
	public Integer getTotalRegRecords() {
		return totalRegRecords;
	}

	/**
	 * Sets the total reg records.
	 *
	 * @param totalRegRecords the new total reg records
	 */
	public void setTotalRegRecords(Integer totalRegRecords) {
		this.totalRegRecords = totalRegRecords;
	}

	/**
	 * Gets the total pfx records.
	 *
	 * @return the total pfx records
	 */
	public Integer getTotalPfxRecords() {
		return totalPfxRecords;
	}

	/**
	 * Sets the total pfx records.
	 *
	 * @param totalPfxRecords the new total pfx records
	 */
	public void setTotalPfxRecords(Integer totalPfxRecords) {
		this.totalPfxRecords = totalPfxRecords;
	}

	/**
	 * Gets the total cfx records.
	 *
	 * @return the total cfx records
	 */
	public Integer getTotalCfxRecords() {
		return totalCfxRecords;
	}

	/**
	 * Sets the total cfx records.
	 *
	 * @param totalCfxRecords the new total cfx records
	 */
	public void setTotalCfxRecords(Integer totalCfxRecords) {
		this.totalCfxRecords = totalCfxRecords;
	}

	/**
	 * Gets the percent pfx records.
	 *
	 * @return the percent pfx records
	 */
	public String getPercentPfxRecords() {
		return percentPfxRecords;
	}

	/**
	 * Sets the percent pfx records.
	 *
	 * @param percentPfxRecords the new percent pfx records
	 */
	public void setPercentPfxRecords(String percentPfxRecords) {
		this.percentPfxRecords = percentPfxRecords;
	}

	/**
	 * Gets the percent cfx records.
	 *
	 * @return the percent cfx records
	 */
	public String getPercentCfxRecords() {
		return percentCfxRecords;
	}

	/**
	 * Sets the percent cfx records.
	 *
	 * @param percentCfxRecords the new percent cfx records
	 */
	public void setPercentCfxRecords(String percentCfxRecords) {
		this.percentCfxRecords = percentCfxRecords;
	}

	/**
	 * Gets the reg pfx by geography.
	 *
	 * @return the reg pfx by geography
	 */
	public Geography getRegPfxByGeography() {
		return regPfxByGeography;
	}

	/**
	 * Sets the reg pfx by geography.
	 *
	 * @param regPfxByGeography the new reg pfx by geography
	 */
	public void setRegPfxByGeography(Geography regPfxByGeography) {
		this.regPfxByGeography = regPfxByGeography;
	}

	/**
	 * Gets the reg cfx by geography.
	 *
	 * @return the reg cfx by geography
	 */
	public Geography getRegCfxByGeography() {
		return regCfxByGeography;
	}

	/**
	 * Sets the reg cfx by geography.
	 *
	 * @param regCfxByGeography the new reg cfx by geography
	 */
	public void setRegCfxByGeography(Geography regCfxByGeography) {
		this.regCfxByGeography = regCfxByGeography;
	}

	/**
	 * Gets the reg pfx by busRegistrationDashboardiness unit.
	 *
	 * @return the reg pfx by business unit
	 */
	public BusinessUnit getRegPfxByBusinessUnit() {
		return regPfxByBusinessUnit;
	}

	/**
	 * Sets the reg pfx by business unit.
	 *
	 * @param regPfxByBusinessUnit the new reg pfx by business unit
	 */
	public void setRegPfxByBusinessUnit(BusinessUnit regPfxByBusinessUnit) {
		this.regPfxByBusinessUnit = regPfxByBusinessUnit;
	}

	/**
	 * Gets the reg cfx by business unit.
	 *
	 * @return the reg cfx by business unit
	 */
	public BusinessUnit getRegCfxByBusinessUnit() {
		return regCfxByBusinessUnit;
	}

	/**
	 * Sets the reg cfx by business unit.
	 *
	 * @param regCfxByBusinessUnit the new reg cfx by business unit
	 */
	public void setRegCfxByBusinessUnit(BusinessUnit regCfxByBusinessUnit) {
		this.regCfxByBusinessUnit = regCfxByBusinessUnit;
	}

	/**
	 * Gets the reg pfx fulfilment.
	 *
	 * @return the reg pfx fulfilment
	 */
	public Fulfilment getRegPfxFulfilment() {
		return regPfxFulfilment;
	}

	/**
	 * Sets the reg pfx fulfilment.
	 *
	 * @param regPfxFulfilment the new reg pfx fulfilment
	 */
	public void setRegPfxFulfilment(Fulfilment regPfxFulfilment) {
		this.regPfxFulfilment = regPfxFulfilment;
	}

	/**
	 * Gets the reg cfx fulfilment.
	 *
	 * @return the reg cfx fulfilment
	 */
	public Fulfilment getRegCfxFulfilment() {
		return regCfxFulfilment;
	}

	/**
	 * Sets the reg cfx fulfilment.
	 *
	 * @param regCfxFulfilment the new reg cfx fulfilment
	 */
	public void setRegCfxFulfilment(Fulfilment regCfxFulfilment) {
		this.regCfxFulfilment = regCfxFulfilment;
	}

	/**
	 * Gets the reg pfx timeline snapshot.
	 *
	 * @return the reg pfx timeline snapshot
	 */
	public TimelineSnapshot getRegPfxTimelineSnapshot() {
		return regPfxTimelineSnapshot;
	}

	/**
	 * Sets the reg pfx timeline snapshot.
	 *
	 * @param regPfxTimelineSnapshot the new reg pfx timeline snapshot
	 */
	public void setRegPfxTimelineSnapshot(TimelineSnapshot regPfxTimelineSnapshot) {
		this.regPfxTimelineSnapshot = regPfxTimelineSnapshot;
	}

	/**
	 * Gets the reg cfx timeline snapshot.
	 *
	 * @return the reg cfx timeline snapshot
	 */
	public TimelineSnapshot getRegCfxTimelineSnapshot() {
		return regCfxTimelineSnapshot;
	}

	/**RegistrationDashboard
	 * Sets the reg cfx timeline snapshot.
	 *
	 * @param regCfxTimelineSnapshot the new reg cfx timeline snapshot
	 */
	public void setRegCfxTimelineSnapshot(TimelineSnapshot regCfxTimelineSnapshot) {
		this.regCfxTimelineSnapshot = regCfxTimelineSnapshot;
	}

	/**
	 * Gets the reg pfx by business unit json string.
	 *
	 * @return the reg pfx by business unit json string
	 */
	public String getRegPfxByBusinessUnitJsonString() {
		return regPfxByBusinessUnitJsonString;
	}

	/**
	 * Sets the reg pfx by business unit json string.
	 *
	 * @param regPfxByBusinessUnitJsonString the new reg pfx by business unit json string
	 */
	public void setRegPfxByBusinessUnitJsonString(String regPfxByBusinessUnitJsonString) {
		this.regPfxByBusinessUnitJsonString = regPfxByBusinessUnitJsonString;
	}

	/**
	 * Gets the reg cfx by business unit json string.
	 *
	 * @return the reg cfx by business unit json string
	 */
	public String getRegCfxByBusinessUnitJsonString() {
		return regCfxByBusinessUnitJsonString;
	}

	/**
	 * Sets the reg cfx by business unit json string.
	 *
	 * @param regCfxByBusinessUnitJsonString the new reg cfx by business unit json string
	 */
	public void setRegCfxByBusinessUnitJsonString(String regCfxByBusinessUnitJsonString) {
		this.regCfxByBusinessUnitJsonString = regCfxByBusinessUnitJsonString;
	}

	/**
	 * Gets the reg pfx by geography json string.
	 *
	 * @return the reg pfx by geography json string
	 */
	public String getRegPfxByGeographyJsonString() {
		return regPfxByGeographyJsonString;
	}

	/**
	 * Sets the reg pfx by geography json string.
	 *
	 * @param regPfxByGeographyJsonString the new reg pfx by geography json string
	 */
	public void setRegPfxByGeographyJsonString(String regPfxByGeographyJsonString) {
		this.regPfxByGeographyJsonString = regPfxByGeographyJsonString;
	}

	/**
	 * Gets the reg cfx by geography json string.
	 *
	 * @return the reg cfx by geography json string
	 */
	public String getRegCfxByGeographyJsonString() {
		return regCfxByGeographyJsonString;
	}

	/**
	 * Sets the reg cfx by geography json string.
	 *
	 * @param regCfxByGeographyJsonString the new reg cfx by geography json string
	 */
	public void setRegCfxByGeographyJsonString(String regCfxByGeographyJsonString) {
		this.regCfxByGeographyJsonString = regCfxByGeographyJsonString;
	}

	/**
	 * Gets the reg pfx fulfilment json string.
	 *
	 * @return the reg pfx fulfilment json string
	 */
	public String getRegPfxFulfilmentJsonString() {
		return regPfxFulfilmentJsonString;
	}

	/**
	 * Sets the reg pfx fulfilment json string.
	 *
	 * @param regPfxFulfilmentJsonString the new reg pfx fulfilment json string
	 */
	public void setRegPfxFulfilmentJsonString(String regPfxFulfilmentJsonString) {
		this.regPfxFulfilmentJsonString = regPfxFulfilmentJsonString;
	}

	/**
	 * Gets the reg cfx fulfilment json string.
	 *
	 * @return the reg cfx fulfilment json string
	 */
	public String getRegCfxFulfilmentJsonString() {
		return regCfxFulfilmentJsonString;
	}

	/**
	 * Sets the reg cfx fulfilment json string.
	 *
	 * @param regCfxFulfilmentJsonString the new reg cfx fulfilment json string
	 */
	public void setRegCfxFulfilmentJsonString(String regCfxFulfilmentJsonString) {
		this.regCfxFulfilmentJsonString = regCfxFulfilmentJsonString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegistrationDashboard [totalRegRecords=" + totalRegRecords + ", totalPfxRecords=" + totalPfxRecords
				+ ", totalCfxRecords=" + totalCfxRecords + ", percentPfxRecords=" + percentPfxRecords
				+ ", percentCfxRecords=" + percentCfxRecords + ", regPfxByGeography=" + regPfxByGeography
				+ ", regCfxByGeography=" + regCfxByGeography + ", regPfxByGeographyJsonString="
				+ regPfxByGeographyJsonString + ", regCfxByGeographyJsonString=" + regCfxByGeographyJsonString
				+ ", regPfxByBusinessUnit=" + regPfxByBusinessUnit + ", regCfxByBusinessUnit=" + regCfxByBusinessUnit
				+ ", regPfxByBusinessUnitJsonString=" + regPfxByBusinessUnitJsonString
				+ ", regCfxByBusinessUnitJsonString=" + regCfxByBusinessUnitJsonString + ", regPfxFulfilment="
				+ regPfxFulfilment + ", regCfxFulfilment=" + regCfxFulfilment + ", regPfxFulfilmentJsonString="
				+ regPfxFulfilmentJsonString + ", regCfxFulfilmentJsonString=" + regCfxFulfilmentJsonString
				+ ", regPfxTimelineSnapshot=" + regPfxTimelineSnapshot + ", regCfxTimelineSnapshot="
				+ regCfxTimelineSnapshot + "]";
	}

}
