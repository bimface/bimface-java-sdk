package com.bimface.sdk.service;

import com.bimface.data.bean.Property;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;

/**
 * 模型属性服务
 *
 * @author bimface, 2016-06-01.
 */
public class PropertyService {
    private DataClient dataClient;
    private AccessTokenService accessTokenService;
    public PropertyService(Endpoint endpoint, AccessTokenService accessTokenService) {
        dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 获取单文件的构件属性
     *
     * @deprecated
     * @param fileId 文件id
     * @param elementId 构件id
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    @Deprecated
    public Property getElementProperty(Long fileId, String elementId) throws BimfaceException {
        return dataClient.getSingleModelElementProperty(fileId, elementId, accessTokenService.getAccessToken());
    }

    /**
     * 获取单文件的构件属性，v2
     *
     * @param fileId 文件id
     * @param elementIds 构件id
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    public Property getSingleModelElementPropertyV2(Long fileId, List<String> elementIds, Boolean includeOverrides) throws BimfaceException {
        return dataClient.getSingleModelElementProperty(fileId, elementIds, includeOverrides, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型的构件属性
     *
     * @deprecated
     * @param integrateId 集成id
     * @param fileId 文件id（模型集成之前所属的单文件id）
     * @param elementId 构件id
     * @return {@link Property}
     * @throws BimfaceException {@link BimfaceException}
     */
    @Deprecated
    public Property getIntegrationElementProperty(Long integrateId, Long fileId,
                                                  String elementId) throws BimfaceException {
        return dataClient.getIntegrateModelProperty(integrateId, fileId, elementId, accessTokenService.getAccessToken());
    }
}
