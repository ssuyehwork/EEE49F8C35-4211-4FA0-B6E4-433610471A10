package com.smartbrowser.adblocker;

import com.smartbrowser.utils.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuleEngine {
    private final List<FilterRule> blockRules = new ArrayList<>();
    private final List<FilterRule> allowRules = new ArrayList<>();
    private final List<FilterRule> hideRules = new ArrayList<>();

    // 缓存：域名 -> CSS选择器列表
    private final Map<String, List<String>> cssCacheMap = new HashMap<>();

    /**
     * 添加单条规则
     */
    public void addRule(FilterRule rule) {
        if (rule == null) return;

        switch (rule.getType()) {
            case BLOCK:
                blockRules.add(rule);
                break;
            case ALLOW:
                allowRules.add(rule);
                break;
            case HIDE:
                hideRules.add(rule);
                cssCacheMap.clear(); // 清空缓存
                break;
        }
    }

    /**
     * 批量添加规则
     */
    public void addRules(List<FilterRule> rules) {
        for (FilterRule rule : rules) {
            addRule(rule);
        }
        Logger.info("规则加载完成 - 拦截: {}, 白名单: {}, CSS隐藏: {}",
                    blockRules.size(), allowRules.size(), hideRules.size());
    }

    /**
     * 判断URL是否应被拦截
     *
     * @param url 要检查的URL
     * @param pageUrl 当前页面URL
     * @return true=拦截，false=放行
     */
    public boolean shouldBlock(String url, String pageUrl) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        // 1. 先检查白名单（优先级最高）
        for (FilterRule rule : allowRules) {
            if (rule.matches(url, pageUrl)) {
                return false; // 白名单放行
            }
        }

        // 2. 检查拦截规则
        for (FilterRule rule : blockRules) {
            if (rule.matches(url, pageUrl)) {
                return true; // 匹配拦截规则
            }
        }

        return false; // 未匹配任何规则，放行
    }

    /**
     * 获取指定域名的CSS隐藏选择器
     */
    public List<String> getCSSSelectors(String domain) {
        if (domain == null || domain.isEmpty()) {
            return new ArrayList<>();
        }

        // 检查缓存
        if (cssCacheMap.containsKey(domain)) {
            return cssCacheMap.get(domain);
        }

        // 收集适用的CSS选择器
        List<String> selectors = hideRules.stream()
            .filter(rule -> rule.appliesToDomain(domain))
            .map(FilterRule::getCssSelector)
            .filter(s -> s != null && !s.isEmpty())
            .collect(Collectors.toList());

        // 缓存结果
        cssCacheMap.put(domain, selectors);

        return selectors;
    }

    /**
     * 清空所有规则
     */
    public void clear() {
        blockRules.clear();
        allowRules.clear();
        hideRules.clear();
        cssCacheMap.clear();
    }

    /**
     * 获取规则总数
     */
    public int getRuleCount() {
        return blockRules.size() + allowRules.size() + hideRules.size();
    }
}
