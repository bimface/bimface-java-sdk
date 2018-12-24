package com.bimface.sdk.interfaces;

import com.bimface.data.bean.*;
import com.glodon.paas.foundation.restclient.RESTResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface DataInteface {
    @GET("element/property")
    Call<RESTResponse<Property>> getSingleModelElementProperty(@Query("fileId") Long fileId, @Query("elementId") String elementId,
                                                               @Header("Authorization") String accessToken);

    @GET("integration/element/property")
    Call<RESTResponse<Property>> getIntegrateModelProperty(@Query("integrateId") Long integrateId, @Query("fileId") Long fileId,
                                                           @Query("elementId") String elementId, @Header("Authorization") String accessToken);

    @GET("databag/downloadUrl")
    Call<RESTResponse<String>> getDatabagDownloadUrl(@Query("fileId") Long fileId, @Query("integrateId") Long integrateId,
                                                     @Query("compareId") Long compareId, @Query("type") String type,
                                                     @Query("databagVersion") String databagVersion, @Header("Authorization") String accessToken);

    @GET("databag/length")
    Call<RESTResponse<DatabagInfo>> getDataBagSize(@Query("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("element/id")
    Call<RESTResponse<List<String>>> getSingleModelElements(@Query("fileId") Long fileId, @Query("specialty") String specialty,
                                                            @Query("floor") String floor, @Query("categoryId") String categoryId,
                                                            @Query("family") String family, @Query("familyType") String familyType,
                                                            @Header("Authorization") String accessToken);

    @GET("integration/element")
    Call<RESTResponse<ElementsWithBoundingBox>> getIntegrateModelElements(@Query("integrateId") Long integrateId, @Query("specialty") String specialty,
                                                                          @Query("floor") String floor, @Query("categoryId") String categoryId,
                                                                          @Query("family") String family, @Query("familyType") String familyType,
                                                                          @Query("systemType") String systemType, @Header("Authorization") String accessToken);

    @GET("integration/tree")
    Call<RESTResponse<Object>> getIntegrateTree(@Query("integrateId") Long integrateId, @Query("treeType") Integer treeType,
                                                @Header("Authorization") String accessToken);

    @GET("compare")
    Call<RESTResponse<List<ModelCompareTree.SpecialtyNode>>> getModelCompareResult(@Query("compareId") Long compareId, @Header("Authorization") String accessToken);

    @GET("compare/element")
    Call<RESTResponse<ModelCompareChange>> getModelCompareChange(@Query("compareId") Long compareId, @Query("previousFileId") Long previousFileId,
                                                                 @Query("previousElementId") String previousElementId, @Query("followingFileId") Long followingFileId,
                                                                 @Query("followingElementId") String followingElementId, @Header("Authorization") String accessToken);

    @GET("hierarchy")
    Call<RESTResponse<Object>> getSingleModelTree(@Query("fileId") Long fileId, @Query("v") String version, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elementIds")
    Call<RESTResponse<List<String>>> getSingleModelElementIds(@Path("fileId") Long fileId, @Query("specialty") String specialty, @Query("floor") String floor,
                                                              @Query("categoryId") String categoryId, @Query("family") String family, @Query("familyType") String familyType,
                                                              @Header("Authorization") String accessToken);

    @GET("v2/files/{fileIds}/fileIdfloorsMappings")
    Call<RESTResponse<Map<String, Object>>> getSingleModelFileIdFloorsMapping(@Query("fileIds") List<String> fileIds, @Query("includeArea") Boolean includeArea,
                                                                              @Query("includeRoom") Boolean includeRoom, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/floors")
    Call<RESTResponse<List<Floor>>> getSingleModelFloors(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elements/{elementId}")
    Call<RESTResponse<Property>> getSingleModelElement(@Path("fileId") Long fileId, @Path("elementId") String elementId,
                                                       @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elements")
    Call<RESTResponse<List<Property>>> getSingleModelElements(@Path("fileId") Long fileId, @Query("elementIds") List<String> elementIds,
                                                              @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/commonElementProperties")
    Call<RESTResponse<Property>> getSingleModelElementProperty(@Path("fileId") Long fileId, @Query("elementIds") List<String> elementIds,
                                                               @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elements/{elementId}/materials")
    Call<RESTResponse<List<MaterialInfo>>> getSingleModelElementMaterials(@Path("fileId") Long fileId, @Query("elementId") String elementId,
                                                                          @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elements/{elementId}/bars")
    Call<RESTResponse<List<Bar>>> getSingleModelElementBars(@Path("fileId") Long fileId, @Path("elementId") String elementId,
                                                            @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elements/{elementId}/quantities")
    Call<RESTResponse<List<Quantity>>> getSingleModelElementQuantities(@Path("fileId") Long fileId, @Path("elementId") String elementId,
                                                                       @Query("type") String type, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/aggregatedElementQuantities")
    Call<RESTResponse<List<AggregatedQuantity>>> getSingleModelAggregatedElementQuantities(@Path("fileId") Long fileId, @Query("elementIds") List<String> elementIds,
                                                                                           @Query("type") String type, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/views")
    Call<RESTResponse<List<View>>> getSingleModelViews(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/rooms")
    Call<RESTResponse<List<Room>>> getSingleModelRooms(@Path("fileId") Long fileId, @Query("floorId") String floorId,
                                                       @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/rooms/{roomId}")
    Call<RESTResponse<Room>> getSingleModelRoom(@Path("fileId") Long fileId, @Path("roomId") String roomId,
                                                @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/areas")
    Call<RESTResponse<List<Area>>> getSingleModelAreas(@Path("fileId") Long fileId, @Query("floorId") String floorId,
                                                       @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/areas/{areaId}")
    Call<RESTResponse<Area>> getSingleModelArea(@Path("fileId") Long fileId, @Path("areaId") String areaId,
                                                @Header("Authorization") String accessToken);

    @POST("v2/files/{fileId}/tree")
    Call<RESTResponse<Object>> getSingleModelTreeV2(@Path("fileId") Long fileId, @Query("v") String version,
                                                    @Query("treeType") String treeType, @Body FileTreeRequestBody request,
                                                    @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/tree")
    Call<RESTResponse<Object>> getSingleModelTreeV2(@Path("fileId") Long fileId, @Query("v") String version,
                                                    @Query("treeType") String treeType, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/links")
    Call<RESTResponse<List<Link>>> getSingleModelLinks(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/drawingsheets")
    Call<RESTResponse<List<DrawingSheet>>> getSingleModelDrawingSheets(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/modelInfo")
    Call<ResponseBody> getSingleModelModelInfo(@Path("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("v2/files/{fileId}/elements/{elementId}/childElementIds")
    Call<RESTResponse<List<ElementIdWithName>>> getSingleModelChildElementIds(@Path("fileId") Long fileId, @Path("elementId") String elementId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/elementIds")
    Call<RESTResponse<ElementsWithBoundingBox>> getIntegrateModelElementIds(@Path("integrateId") Long integrateId, @Query("specialty") String specialty, @Query("roomId") String roomId,
                                                                            @Query("floor") String floor, @Query("categoryId") String categoryId, @Query("family") String family,
                                                                            @Query("familyType") String familyType, @Query("systemType") String systemType, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/floors")
    Call<RESTResponse<List<Floor>>> getIntegrateModelFloors(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/files/{fileId}/elements/{elementId}")
    Call<RESTResponse<Property>> getIntegrateModelElement(@Path("integrateId") Long integrateId, @Path("fileId") Long fileIdHash,
                                                          @Path("elementId") String elementId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/elements/{elementId}")
    Call<RESTResponse<Property>> getIntegrateModelElement(@Path("integrateId") Long integrateId, @Path("elementId") String elementId,
                                                          @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/elements")
    Call<RESTResponse<List<Property>>> getIntegrateModelElementProperties(@Path("integrateId") Long integrateId, @Body List<FileIdHashWithElementIds> fileIdHashWithElementIds,
                                                                          @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/elements")
    Call<RESTResponse<List<Property>>> getIntegrateModelElementProperties(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/files/{fileIdHash}/elements/{elementId}/materials")
    Call<RESTResponse<List<MaterialInfo>>> getIntegrateModelElementMaterials(@Path("integrateId") Long integrateId, @Path("fileIdHash") String fileIdHash,
                                                                             @Path("elementId") String elementId, @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/tree")
    Call<RESTResponse<Tree>> getIntegrateModelTree(@Path("integrateId") Long integrateId, @Query("treeType") String treeType,
                                                   @Query("desiredHierarchy") List<String> desiredHierarchy, @Body IntegrationTreeOptionalRequestBody requestBody,
                                                   @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/tree")
    Call<RESTResponse<Tree>> getIntegrateModelTree(@Path("integrateId") Long integrateId, @Query("treeType") String treeType,
                                                   @Query("desiredHierarchy") List<String> desiredHierarchy, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/fileViews")
    Call<RESTResponse<List<FileViews>>> getIntegrateModelFileViews(@Path("integrateId") Long integrateId, @Query("viewType") String viewType,
                                                                   @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/rooms")
    Call<RESTResponse<List<Room>>> getIntegrateModelRooms(@Path("integrateId") Long integrateId, @Query("floorId") String floorId,
                                                          @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/rooms/{roomId}")
    Call<RESTResponse<Room>> getIntegrateModelRoom(@Path("integrateId") Long integrateId, @Path("roomId") String roomId,
                                                   @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/areas")
    Call<RESTResponse<List<Area>>> getIntegrateModelAreas(@Path("integrateId") Long integrateId, @Query("floorId") String floorId,
                                                          @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/areas/{areaId}")
    Call<RESTResponse<Area>> getIntegrateModelArea(@Path("integrateId") Long integrateId, @Path("areaId") String areaId,
                                                   @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/files/{fileId}/elements/{elementId}/quantities")
    Call<RESTResponse<List<Quantity>>> getIntegrateModelElementQuantities(@Path("integrateId") Long integrateId, @Path("fileId") Long fileId,
                                                                          @Path("elementId") String elementId, @Query("type") String type,
                                                                          @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/elements/{elementId}/quantities")
    Call<RESTResponse<List<Quantity>>> getIntegrateModelElementQuantities(@Path("integrateId") Long integrateId, @Path("elementId") String elementId,
                                                                          @Query("type") String type, @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/aggregatedElementQuantities")
    Call<RESTResponse<List<AggregatedQuantity>>> getIntegrateModelAccumulativeQuantities(@Path("integrateId") Long integrateId, @Body List<FileIdHashWithElementIds> fileIdHashWithElementIds,
                                                                                         @Query("type") String type, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/aggregatedElementQuantities")
    Call<RESTResponse<List<AggregatedQuantity>>> getIntegrateModelAccumulativeQuantities(@Path("integrateId") Long integrateId, @Query("type") String type, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/files")
    Call<RESTResponse<List<IntegrateFile>>> getIntegrateFiles(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/files/{fileId}/elements/{elementId}/bars")
    Call<RESTResponse<List<Bar>>> getIntegrateModelElementBars(@Path("integrateId") Long integrateId, @Path("fileId") Long fileId,
                                                               @Path("elementId") String elementId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/elements/{elementId}/bars")
    Call<RESTResponse<List<Bar>>> getIntegrateModelElementBars(@Path("integrateId") Long integrateId, @Path("elementId") String elementId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/files/{fileId}/viewToken")
    Call<RESTResponse<String>> getIntegrateModelViewToken(@Path("integrateId") Long integrateId, @Path("fileId") String fileId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/segmentGroups")
    Call<RESTResponse<List<SegmentGroupDto>>> getIntegrateModelSegmentGroups(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/segmentTree")
    Call<RESTResponse<List<SegmentGroupDto>>> getIntegrateModelSegmentTree(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/segmentGroups/{segmentGroupId}/segments")
    Call<RESTResponse<List<SegmentDto>>> getIntegrateModelSegments(@Path("integrateId") Long integrateId, @Path("segmentGroupId") String segmentGroupId,
                                                                   @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/segments/{segmentId}/elementIds")
    Call<RESTResponse<List<String>>> getIntegrateModelSegmentElementIds(@Path("integrateId") Long integrateId, @Path("segmentId") String segmentId,
                                                                        @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/commonElementProperties")
    Call<RESTResponse<Property>> getIntegrateModelCommonElementProperties(@Path("integrateId") Long integrateId, @Body List<FileIdHashWithElementIds> fileIdHashWithElementIds,
                                                                          @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/commonElementProperties")
    Call<RESTResponse<Property>> getIntegrateModelCommonElementProperties(@Path("integrateId") Long integrateId, @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/associations")
    Call<RESTResponse<Void>> addAssociations(@Path("integrateId") String integrateId, @Query("fromIntegrationId") String fromIntegrationId,
                                             @Query("fromBusinessType") String fromBusinessType, @Query("fromBusinessFlag") String fromBusinessFlag,
                                             @Body BusinessAssociationRequest request, @Header("Authorization") String accessToken);

    @POST("v2/integrations/{integrateId}/associations")
    Call<RESTResponse<Void>> addAssociations(@Path("integrateId") String integrateId, @Query("fromIntegrationId") String fromIntegrationId,
                                             @Query("fromBusinessType") String fromBusinessType, @Query("fromBusinessFlag") String fromBusinessFlag,
                                             @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/elements/{elementId}/associations")
    Call<RESTResponse<List<ElementBusinessAssociation>>> getAssociationsByElementId(@Path("integrateId") String integrateId, @Path("elementId") String elementId,
                                                                                    @Query("businessType") String businessType, @Query("businessFlag") String businessFlag,
                                                                                    @Header("Authorization") String accessToken);

    @GET("v2/integrations/{integrateId}/businessTypes/{businessType}/businesses/{businessId}/associations")
    Call<RESTResponse<List<BusinessElementAssociation>>> getAssociationsByBusinessId(@Path("integrateId") String integrateId, @Path("businessType") String businessType, @Path("businessId") String businessId,
                                                                                     @Header("Authorization") String accessToken);

    @DELETE("v2/integrations/{integrateId}/elements/{elementId}/associations")
    Call<RESTResponse<Void>> deleteAssociationsByElementId(@Path("integrateId") String integrateId, @Path("elementId") String elementId,
                                                           @Query("businessType") String businessType, @Query("businessFlag") String businessFlag,
                                                           @Query("businessIds") List<String> businessIds, @Header("Authorization") String accessToken);

    @DELETE("v2/integrations/{integrateId}/businessTypes/{businessType}/businesses/{businessId}/associations")
    Call<RESTResponse<Void>> deleteAssociationsByBusinessId(@Path("integrateId") String integrateId, @Path("businessType") String businessType, @Path("businessId") String businessId,
                                                            @Header("Authorization") String accessToken);

    @DELETE("v2/integrations/{integrateId}/associations/element")
    Call<RESTResponse<Void>> deleteAssociationsByElements(@Path("integrateId") String integrateId, @Query("businessType") String businessType, @Query("businessFlag") String businessFlag,
                                                          @Query("elementIds") List<String> elementIds, @Header("Authorization") String accessToken);

    @DELETE("v2/integrations/{integrateId}/associations/biz")
    Call<RESTResponse<Void>> deleteAssociationsByBizIds(@Path("integrateId") String integrateId, @Query("businessType") String businessType, @Query("businessFlag") String businessFlag,
                                                        @Query("businessIds") List<String> businessIds, @Header("Authorization") String accessToken);

    @GET("v2/comparisons/{comparisonId}/elementChange")
    Call<RESTResponse<ModelCompareChange>> getModelCompareElementChange(@Path("comparisonId") Long comparisonId, @Query("previousFileId") Long previousFileId,
                                                                        @Query("previousElementId") String previousElementId, @Query("followingFileId") Long followingFileId,
                                                                        @Query("followingElementId") String followingElementId, @Header("Authorization") String accessToken);

    @GET("v2/comparisons/{comparisonId}/tree")
    Call<RESTResponse<Tree>> getModelCompareTree(@Path("comparisonId") Long comparisonId, @Header("Authorization") String accessToken);

    @GET("v2/comparisons/{comparisonId}/diff")
    Call<RESTResponse<Pagination<ModelCompareDiff>>> getModelCompareResult(@Path("comparisonId") Long comparisonId, @Query("family") String family,
                                                                           @Query("elementName") String elementName, @Query("page") Integer page,
                                                                           @Query("pageSize") Integer pageSize, @Header("Authorization") String accessToken);

    @GET("v2/materialoverridesets/{id}")
    Call<RESTResponse<MaterialOverrideSetVO>> getMaterialOverrideSet(@Path("id") Long id, @Header("Authorization") String accessToken);

    @GET("v2/rfaFiles/{rfaFileId}/propertyNames")
    Call<RESTResponse<List<String>>> getRfaFamilyPropertyNames(@Path("rfaFileId") Long rfaFileId, @Header("Authorization") String accessToken);

    @GET("v2/rfaFiles/{rfaFileId}/familyTypeMetas")
    Call<RESTResponse<List<RfaFamilyType>>> getRfaFamilyTypes(@Path("rfaFileId") Long rfaFileId, @Header("Authorization") String accessToken);

    @GET("v2/rfaFiles/{rfaFileId}/familyTypes/{familyTypeGuid}")
    Call<RESTResponse<RfaFamilyTypeProperty>> getRfaFamilyTypeProperty(@Path("rfaFileId") Long rfaFileId, @Path("familyTypeGuid") String familyTypeGuid,
                                                                       @Header("Authorization") String accessToken);

    @POST("v2/query/elementIds")
    Call<RESTResponse<List<SearchElementIdsResp>>> getElements(@Body String requestBody, @Header("Authorization") String accessToken);

    @POST("v2/query/roomIds")
    Call<RESTResponse<List<SearchRoomIdsResp>>> getRooms(@Body String requestBody, @Header("Authorization") String accessToken);

    @POST("v2/query/areaIds")
    Call<RESTResponse<List<SearchAreaIdsResp>>> getAreas(@Body String requestBody, @Header("Authorization") String accessToken);

    @GET("v2/query/propertyValues")
    Call<RESTResponse<List<PropertyValuesResp>>> getPropertyValues(@Query("targetIds") List<String> targetIds, @Query("targetType") String targetType,
                                                                   @Query("properties") List<String> properties, @Header("Authorization") String accessToken);

    @GET("v2/databag/thumbnail")
    Call<RESTResponse<String>> getFileThumbnailUrl(@Query("fileId") Long fileId, @Query("size") Integer size, @Header("Authorization") String accessToken);

    @GET("v2/databag/pdf")
    Call<RESTResponse<String>> getDwgPdfUrl(@Query("dwgFileId") Long dwgFileId, @Header("Authorization") String accessToken);

    @GET("v2/databag/previewImage")
    Call<RESTResponse<String>> getDwgPreviewImageUrl(@Query("dwgFileId") Long dwgFileId, @Query("layoutName") String layoutName, @Header("Authorization") String accessToken);

    @GET("v2/databag/rootUrl")
    Call<RESTResponse<String>> getFileDatabagRootUrl(@Query("fileId") Long fileId, @Header("Authorization") String accessToken);

    @GET("v2/databag/rootUrl")
    Call<RESTResponse<String>> getIntegrateDatabagRootUrl(@Query("integrateId") Long integrateId, @Header("Authorization") String accessToken);
}
