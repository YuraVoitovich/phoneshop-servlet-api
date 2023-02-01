package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.enums.SortField;
import com.es.phoneshop.model.dao.enums.SortOrder;
import com.es.phoneshop.model.entity.Product;

import java.util.List;

public interface ProductDao {

    void clear();
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(Long id);
}
