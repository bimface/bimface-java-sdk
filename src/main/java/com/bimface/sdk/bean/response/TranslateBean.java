package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 文件转换返回参数
 * 
 * @author bimface 2016-12-15
 */
public class TranslateBean implements Serializable {

    private static final long serialVersionUID = -4106553049907952503L;
    private Long              fileId;                                  // 转换Id
    private String            name;                                    // 文件名称，包括后缀
    private Integer           priority;                                // 优先级
    private String            status;                                  // 转换状态
    private String[]          thumbnail;                               // 缩略图路径
    private String            reason;                                  // 失败原因
    private String            createTime;                              // 创建时间，格式：yyyy-MM-dd hh:mm:ss

    public TranslateBean() {
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
