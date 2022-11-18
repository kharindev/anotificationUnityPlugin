package com.kharin.anotification.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kharin.anotification.NotificationSettings;
import com.kharin.anotification.UnityNotificationManager;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String DefaultActivityClass = "com.google.firebase.MessagingUnityPlayerActivity";
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.hasExtra(UnityNotificationManager.TITLE_KEY) ? intent.getStringExtra(UnityNotificationManager.TITLE_KEY) : "Title";
        String message = intent.hasExtra(UnityNotificationManager.MESSAGE_KEY) ? intent.getStringExtra(UnityNotificationManager.MESSAGE_KEY) : "Message";
        String channelId = intent.hasExtra(UnityNotificationManager.CHANNEL_ID_KEY) ? intent.getStringExtra(UnityNotificationManager.CHANNEL_ID_KEY) : "default";
        int code = intent.getIntExtra(UnityNotificationManager.REQUEST_CODE_KEY, 0);
        String smallIcon = intent.hasExtra(UnityNotificationManager.SMALL_ICON_KEY) ? intent.getStringExtra(UnityNotificationManager.SMALL_ICON_KEY):"";
        String aClass = intent.hasExtra(UnityNotificationManager.CLASS_KEY) ? intent.getStringExtra(UnityNotificationManager.CLASS_KEY):"";
        String openParameter = intent.hasExtra(UnityNotificationManager.OPEN_PARAMETER_KEY) ? intent.getStringExtra(UnityNotificationManager.OPEN_PARAMETER_KEY):"";

        NotificationSettings settings = new NotificationSettings(context)
                .withTitle(title)
                .withMessage(message)
                .withChannelId(channelId)
                .withCode(code)
                .withSmallIcon(smallIcon)
                .withOpenParameter(openParameter)
                .withActivityClass(aClass,DefaultActivityClass);

        UnityNotificationManager.showNotification(settings);
    }
}

