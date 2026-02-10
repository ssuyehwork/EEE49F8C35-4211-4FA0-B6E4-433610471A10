package com.smartbrowser.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * \u6587\u4ef6\u64cd\u4f5c\u5de5\u5177\u7c7b
 */
public class FileUtils {

    /**
     * \u786e\u4fdd\u76ee\u5f55\u5b58\u5728
     */
    public static void ensureDirectoryExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * \u8bfb\u53d6\u6587\u672c\u6587\u4ef6
     */
    public static String readTextFile(String path) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
    }

    /**
     * \u5199\u5165\u6587\u672c\u6587\u4ef6
     */
    public static void writeTextFile(String path, String content) throws IOException {
        org.apache.commons.io.FileUtils.writeStringToFile(new File(path), content, StandardCharsets.UTF_8);
    }

    /**
     * \u590d\u5236\u6587\u4ef6
     */
    public static void copyFile(String source, String dest) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(new File(source), new File(dest));
    }

    /**
     * \u5220\u9664\u6587\u4ef6
     */
    public static boolean deleteFile(String path) {
        return new File(path).delete();
    }

    /**
     * \u83b7\u53d6\u7528\u6237\u6570\u636e\u76ee\u5f55\u8def\u5f84
     */
    public static String getUserDataDir() {
        return "data" + File.separator;
    }
}
