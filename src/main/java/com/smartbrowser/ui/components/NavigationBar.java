package com.smartbrowser.ui.components;

import com.smartbrowser.core.NavigationController;
import com.smartbrowser.utils.IconLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * \u5bfc\u822a\u680f\u7ec4\u4ef6\uff0c\u5305\u542b\u540e\u9000\u3001\u524d\u8fdb\u3001\u5237\u65b0\u3001\u505c\u6b62\u3001\u4e3b\u9875\u6309\u94ae
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
        
        backBtn = createButton("back.svg", "\u25c0"); // \u25c0 = \u25c0
        forwardBtn = createButton("forward.svg", "\u25b6"); // \u25b6 = \u25b6
        refreshBtn = createButton("refresh.svg", "\u21bb"); // \u21bb = \u21bb
        stopBtn = createButton("stop.svg", "\u2715"); // \u2715 = \u2715
        homeBtn = createButton("home.svg", "\u2302"); // \u2302 = \u2302
        
        initActions();
        this.getChildren().addAll(backBtn, forwardBtn, refreshBtn, stopBtn, homeBtn);
    }

    private Button createButton(String iconName, String tooltip) {
        Button btn = new Button();
        javafx.scene.image.ImageView iconView = IconLoader.loadIconView(iconName, 18, 18);
        if (iconView.getImage() != null) {
            btn.setGraphic(iconView);
        } else {
            // \u5982\u679c\u56fe\u6807\u52a0\u8f7d\u5931\u8d25\uff08\u5982 SVG \u4e0d\u652f\u6301\uff09\uff0c\u4f7f\u7528\u6587\u5b57
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
        
        // \u521d\u59cb\u7981\u7528\u72b6\u6001
        updateButtonStates();
    }

    public void updateButtonStates() {
        NavigationController nav = NavigationController.getInstance();
        backBtn.setDisable(!nav.canNavigateBack());
        forwardBtn.setDisable(!nav.canNavigateForward());
    }
}
