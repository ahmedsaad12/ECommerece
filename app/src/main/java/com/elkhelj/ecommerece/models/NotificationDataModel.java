package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class NotificationDataModel implements Serializable {

private long notification_date;
    private String from_user_name;

        public long getNotification_date() {
            return notification_date;
        }

    public String getFrom_user_name() {
        return from_user_name;
    }
}
