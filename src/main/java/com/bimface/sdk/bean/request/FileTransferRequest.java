package com.bimface.sdk.bean.request;

/**
 * 文件转换的请求参数
 * 
 * @author bimface, 2016-06-01.
 */
public class FileTransferRequest {

    private Long   fileId;   // 文件id
    private String callback; // 回调地址

    public FileTransferRequest() {
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "FileTransferRequest [fileId=" + fileId + ", callback=" + callback + "]";
    }
}
