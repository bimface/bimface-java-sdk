package com.bimface.sdk.utils;

/**
 * 文件名称工具类
 *
 * @author singo, 2016-08-27
 */
public class FileNameUtils {

    private final static String[] ILLEGAL_CHAR = new String[] { "/", "\n", "*", "\\", "<", ">", "|", "\"", ":", "?" };

    /**
     * 检查文件名称
     *
     * @param name 文件名
     */
    public static void checkFileName(String name) {
        if (name == null || name.length() <= 0) {
            throw new IllegalArgumentException("File name must not be empty.");
        }
        if (name.length() > 256) {
            throw new IllegalArgumentException("File name too long, no more than 256 characters.");
        }
        String suffix = getSuffix(name);
        if (suffix == null || suffix.length() <= 0) {
            throw new IllegalArgumentException("File name has no suffix.");
        }
        if (containsIllegalChar(name)) {
            throw new IllegalArgumentException("File name contains illegal character.");
        }
    }

    /**
     * 检查后缀名是否支持
     *
     * @param allSupportedType 支持的文件格式
     * @param name 文件名
     */
    public static void checkFileType(String[] allSupportedType, String name) {
        String suffix = getSuffix(name);
        for (String s : allSupportedType) {
            if (s.equalsIgnoreCase(suffix)) {
                return;
            }
        }
        throw new IllegalArgumentException("File type not supported.");
    }

    /**
     * 获取文件后缀名
     *
     * @param name 文件名
     * @return 文件后缀名
     */
    private static String getSuffix(String name) {
        if (name.indexOf(".") == -1) {
            return null;
        }
        String suffix = null;
        if (name != null && name.length() > 0) {
            suffix = name.substring(name.lastIndexOf(".") + 1, name.length());
        }
        return suffix;
    }

    /**
     * 判断是否包含非法字符
     *
     * @param value 值
     * @return 是否包含非法字符
     */
    private static boolean containsIllegalChar(String value) {
        for (String x : ILLEGAL_CHAR) {
            if (value.contains(x)) {
                return true;
            }
        }
        return false;
    }
}
