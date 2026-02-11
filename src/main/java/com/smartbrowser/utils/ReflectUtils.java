package com.smartbrowser.utils;
import javafx.scene.web.WebEngine;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static void listWebEngineMethods() {
        try {
            Method[] methods = WebEngine.class.getMethods();
            for (Method m : methods) {
                if (m.getName().toLowerCase().contains("fullscreen")) {
                    System.out.println("Found: " + m.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
