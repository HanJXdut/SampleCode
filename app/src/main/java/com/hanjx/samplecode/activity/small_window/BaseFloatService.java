package com.hanjx.samplecode.activity.small_window;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.hanjx.samplecode.R;

public abstract class BaseFloatService extends Service {

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private ViewGroup container;
    private View childView = null;

    private TextView close;
    private TextView fullScreen;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = getGravity();
        layoutParams.width = getWidth();
        layoutParams.height = getHeight();
        layoutParams.x = getMarginHorizontal();
        layoutParams.y = getMarginVertical();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        removeView();
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        container = (ViewGroup) layoutInflater.inflate(R.layout.view_float_window_container, null);
        if (getFloatViewResId() != 0) {
            childView = layoutInflater.inflate(getFloatViewResId(), container);
            container.addView(childView);
        }
        initListener();
        windowManager.addView(container, layoutParams);
    }

    protected void initListener() {
        close = container.findViewById(R.id.close);
        fullScreen = container.findViewById(R.id.full_screen);
        close.setOnClickListener(v -> {
            removeView();
        });
        fullScreen.setOnClickListener(v -> {

        });

        container.setOnTouchListener(new FloatingOnTouchListener());
    }

    private void removeView() {
        if (container != null) {
            windowManager.removeView(container);
            container = null;
            childView = null;
        }
    }

    protected int getGravity() {
        return Gravity.START|Gravity.TOP;
    }
    protected int getFloatViewResId() {
        return 0;
    }
    protected abstract int getWidth();
    protected abstract int getHeight();
    protected abstract int getMarginHorizontal();
    protected abstract int getMarginVertical();

    protected class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
