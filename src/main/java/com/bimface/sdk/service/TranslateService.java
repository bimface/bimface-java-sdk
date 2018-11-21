package com.bimface.sdk.service;

import com.bimface.api.bean.request.translate.FileTranslateRequest;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.utils.AssertUtils;

/**
 * 文件转换
 * 
 * @author bimface, 2016-06-01.
 */
public class TranslateService {
    private ApiClient apiClient;
    private AccessTokenService accessTokenService;


    public TranslateService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 发起文件转换
     * 
     * @param request 文件转换请求
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean translate(FileTranslateRequest request) throws BimfaceException {
        return translate(request, accessTokenService.getAccessToken());
    }

    /**
     * 发起文件转换
     *
     * @param request 文件转换请求
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean translate(FileTranslateRequest request, String accessToken) throws BimfaceException {
        check(request);
        return apiClient.translate(request, accessToken);
    }

    /**
     * 查看文件转换状态
     *
     * @param fileId 文件Id
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean getTranslate(Long fileId) throws BimfaceException {
        return getTranslate(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 查看文件转换状态
     *
     * @param fileId 文件Id
     * @return {@link FileTranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileTranslateBean getTranslate(Long fileId, String accessToken) throws BimfaceException {
        return apiClient.getTranslation(fileId, accessToken);
    }


    private void check(FileTranslateRequest request) {
        AssertUtils.assertParameterNotNull(request, "TranslateRequest");
        AssertUtils.assertParameterNotNull(request.getSource(), "source");
        AssertUtils.assertParameterNotNull(request.getSource().getFileId(), "fileId");
    }
}
