package com.spider.star.nexbaseclient.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {


    private static final Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }


    public int getScreenWidth(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }

    public boolean Mobile_No_Validator(String password)
    {
        final String mobile_validator = "^\\d{10}$";
        Pattern pattern = Pattern.compile(mobile_validator);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
