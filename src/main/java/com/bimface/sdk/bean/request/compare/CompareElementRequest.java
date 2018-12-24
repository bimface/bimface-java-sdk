package com.bimface.sdk.bean.request.compare;

/**
 * 模型对比构建差异的请求参数
 * 
 * @author bimface, 2018-02-08.
 */
public class CompareElementRequest {

    private Long                compareId;                                      // 模型对比ID
    private Long                previousFileId;                                 // 对比差异构件来源文件ID
    private String				previousElementId;                              // 对比差异构件来源构件ID
    private Long                followingFileId;                                // 对比差异构件变更文件ID
    private String				followingElementId;                             // 对比差异构件互为变更构件ID

    public CompareElementRequest() {
    }

    public Long getCompareId() {
        return compareId;
    }

    public void setCompareId(Long compareId) {
        this.compareId = compareId;
    }

    public Long getPreviousFileId() {
        return previousFileId;
    }

    public void setPreviousFileId(Long previousFileId) {
        this.previousFileId = previousFileId;
    }

    public String getPreviousElementId() {
        return previousElementId;
    }

    public void setPreviousElementId(String previousElementId) {
        this.previousElementId = previousElementId;
    }

    public Long getFollowingFileId() {
        return followingFileId;
    }

    public void setFollowingFileId(Long followingFileId) {
        this.followingFileId = followingFileId;
    }

    public String getFollowingElementId() {
        return followingElementId;
    }

    public void setFollowingElementId(String followingElementId) {
        this.followingElementId = followingElementId;
    }
}
