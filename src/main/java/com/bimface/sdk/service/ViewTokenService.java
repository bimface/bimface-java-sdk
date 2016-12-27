package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 获取viewToken
 * 
 * @author bimface, 2016-06-01.
 */
public class ViewTokenService extends AbstractAccessTokenService {

    private final String VIEW_TOKEN_FILEID_URL      = getApiHost() + "/view/token?fileId=%s";
    private final String VIEW_TOKEN_TRANSFERID_URL  = getApiHost() + "/view/token?transferId=%s";
    private final String VIEW_TOKEN_INTEGRATEID_URL = getApiHost() + "/view/token?integrateId=%s";

    public ViewTokenService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 兼容接口，用transferId获取单文件浏览凭证
     * 
     * @param transferId 转换id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String grantViewTokenByTransferId(String transferId) throws BimfaceException {
        AssertUtils.assertStringNotNullOrEmpty(transferId, "transferId");
        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(VIEW_TOKEN_TRANSFERID_URL, transferId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }

    /**
     * 用fileId获取单文件浏览凭证
     * 
     * @param fileId 文件id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String grantViewTokenByFileId(Long fileId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(VIEW_TOKEN_FILEID_URL, fileId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }

    /**
     * 获取集成模型的浏览凭证
     * 
     * @param integrateId 集成id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String grantViewTokenByIntegrateId(Long integrateId) throws BimfaceException {
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");
        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(VIEW_TOKEN_INTEGRATEID_URL, integrateId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }
}
