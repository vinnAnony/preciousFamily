package com.vinnie.preciousfamily;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Copyright (c) 2016 Adediji Adeyinka(tdscientist)
 * All rights reserved
 * Created on 26-Oct-2016
 */

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    public void setStatusBarColor(String color) {
        if (color.equals("blue")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.calendar_selected_date_blue));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.calendar_selected_date_blue, null));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.calendar_selected_date_green));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.calendar_selected_date_green, null));
            }
        }
    }


}
