package com.smartbrowser.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * JSON \u5904\u7406\u5de5\u5177\u7c7b
 */
public class JsonUtils {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * \u5bf9\u8c61\u8f6c JSON \u5b57\u7b26\u4e32
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * JSON \u8f6c\u5bf9\u8c61
     */
    public static <T> T fromJson(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * JSON \u8f6c\u5bf9\u8c61 (\u652f\u6301\u6cdb\u578b)
     */
    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * \u4fdd\u5b58\u4e3a JSON \u6587\u4ef6
     */
    public static void toJsonFile(Object obj, String filepath) throws IOException {
        FileUtils.writeTextFile(filepath, toJson(obj));
    }

    /**
     * \u4ece JSON \u6587\u4ef6\u8bfb\u53d6
     */
    public static <T> T fromJsonFile(String filepath, Class<T> type) throws IOException {
        String json = FileUtils.readTextFile(filepath);
        return fromJson(json, type);
    }
}
