package com.hanjx.samplecode.utils;

import android.content.Context;

import java.lang.ref.WeakReference;

public class GlobalConfig {
    static WeakReference<Context> appContextContainer;
    public static void setAppContextContainer(Context context) {
        appContextContainer = new WeakReference<>(context);
    }

    static Context getApplicationContext() {
        return appContextContainer != null ? appContextContainer.get() : null;
    }
}
