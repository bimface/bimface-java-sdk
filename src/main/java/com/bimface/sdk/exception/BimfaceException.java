package com.bimface.sdk.exception;

/**
 * bimface统一返回的异常
 * 
 * @author bimface, 2016-06-01.
 */
public class BimfaceException extends Exception {

    private static final long serialVersionUID = 2192833742497163711L;

    private Integer           httpCode;
    private String            errorCode;

    public BimfaceException(Exception e) {
        super(e);
    }

    public BimfaceException(String message) {
        super(message);
    }

    public BimfaceException(String message, Integer httpCode) {
        this(message);
        this.httpCode = httpCode;
    }

    public BimfaceException(String message, String errorCode) {
        this(message);
        this.errorCode = errorCode;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
