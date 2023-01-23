package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class AddProductToCartFromProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    private CartService cartService;

    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = DAOProvider.getInstance().getProductDao();
        cartService = CartServiceImpl.getInstance();
        recentlyViewedService = DAOProvider.getInstance().getRecentlyViewedService();
    }

    public void init(ServletConfig config, ProductDao productDao, RecentlyViewedService recentlyViewedService, CartService cartService) throws ServletException {
        super.init(config);
        this.productDao = productDao;
        this.recentlyViewedService = recentlyViewedService;
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        String message;
        String quantity = request.getParameter("quantity").trim();
        try {
            if (quantity.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Integer.parseInt(quantity);
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            cartService.add(request.getSession(), productId, format.parse(quantity).intValue());
            message = "success";
        } catch (ParseException | NumberFormatException e) {
            message = "Quantity should be a number";
        } catch (IllegalArgumentException e) {
            message = "Quantity is empty";
        } catch (OutOfStockException e) {
            message = "Out of stock, available: " + e.getAvailableStock()
                    + ", requested: " + e.getRequestedStock();
        }

        response.sendRedirect(String.format("%s/products?productId=%d&message=%s&savedQuantity=%s", request.getContextPath(),
                productId, message, quantity));

    }

    private Long getProductIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
}