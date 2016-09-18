package com.bimface.sdk.service;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.SupportFileBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.bimface.sdk.utils.FileNameUtils;
import com.squareup.okhttp.Response;

/**
 * 文件上传
 * 
 * @author bimface, 2016-06-01.
 */
public class UploadService extends AbstractAccessTokenService {

    private final String       UPLOAD_URL        = getFileHost() + "/upload?name=%s";
    private final String       UPLOAD_BY_URL_URL = getFileHost() + "/upload?name=%s&url=%s";

    private SupportFileService supportFileService;

    public UploadService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    public FileBean upload(FileUploadRequest fileUploadRequest) throws BimfaceException {

        check(fileUploadRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = null;
        String requestUrl = null;
        if (fileUploadRequest.isByUrl()) {
            requestUrl = String.format(UPLOAD_BY_URL_URL, fileUploadRequest.getName(), fileUploadRequest.getUrl());
            response = getServiceClient().put(requestUrl, headers);
        } else {
            requestUrl = String.format(UPLOAD_URL, fileUploadRequest.getName());
            headers.addHeader(HttpHeaders.CONTENT_LENGTH, fileUploadRequest.getContentLength().toString());
            response = getServiceClient().put(requestUrl, fileUploadRequest.getInputStream(),
                                              fileUploadRequest.getContentLength(), headers);
        }
        return HttpUtils.response(response, new TypeReference<GeneralResponse<FileBean>>() {});
    }

    private void check(FileUploadRequest fileUploadRequest) throws BimfaceException {

        AssertUtils.assertParameterNotNull(fileUploadRequest, "fileUploadRequest");

        // 文件名校验
        FileNameUtils.checkFileName(fileUploadRequest.getName());

        // 检查文件内容
        if (fileUploadRequest.isByUrl()) {

            // 如果是URL方式上传
            AssertUtils.checkUrl(fileUploadRequest.getUrl());

        } else {

            // 如果是普通流上传
            if (fileUploadRequest.getContentLength() == null || fileUploadRequest.getContentLength() < 0) {
                throw new IllegalArgumentException("ContentLength is null.");
            }
            AssertUtils.assertParameterNotNull(fileUploadRequest.getInputStream(), "inputStream");
        }
        SupportFileBean supportFileBean = getSupportFileService().getSupport();
        String[] allSupportedType = supportFileBean.getTypes();
        // 检查文件格式是否支持
        FileNameUtils.checkFileType(allSupportedType, fileUploadRequest.getName());
        //检查文件长度是否支持
        AssertUtils.checkFileLength(supportFileBean.getLength(), fileUploadRequest.getContentLength());

    }

    public void setSupportFileService(SupportFileService supportFileService) {
        this.supportFileService = supportFileService;
    }

    public SupportFileService getSupportFileService() {
        return supportFileService;
    }
}
