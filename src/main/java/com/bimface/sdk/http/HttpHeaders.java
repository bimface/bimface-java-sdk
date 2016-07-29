package com.bimface.sdk.http;

import java.util.HashMap;
import java.util.Map;

import com.bimface.sdk.utils.Base64;
import com.bimface.sdk.utils.StringUtils;

/**
 * HTTP headers 工具
 * 
 * @author bimface, 2016-06-01.
 */
public class HttpHeaders {

    public static final String  AUTHORIZATION       = "Authorization";
    public static final String  CACHE_CONTROL       = "Cache-Control";
    public static final String  CONTENT_DISPOSITION = "Content-Disposition";
    public static final String  CONTENT_ENCODING    = "Content-Encoding";
    public static final String  CONTENT_LENGTH      = "Content-Length";
    public static final String  CONTENT_MD5         = "Content-MD5";
    public static final String  CONTENT_TYPE        = "Content-Type";
    public static final String  TRANSFER_ENCODING   = "Transfer-Encoding";
    public static final String  DATE                = "Date";
    public static final String  ETAG                = "ETag";
    public static final String  EXPIRES             = "Expires";
    public static final String  HOST                = "Host";
    public static final String  LAST_MODIFIED       = "Last-Modified";
    public static final String  RANGE               = "Range";
    public static final String  LOCATION            = "Location";
    public static final String  CONNECTION          = "Connection";

    private Map<String, String> headers             = new HashMap<String, String>();

    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * 设置header
     * 
     * @param headers HTTP Qequest Heasers
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * header 追加
     * 
     * @param key Header key
     * @param value Header value
     */
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    /**
     * header里添加授权Authorization的Basic认证
     * 
     * @param appKey bimface颁发授权的应用key
     * @param appSecret bimface颁发授权的应用secret
     */
    public void addBasicAuthHeader(String appKey, String appSecret) {
        byte[] bytes = StringUtils.utf8Bytes(appKey + ":" + appSecret);
        String credential = "Basic " + Base64.encode(bytes);
        addHeader(AUTHORIZATION, credential.replaceAll("\n", "").replaceAll("\r", ""));
    }

    /**
     * header里添加授权Authorization的Bearer认证
     * 
     * @param token 由bimface颁发的appKey和appSecret通过Basic认证获取的token
     */
    public void addOAuth2Header(String token) {
        addHeader(AUTHORIZATION, "Bearer " + token);
    }
}
