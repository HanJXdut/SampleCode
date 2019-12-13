package com.hanjx.samplecode.activity.small_window;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.hanjx.samplecode.R;

import static com.hanjx.samplecode.utils.PermissionUtils.checkFloatWindowPermission;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MyFloatActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfloat);
        button = findViewById(R.id.start);
        button.setOnClickListener(v -> {
            openFloatWindow();
        });
    }

    private void requestPermissionWithDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setMessage("开启悬浮窗权限")
                .setCancelable(false)
                .setPositiveButton("去开启", (dialog1, which) -> openFloatSystemSetting())
                .setNegativeButton("暂不开启", (dialog2, which) -> showPermissionSetToast())
                .create();
        dialog.show();
    }

    private void openFloatSystemSetting() {
        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
    }

    private void showPermissionSetToast() {
        Toast.makeText(
                this,
                "稍后您可在 「设置 > 应用 > 权限设置 > 悬浮窗」 中开启悬浮窗权限",
                Toast.LENGTH_LONG)
                .show();
    }

    private void openFloatWindow() {
        if (!checkFloatWindowPermission()) {
            requestPermissionWithDialog();
        } else {
            startFloatService();
        }
    }

    private void startFloatService() {
        startService(new Intent(this, FloatService.class));
    }

    @Override
    protected void onUserLeaveHint() {
        openFloatWindow();
    }

    @Override
    public void onBackPressed() {
        openFloatWindow();
        finish();
    }
}
