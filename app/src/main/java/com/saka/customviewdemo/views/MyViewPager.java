package com.saka.customviewdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.saka.customviewdemo.model.CustomDate;

/**
 * Created by saka on 2017/3/12.
 */

public class MyViewPager extends ViewPager {
    private Context context;
    private MyCalendar calendar;

    public MyViewPager(Context context) {
        super(context);

    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int width = r - l;
        int height = b - t;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    private void init(int width, int height) {
        ViewPager.LayoutParams params=new ViewPager.LayoutParams();
        calendar.setLayoutParams(params);
        CalendarAdapter adapter = new CalendarAdapter(calendar);
        this.setAdapter(adapter);
    }

    public void setContext(Context context) {
        this.context = context;
        calendar = new MyCalendar(context);
        int width=getWidth();
        int height=getHeight();
        Log.d("viewpager","width"+width);
        init(width, height);

    }

    private class CalendarAdapter extends PagerAdapter {
        private MyCalendar calendar;

        public CalendarAdapter(MyCalendar calendar) {
            this.calendar = calendar;
        }

        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            calendar.setDate(new CustomDate(2017,3,1));
            ViewParent vp = calendar.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(calendar);
            }
            container.addView(calendar);
            return calendar;
        }
    }
}
