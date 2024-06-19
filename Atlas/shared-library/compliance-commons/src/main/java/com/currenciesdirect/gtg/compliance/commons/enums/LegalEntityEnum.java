/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.enums;

/**
 * @author basits
 *
 */
public enum LegalEntityEnum {
	
	CDLGB("CDLGB","Currencies Direct UK"),
	
	CDINC("CDINC","Currencies Direct US"),
	
	CDLCA("CDLCA","Currencies Direct Canada"),
	
	CDLEU("CDLEU","Currencies Direct EU"),
	
	TORGB("TORGB","TorFX UK"),
	
	TOREU("TOREU","TorFX EU"),
	
	FCGGB("FCGGB","FCG"),
	
	FCGEU("FCGEU","FCG EU"),
	
	TORAU("TORAU","TorFX Australia"),
	
	CDLZA("CDLZA","Currencies Direct SA"),
	
	E4FZA("E4FZA","E4F"),
	
	CDLSG("CDLSG","Currencies Direct Singapore"),
	
	TORSG("TORSG","TorFX Singapore"),
	
	FCGSG("FCGSG","FCG Singapore");
	
	private String leCode;
	
	private String leName;
	
	/**
	 * Instantiates a new legal entity enum.
	 *
	 * @param leCode the le code
	 * @param leName the le name
	 */
	LegalEntityEnum(String leCode,String leName){
		this.leCode = leCode;
		this.leName = leName;
	}

	/**
	 * Gets the LE code.
	 *
	 * @return the LE code
	 */
	public String getLECode() {
		return leCode;
	}
	
	/**
	 * Gets the LE name.
	 *
	 * @return the LE name
	 */
	public String getLEName() {
		return leName;
	}
	
	/**
	 * Gets the LE name for code.
	 *
	 * @param leCode the le code
	 * @return the LE name for code
	 */
	public static String getLENameForCode(String leCode) {
		for(LegalEntityEnum legalEntity : LegalEntityEnum.values()) {
			if(legalEntity.getLECode().equals(leCode)) {
				return legalEntity.getLEName();
			}
		}
		return null;
	}
	
	/**
	 * AT-3398
	 * Checks if is EU legal entity.
	 *
	 * @param legalEntity the legal entity
	 * @return true, if is EU legal entity
	 */
	public static boolean isEULegalEntity(String legalEntity) {
		boolean flag = false;
		if(LegalEntityEnum.CDLEU.getLECode().equalsIgnoreCase(legalEntity) 
				|| (LegalEntityEnum.FCGEU.getLECode().equalsIgnoreCase(legalEntity)
				|| (LegalEntityEnum.TOREU.getLECode().equalsIgnoreCase(legalEntity)))) {
			flag = true;
		}
		return flag;
	}
}
