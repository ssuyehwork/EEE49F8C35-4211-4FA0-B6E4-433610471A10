package com.smartbrowser.ui;

import com.smartbrowser.core.BrowserEngine;
import com.smartbrowser.core.NavigationController;
import com.smartbrowser.ui.components.AddressBar;
import com.smartbrowser.ui.components.NavigationBar;
import com.smartbrowser.ui.components.StatusBar;
import com.smartbrowser.utils.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;

/**
 * 完整的主窗口实现 - 增强稳定性版
 */
public class MainWindow {
    private final Stage stage;
    private final BrowserEngine engine;
    private final NavigationBar navBar;
    private final AddressBar addressBar;
    private final StatusBar statusBar;

    public MainWindow(Stage stage) {
        this.stage = stage;
        Logger.info("正在初始化 MainWindow 模块...");

        this.engine = new BrowserEngine();
        this.navBar = new NavigationBar();
        this.addressBar = new AddressBar();
        this.statusBar = new StatusBar();

        NavigationController.getInstance().setBrowserEngine(engine);
        initUI();
    }

    private void initUI() {
        try {
            BorderPane root = new BorderPane();
            // 设置一个淡灰色背景，防止全白导致看不出边界
            root.setStyle("-fx-background-color: #f5f5f5;");

            // 顶部：导航栏 + 地址栏
            VBox topContainer = new VBox();
            topContainer.setSpacing(2);
            // 给顶部容器设置背景色和阴影效果（调试用）
            topContainer.setStyle("-fx-background-color: #ffffff; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            topContainer.getChildren().addAll(navBar, addressBar);
            root.setTop(topContainer);

            // 中间：浏览器视图
            root.setCenter(engine.getWebView());

            // 底部：状态栏
            root.setBottom(statusBar);

            // 绑定事件
            setupBindings();

            Scene scene = new Scene(root, 1280, 800);

            // 安全加载样式表
            loadStyleSheet(scene, "/styles/main.css");
            loadStyleSheet(scene, "/styles/light-theme.css");

            stage.setScene(scene);
            stage.setTitle("SmartBrowser");

            Logger.info("MainWindow UI 初始化完成");
        } catch (Exception e) {
            Logger.error("MainWindow UI 初始化失败", e);
        }
    }

    private void setupBindings() {
        engine.addURLListener(url -> {
            Platform.runLater(() -> {
                addressBar.setURL(url);
                navBar.updateButtonStates();
            });
        });

        engine.addTitleListener(title -> {
            Platform.runLater(() -> {
                if (title != null && !title.isEmpty()) {
                    stage.setTitle(title + " - SmartBrowser");
                }
            });
        });

        engine.addProgressListener(progress -> {
            Platform.runLater(() -> {
                statusBar.setProgress(progress);
                if (progress >= 1.0) {
                    statusBar.setStatus("加载完成");
                } else if (progress > 0) {
                    statusBar.setStatus("正在加载 (" + (int)(progress * 100) + "%)...");
                }
            });
        });
    }

    private void loadStyleSheet(Scene scene, String path) {
        try {
            URL cssUrl = getClass().getResource(path);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                Logger.info("样式表加载成功: " + path);
            } else {
                Logger.warn("未找到样式表资源: " + path);
            }
        } catch (Exception e) {
            Logger.error("加载样式表失败: " + path, e);
        }
    }

    public void show() {
        Logger.info("正在显示主窗口...");
        stage.show();
        // 默认导航到主页
        NavigationController.getInstance().goHome();
    }
}
