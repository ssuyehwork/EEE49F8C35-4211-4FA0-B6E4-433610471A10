package com.smartbrowser.adblocker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class WhitelistManager {
    private final Set<String> whitelist = new HashSet<>();
    
    /**
     * 添加域名到白名单
     */
    public void add(String domain) {
        if (domain != null && !domain.isEmpty()) {
            whitelist.add(domain.toLowerCase());
        }
    }
    
    /**
     * 从白名单移除域名
     */
    public void remove(String domain) {
        if (domain != null) {
            whitelist.remove(domain.toLowerCase());
        }
    }
    
    /**
     * 检查域名是否在白名单
     */
    public boolean isWhitelisted(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        String lowerUrl = url.toLowerCase();
        
        for (String domain : whitelist) {
            if (lowerUrl.contains(domain)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 获取所有白名单域名
     */
    public List<String> getAll() {
        return new ArrayList<>(whitelist);
    }
    
    /**
     * 清空白名单
     */
    public void clear() {
        whitelist.clear();
    }
}
