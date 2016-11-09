package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 文件服务
 *
 * @author bimface, 2016-11-01.
 */
public class FileService extends AbstractAccessTokenService {

    private final String GET_FILE_METADATA_URL = getFileHost() + "/metadata?fileId=%s";
    private final String GET_FILE_DOWNLOAD_URL = getFileHost() + "/download/url?fileId=%s&name=%s";

    public FileService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 获取属性
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    public FileBean getFileMetadata(Long fileId) throws BimfaceException {
        // 参数校验
        AssertUtils.assertParameterNotNull(fileId, "fileId");

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_FILE_METADATA_URL, fileId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<FileBean>>() {});
    }
}
