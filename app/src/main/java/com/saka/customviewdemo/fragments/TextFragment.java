package com.saka.customviewdemo.fragments;


import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.views.SectorButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment implements SectorButton.ClickSectorButton{


    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_text, container, false);
        SectorButton sector= (SectorButton) view.findViewById(R.id.sector_button);
        sector.setClickSectorButton(this);
        return view;
    }

    @Override
    public void onClickOne() {
        Toast.makeText(getActivity(), "点击了左边第一个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickTwo() {
        Toast.makeText(getActivity(), "点击了中间的一个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickThree() {
        Toast.makeText(getActivity(), "点击了右边的一个", Toast.LENGTH_SHORT).show();
    }
}
