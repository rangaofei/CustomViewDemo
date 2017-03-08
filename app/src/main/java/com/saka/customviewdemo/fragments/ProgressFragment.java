package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.views.ArcProgress;
import com.saka.customviewdemo.views.CircleProgress;

import java.util.Random;

import static android.R.attr.button;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {
    private ArcProgress circleProgress;
    private Button button;
    private CircleProgress progress;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        circleProgress = (ArcProgress) view.findViewById(R.id.circleprogress);
        progress = (CircleProgress) view.findViewById(R.id.cprogress);
        button = (Button) view.findViewById(R.id.btn_change);
        circleProgress.setValue(300);
        progress.setValue(10, 10, 10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgress.setValue(new Random().nextInt(360));
                progress.setValue(new Random().nextInt(20), new Random().nextInt(20),
                        new Random().nextInt(20));
            }
        });
        return view;
    }

}
