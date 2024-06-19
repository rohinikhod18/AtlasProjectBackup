package com.currenciesdirect.gtg.compliance.authentication.core.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.currenciesdirect.gtg.compliance.authentication.core.IDomain;

/**
 * @author manish
 *
 */
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AuthRequest")*/
public class AuthRequest implements Serializable,IDomain{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
    private String passWord;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
    
    
}
