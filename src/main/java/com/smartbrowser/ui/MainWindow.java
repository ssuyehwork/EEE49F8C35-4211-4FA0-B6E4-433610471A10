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

/**
 * 完整的主窗口实现
 */
public class MainWindow {
    private final Stage stage;
    private final BrowserEngine engine;
    private final NavigationBar navBar;
    private final AddressBar addressBar;
    private final StatusBar statusBar;

    public MainWindow(Stage stage) {
        this.stage = stage;
        this.engine = new BrowserEngine();
        this.navBar = new NavigationBar();
        this.addressBar = new AddressBar();
        this.statusBar = new StatusBar();

        NavigationController.getInstance().setBrowserEngine(engine);
        initUI();
    }

    private void initUI() {
        BorderPane root = new BorderPane();

        // 顶部：导航栏 + 地址栏
        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(navBar, addressBar);
        root.setTop(topContainer);

        // 中间：浏览器视图
        root.setCenter(engine.getWebView());

        // 底部：状态栏
        root.setBottom(statusBar);

        // 绑定事件
        engine.addURLListener(url -> {
            Platform.runLater(() -> {
                addressBar.setURL(url);
                navBar.updateButtonStates();
            });
        });

        engine.addTitleListener(title -> {
            Platform.runLater(() -> {
                if (title != null) {
                    stage.setTitle(title + " - SmartBrowser");
                }
            });
        });

        engine.addProgressListener(progress -> {
            Platform.runLater(() -> {
                statusBar.setProgress(progress);
                if (progress >= 1.0) {
                    statusBar.setStatus("加载完成");
                } else {
                    statusBar.setStatus("正在加载...");
                }
            });
        });

        Scene scene = new Scene(root, 1280, 800);
        String css = getClass().getResource("/styles/main.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.setTitle("SmartBrowser");
    }

    public void show() {
        stage.show();
        // 默认导航到主页
        NavigationController.getInstance().goHome();
    }
}
