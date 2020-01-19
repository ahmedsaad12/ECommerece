package com.elkhelj.ecommerece.models;

import java.io.Serializable;

public class MessageModel  implements Serializable {
    private SingleMessageModel data;

    public SingleMessageModel getData() {
        return data;
    }

    public class SingleMessageModel implements Serializable {
               private int type;
             private String message;

        public int getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }
}