package com.app.superdistributor;

public class NotificationItemModel {
    String notificationType;
    String notificationTag;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    String notificationDesc;

    public NotificationItemModel(){}

    public NotificationItemModel(String notificationType, String notificationDesc, String notificationTag ) {
        this.notificationType = notificationType;
        this.notificationTag = notificationTag;
        this.notificationDesc = notificationDesc;
    }

    public String getNotificationTag() {
        return notificationTag;
    }

    public void setNotificationTag(String notificationTag) {
        this.notificationTag = notificationTag;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }
}
