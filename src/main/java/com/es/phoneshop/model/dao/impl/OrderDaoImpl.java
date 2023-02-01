package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.NoSuchOrderException;
import com.es.phoneshop.model.exception.NoSuchProductException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OrderDaoImpl implements OrderDao {
    private final List<Order> orders;
    private Long currentId = 1L;
    private final Lock writeLock;
    private final Lock readLock;
    private static class InstanceHolder {
        private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();
    }
    public static OrderDaoImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private void checkSecureIdParameter(String secureId) {
        if (secureId == null) {
            throw new IllegalArgumentException("secureId can not be null");
        }
    }

    private void checkIdParameter(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }
    }

    private OrderDaoImpl() {
        this.orders = new ArrayList<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();

    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        checkSecureIdParameter(secureId);
        readLock.lock();
        Order order;
        try {
            order = orders.stream()
                    .filter(currentOrder -> secureId.equals(currentOrder.getSecureId()))
                    .findAny()
                    .orElseThrow(() -> new NoSuchOrderException(String.format("Order with id=%s not found", secureId)));
        } finally {
            readLock.unlock();
        }
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        checkIdParameter(id);
        readLock.lock();
        Order order;
        try {
            order = orders.stream()
                    .filter(currentOrder -> id.equals(currentOrder.getId()))
                    .findAny()
                    .orElseThrow(() -> new NoSuchOrderException(String.format("Order with id=%s not found", id)));
        } finally {
            readLock.unlock();
        }
        return order;
    }
    private void addOrder(Order order) {
        order.setId(currentId++);
        orders.add(order);
    }
    @Override
    public void save(Order orderToSave) {
        if (orderToSave == null) throw new IllegalArgumentException("Order to save can not be null");
        writeLock.lock();
        try {
            if (orderToSave.getId() != null) {
                Optional<Order> foundOrderOptional = orders.stream()
                        .filter(currentOrder -> currentOrder.getId().equals(orderToSave.getId()))
                        .findAny();
                if (foundOrderOptional.isPresent()) {
                    orders.set(orders.indexOf(foundOrderOptional.get()), orderToSave);
                } else {
                    addOrder(orderToSave);
                }
            } else {
                addOrder(orderToSave);
            }
        } finally {
            writeLock.unlock();
        }
    }

}
