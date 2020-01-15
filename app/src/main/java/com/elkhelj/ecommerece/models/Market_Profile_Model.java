package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Market_Profile_Model  implements Serializable {
    private UserModel user;
    private List<Followers> followers;
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
}
