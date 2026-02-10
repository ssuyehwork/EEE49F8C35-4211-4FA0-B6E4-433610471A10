package com.smartbrowser;

import com.smartbrowser.ui.MainWindow;
import com.smartbrowser.utils.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * SmartBrowser 应用程序主入口
 */
public class SmartBrowser extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Logger.info("正在启动 SmartBrowser...");

            MainWindow mainWindow = new MainWindow(primaryStage);
            mainWindow.show();

            Logger.info("SmartBrowser 启动成功");
        } catch (Exception e) {
            Logger.error("启动失败", e);
        }
    }

    @Override
    public void stop() {
        Logger.info("正在关闭 SmartBrowser...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
