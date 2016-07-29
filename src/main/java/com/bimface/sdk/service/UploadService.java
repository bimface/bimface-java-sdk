package com.bimface.sdk.service;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.squareup.okhttp.Response;

/**
 * 文件上传
 * 
 * @author bimface, 2016-06-01.
 */
public class UploadService extends AbstractAccessTokenService {

    private final String       UPLOAD_URL        = getFileHost() + "/upload?name=%s&suffix=%s";
    private final String       UPLOAD_BY_URL_URL = getFileHost() + "/upload?name=%s&suffix=%s&url=%s";

    private SupportFileService supportFileService;

    public UploadService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public FileBean upload(FileUploadRequest fileUploadRequest) throws BimfaceException {

        // 参数校验
        check(fileUploadRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = null;
        String requestUrl = null;
        if (fileUploadRequest.isByUrl()) {
            requestUrl = String.format(UPLOAD_BY_URL_URL, fileUploadRequest.getName(), fileUploadRequest.getSuffix(),
                                       fileUploadRequest.getUrl());
            response = getServiceClient().put(requestUrl, headers);
        } else {
            requestUrl = String.format(UPLOAD_URL, fileUploadRequest.getName(), fileUploadRequest.getSuffix());
            headers.addHeader(HttpHeaders.CONTENT_LENGTH, fileUploadRequest.getContentLength().toString());
            response = getServiceClient().put(requestUrl, fileUploadRequest.getInputStream(),
                                              fileUploadRequest.getContentLength(), headers);
        }
        return HttpUtils.response(response, new TypeReference<GeneralResponse<FileBean>>() {});
    }

    private void check(FileUploadRequest fileUploadRequest) throws BimfaceException {
        AssertUtils.assertParameterNotNull(fileUploadRequest, "fileUploadRequest");
        if (fileUploadRequest.getUrl() == null) {
            if (fileUploadRequest.getContentLength() == null && fileUploadRequest.getContentLength() < 0) {
                throw new IllegalArgumentException("ParameterLongIsEmpty ContentLength");
            }
            AssertUtils.assertParameterNotNull(fileUploadRequest.getInputStream(), "inputStream");
        }
        AssertUtils.checkFileName(fileUploadRequest.getName());
        List<String> allSupportedType = getSupportFileService().getSupportFile();
        AssertUtils.checkFileSuffix(allSupportedType, fileUploadRequest.getSuffix());
    }

    public void setSupportFileService(SupportFileService supportFileService) {
        this.supportFileService = supportFileService;
    }

    public SupportFileService getSupportFileService() {
        return supportFileService;
    }
}
