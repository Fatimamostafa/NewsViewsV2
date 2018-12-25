package com.fatimamostafa.app.newsviewsv2.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceManager {

    private static SharedPreferences sharedPrefs;
    private static SharedPreferenceManager instance;


    private SharedPreferenceManager(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized SharedPreferenceManager getInstance() {
        return instance;

    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferenceManager(context);

        return instance;
    }

    public static SharedPreferences getSharedPrefs() {
        return sharedPrefs;
    }

    public void clear() {
        sharedPrefs.edit().clear().apply();
    }

    public void removeKey(String key) {
        sharedPrefs.edit().remove(key).apply();
    }

    public void setString(String key, String value) {

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value).apply();
    }

    public String getString(String key) {

        return sharedPrefs.getString(key, "");
    }

    public void setInt(String key, int value) {

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(key, value).apply();
    }

    public int getInt(String key) {

        return sharedPrefs.getInt(key, 0);
    }

    public void setBoolean(String key, Boolean value) {

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {

        return sharedPrefs.getBoolean(key, false);
    }


}
