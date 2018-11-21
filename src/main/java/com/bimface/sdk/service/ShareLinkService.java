package com.bimface.sdk.service;

import com.bimface.api.bean.compatible.response.ShareLinkBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;

/**
 * 分享链接
 * 
 * @author bimface, 2016-06-01.
 */
public class ShareLinkService {
    private ApiClient apiClient;
    private AccessTokenService accessTokenService;

    public ShareLinkService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 创建单文件模型的分享链接
     * 
     * @param fileId 文件Id
     * @param activeHours 有效时长（单位：小时）
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShare(Long fileId, Integer activeHours) throws BimfaceException {
        return apiClient.createShare(fileId, null, activeHours, accessTokenService.getAccessToken());
    }

    /**
     * 创建单文件模型的分享链接
     * 
     * @param fileId 文件Id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShare(Long fileId) throws BimfaceException {
        return createShare(fileId, null);
    }

    /**
     * 取消单文件模型的分享链接
     * 
     * @param fileId 文件id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteShare(Long fileId) throws BimfaceException {
        return apiClient.deleteShare(fileId, null, accessTokenService.getAccessToken());
    }

    /**
     * 分享集成模型，生成链接
     * 
     * @param integrateId 集成id
     * @param activeHours 有效时长（单位：小时）
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareIntegration(Long integrateId, Integer activeHours) throws BimfaceException {
        return apiClient.createShare(null, integrateId, activeHours, accessTokenService.getAccessToken());
    }

    /**
     * 分享集成模型，生成链接，永久有效
     * 
     * @param integrateId 集成id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareIntegration(Long integrateId) throws BimfaceException {
        return createShareIntegration(integrateId, null);
    }

    /**
     * 取消集成模型的分享链接
     * 
     * @param integrateId 集成id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteShareIntegration(Long integrateId) throws BimfaceException {
        return apiClient.deleteShare(null, integrateId, accessTokenService.getAccessToken());
    }
}
