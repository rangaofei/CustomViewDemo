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
import android.widget.Toast;

import com.saka.customviewdemo.model.CustomDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by saka on 2017/3/12.
 */

public class MyViewPager extends ViewPager implements MyCalendar.ClickCellListener {
    private static final String TAG = "MyViewPager";
    private Context context;
    private MyCalendar calendar;
    private Calendar c;
    private int year;
    private int month;
    private List<MyCalendar> myCalendars = new ArrayList<>();
    private CustomDate customeDate;
    private boolean weekendHightLight;
    private float touchRawY = 0;
    private LayoutParams lp;

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
    public boolean showContextMenu() {
//        return super.showContextMenu();
        Log.d(TAG, "showContextMenu");
        return true;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
//        return super.showContextMenuForChild(originalView);
        Log.d(TAG, "showContextMenuForChild");
        return true;
    }


    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean result = super.onInterceptTouchEvent(ev);
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                touchRawY = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "touchRawY=" + touchRawY + ",currentY=" + ev.getY());
//                if (touchRawY - ev.getY() < -150) {
//                    Log.d(TAG, "下滑事件");
//
//                    lp.width = 1080;
//                    lp.height = 100;
//                    calendar.invalidate();
//                }
//                if (touchRawY - ev.getY() > 150) {
//                    Log.d(TAG, "上划事件");
//                }
//                break;
//        }
//        return result;
//    }


    private void init(int width, int height) {
        lp = new LayoutParams();
        CalendarAdapter adapter = new CalendarAdapter(calendar, lp);
        this.setAdapter(adapter);
        c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
    }

    public void setContext(Context context) {
        this.context = context;
        calendar = new MyCalendar(context);
        int width = getWidth();
        int height = getHeight();
        Log.d("viewpager", "width" + width);
        init(width, height);

    }

    public CustomDate getCurrentDate() {
        return this.customeDate;
    }

    public void setWeekendHighLight(boolean b) {
        this.weekendHightLight = b;
    }


    @Override
    public void onClickCell(CustomDate customDate) {
        Toast.makeText(context, customDate.toString(), Toast.LENGTH_SHORT).show();
    }

    private class CalendarAdapter extends PagerAdapter {
        private MyCalendar calendar;
        private LayoutParams lp;

        public CalendarAdapter(MyCalendar calendar) {
            this.calendar = calendar;
        }

        public CalendarAdapter(MyCalendar calendar, LayoutParams lp) {
            this.calendar = calendar;
            this.lp = lp;
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
            if (myCalendars.size() > position) {
                MyCalendar myCalendar = myCalendars.get(position);
                if (myCalendar != null) {
                    return myCalendar;
                }
            }
            calendar = new MyCalendar(context);
            calendar.setClickCellListener(MyViewPager.this);
            customeDate = new CustomDate(position / 12 + 2000, position % 12, 1);
            calendar.setDate(customeDate);
            calendar.setLayoutParams(lp);
            calendar.setWeekendHighLight(weekendHightLight);
            calendar.setSpecialDay(new int[]{10, 20});
            calendar.setCanClickNextOrPreMonth(false);
            while (myCalendars.size() <= position) {
                myCalendars.add(null);
            }
            myCalendars.set(position, calendar);
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
