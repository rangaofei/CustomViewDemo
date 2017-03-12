package com.saka.customviewdemo.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.saka.customviewdemo.model.CustomDate;

import java.util.ArrayList;
import java.util.List;

import static com.saka.customviewdemo.views.MyCalendar.DayState.CURRENTMONTH;
import static com.saka.customviewdemo.views.MyCalendar.DayState.NEXTMONTH;

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
    private int lastDayOfWeek;
    private int lastMonthTotalDays;
    private int monthDaySum;
    private Paint circlePaint;
    private List<PointF> points = new ArrayList<>();
    private float radius;
    private float cellWidth;
    private float cellHeight;
    private Paint textPaint;
    private Paint selectPaint;
    private Paint selectTextPaint;
    private CellDay[] cellDays;
    private ClickCellListener clickCellListener;
    private DayState tempState;

    private float oldPositionX = -100;
    private float oldPositionY = -100;
    private float newPositionX = -100;
    private float newPositionY = -100;
    private float tempPositionX = -100;
    private float tempPositionY = -100;
    private ValueAnimator selectAnimatorX = new ValueAnimator();
    private ValueAnimator selectAnimatorY = new ValueAnimator();
    private AnimatorSet animatorSet;
    private String tempDate = "1";

    private float touchRawX;
    private float touchRawY;


    public MyCalendar(Context context) {
        super(context);
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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
        Log.d(TAG, "onSizeChanged"+w+h);
        cutGrid();
        init();
        setCellDay();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDays(canvas, cellDays);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MontionEvent" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchRawX = event.getX();
                touchRawY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MontionEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "MotionEvent:ACTION_UP");
                float touchX = event.getX();
                float touchY = event.getY();
                if (touchRawY - touchY < -200) {
                    //下滑事件
                    Log.d(TAG, "下滑事件");
                    setMaxView();
                }
                if (touchRawY - touchX > 200) {
                    //上划事件
                    Log.d(TAG, "上划事件");
                    setMinView();
                }
                if (Math.abs(touchRawX - touchX) < 100 && Math.abs(touchY - touchRawY) < 100) {
                    //点击事件
                    Log.d(TAG, "点击事件");
                    int touchRow = (int) (touchX / cellWidth);
                    int touchLine = (int) (touchY / cellHeight);
                    final int touchId = touchLine * ROW_COUNT + touchRow;
                    tempState = cellDays[touchId].getDayState();
                    cellDays[touchId].setSelected(true);
                    CustomDate customDate = cellDays[touchId].getCustomDate();
                    clickCellListener.onClickCell(customDate);
                    newPositionX = cellDays[touchId].getPointX();
                    newPositionY = cellDays[touchId].getPointY();
                    setselectAnimator(touchId);
                }
                break;

        }

        return true;
    }

    private void setselectAnimator(final int touchId) {
        selectAnimatorX.removeAllUpdateListeners();
        selectAnimatorX.setFloatValues(oldPositionX, newPositionX);
        selectAnimatorX.setDuration(300);
        selectAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tempPositionX = (float) animation.getAnimatedValue();

            }
        });
        selectAnimatorY.removeAllUpdateListeners();
        selectAnimatorY.setFloatValues(oldPositionY, newPositionY);
        selectAnimatorY.setDuration(300);
        selectAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tempPositionY = (float) animation.getAnimatedValue();
                tempDate = cellDays[touchId].getDate();
                postInvalidate();
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(selectAnimatorX, selectAnimatorY);
        animatorSet.start();
    }

    private void setMinView() {
        ObjectAnimator animator=ObjectAnimator.ofFloat(this,"Y",0,-viewHeight+100);
        animator.setDuration(300);
        animator.start();
    }

    private void setMaxView() {
        ObjectAnimator animator=ObjectAnimator.ofFloat(this,"Y",-viewHeight+100,0);
        animator.setDuration(300);
        animator.start();
    }

    /**
     * 设置总共显示多少天，每天的状态
     */
    private void setCellDay() {
        cellDays = new CellDay[lineCount * ROW_COUNT];
        for (int i = 0, length = cellDays.length; i < length; i++) {
            cellDays[i] = new CellDay();
            cellDays[i].setPointX(points.get(i).x);
            cellDays[i].setPointY(points.get(i).y);
            if (firstDayOfWeek > 1 && i < firstDayOfWeek - 1) {
                cellDays[i].setDayState(DayState.LASTMONTH);
                cellDays[i].setDate(String.valueOf(lastMonthTotalDays - firstDayOfWeek + i + 2));
                cellDays[i].setCustomDate(new CustomDate(
                        date.getYear(), date.getMonth() - 1, lastMonthTotalDays - firstDayOfWeek + i + 2));
            }
            if (i >= firstDayOfWeek - 1 && i < monthDaySum + firstDayOfWeek - 1) {
                cellDays[i].setDayState(CURRENTMONTH);
                cellDays[i].setDate(String.valueOf(i + 2 - firstDayOfWeek));
                cellDays[i].setCustomDate(new CustomDate(
                        date.getYear(), date.getMonth(), i - firstDayOfWeek + 2));
            }
            if (i >= monthDaySum + firstDayOfWeek - 1) {
                cellDays[i].setDayState(NEXTMONTH);
                cellDays[i].setDate(String.valueOf(i - monthDaySum - firstDayOfWeek + 2));
                cellDays[i].setCustomDate(new CustomDate(
                        date.getYear(), date.getMonth() + 1, i - monthDaySum - firstDayOfWeek + 2));
            }
        }
    }

    private void cutGrid() {
        cellWidth = (float) viewWidth / ROW_COUNT;
        cellHeight = (float) viewHeight / lineCount;
        this.radius = Math.min(cellWidth / 2, cellHeight / 2);
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < ROW_COUNT; j++) {
                points.add(new PointF(cellWidth * j + cellWidth / 2, cellHeight * i + cellHeight / 2));
            }
        }
    }


    private void drawDays(Canvas canvas, CellDay[] cellDays) {
        for (CellDay c : cellDays) {
            switch (c.getDayState()) {
                case LASTMONTH:
                    if (c.isSelected()) {
                        circlePaint.setColor(Color.TRANSPARENT);
                    } else {
                        circlePaint.setColor(Color.BLUE);
                        textPaint.setColor(Color.BLUE);
                    }
                    break;
                case CURRENTMONTH:
                    if (c.isSelected()) {
                        circlePaint.setColor(Color.YELLOW);
                        textPaint.setColor(Color.BLACK);
                    } else {
                        circlePaint.setColor(Color.RED);
                        textPaint.setColor(Color.RED);
                    }
                    break;
                case NEXTMONTH:
                    if (c.isSelected()) {
                        circlePaint.setColor(Color.TRANSPARENT);
                    } else {
                        circlePaint.setColor(Color.GREEN);
                        textPaint.setColor(Color.GREEN);
                    }
                    break;
                case CURRENTDAY:
                    circlePaint.setColor(Color.YELLOW);
                    textPaint.setColor(Color.BLACK);
                    break;
            }
            canvas.drawCircle(tempPositionX, tempPositionY, radius - 10, selectPaint);
            canvas.drawText(tempDate,
                    tempPositionX - textPaint.measureText(tempDate) / 2,
                    tempPositionY + textPaint.getTextSize() / 2,
                    selectTextPaint);
            canvas.drawText(c.getDate(),
                    c.getPointX() - textPaint.measureText(c.getDate()) / 2,
                    c.getPointY() + textPaint.getTextSize() / 2,
                    textPaint);
            canvas.drawCircle(c.getPointX(), c.getPointY(), radius - 10, circlePaint);
            c.setSelected(false);
            oldPositionX = newPositionX;
            oldPositionY = newPositionY;

        }
    }


    private void init() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(radius / 2);
        selectPaint = new Paint();
        selectPaint.setColor(Color.DKGRAY);
        selectPaint.setAntiAlias(true);
        selectPaint.setStyle(Paint.Style.FILL);
        selectTextPaint = new Paint();
        selectTextPaint.setColor(Color.WHITE);
        selectTextPaint.setAntiAlias(true);
        selectTextPaint.setTextSize(radius / 2);
        selectTextPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 暴露接口，设置日期
     *
     * @param customDate
     */
    public void setDate(CustomDate customDate) {
        Log.d(TAG, customDate.toString());
        this.date = customDate;
        firstDayOfWeek = date.getFirstDayOfWeek();
        Log.d(TAG, (date.getMonth() + 1) + "月1号是星期" + firstDayOfWeek);
        lastDayOfWeek = date.getLastDayOfWeek();
        lineCount = calculateLineNum() + 1;
        lastMonthTotalDays = date.getLastMonthDays();
    }

    /**
     * 获得应该设置为多少行
     *
     * @return
     */
    private int calculateLineNum() {
        monthDaySum = date.getTotalDayOfMonth();
        return (firstDayOfWeek - 1 + monthDaySum) / 7;
    }

    public enum DayState {
        LASTMONTH, CURRENTMONTH, NEXTMONTH, CURRENTDAY;
    }

    public void setClickCellListener(ClickCellListener listener) {
        this.clickCellListener = listener;
    }

    private class CellDay {
        private String date;
        private DayState dayState;
        private CustomDate customDate;
        private float pointX;
        private float pointY;
        private boolean isSelected;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public DayState getDayState() {
            return dayState;
        }

        public void setDayState(DayState dayState) {
            this.dayState = dayState;
        }

        public CustomDate getCustomDate() {
            return customDate;
        }

        public void setCustomDate(CustomDate customDate) {
            this.customDate = customDate;
        }

        public float getPointX() {
            return pointX;
        }

        public void setPointX(float pointX) {
            this.pointX = pointX;
        }

        public float getPointY() {
            return pointY;
        }

        public void setPointY(float pointY) {
            this.pointY = pointY;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public interface ClickCellListener {
        void onClickCell(CustomDate customDate);
    }
}
