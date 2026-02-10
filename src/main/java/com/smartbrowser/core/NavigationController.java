package com.smartbrowser.core;

import com.smartbrowser.utils.Logger;
import java.util.Stack;

/**
 * 导航控制器，管理前进/后退栈和全局导航逻辑
 */
public class NavigationController {
    private static NavigationController instance;
    
    private BrowserEngine currentEngine;
    private String homePage = "https://www.google.com";
    
    // 虽然 WebEngine 有历史记录，但按要求维护自定义栈
    private final Stack<String> backStack = new Stack<>();
    private final Stack<String> forwardStack = new Stack<>();

    private NavigationController() {}

    public static synchronized NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    public void setBrowserEngine(BrowserEngine engine) {
        this.currentEngine = engine;
    }

    public void navigateTo(String url) {
        if (currentEngine == null) return;
        
        String currentUrl = currentEngine.getURL();
        if (currentUrl != null && !currentUrl.isEmpty() && !currentUrl.equals("about:blank")) {
            backStack.push(currentUrl);
        }
        forwardStack.clear();
        
        currentEngine.navigate(url);
    }

    public void goBack() {
        if (canNavigateBack()) {
            String currentUrl = currentEngine.getURL();
            forwardStack.push(currentUrl);
            String prevUrl = backStack.pop();
            currentEngine.navigate(prevUrl);
        }
    }

    public void goForward() {
        if (canNavigateForward()) {
            String currentUrl = currentEngine.getURL();
            backStack.push(currentUrl);
            String nextUrl = forwardStack.pop();
            currentEngine.navigate(nextUrl);
        }
    }

    public void reload() {
        if (currentEngine != null) currentEngine.refresh();
    }

    public void stopLoading() {
        if (currentEngine != null) currentEngine.stop();
    }

    public void goHome() {
        navigateTo(homePage);
    }

    public boolean canNavigateBack() {
        return !backStack.isEmpty();
    }

    public boolean canNavigateForward() {
        return !forwardStack.isEmpty();
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getCurrentURL() {
        return currentEngine != null ? currentEngine.getURL() : "";
    }
}
