package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PreRequestFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //指定为前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //指定过滤顺序 越小越先执行
        //FilterConstants.SERVLET_DETECTION_FILTER_ORDER=-3最先执行，最先执行可设置更小
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //可针对一些情况进行过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) // a filter has already forwarded
                && !ctx.containsKey(FilterConstants.SERVICE_ID_KEY); // a filter has already determined serviceId
    }

    /**
     * 每次服务通过过滤器时执行的代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        context.set("startTime", System.currentTimeMillis());
        context.set("token", UUID.randomUUID().toString());
        return null;
    }
}
