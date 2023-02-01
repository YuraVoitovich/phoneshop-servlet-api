package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.dao.enums.PaymentMethod;
import com.es.phoneshop.service.OrderDataValidationService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class OrderDataValidationServiceImpl implements OrderDataValidationService {

    private static class InstanceHolder {
        private static final OrderDataValidationServiceImpl INSTANCE = new OrderDataValidationServiceImpl();
    }
    public static OrderDataValidationServiceImpl getInstance() {
        return OrderDataValidationServiceImpl.InstanceHolder.INSTANCE;
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        return (phoneNumber != null) && phoneNumber.matches("^\\+375(\\s+)?\\(?(17|29|33|44)\\)?(\\s+)?[0-9]{3}-[0-9]{2}-[0-9]{2}$");
    }

    @Override
    public boolean validatePaymentMethod(String paymentMethod) {
        if (paymentMethod == null) {
            return false;
        }
        try {
            PaymentMethod.valueOf(paymentMethod);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateDate(String date) {
        if (date == null) {
            return false;
        }
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateEmptiness(String toValidate) {
        return (toValidate != null) && !toValidate.trim().isEmpty();
    }

    private OrderDataValidationServiceImpl() {

    }
}
