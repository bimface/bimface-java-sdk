package com.bimface.sdk.service;

import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.data.bean.DrawingSplitLayout;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.DatabagDerivativeRequest;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.utils.AssertUtils;

import java.util.List;
import java.util.Map;

/**
 * 图纸拆分
 */
public class DrawingSplitService {
    private ApiClient apiClient;
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public DrawingSplitService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 发起图纸拆分
     * @param fileId
     * @param callback
     * @param config
     * @return
     * @throws BimfaceException
     */
    public TranslateDatabagDerivativeBean createDrawingSplit(Long fileId, String callback, Map<String, String> config) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        DatabagDerivativeRequest databagDerivativeRequest = new DatabagDerivativeRequest();
        databagDerivativeRequest.setConfig(config);
        return apiClient.createDrawingSplit(fileId, callback, databagDerivativeRequest, accessTokenService.getAccessToken());
    }

    /**
     * 获取拆图状态
     * @param fileId 文件id
     * @return
     * @throws BimfaceException
     */
    public TranslateDatabagDerivativeBean getDrawingSplit(Long fileId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return apiClient.getDrawingSplit(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 获取图纸拆分结果
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<DrawingSplitLayout> getDrawingFrames(Long fileId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return dataClient.getDrawingFrames(fileId, accessTokenService.getAccessToken());
    }
}
