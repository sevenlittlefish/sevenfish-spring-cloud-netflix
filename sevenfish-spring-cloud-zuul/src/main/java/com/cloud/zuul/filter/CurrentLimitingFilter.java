package com.cloud.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class CurrentLimitingFilter extends ZuulFilter {

    //定义一个令牌桶，每秒产生若干个令牌，即每秒最多处理若干个请求
    public static final RateLimiter RATE_LIMITER = RateLimiter.create(10);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SERVLET_DETECTION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        if (!RATE_LIMITER.tryAcquire()){
            //指定当前请求未通过过滤
            context.setSendZuulResponse(false);
            //向客户端返回响应码，请求数量过多
            context.setResponseStatusCode(429);
            context.setResponseBody("请求过多");
            context.getResponse().setContentType("text/html;charset=utf-8");
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}