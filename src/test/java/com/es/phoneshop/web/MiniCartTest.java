package com.es.phoneshop.web;

import com.es.phoneshop.model.entity.Cart;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartTest {
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
    private final MiniCartServlet servlet = new MiniCartServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig, cartService);
        when(cartService.getCartBySession(any())).thenReturn(new Cart());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }


    @Test
    public void testGet() throws ServletException, IOException {
        when(request.getParameter("count")).thenReturn("3");
        servlet.doGet(request, response);
        verify(requestDispatcher).include(request, response);
    }


}