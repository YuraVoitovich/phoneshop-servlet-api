package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.impl.RecentlyViewedServiceImpl;

public class DAOProvider {

    private static class InstanceHolder {
        private static final DAOProvider INSTANCE = new DAOProvider();
    }
    public static DAOProvider getInstance() {
        return InstanceHolder.INSTANCE;
    }
    private final ProductDao productDao;

    private final RecentlyViewedService recentlyViewedService;

    public RecentlyViewedService getRecentlyViewedService() {
        return recentlyViewedService;
    }

    public ProductDao getProductDao() {
        return this.productDao;
    }

    private DAOProvider() {
        this.productDao = ArrayListProductDao.getInstance();
        this.recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }
}
