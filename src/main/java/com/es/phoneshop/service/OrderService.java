package com.es.phoneshop.service;

import com.es.phoneshop.model.dao.enums.PaymentMethod;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrderFromCart(Cart cart);

    List<PaymentMethod> getPaymentMethods();

    Order getOrderBySecureId(String secureId);

    void placeOrder(Order order);

    Order getOrderById(Long id);

}
