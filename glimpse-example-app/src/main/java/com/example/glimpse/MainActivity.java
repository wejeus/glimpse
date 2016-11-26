package com.example.glimpse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.isalldigital.glimpse.types.SimpleGlimpse;

public class MainActivity extends AppCompatActivity {

    int notificationId = 0;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNotificationButton = (Button) findViewById(R.id.button_create_new_notification);
        createNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleGlimpse.Builder builder = new SimpleGlimpse.Builder(MainActivity.this, NotificationActionReceiver.SIMPLE_NOTIFICATION_REQUEST_CODE);

                notificationId = builder
                        .setContentTitle("NotificationTitle")
                        .setContentText("NotificationText: " + i++)
                        .build()
                        .send();
            }
        });

        Button appendToNotificationButton = (Button) findViewById(R.id.button_append_to_notification);
        appendToNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleGlimpse.Builder builder = new SimpleGlimpse.Builder(MainActivity.this, NotificationActionReceiver.SIMPLE_NOTIFICATION_WITH_ACTION_REQUEST_CODE);

                notificationId = builder
                        .setContentTitle("With Action")
                        .addAction("Done")
                        .addAction("Done1")
                        .addAction("Done2")
                        .build()
                        .send();
            }
        });
    }
}
