package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 构件
 * 
 * @author bimface, 2016-12-01.
 */
public class Element implements Serializable {

    private static final long serialVersionUID = 6259372444309084393L;

    private Long   fileId;   // 文件id
    private String elementId;// 构件id

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

}
