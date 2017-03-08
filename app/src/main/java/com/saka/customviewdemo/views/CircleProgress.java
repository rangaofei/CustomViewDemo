package com.saka.customviewdemo.views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class CircleProgress extends View {
    private float percentOne;
    private float percentTwo;
    private float percentThree;
    private float oldPercentOne;
    private float oldPercentTwo;
    private float oldPercentThree;
    private float newPercentOne;
    private float newPercentTwo;
    private float newPercentThree;
    private Paint circlePaint1;
    private Paint circlePaint2;
    private Paint circlePaint3;
    private RectF rectF;

    public CircleProgress(Context context) {
        super(context);
        init();
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(50, 50, h - 50, h - 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("rect", rectF.toShortString());
        Log.d("rect arc", percentOne * 360 + "");
        canvas.drawArc(rectF, 0, 360 * newPercentOne, true, circlePaint1);
        canvas.drawArc(rectF, 360 * percentOne, 360 * newPercentTwo, true, circlePaint2);
        canvas.drawArc(rectF, 360 * (percentOne + percentTwo), 360 * newPercentThree, true, circlePaint3);
    }

    private void init() {
        circlePaint1 = new Paint();
        circlePaint2 = new Paint();
        circlePaint3 = new Paint();
        circlePaint1.setColor(Color.parseColor("#2c2c2c"));
        circlePaint2.setColor(Color.parseColor("#3df5fc"));
        circlePaint3.setColor(Color.parseColor("#ff2d6c"));
        circlePaint1.setStyle(Paint.Style.FILL);
        circlePaint2.setStyle(Paint.Style.FILL);
        circlePaint3.setStyle(Paint.Style.FILL);
        circlePaint1.setAntiAlias(true);
        circlePaint2.setAntiAlias(true);
        circlePaint3.setAntiAlias(true);
    }

    public void setValue(int value1, int value2, int value3) {
        percentOne=0;
        percentTwo=0;
        percentThree=0;
        newPercentOne=0;
        newPercentTwo=0;
        newPercentThree=0;
        int sum = value1 + value2 + value3;
        percentOne = (float) value1 / sum;
        oldPercentOne = percentOne;
        percentTwo = (float) value2 / sum;
        oldPercentTwo = percentTwo;
        percentThree = (float) value3 / sum;
        oldPercentThree = percentThree;
        ValueAnimator animator1 = ValueAnimator.ofFloat(0, percentOne);
        ValueAnimator animator2 = ValueAnimator.ofFloat(0, percentTwo);
        ValueAnimator animator3 = ValueAnimator.ofFloat(0, percentThree);
        animator1.setDuration(1000);
        animator2.setDuration(1000);
        animator3.setDuration(1000);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                newPercentOne = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                newPercentTwo = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                newPercentThree= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playSequentially(animator1,animator2,animator3);
        animatorSet.start();
        Log.d("rect :", "percentone=" + percentOne + ",percenttwo=" + percentTwo + ",percentthree=" + percentThree);

    }
}
