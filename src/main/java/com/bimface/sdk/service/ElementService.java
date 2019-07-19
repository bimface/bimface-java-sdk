package com.bimface.sdk.service;


import com.bimface.data.bean.ElementPropertyFilterRequest;
import com.bimface.data.bean.ElementsWithBoundingBox;
import com.bimface.data.bean.MaterialInfo;
import com.bimface.data.bean.Property;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.QueryElementIdsRequest;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;

/**
 * 查询构件ID服务
 *
 * @author bimface, 2016-11-02.
 */
public class ElementService {
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public ElementService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 按查询条件查询构件ID组
     *
     * @param fileId     文件id
     * @param floor      楼层
     * @param specialty  专业
     * @param categoryId 分类id
     * @param family     族
     * @param familyType 族类型
     * @return List&lt;{@link String}&gt;
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public List<String> getElements(Long fileId, String floor, String specialty, String categoryId, String family,
                                    String familyType) throws BimfaceException {
        return dataClient.getSingleModelElements(fileId, specialty, floor, categoryId, family, familyType, accessTokenService.getAccessToken());
    }

    /**
     * 按查询条件获取集成模型的构件列表
     *
     * @param integrateId 集成id
     * @param floor       楼层
     * @param specialty   专业
     * @param categoryId  分类id
     * @param family      族
     * @param familyType  族类型
     * @return {@link ElementsWithBoundingBox}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public ElementsWithBoundingBox getIntegrateElements(Long integrateId, String floor, String specialty, String categoryId,
                                                        String family, String familyType) throws BimfaceException {
        return dataClient.getIntegrateModelElements(integrateId, specialty, floor, categoryId, family, familyType, null, accessTokenService.getAccessToken());
    }

    /**
     * 按查询条件查询构件ID 组，v2
     *
     * @param fileId
     * @param queryElementIdsRequest
     * @return
     * @throws BimfaceException
     */
    public List<String> getElementIdsV2(Long fileId, QueryElementIdsRequest queryElementIdsRequest) throws BimfaceException {
        return dataClient.getSingleModelElementIds(fileId, queryElementIdsRequest, accessTokenService.getAccessToken());
    }

    /**
     * 获取单模型的单个构件，v2
     *
     * @param fileId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public Property getSingleModelElementV2(Long fileId, String elementId, boolean includeOverrides) throws BimfaceException {
        return dataClient.getSingleModelElement(fileId, elementId, includeOverrides, accessTokenService.getAccessToken());
    }

    /**
     * 根据构件 ID 批量获取单模型的构件，v2
     *
     * @param fileId
     * @param elementIds
     * @return
     * @throws BimfaceException
     */
    public List<Property> getSingleModelElementsV2(Long fileId, List<String> elementIds) throws BimfaceException {
        return getSingleModelElementsV2(fileId, elementIds, null, false);
    }

    /**
     * 根据构件 ID 批量获取单模型的构件，v2
     *
     * @param fileId
     * @param elementIds
     * @param filter           查询筛选条件
     * @param includeOverrides 是否查询修改的属性
     * @return
     * @throws BimfaceException
     */
    public List<Property> getSingleModelElementsV2(Long fileId,
                                                   List<String> elementIds,
                                                   List<ElementPropertyFilterRequest.GroupAndKeysPair> filter,
                                                   boolean includeOverrides) throws BimfaceException {
        ElementPropertyFilterRequest propertyFilterRequest = new ElementPropertyFilterRequest();
        propertyFilterRequest.setElementIds(elementIds);
        propertyFilterRequest.setFilter(filter);
        return dataClient.getSingleModelElements(fileId, includeOverrides, propertyFilterRequest, accessTokenService.getAccessToken());
    }

    /**
     * 获取单模型的构件材质，v2
     *
     * @param fileId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public List<MaterialInfo> getSingleModelMaterials(Long fileId, String elementId) throws BimfaceException {
        return dataClient.getSingleModelElementMaterials(fileId, elementId, accessTokenService.getAccessToken());
    }

}
