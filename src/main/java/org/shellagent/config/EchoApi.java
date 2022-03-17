package org.shellagent.config;

import org.shellagent.services.GatewayAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class EchoApi implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GatewayAuth gatewayAuth;

    /**
     * 日志显示访问的接口.
     * @return true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("访问接口:"+request.getServletPath());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("----------------------------");
    }
}
