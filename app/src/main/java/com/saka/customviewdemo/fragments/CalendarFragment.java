package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.model.CustomDate;
import com.saka.customviewdemo.views.MyCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private MyCalendar myCalendar;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        myCalendar= (MyCalendar) view.findViewById(R.id.mycalendar);
        myCalendar.setDate(new CustomDate(2017,1,1));
        return view;
    }

}
