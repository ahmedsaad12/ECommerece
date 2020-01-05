package com.elkhelj.ecommerece.services;




import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(
            @Field("keyword") String mobile,
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
                           @Field("shop_for") String shop_for

    );
    @FormUrlEncoded
    @POST("api/home")
    Call<List<Home_Model>> getproducts(@Field("key_word") String key_word
    );
}