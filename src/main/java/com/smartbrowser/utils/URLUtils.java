package com.smartbrowser.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * URL \u89e3\u6790\u4e0e\u9a8c\u8bc1\u5de5\u5177\u7c7b
 */
public class URLUtils {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    );

    /**
     * \u9a8c\u8bc1 URL \u683c\u5f0f
     */
    public static boolean isValidURL(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return URL_PATTERN.matcher(url).matches();
    }

    /**
     * \u667a\u80fd\u89e3\u6790\u8f93\u5165\uff08\u533a\u5206 URL \u548c\u641c\u7d22\u5173\u952e\u8bcd\uff09
     */
    public static String parseInput(String input) {
        if (input == null) return "";
        input = input.trim();
        
        if (isValidURL(input)) {
            return input;
        }
        
        // \u5c1d\u8bd5\u81ea\u52a8\u6dfb\u52a0\u524d\u7f00
        if (input.contains(".") && !input.contains(" ")) {
            String urlWithPrefix = addHttpPrefix(input);
            if (isValidURL(urlWithPrefix)) {
                return urlWithPrefix;
            }
        }
        
        // \u5426\u5219\u89c6\u4e3a\u641c\u7d22\u8bcd
        return buildSearchURL(input, "google");
    }

    /**
     * \u81ea\u52a8\u6dfb\u52a0 http:// \u524d\u7f00
     */
    public static String addHttpPrefix(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://") && 
            !url.startsWith("ftp://") && !url.startsWith("file://")) {
            return "http://" + url;
        }
        return url;
    }

    /**
     * \u63d0\u53d6\u57df\u540d
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
     * \u6784\u5efa\u641c\u7d22 URL
     */
    public static String buildSearchURL(String query, String searchEngine) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        if ("google".equalsIgnoreCase(searchEngine)) {
            return "https://www.google.com/search?q=" + encodedQuery;
        } else if ("bing".equalsIgnoreCase(searchEngine)) {
            return "https://www.bing.com/search?q=" + encodedQuery;
        } else {
            // \u9ed8\u8ba4 Google
            return "https://www.google.com/search?q=" + encodedQuery;
        }
    }

    /**
     * \u6807\u51c6\u5316 URL
     */
    public static String normalizeURL(String url) {
        if (url == null) return "";
        url = url.trim();
        if (url.isEmpty()) return "";
        return addHttpPrefix(url);
    }
}
