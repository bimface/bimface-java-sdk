package com.bimface.sdk.service;

import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.*;
import com.bimface.sdk.bean.request.FileBatchQueryRequest;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.client.FileClient;
import com.bimface.sdk.config.Endpoint;
import com.bimface.sdk.constants.BimfaceConstants;
import com.bimface.sdk.utils.AssertUtils;
import com.bimface.sdk.utils.FileNameUtils;
import com.bimface.sdk.utils.StringUtils;
import com.glodon.paas.foundation.restclient.RESTStreamRequestBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * 文件上传
 *
 * @author bimface, 2016-06-01.
 */
public class FileService {

    private AccessTokenService accessTokenService;
    private FileClient fileClient;


    public FileService(Endpoint endpoint, AccessTokenService accessTokenService) {
        this.fileClient = FileClient.getFileClient(endpoint.getFileHost());
        this.accessTokenService = accessTokenService;
    }

    /**
     * 上传文件
     *
     * @param fileUploadRequest 文件上传的请求参数
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean upload(FileUploadRequest fileUploadRequest) throws BimfaceException {
        return upload(fileUploadRequest, accessTokenService.getAccessToken());
    }

    public FileBean upload(FileUploadRequest fileUploadRequest, String accessToken) throws BimfaceException {
        check(fileUploadRequest, accessToken);
        FileBean fileBean;
        if (fileUploadRequest.isByUrl()) {
            fileBean = fileClient.uploadFileByUrl(fileUploadRequest.getName(), fileUploadRequest.getSourceId(),
                    fileUploadRequest.getUrl(), fileUploadRequest.getEtag(), accessToken);
        } else if (fileUploadRequest.isByOSS()) {
            fileBean = fileClient.uploadFileFromOSS(fileUploadRequest.getName(), fileUploadRequest.getSourceId(),
                    fileUploadRequest.getBucket(), fileUploadRequest.getObjectKey(), accessToken);
        } else {
            fileBean = fileClient.uploadFileStream(fileUploadRequest.getName(), fileUploadRequest.getSourceId(),
                    fileUploadRequest.getContentLength(), fileUploadRequest.getInputStream(), accessToken);
        }
        return fileBean;
    }

    /**
     * 获取上传凭证
     *
     * @param name     上传文件名,（带后缀名）
     * @param sourceId 上传源文件Id（非必选）
     * @return {@link UploadPolicyBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public UploadPolicyBean getPolicy(String name, String sourceId) throws BimfaceException {
        return getPolicy(name, sourceId, accessTokenService.getAccessToken());
    }

    public UploadPolicyBean getPolicy(String name, String sourceId, String accessToken) throws BimfaceException {

        // 文件名校验
        FileNameUtils.checkFileName(name);
        // sourceId为null时设为""
        if (StringUtils.isNullOrEmpty(sourceId)) {
            sourceId = "";
        }

        SupportFileBean supportFileBean = fileClient.getSupportFileTypes(accessToken);
        String[] allSupportedType = supportFileBean.getTypes();
        FileNameUtils.checkFileType(allSupportedType, name);

        return fileClient.getUploadPolicy(name, sourceId, accessToken);
    }

    /**
     * 通过申请上传Policy的方式直接上传到OSS
     *
     * @param name          上传文件名
     * @param sourceId      上传源文件Id（非必选）
     * @param contentLength 文件长度
     * @param inputStream   二进制文件流
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean uploadByPolicy(String name, String sourceId, Long contentLength,
                                   InputStream inputStream) throws BimfaceException {
        return uploadByPolicy(name, sourceId, contentLength, inputStream, accessTokenService.getAccessToken());
    }

    public FileBean uploadByPolicy(String name, String sourceId, Long contentLength, InputStream inputStream,
                                   String accessToken) throws BimfaceException {
        check(new FileUploadRequest(name, sourceId, contentLength, inputStream), accessToken);

        UploadPolicyBean policy = getPolicy(name, sourceId, accessToken);
        return uploadByPolicy(policy, name, contentLength, inputStream, accessToken);
    }

    private FileBean uploadByPolicy(UploadPolicyBean policy, String name, Long contentLength, InputStream inputStream,
                                    String accessToken) throws BimfaceException {

        if (contentLength == null || contentLength < 0) {
            try {
                contentLength = (long) inputStream.available();
            } catch (IOException e) {
                throw new BimfaceException("invalid input stream", e);
            }
        }
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("key",
                                                                                              policy.getObjectKey()).addFormDataPart("success_action_status",
                                                                                                                                     "200").addFormDataPart("Content-Disposition",
                                                                                                                                                            "attachment;filename=" + name).addFormDataPart("OSSAccessKeyId",
                                                                                                                                                                                                           policy.getAccessId()).addFormDataPart("policy",
                                                                                                                                                                                                                                                 policy.getPolicy()).addFormDataPart("Signature",
                                                                                                                                                                                                                                                                                     policy.getSignature()).addFormDataPart("callback",
                                                                                                                                                                                                                                                                                                                            policy.getCallbackBody()).addFormDataPart("file",
                                                                                                                                                                                                                                                                                                                                                                      name,
                                                                                                                                                                                                                                                                                                                                                                      RESTStreamRequestBody.create(MediaType.parse(BimfaceConstants.STREAM_MIME), contentLength, inputStream)).build();
        return fileClient.uploadByPolicy(policy.getHost(), body);
    }

    public void check(FileUploadRequest fileUploadRequest, String accessToken) throws BimfaceException {

        AssertUtils.assertParameterNotNull(fileUploadRequest, "fileUploadRequest");

        FileNameUtils.checkFileName(fileUploadRequest.getName());
        SupportFileBean supportFileBean = fileClient.getSupportFileTypes(accessToken);
        String[] allSupportedType = supportFileBean.getTypes();
        // 检查文件格式是否支持
        FileNameUtils.checkFileType(allSupportedType, fileUploadRequest.getName());

        // 检查文件内容
        if (fileUploadRequest.isByUrl()) {

            // 如果是URL方式上传
            AssertUtils.checkUrl(fileUploadRequest.getUrl());

        } else if (!fileUploadRequest.isByOSS()) {

            // 如果是普通流上传
            if (fileUploadRequest.getContentLength() == null || fileUploadRequest.getContentLength() < 0) {
                throw new IllegalArgumentException("ContentLength is null.");
            }
            AssertUtils.assertParameterNotNull(fileUploadRequest.getInputStream(), "inputStream");
            // 检查文件长度是否支持
            AssertUtils.checkFileLength(supportFileBean.getLength(), fileUploadRequest.getContentLength());
        }
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @throws BimfaceException {@link BimfaceException}
     */
    public void deleteFile(Long fileId) throws BimfaceException {
        fileClient.deleteFile(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 创建追加文件
     *
     * @param name     文件的全名，使用URL编码（UTF-8），最多256个字符
     * @param sourceId 调用方的文件源ID，不能重复
     * @param length   上传文件长度
     * @return AppendFileBean {@link AppendFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean createAppendFile(String name, String sourceId, Long length) throws BimfaceException {
        return createAppendFile(name, sourceId, length, accessTokenService.getAccessToken());
    }

    public AppendFileBean createAppendFile(String name, String sourceId, Long length, String accessToken) throws BimfaceException {
        // 文件名校验
        FileNameUtils.checkFileName(name);
        AssertUtils.assertParameterNotNull(length, "length");
        // sourceId为null时设为""
        if (StringUtils.isNullOrEmpty(sourceId)) {
            sourceId = "";
        }

        SupportFileBean supportFileBean = fileClient.getSupportFileTypes(accessToken);
        String[] allSupportedType = supportFileBean.getTypes();
        FileNameUtils.checkFileType(allSupportedType, name);

        return fileClient.createAppendFile(name, sourceId, length, accessToken);
    }

    /**
     * 查询追加文件信息
     *
     * @param appendFileId
     * @return AppendFileBean {@link AppendFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean queryAppendFile(Long appendFileId) throws BimfaceException {
        return queryAppendFile(appendFileId, accessTokenService.getAccessToken());
    }

    public AppendFileBean queryAppendFile(Long appendFileId, String accessToken) throws BimfaceException {
        return fileClient.getAppendFile(appendFileId, accessToken);
    }

    /**
     * 追加上传
     *
     * @param appendFileId
     * @return AppendFileBean {@link AppendFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public AppendFileBean uploadAppendFile(InputStream inputStream, Long appendFileId) throws BimfaceException {
        return uploadAppendFile(inputStream, appendFileId, accessTokenService.getAccessToken());
    }

    public AppendFileBean uploadAppendFile(InputStream inputStream, Long appendFileId, String accessToken) throws BimfaceException {
        AppendFileBean appendFileBean = queryAppendFile(appendFileId, accessToken);

        RequestBody body = requestIOFromPosition(inputStream, appendFileBean);

        return fileClient.appendUpload(appendFileId, appendFileBean.getPosition(), body, accessToken);
    }

    private static RequestBody requestIOFromPosition(final InputStream inputStream, final AppendFileBean appendFileBean) {
        if (inputStream == null) throw new NullPointerException("content == null");

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return MediaType.parse(BimfaceConstants.STREAM_MIME);
            }

            @Override
            public long contentLength() {
                return appendFileBean.getLength() - appendFileBean.getPosition();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                try {
                    inputStream.skip(appendFileBean.getPosition());

                    final byte[] buffer = new byte[BimfaceConstants.PUT_THRESHOLD];
                    int l;
                    while ((l = inputStream.read(buffer)) != -1) {
                        sink.outputStream().write(buffer, 0, l);
                    }
                } finally {
                    sink.close();
                }
            }
        };
    }

    /**
     * 根据文件id获取文件元信息
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     * @deprecated
     */
    @Deprecated
    public FileBean getFileMetadata(Long fileId) throws BimfaceException {
        return fileClient.getFileMetaData(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 根据文件id获取文件元信息
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileBean getFile(Long fileId) throws BimfaceException {
        return fileClient.getFile(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 根据批量获取文件元信息
     *
     * @param request
     * @return
     * @throws BimfaceException
     */
    public List<FileBean> getFiles(FileBatchQueryRequest request) throws BimfaceException {
        String startTime = null;
        String endTime = null;
        String status = null;
        LocalDate from = request.getStartTime();
        LocalDate to = request.getEndTime();
        Long offset = request.getOffset();
        Long rows = request.getRows();
        if (from != null && to != null) {
            AssertUtils.assertTrue(from.isBefore(to), "start time must be earlier than end time");
        }
        if (from != null) {
            startTime = from.toString();
        }
        if (to != null) {
            endTime = to.toString();
        }
        if (offset != null && offset < 0) {
            throw new IllegalArgumentException("offset must be equal or greater than 0");
        }
        if (rows != null) {
            AssertUtils.assertParameterInRange(rows, 1, 500);
        }
        if (request.getStatus() != null) {
            status = request.getStatus().getName();
        }
        return fileClient.getFiles(request.getSuffix(), status,
                startTime, endTime, offset, rows, accessTokenService.getAccessToken());
    }

    /**
     * 根据文件id获取文件上传状态信息
     *
     * @param fileId 文件Id
     * @return {@link FileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public FileUploadStatusBean getFileUploadStatus(Long fileId) throws BimfaceException {
        return fileClient.getFileUploadStatus(fileId, accessTokenService.getAccessToken());
    }

    /**
     * 根据文件id获取文件元信息
     *
     * @return {@link SupportFileBean}
     * @throws BimfaceException {@link BimfaceException}
     */
    public SupportFileBean getSupportedFileTypes() throws BimfaceException {
        return fileClient.getSupportFileTypes(accessTokenService.getAccessToken());
    }
}
