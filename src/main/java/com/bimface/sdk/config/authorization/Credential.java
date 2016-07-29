package com.bimface.sdk.config.authorization;

/**
 * APP证书
 * 
 * @author bimface, 2016-06-01.
 */
public final class Credential {

    private String appKey;
    private String appSecret;

    public Credential(String appKey, String appSecret) {
        check(appKey, appSecret);
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    private void check(String appKey, String appSecret) {
        if (appKey == null || appKey.equals("")) {
            throw new IllegalArgumentException("appKey should not be null or empty.");
        }
        if (appSecret == null || appSecret.equals("")) {
            throw new IllegalArgumentException("appSecret should not be null or empty.");
        }
    }
}
