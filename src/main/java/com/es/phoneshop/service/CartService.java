package com.es.phoneshop.service;

import com.es.phoneshop.model.entity.Cart;

import javax.servlet.http.HttpSession;

public interface CartService {

    Cart getCartBySession(HttpSession session);

    void clearCartBySession(HttpSession session);

    boolean update(HttpSession session, Long productId, int quantity);
    void add(HttpSession session, Long productId, int quantity);

    void deleteCartItem(HttpSession session, Long productId);
}
