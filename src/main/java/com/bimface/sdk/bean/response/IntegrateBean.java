package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 文件合并返回参数
 * 
 * @author bimface 2016-12-15
 */
public class IntegrateBean implements Serializable {

    private static final long serialVersionUID = 5938680848200183790L;
    private Long              integrateId;                            // 合并Id
    private Long              name;                                   // 合并文件的名字
    private Integer           priority;                               // 优先级
    private String            status;                                 // 转换状态
    private String[]          thumbnail;                              // 缩略图路径
    private String            reason;                                 // 失败原因
    private String            createTime;                             // 创建时间，格式：yyyy-MM-dd hh:mm:ss

    public Long getIntegrateId() {
        return integrateId;
    }

    public void setIntegrateId(Long integrateId) {
        this.integrateId = integrateId;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
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
