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
    private String      suffix;       // 文件后缀名
    private Long        contentLength;// 文件长度

    private InputStream inputStream;  // 文件流
    private String      url;          // 文件的下载地址，如果提供了下载地址，则无需设置inputStream、contentLength

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (name != null && name.length() > 0) {
            setSuffix(name.substring(name.lastIndexOf(".") + 1, name.length()));
        }
    }

    public String getSuffix() {
        return suffix;
    }

    private void setSuffix(String suffix) {
        this.suffix = suffix;
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

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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

    /**
     * 判断是否通过URL方式上传文件
     * 
     * @return true:是, false:否
     */
    public boolean isByUrl() {
        return this.url != null;
    }

    @Override
    public String toString() {
        return "FileUploadRequest [name=" + name + ", suffix=" + suffix + ", contentLength=" + contentLength + ", url="
               + url + ", inputStream=" + inputStream + "]";
    }
}
