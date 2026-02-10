package com.smartbrowser.ui.components;

import com.smartbrowser.core.NavigationController;
import com.smartbrowser.utils.URLUtils;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * 地址栏组件
 */
public class AddressBar extends HBox {
    private final TextField addressField;
    private final Button goBtn;
    private final Button bookmarkBtn;

    public AddressBar() {
        this.getStyleClass().add("address-bar-container");
        this.setSpacing(5);
        this.setPadding(new javafx.geometry.Insets(5));
        this.setMinHeight(40);

        addressField = new TextField();
        addressField.getStyleClass().add("address-bar");
        addressField.setPromptText("搜索或输入网址");
        HBox.setHgrow(addressField, Priority.ALWAYS);

        goBtn = new Button("Go");
        bookmarkBtn = new Button("⭐");

        initActions();
        this.getChildren().addAll(addressField, goBtn, bookmarkBtn);
    }

    private void initActions() {
        Runnable navigate = () -> {
            String input = addressField.getText();
            String url = URLUtils.parseInput(input);
            NavigationController.getInstance().navigateTo(url);
        };

        addressField.setOnAction(e -> navigate.run());
        goBtn.setOnAction(e -> navigate.run());
    }

    public void setURL(String url) {
        addressField.setText(url);
    }
}
