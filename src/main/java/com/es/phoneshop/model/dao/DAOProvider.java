package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;

public class DAOProvider {
    private static DAOProvider INSTANCE;

    public static DAOProvider getInstance() {
        DAOProvider localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (DAOProvider.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new DAOProvider();
                }
            }
        }
        return localInstance;
    }
    private final ProductDao productDao = new ArrayListProductDao();

    public ProductDao getProductDao() {
        return this.productDao;
    }
}
