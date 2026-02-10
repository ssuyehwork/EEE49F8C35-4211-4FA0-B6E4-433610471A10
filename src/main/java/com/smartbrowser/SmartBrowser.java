package com.smartbrowser;

import com.smartbrowser.ui.MainWindow;
import com.smartbrowser.utils.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * SmartBrowser \u5e94\u7528\u7a0b\u5e8f\u4e3b\u5165\u53e3
 */
public class SmartBrowser extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
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
        System.setProperty("file.encoding", "UTF-8");
        launch(args);
    }
}
