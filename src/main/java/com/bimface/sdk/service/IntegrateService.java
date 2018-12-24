package com.bimface.sdk.service;


import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;

/**
 * 文件转换
 * 
 * @author bimface, 2016-06-01.
 */
public class IntegrateService{

    private ApiClient apiClient;
    private AccessTokenService accessTokenService;

    public IntegrateService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 发起文件集成
     * 
     * @param request 文件集成请求
     * @return {@link FileIntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileIntegrateBean integrate(FileIntegrateRequest request) throws BimfaceException {
        return apiClient.integrate(request, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型的状态
     * 
     * @param integrateId 集成id
     * @return {@link FileIntegrateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileIntegrateBean getIntegrate(Long integrateId) throws BimfaceException {
        return apiClient.getIntegrate(integrateId, accessTokenService.getAccessToken());
    }
    
    /**
     * 删除集成模型
     * @param integrateId 集成模型id
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteIntegrate(Long integrateId) throws BimfaceException {
        apiClient.deleteIntegrate(integrateId, accessTokenService.getAccessToken());
    }
}
