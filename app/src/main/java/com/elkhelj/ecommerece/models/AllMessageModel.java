package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class AllMessageModel implements Serializable {
    private int type;
    private String message;

    public AllMessageModel(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

}
