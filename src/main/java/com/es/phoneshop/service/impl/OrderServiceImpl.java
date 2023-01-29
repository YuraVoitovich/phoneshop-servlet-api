package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.dao.enums.PaymentMethod;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static volatile OrderServiceImpl instance;

    public static OrderServiceImpl getInstance(OrderDao orderDao) {
        OrderServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OrderServiceImpl(orderDao);
                }
            }
        }
        return localInstance;
    }

    public static OrderServiceImpl getInstance() {
        return getInstance(DAOProvider.getInstance().getOrderDao());
    }
    private final OrderDao orderDao;

    private final List<PaymentMethod> paymentMethods;


    private OrderServiceImpl() {
        this.paymentMethods = Arrays.stream(PaymentMethod.values()).collect(Collectors.toList());
        this.orderDao = DAOProvider.getInstance().getOrderDao();
    }

    private OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.paymentMethods = Arrays.stream(PaymentMethod.values()).collect(Collectors.toList());
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.getOrder(id);
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return this.orderDao.getOrderBySecureId(secureId);
    }

    @Override
    public Order createOrderFromCart(Cart cart) {
        Order order = new Order(cart.getItems().stream().map(CartItem::clone).collect(Collectors.toList()));
        order.setDeliveryCost(new BigDecimal(30));
        order.setSubTotal(cart.getTotalCost().add(order.getDeliveryCost()));
        order.setTotalQuantity(cart.getTotalQuantity());

        return order;
    }


}
