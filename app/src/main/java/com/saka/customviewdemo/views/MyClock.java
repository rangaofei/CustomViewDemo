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
import android.view.animation.LinearInterpolator;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class MyClock extends View {

    private float passMinuteArc;
    private float passSecondArc;
    private float passHourArc;
    private int width;
    private int height;
    private Paint bgPaint;
    private Paint boldNumPaint;
    private Paint thinNumPaint;
    private Paint secondPaint;
    private Paint centerPaint;
    private Paint innerPaint;
    private RectF outCircle;
    private RectF inCircle;
    private RectF innerCircle;
    private float radius;
    private MyTime myTime;
    private ValueAnimator animatorSecond;
    private ValueAnimator animatorMinute;
    private ValueAnimator animatorHour;
    private static final String TAG = "Clock";
    private static final float threeSqure = 1.7320508075689F;
    private static final float PIE = 3.1415926535898F;

    public MyClock(Context context) {
        super(context);
        init();
    }

    public MyClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = Math.min(w, h);
        this.height = Math.min(w, h);
        inCircle = new RectF(55, 55, width - 55, height - 55);
        outCircle = new RectF(5, 5, width - 5, height - 5);
        radius = (float) ((width - 110) / 2);
        innerCircle = new RectF(100, 100, width - 100, height - 100);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        super.onDraw(canvas);
        drawBackGround(canvas);
        draw0369(canvas);
        drawHourGap(canvas);
        drawInnerCircle(canvas);
        drawM(canvas);
        drawS(canvas);
        drawH(canvas);
        drawCenter(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(Color.BLACK);
        bgPaint.setStrokeWidth(10);
        bgPaint.setAntiAlias(true);
        boldNumPaint = new Paint();
        boldNumPaint.setStyle(Paint.Style.STROKE);
        boldNumPaint.setColor(Color.BLACK);
        boldNumPaint.setStrokeWidth(20);
        boldNumPaint.setAntiAlias(true);
        thinNumPaint = new Paint();
        thinNumPaint.setStyle(Paint.Style.STROKE);
        thinNumPaint.setColor(Color.BLACK);
        thinNumPaint.setStrokeWidth(10);
        thinNumPaint.setAntiAlias(true);
        secondPaint = new Paint();
        secondPaint.setStyle(Paint.Style.FILL);
        secondPaint.setColor(Color.GREEN);
        secondPaint.setAntiAlias(true);
        secondPaint.setStrokeWidth(10);
        centerPaint = new Paint();
        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setColor(Color.BLACK);
        centerPaint.setAntiAlias(true);
        innerPaint = new Paint();
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(Color.WHITE);
        innerPaint.setAntiAlias(true);
    }

    private void drawBackGround(Canvas canvas) {
        bgPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, bgPaint);
        bgPaint.setColor(Color.BLACK);
        bgPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(outCircle, 0, 360, false, bgPaint);
        canvas.drawArc(inCircle, 0, 360, false, bgPaint);
    }

    private void draw0369(Canvas canvas) {
        canvas.drawLine(width / 2, 55, width / 2, height - 55, boldNumPaint);
        canvas.drawLine(55, height / 2, width - 55, height / 2, boldNumPaint);
    }

    private void drawHourGap(Canvas canvas) {
        canvas.drawLine(radius * (1 - threeSqure / 2) + 55,
                (height - radius) / 2,
                width - 55 - radius * (1 - threeSqure / 2),
                (height + radius) / 2, thinNumPaint);
        canvas.drawLine(radius * (1 - threeSqure / 2) + 55,
                (height + radius) / 2,
                width - 55 - radius * (1 - threeSqure / 2),
                (height - radius) / 2, thinNumPaint);
        canvas.drawLine(radius / 2 + 55,
                height / 2 - radius * threeSqure / 2,
                width - 55 - radius / 2,
                height / 2 + radius * threeSqure / 2, thinNumPaint);
        canvas.drawLine(radius / 2 + 55,
                height / 2 + radius * threeSqure / 2,
                width - 55 - radius / 2,
                height / 2 - radius * threeSqure / 2, thinNumPaint);
    }

    private void drawInnerCircle(Canvas canvas) {
        canvas.drawArc(innerCircle, 0, 360, true, innerPaint);
    }

    private void drawCenter(Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, 20, centerPaint);
    }

    private void drawS(final Canvas canvas) {
        secondPaint.setColor(Color.GREEN);
        secondPaint.setStrokeWidth(10);
        canvas.drawLine(width / 2,
                height / 2,
                height / 2 - (radius - 20) * (float) Math.cos(passSecondArc),
                width / 2 - (radius - 20) * (float) Math.sin(passSecondArc),
                secondPaint);
    }

    private void drawM(Canvas canvas) {
        secondPaint.setColor(Color.BLUE);
        secondPaint.setStrokeWidth(20);
        canvas.drawLine(width / 2, height / 2,
                height / 2 - (radius - 80) * (float) Math.cos(passMinuteArc),
                width / 2 - (radius - 80) * (float) Math.sin(passMinuteArc),
                secondPaint);
    }

    private void drawH(Canvas canvas) {
        secondPaint.setColor(Color.BLACK);
        secondPaint.setStrokeWidth(30);
        canvas.drawLine(width / 2, height / 2,
                height / 2 - (radius - 140) * (float) Math.cos(passHourArc),
                width / 2 - (radius - 140) * (float) Math.sin(passHourArc),
                secondPaint);

    }

    private float setSecond(MyTime myTime) {
        float passSecond = myTime.getSec();
        return 6 * passSecond / 180 * PIE + PIE / 2;
    }

    private float setMinute(MyTime myTime) {
        float passMinute = myTime.getMin() * 6 + myTime.getSec() / 10;
        return passMinute / 180 * PIE + PIE / 2;
    }

    private float setHour(MyTime myTime) {
        float passHour = myTime.getHour() * 30 + myTime.getMin() / 2;
        return passHour / 180 * PIE + PIE / 2;
    }

    public void startClock() {
        myTime = new MyTime();
        Log.d(TAG, myTime.toString());
        animatorSecond = ValueAnimator.ofFloat(setSecond(myTime), setSecond(myTime) + 2 * 60 * PIE);
        animatorMinute = ValueAnimator.ofFloat(setMinute(myTime), setMinute(myTime) + 2 * PIE);
        animatorHour = ValueAnimator.ofFloat(setHour(myTime), setHour(myTime) + 6 * PIE / 180);

        animatorSecond.removeAllUpdateListeners();
        animatorMinute.removeAllUpdateListeners();
        animatorHour.removeAllUpdateListeners();

        animatorSecond.setDuration(60 * 1000 * 60);
        animatorMinute.setDuration(60 * 1000 * 60);
        animatorHour.setDuration(60 * 1000 * 60);

        animatorSecond.setInterpolator(new LinearInterpolator());
        animatorMinute.setInterpolator(new LinearInterpolator());
        animatorHour.setInterpolator(new LinearInterpolator());

        animatorSecond.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                passSecondArc = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        animatorMinute.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                passMinuteArc = (float) animation.getAnimatedValue();
            }
        });

        animatorHour.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                passHourArc = (float) animation.getAnimatedValue();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.removeAllListeners();
        set.playTogether(animatorSecond, animatorMinute, animatorHour);
        set.start();

    }

    class MyTime {
        private int year;
        private int month;
        private int day;
        private int hour;
        private int min;
        private int sec;

        public MyTime() {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
            sec = calendar.get(Calendar.SECOND);
        }

        public MyTime(int year, int month, int day, int hour, int min, int sec) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.min = min;
            this.sec = sec;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getSec() {
            return sec;
        }

        public void setSec(int sec) {
            this.sec = sec;
        }

        @Override
        public String toString() {
            return "MyTime{" +
                    "year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    ", hour=" + hour +
                    ", min=" + min +
                    ", sec=" + sec +
                    '}';
        }
    }
}
