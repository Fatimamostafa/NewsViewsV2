package com.fatimamostafa.app.newsviewsv2.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.fatimamostafa.app.newsviewsv2.R;


public class Utilities {

    /**
     * Show a forward activity transition
     *
     * @param activity
     */
    public static void showForwardTransition(Activity activity) {
        try {
            activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        } catch (Exception e) {
            Log.e("ActivityForwardTransit", e.getMessage());
        }
    }

    /**
     * Show a forward activity transition
     *
     * @param activity
     */
    public static void showForwardTransitionFadeIn(Activity activity) {
        try {
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
            Log.e("ActivityForwardTransit", e.getMessage());
        }
    }

    /**
     * Show a backward activity transition
     *
     * @param activity
     */
    public static void showBackwardTransition(Activity activity) {
        try {
            activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        } catch (Exception e) {
            Log.e("ActivityBackwardTransit", e.getMessage());
        }
    }

    /**
     * Returns the device width in px
     *
     * @return Device width
     */
    public static int getDeviceWidthInPX(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * Returns the device height in px
     *
     * @return Device height
     */
    public static int getDeviceHeightInPX(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * Returns the device width in dp
     *
     * @return Device width
     */
    public static float getDeviceWidthInDP(Context context) {
        int width = getDeviceWidthInPX(context);
        return px2dip(width, context);
    }

    /**
     * Returns he device height in dp
     *
     * @return Device height
     */
    public static float getDeviceHeightInDP(Context context) {
        int height = getDeviceHeightInPX(context);
        return px2dip(height, context);
    }

    /**
     * Converts DP to PX
     *
     * @param dp
     * @return pixel
     */

    public static int dip2px(float dp, Context ctx) {
        return (int) (dp * ctx.getResources().getDisplayMetrics().density + 0.5);
    }

    /**
     * Converts PX to DP
     *
     * @param px
     * @param context
     * @return dp
     */

    public static float px2dip(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
    }


    // Button Topdown animation

    public static void hideAnimation(Context context, Button button) {

        Animation hide = AnimationUtils.loadAnimation(context, R.anim.slide_down_anim);

        button.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                button.clearAnimation();
                button.setVisibility(View.GONE);

            }

        });

    }


    // Button bottomUp animation
    public static void showAnimation(Context context, Button button) {
        Animation show = AnimationUtils.loadAnimation(context, R.anim.slide_up_anim);

        button.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                button.clearAnimation();

            }

        });
    }

    /**
     * Show Long Toast
     *
     * @param context
     * @param msg
     */
    public static void showLongToast(Context context, String msg) {
        String message = msg;

        if (TextUtils.isEmpty(message))
            message = "";

        if (context != null) {
            try {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("LongToast", e.getMessage() + "");
            }
        } else {
            Log.e("LongToast", "Context Can not be null");
        }
    }

    /**
     * SHow Short Toast
     *
     * @param context
     * @param msg
     */
    public static void showShortToast(Context context, String msg) {
        String message = msg;

        if (TextUtils.isEmpty(message))
            message = "";

        if (context != null) {
            try {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("ShortToast", e.getMessage() + "");
            }
        } else {
            Log.e("ShortToast", "Context Can not be null");
        }
    }


}
