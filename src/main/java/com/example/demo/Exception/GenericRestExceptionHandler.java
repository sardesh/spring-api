package com.example.demo.Exception;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.Dto.ValidationErrorDto;
import com.example.demo.utils.AppConstants;
import com.example.demo.utils.ErrorConstants;

/**
 * Generic exception handler class for the application
 * 
 * @author Sardesh Sharma
 *
 */
@RestControllerAdvice
public class GenericRestExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Checks and responds for any generic to app exceptions
	 * 
	 * @param genericException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value=GenericException.class)
	public ResponseEntity<ValidationErrorDto> handleContactException(GenericException cx){
		
		if(cx.getCode().equals(ErrorConstants.EMPTY)){
			return new ResponseEntity<ValidationErrorDto>(HttpStatus.NO_CONTENT);
		}
		
		if(cx.getCode().equals(ErrorConstants.NOT_CREATED)){
			return new ResponseEntity<ValidationErrorDto>(new ValidationErrorDto(AppConstants.CONTACT,cx.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ValidationErrorDto>(new ValidationErrorDto(AppConstants.PHONE_NUMBER_FIELD,cx.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Checks for validation errors in the entities used and responds
	 * 
	 * @param request
	 * @param exception
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value={MethodArgumentNotValidException.class})
	public ResponseEntity<List<ValidationErrorDto>> handleMethodArgumentNotValidExceptions(HttpServletRequest request, MethodArgumentNotValidException ex){
		BindingResult br = ex.getBindingResult();
		List<FieldError> fieldErrors = br.getFieldErrors();
		
		return processFieldErrors(fieldErrors);
	}
	

	
	private ResponseEntity<List<ValidationErrorDto>> processFieldErrors(List<FieldError> fieldErrors){
		List<ValidationErrorDto> validationErrorDtos = new ArrayList<>();
		fieldErrors.stream().forEach(fe -> validationErrorDtos.add(new ValidationErrorDto(fe.getField(),resolveLocalizedErrorMessage(fe))));
		return new ResponseEntity<List<ValidationErrorDto>>(validationErrorDtos,HttpStatus.BAD_REQUEST);
	}
	
	private String resolveLocalizedErrorMessage(FieldError fieldError){
		Locale locale = LocaleContextHolder.getLocale();
		String localizedMessage = messageSource.getMessage(fieldError, locale);
		
		return localizedMessage;
		
	}
	
}
