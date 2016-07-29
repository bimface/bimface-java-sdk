package com.bimface.sdk.service;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.squareup.okhttp.Response;

/**
 * 支持的文件格式
 * 
 * @author bimface, 2016-06-01.
 */
public class SupportFileService extends AbstractAccessTokenService {

    private List<String> supportFile;

    private final String SUPPORT_FILE_URL = getApiHost() + "/support";

    public SupportFileService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public List<String> getSupportFile() throws BimfaceException {

        // 在缓存中获取
        if (supportFile != null && !supportFile.isEmpty()) {
            return supportFile;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(SUPPORT_FILE_URL, headers);
        supportFile = HttpUtils.response(response, new TypeReference<GeneralResponse<List<String>>>() {});
        return supportFile;
    }
}
