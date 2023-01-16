package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.service.RecentlyViewedService;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RecentlyViewedServiceImpl implements RecentlyViewedService {

    private static class InstanceHolder {
        private static final RecentlyViewedServiceImpl INSTANCE = new RecentlyViewedServiceImpl();
    }
    public static RecentlyViewedServiceImpl getInstance()
    {
        return RecentlyViewedServiceImpl.InstanceHolder.INSTANCE;
    }

    private final Map<HttpSession, LinkedList<Product>> usersRecentlyViewed;

    private final Lock readLock;

    private final Lock writeLock;

    private final int recentlyViewedCapacity = 3;

    public RecentlyViewedServiceImpl() {
        usersRecentlyViewed = new HashMap<>();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public List<Product> getRecentlyViewedBySession(HttpSession session) {
        readLock.lock();
        LinkedList<Product> recentlyViewed = null;
        try {
            if (usersRecentlyViewed.containsKey(session)) {
                recentlyViewed = usersRecentlyViewed.get(session);
                return recentlyViewed;
            }

        } finally {
            readLock.unlock();
        }
        recentlyViewed = new LinkedList<>();
        writeLock.lock();
        try {
            usersRecentlyViewed.put(session, recentlyViewed);
        } finally {
            writeLock.unlock();
        }
        session.setAttribute("recentlyViewed", recentlyViewed);
        return recentlyViewed;
    }

    private void ifContains(LinkedList<Product> products, Product product) {
        products.remove(product);
        products.addFirst(product);
    }
    public void addProduct(HttpSession session, Product product) {
        LinkedList<Product> products = (LinkedList<Product>)getRecentlyViewedBySession(session);
        synchronized (session) {
            if (products.size() >= recentlyViewedCapacity) {
                if (products.contains(product)) {
                    ifContains(products, product);
                } else {
                    products.removeLast();
                    products.addFirst(product);
                }
            } else {
                if (!products.contains(product)) {
                    products.addFirst(product);
                } else {
                    ifContains(products, product);
                }
            }
        }
    }
}
