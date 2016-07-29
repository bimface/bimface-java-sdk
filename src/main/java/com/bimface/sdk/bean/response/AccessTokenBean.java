package com.bimface.sdk.bean.response;

/**
 * AccessToken的返回参数
 * 
 * @author bimface, 2016-06-01.
 */
public class AccessTokenBean {

    private String token;     // AccessToken
    private String expireTime;// AccessToken的失效时间，格式：yyyy-MM-dd hh:mm:ss

    public AccessTokenBean() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "AccessTokenBean [token=" + token + ", expireTime=" + expireTime + "]";
    }
}
