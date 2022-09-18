package com.kharin.anotification;

import android.content.Context;
import android.content.res.Resources;

public class NotificationSettings {
    public Context context;
    public Class<?> aClass;
    public String title;
    public String message;
    public String channelId;
    public String openParameter;
    public int smallIcon;
    public int code;

    public NotificationSettings(Context activity){
        this.context = activity;
    }

    public NotificationSettings withTitle(String title) {
        this.title = title;
        return this;
    }

    public NotificationSettings withMessage(String message) {
        this.message = message;
        return this;
    }

    public NotificationSettings withChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public NotificationSettings withCode(int code) {
        this.code = code;
        return this;
    }

    public NotificationSettings withSmallIcon(String iconKey) {

        smallIcon = R.drawable.notification;
        if(iconKey == null ||iconKey.isEmpty()) return this;
        int iconId = findResourceIdInContextByName(context, iconKey);
        if (iconId == 0) {
            iconId = context.getApplicationInfo().icon;
        }
        smallIcon = iconId;

        return this;
    }

    public NotificationSettings withOpenParameter(String openParameter) {
        this.openParameter = openParameter;
        return this;
    }

    protected  int findResourceIdInContextByName(Context context, String name) {
        if (name == null)
            return 0;

        try {
            Resources res = context.getResources();
            if (res != null) {
                int id = res.getIdentifier(name, "mipmap", context.getPackageName());//, activity.getPackageName());
                if (id == 0)
                    return res.getIdentifier(name, "drawable", context.getPackageName());//, activity.getPackageName());
                else
                    return id;
            }
            return 0;
        } catch (Resources.NotFoundException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "NotificationSettings{" +
                "context=" + context +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", channelId='" + channelId + '\'' +
                ", smallIcon=" + smallIcon +
                ", code=" + code +
                ", aClass=" + aClass +
                '}';
    }

    public NotificationSettings withActivityClass(String aClass) {
        try {
            this.aClass = Class.forName(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }


}
