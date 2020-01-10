package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Single_Adversiment_Model implements Serializable {



private List<Images> product_images;
private List<Colors> colors;
private List<Sizes>sizes;
    public List<Images> getProduct_images() {
        return product_images;
    }

    public List<Colors> getColors() {
        return colors;
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
  public class Colors implements Serializable
    {
        private int id;
            private String name;
            private String color;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }
    }

    public List<Sizes> getSizes() {
        return sizes;
    }

    public class Sizes implements Serializable
    {
        private int id;
            private String name;
            private int size;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }
}
