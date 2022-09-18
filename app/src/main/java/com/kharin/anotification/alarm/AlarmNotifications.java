package com.kharin.anotification.alarm;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import com.kharin.anotification.UnityNotificationManager;
import com.unity3d.player.UnityPlayer;

import java.util.Calendar;

public class AlarmNotifications {

    public static void scheduledNotification(int notificationId, String title, String message, int hours, int minutes, int seconds, String iconKey, String openParameter, String channelId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);

        Intent notificationIntent = new Intent(UnityPlayer.currentActivity, AlarmReceiver.class);
        notificationIntent.putExtra(UnityNotificationManager.REQUEST_CODE_KEY, notificationId);
        notificationIntent.putExtra(UnityNotificationManager.TITLE_KEY, title);
        notificationIntent.putExtra(UnityNotificationManager.MESSAGE_KEY, message);
        notificationIntent.putExtra(UnityNotificationManager.SMALL_ICON_KEY, iconKey);
        notificationIntent.putExtra(UnityNotificationManager.CHANNEL_ID_KEY, channelId);
        notificationIntent.putExtra(UnityNotificationManager.CLASS_KEY, UnityPlayer.currentActivity.getClass().getName());
        notificationIntent.putExtra(UnityNotificationManager.OPEN_PARAMETER_KEY, openParameter);

        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(
                UnityPlayer.currentActivity,
                notificationId,
                notificationIntent,
                flag);
        AlarmManager am = (AlarmManager) UnityPlayer.currentActivity.getSystemService(ALARM_SERVICE);
        boolean canAlarm = checkAlarmsAccess(am);
        if (am != null && notificationPendingIntent != null && canAlarm) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
            } else{
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
            }
        }
    }

    private static boolean checkAlarmsAccess(AlarmManager alarmManager)  {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
            return true;
        }
        return alarmManager.canScheduleExactAlarms();
    }

    public static void cancelNotification(int requestCode) {
        Intent intent1 = new Intent(UnityPlayer.currentActivity, AlarmReceiver.class);
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(UnityPlayer.currentActivity, requestCode, intent1, flag);
        AlarmManager am = (AlarmManager) UnityPlayer.currentActivity.getSystemService(ALARM_SERVICE);
        if (am != null && pendingIntent != null) {
            am.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
