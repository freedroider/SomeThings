package com.freedroider.jstempproject.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class ConverterUtils {

    public static float spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static int dpToPx(int dp) {
        return (int) dpToPx((float) dp);
    }

    public static float pxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }
}
