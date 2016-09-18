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

    private final String CREATE_SHARE_URL         = getApiHost() + "/share?transferId=%s&activeHours=%s";
    private final String CREATE_SHARE_URL_FOREVER = getApiHost() + "/share?transferId=%s";
    private final String DELETE_SHARE_URL         = getApiHost() + "/share?transferId=%s";

    public ShareLinkService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public ShareLinkBean create(String transferId, Integer activeHours) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");
        if (activeHours != null && activeHours <= 0) {
            throw new IllegalArgumentException("activeHours must not less than zero.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_SHARE_URL, transferId, activeHours), "",
                                                    headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    public ShareLinkBean create(String transferId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_SHARE_URL_FOREVER, transferId), "", headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    public String delete(String transferId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().delete(String.format(DELETE_SHARE_URL, transferId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }
}
