package com.example.glimpse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.isalldigital.glimpse.GlimpseManager;
import com.isalldigital.glimpse.types.SimpleGlimpse;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private TextView activeNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNotificationButton = (Button) findViewById(R.id.button_create_new_notification);
        createNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleGlimpse.Builder builder = new SimpleGlimpse.Builder(MainActivity.this, NotificationActionReceiver.SIMPLE_NOTIFICATION_REQUEST_CODE);
                int notificationId = builder
                        .setContentTitle("Example 1")
                        .setContentText("Notification of type 1")
                        .build()
                        .send();
                refreshActiveNotifications();
            }
        });

        Button appendToNotificationButton = (Button) findViewById(R.id.button_append_to_notification);
        appendToNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleGlimpse.Builder builder = new SimpleGlimpse.Builder(MainActivity.this, NotificationActionReceiver.SIMPLE_NOTIFICATION_WITH_ACTION_REQUEST_CODE);
                int notificationId = builder
                        .setContentTitle("Example 2")
                        .setContentText("Notification of type 2 with actions")
                        .addAction("Done")
                        .addAction("Abort")
                        .build()
                        .send();
                refreshActiveNotifications();
            }
        });

        activeNotifications = (TextView) findViewById(R.id.active_notifications);
        refreshActiveNotifications();
    }

    private void refreshActiveNotifications() {
        Queue<Integer> list = GlimpseManager.getInstance(this).getActiveNotificationIds();
        if (list.size() == 0) {
            activeNotifications.setText("<none>");
        } else {
            String s = new String();
            for (Integer i : list) {
                s += i + "\n";
            }
            activeNotifications.setText(s);
        }
    }
}
