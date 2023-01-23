package com.es.phoneshop.web;

import com.es.phoneshop.service.CartService;
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
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddProductToCartFromProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private CartService cartService;
    private final AddProductToCartFromProductListPageServlet servlet = new AddProductToCartFromProductListPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig, cartService);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }


    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("quantity")).thenReturn("1");
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }


}