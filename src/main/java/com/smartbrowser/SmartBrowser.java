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
        // \u6ce8\uff1a\u7f16\u7801\u8bbe\u7f6e\u5df2\u79fb\u81f3 Maven \u914d\u7f6e\u548c\u542f\u52a8\u811a\u6722\u7684 JVM \u53c2\u6570\u4e2d\uff0c\u4ee5\u786e\u4fdd\u5728 JVM \u521d\u59cb\u5316\u65f6\u751f\u6548
        launch(args);
    }
}
