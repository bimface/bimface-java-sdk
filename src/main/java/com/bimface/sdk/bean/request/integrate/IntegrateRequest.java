package com.bimface.sdk.bean.request.integrate;

import java.util.List;

/**
 * 文件合并的请求参数
 * 
 * @author bimface, 2016-12-13.
 */
public class IntegrateRequest {

    private static final Byte DEFAULT_PRIORITY = 2; // 默认优先级

    private Byte                  priority = DEFAULT_PRIORITY; // 优先级
    private List<IntegrateSource> sources;                     // 待合并的文件
    private String                callback;                    // 回调地址
    private String                sourceId;
    private String                name;                        // 指定集成后显示名称

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public List<IntegrateSource> getSources() {
        return sources;
    }

    public void setSources(List<IntegrateSource> sources) {
        this.sources = sources;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
