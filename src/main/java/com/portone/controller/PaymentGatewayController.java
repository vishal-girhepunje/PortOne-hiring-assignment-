package com.portone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portone.exception.PaymentException;
import com.portone.model.PaymentIntentRequest;
import com.portone.service.PaymentGatewayService;
import com.stripe.model.PaymentIntent;

@RestController
public class PaymentGatewayController {
	@Autowired
	private PaymentGatewayService paymentGatewayService;
	/**
     * Creates a payment intent.
     * 
     * @param paymentIntentRequest the request body containing payment details
     * @return the response entity with the status and message
     * @throws PaymentException if there is an error creating the payment intent
     */
	@PostMapping("/api/v1/create_intent")
	public ResponseEntity<String> createIntentForPaymentHandler(@RequestBody PaymentIntentRequest paymentIntentRequest) throws PaymentException {
		return new ResponseEntity<> (paymentGatewayService.createIntentForPayment(paymentIntentRequest), HttpStatus.ACCEPTED);
	}
	/**
     * Captures the created payment intent.
     * 
     * @param paymentIntentId the ID of the payment intent to capture
     * @return the response entity with the status and message
     * @throws PaymentException if there is an error capturing the payment intent
     */
	@PostMapping("/api/v1/capture_intent/{id}")
	public ResponseEntity<String> captureTheCreatedIntentHandler(@PathVariable("id") String paymentIntentId) throws PaymentException {
		return new ResponseEntity<> (paymentGatewayService.captureTheCreatedIntent(paymentIntentId), HttpStatus.ACCEPTED);
	}
	/**
     * Creates a refund for the specified payment intent.
     * 
     * @param paymentIntentId the ID of the payment intent to refund
     * @return the response entity with the status and message
     * @throws PaymentException if there is an error creating the refund
     */
	@PostMapping("/api/v1/create_refund/{id}")
	public ResponseEntity<String> createTheRefundByPaymentIntentHandler(@PathVariable("id") String paymentIntentId) throws PaymentException {
		return new ResponseEntity<> (paymentGatewayService.createRefundForPaymentIntent(paymentIntentId), HttpStatus.ACCEPTED);
	}
	/**
     * Retrieves all payment intents.
     * 
     * @return the response entity with the list of payment intents
     * @throws PaymentException if there is an error retrieving the payment intents
     */
	@GetMapping("/api/v1/get_intents")
	public ResponseEntity<List<PaymentIntent>> getAllTheIntentsHandler()throws PaymentException{
		return new ResponseEntity<>(paymentGatewayService.getAllTheIntent(), HttpStatus.OK);
	}
}
