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
     * @param id 可能是fileId或integrateId
     * @param status 转换状态(success || failed)
     * @param nonce 随机数
     * @return true: 验证成功, false: 校验失败
     */
    public boolean validate(String signature, String id, String status, String nonce) {

        AssertUtils.assertStringNotNullOrEmpty(signature, "signature");
        AssertUtils.assertStringNotNullOrEmpty(id, "id");
        AssertUtils.assertStringNotNullOrEmpty(status, "status");
        AssertUtils.assertStringNotNullOrEmpty(nonce, "nonce");

        // 回调签名 MD5(appKey:appSecret:id:status:nonce)
        StringBuffer sb = new StringBuffer(credential.getAppKey()).append(":").append(credential.getAppSecret()).append(":").append(id).append(":").append(status).append(":").append(nonce);
        return signature.equalsIgnoreCase(MD5Util.MD5(sb.toString()));
    }
}