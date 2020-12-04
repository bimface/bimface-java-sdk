package com.bimface.sdk.service;


import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.DatabagDerivativeRequest;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.utils.AssertUtils;

import java.util.List;
import java.util.Map;

/**
 * 导出数据表相关
 */
public class ExportDatabagService {
    private ApiClient apiClient;
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public ExportDatabagService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 导出gltf数据包
     *
     * @param fileId
     * @param callback
     * @return
     * @throws BimfaceException
     */
    public TranslateDatabagDerivativeBean exportTranslateGltfDatabag(Long fileId, String callback) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return apiClient.exportTranslateGltfDatabag(fileId, callback, accessTokenService.getAccessToken());
    }

    /**
     * 获取gltf数据包信息
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<TranslateDatabagDerivativeBean> getTranslateGltfDatabag(Long fileId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return apiClient.getTranslateGltfDatabag(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 导出集成模型gltf数据包
     *
     * @param integrateId
     * @param callback
     * @return
     * @throws BimfaceException
     */
    public IntegrateDatabagDerivativeBean exportIntegrateGltfDatabag(Long integrateId, String callback) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        return apiClient.exportIntegrateGltfDatabag(integrateId, callback, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型gltf数据包信息
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<IntegrateDatabagDerivativeBean> getIntegrateGltfDatabags(Long integrateId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        return apiClient.getIntegrateGltfDatabags(integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 获取gltf数据包下载链接
     *
     * @param fileId         文件id
     * @param databagVersion 数据包版本
     * @return
     * @throws BimfaceException
     */
    public String getTranslateGltfDatabagUrl(Long fileId, String databagVersion) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return dataClient.getDatabagDownloadUrl(fileId, null, null, "gltf", databagVersion,
                accessTokenService.getAccessToken());
    }

    /**
     * 获取gltf数据包下载链接
     *
     * @param integrateId    集成id
     * @param databagVersion 数据包版本
     * @return
     * @throws BimfaceException
     */
    public String getIntegrateGltfDatabagUrl(Long integrateId, String databagVersion) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        return dataClient.getDatabagDownloadUrl(null, integrateId, null, "gltf", databagVersion,
                accessTokenService.getAccessToken());
    }

    /**
     * 通过文件ID创建3DTiles数据包
     *
     * @param fileId
     * @param callback
     * @param config
     * @return
     * @throws BimfaceException
     */
    public TranslateDatabagDerivativeBean exportTranslate3DTilesDatabag(Long fileId, String callback, Map<String, String> config) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        DatabagDerivativeRequest databagDerivativeRequest = new DatabagDerivativeRequest();
        databagDerivativeRequest.setConfig(config);
        return apiClient.exportTranslate3DTilesDatabag(fileId, callback, databagDerivativeRequest, accessTokenService.getAccessToken());
    }

    /**
     * 查询文件的3DTiles数据包
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<TranslateDatabagDerivativeBean> getTranslate3DTilesDatabags(Long fileId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return apiClient.getTranslate3DTilesDatabags(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 通过集成模型ID创建3DTiles数据包
     *
     * @param integrateId
     * @param callback
     * @param config
     * @return
     * @throws BimfaceException
     */
    public IntegrateDatabagDerivativeBean exportIntegrate3DTilesDatabag(Long integrateId, String callback, Map<String, String> config) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        DatabagDerivativeRequest databagDerivativeRequest = new DatabagDerivativeRequest();
        databagDerivativeRequest.setConfig(config);
        return apiClient.exportIntegrate3DTilesDatabag(integrateId, callback, databagDerivativeRequest, accessTokenService.getAccessToken());
    }

    /**
     * 查询集成模型3DTiles数据包
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<IntegrateDatabagDerivativeBean> getIntegrate3DTilesDatabags(Long integrateId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        return apiClient.getIntegrate3DTilesDatabags(integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 获取3DTiles数据包下载地址
     *
     * @param fileId         文件id
     * @param databagVersion 数据包版本
     * @return
     * @throws BimfaceException
     */
    public String getTranslate3dTilesDatabagUrl(Long fileId, String databagVersion) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        return dataClient.getDatabagDownloadUrl(fileId, null, null, "3dtiles", databagVersion,
                accessTokenService.getAccessToken());
    }

    /**
     * 获取3DTiles数据包下载地址
     *
     * @param integrateId    集成id
     * @param databagVersion 数据包版本
     * @return
     * @throws BimfaceException
     */
    public String getIntegrate3dTilesDatabagUrl(Long integrateId, String databagVersion) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        return dataClient.getDatabagDownloadUrl(null, integrateId, null, "3dtiles", databagVersion,
                accessTokenService.getAccessToken());
    }
}
