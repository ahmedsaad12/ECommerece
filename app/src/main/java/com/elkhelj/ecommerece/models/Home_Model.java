package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Home_Model implements Serializable {
     private int id;
             private String name;
             private String order;
           private List<Products> products;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

    public List<Products> getProducts() {
        return products;
    }

    public class Products implements Serializable
    {
        private int id;
            private String name;
            private String gender;
            private String price;
        private String  trend;
        private String  des;
        private String  form_id;
        private String  brand_id;
        private String  super_cat;
        private String  category_id;
        private String  sub_category_id;

        private String  image;
        private String  shop_name;
        private String  category_name;
        private String  sub_category_name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getPrice() {
            return price;
        }

        public String getTrend() {
            return trend;
        }

        public String getDes() {
            return des;
        }

        public String getForm_id() {
            return form_id;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public String getSuper_cat() {
            return super_cat;
        }

        public String getCategory_id() {
            return category_id;
        }

        public String getSub_category_id() {
            return sub_category_id;
        }

        public String getImage() {
            return image;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getCategory_name() {
            return category_name;
        }

        public String getSub_category_name() {
            return sub_category_name;
        }
    }
}
