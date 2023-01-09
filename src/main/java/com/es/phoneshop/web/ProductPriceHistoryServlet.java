package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = DAOProvider.getInstance().getProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] requestPath = request.getPathInfo().split("/");
        String productId = requestPath[requestPath.length - 1];
        Product product = productDao.getProduct(Long.parseLong(productId));
        request.setAttribute("currency", product.getCurrency());
        request.setAttribute("name", product.getDescription());
        request.setAttribute("productPriceHistory", product.getPriceHistoryList());
        request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
    }


}
