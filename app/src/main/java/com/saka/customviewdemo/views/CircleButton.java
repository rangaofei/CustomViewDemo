package com.saka.customviewdemo.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class CircleButton extends ViewGroup {
    private float radius;
    private float square3 = 1.7320508075689F;
    private Scroller scroller;
    private ViewDragHelper dragHelper;
    private int bottomBounds;
    private int tempY;
    private float circleX;
    private float circleY;

    public CircleButton(Context context) {
        super(context);
        init(context);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        setCircle(l, t, r, b);
    }

    private void setCircle(int l, int t, int r, int b) {
        circleX = l + ((float) r - l) / 3;
        circleY = t + ((float) b - t) / 2;
        radius = Math.min(((float) r - l) / 2, ((float) b - t) / 2);
        radius = radius - getChildAt(0).getMeasuredWidth();
        getViewPosition(circleX, circleY);
        this.bottomBounds = (int) (circleY + radius);
    }

    private void getViewPosition(float circleX, float circleY) {
        float oneX = circleX + radius;
        View view1 = getChildAt(0);
        View view2 = getChildAt(1);
        View view3 = getChildAt(2);
        View view4 = getChildAt(3);
        View view5 = getChildAt(4);
        View view6 = getChildAt(5);
        view1.layout((int) (oneX - view1.getMeasuredWidth() / 2),
                (int) (circleY - view1.getMeasuredHeight() / 2),
                (int) (oneX + view1.getMeasuredWidth() / 2),
                (int) (circleY + view1.getMeasuredHeight() / 2));
        float twoX = circleX + radius / 2;
        float twoY = circleY + radius * square3 / 2;
        view2.layout((int) (twoX - view1.getMeasuredWidth() / 2),
                (int) (twoY - view2.getMeasuredHeight()),
                (int) (twoX + view1.getMeasuredWidth() / 2),
                (int) (twoY + view2.getMeasuredHeight() / 2));

        float threeX = circleX - radius / 2;
        view3.layout((int) (threeX - view1.getMeasuredWidth() / 2),
                (int) (twoY - view2.getMeasuredHeight()),
                (int) (threeX + view1.getMeasuredWidth() / 2),
                (int) (twoY + view2.getMeasuredHeight() / 2));
        float fourX = circleX - radius;
        view4.layout((int) (fourX - view1.getMeasuredWidth() / 2),
                (int) (circleY - view1.getMeasuredHeight() / 2),
                (int) (fourX + view1.getMeasuredWidth() / 2),
                (int) (circleY + view1.getMeasuredHeight() / 2));
        float fiveY = circleY - radius * square3 / 2;
        view5.layout((int) (threeX - view1.getMeasuredWidth() / 2),
                (int) (fiveY - view2.getMeasuredHeight()),
                (int) (threeX + view1.getMeasuredWidth() / 2),
                (int) (fiveY + view2.getMeasuredHeight() / 2));
        view6.layout((int) (twoX - view1.getMeasuredWidth() / 2),
                (int) (fiveY - view2.getMeasuredHeight()),
                (int) (twoX + view1.getMeasuredWidth() / 2),
                (int) (fiveY + view2.getMeasuredHeight() / 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    private void init(Context context) {
        dragHelper = ViewDragHelper.create(this, 1.0F, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                float t=child.getTop();

                return (int) (Math.sqrt(radius*radius-(t-circleY)*(t-circleY))+circleX);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                return top;
            }
        });
    }
}
