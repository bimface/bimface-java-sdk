package com.bimface.sdk.utils;

import com.bimface.sdk.bean.request.QueryElementIdsRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * @author bimface, 2019/07/24
 */
public class ConvertUtils {
    public static Map<String, String> convertToParamsMap(QueryElementIdsRequest queryElementIdsRequest) {
        Gson gson = new Gson();
        String requestJson = gson.toJson(queryElementIdsRequest);
        return gson.fromJson(requestJson, new TypeToken<Map<String, String>>(){}.getType());
    }
}
