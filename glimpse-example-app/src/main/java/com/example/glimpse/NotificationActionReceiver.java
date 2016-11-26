package com.example.glimpse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.isalldigital.glimpse.GlimpseConfig;
import com.isalldigital.glimpse.GlimpseActions;
import com.isalldigital.glimpse.GlimpseManager;

public class NotificationActionReceiver extends BroadcastReceiver {

    public static final int SIMPLE_NOTIFICATION_REQUEST_CODE = 1;
    public static final int SIMPLE_NOTIFICATION_WITH_ACTION_REQUEST_CODE = 2;


    @Override
    public void onReceive(final Context context, Intent intent) {
        GlimpseActions actionHandler = new GlimpseActions() {
            @Override
            public void onActivateContent(int id) {
                Toast.makeText(context, "activate", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(int id) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
            }
        };

        GlimpseActions requestWithActionHandler = new GlimpseActions() {
            @Override
            public void onAction1(int id) {
                Toast.makeText(context, "action 1", Toast.LENGTH_SHORT).show();
            }
        };

        GlimpseManager.getInstance(context).setActionHandler(SIMPLE_NOTIFICATION_REQUEST_CODE, actionHandler);
        GlimpseManager.getInstance(context).setActionHandler(SIMPLE_NOTIFICATION_WITH_ACTION_REQUEST_CODE, requestWithActionHandler);
        GlimpseManager.getInstance(context).handlePendingAction(intent);
    }

    private void addStartActivityAction() {
//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(appContext, MainActivity.class);
//
//        // The stack builder object will contain an artificial back stack for the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(appContext);
//
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(MainActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//
//        // Open app intent
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1001, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);
    }

}
