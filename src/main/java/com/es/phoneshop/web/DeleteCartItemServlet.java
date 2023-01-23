package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.NoSuchCartItemException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {

    private CartService cartService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    public void init(ServletConfig config, CartService cartService) throws ServletException {
        super.init(config);
        this.cartService = cartService;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductIdFormPathString(request.getPathInfo());
        String message = "Product deleted successfully";
        try {
            cartService.deleteCartItem(request.getSession(), productId);
        } catch (NoSuchCartItemException e) {
            message = String.format("Error while deleting product with id=%s from cart", productId);
        }
        response.sendRedirect(String.format(request.getContextPath() + "/cart?message=%s", message));
    }

    private Long getProductIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
}
