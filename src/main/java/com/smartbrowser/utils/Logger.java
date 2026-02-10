package com.smartbrowser.utils;

import org.slf4j.LoggerFactory;

/**
 * \u65e5\u5fd7\u5de5\u5177\u7c7b\uff0c\u5c01\u88c5 SLF4J \u529f\u80fd
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
