package com.hanjx.samplecode.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class FloatLayout extends FrameLayout {
    public FloatLayout(@NonNull Context context) {
        super(context);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
