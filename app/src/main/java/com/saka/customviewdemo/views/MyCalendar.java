package com.saka.customviewdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.saka.customviewdemo.model.CustomDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class MyCalendar extends View {
    private static final String TAG = "MyCalendar";
    private int viewWidth;
    private int viewHeight;
    private final static int ROW_COUNT = 7;
    private int lineCount;
    private CustomDate date;
    private int firstDayOfWeek;
    private Paint circlePaint;
    private List<PointF> points = new ArrayList<>();
    private PointF point = new PointF();

    public MyCalendar(Context context) {
        super(context);
        init();
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
        Log.d(TAG, "onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cutGrid();
        drawCircle(canvas, points);
        Log.d(TAG, "onDraw");
    }

    private void cutGrid() {
        float cellWidth = (float) viewWidth / ROW_COUNT;
        float cellHeight = (float) viewHeight / lineCount;
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < lineCount; j++) {
                point.set(cellWidth * i + cellWidth / 2, cellHeight * j + cellHeight / 2);
                Log.d(TAG,point.toString());
                points.add(point);
            }
        }
    }

    public void drawCircle(Canvas canvas, List<PointF> points) {
        for (PointF f : points) {
            canvas.drawCircle(f.x / 2, f.y / 2,
                    Math.min(f.x / 2, f.y / 2), circlePaint);
        }
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
    }

    public void setDate(CustomDate customDate) {
        Log.d(TAG, customDate.toString());
        this.date = customDate;
        firstDayOfWeek = date.getFirstDayOfWeek();
        Log.d(TAG, (date.getMonth() + 1) + "月1号是星期" + firstDayOfWeek);
        lineCount = calculateLineNum();
    }

    private int calculateLineNum() {
        int we = date.getTotalDayOfMonth();
        Log.d(TAG, "总共几天" + we);
        return (firstDayOfWeek - 1 + we) / 7;
    }
}
