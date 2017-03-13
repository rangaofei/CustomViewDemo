package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.model.CustomDate;
import com.saka.customviewdemo.views.MyCalendar;
import com.saka.customviewdemo.views.MyViewPager;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements
        MyCalendar.ClickCellListener,
        View.OnTouchListener,
        ViewPager.OnPageChangeListener {

    private MyViewPager viewPager;
    //    private CustomDate date;
    private MyCalendar myCalendar;
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    private TextView date;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        view.setOnTouchListener(this);
        myCalendar = (MyCalendar) view.findViewById(R.id.ca_original);
        date = (TextView) view.findViewById(R.id.date_txt_one);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        myCalendar.setClickCellListener(this);
        myCalendar.setDate(new CustomDate(year, month, 1));
        viewPager = (MyViewPager) view.findViewById(R.id.myviewpager);
        viewPager.setContext(getActivity());
        viewPager.setCurrentItem((calendar.get(Calendar.YEAR) - 2000) * 12 + calendar.get(Calendar.MONTH));
        viewPager.addOnPageChangeListener(this);
        date.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
        viewPager.setWeekendHighLight(true);
        return view;
    }

    @Override
    public void onClickCell(CustomDate customDate) {
        Log.d("回调到fragment", customDate.toString());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append(position / 12 + 2000);
        builder.append("年");
        builder.append(position % 12 + 1);
        builder.append("月");
        date.setText(builder.toString());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {

            int year = 2000 + position / 12;
            int month = position % 12;
            int day = 1;

//            date = new CustomDate(year, month, day);

            CalendarContainerFragment fragment = new CalendarContainerFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 10000;
        }
    }
}
