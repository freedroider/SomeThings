package com.freedroider.jstempproject.ui.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.freedroider.jstempproject.R;
import com.freedroider.jstempproject.utils.ConverterUtils;

@SuppressWarnings("unused")
public class ArcProgressView extends View {
    private static final int LOW_VALUE = 0;
    private static final int HIGH_VALUE = 360;
    private static final int START_DEGREE = 270;
    private static final long DEFAULT_VALUE_ANIMATION_DURATION = 2700;
    private static final long DEFAULT_VALUE_ANIMATION_DELAY = 150;
    private static final long DEFAULT_SCALE_ANIMATION_DURATION = 1200;
    private static final long DEFAULT_SCALE_ANIMATION_DELAY = 300;
    private static final int DEFAULT_SCALE_MIN = 0;
    private static final int DEFAULT_SCALE_MAX = 100;
    @ColorInt
    private static final int DEFAULT_SCALE_COLOR = Color.parseColor("#F0F0F0");
    @ColorInt
    private static final int DEFAULT_VALUE_COLOR = Color.parseColor("#5ACA74");
    private static final int DEFAULT_PAINT_WIDTH = 15;
    private static final int DEFAULT_VALUE_TEXT_SIZE = 50;

    private final ValueAnimator scaleAnimator = ValueAnimator.ofFloat(LOW_VALUE, HIGH_VALUE);
    private final ValueAnimator valueAnimator = ValueAnimator.ofFloat();
    private final RectF oval = new RectF();
    private final Paint scalePaint = new Paint();
    private final Paint valuePaint = new Paint();
    private final Paint textValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @ColorInt
    private int scalePaintColor, valuePaintColor;
    private int scaleMin, scaleMax;
    private float scaleSweepAngle, valueSweepAngle, scalePaintWidth, valuePaintWidth, valueTextSize;
    private long degreeAnimationDuration;
    private float ratio;
    private int value;
    private boolean isScaleAnimationFinished, isValueAnimatorWaitingForStart;

    public ArcProgressView(Context context) {
        super(context);
        parseAttrs(null);
        init();
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        oval.set(scalePaintWidth / 2, scalePaintWidth / 2, getWidth() - (scalePaintWidth / 2),
                getHeight() - (scalePaintWidth / 2));
        scaleAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(oval, START_DEGREE, scaleSweepAngle, false, scalePaint);
        if (isScaleAnimationFinished) {
            canvas.drawArc(oval, START_DEGREE, valueSweepAngle, false, valuePaint);
            canvas.drawText(String.valueOf(Math.round(valueSweepAngle / ratio)), oval.centerX(),
                    oval.centerY() + (valueTextSize / 3), textValuePaint);
        }
    }

    public void setValue(int value) {
        this.value = value;
        if (isScaleAnimationFinished) {
            animateProgress();
        } else {
            isValueAnimatorWaitingForStart = true;
        }
    }

    public int getValue() {
        return value;
    }

    public void setScaleMax(int scaleMax) {
        this.scaleMax = scaleMax;
        calculateRatio(scaleMin, scaleMax);
    }

    public void setScaleMin(int scaleMin) {
        this.scaleMin = scaleMin;
        calculateRatio(scaleMin, scaleMax);
    }

