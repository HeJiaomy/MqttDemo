package com.example.mqttdemo;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil {

    public static void showMessage(Activity activity,String mainText) {
        Snackbar.make(activity.findViewById(android.R.id.content), mainText, Snackbar.LENGTH_LONG)
                .setAction("Action",null).show();
    }
}
