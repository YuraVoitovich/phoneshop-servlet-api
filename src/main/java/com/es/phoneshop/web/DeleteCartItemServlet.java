package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.NoSuchCartItemException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.ServiceProvider;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {

    private CartService cartService;

    private final String SUCCESS_MESSAGE = "Product deleted successfully";

    private final String ERROR_MESSAGE  = "Error while deleting product with id=%s from cart";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = ServiceProvider.getInstance().getCartService();
    }

    public void init(ServletConfig config, CartService cartService) throws ServletException {
        super.init(config);
        this.cartService = cartService;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductIdFormPathString(request.getPathInfo());
        String message = SUCCESS_MESSAGE;
        try {
            cartService.deleteCartItem(request.getSession(), productId);
        } catch (NoSuchCartItemException e) {
            message = String.format(ERROR_MESSAGE, productId);
        }
        response.sendRedirect(String.format(request.getContextPath() + "/cart?message=%s", message));
    }

    private Long getProductIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
}
