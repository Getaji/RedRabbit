package com.getaji.rrt.util;

import twitter4j.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StatusCache {

    public static StatusCache create() {
        return new StatusCache();
    }

    private final Map<Long, Status> statuses = new HashMap<>();

    private StatusCache() {}

    public Status get(Long id) {
        return statuses.get(id);
    }

    /**
     * Statusを追加。すでに入っている場合はfalseを返す。
     * @param status twitter status
     * @return contains id
     */
    public boolean add(Status status) {
        if (statuses.containsKey(status.getId())) {
            return false;
        }
        statuses.put(status.getId(), status);
        return true;
    }

    /**
     * Statusを更新。入っていない場合はfalseを返す。
     * @param status twitter status
     * @return not contains id
     */
    public boolean update(Status status) {
        if (!statuses.containsKey(status.getId())) {
            return false;
        }
        statuses.put(status.getId(), status);
        return true;
    }

    public void set(Status status) {
        statuses.put(status.getId(), status);
    }

    public Status remove(Long id) {
        return statuses.remove(id);
    }
}
