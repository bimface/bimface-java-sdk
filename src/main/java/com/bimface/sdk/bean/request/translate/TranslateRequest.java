
package com.bimface.sdk.bean.request.translate;

/**
 * 文件转换的API参数
 * 
 * @author bimface, 2016-12-13.
 */
public class TranslateRequest {

    private static final Integer DEFAULT_PRIORITY = 2;                // 默认优先级

    private TranslateSource      source;
    private Integer              priority         = DEFAULT_PRIORITY; // 优先级
    private String               callback;                            // 回调地址

    public TranslateRequest() {
    }

    public TranslateSource getSource() {
        return source;
    }

    public void setSource(TranslateSource source) {
        this.source = source;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

}