    private void parseAttrs(@Nullable AttributeSet attrs) {
        if (attrs == null) {
            defaultSettings();
            return;
        }
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.ArcProgressView, 0, 0);
        try {
            customSettings(typedArray);
        } finally {
            typedArray.recycle();
        }
    }

    private void customSettings(@NonNull TypedArray typedArray) {
        int valueAnimationDuration;
        scalePaintColor = typedArray.getColor(R.styleable.ArcProgressView_scale_color,
                DEFAULT_SCALE_COLOR);
        valuePaintColor = typedArray.getColor(R.styleable.ArcProgressView_value_color,
                DEFAULT_VALUE_COLOR);
        scalePaintWidth = typedArray.getDimensionPixelSize(R.styleable.ArcProgressView_scale_width,
                ConverterUtils.dpToPx(DEFAULT_PAINT_WIDTH));
        valuePaintWidth = typedArray.getDimensionPixelSize(R.styleable.ArcProgressView_value_width,
                ConverterUtils.dpToPx(DEFAULT_PAINT_WIDTH));
        valueTextSize = typedArray.getDimensionPixelSize(R.styleable.ArcProgressView_text_value_size,
                (int) ConverterUtils.spToPx(DEFAULT_VALUE_TEXT_SIZE, getContext()));
        scaleMin = typedArray.getInteger(R.styleable.ArcProgressView_scale_min,
                DEFAULT_SCALE_MIN);
        scaleMax = typedArray.getInteger(R.styleable.ArcProgressView_scale_max,
                DEFAULT_SCALE_MAX);
        valueAnimationDuration = typedArray.getInteger(R.styleable.ArcProgressView_value_animation_duration,
                (int) DEFAULT_VALUE_ANIMATION_DURATION);
        degreeAnimationDuration = valueAnimationDuration / HIGH_VALUE;
    }

    private void defaultSettings() {
        scalePaintWidth = ConverterUtils.dpToPx(DEFAULT_PAINT_WIDTH);
        valuePaintWidth = ConverterUtils.dpToPx(DEFAULT_PAINT_WIDTH);
        valueTextSize = ConverterUtils.spToPx(DEFAULT_VALUE_TEXT_SIZE, getContext());
        degreeAnimationDuration = DEFAULT_VALUE_ANIMATION_DURATION / HIGH_VALUE;
        scalePaintColor = DEFAULT_SCALE_COLOR;
        valuePaintColor = DEFAULT_VALUE_COLOR;
        scaleMin = DEFAULT_SCALE_MIN;
        scaleMax = DEFAULT_SCALE_MAX;
    }

    private void init() {
        calculateRatio(scaleMin, scaleMax);
        initPaints();
        prepareAnimations();
    }

    private void prepareAnimations() {
        scaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimator.setDuration(DEFAULT_SCALE_ANIMATION_DURATION);
        scaleAnimator.setStartDelay(DEFAULT_SCALE_ANIMATION_DELAY);
        scaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isScaleAnimationFinished = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isScaleAnimationFinished = true;
                if (isValueAnimatorWaitingForStart) {
                    animateProgress();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleSweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setStartDelay(DEFAULT_VALUE_ANIMATION_DELAY);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                valueSweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void initPaints() {
        //Init scale paint
        scalePaint.setColor(scalePaintColor);
        scalePaint.setStrokeWidth(scalePaintWidth);
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.STROKE);
        //Init value paint
        valuePaint.setColor(valuePaintColor);
        valuePaint.setStrokeWidth(valuePaintWidth);
        valuePaint.setStrokeCap(Paint.Cap.ROUND);
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.STROKE);
        //Text value paint
        textValuePaint.setColor(valuePaintColor);
        textValuePaint.setTextSize(valueTextSize);
        textValuePaint.setStyle(Paint.Style.STROKE);
        textValuePaint.setTextAlign(Paint.Align.CENTER);
    }

    private void calculateRatio(int scaleMin, int scaleMax) {
        if (valueAnimator.isStarted() || valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        ratio = (float) HIGH_VALUE / (scaleMax - scaleMin);
    }

    private void animateProgress() {
        final float newSweepAngle = value * ratio;
        final float oldValueSweepAngle = (float) (valueAnimator.getAnimatedValue() == null ? 0f
                : valueAnimator.getAnimatedValue());
        if (oldValueSweepAngle == newSweepAngle) {
            return;
        }
        final long duration = (long) Math.abs(oldValueSweepAngle - newSweepAngle)
                * degreeAnimationDuration;
        isValueAnimatorWaitingForStart = false;
        valueAnimator.cancel();
        valueAnimator.setFloatValues(oldValueSweepAngle, newSweepAngle);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
