package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.RecentlyViewedService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private RecentlyViewedService recentlyViewedService;
    @Mock
    private CartService cartService;
    private final ProductPageServlet servlet = new ProductPageServlet();
    private final Currency usd = Currency.getInstance("USD");



    @Before
    public void setup() throws ServletException {
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.init(servletConfig, productDao, recentlyViewedService, cartService);
        when(request.getPathInfo()).thenReturn("/1");
    }

    @Test
    public void testDoGetForward() throws ServletException, IOException {
        Product sampleProduct = new Product( "sgs", "Samsung Galaxy S"
                , new BigDecimal(200), usd, 100
                , "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        when(productDao.getProduct(anyLong()))
                .thenReturn(sampleProduct);

        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetSetAttribute() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("product"), any());
    }

    @Test
    public void testDoPostSendRedirect() throws IOException, ServletException {

        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }



}
