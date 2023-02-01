package com.es.phoneshop.dao;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.dao.impl.OrderDaoImpl;
import com.es.phoneshop.model.entity.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;

import static org.junit.Assert.*;

public class OrderDaoTest
{
    private OrderDao orderDao;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        orderDao = OrderDaoImpl.getInstance();
    }


    @Test
    public void testGetOrder() {
        Order order = new Order();
        orderDao.save(order);

        assertTrue(order.getId() > 0L);
        assertEquals(order, orderDao.getOrder(order.getId()));
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        orderDao.save(order);
        Order foundOrder = orderDao.getOrder(order.getId());

        assertEquals(order, foundOrder);
    }
}
