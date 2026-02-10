package com.smartbrowser.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

/**
 * 图标加载工具类 - 增强版
 */
public class IconLoader {
    private static final String ICON_PATH = "/icons/";

    /**
     * 加载图标
     * @param name 图标名称（带扩展名）
     * @return Image对象，加载失败返回null
     */
    public static Image loadIcon(String name) {
        try {
            InputStream is = IconLoader.class.getResourceAsStream(ICON_PATH + name);
            if (is == null) {
                Logger.warn("图标资源不存在: " + ICON_PATH + name);
                return null;
            }
            // backgroundLoading = false 确保立即尝试加载以便检查错误
            Image img = new Image(is);
            if (img.isError()) {
                Logger.warn("图标加载出错 (可能是不支持的格式): " + name);
                return null;
            }
            return img;
        } catch (Exception e) {
            Logger.error("加载图标过程发生异常: " + name, e);
            return null;
        }
    }

    /**
     * 加载图标并返回 ImageView
     */
    public static ImageView loadIconView(String name, double width, double height) {
        Image img = loadIcon(name);
        ImageView view = new ImageView();
        if (img != null) {
            view.setImage(img);
            view.setFitWidth(width);
            view.setFitHeight(height);
            view.setPreserveRatio(true);
            view.setSmooth(true);
        }
        return view;
    }
}
