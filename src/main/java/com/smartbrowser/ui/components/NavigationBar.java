package com.smartbrowser.ui.components;

import com.smartbrowser.core.NavigationController;
import com.smartbrowser.utils.IconLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * 导航栏组件，包含后退、前进、刷新、停止、主页按钮
 */
public class NavigationBar extends HBox {
    private final Button backBtn;
    private final Button forwardBtn;
    private final Button refreshBtn;
    private final Button stopBtn;
    private final Button homeBtn;

    public NavigationBar() {
        this.getStyleClass().add("navigation-bar");
        this.setSpacing(5);
        this.setPadding(new javafx.geometry.Insets(5));
        this.setMinHeight(40);

        backBtn = createButton("back.svg", "后退");
        forwardBtn = createButton("forward.svg", "前进");
        refreshBtn = createButton("refresh.svg", "刷新");
        stopBtn = new Button("×"); // 暂时用文本
        homeBtn = new Button("🏠"); // 暂时用文本

        initActions();
        this.getChildren().addAll(backBtn, forwardBtn, refreshBtn, stopBtn, homeBtn);
    }

    private Button createButton(String iconName, String tooltip) {
        Button btn = new Button();
        javafx.scene.image.ImageView iconView = IconLoader.loadIconView(iconName, 18, 18);
        if (iconView.getImage() != null) {
            btn.setGraphic(iconView);
        } else {
            // 如果图标加载失败（如 SVG 不支持），使用文字
            btn.setText(tooltip);
        }
        return btn;
    }

    private void initActions() {
        NavigationController nav = NavigationController.getInstance();
        backBtn.setOnAction(e -> nav.goBack());
        forwardBtn.setOnAction(e -> nav.goForward());
        refreshBtn.setOnAction(e -> nav.reload());
        stopBtn.setOnAction(e -> nav.stopLoading());
        homeBtn.setOnAction(e -> nav.goHome());

        // 初始禁用状态
        updateButtonStates();
    }

    public void updateButtonStates() {
        NavigationController nav = NavigationController.getInstance();
        backBtn.setDisable(!nav.canNavigateBack());
        forwardBtn.setDisable(!nav.canNavigateForward());
    }
}
