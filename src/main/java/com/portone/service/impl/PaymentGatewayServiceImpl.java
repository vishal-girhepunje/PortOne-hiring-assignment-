package com.portone.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portone.exception.PaymentException;
import com.portone.model.PaymentIntentRequest;
import com.portone.service.PaymentGatewayService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentListParams;
import com.stripe.param.PaymentIntentUpdateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.RefundCreateParams;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Implementation of the PaymentGatewayService interface for handling payment gateway operations.
 */
@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService{
	@Value("${stripe.api.key}")
	private String stripeApiKey;
	
	
	/**
     * Creates a payment intent based on the provided request.
     * 
     * @param paymentIntentRequest the request object containing payment details
     * @return a string representation of the created payment intent ID
     * @throws PaymentException if there is an error during creation
     */
	@Override
	public String createIntentForPayment(PaymentIntentRequest paymentIntentRequest) throws PaymentException{
		Stripe.apiKey = stripeApiKey;
		
		try {
			PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().
					setAmount(Long.valueOf(paymentIntentRequest.getAmount())).
					setCurrency(paymentIntentRequest.getCurrency()).
					setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL).
					setPaymentMethod("pm_card_visa").
					build();
			
			PaymentIntent paymentIntent= PaymentIntent.create(params);
			
			return confirmPayment(paymentIntent);
		} catch (StripeException stripeException) {
			throw new PaymentException("Error creating PaymentIntent "+stripeException.getMessage());
		}
	}
	
	/**
     * Confirms the specified payment intent by creating a payment method and attaching it.
     * 
     * @param paymentIntent the payment intent to confirm
     * @return a string indicating the result of the confirmation
     * @throws PaymentException if there is an error during confirmation
     */
	@Override
	public String confirmPayment(PaymentIntent paymentIntent) throws PaymentException {
		try {
			String testToken = "tok_visa";
			
			PaymentMethodCreateParams params = PaymentMethodCreateParams.builder().
					setType(PaymentMethodCreateParams.Type.CARD).
					setCard(PaymentMethodCreateParams.Token.builder().
							setToken(testToken).
							build()).
					build();
			PaymentMethod paymentMethod = PaymentMethod.create(params);
			
			return attachPaymentMethod(paymentIntent.getId(), paymentMethod.getId());
		}
		catch (StripeException stripeException) {
			throw new PaymentException("Error creating payment method "+stripeException.getMessage());
		}
	}
	
	/**
     * Helper method to format parameters for HTTP requests.
     * 
     * @param parameters the map of parameters
     * @return a formatted string of parameters
     */
	private String supportFormat(Map<String, String> parameters) {
		StringBuilder parameterInStringBuilder = new StringBuilder("");
		
		for(Map.Entry<String, String> entry: parameters.entrySet()) {
			if(parameterInStringBuilder.length()>0) {
				parameterInStringBuilder.append("&");
			}
			
			parameterInStringBuilder.append(entry.getKey());
			parameterInStringBuilder.append("=");
			parameterInStringBuilder.append(entry.getValue());
		}
		
		return parameterInStringBuilder.toString();
	}

	/**
     * Captures the specified payment intent.
     * 
     * @param paymentIntentId the ID of the payment intent to capture
     * @return a string representation of the captured payment intent
     * @throws PaymentException if there is an error during capture
     */
	@Override
	public String captureTheCreatedIntent(String paymentIntentId) throws PaymentException{
		Stripe.apiKey = stripeApiKey;
		
		try {
			PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

			if ("requires_capture".equals(paymentIntent.getStatus())) {
                PaymentIntent capturedPaymentIntent = paymentIntent.capture();
                
                return capturedPaymentIntent.toJson();
            } else {
                throw new PaymentException("PaymentIntent cannot be captured. Current status: " + paymentIntent.getStatus());
            }
		} catch (StripeException stripeException) {
			throw new PaymentException("Error capturing PaymentIntent "+stripeException.getMessage());
		}
	}
	
	/**
     * Attaches a payment method to the specified payment intent.
     * 
     * @param paymentIntentId the ID of the payment intent
     * @param paymentMethodId the ID of the payment method to attach
     * @return a string indicating the result of the operation
     * @throws PaymentException if there is an error during attachment
     */
	@Override
	public String attachPaymentMethod(String paymentIntentId, String paymentMethodId) throws PaymentException {
		Stripe.apiKey = stripeApiKey;
		
		try {
			PaymentIntentUpdateParams params = PaymentIntentUpdateParams.builder()
					.setPaymentMethod(paymentMethodId).build();
			PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
			PaymentIntent updatedPaymentIntent= paymentIntent.update(params);
			
			updatedPaymentIntent = updatedPaymentIntent.confirm();
			
			return updatedPaymentIntent.toJson();
		}
		catch (StripeException stripeException) {
			throw new PaymentException("Error attach payment method in PaymentIntent "+stripeException.getMessage());
		}
	}

	/**
     * Creates a refund for the specified payment intent.
     * 
     * @param paymentIntentId the ID of the payment intent to refund
     * @return a string representation of the refund
     * @throws PaymentException if there is an error during refund creation
     */
	@Override
	public String createRefundForPaymentIntent(String paymentIntentId) throws PaymentException {
		Stripe.apiKey = stripeApiKey;
		
		try {
			RefundCreateParams params = RefundCreateParams.builder().
					setPaymentIntent(paymentIntentId).
					build();
			Refund refund = Refund.create(params);
			
			return refund.toJson();
		}
		catch (StripeException stripeException) {
			throw new PaymentException("Error creating refund: "+stripeException.getMessage());
		}
	}
	
	/**
     * Retrieves a list of all payment intents.
     * 
     * @return a list of PaymentIntent objects
     * @throws PaymentException if there is an error during retrieval
     */
	@Override
	public List<PaymentIntent> getAllTheIntent() throws PaymentException {
		Stripe.apiKey = stripeApiKey;
		
		try {
			List<PaymentIntent> allPaymentIntent = new ArrayList<>();
			PaymentIntentCollection paymentIntents;
			PaymentIntentListParams params = PaymentIntentListParams.builder().setLimit(100L).build();
			
			do {
				paymentIntents = PaymentIntent.list(params);
				allPaymentIntent.addAll(paymentIntents.getData());
				
				if(paymentIntents.getHasMore()) {
					params = PaymentIntentListParams.builder().
							setStartingAfter(
									paymentIntents.
									getData().
									get(paymentIntents.
											getData().
											size()-1).
									getId()).
							build();
				}
				else {
					break;
				}
			}
			while(paymentIntents.getHasMore());
			
			return allPaymentIntent;
		}
		catch (StripeException stripeException) {
			throw new PaymentException("Error retrieving payment intent: "+ stripeException.getMessage());
		}
	}
}
