package com.rsakin.getaxi.proxy.filter;

import com.netflix.zuul.ZuulFilter;
import com.rsakin.getaxi.proxy.util.FilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RouteFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterUtil.FILTER_TYPE_ROUTE;
    }

    @Override
    public int filterOrder() {
        return FilterUtil.FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return FilterUtil.SHOULD_FILTER;
    }


    @Override
    public Object run() {
        log.info("Inside Route Filter");
        return null;
    }
}
