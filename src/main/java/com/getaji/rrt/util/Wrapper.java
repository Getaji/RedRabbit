package com.getaji.rrt.util;

import java.util.Optional;

/**
 * 値を包むクラスです。{@link java.util.Optional}への変換も可能です。
 *
 * @author Getaji
 */
public class Wrapper<T> {

    private T pushObj = null;
    private T obj;

    // ======== STATIC

    /**
     * 値を包みます。null値は許可されません。
     * @param obj 包むオブジェクト
     * @param <O> オブジェクトの型
     * @return オブジェクトを包んだWrapperインスタンス
     * @throws java.lang.NullPointerException objがnull
     */
    public static <O> Wrapper<O> wrap(O obj) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException();
        }
        return new Wrapper<>(obj);
    }

    /**
     * 値を包みます。null値は許可されます。
     * @param obj 包むオブジェクト
     * @param <O> オブジェクトの型
     * @return オブジェクトを包んだWrapperインスタンス
     */
    public static <O> Wrapper<O> wrapNullable(O obj) {
        return new Wrapper<>(obj);
    }

    // ======== CONSTRUCTOR

    /**
     * オブジェクトを包んだWrapperインスタンスを生成します。null値は許可されます。
     * @param obj 包むオブジェクト
     */
    public Wrapper(T obj) {
        this.obj = obj;
    }

    // ======== GETTER

    /**
     * 包んだオブジェクトを取得します。
     * @return オブジェクト
     */
    public T get() {
        return obj;
    }

    // ======== SETTER

    /**
     * オブジェクトをセットします。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> set(T obj) {
        this.obj = obj;
        return this;
    }

    /**
     * 内部のオブジェクトがnullの場合に、渡されたオブジェクトをセットします。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> setIfNull(T obj) {
        if (this.obj == null) {
            this.obj = obj;
        }
        return this;
    }

    /**
     * 内部のオブジェクトが存在する場合に、渡されたオブジェクトをセットします。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> setIfPresent(T obj) {
        if (this.obj != null) {
            this.obj = obj;
        }
        return this;
    }

    /**
     * オブジェクトをセットします。null値は許可されません。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> setNotNullable(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        this.obj = obj;
        return this;
    }

    // ======== PUSH/MARGE

    /**
     * オブジェクトを保留状態でセットします。保留されたオブジェクトは{@link Wrapper#marge()}が呼び出されるまで反映されません。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> push(T obj) {
        this.pushObj = obj;
        return this;
    }

    /**
     * {@link Wrapper#push(Object)}でセットされた保留状態のオブジェクトを反映します。
     * @return 自身のインスタンス
     */
    public Wrapper<T> marge() {
        obj = pushObj;
        return this;
    }

    // ======== OPTIONAL

    /**
     * {@link java.util.Optional}に変換したものを返します。null値は許可されません。
     * これは<code>{@link Optional#of Optional.of}({@link Wrapper#get() wrapper.get()})</code>と等価です。
     * @return Optional
     */
    public Optional<T> optionalOf() {
        return Optional.of(obj);
    }

    /**
     * {@link java.util.Optional}に変換したものを返します。null値は許可されます。
     * これは<code>{@link Optional#ofNullable(Object) Optional.ofNullable}({@link Wrapper#get() wrapper.get()})</code>と等価です。
     * @return Optional
     */
    public Optional<T> optionalOfNullable() {
        return Optional.ofNullable(obj);
    }
}