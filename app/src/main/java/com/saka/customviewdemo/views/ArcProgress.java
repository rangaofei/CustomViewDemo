package com.saka.customviewdemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class ArcProgress extends View {
    private float value;
    private Paint arcPaint;
    private RectF rectF;
    private float oldValue;

    public ArcProgress(Context context) {
        super(context);
        init();
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, 270, value, false, arcPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("自定义view", "onsizechanged");
        rectF = new RectF(50, 50, getMeasuredHeight() - 50, getMeasuredHeight() - 50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void init() {
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(Color.parseColor("#2c2c2c"));
        arcPaint.setStrokeWidth(50);
    }


    public void setValue(final float v) {
        ValueAnimator animator = ValueAnimator.ofFloat(oldValue, v);
        oldValue = v;
        if (Math.abs(v - oldValue) > 180) {
            animator.setDuration(1000);
        } else {
            animator.setDuration(500);
        }
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                Log.d("change", "=" + animation.getAnimatedValue());
                invalidate();
            }
        });
        animator.start();
    }
}
