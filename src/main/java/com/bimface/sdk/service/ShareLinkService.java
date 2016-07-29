package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.ShareLinkBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 分享链接
 * 
 * @author bimface, 2016-06-01.
 */
public class ShareLinkService extends AbstractAccessTokenService {

    private final String CREATE_SHARE_URL = getApiHost() + "/share?viewId=%s&activeHours=%s";
    private final String UPDATE_SHARE_URL = getApiHost() + "/share?viewId=%s&activeHours=%s";
    private final String GET_SHARE_URL    = getApiHost() + "/share?viewId=%s";
    private final String DELETE_SHARE_URL = getApiHost() + "/share?viewId=%s";

    public ShareLinkService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public ShareLinkBean create(String viewId, Integer activeHours) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(viewId, "viewId");
        if (activeHours != null && activeHours <= 0) {
            throw new IllegalArgumentException("activeHours must not less than zero.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_SHARE_URL, viewId, activeHours), "", headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    public ShareLinkBean get(String viewId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(viewId, "viewId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_SHARE_URL, viewId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    public ShareLinkBean update(String viewId, Integer activeHours) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(viewId, "viewId");
        if (activeHours != null && activeHours <= 0) {
            throw new IllegalArgumentException("activeHours must not less than zero.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().put(String.format(UPDATE_SHARE_URL, viewId, activeHours), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    public String delete(String viewId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(viewId, "viewId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().delete(String.format(DELETE_SHARE_URL, viewId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }
}
