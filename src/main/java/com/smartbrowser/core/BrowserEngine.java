package com.smartbrowser.core;

import com.smartbrowser.utils.Logger;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.ArrayList;
import java.util.List;

/**
 * 浏览器引擎封装类，管理 WebView 和 WebEngine
 */
public class BrowserEngine {
    private final WebView webView;
    private final WebEngine webEngine;

    // 事件监听器列表
    private final List<OnLoadProgressListener> progressListeners = new ArrayList<>();
    private final List<OnURLChangeListener> urlListeners = new ArrayList<>();
    private final List<OnTitleChangeListener> titleListeners = new ArrayList<>();

    public BrowserEngine() {
        this.webView = new WebView();
        this.webEngine = webView.getEngine();
        // 设置默认编码为 UTF-8
        this.webEngine.setUserStyleSheetLocation(null);
        this.webEngine.setUserDataDirectory(new java.io.File(System.getProperty("user.home"), ".smartbrowser/webview"));
        initListeners();
    }

    private void initListeners() {
        // 监听加载进度
        webEngine.getLoadWorker().progressProperty().addListener((obs, old, progress) -> {
            double p = progress.doubleValue();
            for (OnLoadProgressListener listener : progressListeners) {
                listener.onChanged(p);
            }
        });

        // 监听加载状态
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Logger.info("页面加载成功: " + webEngine.getLocation());
                // 注入 CSS 修复潜在的字体渲染/编码乱码视觉问题
                executeScript("if (!document.getElementById('sb-fix')) {" +
                        "var style = document.createElement('style');" +
                        "style.id = 'sb-fix';" +
                        "style.innerHTML = 'body, input, button, select, textarea { font-family: \"Segoe UI\", \"Tahoma\", \"Microsoft YaHei\", \"PingFang SC\", \"Hiragino Sans GB\", \"Arial\", sans-serif !important; }';" +
                        "document.head.appendChild(style);" +
                        "}");
            } else if (newState == Worker.State.FAILED) {
                Logger.error("页面加载失败: " + webEngine.getLocation());
            }
        });

        // 监听 URL 变化
        webEngine.locationProperty().addListener((obs, oldUrl, newUrl) -> {
            for (OnURLChangeListener listener : urlListeners) {
                listener.onChanged(newUrl);
            }
        });

        // 监听标题变化
        webEngine.titleProperty().addListener((obs, oldTitle, newTitle) -> {
            for (OnTitleChangeListener listener : titleListeners) {
                listener.onChanged(newTitle);
            }
        });

        // JavaScript 错误处理 (JavaFX WebView 默认支持较弱，这里记录日志)
        webEngine.setOnError(event -> {
            Logger.error("WebEngine 错误: " + event.getMessage());
        });
    }

    public void navigate(String url) {
        // 这里后续可以集成广告拦截拦截逻辑
        Logger.info("导航到: " + url);
        webEngine.load(url);
    }

    public void back() {
        if (canGoBack()) {
            webEngine.getHistory().go(-1);
        }
    }

    public void forward() {
        if (canGoForward()) {
            webEngine.getHistory().go(1);
        }
    }

    public void refresh() {
        webEngine.reload();
    }

    public void stop() {
        webEngine.getLoadWorker().cancel();
    }

    public String getURL() {
        return webEngine.getLocation();
    }

    public String getTitle() {
        return webEngine.getTitle();
    }

    public boolean canGoBack() {
        return webEngine.getHistory().getCurrentIndex() > 0;
    }

    public boolean canGoForward() {
        return webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1;
    }

    public Object executeScript(String script) {
        try {
            return webEngine.executeScript(script);
        } catch (Exception e) {
            Logger.error("执行脚本失败: " + script, e);
            return null;
        }
    }

    public void setUserAgent(String ua) {
        webEngine.setUserAgent(ua);
    }

    public WebView getWebView() {
        return webView;
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    // 接口定义
    public interface OnLoadProgressListener { void onChanged(double progress); }
    public interface OnURLChangeListener { void onChanged(String url); }
    public interface OnTitleChangeListener { void onChanged(String title); }

    public void addProgressListener(OnLoadProgressListener l) { progressListeners.add(l); }
    public void addURLListener(OnURLChangeListener l) { urlListeners.add(l); }
    public void addTitleListener(OnTitleChangeListener l) { titleListeners.add(l); }
}
