package com.rsakin.getaxi.proxy.util;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtil {

    public static final int FILTER_ORDER = 1;
    public static final boolean SHOULD_FILTER = true;

    public static final String FILTER_TYPE_PRE = "pre";
    public static final String FILTER_TYPE_POST = "post";
    public static final String FILTER_TYPE_ROUTE = "route";

    private String generateTransactionId() {
        return java.util.UUID.randomUUID().toString();
    }

    public void setTransactionId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("trx-id", generateTransactionId());
    }

    public String getTransactionId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getZuulRequestHeaders().get("trx-id");
    }
}
