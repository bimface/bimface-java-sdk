package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.PropertyBean;
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
public class PropertyService extends AbstractAccessTokenService {

    private final String PROPERTIES_URL           = getApiHost() + "/data/element/property?fileId=%s&elementId=%s";

    private final String INTEGRATE_PROPERTIES_URL = getApiHost()
                                                    + "/data/integration/element/property?integrateId=%s&fileId=%s&elementId=%s";

    public PropertyService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 获取单文件的构件属性
     * 
     * @param fileId 文件id
     * @param elementId 构件id
     * @return {@link PropertyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public PropertyBean getElementProperty(Long fileId, String elementId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        AssertUtils.assertStringNotNullOrEmpty(elementId, "elementId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(PROPERTIES_URL, fileId, elementId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<PropertyBean>>() {});
    }

    /**
     * 获取集成模型的构件属性
     * 
     * @param integrateId 集成id
     * @param fileId 文件id（模型集成之前所属的单文件id）
     * @param elementId 构件id
     * @return {@link PropertyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public PropertyBean getIntegrationElementProperty(Long integrateId, Long fileId,
                                                      String elementId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        AssertUtils.assertStringNotNullOrEmpty(elementId, "elementId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(INTEGRATE_PROPERTIES_URL, integrateId, fileId,
                                                                 elementId),
                                                   headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<PropertyBean>>() {});
    }
}
