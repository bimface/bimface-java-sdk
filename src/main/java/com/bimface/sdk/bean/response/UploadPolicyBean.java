package com.bimface.sdk.bean.response;

/**
 * 上传策略实体
 * 
 * @author bimface, 2016-06-01.
 */
public class UploadPolicyBean {

    private String host;        // 域名
    private String policy;      // 上传策略
    private String accessId;    // OSS的访问ID
    private String signature;   // OSS签名
    private Long   expire;      // 超时时间,单位秒
    private String callbackBody;// 回调体
    private String objectKey;   // OSS中存储对象的key

    public UploadPolicyBean() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long i) {
        this.expire = i;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }
}
