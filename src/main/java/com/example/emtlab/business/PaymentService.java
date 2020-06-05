package com.example.emtlab.business;

import com.example.emtlab.model.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws StripeException;
}
