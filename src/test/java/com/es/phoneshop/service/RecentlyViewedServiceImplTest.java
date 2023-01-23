package com.es.phoneshop.service;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.service.impl.RecentlyViewedServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RecentlyViewedServiceImplTest {
    private RecentlyViewedService recentlyViewedService;
    @Mock
    private HttpSession session;

    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    @Test
    public void testAddProductIfNotPresent() {
        Product productToAdd = new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        recentlyViewedService.addProduct(session, productToAdd);

        assertEquals(1, recentlyViewedService.getRecentlyViewedBySession(session).size());
    }

    @Test
    public void testAddFourProducts() {
        Product productToAdd = new Product(1L, "sgs", "Samsung Galaxy S III", new BigDecimal(100)
                , usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        recentlyViewedService.addProduct(session, productToAdd);

        productToAdd = new Product(2L, "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        recentlyViewedService.addProduct(session, productToAdd);

        productToAdd = new Product(3L, "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        recentlyViewedService.addProduct(session, productToAdd);

        productToAdd = new Product(4L, "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        recentlyViewedService.addProduct(session, productToAdd);


        List<Product> recentlyViewed = recentlyViewedService.getRecentlyViewedBySession(session);
        assertEquals(3, recentlyViewed.size());
        assertEquals((Long)4L, recentlyViewed.get(0).getId());
    }
}
