package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.model.CustomDate;
import com.saka.customviewdemo.views.MyCalendar;
import com.saka.customviewdemo.views.MyViewPager;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements MyCalendar.ClickCellListener,View.OnTouchListener{

    private MyViewPager viewPager;
    private CustomDate date;
    private MyCalendar myCalendar;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        view.setOnTouchListener(this);
        myCalendar = (MyCalendar) view.findViewById(R.id.ca_original);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        myCalendar.setClickCellListener(this);
        myCalendar.setDate(new CustomDate(year,month,1));
        viewPager= (MyViewPager) view.findViewById(R.id.myviewpager);
        viewPager.setContext(getActivity());
        return view;
    }

    @Override
    public void onClickCell(CustomDate customDate) {
        Log.d("回调到fragment",customDate.toString());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
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

            date = new CustomDate(year, month, day);

            CalendarContainerFragment fragment = new CalendarContainerFragment().newInstance(date);
            return fragment;
        }

        @Override
        public int getCount() {
            return 10000;
        }
    }
}
