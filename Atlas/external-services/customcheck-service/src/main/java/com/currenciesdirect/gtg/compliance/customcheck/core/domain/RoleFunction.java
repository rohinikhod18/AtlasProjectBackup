/*Copyright Currencies Direct Ltd 2015-2016. All rights reserved
worldwide. Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.*/
package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

/**
 * @author Sonal M.
 * 
 *         The Class RoleFunction.
 */
public class RoleFunction implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private int ID;

	/** The Function ID */
	private int functionId;

	/** The can view. */
	private boolean canView;

	/** The can create. */
	private boolean canCreate;

	/** The can modify. */
	private boolean canModify;

	/** The can delete. */
	private boolean canDelete;

	/**
	 * Instantiates a new role function.
	 */
	public RoleFunction() {
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Sets the id.
	 * 
	 * @param iD
	 *            the new id
	 */
	public void setID(int iD) {
		ID = iD;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	/**
	 * Checks if is can view.
	 * 
	 * @return true, if is can view
	 */
	public boolean isCanView() {
		return canView;
	}

	/**
	 * Sets the can view.
	 * 
	 * @param canView
	 *            the new can view
	 */
	public void setCanView(boolean canView) {
		this.canView = canView;
	}

	/**
	 * Checks if is can create.
	 * 
	 * @return true, if is can create
	 */
	public boolean isCanCreate() {
		return canCreate;
	}

	/**
	 * Sets the can create.
	 * 
	 * @param canCreate
	 *            the new can create
	 */
	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}

	/**
	 * Checks if is can modify.
	 * 
	 * @return true, if is can modify
	 */
	public boolean isCanModify() {
		return canModify;
	}

	/**
	 * Sets the can modify.
	 * 
	 * @param canModify
	 *            the new can modify
	 */
	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}

	/**
	 * Checks if is can delete.
	 * 
	 * @return true, if is can delete
	 */
	public boolean isCanDelete() {
		return canDelete;
	}

	/**
	 * Sets the can delete.
	 * 
	 * @param canDelete
	 *            the new can delete
	 */
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (new StringBuilder()).append(super.toString()).append("[")
				.append(System.lineSeparator()).append("view=").append(canView)
				.append(System.lineSeparator()).append("create=")
				.append(canCreate).append(System.lineSeparator())
				.append("modify=").append(canModify)
				.append(System.lineSeparator()).append("delete=")
				.append(canDelete).append(System.lineSeparator()).append("]")
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (ID);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RoleFunction))
			return false;
		RoleFunction other = (RoleFunction) obj;
		return (ID == other.getID());
	}

}
