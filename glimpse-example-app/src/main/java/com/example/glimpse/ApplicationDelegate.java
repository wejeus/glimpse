package com.example.glimpse;

import android.app.Application;

import com.isalldigital.glimpse.GlimpseConfig;

public class ApplicationDelegate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlimpseConfig.init(R.mipmap.ic_launcher, R.string.app_name, R.color.colorPrimary, 5);
        GlimpseConfig.setReceiver(NotificationActionReceiver.class);
    }
}
