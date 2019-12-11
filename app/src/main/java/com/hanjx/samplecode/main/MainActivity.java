package com.hanjx.samplecode.main;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.hanjx.samplecode.R;
import com.hanjx.samplecode.utils.SystemUtils;
import com.hanjx.samplecode.view.CountDownView;

public class MainActivity extends AppCompatActivity {

    CountDownView countDownView;
    Button button;
    public static long start = 0;
    public static long end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemUtils.resetAnimatorDurationScale();

        countDownView = findViewById(R.id.countDown);
        button = findViewById(R.id.btn);

        countDownView.setDuration(2000L);
        countDownView.setPaintColor(Color.GRAY);
        countDownView.setPaintWidthDp(4);
        countDownView.setAnimatorTimerListener(currentTime -> {
            String newText = "" + ((countDownView.getDuration() - currentTime) / 1000 + 1);
            countDownView.setText(newText);
        });

        button.setOnClickListener(v -> {
            start = System.currentTimeMillis();
            countDownView.start();
        });
    }
}
