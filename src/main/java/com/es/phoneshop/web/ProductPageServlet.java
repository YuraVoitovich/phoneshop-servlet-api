package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.ServiceProvider;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductPageServlet extends HttpServlet {

    private ProductDao productDao;

    private CartService cartService;

    private RecentlyViewedService recentlyViewedService;

    private final String SUCCESS_MESSAGE = "success";

    private final String NO_A_NUMBER_MESSAGE = "Quantity should be a number";

    private final String EMPTY_MESSAGE = "Quantity is empty";

    private final String OUT_OF_STOCK_MESSAGE = "Out of stock, available: %d, requested: %d";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = DAOProvider.getInstance().getProductDao();
        cartService = ServiceProvider.getInstance().getCartService();
        recentlyViewedService = ServiceProvider.getInstance().getRecentlyViewedService();
    }

    public void init(ServletConfig config, ProductDao productDao, RecentlyViewedService recentlyViewedService, CartService cartService) throws ServletException {
        super.init(config);
        this.productDao = productDao;
        this.recentlyViewedService = recentlyViewedService;
        this.cartService = cartService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductIdFormPathString(request.getPathInfo());
        Product product = productDao.getProduct(productId);
        request.setAttribute("product", product);
        recentlyViewedService.addProduct(request.getSession(), product);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductIdFormPathString(request.getPathInfo());
        String message;
        String quantity = request.getParameter("quantity").trim();
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
            message =  String.format(OUT_OF_STOCK_MESSAGE, e.getAvailableStock()
                    , e.getRequestedStock());
        }

        response.sendRedirect(String.format("%s/products/%d?message=%s&savedQuantity=%s", request.getContextPath(),
                productId, message, quantity));

    }

    private Long getProductIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
}
