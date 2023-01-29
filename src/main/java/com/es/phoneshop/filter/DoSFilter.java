package com.es.phoneshop.filter;

import com.es.phoneshop.security.DoSProtectionService;
import com.es.phoneshop.service.ServiceProvider;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DoSFilter implements Filter {

    private DoSProtectionService doSProtectionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        doSProtectionService = ServiceProvider.getInstance().getDoSProtectionService();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (doSProtectionService.isAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
