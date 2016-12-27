package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.request.translate.TranslateRequest;
import com.bimface.sdk.bean.response.TranslateBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 文件转换
 * 
 * @author bimface, 2016-06-01.
 */
public class TranslateService extends AbstractAccessTokenService {

    private final String TRANSLATE_URL     = getApiHost() + "/translate";
    private final String GET_TRANSLATE_URL = getApiHost() + "/translate?fileId=%s";

    public TranslateService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 发起文件转换
     * 
     * @param request 文件转换请求
     * @return {@link TranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateBean translate(TranslateRequest request) throws BimfaceException {

        // 参数校验
        check(request);

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().put(TRANSLATE_URL, request, headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<TranslateBean>>() {});
    }

    /**
     * 参看文件转换状态
     * 
     * @param fileId 文件Id
     * @return {@link TranslateBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public TranslateBean getTranslate(Long fileId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_TRANSLATE_URL, fileId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<TranslateBean>>() {});
    }

    private void check(TranslateRequest request) {
        AssertUtils.assertParameterNotNull(request, "TranslateRequest");
        AssertUtils.assertParameterNotNull(request.getSource(), "source");
        AssertUtils.assertParameterNotNull(request.getSource().getFileId(), "fileId");
    }
}
