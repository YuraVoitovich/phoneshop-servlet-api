package com.es.phoneshop.model.entity;

import com.es.phoneshop.model.dao.enums.PaymentMethod;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class Order extends Cart implements Serializable {
    private Long id;

    private String secureId;

    private BigDecimal subTotal;

    private BigDecimal deliveryCost;

    private String firstName;

    private String lastName;

    private String phone;

    LocalDate deliveryDate;

    private String deliveryAddress;

    private PaymentMethod paymentMethod;

    public Order(List<CartItem> items) {
        setItems(items);
    }

    public Order() {
    }
}
