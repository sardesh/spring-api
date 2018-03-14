package com.example.demo.Exception;

/**
 * 
 * @author Sardesh Sharma
 *
 */
public class GenericException extends Exception {

	/**
	 * Default serialization id
	 */
	private static final long serialVersionUID = 1123;
	
	private String code;
	
	private String message;

	public GenericException(String code, String message){
		super(message);
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
