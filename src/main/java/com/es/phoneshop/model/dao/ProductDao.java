package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query);
    void save(Product product);
    void delete(Long id);
}
