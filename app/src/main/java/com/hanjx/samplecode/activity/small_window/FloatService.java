package com.hanjx.samplecode.activity.small_window;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hanjx.samplecode.R;
import com.hanjx.samplecode.view.CountDownView;

public class FloatService extends Service {

    public static final String KEY_RESTART = "key_restart";

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private ViewGroup container;
    protected View childView;

    CountDownView countDownView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        initLayoutParams();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getBooleanExtra(KEY_RESTART, false)) {
                removeAllView();
            }
        }
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initLayoutParams() {
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.TOP|Gravity.START;
        layoutParams.width = 800;
        layoutParams.height = 600;
        layoutParams.x = 0;
        layoutParams.y = 0;
    }

    private void showFloatingWindow() {
        if (container != null) {
            return;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        container = (ViewGroup) layoutInflater.inflate(R.layout.view_float_window_container, null);
        buildChildView();
        initListener();
        windowManager.addView(container, layoutParams);
    }

    protected void removeFloatWindow() {
        removeAllView();
        stopSelf();
    }

    private void buildChildView() {
        if (childView != null) {
            container.removeView(childView);
        }
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        childView = layoutInflater.inflate(R.layout.view_float_child, null);
        FrameLayout.LayoutParams childParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        childParams.gravity = Gravity.CENTER;
        container.addView(childView, childParams);
        if (childView != null) {
            countDownView = childView.findViewById(R.id.countDown);
            childView.setOnClickListener(v -> backToFullScreen());
            countDownView.setDefaultTimerListener();
            countDownView.startCountDown();
        }
    }

    protected void initListener() {
        TextView close = container.findViewById(R.id.close);
        close.setOnClickListener(v -> {
            removeAllView();
            stopSelf();
        });
        container.setOnClickListener(v -> backToFullScreen());
        container.setOnTouchListener(new FloatingOnTouchListener());
    }

    private void removeAllView() {
        if (container != null) {
            windowManager.removeView(container);
            container = null;
            childView = null;
        }
    }

    protected void backToFullScreen() {
        Intent intent = new Intent(this, MyFloatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        removeFloatWindow();
    }

    protected class FloatingOnTouchListener implements View.OnTouchListener {
        private int startX;
        private int startY;

        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    startX = x;
                    startY = y;
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
                case MotionEvent.ACTION_UP:
                    if (startX == (int) event.getRawX() && startY == (int) event.getRawY()) {
                        view.performClick();
                    }
                default:
                    break;
            }
            return true;
        }

    }
}
