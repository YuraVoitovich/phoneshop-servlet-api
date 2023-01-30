package com.es.phoneshop.service;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    private OrderService orderService;
    @Mock
    private HttpSession session;

    private final Currency usd = Currency.getInstance("USD");
    @Mock
    private OrderDao orderDao;

    @Before
    public void setup() {

        orderService = OrderServiceImpl.getInstance(orderDao);
    }

    @Test
    public void testCreateOrderFromCart() {

        Cart cart = new Cart();
        cart.add(new CartItem(null, 2));
        Order order = orderService.createOrderFromCart(cart);
        
        assertEquals(1, order.getItems().size());
    }

    @Test
    public void testGetOrder() {
        Cart cart = new Cart();
        cart.add(new CartItem(null, 2));
        Order order = orderService.createOrderFromCart(cart);
        when(orderDao.getOrder(anyLong())).thenReturn(order);

        assertEquals(orderService.getOrderById(1L), order);
    }

}
