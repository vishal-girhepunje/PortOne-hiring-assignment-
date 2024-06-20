package com.portone.model;

import lombok.Data;

/**
 * Class representing a request to create a payment intent.
 * This class is used to structure the request body when creating a new payment intent.
 * 
 * The class is annotated with @Data from Lombok, which generates getters, setters,
 * and other utility methods (e.g., toString, equals, hashCode) at compile time.
 */
@Data
public class PaymentIntentRequest {
	 /**
     * The amount to be charged in the smallest currency unit (e.g., cents for USD).
     */
	private int amount;
	/**
     * The currency in which the payment is to be made (e.g., "USD").
     */
	private String currency;
}