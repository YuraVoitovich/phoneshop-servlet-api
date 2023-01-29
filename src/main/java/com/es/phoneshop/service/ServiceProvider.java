package com.es.phoneshop.service;

import com.es.phoneshop.security.DoSProtectionService;
import com.es.phoneshop.security.impl.DoSProtectionServiceImpl;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.service.impl.OrderDataValidationServiceImpl;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import com.es.phoneshop.service.impl.RecentlyViewedServiceImpl;

public class ServiceProvider {

    private static class InstanceHolder {
        private static final ServiceProvider INSTANCE = new ServiceProvider();
    }
    public static ServiceProvider getInstance() {
        return ServiceProvider.InstanceHolder.INSTANCE;
    }

    private final CartService cartService;

    private final OrderDataValidationService orderDataValidationService;

    private final OrderService orderService;

    private final RecentlyViewedService recentlyViewedService;

    private final DoSProtectionService doSProtectionService;

    public DoSProtectionService getDoSProtectionService() {
        return doSProtectionService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderDataValidationService getOrderDataValidationService() {
        return orderDataValidationService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public RecentlyViewedService getRecentlyViewedService() {
        return recentlyViewedService;
    }

    private ServiceProvider() {
        this.doSProtectionService = DoSProtectionServiceImpl.getInstance();
        this.cartService = CartServiceImpl.getInstance();
        this.orderService = OrderServiceImpl.getInstance();
        this.recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
        this.orderDataValidationService = OrderDataValidationServiceImpl.getInstance();
    }
}
