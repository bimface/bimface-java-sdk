package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.AccessTokenBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.config.authorization.AccessTokenStorage;
import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.squareup.okhttp.Response;

/**
 * 获取AccessToken
 * 
 * @author bimface, 2016-06-01.
 */
public final class AccessTokenService extends AbstractService {

    private final String       ACCESS_TOKEN_URL = getApiHost() + "/oauth2/token";

    private Credential         credential;
    private AccessTokenStorage accessTokenStorage;

    public AccessTokenService(ServiceClient serviceClient, Endpoint endpoint, Credential credential,
                              AccessTokenStorage accessTokenStorage) {
        super(serviceClient, endpoint);
        this.credential = credential;
        this.accessTokenStorage = accessTokenStorage;
    }

    public AccessTokenBean get() throws BimfaceException {
        AccessTokenBean accessTokenBean = accessTokenStorage.get();
        if (accessTokenBean == null) {
            accessTokenBean = grant();
            accessTokenStorage.put(accessTokenBean);
        }
        return accessTokenBean;
    }

    private AccessTokenBean grant() throws BimfaceException {
        HttpHeaders headers = new HttpHeaders();
        headers.addBasicAuthHeader(credential.getAppKey(), credential.getAppSecret());
        Response response = getServiceClient().post(ACCESS_TOKEN_URL, getServiceClient().emptyRequestBody.toString(),
                                                    headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<AccessTokenBean>>() {});
    }
}
