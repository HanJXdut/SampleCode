package com.hanjx.samplecode.activity.small_window;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyFloatService extends BaseFloatService {
    // TODO: 2019-12-12 考虑新的初始化方式，重写一堆方法有点蠢
    @Override
    protected int getWidth() {
        return 600;
    }

    @Override
    protected int getHeight() {
        return 400;
    }

    @Override
    protected int getMarginHorizontal() {
        return 50;
    }

    @Override
    protected int getMarginVertical() {
        return 100;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
