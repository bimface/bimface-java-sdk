package com.bimface.sdk.service;

import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.response.AccessTokenBean;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;

/**
 * 获取AccessToken
 * 
 * @author bimface, 2016-06-01.
 */
public class AccessTokenService {
    private Credential         credential;
    private AccessTokenStorage accessTokenStorage;
    private ApiClient apiClient;

    public AccessTokenService(Endpoint endpoint, Credential credential,
                              AccessTokenStorage accessTokenStorage) {
        this.credential = credential;
        this.accessTokenStorage = accessTokenStorage;
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
    }

    public AccessTokenBean getAccessTokenBean() throws BimfaceException {
        AccessTokenBean accessTokenBean = accessTokenStorage.get();
        if (accessTokenBean == null) {
            synchronized (this) {
                if (accessTokenBean == null) {
                    accessTokenBean = grantAccessTokenBean();
                    accessTokenStorage.put(accessTokenBean);
                }
            }
        }
        return accessTokenBean;
    }

    public synchronized void updateAccessTokenBean() throws BimfaceException {
        AccessTokenBean accessTokenBean = grantAccessTokenBean();
        accessTokenStorage.put(accessTokenBean);
    }

    public String getAccessToken() throws BimfaceException {
        AccessTokenBean accessTokenBean = getAccessTokenBean();
        return accessTokenBean.getToken();
    }

    private AccessTokenBean grantAccessTokenBean() throws BimfaceException {

        return apiClient.applyOAuthToken(credential);
    }
}
