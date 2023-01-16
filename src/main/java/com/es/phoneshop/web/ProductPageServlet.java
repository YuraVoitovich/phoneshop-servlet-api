package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;
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

public class ProductPageServlet extends HttpServlet {

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

    public void init(ServletConfig config, ProductDao productDao) throws ServletException {
        super.init(config);
        this.productDao = productDao;
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
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            cartService.add(request.getSession(), productId, format.parse(quantity).intValue());
            message = "success";
        } catch (IllegalArgumentException e) {
            message = "Quantity is empty";
        } catch (OutOfStockException e) {
            message = "Out of stock, available: " + e.getAvailableStock()
                    + ", requested: " + e.getRequestedStock();
        } catch (ParseException e) {
            message = "Quantity should be a number";
        }

        response.sendRedirect(request.getContextPath() + "/products/"
                + productId + "?message=" + message + "&savedQuantity=" + quantity);

    }

    private Long getProductIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
}
