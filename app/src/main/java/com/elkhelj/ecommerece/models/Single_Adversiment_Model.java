package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Single_Adversiment_Model implements Serializable {



private List<Images> product_images;

    public List<Images> getProduct_images() {
        return product_images;
    }

    public class Images implements Serializable
    {
        private int id;
        private int ad_id;
            private String image;

        public int getId() {
            return id;
        }

        public int getAd_id() {
            return ad_id;
        }

        public String getImage() {
            return image;
        }
    }

}
