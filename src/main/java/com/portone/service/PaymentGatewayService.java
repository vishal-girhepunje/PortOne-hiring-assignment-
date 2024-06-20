package com.portone.service;

import java.io.IOException;
import java.util.List;

import com.portone.exception.PaymentException;
import com.portone.model.PaymentIntentRequest;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Refund;


public interface PaymentGatewayService {
	public String createIntentForPayment(PaymentIntentRequest paymentIntentRequest) throws PaymentException;
	
	public String attachPaymentMethod(String paymentIntentId, String paymentMethodId) throws PaymentException;
	
	public String captureTheCreatedIntent(String paymentIntentId) throws PaymentException;
	
	public String createRefundForPaymentIntent(String paymentIntentId) throws PaymentException;
	
	public List<PaymentIntent> getAllTheIntent()throws PaymentException;
	
	public String confirmPayment(PaymentIntent paymentIntent)throws PaymentException;
}