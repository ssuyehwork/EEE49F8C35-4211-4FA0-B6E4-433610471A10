package com.smartbrowser.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

/**
 * 图标加载工具类
 */
public class IconLoader {
    private static final String ICON_PATH = "/icons/";

    /**
     * 加载图标
     * @param name 图标名称（带扩展名）
     * @return Image对象
     */
    public static Image loadIcon(String name) {
        try {
            InputStream is = IconLoader.class.getResourceAsStream(ICON_PATH + name);
            if (is != null) {
                return new Image(is);
            }
        } catch (Exception e) {
            Logger.error("加载图标失败: " + name, e);
        }
        return null;
    }

    /**
     * 加载图标并返回 ImageView
     */
    public static ImageView loadIconView(String name, double width, double height) {
        Image img = loadIcon(name);
        if (img != null) {
            ImageView view = new ImageView(img);
            view.setFitWidth(width);
            view.setFitHeight(height);
            view.setPreserveRatio(true);
            return view;
        }
        return new ImageView();
    }
}
