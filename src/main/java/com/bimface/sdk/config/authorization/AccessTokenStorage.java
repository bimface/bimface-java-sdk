package com.bimface.sdk.config.authorization;

import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.response.AccessTokenBean;

/**
 * 缓存AccessToken的接口定义
 * 
 * @author bimface, 2016-06-01.
 */
public interface AccessTokenStorage {

    /**
     * 保存accessToken
     * 
     * @param accessTokenBean {@link AccessTokenBean}
     */
    void put(AccessTokenBean accessTokenBean);

    /**
     * 获取accessToken
     * 
     * @return {@link AccessTokenBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    AccessTokenBean get() throws BimfaceException;
}
