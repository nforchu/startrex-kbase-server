package com.startrex.kbase.startrexkbase.dto;

import java.io.Serializable;

public class AuthUserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static AuthUserDTO factory() {
		return new AuthUserDTO();
	}
	@Override
	public String toString() {
		return "AuthUserDTO [username=" + username + ", password=" + password + "]";
	}	
	
	
	
}
