package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 文件上传的返回参数
 * 
 * @author bimface, 2016-06-01.
 */
public class FileBean implements Serializable {

    private static final long serialVersionUID = 931141851693960861L;
    private Long              fileId;                                // 文件ID
    private String            name;                                  // 文件名称，包括后缀
    private String            status;                                // 上传状态
    private String            etag;                                  // 文件的md5值
    private String            suffix;                                // 文件后缀
    private Long              length;                                // 文件大小，（单位：byte）
    private String            createTime;                            // 上传时间，格式：yyyy-MM-dd hh:mm:ss

    public FileBean() {
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FileBean [fileId=" + fileId + ", name=" + name + ", etag=" + etag + ", suffix=" + suffix + ", length="
               + length + ", createTime=" + createTime + "]";
    }
}
