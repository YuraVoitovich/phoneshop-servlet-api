package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.impl.OrderDaoImpl;

public class DAOProvider {

    private static class InstanceHolder {
        private static final DAOProvider INSTANCE = new DAOProvider();
    }
    public static DAOProvider getInstance() {
        return InstanceHolder.INSTANCE;
    }
    private final ProductDao productDao;

    public ProductDao getProductDao() {
        return this.productDao;
    }

    private final OrderDao orderDao;

    public OrderDao getOrderDao() {
        return this.orderDao;
    }

    private DAOProvider() {
        this.productDao = ArrayListProductDao.getInstance();
        this.orderDao = OrderDaoImpl.getInstance();
    }
}
