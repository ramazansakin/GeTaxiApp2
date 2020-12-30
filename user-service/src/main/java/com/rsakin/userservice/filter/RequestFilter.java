package com.rsakin.userservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class RequestFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {
        // init anything u wanna use
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info("Logging Request  {} : {} : {}", req.getMethod(), req.getRequestURI(), req.getRequestURL());
        chain.doFilter(request, response);
        log.info("Logging Response :{}", res.getContentType());
    }

    @Override
    public void destroy() {
        // delete, close issues
        log.warn("Destructing filter :{}", this);
    }
}
