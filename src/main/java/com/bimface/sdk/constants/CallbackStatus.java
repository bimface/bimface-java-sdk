package com.bimface.sdk.constants;

/**
 * 枚举：回调状态
 * 
 * @author bimface, 2016-06-01.
 */
public enum CallbackStatus {

    /**
     * 成功
     */
    SUCCESS("success"),

    /**
     * 失败
     */
    FAILED("failed");

    private String value;

    private CallbackStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CallbackStatus parse(String value) {
        for (CallbackStatus e : CallbackStatus.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
