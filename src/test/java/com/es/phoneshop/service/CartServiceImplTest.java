package com.es.phoneshop.service;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.service.impl.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {
    private CartService cartService;
    @Mock
    private HttpSession session;

    private final Currency usd = Currency.getInstance("USD");
    @Mock
    private ProductDao productDao;

    @Before
    public void setup() {

        cartService = CartServiceImpl.getInstance(productDao);
    }

    @Test
    public void testAddProductIfNotPresent() {

        cartService.add(session, 1L, 1);

        assertEquals(1, cartService.getCartBySession(session).getItems().size());
    }

    @Test
    public void testAddProductIfPresent() {
        Product productToAdd = new Product( 1L, "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        when(productDao.getProduct(1L)).thenReturn(productToAdd);

        cartService.add(session, 1L, 1);

        cartService.add(session, 1L, 3);

        assertEquals(4, cartService.getCartBySession(session).getItems().get(0).getQuantity());
    }
}
