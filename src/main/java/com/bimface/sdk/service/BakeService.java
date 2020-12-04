package com.bimface.sdk.service;

import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.DatabagDerivativeRequest;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.utils.AssertUtils;

import java.util.List;
import java.util.Map;

/**
 * 烘焙相关
 */
public class BakeService {
    private ApiClient apiClient;
    private AccessTokenService accessTokenService;

    public BakeService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 为单文件创建bake数据包
     * @param fileId
     * @param callback
     * @param config
     * @return
     * @throws BimfaceException
     */
    public TranslateDatabagDerivativeBean createTranslateBakeDatabag(Long fileId, String callback, Map<String, String> config) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        DatabagDerivativeRequest databagDerivativeRequest = new DatabagDerivativeRequest();
        databagDerivativeRequest.setConfig(config);
        return apiClient.createTranslateBakeDatabag(fileId, callback,
            databagDerivativeRequest, accessTokenService.getAccessToken());
    }

    /**
     * 查询单文件bake数据包
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<TranslateDatabagDerivativeBean> getTranslateBakeDatabags(Long fileId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return apiClient.getTranslateBakeDatabags(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 为集成模型创建bake数据包
     * @param integrateId
     * @param callback
     * @param config
     * @return
     * @throws BimfaceException
     */
    public IntegrateDatabagDerivativeBean createIntegrateBakeDatabag(Long integrateId, String callback, Map<String, String> config) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        DatabagDerivativeRequest databagDerivativeRequest = new DatabagDerivativeRequest();
        databagDerivativeRequest.setConfig(config);
        return apiClient.createIntegrateBakeDatabag(integrateId, callback,
                databagDerivativeRequest, accessTokenService.getAccessToken());
    }

    /**
     * 查询集成模型bake数据包
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<IntegrateDatabagDerivativeBean> getIntegrateBakeDatabags(Long integrateId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        return apiClient.getIntegrateBakeDatabags(integrateId, accessTokenService.getAccessToken());
    }
}
