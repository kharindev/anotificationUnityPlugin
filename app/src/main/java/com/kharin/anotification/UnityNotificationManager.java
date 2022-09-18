package com.kharin.anotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.kharin.anotification.alarm.AlarmNotifications;
import com.unity3d.player.UnityPlayer;

public class UnityNotificationManager {
    public static final String TITLE_KEY ="title_key";
    public static final String MESSAGE_KEY ="message_key";
    public static final String CHANNEL_ID_KEY ="channel_id_key";
    public static final String REQUEST_CODE_KEY ="request_code_key";
    public static final String SMALL_ICON_KEY ="small_icon_key";
    public static final String CLASS_KEY ="class_key";
    public static final String OPEN_PARAMETER_KEY ="open_parameter_key";

    private static String channelId = "default";

    public static void init(String id, String name, String description){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = id;
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            android.app.NotificationManager notificationManager = UnityPlayer.currentActivity.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void scheduledNotification(int notificationId, String title, String message, int hours, int minutes, int seconds, String iconKey, String openParameter){
        AlarmNotifications.scheduledNotification(notificationId, title, message, hours, minutes, seconds,iconKey, openParameter, channelId);
    }

    public static void cancelNotification(int code) {
        AlarmNotifications.cancelNotification(code);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UnityPlayer.currentActivity);
        notificationManager.cancel(code);
    }

    public static void showNotification(NotificationSettings settings)
    {
        Intent intent = new Intent(settings.context, settings.aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(OPEN_PARAMETER_KEY, settings.openParameter);

        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(settings.context, settings.code, intent, flag);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(settings.context, settings.channelId)
                .setSmallIcon(settings.smallIcon)
                .setContentTitle(settings.title)
                .setContentText(settings.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(settings.context);
        notificationManager.notify(settings.code, builder.build());
    }

    public static String getOpenParameter(){
        Activity activity = UnityPlayer.currentActivity;
        return GetParameter(activity.getIntent(), OPEN_PARAMETER_KEY);
    }

    public static String GetParameter(Intent intent, String key){
        return intent.hasExtra(key) ? intent.getStringExtra(key) : "empty";
    }
}
