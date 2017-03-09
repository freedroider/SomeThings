package com.chisw.test;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class InterpolatorFactory {
    public static final String ACCELERATE = "Accelerate";
    public static final String ACCELERATE_DECELERATE = "Accelerate-Decelerate";
    public static final String ANTICIPATE = "Anticipate";
    public static final String ANTICIPATE_OVERSHOOT = "Anticipate-Overshoot";
    public static final String BOUNCE = "Bounce";
    public static final String CYCLE = "Cycle";
    public static final String DECELERATE = "Decelerate";
    public static final String FAST_OUT_LINEAR_IN = "Fast Out Linear In";
    public static final String FAST_OUT_SLOW_IN = "Fast Out Slow In";
    public static final String LINEAR = "Linear";
    public static final String LINEAR_OUT_SLOW_IN = "Linear Out Slow In";
    public static final String OVERSHOOT = "Overshoot";

    public static Interpolator getInstance(String type) {
        switch (type) {
            case ACCELERATE:
                return new AccelerateInterpolator();
            case ACCELERATE_DECELERATE:
                return new AccelerateDecelerateInterpolator();
            case ANTICIPATE:
                return new AnticipateInterpolator();
            case ANTICIPATE_OVERSHOOT:
                return new AnticipateOvershootInterpolator();
            case BOUNCE:
                return new BounceInterpolator();
            case CYCLE:
                return new CycleInterpolator(2);
            case DECELERATE:
                return new DecelerateInterpolator();
            case FAST_OUT_LINEAR_IN:
                return new FastOutLinearInInterpolator();
            case FAST_OUT_SLOW_IN:
                return new FastOutSlowInInterpolator();
            case LINEAR:
                return new LinearInterpolator();
            case LINEAR_OUT_SLOW_IN:
                return new LinearOutSlowInInterpolator();
            case OVERSHOOT:
                return new OvershootInterpolator();
            default:
                throw new IllegalArgumentException();
        }
    }
}
