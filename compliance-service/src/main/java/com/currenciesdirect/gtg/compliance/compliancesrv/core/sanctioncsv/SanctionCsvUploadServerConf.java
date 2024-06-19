package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

/**
 * The Class SanctionCsvUploadServerConf.
 */
public class SanctionCsvUploadServerConf {
	
	/** The sanction csv upload session conf. */
	private SanctionCsvUploadSessionConf session;
	
	/** The remote directory. */
	private String remoteDirectory;
	
	/** The chmod. */
	private String chmod;
	
	/** The mode. */
	private String mode;

	/**
	 * @return the session
	 */
	public SanctionCsvUploadSessionConf getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(SanctionCsvUploadSessionConf session) {
		this.session = session;
	}

	/**
	 * @return the remoteDirectory
	 */
	public String getRemoteDirectory() {
		return remoteDirectory;
	}

	/**
	 * @param remoteDirectory the remoteDirectory to set
	 */
	public void setRemoteDirectory(String remoteDirectory) {
		this.remoteDirectory = remoteDirectory;
	}

	/**
	 * @return the chmod
	 */
	public String getChmod() {
		return chmod;
	}

	/**
	 * @param chmod the chmod to set
	 */
	public void setChmod(String chmod) {
		this.chmod = chmod;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	

}
