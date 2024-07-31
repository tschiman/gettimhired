package com.gettimhired.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RequestContextFilter extends HttpFilter {
    Logger log = LoggerFactory.getLogger(RequestContextFilter.class);

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            log.info("Test to see if this comes through");
            requestHolder.set(request);
            chain.doFilter(request, response);
        } finally {
            requestHolder.remove();
        }
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
