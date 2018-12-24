package com.bimface.sdk.service;


import com.bimface.api.bean.request.modelCompare.ModelCompareRequest;
import com.bimface.api.bean.response.ModelCompareBean;
import com.bimface.data.bean.ModelCompareChange;
import com.bimface.data.bean.ModelCompareTree;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.bean.request.compare.CompareElementRequest;
import com.bimface.sdk.client.ApiClient;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;

/**
 * 模型对比
 * 
 * @author bimface, 2018-02-07.
 */
public class CompareService{
    private ApiClient apiClient;
    private DataClient dataClient;
    private AccessTokenService accessTokenService;
    public CompareService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.apiClient = ApiClient.getApiClient(endpoint.getApiHost());
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 发起模型对比
     * 
     * @param request 模型对比请求
     * @return {@link ModelCompareBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareBean compare(ModelCompareRequest request) throws BimfaceException {
        return apiClient.invokeModelCompare(request, accessTokenService.getAccessToken());
    }

    /**
     * 查看模型对比状态
     *
     * @param compareId 模型对比Id
     * @return {@link ModelCompareBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareBean getCompareInfo(Long compareId) throws BimfaceException {
        return apiClient.getModelCompare(compareId, accessTokenService.getAccessToken());
    }

    /**
     * 查看模型对比结果项
     *
     * @param compareId 模型对比Id
     * @return {@link ModelCompareBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<ModelCompareTree.SpecialtyNode> getCompareResult(Long compareId) throws BimfaceException {
        return dataClient.getModelCompareResult(compareId, accessTokenService.getAccessToken());
    }

    /**
     * 查看模型对比构件差异
     * 注意： 只有变更类型diffType=change的才能调用此接口获取差异对比，新增new和删除delete的调用属性接口查看构件原始属性。
     *
     * @param compareElementRequest {@link CompareElementRequest}
     * @return {@link ModelCompareChange}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ModelCompareChange getCompareElementDiff(CompareElementRequest compareElementRequest) throws BimfaceException {
        return dataClient.getModelCompareChange(compareElementRequest.getCompareId(), compareElementRequest.getPreviousFileId(), compareElementRequest.getPreviousElementId(),
                compareElementRequest.getFollowingFileId(), compareElementRequest.getFollowingElementId(), accessTokenService.getAccessToken());
    }
}
