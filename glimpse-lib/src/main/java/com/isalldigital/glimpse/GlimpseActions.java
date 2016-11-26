package com.isalldigital.glimpse;


import android.support.annotation.Nullable;

/**
 * GlimpseActions define the potential callbacks you can receive when a user interacts
 * with a notification. Extend this class and override only the onces you are interested in.
 *
 * Currently Android enforces a limit on a maximum of 3 notification actions.
 * Glimpse enforces this limit.
 */
public abstract class GlimpseActions {

    public static final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";
    public static final String EXTRA_REQUEST_CODE = "EXTRA_REQUEST_CODE";
    public static final String EXTRA_INLINE_MESSAGE = "EXTRA_INLINE_MESSAGE";

    public static final String ACTION_ACTIVATE_CONTENT = "ACTION_ACTIVATE_CONTENT";
    public static final String ACTION_1 = "ACTION_1";
    public static final String ACTION_2 = "ACTION_2";
    public static final String ACTION_3 = "ACTION_3";
    public static final String ACTION_INLINE_REPLY = "ACTION_INLINE_REPLY";
    public static final String ACTION_DELETE = "ACTION_DELETE";

    public static final int NOT_SET = -1;

    public void onActivateContent(int id) {
        /* Override if needed */
    }

    public void onAction1(int id) {
        /* Override if needed */
    }

    public void onAction2(int id) {
        /* Override if needed */
    }

    public void onAction3(int id) {
        /* Override if needed */
    }

    public void onInlineMessage(int id, @Nullable CharSequence text) {
        /* Override if needed */
    }

    public void onDelete(int id) {
        /* Override if needed */
    }
}
