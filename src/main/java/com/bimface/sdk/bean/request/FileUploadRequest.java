package com.bimface.sdk.bean.request;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bimface.sdk.constants.BimfaceConstants;

/**
 * 文件上传的请求参数
 * 
 * @author bimface, 2016-06-01.
 */
public class FileUploadRequest {

    private String      name;         // 文件名称，包括后缀名
    private Long        contentLength;// 文件长度
    private InputStream inputStream;  // 文件流
    private String      url;          // 文件的下载地址，如果提供了下载地址，则无需设置inputStream、contentLength

    public FileUploadRequest() {
    }

    public FileUploadRequest(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public FileUploadRequest(String name, Long contentLength, InputStream inputStream) {
        this.name = name;
        this.contentLength = contentLength;
        this.inputStream = inputStream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        try {
            this.name = URLEncoder.encode(name, BimfaceConstants.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
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
        if (url == null) {
            return;
        }
        try {
            this.url = URLEncoder.encode(url, BimfaceConstants.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 判断是否通过URL方式上传文件
     * 
     * @return true:是, false:否
     */
    public boolean isByUrl() {
        return (this.url != null) && (url.length() > 0);
    }
}
