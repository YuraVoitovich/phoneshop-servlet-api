package com.es.phoneshop.security;

public interface DoSProtectionService {
    boolean isAllowed(String ip);
}
