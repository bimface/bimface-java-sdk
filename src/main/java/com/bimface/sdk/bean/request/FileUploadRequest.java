package com.bimface.sdk.bean.request;

import java.io.InputStream;

import com.bimface.sdk.utils.StringUtils;

/**
 * 文件上传的请求参数
 * 
 * @author bimface, 2016-06-01.
 */
public class FileUploadRequest {

    private String      name;          // 文件名称，包括后缀名
    private String      sourceId;      // 源文件Id
    private Long        contentLength; // 文件长度
    private InputStream inputStream;   // 文件流
    private String      url;           // 文件的下载地址，如果提供了下载地址，则无需设置inputStream、contentLength

    private String      bucket;        // 文件在阿里云的bucket，如果提供了bucket，则需要提供objectKey，不设置url，inputStream和contentLength
    private String      objectKey;     // 文件在阿里云的objectKey，如果提供了objectKey，则需要提供objectKey，不设置url，inputStream和contentLength

    public FileUploadRequest() {
    }

    public FileUploadRequest(String name, String sourceId, String url) {
        this.name = name;
        this.sourceId = sourceId;
        this.url = url;
    }

    public FileUploadRequest(String name, String sourceId, Long contentLength, InputStream inputStream) {
        this.name = name;
        this.sourceId = sourceId;
        this.contentLength = contentLength;
        this.inputStream = inputStream;
    }

    public FileUploadRequest(String name, String sourceId, String bucket, String objectKey) {
        this.name = name;
        this.sourceId = sourceId;
        this.bucket = bucket;
        this.objectKey = objectKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    /**
     * 判断是否通过URL方式上传文件
     *
     * @return true:是, false:否
     */
    public boolean isByUrl() {
        return (this.url != null) && (url.length() > 0);
    }

    /**
     * 判断是否通过OSS元数据方式上传文件
     *
     * @return true:是, false:否
     */
    public boolean isByOSS() {
        return !StringUtils.isNullOrEmpty(bucket) && (!StringUtils.isNullOrEmpty(objectKey));
    }
}
