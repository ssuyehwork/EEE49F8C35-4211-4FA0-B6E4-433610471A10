package com.smartbrowser;

import com.smartbrowser.ui.MainWindow;
import com.smartbrowser.utils.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import java.nio.charset.Charset;

/**
 * SmartBrowser \u5e94\u7528\u7a0b\u5e8f\u4e3b\u5165\u53e3
 */
public class SmartBrowser extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // \u521d\u59cb\u5316全局 Cookie \u7ba1\u7406\u5668\uff0c\u589e\u5f3a WebView \u7684\u767b\u5f55持久化能力
            if (java.net.CookieHandler.getDefault() == null) {
                java.net.CookieHandler.setDefault(new java.net.CookieManager(null, java.net.CookiePolicy.ACCEPT_ALL));
            }

            // \u6253\u5370\u73af\u5883\u8bca\u65ad\u4fe1\u606f
            System.out.println("=== Environment Diagnosis ===");
            System.out.println("Default Charset: " + Charset.defaultCharset());
            System.out.println("file.encoding: " + System.getProperty("file.encoding"));
            System.out.println("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
            System.out.println("com.sun.webkit.defaultEncoding: " + System.getProperty("com.sun.webkit.defaultEncoding"));
            System.out.println("==============================");

            Logger.info("\u6b63\u5728\u542f\u52a8 SmartBrowser...");

            MainWindow mainWindow = new MainWindow(primaryStage);
            mainWindow.show();

            Logger.info("SmartBrowser \u542f\u52a8\u6210\u529f");
        } catch (Exception e) {
            Logger.error("\u542f\u52a8\u5931\u8d25", e);
        }
    }

    @Override
    public void stop() {
        Logger.info("\u6b63\u5728\u5173\u95ed SmartBrowser...");
    }

    public static void main(String[] args) {
        // \u7f16\u7801\u914d\u7f6e\uff08\u4fdd\u7559\uff09
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("com.sun.webkit.defaultEncoding", "UTF-8");
        System.setProperty("sun.stdout.encoding", "UTF-8");
        System.setProperty("sun.stderr.encoding", "UTF-8");

        // === Cookie\u6301\u4e45\u5316\u914d\u7f6e ===
        System.setProperty("com.sun.webkit.enableCookies", "true");
        System.setProperty("com.sun.webkit.localStorageEnabled", "true");
        System.setProperty("com.sun.webkit.sessionStorageEnabled", "true");

        // === \u786c\u4ef6\u52a0\u901f\u914d\u7f6e ===
        // Windows: \u4f18\u5148\u4f7f\u7528DirectX
        System.setProperty("prism.order", "d3d,sw");
        System.setProperty("prism.vsync", "true");
        System.setProperty("prism.allowhidpi", "true");

        // === \u5a92\u4f53\u64ad\u653e\u914d\u7f6e ===
        System.setProperty("com.sun.webkit.useHTTP2Loader", "true");
        System.setProperty("http.maxConnections", "10");

        // === \u6027\u80fd\u4f18\u5316 ===
        System.setProperty("javafx.animation.fullspeed", "true");
        System.setProperty("javafx.pulseLogger", "false");

        Logger.info("\u7cfb\u7edf\u914d\u7f6e\u5b8c\u6210 - Cookie\u5df2\u542f\u7528, \u786c\u4ef6\u52a0\u901f\u5df2\u542f\u7528");

        launch(args);
    }
}
