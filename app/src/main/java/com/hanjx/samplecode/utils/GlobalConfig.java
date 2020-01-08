package com.hanjx.samplecode.utils;

import android.content.Context;

public class GlobalConfig {
    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context;
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }
}
