package com.bimface.sdk.service;

import com.bimface.data.bean.Category;
import com.bimface.data.bean.FloorTree;
import com.bimface.data.bean.SpecialtyTree;
import com.bimface.data.bean.Tree;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 构件分类树服务
 *
 * @author bimface, 2016-11-01.
 */
public class CategoryTreeService {
    private DataClient dataClient;
    private AccessTokenService accessTokenService;
    private final static Gson GSON = new Gson();
    public CategoryTreeService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }

    /**
     * 获取构件分类树
     *
     * @param fileId 文件Id
     * @return {@link Tree.TreeNode}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Category> getCategoryTree(Long fileId) throws BimfaceException {
        Object object = dataClient.getSingleModelTree(fileId, "1.0", accessTokenService.getAccessToken());
        return GSON.fromJson(GSON.toJson(object), new TypeToken<List<Category>>(){}.getType());
    }

    /**
     * 获取构件分类树V2
     * 
     * @param fileId 文件Id
     * @return {@link Tree.TreeNode}
     * @throws BimfaceException {@link BimfaceException}
     */
    public List<Tree.TreeNode> getCategoryTreeV2(Long fileId) throws BimfaceException {
        Object object = dataClient.getSingleModelTree(fileId, "2.0", accessTokenService.getAccessToken());
        return GSON.fromJson(GSON.toJson(object), new TypeToken<List<Tree.TreeNode>>(){}.getType());
    }

    /**
     * 获取集成模型的楼层层次结构
     * 
     * @param integrateId 集成id
     * @return {@link FloorTree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FloorTree getFloorTree(Long integrateId) throws BimfaceException {
        Object object = dataClient.getIntegrateTree(integrateId, 2, accessTokenService.getAccessToken());
        return GSON.fromJson(GSON.toJson(object), FloorTree.class);
    }

    /**
     * 获取集成模型的专业层次结构
     * 
     * @param integrateId 集成id
     * @return {@link SpecialtyTree}
     * @throws BimfaceException {@link BimfaceException}
     */
    public SpecialtyTree getSpecialtyTree(Long integrateId) throws BimfaceException {
        Object object = dataClient.getIntegrateTree(integrateId, 1, accessTokenService.getAccessToken());
        return GSON.fromJson(GSON.toJson(object), SpecialtyTree.class);
    }
}
