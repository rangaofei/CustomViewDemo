package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.model.CustomDate;
import com.saka.customviewdemo.views.MyCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarContainerFragment extends Fragment implements MyCalendar.ClickCellListener
      {
    private MyCalendar calendar;
    private int year;
    private int month;
    private int day;
    private TextView text;

    public CalendarContainerFragment newInstance(CustomDate date) {
        CalendarContainerFragment fragment = new CalendarContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year", date.getYear());
        bundle.putInt("month", date.getMonth());
        bundle.putInt("day", date.getDay());
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_container, container, false);
        calendar = (MyCalendar) view.findViewById(R.id.viewpager_calendar);
        text= (TextView) view.findViewById(R.id.date_txt);
        StringBuilder builder=new StringBuilder();
        builder.append(year);
        builder.append("年");
        builder.append(month+1);
        builder.append("月");
        text.setText(builder);
        calendar.setClickCellListener(this);

        init();
        return view;
    }

    private void init() {
        calendar.setDate(new CustomDate(year, month, day));
    }

    @Override
    public void onClickCell(CustomDate customDate) {
        Toast.makeText(getActivity(), customDate.toString(), Toast.LENGTH_SHORT).show();
    }

}
