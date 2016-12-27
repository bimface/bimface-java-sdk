package com.bimface.sdk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.bimface.sdk.constants.BimfaceConstants;

/**
 * 参数校验
 * 
 * @author bimface, 2016-06-01.
 */
public class AssertUtils {

    public static void assertParameterNotNull(Object param, String paramName) {
        if (param == null) {
            throw new NullPointerException("ParameterIsNull " + paramName);
        }
    }

    public static void assertParameterInRange(long param, long lower, long upper) {
        if (!checkParamRange(param, lower, true, upper, true)) {
            throw new IllegalArgumentException(String.format("%d not in valid range [%d, %d]", param, lower, upper));
        }
    }

    public static void assertStringNotNullOrEmpty(String param, String paramName) {
        assertParameterNotNull(param, paramName);
        if (param.trim().length() == 0) {
            throw new IllegalArgumentException("ParameterStringIsEmpty " + paramName);
        }
    }

    public static void assertListNotNullOrEmpty(List<?> param, String paramName) {
        assertParameterNotNull(param, paramName);
        if (param.size() == 0) {
            throw new IllegalArgumentException("ParameterListIsEmpty" + paramName);
        }
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean checkParamRange(long param, long from, boolean leftInclusive, long to,
                                          boolean rightInclusive) {

        if (leftInclusive && rightInclusive) { // [from, to]
            if (from <= param && param <= to) {
                return true;
            } else {
                return false;
            }
        } else if (leftInclusive && !rightInclusive) { // [from, to)
            if (from <= param && param < to) {
                return true;
            } else {
                return false;
            }
        } else if (!leftInclusive && !rightInclusive) { // (from, to)
            if (from < param && param < to) {
                return true;
            } else {
                return false;
            }
        } else { // (from, to]
            if (from < param && param <= to) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void checkFileLength(Long maxLength, Long length) {
        if (length == null || length <= 0) {
            throw new IllegalArgumentException("file length is illeagal:" + length);
        }

        if (length > maxLength) {
            throw new IllegalArgumentException("file length is larger:" + length + "than supported length :"
                                               + maxLength);
        }
    }

    public static void checkUrl(String url) {
        assertStringNotNullOrEmpty(url, "url");
        try {
            url = URLDecoder.decode(url, BimfaceConstants.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("Url must starts with http(s)://.");
        }
    }
}
