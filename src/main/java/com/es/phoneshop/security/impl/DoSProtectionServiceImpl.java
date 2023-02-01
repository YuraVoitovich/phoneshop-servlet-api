package com.es.phoneshop.security.impl;

import com.es.phoneshop.security.DoSProtectionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DoSProtectionServiceImpl implements DoSProtectionService {

    private static final long THRESHOLD = 10;

    private ScheduledExecutorService executorService;

    private Map<String, Long> countMap = new ConcurrentHashMap<>();
    private static class InstanceHolder {
        private static final DoSProtectionServiceImpl INSTANCE = new DoSProtectionServiceImpl();
    }
    public static DoSProtectionServiceImpl getInstance() {
        return DoSProtectionServiceImpl.InstanceHolder.INSTANCE;
    }

    private DoSProtectionServiceImpl() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(countMap::clear, 60, 60, TimeUnit.SECONDS);
    }

    @Override
    public boolean isAllowed(String ip) {
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1L;
        } else {
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }
        countMap.put(ip, count);
        return true;
    }
}
