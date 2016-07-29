package com.bimface.sdk.service;

import com.bimface.sdk.config.authorization.Credential;
import com.bimface.sdk.utils.AssertUtils;
import com.bimface.sdk.utils.MD5Util;

/**
 * 验证签名
 * 
 * @author bimface, 2016-06-01.
 */
public class SignatureService {

    private Credential credential;

    public SignatureService(Credential credential) {
        this.credential = credential;
    }

    /**
     * 校验签名方法,针对于回调函数验证是否为bimface发起
     * 
     * @param signature 回调时带的签名
     * @param viewId 转换时返回的预览ID
     * @param status 转换状态(success || failed)
     * @return true: 验证成功, false: 校验失败
     */
    public boolean validate(String signature, String viewId, String status) {

        AssertUtils.assertStringNotNullOrEmpty(signature, "signature");
        AssertUtils.assertStringNotNullOrEmpty(viewId, "viewId");
        AssertUtils.assertStringNotNullOrEmpty(status, "status");

        // 回调签名 MD5(appKey:appSecret:transferId:status)
        StringBuffer sb = new StringBuffer(credential.getAppKey()).append(":").append(credential.getAppSecret()).append(":").append(viewId).append(":").append(status);
        return signature.equalsIgnoreCase(MD5Util.MD5(sb.toString()));
    }
}
