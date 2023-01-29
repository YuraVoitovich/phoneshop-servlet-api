package com.es.phoneshop.web;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderDataValidationService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.ServiceProvider;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OverviewPageServlet extends HttpServlet {

    private final String ORDER_OVERVIEW_JSP = "/WEB-INF/pages/overview.jsp";

    private OrderService orderService;

    private OrderDataValidationService orderDataValidationService;

    private final String FIRST_NAME_PROPERTY = "firstName";
    private final String LAST_NAME_PROPERTY = "lastName";
    private final String PHONE_PROPERTY = "phone";
    private final String DELIVERY_ADDRESS_PROPERTY = "deliveryAddress";
    private final String DELIVERY_DATE_PROPERTY = "deliveryDate";
    private final String PAYMENT_METHOD_PROPERTY = "paymentMethod";

    private final String EMPTY_MESSAGE = "%s should be not empty";

    private final String PAYMENT_MESSAGE = "Not valid payment method";

    private final String SUCCESS_MESSAGE = "Success";

    private final String ERROR_MESSAGE = "Error occurred while placing an order";

    private final String DATE_MESSAGE = "Date is not valid";
    private final String NOT_VALID_PHONE_MESSAGE = "phone number should match the format \"+375 XX XXX XX XX\"";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.orderService = ServiceProvider.getInstance().getOrderService();
        this.orderDataValidationService = ServiceProvider.getInstance().getOrderDataValidationService();
    }

    public void init(ServletConfig config, CartService cartService) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("order", orderService.getOrderBySecureId(getOrderSecureIdFormPathString(request.getPathInfo())));
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());
        request.getRequestDispatcher(ORDER_OVERVIEW_JSP).forward(request, response);
    }

    private Long getOrderIdFormPathString(String pathInfo) {
        return Long.parseLong(pathInfo.substring(1));
    }
    private String getOrderSecureIdFormPathString(String pathInfo) {
        return pathInfo.substring(1);
    }

}
