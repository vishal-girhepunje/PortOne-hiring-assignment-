package com.portone.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler for handling exceptions across the entire application.
 * This class uses @RestControllerAdvice to apply exception handling to all controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
     * Handles NoHandlerFoundException (404 errors).
     * 
     * @param noHandlerFoundException the exception to handle
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error details and HTTP status code
     */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetail> notFoundExceptionHandler(NoHandlerFoundException noHandlerFoundException, WebRequest webRequest){
		MyErrorDetail myErrorDetail= new MyErrorDetail();
		
		myErrorDetail.setTimestamp(LocalDateTime.now());
		myErrorDetail.setMessage(noHandlerFoundException.getMessage());
		myErrorDetail.setDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<MyErrorDetail>(myErrorDetail, HttpStatus.BAD_REQUEST);
	}
	/**
     * Handles MethodArgumentNotValidException (validation errors).
     * 
     * @param methodArgumentNotValidException the exception to handle
     * @return a ResponseEntity containing the error details and HTTP status code
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetail> validationExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
		MyErrorDetail myErrorDetail= new MyErrorDetail();
		
		myErrorDetail.setTimestamp(LocalDateTime.now());
		myErrorDetail.setMessage("Validation error");
		myErrorDetail.setDetails(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<MyErrorDetail>(myErrorDetail, HttpStatus.BAD_GATEWAY);
	}
	 /**
     * Handles custom PaymentException.
     * 
     * @param portoneException the exception to handle
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error details and HTTP status code
     */
	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<MyErrorDetail> portoneExceptionHandler(PaymentException portoneException, WebRequest webRequest){
		MyErrorDetail myErrorDetail = new MyErrorDetail();
		
		myErrorDetail.setTimestamp(LocalDateTime.now());
		myErrorDetail.setMessage(portoneException.getMessage());
		myErrorDetail.setDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<>(myErrorDetail, HttpStatus.BAD_REQUEST);
	}
	/**
     * Handles generic exceptions.
     * 
     * @param exception the exception to handle
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error details and HTTP status code
     */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetail> exceptionHandler(Exception exception, WebRequest webRequest){
		MyErrorDetail myErrorDetail = new MyErrorDetail();
		
		myErrorDetail.setTimestamp(LocalDateTime.now());
		myErrorDetail.setMessage(exception.getMessage());
		myErrorDetail.setDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<>(myErrorDetail, HttpStatus.BAD_REQUEST);
	}
}
