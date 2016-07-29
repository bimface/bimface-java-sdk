package com.bimface.sdk.service;

import com.bimface.sdk.bean.response.AccessTokenBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.ServiceClient;

/**
 * 业务处理的抽象类，针对需要AccessToken的实现
 * 
 * @author bimface, 2016-06-01.
 */
public abstract class AbstractAccessTokenService extends AbstractService {

    private AccessTokenService accessTokenService;

    public AbstractAccessTokenService() {
    }

    public AbstractAccessTokenService(ServiceClient serviceClient, Endpoint endpoint,
                                      AccessTokenService accessTokenService) {
        super(serviceClient, endpoint);
        this.accessTokenService = accessTokenService;
    }

    public String getAccessToken() throws BimfaceException {
        AccessTokenBean accessTokenBean = accessTokenService.get();
        if (accessTokenBean == null) {
            throw new NullPointerException("AccessTokenBean is null, can not get access token.");
        }
        return accessTokenBean.getToken();
    }
}
