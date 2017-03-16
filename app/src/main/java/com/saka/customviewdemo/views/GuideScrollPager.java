package com.saka.customviewdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class GuideScrollPager extends ViewGroup {
    private static final String TAG = "GuideScrollPager";
    private Scroller scroller;
    private int lasyY;
    private float start;
    private float end;
    private int screenHeight;

    public GuideScrollPager(Context context) {
        super(context);
        init(context);
    }

    public GuideScrollPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        Log.d(TAG, "count=" + count);
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        Log.d(TAG, "screenHeight=" + screenHeight);
        mlp.height = screenHeight * childCount;
        Log.d(TAG, "mlp.height=" + mlp.height);
        setLayoutParams(mlp);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * screenHeight, r, (i + 1) * screenHeight);
            }
        }
        Log.d(TAG, "view2 的高度" + getChildAt(2).getTop());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lasyY = y;
                start = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                int dy = lasyY - y;
                Log.d(TAG, "dy=" + dy);
                Log.d(TAG, "正常滑动");
                scrollBy(0, dy);
                lasyY = y;
                break;
            case MotionEvent.ACTION_UP:
                end = getScrollY();
                float dScrollY = end - start;
                Log.d(TAG, "dScrollY=" + dScrollY);
                //判断是上滑还是下滑
                if (dScrollY < 0) {//下滑
                    Log.d(TAG, "向下滑动");
                    if (-dScrollY < screenHeight / 3) {//没滑动的情况
                        scroller.startScroll(0, getScrollY(),
                                0, (int)- dScrollY);
                        Log.d(TAG, "滑动的玩意" + start + " " + end);
                        Log.d(TAG, "未向下滑动");
                    } else {//滑动的情况
                        if (start < screenHeight) {//是第一页划不动
                            scroller.startScroll(0, getScrollY(),
                                    0, (int) (-dScrollY));
                            Log.d(TAG, "滑动的玩意" + start);
                        } else {//不是第一页，划得动
                            scroller.startScroll(0, getScrollY(),
                                    0, (int) (-screenHeight - dScrollY));
                            Log.d(TAG, "向下滑动到下一页");
                        }
                    }
                } else {//上滑
                    Log.d(TAG, "向上滑动");
                    if (dScrollY < screenHeight / 3) {//划不动
                        scroller.startScroll(0, getScrollY(),
                                0, (int) -dScrollY);
                        Log.d(TAG, "未向上滑动");
                    } else {//滑动了
                        if (start > screenHeight) {//是最后一页
                            scroller.startScroll(0, getScrollY(),
                                    0, (int) -dScrollY);
                        } else {//不是最后一页
                            scroller.startScroll(0, getScrollY(),
                                    0, (int) (screenHeight - dScrollY));
                            Log.d(TAG, "向上滑动到上一页");
                        }
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

    private void init(Context context) {
        scroller = new Scroller(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
    }
}
