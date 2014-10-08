package com.getaji.rrt.util;

import lombok.Getter;

/**
 * 3種類の型の値を扱うコンテナクラスです。
 *
 * @author Getaji
 */
@Getter
public class ThreeValues<O, Tw, Th> {

    // ================================================================
    // Static methods
    // ================================================================
    public static <O, Tw, Th> ThreeValues<O, Tw, Th> of(O one, Tw two, Th three) {
        return new ThreeValues<>(one, two, three);
    }

    // ================================================================
    // Fields
    // ================================================================
    private final O one;
    private final Tw two;
    private final Th three;

    // ================================================================
    // Constructors
    // ================================================================
    private ThreeValues(O one, Tw two, Th three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }
}
