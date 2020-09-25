package com.bspl.qscanner.extraclass;

import android.app.Activity;
import android.widget.Toast;



public class showToast {


    public static void show(final String text, final Activity activity) {

       activity. runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
}}
