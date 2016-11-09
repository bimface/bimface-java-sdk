package com.bimface.sdk.service;

import java.io.InputStream;

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
 * 文件下载
 * 
 * @author bimface, 2016-11-01.
 */
public class DownloadService extends AbstractAccessTokenService {

    private final String DOWNLOAD_URL = getFileHost() + "/download/url?fileId=%s&fileName=%s";

    public DownloadService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 获取文件下载链接
     * 
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public String getDownloadUrl(Long fileId) throws BimfaceException {
        return getDownloadUrl(fileId, "");
    }

    /**
     * 获取文件下载链接
     * 
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public String getDownloadUrl(Long fileId, String fileName) throws BimfaceException {

        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");
        AssertUtils.assertParameterNotNull(fileName, "fileName");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(DOWNLOAD_URL, fileId.toString(), fileName), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<String>>() {});
    }

    /**
     * 获取文件流
     *
     * @param fileId 文件id
     * @param name 文件名称
     * @return 文件二进制输入流
     * @throws BimfaceException {@link BimfaceException}
     */
    public InputStream getFileContent(Long fileId, String name) throws BimfaceException {
        String url = null;
        if (name == null) {
            url = getDownloadUrl(fileId);
        } else {
            url = getDownloadUrl(fileId, name);
        }
        HttpHeaders headers = new HttpHeaders();
        Response response = getServiceClient().get(url, headers);
        return HttpUtils.response(response);
    }
}
