package com.isalldigital.glimpse;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;


/**
 * Configuration class for Glimpse.
 * Holds basic configuration used by all notifications issued by Glimpse.
 *
 * Note about max number of notifications> there is no MAX Limit defined for the notification that
 * the Android System shows as a whole. But yes, the Android System imposes a restriction of 50 per app.
 * This value can again be modified by the Manufacturers when they implement their custom skin,
 * like TouchWiz (Samsung), Sense (HTC), MIUI (Xiaomi), EMUI (Huawei), etc.
 * After 50 notifications most manufacturers roll over and return 0 when trying to get notification id
 *
 * @See http://stackoverflow.com/questions/33364368/android-system-notification-limit-per-app
 *
 */
public class GlimpseConfig {

    public static final String packageName = "com.isalldigital.glimpse";

    public static final int MAX_NUM_CONCURRENT_NOTIFICATIONS_ALLOWED_BY_SYSTEM = 50;

    private static int appIcon;
    private static int appName;
    private static int appPrimaryColor;
    private static int maxNumNotifications;
    private static Class<?> receiverClazz;

    public static void init(@DrawableRes int appIcon, @StringRes int appName, @ColorRes int appPrimaryColor, int maxNumNotifications) {
        GlimpseConfig.appIcon = appIcon;
        GlimpseConfig.appName = appName;
        GlimpseConfig.appPrimaryColor = appPrimaryColor;

        if (maxNumNotifications > MAX_NUM_CONCURRENT_NOTIFICATIONS_ALLOWED_BY_SYSTEM) {
            throw new RuntimeException("Will not be able to handle that many concurrent notifications!");
        }

        GlimpseConfig.maxNumNotifications = maxNumNotifications;
    }

    public static void setReceiver(Class<?> clazz) {
        GlimpseConfig.receiverClazz = clazz;
    }

    public static int getAppIcon() {
        return appIcon;
    }

    public static int getAppName() {
        return appName;
    }

    public static int getAppPrimaryColor() {
        return appPrimaryColor;
    }

    public static int getMaxNumNotifications() {
        return maxNumNotifications;
    }

    public static Class<?> getReceiverClazz() {
        return receiverClazz;
    }
}
