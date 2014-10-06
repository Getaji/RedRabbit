package com.getaji.rrt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * ミュータブルなコンテナオブジェクトです。{@link java.util.Optional}への変換も可能です。
 * nullを許可しな{@link #wrap(Object)}で生成されたWrapperインスタンスはset時はnull値を許可します。
 *
 * @author Getaji
 */
public class Wrapper<T> {

    private T obj;
    private final List<Consumer<Wrapper<T>>> handlers = new ArrayList<>();
    private final List<Wrapper<T>> bindWrappers = new ArrayList<>();

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

    public static <O> Wrapper<O> empty() {
        return wrapNullable(null);
    }

    // ======== CONSTRUCTOR

    /**
     * オブジェクトを包んだWrapperインスタンスを生成します。null値は許可されます。
     * @param obj 包むオブジェクト
     */
    private Wrapper(T obj) {
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

    public List<Consumer<Wrapper<T>>> getHandlers() {
        return handlers;
    }

    public List<Wrapper<T>> getBindWrappers() {
        return bindWrappers;
    }

    // ======== SETTER

    /**
     * オブジェクトをセットします。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> set(T obj) {
        this.obj = obj;
        for (Consumer<Wrapper<T>> handler : handlers) {
            handler.accept(this);
        }
        for (Wrapper<T> bindWrapper : bindWrappers) {
            bindWrapper.set(obj);
        }
        return this;
    }

    /**
     * 内部のオブジェクトがnullの場合に、渡されたオブジェクトをセットします。
     * @param obj オブジェクト
     * @return 自身のインスタンス
     */
    public Wrapper<T> setIfNull(T obj) {
        if (this.obj == null) {
            set(obj);
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
            set(obj);
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
        set(obj);
        return this;
    }

    public Wrapper<T> addValueSetHandler(Consumer<Wrapper<T>> handler) {
        handlers.add(handler);
        return this;
    }

    public Wrapper<T> removeValueSetHandler(Consumer<Wrapper<T>> handler) {
        handlers.remove(handler);
        return this;
    }

    public boolean bindTo(Wrapper<T> wrapper) {
        for (Wrapper<T> wrapped : bindWrappers) {
            if (wrapped.bindWrappers.contains(this)) {
                return false;
            }
        }
        wrapper.bindWrappers.add(this);
        return true;
    }

    public boolean unbind(Wrapper<T> wrapper) {
        this.bindWrappers.remove(wrapper);
        return true;
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