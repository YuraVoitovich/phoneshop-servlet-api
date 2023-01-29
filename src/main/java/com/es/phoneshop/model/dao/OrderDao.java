package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.Order;

public interface OrderDao {
    Order getOrder(Long id);
    void save(Order order);

    Order getOrderBySecureId(String secureId);
}
