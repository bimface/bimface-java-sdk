package com.bimface.sdk.config.authorization;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bimface.sdk.bean.response.AccessTokenBean;

/**
 * 默认情况下，缓存AccessToken的方式
 * 
 * @author bimface, 2016-06-01.
 */
public class DefaultAccessTokenStorage implements AccessTokenStorage {

    private AccessTokenBean accessTokenBean;

    public synchronized void put(AccessTokenBean accessTokenBean) {
        this.accessTokenBean = accessTokenBean;
    }

    public AccessTokenBean get() {
        if (accessTokenBean == null) {
            return null;
        }
        if (maybeExpire(accessTokenBean.getExpireTime())) {
            return null;
        }
        return accessTokenBean;
    }

    /**
     * 判断是否过期或即将过期
     * 
     * @param expireTime 过期时间
     * @return true:已过期或即将过期, false:未过期
     */
    private boolean maybeExpire(String expireTime) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date expire = dateFormat.parse(accessTokenBean.getExpireTime());
            return (expire.getTime() - System.currentTimeMillis()) <= 1000; // 时间需要大于1秒
        } catch (ParseException e) {
            return true;
        }
    }
}
