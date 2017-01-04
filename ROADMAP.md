## TODO

* Need to synchronize state for active notifications with actual active between app re:installs

* Imlement type for "Bundled notifications" (seems to bundle automatically on android 7..). To handle this an ADDITIONAL "summary notification" must be issued to describe the summary of all current notifications. Create this as normal (but with different content) and cal .setGroupSummary(true) to indicate it is special

* Implement functionality to handle update of existing notification

* Optional implement notification with customViews

* Transition GlimpseConfig into builder pattern

* TODO: Implement a service can listen for all notifications in system: https://developer.android.com/reference/android/service/notification/NotificationListenerService.html#getActiveNotifications() (GlimpseNotificationListenerService)

* Implement type for "expanded layout" (uses a Notification.Style) https://developer.android.com/reference/android/app/Notification.Style.html Known Direct Subclasses BigPictureStyle, BigTextStyle, DecoratedCustomViewStyle, InboxStyle, MediaStyle, MessagingStyle
//BuilderUtils
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public static NotificationCompat.Builder setExpandedLayout(Context context, NotificationCompat.Builder builder) {
    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
    inboxStyle.setBigContentTitle("Event tracker details:");
    for (int i=0; i < 3; i++) {
        inboxStyle.addLine("asdf");
    }
    return builder.setStyle(inboxStyle);
}