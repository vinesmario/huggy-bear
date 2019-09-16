package com.vinesmario.cloud.getway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AccessFilter extends ZuulFilter {

    /**
     * 过滤器的类型 pre表示请求在路由之前被过滤
     *
     * @return 类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     *
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否会被执行
     *
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤逻辑
     *
     * @return 过滤结果
     */
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        Object authorization = request.getHeader("Authorization");
//        Object accessToken = request.getParameter("accessToken");
        if (ObjectUtils.isEmpty(authorization)) {
            log.warn("Authorization token is empty");
            requestContext.setSendZuulResponse(false);
            requestContext.getResponse().setContentType("application/json");
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("Authorization token is empty");
            return null;
        }

        log.info("Authorization token ok");
        return null;
    }

}
