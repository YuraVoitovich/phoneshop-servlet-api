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
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
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
    private final CartPageServlet servlet = new CartPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig, cartService);
        when(cartService.update(any(), eq(1L), eq(1))).thenReturn(true);
        when(request.getLocale()).thenReturn(Locale.getDefault());
    }


    @Test
    public void testDoGet() throws ServletException, IOException {
        when(cartService.getCartBySession(any())).thenReturn(new Cart());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testNullQuantitiesDoPost() throws ServletException, IOException {

        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }



    @Test
    public void testCorrectDoPost() throws ServletException, IOException {
        when(request.getParameterValues(eq("productId"))).thenReturn(new String[] {"1"});
        when(request.getParameterValues(eq("quantity"))).thenReturn(new String[] {"1"});
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }


}