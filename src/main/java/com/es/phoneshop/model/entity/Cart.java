package com.es.phoneshop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {
    private List<CartItem> items;

    private BigDecimal totalCost;

    private int totalQuantity;

    public Cart() {
        this.totalCost = new BigDecimal(0);
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void add(CartItem cartItem) {
        this.items.add(cartItem);
    }

    public void setItems(List<CartItem> cartItems) {
        this.items = cartItems;
    }
}
