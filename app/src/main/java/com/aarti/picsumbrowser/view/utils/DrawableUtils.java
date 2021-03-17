package com.aarti.picsumbrowser.view.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public class DrawableUtils {

    public static Drawable getDrawable (Context context, int resId) {
        return ResourcesCompat.getDrawable(context.getResources(), resId, null);

    }

}
