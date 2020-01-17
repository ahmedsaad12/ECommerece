package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Market_Profile_Model  implements Serializable {
    private UserModel user;
    private List<Followers> followers;
    private MyLastRate myLastRate;
private List<Products> trends;

    public List<Products> getTrends() {
        return trends;
    }

    public UserModel getUser() {
        return user;
    }

    public List<Followers> getFollowers() {
        return followers;
    }

    public MyLastRate getMyLastRate() {
        return myLastRate;
    }

    public class Followers implements Serializable
    {
        private int id;
            private String name;
            private String address;
            private double latitude;
            private double longitude;
            private int is_login;
            private String shop_name;
            private String phone_code;
            private String phone;
            private String image;
            private String type;
            private String bannar_image;
            private String email;
            private String email_verified_at;
            private String country;
            private int city;

            private String user_name;
            private String user_image;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getIs_login() {
            return is_login;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getImage() {
            return image;
        }

        public String getType() {
            return type;
        }

        public String getBannar_image() {
            return bannar_image;
        }

        public String getEmail() {
            return email;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public String getCountry() {
            return country;
        }

        public int getCity() {
            return city;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_image() {
            return user_image;
        }
    }
    public class MyLastRate implements Serializable {

                private int rate;

        public int getRate() {
            return rate;
        }
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
