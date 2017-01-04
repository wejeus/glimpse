package com.isalldigital.glimpse;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import static com.isalldigital.glimpse.GlimpseActions.ACTION_1;
import static com.isalldigital.glimpse.GlimpseActions.ACTION_2;
import static com.isalldigital.glimpse.GlimpseActions.ACTION_3;
import static com.isalldigital.glimpse.GlimpseActions.ACTION_ACTIVATE_CONTENT;
import static com.isalldigital.glimpse.GlimpseActions.ACTION_DELETE;
import static com.isalldigital.glimpse.GlimpseActions.ACTION_INLINE_REPLY;
import static com.isalldigital.glimpse.GlimpseActions.EXTRA_INLINE_MESSAGE;
import static com.isalldigital.glimpse.GlimpseActions.EXTRA_NOTIFICATION_ID;
import static com.isalldigital.glimpse.GlimpseActions.EXTRA_REQUEST_CODE;
import static com.isalldigital.glimpse.GlimpseActions.NOT_SET;


public class GlimpseManager {

    private static final String PREF_STATE_NOTIFICATION_QUEUE = "pref_state_notification_queue";

    private static GlimpseManager instance;
    private final Context context;
    private Queue<Integer> notificationsQueue = new LinkedBlockingQueue<>(GlimpseConfig.getMaxNumNotifications());

    public static synchronized GlimpseManager getInstance(Context context) {
        if (instance == null) {
            instance = new GlimpseManager(context);
            restoreFromPersistedState(context);
        }
        return instance;
    }

    private GlimpseManager(Context context) {
        this.context = context;
    }

    private void addId(int id) {
        notificationsQueue.add(id);
        persistState();
    }

    private void removeId(int id) {
        notificationsQueue.remove(id);
        persistState();
    }

    public Queue<Integer> getActiveNotificationIds() {
        return notificationsQueue;
    }

    private static void restoreFromPersistedState(Context context) {
        SharedPreferences pref = context.getSharedPreferences(GlimpseConfig.packageName, Context.MODE_PRIVATE);
        try {
            JSONArray jsonArray = new JSONArray(pref.getString(PREF_STATE_NOTIFICATION_QUEUE, "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                instance.notificationsQueue.add(jsonArray.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void persistState() {
        SharedPreferences pref = context.getSharedPreferences(GlimpseConfig.packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        JSONArray jsonArray = new JSONArray();
        for (Integer i : notificationsQueue) {
            jsonArray.put(i);
        }
        editor.putString(PREF_STATE_NOTIFICATION_QUEUE, jsonArray.toString());
        editor.commit();
    }

    /**
     * Generates a new ID subject to limit constraints and not already in use.
     * Prunes oldest. Rather have the latest than old ignored stuff.
     */
    public int nextAvailableId() {
        if (notificationsQueue.size() == GlimpseConfig.getMaxNumNotifications()) {
            int oldestId = notificationsQueue.peek();
            cancelNotificationWithId(oldestId);
        }

        int newId = getNextIntegerNotInUse();
        return newId;
    }

    private int getNextIntegerNotInUse() {
        Random rand = new Random();
        int candidate;
        while (true) {
            candidate = rand.nextInt();
            if (notificationsQueue.contains(candidate)) {
                continue;
            }
            break;
        }
        return candidate;
    }

    public synchronized void sendNotification(int id, NotificationCompat.Builder builder) {
        addId(id);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    public synchronized void cancelNotificationWithId(int id) {
        removeId(id);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    private static CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(EXTRA_INLINE_MESSAGE);
        }
        return null;
    }

    private Map<Integer, GlimpseActions> requestMap = new HashMap<>();

    public void setActionHandler(int requestCode, GlimpseActions actionHandler) {
        requestMap.put(requestCode, actionHandler);
    }

    public void handlePendingAction(Intent intent) {
        int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, NOT_SET);
        int requestCode = intent.getIntExtra(EXTRA_REQUEST_CODE, NOT_SET);

        GlimpseActions actionHandler = requestMap.get(requestCode);

        if (actionHandler != null) {
            switch (intent.getAction()) {
                case ACTION_ACTIVATE_CONTENT: {
                    actionHandler.onActivateContent(notificationId);
                    break;
                }
                case ACTION_1: {
                    actionHandler.onAction1(notificationId);
                    break;
                }
                case ACTION_2: {
                    actionHandler.onAction2(notificationId);
                    break;
                }
                case ACTION_3: {
                    actionHandler.onAction3(notificationId);
                    break;
                }
                case ACTION_INLINE_REPLY: {
                    CharSequence text = getMessageText(intent);
                    actionHandler.onInlineMessage(notificationId, text);
                    break;
                }
                case ACTION_DELETE: {
                    actionHandler.onDelete(notificationId);
                    break;
                }
            }
        }

        GlimpseManager.getInstance(context).cancelNotificationWithId(notificationId);
    }
}
