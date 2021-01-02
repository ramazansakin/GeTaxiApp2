package com.rsakin.getaxi.proxy.filter;

import com.netflix.zuul.ZuulFilter;
import com.rsakin.getaxi.proxy.util.FilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {

    @Autowired
    private FilterUtil filterUtil;

    @Override
    public boolean shouldFilter() {
        return FilterUtil.SHOULD_FILTER;
    }

    @Override
    public String filterType() {
        return FilterUtil.FILTER_TYPE_PRE;
    }

    @Override
    public int filterOrder() {
        return FilterUtil.FILTER_ORDER;
    }

    // Pre-filter all requests and do any ...
    @Override
    public Object run() {
        filterUtil.setTransactionId();
        log.info("transaction id created : " +
                filterUtil.getTransactionId());
        return null;
    }

}