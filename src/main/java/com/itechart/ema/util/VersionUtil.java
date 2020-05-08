package com.itechart.ema.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringBootVersion;

@UtilityClass
public class VersionUtil {

    public static String appVersion() {
        return StringUtils.firstNonEmpty(System.getenv("APP_VERSION"), "unknown");
    }

    public static String springBootVersion() {
        return StringUtils.firstNonEmpty(SpringBootVersion.getVersion(), "unknown");
    }

    public static String javaVersion() {
        return StringUtils.firstNonEmpty(System.getProperty("java.runtime.version"), System.getProperty("java.vm.version"), "unknown");
    }

    public static String javaVendor() {
        return StringUtils.firstNonEmpty(System.getProperty("java.vendor"), System.getProperty("java.vm.vendor"), "unknown");
    }

}
