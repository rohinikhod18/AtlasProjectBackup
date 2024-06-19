/*Copyright Currencies Direct Ltd 2015-2016. All rights reserved
worldwide. Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.*/
package com.currenciesdirect.gtg.compliance.authentication.util;

import java.security.MessageDigest;

/**
 * @author Manish
 * 
 */
public class PasswordUtil {

	/**
	 * @param saltInDatabase
	 * @param passwordInDatabase
	 * @param userEnteredPasswordInClearText
	 * @return
	 */
	public boolean verifyPassword(String saltInDatabase,
			String passwordInDatabase, String userEnteredPasswordInClearText) {

		boolean isValidUser = false;
		try {

			String generatedPassword = generatePassword(saltInDatabase,
					userEnteredPasswordInClearText);
			if (generatedPassword.equals(passwordInDatabase)) {
				isValidUser = true;
			} else if (!generatedPassword.equals(passwordInDatabase)) {
				isValidUser = false;
			}

		} catch (Exception e) {

		}

		return isValidUser;
	}

	/**
	 * @param salt
	 * @param userEnteredPasswordInClearText
	 * @return
	 */
	public static String generatePassword(String salt,
			String userEnteredPasswordInClearText) {

		String generatedPassword = null;
		MessageDigest sha256 = null;
		try {
			// Create SHA-256 hashing...
			sha256 = MessageDigest.getInstance("SHA-256");
			// Concat password and salt
			byte[] passBytes = (salt.concat(userEnteredPasswordInClearText))
					.getBytes();
			// Hash both
			byte[] passHash = sha256.digest(passBytes);
			String hashedPassword = new String(
					org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64
							.encodeBase64(passHash));
			// / This should go in database as a hashed password
			generatedPassword = new String(hashedPassword);
		} catch (Exception e) {
		} finally {
			sha256 = null;
		}
		return generatedPassword;
	}
}
