package com.example.glimpse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("id", id);
                context.startActivity(i);
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
}
