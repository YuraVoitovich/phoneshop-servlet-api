package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.ServiceProvider;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class AddProductToCartFromProductListPageServlet extends HttpServlet {

    private final String SUCCESS_MESSAGE = "success";

    private final String NO_A_NUMBER_MESSAGE = "Quantity should be a number";

    private final String EMPTY_MESSAGE = "Quantity is empty";

    private final String OUT_OF_STOCK_MESSAGE = "Out of stock, available: %d, requested: %d";

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = ServiceProvider.getInstance().getCartService();
    }

    public void init(ServletConfig config, CartService cartService) throws ServletException {
        super.init(config);
        this.cartService = cartService;
    }

    private int findIndex(String[] productIdArray, String productId) {
        for (int i = 1; i < productIdArray.length; i++) {
            if (productIdArray[i].equals(productId)) {
                return i - 1;
            }
        }
        return -1;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdString = request.getParameter("productId");
        Long productId = Long.parseLong(productIdString);
        String message;

        String[] productIdArray = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String quantity = quantities[findIndex(productIdArray, productIdString)];
        try {
            if (quantity.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Integer.parseInt(quantity);
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            cartService.add(request.getSession(), productId, format.parse(quantity).intValue());
            message = SUCCESS_MESSAGE;
        } catch (ParseException | NumberFormatException e) {
            message = NO_A_NUMBER_MESSAGE;
        } catch (IllegalArgumentException e) {
            message = EMPTY_MESSAGE;
        } catch (OutOfStockException e) {
            message = String.format(OUT_OF_STOCK_MESSAGE,e.getAvailableStock(), e.getRequestedStock());
        }

        response.sendRedirect(String.format("%s/products?productId=%d&message=%s&savedQuantity=%s", request.getContextPath(),
                productId, message, quantity));

    }

    private Long getProductIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
}