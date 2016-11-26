package com.isalldigital.glimpse;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

public class BuilderUtils {

    public static NotificationCompat.Builder addDefaultActions(Context context, int id, int requestCode, NotificationCompat.Builder builder) {
        Intent activateContentIntent = new Intent(context, GlimpseConfig.getReceiverClazz())
                .setAction(GlimpseActions.ACTION_ACTIVATE_CONTENT)
                .putExtra(GlimpseActions.EXTRA_NOTIFICATION_ID, id)
                .putExtra(GlimpseActions.EXTRA_REQUEST_CODE, requestCode);
        builder.setContentIntent(PendingIntent.getBroadcast(context, id, activateContentIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        Intent deleteIntent = new Intent(context, GlimpseConfig.getReceiverClazz())
                .setAction(GlimpseActions.ACTION_DELETE)
                .putExtra(GlimpseActions.EXTRA_NOTIFICATION_ID, id)
                .putExtra(GlimpseActions.EXTRA_REQUEST_CODE, requestCode);
        builder.setDeleteIntent(PendingIntent.getBroadcast(context, id, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        return builder;
    }

    /**
     * One and only one PendingIntent will be used per (notification, id) pair (id determines). Actions can be uniquely identified by
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static NotificationCompat.Builder addAction(Context context, int id, int requestCode, NotificationCompat.Builder builder, String action, String label) {
        Intent intent = new Intent(context, GlimpseConfig.getReceiverClazz())
                .putExtra(GlimpseActions.EXTRA_NOTIFICATION_ID, id)
                .putExtra(GlimpseActions.EXTRA_REQUEST_CODE, requestCode)
                .setAction(action);

        NotificationCompat.Action notificationAction = new NotificationCompat.Action.Builder(
                GlimpseConfig.getAppIcon(),
                label,
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();

        return builder.addAction(notificationAction);
    }

    /**
     * Automatically updates the notification view to a loading state (showing progress indicator) when send message action is fired.
     * Issue a new notification to update existing to remove progress indicator. Or just cancel it.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static NotificationCompat.Builder setInlineReplyAction(Context context, int id, int requestCode, NotificationCompat.Builder builder, String action, String label, String hint) {
        Intent intent = new Intent(context, GlimpseConfig.getReceiverClazz())
                .putExtra(GlimpseActions.EXTRA_NOTIFICATION_ID, id)
                .putExtra(GlimpseActions.EXTRA_REQUEST_CODE, requestCode)
                .setAction(action);

        RemoteInput remoteInput = new RemoteInput.Builder(GlimpseActions.EXTRA_INLINE_MESSAGE)
                .setLabel(hint)
                .build();

        NotificationCompat.Action notificationAction = new NotificationCompat.Action.Builder(
                GlimpseConfig.getAppIcon(),
                label,
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addRemoteInput(remoteInput)
                .build();

        return builder.addAction(notificationAction);
    }
}
