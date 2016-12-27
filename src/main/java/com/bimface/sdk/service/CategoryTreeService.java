package com.bimface.sdk.service;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.CategoryBean;
import com.bimface.sdk.bean.response.FloorTree;
import com.bimface.sdk.bean.response.SpecialtyTree;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 构件分类树服务
 *
 * @author bimface, 2016-11-01.
 */
public class CategoryTreeService extends AbstractAccessTokenService {

    private final String GET_CATEGORY_URL = getApiHost() + "/data/hierarchy?fileId=%s";
    private final String GET_TREE_URL     = getApiHost() + "/data/integration/tree?integrateId=%s&treeType=%s";

    public CategoryTreeService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 获取构件分类树
     * 
     * @param fileId 文件Id
     * @return {@link CategoryBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<CategoryBean> getCategoryTree(Long fileId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_CATEGORY_URL, fileId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<List<CategoryBean>>>() {});
    }

    /**
     * 获取集成模型的楼层层次结构
     * 
     * @param integrateId 集成id
     * @return {@link FloorTree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FloorTree getFloorTree(Long integrateId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        Integer treeType = 2;

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_TREE_URL, integrateId, treeType), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<FloorTree>>() {});
    }

    /**
     * 获取集成模型的专业层次结构
     * 
     * @param integrateId 集成id
     * @return {@link SpecialtyTree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public SpecialtyTree getSpecialtyTree(Long integrateId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        Integer treeType = 1;

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_TREE_URL, integrateId, treeType), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<SpecialtyTree>>() {});
    }

}
