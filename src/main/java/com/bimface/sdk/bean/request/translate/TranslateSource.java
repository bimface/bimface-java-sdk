/**
 * 
 */
package com.bimface.sdk.bean.request.translate;

/**
 * 转换输入参数源文件
 *
 * @author bimface, 2016-12-13.
 */
public class TranslateSource {

    private Long    fileId;            // 文件id
    private Boolean compressed = false;// 是否为压缩文件，默认false
    private String  rootName;          // 压缩文件里主文件名

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Boolean getCompressed() {
        return compressed;
    }

    public void setCompressed(Boolean compressed) {
        this.compressed = compressed;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

}
