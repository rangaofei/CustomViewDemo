package com.saka.customviewdemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class SectorButton extends View {
    private static final String TAG = "SectorButton";
    private int viewWidth;
    private int viewHeight;
    private float circleX;
    private float circleY;
    private Paint paint;
    private float circleR;
    private float lastX;
    private float lastY;
    private Paint sectorPaint;
    private RectF buttonRect;
    private boolean expandable;
    private ClickSectorButton listener;
    private float tempR;

    public interface ClickSectorButton {
        void onClickOne();

        void onClickTwo();

        void onClickThree();
    }

    public SectorButton(Context context) {
        super(context);
        init();
    }

    public SectorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SectorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.expandable) {
            maxButton(canvas);
        } else {
            minButton(canvas);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
        this.circleX = (float) w / 2;
        this.circleY = (float) h / 3 * 2;
        this.circleR = (float) h / 3;
        buttonRect = new RectF();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getX() - lastX) < 50 && Math.abs(event.getY() - lastY) < 50) {
                    Log.d(TAG, "event.getX()=" + event.getX() + " ,right=" + (circleX + circleR));
                    Log.d(TAG, "event.getX()=" + event.getX() + " ,left=" + (circleX - circleR));
                    Log.d(TAG, "event.getY()=" + event.getY() + " ,top=" + (circleY - circleR));
                    Log.d(TAG, "event.getY()=" + event.getY() + " ,bottom=" + (circleY + circleR));
                    if (event.getX() < (circleX + circleR) - 10 && event.getX() > (circleX - circleR) + 10
                            && event.getY() > (circleY - circleR) - 10 && event.getY() < (circleY + circleR)) {
                        Log.d(TAG, "点击事件");
                        if (!expandable) {
                            this.expandable = !expandable;
                            setAnimator(0, circleR);
                            Log.d(TAG, "展开");
                        } else {
                            this.expandable = !expandable;
                            setAnimator(circleR, 0);
                            Log.d(TAG, "收缩");
                        }
//                        this.expandable = !expandable;
//                        setAnimator();
//                        invalidate();
                    }
                    if (expandable && listener != null) {
                        Log.d(TAG, "判断点击按钮");
                        //点击左边的按钮
                        if (event.getX() > circleX - 2 * circleR + 10 &&
                                event.getX() < circleX - circleR + 10 &&
                                event.getY() > circleY - circleR - 10 &&
                                event.getY() < circleY) {
                            Log.d(TAG, "点击了按钮1");
                            listener.onClickOne();
                        }
                        //点击中间的按钮
                        if (event.getX() > circleX - circleR + 10 &&
                                event.getX() < circleX + circleR - 10 &&
                                event.getY() > circleY - 2 * circleR - 10 &&
                                event.getY() < circleY - circleR - 10) {
                            Log.d(TAG, "点击了按钮2");
                            listener.onClickTwo();
                        }
                        //点击右边的按钮
                        if (event.getX() > circleX + circleR - 10 &&
                                event.getX() < circleX + 2 * circleX + 10 &&
                                event.getY() > circleY - circleR - 10 &&
                                event.getY() < circleY) {
                            Log.d(TAG, "点击了按钮3");
                            listener.onClickThree();
                        }
                    }
                }
                break;
        }
        return true;
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(circleX, circleY, circleR, paint);
    }

    private void drawSector1(Canvas canvas) {
        sectorPaint.setColor(Color.LTGRAY);
        canvas.drawArc(buttonRect, 180, 60, true, sectorPaint);
    }

    private void drawSector2(Canvas canvas) {
        sectorPaint.setColor(Color.LTGRAY);
        canvas.drawArc(buttonRect, 2400, 60, true, sectorPaint);
    }

    private void drawSector3(Canvas canvas) {
        sectorPaint.setColor(Color.LTGRAY);
        canvas.drawArc(buttonRect, 300, 60, true, sectorPaint);
    }

    private void drawClear(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    private void maxButton(Canvas canvas) {
        drawClear(canvas);
        drawSector1(canvas);
        drawSector2(canvas);
        drawSector3(canvas);
        drawCircle(canvas);
    }

    private void minButton(Canvas canvas) {
        drawClear(canvas);
        drawSector1(canvas);
        drawSector2(canvas);
        drawSector3(canvas);
        drawCircle(canvas);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        sectorPaint = new Paint();
        sectorPaint.setColor(Color.BLUE);
        sectorPaint.setAntiAlias(true);
        sectorPaint.setStrokeWidth(2);

        sectorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        sectorPaint.setAlpha(20);
    }

    public void setClickSectorButton(ClickSectorButton listener) {
        this.listener = listener;
    }

    private void setAnimator(float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tempR = (float) animation.getAnimatedValue();
                buttonRect.set(circleX - 2 * tempR, circleY - 2 * tempR,
                        circleX + 2 * tempR, circleY + 2 * tempR);
                invalidate();
            }
        });
        animator.start();
    }

}
