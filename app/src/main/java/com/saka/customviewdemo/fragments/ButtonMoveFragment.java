package com.saka.customviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.saka.customviewdemo.R;
import com.saka.customviewdemo.views.MyViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonMoveFragment extends Fragment implements
        View.OnTouchListener, View.OnClickListener {
    private static final String TAG = "Button";
    private ImageView button;
    private int screenWidth;
    private int screenHeight;
    private int downX;
    private int downY;

    public ButtonMoveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button_move, container, false);
        button = (ImageView) view.findViewById(R.id.button_move);
        button.setOnClickListener(this);
        button.setOnTouchListener(this);
        return view;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean touchEvent=false;
        if (v.getId() == R.id.button_move) {
            screenWidth = ((MyViewGroup) v.getParent()).getWidth();
            screenHeight = ((MyViewGroup) v.getParent()).getHeight();
            Log.d(TAG, "screenSize=" + screenWidth + "---" + screenHeight);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    Log.d(TAG, "MotionEvent.ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    int touchX = (int) event.getX()-downX;
                    int touchY = (int) event.getY()-downY;
                    Log.d(TAG, "MotionEvent.ACTION_MOVE" + touchX + "---" + touchY);
                    v.layout(touchX + v.getLeft(), touchY + v.getTop(),
                            touchX + v.getRight(), touchY + v.getBottom());
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "MotionEvent.ACTION_UP");
                    break;
            }
        }
        return touchEvent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_move:
                Log.d(TAG, "OnClick");
                break;
        }
    }
}
