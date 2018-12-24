package com.bimface.sdk.bean.request;

/**
 * 离线数据包请求参数
 * 
 * @author xxy, 2017-08-23.
 */
public class OfflineDatabagRequest {

    private Long      fileId;         // 文件ID，三个参数只能选其一
    private Long      integrateId;    // 集成ID，三个参数只能选其一
    private Long      compareId;      // 对比ID，三个参数只能选其一
    private String    callback;       // 回调URL
    private String    databagVersion; // 离线数据包版本，如果只有一个，则下载唯一的数据包，如果多个，则必须指定数据包版本

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getIntegrateId() {
        return integrateId;
    }

    public void setIntegrateId(Long integrateId) {
        this.integrateId = integrateId;
    }

    public Long getCompareId() {
        return compareId;
    }

    public void setCompareId(Long compareId) {
        this.compareId = compareId;
    }

    public String getCallback() {
        return callback;
    }
    
    public void setCallback(String callback) {
        this.callback = callback;
    }
    
    public String getDatabagVersion() {
        return databagVersion;
    }
    
    public void setDatabagVersion(String databagVersion) {
        this.databagVersion = databagVersion;
    }

    public OfflineDatabagRequest() {
        super();
    }

    public OfflineDatabagRequest(Long fileId, Long integrateId, Long compareId, String callback, String databagVersion) {
        this.fileId = fileId;
        this.integrateId = integrateId;
        this.compareId = compareId;
        this.callback = callback;
        this.databagVersion = databagVersion;
    }
}
