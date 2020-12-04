package com.bimface.sdk.service;


import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.DatabagDerivativeRequest;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;

/**
 * 离线数据包
 *
 * @author bimface, 2017-08-23.
 */
public class OfflineDatabagService {
    private ApiClient apiClient;
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public OfflineDatabagService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
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
        DatabagDerivativeRequest databagDerivativeRequest = new DatabagDerivativeRequest();
        databagDerivativeRequest.setConfig(request.getConfig());
        if (request.getFileId() != null) {
            return apiClient.createTranslateOfflineDatabag(request.getFileId(), request.getCallback(),
                    databagDerivativeRequest, accessTokenService.getAccessToken());
        } else if (request.getIntegrateId() != null) {
            return apiClient.createIntegateOfflineDatabag(request.getIntegrateId(), request.getCallback(),
                    databagDerivativeRequest, accessTokenService.getAccessToken());
        } else if (request.getCompareId() != null) {
            return apiClient.createCompareOfflineDatabag(request.getCompareId(), request.getCallback(),
                    databagDerivativeRequest, accessTokenService.getAccessToken());
        } else {
            throw new NullPointerException("'fileId', 'integrateId', 'compareId' can't be all empty");
        }
    }

    /**
     * 查询离线数据包
     * 已过时：请使用：
     * {@linkplain com.bimface.sdk.service.OfflineDatabagService#queryOfflineDatabag(java.lang.Long, java.lang.Long,
     * java.lang.Long) queryOfflineDatabag(java.lang.Long, java.lang.Long, java.lang.Long)}
     *
     * @param request 离线数据包请求
     * @return {@link DatabagDerivativeBean}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(OfflineDatabagRequest request) throws BimfaceException {
        return queryOfflineDatabag(request.getFileId(), request.getIntegrateId(), request.getCompareId());
    }

    /**
     * 查询离线数据包
     *
     * @param fileId
     * @param integrateId
     * @param compareId
     * @return
     * @throws BimfaceException
     */
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(Long fileId, Long integrateId, Long compareId) throws BimfaceException {
        if (fileId != null) {
            return apiClient.getTranslateOfflineDatabag(fileId, accessTokenService.getAccessToken());
        } else if (integrateId != null) {
            return apiClient.getIntegrateOfflineDatabag(integrateId, accessTokenService.getAccessToken());
        } else if (compareId != null) {
            return apiClient.getCompareOfflineDatabag(compareId, accessTokenService.getAccessToken());
        } else {
            throw new NullPointerException("'fileId', 'integrateId', 'compareId' can't be all empty");
        }
    }

    /**
     * 获取离线数据下载链接
     * 已过时，请使用：
     * {@linkplain com.bimface.sdk.service.OfflineDatabagService#getOfflineDatabagUrl(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.String)
     * getOfflineDatabagUrl(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)}
     *
     * @param request 离线数据包请求
     * @return 离线数据包下载地址
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public String getOfflineDatabagUrl(OfflineDatabagRequest request) throws BimfaceException {
        return dataClient.getDatabagDownloadUrl(request.getFileId(), request.getIntegrateId(), request.getCompareId(),
                "offline", request.getDatabagVersion(), accessTokenService.getAccessToken());
    }

    /**
     * 获取离线数据下载链接
     * 其中，文件id，集尘id和比较id不能全为空
     *
     * @param fileId 文件id
     * @param integrateId 集成id
     * @param compareId 比较id
     * @param databagVersion 数据包版本
     * @return
     * @throws BimfaceException
     */
    public String getOfflineDatabagUrl(Long fileId, Long integrateId, Long compareId, String databagVersion) throws BimfaceException {
        return dataClient.getDatabagDownloadUrl(fileId, integrateId, compareId, "offline", databagVersion,
                accessTokenService.getAccessToken());
    }
}
