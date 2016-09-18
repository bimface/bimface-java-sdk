package com.bimface.sdk.bean.response;

import java.util.Arrays;

import com.bimface.sdk.constants.TransferStatus;

/**
 * 发起文件转换的返回参数
 * 
 * @author bimface, 2016-06-01.
 */
public class TransferBean {

    private String   transferId; // 转换ID
    private String   name;       // 文件名称，包括后缀
    private String   status;     // 转换状态
    private String[] thumbnail;  // 缩略图路径
    private String   reason;     // 转换失败的原因描述
    private String   createTime; // 启动转换的时间，格式：yyyy-MM-dd hh:mm:ss

    public TransferBean() {
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
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

    public TransferStatus getTransferStatus() {
        return TransferStatus.parse(status);
    }

    @Override
    public String toString() {
        return "TransferBean [transferId=" + transferId + ", name=" + name + ", status=" + status + ", thumbnail="
               + Arrays.toString(thumbnail) + ", reason=" + reason + ", createTime=" + createTime + "]";
    }

}
