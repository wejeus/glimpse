Glimpse
============

-- Simple notifications using builders and callbacks instead of messy intent handling.

Download
--------

```groovy
dependencies {
  compile 'com.isalldigital:glimpse:0.1.0-SNAPSHOT'
}
```

Note: to get snapshot version you must add the the Sonatype snapshot repository to your Maven configuration:

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots/"
    }
}
```

Usage
--------

First init Glimpse in your application class.

```java
public class ApplicationDelegate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        GlimpseConfig.init(R.drawable.ic_launcher, R.string.app_name, R.color.colorPrimary, 5);
        GlimpseConfig.setReceiver(NotificationActionReceiver.class);
    }
}
```

Create a notification and send it.

```java
SimpleGlimpse.Builder builder = new SimpleGlimpse.Builder(MainActivity.this, NotificationActionReceiver.SIMPLE_NOTIFICATION_WITH_ACTION_REQUEST_CODE);
                int notificationId = builder
                        .setContentTitle("Example 2")
                        .setContentText("Notification of type 2 with actions")
                        .addAction("Done")
                        .addAction("Abort")
                        .build()
                        .send();
```

THATS IT!

...however if you want to handle some user interaction just define a simple receiver of your actions. Interactions between Android and your app is handle by Glimpse and makes it easier for you by letting you define callbacks instead of messy intent handling.

Here is how you do it:

```java
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
```

Dont forget to register your receiver in AndroidManifest.xml

```xml
<receiver
    android:name=".NotificationActionReceiver"
    android:exported="false" >
</receiver>
```


License
-------

    Copyright 2016 Samuel Wejeus

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


