package com.hanjx.samplecode.activity.small_window;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hanjx.samplecode.R;

public class FloatWindowsActivity_ApiGuide extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_window_api_guide);
        button = findViewById(R.id.btn_small_window);
        textView = findViewById(R.id.text_small_window);
        button.setOnClickListener(v -> enableSmallWindow());
    }

    @Override
    public void onPictureInPictureModeChanged (boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            button.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

    private void enableSmallWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            enterPictureInPictureMode();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        enableSmallWindow();
    }
}
