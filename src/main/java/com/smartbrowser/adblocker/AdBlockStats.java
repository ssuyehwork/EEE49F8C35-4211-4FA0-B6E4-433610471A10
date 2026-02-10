package com.smartbrowser.adblocker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class AdBlockStats {
    private final AtomicLong totalBlocked = new AtomicLong(0);
    private final AtomicLong sessionBlocked = new AtomicLong(0);
    private final Map<String, Integer> blockedByDomain = new HashMap<>();

    /**
     * 增加拦截计数
     */
    public void incrementBlockCount() {
        totalBlocked.incrementAndGet();
        sessionBlocked.incrementAndGet();
    }

    /**
     * 按域名增加拦截计数
     */
    public void incrementBlockCount(String domain) {
        incrementBlockCount();

        synchronized (blockedByDomain) {
            blockedByDomain.put(domain,
                blockedByDomain.getOrDefault(domain, 0) + 1);
        }
    }

    /**
     * 获取总拦截数
     */
    public long getTotalBlocked() {
        return totalBlocked.get();
    }

    /**
     * 获取本次会话拦截数
     */
    public long getSessionBlocked() {
        return sessionBlocked.get();
    }

    /**
     * 获取指定域名的拦截数
     */
    public int getBlockCountForDomain(String domain) {
        synchronized (blockedByDomain) {
            return blockedByDomain.getOrDefault(domain, 0);
        }
    }

    /**
     * 重置统计
     */
    public void reset() {
        sessionBlocked.set(0);
        synchronized (blockedByDomain) {
            blockedByDomain.clear();
        }
    }
}
