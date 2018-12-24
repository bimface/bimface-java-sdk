package com.bimface.sdk.service;

import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;

/**
 * 获取viewToken
 * 
 * @author bimface, 2016-06-01.
 */
public class ViewTokenService {
    private ApiClient apiClient;
    private AccessTokenService accessTokenService;

    public ViewTokenService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient (endpoint.getApiHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 用fileId获取单文件浏览凭证
     * 
     * @param fileId 文件id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByFileId(Long fileId) throws BimfaceException {
        return getViewTokenByFileId(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 用fileId获取单文件浏览凭证
     *
     * @param fileId 文件id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByFileId(Long fileId, String accessToken) throws BimfaceException {
        return apiClient.getViewToken(fileId, null, null, accessToken);
    }

    /**
     * 获取集成模型的浏览凭证
     * 
     * @param integrateId 集成id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByIntegrateId(Long integrateId) throws BimfaceException {
        return getViewTokenByIntegrateId(integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型的浏览凭证
     *
     * @param integrateId 集成id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByIntegrateId(Long integrateId, String accessToken) throws BimfaceException {
        return apiClient.getViewToken(null, integrateId, null, accessToken);
    }

    /**
     * 获取模型对比的浏览凭证
     *
     * @param compareId 模型对比id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByCompareId(Long compareId) throws BimfaceException {
        return getViewTokenByCompareId(compareId, accessTokenService.getAccessToken());
    }

    /**
     * 获取模型对比的浏览凭证
     *
     * @param compareId 模型对比id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String getViewTokenByCompareId(Long compareId, String accessToken) throws BimfaceException {
        return apiClient.getViewToken(null, null, compareId, accessToken);
    }
}
