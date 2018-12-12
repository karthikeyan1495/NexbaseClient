package com.spider.star.nexbaseclient;

import android.app.Application;
import android.content.Context;


import com.spider.star.nexbaseclient.font.CustomFontFamily;
import com.spider.star.nexbaseclient.utils.InternetChecker;

public class MyApp extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        MyApp.context = getApplicationContext();
        // this.context = this;
        InternetChecker.getInstance().init();
        CustomFontFamily.getInstance().addAllFont(this);
    }

    public static Context getContext() {
        return MyApp.context;
    }
}
