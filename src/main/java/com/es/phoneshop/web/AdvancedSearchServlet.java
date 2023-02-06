package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.enums.SearchDescriptionType;
import com.es.phoneshop.model.entity.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedSearchServlet extends HttpServlet {

    private ProductDao productDao;

    private final String ADVANCED_SEARCH_JSP = "/WEB-INF/pages/advanced-search.jsp";


    private final String NO_A_NUMBER_MESSAGE = "Value should be a number";

    private final String EMPTY_MESSAGE = "Value is empty";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = DAOProvider.getInstance().getProductDao();
    }

    public void init(ServletConfig config, ProductDao productDao) throws ServletException {
        super.init(config);
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<SearchDescriptionType> methods = productDao.getSearchDescriptionTypeMethods();
        request.setAttribute("methods", methods);
        request.setAttribute("searchMethod", methods.get(0));
        request.setAttribute("products", new ArrayList<Product>());
        request.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(request, response);

    }

    private int parseMinPriceString(Map<String, String> messages, String minPriceString, NumberFormat format) {
        int minPrice = 0;
        try {
            if (minPriceString.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Integer.parseInt(minPriceString);
            minPrice = format.parse(minPriceString).intValue();
        } catch (ParseException | NumberFormatException e) {
            messages.put("minPriceString", NO_A_NUMBER_MESSAGE);
        } catch (IllegalArgumentException e) {
            messages.put("minPriceString", EMPTY_MESSAGE);
        }
        return minPrice;

    }

    private int parseMaxPriceString(Map<String, String> messages, String minPriceString, NumberFormat format) {
        int maxPrice = 0;
        try {
            if (minPriceString.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Integer.parseInt(minPriceString);
            maxPrice = format.parse(minPriceString).intValue();
        } catch (ParseException | NumberFormatException e) {
            messages.put("minPriceString", NO_A_NUMBER_MESSAGE);
        } catch (IllegalArgumentException e) {
            messages.put("minPriceString", EMPTY_MESSAGE);
        }
        return maxPrice;

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String minPriceString = request.getParameter("minPrice").trim();
        String maxPriceString = request.getParameter("maxPrice").trim();
        String description = request.getParameter("description").trim();
        String searchDescriptionType = request.getParameter("searchDescriptionType");
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        Map<String, String> messages = new HashMap<>();
        int minPrice = parseMinPriceString(messages, minPriceString, format);
        int maxPrice = parseMaxPriceString(messages, maxPriceString, format);


        List<SearchDescriptionType> methods = productDao.getSearchDescriptionTypeMethods();
        request.setAttribute("methods", methods);
        request.setAttribute("messages", messages);
        request.setAttribute("minPrice", minPriceString);
        request.setAttribute("maxPrice", maxPriceString);
        request.setAttribute("description", description);
        SearchDescriptionType type = SearchDescriptionType.valueOf(searchDescriptionType);
        request.setAttribute("searchMethod", type);
        if (messages.isEmpty()) {

            List<Product> products = productDao.findProducts(description, BigDecimal.valueOf(minPrice),
                    BigDecimal.valueOf(maxPrice), SearchDescriptionType.valueOf(searchDescriptionType));
            request.setAttribute("products",
                    products);
            request.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(request, response);

        } else {
            request.setAttribute("products", new ArrayList<Product>());
            request.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(request, response);
        }


    }
}
