package com.hanjx.samplecode.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import static com.hanjx.samplecode.utils.DensityUtils.dp2px;

public class CountDownView extends AppCompatTextView {

    // 倒计时时间
    private long duration = 2000L;
    // 圆环颜色
    private int paintColor = Color.BLACK;
    // 圆环宽度
    private int paintWidthDp = 2;
    // 圆环内边距
    private int circlePaddingDp = 4;

    // 已经转过的角度
    private int sweepAngle = 0;

    private ValueAnimator animator;
    private final RectF mRect = new RectF();
    private final Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 时间更新回调
    private AnimatorTimerListener animatorTimerListener;

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setColor(paintColor);
        mBackgroundPaint.setStrokeWidth(dp2px(paintWidthDp));
    }

    public void startCountDown() {
        if (sweepAngle != 0) {
            animator.cancel();
            sweepAngle = 0;
        }
        init();
        //  初始化属性动画
        animator = ValueAnimator.ofInt(0, 360).setDuration(duration);
        // 设置插值
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            // 重绘
            sweepAngle = (int) animation.getAnimatedValue();
            if (animatorTimerListener != null) {
                animatorTimerListener.onTimeUpdate(animation.getCurrentPlayTime());
            }
            invalidate();
        });
        // 开始动画
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int padding = dp2px(circlePaddingDp);
        mRect.top = padding;
        mRect.left = padding;
        mRect.right = getWidth() - padding;
        mRect.bottom = getHeight() - padding;

        // 画倒计时线内圆
        canvas.drawArc(mRect, -90, sweepAngle, false, mBackgroundPaint);
        super.onDraw(canvas);

        if (sweepAngle == 360) {
            sweepAngle = 0;
        }
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public void setPaintWidthDp(int paintWidthDp) {
        this.paintWidthDp = paintWidthDp;
    }

    public void setCirclePaddingDp(int circlePaddingDp) {
        this.circlePaddingDp = circlePaddingDp;
    }

    public long getDuration() {
        return duration;
    }

    public int getSweepAngle() {
        return sweepAngle;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public int getPaintWidthDp() {
        return paintWidthDp;
    }

    public int getCirclePaddingDp() {
        return circlePaddingDp;
    }

    public void setAnimatorTimerListener(AnimatorTimerListener animatorTimerListener) {
        this.animatorTimerListener = animatorTimerListener;
    }

    public void setDefaultTimerListener() {
        this.animatorTimerListener = new NumberCountDownListener();
    }

    public interface AnimatorTimerListener {
        void onTimeUpdate(Long currentTime);
    }

    protected class NumberCountDownListener implements AnimatorTimerListener {
        @Override
        public void onTimeUpdate(Long currentTime) {
            String newText = "" + ((int) Math.floor((double) (getDuration() - currentTime) / 1000) + 1);
            setText(newText);
        }
    }
}
