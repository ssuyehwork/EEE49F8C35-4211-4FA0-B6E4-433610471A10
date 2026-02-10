package com.smartbrowser.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * JSON 处理工具类
 */
public class JsonUtils {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * 对象转 JSON 字符串
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * JSON 转对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * JSON 转对象 (支持泛型)
     */
    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * 保存为 JSON 文件
     */
    public static void toJsonFile(Object obj, String filepath) throws IOException {
        FileUtils.writeTextFile(filepath, toJson(obj));
    }

    /**
     * 从 JSON 文件读取
     */
    public static <T> T fromJsonFile(String filepath, Class<T> type) throws IOException {
        String json = FileUtils.readTextFile(filepath);
        return fromJson(json, type);
    }
}
