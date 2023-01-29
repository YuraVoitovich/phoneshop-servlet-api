package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.enums.PaymentMethod;
import com.es.phoneshop.model.entity.Order;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {

    private final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";

    private CartService cartService;

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
        cartService = ServiceProvider.getInstance().getCartService();
        this.orderService = ServiceProvider.getInstance().getOrderService();
        this.orderDataValidationService = ServiceProvider.getInstance().getOrderDataValidationService();
    }

    public void init(ServletConfig config, CartService cartService) throws ServletException {
        super.init(config);
        this.cartService = cartService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("order", orderService.createOrderFromCart(cartService.getCartBySession(request.getSession())));
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());
        request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = orderService.createOrderFromCart(cartService.getCartBySession(request.getSession()));

        String firstName = request.getParameter(FIRST_NAME_PROPERTY);
        String lastName = request.getParameter(LAST_NAME_PROPERTY);
        String phone = request.getParameter(PHONE_PROPERTY);
        String deliveryDate = request.getParameter(DELIVERY_DATE_PROPERTY);
        String deliveryAddress = request.getParameter(DELIVERY_ADDRESS_PROPERTY);
        String paymentMethod = request.getParameter(PAYMENT_METHOD_PROPERTY);


        Map<String, String> messages = new HashMap<>();

        if (!orderDataValidationService.validateEmptiness(firstName)) {
            messages.put(FIRST_NAME_PROPERTY, String.format(EMPTY_MESSAGE, "first name"));
        } else {
            order.setFirstName(firstName);
        }

        if (!orderDataValidationService.validateEmptiness(lastName)) {
            messages.put(LAST_NAME_PROPERTY, String.format(EMPTY_MESSAGE, "last name"));
        } else {
            order.setLastName(lastName);
        }

        if (!orderDataValidationService.validatePhoneNumber(phone)) {
            messages.put(PHONE_PROPERTY, NOT_VALID_PHONE_MESSAGE);
        } else {
            order.setPhone(phone);
        }

//        if (!orderDataValidationService.validateDate(deliveryDate)) {
//            messages.put(DELIVERY_DATE_PROPERTY, DATE_MESSAGE);
//        } else {
//            order.setDeliveryDate(LocalDate.parse(deliveryDate));
//        }
        order.setDeliveryDate(LocalDate.now());

        if (!orderDataValidationService.validateEmptiness(deliveryAddress)) {
            messages.put(DELIVERY_ADDRESS_PROPERTY, String.format(EMPTY_MESSAGE, "delivery address"));
        } else {
            order.setDeliveryAddress(deliveryAddress);
        }

        if (!orderDataValidationService.validatePaymentMethod(paymentMethod)) {
            messages.put(PAYMENT_METHOD_PROPERTY, PAYMENT_MESSAGE);
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        }

        if (messages.isEmpty()) {
            cartService.clearCartBySession(request.getSession());
            orderService.placeOrder(order);
            response.sendRedirect(String.format("%s/order/overview/%s", request.getContextPath(), order.getSecureId()));
        } else {
            request.setAttribute("messages", messages);
            request.setAttribute("order", order);
            request.setAttribute("message", ERROR_MESSAGE);
            request.setAttribute("paymentMethods", orderService.getPaymentMethods());
            request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
        }

    }
}
