package com.bimface.sdk.service;

import com.bimface.data.bean.Floor;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.client.DataClient;
import com.bimface.sdk.config.Endpoint;

import java.util.List;
import java.util.Map;

public class FloorService {
    private DataClient dataClient;
    private AccessTokenService accessTokenService;

    public FloorService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.dataClient = DataClient.getDataClient(endpoint.getApiHost() + "/data/");
        this.accessTokenService = accessTokenService;
    }


    /**
     * 获取楼层信息
     *
     * @param fileId 文件Id
     * @return 楼层信息映射列表 fileId、floors
     * @throws BimfaceException
     */
    public List<Floor> getFileFloors(Long fileId, Boolean includeArea, Boolean includeRoom) throws BimfaceException {

        return dataClient.getSingleModelFloors(fileId, includeArea, includeRoom, accessTokenService.getAccessToken());
    }

    /**
     *
     * @param integrateId   模型集成ID
     * @return 楼层信息
     * @throws BimfaceException
     */
    /**
     * 获取集成模型楼层信息
     *
     * @param integrateId 模型集成ID
     * @param includeArea 是否将楼层中的空间ID、名称一起返回
     * @param includeRoom 是否将楼层中的房间ID、名称一起返回
     * @return
     * @throws BimfaceException
     */
    public List<Floor> getIntegrateFloors(Long integrateId, Boolean includeArea, Boolean includeRoom) throws BimfaceException {
        return dataClient.getIntegrateModelFloors(integrateId, includeArea, includeRoom, accessTokenService.getAccessToken());
    }

    /**
     * 获取单模型文件 id 与楼层的映射关系，v2
     * @param fileIds
     * @param includeArea
     * @param includeRoom
     * @return
     * @throws BimfaceException
     */
    public List<Map<String, Object>> getSingleModelFileIdFloorsMapping(List<String> fileIds, Boolean includeArea, Boolean includeRoom) throws BimfaceException {
        return dataClient.getSingleModelFileIdFloorsMapping(fileIds, includeArea, includeRoom, accessTokenService.getAccessToken());
    }
}
