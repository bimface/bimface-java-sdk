package com.bimface.sdk.service;

import com.bimface.api.bean.compatible.response.BatchDeleteResultBean;
import com.bimface.api.bean.compatible.response.ShareLinkBean;
import com.bimface.exception.BimfaceException;
import com.bimface.page.PagedList;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;

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
        return apiClient.createShare(fileId, null, activeHours, null, false,  accessTokenService.getAccessToken());
    }

    /**
     * 创建单个文件浏览分享链接,带分享密码
     * @param fileId
     * @param expireDate
     * @param needPassword
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean createShare(Long fileId, String expireDate, Boolean needPassword) throws BimfaceException {
        return apiClient.createShare(fileId, null, null, expireDate, needPassword, accessTokenService.getAccessToken());
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
    public ShareLinkBean createIntegrateShare(Long integrateId, Integer activeHours) throws BimfaceException {
        return apiClient.createShare(null, integrateId, activeHours, null, false,  accessTokenService.getAccessToken());
    }

    /**
     * 创建集成文件浏览分享链接,带分享密码
     * @param integrateId
     * @param expireDate
     * @param needPassword
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean createIntegrateShare(Long integrateId, String expireDate, Boolean needPassword) throws BimfaceException {
        return apiClient.createShare(null, integrateId, null, expireDate, needPassword,  accessTokenService.getAccessToken());
    }

    /**
     * 分享集成模型，生成链接，永久有效
     *
     * @param integrateId 集成id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createIntegrateShare(Long integrateId) throws BimfaceException {
        return createIntegrateShare(integrateId, null);
    }

    /**
     * 取消集成模型的分享链接
     *
     * @param integrateId 集成id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteIntegrateShare(Long integrateId) throws BimfaceException {
        return apiClient.deleteShare(null, integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 批量删除分享链接
     * @param sourceIds
     * @return
     * @throws BimfaceException
     */
    public BatchDeleteResultBean<Long> batchDeteleShare(List<Long> sourceIds) throws BimfaceException{
        return apiClient.batchDeteleShare(sourceIds, accessTokenService.getAccessToken());
    }

    /**
     * 获取分享链接信息
     * @param token
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean getShareLink(String token) throws BimfaceException{
        return apiClient.getShareLink(token, null, null, accessTokenService.getAccessToken());
    }

    /**
     * 获取分享链接信息
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean getTranslateShare(Long fileId) throws BimfaceException{
        return apiClient.getShareLink(null, fileId, null, accessTokenService.getAccessToken());
    }

    /**
     * 获取分享链接信息
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public ShareLinkBean getIntegrateShare(Long integrateId) throws BimfaceException{
        return apiClient.getShareLink(null, null, integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 获取分享列表
     * @return
     * @throws BimfaceException
     */
    public PagedList<ShareLinkBean> shareList(Integer pageNo, Integer pageSize) throws BimfaceException{
        return apiClient.shareList(accessTokenService.getAccessToken(),pageNo, pageSize);
    }
}
