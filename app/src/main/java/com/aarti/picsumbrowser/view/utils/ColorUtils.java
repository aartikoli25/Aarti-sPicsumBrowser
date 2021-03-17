package com.aarti.picsumbrowser.view.utils;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.res.ResourcesCompat;

public class ColorUtils {

    public static int getColor (Context context, int resId) {
        return ResourcesCompat.getColor(context.getResources(), resId, null);

    }

    public static boolean isBrightColor (int color) {
        if (android.R.color.transparent == color) {
            return true;
        }
        int[] rgb = { Color.red(color), Color.green(color), Color.blue(color) };
        int brightness = (int) Math.sqrt(rgb[0] * rgb[0] * 0.241 + rgb[1] * rgb[1] * 0.691 + rgb[2] * rgb[2] * 0.068);
        return brightness >= 200;
    }

    public static int getDarkerColor (int color) {
        float factor = 0.8f;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.parseColor("#212121");
    }

}
