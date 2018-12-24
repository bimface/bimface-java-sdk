package com.bimface.sdk.constants;

import okhttp3.MediaType;

import java.nio.charset.Charset;

/**
 * 常量
 * 
 * @author bimface, 2016-06-01.
 */
public class BimfaceConstants {

    // 默认字符集编码
    public static final Charset   UTF_8                          = Charset.forName("UTF-8");

    public static final MediaType MEDIA_TYPE_JSON                = MediaType.parse("application/json; charset=utf-8");

    // API服务器地址
    public static final String    API_HOST                       = "https://api.bimface.com";

    // 文件上传API服务器地址
    public static final String    FILE_HOST                      = "https://file.bimface.com";

    public static final String    STREAM_MIME                    = "application/octet-stream";
    public static final String    JSON_MIME                      = "application/json";
    public static final String    FORM_MIME                      = "application/x-www-form-urlencoded";

    // 断点上传时的分块大小(默认的分块大小, 不允许改变)
    public static final int       BLOCK_SIZE                     = 4 * 1024 * 1024;

    // 如果文件大小大于此值则使用断点上传, 否则使用Form上传
    public static int             PUT_THRESHOLD                  = BLOCK_SIZE;

    // 最大空闲连接数
    public static final int       DEFAULT_MAX_IDLE_CONNECTIONS   = 32;

    // 保持活动周期时长
    public static final long      DEFAULT_KEEP_ALIVE_DURATION_NS = 5 * 60 * 1000;

    // 最大请求数
    public static final int       DEFAULT_MAX_REQUESTS           = 64;

    // 每台主机最大的请求数
    public static final int       DEFAULT_MAX_REQUESTS_PER_HOST  = 5;

    // 连接超时时间 单位秒(默认10s)
    public static final int       DEFAULT_CONNECT_TIMEOUT        = 10;

    // 写超时时间 单位秒(默认 0 , 不超时)
    public static final int       DEFAULT_WRITE_TIMEOUT          = 0;

    // 回复超时时间 单位秒(默认30s)
    public static final int       DEFAULT_RESPONSE_TIMEOUT       = 30;
}
