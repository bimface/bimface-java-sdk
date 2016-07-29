package com.bimface.sdk.bean;

/**
 * 统一的返回类
 * 
 * @author bimface, 2016-06-01.
 */
public class GeneralResponse<T> {

    public static final String CODE_SUCCESS = "success";            // 成功的业务编码
    public static final String DATE_FORMAT  = "yyyy-MM-dd HH:mm:ss";// 统一的日期格式

    private String             code         = CODE_SUCCESS;         // 返回的业务编码，默认成功
    private String             message;                             // 失败的错误原因
    private T                  data;                                // 执行成功后的返回结果

    public GeneralResponse() {
    }

    public GeneralResponse(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GeneralResponse [code=" + code + ", message=" + message + ", data=" + data + "]";
    }
}
