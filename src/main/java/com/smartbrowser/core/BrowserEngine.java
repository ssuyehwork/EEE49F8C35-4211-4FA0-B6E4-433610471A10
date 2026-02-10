package com.smartbrowser.core;

import com.smartbrowser.utils.Logger;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.ArrayList;
import java.util.List;

/**
 * \u6d4f\u89c8\u5668\u5f15\u64ce\u5c01\u88c5\u7c7b\uff0c\u7ba1\u7406 WebView \u548c WebEngine
 */
public class BrowserEngine {
    private final WebView webView;
    private final WebEngine webEngine;

    // \u4e8b\u4ef6\u76d1\u542c\u5668\u5217\u8868
    private final List<OnLoadProgressListener> progressListeners = new ArrayList<>();
    private final List<OnURLChangeListener> urlListeners = new ArrayList<>();
    private final List<OnTitleChangeListener> titleListeners = new ArrayList<>();

    public BrowserEngine() {
        this.webView = new WebView();
        this.webEngine = webView.getEngine();

        // \u5f3a\u5236\u7f16\u7801\u548c\u5b57\u4f53\u4ee5\u4fee\u590d\u4e71\u7801
        try {
            String css = "body, input, button, select, textarea { " +
                        "font-family: 'Segoe UI', 'Tahoma', 'Microsoft YaHei', 'PingFang SC', 'Hiragino Sans GB', 'Arial', sans-serif !important; " +
                        "}";
            String base64Css = java.util.Base64.getEncoder().encodeToString(css.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            this.webEngine.setUserStyleSheetLocation("data:text/css;base64," + base64Css);
        } catch (Exception e) {
            Logger.error("\u8bbe\u7f6e\u9ed8\u8ba4\u6837\u5f0f\u8868\u5931\u8d25", e);
        }

        this.webEngine.setUserDataDirectory(new java.io.File(System.getProperty("user.home"), ".smartbrowser/webview"));
        initListeners();
    }

    private void initListeners() {
        // \u76d1\u542c\u52a0\u8f7d\u8fdb\u5ea6
        webEngine.getLoadWorker().progressProperty().addListener((obs, old, progress) -> {
            double p = progress.doubleValue();
            for (OnLoadProgressListener listener : progressListeners) {
                listener.onChanged(p);
            }
        });

        // \u76d1\u542c\u52a0\u8f7d\u72b6\u6001
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Logger.info("\u9875\u9762\u52a0\u8f7d\u6210\u529f: " + webEngine.getLocation());
                // \u518d\u6b21\u6ce8\u5165\u811a\u672c\u4ee5\u786e\u4fdd\u5b57\u4f53\u5e94\u7528
                executeScript("if (!document.getElementById('sb-fix')) {" +
                        "var style = document.createElement('style');" +
                        "style.id = 'sb-fix';" +
                        "style.innerHTML = 'body, input, button, select, textarea { font-family: \"Segoe UI\", \"Tahoma\", \"Microsoft YaHei\", \"PingFang SC\", \"Hiragino Sans GB\", \"Arial\", sans-serif !important; }';" +
                        "document.head.appendChild(style);" +
                        "}");
            } else if (newState == Worker.State.FAILED) {
                Logger.error("\u9875\u9762\u52a0\u8f7d\u5931\u8d25: " + webEngine.getLocation());
            }
        });

        // \u76d1\u542c URL \u53d8\u5316
        webEngine.locationProperty().addListener((obs, oldUrl, newUrl) -> {
            for (OnURLChangeListener listener : urlListeners) {
                listener.onChanged(newUrl);
            }
        });

        // \u76d1\u542c\u6807\u9898\u53d8\u5316
        webEngine.titleProperty().addListener((obs, oldTitle, newTitle) -> {
            for (OnTitleChangeListener listener : titleListeners) {
                listener.onChanged(newTitle);
            }
        });

        // JavaScript \u9519\u8bef\u5904\u7406 (JavaFX WebView \u9ed8\u8ba4\u652f\u6301\u8f83\u5f31\uff0c\u8fd9\u91cc\u8bb0\u5f55\u65e5\u5fd7)
        webEngine.setOnError(event -> {
            Logger.error("WebEngine \u9519\u8bef: " + event.getMessage());
        });
    }

    public void navigate(String url) {
        // \u8fd9\u91cc\u540e\u7eed\u53ef\u4ee5\u96c6\u6210\u5e7f\u544a\u62e6\u622a\u62e6\u622a\u903b\u8f91
        Logger.info("\u5bfc\u822a\u5230: " + url);
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
            Logger.error("\u6267\u884c\u811a\u672c\u5931\u8d25: " + script, e);
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

    // \u63a5\u53e3\u5b9a\u4e49
    public interface OnLoadProgressListener { void onChanged(double progress); }
    public interface OnURLChangeListener { void onChanged(String url); }
    public interface OnTitleChangeListener { void onChanged(String title); }

    public void addProgressListener(OnLoadProgressListener l) { progressListeners.add(l); }
    public void addURLListener(OnURLChangeListener l) { urlListeners.add(l); }
    public void addTitleListener(OnTitleChangeListener l) { titleListeners.add(l); }
}
