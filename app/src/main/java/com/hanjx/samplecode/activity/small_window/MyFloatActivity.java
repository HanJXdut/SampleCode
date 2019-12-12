package com.hanjx.samplecode.activity.small_window;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hanjx.samplecode.R;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MyFloatActivity extends BaseFloatActivity {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfloat);
        button = findViewById(R.id.start);
        button.setOnClickListener(v -> {
            requestFloatPermission();
        });
    }

    @Override
    protected void onPermissionResult(boolean allowed) {
        super.onPermissionResult(allowed);
        if (allowed) {
            startFloatService();
        } else {
            Toast.makeText(this, "权限请求失败", Toast.LENGTH_LONG);
        }
    }

    private void startFloatService() {
        startService(new Intent(this, MyFloatService.class));
    }
}
