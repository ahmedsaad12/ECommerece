package com.elkhelj.ecommerece.models;

import java.io.Serializable;
import java.util.List;

public class Single_Adversiment_Model implements Serializable {




private Products singelproduct;
    public Products getSingelproduct() {
        return singelproduct;
    }



    public class Products implements Serializable
    {
        private List<Youmaylike> youmaylike;
private List<Rates> rates;
        public List<Youmaylike> getYoumaylike() {
            return youmaylike;
        }
        private List<Images> product_images;
        private List<Colors> colors;
        private List<Sizes>sizes;
        public List<Images> getProduct_images() {
            return product_images;
        }
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
        public List<Colors> getColors() {
            return colors;
        }

        public List<Rates> getRates() {
            return rates;
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

        public class Youmaylike implements Serializable
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
        public class Rates implements Serializable
        {
           private double rate;
           private String comment;
           private String from_name;
           private String from_image;
private String created_at;
            public double getRate() {
                return rate;
            }

            public String getComment() {
                return comment;
            }

            public String getFrom_name() {
                return from_name;
            }

            public String getFrom_image() {
                return from_image;
            }

            public String getCreated_at() {
                return created_at;
            }
        }
}

}
