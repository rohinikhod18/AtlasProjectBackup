package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * The Class FraugsterCsvUploadServerConf.
 */
public class FraugsterCsvUploadServerConf {

	/** The session. */
	private FraugsterCsvUploadSessionConf session;
	
	/** The remote directory. */
	private String remoteDirectory;
	
	/** The chmod. */
	private String chmod;
	
	/** The mode. */
	private String mode;

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public FraugsterCsvUploadSessionConf getSession() {
		return session;
	}

	/**
	 * Sets the session.
	 *
	 * @param session the new session
	 */
	public void setSession(FraugsterCsvUploadSessionConf session) {
		this.session = session;
	}

	/**
	 * Gets the remote directory.
	 *
	 * @return the remote directory
	 */
	public String getRemoteDirectory() {
		return remoteDirectory;
	}

	/**
	 * Sets the remote directory.
	 *
	 * @param remoteDirectory the new remote directory
	 */
	public void setRemoteDirectory(String remoteDirectory) {
		this.remoteDirectory = remoteDirectory;
	}

	/**
	 * Gets the chmod.
	 *
	 * @return the chmod
	 */
	public String getChmod() {
		return chmod;
	}

	/**
	 * Sets the chmod.
	 *
	 * @param chmod the new chmod
	 */
	public void setChmod(String chmod) {
		this.chmod = chmod;
	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
