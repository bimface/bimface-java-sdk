package com.bimface.sdk.constants;

/**
 * 枚举：转换状态
 * 
 * @author bimface, 2016-06-01.
 */
public enum TransferStatus {

    /**
     * 准备中
     */
    PREPARE("prepare"),

    /**
     * 转换中
     */
    PROCESSING("processing"),

    /**
     * 转换成功
     */
    SUCCESS("success"),

    /**
     * 转换失败
     */
    FAILED("failed");

    private String value;

    private TransferStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransferStatus parse(String value) {
        for (TransferStatus e : TransferStatus.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
