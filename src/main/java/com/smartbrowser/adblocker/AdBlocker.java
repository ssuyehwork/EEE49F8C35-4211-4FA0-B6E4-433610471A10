package com.smartbrowser.adblocker;

import com.smartbrowser.utils.Logger;
import java.util.List;

/**
 * 广告拦截器 - 单例模式
 * 统一管理规则引擎、白名单和统计
 */
public class AdBlocker {
    private static AdBlocker instance;

    private final RuleEngine ruleEngine;
    private final WhitelistManager whitelistManager;
    private final AdBlockStats stats;
    private boolean enabled = true;

    private AdBlocker() {
        this.ruleEngine = new RuleEngine();
        this.whitelistManager = new WhitelistManager();
        this.stats = new AdBlockStats();

        loadRules();
    }

    public static synchronized AdBlocker getInstance() {
        if (instance == null) {
            instance = new AdBlocker();
        }
        return instance;
    }

    /**
     * 加载拦截规则
     */
    private void loadRules() {
        Logger.info("开始加载广告拦截规则...");

        RuleLoader loader = new RuleLoader();

        // 加载EasyList规则
        List<FilterRule> easylist = loader.loadFromFile("/adblock/easylist.txt");
        ruleEngine.addRules(easylist);

        // 加载隐私保护规则
        List<FilterRule> easyprivacy = loader.loadFromFile("/adblock/easyprivacy.txt");
        ruleEngine.addRules(easyprivacy);

        // 加载中文规则
        List<FilterRule> chinese = loader.loadFromFile("/adblock/chinese-filters.txt");
        ruleEngine.addRules(chinese);

        Logger.info("广告拦截规则加载完成，总规则数: {}", ruleEngine.getRuleCount());
    }

    /**
     * 判断URL是否应被拦截
     *
     * @param url 要检查的URL
     * @param pageUrl 当前页面URL
     * @return true=拦截，false=放行
     */
    public boolean shouldBlock(String url, String pageUrl) {
        if (!enabled) {
            return false;
        }

        // 检查白名单
        if (whitelistManager.isWhitelisted(pageUrl)) {
            return false;
        }

        // 检查拦截规则
        boolean blocked = ruleEngine.shouldBlock(url, pageUrl);

        if (blocked) {
            stats.incrementBlockCount();
        }

        return blocked;
    }

    /**
     * 获取指定域名的CSS隐藏规则
     */
    public List<String> getCSSRules(String domain) {
        if (!enabled) {
            return List.of();
        }

        return ruleEngine.getCSSSelectors(domain);
    }

    /**
     * 添加域名到白名单
     */
    public void addToWhitelist(String domain) {
        whitelistManager.add(domain);
    }

    /**
     * 从白名单移除域名
     */
    public void removeFromWhitelist(String domain) {
        whitelistManager.remove(domain);
    }

    /**
     * 获取拦截统计
     */
    public AdBlockStats getStats() {
        return stats;
    }

    /**
     * 启用/禁用广告拦截
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        Logger.info("广告拦截已{}",  enabled ? "启用" : "禁用");
    }

    /**
     * 检查是否已启用
     */
    public boolean isEnabled() {
        return enabled;
    }
}
