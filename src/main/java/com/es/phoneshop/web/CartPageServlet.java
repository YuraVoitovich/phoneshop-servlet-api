package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private final String CART_JSP = "/WEB-INF/pages/cart.jsp";

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(CartServiceImpl.CART_ATTRIBUTE, cartService.getCartBySession(request.getSession()));
        request.getRequestDispatcher(CART_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productId = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        if(quantities == null) {
            response.sendRedirect(String.format(request.getContextPath() + "/cart?message=%s"
                    , "Ups, maybe you should put some products first :("));
            return;
        }
        Long currentProductId = 0L;
        Map<Long, String> messages = new HashMap<>();
        boolean isChanged = false;
        String message = "";
        for (int i = 0; i < quantities.length; i++) {
            try {

                currentProductId = Long.parseLong(productId[i]);
                NumberFormat format = NumberFormat.getInstance(request.getLocale());

                if (quantities[i].isEmpty()) {
                    throw new IllegalArgumentException();
                }
                Integer.parseInt(quantities[i]);

                if(cartService.update(request.getSession(), currentProductId
                        , format.parse(quantities[i]).intValue())) {
                    isChanged = true;
                }
                if (isChanged) {
                    messages.put(currentProductId, "success");
                }
            } catch (ParseException | NumberFormatException e) {
                messages.put(currentProductId, "Quantity should be a number");
                message = "There where errors while updating cart";
            } catch (IllegalArgumentException e) {
                messages.put(currentProductId, "Quantity is empty");
                message = "There where errors while updating cart";
            } catch (OutOfStockException e) {
                message = "There where errors while updating cart";
                messages.put(currentProductId, "Out of stock, available: " + e.getAvailableStock()
                        + ", requested: " + e.getRequestedStock());
            }
        }
        request.setAttribute("messages", messages);
        if (message.isEmpty() && isChanged) {
            message = "success";
            request.setAttribute("message", message);
        }
        if (message.equals("success") || message.isEmpty()) {
            response.sendRedirect(String.format(request.getContextPath() + "/cart?message=%s", message));
        } else {
            doGet(request, response);
        }

    }

}
