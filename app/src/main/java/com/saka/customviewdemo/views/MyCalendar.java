package com.saka.customviewdemo.views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.saka.customviewdemo.model.CustomDate;

import java.util.ArrayList;
import java.util.List;

import static com.saka.customviewdemo.views.MyCalendar.DayState.CURRENTMONTH;
import static com.saka.customviewdemo.views.MyCalendar.DayState.NEXTMONTH;
import static com.saka.customviewdemo.views.MyCalendar.DayState.SPECIALDAY;
import static com.saka.customviewdemo.views.MyCalendar.DayState.WEEKEND;

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
    private boolean weekendHighlight;

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
    private CellDay tempCellDay;
    private boolean clearCanvas;
    private int[] specialDays;
    private boolean canClickNextOrPreMonth;


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
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        Log.d(TAG, "onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
        Log.d(TAG, "onSizeChanged" + w + h);
        cutGrid();
        init();
        setCellDay();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDays(canvas, cellDays);
        if (clearCanvas) {
            clearCanvas(canvas);
        }
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
                Log.d(TAG, "rawY= " + touchRawY + ",touchY= " + event.getY());
                float touchX = event.getX();
                float touchY = event.getY();
                if (touchRawY - touchY < -200) {
                    //下滑事件
                    Log.d(TAG, "下滑事件");
//                    setMaxView();
//                    setCellDay();
//                    this.clearCanvas = false;
//                    invalidate();

                    this.layout(0, 0, this.viewWidth, this.viewHeight / 2);

                    invalidate();
                }
                if (touchRawY - touchY > 200) {
                    //上划事件
                    Log.d(TAG, "上划事件");
//                    setMinView();
//                    this.clearCanvas = true;
//                    invalidate();
                }
                if (Math.abs(touchRawX - touchX) < 100 && Math.abs(touchY - touchRawY) < 100) {
                    //点击事件
                    Log.d(TAG, "点击事件");
                    int touchRow = (int) (touchX / cellWidth);
                    int touchLine = (int) (touchY / cellHeight);
                    final int touchId = touchLine * ROW_COUNT + touchRow;
                    if (canClickNextOrPreMonth) {
                        setClickEvent(touchId);
                    } else {
                        if (touchId > firstDayOfWeek - 2 && touchId < monthDaySum + firstDayOfWeek - 1) {
                            setClickEvent(touchId);
                            tempCellDay = cellDays[touchId];
                            tempState = cellDays[touchId].getDayState();
                            cellDays[touchId].setSelected(true);
                            newPositionX = cellDays[touchId].getPointX();
                            newPositionY = cellDays[touchId].getPointY();
                            setselectAnimator(touchId);
                        }
                    }

                }
                break;

        }

        return true;
    }

    private void setClickEvent(int touchId) {
        CustomDate customDate = cellDays[touchId].getCustomDate();
        if (clickCellListener != null) {
            clickCellListener.onClickCell(customDate);
        }
    }

    /**
     * 设置选中的动画效果
     *
     * @param touchId
     */
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
//                tempDate = cellDays[touchId].getDate();
                postInvalidate();
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(selectAnimatorX, selectAnimatorY);
        animatorSet.start();
    }

    /**
     * 清除canvas
     *
     * @param canvas
     */
    private void clearCanvas(Canvas canvas) {

        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC);
        CellDay[] singleLineCellDay = {
                cellDays[0], cellDays[1], cellDays[2],
                cellDays[3], cellDays[4], cellDays[5],
                cellDays[6]
        };
        drawDays(canvas, singleLineCellDay);
    }

    private void setMinView() {
        ViewGroup.LayoutParams lp = this.getLayoutParams();
        this.viewHeight = lp.height;
        this.viewWidth = lp.width;
        this.setLayoutParams(lp);
        cutGrid();
        init();
        setCellDay();
        invalidate();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Y", 0, -viewHeight + 100);
//        animator.setDuration(300);
//        animator.start();
    }

    private void setMaxView() {
        this.setMeasuredDimension(this.viewWidth, this.viewHeight / lineCount);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Y", 100, viewHeight);
//        animator.setDuration(300);
//        animator.start();
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
                //设置周末高亮
                if (weekendHighlight) {
                    if (i % 7 == 0 || i % 7 == 6) {
                        cellDays[i].setDayState(WEEKEND);
                    }
                }
            }
            if (i >= monthDaySum + firstDayOfWeek - 1) {
                cellDays[i].setDayState(NEXTMONTH);
                cellDays[i].setDate(String.valueOf(i - monthDaySum - firstDayOfWeek + 2));
                cellDays[i].setCustomDate(new CustomDate(
                        date.getYear(), date.getMonth() + 1, i - monthDaySum - firstDayOfWeek + 2));
            }
            for (int j = 0, s = specialDays.length; j < s; j++) {
                if (specialDays[j] + firstDayOfWeek - 2 == i) {
                    cellDays[i].setDayState(SPECIALDAY);
                }
            }
        }
    }

    /**
     * 切分为每天
     */
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

    private void drawSelectDay(Canvas canvas, CellDay c) {
        canvas.drawCircle(tempPositionX, tempPositionY, radius - 10, selectPaint);
//        canvas.drawText(c.getDate(),
//                c.getPointX() - textPaint.measureText(c.getDate()) / 2,
//                c.getPointY() + textPaint.getTextSize() / 2,
//                textPaint);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawDays(Canvas canvas, CellDay[] cellDays) {
        for (CellDay c : cellDays) {
            switch (c.getDayState()) {
                case LASTMONTH:
                    if (c.isSelected()) {
                        circlePaint.setColor(Color.TRANSPARENT);
                    } else {
                        circlePaint.setColor(Color.TRANSPARENT);
                        textPaint.setColor(Color.GRAY);
                    }
                    break;
                case CURRENTMONTH:
                    if (c.isSelected()) {
                        circlePaint.setColor(Color.YELLOW);
                        textPaint.setColor(Color.BLACK);
                    } else {
                        circlePaint.setColor(Color.RED);
                        textPaint.setColor(Color.RED);
                        canvas.drawText("班",
                                c.getPointX() + textPaint.measureText(tempDate) / 2,
                                c.getPointY() - textPaint.getTextSize() / 2,
                                textPaint);
                    }
                    break;
                case NEXTMONTH:
                    if (c.isSelected()) {
                        circlePaint.setColor(Color.TRANSPARENT);
                    } else {
                        circlePaint.setColor(Color.TRANSPARENT);
                        textPaint.setColor(Color.GRAY);
                    }
                    break;
                case CURRENTDAY:
                    circlePaint.setColor(Color.YELLOW);
                    textPaint.setColor(Color.BLACK);
                    break;
                case WEEKEND:
                    circlePaint.setColor(Color.CYAN);
                    textPaint.setColor(Color.CYAN);
                    canvas.drawText("休",
                            c.getPointX() + textPaint.measureText(tempDate) / 2,
                            c.getPointY() - textPaint.getTextSize() / 2,
                            textPaint);
                    break;
                case SPECIALDAY:
                    circlePaint.setColor(Color.GREEN);
                    textPaint.setColor(Color.GREEN);
                    canvas.drawText("假",
                            c.getPointX() + textPaint.measureText(tempDate) / 2,
                            c.getPointY() - textPaint.getTextSize() / 2,
                            textPaint);
            }
            canvas.drawCircle(tempPositionX, tempPositionY, radius - 10, selectPaint);
//            canvas.drawText(tempDate,
//                    tempPositionX - textPaint.measureText(tempDate) / 2,
//                    tempPositionY + textPaint.getTextSize() / 2,
//                    selectTextPaint);
            canvas.drawText(c.getDate(),
                    c.getPointX() - textPaint.measureText(c.getDate()) / 2,
                    c.getPointY() + textPaint.getTextSize() / 2,
                    textPaint);
//            canvas.drawCircle(c.getPointX(), c.getPointY(), radius - 10, circlePaint);
            //这个地方改为rectf可以向下兼容
            canvas.drawArc(c.getPointX() - radius + 10,
                    c.getPointY() - radius + 10,
                    c.getPointX() + radius - 10,
                    c.getPointY() + radius - 10,
                    0, 270, false, circlePaint);
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
        selectPaint.setColor(Color.YELLOW);
        selectPaint.setAlpha(10);
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
     * 暴露接口，设置是否周末高亮
     *
     * @param b
     */
    public void setWeekendHighLight(boolean b) {
        this.weekendHighlight = b;
    }

    public void setSpecialDay(int[] ints) {
        this.specialDays = ints;
    }

    /**
     * 暴露接口，设置是否可以点击前一个月和后一个月的日期
     *
     * @param b
     */
    public void setCanClickNextOrPreMonth(boolean b) {
        this.canClickNextOrPreMonth = b;
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
        LASTMONTH, CURRENTMONTH, NEXTMONTH, CURRENTDAY, WEEKEND, SPECIALDAY;
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
