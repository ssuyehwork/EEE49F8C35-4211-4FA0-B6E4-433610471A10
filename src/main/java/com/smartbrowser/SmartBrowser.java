package com.smartbrowser;

import com.smartbrowser.utils.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * SmartBrowser 应用程序主入口
 */
public class SmartBrowser extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Logger.info("正在启动 SmartBrowser...");
            
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 1280, 800);
            
            primaryStage.setTitle("SmartBrowser");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            Logger.info("SmartBrowser 启动成功");
        } catch (Exception e) {
            Logger.error("启动失败", e);
        }
    }

    @Override
    public void stop() {
        Logger.info("正在关闭 SmartBrowser...");
        // 这里后续将添加保存数据、关闭线程池等逻辑
    }

    public static void main(String[] args) {
        launch(args);
    }
}
