package com.bimface.sdk.utils;

import java.util.List;

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

    /**
     * 检查文件名
     * 
     * @param name 文件名
     */
    public static void checkFileName(String name) {

        assertStringNotNullOrEmpty(name, "file name must not be empty");

        if (name.length() > 256) {
            throw new IllegalArgumentException("file name too long, no more than 256 characters");
        }

        String[] string = new String[] { "/", "\n", "*", "\\", "<", ">", "|", "\"", ":", "?" };

        if (StringUtils.containsStringArray(name, string)) {
            throw new IllegalArgumentException("file name contains illegal character, '/', '\n', '*', '\\', '<', '>', '|', '\"', ':', '?'");
        }
    }

    /**
     * 检查文件上传后缀
     * 
     * @param allSupportedType bimface支持的所有文件后缀
     * @param suffix 文件后缀
     */
    public static void checkFileSuffix(List<String> allSupportedType, String suffix) {

        assertStringNotNullOrEmpty(suffix, "suffix must not be empty");

        if (!fileFormatsSupported(allSupportedType, suffix)) {
            throw new IllegalArgumentException("file type not supported (supported types:"
                                               + StringUtils.join(allSupportedType, ",") + ")");
        }
    }

    /**
     * 文件格式匹配
     * 
     * @param allSupportedType bimface支持的所有文件后缀
     * @param suffix 文件后缀
     * @return true：支持上传，false：不支持上传
     */
    public static boolean fileFormatsSupported(List<String> allSupportedType, String suffix) {
        for (String s : allSupportedType) {
            if (s.equalsIgnoreCase(suffix)) {
                return true;
            }
        }
        return false;
    }
}
