package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.SupportFileBean;
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

    private final String SUPPORT_FILE_URL = getFileHost() + "/support";

    public SupportFileService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 获取支持上传文件格式和文件大小
     * 
     * @return {@link SupportFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public SupportFileBean getSupport() throws BimfaceException {

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(SUPPORT_FILE_URL, headers);
        SupportFileBean supportFileBean = HttpUtils.response(response,
                                                             new TypeReference<GeneralResponse<SupportFileBean>>() {});
        return supportFileBean;
    }
}
