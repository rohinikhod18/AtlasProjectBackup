package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

/**
 * The Class Name.
 */
public class Name {

	/** The title. */
	private String title;
	
	/** The prefered name. */
	private String preferedName;
	
	/** The fore name. */
	private String foreName;
	
	/** The middle name. */
	private String middleName;
	
	/** The sur name. */
	private String surName;
	
	/** The second surname. */
	private String secondSurname;
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the prefered name.
	 *
	 * @return the prefered name
	 */
	public String getPreferedName() {
		return preferedName;
	}
	
	/**
	 * Sets the prefered name.
	 *
	 * @param preferedName the new prefered name
	 */
	public void setPreferedName(String preferedName) {
		this.preferedName = preferedName;
	}
	
	/**
	 * Gets the fore name.
	 *
	 * @return the fore name
	 */
	public String getForeName() {
		return foreName;
	}
	
	/**
	 * Sets the fore name.
	 *
	 * @param foreName the new fore name
	 */
	public void setForeName(String foreName) {
		this.foreName = foreName;
	}
	
	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}
	
	/**
	 * Sets the middle name.
	 *
	 * @param middleName the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	/**
	 * Gets the sur name.
	 *
	 * @return the sur name
	 */
	public String getSurName() {
		return surName;
	}
	
	/**
	 * Sets the sur name.
	 *
	 * @param surName the new sur name
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}
	
	/**
	 * Gets the second surname.
	 *
	 * @return the second surname
	 */
	public String getSecondSurname() {
		return secondSurname;
	}
	
	/**
	 * Sets the second surname.
	 *
	 * @param secondSurname the new second surname
	 */
	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}
	
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foreName == null) ? 0 : foreName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((preferedName == null) ? 0 : preferedName.hashCode());
		result = prime * result + ((secondSurname == null) ? 0 : secondSurname.hashCode());
		result = prime * result + ((surName == null) ? 0 : surName.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if(this.foreName != null && other.foreName != null && this.foreName.equals(other.foreName)){
			return true;
		}
		if(this.middleName != null && other.middleName != null && this.middleName.equals(other.middleName)){
			return true;
		}
		if(this.preferedName != null && other.preferedName != null && this.preferedName.equals(other.preferedName)){
			return true;
		}
		if(this.surName != null && other.surName != null && this.surName.equals(other.surName)){
			return true;
		}
		if(this.secondSurname != null && other.secondSurname != null && this.secondSurname.equals(other.secondSurname)){
			return true;
		}
		
		return false;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name [title=" + title + ", preferedName=" + preferedName + ", foreName=" + foreName + ", middleName="
				+ middleName + ", surName=" + surName + ", secondSurname=" + secondSurname + "]";
	}
}
