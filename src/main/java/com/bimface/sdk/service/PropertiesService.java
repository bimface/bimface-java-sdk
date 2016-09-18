package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.PropertiesBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 模型属性服务
 * 
 * @author bimface, 2016-06-01.
 */
public class PropertiesService extends AbstractAccessTokenService {

    private final String PROPERTIES_URL = getApiHost() + "/attribute?transferId=%s&elementId=%s";

    public PropertiesService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public PropertiesBean getProperties(String transferId, String elementId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");
        AssertUtils.assertStringNotNullOrEmpty(elementId, "elementId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(PROPERTIES_URL, transferId, elementId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<PropertiesBean>>() {});
    }
}
