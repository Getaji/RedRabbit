package com.getaji.rrt.util;

import javafx.application.Platform;
import javafx.scene.image.Image;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class ImageCache {

    public static ImageCache create() {
        return new ImageCache();
    }

    private final Map<String, Image> images = new WeakHashMap<>();
    private final Map<String, List<BiConsumer<String, Image>>> requester = new HashMap<>();

    private final Set<String> loadingUrls = new HashSet<>();

    private final ExecutorService executorService;

    private ImageCache() {
        executorService = Executors.newCachedThreadPool(r -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                });
    }

    /**
     * キャッシュが存在する場合に破棄し、画像を新たに読み込んで返します。
     *
     * @param imageLocation 画像の場所
     * @param consumer 画像を渡す関数
     */
    public synchronized void requestNew(String imageLocation, BiConsumer<String, Image> consumer) {
        images.remove(imageLocation);
        request(imageLocation, consumer);
    }

    public synchronized void request(String imageLocation, BiConsumer<String, Image> consumer) {
        Image image = images.get(imageLocation);
        if (image == null) {
            List<BiConsumer<String, Image>> list = requester.get(imageLocation);
            if (list == null) {
                list = new ArrayList<>();
                list.add(consumer);
                requester.put(imageLocation, list);
                load(imageLocation);
            } else {
                list.add(consumer);
            }
        } else {
            consumer.accept(imageLocation, image);
        }
    }

    @NonNull
    public void put(String imageId, Image image) {
        images.put(imageId, image);
    }

    private void load(String imageLocation) {
        executorService.submit(() -> {
            Image image = new Image(imageLocation);
            put(imageLocation, image);
            Platform.runLater(() -> {
                requester.get(imageLocation).forEach(c ->
                        c.accept(imageLocation, image));
                requester.remove(imageLocation);
            });
        });
    }
}
