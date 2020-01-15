package com.elkhelj.ecommerece.services;


import com.elkhelj.ecommerece.models.Add_Order_Model;
import com.elkhelj.ecommerece.models.Cities_Model;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @GET("api/all_cities")
    Call<List<Cities_Model>> getCity();

    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(
            @Field("kayWord") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUp(@Field("name") String name,
                           @Field("shop_name") String shop_name,
                           @Field("email") String email,
                           @Field("password") String password,
                           @Field("phone") String phone,
                           @Field("phone_code") String phone_code,

                           @Field("type") String type,
                           @Field("shop_for") String shop_for,
                           @Field("country") String country,
                           @Field("city") String city

    );

    @FormUrlEncoded
    @POST("api/home")
    Call<List<Home_Model>> getproducts(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/home")
    Call<Home_Model> getproductss(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/filter_shop_name")
    Call<List<Home_Model>> getSHOPS(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/contact_us")
    Call<ResponseBody> sendContact(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("subject") String subject,
                                   @Field("message") String message
    );
    @FormUrlEncoded
    @POST("api/single_product")
    Call<Single_Adversiment_Model> getSingleAds(

            @Field("product_id") String product_id
    );
    @POST("api/order/add")
    Call<ResponseBody> accept_orders(@Body Add_Order_Model add_order_model);
}