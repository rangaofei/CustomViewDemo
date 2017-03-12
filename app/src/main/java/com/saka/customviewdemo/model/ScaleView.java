package com.saka.customviewdemo.model;

import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by saka on 2017/3/12.
 */

public class ScaleView {
    private View view;
    private FrameLayout.LayoutParams layoutParams;
    private int height;

    public ScaleView(View view) {
        this.view = view;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        height = layoutParams.height;
    }

    public void setScaleY(float value) {
        view.setY(value * height - height);

    }
}
