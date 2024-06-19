package com.currenciesdirect.gtg.compliance.authentication.core.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.currenciesdirect.gtg.compliance.authentication.core.IDomain;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AuthResponse")
public class AuthResponse implements Serializable,IDomain{
	
	
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The  username. */
		private String username;
		
		/** The role. */
		private String role;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
		
		

}
