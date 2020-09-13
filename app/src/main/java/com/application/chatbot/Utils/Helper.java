package com.application.chatbot.Utils;

import android.content.Context;
import android.net.Uri;
import android.text.format.DateFormat;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.application.chatbot.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public static final String GROUP_PREFIX = "group";

    public static String getTime(Long milliseconds) {
        return new SimpleDateFormat("kk:mm", Locale.getDefault()).format(new Date(milliseconds));
    }

    public static String TimestampToDate(long ts) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(ts);
        String date = DateFormat.format("dd, MMM yyyy", cal).toString();
        return date;
    }

    public static void loadUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        intentBuilder.addDefaultShareMenuItem();
        intentBuilder.enableUrlBarHiding();
        CustomTabsIntent customTabsIntent = intentBuilder.build();
        customTabsIntent.launchUrl(context, uri);
    }

}
