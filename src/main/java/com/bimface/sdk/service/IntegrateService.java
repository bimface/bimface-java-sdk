package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.request.integrate.IntegrateRequest;
import com.bimface.sdk.bean.response.IntegrateBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 文件转换
 * 
 * @author bimface, 2016-06-01.
 */
public class IntegrateService extends AbstractAccessTokenService {

    private final String INTEGRATE_URL     = getApiHost() + "/integrate";
    private final String GET_INTEGRATE_URL = getApiHost() + "/integrate?integrateId=%s";

    public IntegrateService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 发起文件集成
     * 
     * @param request 文件集成请求
     * @return {@link IntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateBean integrate(IntegrateRequest request) throws BimfaceException {

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().put(INTEGRATE_URL, request, headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<IntegrateBean>>() {});
    }

    /**
     * 获取集成模型的状态
     * 
     * @param integrateId 集成id
     * @return {@link IntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public IntegrateBean getIntegrate(Long integrateId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_INTEGRATE_URL, integrateId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<IntegrateBean>>() {});
    }
}
