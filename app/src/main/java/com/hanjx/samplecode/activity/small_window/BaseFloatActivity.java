package com.hanjx.samplecode.activity.small_window;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static com.hanjx.samplecode.utils.PermissionUtils.checkFloatWindowPermission;

@RequiresApi(api = Build.VERSION_CODES.M)
// TODO: 2019-12-12 这个基类有必要吗？ 
public abstract class BaseFloatActivity extends AppCompatActivity {
    // TODO: 2019-12-12 完成离开开启悬浮窗
    private boolean leaveWithFloatWindow = false;

    protected void requestFloatPermission() {
        if (checkFloatWindowPermission()) {
            onPermissionResult(true);
        } else {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (checkFloatWindowPermission()) {
            onPermissionResult(true);
        } else {
            onPermissionResult(false);
        }
    }

    protected void onPermissionResult(boolean allowed) {
        if (allowed) {
            Log.d("hjx", "Permission allowed.");
        } else {
            Log.w("hjx", "Permission denied.");
        }
    }

    public void setLeaveWithFloatWindow(boolean leaveWithFloatWindow) {
        this.leaveWithFloatWindow = leaveWithFloatWindow;
    }
}
