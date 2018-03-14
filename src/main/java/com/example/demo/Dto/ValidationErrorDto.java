package com.example.demo.Dto;
/**
 * Data transfer object for validation errors
 * 
 * @author Sardesh Sharma
 *
 */
public class ValidationErrorDto {
		private String field;
		private String message;
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public ValidationErrorDto(String field, String message) {
			super();
			this.field = field;
			this.message = message;
		}
		public ValidationErrorDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
}
