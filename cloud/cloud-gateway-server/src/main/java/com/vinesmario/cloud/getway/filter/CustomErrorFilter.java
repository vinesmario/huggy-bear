package com.vinesmario.cloud.getway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

/**
 * Created by leon on 2017/5/13.
 */
@Slf4j
public class CustomErrorFilter extends ZuulFilter {

    /**
     * 过滤器的类型 post表示请求在路由之后被过滤
     *
     * @return 类型
     */
    @Override
    public String filterType() {
        return "post";
    }

    /**
     * 过滤器的执行顺序
     *
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return -1; // Needs to run before SendErrorFilter which has filterOrder == 0
    }

    /**
     * 过滤器是否会被执行
     *
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return RequestContext.getCurrentContext().containsKey("error.status_code");
    }

    /**
     * 过滤逻辑
     *
     * @return 过滤结果
     */
    @Override
    public Object run() {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            Object e = requestContext.get("error.exception");

            if (e != null && e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) e;
                log.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

                // Remove error code to prevent further error handling in follow up filters
                requestContext.remove("error.status_code");

                // Populate context with new response values
                requestContext.getResponse().setContentType("application/json");
                requestContext.setResponseStatusCode(500); //Can set any error code as excepted
                requestContext.setResponseBody("Overriding Zuul Exception Body");
            }
        } catch (Exception ex) {
            log.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}
