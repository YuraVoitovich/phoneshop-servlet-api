package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.impl.RecentlyViewedServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecentlyViewedServlet extends HttpServlet {

    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recentlyViewedService = DAOProvider.getInstance().getRecentlyViewedService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(RecentlyViewedServiceImpl.RECENTLY_VIEWED_ATTRIBUTE, recentlyViewedService
                .getRecentlyViewedBySession(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/recentlyViewed.jsp").include(request, response);
    }

}
