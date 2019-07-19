package com.bimface.sdk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.request.QueryElementIdsRequest;

import java.util.Map;

public class ConvertUtils {
    public static Map<String, String> convertToParamsMap(QueryElementIdsRequest queryElementIdsRequest) {
        String requestJsonString = JSON.toJSONString(queryElementIdsRequest);
        Map<String, String> requestMap = JSON.parseObject(requestJsonString, new TypeReference<Map<String, String>>() {
        });
        return requestMap;
    }
}
