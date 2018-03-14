package com.example.demo.Dto;

/**
 * data transfer object for Token
 * @author Sardesh Sharma
 *
 */
public class TokenDto {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenDto(String token) {
		super();
		this.token = token;
	}

	public TokenDto() {
		super();
	}
	
	
	
	
}
