package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.views.MyClock;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment {
    private MyClock myClock;

    public ClockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_clock, container, false);
        myClock= (MyClock) view.findViewById(R.id.clock);
        myClock.startClock();
        return view;
    }

}
