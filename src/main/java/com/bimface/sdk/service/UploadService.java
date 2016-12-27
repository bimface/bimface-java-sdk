package com.bimface.sdk.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.alibaba.fastjson.TypeReference;
import com.bimface.sdk.bean.GeneralResponse;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.SupportFileBean;
import com.bimface.sdk.bean.response.UploadPolicyBean;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.constants.BimfaceConstants;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.http.HttpHeaders;
import com.bimface.sdk.http.HttpUtils;
import com.bimface.sdk.http.ServiceClient;
import com.bimface.sdk.utils.AssertUtils;
import com.bimface.sdk.utils.FileNameUtils;
import com.bimface.sdk.utils.RequestBodyUtil;
import com.bimface.sdk.utils.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 文件上传
 * 
 * @author bimface, 2016-06-01.
 */
public class UploadService extends AbstractAccessTokenService {

    private final String UPLOAD_URL            = getFileHost() + "/upload?name=%s&sourceId=%s";
    private final String UPLOAD_BY_URL_URL     = getFileHost() + "/upload?name=%s&sourceId=%s&url=%s";
    private final String GET_UPLOAD_POLICY_URL = getFileHost() + "/upload/policy?name=%s&sourceId=%s";

    private SupportFileService supportFileService;

    public UploadService(ServiceClient serviceClient, Endpoint endpoint, AccessTokenService accessTokenService) {
        super(serviceClient, endpoint, accessTokenService);
    }

    /**
     * 上传文件
     * 
     * @param fileUploadRequest 文件上传的请求参数
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(FileUploadRequest fileUploadRequest) throws BimfaceException {

        check(fileUploadRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = null;
        String requestUrl = null;
        if (fileUploadRequest.isByUrl()) {
            requestUrl = String.format(UPLOAD_BY_URL_URL, fileUploadRequest.getName(), fileUploadRequest.getSourceId(),
                                       fileUploadRequest.getUrl());
            response = getServiceClient().put(requestUrl, headers);
        } else {
            requestUrl = String.format(UPLOAD_URL, fileUploadRequest.getName(), fileUploadRequest.getSourceId());
            headers.addHeader(HttpHeaders.CONTENT_LENGTH, fileUploadRequest.getContentLength().toString());
            response = getServiceClient().put(requestUrl, fileUploadRequest.getInputStream(),
                                              fileUploadRequest.getContentLength(), headers);
        }
        return HttpUtils.response(response, new TypeReference<GeneralResponse<FileBean>>() {});
    }

    /**
     * 获取上传凭证
     *
     * @param name 上传文件名,（带后缀名）
     * @param sourceId 上传源文件Id（非必选）
     * @return {@link UploadPolicyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public UploadPolicyBean getPolicy(String name, String sourceId) throws BimfaceException {

        // 文件名校验
        FileNameUtils.checkFileName(name);
        // sourceId为null时设为""
        if (StringUtils.isNullOrEmpty(sourceId)) {
            sourceId = "";
        }

        SupportFileBean supportFileBean = getSupportFileService().getSupport();
        String[] allSupportedType = supportFileBean.getTypes();
        FileNameUtils.checkFileType(allSupportedType, name);

        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());
        Response response = getServiceClient().get(String.format(GET_UPLOAD_POLICY_URL, name, sourceId), headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<UploadPolicyBean>>() {});
    }

    /**
     * 通过申请上传Policy的方式直接上传到OSS
     * 
     * @param name
     * @param sourceId 上传源文件Id（非必选）
     * @param contentLength 文件长度
     * @param inputStream 二进制文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean uploadByPolicy(String name, String sourceId, Long contentLength,
                                   InputStream inputStream) throws BimfaceException {
        check(new FileUploadRequest(name, sourceId, contentLength, inputStream));

        UploadPolicyBean policy = getPolicy(name, sourceId);
        return uploadByPolicy(policy, name, contentLength, inputStream);
    }

    private FileBean uploadByPolicy(UploadPolicyBean policy, String name, Long contentLength,
                                    InputStream inputStream) throws BimfaceException {
        HttpHeaders headers = new HttpHeaders();
        headers.addOAuth2Header(getAccessToken());

        RequestBody body = new MultipartBuilder().type(MultipartBuilder.FORM).addFormDataPart("key",
                                                                                              policy.getObjectKey()).addFormDataPart("success_action_status",
                                                                                                                                     "200").addFormDataPart("Content-Disposition",
                                                                                                                                                            "attachment;filename=" + name).addFormDataPart("OSSAccessKeyId",
                                                                                                                                                                                                           policy.getAccessId()).addFormDataPart("policy",
                                                                                                                                                                                                                                                 policy.getPolicy()).addFormDataPart("Signature",
                                                                                                                                                                                                                                                                                     policy.getSignature()).addFormDataPart("callback",
                                                                                                                                                                                                                                                                                                                            policy.getCallbackBody()).addFormDataPart("file",
                                                                                                                                                                                                                                                                                                                                                                      name,
                                                                                                                                                                                                                                                                                                                                                                      RequestBodyUtil.create(MediaType.parse(BimfaceConstants.STREAM_MIME),
                                                                                                                                                                                                                                                                                                                                                                                             inputStream)).build();
        Response response = getServiceClient().post(policy.getHost(), body, headers);
        return HttpUtils.response(response, new TypeReference<GeneralResponse<FileBean>>() {});
    }

    private void check(FileUploadRequest fileUploadRequest) throws BimfaceException {

        AssertUtils.assertParameterNotNull(fileUploadRequest, "fileUploadRequest");

        // 文件名校验
        String fileName = null;
        try {
            fileName = URLDecoder.decode(fileUploadRequest.getName(), BimfaceConstants.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        FileNameUtils.checkFileName(fileName);
        SupportFileBean supportFileBean = getSupportFileService().getSupport();
        String[] allSupportedType = supportFileBean.getTypes();
        // 检查文件格式是否支持
        FileNameUtils.checkFileType(allSupportedType, fileUploadRequest.getName());

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
            // 检查文件长度是否支持
            AssertUtils.checkFileLength(supportFileBean.getLength(), fileUploadRequest.getContentLength());
        }
    }

    public void setSupportFileService(SupportFileService supportFileService) {
        this.supportFileService = supportFileService;
    }

    public SupportFileService getSupportFileService() {
        return supportFileService;
    }
}
