package org.teachease.courseservice;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.teachease.courseservice.config.RequestContext;

import java.time.Instant;
import java.util.UUID;

public class RequestContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContext.setRequestPath(request.getRequestURI());
        RequestContext.setRequestId(UUID.randomUUID().toString());
        RequestContext.setUserId(request.getHeader("X-USER-ID"));
        RequestContext.setIpAddress(request.getRemoteAddr());
        RequestContext.setUserAgent(request.getHeader("User-Agent"));
        RequestContext.setRequestTime(String.valueOf(Instant.now()));
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.clear(); // Clean up to prevent memory leaks
    }
}
