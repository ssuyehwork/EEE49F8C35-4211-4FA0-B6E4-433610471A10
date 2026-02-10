package com.smartbrowser.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 文件操作工具类
 */
public class FileUtils {

    /**
     * 确保目录存在
     */
    public static void ensureDirectoryExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 读取文本文件
     */
    public static String readTextFile(String path) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
    }

    /**
     * 写入文本文件
     */
    public static void writeTextFile(String path, String content) throws IOException {
        org.apache.commons.io.FileUtils.writeStringToFile(new File(path), content, StandardCharsets.UTF_8);
    }

    /**
     * 复制文件
     */
    public static void copyFile(String source, String dest) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(new File(source), new File(dest));
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String path) {
        return new File(path).delete();
    }

    /**
     * 获取用户数据目录路径
     */
    public static String getUserDataDir() {
        return "data" + File.separator;
    }
}
