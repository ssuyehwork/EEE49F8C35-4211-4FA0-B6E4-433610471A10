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
 * \u5b8c\u6574\u7684\u4e3b\u7a97\u53e3\u5b9e\u73b0 - \u589e\u5f3a\u7a33\u5b9a\u6027\u7248
 */
public class MainWindow {
    private final Stage stage;
    private final BrowserEngine engine;
    private final NavigationBar navBar;
    private final AddressBar addressBar;
    private final StatusBar statusBar;
    private VBox topContainer;

    public MainWindow(Stage stage) {
        this.stage = stage;
        Logger.info("\u6b63\u5728\u521d\u59cb\u5316 MainWindow \u6a21\u5757...");

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
            // \u8bbe\u7f6e深色\u80cc\u666f
            root.setStyle("-fx-background-color: #1e1e1e;");

            // \u9876\u90e8\uff1a\u5bfc\u822a\u680f + \u5730\u5740\u680f
            this.topContainer = new VBox();
            topContainer.setSpacing(2);
            // \u7ed9\u9876\u90e8\u5bb9\u5668\u8bbe\u7f6e深色\u80cc\u666f\u548c相应阴影
            topContainer.setStyle("-fx-background-color: #2b2b2b; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
            topContainer.getChildren().addAll(navBar, addressBar);
            root.setTop(topContainer);

            // \u4e2d\u95f4\uff1a\u6d4f\u89c8\u5668\u89c6\u56fe
            root.setCenter(engine.getWebView());

            // \u5e95\u90e8\uff1a\u72b6\u6001\u680f
            root.setBottom(statusBar);

            // \u7ed1\u5b9a\u4e8b\u4ef6
            setupBindings();

            Scene scene = new Scene(root, 1280, 800);

            // \u5b89\u5168\u52a0\u8f7d\u6837\u5f0f\u8868
            loadStyleSheet(scene, "/styles/main.css");
            loadStyleSheet(scene, "/styles/dark-theme.css");

            stage.setScene(scene);
            stage.setTitle("SmartBrowser");

            // \u914d\u7f6e\u5168\u5c4f\u652f\u6301
            setupFullScreen();

            Logger.info("MainWindow UI \u521d\u59cb\u5316\u5b8c\u6210");
        } catch (Exception e) {
            Logger.error("MainWindow UI \u521d\u59cb\u5316\u5931\u8d25", e);
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
                    statusBar.setStatus("\u52a0\u8f7d\u5b8c\u6210");
                } else if (progress > 0) {
                    statusBar.setStatus("\u6b63\u5728\u52a0\u8f7d (" + (int)(progress * 100) + "%)...");
                }
            });
        });
    }

    private void loadStyleSheet(Scene scene, String path) {
        try {
            URL cssUrl = getClass().getResource(path);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                Logger.info("\u6837\u5f0f\u8868\u52a0\u8f7d\u6210\u529f: " + path);
            } else {
                Logger.warn("\u672a\u627e\u5230\u6837\u5f0f\u8868\u8d44\u6e90: " + path);
            }
        } catch (Exception e) {
            Logger.error("\u52a0\u8f7d\u6837\u5f0f\u8868\u5931\u8d25: " + path, e);
        }
    }

    private void setupFullScreen() {
        engine.setFullScreenHandler(full -> {
            Platform.runLater(() -> {
                stage.setFullScreen(full);
                // \u5168\u5c4f\u65f6\u9690\u85cf\u63a7\u5236\u680f\u548c\u72b6\u6001\u680f
                topContainer.setVisible(!full);
                topContainer.setManaged(!full);
                statusBar.setVisible(!full);
                statusBar.setManaged(!full);

                if (full) {
                    Logger.info("\u8fdb\u5165\u5168\u5c4f\u6a21\u5f0f");
                } else {
                    Logger.info("\u9000\u51fa\u5168\u5c4f\u6a21\u5f0f");
                }
            });
            return null;
        });

        // \u76d1\u542c Stage \u7684\u5168\u5c4f\u5c5e\u6027\uff0c\u4ee5\u5904\u7406 ESC \u952e\u9000\u51fa\u7684\u60c5\u51b5
        stage.fullScreenProperty().addListener((obs, old, isFullScreen) -> {
            if (!isFullScreen) {
                topContainer.setVisible(true);
                topContainer.setManaged(true);
                statusBar.setVisible(true);
                statusBar.setManaged(true);
            }
        });
    }

    public void show() {
        Logger.info("\u6b63\u5728\u663e\u793a\u4e3b\u7a97\u53e3...");
        stage.show();
        // \u9ed8\u8ba4\u5bfc\u822a\u5230\u4e3b\u9875
        NavigationController.getInstance().goHome();
    }
}
