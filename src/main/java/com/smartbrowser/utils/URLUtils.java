package com.smartbrowser.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * URL 解析与验证工具类
 */
public class URLUtils {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    );

    /**
     * 验证 URL 格式
     */
    public static boolean isValidURL(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return URL_PATTERN.matcher(url).matches();
    }

    /**
     * 智能解析输入（区分 URL 和搜索关键词）
     */
    public static String parseInput(String input) {
        if (input == null) return "";
        input = input.trim();
        
        if (isValidURL(input)) {
            return input;
        }
        
        // 尝试自动添加前缀
        if (input.contains(".") && !input.contains(" ")) {
            String urlWithPrefix = addHttpPrefix(input);
            if (isValidURL(urlWithPrefix)) {
                return urlWithPrefix;
            }
        }
        
        // 否则视为搜索词
        return buildSearchURL(input, "baidu");
    }

    /**
     * 自动添加 http:// 前缀
     */
    public static String addHttpPrefix(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://") && 
            !url.startsWith("ftp://") && !url.startsWith("file://")) {
            return "http://" + url;
        }
        return url;
    }

    /**
     * 提取域名
     */
    public static String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain != null ? domain : "";
        } catch (URISyntaxException e) {
            return "";
        }
    }

    /**
     * 构建搜索 URL
     */
    public static String buildSearchURL(String query, String searchEngine) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        if ("google".equalsIgnoreCase(searchEngine)) {
            return "https://www.google.com/search?q=" + encodedQuery;
        } else if ("bing".equalsIgnoreCase(searchEngine)) {
            return "https://www.bing.com/search?q=" + encodedQuery;
        } else {
            // 默认百度
            return "https://www.baidu.com/s?wd=" + encodedQuery;
        }
    }

    /**
     * 标准化 URL
     */
    public static String normalizeURL(String url) {
        if (url == null) return "";
        url = url.trim();
        if (url.isEmpty()) return "";
        return addHttpPrefix(url);
    }
}
