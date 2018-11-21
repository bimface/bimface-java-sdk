package com.bimface.sdk.service;


import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;

/**
 * 离线数据包
 *
 * @author xxy, 2017-08-23.
 */
public class OfflineDatabagService {
    private ApiClient apiClient;
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public OfflineDatabagService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.dataClient = DataClient.getDataClient(endpoint.getFileHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 构建离线数据包
     *
     * @param request 离线数据包请求
     * @return {@link DatabagDerivativeBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public DatabagDerivativeBean generateOfflineDatabag(OfflineDatabagRequest request) throws BimfaceException {
        if (request.getFileId() != null) {
            return apiClient.createTranslateOfflineDatabag(request.getFileId(), request.getCallback(), accessTokenService.getAccessToken());
        } else if (request.getIntegrateId() != null) {
            return apiClient.createIntegateOfflineDatabag(request.getIntegrateId(), request.getCallback(), accessTokenService.getAccessToken());
        } else if (request.getCompareId() != null) {
            return apiClient.createCompareOfflineDatabag(request.getCompareId(), request.getCallback(), accessTokenService.getAccessToken());
        } else {
            throw new NullPointerException("'fileId', 'integrateId', 'compareId' can't be all empty");
        }
    }

    /**
     * 查询离线数据包
     *
     * @param request 离线数据包请求
     * @return {@link DatabagDerivativeBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(OfflineDatabagRequest request) throws BimfaceException {
        if (request.getFileId() != null) {
            return apiClient.getTranslateOfflineDatabag(request.getFileId(), accessTokenService.getAccessToken());
        } else if (request.getIntegrateId() != null) {
            return apiClient.getIntegateOfflineDatabag(request.getIntegrateId(), accessTokenService.getAccessToken());
        } else if (request.getCompareId() != null) {
            return apiClient.getCompareOfflineDatabag(request.getCompareId(), accessTokenService.getAccessToken());
        } else {
            throw new NullPointerException("'fileId', 'integrateId', 'compareId' can't be all empty");
        }
    }

    /**
     * 获取离线数据包信息
     *
     * @param request 离线数据包请求
     * @return 离线数据包下载地址
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getOfflineDatabagUrl(OfflineDatabagRequest request) throws BimfaceException {
        return dataClient.getDatabagDownloadUrl(request.getFileId(), request.getIntegrateId(), request.getCompareId(),
                "offline", request.getDatabagVersion(), accessTokenService.getAccessToken());
    }
}
