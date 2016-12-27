package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 分享链接的返回参数
 * 
 * @author bimface, 2016-06-01.
 */
public class ShareLinkBean implements Serializable {

    private static final long serialVersionUID = 5433443661267897028L;
    private String            url;                                    // 分享链接的URL
    private String            expireTime;                             // 分享链接的过期时间，格式：yyyy-MM-dd hh:mm:ss

    public ShareLinkBean() {
    }

    public ShareLinkBean(String url, String expireTime) {
        this.url = url;
        this.expireTime = expireTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ShareLinkBean [url=" + url + ", expireTime=" + expireTime + "]";
    }
}
