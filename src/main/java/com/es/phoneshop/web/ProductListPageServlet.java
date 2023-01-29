package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.enums.SortField;
import com.es.phoneshop.model.dao.enums.SortOrder;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.ServiceProvider;
import com.es.phoneshop.service.impl.RecentlyViewedServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = DAOProvider.getInstance().getProductDao();
        recentlyViewedService = ServiceProvider.getInstance().getRecentlyViewedService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SortField sortField = Optional.ofNullable(request.getParameter("sortfield")).map(SortField::valueOf).orElse(null);
        SortOrder sortOrder = Optional.ofNullable(request.getParameter("order")).map(SortOrder::valueOf).orElse(null);
        String query = request.getParameter("query");
        request.setAttribute(RecentlyViewedServiceImpl.RECENTLY_VIEWED_ATTRIBUTE, recentlyViewedService
                .getRecentlyViewedBySession(request.getSession()));
        request.setAttribute("products", productDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }


}
