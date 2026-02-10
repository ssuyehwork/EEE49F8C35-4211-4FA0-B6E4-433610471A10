package com.smartbrowser.utils;

import org.slf4j.LoggerFactory;

/**
 * 日志工具类，封装 SLF4J 功能
 */
public class Logger {
    private static org.slf4j.Logger getLogger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // Index 3 is the caller of the Logger methods
        String className = stackTrace[3].getClassName();
        return LoggerFactory.getLogger(className);
    }

    public static void info(String format, Object... args) {
        getLogger().info(format, args);
    }

    public static void debug(String format, Object... args) {
        getLogger().debug(format, args);
    }

    public static void warn(String format, Object... args) {
        getLogger().warn(format, args);
    }

    public static void error(String format, Object... args) {
        getLogger().error(format, args);
    }

    public static void error(String message, Throwable t) {
        getLogger().error(message, t);
    }
}
