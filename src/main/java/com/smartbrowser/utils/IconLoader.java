package com.smartbrowser.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

/**
 * \u56fe\u6807\u52a0\u8f7d\u5de5\u5177\u7c7b - \u589e\u5f3a\u7248
 */
public class IconLoader {
    private static final String ICON_PATH = "/icons/";

    /**
     * \u52a0\u8f7d\u56fe\u6807
     * @param name \u56fe\u6807\u540d\u79f0\uff08\u5e26\u6269\u5c55\u540d\uff09
     * @return Image\u5bf9\u8c61\uff0c\u52a0\u8f7d\u5931\u8d25\u8fd4\u56denull
     */
    public static Image loadIcon(String name) {
        try {
            InputStream is = IconLoader.class.getResourceAsStream(ICON_PATH + name);
            if (is == null) {
                Logger.warn("\u56fe\u6807\u8d44\u6e90\u4e0d\u5b58\u5728: " + ICON_PATH + name);
                return null;
            }
            // backgroundLoading = false \u786e\u4fdd\u7acb\u5373\u5c1d\u8bd5\u52a0\u8f7d\u4ee5\u4fbf\u68c0\u67e5\u9519\u8bef
            Image img = new Image(is);
            if (img.isError()) {
                Logger.warn("\u56fe\u6807\u52a0\u8f7d\u51fa\u9519 (\u53ef\u80fd\u662f\u4e0d\u652f\u6301\u7684\u683c\u5f0f): " + name);
                return null;
            }
            return img;
        } catch (Exception e) {
            Logger.error("\u52a0\u8f7d\u56fe\u6807\u8fc7\u7a0b\u53d1\u751f\u5f02\u5e38: " + name, e);
            return null;
        }
    }

    /**
     * \u52a0\u8f7d\u56fe\u6807\u5e76\u8fd4\u56de ImageView
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
