package com.es.phoneshop.service;

public interface OrderDataValidationService {
    boolean validatePhoneNumber(String phoneNumber);

    boolean validateEmptiness(String toValidate);

    boolean validatePaymentMethod(String paymentMethod);

    boolean validateDate(String date);
}
