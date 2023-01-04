package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;

public class DAOProvider {
    private static final DAOProvider INSTANCE = new DAOProvider();

    public static DAOProvider getInstance() {
        return INSTANCE;
    }
    private final ProductDao productDao = new ArrayListProductDao();

    public ProductDao getProductDao() {
        return this.productDao;
    }
}
