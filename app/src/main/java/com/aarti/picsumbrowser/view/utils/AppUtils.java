package com.aarti.picsumbrowser.view.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.aarti.picsumbrowser.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.HashMap;


public class AppUtils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static Snackbar createSnackBar(Context context, View referenceView, String message) {
        final Snackbar snackbar = Snackbar.make(referenceView, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ColorUtils.getColor(context, R.color.black));
        return snackbar;
    }

    public static Snackbar createSnackBar(Context context, View referenceView, String message, int duration) {
        final Snackbar snackbar = Snackbar.make(referenceView, message, duration);
        snackbar.getView().setBackgroundColor(ColorUtils.getColor(context, R.color.black));
        return snackbar;
    }

    public static Snackbar showSnackBarMessage(Context context, View referenceView, String message) {
        final Snackbar snackbar = Snackbar.make(referenceView, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ColorUtils.getColor(context, R.color.black));
        snackbar.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showSnackBarMessage(Context context, View referenceView, String message, int duration) {
        final Snackbar snackbar = Snackbar.make(referenceView, message, duration);
        snackbar.getView().setBackgroundColor(ColorUtils.getColor(context, R.color.black));
        snackbar.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
        return snackbar;
    }

    public static void parseIntentExtras(Intent intent, HashMap<String, Object> extras) {
        for (String key : extras.keySet()) {
            Object extra = extras.get(key);
            if (extra instanceof Boolean) {
                intent.putExtra(key, (boolean) extra);
            } else if (extra instanceof Byte) {
                intent.putExtra(key, (byte) extra);
            } else if (extra instanceof Character) {
                intent.putExtra(key, (char) extra);
            } else if (extra instanceof Short) {
                intent.putExtra(key, (short) extra);
            } else if (extra instanceof Integer) {
                intent.putExtra(key, (int) extra);
            } else if (extra instanceof Long) {
                intent.putExtra(key, (long) extra);
            } else if (extra instanceof Float) {
                intent.putExtra(key, (float) extra);
            } else if (extra instanceof Double) {
                intent.putExtra(key, (double) extra);
            } else if (extra instanceof String) {
                intent.putExtra(key, (String) extra);
            } else if (extra instanceof CharSequence) {
                intent.putExtra(key, (CharSequence) extra);
            } else if (extra instanceof int[]) {
                intent.putExtra(key, (int[]) extra);
            } else if (extra instanceof Integer[]) {
                intent.putExtra(key, (Integer[]) extra);
            } else if (extra instanceof String[]) {
                intent.putExtra(key, (String[]) extra);
            } else if (extra instanceof Parcelable) {
                intent.putExtra(key, (Parcelable) extra);
            } else if (extra instanceof Parcelable[]) {
                intent.putExtra(key, (Parcelable[]) extra);
            } else if (extra instanceof Serializable) {
                intent.putExtra(key, (Serializable) extra);
            }
        }
    }


}
