package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.sql.Timestamp;

import org.springframework.integration.file.FileNameGenerator;
import org.springframework.messaging.Message;

/**
 * The Class FileNameGeneratorImpl.
 */
public class FileNameGeneratorImpl implements FileNameGenerator {

	/** The name. */
	private String name;
	
	/** The ext. */
	private String ext;
	

	/* (non-Javadoc)
	 * @see org.springframework.integration.file.FileNameGenerator#generateFileName(org.springframework.messaging.Message)
	 */
	@Override
	public String generateFileName(Message<?> message) {
		return name+new Timestamp(System.currentTimeMillis())+"."+ext;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the ext.
	 *
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}


	/**
	 * Sets the ext.
	 *
	 * @param ext the new ext
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}
}
