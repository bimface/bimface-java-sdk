package com.bimface.sdk.bean.response;

/**
 * 文件上传的返回参数
 * 
 * @author bimface, 2016-06-01.
 */
public class FileBean {

    private Long   fileId;    // 文件ID
    private String name;      // 文件名称，包括后缀
    private String suffix;    // 文件后缀
    private Long   length;    // 文件大小，（单位：byte）
    private String createTime;// 上传时间，格式：yyyy-MM-dd hh:mm:ss

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
        return "FileBean [fileId=" + fileId + ", name=" + name + ", suffix=" + suffix + ", length=" + length
               + ", createTime=" + createTime + "]";
    }
}
