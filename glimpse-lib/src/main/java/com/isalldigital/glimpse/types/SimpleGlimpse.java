package com.isalldigital.glimpse.types;

import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.isalldigital.glimpse.BuilderUtils;
import com.isalldigital.glimpse.GlimpseActions;
import com.isalldigital.glimpse.GlimpseManager;

/**
 * Simple notification that just shows a message. Does not group or expand.
 */
public class SimpleGlimpse extends Glimpse {

    private SimpleGlimpse(Context context, int id, int requestCode, NotificationCompat.Builder builder) {
        super(context, id, requestCode, builder);
    }

    public static class Builder {

        private Context context;
        private int id;
        private int requestCode;
        private NotificationCompat.Builder notificationBuilder;

        private int numActions = 0;

        public Builder(Context context, int requestCode) {
            this.id = GlimpseManager.getInstance(context).nextAvailableId();
            this.requestCode = requestCode;
            this.context = context.getApplicationContext();
            this.notificationBuilder = new NotificationCompat.Builder(context);

            notificationBuilder.setCategory(NotificationCompat.CATEGORY_REMINDER);
        }

        public Builder setContentTitle(String text) {
            notificationBuilder.setContentTitle(text);
            return this;
        }

        public Builder setContentText(String text) {
            notificationBuilder.setContentText(text);
            return this;
        }

        public Builder addAction(String label) {
            if (numActions == 3) {
                throw new RuntimeException("Can only handle maximum of 3 actions for notifications!");
            }

            switch (numActions) {
                case 0: {
                    BuilderUtils.addAction(context, id, requestCode, notificationBuilder, GlimpseActions.ACTION_1, label);
                    break;
                }
                case 1: {
                    BuilderUtils.addAction(context, id, requestCode, notificationBuilder, GlimpseActions.ACTION_2, label);
                    break;
                }
                case 2: {
                    BuilderUtils.addAction(context, id, requestCode, notificationBuilder, GlimpseActions.ACTION_3, label);
                    break;
                }
            }
            numActions++;
            return this;
        }

        public SimpleGlimpse build() {
            return new SimpleGlimpse(context, id, requestCode, notificationBuilder);
        }
    }
}
