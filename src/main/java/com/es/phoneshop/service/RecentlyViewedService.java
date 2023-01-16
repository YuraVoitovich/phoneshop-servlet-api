package com.es.phoneshop.service;

import com.es.phoneshop.model.entity.Product;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RecentlyViewedService {

    void addProduct(HttpSession session, Product product);
    List<Product> getRecentlyViewedBySession(HttpSession session);
}
