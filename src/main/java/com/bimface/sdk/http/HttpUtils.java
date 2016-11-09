package com.bimface.sdk.http;

import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.exception.BimfaceException;
import com.squareup.okhttp.Response;

/**
 * http工具类
 * 
 * @author bimface, 2016-06-01.
 */
public class HttpUtils {

    public static <T> T response(Response response,
                                 TypeReference<GeneralResponse<T>> typeReference) throws BimfaceException {
        try {
            String body = response.body().string();
            if (response.isSuccessful()) {
                GeneralResponse<T> generalResponse = JSONObject.parseObject(body, typeReference);
                if (GeneralResponse.CODE_SUCCESS.equalsIgnoreCase(generalResponse.getCode())) {
                    return generalResponse.getData();
                } else {
                    throw new BimfaceException(generalResponse.getMessage(), generalResponse.getCode());
                }
            } else {
                throw new BimfaceException(body, response.code());
            }
        } catch (IOException e) {
            throw new BimfaceException(e);
        }
    }

    public static InputStream response(Response response) throws BimfaceException {
        try {
            return response.body().byteStream();
        } catch (IOException e) {
            throw new BimfaceException(e);
        }
    }
}
