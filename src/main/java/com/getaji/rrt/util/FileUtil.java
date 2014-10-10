package com.getaji.rrt.util;

import javafx.scene.image.Image;

import java.io.File;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public final class FileUtil {
    private FileUtil() {
    }

    /**
     * 拡張子のついたファイル名を返します。
     *
     * @param path ファイルパス
     * @return ファイル名
     */
    public static String getFileName(String path) {
        String[] split = path.split("[\\\\/]");
        if (split.length == 0) {
            return "";
        }
        return split[split.length - 1];
    }

    public static String getExtension(String path) {
        String[] split = path.split("\\.");
        if (1 < split.length) {
            String extension = split[split.length - 1];
            if (StringUtil.containsOr(extension, "\\", "/")) {
                return "";
            }
            return extension;
        } else {
            return "";
        }
    }

    public static void writeImage(Image image, File writeFile) {
        // TODO
    }
}
