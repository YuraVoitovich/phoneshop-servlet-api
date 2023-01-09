package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceHistoryServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ProductDao productDao;
    private final ProductPriceHistoryServlet servlet = new ProductPriceHistoryServlet();
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() throws ServletException {

        when(productDao.getProduct(anyLong()))
                .thenReturn(new Product( "sgs", "Samsung Galaxy S"
                        , new BigDecimal(200), usd, 100
                        , "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        servlet.init(servletConfig, productDao);
        when(request.getPathInfo()).thenReturn("/price-history/1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetForward() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetSetAttribute() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("currency"), any());
        verify(request).setAttribute(eq("name"), any());
        verify(request).setAttribute(eq("productPriceHistory"), any());
    }

}
