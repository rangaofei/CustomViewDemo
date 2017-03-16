package com.saka.customviewdemo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import com.saka.customviewdemo.fragments.ButtonMoveFragment;
import com.saka.customviewdemo.fragments.CalendarFragment;
import com.saka.customviewdemo.fragments.CircleFragment;
import com.saka.customviewdemo.fragments.ClockFragment;
import com.saka.customviewdemo.fragments.ProgressFragment;
import com.saka.customviewdemo.fragments.ScrollFragment;
import com.saka.customviewdemo.fragments.TextFragment;
import com.saka.customviewdemo.views.MyCalendar;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.content, new CircleFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.content, new ProgressFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.content, new ClockFragment()).commit();
                    return true;
                case R.id.navigation_third:
                    ft=fm.beginTransaction();
                    ft.replace(R.id.content,new CalendarFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
