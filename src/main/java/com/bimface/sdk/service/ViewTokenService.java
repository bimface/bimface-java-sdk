package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 获取viewToken
 * 
 * @author bimface, 2016-06-01.
 */
public class ViewTokenService extends AbstractAccessTokenService {

    private final String VIEW_TOKEN_URL = getApiHost() + "/view/token?transferId=%s";

    public ViewTokenService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public String grantViewToken(String transferId) throws BimfaceException {
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");
        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(VIEW_TOKEN_URL, transferId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }
}
