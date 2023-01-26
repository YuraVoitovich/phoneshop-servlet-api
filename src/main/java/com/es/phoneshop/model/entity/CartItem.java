package com.es.phoneshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CartItem implements Serializable {
    private Product product;
    private int quantity;

}
