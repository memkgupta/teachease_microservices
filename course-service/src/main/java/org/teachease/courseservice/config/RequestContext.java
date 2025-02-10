package org.teachease.courseservice.config;

import org.springframework.stereotype.Component;

@Component
public class RequestContext {
    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> ipAddress = new ThreadLocal<>();
    private static final ThreadLocal<String> userAgent = new ThreadLocal<>();
    private static final ThreadLocal<String> requestTime = new ThreadLocal<>();
    private static final ThreadLocal<String> requestId = new ThreadLocal<>();
    private static final ThreadLocal<String> requestPath = new ThreadLocal<>();
    public static String getUserId() {
        return userId.get();
    }
    public static void setUserId(String id) {
     userId.set(id);
    }
    public static String getIpAddress() {
        return ipAddress.get();
    }
    public static void setIpAddress(String ipAddress) {
        RequestContext.ipAddress.set(ipAddress);
    }
    public static String getUserAgent() {
        return userAgent.get();
    }
    public static void setUserAgent(String userAgent) {
        RequestContext.userAgent.set(userAgent);
    }
    public static String getRequestTime() {
        return requestTime.get();
    }
    public static void setRequestTime(String requestTime) {
        RequestContext.requestTime.set(requestTime);
    }
    public static String getRequestId() {
        return requestId.get();
    }
    public static void setRequestId(String requestId) {
        RequestContext.requestId.set(requestId);
    }
    public static String getRequestPath() {
        return requestPath.get();
    }
    public static void setRequestPath(String requestPath) {
        RequestContext.requestPath.set(requestPath);
    }
    public static void clear() {
        userId.remove();
        ipAddress.remove();
        userAgent.remove();
        requestTime.remove();
        requestId.remove();
        requestPath.remove();
    }
}
