package com.bimface.sdk.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 版本工具
 * 
 * @author bimface, 2016-06-01.
 */
public class VersionInfoUtils {

    private static final String VERSION_INFO_FILE = "/META-INF/maven/com.bimface/bimface-java-sdk/pom.properties";
    private static final String USER_AGENT_PREFIX = "bimface-sdk-java";

    private static String       version           = null;

    private static String       defaultUserAgent  = null;

    public static String getVersion() {
        if (version == null) {
            initializeVersion();
        }
        return version;
    }

    public static String getDefaultUserAgent() {
        if (defaultUserAgent == null) {
            defaultUserAgent = USER_AGENT_PREFIX + "/" + getVersion() + "(" + System.getProperty("os.name") + "/"
                               + System.getProperty("os.version") + "/" + System.getProperty("os.arch") + ";"
                               + System.getProperty("java.version") + ")";
        }
        return defaultUserAgent;
    }

    private static void initializeVersion() {
        InputStream inputStream = VersionInfoUtils.class.getResourceAsStream(VERSION_INFO_FILE);
        Properties versionInfoProperties = new Properties();
        try {
            if (inputStream == null) {
                throw new IllegalArgumentException(VERSION_INFO_FILE + " not found on classpath");
            }
            versionInfoProperties.load(inputStream);
            version = versionInfoProperties.getProperty("version");
        } catch (Exception e) {
            version = "unknown-version";
        }
    }
}
