package com.greatlove.musicplay.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.greatlove.musicplay.utils.Constants.PREFS_NAME;

/**
 * Created by sagar on 9/29/2019.
 */
public class AppMethods {

    public static boolean setStringPreference(Activity activity, String key, String value) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getStringPreference(Activity activity, String key)
    {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key,"");
    }

    public static boolean setBooleanPreference(Activity activity, String key, boolean value) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBooleanPreference(Activity activity, String key)
    {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key,false);
    }
}
