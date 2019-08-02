package com.bimface.sdk.service;

import com.bimface.data.bean.*;
import com.bimface.data.enums.ToleranceType;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.QueryElementIdsRequest;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DataService {
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public DataService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 查询单模型三维视点信息，v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<View> getSingleModelViews(Long fileId) throws BimfaceException {

        return dataClient.getSingleModelViews(fileId, accessTokenService.getAccessToken());
    }

    /**
     * Rvt单模型查询楼层对应房间列表，v2
     *
     * @param fileId
     * @param floorId
     * @return
     * @throws BimfaceException
     */
    public List<Room> getSingleModelRooms(Long fileId, String floorId, String elementId, ToleranceType roomToleranceZ,
                                          ToleranceType roomToleranceXY) throws BimfaceException {
        AssertUtils.assertTrue(StringUtils.isNotBlank(floorId) || StringUtils.isNotBlank(elementId),
                "floorId or elementId can't be both empty or blank");
        return dataClient.getSingleModelRooms(fileId, floorId, elementId, roomToleranceZ, roomToleranceXY, accessTokenService.getAccessToken());
    }

    /**
     * Rvt单模型查询楼层对应房间, v2
     *
     * @param fileId
     * @param roomId
     * @return
     * @throws BimfaceException
     */
    public Room getSingleModelRoom(Long fileId, String roomId) throws BimfaceException {
        return dataClient.getSingleModelRoom(fileId, roomId, accessTokenService.getAccessToken());
    }

    /**
     * Rvt单模型查询楼层对应Area列表, v2
     *
     * @param fileId
     * @param floorId
     * @return {@link Area}
     * @throws BimfaceException
     */
    public List<Area> getSingleModelAreas(Long fileId, String floorId) throws BimfaceException {
        return dataClient.getSingleModelAreas(fileId, floorId, accessTokenService.getAccessToken());
    }

    /**
     * Rvt单模型查询 Area, v2
     *
     * @param fileId
     * @param areaId
     * @return
     * @throws BimfaceException
     */
    public Area getSingleModelArea(Long fileId, String areaId) throws BimfaceException {
        return dataClient.getSingleModelArea(fileId, areaId, accessTokenService.getAccessToken());
    }

    /**
     * 查询单模型获取构件分类树，v2
     *
     * @param fileId
     * @param requestBody {@link FileTreeRequestBody}
     * @return
     * @throws BimfaceException
     */
    public List<Tree.TreeNode> getSingleModelTreeV2(Long fileId, FileTreeRequestBody requestBody) throws BimfaceException {
        return dataClient.getSingleModelTreeV2(fileId, requestBody, accessTokenService.getAccessToken());
    }

    /**
     * 查询单模型自定义构件分类树
     *
     * @param fileId
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public Tree getSingleModelCustomizedTree(Long fileId, FileTreeRequestBody requestBody) throws BimfaceException {
        return dataClient.getSingleModelCustomizedTree(fileId, requestBody, accessTokenService.getAccessToken());
    }

    /**
     * 单模型查询链接, v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public List<Link> getSingleModelLinks(Long fileId) throws BimfaceException {
        return dataClient.getSingleModelLinks(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 单模型查询drawingSheets信息, v2
     *
     * @param fileId 文件ID
     * @param elementId 构件ID
     * @return 图纸列表
     * @throws BimfaceException
     */
    public List<DrawingSheet> getSingleModelDrawingSheets(Long fileId, String elementId) throws BimfaceException {
        return dataClient.getSingleModelDrawingSheets(fileId, elementId, accessTokenService.getAccessToken());
    }

    /**
     * 获取模型数据包meta信息, v2
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public Object getSingleModelModelInfo(Long fileId) throws BimfaceException {
        return dataClient.getSingleModelModelInfo(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 获取构件的子构件 id，v2
     *
     * @param fileId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public List<ElementIdWithName> getSingleModelChildElementIds(Long fileId, String elementId) throws BimfaceException {
        return dataClient.getSingleModelChildElementIds(fileId, elementId, accessTokenService.getAccessToken());
    }

    /**
     * 修改（包含添加和更新）构件基本属性组以外的属性, V2
     *
     * @param fileId
     * @param elementId
     * @param propertyGroups 更新的属性
     * @return
     * @throws BimfaceException
     */
    public String updateSingleModelElementProperties(Long fileId, String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataClient.updateSingleModelElementProperties(fileId, elementId, propertyGroups, accessTokenService.getAccessToken());
    }

    /**
     * 删除构件基本属性组以外的属性, V2
     *
     * @param fileId
     * @param elementId
     * @param propertyGroups 删除的属性
     * @return
     * @throws BimfaceException
     */
    public String deleteSingleModelElementProperties(Long fileId, String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataClient.deleteSingleModelElementProperties(fileId, elementId, propertyGroups, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型构件ID组, v2
     *
     * @param integrateId
     * @param queryElementIdsRequest
     * @return
     * @throws BimfaceException
     */
    public ElementsWithBoundingBox getIntegrateModelElementIds(Long integrateId, QueryElementIdsRequest queryElementIdsRequest) throws BimfaceException {
        return dataClient.getIntegrateModelElementIds(integrateId, queryElementIdsRequest, accessTokenService.getAccessToken());
    }

    /**
     * 修改集成模型指定构件的属性 V2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @param propertyGroups
     * @return
     * @throws BimfaceException
     */
    public String updateIntegrateModelElementProperties(Long integrateId, String fileIdHash,
                                                        String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataClient.updateIntegrateModelElementProperties(integrateId, fileIdHash, elementId, propertyGroups, accessTokenService.getAccessToken());
    }

    /**
     * 删除集成模型指定构件的属性 V2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @param propertyGroups
     * @return
     * @throws BimfaceException
     */
    public String deleteIntegrateModelElementProperties(Long integrateId, String fileIdHash,
                                                        String elementId, List<PropertyGroup> propertyGroups) throws BimfaceException {
        return dataClient.deleteIntegrateModelElementProperties(integrateId, fileIdHash, elementId, propertyGroups, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型构件, v2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public Property getIntegrateModelElement(Long integrateId, String fileIdHash, String elementId, Boolean includeOverrides) throws BimfaceException {
        return dataClient.getIntegrateModelElement(integrateId, fileIdHash, elementId, includeOverrides, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型构件, v2
     *
     * @param integrateId
     * @param elementId
     * @return
     * @throws BimfaceException
     */
    public Property getIntegrateModelElement(Long integrateId, String elementId) throws BimfaceException {
        return dataClient.getIntegrateModelElement(integrateId, elementId, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型构件材质, v2
     *
     * @param integrateId
     * @param fileIdHash
     * @param elementId
     * @return {@link Property}
     * @throws BimfaceException
     */
    public List<MaterialInfo> getIntegrateModelElementMaterials(Long integrateId, String fileIdHash, String elementId) throws BimfaceException {
        return dataClient.getIntegrateModelElementMaterials(integrateId, fileIdHash, elementId, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型分类树，v2
     *
     * @param integrateId
     * @param treeType
     * @param desiredHierarchy
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public Tree getIntegrateModelTree(Long integrateId, String treeType, List<String> desiredHierarchy,
                                      IntegrationTreeOptionalRequestBody requestBody) throws BimfaceException {
        return dataClient.getIntegrateModelTree(integrateId, treeType, desiredHierarchy, requestBody, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型三维视点/二维视点, v2
     *
     * @param integrateId
     * @param viewType
     * @return
     * @throws BimfaceException
     */
    public List<FileViews> getIntegrateModelFileViews(Long integrateId, String viewType) throws BimfaceException {
        return dataClient.getIntegrateModelFileViews(integrateId, viewType, accessTokenService.getAccessToken());
    }

    /**
     * 集成模型查询楼层对应房间列表, v2
     *
     * @param integrateId
     * @param floorId
     * @return
     * @throws BimfaceException
     */
    public List<Room> getIntegrateModelRooms(Long integrateId, String floorId, String elementId, ToleranceType roomToleranceZ,
                                             ToleranceType roomToleranceXY) throws BimfaceException {
        AssertUtils.assertTrue(StringUtils.isNotBlank(floorId) || StringUtils.isNotBlank(elementId),
                "floorId or elementId can't be both empty or blank");
        return dataClient.getIntegrateModelRooms(integrateId, floorId, elementId, roomToleranceZ,
                roomToleranceXY, accessTokenService.getAccessToken());
    }

    /**
     * 集成模型查询房间属性，v2
     *
     * @param integrateId
     * @param roomId
     * @return
     * @throws BimfaceException
     */
    public Room getIntegrateModelRoom(Long integrateId, String roomId) throws BimfaceException {
        return dataClient.getIntegrateModelRoom(integrateId, roomId, accessTokenService.getAccessToken());
    }

    /**
     * 集成模型查询楼层对应Area列表, v2
     *
     * @param integrateId
     * @param floorId
     * @return
     * @throws BimfaceException
     */
    public List<Area> getIntegrateModelAreas(Long integrateId, String floorId) throws BimfaceException {
        return dataClient.getIntegrateModelAreas(integrateId, floorId, accessTokenService.getAccessToken());
    }

    /**
     * 集成模型查询Area, v2
     *
     * @param integrateId
     * @param areaId
     * @return
     * @throws BimfaceException
     */
    public Area getIntegrateModelArea(Long integrateId, String areaId) throws BimfaceException {
        return dataClient.getIntegrateModelArea(integrateId, areaId, accessTokenService.getAccessToken());
    }

    /**
     * 查询指定的集成模型内参与集成的子文件信息, v2
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<IntegrateFileData> getIntegrateFiles(Long integrateId) throws BimfaceException {
        return dataClient.getIntegrateFiles(integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 根据集成ID,文件ID获取viewToken，v2
     *
     * @param integrateId
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public String getIntegrateModelViewToken(Long integrateId, String fileId) throws BimfaceException {
        return dataClient.getIntegrateModelViewToken(integrateId, fileId, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型中所有的流水段分组，v2
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<SegmentGroupDto> getIntegrateModelSegmentGroups(Long integrateId) throws BimfaceException {
        return dataClient.getIntegrateModelSegmentGroups(integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 获取集成模型的流水段树, v2
     *
     * @param integrateId
     * @return
     * @throws BimfaceException
     */
    public List<SegmentGroupDto> getIntegrateModelSegmentTree(Long integrateId) throws BimfaceException {
        return dataClient.getIntegrateModelSegmentTree(integrateId, accessTokenService.getAccessToken());
    }

    /**
     * 获取流水段分组中的所有流水段, v2
     *
     * @param integrateId
     * @param segmentGroupId
     * @return
     * @throws BimfaceException
     */
    public List<SegmentDto> getIntegrateModelSegments(Long integrateId, String segmentGroupId) throws BimfaceException {
        return dataClient.getIntegrateModelSegments(integrateId, segmentGroupId, accessTokenService.getAccessToken());
    }

    /**
     * 获取流水段对应的所有构件id
     *
     * @param integrateId 集成ID
     * @param segmentId   流水段ID；支持批量查询，多个segment id用逗号隔开
     * @return
     * @throws BimfaceException
     */
    public List<String> getIntegrateModelSegmentElementIds(Long integrateId, String segmentId) throws BimfaceException {
        return dataClient.getIntegrateModelSegmentElementIds(integrateId, segmentId, accessTokenService.getAccessToken());
    }

    /**
     * 集成模型获取构件属性
     *
     * @param integrateId
     * @param fileIdHashWithElementIds
     * @return
     * @throws BimfaceException
     */
    public Property getIntegrateModelCommonElementProperties(Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds, Boolean includeOverrides) throws BimfaceException {
        return dataClient.getIntegrateModelCommonElementProperties(integrateId, fileIdHashWithElementIds, includeOverrides, accessTokenService.getAccessToken());
    }

    /**
     * 新增双向业务挂接,可以在request body中指定新增项，也可以指定从其他集成拷贝
     *
     * @param integrateId
     * @param fromIntegrationId
     * @param fromBusinessType
     * @param fromBusinessFlag
     * @param request
     * @throws BimfaceException
     */
    public void addAssociations(String integrateId, String fromIntegrationId, String fromBusinessType, String fromBusinessFlag,
                                BusinessAssociationRequest request) throws BimfaceException {
        dataClient.addAssociations(integrateId, fromIntegrationId, fromBusinessType, fromBusinessFlag, request, accessTokenService.getAccessToken());
    }

    /**
     * 根据构件ID查询对应业务挂接
     *
     * @param integrateId
     * @param elementId
     * @param businessType
     * @param businessFlag
     * @return
     * @throws BimfaceException
     */
    public List<ElementBusinessAssociation> getAssociationsByElementId(String integrateId, String elementId, String businessType,
                                                                       String businessFlag) throws BimfaceException {
        return dataClient.getAssociationsByElementId(integrateId, elementId, businessType, businessFlag, accessTokenService.getAccessToken());
    }

    /**
     * 根据业务ID查询对应构件
     *
     * @param integrateId
     * @param businessType
     * @param businessId
     * @return
     * @throws BimfaceException
     */
    public List<BusinessElementAssociation> getAssociationsByBusinessId(String integrateId, String businessType, String businessId) throws BimfaceException {
        return dataClient.getAssociationsByBusinessId(integrateId, businessType, businessId, accessTokenService.getAccessToken());
    }

    /**
     * 删除构件关联的业务挂接
     *
     * @param integrateId
     * @param elementId
     * @param businessType
     * @param businessFlag
     * @param businessIds
     * @throws BimfaceException
     */
    public void deleteAssociationsByElementId(String integrateId, String elementId, String businessType,
                                              String businessFlag, List<String> businessIds) throws BimfaceException {
        dataClient.deleteAssociationsByElementId(integrateId, elementId, businessType, businessFlag, businessIds, accessTokenService.getAccessToken());
    }

    /**
     * 删除业务ID关联的业务挂接
     *
     * @param integrateId
     * @param businessType
     * @param businessId
     * @throws BimfaceException
     */
    public void deleteAssociationsByBusinessId(String integrateId, String businessType, String businessId) throws BimfaceException {
        dataClient.deleteAssociationsByBusinessId(integrateId, businessType, businessId, accessTokenService.getAccessToken());
    }

    /**
     * 删除业务挂接
     *
     * @param integrateId
     * @param businessType
     * @param businessFlag
     * @param elementIds
     * @throws BimfaceException
     */
    public void deleteAssociationsByElements(String integrateId, String businessType, String businessFlag,
                                             List<String> elementIds) throws BimfaceException {
        dataClient.deleteAssociationsByElements(integrateId, businessType, businessFlag, elementIds, accessTokenService.getAccessToken());
    }

    /**
     * 删除业务挂接
     *
     * @param integrateId
     * @param businessType
     * @param businessFlag
     * @param businessIds
     * @throws BimfaceException
     */
    public void deleteAssociationsByBizIds(String integrateId, String businessType, String businessFlag,
                                           List<String> businessIds) throws BimfaceException {
        dataClient.deleteAssociationsByBizIds(integrateId, businessType, businessFlag, businessIds, accessTokenService.getAccessToken());
    }

    /**
     * 获取模型构件对比差异, v2
     *
     * @param comparisonId
     * @param previousFileId
     * @param previousElementId
     * @param followingFileId
     * @param followingElementId
     * @return
     * @throws BimfaceException
     */
    public ModelCompareChange getModelCompareElementChange(Long comparisonId, Long previousFileId, String previousElementId, Long followingFileId, String followingElementId) throws BimfaceException {
        return dataClient.getModelCompareElementChange(comparisonId, previousFileId, previousElementId, followingFileId, followingElementId, accessTokenService.getAccessToken());
    }

    /**
     * 获取模型对比构件分类树, v2
     *
     * @param comparisonId
     * @return
     * @throws BimfaceException
     */
    public Tree getModelCompareTree(Long comparisonId) throws BimfaceException {
        return dataClient.getModelCompareTree(comparisonId, accessTokenService.getAccessToken());
    }

    /**
     * 分页获取模型对比结果, v2
     *
     * @param comparisonId
     * @param family
     * @param elementName
     * @param page
     * @param pageSize
     * @return
     * @throws BimfaceException
     */
    public Pagination<ModelCompareDiff> getModelCompareResult(Long comparisonId, String family, String elementName, Integer page,
                                                              Integer pageSize) throws BimfaceException {
        return dataClient.getModelCompareResult(comparisonId, family, elementName, page, pageSize, accessTokenService.getAccessToken());
    }

    /**
     * 获取材质配置方案, v2
     *
     * @param id
     * @return
     * @throws BimfaceException
     */
    public MaterialOverrideSetVO getMaterialOverrideSet(Long id) throws BimfaceException {
        return dataClient.getMaterialOverrideSet(id, accessTokenService.getAccessToken());
    }

    /**
     * 获取rfa文件族属性key列表, v2
     *
     * @param rfaFileId
     * @return
     * @throws BimfaceException
     */
    public List<String> getRfaFamilyPropertyNames(Long rfaFileId) throws BimfaceException {
        return dataClient.getRfaFamilyPropertyNames(rfaFileId, accessTokenService.getAccessToken());
    }

    /**
     * rfa文件族类型列表, v2
     *
     * @param rfaFileId
     * @return
     * @throws BimfaceException
     */
    public List<RfaFamilyType> getRfaFamilyTypes(Long rfaFileId) throws BimfaceException {
        return dataClient.getRfaFamilyTypes(rfaFileId, accessTokenService.getAccessToken());
    }

    /**
     * 获取rfa文件族类型属性列表, v2
     *
     * @param rfaFileId
     * @param familyTypeGuid
     * @return
     * @throws BimfaceException
     */
    public RfaFamilyTypeProperty getRfaFamilyTypeProperty(Long rfaFileId, String familyTypeGuid) throws BimfaceException {
        return dataClient.getRfaFamilyTypeProperty(rfaFileId, familyTypeGuid, accessTokenService.getAccessToken());
    }

    /**
     * 查询elementId
     *
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public List<SearchElementIdsResp> getElements(String requestBody) throws BimfaceException {

        return dataClient.getElements(requestBody, accessTokenService.getAccessToken());
    }

    /**
     * 查询roomId
     *
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public List<SearchRoomIdsResp> getRooms(String requestBody) throws BimfaceException {
        return dataClient.getRooms(requestBody, accessTokenService.getAccessToken());
    }

    /**
     * 查询areaId
     *
     * @param requestBody
     * @return
     * @throws BimfaceException
     */
    public List<SearchAreaIdsResp> getAreas(String requestBody) throws BimfaceException {
        return dataClient.getAreas(requestBody, accessTokenService.getAccessToken());
    }

    /**
     * 查询一些文件中某个property的所有可能值
     *
     * @param targetIds
     * @param targetType
     * @param properties
     * @return
     * @throws BimfaceException
     */
    public List<PropertyValuesResp> getPropertyValues(List<String> targetIds, String targetType,
                                                      List<String> properties) throws BimfaceException {
        return dataClient.getPropertyValues(targetIds, targetType, properties, accessTokenService.getAccessToken());
    }

    /**
     * 生成分页查询的ContextId
     *
     * @return
     * @throws BimfaceException
     */
    public String getPaginationContextId() throws BimfaceException {
        return dataClient.getPaginationContextId(accessTokenService.getAccessToken());
    }

    /**
     * 获取缩略图url
     *
     * @param fileId
     * @param size
     * @return
     * @throws BimfaceException
     */
    public String getFileThumbnailUrl(Long fileId, Integer size) throws BimfaceException {
        return dataClient.getFileThumbnailUrl(fileId, size, accessTokenService.getAccessToken());
    }

    /**
     * 获取DWG文件转换生成的pdf文件的url
     *
     * @param dwgFileId
     * @return
     * @throws BimfaceException
     */
    public String getDwgPdfUrl(Long dwgFileId) throws BimfaceException {
        return dataClient.getDwgPdfUrl(dwgFileId, accessTokenService.getAccessToken());
    }

    /**
     * 获取DWG文件转换生成的预览图片的url
     *
     * @param dwgFileId
     * @param layoutName
     * @return
     * @throws BimfaceException
     */
    public String getDwgPreviewImageUrl(Long dwgFileId, String layoutName) throws BimfaceException {
        return dataClient.getDwgPreviewImageUrl(dwgFileId, layoutName, accessTokenService.getAccessToken());
    }
}