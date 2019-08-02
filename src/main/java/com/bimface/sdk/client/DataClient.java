package com.bimface.sdk.client;

import com.bimface.data.bean.*;
import com.bimface.data.enums.ToleranceType;
import com.bimface.exception.BimfaceException;
import com.bimface.http.BimfaceResponseChecker;
import com.bimface.sdk.bean.request.QueryElementIdsRequest;
import com.bimface.sdk.interfaces.DataInteface;
import com.bimface.sdk.utils.ConvertUtils;
import com.glodon.paas.foundation.restclient.RESTClientBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class DataClient extends AbstractClient {
    private static final Logger logger = LoggerFactory.getLogger(DataClient.class);

    private final static Gson GSON = new Gson();

    private DataInteface dataClient;

    private static DataClient instance;

    public static synchronized DataClient getDataClient(String dataBaseUrl) {
        if (instance == null)
            instance = new DataClient(dataBaseUrl);
        return instance;
    }

    private DataClient(String dataBaseUrl) {
        RESTClientBuilder builder = new RESTClientBuilder()
                .serviceBaseUrl(dataBaseUrl)
                .responseChecker(new BimfaceResponseChecker());
        if (logger.isDebugEnabled()) {
            builder.enableHttpLoggingInterceptor();
        }
        this.dataClient = builder.build(DataInteface.class);
    }

    @Deprecated
    public Property getSingleModelElementProperty(@NotNull Long fileId, @NotNull String elementId,
                                                  @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElementProperty(fileId, elementId, accessToken));
    }

    public Property getIntegrateModelProperty(@NotNull Long integrateId, @NotNull Long fileId,
                                              @NotNull String elementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelProperty(integrateId, fileId, elementId, accessToken));
    }

    public String getDatabagDownloadUrl(Long fileId, Long integrateId, Long compareId, String type,
                                        String databagVersion, @NotNull String accessToken) throws BimfaceException {
        if ((fileId != null && integrateId != null) ||
                (integrateId != null && compareId != null) ||
                (fileId != null && compareId != null) ||
                (fileId == null && integrateId == null && compareId == null)) {
            throw new IllegalArgumentException("one and only one argument can be not null in (fileId, integrateId, compareId)");
        }

        accessToken = validToken(accessToken);
        return executeCall(dataClient.getDatabagDownloadUrl(fileId, integrateId, compareId, type, databagVersion, accessToken));
    }

    public Long getDatabagSize(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        DatabagInfo databagInfo = executeCall(dataClient.getDataBagSize(fileId, accessToken));
        if (databagInfo != null) {
            return databagInfo.getLength();
        }
        throw new BimfaceException("find databagInfo error");
    }

    public List<String> getSingleModelElements(@NotNull Long fileId, String specialty, String floor, String categoryId,
                                               String family, String familyType, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElements(fileId, specialty, floor, categoryId, family, familyType, accessToken));
    }

    public ElementsWithBoundingBox getIntegrateModelElements(@NotNull Long integrateId, String specialty, String floor, String categoryId,
                                                             String family, String familyType, String systemType, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelElements(integrateId, specialty, floor, categoryId, family, familyType, systemType, accessToken));
    }

    public Object getIntegrateTree(@NotNull Long integrateId, @NotNull Integer treeType, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateTree(integrateId, treeType, accessToken));
    }

    public List<ModelCompareTree.SpecialtyNode> getModelCompareResult(@NotNull Long compareId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getModelCompareResult(compareId, accessToken));
    }

    public ModelCompareChange getModelCompareChange(@NotNull Long compareId, @NotNull Long previousFileId,
                                                    @NotNull String previousElementId, @NotNull Long followingFileId,
                                                    @NotNull String followingElementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getModelCompareChange(compareId, previousFileId, previousElementId, followingFileId, followingElementId, accessToken));
    }

    public Object getSingleModelTree(@NotNull Long fileId, String version, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelTree(fileId, version, accessToken));
    }

    public List<String> getSingleModelElementIds(@NotNull Long fileId, @NotNull QueryElementIdsRequest queryElementIdsRequest, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElementIds(fileId,
                ConvertUtils.convertToParamsMap(queryElementIdsRequest), accessToken));
    }

    public List<Map<String, Object>> getSingleModelFileIdFloorsMapping(@NotNull List<String> fileIds, Boolean includeArea,
                                                                       Boolean includeRoom, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        String fileIdsList = fileIds.stream().reduce((i,j)->i+","+j).orElse(null);
        return executeCall(dataClient.getSingleModelFileIdFloorsMapping(fileIdsList, includeArea, includeRoom, accessToken));
    }

    public List<Floor> getSingleModelFloors(@NotNull Long fileId, Boolean includeArea, Boolean includeRoom, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelFloors(fileId, includeArea, includeRoom, accessToken));
    }

    public Property getSingleModelElement(@NotNull Long fileId, @NotNull String elementId, @NotNull Boolean includeOverrides, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElement(fileId, elementId, includeOverrides, accessToken));
    }

    public List<Property> getSingleModelElements(@NotNull Long fileId, Boolean includeOverrides, ElementPropertyFilterRequest elementPropertyFilterRequest, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElements(fileId, includeOverrides, elementPropertyFilterRequest, accessToken));
    }

    public Property getSingleModelElementProperty(@NotNull Long fileId, @NotNull List<String> elementIds, @NotNull Boolean includeOverrides, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElementProperty(fileId, elementIds, includeOverrides, accessToken));
    }

    public List<MaterialInfo> getSingleModelElementMaterials(@NotNull Long fileId, @NotNull String elementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelElementMaterials(fileId, elementId, accessToken));
    }

    public List<View> getSingleModelViews(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelViews(fileId, accessToken));
    }

    public List<Room> getSingleModelRooms(@NotNull Long fileId, String floorId, String elementId,
                                          ToleranceType roomToleranceZ, ToleranceType roomToleranceXY,
                                          @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelRooms(fileId, floorId, elementId, roomToleranceZ, roomToleranceXY,
                accessToken));
    }

    public Room getSingleModelRoom(@NotNull Long fileId, @NotNull String roomId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelRoom(fileId, roomId, accessToken));
    }

    public List<Area> getSingleModelAreas(@NotNull Long fileId, @NotNull String floorId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelAreas(fileId, floorId, accessToken));
    }

    public Area getSingleModelArea(@NotNull Long fileId, @NotNull String areaId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelArea(fileId, areaId, accessToken));
    }

    public List<Tree.TreeNode> getSingleModelTreeV2(@NotNull Long fileId, FileTreeRequestBody requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        Object response = requestBody != null ? executeCall(dataClient.getSingleModelTreeV2(fileId, "2.0", null, requestBody, accessToken))
                : executeCall(dataClient.getSingleModelTreeV2(fileId, "2.0", null, accessToken));

        return GSON.fromJson(GSON.toJson(response), new TypeToken<List<Tree.TreeNode>>() {
        }.getType());
    }

    public Tree getSingleModelCustomizedTree(@NotNull Long fileId, FileTreeRequestBody requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        Object response = requestBody != null ? executeCall(dataClient.getSingleModelTreeV2(fileId, null, "customized", requestBody, accessToken))
                : executeCall(dataClient.getSingleModelTreeV2(fileId, null, "customized", accessToken));

        return GSON.fromJson(GSON.toJson(response), new TypeToken<Tree>() {
        }.getType());
    }

    public List<Link> getSingleModelLinks(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelLinks(fileId, accessToken));
    }

    public List<DrawingSheet> getSingleModelDrawingSheets(@NotNull Long fileId, String elementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelDrawingSheets(fileId, elementId, accessToken));
    }

    public Object getSingleModelModelInfo(@NotNull Long fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return execute(dataClient.getSingleModelModelInfo(fileId, accessToken));
    }

    public List<ElementIdWithName> getSingleModelChildElementIds(@NotNull Long fileId, @NotNull String elementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getSingleModelChildElementIds(fileId, elementId, accessToken));
    }

    public String updateSingleModelElementProperties(@NotNull Long fileId, @NotNull String elementId, List<PropertyGroup> propertyGroups, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.updateSingleModelElementProperties(fileId, elementId, propertyGroups, accessToken));
    }

    public String deleteSingleModelElementProperties(@NotNull Long fileId, @NotNull String elementId, List<PropertyGroup> propertyGroups, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.deleteSingleModelElementProperties(fileId, elementId, propertyGroups, accessToken));
    }

    public ElementsWithBoundingBox getIntegrateModelElementIds(@NotNull Long integrateId,
                                                               @NotNull QueryElementIdsRequest queryElementIdsRequest,
                                                               @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelElementIds(integrateId,
                ConvertUtils.convertToParamsMap(queryElementIdsRequest), accessToken));
    }

    public String updateIntegrateModelElementProperties(@NotNull Long integrateId, @NotNull String fileIdHash,
                                                        @NotNull String elementId, List<PropertyGroup> propertyGroups,
                                                        @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.updateIntegrateModelElementProperties(integrateId, fileIdHash, elementId, propertyGroups, accessToken));
    }

    public String deleteIntegrateModelElementProperties(@NotNull Long integrateId, @NotNull String fileIdHash,
                                                        @NotNull String elementId, List<PropertyGroup> propertyGroups,
                                                        @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.deleteIntegrateModelElementProperties(integrateId, fileIdHash, elementId, propertyGroups, accessToken));
    }

    public List<Floor> getIntegrateModelFloors(@NotNull Long integrateId, Boolean includeArea, Boolean includeRoom, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelFloors(integrateId, includeArea, includeRoom, accessToken));
    }

    public Property getIntegrateModelElement(@NotNull Long integrateId, @NotNull String fileIdHash,
                                             @NotNull String elementId, @NotNull Boolean includeOverrides, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelElement(integrateId, fileIdHash, elementId, includeOverrides, accessToken));
    }

    public Property getIntegrateModelElement(@NotNull Long integrateId, @NotNull String elementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelElement(integrateId, elementId, accessToken));
    }

    public List<MaterialInfo> getIntegrateModelElementMaterials(@NotNull Long integrateId, @NotNull String fileIdHash,
                                                                @NotNull String elementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelElementMaterials(integrateId, fileIdHash, elementId, accessToken));
    }

    public Tree getIntegrateModelTree(@NotNull Long integrateId, String treeType, List<String> desiredHierarchy,
                                      IntegrationTreeOptionalRequestBody requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return requestBody != null ? executeCall(dataClient.getIntegrateModelTree(integrateId, treeType, desiredHierarchy, requestBody, accessToken))
                : executeCall(dataClient.getIntegrateModelTree(integrateId, treeType, desiredHierarchy, accessToken));
    }

    public List<FileViews> getIntegrateModelFileViews(@NotNull Long integrateId, String viewType, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelFileViews(integrateId, viewType, accessToken));
    }

    public List<Room> getIntegrateModelRooms(@NotNull Long integrateId, String floorId, String elementId,
                                             ToleranceType roomToleranceZ, ToleranceType roomToleranceXY,
                                             @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelRooms(integrateId, floorId, elementId, roomToleranceZ,
                roomToleranceXY, accessToken));
    }

    public Room getIntegrateModelRoom(@NotNull Long integrateId, @NotNull String roomId,
                                      @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelRoom(integrateId, roomId, accessToken));
    }

    public List<Area> getIntegrateModelAreas(@NotNull Long integrateId, @NotNull String floorId,
                                             @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelAreas(integrateId, floorId, accessToken));
    }

    public Area getIntegrateModelArea(@NotNull Long integrateId, @NotNull String areaId,
                                      @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelArea(integrateId, areaId, accessToken));
    }

    public List<IntegrateFileData> getIntegrateFiles(@NotNull Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateFiles(integrateId, accessToken));
    }

    public String getIntegrateModelViewToken(@NotNull Long integrateId, @NotNull String fileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelViewToken(integrateId, fileId, accessToken));
    }

    public List<SegmentGroupDto> getIntegrateModelSegmentGroups(@NotNull Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelSegmentGroups(integrateId, accessToken));
    }

    public List<SegmentGroupDto> getIntegrateModelSegmentTree(@NotNull Long integrateId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelSegmentTree(integrateId, accessToken));
    }

    public List<SegmentDto> getIntegrateModelSegments(@NotNull Long integrateId, @NotNull String segmentGroupId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelSegments(integrateId, segmentGroupId, accessToken));
    }

    public List<String> getIntegrateModelSegmentElementIds(@NotNull Long integrateId, @NotNull String segmentId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateModelSegmentElementIds(integrateId, segmentId, accessToken));
    }

    public Property getIntegrateModelCommonElementProperties(@NotNull Long integrateId, List<FileIdHashWithElementIds> fileIdHashWithElementIds,
                                                             @NotNull Boolean includeOverrides, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return fileIdHashWithElementIds != null ? executeCall(dataClient.getIntegrateModelCommonElementProperties(integrateId, fileIdHashWithElementIds, includeOverrides, accessToken)) :
                executeCall(dataClient.getIntegrateModelCommonElementProperties(integrateId, accessToken));
    }

    public void addAssociations(@NotNull String integrateId, String fromIntegrationId, String fromBusinessType, String fromBusinessFlag,
                                BusinessAssociationRequest request, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        if (request != null)
            executeCall(dataClient.addAssociations(integrateId, fromIntegrationId, fromBusinessType, fromBusinessFlag, request, accessToken));
        else
            executeCall(dataClient.addAssociations(integrateId, fromIntegrationId, fromBusinessType, fromBusinessFlag, accessToken));
    }

    public List<ElementBusinessAssociation> getAssociationsByElementId(@NotNull String integrateId, @NotNull String elementId, String businessType,
                                                                       String businessFlag, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getAssociationsByElementId(integrateId, elementId, businessType, businessFlag, accessToken));
    }

    public List<BusinessElementAssociation> getAssociationsByBusinessId(@NotNull String integrateId, @NotNull String businessType, @NotNull String businessId,
                                                                        @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getAssociationsByBusinessId(integrateId, businessType, businessId, accessToken));
    }

    public void deleteAssociationsByElementId(@NotNull String integrateId, @NotNull String elementId, String businessType,
                                              String businessFlag, List<String> businessIds, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(dataClient.deleteAssociationsByElementId(integrateId, elementId, businessType, businessFlag, businessIds, accessToken));
    }

    public void deleteAssociationsByBusinessId(@NotNull String integrateId, @NotNull String businessType, @NotNull String businessId,
                                               @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(dataClient.deleteAssociationsByBusinessId(integrateId, businessType, businessId, accessToken));
    }

    public void deleteAssociationsByElements(@NotNull String integrateId, String businessType, String businessFlag,
                                             List<String> elementIds, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(dataClient.deleteAssociationsByElements(integrateId, businessType, businessFlag, elementIds, accessToken));
    }

    public void deleteAssociationsByBizIds(@NotNull String integrateId, String businessType, String businessFlag,
                                           List<String> businessIds, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        executeCall(dataClient.deleteAssociationsByBizIds(integrateId, businessType, businessFlag, businessIds, accessToken));
    }

    public ModelCompareChange getModelCompareElementChange(@NotNull Long comparisonId, @NotNull Long previousFileId, @NotNull String previousElementId,
                                                           @NotNull Long followingFileId, @NotNull String followingElementId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getModelCompareElementChange(comparisonId, previousFileId, previousElementId, followingFileId, followingElementId, accessToken));
    }

    public Tree getModelCompareTree(@NotNull Long comparisonId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getModelCompareTree(comparisonId, accessToken));
    }

    public Pagination<ModelCompareDiff> getModelCompareResult(@NotNull Long comparisonId, String family, String elementName, Integer page,
                                                              Integer pageSize, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getModelCompareResult(comparisonId, family, elementName, page, pageSize, accessToken));
    }

    public MaterialOverrideSetVO getMaterialOverrideSet(@NotNull Long id, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getMaterialOverrideSet(id, accessToken));
    }

    public List<String> getRfaFamilyPropertyNames(@NotNull Long rfaFileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getRfaFamilyPropertyNames(rfaFileId, accessToken));
    }

    public List<RfaFamilyType> getRfaFamilyTypes(@NotNull Long rfaFileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getRfaFamilyTypes(rfaFileId, accessToken));
    }

    public RfaFamilyTypeProperty getRfaFamilyTypeProperty(@NotNull Long rfaFileId, @NotNull String familyTypeGuid, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getRfaFamilyTypeProperty(rfaFileId, familyTypeGuid, accessToken));
    }

    public List<SearchElementIdsResp> getElements(@NotNull String requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getElements(requestBody, accessToken));
    }

    public List<SearchRoomIdsResp> getRooms(@NotNull String requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getRooms(requestBody, accessToken));
    }

    public List<SearchAreaIdsResp> getAreas(@NotNull String requestBody, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getAreas(requestBody, accessToken));
    }

    public List<PropertyValuesResp> getPropertyValues(@NotNull List<String> targetIds, String targetType,
                                                      @NotNull List<String> properties, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getPropertyValues(targetIds, targetType, properties, accessToken));
    }

    public String getPaginationContextId(@NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getPaginationContextId(accessToken));
    }

    public String getFileThumbnailUrl(@NotNull Long fileId, @NotNull Integer size, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getFileThumbnailUrl(fileId, size, accessToken));
    }

    public String getDwgPdfUrl(@NotNull Long dwgFileId, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getDwgPdfUrl(dwgFileId, accessToken));
    }

    public String getDwgPreviewImageUrl(@NotNull Long dwgFileId, @NotNull String layoutName, @NotNull String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getDwgPreviewImageUrl(dwgFileId, layoutName, accessToken));
    }

    public String getFileDatabagRootUrl(Long fileId, String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getFileDatabagRootUrl(fileId, accessToken));
    }

    public String getIntegrateDatabagRootUrl(Long integrateId, String accessToken) throws BimfaceException {
        accessToken = validToken(accessToken);
        return executeCall(dataClient.getIntegrateDatabagRootUrl(integrateId, accessToken));
    }
}
