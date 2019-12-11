package com.hanjx.samplecode.utils;

import android.animation.ValueAnimator;

import java.lang.reflect.Field;

public class SystemUtils {
    /**
     * 重制动画缩放倍数
     */
    public static void resetAnimatorDurationScale() {
        try {
            Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
            field.setAccessible(true);
            if (field.getFloat(null) == 0) {
                field.setFloat(null, 1);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
