package com.hanjx.samplecode.utils;

import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

public class PermissionUtils {

    // 悬浮窗权限
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkFloatWindowPermission() {
        return Settings.canDrawOverlays(GlobalConfig.getApplicationContext());
    }
}
