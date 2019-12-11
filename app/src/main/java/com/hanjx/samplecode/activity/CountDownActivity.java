package com.hanjx.samplecode.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.hanjx.samplecode.R;
import com.hanjx.samplecode.utils.SystemUtils;
import com.hanjx.samplecode.view.CountDownView;

public class CountDownActivity extends AppCompatActivity {
    private CountDownView countDownView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
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

        button.setOnClickListener(v -> countDownView.start());
    }
}
