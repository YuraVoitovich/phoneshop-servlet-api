package com.es.phoneshop.web;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class MiniCartServlet extends HttpServlet {
    private final String CART_JSP = "/WEB-INF/pages/minicart.jsp";

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = Integer.parseInt(request.getParameter("count"));
        request.setAttribute("cartItems", cartService.getCartBySession(request.getSession())
                .getItems().stream().limit(count).collect(Collectors.toList()));
        request.getRequestDispatcher(CART_JSP).include(request, response);
    }



}

