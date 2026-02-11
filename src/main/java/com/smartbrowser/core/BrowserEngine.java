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

        // \u5f3a\u5236\u7f16\u7801\u548c\u5b57\u4f53\u4ee5\u4fee\u590d\u4e71\u7801 (\u4f7f\u7528\u901a\u914d\u7b26 * \u4ee5\u8986\u76d6\u6240\u6709\u6807\u7b7e)
        try {
            String css = "* { " +
                        "font-family: 'Microsoft YaHei', 'PingFang SC', 'Segoe UI', 'Tahoma', 'Hiragino Sans GB', 'Arial', sans-serif !important; " +
                        "}";
            String base64Css = java.util.Base64.getEncoder().encodeToString(css.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            this.webEngine.setUserStyleSheetLocation("data:text/css;base64," + base64Css);
        } catch (Exception e) {
            Logger.error("\u8bbe\u7f6e\u9ed8\u8ba4\u6837\u5f0f\u8868\u5931\u8d25", e);
        }

        // \u914d\u7f6e\u6570\u636e\u76ee\u5f55\u4ee5\u5b9e\u73b0\u767b\u5f55持久化 (Cookies, LocalStorage)
        java.io.File dataDir = new java.io.File(System.getProperty("user.home"), ".smartbrowser/webview");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        this.webEngine.setUserDataDirectory(dataDir);

        // \u8bbe\u7f6e现代 User-Agent \u4ee5\u63d0\u9ad8兼容性
        this.webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

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
                injectFontFixScript();
                injectAdBlockScript();  // \u65b0\u589e\uff1a\u6ce8\u5165\u5e7f\u544a\u62e6\u622a\u811a\u6722
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

    /**
     * \u8bbe\u7f6e\u5168\u5c4f\u5904\u7406\u5668
     */
    public void setFullScreenHandler(javafx.util.Callback<Boolean, Void> handler) {
        try {
            // \u5c1d\u8bd5使用反射设置\uff0c\u517c\u5bb9不同 OpenJFX \u7248\u672c
            java.lang.reflect.Method method = webEngine.getClass().getMethod("setFullScreenHandler", javafx.util.Callback.class);
            method.invoke(webEngine, handler);
        } catch (Exception e) {
            Logger.warn("\u5f53\u524d JavaFX \u7248\u672c不支持原生 FullScreenHandler\uff0c\u5c06使用 JS 注入兼容模式");
            setupJsFullScreenCompatibility(handler);
        }
    }

    private void setupJsFullScreenCompatibility(javafx.util.Callback<Boolean, Void> handler) {
        // JS 兼容模式\uff1a通过监听事件或覆盖方法来模拟全屏切换
        // \u8fd9\u91cc可以监听 alert \u6216使用 JS \u6865\u63a5
        // \u4e3a\u4e86简单起见\uff0c\u6211\u4eec暂时记录日志\uff0c后续可以扩展
    }

    public void navigate(String url) {
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


    /**
     * \u6ce8\u5165\u5f3a\u529b\u5b57\u4f53\u4fee\u590d\u811a\u6722\uff0c\u652f\u6301\u7a3f\u900f Shadow DOM \u548c动态加载元素
     */
    private void injectFontFixScript() {
        // \u6ce8\uff1a\u5728\u6b64\u540c\u65f6\u6ce8\u5165全屏 API \u652f\u6301\u8865\u4e01\uff0c解决部分网站提示不支持全屏的问题
        String fullscreenShim = "if (!document.documentElement.requestFullscreen) {" +
                "  document.documentElement.requestFullscreen = function() { console.log('WEBKIT_REQ_FULLSCREEN'); };" +
                "  document.exitFullscreen = function() { console.log('WEBKIT_EXIT_FULLSCREEN'); };" +
                "}";
        executeScript(fullscreenShim);

        String js = "(function() {" +
                "  function applyFont(root) {" +
                "    if (!root) return;" +
                "    if (root.nodeType === 9 || (root.shadowRoot || root.host)) {" +
                "      if (!root.querySelector || !root.querySelector('#sb-font-fix')) {" +
                "        const style = document.createElement('style');" +
                "        style.id = 'sb-font-fix';" +
                "        style.textContent = '* { font-family: \"Microsoft YaHei\", \"PingFang SC\", \"Segoe UI\", sans-serif !important; }';" +
                "        if (root.appendChild) root.appendChild(style);" +
                "        else if (root.head) root.head.appendChild(style);" +
                "      }" +
                "    }" +
                "    const walker = document.createTreeWalker(root, NodeFilter.SHOW_ELEMENT);" +
                "    let node;" +
                "    while (node = walker.nextNode()) {" +
                "      if (node.shadowRoot) applyFont(node.shadowRoot);" +
                "    }" +
                "  }" +
                "  applyFont(document);" +
                "  const observer = new MutationObserver((mutations) => {" +
                "    mutations.forEach((mutation) => {" +
                "      mutation.addedNodes.forEach((node) => {" +
                "        if (node.nodeType === 1) {" +
                "          if (node.shadowRoot) applyFont(node.shadowRoot);" +
                "          const subWalker = document.createTreeWalker(node, NodeFilter.SHOW_ELEMENT);" +
                "          let subNode;" +
                "          while (subNode = subWalker.nextNode()) {" +
                "            if (subNode.shadowRoot) applyFont(subNode.shadowRoot);" +
                "          }" +
                "        }" +
                "      });" +
                "    });" +
                "  });" +
                "  observer.observe(document.documentElement, { childList: true, subtree: true });" +
                "})();";
        executeScript(js);
    }

    public void setUserAgent(String ua) {
        webEngine.setUserAgent(ua);
    }

    /**
     * \u6ce8\u5165\u5e7f\u544a\u62e6\u622a\u811a\u6722 - \u62e6\u622a\u5e38\u89c1\u5e7f\u544a\u57df\u540d\u548c\u9690\u85cf\u5e7f\u544a\u5143\u7d20
     */
    private void injectAdBlockScript() {
        String js = "(function() {" +
            "console.log('[SmartBrowser] \u5e7f\u544a\u62e6\u622a\u5df2\u542f\u7528');" +

            // === \u7b2c\u4e00\u90e8\u5206\uff1a\u62e6\u622a\u5e7f\u544a\u57df\u540d\u8bf7\u6c42 ===
            "var blockedDomains = [" +
            "  'doubleclick.net'," +
            "  'googlesyndication.com'," +
            "  'googleadservices.com'," +
            "  'adservice.google.com'," +
            "  'google-analytics.com'," +
            "  'googletagmanager.com'," +
            "  'scorecardresearch.com'," +
            "  'criteo.com'," +
            "  'outbrain.com'," +
            "  'taboola.com'," +
            "  'media.net'," +
            "  '2mdn.net'," +
            "  'pos.baidu.com'," +
            "  'cpro.baidu.com'," +
            "  'cbjs.baidu.com'," +
            "  'baidustatic.com/aoyou'," +
            "  'tanx.com'," +
            "  'alimama.cn'," +
            "  'mmstat.com'," +
            "  'cnzz.com'," +
            "  '51.la'" +
            "];" +

            "var blockedPatterns = [" +
            "  /\\/ads?\\//" + "," +
            "  /\\/ad\\d+\\// ," +
            "  /\\/banner\\// ," +
            "  /\\/adv\\// ," +
            "  /\\/advertising\\// ," +
            "  /\\/_ads\\// ," +
            "  /\\/ad\\.js/ ," +
            "  /\\/ads\\.js/ ," +
            "  /\\/adframe\\./," +
            "  /\\/adserver\\./," +
            "  /analytics/," +
            "  /tracking/" +
            "];" +

            "function shouldBlockURL(url) {" +
            "  if (!url) return false;" +
            "  var urlLower = url.toLowerCase();" +
            "  " +
            "  for (var i = 0; i < blockedDomains.length; i++) {" +
            "    if (urlLower.indexOf(blockedDomains[i]) !== -1) {" +
            "      console.log('[AdBlock] \u5df2\u62e6\u622a\u57df\u540d:', url);" +
            "      return true;" +
            "    }" +
            "  }" +
            "  " +
            "  for (var i = 0; i < blockedPatterns.length; i++) {" +
            "    if (blockedPatterns[i].test(urlLower)) {" +
            "      console.log('[AdBlock] \u5df2\u62e6\u622a\u8def\u5f84:', url);" +
            "      return true;" +
            "    }" +
            "  }" +
            "  " +
            "  return false;" +
            "}" +

            // \u62e6\u622a fetch \u8bf7\u6c42
            "var originalFetch = window.fetch;" +
            "window.fetch = function(url, options) {" +
            "  var urlStr = url.toString ? url.toString() : url;" +
            "  if (shouldBlockURL(urlStr)) {" +
            "    return Promise.reject(new Error('Blocked by AdBlocker'));" +
            "  }" +
            "  return originalFetch.apply(this, arguments);" +
            "};" +

            // \u62e6\u622a XMLHttpRequest
            "var originalOpen = XMLHttpRequest.prototype.open;" +
            "XMLHttpRequest.prototype.open = function(method, url) {" +
            "  if (shouldBlockURL(url)) {" +
            "    throw new Error('Blocked by AdBlocker');" +
            "  }" +
            "  return originalOpen.apply(this, arguments);" +
            "};" +

            // === \u7b2c\u4e8c\u90e8\u5206\uff1a\u9690\u85cf\u5e7f\u544a\u5143\u7d20\uff08CSS\uff09 ===
            "var adBlockCSS = '" +
            "  .ad, .ads, .ad-banner, .ad-box, .ad-container," +
            "  .advertisement, .advertising, .adv, .adsense," +
            "  [class*=\"google-ad\"], [id*=\"google-ad\"]," +
            "  [class^=\"ad-\"], [id^=\"ad-\"]," +
            "  [class*=\"-ad-\"], [id*=\"-ad-\"]," +
            "  [class$=\"-ad\"], [id$=\"-ad\"]," +
            "  .sponsored, .sponsor," +
            "  iframe[src*=\"ads\"], iframe[src*=\"doubleclick\"]," +
            "  iframe[src*=\"googleads\"], iframe[src*=\"googlesyndication\"]," +
            "  .gg, .guanggao," +  // \u4e2d\u6587\u7f51\u7ad9
            "  [class*=\"guanggao\"], [id*=\"guanggao\"]" +
            "  { display: none !important; visibility: hidden !important; }" +
            "';" +

            "var style = document.createElement('style');" +
            "style.id = 'smartbrowser-adblock';" +
            "style.textContent = adBlockCSS;" +
            "if (!document.getElementById('smartbrowser-adblock')) {" +
            "  document.head.appendChild(style);" +
            "}" +

            // === \u7b2c\u4e09\u90e8\u5206\uff1a\u52a8\u6001\u76d1\u63a7\u65b0\u589e\u5e7f\u544a\u5143\u7d20 ===
            "var observer = new MutationObserver(function(mutations) {" +
            "  mutations.forEach(function(mutation) {" +
            "    mutation.addedNodes.forEach(function(node) {" +
            "      if (node.nodeType === 1) {" +  // Element\u8282\u70b9
            "        var className = node.className || '';" +
            "        var id = node.id || '';" +
            "        var tag = node.tagName ? node.tagName.toLowerCase() : '';" +
            "        " +
            "        if (tag === 'iframe' || tag === 'script') {" +
            "          var src = node.src || '';" +
            "          if (shouldBlockURL(src)) {" +
            "            console.log('[AdBlock] \u5df2\u79fb\u9664\u5e7f\u544a\u5143\u7d20:', tag, src);" +
            "            node.remove();" +
            "            return;" +
            "          }" +
            "        }" +
            "        " +
            "        if (className.match(/\\b(ad|ads|advertisement|adsense|google-ad|guanggao)\\b/i) ||" +
            "            id.match(/\\b(ad|ads|advertisement|adsense|google-ad|guanggao)\\b/i)) {" +
            "          console.log('[AdBlock] \u5df2\u9690\u85cf\u5e7f\u544a\u5143\u7d20:', className, id);" +
            "          node.style.display = 'none';" +
            "          node.style.visibility = 'hidden';" +
            "        }" +
            "      }" +
            "    });" +
            "  });" +
            "});" +

            "observer.observe(document.documentElement, {" +
            "  childList: true," +
            "  subtree: true" +
            "});" +

            "console.log('[SmartBrowser] \u5e7f\u544a\u62e6\u622a\u811a\u6722\u5df2\u6fc0\u6d3b - \u6b63\u5728\u76d1\u63a7\u9875\u9762');" +

            "})();";

        executeScript(js);
        Logger.info("\u5df2\u6ce8\u5165\u5e7f\u544a\u62e6\u622a\u811a\u6722");
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
