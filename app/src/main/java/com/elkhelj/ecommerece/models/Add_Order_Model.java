package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Add_Order_Model implements Serializable {


    private int user_id;


  private List<Orders_Cart_Model> order_detials;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Orders_Cart_Model> getOrder_detials() {
        return order_detials;
    }

    public void setOrder_detials(List<Orders_Cart_Model> order_detials) {
        this.order_detials = order_detials;
    }
}
