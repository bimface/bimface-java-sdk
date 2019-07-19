package com.bimface.sdk.config;

import com.bimface.sdk.constants.BimfaceConstants;
import com.bimface.sdk.utils.VersionInfoUtils;

/**
 * 配置
 *
 * @author bimface, 2016-06-01.
 */
public class Config {

    private static final String DEFAULT_USER_AGENT  = VersionInfoUtils.getDefaultUserAgent();

    private String              userAgent           = DEFAULT_USER_AGENT;

    private int                 maxIdleConnections  = BimfaceConstants.DEFAULT_MAX_IDLE_CONNECTIONS;
    private long                keepAliveDurationNs = BimfaceConstants.DEFAULT_KEEP_ALIVE_DURATION_NS;
    private int                 maxRequests         = BimfaceConstants.DEFAULT_MAX_REQUESTS;
    private int                 maxRequestsPerHost  = BimfaceConstants.DEFAULT_MAX_REQUESTS_PER_HOST;

    private int                 connectTimeout      = BimfaceConstants.DEFAULT_CONNECT_TIMEOUT;
    private int                 readTimeout         = BimfaceConstants.DEFAULT_WRITE_TIMEOUT;
    private int                 writeTimeout        = BimfaceConstants.DEFAULT_RESPONSE_TIMEOUT;

    /**
     * 构造用户代理。
     *
     * @return 用户代理。
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置用户代理。
     *
     * @param userAgent 用户代理。
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 返回最大空闲连接数
     *
     * @return 最大空闲连接数
     */
    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    /**
     * 设置最大空闲连接数
     *
     * @param maxIdleConnections 最大空闲连接数
     */
    public void setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    /**
     * 返回保持活动周期时长
     *
     * @return 保持活动周期时长（单位：纳秒）
     */
    public long getKeepAliveDurationNs() {
        return keepAliveDurationNs;
    }

    /**
     * 设置保持活动周期时长
     *
     * @param keepAliveDurationNs 保持活动周期时长（单位：纳秒）
     */
    public void setKeepAliveDurationNs(long keepAliveDurationNs) {
        this.keepAliveDurationNs = keepAliveDurationNs;
    }

    /**
     * 返回允许打开的最大请求数。
     *
     * @return 最大请求数。
     */
    public int getMaxRequests() {
        return maxRequests;
    }

    /**
     * 设置允许打开的最大请求数。
     *
     * @param maxRequests 最大请求数。
     */
    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    /**
     * 返回每台主机最大的请求数
     *
     * @return 每台主机最大的请求数
     */
    public int getMaxRequestsPerHost() {
        return maxRequestsPerHost;
    }

    /**
     * 设置每台主机最大的请求数
     *
     * @param maxRequestsPerHost 每台主机最大的请求数
     */
    public void setMaxRequestsPerHost(int maxRequestsPerHost) {
        this.maxRequestsPerHost = maxRequestsPerHost;
    }

    /**
     * 返回建立连接的超时时间（单位：秒）。
     *
     * @return 建立连接的超时时间（单位：秒）。
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 设置建立连接的超时时间（单位：秒）。
     *
     * @param connectTimeout 建立连接的超时时间（单位：秒）。
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 返回获取响应的超时时间
     *
     * @return 获取响应的超时时间
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * 设置获取请求的超时时间
     *
     * @param readTimeout 获取请求的超时时间
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 返回写入请求的超时时间
     *
     * @return 写入请求的超时时间
     */
    public int getWriteTimeout() {
        return writeTimeout;
    }

    /**
     * 设置写入请求的超时时间
     *
     * @param writeTimeout 写入请求的超时时间
     */
    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
