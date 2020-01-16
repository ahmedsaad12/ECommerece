package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class NotificationDataModel implements Serializable {
    private int current_page;
    private List<NotificationModel> data;

    public int getCurrent_page() {
        return current_page;
    }

    public List<NotificationModel> getData() {
        return data;
    }

    public class NotificationModel implements Serializable {
private long notification_date;

        public long getNotification_date() {
            return notification_date;
        }
    }



}
