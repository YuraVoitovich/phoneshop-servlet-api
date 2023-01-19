package com.es.phoneshop.service;

import com.es.phoneshop.model.entity.Cart;

import javax.servlet.http.HttpSession;

public interface CartService {

    Cart getCartBySession(HttpSession session);
    void add(HttpSession session, Long productId, int quantity);
}
