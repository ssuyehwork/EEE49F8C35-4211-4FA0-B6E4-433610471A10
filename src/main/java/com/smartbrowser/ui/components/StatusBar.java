package com.smartbrowser.ui.components;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * \u72b6\u6001\u680f\u7ec4\u4ef6
 */
public class StatusBar extends HBox {
    private final Label statusLabel;
    private final ProgressBar progressBar;

    public StatusBar() {
        this.getStyleClass().add("status-bar");
        this.setSpacing(10);
        
        statusLabel = new Label("\u5c31\u7eea");
        progressBar = new ProgressBar(0);
        progressBar.setVisible(false);
        progressBar.setPrefWidth(200);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        this.getChildren().addAll(statusLabel, spacer, progressBar);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setProgress(double progress) {
        if (progress >= 1.0 || progress <= 0) {
            progressBar.setVisible(false);
        } else {
            progressBar.setVisible(true);
            progressBar.setProgress(progress);
        }
    }
}
