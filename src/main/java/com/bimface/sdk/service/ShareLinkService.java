package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.ShareLinkBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 分享链接
 * 
 * @author bimface, 2016-06-01.
 */
public class ShareLinkService extends AbstractAccessTokenService {

    private final String CREATE_TRANSLATE_SHARE_URL         = getApiHost() + "/share?fileId=%s&activeHours=%s";

    private final String CREATE_TRANSLATE_SHARE_URL_FOREVER = getApiHost() + "/share?fileId=%s";

    private final String DELETE_TRANSLATE_SHARE_URL         = getApiHost() + "/share?fileId=%s";

    private final String CREATE_INTEGRATE_SHARE_URL         = getApiHost() + "/share?integrateId=%s&activeHours=%s";

    private final String CREATE_INTEGRATE_SHARE_URL_FOREVER = getApiHost() + "/share?integrateId=%s";

    private final String DELETE_INTEGRATE_SHARE_URL         = getApiHost() + "/share?integrateId=%s";

    public ShareLinkService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 创建单文件模型的分享链接
     * 
     * @param fileId 文件Id
     * @param activeHours 有效时长（单位：小时）
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShare(Long fileId, Integer activeHours) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "transferId");
        if (activeHours == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.addOAuth2Header(getAccessToken());
            Response response = getServiceClient().post(String.format(CREATE_TRANSLATE_SHARE_URL_FOREVER, fileId), "",
                                                        headers);
            return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
        }
        if (activeHours != null && activeHours <= 0) {
            throw new IllegalArgumentException("activeHours must not less than zero.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_TRANSLATE_SHARE_URL, fileId, activeHours), "",
                                                    headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    /**
     * 创建单文件模型的分享链接
     * 
     * @param fileId 文件Id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShare(Long fileId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_TRANSLATE_SHARE_URL_FOREVER, fileId), "",
                                                    headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    /**
     * 取消单文件模型的分享链接
     * 
     * @param fileId 文件id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteShare(Long fileId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().delete(String.format(DELETE_TRANSLATE_SHARE_URL, fileId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }

    /**
     * 分享集成模型，生成链接
     * 
     * @param integrateId 集成id
     * @param activeHours 有效时长（单位：小时）
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareIntegration(Long integrateId, Integer activeHours) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_INTEGRATE_SHARE_URL, integrateId, activeHours),
                                                    "", headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    /**
     * 分享集成模型，生成链接，永久有效
     * 
     * @param integrateId 集成id
     * @return {@link ShareLinkBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public ShareLinkBean createShareIntegration(Long integrateId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().post(String.format(CREATE_INTEGRATE_SHARE_URL_FOREVER, integrateId), "",
                                                    headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<ShareLinkBean>>() {});
    }

    /**
     * 取消集成模型的分享链接
     * 
     * @param integrateId 集成id
     * @return String
     * @throws BimfaceException {@link BimfaceException}
     */
    public String deleteShareIntegration(Long integrateId) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(integrateId, "integrateId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().delete(String.format(DELETE_INTEGRATE_SHARE_URL, integrateId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }
}
