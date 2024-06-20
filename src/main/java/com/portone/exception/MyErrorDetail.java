package com.portone.exception;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Class representing detailed error information.
 * This class is used to structure error responses sent to the client.
 * 
 * The class is annotated with @Data from Lombok, which generates getters, setters,
 * and other utility methods (e.g., toString, equals, hashCode) at compile time.
 */
@Data
public class MyErrorDetail {
	 /**
     * The timestamp when the error occurred.
     */
	private LocalDateTime timestamp;
	/**
     * The error message describing the error.
     */
	private String message;
	/**
     * Additional details about the error.
     */
	private String details;
}
