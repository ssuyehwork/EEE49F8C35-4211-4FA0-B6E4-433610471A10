package com.smartbrowser.adblocker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FilterRule {
    public enum RuleType {
        BLOCK,      // 拦截规则
        ALLOW,      // 白名单规则（@@开头）
        HIDE        // CSS隐藏规则（##开头）
    }
    
    private String rawRule;           // 原始规则文本
    private RuleType type;            // 规则类型
    private Pattern urlPattern;       // URL匹配模式
    private String cssSelector;       // CSS选择器（HIDE类型）
    private List<String> domains;     // 适用域名
    private List<String> excludeDomains; // 排除域名
    
    // 构造函数
    public FilterRule(String rawRule, RuleType type) {
        this.rawRule = rawRule;
        this.type = type;
        this.domains = new ArrayList<>();
        this.excludeDomains = new ArrayList<>();
    }
    
    /**
     * 解析规则文本，返回FilterRule对象
     * 
     * 规则格式示例：
     * - "||doubleclick.net^" → 拦截doubleclick.net域名
     * - "@@||google.com^" → 白名单，允许google.com
     * - "##.ad-banner" → 隐藏所有.ad-banner元素
     * - "example.com##div[id^='ad-']" → 在example.com隐藏特定元素
     */
    public static FilterRule parse(String ruleText) {
        if (ruleText == null || ruleText.trim().isEmpty()) {
            return null;
        }
        
        ruleText = ruleText.trim();
        
        // 1. 白名单规则（@@开头）
        if (ruleText.startsWith("@@")) {
            FilterRule rule = new FilterRule(ruleText, RuleType.ALLOW);
            String pattern = ruleText.substring(2); // 去掉@@
            rule.urlPattern = convertToPattern(pattern);
            return rule;
        }
        
        // 2. CSS隐藏规则（包含##）
        if (ruleText.contains("##")) {
            FilterRule rule = new FilterRule(ruleText, RuleType.HIDE);
            String[] parts = ruleText.split("##", 2);
            
            if (parts.length == 2) {
                // 有域名限制：example.com##.ad
                if (!parts[0].isEmpty()) {
                    rule.domains.add(parts[0]);
                }
                rule.cssSelector = parts[1];
            } else {
                // 全局规则：##.ad
                rule.cssSelector = parts[0];
            }
            return rule;
        }
        
        // 3. 普通拦截规则
        FilterRule rule = new FilterRule(ruleText, RuleType.BLOCK);
        rule.urlPattern = convertToPattern(ruleText);
        return rule;
    }
    
    /**
     * 将EasyList规则转换为Java正则表达式
     */
    private static Pattern convertToPattern(String rule) {
        // 处理特殊字符
        String regex = rule
            .replace(".", "\\.")      // . → \.
            .replace("?", "\\?")      // ? → \?
            .replace("*", ".*")       // * → .*
            .replace("^", "[/?&]")    // ^ → [/?&] (分隔符)
            .replace("||", "^https?://([^/]*\\.)?"); // || → 域名开头
        
        try {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            // 解析失败，返回永不匹配的模式
            return Pattern.compile("(?!)");
        }
    }
    
    /**
     * 检查URL是否匹配此规则
     */
    public boolean matches(String url, String pageUrl) {
        if (type == RuleType.HIDE) {
            return false; // CSS规则不用于URL匹配
        }
        
        if (urlPattern == null) {
            return false;
        }
        
        return urlPattern.matcher(url).find();
    }
    
    /**
     * 检查规则是否适用于指定域名
     */
    public boolean appliesToDomain(String domain) {
        if (domains.isEmpty()) {
            return true; // 无域名限制，适用所有
        }
        
        for (String d : domains) {
            if (domain.contains(d) || d.contains(domain)) {
                return true;
            }
        }
        return false;
    }
    
    // Getters
    public RuleType getType() { return type; }
    public String getCssSelector() { return cssSelector; }
    public String getRawRule() { return rawRule; }
}
