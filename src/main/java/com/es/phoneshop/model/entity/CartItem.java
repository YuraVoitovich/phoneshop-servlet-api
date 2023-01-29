package com.es.phoneshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CartItem implements Cloneable, Serializable {
    private Product product;
    private int quantity;

    @Override
    public CartItem clone() {
        CartItem cartItem;
        try {
            cartItem = (CartItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return cartItem;
    }
}
