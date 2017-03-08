package com.saka.customviewdemo.fragments;


import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saka.customviewdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {


    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

}
