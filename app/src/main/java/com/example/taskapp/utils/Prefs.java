package com.example.taskapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveShowStatus(){
        preferences.edit().putBoolean("isShow", true).apply();
    }

    public boolean isShown(){
        return preferences.getBoolean("isShow",false);
    }

}
