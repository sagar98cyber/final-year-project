package com.application.chatbot.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class GeneralUtils {

    private static String lastFileName = "";
    private static int lastFileNameIndex;

    public static void removeToolbarFlags(Activity activity, Toolbar toolbar) {
        if (toolbar != null) {
            final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(0);
            toolbar.setLayoutParams(params);
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        }
    }

    public static void setToolbarFlags(Activity activity, Toolbar toolbar) {
        if (toolbar != null) {
            final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        }
    }

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int pxToDp(Context context, int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

}
