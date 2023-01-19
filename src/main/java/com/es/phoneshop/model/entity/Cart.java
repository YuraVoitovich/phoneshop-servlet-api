package com.es.phoneshop.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void add(CartItem cartItem) {
        this.items.add(cartItem);
    }
}
