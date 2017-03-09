package com.saka.customviewdemo.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class MyViewGroup extends LinearLayout {
    private static final String TAG="MyViewGroup";
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,TAG+"dispatchTouchEvent");
//        return super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,TAG+"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,TAG+"onTouchEvent");
        return super.onTouchEvent(event);
    }
}
