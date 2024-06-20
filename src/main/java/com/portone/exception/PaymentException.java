package com.portone.exception;

/**
 * Custom exception class for handling payment-related errors.
 * This class extends the standard Exception class to provide
 * additional context for errors occurring in the payment processing.
 */
public class PaymentException extends Exception{
	/**
     * Default constructor for PaymentException.
     * Calls the default constructor of the superclass (Exception).
     */
	public PaymentException() {
		
	}
	/**
     * Constructor for PaymentException with a custom error message.
     * 
     * @param message the custom error message
     */
	public PaymentException(String message) {
		super(message);
	}
}
