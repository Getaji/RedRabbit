package com.getaji.rrt.util;

import lombok.NonNull;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StringUtil {
    public static boolean containsOr(@NonNull String target, @NonNull CharSequence... words) {
        for (CharSequence word : words) {
            if (target.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAnd(@NonNull String target, @NonNull CharSequence... words) {
        for (CharSequence word : words) {
            if (!target.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
