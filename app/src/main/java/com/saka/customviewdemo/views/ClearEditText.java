package com.saka.customviewdemo.views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.saka.customviewdemo.R;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private RotateDrawable drawableRotate;
    private ValueAnimator alphaAnimator = ValueAnimator.ofInt(0, 255);
    private ValueAnimator rotateAnimator = ValueAnimator.ofInt(0, 10000);

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("顺序", "draw");
        super.onDraw(canvas);
    }

    private void init() {
        Log.d("顺序", "init");
        setIconVisible(false, getCompoundDrawables());
    }

    private void setIconVisible(boolean b, Drawable[] drawables) {
        if (b) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.mydelete), drawables[3]);
            drawableRotate = (RotateDrawable) getCompoundDrawables()[2];
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0 && before > 0) {
            Log.d("数字减到0", "before=" + before + ",  count=" + count);
            startAnimatorSetResver();
            return;
        }
        if (start == 0 && s.length() > 0) {
            Log.d("数字从0增加", "before=" + before + ",  count=" + count);
            setIconVisible(true, getCompoundDrawables());
            setAnimator();
            startAnimatorSet();
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setAnimator() {
        alphaAnimator.setDuration(1000);
        alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawableRotate.setAlpha((Integer) animation.getAnimatedValue());
            }
        });

        rotateAnimator.setDuration(1000);
        rotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawableRotate.setLevel((Integer) animation.getAnimatedValue());
            }
        });
    }

    private void startAnimatorSet() {
        AnimatorSet setVisible = new AnimatorSet();
        setVisible.playTogether(alphaAnimator, rotateAnimator);
        setVisible.start();
    }

    private void startAnimatorSetResver() {
        alphaAnimator.removeAllUpdateListeners();
        rotateAnimator.removeAllUpdateListeners();
        alphaAnimator.setDuration(1000);
        alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawableRotate.setAlpha(255 - (Integer) animation.getAnimatedValue());
            }
        });

        rotateAnimator.setDuration(1000);
        rotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawableRotate.setLevel(10000 - (Integer) animation.getAnimatedValue());
            }
        });
        startAnimatorSet();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = -1;
        float y = -1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getCompoundDrawables()[2] != null) {
                    if (getWidth() - getTotalPaddingRight() > event.getX() &&
                            getWidth() - getTotalPaddingRight() - drawableRotate.getBounds().left < event.getX()) {
                        x = event.getX();
                        y = event.getY();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (getCompoundDrawables()[2] != null) {
                    if (getWidth() - getTotalPaddingRight() < event.getX() &&
                            getWidth() - getPaddingRight() > event.getX()) {
                        x = event.getX();
                        y = event.getY();
                        this.setText("");
                        Log.d("点击了图片", "图片");
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("顺序", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
