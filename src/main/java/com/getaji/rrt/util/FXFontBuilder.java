package com.getaji.rrt.util;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * {@link javafx.scene.text.Font}をミュータブルにわいわいしてbuildする感じのアレ
 *
 * @author Getaji
 */
public class FXFontBuilder {

    // ================================================================
    // Static methods
    // ================================================================
    /**
     * {@link javafx.scene.text.Font}インスタンスからビルダーインスタンスを生成。
     * @param font フォント
     * @return ビルダーインスタンス
     */
    public static FXFontBuilder of(Font font) {
        return new FXFontBuilder(font);
    }

    // ================================================================
    // Fields
    // ================================================================
    private Font font;
    private String fontFamily;
    private FontWeight fontWeight;
    private FontPosture fontPosture;
    private double fontSize;

    // ================================================================
    // Constructors
    // ================================================================
    private FXFontBuilder(Font font) {
        this.font = font;
        fontFamily = font.getFamily();
        fontWeight = FontWeight.findByName(font.getStyle());
        fontPosture = FontPosture.findByName(font.getStyle());
        fontSize = font.getSize();
    }

    // ================================================================
    // Setters
    // ================================================================
    /**
     * フォントファミリーをセット。
     * @param fontFamily フォントファミリー
     * @return ビルダーインスタンス
     */
    public FXFontBuilder family(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    /**
     * フォントの太さ(font weight)をセット。
     * @param fontWeight フォントの太さ
     * @return ビルダーインスタンス
     */
    public FXFontBuilder weight(FontWeight fontWeight) {
        this.fontWeight = fontWeight;
        return this;
    }

    /**
     * フォントの傾き(font posture)をセット。
     * @param fontPosture フォントの傾き
     * @return ビルダーインスタンス
     */
    public FXFontBuilder posture(FontPosture fontPosture) {
        this.fontPosture = fontPosture;
        return this;
    }

    /**
     * フォントサイズをセット。
     * @param fontSize フォントサイズ
     * @return ビルダーインスタンス
     */
    public FXFontBuilder size(double fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * フォントサイズを加算。
     * @param addSize 加算量
     * @return ビルダーインスタンス
     */
    public FXFontBuilder add(double addSize) {
        fontSize += addSize;
        return this;
    }

    /**
     * フォントサイズを減算。
     * @param addSize 減算量
     * @return ビルダーインスタンス
     */
    public FXFontBuilder sub(double addSize) {
        fontSize -= addSize;
        return this;
    }

    /**
     * フォントサイズを乗算。
     * @param addSize 乗算量
     * @return ビルダーインスタンス
     */
    public FXFontBuilder multi(double addSize) {
        fontSize *= addSize;
        return this;
    }

    /**
     * フォントサイズを除算。
     * @param addSize 除算量
     * @return ビルダーインスタンス
     */
    public FXFontBuilder divide(double addSize) {
        fontSize /= addSize;
        return this;
    }

    /**
     * {@link javafx.scene.text.Font}インスタンスを生成。
     * @return フォントインスタンス
     */
    public Font build() {
        return Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }
}
