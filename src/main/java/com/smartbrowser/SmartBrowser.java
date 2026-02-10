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
        // \u5728 JVM \u5c42\u9762\u5c3d\u53ef\u80fd\u5f3a\u5236 UTF-8
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("com.sun.webkit.defaultEncoding", "UTF-8");
        launch(args);
    }
}
