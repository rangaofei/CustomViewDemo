package com.saka.customviewdemo.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class ScaleDrawable {
    private Drawable drawable;
    private int width;
    private int height;
    private float scale;

    public ScaleDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {

        this.scale = scale;
    }
}
