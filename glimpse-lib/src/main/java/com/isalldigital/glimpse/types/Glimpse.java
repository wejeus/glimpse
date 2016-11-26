package com.isalldigital.glimpse.types;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.isalldigital.glimpse.BuilderUtils;
import com.isalldigital.glimpse.GlimpseConfig;
import com.isalldigital.glimpse.GlimpseManager;

public class Glimpse {

    private Context context;
    private int id;
    private int requestCode;
    private NotificationCompat.Builder notificationBuilder;

    public Glimpse(Context context, int id, int requestCode, NotificationCompat.Builder builder) {
        this.context = context.getApplicationContext();
        this.id = id;
        this.requestCode = requestCode;
        this.notificationBuilder = builder;

        notificationBuilder.setSmallIcon(GlimpseConfig.getAppIcon());
        notificationBuilder.setColor(ContextCompat.getColor(context, GlimpseConfig.getAppPrimaryColor()));
        BuilderUtils.addDefaultActions(context, id, requestCode, builder);
    }

    public int send() {
        GlimpseManager.getInstance(context).sendNotification(id, notificationBuilder);
        return id;
    }
}
