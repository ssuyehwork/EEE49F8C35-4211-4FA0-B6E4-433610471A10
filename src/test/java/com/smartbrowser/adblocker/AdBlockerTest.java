package com.smartbrowser.adblocker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdBlockerTest {
    
    @Test
    public void testBlockAds() {
        AdBlocker blocker = AdBlocker.getInstance();
        
        // 测试拦截广告域名
        assertTrue(blocker.shouldBlock(
            "https://doubleclick.net/ad.js", 
            "https://example.com"
        ));
        
        // 测试放行正常请求
        assertFalse(blocker.shouldBlock(
            "https://example.com/script.js", 
            "https://example.com"
        ));
    }
    
    @Test
    public void testCSSRules() {
        AdBlocker blocker = AdBlocker.getInstance();
        
        // 获取CSS隐藏规则
        var selectors = blocker.getCSSRules("example.com");
        
        assertNotNull(selectors);
        System.out.println("CSS Selectors: " + selectors);
    }
}
